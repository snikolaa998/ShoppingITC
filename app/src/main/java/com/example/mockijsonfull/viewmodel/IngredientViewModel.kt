package com.example.mockijsonfull.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mockijsonfull.database.Repository
import com.example.mockijsonfull.model.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class IngredientViewModel (private val repository: Repository) : ViewModel() {

    val allIngredient = MutableLiveData<List<Ingredient>>()

    fun getAllIngredient() {
        viewModelScope.launch(Dispatchers.IO) {
            allIngredient.postValue(repository.getIngredient())
        }
    }

    fun insert(ingredient: Ingredient) {
        viewModelScope.launch {
            repository.insert(ingredient)
            getAllIngredient()
        }
    }
    fun delete(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
            getAllIngredient()
        }
    }
}

class IngredientViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngredientViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}
