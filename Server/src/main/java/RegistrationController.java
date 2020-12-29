import javax.jws.soap.SOAPBinding;
import java.util.Vector;

public class RegistrationController {
    private Database db;
    private MessageFactory messageFactory;

    public RegistrationController(){}
    //handle msg(msg)
    public Message handleMessage(Message msg, String userName){
        OpcodeType type = msg.getType();
        String optional = "";
        boolean succeed;
        switch (type){
            case COURSEREG:
                return handleRegistration((CourseMessage)msg,userName);
            case KDAMCHECK:
                return checKdams((CourseMessage)msg);
            case UNREGISTER:
                return handleUnregister((CourseMessage)msg,userName);
            case MYCOURSES:
                return studentCourses((OpCodeMessage)msg, userName);
            case STUDENTSTAT:
                return studentStat((UserMessage)msg);
            case COURSESTAT:
                return courseStat((CourseMessage)msg);
            case ISREGISTERED:
                return isRegister((CourseMessage)msg, userName);
        }
        return messageFactory.createMessage(OpcodeType.ERR, OpcodeType.NOTEXIST);
    }

    private Message handleRegistration(CourseMessage msg,String userName){
        int courseNum = msg.getCourseNum();
        Course course = db.getCourse(courseNum);
        User user = db.getUser(userName);
        if(!(course == null) && course.registerUser(user))
            return messageFactory.createMessage(OpcodeType.ACK,msg.getType());
        return messageFactory.createMessage(OpcodeType.ERR,msg.getType());
    }

    private Message handleUnregister(CourseMessage msg, String userName){
        int courseNum = msg.getCourseNum();
        Course course = db.getCourse(courseNum);
        User user = db.getUser(userName);
        if(!(course == null) && course.unregister(user))
            return messageFactory.createMessage(OpcodeType.ACK,msg.getType());
        return messageFactory.createMessage(OpcodeType.ERR,msg.getType());
    }
    private Message checKdams(CourseMessage msg) {
        int courseNum = msg.getCourseNum();
        Course course = db.getCourse(courseNum);
        if (!(course == null)) {
            Vector<Integer> kdams = course.getKdams();
            return messageFactory.createMessage(OpcodeType.ACK,msg.getType(),kdams.toString());
        }
        return messageFactory.createMessage(OpcodeType.ERR,msg.getType());
    }

    private Message studentCourses(OpCodeMessage msg,String userName){
        User user = db.getUser(userName);
        Vector<Integer> courses = user.getRegisteredCourses();
        return messageFactory.createMessage(OpcodeType.ACK,msg.getType(),courses.toString());
    }
}
