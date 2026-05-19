package dao;

// Busca parametrizações e faz o insert do alerta

import conexao.Conexao;
import model.ComponenteParametro;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO  {

    // Variável para realizar a conexão
    private Connection conexao;

    // Recebe a conexão aberta pela classe Conexao
    public AlertaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    /*
        Pegar parametrização de alertas do Bando de Dados
        Buscando os limites minimo e maximo da tabela associativa 'componente_display'
        filtrando pelo id da empresa que descobrimos através do mac

     */

    public List<ComponenteParametro> buscarParametroPorEmpresa(int idEmpresa) {

        // Lista para armazenar os parâmetros por componente
        List<ComponenteParametro> componenteParametros = new ArrayList<>();


       // Query que faz select para trazer os parâmetros de cada componente
        String sql = """
                SELECT fk_display, fk_empresa, fk_componente, minimo, maximo
                FROM componente_display
                WHERE fk_empresa = ?;
                """;

        // Tentativa de conexão
        try (Connection conexao = Conexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);

            // Para a execução da query
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ComponenteParametro compPram = new ComponenteParametro();
                    // Adicionar a Foreign Key do display
                    compPram.setFkDisplay(rs.getInt("fk_display"));
                    // Adicionar a Foreign Key da empresa
                    compPram.setFkEmpresa(rs.getInt("fk_empresa"));
                    // Adicionar a Foreign Key do Componente
                    compPram.setFkComponente(rs.getInt("fk_componente"));
                    // Valor minimo
                    compPram.setMinimo(rs.getDouble("minimo"));
                    // Valor maximo
                    compPram.setMaximo(rs.getDouble("maximo"));

                    // Adicionar na lista
                    componenteParametros.add(compPram);
                }

                // Print de sucesso
                System.out.println("Parâmetros salvos com sucesso!");
            }

        } catch (SQLException e) {
            // Print de erro
            System.out.println("Erro: " + e.getMessage());
        }

        // Retorna a lista de parâmetros
        return componenteParametros;
    }

    /*
        Realizar INSERT no banco de dados para salvar os alertas
     */

    public void salvarAlerta(int fkDisplay, int fkEmpresa, int fkComponente, String tipoAlerta) {

        // Query que faz o insert dos alertas no banco de dados
        String sql = """
                INSERT INTO alertas (fk_display, fk_empresa, fk_componente, tipo, status_alerta, data_hora_emissao) " +
                                     VALUES (?, ?, ?, ?, ?, ?)
                """;

        // Realizar conexão
        try (Connection conexao = Conexao.conectar();
             PreparedStatement ps = conexao.prepareStatement(sql)){

            ps.setInt(1, fkDisplay); // Adiciona a Foreign Key do Display
            ps.setInt(2, fkEmpresa); // Adiciona a Foreign Key da Empresa
            ps.setInt(3, fkComponente); // Adiciona a Foreign Key do Componente
            ps.setString(4, tipoAlerta); // Adiciona o tipo de alerta ('Atenção', 'Crítico')
            ps.setString(5, "Ativo"); // Começa com o Status 'Ativo'
            // Adiciona o momento que foi salvo
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

            // Executar a query
            ps.executeQuery();

            // Print de sucesso
            System.out.println("Alerta do tipo: " + tipoAlerta + " gravado com sucesso!");

        } catch (SQLException e) {
            // Print de erro
            System.out.println("Erro ao salvar alerta no banco de dados: " + e.getMessage());
        }
    }
}
