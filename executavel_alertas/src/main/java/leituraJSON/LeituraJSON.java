package leituraJSON;

import model.RegistroGold;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LeituraJSON {

    public List<RegistroGold> processarArquivo(File arquivo) {

        // Cria uma lista de registros do arquivo
        List<RegistroGold> registros = new ArrayList<>();

        try {
            // Ler o conteúdo do arquivo
            String conteudo = Files.readString(arquivo.toPath());
            JSONArray jsonArray = new JSONArray(conteudo);

            // Laço de repetição para salvar as informações
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject json = jsonArray.getJSONObject(i);

                RegistroGold registro = new RegistroGold();

                registro.setMac(json.getString("mac"));
                registro.setFkDisplay(json.getInt("fk_display"));
                registro.setFkComponente(json.getInt("fk_componente"));
                registro.setValor(json.getDouble("valor"));
                registro.setTipo(json.getString("tipo"));

                // Adicionando registros na lista
                registros.add(registro);
            }

            // Print sucesso
            System.out.println("Registros processados: " + registros.size());

        } catch (Exception e) {
            // Print erro
            System.out.println("Erro ao ler JSON: " + e.getMessage());
        }

        return registros;
    }
}