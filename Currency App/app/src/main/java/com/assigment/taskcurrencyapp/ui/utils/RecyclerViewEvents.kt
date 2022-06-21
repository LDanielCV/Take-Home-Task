package com.assigment.taskcurrencyapp.ui.utils

import android.view.View

interface RecyclerViewEvents {
    fun onItemClick(view: View?, item:Any, itemPosition:Int)
}