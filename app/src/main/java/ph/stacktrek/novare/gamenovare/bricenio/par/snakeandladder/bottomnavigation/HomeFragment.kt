package ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.bottomnavigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.GameActivity
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.databinding.FragmentHomeBinding
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.utility.PreferenceUtility
import kotlin.system.exitProcess


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnRules.setOnClickListener(){
            showRules()
        }

        binding.btnMultiplayer.setOnClickListener(){
            showMultiplayerOptions()
        }

        binding.btnExit.setOnClickListener(){
            exitApp()
        }

        // Inflate the layout for this fragment

        return binding.root


    }

    @SuppressLint("ResourceType")
    private fun exitApp() {
        val exitMessage = "Do you want to close the app?"
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(exitMessage)

        builder.setPositiveButton("Yes") { dialog, which ->
            exitProcess(0)
        }
        builder.setNegativeButton("No") { dialog, which ->
            //Do nothing
        }

        builder.show()
    }

    private fun showRules() {
       val rulesMessage =
           "1. The minimum number of players is 2 and maximum is 4 and board size is 100 (10x10)\n"+
           "\n2. Players can change their names in the User Profile Page\n" +
           "\n3. Possible outcomes by throwing a dice are 1,2,3,4,5, and 6 \n" +
           "\n4. Snakes will take you backwards in the journey \n" +
           "\n5. Ladders will push you ahead, you can call them shortcuts \n" +
           "\n6. Players must use the 'roll' button to roll the dice \n" +
           "\n7. If player's current position roll is greater than 100, game continues, the other "+
           "\n player/s will get a chance to throw the dice \n" +
           "\n8. Any player reaching 100 earlier than the other player " +
           "\nwill be the WINNER and the game will end "
       val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("RULES OF THE GAME")
        builder.setMessage(rulesMessage)
        builder.setPositiveButton("I UNDERSTAND") { dialog, which ->
                    //Do nothing
        }

        builder.show()
    }

    private fun showMultiplayerOptions() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose number of players")

        val playerOptions = arrayOf("2 players", "3 players", "4 players")
        var selectedOption = 0

        builder.setSingleChoiceItems(playerOptions, selectedOption) { _, which ->
            selectedOption = which
        }

        builder.setPositiveButton("Start") { _, _ ->
            val intent = Intent(requireContext(), GameActivity::class.java)
            val numPlayers = selectedOption + 2
            PreferenceUtility(requireContext()).apply {
                saveStringPreferences("numPlayers", numPlayers.toString())
            }
            startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { _, _ -> }
        builder.create().show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}