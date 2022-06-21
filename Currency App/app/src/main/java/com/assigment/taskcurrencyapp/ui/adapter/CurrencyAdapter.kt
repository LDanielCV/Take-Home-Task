package com.assigment.taskcurrencyapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assigment.taskcurrencyapp.domain.models.BaseModel
import com.assigment.taskcurrencyapp.databinding.SymbolsCardBinding
import com.assigment.taskcurrencyapp.ui.utils.RecyclerViewEvents

class CurrencyAdapter() : ListAdapter<Any, RecyclerView.ViewHolder>(Companion) {

    private var listener: RecyclerViewEvents? = null

    constructor(listener: RecyclerViewEvents?) : this() {
        this.listener = listener
    }

    companion object : DiffUtil.ItemCallback<Any>() {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = oldItem != newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CurrencyCountyHolder(SymbolsCardBinding.inflate(LayoutInflater.from(parent.context)))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        handleCurrencyCountries(item as BaseModel, holder as CurrencyCountyHolder, position)
    }

    private inner class CurrencyCountyHolder(val binding: SymbolsCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    private fun handleCurrencyCountries(
        item: BaseModel,
        holder: CurrencyCountyHolder,
        position: Int
    ) {
        holder.binding.let { cardBinding ->
            cardBinding.model = item
            cardBinding.root.setOnClickListener { view ->
                listener?.onItemClick(view.rootView, item, position)
            }
            cardBinding.executePendingBindings()
        }
    }


}