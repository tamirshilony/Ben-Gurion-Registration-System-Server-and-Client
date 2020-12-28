import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class Course {
    private final int courseNum;
    private final String courseName;
    private final Vector<Integer> kdams;
    private final int limit;
    private CopyOnWriteArrayList<Student> registerStudents;
    private int numOfRegistered;

    public Course(int courseNum_, String courseName_,
                  Vector<Integer> kdams_, int limit_){
        courseNum = courseNum_;
        courseName = courseName_;
        kdams = kdams_;
        limit = limit_;
        registerStudents = new CopyOnWriteArrayList<Student>();
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

    public boolean registerStudent(Student student){
        boolean ans = true;
        if(numOfRegistered == limit)
            return false;
        synchronized (registerStudents) {
            registerStudents.add(student);
            numOfRegistered++;
        }
        return ans;
    }
    public synchronized void unregister(Student student){
        synchronized (registerStudents){
            registerStudents.remove(student);
            numOfRegistered--;
        }
    }

    public CopyOnWriteArrayList<Student> getRegisterStudents() {
        return registerStudents;
    }
}
