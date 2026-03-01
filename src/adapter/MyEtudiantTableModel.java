package adapter;

import DataBase.EtudiantImp;
import IHM.gestionEtudiant.Form;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyEtudiantTableModel extends AbstractTableModel {
    private final ResultSetMetaData rsmd;
    private final ArrayList<Object[]> allData = new ArrayList<>();
    private ArrayList<Object[]> data = new ArrayList<>();
    private final EtudiantImp etudiantImp;
    Form form;
    public MyEtudiantTableModel(ResultSet rs, EtudiantImp etudiantImp, Form form) {
        this.etudiantImp = etudiantImp;
        this.form = form;

        try {



             rsmd = rs.getMetaData();



             while (rs.next()){
                 Object[] ligne = new Object[getColumnCount()];
                 for (int i = 0; i < getColumnCount(); i++) {
                     ligne[i] = rs.getObject(i+1);
                 }
                 allData.add(ligne);
             }
             data = new ArrayList<>(allData);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        try {
            return rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        try {
            return rsmd.getColumnName(column+1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return getColumnName(columnIndex).equals("moyenne");
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Object ligne = data.get(rowIndex);

        etudiantImp.updateEtudiant((int)this.getValueAt(rowIndex,0), (String)this.getValueAt(rowIndex,1),(String) this.getValueAt(rowIndex,2),  Double.parseDouble(aValue+""));

        data.get(rowIndex)[columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void ajouterEtudiant(){
        int cin = Integer.parseInt(form.getCin().getText());
        String nom = form.getNom().getText();
        String prenom = form.getPrenom().getText();
        double moyenne = Double.parseDouble(form.getMoyenne().getText());

        etudiantImp.insertEtudiant(cin, nom, prenom, moyenne);

        Object[] row = new Object[]{cin, nom, prenom, moyenne};
        allData.add(row);
        data.add(row);
        fireTableDataChanged();

    }

    public void supprimerEtudiant(int rowIndex) {
        Object[] row = data.get(rowIndex);
        int cin = (int) row[0];
        etudiantImp.deleteEtudiant(cin);
        data.remove(rowIndex);
        allData.remove(row);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void supprimerTousEtudiants() {
        etudiantImp.deleteAllEtudiants();
        data.clear();
        allData.clear();
        fireTableDataChanged();
    }

    public void rechercher(String searchText) {
        String text = searchText.trim().toLowerCase();
        if (text.isEmpty()) {
            data = new ArrayList<>(allData);
        } else {
            data = new ArrayList<>();
            for (Object[] row : allData) {
                String cin = String.valueOf(row[0]).toLowerCase();
                String nom = String.valueOf(row[1]).toLowerCase();
                String prenom = String.valueOf(row[2]).toLowerCase();
                if (cin.contains(text) || nom.contains(text) || prenom.contains(text)) {
                    data.add(row);
                }
            }
        }
        fireTableDataChanged();
    }
}
