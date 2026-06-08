package com.sptech.school.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertaDooh {

    @JsonProperty("idDisplay")
    private Integer idDisplay;

    @JsonProperty("macAddress")
    private String mac;

    @JsonProperty("dataHora")
    private String horario;

    @JsonProperty("status_geral")
    private String nivel;

    private String zona;
    private String empresa;
    private String componente;
    private Double valor;
    private String mensagem;

    public String  getDisplayId()  { return idDisplay != null ? String.valueOf(idDisplay) : null; }
    public String  getMac()        { return mac; }
    public String  getHorario()    { return horario; }
    public String  getNivel()      { return nivel; }
    public String  getZona()       { return zona; }
    public String  getEmpresa()    { return empresa; }
    public String  getComponente() { return componente; }
    public Double  getValor()      { return valor; }
    public String  getMensagem()   { return mensagem; }

    public void setIdDisplay(Integer idDisplay)  { this.idDisplay = idDisplay; }
    public void setMac(String mac)               { this.mac = mac; }
    public void setHorario(String horario)       { this.horario = horario; }
    public void setNivel(String nivel)           { this.nivel = nivel; }
    public void setZona(String zona)             { this.zona = zona; }
    public void setEmpresa(String empresa)       { this.empresa = empresa; }
    public void setComponente(String componente) { this.componente = componente; }
    public void setValor(Double valor)           { this.valor = valor; }
    public void setMensagem(String mensagem)     { this.mensagem = mensagem; }

    @Override
    public String toString() {
        return String.format("AlertaDooh{idDisplay=%s, mac='%s', zona='%s', nivel='%s', horario='%s'}",
                idDisplay, mac, zona, nivel, horario);
    }
}
