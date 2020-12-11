/*
My BiblioApp
Created by:
            Alexis Berrio Arenas
 */
package com.alexisberrio.formulario.ui.splash

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.databinding.ActivitySplashBinding
import com.alexisberrio.formulario.ui.bottom.BottomActivity
import com.alexisberrio.formulario.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private var anim: Animation? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        // Carga la animación inicial para el logo
        anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        // Carga la instancia actual de firebase authentication
        auth = FirebaseAuth.getInstance()

        // Corre la animación actual
        anim?.run {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {

                    // Veifica si hay una sesión iniciada la app
                    val auth = FirebaseAuth.getInstance().currentUser
                    if (auth == null) {
                        goToLoginActivity() // Si no hay una va a la actividad de login
                    } else {
                        goToBottomActivity() // Si existe,v a directamente a la actividad principal
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            binding.imageView.startAnimation(anim)
        }

    }

    // Salta a la actividad con una transición y una bienvenida al usuario actual
    private fun goToBottomActivity() {
        val intent = Intent(this, BottomActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)
        Toast.makeText(
            baseContext, "Saludos ${auth.currentUser?.email}",
            Toast.LENGTH_SHORT
        ).show()
    }

    // Ends this activity and goes to Login
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        overridePendingTransition(R.anim.zoom_in, R.anim.static_animation)

    }
}