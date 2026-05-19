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

        JSONObject json = new JSONObject();

        json.put("text", "Fácil né? :shrug:");

        Slack.sendMessage(json);
    }
}


