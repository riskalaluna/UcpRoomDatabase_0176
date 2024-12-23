package com.example.ucp2_176_pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2_176_pam.ui.view.DestinasiHome
import com.example.ucp2_176_pam.ui.view.HomeUtamaView
import com.example.ucp2_176_pam.ui.view.dosen.DestinasiInsertDsn
import com.example.ucp2_176_pam.ui.view.dosen.HomeDsnView
import com.example.ucp2_176_pam.ui.view.dosen.InsertDsnView
import com.example.ucp2_176_pam.ui.view.matakuliah.DestinasiInsertMK
import com.example.ucp2_176_pam.ui.view.matakuliah.DetailMataKuliahView
import com.example.ucp2_176_pam.ui.view.matakuliah.HomeMataKuliahView
import com.example.ucp2_176_pam.ui.view.matakuliah.InsertMKView
import com.example.ucp2_176_pam.ui.view.matakuliah.UpdateMataKuliahView
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
            HomeUtamaView(
                onDosen = {
                    navController.navigate(DestinasiHomeDsn.route)
                },
                onMataKuliah = {
                    navController.navigate(DestinasiHomeMataKuliah.route)
                },
            )
        }

        composable(
            route = DestinasiHomeDsn.route
        ) {
            HomeDsnView(
                onAddDsn = {
                    navController.navigate(DestinasiInsertDsn.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertDsn.route
        ) {
            InsertDsnView(
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
            route = DestinasiHomeMataKuliah.route
        ) {
            HomeMataKuliahView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetail.route}/$kode")
                    println(
                        "PengelolaHalaman: kode = $kode"
                    )
                },
                onAddMK = {
                    navController.navigate(DestinasiInsertMK.route)
                },
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsertMK.route
        ) {
            InsertMKView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            val kode = it.arguments?.getString(DestinasiDetail.KODE)

            kode?.let { kode ->
                DetailMataKuliahView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.KODE) {
                    type = NavType.StringType
                }
            )
        ) {

            UpdateMataKuliahView(
                onBack = {
                    navController.popBackStack()
                }, onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}