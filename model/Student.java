package a2_1901040115.model;
/*
Student name: Ha Thi Ngoc Lan
Student id: 1901040115
Class: 1C19
 */


public class Student {


    //attributes
    private String id;
    private String name;
    private String dob;
    private String address;
    private String email;

    //constructor
    public Student(String name, String dob, String address, String email) {

        this.id = "";
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.email = email;
    }
    public Student(){
    }
    public Student(String id, String name, String dob, String address, String email) {

        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.email = email;
    }

    //setters and getters
    public String getId() {
        return id;
    }
    public void setId(String id){this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
