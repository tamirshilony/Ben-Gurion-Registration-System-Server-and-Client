public class MessageFactory {
    public MessageFactory(){

    }
    public Message createMessage(OpcodeType type){
        return new OpCodeMessage(type);
    }

    public Message createMessage(OpcodeType type,String useName,String password){
        return new PermissionMessage(type,useName,password);
    }

    public Message createMessage(OpcodeType type,int courseNum){
        return new CourseMessage(type,courseNum);
    }

    public Message createMessage(OpcodeType type, OpcodeType sourceType){
        if (type == OpcodeType.ERR){
            return new Error(sourceType);
        }
        else
            return new Ack(sourceType);
    }
    public Message createMessage(OpcodeType sourceOp,String response){
        return new Ack(sourceOp,response);
    }

}
