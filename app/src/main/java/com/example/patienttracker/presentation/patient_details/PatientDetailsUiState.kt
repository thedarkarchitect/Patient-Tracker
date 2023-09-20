package com.example.patienttracker.presentation.patient_details

data class PatientDetailsUiState(//this is going to be used to control the state
    val name: String = "",
    val age: String = "",
    val gender: Int = 0,
    val doctorAssigned: String = "",
    val prescription: String = ""
)
