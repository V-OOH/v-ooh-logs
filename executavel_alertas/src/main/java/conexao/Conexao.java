package conexao;

// Realizar conexão com o container de MySQL

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // URL do caminho que deve acessar a Database
    private static final String URL = "jdbc:mysql://localhost:3306/vooh";

    // Usuário
    private static final String usuario = "root";

    // Senha
    private static final String senha = "24112002";

    // Conectar
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, usuario, senha);
    }
}
