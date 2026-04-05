package SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int port;
    private final int maxClients;
    private  boolean running;
    private ServerSocket serverSocket;
    private Thread acceptThread;

    public Server() {
        this(9001, 3);
    }

    public Server(int port, int maxClients) {
        this.port = port;
        this.maxClients = maxClients;
    }

    public synchronized void start() throws IOException {
        if (running) {
            return;
        }
        running = true;
        serverSocket = new ServerSocket(port);
        System.out.println("Server is on port " + port + " ...");

        acceptThread = new Thread(() -> {
            int clientNumber = 0;
            while (running && clientNumber < maxClients) {
                try {
                    Socket socket = serverSocket.accept();
                    clientNumber++;
                    System.out.println("client connected ...");
                    new ClientHandler(socket).start();
                } catch (IOException e) {
                    if (running) {
                        System.out.println("Server accept error: " + e.getMessage());
                    }
                }
            }
            running = false;
            closeServerSocket();
            System.out.println("server is offline...");
        }, "chat-server-accept");
        acceptThread.start();
    }

    public synchronized void stop() {
        running = false;
        closeServerSocket();
    }

    public boolean isRunning() {
        return running;
    }

    private synchronized void closeServerSocket() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ignored) {
                // Ignore close errors while shutting down.
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException("Unable to start server", e);
        }
    }



}
