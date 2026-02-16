package IHM;

import javax.swing.*;
import java.awt.*;

public class FrameFlow extends JInternalFrame{
    JButton button1, button2, button3, button4, button5, button6;
    public FrameFlow(String title) {
        super("frame flow",true,true,true,true);
        this.setTitle(title);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setLayout(new FlowLayout());

        button1 = new JButton("Button 1");
        button2 = new JButton("Button 2");
        button3 = new JButton("Button 3");
        button4 = new JButton("Button 4");
        button5 = new JButton("Button 5");
        button6 = new JButton("Button 6");

        button1.addActionListener(e->{
            JOptionPane.showMessageDialog(this, "You clicked Button 1");
        });

        button2.addActionListener(e->{
            JOptionPane.showMessageDialog(this, "You clicked Button 2");
        });


        this.add(button1);
        this.add(button2);
        this.add(button3);
        this.add(button4);
        this.add(button5);
        this.add(button6);



        this.setVisible(true);
    }
}
