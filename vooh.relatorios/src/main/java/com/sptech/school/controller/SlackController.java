package com.sptech.school.controller;

import com.sptech.school.config.Slack;
import com.sptech.school.dto.AlertaDooh;

public class SlackController {

    private final Slack slack = new Slack();

    public void notificarAlerta(AlertaDooh alerta, String jiraKey) {
        boolean critico = alerta.getNivel() != null && alerta.getNivel().toUpperCase().contains("CRITICO");
        String prefixo = critico ? "🚨 *[CRÍTICO]*" : "⚠️ *[ATENÇÃO]*";

        String mensagem = String.format(
                "%s INCIDENTE DETECTADO — DISPLAY DOOH\n\n" +
                "*Zona:* %s\n"           +
                "*Empresa:* %s\n"        +
                "*ID do Display:* %s\n"  +
                "*MAC:* %s\n"            +
                "*Componente:* %s\n"     +
                "*Uso Atual:* %.2f%%\n"  +
                "*Horário:* %s\n\n"      +
                "*Diagnóstico:* %s\n"    +
                "*Ticket Jira:* %s\n"    +
                "──────────────────────────────\n" +
                "Verifique o display e escale se necessário.",
                prefixo,
                nvl(alerta.getZona()), nvl(alerta.getEmpresa()), nvl(alerta.getDisplayId()),
                nvl(alerta.getMac()),
                alerta.getComponente() != null ? alerta.getComponente().toUpperCase() : "—",
                alerta.getValor() != null ? alerta.getValor() : 0.0,
                nvl(alerta.getHorario()), nvl(alerta.getMensagem()),
                jiraKey != null ? jiraKey : "N/A"
        );

        slack.enviarAlerta(mensagem);
    }

    private String nvl(String v) {
        return v != null ? v : "—";
    }
}
