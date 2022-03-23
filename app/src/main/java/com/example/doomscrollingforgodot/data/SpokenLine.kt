package com.example.doomscrollingforgodot.data

import kotlinx.serialization.Serializable

@Serializable
data class SpokenLine(
    val speaker: String,
    val line: String,
)