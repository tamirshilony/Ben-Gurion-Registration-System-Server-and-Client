public abstract class  Message {
    protected OpcodeType type;
    private String userName;

    public Message(OpcodeType type_){
        type = type_;
    }
    public Message(OpcodeType type_, String userName_){
        type = type_;
        userName =userName_;
    }
    public OpcodeType getType() {
        return type;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
