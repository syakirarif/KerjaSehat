package id.amoled.timerapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.amoled.timerapp.util.NotificationUtil
import id.amoled.timerapp.util.PrefUtil
import kotlinx.android.synthetic.main.activity_kedua.*
import kotlinx.android.synthetic.main.content_main2.*
import java.util.*
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog

class KeduaActivity : AppCompatActivity() {


    companion object {
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context) {
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000

        val PREF_USER_FIRST_TIME = "user_first_time"

    }

    enum class TimerState {
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds = 0L
    private var timerState = TimerState.Stopped

    private var secondsRemaining = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kedua)

        fab_play.setOnClickListener {
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }

        fab_pause.setOnClickListener {
            NotificationUtil.showTimerPaused(this@KeduaActivity)
            pauseTimer()
            timerState = TimerState.Paused
            updateButtons()
        }

        fab_stop.setOnClickListener {
            val pDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            pDialog.titleText = "Peringatan"
            pDialog.contentText = "Yakin ingin hentikan timer?"
            pDialog.confirmText = "Ya"
            pDialog.cancelText = "Tidak"
            pDialog.setCancelable(true)
            pDialog.showCancelButton(true)
            pDialog.setConfirmClickListener {
                KeduaActivity.removeAlarm(this@KeduaActivity)
                NotificationUtil.showTimerExpired(this@KeduaActivity)
                stopTimer()
                pDialog.cancel()
            }
            pDialog.show()
        }
    }

    override fun onResume() {
        super.onResume()

        initTimer()

        removeAlarm(this)
        NotificationUtil.hideTimerNotification(this)
    }

    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running) {
            timer.cancel()
            pauseTimer()
            val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
            NotificationUtil.showTimerRunning(this, wakeUpTime)
        } else if (timerState == TimerState.Paused) {
            NotificationUtil.showTimerPaused(this)
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondsRemaining, this)
        PrefUtil.setTimerState(timerState, this)
    }

    private fun initTimer() {
        timerState = PrefUtil.getTimerState(this)

        if (timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining =
            if (timerState == TimerState.Running || timerState == TimerState.Paused)
                PrefUtil.getSecondsRemaining(this)
            else
                timerLengthSeconds

        val alarmSetTime = PrefUtil.getAlarmSetTime(this)
        if (alarmSetTime > 0)
            secondsRemaining -= nowSeconds - alarmSetTime

        if (secondsRemaining <= 0)
            onTimerFinished()
        else if (timerState == TimerState.Running)
            startTimer()

        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished() {

        val pDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        pDialog.titleText = "Selesai"
        pDialog.contentText = "Timer telah berhenti"
        pDialog.confirmText = "OK"
        pDialog.setCancelable(true)
        pDialog.show()

        timerState = TimerState.Stopped
        setNewTimerLength()

        progress_countdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCountdownUI()
    }

    fun startTimer() {
        timerState = TimerState.Running
        tv_tekan_play.text = "Fokuslah dan perhatikan postur tubuh saat bekerja"
        animation_kerja.visibility = View.VISIBLE

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()

        val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
        NotificationUtil.showTimerRunning(this, wakeUpTime)
    }

    fun pauseTimer() {
        timer.cancel()
        tv_tekan_play.text = "Timer di pause"
        animation_kerja.visibility = View.GONE

    }

    fun stopTimer() {
        timer.cancel()
        tv_tekan_play.text = "Timer dihentikan"
        animation_kerja.visibility = View.GONE
        //pauseOffset = 0L
        onTimerFinished()
    }

    private fun setNewTimerLength() {
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerLengthSeconds = (lengthInMinutes * 1200L) //TODO: GANTI WAKTU DISINI

        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength() {
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
        progress_countdown.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI() {

        progress_countdown.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons() {
        when (timerState) {
            TimerState.Running -> {
                fab_play.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }

            TimerState.Stopped -> {
                fab_play.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false
            }

            TimerState.Paused -> {
                fab_play.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true
            }
        }
    }
}
