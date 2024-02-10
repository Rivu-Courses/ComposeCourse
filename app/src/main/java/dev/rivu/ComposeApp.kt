package dev.rivu

import android.app.Application
import com.theapache64.rebugger.Rebugger
import com.theapache64.rebugger.RebuggerConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeApp: Application() {
    override fun onCreate() {
        super.onCreate()
        RebuggerConfig.init(tag = "Pagination") { tag, message ->
            println()
        }
    }
}