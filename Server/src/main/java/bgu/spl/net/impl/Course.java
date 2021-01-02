package bgu.spl.net.impl;

import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;


public class Course {
    private final int courseNum;
    private final String courseName;
    private final Vector<Integer> kdams;
    private final int limit;
    private CopyOnWriteArrayList<User> registerUsers;
    private int numOfRegistered;

    public Course(int courseNum_, String courseName_,
                  Vector<Integer> kdams_, int limit_){
        courseNum = courseNum_;
        courseName = courseName_;
        kdams = kdams_;
        limit = limit_;
        registerUsers = new CopyOnWriteArrayList<User>();
        numOfRegistered = 0;
    }
    //getters

    public int getCourseNum() {
        return courseNum;
    }

    public String getCourseName() {
        return courseName;
    }

    public Vector<Integer> getKdams() {
        return kdams;
    }

    public int getLimit() {
        return limit;
    }

    public int getNumOfRegistered() {
        return numOfRegistered;
    }

    public boolean registerUser(User User){
        if(numOfRegistered == limit)
            return false;
        synchronized (registerUsers) {
            registerUsers.add(User);
            numOfRegistered++;
        }
        return true;
    }
    public boolean unregister(User User){
        synchronized (registerUsers){
            boolean succeed = registerUsers.remove(User);
            if(succeed)
                numOfRegistered--;
            return succeed;
        }
    }

    public CopyOnWriteArrayList<User> getRegisterUsers() {
        return registerUsers;
    }
}
