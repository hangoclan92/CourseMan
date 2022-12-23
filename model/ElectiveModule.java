package a2_1901040115.model;
/*
Student name: Ha Thi Ngoc Lan
Student id: 1901040115
Class: 1C19
 */

public class ElectiveModule extends Module{

    /*
    The elective module inherits all attributes from module and has one extra attribute called department name
     */
    private String departmentName;

    //constructor
    public ElectiveModule(String code, String name, int semester, int credits, String departmentName) {
        super(code, name, semester, credits);

        this.departmentName = departmentName;

    }

    //setter and getter
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
