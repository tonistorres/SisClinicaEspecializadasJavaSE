package br.com.multclin.telas;

import br.com.multclin.bo.EspecialidadeBO;
import br.com.multclin.dao.CadEspecialidadeDAO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.GeraCodigoAutomaticoDAO;
import br.com.multclin.dto.CadEspecialidadeDTO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import static br.com.multclin.telas.TelaAgendamento.lblIdMedico;
import static br.com.multclin.telas.TelaMedico.cbEstaCivil;
import static br.com.multclin.telas.TelaMedico.cbSexo;
import static br.com.multclin.telas.TelaMedico.cbUF;
import static br.com.multclin.telas.TelaMedico.txtBairro;

import static br.com.multclin.telas.TelaPrincipal.DeskTop;

//objetos vindo de outros formulários 
//*******************
//TELA PRINCIPAL 
//******************
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
//*******************
//TELA MEDICO
//******************
import static br.com.multclin.telas.TelaMedico.txtID;
import static br.com.multclin.telas.TelaMedico.txtNome;
import static br.com.multclin.telas.TelaMedico.txtCPF;
import static br.com.multclin.telas.TelaMedico.txtCidade;
import static br.com.multclin.telas.TelaMedico.txtComplemento;
import static br.com.multclin.telas.TelaMedico.txtFormatedCEP;
import static br.com.multclin.telas.TelaMedico.txtFormatedCelular;
import static br.com.multclin.telas.TelaMedico.txtFormatedConselho;
import static br.com.multclin.telas.TelaMedico.txtFormatedFoneFixo;
import static br.com.multclin.telas.TelaMedico.txtGmail;
import static br.com.multclin.telas.TelaMedico.txtNCasa;
import static br.com.multclin.telas.TelaMedico.txtRua;

import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
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
public class SubTelaEspecialidades extends javax.swing.JInternalFrame {

    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeBO especialidadeBO = new EspecialidadeBO();

    CadEspecialidadeDTO cadEspecialidadeDTO = new CadEspecialidadeDTO();
    CadEspecialidadeDAO cadEspecialidadeDAO = new CadEspecialidadeDAO();

    GeraCodigoAutomaticoDAO gera = new GeraCodigoAutomaticoDAO();

    Font f = new Font("Tahoma", Font.BOLD, 9);

    int flag = 0;

    br.com.multclin.telas.FrmListaPaisRegiao formListaPaisRegiao;
    br.com.multclin.telas.TelaUsuario formUsuario;
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
    private static SubTelaEspecialidades instance = null;

