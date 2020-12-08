package com.alexisberrio.formulario.data.server

import java.io.Serializable

class Libros(
    val id: String = "",
    val titulo: String = "",
    val anio: Int = 0,
    val autor: String = "",
    val portada: String = "",
    val prestado: Boolean = false,
    val userPrestamo: String = " ",
    val sinopsis: String = ""
) : Serializable