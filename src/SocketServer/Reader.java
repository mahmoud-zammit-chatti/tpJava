package SocketServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.Instant;

public class Reader extends Thread{

    private final Socket socket;
    private final String senderId;
    Reader(CustomSocket customSocket, String senderId){
        this.socket = customSocket.socket;
        this.senderId = senderId;
    }

    @Override
    public void run() {

        try {

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (true){
        String raw = br.readLine();
            if (raw == null) {
                break;
            }

            String[] parts = raw.split("\\|", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                continue;
            }

            String destination = parts[0].trim();
            String msg = parts[1].trim();
            ClientManager.diffuseMessage(msg, senderId, Instant.now(), destination);
        }
        }catch (Exception e){
            System.out.println("server reader error "+e);
        } finally {
            ClientManager.removeBySocket(socket);
        }






    }
}
