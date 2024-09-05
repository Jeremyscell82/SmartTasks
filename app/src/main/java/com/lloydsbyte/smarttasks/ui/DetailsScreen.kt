package com.lloydsbyte.smarttasks.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lloydsbyte.smarttasks.R
import com.lloydsbyte.smarttasks.ui.theme.PreviewWithTheme
import com.lloydsbyte.smarttasks.ui.theme.SmartTasksTheme
import com.lloydsbyte.smarttasks.utils.TaskDetailButtonConstants.Companion._CANT_RESOLVE
import com.lloydsbyte.smarttasks.utils.TaskDetailButtonConstants.Companion._RESOLVED
import com.lloydsbyte.smarttasks.utils.TaskDetailButtonConstants.Companion._UNRESOLVED
import com.lloydsbyte.smarttasks.utils.Utils
import com.lloydsbyte.smarttasks.utils.database.AppDatabase
import timber.log.Timber

@PreviewWithTheme
@Composable
fun PreviewDetailsScreen() {
    SmartTasksTheme {
        MainWallpaper()
        DetailScreen(
            innerPadding = PaddingValues(),
            navHostController = rememberNavController(),
            taskId = "0"
        )
    }
}


@Composable
fun DetailScreen(
    innerPadding: PaddingValues,
    navHostController: NavHostController,
    taskId: String
) {

    val viewModel = viewModel<DetailViewModel>()
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context.applicationContext)

    var resolvedStatus by remember {
        mutableStateOf("")
    }
    var colorStatus by remember {
        mutableStateOf(Color.Red)
    }
    var ticketStatusColor by remember {
        mutableStateOf(Color.Red)
    }
    var ticketStatus by remember {
        mutableStateOf("")
    }
    var actionClicked by remember {
        mutableStateOf(false)
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var showCommentDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getTask(database, taskId)
    }

    LaunchedEffect(key1 = actionClicked) {
        if (actionClicked) {
            showDialog = true
            actionClicked = false
        }
    }




    if (viewModel.taskModel.value.taskId.isNotEmpty()) {
        colorStatus =
            if (viewModel.isResolved()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
        if (viewModel.isCompleted()) {
            ticketStatusColor = if (viewModel.isResolved()) {
                resolvedStatus = _RESOLVED
                MaterialTheme.colorScheme.primary
            } else {
                resolvedStatus = _CANT_RESOLVE
                MaterialTheme.colorScheme.secondary
            }
        } else {
            resolvedStatus = _UNRESOLVED
            ticketStatusColor = MaterialTheme.colorScheme.onPrimaryContainer
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "Back Arrow",
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
                    .clickable {
                        navHostController.navigateUp()
                    },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
            )

            Text(
                text = stringResource(id = R.string.task_detail_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
            )

        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .paint(
                        painter = painterResource(id = R.drawable.task_details),
                        contentScale = ContentScale.FillBounds
                    )
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(0.dp, 48.dp, 0.dp, 0.dp)
                ) {

                    Text(
                        text = viewModel.taskModel.value.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = colorStatus,
                        textAlign = TextAlign.Start
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(0.dp, 7.dp, 0.dp, 10.dp)
                            .background(Color.Black.copy(alpha = .5f))
                            .height(1.dp)
                            .fillMaxWidth()
                    )

                    //Due date section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                        ) {

                            Text(
                                text = stringResource(id = R.string.task_item_due_date),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                text = Utils().convertToUserFormat(
                                    viewModel.taskModel.value.dueDate ?: ""
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                color = colorStatus
                            )

                        }

                        Column(
                            modifier = Modifier
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.End
                        ) {

                            Text(
                                text = stringResource(id = R.string.task_item_days_left),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Spacer(modifier = Modifier.height(7.dp))

                            Text(
                                text = Utils().getDaysLeft(viewModel.taskModel.value.dueDate ?: ""),
                                style = MaterialTheme.typography.titleMedium,
                                color = colorStatus
                            )

                        }

                    }

                    Spacer(
                        modifier = Modifier
                            .padding(0.dp, 7.dp, 0.dp, 10.dp)
                            .background(Color.Black.copy(alpha = .5f))
                            .height(1.dp)
                            .fillMaxWidth()
                    )

                    Text(
                        text = viewModel.taskModel.value.description,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )


                    Spacer(
                        modifier = Modifier
                            .padding(0.dp, 7.dp, 0.dp, 10.dp)
                            .background(Color.Black.copy(alpha = .5f))
                            .height(1.dp)
                            .fillMaxWidth()
                    )

                    Text(
                        text = resolvedStatus,
                        color = ticketStatusColor,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(0.dp, 0.dp, 0.dp, 10.dp)
                    )

                }
            }
        }

        if (!viewModel.isCompleted()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    modifier = Modifier
                        .padding(12.dp, 0.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        ticketStatus = _RESOLVED
                        actionClicked = true
                    }) {
                    Text(
                        text = stringResource(id = R.string.task_detail_act_resolve),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Button(
                    modifier = Modifier
                        .padding(12.dp, 0.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = {
                        ticketStatus = _CANT_RESOLVE
                        actionClicked = true
                    }) {
                    Text(
                        text = stringResource(id = R.string.task_detail_act_cant_resolve),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize()){
                val image = if (viewModel.isResolved()) R.drawable.sign_resolved else R.drawable.unresolved_sign
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Ticket Status Icon",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(140.dp)
                        .width(140.dp))
            }
        }
    }

    AnimatedVisibility(
        visible = showDialog,
        enter = fadeIn(
            animationSpec = tween(600)
        ),
        exit = fadeOut(
            tween(300)
        )
    ) {
        CustomDialogInformative(title = stringResource(id = R.string.custom_dialog_title)) { userClicked ->
            if (userClicked){
                //User wants leave a comment
                showCommentDialog = true
                showDialog = false
            } else {
                //User does not want to leave a comment
                viewModel.updateTaskStatus(database, ticketStatus)
            }
            showDialog = false
            Unit
        }
    }

    AnimatedVisibility(
        visible = showCommentDialog,
        enter = fadeIn(tween(600)),
        exit = fadeOut(tween(300))
    ) {
        CustomDialogInput(title = stringResource(id = R.string.custom_input_dialog_hint)) { comment ->
            viewModel.updateTaskWithComment(database, comment, ticketStatus)
            showCommentDialog = false
            Unit
        }
    }
}