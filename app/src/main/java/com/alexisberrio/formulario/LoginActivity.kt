package com.alexisberrio.formulario

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mail:String
    private lateinit var pass:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val datosrecibidos = intent.extras
        mail = datosrecibidos?.getString("correo").toString()
        pass = datosrecibidos?.getString("contraseña").toString()

        registrase_button.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
            finish()
        }

        login_button.setOnClickListener {
            val correoLogin = correo_editTextTextEmailAddress.text.toString()
            val contrasenaLogin = contrasena_editTextTextPassword.text.toString()

            if (correoLogin != mail || contrasenaLogin != pass) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.datos_erroneos),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("correo", correoLogin)
                intent.putExtra("contraseña", contrasenaLogin)
                startActivity(intent)
            }
        }
    }
}