package com.example.distributedsearch.server;

import com.example.distributedsearch.utils.JSONUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerB {
    public static void main(String[] args) {
        int port = 6000; // Porta para comunicação com Servidor A

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor B aguardando conexões na porta " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Conexão recebida do Servidor A.");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    // Recebe a string de busca
                    String query = in.readLine();
                    System.out.println("Recebida busca: " + query);

                    // Realiza busca no arquivo completo
                    List<String> results = JSONUtils.searchInJSON("src/main/resources/arxiv.json", query);

                    // Envia resultados de volta ao Servidor A
                    for (String result : results) {
                        out.println(result);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}