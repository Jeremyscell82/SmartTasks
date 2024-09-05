package com.lloydsbyte.smarttasks.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lloydsbyte.smarttasks.MainNavKeys
import com.lloydsbyte.smarttasks.R
import com.lloydsbyte.smarttasks.ui.theme.PreviewWithTheme
import com.lloydsbyte.smarttasks.ui.theme.SmartTasksTheme
import com.lloydsbyte.smarttasks.utils.database.AppDatabase

@PreviewWithTheme
@Composable
fun PreviewHomeScreens() {
    SmartTasksTheme {
        MainWallpaper()
        HomeScreen(innerpadding = PaddingValues(), navHostController = rememberNavController())
    }
}

@Composable
fun HomeScreen(
    innerpadding: PaddingValues,
    navHostController: NavHostController
) {
    val viewModel = viewModel<HomeViewModel>()
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context.applicationContext)
    val todayInStr = stringResource(id = R.string.home_today)

    LaunchedEffect(key1 = Unit) {
        viewModel.todayStr = todayInStr
        viewModel.initViewModel(database)
    }
    var clicked by remember {
        mutableStateOf(false)
    }

    var taskId by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = clicked) {
        if (clicked) {
            navHostController.navigate(MainNavKeys.DetailScreen+"/$taskId")
            clicked = false
        }
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .padding(innerpadding)){

        Row(
            modifier = Modifier
                .padding(6.dp, 24.dp)
                .fillMaxWidth()
                .height(38.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Left button",
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
                    .clickable {
                        viewModel.showPreviousDay(database)
                    },
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondary)
            )

            Text(
                text = viewModel.displayDate.value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Image(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Left button",
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
                    .clickable {
                        viewModel.showNextDay(database)
                    },
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondary)
            )

        }

        if (viewModel.taskList.isEmpty()){
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 60.dp)){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .height(200.dp)
                            .aspectRatio(1f),
                        painter = painterResource(id = R.drawable.empty_screen), contentDescription = "Empty Screen Icon")

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.home_empty_screen),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .padding(0.dp, 60.dp, 0.dp, 0.dp)
                    .fillMaxSize()
            ) {
                items(viewModel.taskList) { task ->
                    TaskItemView(taskModel = task) { taskModel ->
                        taskId = taskModel.dbKey.toString()
                        clicked = true
                        Unit
                    }
                }
            }
        }
    }
}