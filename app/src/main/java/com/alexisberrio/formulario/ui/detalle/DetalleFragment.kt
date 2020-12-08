package com.alexisberrio.formulario.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.databinding.FragmentDetalleBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth


class DetalleFragment : Fragment() {

    private lateinit var binding: FragmentDetalleBinding
    private lateinit var auth: FirebaseAuth

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalle, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetalleBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid.toString()

        //TODO Boton de atras
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.onBackPressed()
                }
            })

        val args: DetalleFragmentArgs by navArgs()
        val libroDetalle = args.LibroSeleccionado


        binding.detalleAutorTextView.text = libroDetalle.autor
        binding.detalleTituloTextView.text = libroDetalle.titulo
        binding.detalleYearTextView.text = libroDetalle.anio.toString()
        binding.detalleSinopsisTextView.text = libroDetalle.sinopsis
        if (libroDetalle.portada != "")
            Glide.with(view).load(libroDetalle.portada).into(binding.portadaImageView)

        if (libroDetalle.prestado == true) {
            binding.detalleEstadoPrestamoTextView.setText(R.string.en_prestamo)
            binding.detalleReservarButton.isEnabled = false
        } else {
            binding.detalleEstadoPrestamoTextView.setText(R.string.disponible)
            binding.detalleReservarButton.isEnabled = true
        }
        /*binding.detalleReservarButton.setOnClickListener {
//TODO actualizar datos

            val database = FirebaseDatabase.getInstance()
            val myLibroRef = database.getReference("libros")

            val childUpdates = mutableMapOf<String, Any>()
            childUpdates["prestado"] = true
            childUpdates["userpestamo"] = userId
            //libroDetalle
            // updateLibro?.let { myLibroRef.child(it).updateChildren(childUpdates) }

*//*            myLibroRef
                .child("prestado")
                .child("userpestamo")
                .updateChildren(childUpdates)*//*
        }*/
    }
}