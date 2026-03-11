package com.mycompany.socketserver;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {
     public static List<ClientHandler> clients = new ArrayList<>();

    public static void addClient(ClientHandler client) {
        clients.add(client);
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static void broadcast(String message, ClientHandler sender) {

        for (ClientHandler client : clients) {

            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}
