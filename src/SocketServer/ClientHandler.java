package SocketServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    Socket socket;
            public ClientHandler(Socket socket) {
                this.socket = socket;
    }

    @Override
    public void run() {
        try {

        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        pw.println("Enter your client id");
        pw.flush();

        String clientId = br.readLine();
        if (clientId == null || clientId.trim().isEmpty()) {
            socket.close();
            return;
        }

        CustomSocket cs = new CustomSocket(socket, clientId.trim());
        ClientManager.clients.add(cs);

        System.out.println("Client connected with id: " + clientId);

            Reader reader = new Reader(cs, clientId.trim());

            reader.start();


        }catch (Exception e) {
            throw new RuntimeException("client handler error "+e);
        }
    }
}
