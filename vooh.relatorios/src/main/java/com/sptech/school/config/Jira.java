package com.sptech.school.config;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;

public class Jira {

    private static final Dotenv dotenv = Dotenv.configure().directory("vooh.relatorios").filename(".env.dev").ignoreIfMissing().load();

    private static final String BASE_URL  = dotenv.get("JIRA_BASE_URL");
    private static final String EMAIL     = dotenv.get("JIRA_EMAIL");
    private static final String API_TOKEN = dotenv.get("JIRA_API_TOKEN");
    private static final MediaType JSON   = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient httpClient = new OkHttpClient();

    private String basicAuth() {
        return "Basic " + Base64.getEncoder().encodeToString((EMAIL + ":" + API_TOKEN).getBytes());
    }

    public String createIssue(String projectKey, String summary, String description,
                              String issueType, String priority, String mac) throws IOException {
        String body = String.format("""
            {
              "fields": {
                "project":   { "key": "%s" },
                "summary":   "%s",
                "issuetype": { "name": "%s" },
                "priority":  { "name": "%s" },
                "description": {
                  "type": "doc",
                  "version": 1,
                  "content": [
                    {
                      "type": "paragraph",
                      "content": [
                        { "type": "text", "text": "%s" }
                      ]
                    }
                  ]
                }
              }
            }
            """,
                projectKey, escaparJson(summary), issueType, priority, escaparJson(description)
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "/rest/api/3/issue")
                .header("Authorization", basicAuth())
                .header("Accept", "application/json")
                .post(RequestBody.create(body, JSON))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Jira API error " + response.code()
                        + ": " + (response.body() != null ? response.body().string() : ""));
            }
            return response.body() != null ? response.body().string() : "{}";
        }
    }

    public String extractKey(String jsonResponse) {
        int idx = jsonResponse.indexOf("\"key\"");
        if (idx == -1) return "N/A";
        int start = jsonResponse.indexOf("\"", idx + 6) + 1;
        int end   = jsonResponse.indexOf("\"", start);
        return jsonResponse.substring(start, end);
    }

    private String escaparJson(String texto) {
        if (texto == null) return "";
        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
