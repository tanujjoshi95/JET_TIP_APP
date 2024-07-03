package com.example.jettipapp.Component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun input(
    modifier:Modifier=Modifier,
    valstate:MutableState<String>,
    labelId:String,
    enable:Boolean,
    isSingleLine:Boolean,
    keyBoardType:KeyboardType=KeyboardType.Number,
    imeAction:ImeAction=ImeAction.Next,
    onAction:KeyboardActions= KeyboardActions.Default
){
    OutlinedTextField(value = valstate.value,
        onValueChange = {valstate.value=it},
        label ={ Text(text = labelId)},
        leadingIcon = { Icon(imageVector =Icons.Rounded.AttachMoney , contentDescription = "money")},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = Color.Black),
        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enable,
        keyboardOptions = KeyboardOptions(keyboardType = keyBoardType,
            imeAction = imeAction),
        keyboardActions = onAction
    )
}