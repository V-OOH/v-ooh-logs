package com.sptech.school.controller;

import com.sptech.school.config.Jira;
import com.sptech.school.dto.AlertaDooh;

import java.util.Map;

public class JiraController {

    private static final String PROJECT_KEY = "SCRUM";

    private final Jira jira = new Jira();

    public String criarChamadoDooh(AlertaDooh alerta) throws Exception {
        String resumo = String.format("[%s][%s] Falha em %s — Display %s",
                alerta.getZona(), alerta.getNivel(), alerta.getComponente(), alerta.getDisplayId());

        String descricao = String.format(
                "Zona: %s\n"                 +
                        "Empresa Responsável: %s\n"  +
                        "ID do Display: %s\n"        +
                        "MAC Address: %s\n"          +
                        "Componente com Falha: %s\n" +
                        "Data/Hora do Alerta: %s\n"  +
                        "Nível de Criticidade: %s\n" +
                        "Valor de Uso: %.2f%%\n\n"   +
                        "Diagnóstico: %s",
                alerta.getZona(), alerta.getEmpresa(), alerta.getDisplayId(),
                alerta.getMac(), alerta.getComponente(), alerta.getHorario(),
                alerta.getNivel(), alerta.getValor(), alerta.getMensagem()
        );

        String prioridade = alerta.getNivel() != null && alerta.getNivel().equalsIgnoreCase("CRITICO")
                ? "Highest" : "Medium";

        String response = jira.createIssue(PROJECT_KEY, resumo, descricao, "Task", prioridade, alerta.getMac());
        return jira.extractKey(response);
    }

    public String criarChamadoDisplayOffline(AlertaDooh alerta) throws Exception {
        String resumo = String.format(
                "[INCIDENTE][DISPLAY OFFLINE] Display %s sem telemetria",
                alerta.getDisplayId()
        );

        String descricao = String.format(
                """
                        Foi detectado um display offline na dashboard de incidentes.
                        Empresa Responsável: %s
                        Zona: %s
                        ID do Display: %s
                        MAC Address: %s
                        Data/Hora da Detecção: %s
                        Criticidade: %s
                        
                        Diagnóstico: %s
                        
                        Ação recomendada:
                        Verificar energia, conexão de rede e envio de telemetria do display.""",
                alerta.getEmpresa(),
                alerta.getZona(),
                alerta.getDisplayId(),
                alerta.getMac(),
                alerta.getHorario(),
                alerta.getNivel(),
                alerta.getMensagem()
        );

        String response = jira.createIssue(
                PROJECT_KEY,
                resumo,
                descricao,
                "Task",
                "Highest",
                alerta.getMac()
        );

        return jira.extractKey(response);
    }

    public String criarChamadoIncidente(Map<String, Object> display) throws Exception {
        String idDisplay = String.valueOf(display.get("idDisplay"));
        String mac       = String.valueOf(display.get("mac"));
        String zona      = String.valueOf(display.get("zona"));
        String logradouro = String.valueOf(display.get("logradouro"));
        String motivo    = String.valueOf(display.get("motivoOffline"));

        String resumo = String.format("[INCIDENTE][OFFLINE] Display %s offline — Zona %s", idDisplay, zona);

        String descricao = String.format(
                """
                        Display offline detectado
                        ID do Display: %s
                        MAC Address: %s
                        Zona: %s
                        Logradouro: %s
                        Motivo: %s""",
                idDisplay, mac, zona, logradouro, motivo
        );

        String response = jira.createIssue(PROJECT_KEY, resumo, descricao,
                "Task", "Highest", mac);
        return jira.extractKey(response);
    }
}
