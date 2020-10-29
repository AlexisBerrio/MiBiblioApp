package com.alexisberrio.formulario

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexisberrio.formulario.bottom.BottomActivity
import kotlinx.android.synthetic.main.activity_login.*

/*
My BiblioApp
Created by:
            Alexis Berrio Arenas
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var mail: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Take and save current user information
        val datosrecibidos = intent.extras
        mail = datosrecibidos?.getString("correo").toString()
        pass = datosrecibidos?.getString("contraseña").toString()

        // Go to RegistroActivity when button is pressed
        registrase_button.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        // Go to MainActivity when button Login is pressed and credentials ok
        login_button.setOnClickListener {
            val correoLogin = correo_textInputEditText.text.toString()
            val contrasenaLogin = contrasena_textInputEditText.text.toString()

            if (correoLogin != mail || contrasenaLogin != pass) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.datos_erroneos),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, BottomActivity::class.java)
                intent.putExtra("correo", correoLogin)
                intent.putExtra("contraseña", contrasenaLogin)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}