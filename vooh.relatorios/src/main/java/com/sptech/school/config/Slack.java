package com.sptech.school.config;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;

public class Slack {

    private static final Dotenv dotenv = Dotenv.configure().directory("vooh.relatorios").filename(".env.dev").ignoreIfMissing().load();
    private static final String WEBHOOK_URL = dotenv.get("SLACK_BASE_URL");
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient httpClient = new OkHttpClient();

    public void enviarAlerta(String mensagem) {
        String payload = String.format("{\"text\": \"%s\"}",
                mensagem
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
        );

        Request request = new Request.Builder()
                .url(WEBHOOK_URL)
                .post(RequestBody.create(payload, JSON))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("[Slack] Erro ao enviar: " + response.code());
            } else {
                System.out.println("[Slack] Notificação enviada com sucesso.");
            }
        } catch (IOException e) {
            System.err.println("[Slack] Falha na conexão: " + e.getMessage());
        }
    }
}
