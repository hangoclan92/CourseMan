package a2_1901040115.View;
import a2_1901040115.Controller.ModuleController;
import a2_1901040115.model.CompulsoryModule;
import a2_1901040115.model.ElectiveModule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddModuleWindow extends  JFrame{
    public static final int WIDTH = 500;
    public static final int HEIGHT = 300;
    private JTextField name;
    private JTextField semester;
    private JTextField credits;
    private JTextField departmentName;
    private JLabel departmentLabel;
    private JComboBox cb;
    private int xParent;
    private  int yParent;

    public AddModuleWindow(int x, int y){
        super();
        setSize(WIDTH, HEIGHT);
        xParent = x;
        yParent = y;

        setTitle("New module");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        createGUI();
    }

    private void createGUI(){
        //title
        JPanel top = new JPanel();
        JLabel title = new JLabel("Add New Module");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 15f));
        top.add(title);
        add(top, BorderLayout.NORTH);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("Cancel")) {
                    disposeGUI();
                } else if (command.equals("Add new module")) {
                    boolean isContinue = isFilled();
                    if(isContinue){
                        boolean isDone = false;
                        //add student into database
                        String nameModule = name.getText();
                        int semesterModule = Integer.parseInt(semester.getText());
                        int creditsModule = Integer.parseInt(credits.getText());
                        String departmentModule = departmentName.getText();
                        try {
                            ModuleController mc = new ModuleController();
                            if (departmentModule.equals(null)){
                                CompulsoryModule m = new CompulsoryModule(mc.generateCode(semesterModule),nameModule,
                                        semesterModule, creditsModule);
                                isDone = mc.addCompulsoryModuleToDb(m);

                            }else {
                                ElectiveModule m = new ElectiveModule(mc.generateCode(semesterModule), nameModule,semesterModule,creditsModule,
                                        departmentModule);
                                isDone = mc.addElectiveModuleToDb(m);
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        if (isDone==true){
                            JOptionPane.showMessageDialog(null, "Add new module successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "This module has existed!", "Warning", JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (isFilled()==false) {
                        JOptionPane.showMessageDialog(null, "This module has existed!", "Warning", JOptionPane.ERROR_MESSAGE);

                    }
                    disposeGUI();
                }
            }};

        //center panel
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                String type = (String) comboBox.getSelectedItem();
                if (type == "Compulsory Module") {
                    //hide components
                    departmentLabel.setVisible(false);
                    departmentName.setVisible(false);
                    departmentName.setText("");

                } else if (type == "Elective Module") {
                    //show components
                    departmentLabel.setVisible(true);
                    departmentName.setVisible(true);
                }
            }
        };

        JPanel mid = new JPanel(new GridLayout(5, 2, 5, 10));
        mid.setBorder(BorderFactory.createEmptyBorder(15,20,10,20));
        String moduleType[] = {"Compulsory Module", "Elective Module"};
        mid.add(new JLabel("Module Type"));
        cb = new JComboBox(moduleType);
        cb.setSelectedIndex(1);
        cb.addItemListener(itemListener);
        mid.add(cb);

        mid.add(new JLabel("Name"));
        name = new JTextField(15);
        mid.add(name);
        mid.add(new JLabel("Semester"));
        semester = new JTextField(15);
        mid.add(semester);
        mid.add(new JLabel("credits"));
        credits = new JTextField(15);
        mid.add(credits);
        mid.add(departmentLabel = new JLabel("Department name"));
        departmentName = new JTextField(15);
        mid.add(departmentName);


        add(mid);

        //bottom
        JPanel bot = new JPanel();
        JButton btnAdd = new JButton("Add new module");
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
        name.setText("");
        semester.setText("");
        credits.setText("");
        departmentName.setText("");
        dispose();
    }
    private boolean isFilled(){
        boolean result = false;
        result = (semester.getText() != "") && (credits.getText() != "") && (name.getText() != "");

        return result;
    }

}
