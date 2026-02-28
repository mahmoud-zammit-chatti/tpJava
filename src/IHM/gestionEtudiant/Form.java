package IHM.gestionEtudiant;

import javax.swing.*;

public class Form extends JPanel {

    private JTextField cin;
    private JTextField nom;
    private JTextField prenom;
    private JTextField moyenne;
    private JButton btnEnregistrer;

    public JTextField getCin() {
        return cin;
    }

    public JTextField getNom() {
        return nom;
    }

    public JTextField getPrenom() {
        return prenom;
    }

    public JTextField getMoyenne() {
        return moyenne;
    }
    public JButton getBtnEnregistrer() {
        return btnEnregistrer;
    }

    public Form() {
        cin=new JTextField(10);
        this.add(cin);
        this.add(new JLabel("Nom"));
        nom=new JTextField(10);
        this.add(nom);
        this.add(new JLabel("Prenom"));
        prenom=new JTextField(10);
        this.add(prenom);
        this.add(new JLabel("Moyenne"));
        moyenne=new JTextField(10);
        this.add(moyenne);

        btnEnregistrer=new JButton("Enregistrer");


        this.add(btnEnregistrer);
    }

}
