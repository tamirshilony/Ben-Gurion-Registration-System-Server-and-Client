package bgu.spl.net.impl;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.Messages.*;

public abstract class MessageHandler {
    protected Database db;
    protected MessageFactory messageFactory;

    public MessageHandler(){
        db = Database.getInstance();
        messageFactory = new MessageFactory();
    }

    public abstract Message handleMessage(Message msg);
}
