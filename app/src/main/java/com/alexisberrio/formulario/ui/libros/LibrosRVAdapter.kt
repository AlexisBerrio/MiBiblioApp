package com.alexisberrio.formulario.ui.libros

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Libros
import com.alexisberrio.formulario.databinding.LibrosItemBinding
import com.bumptech.glide.Glide

class LibrosRVAdapter(private var librosList: ArrayList<Libros>) :
    RecyclerView.Adapter<LibrosRVAdapter.LibrosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrosViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.libros_item, parent, false)
        return LibrosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LibrosViewHolder, position: Int) {
        val libro = librosList[position]
        holder.bindLibro(libro)
    }

    override fun getItemCount(): Int {
        return librosList.size
    }

    class LibrosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = LibrosItemBinding.bind(itemView)

        fun bindLibro(libro: Libros) {

            binding.autorLibroTextView.text = libro.autor
            binding.tituloLibroTextView.text = libro.titulo
            Glide.with(itemView).load(libro.portada).into(binding.libroImageView)
        }
    }
}