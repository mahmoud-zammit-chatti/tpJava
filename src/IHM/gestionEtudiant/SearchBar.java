package IHM.gestionEtudiant;

import javax.swing.*;

public class SearchBar extends JPanel {


        private JTextField searchField;
        private JButton searchButton;

        public SearchBar() {
            searchField = new JTextField(20);
            searchButton = new JButton("Rechercher");

            this.add(searchField);
            this.add(searchButton);
        }

        public JTextField getSearchField() {
            return searchField;
        }

        public JButton getSearchButton() {
            return searchButton;
        }


}
