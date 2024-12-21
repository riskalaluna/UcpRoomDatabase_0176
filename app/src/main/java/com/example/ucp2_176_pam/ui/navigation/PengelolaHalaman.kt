package com.example.ucp2_176_pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2_176_pam.ui.view.dosen.DestinasiInsert
import com.example.ucp2_176_pam.ui.view.dosen.DetailDsnView
import com.example.ucp2_176_pam.ui.view.dosen.HomeDsnView
import com.example.ucp2_176_pam.ui.view.dosen.InserDsnView
import com.example.ucp2_176_pam.ui.viewmodel.dosen.HomeDsnViewModel

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = DestinasiHome.route) {
        composable(
            route = DestinasiHome.route
        ) {
            HomeDsnView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDetail.route}/$nidn")
                    println(
                        "PengelolaHalaman: nidn = $nidn"
                    )
                },
                onAddDsn = {
                    navController.navigate(DestinasiInsert.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsert.route
        ) {
            InserDsnView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.NIDN) {
                    type = NavType.StringType
                }
            )
        ) {
            val nidn = it.arguments?.getString(DestinasiDetail.NIDN)

            nidn?.let { nidn ->
                DetailDsnView(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}