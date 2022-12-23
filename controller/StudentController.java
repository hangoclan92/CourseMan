package a2_1901040115.Controller;

import a2_1901040115.model.Student;
import java.sql.*;
import java.util.ArrayList;

public class StudentController {
    private static int startedID;

    static {
        try {
            startedID = getLastEntry() + 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public StudentController() throws Exception {
        setNewStartedID();
    }

    public static int getLastEntry() throws SQLException {
        int year;

        String id = "";
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        Statement stm = conn.createStatement();
        conn.setAutoCommit(true);
        String sql = "SELECT * FROM student ORDER BY studentid DESC LIMIT 1;";
        ResultSet rs = stm.executeQuery(sql);
        if (rs.next()){
            id = rs.getString("studentid");
        }
        year = Integer.parseInt(id.substring(1));
        conn.close();


        return year;
    }

    private static boolean isFirst = true;


    private String generateID(){
        return "S" + startedID;
    }
    private static void setNewStartedID(){
        if(!isFirst){
            startedID++;
        } else {
            isFirst = false;
        }
    }
    
    public boolean addStudentToDB(Student std) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        conn.setAutoCommit(true);
        boolean isDone = false;
        
            String id = generateID();
            String name = std.getName();
            String dob = std.getDob();
            String address = std.getAddress();
            String email = std.getEmail();
            PreparedStatement preparedStatement ;
            if (isExist(name, dob)==false){
                try{
                    String sql = "INSERT INTO student (studentid, name, dob, address, email) VALUES (?, ?, ?, ?, ?);";

                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, name);
                    preparedStatement.setString(3, dob);
                    preparedStatement.setString(4, address);
                    preparedStatement.setString(5, email);

                    preparedStatement.executeUpdate();
                    conn.close();
                    isDone = true;

                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return isDone;

    }

    public Object[][] getAllStudent() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        Statement stm = conn.createStatement();
        ArrayList<Object[]> listStd = new ArrayList<>();
        Object[][] data = null;
        try {
            ResultSet rs = stm.executeQuery("SELECT * FROM student;");
            while (rs.next()) {
                listStd.add(new Object[]{rs.getString("studentid"), rs.getString("name"),
                        rs.getString("dob"), rs.getString("address"), rs.getString("email")});
            }
            data = new Object[listStd.size()][listStd.get(0).length];
            for (int i = 0; i < data.length; i++) {
                data[i] = listStd.get(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();

        return data;
    }

    public String[] getAllStudentName() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        String[] students = new String[100];
        int count = 0;
        Statement stm = conn.createStatement();
        try {
            ResultSet rs = stm.executeQuery("SELECT * FROM student;");
            while (rs.next()){
                students[count] = rs.getString("name");
                count++;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        conn.close();
        return students;
    }
    public Student getStudentByName(String name) {

        String sql = "SELECT * FROM student WHERE name = ?;";
        String id = null;
        String dob = null;
        String address = null;
        String email = null;
        Student s;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getString("studentid");
                dob = rs.getString("dob");
                address = rs.getString("address");
                email = rs.getString("email");
            }

            s = new Student(id, name, dob, address, email);
            conn.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return s;
    }

    private boolean isExist(String name, String dob){
        boolean isExist = false;
        String sql = "SELECT * FROM student WHERE name = ? AND dob = ?;";
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, dob);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                isExist = true;
            }
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return isExist;
    }
}
