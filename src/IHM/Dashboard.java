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
    private boolean blinkColon = true;

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
            blinkColon = !blinkColon;
            desktopPane.setClockText(formatClock(LocalTime.now()));
            desktopPane.repaint();
        });
        desktopPane.setClockText(formatClock(LocalTime.now()));
        clockTimer.start();
    }

    private String formatClock(LocalTime now) {
        String raw = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return blinkColon ? raw : raw.replace(':', ' ');
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

        void setClockText(String clockText) {
            this.clockText = clockText;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (getAllFrames().length > 0) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            GradientPaint bg = new GradientPaint(0, 0, new Color(8, 12, 16), 0, getHeight(), new Color(20, 26, 34));
            g2.setPaint(bg);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int panelWidth = Math.min(960, getWidth() - 120);
            int panelHeight = 280;
            int panelX = (getWidth() - panelWidth) / 2;
            int panelY = (getHeight() - panelHeight) / 2;

            g2.setColor(new Color(18, 24, 32));
            g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 36, 36);
            g2.setColor(new Color(58, 77, 98));
            g2.setStroke(new BasicStroke(3f));
            g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 36, 36);

            drawSevenSegmentClock(g2, panelX + 36, panelY + 30, panelWidth - 72, panelHeight - 90);

            g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
            String subtitle = "DIGITAL HOME CLOCK";
            int sx = panelX + (panelWidth - g2.getFontMetrics().stringWidth(subtitle)) / 2;
            g2.setColor(new Color(127, 145, 166));
            g2.drawString(subtitle, sx, panelY + panelHeight - 22);

            g2.dispose();
        }

        private void drawSevenSegmentClock(Graphics2D g2, int x, int y, int width, int height) {
            int[] slotUnits = {2, 2, 1, 2, 2, 1, 2, 2};
            int totalUnits = 0;
            for (int slotUnit : slotUnits) {
                totalUnits += slotUnit;
            }

            int gap = Math.max(8, width / 140);
            int usableWidth = width - gap * (slotUnits.length - 1);
            int unitWidth = usableWidth / totalUnits;

            int digitW = Math.max(42, unitWidth * 2);
            int digitH = Math.max(110, height);
            int colonW = Math.max(16, unitWidth);

            int posX = x;
            for (int i = 0; i < clockText.length() && i < 8; i++) {
                char c = clockText.charAt(i);
                if (Character.isDigit(c)) {
                    drawDigit(g2, posX, y, digitW, digitH, c - '0');
                    posX += digitW + gap;
                } else {
                    drawColon(g2, posX, y, colonW, digitH, c == ':');
                    posX += colonW + gap;
                }
            }
        }

        private void drawColon(Graphics2D g2, int x, int y, int w, int h, boolean on) {
            Color off = new Color(57, 120, 82, 70);
            Color onColor = new Color(95, 255, 145);

            int dotSize = Math.max(8, w / 2);
            int cx = x + (w - dotSize) / 2;
            int topY = y + h / 3 - dotSize / 2;
            int botY = y + (2 * h) / 3 - dotSize / 2;

            g2.setColor(on ? new Color(57, 255, 125, 45) : off);
            g2.fillOval(cx - 2, topY - 2, dotSize + 4, dotSize + 4);
            g2.fillOval(cx - 2, botY - 2, dotSize + 4, dotSize + 4);

            g2.setColor(on ? onColor : off);
            g2.fillOval(cx, topY, dotSize, dotSize);
            g2.fillOval(cx, botY, dotSize, dotSize);
        }

        private void drawDigit(Graphics2D g2, int x, int y, int w, int h, int digit) {
            boolean[][] digitMap = {
                    {true, true, true, false, true, true, true},
                    {false, false, true, false, false, true, false},
                    {true, false, true, true, true, false, true},
                    {true, false, true, true, false, true, true},
                    {false, true, true, true, false, true, false},
                    {true, true, false, true, false, true, true},
                    {true, true, false, true, true, true, true},
                    {true, false, true, false, false, true, false},
                    {true, true, true, true, true, true, true},
                    {true, true, true, true, false, true, true}
            };

            int t = Math.max(10, Math.min(w, h) / 8);
            int midY = y + h / 2;

            int[][] seg = {
                    {x + t, y, w - 2 * t, t},
                    {x, y + t, t, h / 2 - t},
                    {x + w - t, y + t, t, h / 2 - t},
                    {x + t, midY - t / 2, w - 2 * t, t},
                    {x, midY + t / 2, t, h / 2 - t},
                    {x + w - t, midY + t / 2, t, h / 2 - t},
                    {x + t, y + h - t, w - 2 * t, t}
            };

            for (int i = 0; i < seg.length; i++) {
                boolean on = digitMap[digit][i];
                drawSegment(g2, seg[i][0], seg[i][1], seg[i][2], seg[i][3], on);
            }
        }

        private void drawSegment(Graphics2D g2, int x, int y, int w, int h, boolean on) {
            Color off = new Color(57, 120, 82, 70);
            Color onColor = new Color(95, 255, 145);

            if (on) {
                g2.setColor(new Color(57, 255, 125, 40));
                g2.fillRoundRect(x - 2, y - 2, w + 4, h + 4, h, h);
            }

            g2.setColor(on ? onColor : off);
            g2.fillRoundRect(x, y, w, h, h, h);
        }
    }
}
