public class UserMessage extends Message{
    private String userName;
    public UserMessage(OpcodeType type,String userName){
        super(type);
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
}
