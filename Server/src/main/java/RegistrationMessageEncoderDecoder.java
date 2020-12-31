import sun.security.util.ArrayUtil;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Vector;

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
        OpcodeType type = message.getType();
        byte[] opByte = shortToBytes((short)type.ordinal());
        byte[] sourceOpcode = new byte[0];
        switch (type){
            case ACK:
                sourceOpcode = encodeAck((Ack)message);
            case ERR:
                sourceOpcode = encodeErr((Error)message);
        }
        byte[] encodedResponse = new byte[opByte.length+sourceOpcode.length];
        System.arraycopy(opByte, 0, encodedResponse,0, opByte.length);
        System.arraycopy(sourceOpcode, 0, encodedResponse,opByte.length, sourceOpcode.length);

        return encodedResponse;
    }

    private byte[] encodeAck(Ack ack){}
    //transform source and optional to bytes
    private byte[] encodeErr(Error err){}
    //transform source bytes

    private static byte[] shortToBytes ( short num)
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
