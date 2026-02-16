package IHM.menu;

import javax.swing.*;
import java.awt.*;

public class MenuItem extends JMenuItem {

    public MenuItem(String title) {
        super(title);
        this.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        this.setSize(new Dimension(50,20));

    }


}
