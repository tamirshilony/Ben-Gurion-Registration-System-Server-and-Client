public class BGURSProtocol implements MessagingProtocol {
    private boolean shouldTerminate = false;
    private PermissionController permissionController;
    private RegistrationController registrationController;

    @Override
    public Object process(Object msg) {
        //pass msg to controller and get message
        //if msg ok
            //get username
            //pass msg to regController with username
        return msg;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
