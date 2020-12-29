public class Error extends Message {
    private OpcodeType sourceMsgType;
    public Error(OpcodeType sourceMsgType){
        super(OpcodeType.ERR);
        this.sourceMsgType = sourceMsgType;
    }
}
