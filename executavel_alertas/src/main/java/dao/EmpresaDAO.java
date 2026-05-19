package dao;

// Busca dados da empresa e webhook

import conexao.Conexao;
import model.Empresa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpresaDAO {

    private Connection conexao;

    // Recebe a conexão aberta pela classe Conexao
    public EmpresaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Busca informações necessárias para o funcionamento
    public Empresa buscarEmpresaPorMac(String macDisplay) {

        /*
            Query responsável por realizar o select no banco
            pegando as informações da empresa pelo mac cadastrado
            no display
         */
        String sql = """
                SELECT e.id, e.nome_empresa, e.urlWEBHOOK FROM empresa e
                                     JOIN display d ON d.fk_empresa = e.id
                                     WHERE d.mac = ?
                """;

        // Realizar a conexão
        try (Connection conexao = Conexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)
        ) {
                 ps.setString(1, macDisplay); // Pega o mac do display

                 try (ResultSet rs = ps.executeQuery()) {
                     if (rs.next()) {
                         // Instância a classe empresa
                         Empresa empresa = new Empresa();
                         // Pega o id da empresa
                         empresa.setId(rs.getInt("id"));
                         // Pega o nome da empresa
                         empresa.setNomeEmpresa(rs.getString("nome_empresa"));
                         // Pega a url do webhook da empresa
                         empresa.setUrlWEBHOOK(rs.getString("urlWEBHOOK"));

                         return empresa;
                     }
                     // Print de sucesso
                     System.out.println("Informações da empresa salvo com sucesso!");
                 }
        } catch (SQLException e) {
            // Print de erro
            System.out.println("Erro: " + e.getMessage());
        }
        return null;
    }
}
