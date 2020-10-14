/*
My BiblioApp
Created by:
            Alexis Berrio Arenas
 */
package com.alexisberrio.formulario

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
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
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

    }
}