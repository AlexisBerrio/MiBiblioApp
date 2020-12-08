package com.alexisberrio.formulario.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.databinding.FragmentPerfilBinding
import com.alexisberrio.formulario.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PerfilFragment : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPerfilBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        val otro = auth.currentUser?.uid

        val database = FirebaseDatabase.getInstance()
        val myUserRef =
            database.getReference("usuarios").orderByChild("id").equalTo(otro)
        myUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { childSnapshot ->

                    binding.correoTextView.text =
                        childSnapshot.child("correo").getValue(String::class.java)
                    binding.nombreTextView.text =
                        childSnapshot.child("nombre").getValue(String::class.java)
                    if (childSnapshot.child("genero").getValue(String::class.java) == "Masculino") {
                        binding.caraImageView.setImageResource(R.drawable.man)
                    } else {
                        binding.caraImageView.setImageResource(R.drawable.ic_girl)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }


        })

        val myPrestamosRef =
            database.getReference("libros").orderByChild("userprestamo").equalTo(otro)
        myPrestamosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val prestamos = ArrayList<String>()

                snapshot.children.forEach { childSnapchot ->
                    println("Pasa por aca")
                    prestamos.add(
                        childSnapchot.child("titulo").getValue(String::class.java).toString()
                    )
                    for (libro in prestamos)
                        println(libro)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        binding.cerrarSesionButton.setOnClickListener {
            auth.signOut()
            goToLoginActivity()
        }
    }


    private fun goToLoginActivity() {
        val intent = Intent(this@PerfilFragment.context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

} 