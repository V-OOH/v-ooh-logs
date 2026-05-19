package model;

// Registrar os limites aceitáveis de cada componente.

/*
    Na hora de checar se há um alerta, o Java vai olhar o valor atual no
    RegistroGold e comparar com o minimo e máximo guardados aqui

 */

public class ComponenteParametro {

    // Campos da Tabela Associativa Componente e Display
    private Integer fkDisplay; // Foreign Key do Display
    private Integer fkComponente; // Foreign Key do Componente
    private Integer fkEmpresa; // Foreign Key da Empresa
    private Double minimo; // Minimo que um componente deve chegar
    private Double maximo; // Máximo que um componente deve chegar

    // Construtores
    public ComponenteParametro() {
    }

    public ComponenteParametro(Integer fkDisplay, Integer fkEmpresa, Integer fkComponente, Double minimo, Double maximo) {
        this.fkDisplay = fkDisplay;
        this.fkComponente = fkComponente;
        this.fkEmpresa = fkEmpresa;
        this.minimo = minimo;
        this.maximo = maximo;
    }

    // Getters e Setters
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

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    // Metodo toString
    @Override
    public String toString() {
        return "ComponenteParametro{" +
                "fkDisplay=" + fkDisplay +
                ", fkComponente=" + fkComponente +
                ", fkEmpresa=" + fkEmpresa +
                ", minimo=" + minimo +
                ", maximo=" + maximo +
                '}';
    }
}
