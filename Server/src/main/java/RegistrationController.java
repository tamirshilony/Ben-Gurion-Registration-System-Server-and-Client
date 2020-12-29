import java.util.Vector;

public class RegistrationController {
    private Database db;
    private MessageFactory messageFactory;

    public RegistrationController(){}
    //handle msg(msg)
    public Message handleMessage(Message msg){
        OpcodeType type = msg.getType();
        String optional = "";
        boolean succeed;
        switch (type){
            case COURSEREG:
                return handleRegistration((CourseMessage)msg);
            case KDAMCHECK:
                return checKdams((CourseMessage)msg);
            case UNREGISTER:
                return handleUnregister((CourseMessage)msg);
            case MYCOURSES:
                return studentCourses((OpCodeMessage)msg);
            case STUDENTSTAT:
                return studentStat((UserMessage)msg);
            case COURSESTAT:
                return courseStat((CourseMessage)msg);
            case ISREGISTERED:
                return isRegister((CourseMessage)msg);
        }
        return messageFactory.createMessage(OpcodeType.ERR, OpcodeType.NOTEXIST);
    }

}
