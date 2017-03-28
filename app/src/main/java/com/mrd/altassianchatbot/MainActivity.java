package com.mrd.altassianchatbot;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrd.altassianchatbot.adapter.ChatAdapter;
import com.mrd.altassianchatbot.adapter.OnLongClickListener;
import com.mrd.altassianchatbot.controller.ChatController;
import com.mrd.altassianchatbot.model.ChatMessage;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
        , Handler.Callback, OnLongClickListener {

    RecyclerView rcMessages;
    ArrayList<ChatMessage> chatMessages;
    EditText edUserInput;
    ImageButton btnSendMessage;

    ChatAdapter chatAdapter;


    Handler mHandler;
    ChatController chatController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        mHandler = new Handler(this);
        chatController = ChatController.getInstance(getApplicationContext());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sendmsg:
                String message = edUserInput.getEditableText().toString();
                if (!TextUtils.isEmpty(message)) {
                    sendMessage(message, true,false);
                    sendMessage(message, false,true);
                    chatController.handleMessage(ChatController.MESSAGE_PARSE, message);
                }
                break;
        }
    }

    private void sendMessage(String message, boolean mine,boolean isReplying) {
        final ChatMessage chatMessage = new ChatMessage(message, mine);
        chatMessage.setMsgID();
        chatMessage.body = message;
        chatMessage.isReplying = isReplying;
        edUserInput.setText("");
        chatAdapter.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
        rcMessages.scrollToPosition(chatAdapter.getItemCount()-1);
    }

    private void initUI() {
        rcMessages = (RecyclerView) findViewById(R.id.rc_msglist);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setSmoothScrollbarEnabled(false);
        rcMessages.setLayoutManager(manager);

        edUserInput = (EditText) findViewById(R.id.ed_msg);
        edUserInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String message = edUserInput.getEditableText().toString();
                if (!TextUtils.isEmpty(message)) {
                    sendMessage(message, true,false);
                    sendMessage(message, false,true);
                    chatController.handleMessage(ChatController.MESSAGE_PARSE, message);
                    return true;
                } else {
                    return false;
                }
            }
        });
        btnSendMessage = (ImageButton) findViewById(R.id.btn_sendmsg);
        btnSendMessage.setOnClickListener(this);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages, this);
        rcMessages.setAdapter(chatAdapter);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case ChatController.MESSAGE_PARSE:
                String json = (String) message.obj;
                if (json != null) {
                    ChatMessage chatMessage = chatMessages.get(chatMessages.size()-1);
                    chatMessage.body = json;
                    chatMessage.isReplying = false;
                    chatAdapter.updateMessage(chatAdapter.getItemCount()-1,chatMessage);
                    rcMessages.scrollToPosition(chatAdapter.getItemCount()-1);
                }
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatController.addOutboxHandler(mHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatController.removeOutboxHandler(mHandler);
    }

    @Override
    public void OnLongClick(int position, Object message) {
        if (message != null) {
            ChatMessage chatMessage = (ChatMessage) message;
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, chatMessage.body);
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share JSON using"));
        }
    }
}
