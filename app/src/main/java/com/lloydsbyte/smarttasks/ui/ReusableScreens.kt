package com.lloydsbyte.smarttasks.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.lloydsbyte.smarttasks.R
import com.lloydsbyte.smarttasks.ui.theme.PreviewWithTheme
import com.lloydsbyte.smarttasks.ui.theme.SmartTasksTheme
import com.lloydsbyte.smarttasks.utils.TaskDetailButtonConstants.Companion._RESOLVED
import com.lloydsbyte.smarttasks.utils.TaskDetailButtonConstants.Companion._UNRESOLVED
import com.lloydsbyte.smarttasks.utils.Utils
import com.lloydsbyte.smarttasks.utils.database.TaskModel

@PreviewWithTheme
@Composable
fun PreviewReusables() {
    val navHost = rememberNavController()
    SmartTasksTheme {
        MainWallpaper()
//        TaskItemView(
//            taskModel = TaskModel(
//                title = "Hello there",
//                targetDate = "Apr 3 2024",
//                dueDate = "2024-08-29",
//                taskStatus = _RESOLVED
//            ), { }
//        )
//        CustomDialogInformative(title = "Hello There") {
//
//        }
        CustomDialogInput(title = "Enter your comment here") {
            
        }
    }
}

@Composable
fun MainWallpaper() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

    }
}

@Composable
fun TaskItemView(
    taskModel: TaskModel,
    onClick: (TaskModel) -> Unit?
) {

    val textColor =
        if (taskModel.taskStatus == _RESOLVED) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    val statusImage =
        if (taskModel.taskStatus == _RESOLVED) R.drawable.btn_resolved else R.drawable.btn_unresolved
    var widthPercentage by remember {
        mutableFloatStateOf(1f)
    }
    widthPercentage = if (taskModel.taskStatus != _UNRESOLVED) {
        .9f
    } else {
        1f
    }

    Box(modifier = Modifier
        .padding(10.dp, 5.dp)
        .fillMaxWidth()) {
        Card(
            modifier = Modifier
                .width(298.dp)
                .wrapContentHeight()
                .align(Alignment.Center),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            onClick = {
                onClick(taskModel)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = taskModel.title,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleMedium,
                        color = textColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth(widthPercentage)
                    )
                    if (taskModel.taskStatus != _UNRESOLVED) {
                        Image(
                            painter = painterResource(id = statusImage),
                            contentDescription = "Ticket Status Icon"
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .padding(0.dp, 7.dp, 0.dp, 10.dp)
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(color = Color.Black.copy(alpha = .5f))
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            text = "Due Date",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Start
                        )

                        Spacer(modifier = Modifier.height(7.dp))


                        Text(
                            text = Utils().convertToUserFormat(taskModel.dueDate ?: ""),
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor,
                            textAlign = TextAlign.Start
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {

                        Text(
                            text = "Days Left",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.End
                        )

                        Spacer(modifier = Modifier.height(7.dp))


                        Text(
                            text = Utils().getDaysLeft(taskModel.dueDate ?: ""),
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor,
                            textAlign = TextAlign.Start
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun CustomDialogInformative(
    title: String,
    onClick: (Boolean) -> Unit?
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Card(
            modifier = Modifier
                .aspectRatio(1.2f)
                .width(298.dp)
                .padding(10.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .padding(12.dp, 0.dp)
                            .weight(1f),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = {
                            onClick(true)
                        }) {
                        Text(
                            text = stringResource(id = R.string.custom_dialog_yes),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(12.dp, 0.dp)
                            .weight(1f),
                        colors = ButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledContentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledContainerColor = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(5.dp),
                        onClick = {
                            onClick(false)
                        }) {
                        Text(
                            text = stringResource(id = R.string.custom_dialog_no),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomDialogInput(
    title: String,
    onClick: (String) -> Unit?
) {

    var commentEnteredState by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Card(
            modifier = Modifier
                .aspectRatio(1.2f)
                .width(298.dp)
                .padding(10.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = commentEnteredState,
                    onValueChange = {
                        commentEnteredState = it
                    },
                    label = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    shape = RoundedCornerShape(5.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    maxLines = 5,
                    colors = TextFieldDefaults.colors(
                        unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary
                    )
                )

                Button(
                    modifier = Modifier
                        .padding(12.dp, 0.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    onClick = {
                        onClick(commentEnteredState)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.custom_input_action_btn),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

            }

        }
    }

}