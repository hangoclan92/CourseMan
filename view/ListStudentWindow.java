package a2_1901040115.View;

import a2_1901040115.Controller.StudentController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListStudentWindow  extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 300;
    private JTable tableStd;
    private int xParent;
    private  int yParent;


    public ListStudentWindow(int x, int y) {
        super();
        setSize(WIDTH, HEIGHT);
        xParent = x;
        yParent = y;

        setTitle("List Student");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        createGUI();
    }
    private void createGUI() {
        //north panel
        JPanel top = new JPanel();
        JLabel lblTitle = new JLabel("List of Students");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 15f));
        top.add(lblTitle);
        add(top, BorderLayout.NORTH);

        //center panel
        JPanel mid = new JPanel();
        mid.setLayout(new BorderLayout());

        String[] headers = {"Student ID", "Student Name", "Date of Birth", "Address", "Email"};
        Object[][] data = null;
        try{
        StudentController sc = new StudentController();
        data = sc.getAllStudent();
        }catch (Exception e){
            e.printStackTrace();
        }

        DefaultTableModel tm = new DefaultTableModel(data, headers);
        tableStd = new JTable(tm);
        JScrollPane scrTableStudent = new JScrollPane(tableStd);
        mid.add(scrTableStudent);
        add(mid);

        pack();

        int x = xParent +100;
        int y = yParent + 100;
        setLocation(x,y);

    }
}
