package ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.databinding.ActivityCongratsPageBinding
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.utility.PreferenceUtility

class CongratsPage : AppCompatActivity() {
    private lateinit var binding: ActivityCongratsPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongratsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PreferenceUtility(applicationContext).apply {
            val prefWin = getStringPreferences("winner")
            binding.winnerId.setText(prefWin + " WON!")
        }

        binding.btnCongratsHome.setOnClickListener(){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}