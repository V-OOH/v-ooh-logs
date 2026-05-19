package envio.mensagem;

import envio.mensagem.Slack;
import java.io.IOException;
import org.json.JSONObject;

/**
 *
 * @author Diego Brito <diego.lima@bandtec.com.br>
 */
public class AppSlack {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Cria a instância da classe do Slack
        Slack slack = new Slack();

        String urlWebhook = "";

        // Nome do json com o texto da mensagem
        JSONObject json = new JSONObject();
        json.put("text", "Teste");

        Slack.sendMessage(urlWebhook, json);
    }
}


