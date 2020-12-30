public abstract class MessageHandler {
    private Database db = Database.getInstance();
    private MessageFactory messageFactory = new MessageFactory();

    public abstract Message handleMessage(Message msg);
}
