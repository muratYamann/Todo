package com.yaman.todolist.pages

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yaman.todolist.R
import com.yaman.todolist.pages.home.MainActivity

class Notify(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val b = "420"
    private val task: String? by lazy {
        inputData.getString("Task Name")
    }
    private val intent: Intent by lazy {
        Intent(ctx, MainActivity::class.java)
    }
    private val pi: PendingIntent by lazy {
        PendingIntent.getActivity(
                ctx,
                333,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    private val notificationManager: NotificationManager by lazy {
        ctx.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    private val notification: Notification by lazy {
        NotificationCompat.Builder(ctx, b)
                .setSmallIcon(R.drawable.ic_alarm_add)
                .setColor(Color.rgb(30, 136, 229))
                .setContentTitle("Reminder")
                .setContentText(task)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .build()
    }


    override fun doWork(): Result {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val notificationChannel =
                        NotificationChannel(
                                b,
                                "Default Channel",
                                NotificationManager.IMPORTANCE_DEFAULT
                        )
                notificationManager.createNotificationChannel(notificationChannel)
            }

            notificationManager.notify(1112, notification)
            return Result.success()

        } catch (e: Exception) {

            return Result.failure()
        }

    }

}