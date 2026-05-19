
// Classe principal

import conexao.Conexao;
import dao.AlertaDAO;
import dao.EmpresaDAO;
import model.ComponenteParametro;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException {
        System.out.println("Iniciando o JAR para verificar se há alertas");

        // Abrir conexao com MySQL
        Connection conexaoBanco = Conexao.conectar();

        // Instaciar DAO
        AlertaDAO alertaDAO = new AlertaDAO(conexaoBanco);
        EmpresaDAO empresaDAO = new EmpresaDAO(conexaoBanco);

        /*
            1. Realizar lógica para pegar arquivo do Bucket

            2. Validação da empresa

            3. Validar status da empresa

            4. Registrar parametros


         */


    }
}
