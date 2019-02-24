package com.rejoyz.smsreader.smslisting.domain;

import com.rejoyz.smsreader.smslisting.data.Message;

import java.util.List;

import io.reactivex.Observable;

public interface MessageRespository {
    Observable<List<Message>> getMessages();
}
