package IHM.gestionProfil;

import javax.swing.*;
import java.awt.*;

public class HelpElement extends JPanel {

    JLabel helpLabel;

    public HelpElement() {

        helpLabel = new JLabel("Aide et Support");
        this.setLayout(new FlowLayout());
        this.add(helpLabel);

    }


}
