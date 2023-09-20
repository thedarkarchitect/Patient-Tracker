package com.example.patienttracker.presentation.patient_details

sealed class PatientDetailsEvents {//this allows you to manipulate user events
//    data class receive input and objects are for actions
//    inputs
    data class EnteredName(val name: String): PatientDetailsEvents()
    data class EnteredAge(val age: String): PatientDetailsEvents()
    data class EnteredAssignedDoctor(val doctor: String): PatientDetailsEvents()
    data class EnteredPrescription(val prescription: String): PatientDetailsEvents()
//    Actions
    data object SelectedMale: PatientDetailsEvents()
    data object SelectedFemale: PatientDetailsEvents()
    data object SaveButton: PatientDetailsEvents()
}
