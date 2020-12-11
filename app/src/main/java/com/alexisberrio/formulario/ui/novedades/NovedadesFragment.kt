package com.alexisberrio.formulario.ui.novedades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Novedades
import com.alexisberrio.formulario.databinding.FragmentNovedadesBinding

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NovedadesFragment : Fragment() {

    private lateinit var binding: FragmentNovedadesBinding
    var listnovedades: MutableList<Novedades> = mutableListOf()
    private lateinit var novedadesRVAdapter: NovedadesRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_novedades, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNovedadesBinding.bind(view)

        // Setting del recycler view
        binding.novedadesRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.novedadesRecyclerView.setHasFixedSize(true)

        novedadesRVAdapter = NovedadesRVAdapter(listnovedades as ArrayList<Novedades>)
        binding.novedadesRecyclerView.adapter = novedadesRVAdapter

        cargarDesdeFirebase()
        novedadesRVAdapter.notifyDataSetChanged()
    }

    // Carga la información de la tabla novedades alojada en firebase
    private fun cargarDesdeFirebase() {

        val database = FirebaseDatabase.getInstance()
        val myNovedadesRef = database.getReference("novedades") //Referencia a la tabla

        listnovedades.clear() // Borra cualquier dato en la lista de novedades

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dato: DataSnapshot in snapshot.children) {
                    val novedad = dato.getValue(Novedades::class.java)
                    novedad?.let { listnovedades.add(it) }
                }
                novedadesRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myNovedadesRef.addValueEventListener(postListener)
    }
}