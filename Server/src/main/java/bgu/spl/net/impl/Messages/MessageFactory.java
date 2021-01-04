package bgu.spl.net.impl.Messages;

public class MessageFactory {
    public MessageFactory(){

    }
    //OpCodeMessage creator
    public OpCodeMessage createMessage(OpcodeType type){
        return new OpCodeMessage(type);
    }

    //PermissionMessage creator
    public PermissionMessage createMessage(OpcodeType type,String useName,String password){
        return new PermissionMessage(type,useName,password);
    }

    //UserNameMessage creator
    public UserNameMessage createMessage(OpcodeType type,String useName){
        return new UserNameMessage(type,useName);
    }
    //CourseMessage creator
    public CourseMessage createMessage(OpcodeType type,int courseNum){
        return new CourseMessage(type,courseNum);
    }

    //response creators
    public ResponseMessage createMessage(OpcodeType type, OpcodeType sourceType){
            return new ResponseMessage(type, sourceType);
    }

    public ResponseMessage createMessage(OpcodeType type, OpcodeType sourceOp,String response){
        return new ResponseMessage(type, sourceOp,response);
    }

}
