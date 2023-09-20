package com.example.patienttracker.presentation.patient_details

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: PatientDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onSuccessfullySaving: () -> Unit
){
    val state = viewModel.state

    val focusRequester = remember{ FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit){
        delay(500)
        focusRequester.requestFocus()
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                PatientDetailsViewModel.UiEvent.SaveNote -> {
                    onSuccessfullySaving()
                    Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show()
                }

                is PatientDetailsViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = { TopBar (onBackClicked = onBackClicked) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
            ,
        ){
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                ,
                value = state.name,
                onValueChange = { newValue ->
                    viewModel.onEvent(PatientDetailsEvents.EnteredName(newValue))
                },
                label = { Text(text = "Name") },
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next//this a button the keyboard that makes the action to go to the next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) } //the specific action button when clicked will move the focus to the next field
                )
            )
            Spacer(modifier = modifier.height(10.dp))
            Row(
                modifier = modifier.fillMaxWidth()
            ){
                OutlinedTextField(
                    modifier = modifier.weight(1f),
                    value = state.age,
                    onValueChange = { newValue ->
                        viewModel.onEvent(PatientDetailsEvents.EnteredAge(newValue))
                    },
                    label = { Text(text = "Age") },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Next) } //the specific action button when clicked will move the focus to the next field
                    )
                )
                Spacer(modifier = modifier.height(10.dp))
                RadioButtonGroup(
                    text = "Male",
                    selected = state.gender == 1,
                    onClick = { viewModel.onEvent(PatientDetailsEvents.SelectedMale) }
                )
                RadioButtonGroup(
                    text = "Female",
                    selected = state.gender == 2,
                    onClick = { viewModel.onEvent(PatientDetailsEvents.SelectedFemale) }
                )
            }
            Spacer(modifier = modifier.height(10.dp))
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.doctorAssigned,
                onValueChange = {newValue ->
                    viewModel.onEvent(PatientDetailsEvents.EnteredAssignedDoctor(newValue))},
                label = { Text(text = "Assigned Doctor's Name") },
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) }
                )
            )
            Spacer(modifier = modifier.height(10.dp))
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = state.prescription,
                onValueChange = {newValue ->
                    viewModel.onEvent(PatientDetailsEvents.EnteredPrescription(newValue))},
                label = { Text(text = "Prescription") },
                textStyle = MaterialTheme.typography.bodyMedium
            )
            Button(
                modifier = modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                onClick = {
                    viewModel.onEvent( PatientDetailsEvents.SaveButton )
                }
            ){
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun RadioButtonGroup(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
){
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onBackClicked: () -> Unit
){
    TopAppBar(
        title = {
            Text(
                text = "Patient's Details Screen",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClicked
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun PatientDetailsPreview(){
//    PatientTrackerTheme {
//        PatientDetailsScreen()
//    }
//}