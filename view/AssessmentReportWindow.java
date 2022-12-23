package a2_1901040115.View;

import a2_1901040115.Controller.EnrolmentController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AssessmentReportWindow extends JFrame{
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 300;
    private JTable tableRp;
    private int xParent;
    private  int yParent;

    public AssessmentReportWindow(int x, int y) {
        super();
        setSize(WIDTH, HEIGHT);
        xParent = x;
        yParent = y;

        setTitle("Initial Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        createGUI();
    }

    public void createGUI(){
        //north panel
        JPanel top = new JPanel();
        JLabel title = new JLabel("Assessment Report");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 15f));
        top.add(title);
        add(top, BorderLayout.NORTH);

        //center panel
        JPanel mid = new JPanel();
        mid.setLayout(new BorderLayout());

        String[] headers = {"ID", "Student ID", "Module Code", "Internal Mark", "Exam Mark", "Final Grade"};
        Object[][] data = null;
        try{
            EnrolmentController ec = new EnrolmentController();
            data = ec.getAssessmentReportData();
        }catch (Exception e){
            e.printStackTrace();
        }

        DefaultTableModel tm = new DefaultTableModel(data, headers);
        tableRp = new JTable(tm);
        JScrollPane scrTableStudent = new JScrollPane(tableRp);
        mid.add(scrTableStudent);
        add(mid);

        pack();

        int x = xParent +100;
        int y = yParent + 100;
        setLocation(x,y);
    }
}
