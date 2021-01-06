package bgu.spl.net.impl;

import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;


public class Course {
    private final int courseNum;
    private final String courseName;
    private final Vector<Integer> kdams;
    private final int limit;
    private CopyOnWriteArrayList<User> registerUsers;
    private int seatsAvailable;

    public Course(int courseNum_, String courseName_,
                  Vector<Integer> kdams_, int limit_){
        courseNum = courseNum_;
        courseName = courseName_;
        kdams = kdams_;
        limit = limit_;
        registerUsers = new CopyOnWriteArrayList<User>();
        seatsAvailable = limit_;
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

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public boolean registerUser(User User){
        synchronized (registerUsers) {
            if(seatsAvailable == 0)
                return false;
            registerUsers.add(User);
            seatsAvailable--;
        }
        return true;
    }
    public boolean unregister(User User){
        synchronized (registerUsers){
            boolean succeed = registerUsers.remove(User);
            if(succeed)
                seatsAvailable++;
            return succeed;
        }
    }

    public CopyOnWriteArrayList<User> getRegisterUsers() {
        return registerUsers;
    }

}
