package a2_1901040115.Controller;

import a2_1901040115.model.CompulsoryModule;
import a2_1901040115.model.ElectiveModule;
import java.sql.*;
import java.util.ArrayList;

public class ModuleController {
     ArrayList<String> existingCodes = new ArrayList<>();
      ArrayList<Integer> semesters = new ArrayList<>();

    int count;
    public ModuleController() throws Exception {
    }
    public void checkExistingModules() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        String sql = "SELECT code, semester FROM module;";
        ResultSet rs = conn.createStatement().executeQuery(sql);

        while (rs.next()){
            existingCodes.add(rs.getString("code"));
            semesters.add(rs.getInt("semester"));
        }
        conn.close();
    }

    public String generateCode(int semester) throws SQLException {
        checkExistingModules();
        if(!semesters.contains(semester)){
            count = 1;
        } else {
            count = 1;
            for (String s:existingCodes){
                if(s.startsWith("M" + semester)){
                    count++;
                }
            }
        }
        String code = "M" + (semester*100 + count);
        return code;
    }

    public boolean addCompulsoryModuleToDb(CompulsoryModule m){
        boolean isDone = false;
        String sql = "INSERT INTO module (code, type, name, semester, credits) VALUES (?,?,?,?,?);";
        String codeMdl = m.getCode();
        String nameMdl = m.getName();
        int semesterMdl = m.getSemester();
        int creditsMdl = m.getCredits();

        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,codeMdl);
            ps.setString(2,"Compulsory");
            ps.setString(3, nameMdl);
            ps.setInt(4,semesterMdl);
            ps.setInt(5,creditsMdl);
            ps.executeUpdate();
            conn.close();
            isDone = true;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return isDone;
    }

    public boolean addElectiveModuleToDb(ElectiveModule m){
        boolean isDone = false;
        String sql = "INSERT INTO module VALUES (?,?,?,?,?,?);";
        String code = m.getCode();
        String name = m.getName();
        int semester = m.getSemester();
        int credits = m.getCredits();
        String department = m.getDepartmentName();
        if(isExist(name) == false){
            try{
                Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,code);
                ps.setString(2,"Elective");
                ps.setString(3,name);
                ps.setInt(4,semester);
                ps.setInt(5,credits);
                ps.setString(6,department);
                ps.executeUpdate();
                conn.close();


            }catch (SQLException e){
                e.printStackTrace();
            }
            isDone = true;
        }

        return isDone;
    }
    public Object[][] getAllModule() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        Statement stm = conn.createStatement();
        ArrayList<Object[]> listMdl = new ArrayList<>();
        Object[][] data = null;
        try {
            ResultSet rs = stm.executeQuery("SELECT * FROM module;");
            while (rs.next()) {
                listMdl.add(new Object[]{rs.getString("code"), rs.getString("type"),
                        rs.getString("name"), rs.getInt("semester"), rs.getInt("credits"),
                        rs.getString("department")});
            }
            data = new Object[listMdl.size()][listMdl.get(0).length];
            for (int i = 0; i < data.length; i++) {
                data[i] = listMdl.get(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();

        return data;
    }

    public String[] getAllModuleName() throws SQLException {
        String[] modules = new String[100];
        int count = 0;
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        Statement stm = conn.createStatement();
        try {
            ResultSet rs = stm.executeQuery("SELECT name FROM module;");
            while (rs.next()){
                modules[count] = rs.getString("name");
                count++;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        conn.close();
        return modules;
    }

    public String getTypeModuleByName(String name) {
        String sql = "SELECT * FROM module WHERE name = ?;";
        String type = null;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                type = rs.getString("type");
            }
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    public CompulsoryModule getCompulsoryModuleByName(String name){
        String sql = "SELECT * FROM module WHERE name = ?;";

        String code = null;
        int semester = 0;
        int credits = 0;
        CompulsoryModule m = null;
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                code = rs.getString("code");
                semester = rs.getInt("semester");
                credits = rs.getInt("credits");
            }
            m = new CompulsoryModule(code, name, semester, credits);
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return m;
    }

    public ElectiveModule getElectiveModuleByName(String name){
        String sql = "SELECT * FROM module WHERE name = ?;";
        String code = null;
        int semester = 0;
        int credits = 0;
        String department = null;
        ElectiveModule m = null;
        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                code = rs.getString("code");
                semester = rs.getInt("semester");
                credits = rs.getInt("credits");
                department = rs.getString("department");
            }
            m = new ElectiveModule(code, name, semester, credits, department);
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return m;
    }
    private boolean isExist(String name){
        boolean isExist = false;
        String sql = "SELECT * FROM module WHERE name = ?;";

        try{
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                isExist = true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }



        return isExist;
    }


}
