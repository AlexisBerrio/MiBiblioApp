package com.alexisberrio.formulario.ui.novedades

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Novedades
import com.alexisberrio.formulario.databinding.NovedadesItemBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*


class NovedadesRVAdapter(private var novedadesList: ArrayList<Novedades>) :
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


            val finalDate: String = unixDateToString(novedad)

            Glide.with(itemView).load(novedad.imagen).into(binding.novedadImageView)
            binding.tituloTextView.text = novedad.nombre
            binding.fechaTextView.text = finalDate
            binding.tipoTextView.text = novedad.tipo
            binding.descripcionTextView.text = novedad.descripcion

        }

        @SuppressLint("SimpleDateFormat")
        private fun unixDateToString(novedad: Novedades): String {
            val unixSeconds = novedad.fecha
            //Segundos a milisegundos
            val date = Date(unixSeconds * 1000L)
            // Formato de la fecha
            val jdf = SimpleDateFormat("dd-MMM-yyy HH:mm a")
            return jdf.format(date)
        }
    }

}