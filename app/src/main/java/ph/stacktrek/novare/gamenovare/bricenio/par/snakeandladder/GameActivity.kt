package ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.databinding.ActivityGameBinding
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.utility.PreferenceUtility
import kotlin.random.Random


class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    var turn: Int = 0
    var winner: String = ""
    var playerTurn: String = ""
    var rolledNumber: Int = 0
    private var playerPositions = arrayOf(0, 0, 0, 0)
    private val snakes = mapOf(33 to 5, 54 to 34, 59 to 17, 93 to 75, 97 to 62)
    private val ladders = mapOf(2 to 40, 8 to 31, 46 to 84, 49 to 67, 70 to 91, 80 to 99)
    private var doubleBackToExitPressedOnce = false

    fun rollDice(){
        rolledNumber = Random!!.nextInt(6) + 1
        when(rolledNumber) {
            1 -> binding.diceLayout.setImageResource(R.drawable.roll1)
            2 -> binding.diceLayout.setImageResource(R.drawable.roll2)
            3 -> binding.diceLayout.setImageResource(R.drawable.roll3)
            4 -> binding.diceLayout.setImageResource(R.drawable.roll4)
            5 -> binding.diceLayout.setImageResource(R.drawable.roll5)
            else -> binding.diceLayout.setImageResource(R.drawable.roll6)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        boardLayout()

        val numPlayersStr = PreferenceUtility(applicationContext)
            .getStringPreferences("numPlayers", "2")
        val playerCount = numPlayersStr?.toInt()
        playerPositions = Array(playerCount!!) { 0 }

        val player1 = PreferenceUtility(applicationContext)
            .getStringPreferences("nickname1", "Player1")
        val player2 = PreferenceUtility(applicationContext)
            .getStringPreferences("nickname2", "Player2")
        val player3 = PreferenceUtility(applicationContext)
            .getStringPreferences("nickname3", "Player3")
        val player4 = PreferenceUtility(applicationContext)
            .getStringPreferences("nickname4", "Player4")

        if (playerCount == 2){
            binding.player1Score.setText("${player1} (position = ${playerPositions[0]})")
            binding.player2Score.setText("${player2} (position = ${playerPositions[1]})")
            binding.player3Score.visibility = View.INVISIBLE
            binding.player4Score.visibility = View.INVISIBLE
        }
        else if (playerCount == 3){
            binding.player1Score.setText("${player1} (position = ${playerPositions[0]})")
            binding.player2Score.setText("${player2} (position = ${playerPositions[1]})")
            binding.player3Score.setText("${player3} (position = ${playerPositions[2]})")
            binding.player4Score.visibility = View.INVISIBLE
        }
        else {
            binding.player1Score.setText("${player1} (position = ${playerPositions[0]})")
            binding.player2Score.setText("${player2} (position = ${playerPositions[1]})")
            binding.player3Score.setText("${player3} (position = ${playerPositions[2]})")
            binding.player4Score.setText("${player4} (position = ${playerPositions[3]})")
        }

        binding.turnView.setText("${player1}'s Turn")

        binding.buttonRoll.setOnClickListener {
            rollDice()
            val currentPlayerPosition = playerPositions[turn]
            var newPosition = currentPlayerPosition + rolledNumber
            newPosition = snakes[newPosition] ?: ladders[newPosition] ?: newPosition
            playerPositions[turn] = newPosition

            if (newPosition == 100) {
                binding.buttonRoll!!.isEnabled = false

                // check winner
                when (turn + 1) {
                    1 -> winner = player1.toString()
                    2 -> winner = player2.toString()
                    3 -> winner = player3.toString()
                    4 -> winner = player4.toString()
                }

                //Winner's nickname to save and display on Congrats Page
                val winnerPref = winner.toString()
                PreferenceUtility(applicationContext).apply {
                    saveStringPreferences("winner", winnerPref)
                }

                //Winner's nickname to save as List and used on RecyclerView
                var intent =Intent(applicationContext, CongratsPage::class.java)

                val preferences: SharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(applicationContext)
                val winnersJson = preferences.getString("winners", "[]") ?: "[]"
                val newWinners = Gson().fromJson(winnersJson, object :
                    TypeToken<MutableList<String>>() {}.type) as MutableList<String>

                newWinners.add(winner)
                val newWinnerJSON = Gson().toJson(newWinners)
                preferences.edit().putString("winners", newWinnerJSON).apply()

                startActivity(intent)
                finish()
            } else {

                if(newPosition > 100) {
                    val exceededMove = newPosition - 100
                    val backward = 100 - exceededMove
                    newPosition = backward
                    newPosition = snakes[newPosition] ?: ladders[newPosition] ?: newPosition
                    playerPositions[turn] = newPosition
                }

                playerPositions[turn] = newPosition

                val player1 = PreferenceUtility(applicationContext)
                    .getStringPreferences("nickname1", "Player1")
                val player2 = PreferenceUtility(applicationContext)
                    .getStringPreferences("nickname2", "Player2")
                val player3 = PreferenceUtility(applicationContext)
                    .getStringPreferences("nickname3", "Player3")
                val player4 = PreferenceUtility(applicationContext)
                    .getStringPreferences("nickname4", "Player4")


                if (playerCount == 2){
                    binding.player1Score.setText(player1 + " (position = ${playerPositions[0]})")
                    binding.player2Score.setText(player2 + " (position = ${playerPositions[1]})")
                }
                else if (playerCount == 3){
                    binding.player1Score.setText(player1 + " (position = ${playerPositions[0]})")
                    binding.player2Score.setText(player2 + " (position = ${playerPositions[1]})")
                    binding.player3Score.setText(player3 + " (position = ${playerPositions[2]})")
                }
                else{
                    binding.player1Score.setText(player1 + " (position = ${playerPositions[0]})")
                    binding.player2Score.setText(player2 + " (position = ${playerPositions[1]})")
                    binding.player3Score.setText(player3 + " (position = ${playerPositions[2]})")
                    binding.player4Score.setText(player4 + " (position = ${playerPositions[3]})")
                }

                if(turn+1 < playerCount!!){
                    turn ++
                } else {
                    turn = 0
                }

                when(turn+1) {
                    1 -> playerTurn = player1.toString()
                    2 -> playerTurn = player2.toString()
                    3 -> playerTurn = player3.toString()
                    4 -> playerTurn = player4.toString()
                }
                binding.turnView.setText("${playerTurn}'s Turn")
            }

            boardLayout()

        }
    }
    private fun boardLayout() {
        val gameBoard = binding.board

        // Remove all views from the table if there are existing
        gameBoard.removeAllViews()

        for (row in 0 until 10) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT,
                1f
            )

            for (col in 0 until 10) {
                val button = Button(this)
                button.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1f
                ).apply {
                    setMargins(2, 2, 2, 2)
                }

                val positions = if (row % 2 == 0) {
                    100 - (row * 10 + col)
                } else {
                    100 - (row * 10 + (9 - col))
                }
                // Set icon position on the board
                when {
                    playerPositions.any { it == positions } -> {
                        val playerIndex = playerPositions.indexOfFirst { it == positions }
                        val playerColor = when (playerIndex) {
                            0 -> R.drawable.baseline_person_pin_circle_p1
                            1 -> R.drawable.baseline_person_pin_circle_p2
                            2 -> R.drawable.baseline_person_pin_circle_p3
                            else -> R.drawable.baseline_person_pin_circle_p4
                        }
                        button.setBackgroundResource(playerColor)
                    }
                    else -> button.setBackgroundColor(Color.TRANSPARENT)
                }

                // Add column to table row
                tableRow.addView(button)
            }

            // Add row to table layout
            gameBoard.addView(tableRow)
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Snackbar.make(binding.root,"Please click BACK again to exit",
                 Snackbar.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable
        { doubleBackToExitPressedOnce = false }
        , 2000)
    }
}