    public static SubTelaEspecialidades getInstance() {

        if (instance == null) {

            instance = new SubTelaEspecialidades();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public SubTelaEspecialidades() {
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

    private void criarListaComboEspecialidade() {

        // int codigo = Integer.parseInt(lblIdMedico.getText());
        ArrayList<CadEspecialidadeDTO> list;
        try {

            list = (ArrayList<CadEspecialidadeDTO>) cadEspecialidadeDAO.listarTodos();

            if (list.size() > 0) {
                cbEspecialidades.removeAllItems();

                for (int i = 0; i < list.size(); i++) {

                    cbEspecialidades.addItem(list.get(i).getNomeDto());

                }

            } else {
                for (int i = 0; i < list.size(); i++) {

                    cbEspecialidades.addItem(list.get(i).getNomeDto());

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();

        }

    }

    private void criarListaComboEspecialidadeBotaoAdicionar() {

        // int codigo = Integer.parseInt(lblIdMedico.getText());
        ArrayList<CadEspecialidadeDTO> list;
        try {

            list = (ArrayList<CadEspecialidadeDTO>) cadEspecialidadeDAO.listarTodos();

            if (list.size() > 0) {
                cbEspecialidades.removeAllItems();

                for (int i = 0; i < list.size(); i++) {

                    cbEspecialidades.addItem(list.get(i).getNomeDto());

                }

                //   cbEspecialidades.setSelectedItem(null);
                //lblRepositIDEspecialidade.setText(list.get(i).getIdDto().toString());
            } else {
                System.out.println("Não exite itens na lista de especialidades");

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();

        }

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<EspecialidadeDTO> list;

        try {

            JButton btnLancaEdicao = new JButton("Editar");
            JButton btnLancaExclusao = new JButton("delete");

            btnLancaEdicao.setName("Ed");
            btnLancaExclusao.setName("Ex");

            String cpf = lblRepositCPF.getText().toString().trim();

            list = (ArrayList<EspecialidadeDTO>) especialidadeDAO.listarTodosParametro(cpf);

            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdAutoDto();
                rowData[1] = list.get(i).getEspecialidadeDto();
                rowData[2] = btnLancaEdicao;
                rowData[3] = btnLancaExclusao;

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(170);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    public void pesquisar() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<EspecialidadeDTO> list;

        try {

            JButton btnLancaEdicao = new JButton("Editar");
            JButton btnLancaExclusao = new JButton("delete");

            btnLancaEdicao.setName("Ed");
            btnLancaExclusao.setName("Ex");

//            String celular = txtBuscar.getText().toString();
            String cpf = lblRepositCPF.getText().toString();
            list = (ArrayList<EspecialidadeDTO>) especialidadeDAO.listarTodosParametro(cpf);

            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getEspecialidadeDto();
                rowData[2] = btnLancaEdicao;
                rowData[3] = btnLancaExclusao;

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(170);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void aoCarregarForm() {
        lblRepositPerfil.setText("MEDICO");
        lblRepositCPF.setText(txtCPF.getText());
        lblRepositNome.setText(txtNome.getText());
        lblConselhoRepositorio.setText(txtFormatedConselho.getText());

        cbEspecialidades.setEnabled(false);

        //acões relativa a componentes do form 
        btnSalvar.setEnabled(false);
        //barra de nuvem back-ende orientar usuario 
        progressBarraPesquisa.setIndeterminate(true);
    }

    private void personalizacaoFrontEnd() {
//        lblCell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cell.png")));
        lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        // btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));

        //SOBRE OS LABELS DE CODIGO SMS E PAIS PINTAR 
        lblRepositIDPaisRegiao.setForeground(new Color(0, 102, 102));
        lblReposiSMStPaisRegiao.setForeground(new Color(0, 102, 102));
        lblRepositPaisRegiao.setForeground(new Color(0, 102, 102));

    }

    private void desabilitarCampos() {
//        txtBuscar.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {

//        btnPesquisar.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        painelDados = new javax.swing.JPanel();
        cbEspecialidades = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        lblRepositIDEspecialidade = new javax.swing.JLabel();
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
        lblRepositIDPaisRegiao = new javax.swing.JLabel();
        lblReposiSMStPaisRegiao = new javax.swing.JLabel();
        lblRepositPaisRegiao = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblConselho = new javax.swing.JLabel();
        lblConselhoRepositorio = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setClosable(true);
        setTitle("Especialidade do Profissional");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 20));

        painelDados.setBackground(java.awt.Color.white);
        painelDados.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastrar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelDados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbEspecialidades.setBackground(new java.awt.Color(0, 102, 102));
        cbEspecialidades.setForeground(java.awt.Color.white);
        cbEspecialidades.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cbEspecialidades.setPreferredSize(new java.awt.Dimension(56, 30));
        cbEspecialidades.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEspecialidadesItemStateChanged(evt);
            }
        });
        cbEspecialidades.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbEspecialidadesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbEspecialidadesFocusLost(evt);
            }
        });
        cbEspecialidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEspecialidadesActionPerformed(evt);
            }
        });
        painelDados.add(cbEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 250, -1));

        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Especialidade:");
        painelDados.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 23, -1, -1));
        painelDados.add(lblRepositIDEspecialidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 25));

        painelPrincipal.add(painelDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 310, 90));
        painelPrincipal.add(lblNuvemForms, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 30, 30));
        painelPrincipal.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 50, -1));

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
        painelPrincipal.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

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
        painelPrincipal.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 45, 45));

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
        painelPrincipal.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        painelDadosCapturados.setBackground(java.awt.Color.white);
        painelDadosCapturados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelDadosCapturados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositPerfil.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPerfil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        painelDadosCapturados.add(lblRepositPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 5, 50, 20));

        lblPerfilSubTelaPais.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblPerfilSubTelaPais.setForeground(new java.awt.Color(0, 102, 102));
        lblPerfilSubTelaPais.setText("Perfil:");
        painelDadosCapturados.add(lblPerfilSubTelaPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 5, 60, -1));

        lblCPF.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblCPF.setForeground(new java.awt.Color(0, 102, 102));
        lblCPF.setText("CPF:");
        painelDadosCapturados.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 5, -1, -1));

        lblRepositCPF.setForeground(new java.awt.Color(0, 102, 102));
        painelDadosCapturados.add(lblRepositCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 5, 100, 20));

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNome.setForeground(new java.awt.Color(0, 102, 102));
        lblNome.setText("Nome:");
        painelDadosCapturados.add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        lblRepositNome.setForeground(new java.awt.Color(0, 102, 102));
        painelDadosCapturados.add(lblRepositNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 180, 20));

        lblRepositIDPaisRegiao.setBackground(new java.awt.Color(0, 102, 102));
        lblRepositIDPaisRegiao.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositIDPaisRegiao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelDadosCapturados.add(lblRepositIDPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 75, 30, 20));

        lblReposiSMStPaisRegiao.setForeground(new java.awt.Color(0, 102, 102));
        painelDadosCapturados.add(lblReposiSMStPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 75, 40, 20));
        painelDadosCapturados.add(lblRepositPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 75, 60, 20));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        lblConselho.setForeground(new java.awt.Color(0, 102, 102));
        lblConselho.setText("Conselho:");

        lblConselhoRepositorio.setForeground(new java.awt.Color(0, 102, 102));
        lblConselhoRepositorio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblConselho)
                .addContainerGap())
            .addComponent(lblConselhoRepositorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblConselho)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConselhoRepositorio, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
        );

        painelDadosCapturados.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 70, 60));

        lblID.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        lblID.setForeground(new java.awt.Color(0, 102, 102));
        lblID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblID.setText("ID");
        painelDadosCapturados.add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, 27, 20));

        painelPrincipal.add(painelDadosCapturados, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 330, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ESPECIALIDADE", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
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
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );

        painelPrincipal.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 310, 160));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
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

                if (boton.getName().equalsIgnoreCase("Ed")) {

                    flag = 2;

                    criarListaComboEspecialidade();
                    lblID.setText(txtID.getText());
                    lblRepositIDEspecialidade.setText(tabela.getValueAt(linha, 0).toString());
                    cbEspecialidades.setSelectedItem((String) tabela.getValueAt(linha, 1));

                    cbEspecialidades.setEnabled(true);
                    cbEspecialidades.requestFocus();

                    btnAdicionar.setEnabled(false);
                    btnSalvar.setEnabled(true);
                    btnCancelar.setEnabled(true);
                }

                if (boton.getName().equalsIgnoreCase("Ex")) {

                    String numeroCapturado = tabela.getValueAt(linha, 0).toString();

                    try {

                        int excluir = JOptionPane.showConfirmDialog(null, "Deseja Excluir Registro?\n " + numeroCapturado, "Informação", JOptionPane.YES_NO_OPTION);

                        if (excluir == JOptionPane.YES_OPTION) {

                            especialidadeDTO.setIdAutoDto(Integer.parseInt(numeroCapturado));
                            especialidadeDAO.deletar(especialidadeDTO);
                            comportamentoAposExclusao();

                        }

                    } catch (Exception e) {

                        e.getMessage();
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                                + "Info:" + e.getMessage()
                                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    }

                }//exclusão fim 

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
//        txtBuscar.requestFocus();
//        txtBuscar.setBackground(Color.CYAN);
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

    private void comportamentoAposExclusao() {

        btnAdicionar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnSalvar.setEnabled(false);
        cbEspecialidades.setSelectedItem("Selecione...");

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

        lblID.setText(txtID.getText());
        cbEspecialidades.setEnabled(true);
        this.btnAdicionar.setEnabled(false);
        this.btnSalvar.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        // criarListaComboEspecialidade();
        criarListaComboEspecialidadeBotaoAdicionar();
    }

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

        if (cbEspecialidades.getSelectedItem().equals("Selecione...")) {
            cbEspecialidades.requestFocus();
            cbEspecialidades.setBackground(Color.YELLOW);
            cbEspecialidades.setForeground(Color.BLACK);
            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Selecione uma Especialidade Válida."
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } else {

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
        }


    }//GEN-LAST:event_btnSalvarFocusGained

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_btnSalvarFocusLost

    private void liberarCampoNoFomMedicos() {
        cbSexo.setEnabled(true);
        cbEstaCivil.setEnabled(true);
        cbUF.setEnabled(true);
        txtCidade.setEnabled(true);
        txtBairro.setEnabled(true);
        txtRua.setEnabled(true);
        txtComplemento.setEnabled(true);
        txtNCasa.setEnabled(true);
        txtFormatedCEP.setEnabled(true);
        txtFormatedFoneFixo.setEnabled(true);
        txtFormatedCelular.setEnabled(true);
        txtGmail.setEnabled(true);

    }

    private void salvar() {

        String variavelAuxiliar = lblRepositIDEspecialidade.getText();

        capturarEspecialidadeId();
        //dados capturados 
        especialidadeDTO.setPerfilDto(lblRepositPerfil.getText());//perfil
        especialidadeDTO.setCpfDto(lblRepositCPF.getText());//cpf
        especialidadeDTO.setNomeDto(lblRepositNome.getText());//especialidade
        especialidadeDTO.setConselhoDto(lblConselhoRepositorio.getText());//conselho
        especialidadeDTO.setFk_cadEspecialidadeDto(Integer.parseInt(lblRepositIDEspecialidade.getText()));
        especialidadeDTO.setIdDto(Integer.parseInt(lblID.getText()));

        if (!cbEspecialidades.getSelectedItem().equals("")) {
            especialidadeDTO.setEspecialidadeDto((String) cbEspecialidades.getSelectedItem());
        }

        try {

            if ((flag == 1)) {

                especialidadeBO.cadastrar(especialidadeDTO);

                btnSalvar.setEnabled(false);
                btnAdicionar.setEnabled(true);

                lblLinhaInformativa.setText("Resgistro Cadastrado com Sucesso");
                lblLinhaInformativa.setFont(f);
                lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));
                btnCancelar.setEnabled(false);
                cbEspecialidades.setSelectedItem("Selecione...");
                cbEspecialidades.setEnabled(false);

                lblRepositIDEspecialidade.setText("");
                cbEspecialidades.setSelectedItem(null);

                //liberarCampoNoFomMedicos();
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

                if (!variavelAuxiliar.isEmpty()) {
                    especialidadeDTO.setIdAutoDto(Integer.parseInt(variavelAuxiliar));
                    especialidadeBO.atualizarBO(especialidadeDTO);
                    btnAdicionar.setEnabled(true);
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);

                    //liberarCampoNoFomMedicos();
                    lblRepositIDEspecialidade.setText("");
                    cbEspecialidades.setSelectedItem(null);
                    cbEspecialidades.setEnabled(false);

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

        }

    }


    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited
    private void acaoBtnCancelar() {

        cbEspecialidades.setSelectedItem(null);
        lblID.setText("");
        lblRepositIDEspecialidade.setText("");
        cbEspecialidades.setEnabled(false);

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

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instance = null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void cbEspecialidadesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEspecialidadesFocusGained

        cbEspecialidades.setBackground(new Color(0, 102, 102));
        cbEspecialidades.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Selecione a Especialidade do Médico");
        lblLinhaInformativa.setFont(f);

    }//GEN-LAST:event_cbEspecialidadesFocusGained

    private void cbEspecialidadesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEspecialidadesFocusLost

        cbEspecialidades.setBackground(Color.WHITE);
        cbEspecialidades.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_cbEspecialidadesFocusLost

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing

    }//GEN-LAST:event_formInternalFrameClosing

    private void capturarEspecialidadeId() {

        String itemSelecionado = (String) cbEspecialidades.getSelectedItem();//combo 

        ArrayList<CadEspecialidadeDTO> list;

        try {

            list = (ArrayList<CadEspecialidadeDTO>) cadEspecialidadeDAO.filtrarPesqRapida(itemSelecionado);

            if (list.size() > 0) {
                cbEspecialidades.removeAllItems();
                for (int i = 0; i < list.size(); i++) {
                    lblRepositIDEspecialidade.setText(String.valueOf(list.get(i).getIdDto()));
                    cbEspecialidades.addItem(list.get(i).getNomeDto());
                }

            } else {
                for (int i = 0; i < list.size(); i++) {
                    lblRepositIDEspecialidade.setText(String.valueOf(list.get(i).getIdDto()));
                    cbEspecialidades.addItem(list.get(i).getNomeDto());
                }

            }
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Camada GUI:\n" + ex.getMessage());
        }

    }


    private void cbEspecialidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEspecialidadesActionPerformed

//   capturarEspecialidadeId();
    }//GEN-LAST:event_cbEspecialidadesActionPerformed

    private void cbEspecialidadesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEspecialidadesItemStateChanged

    }//GEN-LAST:event_cbEspecialidadesItemStateChanged
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
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox cbEspecialidades;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblConselho;
    private javax.swing.JLabel lblConselhoRepositorio;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JLabel lblPerfilSubTelaPais;
    public static javax.swing.JLabel lblReposiSMStPaisRegiao;
    public static javax.swing.JLabel lblRepositCPF;
    private javax.swing.JLabel lblRepositIDEspecialidade;
    public static javax.swing.JLabel lblRepositIDPaisRegiao;
    public static javax.swing.JLabel lblRepositNome;
    public static javax.swing.JLabel lblRepositPaisRegiao;
    public static javax.swing.JLabel lblRepositPerfil;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelDadosCapturados;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
