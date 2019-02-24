package com.rejoyz.smsreader.smslisting.presentation;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.rejoyz.smsreader.smslisting.data.Message;
import com.rejoyz.smsreader.smslisting.domain.GetMessagesUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class MessageListingViewModel extends ViewModel {

    private GetMessagesUseCase mGetMessagesUseCase;

    MutableLiveData<List<Message>> messagesLiveData;

    @Inject
    public MessageListingViewModel(GetMessagesUseCase getMessagesUseCase) {
        mGetMessagesUseCase = getMessagesUseCase;
        messagesLiveData=new MutableLiveData<>();

    }

    public MutableLiveData<List<Message>> observeOnMessages(){
        return messagesLiveData;
    }

    public void loadMessages(){
        mGetMessagesUseCase.execute(new DisposableObserver<List<Message>>() {
            @Override
            public void onNext(List<Message> value) {
                messagesLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },null);
    }
}
