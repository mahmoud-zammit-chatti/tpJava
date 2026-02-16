package IHM;

import IHM.gestionProfil.GestionProfil;
import IHM.menu.MenuBar;
import com.cvgenerator.ui.MainFrame;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    MenuBar menuBar;
    JDesktopPane desktopPane ;

    public Dashboard() {
        this.setTitle("Dashboard");
        this.setSize(1400, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        menuBar= new MenuBar();
        this.setJMenuBar(menuBar);

        menuBar.flow.addActionListener(new EcouteurMenu());
        menuBar.border.addActionListener(new EcouteurMenu());
        menuBar.grid.addActionListener(new EcouteurMenu());
        menuBar.cv.addActionListener(new EcouteurMenu());
        menuBar.gestion.addActionListener(new EcouteurMenu());

        desktopPane = new JDesktopPane();
        this.setContentPane(desktopPane);


    }
    class EcouteurMenu implements ActionListener {
        public EcouteurMenu(){}
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==menuBar.flow){
                FrameFlow ff =  new FrameFlow("Flow Layout Example");
                ff.setSize(1100,800);
                desktopPane.add(ff);
                setVisible(true);

            }
            if(e.getSource()==menuBar.border){
                Border border = new Border("Border Layout Example");
                desktopPane.add(border);
                border.setSize(1100,800);
                setVisible(true);
            }
            if(e.getSource()==menuBar.grid){
                Grid grid = new Grid("Grid Layout Example");
                desktopPane.add(grid);
                grid.setSize(1100,800);
                grid.setVisible(true);
            }
            if(e.getSource()==menuBar.cv){
                //cv
                MainFrame cv = new MainFrame();
                desktopPane.add(cv);
                cv.setSize(1100,800);
                cv.setVisible(true);
            }
            if(e.getSource()==menuBar.gestion){
                GestionProfil gp = new GestionProfil();
                desktopPane.add(gp);
                gp.setSize(1100,800);
                gp.setVisible(true);
            }
        }
    }
}
