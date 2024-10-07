package br.uri.listoflegends.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.uri.listoflegends.R

fun sendNotification(channelId: String, title: String, descriptionText: String, context: Context, importance: Int?) {
    val notificationId = 1

    var importance = importance
    if (importance == null){
        importance = NotificationManager.IMPORTANCE_DEFAULT
    }
    val channel =
        NotificationChannel(channelId, title, importance).apply {
            description = descriptionText
        }

    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel as NotificationChannel)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle(title)
        .setContentText(descriptionText)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Sem permissão de notificação.", Toast.LENGTH_LONG).show()
        }
        notify(notificationId, builder.build())
    }
}