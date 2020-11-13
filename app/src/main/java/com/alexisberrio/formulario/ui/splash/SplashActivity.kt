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
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.databinding.ActivitySplashBinding
import com.alexisberrio.formulario.ui.bottom.BottomActivity
import com.alexisberrio.formulario.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

// TODO Cambiar transición de la actividad

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    var anim: Animation? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        auth = FirebaseAuth.getInstance()


        anim?.run {
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {

                    val auth = FirebaseAuth.getInstance().currentUser
                    if (auth == null) {
                        goToLoginActivity()
                    } else {
                        goToBottomActivity()
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
            binding.imageView.startAnimation(anim)
        }

    }

    private fun goToBottomActivity() {
        val intent = Intent(this, BottomActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    // Ends this activity and goes to Login
    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)

    }
}