package SocketServer;

import java.io.PrintWriter;
import java.net.Socket;
import java.time.Instant;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager {
    public static CopyOnWriteArrayList<CustomSocket> clients = new CopyOnWriteArrayList<>();


    public static void diffuseMessage(String msg, String senderId, Instant time, String destination){

        for(int i= 0;i<clients.size();i++){
            Socket socket=clients.get(i).socket;

            try {
                if(clients.get(i).id.equals(destination)){

                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.println(msg+" from client with id: "+senderId+" at "+time);
                pw.flush();
                }
            }catch (Exception e){

                throw new RuntimeException("error in diffuse message "+e);
            }
        }

    }

    public static void removeBySocket(Socket socket) {
        clients.removeIf(client -> client.socket == socket);
    }


}
