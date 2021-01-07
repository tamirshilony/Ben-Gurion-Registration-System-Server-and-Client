package bgu.spl.net.impl;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.Messages.*;

public class PermissionMessageHandler extends MessageHandler {
    private String userName = null;
    private boolean isAdmin = false;

    public PermissionMessageHandler(){super();}

    private Message registerUser(PermissionMessage msg){
        OpcodeType type = msg.getType();
        // check register condition and update field
        boolean success = db.register(msg.getUserName(),msg.getPassword())== null;
        if(success){
            //checking if admin request and update field
            if(type == OpcodeType.ADMINREG)
                db.addAdmin(msg.getUserName());
            return messageFactory.createMessage(OpcodeType.ACK,type);
        }
        else {
            return messageFactory.createMessage(OpcodeType.ERR,type);
        }
    }


    private Message login(PermissionMessage msg){
        OpcodeType type = msg.getType();
        // get user from dataBase if exist
        User user =db.getUser(msg.getUserName());
        // check valid login condition
        if(user != null && msg.getPassword().equals(user.getUserPassword())){
            //update
            db.getLoggedUsers().add(msg.getUserName());
            userName = msg.getUserName();
            if(db.isAdmin(userName))
                isAdmin = true;
            return messageFactory.createMessage(OpcodeType.ACK,type);
        }
        else {
            return  messageFactory.createMessage(OpcodeType.ERR,type);
        }
    }

    public Message handleMessage(Message msg){
        OpcodeType type = msg.getType();
        if(!db.getLoggedUsers().contains(msg.getUserName())&& userName == null)
        {
            if(type != OpcodeType.LOGIN && !(type == OpcodeType.STUDENTREG || type == OpcodeType.ADMINREG))
                return messageFactory.createMessage(OpcodeType.ERR,type);
            else{
                if(type == OpcodeType.LOGIN)
                    return login((PermissionMessage) msg);
                else
                    return registerUser((PermissionMessage)msg);
            }
        }
        else if (type == OpcodeType.LOGIN)
            return messageFactory.createMessage(OpcodeType.ERR,type);
        else if (type == OpcodeType.LOGOUT) {
            db.getLoggedUsers().remove(userName);
            userName = null;
            return messageFactory.createMessage(OpcodeType.ACK, type);
        }
        else if(type == OpcodeType.STUDENTREG || type == OpcodeType.ADMINREG)
            return messageFactory.createMessage(OpcodeType.ERR,type);
        // check admin credentials
        else if((!isAdmin & (type == OpcodeType.COURSESTAT || type == OpcodeType.STUDENTSTAT)) ||
                (isAdmin & type == OpcodeType.COURSEREG))
            return messageFactory.createMessage(OpcodeType.ERR,type);
        else {
            if (msg.getUserName() == null)
                msg.setUserName(userName);
            return msg;
        }
    }

//    public boolean isAdmin() {
//        return isAdmin;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
}
