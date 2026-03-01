package IHM.menu;

import IHM.FrameFlow;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public Menu tp1,tp2,tp3;
    public MenuItem flow,border,grid,cv,gestion,etudiant;

    public MenuBar() {
        super();
        this.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        tp1 = new Menu("tp1: gestion layout");
        tp2 = new Menu("tp2: gestion profil");
        tp3 = new Menu("tp3: gestion etudiant");

        this.add(tp1);
        this.add(tp2);
        this.add(tp3);

        //tp1 items
        flow = new MenuItem("Flow");
        border = new MenuItem("Border");
        grid = new MenuItem("Grid");


        tp1.add(flow);
        tp1.add(border);
        tp1.add(grid);
        //tp2 items
        cv = new MenuItem("Curriculum Vitae");
        gestion = new MenuItem("Gestion Profile");
        tp2.add(cv);
        tp2.add(gestion);

        //tp3 items
        etudiant=new MenuItem("Gestion Etudiant");
        tp3.add(etudiant);


    }

}
