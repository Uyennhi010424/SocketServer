
package com.mycompany.socketserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class ClientHandler extends Thread{

     private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {

        this.socket = socket;

        try {

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void run() {

        try {

            username = in.readLine();

            ClientManager.broadcast(username + " joined chat", this);

            String message;

            while ((message = in.readLine()) != null) {

                System.out.println(username + ": " + message);

                ClientManager.broadcast(username + ": " + message, this);
            }

        } catch (Exception e) {

        } finally {

            ClientManager.removeClient(this);

            ClientManager.broadcast(username + " left chat", this);

            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }
}
