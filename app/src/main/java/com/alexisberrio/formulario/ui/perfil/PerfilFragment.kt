package com.alexisberrio.formulario.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Libros
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
    private lateinit var prestamosRVAadapter: PrestamosRVAdapter
    var listlibros: MutableList<Libros> = mutableListOf()

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
        val userIdActual = auth.currentUser?.uid

        // Crear el adaptador encargado de montar los libros prestados en el Recycler View
        crearRVAdapter(userIdActual)

        // Cargar los datos de usuario referentes al id de la sesión iniciada (Nombre, correo, imagen)
        val database = FirebaseDatabase.getInstance()
        cargarDatosDeUsuario(database, userIdActual)


        binding.cerrarSesionButton.setOnClickListener {
            auth.signOut()
            goToLoginActivity()
        }
    }

    private fun cargarDatosDeUsuario(
        database: FirebaseDatabase,
        userIdActual: String?
    ) {
        val myUserRef =
            database.getReference("usuarios").orderByChild("id").equalTo(userIdActual)
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
    }

    private fun crearRVAdapter(userIdActual: String?) {
        binding.prestamosRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.prestamosRecyclerView.setHasFixedSize(true)

        prestamosRVAadapter = PrestamosRVAdapter(listlibros as ArrayList<Libros>)
        binding.prestamosRecyclerView.adapter = prestamosRVAadapter

        cargarDesdeFirebase(userIdActual)
        prestamosRVAadapter.notifyDataSetChanged()
    }

    private fun cargarDesdeFirebase(userIdActual: String?) {
        val database = FirebaseDatabase.getInstance()
        val myLibrosRef =
            database.getReference("libros").orderByChild("userpestamo").equalTo(userIdActual)

        listlibros.clear()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dato: DataSnapshot in snapshot.children) {
                    val libro = dato.getValue(Libros::class.java)
                    libro?.let { listlibros.add(it) }
                }
                prestamosRVAadapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myLibrosRef.addValueEventListener(postListener)

    }


    private fun goToLoginActivity() {
        val intent = Intent(this@PerfilFragment.context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

} 