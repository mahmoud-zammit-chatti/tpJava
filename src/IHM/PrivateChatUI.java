package IHM;

import SocketClient.Client;
import SocketServer.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class PrivateChatUI extends JFrame {

    private final Server server;
    private final JButton startServerButton;
    private final JLabel serverStatusLabel;
    private final ClientPanel[] clientPanels;

    public PrivateChatUI() {
        super("Private Chat (3 Clients)");
        this.server = new Server(9001, 3);
        this.startServerButton = new JButton("Start Server");
        this.serverStatusLabel = new JLabel("Server: stopped");
        this.clientPanels = new ClientPanel[3];

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1200, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildClientsArea(), BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdown();
            }
        });
    }

    private JPanel buildTopBar() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        startServerButton.addActionListener(e -> startServer());

        top.add(startServerButton);
        top.add(serverStatusLabel);
        return top;
    }

    private JPanel buildClientsArea() {
        JPanel clientsArea = new JPanel(new GridLayout(1, 3, 10, 10));
        for (int i = 0; i < 3; i++) {
            clientPanels[i] = new ClientPanel("Client " + (i + 1));
            clientsArea.add(clientPanels[i]);
        }
        return clientsArea;
    }

    private void startServer() {
        if (server.isRunning()) {
            return;
        }

        try {
            server.start();
            serverStatusLabel.setText("Server: running on port 9001");
            startServerButton.setEnabled(false);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Unable to start server: " + ex.getMessage(), "Server Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void shutdown() {
        for (ClientPanel panel : clientPanels) {
            panel.disconnect();
        }
        server.stop();
    }

    private static class ClientPanel extends JPanel {

        private final Client client;
        private final JTextField idField;
        private final JTextField destinationField;
        private final JTextField messageField;
        private final JTextArea chatArea;
        private final JButton connectButton;
        private final JButton sendButton;

        ClientPanel(String title) {
            this.client = new Client();
            this.idField = new JTextField();
            this.destinationField = new JTextField();
            this.messageField = new JTextField();
            this.chatArea = new JTextArea();
            this.connectButton = new JButton("Connect");
            this.sendButton = new JButton("Send");

            setBorder(BorderFactory.createTitledBorder(title));
            setLayout(new BorderLayout(5, 5));

            add(buildConfigPanel(), BorderLayout.NORTH);
            add(buildChatPanel(), BorderLayout.CENTER);
            add(buildSendPanel(), BorderLayout.SOUTH);

            connectButton.addActionListener(e -> connect());
            sendButton.addActionListener(e -> sendMessage());
            sendButton.setEnabled(false);
            chatArea.setEditable(false);
        }

        private JPanel buildConfigPanel() {
            JPanel config = new JPanel(new GridLayout(3, 2, 5, 5));
            config.add(new JLabel("ID:"));
            config.add(idField);
            config.add(new JLabel("Destination ID:"));
            config.add(destinationField);
            config.add(new JLabel(""));
            config.add(connectButton);
            return config;
        }

        private JScrollPane buildChatPanel() {
            return new JScrollPane(chatArea);
        }

        private JPanel buildSendPanel() {
            JPanel send = new JPanel(new BorderLayout(5, 5));
            send.add(messageField, BorderLayout.CENTER);
            send.add(sendButton, BorderLayout.EAST);
            return send;
        }

        private void connect() {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                append("Please enter a client id before connecting.");
                return;
            }

            try {
                client.connect("127.0.0.1", 9001, id, this::append);
                append("Connected as id " + id);
                idField.setEditable(false);
                connectButton.setEnabled(false);
                sendButton.setEnabled(true);
            } catch (IOException ex) {
                append("Connection failed: " + ex.getMessage());
            }
        }

        private void sendMessage() {
            String destination = destinationField.getText().trim();
            String message = messageField.getText().trim();

            if (destination.isEmpty()) {
                append("Enter a destination id.");
                return;
            }
            if (message.isEmpty()) {
                return;
            }

            try {
                client.sendPrivateMessage(destination, message);
                append("Me -> " + destination + ": " + message);
                messageField.setText("");
            } catch (Exception ex) {
                append("Send failed: " + ex.getMessage());
            }
        }

        private void append(String line) {
            SwingUtilities.invokeLater(() -> {
                chatArea.append(line + "\n");
                chatArea.setCaretPosition(chatArea.getDocument().getLength());
            });
        }

        void disconnect() {
            client.disconnect();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PrivateChatUI ui = new PrivateChatUI();
            ui.setVisible(true);
        });
    }
}

