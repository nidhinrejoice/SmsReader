package com.rejoyz.smsreader.smslisting.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Message implements Parcelable {

    String body;
    String sender;
    Date date;
    int type;
    String group;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Message() {
    }

    public Message(String group) {
        this.group = group;
        this.type = 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.body);
        dest.writeString(this.sender);
        dest.writeLong(this.date != null ? this.date.getTime() : -1);
        dest.writeInt(this.type);
        dest.writeString(this.group);
    }

    protected Message(Parcel in) {
        this.body = in.readString();
        this.sender = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.type = in.readInt();
        this.group = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
