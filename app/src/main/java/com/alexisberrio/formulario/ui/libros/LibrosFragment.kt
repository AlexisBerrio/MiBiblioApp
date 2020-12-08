package com.alexisberrio.formulario.ui.libros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Libros
import com.alexisberrio.formulario.databinding.FragmentLibrosBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LibrosFragment : Fragment(), LibrosRVAdapter.OnItemClickListener {

    private lateinit var binding: FragmentLibrosBinding
    var listlibros: MutableList<Libros> = mutableListOf()
    private lateinit var librosRVAdapter: LibrosRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_libros, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLibrosBinding.bind(view)

        binding.librosRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.librosRecyclerView.setHasFixedSize(true)

        librosRVAdapter = LibrosRVAdapter(listlibros as ArrayList<Libros>, this@LibrosFragment)
        binding.librosRecyclerView.adapter = librosRVAdapter

        cargarDesdeFirebase()
        librosRVAdapter.notifyDataSetChanged()

        binding.tituloSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                librosRVAdapter.filter.filter(newText)
                return false
            }
        })


    }

    private fun cargarDesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myLibrosRef = database.getReference("libros")

        listlibros.clear()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dato: DataSnapshot in snapshot.children) {
                    val libro = dato.getValue(Libros::class.java)
                    libro?.let { listlibros.add(it) }
                }
                librosRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myLibrosRef.addValueEventListener(postListener)
    }

    override fun onItemClick(libro: Libros) {
        val action = LibrosFragmentDirections.actionNavigationPrestamoToDetalleFragment(libro)
        findNavController().navigate(action)
    }


}