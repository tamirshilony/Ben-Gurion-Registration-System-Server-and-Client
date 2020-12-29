import java.util.Vector;

public class PermissionController {
    private Database db = Database.getInstance();
    private MessageFactory messageFactory = new MessageFactory();
    private boolean isAdmin = false;
    private boolean isRegistered = false;
    private boolean isLoggedin = false;

    public PermissionController(){
    }

    private Message registerUser(PermissionMessage msg){
        OpcodeType opcodeType = msg.getType();
        // check register condition and update field
        if(db.register(msg.getUserName(),msg.getPassword())!= null){
            isRegistered = true;
            //checking if admin request and update field
            if(opcodeType == OpcodeType.ADMINREG)
                isAdmin =true;
            //ack
            return messageFactory.createMessage(OpcodeType.ACK,opcodeType);
        }
        else {
            // error
            return messageFactory.createMessage(OpcodeType.ERR,opcodeType);
        }
    }


    private Message login(PermissionMessage msg){
        OpcodeType opcodeType = msg.getType();
        // get user from dataBase if exist
        User user =db.getUser(msg.getUserName());
        // check valid login condition
        if(user != null && msg.getPassword() == user.getUserPassword()){
            //update field
            isLoggedin = true;
            //ack
            return messageFactory.createMessage(opcodeType,opcodeType);
        }
        else {
            // error
            return  messageFactory.createMessage(OpcodeType.ERR,opcodeType);
        }

    }

    public Message handleMessage(Message msg){
        //get OpcodeType
        OpcodeType opcodeType = msg.getType();
        //check if not register
        if(!isRegistered){
            if(opcodeType == OpcodeType.STUDENTREG)
                return registerUser((PermissionMessage)msg);
            else
                return messageFactory.createMessage(OpcodeType.ERR,opcodeType);
        }
        //check if not login
        else if (!isLoggedin) {
            if (opcodeType == OpcodeType.LOGIN)
                return login((PermissionMessage) msg);
            else
                //error
                return messageFactory.createMessage(OpcodeType.ERR, opcodeType);
        }
        else if (opcodeType == OpcodeType.LOGOUT) {
            isLoggedin = false;
            return messageFactory.createMessage(OpcodeType.ACK, opcodeType);
        }
        // check admin credentials
        else if(!isAdmin & (opcodeType == OpcodeType.COURSESTAT || opcodeType == OpcodeType.STUDENTSTAT))
            // error
            return messageFactory.createMessage(OpcodeType.ERR,opcodeType);
        else
            return null;
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
}
