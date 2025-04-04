package com.rogerjack.noteapp.features.notes.ui.screens

import com.rogerjack.noteapp.R



import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rogerjack.noteapp.common.*
import com.rogerjack.noteapp.common.base.NoteState
import com.rogerjack.noteapp.data.local.models.Notes
import com.rogerjack.noteapp.data.local.models.categoryList
import com.rogerjack.noteapp.features.notes.ui.viewmodel.NoteEvents
import com.rogerjack.noteapp.features.notes.ui.viewmodel.NoteViewModel
import com.rogerjack.noteapp.ui.theme.Background
import com.rogerjack.noteapp.ui.theme.Purple500
import com.rogerjack.noteapp.ui.theme.Teal200
import kotlinx.coroutines.flow.collectLatest


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UpdateNoteScreen(
    navHostController: NavHostController,
    viewModel: NoteViewModel = hiltViewModel()
) {

    val note =
        navHostController.previousBackStackEntry?.savedStateHandle?.get<Notes>("data") ?: Notes(
            "",
            "",
            "", 0
        )

    var title by remember { mutableStateOf(note.title) }
    var task by remember { mutableStateOf(note.description) }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current.getActivity()!!
    var currentCategoryState by remember { mutableStateOf(note.category) }
    var currentColor by remember { mutableStateOf(note.color) }
    var currentId by remember { mutableStateOf(categoryList[0].id) }


    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.updateNoteEventFlow.collectLatest {
            isLoading = when (it) {
                is NoteState.Success -> {
                    context.showToast(it.data)
                    navHostController.navigateUp()
                    false
                }
                is NoteState.Failure -> {
                    context.showToast(
                        it.msg
                    )
                    false
                }
                NoteState.Loading -> true

            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(task))
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(
                            NoteEvents.UpdateNoteEvent(
                                note.copy(
                                    title = title,
                                    description = task,
                                    category = currentCategoryState,
                                    color = currentColor
                                )
                            )
                        )
                    },
                    backgroundColor = Color.Blue,
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)

        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Purple500)
                ) {
                    Row(
                        modifier = Modifier.padding(start = 10.dp, top = 40.dp, bottom = 10.dp)
                    ) {
                        IconButton(onClick = {
                            navHostController.navigateUp()
                        }) {
                            Icon(
                                Icons.Rounded.ArrowBack,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.edit_note),
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(CenterVertically)
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    categoryList.forEach {
                        AppRadioButton(
                            category = it,
                            selected = it.title == currentCategoryState,
                            onValueChange = { data ->
                                currentCategoryState = data.title
                                currentColor = data.id
                                currentId = data.id

                            }
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            if (currentColor == 1) Color.Red.copy(alpha = 0.4f) else if (currentColor == 2) Teal200.copy(
                                alpha = 0.4f
                            ) else if (currentColor == 3) Color.Green.copy(alpha = 0.4f) else Color.White
                        )
                ) {
                    LazyColumn {
                        item {
                            CommonTextField(
                                text = title,
                                label = stringResource(id = R.string.enter_title),
                                modifier = Modifier.fillMaxWidth(),
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Down)
                                }
                            ) {
                                title = it

                            }

                            CommonTextField(
                                text = task,
                                label = stringResource(id = R.string.write_task),
                                modifier = Modifier.fillMaxSize(),
                                imeAction = ImeAction.Default,
                                onNext = {
                                    focusManager.moveFocus(FocusDirection.Right)
                                }
                            ) {
                                task = it

                            }
                        }
                    }
                }
            }
        }
    }

    if (isLoading)
        LoadingDialog()

}