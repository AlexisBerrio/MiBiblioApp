/*
My BiblioApp
Created by:
            Alexis Berrio Arenas
 */
package com.alexisberrio.formulario

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alexisberrio.formulario.fragments.EncuentranosFragment
import com.alexisberrio.formulario.fragments.NovedadesFragment
import com.alexisberrio.formulario.fragments.PrestamoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val prestamoFragment = PrestamoFragment()
    private val novedadesFragment = NovedadesFragment()
    private val encuentranosFragment = EncuentranosFragment()


    private lateinit var mail: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(novedadesFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_info -> replaceFragment(novedadesFragment)
                R.id.ic_book -> replaceFragment(prestamoFragment)
                R.id.ic_map -> replaceFragment(encuentranosFragment)
            }
            true
        }

        val datosrecibidos = intent.extras
        mail = datosrecibidos?.getString("correo").toString()
        pass = datosrecibidos?.getString("contraseña").toString()

        //correo_usuario_textView.text = mail
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cerrar_sesion -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("correo", mail)
                intent.putExtra("contraseña", pass)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("correo",mail)
        intent.putExtra("contraseña", pass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}