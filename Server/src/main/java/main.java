import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class main {
    public static void main(String[] args) {
//       File f = new File("stam");
       Database db = Database.getInstance();
       db.initialize("/home/ofer/Desktop/Studies/SPL/SPL3/Server//src/main/java/stam");
       ConcurrentHashMap<Integer,Course> t= db.getCourseID2Course();
    }
}
