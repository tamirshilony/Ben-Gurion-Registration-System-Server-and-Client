public class MessageFactory {
    public MessageFactory(){

    }
    //OpCodeMessage creator
    public Message createMessage(OpcodeType type){
        return new OpCodeMessage(type);
    }

    //PermissionMessage creator
    public Message createMessage(OpcodeType type,String useName,String password){
        return new PermissionMessage(type,useName,password);
    }

    //CourseMessage creator
    public Message createMessage(OpcodeType type,int courseNum){
        return new CourseMessage(type,courseNum);
    }

    public Message createMessage(OpcodeType type,String userName){return new UserMessage(type,userName);
    }

    //Ack/Err creator
    public Message createMessage(OpcodeType type, OpcodeType sourceType){
        if (type == OpcodeType.ERR){
            return new Error(sourceType);
        }
        else
            return new Ack(sourceType);
    }
    //Ack with optional string creator
    public Message createMessage(OpcodeType type, OpcodeType sourceOp,String response){
        //OpcodeType type only for uniformity with acc/err creator
        return new Ack(sourceOp,response);
    }

}
