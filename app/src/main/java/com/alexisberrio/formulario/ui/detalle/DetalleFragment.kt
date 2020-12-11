package com.alexisberrio.formulario.ui.detalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.databinding.FragmentDetalleBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class DetalleFragment : Fragment() {

    private lateinit var binding: FragmentDetalleBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalle, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentDetalleBinding.bind(view)

        // Cargar el id del usuario actual
        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid.toString()

        // Retornar a la lista de libros al presionar el botón de Back
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val action = Navigation.findNavController(view)
                    action.popBackStack()
                }
            })

        // Cargar referencia del libro actual
        val args: DetalleFragmentArgs by navArgs()
        val libroDetalle = args.LibroSeleccionado


        // Cargar la información de Database en el fragmento
        binding.detalleAutorTextView.text = libroDetalle.autor
        binding.detalleTituloTextView.text = libroDetalle.titulo
        binding.detalleYearTextView.text = libroDetalle.anio.toString()
        binding.detalleSinopsisTextView.text = libroDetalle.sinopsis

        if (libroDetalle.portada != "")
            Glide.with(view).load(libroDetalle.portada).into(binding.portadaImageView)

        // Activa o desactiva el botón de reservar dependiendo de la disponibilidad del libro
        if (libroDetalle.prestado == true) {
            binding.detalleEstadoPrestamoTextView.setText(R.string.en_prestamo)
            binding.detalleReservarButton.isEnabled = false
        } else {
            binding.detalleEstadoPrestamoTextView.setText(R.string.disponible)
            binding.detalleReservarButton.isEnabled = true
        }

        // Actualizar información en Database cuando se da click en el botón reservar
        binding.detalleReservarButton.setOnClickListener {

            val database = FirebaseDatabase.getInstance()
            val myLibroRef = database.getReference("libros")

            val childUpdates = mutableMapOf<String, Any>()
            childUpdates["prestado"] = true
            childUpdates["userpestamo"] = userId

            myLibroRef
                .child(libroDetalle.id)
                .updateChildren(childUpdates)
        }

    }
}