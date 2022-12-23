package a2_1901040115.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame{
    private JFrame addStd;
    private JFrame addMdl;
    private JFrame listStd;
    private JFrame listMdl;
    private JFrame addEnrolment;
    private  JFrame initialRp;
    private JFrame assessmentRp;



    public static final int WIDTH = 600;
    public static final int HEIGHT = 250;

    public MainWindow() {
        super();
        setSize(WIDTH, HEIGHT);

        setTitle("Course Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createMenu(this);

        //north panel
        JPanel top = new JPanel(new GridLayout(5,1,5,10));
        top.setBackground(new Color(179,230,204));
        JLabel title = new JLabel("Course Man Program");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(title);
        JLabel name = new JLabel("Student name: Hà Thị Ngọc Lan");
        name.setFont(name.getFont().deriveFont(Font.PLAIN, 15f));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(name);
        JLabel id = new JLabel("Student ID: 1901040115");
        id.setFont(name.getFont().deriveFont(Font.PLAIN, 15f));
        id.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(id);
        JLabel subject = new JLabel("Course: Java Software Development");
        subject.setFont(name.getFont().deriveFont(Font.PLAIN, 15f));
        subject.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(subject);
        JLabel instructor = new JLabel("Teacher: Đặng Đình Quân");
        instructor.setFont(name.getFont().deriveFont(Font.PLAIN, 15f));
       instructor.setHorizontalAlignment(SwingConstants.CENTER);
       top.add(instructor);

        add(top);

    }
private void createMenu(final JFrame w){
        //create listener
    ActionListener listener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String menuItem = e.getActionCommand();
           if (menuItem.equals("New Student")){
               addStd = new AddStudentWindow(w.getX(), w.getY());
               addStd.setVisible(true);
           }
           if (menuItem.equals("New Module")){
               addMdl = new AddModuleWindow(w.getX(), w.getY());
               addMdl.setVisible(true);
           }
           if (menuItem.equals("List Student")){
               listStd = new ListStudentWindow(w.getX(), w.getY());
               listStd.setVisible(true);
           }
            if (menuItem.equals("List Module")){
                listMdl = new ListModuleWindow(w.getX(), w.getY());
                listMdl.setVisible(true);
            }
            if (menuItem.equals("New Enrolment")){
                addEnrolment = new AddEnrolmentWindow(w.getX(), w.getY());
                addEnrolment.setVisible(true);
            }
            if (menuItem.equals("Initial Report")){
                initialRp = new InitialReportWindow(w.getX(), w.getY());
                initialRp.setVisible(true);
            }
            if (menuItem.equals("Assessment Report")){
                assessmentRp = new AssessmentReportWindow(w.getX(), w.getY());
                assessmentRp.setVisible(true);

            }

        }};


    //create menu bar
    JMenuBar menuBar = new JMenuBar();
    // create menus and corresponding menu items
    JMenu fileMenu = new JMenu("File");
    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(new ActionListener() {
        @Override
        public  void actionPerformed(ActionEvent e) {
            w.dispose();
        }
    });
    fileMenu.add(exit);

    //nested menu for student menu
    JMenu studentMenu = new JMenu("Student");
    JMenuItem newStd = new JMenuItem("New Student");
    newStd.addActionListener(listener);
    JMenuItem listStd = new JMenuItem("List Student");
    listStd.addActionListener(listener);
    studentMenu.add(newStd);
    studentMenu.add(listStd);

    //nested menu for module
    JMenu moduleMenu = new JMenu("Module");
    JMenuItem newModule = new JMenuItem("New Module");
    newModule.addActionListener(listener);
    JMenuItem listModule = new JMenuItem("List Module");
    listModule.addActionListener(listener);
    moduleMenu.add(newModule);
    moduleMenu.add(listModule);

    //nested menu for enrolment & report
    JMenu enrolment = new JMenu("Enrolment");
    JMenuItem newEnrolment = new JMenuItem("New Enrolment");
    newEnrolment.addActionListener(listener);
    JMenuItem initialReport = new JMenuItem("Initial Report");
    initialReport.addActionListener(listener);
    JMenuItem assessmentReport = new JMenuItem("Assessment Report");
    assessmentReport.addActionListener(listener);
    enrolment.add(newEnrolment);
    enrolment.add(initialReport);
    enrolment.add(assessmentReport);

    menuBar.add(fileMenu);
    menuBar.add(studentMenu);
    menuBar.add(moduleMenu);
    menuBar.add(enrolment);

    w.setJMenuBar(menuBar);

}

}
