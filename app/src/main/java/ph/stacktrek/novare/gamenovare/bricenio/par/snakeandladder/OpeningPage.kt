package ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.databinding.ActivityOpeningPageBinding
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.loading.LoadingDialog

class OpeningPage : AppCompatActivity() {
    private lateinit var binding: ActivityOpeningPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpeningPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingDialog = LoadingDialog(this)

        loadingDialog.startLoading()

        Handler().postDelayed({
            // Dismiss the loading dialog after a 2-second delay
            loadingDialog.isDismiss()
            val goToMain = Intent(applicationContext, MainActivity::class.java)
            startActivity(goToMain)
            finish()
        }, 2000)


    }
}