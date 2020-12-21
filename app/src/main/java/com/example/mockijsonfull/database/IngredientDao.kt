package com.example.mockijsonfull.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mockijsonfull.model.Ingredient

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredient_table")
    fun getAllIngredient(): List<Ingredient>

    @Insert
    suspend fun insertIngredient(ingredient: Ingredient)

    @Query("DELETE FROM ingredient_table WHERE id = :id")
    suspend fun deleteIngredient(id: Int)
}