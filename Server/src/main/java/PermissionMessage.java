public class PermissionMessage extends Message{

    private String userName;
    private String password;

    public PermissionMessage(OpcodeType type_,String userName_,String password_) {
        super(type_);
        userName = userName_;
        password = password_;
    }
}
