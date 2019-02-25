package com.rejoyz.smsreader.smslisting;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.rejoyz.smsreader.R;
import com.rejoyz.smsreader.smslisting.presentation.MainActivity;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            int j = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getDisplayOriginatingAddress();
                    smsBody += smsMessage.getMessageBody();
                }
                showNotification(context, smsSender, j++);
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody += messages[i].getMessageBody();
                    }
                    showNotification(context, smsSender, messages.length-1);
                    smsSender = messages[0].getOriginatingAddress();
                }
            }

        }
    }

    void showNotification(Context context, String sender, int id) {
        String CHANNEL_ID = "sms_reader";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("New SMS from " + sender)
                .setSmallIcon(R.drawable.ic_account);
        String name = "SMS Reader";
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("messageId",1001);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
        builder.setContentIntent(intent);
        builder.setAutoCancel(true);
        mNotificationManager.notify(id, builder.build());
    }

    interface SmsListener {
        void onTextReceived(String text);
    }

}
