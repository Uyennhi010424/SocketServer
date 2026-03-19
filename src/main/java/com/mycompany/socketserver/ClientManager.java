package com.mycompany.socketserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager {

    public static List<ClientHandler> clients = new ArrayList<>();

    public static void addClient(ClientHandler client) {
        clients.add(client);
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static void broadcast(String message, ClientHandler sender) {
        // gửi cho tất cả (trừ người gửi)
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage("[" + sender.getUsername() + "]: " + message);
            }
        }
    }

    public static void sendUserList() {

        StringBuilder users = new StringBuilder("USERS:");

        for (ClientHandler client : clients) {
            users.append(client.getUsername()).append(",");
        }

        for (ClientHandler client : clients) {
            client.sendMessage(users.toString());
        }
    }

    public static void sendPrivateMessage(String sender, String receiver, String message) {

        for (ClientHandler client : clients) {

            if (client.getUsername().equals(receiver)) {

                client.sendMessage("PM: " + sender + ": " + message);

                break;
            }
        }
    }
}
