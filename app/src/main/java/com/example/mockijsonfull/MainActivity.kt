package com.example.mockijsonfull

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mockijsonfull.adapter.IngredientAdapter
import com.example.mockijsonfull.database.Repository
import com.example.mockijsonfull.interfaces.OnItemClickListener
import com.example.mockijsonfull.model.Ingredient
import com.example.mockijsonfull.viewmodel.IngredientViewModel
import com.example.mockijsonfull.viewmodel.IngredientViewModelFactory
import org.json.JSONArray
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder
import java.nio.charset.Charset

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val TAG = "MainActivityState"
    private lateinit var ingredientViewModel: IngredientViewModel
    private lateinit var ingredientViewModelFactory: IngredientViewModelFactory
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ingredients: ArrayList<Ingredient> = ArrayList()
        val repo = Repository(application)
        val button = findViewById<Button>(R.id.btn_default_ingredient)
        ingredientViewModelFactory = IngredientViewModelFactory(repo)
        ingredientViewModel = ViewModelProvider(this, ingredientViewModelFactory).get(IngredientViewModel::class.java)
        recyclerView = findViewById(R.id.activity_main_recyclerview)

        button.setOnClickListener {
            try {

                val arr = JSONArray(getJSONFromAssets()!!)
                for (i in 0 until arr.length()) {
                    val obj = arr.getJSONObject(i)
                    val name = obj.getString("name")
                    Log.d(TAG, "Name = $name")
                    val ingredientsList = obj.getJSONArray("ingredients")
                    for (p in 0 until ingredientsList.length()) {
                        val obj2 = ingredientsList.getJSONObject(p)
                        val itemList = obj2.getJSONArray("item")
                        val title = obj2.getString("title")
//                    Log.d(TAG, "Item: $p")
                        Log.d(TAG, "Title = $title")
                        val stringBuilder = StringBuilder()
                        for (j in 0 until itemList.length()) {
                            val data = itemList.getJSONObject(j)
//                        Log.d(TAG, data.getString("ingredient"))
                            stringBuilder.append(data.getString(("ingredient"))).append("\n")
                        }
                        ingredients.add(Ingredient(name = name, title = title, ingredients = stringBuilder.toString()))
                        val ingredient = Ingredient(name = name, title = title, ingredients = stringBuilder.toString())
                        ingredientViewModel.insert(ingredient)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        fillRecyclerView()
    }

    private fun fillRecyclerView() {
        ingredientViewModel.getAllIngredient()
        ingredientViewModel.allIngredient.observe(this, Observer {
            val adapter = IngredientAdapter(it, this, this)
            recyclerView.apply {
                this.adapter = adapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        })
    }

    override fun onItemClickListener(ingredient: Ingredient) {
        val id = ingredient.id
        ingredientViewModel.delete(id)
    }


    private fun getJSONFromAssets(): String? {
        var json: String? = null
        val charset: Charset = Charsets.UTF_8

        try {
            val `is` = assets.open("test.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}