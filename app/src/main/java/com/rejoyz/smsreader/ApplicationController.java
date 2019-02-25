package com.rejoyz.smsreader;

import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.provider.Telephony;


import com.rejoyz.smsreader.di.DaggerApplicationComponent;
import com.rejoyz.smsreader.smslisting.SMSBroadcastReceiver;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class ApplicationController extends Application implements HasActivityInjector {

    private SMSBroadcastReceiver smsBroadcastReceiver;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;


    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
        smsBroadcastReceiver = new SMSBroadcastReceiver();
        registerReceiver(smsBroadcastReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
