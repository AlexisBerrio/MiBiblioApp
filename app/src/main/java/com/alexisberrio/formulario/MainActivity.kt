package com.alexisberrio.formulario

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mail:String
    private lateinit var pass:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val datosrecibidos = intent.extras
        mail = datosrecibidos?.getString("correo").toString()
        pass = datosrecibidos?.getString("contraseña").toString()

        correo_usuario_textView.text = mail
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cerrar_sesion -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("correo",mail)
                intent.putExtra("contraseña",pass)
                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("correo",mail)
        intent.putExtra("contraseña",pass)
        startActivity(intent)
        finish()
    }
}