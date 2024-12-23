package com.example.ucp2_176_pam.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHomeDsn : AlamatNavigasi {
    override val route = "homeDsn"
}
object DestinasiHomeMataKuliah : AlamatNavigasi {
    override val route = "homeMatkul"
}

object DestinasiDetail : AlamatNavigasi {
    override val route = "detail"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdate : AlamatNavigasi {
    override val route = "update"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}