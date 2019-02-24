package com.rejoyz.smsreader.di;

import android.app.Application;
import android.content.Context;

import com.rejoyz.smsreader.smslisting.data.MessageDataRepository;
import com.rejoyz.smsreader.smslisting.domain.GetMessagesUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }


    @Provides
    @Singleton
    MessageDataRepository provideMessageRepository(Context context) {
        return new MessageDataRepository(context);
    }
    @Provides
    @Singleton
    GetMessagesUseCase provideGetMessagesUseCase(MessageDataRepository messageDataRepository) {
        return new GetMessagesUseCase(messageDataRepository);
    }

}
