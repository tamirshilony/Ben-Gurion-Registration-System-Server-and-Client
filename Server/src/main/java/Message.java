public abstract class  Message {
    protected OpcodeType type;

    public Message(OpcodeType type_){
        type = type_;
    }
    public OpcodeType getType() {
        return type;
    }
}
