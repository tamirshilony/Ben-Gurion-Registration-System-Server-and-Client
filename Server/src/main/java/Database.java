import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
	private static class SingletonHolder{
		private static Database instance = new Database();
	}
	private ConcurrentHashMap<Integer,Course>courseID2Course;
	private ConcurrentHashMap<String,User>userName2User;

	//to prevent user from creating new Database

	private Database() {
		courseID2Course = new ConcurrentHashMap<>();
		userName2User = new ConcurrentHashMap<>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * loades the courses from the file path specified 
	 * into the Database, returns true if successful.
	 */
	boolean initialize(String coursesFilePath) {
		try (Scanner scanner = new Scanner(new File(coursesFilePath))) {
			while (scanner.hasNext()) {
				String courseInfo = scanner.nextLine();
				String[] info = courseInfo.split("\\|");
				int courseId =Integer.parseInt(info[0]);
				String courseName = info[1];
				int limit = Integer.parseInt(info[3]);
				String kdam = info[2].substring(1,info[2].length()-1);
				String[] kdams = kdam.split(",");
				Vector<Integer>kdamsVec = new Vector<>();
				for (String coursNum:kdams) {
					kdamsVec.add(Integer.parseInt(coursNum));
				}
				Course currCourse = new Course(courseId,courseName,kdamsVec,limit);
				courseID2Course.put(courseId,currCourse);
			}
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		return false;
	}
	public ConcurrentHashMap<Integer,Course>getCourseID2Course(){
		return courseID2Course;
	}

	public User register(String userName,String password){
		return userName2User.putIfAbsent(userName,new User(userName,password));
	}

	public User getUser(String userName){
		return userName2User.get(userName);
	}

	public Course getCourse(int courseNum){
		return courseID2Course.get(courseNum);
	}

}
