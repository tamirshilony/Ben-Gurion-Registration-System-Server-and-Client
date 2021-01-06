package bgu.spl.net.impl;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.Messages.*;

public class BGURSProtocol implements MessagingProtocol<Message> {
    private boolean shouldTerminate = false;
    private PermissionMessageHandler permissionMessageHandler;
    private RegistrationMessageHandler registrationMessageHandler;

    public BGURSProtocol(){
        permissionMessageHandler = new PermissionMessageHandler();
        registrationMessageHandler = new RegistrationMessageHandler();
    }

    @Override
    public Message process(Message msg) {
        //pass msg to permission handler and get message
        Message permittedMsg = permissionMessageHandler.handleMessage(msg);
        //if msg ok
        if(permittedMsg.getType() != OpcodeType.ACK && permittedMsg.getType() != OpcodeType.ERR){
            //pass msg to registration handler
            Message response = registrationMessageHandler.handleMessage(permittedMsg);
            return response;
        }
//        if(permittedMsg.getType() == OpcodeType.ACK && msg.getType() == OpcodeType.LOGOUT)
//            shouldTerminate = true;
        return permittedMsg;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public void setShouldTerminate(boolean shouldTerminate) {
        this.shouldTerminate = shouldTerminate;
    }
}
