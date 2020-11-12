package com.alexisberrio.formulario.data.server

data class Usuario(
    val id: String?,
    val correo: String,
    val nombre: String,
    val telefono: Int,
    val genero: String
)