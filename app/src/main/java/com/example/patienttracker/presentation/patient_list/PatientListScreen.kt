package com.example.patienttracker.presentation.patient_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    modifier : Modifier = Modifier,
    onFabClicked: () -> Unit,
    onItemClicked: (Int?) -> Unit,
    viewModel: PatientListViewModel = hiltViewModel()
) {

    val patientList by viewModel.patientList.collectAsState()

    Scaffold(
        topBar = { ListAppbar() },
        floatingActionButton = {
            ListFab( onFabClicked = onFabClicked )
        }
    ){innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                items(patientList){ patient ->
                    PatientItem(
                        patient = patient,
                        onItemClicked = {
                            patient.patientId?.let { id ->
                                onItemClicked(id)
                            }
                        },
                        onDeleteConfirm = {
                            viewModel.deletePatient(patient)
                        }
                    )
                }
            }
        }

        if( patientList.isEmpty() && !viewModel.isLoading){
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Add Patients Details\nby pressing add button.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAppbar(){
    TopAppBar(
        title = {
            Text(
                text = "Patient Tracker",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: () -> Unit
){
    FloatingActionButton(
        onClick = onFabClicked
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Patient Button",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}