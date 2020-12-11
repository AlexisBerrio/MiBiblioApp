package com.alexisberrio.formulario.ui.bottom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alexisberrio.formulario.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class BottomActivity : AppCompatActivity() {

    // Parámetros para controlar la navegación del los fragments distintos a los del bottomNavigation
    private val navController2 by lazy { findNavController(R.id.nav_host_fragment) }
    private val appBarConfiguration2 by lazy { AppBarConfiguration(navController2.graph) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom)

        // Setting del bottom navigation
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController: NavController = findNavController(R.id.nav_host_fragment)
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

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController2.navigateUp(appBarConfiguration2) || super.onSupportNavigateUp()
    }

}