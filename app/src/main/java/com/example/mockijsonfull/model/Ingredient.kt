package com.example.mockijsonfull.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_table")
data class Ingredient(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String,
    val title: String,
    val ingredients: String)