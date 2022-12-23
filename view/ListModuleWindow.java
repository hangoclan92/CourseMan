package a2_1901040115.View;

import a2_1901040115.Controller.ModuleController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListModuleWindow extends JFrame{
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 300;
    private JTable tableMdl;
    private int xParent;
    private  int yParent;


    public ListModuleWindow(int x, int y) {
        super();
        setSize(WIDTH, HEIGHT);
        xParent = x;
        yParent = y;

        setTitle("List Module");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        createGUI();
    }
    private void createGUI() {
        //north panel
        JPanel top = new JPanel();
        JLabel title = new JLabel("List of Modules");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 15f));
        top.add(title);
        add(top, BorderLayout.NORTH);

        //center panel
        JPanel mid = new JPanel();
        mid.setLayout(new BorderLayout());

        String[] headers = {"Code", "Type", "Name", "Semester", "Credits", "Department Name(only for type Elective)"};
        Object[][] data = null;
        try{
            ModuleController mc = new ModuleController();
            data = mc.getAllModule();
        }catch (Exception e){
            e.printStackTrace();
        }

        DefaultTableModel tm = new DefaultTableModel(data, headers);
        tableMdl = new JTable(tm);
        JScrollPane scrTableStudent = new JScrollPane(tableMdl);
        mid.add(scrTableStudent);
        add(mid);

        pack();

        int x = xParent +100;
        int y = yParent + 100;
        setLocation(x,y);

    }
}
