import java.util.Vector;

public class PermissionMessageHandler extends MessageHandler {
    private Database db = Database.getInstance();
    private MessageFactory messageFactory = new MessageFactory();
    private boolean isRegistered = false;
    private String userName;
    private boolean isAdmin = false;
    private boolean isLoggedin = false;

    public PermissionMessageHandler(){
    }

    private Message registerUser(PermissionMessage msg){
        OpcodeType type = msg.getType();
        // check register condition and update field
        if(db.register(msg.getUserName(),msg.getPassword())== null){
            isRegistered = true;
            userName = msg.getUserName();
            //checking if admin request and update field
            if(type == OpcodeType.ADMINREG)
                isAdmin =true;
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
        if(user != null && msg.getPassword() == user.getUserPassword()){
            //update field
            isLoggedin = true;
            return messageFactory.createMessage(type,type);
        }
        else {
            return  messageFactory.createMessage(OpcodeType.ERR,type);
        }
    }

    public Message handleMessage(Message msg){
        OpcodeType type = msg.getType();
        if(!isRegistered){
            if(type == OpcodeType.STUDENTREG || type == OpcodeType.ADMINREG)
                return registerUser((PermissionMessage)msg);
            else
                return messageFactory.createMessage(OpcodeType.ERR,type);
        }
        else if (!isLoggedin) {
            if (type == OpcodeType.LOGIN)
                return login((PermissionMessage) msg);
            else
                return messageFactory.createMessage(OpcodeType.ERR, type);
        }
        else if (type == OpcodeType.LOGOUT) {
            isLoggedin = false;
            return messageFactory.createMessage(OpcodeType.ACK, type);
        }
        // check admin credentials
        else if(!isAdmin & (type == OpcodeType.COURSESTAT || type == OpcodeType.STUDENTSTAT)
                                                    || (isAdmin & type == OpcodeType.COURSEREG))
            return messageFactory.createMessage(OpcodeType.ERR,type);
        else
            msg.setUserName(userName);
            return msg;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isLoggedin() {
        return isLoggedin;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
    public String getUserName() {
        return userName;
    }
}
