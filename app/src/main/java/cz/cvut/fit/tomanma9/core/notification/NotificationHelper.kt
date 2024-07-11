package cz.cvut.fit.tomanma9.core.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cz.cvut.fit.tomanma9.R
import cz.cvut.fit.tomanma9.app.MainActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationHelper : KoinComponent {
  private val context: Context by inject()

  private var notificationId = 1
  private val channelId = context.packageName

  init {
    createChannel()
  }

  suspend fun showProgressNotification(
    title: String,
    content: String,
    priority: Int = NotificationCompat.PRIORITY_HIGH,
  ) {
    val intent = Intent(context, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(context, 7, intent, PendingIntent.FLAG_IMMUTABLE)
    if (
      ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) !=
        PackageManager.PERMISSION_GRANTED
    ) {
      return
    }

    val builder =
      NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_sheriff)
        .setContentTitle(title)
        .setContentText(content)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(priority)

    with(NotificationManagerCompat.from(context)) { notify(notificationId, builder.build()) }
    notificationId += 1
  }

  private fun createChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val name = context.getString(R.string.notification_channel_name)
      val descriptionText = context.getString(R.string.notification_channel_description)
      val importance = NotificationManager.IMPORTANCE_DEFAULT
      val channel =
        NotificationChannel(channelId, name, importance).apply { description = descriptionText }
      val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
      notificationManager.createNotificationChannel(channel)
    }
  }
}
