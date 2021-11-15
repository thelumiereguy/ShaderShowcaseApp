package com.thelumiereguy.shadershowcase

import android.app.Application
import com.github.anrwatchdog.ANRWatchDog


class App : Application() {

    override fun onCreate() {
//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(
//                ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectNetwork() // or .detectAll() for all detectable problems
//                    .penaltyLog()
//                    .build()
//            )
//            StrictMode.setVmPolicy(
//                VmPolicy.Builder()
//                    .detectLeakedSqlLiteObjects()
//                    .detectLeakedClosableObjects()
//                    .penaltyLog()
//                    .build()
//            )
//        }
//        ANRWatchDog().start()
        super.onCreate()
    }
}