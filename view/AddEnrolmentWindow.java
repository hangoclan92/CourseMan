package a2_1901040115.View;

import a2_1901040115.Controller.EnrolmentController;
import a2_1901040115.Controller.ModuleController;
import a2_1901040115.Controller.StudentController;
import a2_1901040115.model.CompulsoryModule;
import a2_1901040115.model.ElectiveModule;
import a2_1901040115.model.Enrolment;
import a2_1901040115.model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEnrolmentWindow extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;
    private JComboBox studentBox;
    private JComboBox moduleBox;
    private JTextField internalMark;
    private JTextField examMark;
    private int xParent;
    private int yParent;

    public AddEnrolmentWindow(int x, int y) {
        super();
        setSize(WIDTH, HEIGHT);
        xParent = x;
        yParent = y;

        setTitle("New Enrolment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        createGUI();
    }

    public void createGUI() {

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("Cancel")) {
                    disposeGUI();
                } else if (command.equals("Add new enrolment")) {
                    String nameStd = (String) studentBox.getSelectedItem();
                    String nameMdl = (String) moduleBox.getSelectedItem();


                    double internal = Double.parseDouble(internalMark.getText());
                    double exam = Double.parseDouble(examMark.getText());


                    try {
                        ModuleController mc = new ModuleController();
                        StudentController sc = new StudentController();
                        EnrolmentController ec = new EnrolmentController();
                        String finalGrade = ec.calFinal(internal, exam);
                        String type = mc.getTypeModuleByName(nameMdl);

                        if (type.equals("Compulsory")) {
                            Student s = sc.getStudentByName(nameStd);
                            CompulsoryModule m = mc.getCompulsoryModuleByName(nameMdl);
                            Enrolment enrolment = new Enrolment(s, m, internal, exam, finalGrade);
                            boolean isDone = ec.addEnrolmentToDB(enrolment);
                            if (isDone == true) {
                                JOptionPane.showMessageDialog(null, "Add new enrolment successfully!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Enrolment has existed!", "Warning", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (type.equals("Elective")) {
                            Student s = sc.getStudentByName(nameStd);
                            ElectiveModule m = mc.getElectiveModuleByName(nameMdl);
                            Enrolment enrolment = new Enrolment(s, m, internal, exam, finalGrade);
                            boolean isDone = ec.addEnrolmentToDB(enrolment);
                            if(isDone == true){
                                JOptionPane.showMessageDialog(null, "Add new enrolment successfully!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Enrolment has existed!", "Warning", JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
                disposeGUI();
            }
        };

        JPanel top = new JPanel();
        JLabel title = new JLabel("Add New Enrolment");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 15f));
        top.add(title);
        add(top, BorderLayout.NORTH);

        //center panel
        JPanel mid = new JPanel(new GridLayout(4, 2, 5, 10));
        mid.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        String[] studentList = null;
        String[] moduleList = null;
        try {
            StudentController sc = new StudentController();
            ModuleController mc = new ModuleController();

            studentList = sc.getAllStudentName();
            studentBox = new JComboBox(studentList);
            moduleList = mc.getAllModuleName();
            moduleBox = new JComboBox(moduleList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        mid.add(new JLabel("Select Student:"));
        mid.add(studentBox);
        mid.add(new JLabel("Select Module:"));
        mid.add(moduleBox);
        mid.add(new JLabel("Internal Mark"));
        internalMark = new JTextField(15);
        mid.add(internalMark);
        mid.add(new JLabel("Exam Mark"));
        examMark = new JTextField(15);
        mid.add(examMark);
        add(mid);

        //bottom
        JPanel bot = new JPanel();
        JButton btnAdd = new JButton("Add new enrolment");
        btnAdd.addActionListener(listener);
        bot.add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(listener);
        bot.add(btnCancel);

        bot.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        add(bot, BorderLayout.SOUTH);
        pack();

        int x = xParent + 100;
        int y = yParent + 100;
        setLocation(x, y);
    }

    private void disposeGUI() {
        internalMark.setText(null);
        examMark.setText(null);
        dispose();
    }
}


