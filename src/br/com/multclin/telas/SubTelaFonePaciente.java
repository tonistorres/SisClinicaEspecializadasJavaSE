package br.com.multclin.telas;

import br.com.multclin.bo.FoneBO;
import br.com.multclin.dao.FoneDAO;
import br.com.multclin.dto.FoneDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;

import static br.com.multclin.telas.TelaPrincipal.DeskTop;

//objetos vindo de outros formulários 
//*******************
//TELA PRINCIPAL 
//******************
//*******************
//TELA PACIENTE
//******************
//*******************
//TELA PACIENTE 
//******************
import static br.com.multclin.telas.TelaPaciente.txtID;
import static br.com.multclin.telas.TelaPaciente.txtCPFPaciente;
import static br.com.multclin.telas.TelaPaciente.txtNome;
import static br.com.multclin.telas.TelaPaciente.lblFonePreferencial;

import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author DaTorres
 */
public class SubTelaFonePaciente extends javax.swing.JInternalFrame {

    FoneDTO foneDTO = new FoneDTO();
    FoneDAO foneDAO = new FoneDAO();
    FoneBO foneBO = new FoneBO();

    Font f = new Font("Tahoma", Font.BOLD, 9);

    int flag = 0;

    br.com.multclin.telas.FrmListaPaisRegiao formListaPaisRegiao;
    br.com.multclin.telas.TelaPaciente formUsuario;
    br.com.multclin.telas.TelaPaciente formPaciente;

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
    private static SubTelaFonePaciente instance = null;

