package br.com.seuprojeto.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.seuprojeto.model.Usuario;
import br.com.seuprojeto.util.ConexaoMySQL;
import br.com.seuprojeto.exception.DataAccessException; // Exceção personalizada

public class UsuarioDAO {

    // 1. Inicializa o Logger para a classe
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    // CREATE
    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.executeUpdate();
            logger.info("Usuário salvo com sucesso: {}", usuario.getEmail());
        } catch (SQLException e) {
            // 2. Loga o erro com detalhes técnicos
            logger.error("Erro ao salvar usuário: {}", usuario.getEmail(), e);
            // 3. Lança uma exceção para a camada superior tratar
            throw new DataAccessException("Não foi possível salvar o usuário. Tente novamente mais tarde.", e);
        }
    }

    // READ
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        // Alteração para forçar um erro de tabela inexistente
//        String sql = "SELECT * FROM usuarios_INEXISTENTE"; //linha de teste de erros
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar todos os usuários.", e);
            throw new DataAccessException("Ocorreu um erro ao buscar os usuários.", e);
        }
        return usuarios;
    }

    // UPDATE
    public void atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setInt(3, usuario.getId());
            ps.executeUpdate();
            logger.info("Usuário atualizado com sucesso: ID {}", usuario.getId());
        } catch (SQLException e) {
            logger.error("Erro ao atualizar usuário com ID: {}", usuario.getId(), e);
            throw new DataAccessException("Não foi possível atualizar o usuário.", e);
        }
    }

    // DELETE
    public void deletar(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = ConexaoMySQL.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            logger.warn("Usuário deletado com sucesso: ID {}", id); // Nível WARN para ações destrutivas
        } catch (SQLException e) {
            logger.error("Erro ao deletar usuário com ID: {}", id, e);
            throw new DataAccessException("Falha ao tentar deletar o usuário.", e);
        }
    }
}