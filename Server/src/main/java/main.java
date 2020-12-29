import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class main {
    public static void main(String[] args) {
       Database db = Database.getInstance();
       db.initialize("/home/spl211/stud/SPL/ass3/SPL3/Server/src/main/java/stam");
       ConcurrentHashMap<Integer,Course> t= db.getCourseID2Course();
       User tamir = new User("shilonyt","123");
       Course c1 = t.get(333);
       c1.registerUser(tamir);
       c1.getRegisterUsers();

    }
}
