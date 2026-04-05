package SocketClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

public class Reader extends Thread{

    private final Socket socket;
    private final Consumer<String> onMessage;

    Reader(Socket socket, Consumer<String> onMessage){
        this.socket = socket;
        this.onMessage = onMessage;
    }

    Reader(Socket socket){
        this(socket, null);
    }

    @Override
    public void run() {

        try {

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (true){
            String line = br.readLine();
            if (line == null) {
                break;
            }

            if (onMessage != null) {
                onMessage.accept(line);
            } else {
                System.out.println("client : " + line);
            }
        }
        }catch (Exception e){
            System.out.println("server reader error "+e);
        }






    }
}
