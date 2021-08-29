package br.com.multclin.telas;

import br.com.multclin.bo.MedicoBO;
import br.com.multclin.bo.UsuarioBO;

import br.com.multclin.dao.GeraCodigoAutomaticoDAO;
import br.com.multclin.dao.MedicoDAO;
//import br.com.multclin.componentes.ComponentesFormTelaUsuario;
//import br.com.multclin.dao.SetorTramiteInternoDAO;
import br.com.multclin.dao.UsuarioDAO;
import br.com.multclin.dto.MedicoDTO;
//import br.com.multclin.dto.ItensDoProtocoloTFDDTO;
//import br.com.multclin.dto.SetorTramiteInternoDTO;
import br.com.multclin.dto.UsuarioDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.SubTelaFone.lblRepositCPF;
import static br.com.multclin.telas.SubTelaFone.lblRepositNome;
import static br.com.multclin.telas.SubTelaFone.lblRepositPerfil;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import br.com.multclin.util.JtextFieldSomenteLetras;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author DaTorres
 */
public class TelaUsuario extends javax.swing.JInternalFrame {

    UsuarioDTO usuarioDTO = new UsuarioDTO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    UsuarioBO usuarioBO = new UsuarioBO();
    GeraCodigoAutomaticoDAO geraCodigoDAO = new GeraCodigoAutomaticoDAO();

    MedicoDTO medicoDTO = new MedicoDTO();
    MedicoDAO medicoDAO = new MedicoDAO();
    MedicoBO medicoBO = new MedicoBO();

    Font f = new Font("Tahoma", Font.BOLD, 9);

    br.com.multclin.telas.SubTelaFone formFone;

//    ComponentesFormTelaUsuario componente = new ComponentesFormTelaUsuario();
//   SetorTramiteInternoDTO setorInternoDTO = new SetorTramiteInternoDTO();
//    SetorTramiteInternoDAO setorInternoDAO = new SetorTramiteInternoDAO();
    int flag = 0;

    //Hugo vasconcelos Aula 25 - verificando Janelas Abertas
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555436?start=0
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555438?start=0
    //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555444?start=0
    //agroa criamos o método que irá fazer a verificaçao e passamos como parâmetros um obj do tipo Object 
    public boolean estaFechado(Object obj) {

        /**
         * Criei um objeto chamado ativo do tipo JInternalFrame que está
         * inicializado como um array (array é um objeto que guarda vários
         * elementos detro) Pergunta-se: quais são os objetos todas as janelas
         * que são carregadas pelo objeto carregador. Bem o objeto ativo irá
         * guardar todas as janelas que estão sendo abertas no sistema
         */
        JInternalFrame[] ativo = DeskTop.getAllFrames();
        boolean fechado = true;
        int cont = 0;

        while (cont < ativo.length && fechado) {
            if (ativo[cont] == obj) {
                fechado = false;

            }
            cont++;
        }
        return fechado;
    }

    /**
     * Código Mestre Chimura
     */
    private static TelaUsuario instance = null;

