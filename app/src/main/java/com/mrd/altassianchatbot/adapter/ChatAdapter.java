package com.mrd.altassianchatbot.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhargavms.dotloader.DotLoader;
import com.mrd.altassianchatbot.R;
import com.mrd.altassianchatbot.model.ChatMessage;

import java.util.ArrayList;


/**
 * Created by mayurdube on 28/03/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatMessageVH> {

    private final OnLongClickListener listener;
    private ArrayList<ChatMessage> lstChatMessages;

    public ChatAdapter(ArrayList<ChatMessage> messages, OnLongClickListener listener) {
        this.lstChatMessages = messages;
        this.listener = listener;
    }

    @Override
    public ChatMessageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat, parent, false);
        ChatMessageVH holder = new ChatMessageVH(view);
        return holder;
    }

    public void updateMessage(int position,ChatMessage message) {
        lstChatMessages.remove(position);
        lstChatMessages.add(position,message);
        notifyDataSetChanged();
    }
    public void add(ChatMessage object) {
        lstChatMessages.add(object);
    }

    @Override
    public void onBindViewHolder(ChatMessageVH holder, int position) {
        ChatMessage chatMessage = lstChatMessages.get(position);
        if (chatMessage.isMine) {
            holder.vwBubble.setBackgroundResource(R.drawable.bubble2);
            holder.vwParent.setGravity(Gravity.RIGHT);
        } else {
            // If not mine then align to left
            holder.vwBubble.setBackgroundResource(R.drawable.bubble1);
            holder.vwParent.setGravity(Gravity.LEFT);
        }

        if (chatMessage.isReplying) {
            holder.pgLoader.setVisibility(View.VISIBLE);
            holder.txtMsg.setVisibility(View.GONE);
        } else {
            holder.txtMsg.setVisibility(View.VISIBLE);
            holder.txtMsg.setText(chatMessage.body);
            holder.pgLoader.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lstChatMessages.size();
    }

    public class ChatMessageVH extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView txtMsg;
        private LinearLayout vwParent;
        private LinearLayout vwBubble;
        private DotLoader pgLoader;

        public ChatMessageVH(View itemView) {
            super(itemView);
            txtMsg = (TextView) itemView.findViewById(R.id.message_text);
            vwBubble = (LinearLayout) itemView.findViewById(R.id.bubble_layout);
            vwParent = (LinearLayout) itemView.findViewById(R.id.bubble_layout_parent);
            pgLoader = (DotLoader) itemView.findViewById(R.id.pg_dot_loader);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            if (listener != null) {
                listener.OnLongClick(getAdapterPosition(), lstChatMessages.get(getAdapterPosition()));
            }
            return false;
        }
    }
}
