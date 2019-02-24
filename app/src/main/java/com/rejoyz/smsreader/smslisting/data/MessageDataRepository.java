package com.rejoyz.smsreader.smslisting.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.rejoyz.smsreader.smslisting.domain.MessageRespository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;

public class MessageDataRepository implements MessageRespository {

    private Context mContext;

    public MessageDataRepository(Context context) {

        mContext = context;
    }

    @Override
    public Observable<List<Message>> getMessages() {
        return Observable.just(fetchInboxSms(1));
    }

    public List<Message> fetchInboxSms(int type) {
        List<Message> smsInbox = new ArrayList<Message>();

        Uri uriSms = Uri.parse("content://sms/inbox");

        Cursor cursor = mContext.getContentResolver()
                .query(uriSms,
                        new String[]{"_id", "address", "date", "body",
                                "type", "read"}, "type=" + type, null,
                        "date" + " COLLATE LOCALIZED ASC");
        if (cursor != null) {
            cursor.moveToLast();
            if (cursor.getCount() > 0) {

                do {

                    Message message = new Message();
                    message.setSender(cursor.getString(cursor
                            .getColumnIndex("address")));
                    message.setBody(cursor.getString(cursor
                            .getColumnIndex("body")));
                    message.setDate(new Date(cursor.getLong(cursor
                            .getColumnIndex("date"))));
                    smsInbox.add(message);
                } while (cursor.moveToPrevious());
            }
        }

        return smsInbox;

    }
}
