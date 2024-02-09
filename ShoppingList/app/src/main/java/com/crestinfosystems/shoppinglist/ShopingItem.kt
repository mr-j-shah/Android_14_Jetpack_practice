package com.crestinfosystems.shoppinglist


data class shoppingItem(
    var title: String,
    var qty: Int,
    var editMode: Boolean = false
)
