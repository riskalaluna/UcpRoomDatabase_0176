package com.example.ucp2_176_pam

import android.app.Application
import com.example.ucp2_176_pam.dependenciesinjection.ContainerApp

class DataDsnApp : Application() {
    lateinit var containerApp: ContainerApp //Fungsinya untuk menyimpan

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) //Membuat instance
        //instance adalah object yang dibuat dari class
    }
}