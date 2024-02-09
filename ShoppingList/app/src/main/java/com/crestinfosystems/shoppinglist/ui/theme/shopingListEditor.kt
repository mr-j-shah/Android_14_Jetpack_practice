package com.crestinfosystems.shoppinglist.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.crestinfosystems.shoppinglist.shoppingItem

@Composable
fun editorView(item: shoppingItem, onEditComplete: (String,Int) -> Unit) {
    var editedName by remember {
        mutableStateOf(item.title)
    }
    var editedQty by remember {
        mutableStateOf(item.qty.toString())
    }
    var isEditing by remember {
        mutableStateOf(item.editMode)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            BasicTextField(
                value = editedName,
                onValueChange = { editedName = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
            BasicTextField(
                value = editedQty,
                onValueChange = { editedQty = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }
        Button(onClick = {
            isEditing = false
            onEditComplete(editedName, editedQty.toIntOrNull() ?: 1)
        }) {
            Text(text = "Save")
        }
    }
}

//@Preview
//@Composable
//fun editPreview() {
//    editorView(shoppingItem("Apple", 10)) {
//
//    }
//}