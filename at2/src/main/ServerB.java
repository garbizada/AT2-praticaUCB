package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServerB {
    private static final String DATASET_PATH = "src/main/resources/arxiv_part2.json"; // Caminho do dataset

    public static void main(String[] args) {
        int port = 8081;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor B aguardando conexões na porta " + port + "...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    System.out.println("Conexão estabelecida com o Servidor A.");

                    // Receber a query enviada pelo Servidor A
                    String query = in.readLine();
                    System.out.println("Recebida string de busca: " + query);

                    // Realizar a busca no dataset
                    List<String> results = searchInDataset(query);

                    // Enviar os resultados para o Servidor A
                    for (String result : results) {
                        out.println(result);
                    }

                    // Finalizar a conexão
                    out.println("END"); // Marcador para indicar fim dos resultados
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Realiza a busca no dataset por artigos que contenham a substring no título ou na introdução.
     *
     * @param query Substring de busca
     * @return Lista de strings com os resultados encontrados
     */
    private static List<String> searchInDataset(String query) {
        List<String> results = new ArrayList<>();

        try {
            // Ler o arquivo JSON
            String content = new String(Files.readAllBytes(Paths.get(DATASET_PATH)));
            JSONArray articles = new JSONArray(content);

            // Iterar sobre os artigos e buscar por substrings
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.optString("title", "").toLowerCase();
                String abstractText = article.optString("abstract", "").toLowerCase();

                // Verificar se a substring está no título ou na introdução
                if (title.contains(query.toLowerCase()) || abstractText.contains(query.toLowerCase())) {
                    results.add(article.toString()); // Adicionar o artigo completo ao resultado
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o dataset: " + e.getMessage());
        }

        return results;
    }
}