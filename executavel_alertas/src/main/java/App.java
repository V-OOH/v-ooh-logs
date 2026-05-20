import conexao.Conexao;
import conexao.ConexaoS3;
import dao.AlertaDAO;
import dao.EmpresaDAO;
import envio.mensagem.Jira;
import envio.mensagem.Slack;
import leituraJSON.LeituraJSON;
import model.Empresa;
import model.RegistroGold;
import org.json.JSONObject;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/*
    Tudo que este arquivo faz

    1. Realizar a conexão com Bucket s3
    2. Pegar arquivo JSON
    3. Salvar mac do display
    4. Realizar leitura do JSON
    5. Buscar webhook da empresa
    6. Enviar mensagem para o Slack
    7. Pegar token da empresa
    8. Abrir chamado no Jira
 */

public class App {
    public static void main(String[] args) throws SQLException {
        System.out.println("Iniciando o JAR para enviar mensagens...");

        // Realizar conexão com o S3 e Banco de Dados
        Connection conexaoBanco = Conexao.conectar();
        ConexaoS3 conexaoS3 = new ConexaoS3();

        // Instanciando as classes de suporte
        LeituraJSON leituraJSON = new LeituraJSON();
        AlertaDAO alertaDAO = new AlertaDAO(conexaoBanco);
        EmpresaDAO empresaDAO = new EmpresaDAO(conexaoBanco);

        try {
            // Pegar arquivo JSON
            File arquivoJson = conexaoS3.baixarArquivoBucket("Nome_arquivo.json");

            // Realizar leitura do JSON
            List<RegistroGold> registroGolds = leituraJSON.processarArquivo(arquivoJson);

            if (registroGolds == null || registroGolds.isEmpty()) {
                System.out.println("Nenhum registro encontrado no JSON");
                return;
            }

            // Salvar mac do Display (Pega a primeira linha)
            String macDisplay = registroGolds.get(0).getMac();
            System.out.println("Display identificado com o mac: " + macDisplay);

            // Buscar empresa pelo mac
            Empresa empresa = empresaDAO.buscarEmpresaPorMac(macDisplay);

            // Caso a empresa estaja como null
            if (empresa == null) {
                System.out.println("Empresa não encontrada com o mac fornecido");
                return;
            }

            // Print de sucesso
            System.out.println("Processando alertas para a empresa: " + empresa.getNomeEmpresa());

            // Loop para percorrer os alertas
            for (RegistroGold alerta : registroGolds) {

                // Pega o tipo do alerta
                String tipoAlerta = alerta.getTipo();
                System.out.println("Enviando alerta do tipo: " + tipoAlerta);

                // Tentativa de envio de mensagem Slack
                try {
                    // Instancia a classe Slack
                    Slack slack = new Slack();

                    // Pega o WebHook da empresa
                    String urlWebhook = empresa.getUrlWEBHOOK();

                    // Chama o objeto json
                    JSONObject jsonSlack = new JSONObject();

                    // Mensagem que será enviada para o Slack
                    jsonSlack.put("text", String.format("Alerta [%s] detectado: O componente %d do Display %d registrou o valor de %.2f",
                            tipoAlerta, alerta.getFkComponente(), alerta.getFkDisplay(), alerta.getValor()));

                    // Mandar mensagem
                    slack.sendMessage(urlWebhook, jsonSlack);

                    // Print de sucesso
                    System.out.println("Mensagem enviada com sucesso para o Slack!");

                } catch (Exception e) {
                    // print de erro
                    System.out.println("Erro ao enviar para o Slack: " + e.getMessage());
                }

                // Tentativa de abrir chamado no Jira
                try {
                    // Variáveis para enviar mensagem para o Jira

                    // Salva a url da empresa
                    String jiraUrl = empresa.getJiraUrl();

                    // Slava o email da empresa
                    String jiraEmail = empresa.getJiraEmail();

                    // Slava o token da empresa
                    String jiraToken = empresa.getJiraToken();

                    // Instancia a classe do Jira
                    Jira jira = new Jira(jiraUrl, jiraEmail, jiraToken);

                    // Mensagem que abrirá o chamado no Jira
                    String resumoJira = String.format("Incidente [%s] - Componente %d do Display %d na empresa %s",
                            tipoAlerta, alerta.getFkComponente(), alerta.getFkDisplay(), empresa.getNomeEmpresa());

                    // Envio de chamado
                    jira.createIssue("VOOH", resumoJira, "Task");

                    // Print de sucesso
                    System.out.println("Chamado aberto no Jira!");

                } catch (Exception e) {
                    // Print de erro
                    System.out.println("Erro ao enviar para o Jira: " + e.getMessage());
                }

                // Print de sucesso geral
                System.out.println("Todos os alerta foram enviados!");

            }

        } catch (Exception e) {
            // Print de erro geral
            System.out.println("Erro ao disparar: " + e.getMessage());
        }
    }
}
