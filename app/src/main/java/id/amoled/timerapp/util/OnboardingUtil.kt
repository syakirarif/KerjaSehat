package id.amoled.timerapp.util

import android.content.Context


class OnboardingUtil {

    companion object {
        private val PREFERENCES_FILE = "materialsample_settings"

        fun saveSharedSetting(ctx: Context, settingName: String, settingValue: String) {
            val sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(settingName, settingValue)
            editor.apply()
        }

        fun readSharedSetting(ctx: Context, settingName: String, defaultValue: String): String? {
            val sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)
            return sharedPref.getString(settingName, defaultValue)
        }
    }

}