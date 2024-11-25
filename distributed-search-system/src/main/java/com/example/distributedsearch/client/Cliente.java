package com.example.distributedsearch.client;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 5000; // Porta do Servidor A

        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado ao Servidor A. Digite a string para busca:");
            String query = reader.readLine();

            // Envia a busca para o servidor A
            out.println(query);

            // Recebe o resultado da busca
            System.out.println("Resultado da busca:");
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
