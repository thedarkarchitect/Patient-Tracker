package com.example.patienttracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.patienttracker.presentation.patient_details.PatientDetailsScreen
import com.example.patienttracker.presentation.patient_list.PatientListScreen
import com.example.patienttracker.util.Constants.PATIENT_DETAILS_ARG_KEY

sealed class Screen(val route: String){
    data object PatientList: Screen("patient_list_screen")
    data object PatientDetails: Screen("patient_details_screen?$PATIENT_DETAILS_ARG_KEY={$PATIENT_DETAILS_ARG_KEY}"){
        fun passPatientId(patientId: Int? = null): String {
            return "patient_details_screen?$PATIENT_DETAILS_ARG_KEY=$patientId"
        }
    }
}

@Composable
fun NavGraphSetup(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Screen.PatientList.route
    ){
        composable(
          route = Screen.PatientList.route
        ){
            PatientListScreen(
                onFabClicked = {
                    //this will navigate to the add patient screen using a floating action
                    navController.navigate( Screen.PatientDetails.route )
                },
                onItemClicked = { id ->
                    //this will navigate using the specific patient id to the screen to mke changes
                    navController.navigate( Screen.PatientDetails.passPatientId(patientId = id) )
                }
            )
        }

        composable(
            route = Screen.PatientDetails.route,
            arguments = listOf(navArgument(name = PATIENT_DETAILS_ARG_KEY){
                type = NavType.IntType
                defaultValue = -1
            })
        ){
            PatientDetailsScreen(
                onBackClicked = { navController.navigateUp() },
                onSuccessfullySaving = { navController.navigateUp() }
            )
        }
    }
}