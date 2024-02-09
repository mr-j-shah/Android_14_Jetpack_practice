package com.crestinfosystems.shoppinglist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crestinfosystems.shoppinglist.ui.theme.ShoppingListTheme
import com.crestinfosystems.shoppinglist.ui.theme.editorView

@Composable
fun Greeting() {
    var shopingItemList by remember {
        mutableStateOf(listOf<shoppingItem>())
    }
    var dialogBox by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                dialogBox = true
            }, modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Add Item")
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(shopingItemList) { index ->
                if (index.editMode) {
                    editorView(item = index, onEditComplete = { editedTitle, editedQty ->
                        shopingItemList = shopingItemList.map { it.copy(editMode = false) }
                        val editedItem = shopingItemList.find { it.title == index.title }
                        editedItem?.let {
                            it.title = editedTitle
                            it.qty = editedQty
                        }
                    })
                } else {

                    shopingItemTile(item = index, {
                        shopingItemList = shopingItemList.map {
                            it.copy(editMode = it.title == index.title)
                        }
                    }, {
                        shopingItemList -= index
                    })
                }
            }
        }
    }
    if (dialogBox) {
        var name: String by remember {
            mutableStateOf("")
        }
        var qty: Int by remember {
            mutableStateOf(0)
        }

        AlertDialog(
            onDismissRequest = { dialogBox = false },
            confirmButton = {
                TextButton(onClick = {
                    shopingItemList += shoppingItem(name,qty)
                    dialogBox = false
                }) {
                    Text(text = "Ok")
                }
            },
            title = {
                Text(
                    text = "Add Item"
                )
            },

            dismissButton = {
                TextButton(onClick = { dialogBox = false }) {
                    Text(text = "Cancel")
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    TextField(value = name,
                        onValueChange = { name = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        label = { Text("Item Name") })
                    Spacer(modifier = Modifier.height(5.dp))
                    TextField(
                        value = qty.toString(),
                        onValueChange = { qty = it.toIntOrNull() ?: 0 },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Quantity") })
                }
            }
        )
    }
}


@Composable
fun shopingItemTile(
    item: shoppingItem, onEditClick: () -> Unit, onDeleteClcik: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()

            .padding(8.dp)
            .border(
                border = BorderStroke(width = 2.dp, color = Color(0xFF018786)),
                shape = RoundedCornerShape(
                    20
                )
            )
    ) {
        Text(
            text = item.title.toString(),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Qty : ${item.qty.toString()}",
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
            fontSize = 18.sp
        )
//
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDeleteClcik) {
                Icon(Icons.Default.Delete, contentDescription = "Edit")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        shopingItemTile(shoppingItem("apple", 10), {}, {})
    }
}