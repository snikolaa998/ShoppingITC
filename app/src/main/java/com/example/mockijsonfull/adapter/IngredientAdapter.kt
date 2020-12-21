package com.example.mockijsonfull.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mockijsonfull.R
import com.example.mockijsonfull.interfaces.OnItemClickListener
import com.example.mockijsonfull.model.Ingredient
import java.lang.StringBuilder

class IngredientHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ingredientName: TextView = view.findViewById(R.id.tv_ingredient_name)
    val ingredientTitle: TextView = view.findViewById(R.id.ingredient_item_title)
    val button: Button = view.findViewById(R.id.btn_ingredient_item)
    val ingredients: TextView = view.findViewById(R.id.tv_all_ingredients)
}

class IngredientAdapter(private val items: List<Ingredient>, private val context: Context, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<IngredientHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        val item = items[position]
        with(holder) {
            ingredientName.text = item.name
            ingredientTitle.text = item.title
            ingredients.text = item.ingredients
            button.setOnClickListener {
                onItemClickListener.onItemClickListener(item)
            }
        }
    }
}