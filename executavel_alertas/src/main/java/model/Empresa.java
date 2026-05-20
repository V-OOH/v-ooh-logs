package model;

/*

    Receber as informações da empresa

    Quando o usamos o EmpresaDAO para realizar os selects no banco

    O código ganha um objeto chamado empresa. A partir daí,
    qualquer outra classe, como o Slack e do Jira, pode dar um
    empresa.getUrlWebhook() para saber para onde enviar a notificação.

 */

public class Empresa {
    // Campos da tabela de cadastro da empresa
    private Integer id; // Id da empresa
    private String nomeEmpresa; // Nome da empresa
    private String statusEmpresa; // Status da empresa (verificar se esta Ativa ou Inativa)
    private String urlWEBHOOK; // URL do webhook para integração dos app - Slack
    private String jiraUrl;
    private String jiraEmail;
    private String jiraToken;

    // Construtores
    public Empresa() {
    }

    public Empresa(Integer id, String nomeEmpresa, String statusEmpresa, String urlWEBHOOK) {
        this.id = id;
        this.nomeEmpresa = nomeEmpresa;
        this.statusEmpresa = statusEmpresa;
        this.urlWEBHOOK = urlWEBHOOK;
    }

    public Empresa(String nomeEmpresa, String statusEmpresa, String urlWEBHOOK) {
        this.nomeEmpresa = nomeEmpresa;
        this.statusEmpresa = statusEmpresa;
        this.urlWEBHOOK = urlWEBHOOK;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getStatusEmpresa() {
        return statusEmpresa;
    }

    public void setStatusEmpresa(String statusEmpresa) {
        this.statusEmpresa = statusEmpresa;
    }

    public String getUrlWEBHOOK() {
        return urlWEBHOOK;
    }

    public void setUrlWEBHOOK(String urlWEBHOOK) {
        this.urlWEBHOOK = urlWEBHOOK;
    }

    public String getJiraUrl() {
        return jiraUrl;
    }

    public void setJiraUrl(String jiraUrl) {
        this.jiraUrl = jiraUrl;
    }

    public String getJiraEmail() {
        return jiraEmail;
    }

    public void setJiraEmail(String jiraEmail) {
        this.jiraEmail = jiraEmail;
    }

    public String getJiraToken() {
        return jiraToken;
    }

    public void setJiraToken(String jiraToken) {
        this.jiraToken = jiraToken;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nomeEmpresa='" + nomeEmpresa + '\'' +
                ", statusEmpresa='" + statusEmpresa + '\'' +
                ", urlWEBHOOK='" + urlWEBHOOK + '\'' +
                ", jiraUrl='" + jiraUrl + '\'' +
                ", jiraEmail='" + jiraEmail + '\'' +
                ", jiraToken='" + jiraToken + '\'' +
                '}';
    }
}
