package com.lloydsbyte.smarttasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.lloydsbyte.smarttasks.ui.MainWallpaper
import com.lloydsbyte.smarttasks.ui.theme.PreviewWithTheme
import com.lloydsbyte.smarttasks.ui.theme.SmartTasksTheme
import com.lloydsbyte.smarttasks.utils.database.AppDatabase
import timber.log.Timber

class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.pullTasks(database = AppDatabase.getDatabase(this.application))

        enableEdgeToEdge()
        setContent {
            SmartTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainWallpaper()
                    val navController = rememberNavController()
                    MainNavHostController(innerPadding = innerPadding, navHostController = navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@PreviewWithTheme
@Composable
fun GreetingPreview() {
    SmartTasksTheme {
        MainWallpaper()
        Greeting("Android")
    }
}