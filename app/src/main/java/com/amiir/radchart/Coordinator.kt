package com.amiir.radchart

import android.app.Activity
import android.content.Intent
import com.amiir.radchart.ui.MainActivity
import com.core.utils.SettingManager


object Coordinator {

    fun getNextIntent(activity: Activity, settingManager: SettingManager): Intent {

        return Intent(activity, MainActivity::class.java)

    }

}