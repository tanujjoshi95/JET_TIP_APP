package com.example.jettipapp.widegts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun roundIconButton(
    modifier:Modifier,
    imgVector: ImageVector,
    onClick:()->Unit,
    tint :Color=Color.Black.copy(0.8f),
    backgroundColor:Color=MaterialTheme.colorScheme.background
)
{
    val iconButtonSizeMod=Modifier.size(40.dp)
    Card (
        modifier= modifier
            .padding(all = 4.dp)
            .clickable { onClick.invoke() }
            .then(iconButtonSizeMod)
           // .background(backgroundColor)
                    ,
        shape = CircleShape,
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        //backgroundColor=Color.Black
    ){
        Icon(imageVector = imgVector, contentDescription = null,
            tint=tint,
            modifier = Modifier.size(40.dp))
    }

}