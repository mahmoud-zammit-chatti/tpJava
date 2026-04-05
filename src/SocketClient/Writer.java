package SocketClient;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Writer extends Thread {

    Socket socket;
    public Writer(Socket socket) {
        this.socket = socket;
    }

    @Override
        public void run() {
        try {

        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        Scanner sc = new Scanner(System.in);

        while(true){
            pw.println(sc.nextLine());
            pw.flush();
        }
        }catch (Exception e){
            System.out.println("client writer error "+e);
        }
    }
}
