package com.sptech.school.app;

import com.sptech.school.controller.JiraController;
import com.sptech.school.dto.AlertaDooh;
import com.sptech.school.service.AlertaProcessorService;
import com.sptech.school.service.PdfRelatorioService;
import com.sptech.school.service.S3Service;
import com.sptech.school.util.DateUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class App {

    private static final String CHAVE_ALERTAS_S3 = "client/dados_dashboard_alertas_empresa_2_07_06_2026.json";
    private static final String CHAVE_INCIDENTES_S3 = "client/dashIncidente_Empresa2.json";
    private static final long POLLING_INTERVAL_MS = Long.parseLong(
            System.getenv().getOrDefault("POLLING_INTERVAL_MS", "60000"));

    private static LocalDate ultimoRelatorio = null;
    static JiraController jira = new JiraController(); // junto com o AlertaProcessorService

    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  Iniciando geração de relatórios     ║");
        System.out.println("╚══════════════════════════════════════╝");

        if (Arrays.asList(args).contains("--gerar-relatorio")) {
            gerarRelatorio();
            return;
        }

        AlertaProcessorService processor = new AlertaProcessorService();
        System.out.printf("[App] Polling ativo — intervalo: %d segundos%n", POLLING_INTERVAL_MS / 1000);

        while (true) {
            try {
                // alertas de componentes
                List<AlertaDooh> alertas = S3Service.lerAlertas(CHAVE_ALERTAS_S3);
                if (!alertas.isEmpty()) {
                    System.out.printf("[App] %d alerta(s) recebido(s). Processando...%n", alertas.size());
                    boolean chamadoCriado = processor.processar(alertas);
                    if (chamadoCriado && !LocalDate.now().equals(ultimoRelatorio)) {
                        gerarRelatorio();
                        ultimoRelatorio = LocalDate.now();
                    }
                }

                // incidentes de displays offline
                List<Map<String, Object>> offline = S3Service.lerDisplaysOffline(CHAVE_INCIDENTES_S3);
                if (!offline.isEmpty()) {
                    System.out.printf("[App] %d display(s) novo(s) offline. Criando incidentes...%n", offline.size());
                    for (Map<String, Object> display : offline) {
                        try {
                            String key = jira.criarChamadoIncidente(display);
                            System.out.printf("[App] Incidente criado: %s — Display %s%n", key, display.get("idDisplay"));
                        } catch (Exception e) {
                            System.err.println("[App] Erro ao criar incidente: " + e.getMessage());
                        }
                    }
                }

            } catch (Exception e) {
                System.err.println("[App] Erro no ciclo: " + e.getMessage());
            }

            Thread.sleep(POLLING_INTERVAL_MS);
        }
    }

    private static void gerarRelatorio() {
        List<AlertaDooh> historico = S3Service.getHistoricoDiario();

        if (historico.isEmpty()) {
            System.out.println("[App] Sem histórico — relatório não gerado.");
            return;
        }

        String nomeArquivo = "relatorio_dooh_" + DateUtil.hojeString() + ".pdf";
        new PdfRelatorioService().gerarRelatorio(nomeArquivo, historico);
        S3Service.uploadArquivo(nomeArquivo, "relatorios/" + nomeArquivo);
    }

}
