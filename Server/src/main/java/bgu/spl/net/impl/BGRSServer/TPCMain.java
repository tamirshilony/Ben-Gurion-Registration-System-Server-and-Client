package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.impl.BGURSProtocol;
import bgu.spl.net.impl.Database;
import bgu.spl.net.impl.RegistrationMessageEncoderDecoder;
import bgu.spl.net.srv.Server;

public class TPCMain {
    public static void main(String[] args) {
        Database db = Database.getInstance();
        Server.threadPerClient(Integer.parseInt(args[0]), () -> new BGURSProtocol(),() -> new RegistrationMessageEncoderDecoder()).serve();
    }
}
