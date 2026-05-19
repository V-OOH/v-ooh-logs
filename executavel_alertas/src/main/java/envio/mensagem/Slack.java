package envio.mensagem;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 *
 * @author Diego Brito <diego.lima@bandtec.com.br>
 */
public class Slack {

    private static HttpClient client = HttpClient.newHttpClient();
//    private static final String URL = "INSIRA SUA URL AQUI (WEBHOOK)";

    public static void sendMessage(String urlWebhook, JSONObject content) throws IOException, InterruptedException {

        if (urlWebhook == null || urlWebhook.trim().isEmpty()) {
            System.out.println("Erro: a URL Webhook está vazia!");
        }

        // Cria a requisição HTTP passando a URL como parâmetro
        HttpRequest request = HttpRequest.newBuilder(URI.create(urlWebhook))
                .header("Content-Type", "application/json") // Padrão do Slack é Content-Type
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build(); // cria o HttpResponse

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(String.format("Status: %s", response.statusCode()));

        System.out.println(String.format("Response: %s", response.body()));
    }
}

