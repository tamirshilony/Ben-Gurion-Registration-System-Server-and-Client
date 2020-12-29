public class CourseMessage extends Message{
    private int courseNum;


    public CourseMessage(OpcodeType type_,int courseNum_) {
        super(type_);
        courseNum = courseNum_;
    }

    public int getCourseNum() {
        return courseNum;
    }
}
