package com.rejoyz.smsreader.smslisting.presentation;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.rejoyz.smsreader.smslisting.data.Message;

import java.util.List;

public class MessagesDiffUtilsCallback extends DiffUtil.Callback {
    private final List<Message> oldList;
    private final List<Message> newList;

    public MessagesDiffUtilsCallback(List<Message> oldList, List<Message> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldList.get(oldItemPosition).getType() == newList.get(
                newItemPosition).getType());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Message oldMessage = oldList.get(oldItemPosition);
        final Message newMessage = newList.get(newItemPosition);
        if (oldMessage.getType() == newMessage.getType()) {
            if (oldMessage.getType() == 0) {
                return oldMessage.getBody().equals(newMessage.getBody());
            } else {
                return oldMessage.getGroup().equals(newMessage.getGroup());
            }
        }
        return false;
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
