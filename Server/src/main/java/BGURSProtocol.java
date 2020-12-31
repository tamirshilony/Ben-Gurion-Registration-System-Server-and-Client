public class BGURSProtocol implements MessagingProtocol<Message> {
    private boolean shouldTerminate = false;
    private PermissionMessageHandler permissionMessageHandler;
    private RegistrationMessageHandler registrationMessageHandler;

    public BGURSProtocol(){
        permissionMessageHandler = new PermissionMessageHandler();
        registrationMessageHandler = new RegistrationMessageHandler();
    }

    @Override
    public Message process(Message msg) {
        //pass msg to permission handler and get message
        Message permissionMsg = permissionMessageHandler.handleMessage(msg);
        //if msg ok
        if(permissionMsg.getType()!=OpcodeType.ACK & permissionMsg.getType()!=OpcodeType.ERR){
            //pass msg to registration handler
            Message response = registrationMessageHandler.handleMessage(permissionMsg);
            return response;
        }
        return permissionMsg;
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
