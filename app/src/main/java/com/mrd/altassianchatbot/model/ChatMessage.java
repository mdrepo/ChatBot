package com.mrd.altassianchatbot.model;

/**
 * Created by mayurdube on 28/03/17.
 */

import java.util.Random;

public class ChatMessage {

    public String body;
    public String Date, Time;
    public String msgid;
    public boolean isMine;

    public ChatMessage(String messageString,boolean isMINE) {
        body = messageString;
        isMine = isMINE;
        msgid = String.format("%02d", new Random().nextInt(100));;
    }

    public void setMsgID() {

        msgid += "-" + String.format("%02d", new Random().nextInt(100));
        ;
    }
}
