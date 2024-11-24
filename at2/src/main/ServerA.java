package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerA {
    public static void main(String[] args) {
        int port = 8080;
        String serverBHost = "localhost";
        int serverBPort = 8081;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor A aguardando conexões...");
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
                     Socket serverBSocket = new Socket(serverBHost, serverBPort);
                     PrintWriter serverBOut = new PrintWriter(serverBSocket.getOutputStream(), true);
                     BufferedReader serverBIn = new BufferedReader(new InputStreamReader(serverBSocket.getInputStream()))) {

                    String query = clientIn.readLine();
                    System.out.println("Busca recebida do cliente: " + query);

                    // Enviar consulta ao servidor B
                    serverBOut.println(query);

                    // Busca localmente
                    List<String> localResults = search("arxiv_part1.json", query);

                    // Receber resultados do servidor B
                    String resultFromB;
                    while ((resultFromB = serverBIn.readLine()) != null) {
                        localResults.add(resultFromB);
                    }

                    // Enviar resultados ao cliente
                    for (String result : localResults) {
                        clientOut.println(result);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> search(String filePath, String query) {
        // Implementação da busca no arquivo JSON
        // Retornar uma lista de resultados como strings
    }
}

