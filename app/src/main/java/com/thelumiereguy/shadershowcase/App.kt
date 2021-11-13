package com.thelumiereguy.shadershowcase

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy


class App : Application() {

    override fun onCreate() {
//        StrictMode.setThreadPolicy(
//            ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork() // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build()
//        )
//        StrictMode.setVmPolicy(
//            VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .penaltyDeath()
//                .build()
//        )
        super.onCreate()
    }
}