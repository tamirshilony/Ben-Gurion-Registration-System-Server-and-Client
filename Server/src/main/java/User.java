import java.util.Vector;

public class User {
    private String userName;
    private String password;
    private Vector<Integer> registeredCourses;

    public User (String userName_, String password_){
        userName = userName_;
        password = password_;
        registeredCourses = new Vector<>();
    }

    //getters
    public String getUserUserName(){
        return userName;
    }
    public String getUserPassword(){
        return password;
    }
    public Vector<Integer> getRegisteredCourses(){
        return registeredCourses;
    }

    //boolean register(int)
    public void register2Course(int courseID){
        if(!registeredCourses.contains(courseID)){
            registeredCourses.add(courseID);
        }
    }

    //boolean unRegister
    public void unregisterCourse(int courseID){
        if(registeredCourses.contains(courseID)) {
            registeredCourses.remove(courseID);
        }
    }
}
