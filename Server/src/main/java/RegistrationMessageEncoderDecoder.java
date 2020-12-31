

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

public class RegistrationMessageEncoderDecoder implements MessageEncoderDecoder<Message>{
    MessageFactory messageFactory = new MessageFactory();
    ByteBuffer byteBuffer = ByteBuffer.allocate(2);
    ByteArrayOutputStream stringBuffer = new ByteArrayOutputStream(4);
    OpcodeType type = null;
    PermissionMessage decodedPermissionMsg = null;
    CourseMessage decodedCourseMsg = null;

    @Override
    public Message decodeNextByte(byte nextByte) {
        //if opcode is null
        if(type == null){
            decodedPermissionMsg = null;
            decodedCourseMsg = null;
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
        if(decodedPermissionMsg == null) {
            //init permission msg
            decodedPermissionMsg = messageFactory.createMessage(type, "", "");
        }
        //if next byte still part of the message protocol
        if(nextByte != 0) {
            //put byte in stringbuffer
            stringBuffer.write(nextByte);
        }
        else {
            //put 0 byteif(decodedMsg == null) { in buffer to know we end a parameter
            byteBuffer.put((byte) 0);
            //check witch parameter it is
            if (!byteBuffer.hasRemaining()) {
                // create the parameter and update
                String pasword = new String(stringBuffer.toByteArray(), StandardCharsets.UTF_8);
                decodedPermissionMsg.setPassword(pasword);
                byteBuffer.clear();
                return decodedPermissionMsg;
            }
            // create the parameter and update
            String username = new String(stringBuffer.toByteArray(),StandardCharsets.UTF_8);
            decodedPermissionMsg.setUserName(username);
        }
        return null;
    }
    private Message courseMessageDecoder(byte nextByte) {
        //if we didn't start decode the message
        if(decodedCourseMsg == null) {
            //init course msg
            decodedCourseMsg = messageFactory.createMessage(type,-1);
        }
//        else put next byte in buffer
        byteBuffer.put(nextByte);
        if (!byteBuffer.hasRemaining()){
            // resolve course number put in message and return message
            int courseNum = bytesToShort(byteBuffer.array());
            decodedCourseMsg.setCourseNum(courseNum);
            byteBuffer.clear();
            return decodedCourseMsg;
        }
        return null;
    }


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
