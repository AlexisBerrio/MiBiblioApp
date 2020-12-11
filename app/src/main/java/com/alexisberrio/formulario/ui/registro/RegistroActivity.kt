/*
My BiblioApp
Developed by:
            Alexis Berrio Arenas
 */

package com.alexisberrio.formulario.ui.registro

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Usuario
import com.alexisberrio.formulario.databinding.ActivityRegistroBinding
import com.alexisberrio.formulario.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistroBinding

    companion object {
        private val TAG = RegistroActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registro)

        auth = FirebaseAuth.getInstance()


        binding.registrarButton.setOnClickListener {

            // Toma los valores actuales de los edit text
            val correo = binding.correoEditText.text.toString()
            val contrasena = binding.contrasenaEditText.text.toString()
            val nombre = binding.nombreEditText.text.toString()
            val telefono = binding.telefonoEditText.text.toString()
            val repContrasena = binding.repContrasenaEditText.text.toString()
            val genero =
                if (binding.masculinoRadioButton.isChecked) getString(R.string.masculino) else getString(
                    R.string.femenino
                )


            // Detects empty edit texts and display the currently error
            when {
                nombre.isEmpty() -> binding.nombreEditText.error = getString(R.string.campo_vacio)
                correo.isEmpty() -> binding.correoEditText.error = getString(R.string.campo_vacio)
                telefono.isEmpty() -> binding.telefonoEditText.error =
                    getString(R.string.campo_vacio)
                contrasena.isEmpty() -> binding.contrasenaEditText.error =
                    getString(R.string.campo_vacio)
                repContrasena.isEmpty() -> binding.repContrasenaEditText.error =
                    getString(R.string.campo_vacio)
                contrasena.length < 6 -> {
                    binding.contrasenaEditText.error = getString(R.string.contrasenacorta)
                }
                contrasena != repContrasena -> {
                    binding.contrasenaEditText.error = getString(R.string.error_contrasena)
                }
                // If any error, send user information and go to Login
                else -> {
                    binding.contrasenaEditText.error = null
                    registroEnFirebase(correo, contrasena, nombre, telefono.toInt(), genero)

                }
            }
        }

    }

    // Registra el usuario actual en la base de datos de firebase authentication
    private fun registroEnFirebase(
        correo: String,
        contrasena: String,
        nombre: String,
        telefono: Int,
        genero: String
    ) {
        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val uid = auth.currentUser?.uid
                    crearUsuarioEnDataBase(uid, correo, nombre, telefono, genero)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }

    // Registra el usuario actual en la base de datos de firebase realtime database
    private fun crearUsuarioEnDataBase(
        uid: String?,
        correo: String,
        nombre: String,
        telefono: Int,
        genero: String
    ) {
        val database = FirebaseDatabase.getInstance()
        val myUsuarioRef = database.getReference("usuarios")
        val usuario = Usuario(uid, correo, nombre, telefono, genero)

        uid?.let { myUsuarioRef.child(uid).setValue(usuario) }
        auth.signOut() // Cierra la sesión actual iniciada por default
        goToLoginActivity()
    }

    private fun goToLoginActivity() {
        onBackPressed()
    }

    // Go to Login
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}