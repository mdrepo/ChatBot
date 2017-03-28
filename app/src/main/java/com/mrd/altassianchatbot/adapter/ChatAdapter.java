package com.mrd.altassianchatbot.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrd.altassianchatbot.R;
import com.mrd.altassianchatbot.model.ChatMessage;

import java.util.ArrayList;


/**
 * Created by mayurdube on 28/03/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatMessageVH> {

    private ArrayList<ChatMessage> lstChatMessages;

    public ChatAdapter(ArrayList<ChatMessage> messages) {
        this.lstChatMessages = messages;
    }
    @Override
    public ChatMessageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat,parent,false);
        ChatMessageVH holder = new ChatMessageVH(view);
        return holder;
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
        holder.txtMsg.setText(chatMessage.body);
    }

    @Override
    public int getItemCount() {
        return lstChatMessages.size();
    }

    public class ChatMessageVH extends RecyclerView.ViewHolder {

        private TextView txtMsg;
        private LinearLayout vwParent;
        private LinearLayout vwBubble;

        public ChatMessageVH(View itemView) {
            super(itemView);
            txtMsg = (TextView) itemView.findViewById(R.id.message_text);
            vwBubble = (LinearLayout) itemView.findViewById(R.id.bubble_layout);
            vwParent = (LinearLayout) itemView.findViewById(R.id.bubble_layout_parent);
        }
    }
}
