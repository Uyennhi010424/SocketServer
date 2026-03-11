
package com.mycompany.socketserver;

import java.net.ServerSocket;
import java.net.Socket;
public class SocketServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Server started...");

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler handler = new ClientHandler(socket);
                ClientManager.addClient(handler);

                handler.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
