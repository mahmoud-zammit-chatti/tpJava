package IHM.gestionProfil;

import IHM.Border;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class GestionProfil extends JInternalFrame {

        InfoPerso infoPerso;
        ElementCenter E = new ElementCenter();
        HelpElement H = new HelpElement();


        public GestionProfil(){
            super("Gestion Profil",true,true,true,true);
            this.setPreferredSize(new Dimension(1200, 600));
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setLayout(new BorderLayout());



            infoPerso = new InfoPerso();

            infoPerso.btnEnregistrer.addActionListener(e->{
                E.listModel.addElement(infoPerso.jpseudo.getText().trim());
                infoPerso.jpseudo.setText("");
                infoPerso.jnom.setText("");
                infoPerso.jprenom.setText("");
            });

            this.add(infoPerso, BorderLayout.NORTH);
            this.add(E,BorderLayout.CENTER);
            this.add(H,BorderLayout.SOUTH);
            this.pack();
            this.setVisible(true);

        }





}
