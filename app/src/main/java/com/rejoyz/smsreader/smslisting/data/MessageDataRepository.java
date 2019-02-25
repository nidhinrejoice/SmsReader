package com.rejoyz.smsreader.smslisting.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.rejoyz.smsreader.smslisting.domain.MessageRespository;
import com.rejoyz.smsreader.smslisting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
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
        List<Message> lastHourMessages = new ArrayList<Message>();
        List<Message> oneHourMessages = new ArrayList<Message>();
        List<Message> twoHourMessages = new ArrayList<Message>();
        List<Message> threeHourMessages = new ArrayList<Message>();
        List<Message> sixHourMessages = new ArrayList<Message>();
        List<Message> twelveHourMessages = new ArrayList<Message>();
        List<Message> othersMessages = new ArrayList<Message>();

        Uri uriSms = Uri.parse("content://sms/inbox");

        String filter = "date>=" + (Calendar.getInstance().getTime().getTime() - 86400000);//filtering messages beyond 24 hours
        Cursor cursor = mContext.getContentResolver()
                .query(uriSms,
                        new String[]{"_id", "address", "date", "body",
                                "type", "read"}, "type=" + type + " and " + filter, null,
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

                    if (Utils.getHoursAgo(message.getDate()) == 0) {
                        if (lastHourMessages.isEmpty())
                            lastHourMessages.add(new Message("Last hour"));
                        lastHourMessages.add(message);
                    } else if (Utils.getHoursAgo(message.getDate()) == 1) {
                        if (oneHourMessages.isEmpty())
                            oneHourMessages.add(new Message("One hour ago"));
                        oneHourMessages.add(message);
                    } else if (Utils.getHoursAgo(message.getDate()) == 2) {
                        if (twoHourMessages.isEmpty())
                            twoHourMessages.add(new Message("2 hours ago"));
                        twoHourMessages.add(message);
                    } else if (Utils.getHoursAgo(message.getDate()) == 3) {
                        if (threeHourMessages.isEmpty())
                            threeHourMessages.add(new Message("3 hours ago"));
                        threeHourMessages.add(message);
                    } else if (Utils.getHoursAgo(message.getDate()) <= 6) {
                        if (sixHourMessages.isEmpty())
                            sixHourMessages.add(new Message("6 hours ago"));
                        sixHourMessages.add(message);
                    } else if (Utils.getHoursAgo(message.getDate()) <= 12) {
                        if (twelveHourMessages.isEmpty())
                            twelveHourMessages.add(new Message("12 hours ago"));
                        twelveHourMessages.add(message);
                    } else {
                        if (othersMessages.isEmpty())
                            othersMessages.add(new Message("24 hours ago"));
                        othersMessages.add(message);
                    }

                } while (cursor.moveToPrevious());
            }
        }
        smsInbox.addAll(lastHourMessages);
        smsInbox.addAll(oneHourMessages);
        smsInbox.addAll(twoHourMessages);
        smsInbox.addAll(threeHourMessages);
        smsInbox.addAll(sixHourMessages);
        smsInbox.addAll(twelveHourMessages);
        smsInbox.addAll(othersMessages);
        return smsInbox;

    }
}
