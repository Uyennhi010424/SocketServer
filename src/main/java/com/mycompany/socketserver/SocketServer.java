package com.mycompany.socketserver;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) {
<<<<<<< HEAD
        // hehee
    }// kjdfvbdhvods
}
=======

        try {

            ServerSocket serverSocket = new ServerSocket(1115);
            System.out.println("Server started...");

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler handler = new ClientHandler(socket);

                handler.start(); // chỉ start thread

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
>>>>>>> 990fc661a5b4e00fd4e941582eb55016bc37d8d9
