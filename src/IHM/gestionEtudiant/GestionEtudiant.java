package IHM.gestionEtudiant;

import DataBase.EtudiantImp;
import adapter.MyEtudiantTableModel;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class GestionEtudiant extends JFrame {

    private JTable table;
    private EtudiantImp etudiantImp;

    private Form form;



    public GestionEtudiant(){
        Form form=new Form();

        setPreferredSize(new Dimension(800,600));
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        etudiantImp=new EtudiantImp();
        ResultSet rs=etudiantImp.selectEtudiant("SELECT * FROM etudiant");

        MyEtudiantTableModel model=new MyEtudiantTableModel(rs,etudiantImp,form);
        table=new JTable(model);
        this.add(new JScrollPane(table));

        this.setTitle("Gestion des Etudiants");

        this.add(form,BorderLayout.NORTH);
        this.setVisible(true);






    }
    public static void main(String[] args) {
        GestionEtudiant gestionEtudiant=new GestionEtudiant();
        gestionEtudiant.pack();
        gestionEtudiant.setVisible(true);
    }

}
