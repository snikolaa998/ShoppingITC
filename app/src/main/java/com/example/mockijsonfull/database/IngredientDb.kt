package com.example.mockijsonfull.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mockijsonfull.model.Ingredient

@Database(
    entities = [Ingredient::class],
    version = 1,
    exportSchema = false
)

abstract class IngredientDb() : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao

    companion object {
        private var INSTANCE: IngredientDb? = null
        internal fun getDatabase(context: Context): IngredientDb? {
            if (INSTANCE == null) {
                synchronized(IngredientDb::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            IngredientDb::class.java,
                            "ingredient_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}