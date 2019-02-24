package com.rejoyz.smsreader.smslisting.domain;

import com.rejoyz.smsreader.smslisting.data.Message;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetMessagesUseCase extends UseCase<List<Message>, Void> {

    MessageRespository mMessageRespository;

    public GetMessagesUseCase(MessageRespository messageRespository) {
        mMessageRespository = messageRespository;
    }

    @Override
    Observable<List<Message>> buildUseCaseObservable(Void aVoid) {
        return mMessageRespository.getMessages().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
