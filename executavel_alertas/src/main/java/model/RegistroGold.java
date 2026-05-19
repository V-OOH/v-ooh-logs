package model;

// Opcional: Registrar as informações vindas do .JSON

public class RegistroGold {
    private String mac;
    private Integer fkDisplay;
    private Integer fkComponente;
    private Double valor;

    // Getters e Setters
    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getFkDisplay() {
        return fkDisplay;
    }

    public void setFkDisplay(Integer fkDisplay) {
        this.fkDisplay = fkDisplay;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
