package com.mycompany.socketserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class ClientHandler extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {

        this.socket = socket;

        try {

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));

            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getUsername() {
        return username;
    }

    public void run() {

        try {

            username = in.readLine();

            if (username == null || username.trim().isEmpty()) {
                socket.close();
                return;
            }

            ClientManager.addClient(this);
            ClientManager.sendUserList();

            ClientManager.broadcast(username + " tham gia chat", this);

            String message;

            while ((message = in.readLine()) != null) {

                if (handlePrivateMessage(message)) {
                    continue;
                }

                System.out.println("[" + username + "] " + message);

                ClientManager.broadcast(message, this);
                
                
            }

        } catch (Exception e) {

            System.out.println(username + " bị ngắt kết nối");

        } finally {

            ClientManager.removeClient(this);
            ClientManager.sendUserList();

            ClientManager.broadcast(username + " rời phòng chat", this);

            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }

    private boolean handlePrivateMessage(String message) {
        if (message == null) {
            return false;
        }

        String trimmed = message.trim();
        if (trimmed.isEmpty()) {
            return false;
        }

        if (trimmed.startsWith("@")) {
            String[] parts = trimmed.split(" ", 2);
            String targetUser = parts[0].substring(1);
            String privateMsg = parts.length > 1 ? parts[1] : "";
            ClientManager.sendPrivateMessage(username, targetUser, privateMsg);
            System.out.println("[PM] " + username + " -> " + targetUser + ": " + privateMsg);
            return true;
        }

        String lower = trimmed.toLowerCase();
        String command = null;
        if (lower.startsWith("/pm ")) {
            command = "/pm";
        } else if (lower.startsWith("/w ")) {
            command = "/w";
        } else if (lower.startsWith("/msg ")) {
            command = "/msg";
        } else if (lower.startsWith("/whisper ")) {
            command = "/whisper";
        }

        if (command == null) {
            return false;
        }

        String body = trimmed.substring(command.length()).trim();
        int spaceIndex = body.indexOf(' ');
        if (spaceIndex <= 0) {
            return true;
        }

        String targetUser = body.substring(0, spaceIndex).trim();
        String privateMsg = body.substring(spaceIndex + 1).trim();
        ClientManager.sendPrivateMessage(username, targetUser, privateMsg);
        System.out.println("[PM] " + username + " -> " + targetUser + ": " + privateMsg);
        return true;
    }
}
