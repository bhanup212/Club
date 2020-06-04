package com.bhanu.club

import android.app.Application
import com.bhanu.club.di.apiModule
import com.bhanu.club.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


/**
 * Created by Bhanu Prakash Pasupula on 04,Jun-2020.
 * Email: pasupula1995@gmail.com
 */
class App:Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
    private fun initKoin(){
        startKoin {
            androidContext(applicationContext)
            androidLogger(Level.DEBUG)
            modules( viewModelModules,apiModule)
        }
    }
}