package IHM;

import javax.swing.*;
import java.awt.*;

public class Border extends JInternalFrame {

    JButton button1, button2, button3, button4, button5;

    public Border(String title) {
        super("Border frame",true,true,true,true);

        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLayout(new BorderLayout());


        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Button 3");
        button4 = new JButton("Button 4");
        button5 = new JButton("Button 5");



        this.add(button1,BorderLayout.NORTH);
        this.add(button2,BorderLayout.SOUTH);
        this.add(button3,BorderLayout.EAST);
        this.add(button4,BorderLayout.WEST);
        this.add(button5,BorderLayout.CENTER);



        this.setVisible(true);
    }
}