public class Ack extends Message{
    private OpcodeType sourceMsgType;
    private String optionalData = null;

    // 2 constructors
    public Ack(OpcodeType sourceMsgType){
        super(OpcodeType.ACK);
        this.sourceMsgType = sourceMsgType;
    }
    public Ack(OpcodeType sourceMsgType, String optionalData) {
        super(OpcodeType.ACK);
        this.sourceMsgType = sourceMsgType;
        this.optionalData = optionalData;
    }
}
