package LesThreads;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Animation extends JPanel {

    private final JButton stopButton;
    private final JButton startButton;
    private final PanelAnimation panelAnimation;

    private final Object stateLock = new Object();
    private final Object barrierLock = new Object();

    private final CircleState circleOne = new CircleState(80, 300, 90, new Color(33, 33, 33));
    private final CircleState circleTwo = new CircleState(860, 300, 90, new Color(245, 245, 245));

    private boolean running = false;
    private boolean workersStarted = false;

    private int targetX;
    private int targetY;
    private boolean targetReady = false;
    private boolean targetVisible = false;

    private int arrivedCount = 0;
    private int returnedCount = 0;
    private int arriveGeneration = 0;
    private int returnGeneration = 0;

    public Animation() {
        setLayout(new BorderLayout(0, 8));
        setBackground(new Color(236, 239, 244));

        panelAnimation = new PanelAnimation();
        stopButton = new JButton("STOP");
        startButton = new JButton("START");

        styleButton(startButton, new Color(46, 204, 113));
        styleButton(stopButton, new Color(231, 76, 60));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        controls.setBackground(new Color(47, 53, 66));

        JLabel title = new JLabel("TP4 - Animation Threads");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));

        controls.add(title);
        controls.add(startButton);
        controls.add(stopButton);

        startButton.addActionListener(e -> startAnimation());
        stopButton.addActionListener(e -> stopAnimation());

        panelAnimation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                publishTarget(e.getX(), e.getY());
            }
        });

        add(controls, BorderLayout.NORTH);
        add(panelAnimation, BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Color bg) {
        button.setFocusPainted(false);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
    }

    private void startAnimation() {
        synchronized (stateLock) {
            running = true;
            if (!workersStarted) {
                workersStarted = true;
                new CircleWorker(circleOne, "circle-worker-1").start();
                new CircleWorker(circleTwo, "circle-worker-2").start();
            }
            stateLock.notifyAll();
        }
    }

    private void stopAnimation() {
        synchronized (stateLock) {
            running = false;
            targetReady = false;
            targetVisible = false;
            circleOne.resetToInitial();
            circleTwo.resetToInitial();
            stateLock.notifyAll();
        }
        synchronized (barrierLock) {
            arrivedCount = 0;
            returnedCount = 0;
            arriveGeneration++;
            returnGeneration++;
            barrierLock.notifyAll();
        }
        panelAnimation.repaint();
    }

    private void publishTarget(int x, int y) {
        synchronized (stateLock) {
            if (!running || targetReady) {
                return;
            }
            targetX = x;
            targetY = y;
            targetReady = true;
            targetVisible = true;
            stateLock.notifyAll();
        }
        panelAnimation.repaint();
    }

    class CircleWorker extends Thread {
        private static final int STEP = 7;
        private final CircleState circle;

        CircleWorker(CircleState circle, String name) {
            super(name);
            this.circle = circle;
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                int localTargetX;
                int localTargetY;
                try {
                    synchronized (stateLock) {
                        while (!running || !targetReady) {
                            stateLock.wait();
                        }
                        localTargetX = targetX;
                        localTargetY = targetY;
                    }

                    if (!moveCircleTo(localTargetX, localTargetY)) {
                        continue;
                    }

                    synchronized (barrierLock) {
                        int myArriveGeneration = arriveGeneration;
                        arrivedCount++;
                        if (arrivedCount < 2) {
                            while (running && myArriveGeneration == arriveGeneration) {
                                barrierLock.wait();
                            }
                        } else {
                            arrivedCount = 0;
                            arriveGeneration++;
                            barrierLock.notifyAll();
                        }
                    }

                    if (!moveCircleTo(circle.initX, circle.initY)) {
                        continue;
                    }

                    synchronized (barrierLock) {
                        int myReturnGeneration = returnGeneration;
                        returnedCount++;
                        if (returnedCount < 2) {
                            while (running && myReturnGeneration == returnGeneration) {
                                barrierLock.wait();
                            }
                        } else {
                            returnedCount = 0;
                            returnGeneration++;
                            synchronized (stateLock) {
                                targetReady = false;
                                targetVisible = false;
                                stateLock.notifyAll();
                            }
                            barrierLock.notifyAll();
                        }
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        private boolean moveCircleTo(int tx, int ty) throws InterruptedException {
            while (true) {
                synchronized (stateLock) {
                    if (!running) {
                        return false;
                    }

                    double dx = tx - circle.fx;
                    double dy = ty - circle.fy;
                    double distance = Math.hypot(dx, dy);

                    if (distance <= STEP) {
                        circle.fx = tx;
                        circle.fy = ty;
                        circle.syncIntPosition();
                        panelAnimation.repaint();
                        return true;
                    }

                    circle.fx += STEP * dx / distance;
                    circle.fy += STEP * dy / distance;
                    circle.syncIntPosition();
                }

                panelAnimation.repaint();
                Thread.sleep(20);
            }
        }
    }

    static class CircleState {
        int x;
        int y;
        double fx;
        double fy;
        final int initX;
        final int initY;
        final int size;
        final Color color;

        CircleState(int initX, int initY, int size, Color color) {
            this.initX = initX;
            this.initY = initY;
            this.size = size;
            this.color = color;
            resetToInitial();
        }

        void resetToInitial() {
            fx = initX;
            fy = initY;
            syncIntPosition();
        }

        void syncIntPosition() {
            x = (int) Math.round(fx);
            y = (int) Math.round(fy);
        }
    }

    class PanelAnimation extends JPanel {

        PanelAnimation() {
            setBackground(new Color(223, 228, 234));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int localTargetX;
            int localTargetY;
            boolean drawTarget;

            synchronized (stateLock) {
                localTargetX = targetX;
                localTargetY = targetY;
                drawTarget = targetVisible;

                GradientPaint bg = new GradientPaint(0, 0, new Color(238, 242, 247), 0, getHeight(), new Color(214, 222, 235));
                g2.setPaint(bg);
                g2.fillRect(0, 0, getWidth(), getHeight());

                if (drawTarget) {
                    int boxSize = 26;
                    int rx = localTargetX - boxSize / 2;
                    int ry = localTargetY - boxSize / 2;
                    g2.setColor(new Color(41, 128, 185));
                    g2.setStroke(new BasicStroke(3f));
                    g2.drawRect(rx, ry, boxSize, boxSize);
                }

                g2.setColor(circleOne.color);
                g2.fillOval(circleOne.x, circleOne.y, circleOne.size, circleOne.size);
                g2.setColor(new Color(255, 255, 255, 90));
                g2.drawOval(circleOne.x + 10, circleOne.y + 10, circleOne.size - 20, circleOne.size - 20);

                g2.setColor(circleTwo.color);
                g2.fillOval(circleTwo.x, circleTwo.y, circleTwo.size, circleTwo.size);
                g2.setColor(new Color(55, 55, 55, 110));
                g2.drawOval(circleTwo.x + 10, circleTwo.y + 10, circleTwo.size - 20, circleTwo.size - 20);
            }

            g2.dispose();
        }
    }
}
