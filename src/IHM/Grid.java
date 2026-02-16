package IHM;

import javax.swing.*;
import java.awt.*;

public class Grid extends JInternalFrame {
    JButton button1, button2, button3, button4, button5, button6;
    public Grid(String title) {
        super("Grid frame",true,true,true,true);

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLayout(new GridLayout(2,3));


        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Button 3");
        button4 = new JButton("Button 4");
        button5 = new JButton("Button 5");
        button6 = new JButton("Button 6");
        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        this.add(button6);


        this.setVisible(true);
    }
}
