package com.alexisberrio.formulario.ui.perfil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Libros
import com.alexisberrio.formulario.databinding.PrestamosItemBinding
import java.util.*

class PrestamosRVAdapter(
    private var librosList: ArrayList<Libros>
) :
    RecyclerView.Adapter<PrestamosRVAdapter.LibrosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrosViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.prestamos_item, parent, false)
        return LibrosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LibrosViewHolder, position: Int) {
        val libro = librosList[position]
        holder.bindLibro(libro)
    }

    override fun getItemCount(): Int {
        return librosList.size
    }

    class LibrosViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = PrestamosItemBinding.bind(itemView)

        fun bindLibro(libro: Libros) {
            binding.tituloPrestamosTextView.text = libro.titulo
        }

    }


}

