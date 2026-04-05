package SocketServer;

import java.net.Socket;

public class CustomSocket  {

    final String id;
    final Socket socket;

    CustomSocket(Socket socket, String id) {
        this.socket = socket;
        this.id = id;

    }

}
