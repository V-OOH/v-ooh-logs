package conexao;

// Realizar conexão com o bucket S3

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConexaoS3 {

    // Colocar a região que está o bucket
    Region region = Region.US_EAST_1;

    // Chamar o S3 client e passar a região do bucket acima
    S3Client s3 = S3Client.builder().region(region).build();

    // Adicionar o nome do bucket
    String bucketName = "";

    // Adicionar o caminho que estara o arquivo que quer fazer o download
    String caminhoName = "";

    // Adicionar o destino que quer salvar o arquivo
    Path destinioPath = Paths.get("");

    // Exemplo para realizar a conexao no App

//     try {
//        GetObjectRequest request = GetObjectRequest.builder()
//                .bucket(bucketName)
//                .key(keyName)
//                .build();
//
//        ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(request);
//        byte[] data = objectBytes.asByteArray();
//        Files.write(destinationPath, data);
//
//        System.out.println("Download realizado com sucesso!");
//    } catch (Exception e) {
//        System.err.println("Erro ao baixar o arquivo: " + e.getMessage());
//    } finally {
//        s3.close();
//    }
}
