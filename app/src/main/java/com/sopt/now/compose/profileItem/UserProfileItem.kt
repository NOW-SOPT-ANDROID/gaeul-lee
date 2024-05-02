package com.sopt.now.compose.profileItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sopt.now.compose.R
import com.sopt.now.compose.data.User

@Composable
fun UserProfileItem(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(color = colorResource(id = R.color.pink)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier.padding(20.dp),
            model = "https://avatars.githubusercontent.com/u/91470334?v=4",
            contentDescription = "User Image",
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = user.nickname,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = user.phone,
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

