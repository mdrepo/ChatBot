package com.mrd.altassianchatbot.controller;


import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.mrd.altassianchatbot.parser.Parser;

import org.json.JSONObject;

/**
 * Created by mayurdube on 28/03/17.
 */

public class ChatController extends Controller {

    public static final int MESSAGE_PARSE = 2001;
    HandlerThread workerThread;
    Handler workerHandler;

    private static ChatController sChatController;
    private Context context;

    public static ChatController getInstance(Context context) {
        if(sChatController == null) {
            sChatController = new ChatController(context);
        }
        return sChatController;
    }

    public ChatController(Context context) {
        this.context = context;
        workerThread = new HandlerThread("ChatControllerThread");
        workerThread.start();
        workerHandler = new Handler(workerThread.getLooper());
    }
    @Override
    public boolean handleMessage(int what, Object data) {
        switch (what) {
            case MESSAGE_PARSE:
                parse((String)data);
                break;
        }
        return false;
    }

    private void parse(final String message) {
        workerHandler.post(new Runnable() {
            @Override
            public void run() {
                String response = Parser.stemsByRule(message);
                notifyOutboxHandlers(MESSAGE_PARSE,0,0,response);
            }
        });
    }
}
