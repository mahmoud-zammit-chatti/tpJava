package SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class Client {
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private Reader reader;

    public synchronized void connect(String host, int port, String clientId, Consumer<String> onMessage) throws IOException {
        if (isConnected()) {
            return;
        }

        socket = new Socket(host, port);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);

        // Read and ignore server prompt used during registration handshake.
        br.readLine();
        pw.println(clientId);
        pw.flush();

        reader = new Reader(socket, onMessage);
        reader.start();
    }

    public synchronized void sendPrivateMessage(String destinationId, String message) {
        if (!isConnected()) {
            throw new IllegalStateException("Client is not connected");
        }

        pw.println(destinationId + "|" + message);
        pw.flush();
    }

    public synchronized boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public synchronized void disconnect() {
        if (reader != null) {
            reader.interrupt();
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ignored) {
                // Ignore close errors while disconnecting.
            }
        }
        socket = null;
        br = null;
        pw = null;
        reader = null;
    }

    static void main() {
        System.out.println("Client API ready. Use the Swing UI to connect and chat.");

    }
}
