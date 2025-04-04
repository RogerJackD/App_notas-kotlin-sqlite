package com.rogerjack.noteapp.data.local.models

import androidx.compose.ui.graphics.Color
import com.rogerjack.noteapp.ui.theme.Teal200

data class Category(
    val id: Int,
    val title: String,
    val color: Color
)

val categoryList = listOf(
    Category(
        1,
        "Importante",
        Color.Red
    ),
    Category(
        2,
        "Por hacer",
        Teal200
    ),
    Category(
        3,
        "Recordar",
        Color.Green
    )
)