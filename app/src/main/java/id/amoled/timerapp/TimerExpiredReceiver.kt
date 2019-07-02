package id.amoled.timerapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import id.amoled.timerapp.util.NotificationUtil
import id.amoled.timerapp.util.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(KeduaActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)

    }
}
