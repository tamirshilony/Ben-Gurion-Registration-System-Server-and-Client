package bgu.spl.net.impl.Messages;
public class ResponseMessage extends Message{
    private OpcodeType sourceMsgType;
    private String optionalData = "";

    // 2 constructors
    public ResponseMessage(OpcodeType type ,OpcodeType sourceMsgType){
        super(type);
        this.sourceMsgType = sourceMsgType;
    }
    public ResponseMessage(OpcodeType type ,OpcodeType sourceMsgType, String optionalData) {
        super(type);
        this.sourceMsgType = sourceMsgType;
        this.optionalData = optionalData;
    }

    public OpcodeType getSourceMsgType() {
        return sourceMsgType;
    }

    public String getOptionalData() {
        return optionalData;
    }

}
