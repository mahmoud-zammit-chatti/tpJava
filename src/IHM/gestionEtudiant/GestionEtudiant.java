package IHM.gestionEtudiant;

import DataBase.EtudiantImp;
import adapter.MyEtudiantTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class GestionEtudiant extends JInternalFrame {

    private JTable table;
    private EtudiantImp etudiantImp;

    private Form form;
    private SearchBar searchBar;



    public GestionEtudiant(){
        form=new Form();
        searchBar=new SearchBar();

        setPreferredSize(new Dimension(800,600));
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        etudiantImp=new EtudiantImp();
        ResultSet rs=etudiantImp.selectEtudiant("SELECT * FROM etudiant");

        MyEtudiantTableModel model=new MyEtudiantTableModel(rs,etudiantImp,form);
        table=new JTable(model);

        this.setTitle("Gestion des Etudiants");

        this.add(form, BorderLayout.NORTH);
        this.add(new JScrollPane(table), BorderLayout.CENTER);
        this.add(searchBar, BorderLayout.SOUTH);

        form.getBtnEnregistrer().addActionListener(e -> model.ajouterEtudiant());

        searchBar.getSearchButton().addActionListener(e -> model.rechercher(searchBar.getSearchField().getText()));

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteRow = new JMenuItem("Supprimer cette ligne");
        JMenuItem deleteAll = new JMenuItem("Supprimer toutes les lignes");
        popupMenu.add(deleteRow);
        popupMenu.add(deleteAll);

        deleteRow.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                model.supprimerEtudiant(selectedRow);
            }
        });

        deleteAll.addActionListener(e -> {

                model.supprimerTousEtudiants();

        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0) {
                        table.setRowSelectionInterval(row, row);
                    }
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        this.setVisible(true);
    }

}
