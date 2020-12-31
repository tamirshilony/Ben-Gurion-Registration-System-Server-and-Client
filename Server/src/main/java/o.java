import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class o implements MessageEncoderDecoder<Message>{
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
//        permissionMessageDecodmsger(nextByte)
//      case(al types of course messages)
//        courseMessageDecoder(nextByte)

        return null;
    }
    private Message permissionMessageDecoder(byte nextByte) {
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
    private Message courseMessageDecoder(byte nextByte) {
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
        ResponseMessage msg = (ResponseMessage)message;
        OpcodeType type = msg.getType();
        ByteArrayOutputStream out = new ByteArrayOutputStream(4);
        try {
            out.write(shortToBytes((short)type.ordinal()));
            out.write(shortToBytes((short)msg.getSourceMsgType().ordinal()));
            if(type == OpcodeType.ACK) {
                out.write(msg.getOptionalData().getBytes(StandardCharsets.UTF_8));
                out.write((byte) 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    private static byte[] shortToBytes (short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }

    private short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }
}
