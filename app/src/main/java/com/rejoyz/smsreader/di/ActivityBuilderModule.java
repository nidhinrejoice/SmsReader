package com.rejoyz.smsreader.di;

import com.rejoyz.smsreader.smslisting.presentation.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

}
