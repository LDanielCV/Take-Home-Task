package com.assigment.taskcurrencyapp.ui.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assigment.taskcurrencyapp.domain.models.BaseModel
import com.assigment.taskcurrencyapp.domain.models.CurrencySymbols
import com.assigment.taskcurrencyapp.domain.models.Rate
import java.lang.IllegalArgumentException
import java.util.*

@BindingAdapter("setAdapter")
fun RecyclerView.bindRecyclerViewAdapter(adapter:RecyclerView.Adapter<*>){
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}


@BindingAdapter("formatText")
fun TextView.bindText(data:BaseModel){
    this.run {

        val formatedText = when(data){

            is Rate ->{
                val symbol:String = Currency.getInstance(data.currency).symbol
                val formatedValue = String.format("%.2f", data.value)
                "$symbol $formatedValue"
            }

            is CurrencySymbols ->
                data.name

            else -> throw IllegalArgumentException("No supported Type")
        }

        this.text = formatedText
    }

}