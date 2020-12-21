package com.example.mockijsonfull.database

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.mockijsonfull.model.Ingredient

class Repository(application: Application) {

    private var ingredientDao: IngredientDao?

    init {
        val db = IngredientDb.getDatabase(application)
        ingredientDao = db?.ingredientDao()
    }

    fun getIngredient(): List<Ingredient> {
        return ingredientDao?.getAllIngredient()!!
    }

    @Suppress
    @WorkerThread
    suspend fun insert(ingredient: Ingredient) {
        ingredientDao?.insertIngredient(ingredient)
    }

    @Suppress
    @WorkerThread
    suspend fun delete(id: Int) {
        ingredientDao?.deleteIngredient(id)
    }
}