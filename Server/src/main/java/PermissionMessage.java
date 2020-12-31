public class PermissionMessage extends Message{


    private String password;

    public PermissionMessage(OpcodeType type_,String userName_,String password_) {
        super(type_,userName_);
        password = password_;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
