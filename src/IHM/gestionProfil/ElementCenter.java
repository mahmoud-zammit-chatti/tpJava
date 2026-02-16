package IHM.gestionProfil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

import IHM.menu.MenuItem;

public class ElementCenter extends JPanel {
    JList<String> jl;
    JTabbedPane jtp;
    JSplitPane jsp;
    JPopupMenu popupMenu;
    DefaultListModel<String> listModel ;
    MenuItem modifier,sup,supt;
    Greeting greeting;

    public ElementCenter() {

        this.setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        jtp = new JTabbedPane();
        jl = new JList<>(listModel);
        jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jl, jtp);
        jsp.setDividerLocation(200);


         popupMenu= new JPopupMenu();
         modifier=new MenuItem("Modifier");
         sup=new MenuItem("Supprimer");
         supt=new MenuItem("Supprimer tout");
         popupMenu.add(modifier);
         popupMenu.add(sup);
         popupMenu.add(supt);

         jl.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(java.awt.event.MouseEvent e){
                 int index = jl.locationToIndex(e.getPoint());
                 if(index != -1){
                     jl.setSelectedIndex(index);

                     // popup menu
                     if(SwingUtilities.isRightMouseButton(e)){
                         popupMenu.show(jl, e.getX(), e.getY());
                     }
                     //  pseudo to TabbedPane
                     else if(e.getClickCount() == 2){
                         String selectedPseudo = jl.getSelectedValue();
                         // Check if tab already exists
                         boolean tabExists = false;
                         for(int i = 0; i < jtp.getTabCount(); i++){
                             if(jtp.getTitleAt(i).equals(selectedPseudo)){
                                 jtp.setSelectedIndex(i);
                                 tabExists = true;
                                 break;
                             }
                         }
                         if(!tabExists){
                             //affichage de bounjour..................
                             JPanel tabPanel = new JPanel();
                             //tabPanel.add(new JLabel("Bonjour " + selectedPseudo));
                             greeting = new Greeting(selectedPseudo);

                             tabPanel.add(greeting);
                             jtp.addTab(selectedPseudo, tabPanel);
                             jtp.setSelectedIndex(jtp.getTabCount() - 1);
                         }
                     }
                 }
             }
         });
        modifier.addActionListener(e -> {
            int index = jl.getSelectedIndex();
            if (index != -1) {
                String newValue = JOptionPane.showInputDialog(this, "Nouveau pseudo:", listModel.get(index));

                if (newValue != null && !newValue.trim().isEmpty()) {
                    listModel.set(index, newValue);
                }
            }
        });
        sup.addActionListener(e -> {
            int index = jl.getSelectedIndex();
            if (index != -1) {
                listModel.remove(index);
            }
        });

        supt.addActionListener(e -> {
            listModel.clear();
            jtp.removeAll();
        });


        this.add(jsp,BorderLayout.CENTER);


    }






}
