package com.alexisberrio.formulario.ui.novedades

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Novedades
import com.alexisberrio.formulario.databinding.NovedadesItemBinding
import com.bumptech.glide.Glide


class NovedadesRVAdapter(var novedadesList: ArrayList<Novedades>) :
    RecyclerView.Adapter<NovedadesRVAdapter.NovedadesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovedadesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.novedades_item, parent, false)
        return NovedadesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NovedadesViewHolder, position: Int) {
        val novedad = novedadesList[position]
        holder.bindNovedad(novedad)
    }

    override fun getItemCount(): Int {
        return novedadesList.size

    }

    class NovedadesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = NovedadesItemBinding.bind(itemView)

        fun bindNovedad(novedad: Novedades) {

            Glide.with(itemView).load(novedad.imagen).into(binding.novedadImageView)

            binding.tituloTextView.text = novedad.nombre

        }
    }

}