package IHM.gestionProfil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InfoPerso extends JPanel {
    JLabel nom, prenom, pseudo;
    JTextField jnom, jprenom, jpseudo;
    JButton btnEnregistrer;
    final Color couleur1 = new Color(224, 75, 75);
    final Color couleur2 = new Color(102, 204, 255);
    final Color couleur3 = new Color(90, 218, 123);

    public InfoPerso() {
        this.setPreferredSize(new Dimension(1000, 150));
        this.setLayout(new FlowLayout());


        nom = new JLabel("Nom");
        prenom = new JLabel("Prénom");
        pseudo = new JLabel("Pseudo");

        nom.setPreferredSize(new Dimension(100, 20));
        prenom.setPreferredSize(new Dimension(100, 20));
        pseudo.setPreferredSize(new Dimension(100, 20));

        jnom = new JTextField(15);
        jprenom = new JTextField(15);
        jpseudo = new JTextField(15);

        btnEnregistrer = new JButton("Enregistrer");

        nom.addMouseListener(new ecouteur());
        prenom.addMouseListener(new ecouteur());
        pseudo.addMouseListener(new ecouteur());





        // Add components to the panel
        this.add(nom);
        this.add(jnom);
        this.add(prenom);
        this.add(jprenom);
        this.add(pseudo);
        this.add(jpseudo);
        this.add(btnEnregistrer);



    }



    class ecouteur extends MouseAdapter {
        Timer timer = new Timer(500, null); // 300ms delay between colors


        @Override
        public void mouseEntered(MouseEvent e) {
            final int[] step = {0};
            Color[] colors = {couleur1, couleur2, couleur3};

            timer.addActionListener(evt -> {



                    ((JLabel) e.getSource()).setForeground(colors[step[0]]);
                    step[0]++;
                    if(step[0]>=colors.length){
                        step[0]=0;
                    }


            });
            timer.start();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(timer!=null){
                timer.stop();
            }
            ((JLabel) e.getSource()).setForeground(Color.BLACK); // Reset to default
        }
    }

}

