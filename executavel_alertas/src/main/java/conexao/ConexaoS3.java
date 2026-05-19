package conexao;

// Importações necessárias da AWS SDK v2
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConexaoS3 {

    // Colocar a região onde está o bucket S3 foi criado
    private final Region region = Region.US_EAST_1;

    // Chama o cliente e usa a região logo acima
    private final S3Client s3 = S3Client.builder().region(region).build();

    // Adicionar o nome do bucket que irá ter o JSON
    private final String bucketName = "";

    public File baixarArquivoBucket(String nomeArquivo) {

        // Caminho de onde está o arquivo (ex: gold)
        String keyName = "gold/" + nomeArquivo;

        // Colocar o caminho onde o arquivo será salvo para a execução
        String caminhoDestinoLocal = "" + nomeArquivo;
        Path destinoPath = Paths.get(caminhoDestinoLocal);


        // Print de Inicio
        System.out.println("Buscando o arquivo " + keyName + " no bucket " + bucketName + "...");

        try {
            // Requisição para pegar o arquivo
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName) // nome do bucket
                    .key(keyName) // caminho onde esta
                    .build(); // como se fosse um "ponto final" nas configurações

            // Faz o download do arquivo transformando os dados em Bytes
            ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(request);
            byte[] data = objectBytes.asByteArray();

            // Cria os diretórios locais caso eles não existam
            if (destinoPath.getParent() != null) {
                Files.createDirectories(destinoPath.getParent());
            }

            // Grava os bytes baixados no arquivo local
            Files.write(destinoPath, data);

            System.out.println("Download realizado com sucesso! O Arquivo foi salvo em: " + caminhoDestinoLocal);

            // Retorna o arquivo local pronto para ser lido pela sua classe LeituraJSON
            return destinoPath.toFile();

        } catch (Exception e) {
            System.err.println("Erro ao baixar o arquivo do S3: " + e.getMessage());
            return null;
        }
    }
}