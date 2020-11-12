package com.alexisberrio.formulario.ui.login

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.databinding.ActivityLoginBinding
import com.alexisberrio.formulario.ui.bottom.BottomActivity
import com.alexisberrio.formulario.ui.registro.RegistroActivity
import com.google.firebase.auth.FirebaseAuth

/*
My BiblioApp
Created by:
            Alexis Berrio Arenas
 */

//TODO INICIO DE SESIÓN CON GOOGLE
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    companion object {
        private val TAG = LoginActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        // Go to RegistroActivity when button is pressed
        binding.registraseButton.setOnClickListener {
            goToRegistroActivity()
        }

        // Go to MainActivity when button Login is pressed and credentials ok
        binding.loginButton.setOnClickListener {
            val correo = binding.correoTextInputEditText.text.toString()
            val contrasena = binding.contrasenaTextInputEditText.text.toString()

            when {
                correo.isEmpty() -> binding.correoTextInputEditText.error =
                    getString(R.string.campo_vacio)
                contrasena.isEmpty() -> binding.contrasenaTextInputEditText.error =
                    getString(R.string.campo_vacio)
                else -> {
                    binding.contrasenaTextInputEditText.text = null
                    loginWithFirebase(correo, contrasena)
                }
            }
        }
    }

    private fun loginWithFirebase(correo: String, contrasena: String) {
        auth.signInWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    goToMainActivity()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun goToRegistroActivity() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }

    private fun goToMainActivity() {
        val intent = Intent(this, BottomActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}