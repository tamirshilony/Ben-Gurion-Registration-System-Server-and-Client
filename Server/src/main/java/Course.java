import java.util.Vector;

public class Course {
    private final int courseNum;
    private final String courseName;
    private final Vector<Integer> kdams;
    private final int limit;
    private int numOfRegistered;

    public Course(int courseNum_, String courseName_,
                  Vector<Integer> kdams_, int limit_){
        courseNum = courseNum_;
        courseName = courseName_;
        kdams = kdams_;
        limit = limit_;
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

    public boolean registerStudent(){
        boolean ans = true;
        if(numOfRegistered == limit)
            return false;
        synchronized (this) {
            numOfRegistered++;
        }
        return ans;
    }
    public synchronized void unregister(){
        numOfRegistered--;
    }
}
