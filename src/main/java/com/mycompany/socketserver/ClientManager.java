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

    // kiểm tra có tag @ không
    if (message.startsWith("@")) {

        String[] parts = message.split(" ", 2);
        String targetUser = parts[0].substring(1); // bỏ dấu @
        String realMessage = parts.length > 1 ? parts[1] : "";

        for (ClientHandler client : clients) {

            if (client.getUsername().equals(targetUser)) {
                client.sendMessage("(Private) " + sender.getUsername() + ": " + realMessage);
                return;
            }

        }

    }

    // nếu không phải @username thì gửi cho tất cả
    for (ClientHandler client : clients) {
        if (client != sender) {
            client.sendMessage(sender.getUsername() + ": " + message);
        }
    }
}

    public static void sendPrivateMessage(String sender, String receiver, String message) {

        for (ClientHandler client : clients) {

            if (client.getUsername().equals(receiver)) {

                client.sendMessage("(Private) " + sender + ": " + message);

                break;
            }
        }
    }
}
