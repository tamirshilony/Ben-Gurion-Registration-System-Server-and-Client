package bgu.spl.net.impl;

import bgu.spl.net.impl.Messages.*;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class RegistrationMessageHandler extends MessageHandler{


    public RegistrationMessageHandler(){super();}

    //handle msg(msg)
    public Message handleMessage(Message msg){
        String userName = msg.getUserName();
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
                return studentStat((UserNameMessage)msg);
            case COURSESTAT:
                return courseStat((CourseMessage)msg);
            case ISREGISTERED:
                return isRegister((CourseMessage)msg, userName);
        }
        return messageFactory.createMessage(OpcodeType.ERR, OpcodeType.NOTEXIST);
    }

    private Message handleRegistration(CourseMessage msg,String userName)
    {
        int courseNum = msg.getCourseNum();
        Course course = db.getCourse(courseNum);
        User user = db.getUser(userName);
        if(course != null && hasAllKdams(user, course) && checkCourseLimit(course) && !checkDoubleRegistration(user,course))
        {
            course.registerUser(user);
            user.register2Course(courseNum);
            return messageFactory.createMessage(OpcodeType.ACK,msg.getType());
        }
        return messageFactory.createMessage(OpcodeType.ERR,msg.getType());
    }

    private Message handleUnregister(CourseMessage msg, String userName)
    {
        int courseNum = msg.getCourseNum();
        Course course = db.getCourse(courseNum);
        User user = db.getUser(userName);
        if(!(course == null) && user.isRegistered(courseNum))
        {
            course.unregister(user);
            user.unregisterCourse(courseNum);
            return messageFactory.createMessage(OpcodeType.ACK,msg.getType());
        }
        return messageFactory.createMessage(OpcodeType.ERR,msg.getType());
    }
    private Message checKdams(CourseMessage msg)
    {
        int courseNum = msg.getCourseNum();
        Course course = db.getCourse(courseNum);
        if (!(course == null))
        {
            Vector<Integer> kdams = course.getKdams();
            return messageFactory.createMessage(OpcodeType.ACK,msg.getType(),kdams.toString());
        }
        return messageFactory.createMessage(OpcodeType.ERR,msg.getType());
    }

    private Message studentCourses(OpCodeMessage msg,String userName)
    {
        User user = db.getUser(userName);
        Vector<Integer> courses = user.getRegisteredCourses();
        return messageFactory.createMessage(OpcodeType.ACK,msg.getType(),courses.toString());
    }

    private Message isRegister(CourseMessage msg, String username)
    {
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
            return messageFactory.createMessage(OpcodeType.ACK,msg.getType(),"REGISTERED");
        else
            return messageFactory.createMessage(OpcodeType.ACK,msg.getType(),"NOT REGISTER");
    }

    private Message courseStat(CourseMessage msg)
    {
        // get course num
        int courseNum = msg.getCourseNum();
        // get course instance
        Course course = db.getCourse(courseNum);
        // get course name
        String courseName = course.getCourseName();
        //string for response
        String courseNum_Name = "Course: (" + courseNum + ") " + courseName;
        // get num of register and limit
        int numOfRegister = course.getNumOfRegistered();
        int limit = course.getLimit();
        //string for response
        String seatsAvailable = "Seats Available: " + numOfRegister + "/" + limit;
        // get user that register to the course
        CopyOnWriteArrayList<User> registerUser = course.getRegisterUsers();
        Vector<String> registerUserNames = new Vector<>();
        for (User user:registerUser) {
            registerUserNames.add(user.getUserUserName());
        }
        // sort name by alfabetic
        Collections.sort(registerUserNames);
        //string for response
        String registerUserName = "Students Registered: " + registerUserNames.toString();
        return messageFactory.createMessage(OpcodeType.ACK,msg.getType(),courseNum_Name + "\n" + seatsAvailable + "\n" + registerUserName);
    }

    private Message studentStat(UserNameMessage msg)
    {
        // get user name
        String name = msg.getUserName();
        //string for response
        String userName = "Student: " + name;
        // get user instance
        User user = db.getUser(name);
        // get the course the user register
        Vector<Integer> registerCourses = user.getRegisteredCourses();
        // sort by txt.file for tests
        registerCourses = sortCoursesForTest(registerCourses);
        //string for response
        String registerCourseNum = "Courses: " + registerCourses.toString();
        return  messageFactory.createMessage(OpcodeType.ACK,msg.getType(),userName + "\n" + registerCourseNum);

    }

    private Vector<Integer> sortCoursesForTest(Vector<Integer> registerCourses)
    {
        Vector<Integer> sortedCourses = new Vector<>(registerCourses.size());
        Vector<Integer> allCourses = db.getAllCourses();
        for (Integer courseNum: allCourses)
        {
            if (registerCourses.contains(courseNum))
                sortedCourses.add(courseNum);
        }
        return  sortedCourses;
    }

    private boolean hasAllKdams(User user,Course course)
    {
        Vector<Integer> kdams = course.getKdams();
        boolean ans = true;
        for (int kdam:kdams) {
            if(!user.isRegistered(kdam)) {
                ans = false;
                break;
            }
        }
        return ans;
    }

    private boolean checkCourseLimit(Course course){
        return (course.getLimit() > course.getNumOfRegistered());
    }

    private boolean checkDoubleRegistration(User user,Course course){
        return user.isRegistered(course.getCourseNum());
    }
}

