package bgu.spl.net.impl;

import bgu.spl.net.impl.Messages.Message;
import bgu.spl.net.srv.*;


import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class main {
    public static void main(String[] args) {
//       Database db = Database.getInstance();
//       db.initialize("/home/spl211/stud/SPL/ass3/SPL3/Server/src/main/java/stam");
//       ConcurrentHashMap<Integer,Course> t= db.getCourseID2Course();
//       MessageFactory messageFactory = new MessageFactory();
////       test register
//       BGURSProtocol protocol = new BGURSProtocol();
//       Message m1 = messageFactory.createMessage(OpcodeType.ADMINREG,"ofer", "qwe");
//       Message response = protocol.process(m1);
//       User u1 = db.getUser("ofer");
////       test 2 client register with same user
////        BGURSProtocol protocol2 = new BGURSProtocol();
////       Message m2 = messageFactory.createMessage(OpcodeType.STUDENTREG,"ofer", "asd");
////       Message response2 = protocol2.process(m1);
////        test login
////        Message m3 = messageFactory.createMessage(OpcodeType.LOGIN,"ofer", "qwe");
////        Message response3 = protocol.process(m3);
////        test login wrong password
//        Message m4 = messageFactory.createMessage(OpcodeType.LOGIN,"ofer", "wrong password");
//        Message response3 = protocol.process(m4);
        Reactor<Message> server = new Reactor<>(3,7777,
                () -> new BGURSProtocol(),() -> new RegistrationMessageEncoderDecoder());
        server.serve();

    }
}
