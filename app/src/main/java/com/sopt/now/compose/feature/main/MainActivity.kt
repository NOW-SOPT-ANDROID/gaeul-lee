package com.sopt.now.compose.feature.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.sopt.now.compose.feature.base.BottomNavigationItem
import com.sopt.now.compose.R
import com.sopt.now.compose.feature.home.HomeScreen
import com.sopt.now.compose.feature.main.MainViewModel.Companion.LOGIN_INFO
import com.sopt.now.compose.feature.mypage.MyPageScreen
import com.sopt.now.compose.ui.theme.NOWSOPTAndroidTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOWSOPTAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userId = intent.getStringExtra(LOGIN_INFO)
                    Log.e("MainActivity", "userId: $userId")
                    userId?.let { id ->
                        MainScreen(userId = id.toInt())
                    }
                }
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(userId: Int){
        val context = LocalContext.current
        var selectedItem by remember { mutableIntStateOf(0) }
        val items = listOf(
            BottomNavigationItem(
                icon = Icons.Filled.Home,
                label = stringResource(id = R.string.bnv_home)
            ),
            BottomNavigationItem(
                icon = Icons.Filled.Search,
                label = stringResource(id = R.string.bnv_search)
            ),
            BottomNavigationItem(
                icon = Icons.Filled.Person,
                label = stringResource(id = R.string.bnv_mypage)
            )
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(stringResource(id = R.string.app_name))
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {

                when (selectedItem) {
                    0 -> {
                        HomeScreen(context, userId = userId)
                    }

                    1 -> {
                        Text("검색")
                    }

                    2 -> {
                        MyPageScreen(context, userId = userId)
                    }

                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainPreview() {
        NOWSOPTAndroidTheme {
            MainScreen(userId = 0)
        }
    }
}