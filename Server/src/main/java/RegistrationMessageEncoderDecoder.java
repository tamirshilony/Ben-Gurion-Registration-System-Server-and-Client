

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

public class RegistrationMessageEncoderDecoder implements MessageEncoderDecoder<Message>{
    MessageFactory messageFactory = new MessageFactory();
    ByteBuffer byteBuffer = ByteBuffer.allocate(2);
    ByteArrayOutputStream stringBuffer = new ByteArrayOutputStream(4);
    OpcodeType type = null;
    Message decodedMsg = null;


    @Override
    public Message decodeNextByte(byte nextByte) {
        //if opcode is null
        if(type == null){
            //put next byte in buffer
            byteBuffer.put(nextByte);
            //if buffer not full return null
            if(byteBuffer.hasRemaining())
                return null;
            else { // buffer full
                //resolve opcode and update field
                byte[]opcodeByte = byteBuffer.array();
                short opcode = bytesToShort(opcodeByte);
                type = OpcodeType.values()[opcode];
                //clear buffer
                byteBuffer.clear();
                return null;
            }
        }
        switch (type){
            case MYCOURSES: case LOGOUT: case STUDENTSTAT:
                return messageFactory.createMessage(type);
            //case(all types of permission messages)
            case LOGIN: case ADMINREG: case STUDENTREG:
                return permissionMessageDecoder(nextByte);
            //case(al types of course messages)
            case COURSEREG: case COURSESTAT: case KDAMCHECK: case UNREGISTER: case ISREGISTERED:
                return courseMessageDecoder(nextByte);
        }


        return null;
    }
    private Message permissionMessageDecoder(byte nextByte) {
        //if we didn't start decode the message
        if(decodedMsg == null) {
            //init permission msg
            decodedMsg = messageFactory.createMessage(type, "", "");
        }
        //if next byte still part of the message protocol
        if(nextByte != 0) {
            //put byte in stringbuffer
            stringBuffer.write(nextByte);
        }
        else {
            //put 0 byte in buffer to know we end a parameter
            byteBuffer.put((byte) 0);
            //check witch parameter it is
            if (!byteBuffer.hasRemaining()) {
                // create the parameter and update
                String pasword = new String(stringBuffer.toByteArray(), StandardCharsets.UTF_8);
                decodedMsg.setPassword(pasword);
                byteBuffer.clear();
                return decodedMsg;
            }
            // create the parameter and update
            String username = new String(stringBuffer.toByteArray(),StandardCharsets.UTF_8);
            decodedMsg.setUserName(username);
        }
        return decodedMsg;
    }
    private Message courseMessageDecoder(byte nextByte) {
        //if we didn't start decode the message
        if(decodedMsg == null) {
            //init course msg
            decodedMsg = messageFactory.createMessage(type,-1);
        }
//        else put next byte in buffer
        byteBuffer.put(nextByte);
        if (!byteBuffer.hasRemaining()){
            // resolve course number put in message and return message
            int courseNum = bytesToShort(byteBuffer.array());
            decodedMsg.setCourseNum(courseNum);
            byteBuffer.clear();
        }
        return decodedMsg;
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

    private byte[] encodeAck(Ack ack){return  null;}
    //transform source and optional to bytes

    private byte[] encodeErr(Error err){return null;}
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
