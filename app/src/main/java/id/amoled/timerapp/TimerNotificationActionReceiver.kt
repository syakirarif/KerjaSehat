package id.amoled.timerapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.amoled.timerapp.util.NotificationUtil
import id.amoled.timerapp.util.PrefUtil

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstants.ACTION_STOP -> {
                KeduaActivity.removeAlarm(context)
                PrefUtil.setTimerState(KeduaActivity.TimerState.Stopped, context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = KeduaActivity.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                //KeduaActivity.removeAlarm(context)
                PrefUtil.setTimerState(KeduaActivity.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstants.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = KeduaActivity.setAlarm(context, KeduaActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(KeduaActivity.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
            AppConstants.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 1200L //TODO: Ganti waktu disini
                val wakeUpTime = KeduaActivity.setAlarm(context, KeduaActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(KeduaActivity.TimerState.Running, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}
