package com.example.playgroundapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast


class MessageBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("zeeshan", "onReceive: ${intent.data}")
        Toast.makeText(context, "zee received", Toast.LENGTH_SHORT).show()

        if (!intent.action.equals("android.provider.Telephony.SMS_RECEIVED")) return
        val data = intent.extras ?: return
        val allKeys = data.keySet()
        allKeys.forEach {key ->
            Log.d("zeeshan", "onReceive: key $key value ${data[key]}")
        }

    }

}