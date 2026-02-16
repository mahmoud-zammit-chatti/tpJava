package IHM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CurriculumVitae extends JFrame {
    //partie declaration
    JButton btn_valid , btn_Quitter;
    JLabel lb_titre;
    TextField tf_nom;
    CurriculumVitae() {
        this.setTitle("Curriculum Vitae");
        this.setSize(1100,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Creation de l'interface

        //positionnement
        this.setLayout(new FlowLayout());

        //buttons
        btn_valid = new JButton("Valider");
        btn_Quitter = new JButton("Quitter");


        //label
        lb_titre= new JLabel("Curriculum Vitae");

        lb_titre.setPreferredSize(new Dimension(1100,200));
        lb_titre.setForeground(Color.WHITE);
        lb_titre.setBackground(Color.BLUE);
        lb_titre.setOpaque(true);
        lb_titre.setFont(new Font(Font.MONOSPACED,Font.BOLD,80));
        this.add(lb_titre);

        //textField(input)
        tf_nom= new TextField(20);

        tf_nom.setPreferredSize(new Dimension(500,200));
        tf_nom.setForeground(Color.BLUE);
        tf_nom.setBackground(Color.WHITE);
        tf_nom.setFont(new Font(Font.MONOSPACED,Font.BOLD,80));
        this.add(tf_nom);

        //evenement
        btn_Quitter.addActionListener(new EcouteurButton());
        btn_valid.addActionListener(new EcouteurButton());

        this.add(btn_valid);
        this.add(btn_Quitter);



    }
    class EcouteurButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TEST OBLIGATOIRE PUISQUE 2 BUTTONS
            if(e.getSource()==btn_Quitter){
                System.exit(0);
            }
            if (e.getSource()==btn_valid){
                File file = new File("cv.html");
                FileWriter fw = null;
                try {
                    fw = new FileWriter(file, false);
                    String input = tf_nom.getText();
                    fw.write("<html>" +
                            "<title> CV </title>" +
                            "<body>vos infos : <br> " +input+ "</body>" +
                            "</html>");
                    fw.close();
                    Desktop.getDesktop().open(file);
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

}