package IHM;

import IHM.gestionEtudiant.GestionEtudiant;
import IHM.gestionProfil.GestionProfil;
import IHM.menu.MenuBar;
import com.cvgenerator.ui.MainFrame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Dashboard extends JFrame {
    MenuBar menuBar;
    ClockDesktopPane desktopPane;
    private final Timer clockTimer;
    private final Timer floatTimer;

    public Dashboard() {
        this.setTitle("Dashboard");
        this.setSize(1400, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        menuBar.flow.addActionListener(new EcouteurMenu());
        menuBar.border.addActionListener(new EcouteurMenu());
        menuBar.grid.addActionListener(new EcouteurMenu());
        menuBar.cv.addActionListener(new EcouteurMenu());
        menuBar.gestion.addActionListener(new EcouteurMenu());
        menuBar.etudiant.addActionListener(new EcouteurMenu());
        menuBar.animation.addActionListener(new EcouteurMenu());

        desktopPane = new ClockDesktopPane();
        this.setContentPane(desktopPane);

        clockTimer = new Timer(1000, e -> {
            desktopPane.setClockText(formatClock(LocalTime.now()));
            desktopPane.repaint();
        });
        floatTimer = new Timer(25, e -> {
            desktopPane.advanceFloatPhase();
            desktopPane.repaint();
        });
        desktopPane.setClockText(formatClock(LocalTime.now()));
        clockTimer.start();
        floatTimer.start();
    }

    private String formatClock(LocalTime now) {
        return now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
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
            if(e.getSource()==menuBar.etudiant){
                GestionEtudiant ge = new GestionEtudiant();
                desktopPane.add(ge);
                ge.setSize(1100,800);
                ge.setVisible(true);
            }
            if(e.getSource()==menuBar.animation){
                AnimationInternalFrame animationFrame = new AnimationInternalFrame();
                desktopPane.add(animationFrame);
                animationFrame.setSize(1100,800);
                animationFrame.setVisible(true);
            }
        }
    }

    class ClockDesktopPane extends JDesktopPane {
        private String clockText = "00:00:00";
        private double floatPhase = 0.0;

        void setClockText(String clockText) {
            this.clockText = clockText;
        }

        void advanceFloatPhase() {
            floatPhase += 0.04;
            if (floatPhase > Math.PI * 2) {
                floatPhase -= Math.PI * 2;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (getAllFrames().length > 0) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint bg = new GradientPaint(0, 0, new Color(240, 244, 248), 0, getHeight(), new Color(226, 232, 240));
            g2.setPaint(bg);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int panelWidth = Math.min(780, getWidth() - 140);
            int panelHeight = 210;
            int basePanelX = (getWidth() - panelWidth) / 2;
            int basePanelY = (getHeight() - panelHeight) / 2;

            // Subtle floating movement for the home card.
            int floatX = (int) Math.round(Math.sin(floatPhase * 1.35) * 36);
            int floatY = (int) Math.round(Math.cos(floatPhase) * 24);
            int panelX = Math.max(20, Math.min(getWidth() - panelWidth - 20, basePanelX + floatX));
            int panelY = Math.max(20, Math.min(getHeight() - panelHeight - 20, basePanelY + floatY));

            g2.setColor(new Color(255, 255, 255, 235));
            g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 36, 36);
            g2.setColor(new Color(186, 196, 210));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 36, 36);

            g2.setColor(new Color(45, 55, 72));
            g2.setFont(new Font("SansSerif", Font.BOLD, 84));
            FontMetrics fm = g2.getFontMetrics();
            int tx = panelX + (panelWidth - fm.stringWidth(clockText)) / 2;
            int ty = panelY + (panelHeight + fm.getAscent()) / 2 - 18;
            g2.drawString(clockText, tx, ty);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
            String subtitle = "Home Clock";
            int sx = panelX + (panelWidth - g2.getFontMetrics().stringWidth(subtitle)) / 2;
            g2.setColor(new Color(90, 104, 124));
            g2.drawString(subtitle, sx, panelY + panelHeight - 22);

            g2.dispose();
        }
    }
}