    public static TelaUsuario getInstance() {

        if (instance == null) {

            instance = new TelaUsuario();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public TelaUsuario() {
        initComponents();
        aoCarregarForm();
        personalizarFrontEnd();
        addRowJTable();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tblUsuarios.getTableHeader().setDefaultRenderer(new br.com.multclin.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tblUsuarios.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

        //********************************************************************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
        tblUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 9));
        tblUsuarios.getTableHeader().setOpaque(false);
        tblUsuarios.getTableHeader().setBackground(new Color(32, 136, 203));
        tblUsuarios.getTableHeader().setForeground(new Color(255, 255, 255));
        tblUsuarios.setRowHeight(34);

    }

    private void gerarCodigoGUIeDataSistema() {

        /**
         * Setar uma Data pega no sistema e setar num JDateChooser
         * https://www.youtube.com/watch?v=GlBMniOwCnI
         */
        int numeroMax = geraCodigoDAO.gerarCodigoUsuario();
        int resultado = 0;
        //  JOptionPane.showMessageDialog(null, "Numero"+ numeroMax);
        resultado = numeroMax + 1;
        txtIDUsuario.setText(String.valueOf(resultado));

    }

    private void personalizarFrontEnd() {

        //Formulario TelaUsuariobtnEmailPreferencial
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        btnCadastroFone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/linkForms.png")));
        btnEmailPreferencial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/email.png")));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png")));
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png")));
        lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
    }

    public void aoCarregarForm() {
        this.txtCPF.setEnabled(false);
        this.txtUsuario.setEnabled(false);
        this.txtLogin.setEnabled(false);
        this.txtSenhaPS.setEnabled(false);
        this.lblFonePreferencial.setEnabled(false);
        this.cbPerfilUsuarios.setEnabled(false);
        this.cbPerfilUsuarios.setEnabled(false);
        this.txtGmail.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.cbSexo.setSelectedItem(null);
        this.btnEmailPreferencial.setEnabled(false);
        this.cbEstaUsuario.setEnabled(false);
        this.btnCadastroFone.setEnabled(false);
        this.btnValidaCPF.setEnabled(false);
        progressBarraPesquisa.setIndeterminate(true);
    }

    private void verificarCombos() {
        if (cbPerfilUsuarios.getSelectedItem().equals("Selecione...")) {
            cbPerfilUsuarios.setBackground(Color.YELLOW);
        }

        if (cbEstaUsuario.getSelectedItem().equals("Selecione...")) {
            cbEstaUsuario.setBackground(Color.YELLOW);
        }
        if (cbSexo.getSelectedItem().equals("Selecione...")) {
            cbSexo.setBackground(Color.YELLOW);
        }

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();

        ArrayList<UsuarioDTO> list;

        try {

            list = (ArrayList<UsuarioDTO>) usuarioDAO.listarTodos();

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdUserDto();
                rowData[1] = list.get(i).getLoginDto();
                rowData[2] = list.get(i).getPerfilDto();
                rowData[3] = list.get(i).getCelularPrincipalDto();

                model.addRow(rowData);
            }

            tblUsuarios.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(130);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(90);

            //coloca o cursor em modo de espera 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        } catch (PersistenciaException ex) {

            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

//    private void personalizarBotoesInternosDoForm() {
//
//        componente.personalizaBotoesFormTelaUsuario(btnAdicionar, btnEditar, btnSalvar, btnExcluir, btnPesquisar, btnCancelar);
//    }
    private void pesquisarUsuario() {
        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tblUsuarios.getModel();

        ArrayList<UsuarioDTO> list;

        try {

            list = (ArrayList<UsuarioDTO>) usuarioDAO.filtrarUsuarioPesqRapida(pesquisarUsuario);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdUserDto();
                rowData[1] = list.get(i).getLoginDto();
                rowData[2] = list.get(i).getPerfilDto();
                rowData[3] = list.get(i).getCelularPrincipalDto();

                model.addRow(rowData);
            }

            tblUsuarios.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(75);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(70);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(70);

            //coloca o cursor em modo de espera 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void salvar() {

        //coloca o cursor em modo de espera 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        /**
         * capturando os campos do Form na Camada Gui e em vez de adicionar ha
         * uma variável encapsulamos e setamos como o método set
         */
        usuarioDTO.setIdUserDto(Integer.parseInt(txtIDUsuario.getText()));
        usuarioDTO.setUsuarioDto(txtUsuario.getText());//USUARIO
        usuarioDTO.setLoginDto(txtLogin.getText());//login
        /**
         * Observação:Essa é a forma de captuar do form gui um campo do tipo
         * senha para salva-lo num banco de dados como uma string
         */
        usuarioDTO.setSenhaDto(new String(txtSenhaPS.getPassword()));//senha
        usuarioDTO.setCelularPrincipalDto(lblFonePreferencial.getText());//celular principal 
        usuarioDTO.setEmailPrincipalDto(txtGmail.getText());//email principal 

        usuarioDTO.setCpfDto(txtCPF.getText());//cpf

        // Observação: essa é a forma de capturar um campo do tipo Caixa de combinaçao 
        if (!cbPerfilUsuarios.getSelectedItem().equals("Selecione...")) {
            usuarioDTO.setPerfilDto((String) cbPerfilUsuarios.getSelectedItem());//perfil

        }
        if (!cbSexo.getSelectedItem().equals("Selecione...")) {
            usuarioDTO.setSexoDto((String) cbSexo.getSelectedItem());//sexo 
        }

        if (!cbEstaUsuario.getSelectedItem().equals("Selecione...")) {
            usuarioDTO.setEstadoDto((String) cbEstaUsuario.getSelectedItem());//estado

        }

        try {
            /**
             * Depois de capturados e atribuídos seus respectivos valores
             * capturados nas variáveis acimas descrita. Iremos criar um objeto
             * do tipo UsuarioBO
             */
            usuarioBO = new UsuarioBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((usuarioBO.validaCampos(usuarioDTO)) == false) {
                txtUsuario.setText("");

            } else {

                if ((flag == 1)) {

                    //  usuarioDTO.setUsuarioDto(txtCPF.getText());
                    //verificando a duplicidade pelo cpf
                    boolean retornoVerifcaDuplicidade = usuarioDAO.verificaDuplicidade(usuarioDTO);

                    if (retornoVerifcaDuplicidade == false) {

                        usuarioBO.cadastrar(usuarioDTO);
                        /**
                         * Após salvar limpar os campos
                         */
                        txtIDUsuario.setText("");
                        txtUsuario.setText("");
                        txtLogin.setText("");
                        txtSenhaPS.setText("");
                        lblFonePreferencial.setText("");
                        txtCPF.setText("");

                        cbPerfilUsuarios.setSelectedItem(null);
                        cbEstaUsuario.setSelectedItem(null);
                        cbSexo.setSelectedItem(null);

                        /**
                         * Bloquear campos e Botões
                         */
                        txtIDUsuario.setEnabled(false);
                        txtUsuario.setEnabled(false);
                        txtLogin.setEnabled(false);
                        lblFonePreferencial.setEnabled(false);

                        cbPerfilUsuarios.setEnabled(false);
                        cbEstaUsuario.setEnabled(false);
                        cbSexo.setEnabled(false);

                        txtSenhaPS.setEnabled(false);
                        txtPesquisa.setEnabled(true);
                        /**
                         * Liberar campos necessário para operações após
                         * salvamento
                         */
                        btnSalvar.setEnabled(false);
                        btnAdicionar.setEnabled(true);

                        //OUTROS BOTOES 
                        btnCadastroFone.setEnabled(false);

                        btnEmailPreferencial.setEnabled(false);

                        lblLinhaInformativa.setText("Resgistro Cadastrado com Sucesso");
                        Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
                        lblLinhaInformativa.setFont(f);
                        lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));

                        txtGmail.setText("");
                        txtGmail.setEnabled(false);

                        //tira o cursor em modo de espera 
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        btnCancelar.setEnabled(false);
                        JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                                + "Resgistro Salvo Com Sucesso.\n"
                                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                        /**
                         * Zera a Linha informativa criada para esse Sistema
                         */
                        int numeroLinhas = tblUsuarios.getRowCount();

                        if (numeroLinhas > 0) {

                            while (tblUsuarios.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

                            }

                            addRowJTable();

                        } else {
                            addRowJTable();

                        }

                    } else {

                        txtLogin.requestFocus();
                        txtLogin.setBackground(Color.YELLOW);
                        lblLinhaInformativa.setText("Verificação efetuada, Login já cadastrado no Sistema");
                        lblLinhaInformativa.setForeground(Color.RED);

                    }

                } else {

                    /**
                     * Caso não seja um novo registro equivale dizer que é uma
                     * edição então executará esse código flag será !=1
                     */
                    usuarioDTO.setIdUserDto(Integer.parseInt(txtIDUsuario.getText()));

                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    usuarioBO.atualizarBO(usuarioDTO);

                    /**
                     * Após salvar limpar os campos
                     */
                    txtIDUsuario.setText("");
                    txtCPF.setText("");
                    txtUsuario.setText("");
                    txtLogin.setText("");
                    lblFonePreferencial.setText("");
                    txtSenhaPS.setText("");
                    txtPesquisa.setText("");
                    txtGmail.setText("");

                    cbPerfilUsuarios.setSelectedItem(null);
                    cbSexo.setSelectedItem(null);
                    cbEstaUsuario.setSelectedItem(null);

                    /**
                     * Bloquear campos e Botões
                     */
                    txtIDUsuario.setEnabled(false);
                    txtUsuario.setEnabled(false);
                    txtLogin.setEnabled(false);
                    lblFonePreferencial.setEnabled(false);
                    txtSenhaPS.setEnabled(false);
                    txtPesquisa.setEnabled(true);
                    txtGmail.setEnabled(false);

                    cbPerfilUsuarios.setEnabled(false);
                    cbSexo.setEnabled(false);
                    cbEstaUsuario.setEnabled(false);

                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);

                    //outros botoes 
                    btnCadastroFone.setEnabled(false);
                    btnEmailPreferencial.setEnabled(false);

                    //tira o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                            + "Resgistro Editado Com Sucesso.\n"
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                    int numeroLinhas = tblUsuarios.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tblUsuarios.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

                        }

                        addRowJTable();

                    } else {
                        addRowJTable();

                    }
                    //tira o cursor do modo load
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada GUI: " + e.getMessage());

            if (e.getMessage().equalsIgnoreCase("Campo nome Obrigatório")) {
                txtUsuario.requestFocus();
                txtUsuario.setBackground(Color.RED);
            }

            if (e.getMessage().equalsIgnoreCase("Campo login Obrigatório")) {
                txtLogin.requestFocus();
                txtLogin.setBackground(Color.RED);
            }

            if (e.getMessage().equalsIgnoreCase("Campo senha Obrigatório")) {
                txtSenhaPS.requestFocus();
                txtSenhaPS.setBackground(Color.RED);
            }
            if (e.getMessage().equalsIgnoreCase("Selecione um Sexo Válido.")) {
                cbSexo.requestFocus();
                cbSexo.setBackground(Color.RED);
            }

            if (e.getMessage().equalsIgnoreCase("É necessário definir um perfil para Usuario.")) {
                cbPerfilUsuarios.requestFocus();
                cbPerfilUsuarios.setBackground(Color.RED);
            }

            if (e.getMessage().equalsIgnoreCase("É necessário definir se Usuário está ATIVO OU INATIVO.")) {
                cbEstaUsuario.requestFocus();
                cbEstaUsuario.setBackground(Color.RED);
            }

            //
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelDadosUsuario = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenhaPS = new javax.swing.JPasswordField();
        lblPerfilRotulo = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        txtUsuario =  new JtextFieldSomenteLetras(45);
        PanelJTable = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox();
        lblSexo = new javax.swing.JLabel();
        txtGmail = new javax.swing.JTextField();
        btnCadastroFone = new javax.swing.JButton();
        btnEmailPreferencial = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        painelObrigatorio = new javax.swing.JPanel();
        btnValidaCPF = new javax.swing.JButton();
        txtCPF = new javax.swing.JFormattedTextField();
        lblCPFUsuario = new javax.swing.JLabel();
        txtIDUsuario = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        cbPerfilUsuarios = new javax.swing.JComboBox();
        cbEstaUsuario = new javax.swing.JComboBox();
        lblEstadoUsuario = new javax.swing.JLabel();
        lblFonePreferencial = new javax.swing.JLabel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        lblNuvemForms = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();

        setClosable(true);
        setTitle("Usuário");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        PanelDadosUsuario.setBackground(java.awt.Color.white);
        PanelDadosUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Usuário:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        PanelDadosUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsuario.setForeground(new java.awt.Color(0, 102, 102));
        lblUsuario.setText("Nome:");
        PanelDadosUsuario.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, -1, -1));

        lblLogin.setForeground(new java.awt.Color(0, 102, 102));
        lblLogin.setText("Login:");
        PanelDadosUsuario.add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        txtLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLoginFocusLost(evt);
            }
        });
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoginKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLoginKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 130, 30));

        lblSenha.setForeground(new java.awt.Color(0, 102, 102));
        lblSenha.setText("Senha:");
        PanelDadosUsuario.add(lblSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 50, -1));

        txtSenhaPS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenhaPSFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSenhaPSFocusLost(evt);
            }
        });
        txtSenhaPS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaPSKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSenhaPSKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtSenhaPS, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 210, 60, 30));

        lblPerfilRotulo.setForeground(new java.awt.Color(0, 102, 102));
        lblPerfilRotulo.setText("Perfil:");
        PanelDadosUsuario.add(lblPerfilRotulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 80, -1));

        lblCelular.setForeground(new java.awt.Color(0, 102, 102));
        lblCelular.setText("Celular Pref.:");
        PanelDadosUsuario.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 80, -1));

        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 320, 30));

        PanelJTable.setLayout(new java.awt.GridLayout(1, 0));
        PanelDadosUsuario.add(PanelJTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 164, 481, -1));

        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa.setText("Linha Informativa");
        PanelDadosUsuario.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 320, 20));

        cbSexo.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbSexo.setForeground(new java.awt.Color(0, 102, 102));
        cbSexo.setMaximumRowCount(9);
        cbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "MASCULINO", "FEMININO" }));
        cbSexo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbSexoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbSexoFocusLost(evt);
            }
        });
        cbSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSexoActionPerformed(evt);
            }
        });
        cbSexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbSexoKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(cbSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 110, 30));

        lblSexo.setForeground(new java.awt.Color(0, 102, 102));
        lblSexo.setText("Sexo:");
        PanelDadosUsuario.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, -1, -1));

        txtGmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGmail.setForeground(new java.awt.Color(0, 102, 102));
        txtGmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGmailFocusLost(evt);
            }
        });
        PanelDadosUsuario.add(txtGmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 280, 30));

        btnCadastroFone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnCadastroFoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnCadastroFoneFocusLost(evt);
            }
        });
        btnCadastroFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroFoneActionPerformed(evt);
            }
        });
        btnCadastroFone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCadastroFoneKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(btnCadastroFone, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 31, 31));

        btnEmailPreferencial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEmailPreferencialFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnEmailPreferencialFocusLost(evt);
            }
        });
        btnEmailPreferencial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnEmailPreferencialKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(btnEmailPreferencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 330, 31, 31));

        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Email Preferêncial:");
        PanelDadosUsuario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        painelObrigatorio.setBackground(new java.awt.Color(0, 102, 102));
        painelObrigatorio.setForeground(new java.awt.Color(0, 102, 102));
        painelObrigatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnValidaCPF.setPreferredSize(new java.awt.Dimension(32, 32));
        btnValidaCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusLost(evt);
            }
        });
        btnValidaCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidaCPFActionPerformed(evt);
            }
        });
        btnValidaCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnValidaCPFKeyPressed(evt);
            }
        });
        painelObrigatorio.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCPFFocusLost(evt);
            }
        });
        txtCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPFActionPerformed(evt);
            }
        });
        txtCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFKeyPressed(evt);
            }
        });
        painelObrigatorio.add(txtCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 140, 30));

        lblCPFUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCPFUsuario.setForeground(java.awt.Color.white);
        lblCPFUsuario.setText("CPF:");
        painelObrigatorio.add(lblCPFUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        txtIDUsuario.setEnabled(false);
        painelObrigatorio.add(txtIDUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 30, 30));

        lblID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblID.setForeground(java.awt.Color.white);
        lblID.setText("ID:");
        painelObrigatorio.add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        PanelDadosUsuario.add(painelObrigatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 320, 60));

        cbPerfilUsuarios.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbPerfilUsuarios.setForeground(new java.awt.Color(0, 102, 102));
        cbPerfilUsuarios.setMaximumRowCount(9);
        cbPerfilUsuarios.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "ANALISTA", "ADMIN", "USER1", "USER2", "MEDICO" }));
        cbPerfilUsuarios.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbPerfilUsuariosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbPerfilUsuariosFocusLost(evt);
            }
        });
        cbPerfilUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerfilUsuariosActionPerformed(evt);
            }
        });
        cbPerfilUsuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbPerfilUsuariosKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(cbPerfilUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 80, 30));

        cbEstaUsuario.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbEstaUsuario.setForeground(new java.awt.Color(0, 102, 102));
        cbEstaUsuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "ATIVO", "INATIVO" }));
        cbEstaUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbEstaUsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbEstaUsuarioFocusLost(evt);
            }
        });
        cbEstaUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbEstaUsuarioKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(cbEstaUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 90, 30));

        lblEstadoUsuario.setForeground(new java.awt.Color(0, 102, 102));
        lblEstadoUsuario.setText("Estado Usuário:");
        PanelDadosUsuario.add(lblEstadoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, -1, -1));

        lblFonePreferencial.setForeground(new java.awt.Color(0, 102, 102));
        PanelDadosUsuario.add(lblFonePreferencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 100, 30));

        PanelBotoesManipulacaoBancoDados.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.disabledShadow"));

        btnCancelar.setToolTipText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setToolTipText("Gravar Registro");
        btnSalvar.setEnabled(false);
        btnSalvar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnSalvar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSalvarFocusGained(evt);
            }
        });
        btnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalvarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalvarMouseExited(evt);
            }
        });
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        btnSalvar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalvarKeyPressed(evt);
            }
        });

        btnEditar.setToolTipText("Editar Registro");
        btnEditar.setEnabled(false);
        btnEditar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEditarFocusGained(evt);
            }
        });
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnAdicionar.setToolTipText("Adicionar Registro");
        btnAdicionar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnAdicionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseExited(evt);
            }
        });
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnExcluir.setToolTipText("Excluir Registro");
        btnExcluir.setEnabled(false);
        btnExcluir.setPreferredSize(new java.awt.Dimension(45, 45));
        btnExcluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnExcluirFocusGained(evt);
            }
        });
        btnExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirMouseExited(evt);
            }
        });
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBotoesManipulacaoBancoDadosLayout = new javax.swing.GroupLayout(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setLayout(PanelBotoesManipulacaoBancoDadosLayout);
        PanelBotoesManipulacaoBancoDadosLayout.setHorizontalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblNuvemForms, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        PanelBotoesManipulacaoBancoDadosLayout.setVerticalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblNuvemForms, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa Rápida:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtPesquisa.setForeground(new java.awt.Color(0, 102, 102));
        txtPesquisa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPesquisaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPesquisaFocusLost(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyPressed(evt);
            }
        });
        jPanel1.add(txtPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 270, 35));

        btnPesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesquisarFocusLost(evt);
            }
        });
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        jPanel1.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 32, 32));

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "LOGIN", "PERFIL ", "CELULAR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);
        if (tblUsuarios.getColumnModel().getColumnCount() > 0) {
            tblUsuarios.getColumnModel().getColumn(0).setMinWidth(40);
            tblUsuarios.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblUsuarios.getColumnModel().getColumn(0).setMaxWidth(40);
            tblUsuarios.getColumnModel().getColumn(1).setResizable(false);
            tblUsuarios.getColumnModel().getColumn(1).setPreferredWidth(130);
            tblUsuarios.getColumnModel().getColumn(2).setMinWidth(70);
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(120);
            tblUsuarios.getColumnModel().getColumn(2).setMaxWidth(70);
            tblUsuarios.getColumnModel().getColumn(3).setResizable(false);
            tblUsuarios.getColumnModel().getColumn(3).setPreferredWidth(90);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelBotoesManipulacaoBancoDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelDadosUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PanelBotoesManipulacaoBancoDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(PanelDadosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed

        if (!txtLogin.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                txtLogin.setText(MetodoStaticosUtil.removerAcentosCaixAlta(MetodoStaticosUtil.removerTodosEspacosEmBranco(txtLogin.getText())));
                txtSenhaPS.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtUsuario.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo [LOGIN] é Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtLogin.requestFocus();

            }

        }
    }//GEN-LAST:event_txtLoginKeyPressed

    private void txtLoginKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyTyped

        int numeroDeCaracter = 15;

        if (txtLogin.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 15 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtLogin.setBackground(Color.YELLOW);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Login: Máximo 15 caracteres");
        }
    }//GEN-LAST:event_txtLoginKeyTyped

    private void txtSenhaPSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaPSKeyPressed

        if (!txtSenhaPS.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                txtSenhaPS.setText(txtSenhaPS.getText().trim());
                cbSexo.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                txtLogin.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo [SENHA] é Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtLogin.requestFocus();

            }

        }
    }//GEN-LAST:event_txtSenhaPSKeyPressed

    private void txtSenhaPSKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaPSKeyTyped

        int numeroDeCaracter = 7;

        if (txtSenhaPS.getPassword().length > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 8 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtSenhaPS.setBackground(Color.YELLOW);//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Senha: Máximo 8 caracteres");
        }
    }//GEN-LAST:event_txtSenhaPSKeyTyped

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed

        if (!txtUsuario.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                txtUsuario.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtUsuario.getText().trim()));
                btnCadastroFone.setEnabled(true);
                btnEmailPreferencial.setEnabled(true);

                txtLogin.requestFocus();
            }
        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo [USUÁRIO] é Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

        }
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        int numeroDeCaracter = 40;

        if (txtUsuario.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 40 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtUsuario.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 42 caracteres");

        }
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void acaoBtnCancelar() {

        /**
         * Após salvar limpar os campos
         */
        /**
         * Após salvar limpar os campos
         */
        this.txtCPF.setText("");
        this.txtIDUsuario.setText("");
        this.txtUsuario.setText("");
        this.txtLogin.setText("");
        this.txtSenhaPS.setText("");
        this.lblFonePreferencial.setText("");
        this.cbPerfilUsuarios.setSelectedItem(null);
        this.cbSexo.setSelectedItem(null);
        this.txtGmail.setText("");
        this.txtPesquisa.setText("");
        this.txtCPF.setText("");
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        this.txtCPF.setEnabled(false);
        this.txtIDUsuario.setEnabled(false);
        this.txtUsuario.setEnabled(false);
        this.cbPerfilUsuarios.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.cbEstaUsuario.setEnabled(false);
        this.txtLogin.setEnabled(false);
        this.txtSenhaPS.setEnabled(false);
        this.lblFonePreferencial.setEnabled(false);
        this.txtGmail.setEnabled(false);

        this.txtPesquisa.setEnabled(true);

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        this.btnSalvar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnAdicionar.setEnabled(true);
        this.btnCadastroFone.setEnabled(false);
        this.btnEmailPreferencial.setEnabled(false);
        this.btnValidaCPF.setEnabled(false);

        this.btnEditar.setEnabled(false);
        this.btnExcluir.setEnabled(false);
        JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                + "Registro Cancelado."
                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
    }


    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBtnCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void desabilitarCampos() {

        lblFonePreferencial.setEnabled(false);
        txtIDUsuario.setEnabled(false);
        txtLogin.setEnabled(false);
        txtPesquisa.setEnabled(false);
        txtSenhaPS.setEnabled(false);
        txtUsuario.setEnabled(false);
        cbPerfilUsuarios.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnSalvar.setEnabled(false);
    }


    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

    }//GEN-LAST:event_btnSalvarFocusGained

    private void acaoBotaoSalvar() {

        //coloca o cursor em modo de espera 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            salvar();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void acaoBotaoEditar() {

        flag = 2;

        /**
         * Devido as fotos da kelly, Suelma e Jardeane não é possivel mais
         * liberar o campo de login para ser editado pois exite codificação na
         * em Login que faz referencia a informaçao desse campo não podendo mais
         * ser alterado depois de cadastrado o mesmo acontece com o campo
         * txtUsuario agora está vinculado ao médico que irá realizar a consulta
         * há um filtro que depende do nome completo dele
         */
        txtUsuario.setEnabled(false);
        txtLogin.setEnabled(false);
        txtSenhaPS.setEnabled(true);

        cbPerfilUsuarios.setEnabled(true);
        cbEstaUsuario.setEnabled(true);
        cbSexo.setEnabled(true);

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnEditar.setEnabled(false);
        btnAdicionar.setEnabled(false);
        btnExcluir.setEnabled(false);
        //outros botoes
        btnCadastroFone.setEnabled(true);
        btnEmailPreferencial.setEnabled(true);

        cbPerfilUsuarios.removeAllItems();
        cbPerfilUsuarios.addItem("Selecione...");
        cbPerfilUsuarios.addItem("ADMIN");
        cbPerfilUsuarios.addItem("USER1");
        cbPerfilUsuarios.addItem("USER2");
        cbPerfilUsuarios.addItem("MEDICO");

    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        acaoBotaoEditar();


    }//GEN-LAST:event_btnEditarActionPerformed

    private void habilitarCampos() {
        this.txtUsuario.setEnabled(true);
        this.txtLogin.setEnabled(true);
        this.cbPerfilUsuarios.setEnabled(true);
        this.txtSenhaPS.setEnabled(true);

        this.cbSexo.setEnabled(true);
        this.cbEstaUsuario.setEnabled(true);

        /**
         * Limpar os campos para cadastrar
         */
        //txtIDUsuario.setVisible(false);
        this.txtUsuario.setText("");
        this.txtSenhaPS.setText("");
        this.txtLogin.setText("");
        this.lblFonePreferencial.setText("");

        /**
         * Botões que deverão ficar habilitados nesse evento para esse tipo de
         * Formulario
         */
        this.btnAdicionar.setEnabled(false);
        this.btnSalvar.setEnabled(true);
        this.btnCancelar.setEnabled(true);

        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        this.txtPesquisa.setEnabled(false);
        this.btnEditar.setEnabled(false);

        this.txtUsuario.requestFocus();//setar o campo nome Bairro após adicionar
        this.txtUsuario.setBackground(Color.WHITE);  // altera a cor do fundo
        this.txtUsuario.setForeground(new Color(0, 102, 102));

        this.cbSexo.setSelectedItem("Selecione...");
        this.cbPerfilUsuarios.setSelectedItem("Selecione...");
        this.cbEstaUsuario.setSelectedItem("Selecione...");

        verificarCombos();
    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        gerarCodigoGUIeDataSistema();
        //limpar
        this.txtCPF.setText("");

        /**
         * Campos devem ser ativados
         */
        this.txtCPF.setEnabled(true);
        this.txtCPF.requestFocus();
        this.txtCPF.setBackground(Color.YELLOW);
        this.txtCPF.setForeground((new Color(0, 102, 102)));
        this.btnValidaCPF.setEnabled(true);
        this.btnCancelar.setEnabled(true);

        if (!lblPerfil.getText().equalsIgnoreCase("ANALISTA")) {
            cbPerfilUsuarios.removeAllItems();
            cbPerfilUsuarios.addItem("Selecione...");
            cbPerfilUsuarios.addItem("ANALISTA");
            cbPerfilUsuarios.addItem("ADMIN");
            cbPerfilUsuarios.addItem("USER1");
            cbPerfilUsuarios.addItem("USER2");
            cbPerfilUsuarios.addItem("MEDICO");

        } else {

            cbPerfilUsuarios.removeAllItems();
            cbPerfilUsuarios.addItem("Selecione...");
            cbPerfilUsuarios.addItem("ADMIN");
            cbPerfilUsuarios.addItem("USER1");
            cbPerfilUsuarios.addItem("USER2");
            cbPerfilUsuarios.addItem("MEDICO");

        }
    }
    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void acaoExcluirRegistro() {

        /*Evento ao ser clicado executa código*/
        int excluir = JOptionPane.showConfirmDialog(null, "Deseja Excluir Registro?", "Informação", JOptionPane.YES_NO_OPTION);

        if (excluir == JOptionPane.YES_OPTION) {
            usuarioDTO.setIdUserDto(Integer.parseInt(txtIDUsuario.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            usuarioBO.ExcluirBO(usuarioDTO);
            /**
             * Após salvar limpar os campos
             */
            txtIDUsuario.setText("");
            txtUsuario.setText("");
            txtLogin.setText("");
            txtSenhaPS.setText("");
            lblFonePreferencial.setText("");
            cbPerfilUsuarios.setSelectedItem(null);
            btnAdicionar.setEnabled(true);
            btnExcluir.setEnabled(false);
            btnEditar.setEnabled(false);
            try {
                /**
                 * Conta o Número de linhas na minha tabela e armazena na
                 * variável numeroLinas
                 * https://www.youtube.com/watch?v=1fKwn-Vd0uc
                 */
                int numeroLinhas = tblUsuarios.getRowCount();
                if (numeroLinhas > 0) {

                    //http://andersonneto.blogspot.com.br/2015/05/tutorial-remover-todas-as-linhas-de-um.html
                    while (tblUsuarios.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);
                    }
                    addRowJTable();
                } else {
                    addRowJTable();
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }

    }

    private void acaoBotaoExcluir() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            acaoExcluirRegistro();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        acaoBotaoExcluir();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed

        if (txtPesquisa.getText().equalsIgnoreCase("")) {

            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtPesquisa.requestFocus();
                txtPesquisa.setBackground(Color.YELLOW);
                txtPesquisa.setForeground(Color.BLACK);

                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("Digite o Registro a ser pesquisado [ENTER]");
                lblLinhaInformativa.setForeground(Color.red);
                lblLinhaInformativa.setFont(f);
                try {

                    int numeroLinhas = tblUsuarios.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tblUsuarios.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

                        }
                        addRowJTable();

                    } else {
                        addRowJTable();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        if (!txtPesquisa.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                btnPesquisar.requestFocus();

                //coloca o cursor em modo de espera 
                setCursor(new Cursor(Cursor.WAIT_CURSOR));

                // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
                //  JOptionPane.showMessageDialog(this, "flag: " + flag);
                ConexaoUtil conecta = new ConexaoUtil();
                boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
                if (recebeConexao == true) {

                    try {

                        int numeroLinhas = tblUsuarios.getRowCount();

                        if (numeroLinhas > 0) {

                            while (tblUsuarios.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

                            }

                            pesquisarUsuario();
                        } else {
                            addRowJTable();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    //coloca o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                            + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                }
            }
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed
    private void acaoMouseClicked() {

        int codigo = Integer.parseInt("" + tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0));

        try {

            UsuarioDTO retorno = usuarioDAO.buscarPorIdTblConsultaList(codigo);

            if ((lblPerfil.getText().equalsIgnoreCase("ANALISTA") && retorno.getPerfilDto().equalsIgnoreCase("ANALISTA"))
                    || lblPerfil.getText().equalsIgnoreCase("ANALISTA") && !retorno.getPerfilDto().equalsIgnoreCase("ANALISTA")) {

                if (retorno.getIdUserDto() != null || !retorno.getIdUserDto().equals("")) {

                    txtIDUsuario.setText(String.valueOf(retorno.getIdUserDto()));//id
                    txtCPF.setText(retorno.getCpfDto());//cpf
                    txtUsuario.setText(retorno.getUsuarioDto());//nome
                    txtLogin.setText(retorno.getLoginDto());//login
                    txtSenhaPS.setText(retorno.getSenhaDto());//senha

                    cbSexo.setSelectedItem(retorno.getSexoDto());//sexo

                    if (!cbPerfilUsuarios.equals("Selecione...")) {
                        cbPerfilUsuarios.setSelectedItem(retorno.getPerfilDto().toString());
                    }

                    cbEstaUsuario.setSelectedItem(retorno.getEstadoDto());//estado 
                    lblFonePreferencial.setText(retorno.getCelularPrincipalDto());//celular principal 
                    txtGmail.setText(retorno.getEmailPrincipalDto());//email principal 

                    /**
                     * Liberar os botões abaixo
                     */
                    btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                    btnAdicionar.setEnabled(false);
                    btnCancelar.setEnabled(true);
                    btnSalvar.setEnabled(false);
                    txtGmail.setEnabled(true);
                    /**
                     * Habilitar Campos
                     */
                    txtPesquisa.setEnabled(true);

                    /**
                     * Desabilitar campos
                     */
                    /**
                     * Também irá habilitar nossos campos para que possamos
                     * digitar os dados no formulario medicos
                     */
                    this.txtUsuario.setEnabled(false);
                    this.cbPerfilUsuarios.setEnabled(false);
                    this.cbSexo.setEnabled(false);
                    this.cbEstaUsuario.setEnabled(false);
                    this.txtLogin.setEnabled(false);
                    this.txtSenhaPS.setEnabled(false);
                    this.lblFonePreferencial.setEnabled(false);
                    this.txtGmail.setEnabled(false);
                    this.btnValidaCPF.setEnabled(false);
                    this.txtCPF.setEnabled(false);
                    lblLinhaInformativa.setText("");
                    //tira o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            if (!lblPerfil.getText().equalsIgnoreCase("ANALISTA") && retorno.getPerfilDto().equalsIgnoreCase("ANALISTA")) {

                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("Registro protegido contra alteração");
                lblLinhaInformativa.setFont(f);
                lblLinhaInformativa.setForeground(Color.red);
                //tira o cursor em modo de espera 
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Registro Protegido Sem Permissão para alterá-lo"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }

            if (!lblPerfil.getText().equalsIgnoreCase("ANALISTA") && !retorno.getPerfilDto().equalsIgnoreCase("ANALISTA")) {

                if (retorno.getIdUserDto() != null || !retorno.getIdUserDto().equals("")) {

                    txtIDUsuario.setText(String.valueOf(retorno.getIdUserDto()));//id
                    txtCPF.setText(retorno.getCpfDto());//cpf
                    txtUsuario.setText(retorno.getUsuarioDto());//nome
                    txtLogin.setText(retorno.getLoginDto());//login
                    txtSenhaPS.setText(retorno.getSenhaDto());//senha

                    cbSexo.setSelectedItem(retorno.getSexoDto());//sexo

                    if (!cbPerfilUsuarios.equals("Selecione...")) {
                        cbPerfilUsuarios.setSelectedItem(retorno.getPerfilDto().toString());

                    }

                    cbEstaUsuario.setSelectedItem(retorno.getEstadoDto());//estado 
                    lblFonePreferencial.setText(retorno.getCelularPrincipalDto());//celular principal 
                    txtGmail.setText(retorno.getEmailPrincipalDto());//email principal 

                    /**
                     * Liberar os botões abaixo
                     */
                    btnEditar.setEnabled(true);
                    btnExcluir.setEnabled(true);
                    btnAdicionar.setEnabled(false);
                    btnCancelar.setEnabled(true);
                    btnSalvar.setEnabled(false);
                    txtGmail.setEnabled(true);
                    /**
                     * Habilitar Campos
                     */
                    txtPesquisa.setEnabled(true);

                    /**
                     * Desabilitar campos
                     */
                    /**
                     * Também irá habilitar nossos campos para que possamos
                     * digitar os dados no formulario medicos
                     */
                    this.txtUsuario.setEnabled(false);
                    this.cbPerfilUsuarios.setEnabled(false);
                    this.cbSexo.setEnabled(false);
                    this.cbEstaUsuario.setEnabled(false);
                    this.txtLogin.setEnabled(false);
                    this.txtSenhaPS.setEnabled(false);
                    this.lblFonePreferencial.setEnabled(false);
                    this.txtGmail.setEnabled(false);
                    this.btnValidaCPF.setEnabled(false);
                    this.txtCPF.setEnabled(false);
                    lblLinhaInformativa.setText("");
                    //tira o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + ex.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }
    }


    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked

        //tira o cursor em modo de espera 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoMouseClicked();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            desabilitarCampos();
            desabilitarTodosBotoes();
        }


    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void acaoBotaoPesquisar() {

        int numeroLinhas = tblUsuarios.getRowCount();

        if (numeroLinhas > 0) {

            while (tblUsuarios.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tblUsuarios.getModel()).removeRow(0);

            }

            pesquisarUsuario();

        } else {
            addRowJTable();
        }

    }


    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

// 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoBotaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }
    }//GEN-LAST:event_btnPesquisarActionPerformed


    private void btnCadastroFoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCadastroFoneFocusGained
        btnCadastroFone.setBackground(new Color(0, 102, 102));
        btnCadastroFone.setForeground(Color.WHITE);

    }//GEN-LAST:event_btnCadastroFoneFocusGained

    private void btnCadastroFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroFoneActionPerformed

        String perfil = (String) cbPerfilUsuarios.getSelectedItem();
        if (perfil.equals("Selecione...")) {
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Selecione um Perfil para o Usuário [OBRIGATÓRIO]");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
            cbPerfilUsuarios.requestFocus();
        } else {

            if (estaFechado(formFone)) {
                formFone = new SubTelaFone();
                DeskTop.add(formFone).setLocation(5, 3);
                formFone.setTitle("Cadasto de Celulares/Usuários");
                formFone.setVisible(true);

            } else {
                formFone.toFront();
                formFone.setVisible(true);

            }

        }

    }//GEN-LAST:event_btnCadastroFoneActionPerformed

    private void txtUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusGained
        txtUsuario.setBackground(new Color(0, 102, 102));
        txtUsuario.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe o Nome do Usuário");
        lblLinhaInformativa.setFont(f);
    }//GEN-LAST:event_txtUsuarioFocusGained

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        txtUsuario.setBackground(Color.WHITE);
        txtUsuario.setForeground(Color.BLACK);

        if (!txtUsuario.getText().isEmpty()) {
            btnCadastroFone.setEnabled(true);
            btnEmailPreferencial.setEnabled(true);
        }
    }//GEN-LAST:event_txtUsuarioFocusLost

    private void txtCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusGained
        txtCPF.setBackground(Color.YELLOW);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o CPF");


    }//GEN-LAST:event_txtCPFFocusGained

    private void txtCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusLost
        txtCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCPFFocusLost

    private void txtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFActionPerformed

    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed

        if (!txtCPF.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                //this.btnValidaCPF.requestFocus();
                acaoValidaCPFPolindoDados();
            }
        }
        if (txtCPF.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                txtCPF.requestFocus();
                JOptionPane.showMessageDialog(this, "" + "\n Obrigatório CPF.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }
        }

    }//GEN-LAST:event_txtCPFKeyPressed

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained
        btnValidaCPF.setBackground(Color.YELLOW);
        this.lblLinhaInformativa.setFont(f);
        this.lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        this.lblLinhaInformativa.setText("");
        this.lblLinhaInformativa.setText("Click sobre o [BOTÃO] ou Pressione [ENTER]");
    }//GEN-LAST:event_btnValidaCPFFocusGained

    private void btnValidaCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusLost
        this.btnValidaCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnValidaCPFFocusLost

    private void acaoValidaCPFPolindoDados() {

        /**
         * Primeiro criamos uma String com o nome de [CNPJ] e capturamos o valor
         * digitado no campo txtCNPJ por meio do método getText() onde ficará
         * armazenado na variável CNPJ criado para receber o valor capturado
         * pelou usuário.
         */
        usuarioDTO.setCpfDto(this.txtCPF.getText());

        try {
            //robo conectado ao servidor google 
            boolean retornoVerifcaDuplicidade = usuarioDAO.verificaDuplicidadeCPF(usuarioDTO);//verificar se já existe CNPJ

            if (retornoVerifcaDuplicidade == false) {

                /**
                 * Criamos um contador que será incrementado a medida que
                 * estiver sendo executado na string passando por cada caracter
                 * da mesma e nos dando a posição exata onde se encontra para
                 * que possamos fazer uma intervenção exata.
                 */
                int cont = 0;

                /**
                 * Inicia-se o for que irá percorrer o tamanho total da variável
                 * CNPJ que guarda o valor capturado do campo txtCNPJ
                 */
                for (int i = 0; i < usuarioDTO.getCpfDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (usuarioDTO.getCpfDto().charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             *
                             */
                            usuarioDTO.setCpfDto(usuarioDTO.getCpfDto().replace(usuarioDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 12) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (usuarioDTO.getCpfDto().charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            usuarioDTO.setCpfDto(usuarioDTO.getCpfDto().replace(usuarioDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cpfTratado = usuarioDTO.getCpfDto().replace(" ", "");

                /**
                 * A baixo fazemos a aplicação da função que irá validar se o
                 * cnpj é válido ou não isCNPJ
                 */
                boolean recebeCPF = MetodoStaticosUtil.isCPF(cpfTratado);
                /**
                 * se o retorno for verdadeiro CNPJ válido caso contrário CNPJ
                 * Inválido
                 */
                if (recebeCPF == true) {
                    //   JOptionPane.showMessageDialog(this, "" + "\n Validado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                    txtCPF.setEditable(true);
                    txtCPF.setBackground(Color.WHITE);

                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setText("Validado com Sucesso.");

                    filtrarSeMedicoCadastrado();

                    lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                    txtUsuario.requestFocus();
                    txtCPF.setEnabled(false);
                    btnValidaCPF.setEnabled(false);

                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n CPF Inválido.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtCPF.setBackground(Color.YELLOW);
                    txtCPF.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Registro Duplicado.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.RED);

            }
        } catch (PersistenciaException ex) {
            //robo conectado servidor google 
            erroViaEmail(ex.getMessage(), "acaoValidaCPFPolindoDados()\n"
                    + "Camada GUI - validando cpf e polindo dados ");
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    private void filtrarSeMedicoCadastrado() {

        ArrayList<MedicoDTO> list;

        try {

            list = (ArrayList<MedicoDTO>) medicoDAO.filtrarPesqRapidaPorCPF(txtCPF.getText());
            //JOptionPane.showMessageDialog(null, "Tamanho da Lista:" + list.size());
            if (list.size() > 0) {

                cbPerfilUsuarios.setEnabled(true);
                txtLogin.setEnabled(true);
                txtSenhaPS.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnSalvar.setEnabled(true);

                for (int i = 0; i < list.size(); i++) {
                    txtUsuario.setText(list.get(i).getNomeDto());
                    cbSexo.setSelectedItem((String) list.get(i).getSexoDto());
                    cbPerfilUsuarios.setSelectedItem("MEDICO");
                    lblFonePreferencial.setText(list.get(i).getCelularPrefDto());
                    cbEstaUsuario.setSelectedItem("ATIVO");
                    txtGmail.setText(list.get(i).getEmailPrefDto());
                }

            } else {
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setFont(f);
                lblLinhaInformativa.setText("Cadastro Liberado. Obs.: Médico não cadastrado.");
                lblLinhaInformativa.setForeground(Color.BLACK);
                lblLinhaInformativa.setBackground(Color.ORANGE);
                acaoCNPJTrue();

            }

        } catch (PersistenciaException ex) {
            ex.getMessage();

        }

    }

    private void acaoCNPJTrue() {
        habilitarCampos();
        //btnSalvar.requestFocus();
    }

    private void erroViaEmail(String mensagemErro, String metodo) {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        //agora iremos utilizar os objetos das Bibliotecas referente Email
        //commons-email-1.5.jar e mail-1.4.7.jar
        SimpleEmail email = new SimpleEmail();
        //Agora podemos utilizar o objeto email do Tipo SimplesEmail
        //a primeira cois a fazer é acessar o host abaixo estarei usando
        //o host do google para enviar as informações
        email.setHostName("smtp.gmail.com");
        //O proximo método abaixo é para configurar a porta desse host
        email.setSmtpPort(465);
        //esse método vai autenticar a conexão o envio desse email
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        //esse método vai ativar a conexao de forma segura 
        email.setSSLOnConnect(true);

        //agora um try porque os outro métodos podem gerar erros daí devem está dentro de um bloco de exceções
        try {
            //configura de quem é esse email de onde ele está vindo 
            email.setFrom(meuEmail);
            //configurar o assunto 
            email.setSubject("Erro Camada DAO - " + metodo);
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                    + "Erro Método de Envio Email:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            e.printStackTrace();
        }
    }

    private void btnValidaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCPFActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoValidaCPFPolindoDados();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
        }
    }//GEN-LAST:event_btnValidaCPFActionPerformed

    private void btnValidaCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnValidaCPFKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            //se ele executar usando a ação keyPress desabilita o botão 
            btnValidaCPF.setEnabled(false);
            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {
                acaoValidaCPFPolindoDados();
            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }

        }

        if (evt.getKeyCode() == evt.VK_RIGHT) {
            cbSexo.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtCPF.requestFocus();
        }
    }//GEN-LAST:event_btnValidaCPFKeyPressed

    private void cbPerfilUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPerfilUsuariosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPerfilUsuariosActionPerformed

    private void cbSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSexoActionPerformed

    private void obrigarPreenchimentoUsuario() {
        if (txtUsuario.getText().isEmpty()) {
            txtUsuario.requestFocus();
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("É obrigatório o preenchimento do Campo Nome");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
        }

    }

    private void txtLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusGained
        txtLogin.setBackground(new Color(0, 102, 102));
        txtLogin.setForeground(Color.WHITE);
        obrigarPreenchimentoUsuario();

    }//GEN-LAST:event_txtLoginFocusGained

    private void txtLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusLost
        txtLogin.setBackground(Color.WHITE);
        txtLogin.setForeground(Color.BLACK);

    }//GEN-LAST:event_txtLoginFocusLost

    private void txtSenhaPSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaPSFocusGained
        txtSenhaPS.setBackground(new Color(0, 102, 102));
        txtSenhaPS.setForeground(Color.WHITE);
        obrigarPreenchimentoUsuario();
    }//GEN-LAST:event_txtSenhaPSFocusGained

    private void txtSenhaPSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaPSFocusLost
        txtSenhaPS.setBackground(Color.WHITE);
        txtSenhaPS.setForeground(Color.BLACK);
        obrigarPreenchimentoUsuario();
    }//GEN-LAST:event_txtSenhaPSFocusLost

    private void cbPerfilUsuariosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbPerfilUsuariosFocusGained
        cbPerfilUsuarios.setBackground(new Color(0, 102, 102));
        cbPerfilUsuarios.setForeground(Color.WHITE);
        obrigarPreenchimentoUsuario();

    }//GEN-LAST:event_cbPerfilUsuariosFocusGained

    private void cbPerfilUsuariosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbPerfilUsuariosFocusLost
        cbPerfilUsuarios.setBackground(Color.WHITE);
        cbPerfilUsuarios.setForeground(Color.BLACK);

    }//GEN-LAST:event_cbPerfilUsuariosFocusLost

    private void cbSexoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusGained
        cbSexo.setBackground(new Color(0, 102, 102));
        cbSexo.setForeground(Color.WHITE);
        obrigarPreenchimentoUsuario();

    }//GEN-LAST:event_cbSexoFocusGained

    private void cbSexoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusLost
        cbSexo.setBackground(Color.WHITE);
        cbSexo.setForeground(Color.BLACK);

    }//GEN-LAST:event_cbSexoFocusLost

    private void txtGmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGmailFocusGained
        txtGmail.setBackground(new Color(0, 102, 102));
        txtGmail.setForeground(Color.WHITE);

    }//GEN-LAST:event_txtGmailFocusGained

    private void txtGmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGmailFocusLost
        txtGmail.setBackground(Color.WHITE);
        txtGmail.setForeground(Color.BLACK);

    }//GEN-LAST:event_txtGmailFocusLost

    private void btnEmailPreferencialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEmailPreferencialFocusGained
        btnEmailPreferencial.setBackground(new Color(0, 102, 102));
        btnEmailPreferencial.setForeground(Color.WHITE);

        txtGmail.setEnabled(true);
        txtGmail.requestFocus();
    }//GEN-LAST:event_btnEmailPreferencialFocusGained

    private void cbSexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSexoKeyPressed

        if (!cbSexo.getSelectedItem().equals("Selecione...")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                cbPerfilUsuarios.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                txtSenhaPS.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_UP) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Obrigatório Selecione Sexo Usuário  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                cbSexo.requestFocus();

            }

            if (evt.getKeyCode() == evt.VK_LEFT) {
                txtSenhaPS.requestFocus();
            }

        }
    }//GEN-LAST:event_cbSexoKeyPressed

    private void cbPerfilUsuariosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbPerfilUsuariosKeyPressed

        if (!cbPerfilUsuarios.getSelectedItem().equals("Selecione...")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                btnCadastroFone.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                cbSexo.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_UP) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Obrigatório Selecione Perfil do Usuário  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                cbPerfilUsuarios.requestFocus();

            }

            if (evt.getKeyCode() == evt.VK_LEFT) {
                cbSexo.requestFocus();
            }

        }
    }//GEN-LAST:event_cbPerfilUsuariosKeyPressed

    private void btnCadastroFoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCadastroFoneKeyPressed

        //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

            //polindo dados Campo Usuário
            cbEstaUsuario.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
            cbPerfilUsuarios.requestFocus();
        }


    }//GEN-LAST:event_btnCadastroFoneKeyPressed

    private void btnCadastroFoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCadastroFoneFocusLost
        btnCadastroFone.setBackground(Color.WHITE);
        btnCadastroFone.setForeground(Color.BLACK);

    }//GEN-LAST:event_btnCadastroFoneFocusLost

    private void cbEstaUsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEstaUsuarioFocusGained
        cbEstaUsuario.setBackground(new Color(0, 102, 102));
        cbEstaUsuario.setForeground(Color.WHITE);
        obrigarPreenchimentoUsuario();

    }//GEN-LAST:event_cbEstaUsuarioFocusGained

    private void cbEstaUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEstaUsuarioFocusLost
        cbEstaUsuario.setBackground(Color.WHITE);
        cbEstaUsuario.setForeground(Color.BLACK);

    }//GEN-LAST:event_cbEstaUsuarioFocusLost

    private void cbEstaUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbEstaUsuarioKeyPressed

        if (!cbEstaUsuario.getSelectedItem().equals("Selecione...")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                btnEmailPreferencial.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                btnCadastroFone.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_UP) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Obrigatório Selecione Estado \nAtivo ou Inativo do Usuário  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                cbEstaUsuario.requestFocus();

            }

            if (evt.getKeyCode() == evt.VK_LEFT) {
                btnCadastroFone.requestFocus();
            }

        }
    }//GEN-LAST:event_cbEstaUsuarioKeyPressed

    private void btnEmailPreferencialFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEmailPreferencialFocusLost
        btnEmailPreferencial.setBackground(Color.WHITE);
        btnEmailPreferencial.setForeground(Color.BLACK);

    }//GEN-LAST:event_btnEmailPreferencialFocusLost

    private void btnEmailPreferencialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnEmailPreferencialKeyPressed

        //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

            //polindo dados Campo Usuário
            btnSalvar.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
            cbEstaUsuario.requestFocus();
        }

    }//GEN-LAST:event_btnEmailPreferencialKeyPressed

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        btnEditar.setBackground(new Color(0, 102, 102));

        this.lblLinhaInformativa.setText("");
        this.lblLinhaInformativa.setText("Edita o Registro Selecionado");

        this.lblLinhaInformativa.setFont(f);
        this.lblLinhaInformativa.setForeground(new Color(0, 102, 102));


    }//GEN-LAST:event_btnEditarFocusGained

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        if ((lblPerfil.getText().equalsIgnoreCase("ANALISTA"))) {
            btnExcluir.setEnabled(true);
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Liberado para Exclusão");
        } else {
            btnExcluir.setEnabled(false);
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Perfil não possui autorização de Exclusão");

        }
    }//GEN-LAST:event_btnExcluirFocusGained

    private void txtPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusGained
        txtPesquisa.setBackground(new Color(0, 102, 102));
        txtPesquisa.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtPesquisaFocusGained

    private void txtPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusLost
        txtPesquisa.setBackground(Color.WHITE);
        txtPesquisa.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtPesquisaFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        btnPesquisar.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);

    }//GEN-LAST:event_btnPesquisarFocusLost

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instance = null;

    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        if (estaFechado(formFone) == false) {
            formFone.dispose();
        }
    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JPanel PanelDadosUsuario;
    private javax.swing.JPanel PanelJTable;
    public static javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCadastroFone;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEmailPreferencial;
    public static javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    public static javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnValidaCPF;
    private javax.swing.JComboBox cbEstaUsuario;
    public static javax.swing.JComboBox cbPerfilUsuarios;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCPFUsuario;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblEstadoUsuario;
    public static javax.swing.JLabel lblFonePreferencial;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JLabel lblPerfilRotulo;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel painelObrigatorio;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tblUsuarios;
    public static javax.swing.JFormattedTextField txtCPF;
    private javax.swing.JTextField txtGmail;
    public static javax.swing.JTextField txtIDUsuario;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JPasswordField txtSenhaPS;
    public static javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
