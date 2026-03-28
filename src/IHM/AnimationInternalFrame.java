package IHM;

import LesThreads.Animation;

import javax.swing.*;
import java.awt.*;

public class AnimationInternalFrame extends JInternalFrame {

    public AnimationInternalFrame() {
        super("TP4 Animation", true, true, true, true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        add(new Animation(), BorderLayout.CENTER);
        setSize(1100, 800);
        setVisible(true);
    }
}

