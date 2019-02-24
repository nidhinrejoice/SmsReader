package com.rejoyz.smsreader.smslisting.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rejoyz.smsreader.R;
import com.rejoyz.smsreader.smslisting.data.Message;
import com.rejoyz.smsreader.smslisting.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 1;
    List<Message> dataList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_messages_header, parent, false));

            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_messages, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = dataList.get(position);
        if (message.getType() == TYPE_HEADER) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.bind(message);
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.bind(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
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
            tvTime.setText(Utils.getMins(message.getDate()));
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.header)
        TextView tvHeader;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Message message) {
            tvHeader.setText(message.getGroup());
        }
    }

}