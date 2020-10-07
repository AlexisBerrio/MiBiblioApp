package com.alexisberrio.formulario

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Timer to wait 1 sec on this activity and jump to Login
        val timer = Timer()
        timer.schedule(
            timerTask {
                goToLoginActivity()
            }, 1000
        )

    }

    // Ends this activity and goes to Login
    fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}