    public static SubTelaFonePaciente getInstance() {

        if (instance == null) {

            instance = new SubTelaFonePaciente();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public SubTelaFonePaciente() {
        initComponents();

        aoCarregarForm();
        addRowJTable();
        personalizacaoFrontEnd();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        //tabela.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela         
        tabela.getTableHeader().setDefaultRenderer(new br.com.multclin.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        tabela.setDefaultRenderer(Object.class, new Render());
        //********************************************************************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 9));
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
        tabela.setRowHeight(25);

        //*************************************************************************
    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<FoneDTO> list;

        try {

            JButton btnPreferencial = new JButton("Lançar");
            JButton btnLancaEdicao = new JButton("Editar");
            JButton btnLancaExclusao = new JButton("delete");

            btnPreferencial.setName("PF");
            btnLancaEdicao.setName("Ed");
            btnLancaExclusao.setName("Ex");

            String cpf = lblRepositCPF.getText().toString().trim();

            list = (ArrayList<FoneDTO>) foneDAO.listarTodosParametro(cpf);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[5];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdFoneAutoIncrementoDto();
                rowData[1] = list.get(i).getFoneDto();
                rowData[2] = btnLancaEdicao;
                rowData[3] = btnLancaExclusao;
                rowData[4] = btnPreferencial;

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(90);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    public void pesquisar() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<FoneDTO> list;

        try {

            JButton btnPreferencial = new JButton("Lançar");
            JButton btnLancaEdicao = new JButton("Editar");
            JButton btnLancaExclusao = new JButton("delete");

            btnPreferencial.setName("PF");
            btnLancaEdicao.setName("Ed");
            btnLancaExclusao.setName("Ex");

            String celular = txtBuscar.getText().toString();
            String cpf = lblRepositCPF.getText().toString();
            list = (ArrayList<FoneDTO>) foneDAO.listarTodosFone(celular, cpf);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[5];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdFoneAutoIncrementoDto();
                rowData[1] = list.get(i).getFoneDto();
                rowData[2] = btnLancaEdicao;
                rowData[3] = btnLancaExclusao;
                rowData[4] = btnPreferencial;

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(90);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

//    public void buscandoDadsoAutomaticos() {
//
//        if (estaFechado(formUsuario)) {
//            //informação 
//            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
//                    + "Cadastro Fone Será Fechado\n"
//                    + "para realizamos o cadastro \n"
//                    + "é necessário que o cadastro\n"
//                    + "de usuário esteja aberto por\n"
//                    + "o sistema não detectou esse  \n"
//                    + "Cadastro Aberto."
//                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
//
//            this.dispose();
//        } else {
//
//            lblRepositPerfil.setText((String) cbPerfilUsuarios.getSelectedItem());
//            lblRepositCPF.setText(txtCPF.getText());
//            lblRepositNome.setText(txtUsuario.getText());
//            lblRepositIDPaisRegiao.setText("10");
//            lblReposiSMStPaisRegiao.setText("+55");
//            lblRepositPaisRegiao.setText("BRASIL");
//
//        }
//
//    }
    public void validandoForms() {

        if (formUsuario.isOpen()) {
            //https://brunoagt.wordpress.com/2011/03/28/javax-swing-joptionpane-conhecendo-e-utilizando-a-classe-joptionpane/
            JOptionPane.showMessageDialog(null, "Usuario Aberto \n",
                    "Alerta", JOptionPane.WARNING_MESSAGE);
        } else {

            //https://brunoagt.wordpress.com/2011/03/28/javax-swing-joptionpane-conhecendo-e-utilizando-a-classe-joptionpane/
            JOptionPane.showMessageDialog(null, "Usuario FECHADO\n",
                    "Alerta", JOptionPane.WARNING_MESSAGE);

        }
    }

    private void aoCarregarForm() {
        lblRepositPerfil.setText("PACIENTE");
        lblRepositCPF.setText(txtCPFPaciente.getText());
        lblRepositNome.setText(txtNome.getText());
        lblIDFone.setText(txtID.getText());
        lblRepositIDPaisRegiao.setText("10");
        lblReposiSMStPaisRegiao.setText("+55");
        lblRepositPaisRegiao.setText("BRASIL");
        
        

        //acões relativa a componentes do form 
        btnSalvar.setEnabled(false);
        txtCelular.setEnabled(false);
        //barra de nuvem back-ende orientar usuario 
        progressBarraPesquisa.setIndeterminate(true);
    }

    private void personalizacaoFrontEnd() {
        lblCell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cell.png")));
        lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));

        //SOBRE OS LABELS DE CODIGO SMS E PAIS PINTAR 
        lblRepositIDPaisRegiao.setForeground(new Color(0, 102, 102));
        lblReposiSMStPaisRegiao.setForeground(new Color(0, 102, 102));
        lblRepositPaisRegiao.setForeground(new Color(0, 102, 102));

    }

    private void desabilitarCampos() {
        txtBuscar.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {

        btnPesquisar.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        painelDados = new javax.swing.JPanel();
        txtCelular = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        lblCell = new javax.swing.JLabel();
        lblIDFone = new javax.swing.JLabel();
        lblNuvemForms = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        btnAdicionar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        painelDadosCapturados = new javax.swing.JPanel();
        lblRepositPerfil = new javax.swing.JLabel();
        lblPerfilSubTelaPais = new javax.swing.JLabel();
        lblCPF = new javax.swing.JLabel();
        lblRepositCPF = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblRepositNome = new javax.swing.JLabel();
        lblIDPaisRegiao = new javax.swing.JLabel();
        lblRepositIDPaisRegiao = new javax.swing.JLabel();
        lblSMSPaisRegiao = new javax.swing.JLabel();
        lblReposiSMStPaisRegiao = new javax.swing.JLabel();
        lblPaisRegiao = new javax.swing.JLabel();
        lblRepositPaisRegiao = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JFormattedTextField();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setClosable(true);
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
            }
        });

        painelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa.setText("Linha Informativa");
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 240, 20));

        painelDados.setBackground(java.awt.Color.white);
        painelDados.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastrar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelDados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCelular.setBackground(new java.awt.Color(0, 102, 102));
        txtCelular.setForeground(java.awt.Color.white);
        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCelular.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCelular.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCelular.setPreferredSize(new java.awt.Dimension(6, 30));
        txtCelular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCelularFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCelularFocusLost(evt);
            }
        });
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCelularKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });
        painelDados.add(txtCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 150, 30));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Celular:");
        painelDados.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));
        painelDados.add(lblCell, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 30, 30));

        lblIDFone.setForeground(new java.awt.Color(0, 102, 102));
        lblIDFone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIDFone.setText("ID");
        painelDados.add(lblIDFone, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 30, 20));

        painelPrincipal.add(painelDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 280, 65));
        painelPrincipal.add(lblNuvemForms, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 3, 30, 30));
        painelPrincipal.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 50, -1));

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
        painelPrincipal.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, -1));

        btnSalvar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSalvarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnSalvarFocusLost(evt);
            }
        });
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 45, 45));

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
        painelPrincipal.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, -1, -1));

        painelDadosCapturados.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Capturadas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelDadosCapturados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositPerfil.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPerfil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        painelDadosCapturados.add(lblRepositPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 90, 20));

        lblPerfilSubTelaPais.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblPerfilSubTelaPais.setForeground(new java.awt.Color(0, 102, 102));
        lblPerfilSubTelaPais.setText("Perfil:");
        painelDadosCapturados.add(lblPerfilSubTelaPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 60, -1));

        lblCPF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCPF.setForeground(new java.awt.Color(0, 102, 102));
        lblCPF.setText("CPF:");
        painelDadosCapturados.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        lblRepositCPF.setForeground(new java.awt.Color(0, 102, 102));
        painelDadosCapturados.add(lblRepositCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 90, 20));

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNome.setForeground(new java.awt.Color(0, 102, 102));
        lblNome.setText("Nome:");
        painelDadosCapturados.add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        lblRepositNome.setForeground(new java.awt.Color(0, 102, 102));
        painelDadosCapturados.add(lblRepositNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 220, 20));

        lblIDPaisRegiao.setForeground(new java.awt.Color(0, 102, 102));
        lblIDPaisRegiao.setText("ID:");
        painelDadosCapturados.add(lblIDPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 75, -1, -1));

        lblRepositIDPaisRegiao.setBackground(new java.awt.Color(0, 102, 102));
        lblRepositIDPaisRegiao.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositIDPaisRegiao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelDadosCapturados.add(lblRepositIDPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 75, 30, 20));

        lblSMSPaisRegiao.setForeground(new java.awt.Color(0, 102, 102));
        lblSMSPaisRegiao.setText("SMS:");
        painelDadosCapturados.add(lblSMSPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 75, -1, -1));

        lblReposiSMStPaisRegiao.setForeground(new java.awt.Color(0, 102, 102));
        painelDadosCapturados.add(lblReposiSMStPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 75, 40, 20));

        lblPaisRegiao.setForeground(new java.awt.Color(0, 102, 102));
        lblPaisRegiao.setText("País:");
        painelDadosCapturados.add(lblPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 75, -1, -1));
        painelDadosCapturados.add(lblRepositPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 75, 60, 20));

        painelPrincipal.add(painelDadosCapturados, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 280, 120));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa Rápida:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N

        try {
            txtBuscar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtBuscar.setPreferredSize(new java.awt.Dimension(6, 30));
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarKeyTyped(evt);
            }
        });

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
        btnPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPesquisarKeyPressed(evt);
            }
        });

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CELULAR", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(120);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(212, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        int coluna = tabela.getColumnModel().getColumnIndexAtX(evt.getX());
        int linha = evt.getY() / tabela.getRowHeight();

        if (linha < tabela.getRowCount() && linha >= 0 && coluna < tabela.getColumnCount() && coluna >= 0) {
            Object value = tabela.getValueAt(linha, coluna);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;

                /**
                 * Botão Exclusão Evento
                 */
                if (boton.getName().equals("PF")) {

                    lblFonePreferencial.setText(tabela.getValueAt(linha, 1).toString());
                    lblFonePreferencial.setForeground(new Color(0, 102, 102));
                    lblFonePreferencial.setFont(f);
                    this.dispose();
                }

                if (boton.getName().equalsIgnoreCase("Ed")) {

                    int codigo = Integer.parseInt("" + tabela.getValueAt(tabela.getSelectedRow(), 0));

                    try {

                        FoneDTO retorno = foneDAO.buscarPorIdProtege(codigo);
                    //    JOptionPane.showMessageDialog(null, retorno.getPerfilDto());
                        if (retorno.getPerfilDto().equals("PACIENTE")) {

                            lblIDFone.setText(tabela.getValueAt(linha, 0).toString());
                            txtCelular.setText(tabela.getValueAt(linha, 1).toString());
                            acaoBotaoEditar();
                        } else {
                            JOptionPane.showMessageDialog(this, ""
                                    + "Info:Este Registro não pode ser Editado \n "
                                    + "a partir do Cadastro de Celulares/Paciente.\n,"
                                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }

                }

                if (boton.getName().equalsIgnoreCase("Ex")) {

                    String numeroCapturado = tabela.getValueAt(linha, 0).toString();

                    try {

                        /*Evento ao ser clicado executa código*/
                        int excluir = JOptionPane.showConfirmDialog(null, "Deseja Excluir Registro?", "Informação", JOptionPane.YES_NO_OPTION);

                        if (excluir == JOptionPane.YES_OPTION) {

                            foneDTO.setIdFoneAutoIncrementoDto(Integer.parseInt(numeroCapturado));

                            /**
                             * Chamando o método que irá executar a Edição dos
                             * Dados em nosso Banco de Dados
                             */
                            foneDAO.deletar(foneDTO);

                            //ações após exclusão
                            comportamentoAposExclusao();

                        }

                    } catch (Exception e) {

                        e.getMessage();
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                                + "Info:" + e.getMessage()
                                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    }

                }

            }

        }


    }//GEN-LAST:event_tabelaMouseClicked
    private void aoReceberFocoBtnPesquisar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Pressione -->[ENTER]<-- Procurar -->[MYSQL]<--");

    }

    private void aoReceberFocoTxtBuscar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Digite o Registro Procurado -->[ENTER]<--");
    }

    private void personalizandoBarraInfoPesquisaTermino() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.ORANGE);
        lblLinhaInformativa.setText("Pesquisa Terminada.");
        txtBuscar.requestFocus();
        txtBuscar.setBackground(Color.CYAN);
    }

    private void acaoBotaoPesquisar() {

        progressBarraPesquisa.setIndeterminate(true);

        try {

            int numeroLinhas = tabela.getRowCount();

            if (numeroLinhas > 0) {

                while (tabela.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tabela.getModel()).removeRow(0);

                }

                pesquisar();
            } else {
                addRowJTable();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        txtBuscar.requestFocus();
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoBotaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));

        }

    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

        }
    }//GEN-LAST:event_btnPesquisarKeyPressed

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        aoReceberFocoBtnPesquisar();
    }//GEN-LAST:event_btnPesquisarFocusGained

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (txtBuscar.getText().equals("(  )     -    ")) {

            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtBuscar.requestFocus();
                txtBuscar.setBackground(Color.YELLOW);
                txtBuscar.setForeground(Color.BLACK);

                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("Digite o Registro a ser pesquisado [ENTER]");
                lblLinhaInformativa.setForeground(Color.red);
                lblLinhaInformativa.setFont(f);
                try {

                    int numeroLinhas = tabela.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tabela.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tabela.getModel()).removeRow(0);

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

        if (!txtBuscar.getText().equals("(  )     -    ")) {
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

                        int numeroLinhas = tabela.getRowCount();

                        if (numeroLinhas > 0) {

                            while (tabela.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tabela.getModel()).removeRow(0);

                            }

                            pesquisar();
                        } else {
                            addRowJTable();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //coloca o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                } else {

                    //coloca o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                            + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                }
            }
        }    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyTyped

    }//GEN-LAST:event_txtBuscarKeyTyped


    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(new Color(0, 102, 102));
        txtBuscar.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("Pesquise Digitando o Nº Celular");
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));

    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(Color.WHITE);
        txtBuscar.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtBuscarFocusLost

    private void comportamentoAposExclusao() {
        /**
         * Ações de Botões
         */

        btnAdicionar.setEnabled(true);
        /**
         * Após salvar limpar os campos
         */
        txtBuscar.setText("");
        txtCelular.setText("");

        /**
         * Atualiza a tabela
         */
        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            addRowJTable();

        } else {
            addRowJTable();

        }

    }


    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited
    private void acaoBotaoAdicionar() {

        flag = 1;

//        gerarCodigoGUIeDataSistema();
        /**
         * Campos devem ser ativados
         */
        lblIDFone.setText(txtID.getText());
        this.txtCelular.setEnabled(true);

        /**
         * Limpar os campos para cadastrar
         */
        //txtIDUsuario.setVisible(false);
        this.txtCelular.setText("");

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
        this.txtBuscar.setEnabled(false);

        this.txtCelular.requestFocus();//setar o campo nome Bairro após adicionar

    }

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed
    private void acaoBotaoEditar() {

        /**
         * Quando clicar em Editar essa flag recebe o valor de 2
         */
        flag = 2;

        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        this.txtCelular.setEnabled(true);

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        this.btnSalvar.setEnabled(true);
        this.btnCancelar.setEnabled(true);

        this.btnAdicionar.setEnabled(false);

        this.txtCelular.requestFocus();
        this.txtCelular.setBackground(new Color(0, 102, 102));

    }

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

        btnSalvar.setBackground(new Color(0, 102, 102));
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Salvar Registro no Banco");
    }//GEN-LAST:event_btnSalvarFocusGained

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_btnSalvarFocusLost

    private void salvar() {

        //CAPTURANDO DADOS TABELA PAIS REGIAO 
        foneDTO.setIdFoneDto(Integer.parseInt(lblIDFone.getText()));
        foneDTO.setFkIDPaisRegiaoDto(Integer.parseInt(lblRepositIDPaisRegiao.getText()));
        foneDTO.setNomeDto(lblRepositNome.getText());
        foneDTO.setCpfDto(lblRepositCPF.getText());

        foneDTO.setCodigoSMSDto(lblReposiSMStPaisRegiao.getText());
        foneDTO.setPaisDto(lblRepositPaisRegiao.getText());
        foneDTO.setFoneDto(txtCelular.getText());
        foneDTO.setPerfilDto(lblRepositPerfil.getText());

        try {
            /**
             * Depois de capturados e atribuídos seus respectivos valores
             * capturados nas variáveis acimas descrita. Iremos criar um objeto
             * do tipo UsuarioBO
             */
            foneBO = new FoneBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((foneBO.validaCustomizado(foneDTO)) == false) {
                txtCelular.setText("");

            } else {

                if ((flag == 1)) {

                    foneBO.cadastrar(foneDTO);
                    /**
                     * Após salvar limpar os campos
                     */
                    txtCelular.setText("");

                    /**
                     * Bloquear campos e Botões
                     */
                    txtCelular.setEnabled(false);
                    txtBuscar.setEnabled(true);
                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnSalvar.setEnabled(false);
                    btnAdicionar.setEnabled(true);

                    lblLinhaInformativa.setText("Resgistro Cadastrado com Sucesso");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));

                    //desabilitar o botão cancelar
                    btnCancelar.setEnabled(false);
                    
                    JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                            + "Registro Salvos com Sucesso."
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                    /**
                     * Zera a Linha informativa criada para esse Sistema
                     */
                    int numeroLinhas = tabela.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tabela.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tabela.getModel()).removeRow(0);

                        }

                        addRowJTable();

                    } else {
                        addRowJTable();

                    }

                    
                } else {

                    /**
                     * Caso não seja um novo registro equivale dizer que é uma
                     * edição então executará esse código flag será !=1
                     */
                    foneDTO.setIdFoneAutoIncrementoDto(Integer.parseInt(lblIDFone.getText()));

                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    foneBO.atualizarBO(foneDTO);

                    /**
                     * Após salvar limpar os campos
                     */
                    txtCelular.setText("");

                    /**
                     * Bloquear campos e Botões
                     */
                    txtCelular.setEnabled(false);

                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);
                    JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                            + "Edição Salva com Sucesso."
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    int numeroLinhas = tabela.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tabela.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tabela.getModel()).removeRow(0);

                        }

                        addRowJTable();

                    } else {
                        addRowJTable();

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            if (e.getMessage().equals("Campo Fone Obrigatorio")) {
                txtCelular.setBackground((Color.YELLOW));
                txtCelular.setForeground(Color.BLACK);
                txtCelular.requestFocus();
            }

        }

    }


    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            salvar();
        } else {
            erroViaEmail("Erro na Camada GUI:btnSalvar\n", "Evento:btnSalvarActionPerformed()");
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited
    private void acaoBtnCancelar() {

        /**
         * Após salvar limpar os campos
         */
        /**
         * Após salvar limpar os campos
         */
        txtBuscar.setText("");
        txtCelular.setText("");

        txtBuscar.setEnabled(true);
        txtCelular.setEnabled(false);
        txtCPFPaciente.setEnabled(false);

        /**
         * BOTÕES PADROES DE QUALQUER FORM
         */
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);

        //BOTOES ADICIONAID 
        JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                + "Dados Cancelados com Sucesso."
                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBtnCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCelularFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCelularFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularFocusGained

    private void txtCelularFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCelularFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularFocusLost

    private void txtCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularKeyPressed

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularKeyTyped

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instance = null;
    }//GEN-LAST:event_formInternalFrameClosed
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
            email.setSubject("Metodo:" + metodo);
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Mensagem:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            erroViaEmail(mensagemErro, metodo);

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            e.printStackTrace();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAdicionar;
    public static javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCell;
    private javax.swing.JLabel lblIDFone;
    private javax.swing.JLabel lblIDPaisRegiao;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JLabel lblPaisRegiao;
    private javax.swing.JLabel lblPerfilSubTelaPais;
    public static javax.swing.JLabel lblReposiSMStPaisRegiao;
    public static javax.swing.JLabel lblRepositCPF;
    public static javax.swing.JLabel lblRepositIDPaisRegiao;
    public static javax.swing.JLabel lblRepositNome;
    public static javax.swing.JLabel lblRepositPaisRegiao;
    public static javax.swing.JLabel lblRepositPerfil;
    private javax.swing.JLabel lblSMSPaisRegiao;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelDadosCapturados;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JFormattedTextField txtBuscar;
    private javax.swing.JFormattedTextField txtCelular;
    // End of variables declaration//GEN-END:variables
}
