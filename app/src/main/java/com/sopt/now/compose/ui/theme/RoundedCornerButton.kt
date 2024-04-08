package com.sopt.now.compose.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCornerButton(
    buttonText : Int,
    onClick : () -> Unit,
    modifier : Modifier = Modifier
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ){
        Text(stringResource(id = buttonText))
    }
}