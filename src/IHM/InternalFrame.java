package IHM;

import javax.swing.*;

public class InternalFrame extends JInternalFrame {

    public InternalFrame(String title) {
        super(title, true, true, true, true);
        this.setSize(400, 300);
        this.setVisible(true);
    }

}
