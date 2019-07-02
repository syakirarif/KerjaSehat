package id.amoled.timerapp.util

import android.content.Context
import android.preference.PreferenceManager
import id.amoled.timerapp.KeduaActivity

class PrefUtil {
    companion object {
        fun getTimerLength(context: Context): Int {
            return 1
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "id.amoled.timerapp.previous_timer_length"

        fun getPreviousTimerLengthSeconds(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }

        private const val TIMER_STATE_ID = "id.amoled.timerapp.timer_state"

        fun getTimerState(context: Context): KeduaActivity.TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return KeduaActivity.TimerState.values()[ordinal]
        }

        fun setTimerState(state: KeduaActivity.TimerState, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }

        private const val SECONDS_REMAINING_ID = "id.amoled.timerapp.seconds_remaining"

        fun getSecondsRemaining(context: Context): Long {
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            return preference.getLong(SECONDS_REMAINING_ID, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }

        private const val ALARM_SET_TIME_ID = "id.amoled.timerapp.backgrounded_time"

        fun getAlarmSetTime(context: Context): Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getLong(ALARM_SET_TIME_ID, 0)
        }

        fun setAlarmSetTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID, time)
            editor.apply()
        }
    }
}