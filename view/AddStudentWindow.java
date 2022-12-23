package a2_1901040115.View;
import a2_1901040115.Controller.StudentController;
import a2_1901040115.model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddStudentWindow extends JFrame {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 500;
    private JTextField name;
    private JTextField dob;
    private JTextField address;
    private JTextField email;

    private int xParent;
    private  int yParent;

    public AddStudentWindow(int x, int y){
        super();
        setSize(WIDTH, HEIGHT);
        xParent = x;
        yParent = y;

        setTitle("Add new student");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        createGUI();
    }
    private void createGUI(){
        //top panel
        JPanel top = new JPanel();
        JLabel title = new JLabel("Add New Student");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 15f));
        top.add(title);
        add(top, BorderLayout.NORTH);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("Cancel")) {
                    disposeGUI();
                } else if (command.equals("Add")) {
                        //add student into database
                        boolean isDone;
                        Student a1 = new Student(name.getText(), dob.getText(),address.getText(), email.getText());
                        try {
                            StudentController sc = new StudentController();
                            isDone = sc.addStudentToDB(a1);

                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        if (isDone == true){
                            JOptionPane.showMessageDialog(null, "Add new student successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Student has existed!", "Warning", JOptionPane.ERROR_MESSAGE);
                        }


                }
            }};
        //center panel
        JPanel mid = new JPanel(new GridLayout(4, 2, 5, 10));
        mid.setBorder(BorderFactory.createEmptyBorder(15,20,10,20));
        mid.add(new JLabel("Name"));
        name = new JTextField(15);
        mid.add(name);
        mid.add(new JLabel("Date of birth"));
        dob = new JTextField(15);
        mid.add(dob);
        mid.add(new JLabel("Address"));
        address = new JTextField(15);
        mid.add(address);
        mid.add(new JLabel("Email"));
        email = new JTextField(15);
        mid.add(email);

        add(mid);

        //bottom
        JPanel bot = new JPanel();
        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(listener);
        bot.add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(listener);
        bot.add(btnCancel);

        bot.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        add(bot, BorderLayout.SOUTH);
        pack();

        int x = xParent +100;
        int y = yParent + 100;
        setLocation(x,y);

    }

    private void disposeGUI(){
        name.setText(null);
        dob.setText(null);
        address.setText(null);
        email.setText(null);
        dispose();
    }


}
