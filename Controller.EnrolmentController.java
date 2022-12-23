package controller;

import model.Enrolment;
import java.sql.*;
import java.util.ArrayList;

public class EnrolmentController {
    public EnrolmentController() {
    }

    public boolean addEnrolmentToDB(Enrolment e) throws SQLException {
        boolean isDone = false;

        String stdName = e.getStudent().getName();
        String stdID = e.getStudent().getId();
        String mdlName = e.getModule().getName();
        String mdlCode = e.getModule().getCode();
        Double internal = e.getInternalMark();
        Double exam = e.getExamMark();
        String finalGrade = e.getFinalGrade();
        if (validate(stdID,mdlCode)==false){
            PreparedStatement preparedStatement ;
            try{
                Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
                String sql = "INSERT INTO enrolment (studentid, studentName, moduleCode, moduleName, internalMark, examMark, finalGrade) VALUES (?,?,?,?,?,?,?);";

                preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, stdID);
                preparedStatement.setString(2, stdName);
                preparedStatement.setString(3, mdlCode);
                preparedStatement.setString(4, mdlName);
                preparedStatement.setDouble(5, internal);
                preparedStatement.setDouble(6, exam);
                preparedStatement.setString(7, finalGrade);

                preparedStatement.executeUpdate();
                conn.close();

            } catch (SQLException ex){
                ex.printStackTrace();
            }
            isDone = true;
        }
        return isDone;
    }
    //Final grade is a single character which must be one of
    //the followings: “E” (excellent - from 9 to 10), “G” (good - from 7 to 8.9), “P” (pass - from 5 to 6.9), and “F” (failed - below 5).
    public String calFinal(double internal, double exam) {
        double aggregatedMark = internal*0.4 + exam*0.6;
        if (aggregatedMark < 5){ return "F";}
        else if(aggregatedMark < 7){ return "P";}
        else if (aggregatedMark < 9) {
            return "G" ;
        } else { return "E";
        }
    }

    public Object[][] getInitialReportData() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        Statement stm = conn.createStatement();
        ArrayList<Object[]> initialRp = new ArrayList<>();
        Object[][] data = null;
        try {
            ResultSet rs = stm.executeQuery("SELECT * FROM enrolment;");
            while (rs.next()) {
                initialRp.add(new Object[]{rs.getString("enrolmentID"), rs.getString("studentid"),
                        rs.getString("studentName"), rs.getString("moduleCode"), rs.getString("moduleName")});
            }
            data = new Object[initialRp.size()][initialRp.get(0).length];
            for (int i = 0; i < data.length; i++) {
                data[i] = initialRp.get(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();

        return data;
    }

    public Object[][] getAssessmentReportData() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        Statement stm = conn.createStatement();
        ArrayList<Object[]> assessmentRp = new ArrayList<>();
        Object[][] data = null;
        try {
            ResultSet rs = stm.executeQuery("SELECT * FROM enrolment;");
            while (rs.next()) {
                assessmentRp.add(new Object[]{rs.getString("enrolmentID"), rs.getString("studentid"),
                        rs.getString("moduleCode"), rs.getDouble("internalMark"), rs.getDouble("examMark"),
                rs.getString("finalGrade")});
            }
            data = new Object[assessmentRp.size()][assessmentRp.get(0).length];
            for (int i = 0; i < data.length; i++) {
                data[i] = assessmentRp.get(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();

        return data;
    }

    private boolean validate(String stdID, String codeMdl) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:src/a2_1901040115/database.sqlite3");
        boolean isExist = false;
        String sql = "SELECT * FROM enrolment WHERE studentid = ? AND moduleCode = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, stdID);
        ps.setString(2, codeMdl);
        try{
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                isExist = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        conn.close();

        return isExist;

    }

}
