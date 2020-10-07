/*
Formulario app
Created by:
            Alexis Berrio Arenas
 */

package com.alexisberrio.formulario

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        registrar_button.setOnClickListener {
            val nombre = nombre_edit_text.text.toString()
            val correo = correo_edit_text.text.toString()
            val telefono = telefono_edit_text.text.toString()
            val contrasena = contraseña_edit_text.text.toString()
            val repContrasena = repcontraseña_edit_text.text.toString()
            val genero =
                if (masculino_radioButton.isChecked) getString(R.string.masculino) else getString(R.string.femenino)


            // Detects empty edit texts and display the currently error
            when {
                contrasena != repContrasena -> {
                    contraseña_edit_text.error = getString(R.string.error_contrasena)
                }
                contrasena.length < 6 -> {
                    contraseña_edit_text.error = getString(R.string.contrasenacorta)
                }
                nombre.isEmpty() -> nombre_edit_text.error = getString(R.string.campo_vacio)
                correo.isEmpty() -> correo_edit_text.error = getString(R.string.campo_vacio)
                telefono.isEmpty() -> telefono_edit_text.error = getString(R.string.campo_vacio)
                contrasena.isEmpty() -> contraseña_edit_text.error =
                    getString(R.string.campo_vacio)
                repContrasena.isEmpty() -> repcontraseña_edit_text.error =
                    getString(R.string.campo_vacio)


                // If any error, send user information and go to Login
                else -> {
                    contraseña_edit_text.error = null

                    Toast.makeText(
                        applicationContext,
                        getString(R.string.registro_guardado),
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("correo", correo)
                    intent.putExtra("contraseña", contrasena)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }

    // Go to Login
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}