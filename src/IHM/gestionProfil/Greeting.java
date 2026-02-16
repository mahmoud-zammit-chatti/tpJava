package IHM.gestionProfil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Greeting extends JPanel {
    String name;
    JLabel greetingLabel, Lan;
    JButton ajouterLan;
    JComboBox<String> cycleCombo;
    JPanel anneePanel;
    JSpinner langue1Spinner, langue2Spinner, langue3Spinner;

    public Greeting(String name) {
        this.setLayout(new BorderLayout());
        this.name = name;

        // Top panel - greeting centered
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        greetingLabel = new JLabel("Bonjour !!! " + name);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 40));
        topPanel.add(greetingLabel);

        // Center panel with 4 rows
        JPanel CenterPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        // Languages header: label left, + button right
        JPanel lanPanel = new JPanel(new BorderLayout());
        Lan = new JLabel("Langues");
        Lan.setFont(new Font("Arial", Font.BOLD, 25));
        ajouterLan = new JButton("+");
        ajouterLan.setPreferredSize(new Dimension(50, 30));

        ajouterLan.setFont(new Font("Arial", Font.BOLD, 20));

        lanPanel.add(Lan, BorderLayout.WEST);
        lanPanel.add(ajouterLan, BorderLayout.EAST);

        JPanel langSpinnersPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        langue1Spinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        langue2Spinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        langue3Spinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        langSpinnersPanel.add(new JLabel("arabe:"));
        langSpinnersPanel.add(langue1Spinner);
        langSpinnersPanel.add(new JLabel("Anglais:"));
        langSpinnersPanel.add(langue2Spinner);
        langSpinnersPanel.add(new JLabel("Francais:"));
        langSpinnersPanel.add(langue3Spinner);

        JPanel cyclePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cycleCombo = new JComboBox<>(new String[]{"1ère Cycle", "2ème Cycle"});
        cyclePanel.add(new JLabel("Cycle: "));
        cyclePanel.add(cycleCombo);

        anneePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        updateAnneePanel("1ère Cycle");

        cycleCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnneePanel((String) cycleCombo.getSelectedItem());
            }
        });

        // Add all to center panel
        CenterPanel.add(lanPanel);
        CenterPanel.add(langSpinnersPanel);
        CenterPanel.add(cyclePanel);
        CenterPanel.add(anneePanel);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(CenterPanel, BorderLayout.CENTER);
    }

    private void updateAnneePanel(String cycle) {
        anneePanel.removeAll();
        anneePanel.add(new JLabel("Année: "));

        ButtonGroup group = new ButtonGroup();
        JRadioButton rb1 = new JRadioButton("1ère");
        JRadioButton rb2 = new JRadioButton("2ème");
        group.add(rb1);
        group.add(rb2);
        anneePanel.add(rb1);
        anneePanel.add(rb2);

        if (cycle.equals("2ème Cycle")) {
            JRadioButton rb3 = new JRadioButton("3ème");
            group.add(rb3);
            anneePanel.add(rb3);
        }

        anneePanel.revalidate();
        anneePanel.repaint();
    }

}
