package id.amoled.timerapp.util

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import id.amoled.timerapp.AppConstants
import id.amoled.timerapp.KeduaActivity
import id.amoled.timerapp.R
import id.amoled.timerapp.TimerNotificationActionReceiver
import id.amoled.timerapp.ergonomic.ErgonomicActivity
import java.text.SimpleDateFormat
import java.util.*


class NotificationUtil {
    companion object {
        private const val CHANNEL_ID_TIMER = "10001"
        private const val CHANNEL_NAME_TIMER = "Timer App Timer"
        private const val TIMER_ID = 1

        fun showTimerExpired(context: Context) {
            val startIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            startIntent.action = AppConstants.ACTION_START
            val startPendingIntent = PendingIntent.getBroadcast(
                context,
                0, startIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER)
            nBuilder.setContentTitle("Timer berhenti dan istirahatlah")
                .setContentText("Bagaimana cara istirahat yang baik? klik disini.")
                .setContentIntent(getPendingIntentWithStack(context, ErgonomicActivity::class.java))
                .addAction(R.drawable.ic_play_arrow_white_24dp, "Mulai lagi", startPendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER)

            nManager.notify(TIMER_ID, nBuilder.build())

            triggerAlarmSoundAndVibration(context)
        }

        fun showTimerRunning(context: Context, wakeUpTime: Long) {
            val stopIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            stopIntent.action = AppConstants.ACTION_STOP
            val stopPendingIntent = PendingIntent.getBroadcast(
                context,
                0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            val pauseIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            pauseIntent.action = AppConstants.ACTION_PAUSE
            val pausePendingIntent = PendingIntent.getBroadcast(
                context,
                0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            val df = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT)

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER)
            nBuilder.setContentTitle("Timer dimulai dan saatnya bekerja!")
                .setContentText("Waktu istirahat: ${df.format(Date(wakeUpTime))}")
                .setContentIntent(getPendingIntentWithStack(context, KeduaActivity::class.java))
                .setOngoing(true)
                .addAction(R.drawable.ic_stop_white_24dp, "Berhenti", stopPendingIntent)
                .addAction(R.drawable.ic_pause_white_24dp, "Pause", pausePendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER)

            nManager.notify(TIMER_ID, nBuilder.build())

            triggerNotifSoundAndVibration(context)
        }

        fun showTimerPaused(context: Context) {
            val resumeIntent = Intent(context, TimerNotificationActionReceiver::class.java)
            resumeIntent.action = AppConstants.ACTION_RESUME
            val resumePendingIntent = PendingIntent.getBroadcast(
                context,
                0, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )

            val nBuilder = getBasicNotificationBuilder(context, CHANNEL_ID_TIMER)
            nBuilder.setContentTitle("Waktu di pause")
                .setContentText("Lanjutkan?")
                .setContentIntent(getPendingIntentWithStack(context, KeduaActivity::class.java))
                .setOngoing(true)
                .addAction(R.drawable.ic_play_arrow_white_24dp, "Lanjut", resumePendingIntent)

            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.createNotificationChannel(CHANNEL_ID_TIMER, CHANNEL_NAME_TIMER)

            nManager.notify(TIMER_ID, nBuilder.build())

            triggerNotifSoundAndVibration(context)

        }

        fun triggerAlarmSoundAndVibration(context: Context){
            val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(2500, VibrationEffect.DEFAULT_AMPLITUDE))
            }else{
                vibrator.vibrate(2500)
            }

            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val ringtone: Ringtone = RingtoneManager.getRingtone(context, notificationSound)
            ringtone.play()
        }

        fun triggerNotifSoundAndVibration(context: Context){
            val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
            }else{
                vibrator.vibrate(300)
            }

            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone: Ringtone = RingtoneManager.getRingtone(context, notificationSound)
            ringtone.play()
        }

        fun hideTimerNotification(context: Context) {
            val nManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nManager.cancel(TIMER_ID)
        }

        private fun getBasicNotificationBuilder(context: Context, channelId: String)
                : NotificationCompat.Builder {
            val notificationSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val nBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_timer_white_24dp)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setDefaults(Notification.DEFAULT_ALL)
                //.setSound(notificationSound)
            //if (playSound) nBuilder.setSound(notificationSound)
            return nBuilder
        }

        private fun <T> getPendingIntentWithStack(context: Context, javaClass: Class<T>): PendingIntent {
            val resultIntent = Intent(context, javaClass)
            resultIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addParentStack(javaClass)
            stackBuilder.addNextIntent(resultIntent)

            return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        @TargetApi(28)
        private fun NotificationManager.createNotificationChannel(
            channelID: String,
            channelName: String
        ) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                val channelImportance = NotificationManager.IMPORTANCE_HIGH
                val nChannel = NotificationChannel(channelID, channelName, channelImportance)
                nChannel.enableLights(true)
                nChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                nChannel.shouldShowLights()
                nChannel.canBypassDnd()
                nChannel.canShowBadge()
                nChannel.enableVibration(true)
                nChannel.lightColor = Color.BLUE
                this.createNotificationChannel(nChannel)
            }
        }
    }
}