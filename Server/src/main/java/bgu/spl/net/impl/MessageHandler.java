package bgu.spl.net.impl;

import bgu.spl.net.impl.Messages.*;

public abstract class MessageHandler {
    private Database db = Database.getInstance();
    private MessageFactory messageFactory = new MessageFactory();

    public abstract Message handleMessage(Message msg);
}
