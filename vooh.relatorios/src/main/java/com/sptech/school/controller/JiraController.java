package com.sptech.school.controller;

import com.sptech.school.config.Jira;
import com.sptech.school.dto.AlertaDooh;

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
}
