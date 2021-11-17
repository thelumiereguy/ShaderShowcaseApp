package com.thelumiereguy.shadershowcase

import android.app.Application
import android.os.StrictMode
import com.github.anrwatchdog.ANRWatchDog


class App : Application() {

    override fun onCreate() {
//        if (BuildConfig.DEBUG) {
//            StrictMode.setThreadPolicy(
//                StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .penaltyLog()
//                    .build()
//            )
//            StrictMode.setVmPolicy(
//                StrictMode.VmPolicy.Builder()
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