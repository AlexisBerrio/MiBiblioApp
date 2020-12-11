package com.alexisberrio.formulario.ui.detalle

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.alexisberrio.formulario.R
import com.alexisberrio.formulario.data.server.Libros
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
                    action.popBackStack() // Retorna al fragment anterior en la pila
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

        actualizarReserva(libroDetalle)

        // Actualizar información en Database cuando se da click en el botón reservar
        binding.detalleReservarButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
                .setTitle(R.string.reservar)
                .setMessage(R.string.recomendacion)
                .setNegativeButton(R.string.cancelar) { view, _ ->
                    Toast.makeText(context, "Reserva Cancelada", Toast.LENGTH_SHORT).show()
                    view.dismiss()
                }
                .setPositiveButton(R.string.reservar) { view, _ ->
                    actualizarDatabase(userId, libroDetalle)
                    actualizarReserva(libroDetalle)
                    Toast.makeText(context, "Libro Reservado", Toast.LENGTH_SHORT).show()
                    view.dismiss()
                }
                .setCancelable(false)
                .create()
            dialog.show()

        }

    }

    /*
     Activa o desactiva el botón de reservar y cambia el texto del text view estado dependiendo
     de la disponibilidad del libro
     */
    private fun actualizarReserva(libroDetalle: Libros) {
        if (libroDetalle.prestado) { // La condición se cumple cuando el libro está prestado
            binding.detalleEstadoPrestamoTextView.setText(R.string.en_prestamo)
            binding.detalleReservarButton.isEnabled = false
        } else {
            binding.detalleEstadoPrestamoTextView.setText(R.string.disponible)
            binding.detalleReservarButton.isEnabled = true
        }
    }


    //Actualiza los datos de préstamo en la base de datos de Firebase cuando el libro es reservado
    private fun actualizarDatabase(
        userId: String,
        libroDetalle: Libros
    ) {
        val database = FirebaseDatabase.getInstance()
        val myLibroRef = database.getReference("libros")

        val childUpdates = mutableMapOf<String, Any>()
        childUpdates["prestado"] = true // Actualiza el campo de prestado a true
        childUpdates["userpestamo"] = userId // Actualiza el id vinculado al user que reservó

        myLibroRef
            .child(libroDetalle.id) // Actualiza los campos según la referencia del libro actual
            .updateChildren(childUpdates)
    }
}