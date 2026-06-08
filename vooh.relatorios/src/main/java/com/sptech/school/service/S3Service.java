package com.sptech.school.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sptech.school.config.S3Connection;
import com.sptech.school.config.S3Provider;
import com.sptech.school.dto.AlertaDooh;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Paths;
import java.util.*;

public class S3Service {

    private static final S3Client     client = S3Provider.criarCliente();
    private static final String       bucket = S3Connection.getBUCKET_NAME();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final List<AlertaDooh> historicoDiario = new ArrayList<>();

    public static List<AlertaDooh> lerAlertas(String chaveS3) {
        try {
            GetObjectRequest req = GetObjectRequest.builder().bucket(bucket).key(chaveS3).build();
            ResponseBytes<GetObjectResponse> obj = client.getObjectAsBytes(req);
            String json = obj.asUtf8String();

            var root = mapper.readTree(json);
            List<AlertaDooh> alertas = new ArrayList<>();

            if (root.has("telemetria_detalhada")) {
                alertas = mapper.readerForListOf(AlertaDooh.class).readValue(root.get("telemetria_detalhada"));

                Map<String, String> zonaDoDisplay = new HashMap<>();
                var zonas = root.path("zonas");
                zonas.fieldNames().forEachRemaining(zonaId -> {
                    var rankingDisplays = zonas.path(zonaId).path("grafico_ranking_displays");
                    for (var d : rankingDisplays) {
                        if (d.has("idDisplay")) {
                            zonaDoDisplay.put(String.valueOf(d.get("idDisplay").asInt()), zonaId);
                        }
                    }
                });

                for (AlertaDooh a : alertas) {
                    if (a.getDisplayId() != null) {
                        a.setZona(zonaDoDisplay.getOrDefault(a.getDisplayId(), "—"));
                    }
                    a.setEmpresa("TechSolutions");

                    String componenteMaior = "—";
                    if (a.getZona() != null && !a.getZona().equals("—")) {
                        var causaRaiz = zonas.path(a.getZona()).path("grafico_causa_raiz");
                        int maiorValor = 0;
                        var campos = causaRaiz.fields();
                        while (campos.hasNext()) {
                            var entry = campos.next();
                            if (entry.getValue().asInt() > maiorValor) {
                                maiorValor = entry.getValue().asInt();
                                componenteMaior = entry.getKey();
                            }
                        }
                    }
                    a.setComponente(componenteMaior);
                    a.setMensagem("Status " + (a.getNivel() != null ? a.getNivel() : "—") + " — " + componenteMaior);
                }
            }

            historicoDiario.addAll(alertas);
            return alertas;

        } catch (NoSuchKeyException e) {
            System.out.println("[S3Service] Nenhum alerta pendente no bucket.");
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("[S3Service] Erro ao ler S3: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void uploadArquivo(String caminhoLocal, String chaveS3) {
        try {
            PutObjectRequest req = PutObjectRequest.builder().bucket(bucket).key(chaveS3).build();
            client.putObject(req, RequestBody.fromFile(Paths.get(caminhoLocal)));
            System.out.println("[S3Service] Upload concluído → " + chaveS3);
        } catch (Exception e) {
            System.err.println("[S3Service] Erro no upload: " + e.getMessage());
        }
    }

    public static List<AlertaDooh> getHistoricoDiario() {
        return Collections.unmodifiableList(historicoDiario);
    }
}
