package com.example.distributedsearch.server;

import com.example.distributedsearch.utils.JSONUtils;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.List;

public class ServerA {
    public static void main(String[] args) {
        int port = 5000; // Porta para o cliente
        int serverBPort = 6000; // Porta do Servidor B
        String serverBHost = "localhost";

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor A aguardando conexões na porta " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado.");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    // Recebe a string de busca
                    String query = in.readLine();
                    System.out.println("Recebida busca: " + query);

                    // Busca no arquivo completo
                    List<String> allResults = JSONUtils.searchInJSON("src/main/resources/arxiv.json", query);

                    // Divide os resultados em duas partes (metade para A, metade para B)
                    int middle = allResults.size() / 2;
                    List<String> resultsA = allResults.subList(0, middle);

                    // Conecta ao Servidor B para buscar a outra metade
                    try (Socket serverBSocket = new Socket(serverBHost, serverBPort);
                         PrintWriter outToB = new PrintWriter(serverBSocket.getOutputStream(), true);
                         BufferedReader inFromB = new BufferedReader(new InputStreamReader(serverBSocket.getInputStream()))) {

                        outToB.println(query); // Envia busca para o Servidor B
                        List<String> resultsB = inFromB.lines().toList();

                        // Combina os resultados e envia ao cliente
                        resultsA.addAll(resultsB);
                        for (String result : resultsA) {
                            out.println(result);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}