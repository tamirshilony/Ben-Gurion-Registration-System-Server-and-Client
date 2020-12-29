import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class t {
    private Database db = Database.getInstance();
    private MessageFactory ms;
    private Message isRegister(CourseMessage msg, String username){
        boolean isRegister;
        // get the requested course num
        int coursNum = msg.getCourseNum();
        // get user instance
        User user = db.getUser(username);
        // get user registered Courses
        Vector<Integer> registeredCourses = user.getRegisteredCourses();
        // check if user register to courseNum
        isRegister = registeredCourses.contains(coursNum);
        if(isRegister)
            return ms.createMessage(OpcodeType.ACK,msg.getType(),"REGISTERED\0");
        else
            return ms.createMessage(OpcodeType.ACK,msg.getType(),"NOT REGISTER\0");
    }

    private Message courseStat(CourseMessage msg){
        // get course num
        int courseNum = msg.getCourseNum();
        // get course instance
        Course course = db.getCourse(courseNum);
        // get course name
        String courseName = course.getCourseName();
        //string for response
        String courseNum_Name = "Course:(" + courseNum + ") " + courseName;
        // get num of register and limit
        int numOfRegister = course.getNumOfRegistered();
        int limit = course.getLimit();
        //string for response
        String seatsAvailable = numOfRegister + "/" + limit;
        // get user that register to the course
        CopyOnWriteArrayList<User> registerUser = course.getRegisterUsers();
        Vector<String> registerUserNames = new Vector<>();
        for (User user:registerUser) {
            registerUserNames.add(user.getUserUserName());
        }
        // sort name by alfabetic
        Collections.sort(registerUserNames);
        //string for response
        String registerUserName = registerUserNames.toString();
        return ms.createMessage(OpcodeType.ACK,msg.getType(),courseNum_Name + "\n" + seatsAvailable + "\n" + registerUserName);
    }

    private Message studentStat(UserMessage msg){
        // get user name
        String name = msg.getUserName();
        //string for response
        String userName = "Student: " + name;
        // get user instance
        User user = db.getUser(name);
        // get the course the user register
        Vector<Integer> registerCourses = user.getRegisteredCourses();
        // sort by txt.file

        //string for response
        String registerCourseNum = registerCourses.toString();
        return  ms.createMessage(OpcodeType.ACK,msg.getType(),userName + "\n" + registerCourseNum);

    }
}
