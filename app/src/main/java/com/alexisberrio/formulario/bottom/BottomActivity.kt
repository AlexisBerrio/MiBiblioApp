package com.alexisberrio.formulario.bottom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alexisberrio.formulario.LoginActivity
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.fragments.PerfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomActivity : AppCompatActivity() {

    private lateinit var mail: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_novedades,
                R.id.navigation_encuentranos,
                R.id.navigation_prestamo,
                R.id.navigation_perfil
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val datosrecibidos = intent.extras
        mail = datosrecibidos?.getString("correo").toString()
        pass = datosrecibidos?.getString("contraseña").toString()

        val intent = Intent(this, PerfilFragment::class.java)
        intent.putExtra("correo", mail)
        intent.putExtra("contraseña", pass)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("correo", mail)
        intent.putExtra("contraseña", pass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}