package com.sptech.school.dto;

import java.time.LocalDateTime;

public class RelatorioDoohData {

    private String        displayId;
    private String        zona;
    private String        empresa;
    private String        mac;
    private String        componente;
    private String        nivel;
    private Double        valorLeitura;
    private LocalDateTime dataHora;
    private String        mensagem;
    private String        jiraKey;

    public String        getDisplayId()    { return displayId; }
    public String        getZona()         { return zona; }
    public String        getEmpresa()      { return empresa; }
    public String        getMac()          { return mac; }
    public String        getComponente()   { return componente; }
    public String        getNivel()        { return nivel; }
    public Double        getValorLeitura() { return valorLeitura; }
    public LocalDateTime getDataHora()     { return dataHora; }
    public String        getMensagem()     { return mensagem; }
    public String        getJiraKey()      { return jiraKey; }

    public void setDisplayId(String displayId)       { this.displayId = displayId; }
    public void setZona(String zona)                 { this.zona = zona; }
    public void setEmpresa(String empresa)           { this.empresa = empresa; }
    public void setMac(String mac)                   { this.mac = mac; }
    public void setComponente(String componente)     { this.componente = componente; }
    public void setNivel(String nivel)               { this.nivel = nivel; }
    public void setValorLeitura(Double valorLeitura) { this.valorLeitura = valorLeitura; }
    public void setDataHora(LocalDateTime dataHora)  { this.dataHora = dataHora; }
    public void setMensagem(String mensagem)         { this.mensagem = mensagem; }
    public void setJiraKey(String jiraKey)           { this.jiraKey = jiraKey; }
}
