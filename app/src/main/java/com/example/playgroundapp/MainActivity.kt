package com.example.playgroundapp

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Telephony
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import android.provider.Telephony.TextBasedSmsColumns
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.lang.Long
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }


    private lateinit var receiver: MessageBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intentFilter = IntentFilter(SMS_RECEIVED_ACTION)

        receiver = MessageBroadcastReceiver()
        registerReceiver(receiver, intentFilter)
        getAllSms(this)
    }

    private fun getInstalledUpiApps() {

        val uriBuilder = Uri.Builder()
        uriBuilder.scheme("upi").authority("pay")

        val uri = uriBuilder.build()
        val intent = Intent(Intent.ACTION_VIEW, uri)



        try {
            val activities =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            val activity = activities.first()


            Log.d(
                TAG,
                "getInstalledUpiApps: resolvePackageName ${activity.loadLabel(packageManager)}"
            )
            // Convert the activities into a response that can be transferred over the channel.
            val activityResponse = activities.map {
                val packageName = it.activityInfo.packageName
                val drawable = packageManager.getApplicationIcon(packageName)

                val bitmap = getBitmapFromDrawable(drawable)
                val icon = encodeToBase64(bitmap)

                mapOf(
                    "packageName" to packageName,
                    "icon" to icon,
                    "priority" to it.priority,
                    "preferredOrder" to it.preferredOrder
                )
            }

//            result.success(activityResponse)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
//            result.error("getInstalledUpiApps", "exception", ex)
        }
    }

    private fun encodeToBase64(image: Bitmap): String? {
        val byteArrayOS = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOS)
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP)
    }

    private fun getBitmapFromDrawable(drawable: Drawable): Bitmap {
        val bmp: Bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bmp)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bmp
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }


    private fun getAllSms(context: Context) {
        val cr: ContentResolver = context.contentResolver
        val c: Cursor? = cr.query(Telephony.Sms.CONTENT_URI, null, "body LIKE ? OR body LIKE ? OR body LIKE ?", arrayOf("%debit%","%transfer%","%credit%"), null)
        var totalSMS = 0
        if (c != null) {
            totalSMS = c.count
            if (c.moveToFirst()) {
                for (j in 0 until totalSMS) {

                    val smsDate: String = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.DATE))
                    val number: String = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                    val body: String = c.getString(c.getColumnIndexOrThrow(Telephony.Sms.BODY))
                    val dateFormat = Date(Long.valueOf(smsDate))
                    c.columnNames.forEach {
                        Log.d(TAG, "getAllSms: column $it")
                    }
                    val type: String =
                        when (c.getString(c.getColumnIndexOrThrow(Telephony.Sms.TYPE)).toInt()) {
                            Telephony.Sms.MESSAGE_TYPE_INBOX -> "inbox"
                            Telephony.Sms.MESSAGE_TYPE_SENT -> "sent"
                            Telephony.Sms.MESSAGE_TYPE_OUTBOX -> "outbox"
                            else -> ""
                        }
                    c.moveToNext()
                    Log.d(TAG, "getAllSms: smsDate $smsDate number $number body $body dateFormat $dateFormat type $type")
                }
            }
            c.close()
            Toast.makeText(this, "count $totalSMS", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No message to show!", Toast.LENGTH_SHORT).show()
        }
    }
}