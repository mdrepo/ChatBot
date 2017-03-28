package com.mrd.altassianchatbot;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mrd.altassianchatbot.adapter.ChatAdapter;
import com.mrd.altassianchatbot.controller.ChatController;
import com.mrd.altassianchatbot.model.ChatMessage;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Handler.Callback {

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
                if(!TextUtils.isEmpty(message)) {
                    sendMessage(message,true);
                    chatController.handleMessage(ChatController.MESSAGE_PARSE,message);
                }
                break;
        }
    }

    private void sendMessage(String message, boolean mine) {
        final ChatMessage chatMessage = new ChatMessage(message, mine);
        chatMessage.setMsgID();
        chatMessage.body = message;
        edUserInput.setText("");
        chatAdapter.add(chatMessage);
        chatAdapter.notifyDataSetChanged();
        rcMessages.scrollToPosition(chatAdapter.getItemCount()-1);
    }

    private void initUI() {
        rcMessages = (RecyclerView) findViewById(R.id.rc_msglist);
        rcMessages.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        edUserInput = (EditText) findViewById(R.id.ed_msg);
        btnSendMessage = (ImageButton) findViewById(R.id.btn_sendmsg);
        btnSendMessage.setOnClickListener(this);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        rcMessages.setAdapter(chatAdapter);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case ChatController.MESSAGE_PARSE:
                String json = (String) message.obj;
                if(json != null) {
                    sendMessage(json,false);
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
}
