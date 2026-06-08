package com.sptech.school.service;

import com.sptech.school.controller.JiraController;
import com.sptech.school.controller.SlackController;
import com.sptech.school.dto.AlertaDooh;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AlertaProcessorService {

    private static final int CICLOS_PARA_ALERTA = 1;
    private static final int COOLDOWN_MINUTOS   = 30;

    private final Map<String, EstadoAlerta> estadoMaquinas = new HashMap<>();
    private final JiraController  jira  = new JiraController();
    private final SlackController slack = new SlackController();

    public boolean processar(List<AlertaDooh> alertas) {
        Map<String, AlertaDooh> alertasPorDisplay = new LinkedHashMap<>();
        for (AlertaDooh a : alertas) {
            alertasPorDisplay.putIfAbsent(a.getDisplayId(), a);
        }

        boolean chamadoCriado = false;

        for (AlertaDooh alerta : alertasPorDisplay.values()) {
            String chave  = alerta.getDisplayId() + "│" + alerta.getComponente();
            EstadoAlerta estado = estadoMaquinas.computeIfAbsent(chave, k -> new EstadoAlerta());

            String nivel = alerta.getNivel() != null ? alerta.getNivel().toUpperCase(java.util.Locale.ROOT) : "";
            String nivelNorm = java.text.Normalizer
                    .normalize(nivel, java.text.Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

            if (!nivelNorm.contains("CRITICO") && !nivelNorm.contains("ALERTA")
                    && !nivelNorm.contains("ATENCAO") && !nivelNorm.contains("RISCO")) {
                estado.contagemViolacoes = 0;
                continue;
            }

            if (estado.ultimoChamado != null) {
                long minutos = Duration.between(estado.ultimoChamado, LocalDateTime.now()).toMinutes();
                if (minutos < COOLDOWN_MINUTOS) {
                    System.out.printf("[Processor] Display %s em cooldown — %d/%d min%n",
                            alerta.getDisplayId(), minutos, COOLDOWN_MINUTOS);
                    continue;
                }
                estado.ultimoChamado = null;
            }

            estado.contagemViolacoes++;
            System.out.printf("[Processor] Display %s: ciclo %d/%d%n",
                    alerta.getDisplayId(), estado.contagemViolacoes, CICLOS_PARA_ALERTA);

            if (estado.contagemViolacoes >= CICLOS_PARA_ALERTA) {
                try {
                    String jiraKey = jira.criarChamadoDooh(alerta);
                    slack.notificarAlerta(alerta, jiraKey);

                    estado.contagemViolacoes = 0;
                    estado.ultimoChamado     = LocalDateTime.now();
                    chamadoCriado            = true;

                    System.out.printf("[Processor] Chamado criado: %s — Display %s%n",
                            jiraKey, alerta.getDisplayId());

                } catch (Exception e) {
                    System.err.println("[Processor] Erro ao criar chamado: " + e.getMessage());
                }
            }
        }
        return chamadoCriado;
    }

    private static class EstadoAlerta {
        int           contagemViolacoes = 0;
        LocalDateTime ultimoChamado     = null;
    }
}
