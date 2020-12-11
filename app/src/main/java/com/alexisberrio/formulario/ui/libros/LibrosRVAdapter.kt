package com.alexisberrio.formulario.ui.libros

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Libros
import com.alexisberrio.formulario.databinding.LibrosItemBinding
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList


class LibrosRVAdapter(
    private var librosList: ArrayList<Libros>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<LibrosRVAdapter.LibrosViewHolder>(), Filterable {

    var libroFilterList = ArrayList<Libros>()

    init {
        libroFilterList = librosList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrosViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.libros_item, parent, false)
        return LibrosViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(holder: LibrosViewHolder, position: Int) {
        val libro = libroFilterList[position]
        holder.bindLibro(libro)
    }

    override fun getItemCount(): Int {
        return libroFilterList.size
    }

    class LibrosViewHolder(
        itemView: View,
        var onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = LibrosItemBinding.bind(itemView)

        fun bindLibro(libro: Libros) {

            binding.autorLibroTextView.text = libro.autor
            binding.tituloLibroTextView.text = libro.titulo
            if (libro.portada != "")
                Glide.with(itemView).load(libro.portada).into(binding.libroImageView)

            binding.prestamosCardView.setOnClickListener {
                onItemClickListener.onItemClick(libro)
            }

        }

    }

    interface OnItemClickListener {
        fun onItemClick(libro: Libros)

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    libroFilterList = librosList
                } else {
                    val resultList = ArrayList<Libros>()
                    for (row in librosList) {
                        if (row.titulo.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT)) || row.autor.toLowerCase(
                                Locale.ROOT
                            ).contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    libroFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = libroFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                libroFilterList = results?.values as ArrayList<Libros>
                notifyDataSetChanged()
            }

        }

    }

}