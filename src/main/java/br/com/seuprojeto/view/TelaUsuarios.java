package br.com.seuprojeto.view;

import br.com.seuprojeto.dao.UsuarioDAO;
import br.com.seuprojeto.exception.DataAccessException;
import br.com.seuprojeto.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaUsuarios extends JFrame {

    // --- Componentes da View 🖥️ ---
    private JTextField idField; // Campo para mostrar o ID (não editável)
    private JTextField nomeField;
    private JTextField emailField;
    private JTable usuariosTable;
    private DefaultTableModel tableModel; // Modelo da tabela para manipular os dados

    // --- Objeto de Acesso a Dados 💾 ---
    private final UsuarioDAO usuarioDAO;

    // Construtor da classe
    public TelaUsuarios() {
        // Inicializa o DAO
        this.usuarioDAO = new UsuarioDAO();

        // Configurações básicas da janela
        setTitle("Cadastro de Usuários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa e organiza os componentes na tela
        inicializarComponentes();

        // Carrega os dados iniciais na tabela
        carregarTabela();
    }
    // Dentro da classe TelaUsuarios

    private void inicializarComponentes() {
        // Painel principal e layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // Painel para o formulário

        // Inicialização dos componentes
        idField = new JTextField();
        idField.setEditable(false); // O ID não deve ser editado pelo usuário
        nomeField = new JTextField();
        emailField = new JTextField();

        JButton salvarButton = new JButton("Salvar");
        JButton atualizarButton = new JButton("Atualizar");
        JButton excluirButton = new JButton("Excluir");
        JButton limparButton = new JButton("Limpar");

        // Adiciona os componentes ao painel do formulário
        formPanel.add(new JLabel("    ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("    Nome:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("    Email:"));
        formPanel.add(emailField);
        formPanel.add(salvarButton);
        formPanel.add(atualizarButton);
        formPanel.add(excluirButton);
        formPanel.add(limparButton);

        // Configuração da Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Email"}, 0);
        usuariosTable = new JTable(tableModel);

        // Adiciona os painéis à janela principal
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(usuariosTable), BorderLayout.CENTER); // Tabela com barra de rolagem

        add(panel);

        // ✨(ADICIONAR OS ACTIONLISTENERS) ✨
    

// --- Lógica do Controller (ActionListeners) ---

// Ação do botão SALVAR
        salvarButton.addActionListener(e -> {
            // 1. Pegar os dados da View
            String nome = nomeField.getText();
            String email = emailField.getText();

            // Validação simples
            if (nome.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e Email são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Criar o objeto de modelo
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);

            // --- Início da alteração ---
            try {
                // 3. Tenta chamar o método do DAO
                usuarioDAO.salvar(novoUsuario);

                // Se a linha acima for bem-sucedida, o código continua aqui:
                // Exibe mensagem de sucesso e atualiza a UI
                JOptionPane.showMessageDialog(this, "Usuário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarTabela();
                limparCampos();

            } catch (DataAccessException ex) {
                // Se a chamada ao DAO falhar e lançar uma DataAccessException,
                // o código pula para este bloco.

                // Exibe a mensagem de erro que veio do DAO.
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao Salvar", JOptionPane.ERROR_MESSAGE);
            }
            // --- Fim da alteração ---

/*            // 3. Chamar o método do DAO
            usuarioDAO.salvar(novoUsuario);

            JOptionPane.showMessageDialog(this, "Usuário salvo com sucesso!");

            // 4. Atualizar a JTable e limpar campos
            carregarTabela();
            limparCampos();
*/
        });

// Ação do botão EXCLUIR
        excluirButton.addActionListener(e -> {
            int selectedRow = usuariosTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Pega o ID da primeira coluna da linha selecionada
            int idParaExcluir = (int) tableModel.getValueAt(selectedRow, 0);

            // Confirmação
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                usuarioDAO.deletar(idParaExcluir);
                carregarTabela();
                limparCampos();
            }
        });

// Ação para carregar os dados do formulário ao clicar na tabela
        usuariosTable.getSelectionModel().addListSelectionListener(event -> {
            if (usuariosTable.getSelectedRow() != -1) {
                int selectedRow = usuariosTable.getSelectedRow();
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nomeField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                emailField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            }
        });

// Ação do botão ATUALIZAR
        atualizarButton.addActionListener(e -> {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setId(Integer.parseInt(idField.getText()));
            usuario.setNome(nomeField.getText());
            usuario.setEmail(emailField.getText());

            usuarioDAO.atualizar(usuario);

            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");

            carregarTabela();
            limparCampos();
        });

// Ação do botão LIMPAR
        limparButton.addActionListener(e -> limparCampos());

// ... fim do método inicializarComponentes()
    }
    // Dentro da classe TelaUsuarios

    private void carregarTabela() {
        // 1. Limpa a tabela antes de carregar novos dados
        tableModel.setRowCount(0);

        // 2. Busca a lista de usuários do banco de dados
        List<Usuario> usuarios = usuarioDAO.listarTodos();

        // 3. Preenche a tabela com os dados da lista
        for (Usuario usuario : usuarios) {
            tableModel.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail()
            });
        }
    }
    // Dentro da classe TelaUsuarios

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        emailField.setText("");
        usuariosTable.clearSelection(); // Remove a seleção da tabela
    }
}

