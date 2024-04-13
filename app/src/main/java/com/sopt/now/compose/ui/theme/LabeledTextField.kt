package com.sopt.now.compose.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun LabeledTextField(
    labelTextId: Int,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderTextId: Int,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = modifier) {
        Text(stringResource(id = labelTextId))
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(stringResource(id = placeholderTextId)) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
        )
    }
}