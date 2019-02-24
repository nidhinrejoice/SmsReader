package com.rejoyz.smsreader.smslisting.presentation;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rejoyz.smsreader.R;
import com.rejoyz.smsreader.smslisting.data.Message;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    List<Message> dataList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public void setMessages(List<Message> messages) {
        dataList = messages;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sender)
        TextView tvSender;
        @BindView(R.id.body)
        TextView tvBody;
        @BindView(R.id.time)
        TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Message message) {
            tvSender.setText(message.getSender());
            tvBody.setText(message.getBody());
            tvTime.setText(DateFormat.format("dd-MM-yyyy hh:mm a", message.getDate()));
        }
    }

}