import java.nio.ByteBuffer;

public class RegistrationMessageEncoderDecoder implements MessageEncoderDecoder<Message>{
    MessageFactory messageFactory = new MessageFactory();
    ByteBuffer byteBuffer = ByteBuffer.allocate(2);
    ByteBuffer stringBuffer = ByteBuffer.allocate(4);
    OpcodeType type = null;
    Message decodedMsg = null;


    @Override
    public Message decodeNextByte(byte nextByte) {
        //if opcode is null
            //put next byte in buffer
            //if buffer not full return null
            //if full
                //resolve opcode and update field
                //clear buffer
                //return null
        //switch(type)
//        case opcode: type decodedmsg is new opcode msg
        //case(all types of permission messages)
//        permissionMessageDecoder(nextByte)
//      case(al types of course messages)
//        courseMessageDecoder(nextByte)

        return null;
    }
    public Message permissionMessageDecoder(byte nextByte) {
        //if decMsg == nul
        //init permission msg
        //else
            //if (!nextByte == 0) {
                //put byte in stringbuffer
            //else
                //put 0 byte in buffer
//              if buffer full
//                put string in password
//                clear buffer
//                return msg
//              else
//                put user name in message
        return null;
    }
    public Message courseMessageDecoder(byte nextByte) {
//        if decMsg == nul
//              init course msg
//        else
//            put next byte in buffer
//            if buffer full
//                resolve course number put in message and return message
//                clear buffer
        return null;
    }


    @Override
    public byte[] encode(Message message) {
        return new byte[0];
    }
}
