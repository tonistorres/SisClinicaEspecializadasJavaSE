package br.com.multclin.telas;

import br.com.multclin.bo.ProcedimentoBO;
import br.com.multclin.dao.ProcedimentoDAO;
import br.com.multclin.dao.GeraCodigoAutomaticoDAO;
import br.com.multclin.dto.ProcedimentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
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

import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TelaProcedimento extends javax.swing.JInternalFrame {

    //TRABALHANDO A THREAD QUE IRÁ DA O EFEITO 
    //DE PISCAR O CAMPO QUANDO RECEBER FOCO
    static int milissegundos = 0;
    static int segundos = 0;
    static int minutos = 0;
    static int horas = 0;
    //estado para controle de movimentos paineis txtLogin e txtSenha
    //bem essas flags já entram setada em true porque ja iniciam suas
    //tarefas no momento que o formulario é aberto 
    static boolean estado = true;

    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());
    ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();
    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();
    ProcedimentoBO procedimentoBO = new ProcedimentoBO();
    GeraCodigoAutomaticoDAO gera = new GeraCodigoAutomaticoDAO();

    Font f = new Font("Tahoma", Font.BOLD, 9);

    int flag = 0;
    int flagDeControAddRow = 0;

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
    private static TelaProcedimento instance = null;

    public static TelaProcedimento getInstance() {

        if (instance == null) {

            instance = new TelaProcedimento();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public TelaProcedimento() {
        initComponents();
        aoCarregarForm();
        personalizacaoFrontEnd();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela         
        tabela.getTableHeader().setDefaultRenderer(new br.com.multclin.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        tabela.setDefaultRenderer(Object.class, new Render());
        //********************************************************************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
       tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 9));
//        tabela.getTableHeader().setOpaque(false);
//        tabela.getTableHeader().setBackground(new Color(165,42,42));
//        tabela.getTableHeader().setForeground(Color.white);
        tabela.setRowHeight(25);

        //*************************************************************************
        //addRowJTable();
        btnAdicionar.requestFocus();
    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ProcedimentoDTO> list;

        try {

            JButton btnLancaEdicao = new JButton("Editar");
            JButton btnLancaExclusao = new JButton("Delete");

            btnLancaEdicao.setName("Ed");
            btnLancaExclusao.setName("Ex");
            int id = Integer.parseInt(lblIDProcedimento.getText());

            list = (ArrayList<ProcedimentoDTO>) procedimentoDAO.filtrarParametroID(id);

            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdAutoDto().toString();
                rowData[1] = list.get(i).getNomeDto().toString();
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

        ArrayList<ProcedimentoDTO> list;

        try {

            JButton btnLancaEdicao = new JButton("Editar");
            JButton btnLancaExclusao = new JButton("Delete");

            btnLancaEdicao.setName("Ed");
            btnLancaExclusao.setName("Ex");

            String nome = txtPesquisa.getText();
            list = (ArrayList<ProcedimentoDTO>) procedimentoDAO.filtrarPassandoUmParameter(nome);

            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdAutoDto().toString();
                rowData[1] = list.get(i).getNomeDto().toString();
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
        desabilitaCampo();
        btnCalcula.setEnabled(false);
        btnSalvar.setEnabled(false);

        String rsBruto = moeda.format(0.00);
        String rsClinica = moeda.format(0.00);
        String rsTerceiro = moeda.format(0.00);
        String rsDesconto = moeda.format(0.00);

        txtFormatedBruto.setText(rsBruto.replace("R$", "").trim());
        txtFormatedClinica.setText(rsClinica.replace("R$", "").trim());
        txtFormatedMedico.setText(rsTerceiro.replace("R$", "").trim());

        progressBarraPesquisa.setIndeterminate(true);
    }

    private void personalizacaoFrontEnd() {
        lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        painelDados = new javax.swing.JPanel();
        lblPesNome = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        lblNuvemForms = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        btnAdicionar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        txtFormatedBruto = new javax.swing.JFormattedTextField();
        txtFormatedClinica = new javax.swing.JFormattedTextField();
        txtFormatedMedico = new javax.swing.JFormattedTextField();
        txtProcedimento = new javax.swing.JTextField();
        btnCalcula = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblBruto = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblIDProcedimento = new javax.swing.JLabel();
        lblEspecialidades = new javax.swing.JLabel();
        txtEspecialidadesProcede = new javax.swing.JLabel();
        lblAuxiliar = new javax.swing.JLabel();

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
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 340, 20));

        painelDados.setBackground(java.awt.Color.white);
        painelDados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelDados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPesNome.setBackground(new java.awt.Color(0, 102, 102));
        lblPesNome.setForeground(new java.awt.Color(0, 102, 102));
        lblPesNome.setText("Nome:");
        painelDados.add(lblPesNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

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
        painelDados.add(txtPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 15, 230, 35));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
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
        painelDados.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 15, 32, 32));

        painelPrincipal.add(painelDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 300, 60));
        painelPrincipal.add(lblNuvemForms, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, 30, 30));
        painelPrincipal.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 60, -1));

        btnAdicionar.setToolTipText("Adicionar Registro");
        btnAdicionar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnAdicionar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAdicionarFocusGained(evt);
            }
        });
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

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        tabela.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tabela.setForeground(new java.awt.Color(0, 102, 102));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PROCEDIMENTO", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
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
            tabela.getColumnModel().getColumn(1).setPreferredWidth(170);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );

        painelPrincipal.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 330, 150));

        txtFormatedBruto.setForeground(new java.awt.Color(0, 102, 102));
        txtFormatedBruto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtFormatedBruto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtFormatedBruto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFormatedBrutoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFormatedBrutoFocusLost(evt);
            }
        });
        txtFormatedBruto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFormatedBrutoKeyPressed(evt);
            }
        });
        painelPrincipal.add(txtFormatedBruto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 78, 30));

        txtFormatedClinica.setForeground(new java.awt.Color(0, 102, 102));
        txtFormatedClinica.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtFormatedClinica.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtFormatedClinica.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFormatedClinicaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFormatedClinicaFocusLost(evt);
            }
        });
        txtFormatedClinica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFormatedClinicaKeyPressed(evt);
            }
        });
        painelPrincipal.add(txtFormatedClinica, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 230, 78, 30));

        txtFormatedMedico.setForeground(new java.awt.Color(0, 102, 102));
        txtFormatedMedico.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtFormatedMedico.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtFormatedMedico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFormatedMedicoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFormatedMedicoFocusLost(evt);
            }
        });
        painelPrincipal.add(txtFormatedMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 78, 30));

        txtProcedimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProcedimentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProcedimentoFocusLost(evt);
            }
        });
        txtProcedimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProcedimentoKeyPressed(evt);
            }
        });
        painelPrincipal.add(txtProcedimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 175, 210, 30));

        btnCalcula.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/calculator.png"))); // NOI18N
        btnCalcula.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnCalculaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnCalculaFocusLost(evt);
            }
        });
        btnCalcula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculaActionPerformed(evt);
            }
        });
        painelPrincipal.add(btnCalcula, new org.netbeans.lib.awtextra.AbsoluteConstraints(283, 225, 38, 38));

        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Procedimento:");
        painelPrincipal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 175, 90, -1));

        lblBruto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBruto.setForeground(new java.awt.Color(0, 102, 102));
        lblBruto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBruto.setText("R$ Bruto");
        painelPrincipal.add(lblBruto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 78, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("R$ Clinica");
        painelPrincipal.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 70, 20));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("R$ Terc.");
        painelPrincipal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 80, 20));

        lblIDProcedimento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIDProcedimento.setForeground(new java.awt.Color(0, 102, 102));
        lblIDProcedimento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblIDProcedimento.setText("ID");
        painelPrincipal.add(lblIDProcedimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 30, 30));

        lblEspecialidades.setForeground(new java.awt.Color(0, 102, 102));
        lblEspecialidades.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEspecialidades.setText("Especialidade:");
        painelPrincipal.add(lblEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(48, 144, 100, 20));

        txtEspecialidadesProcede.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtEspecialidadesProcede.setForeground(new java.awt.Color(0, 102, 102));
        painelPrincipal.add(txtEspecialidadesProcede, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 170, 30));

        lblAuxiliar.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblAuxiliar.setForeground(new java.awt.Color(0, 102, 102));
        painelPrincipal.add(lblAuxiliar, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 56, 40, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void habilitarCampos() {
        txtFormatedBruto.setEnabled(true);
        txtFormatedClinica.setEnabled(true);
        txtFormatedClinica.setEnabled(true);
        txtProcedimento.setEnabled(true);
    }


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
                    int codigo = Integer.parseInt("" + tabela.getValueAt(tabela.getSelectedRow(), 0));
                    //label auxiliar para usar na edição de registro nova tecnica como se fosse uma flag
                    lblAuxiliar.setText(String.valueOf(codigo));

                    try {

                        ProcedimentoDTO retorno = procedimentoDAO.buscarPorIdTblConsultaList(codigo);

                        txtProcedimento.setText(tabela.getValueAt(linha, 1).toString());
                        //txtEspecialidadesProcede.setText(tabela.getValueAt(linha, 1).toString());
                        //***********************************************************************************  
                        //Contribuição Forum:
                        //https://www.guj.com.br/t/tratando-double-no-statment-trocar-virgula-por-ponto/89037/2
                        //Canal Loiane Gronner:https://www.youtube.com/watch?v=f8RWk9N49s0
                        //*****************************************************************************************

                        String rsBrutoConvetido = moeda.format(retorno.getRsBrutoDto());
                        String rsClinicaConvetido = moeda.format(retorno.getRsClinicaDto());
                        String rsTerceiroConvetido = moeda.format(retorno.getRsMedicoDto());

                        txtFormatedBruto.setText(rsBrutoConvetido.replace("R$", "").trim());
                        txtFormatedClinica.setText(rsClinicaConvetido.replace("R$", "").trim());
                        txtFormatedMedico.setText(rsTerceiroConvetido.replace("R$", "").trim());

                        habilitarCampos();

                        this.btnSalvar.setEnabled(true);
                        this.btnCancelar.setEnabled(true);
                        this.btnCalcula.setEnabled(true);
                        this.btnAdicionar.setEnabled(false);

                    } catch (Exception e) {
                        e.printStackTrace();
                        e.getMessage();
                    }

                }

                if (boton.getName().equalsIgnoreCase("Ex")) {

                    String numeroCapturado = tabela.getValueAt(linha, 0).toString();

                    try {
                        int excluir = JOptionPane.showConfirmDialog(null, "Deseja Excluir Registro?\n " + numeroCapturado, "Informação", JOptionPane.YES_NO_OPTION);

                        if (excluir == JOptionPane.YES_OPTION) {

                            procedimentoDTO.setIdDto(Integer.parseInt(numeroCapturado));
                            procedimentoDAO.deletar(procedimentoDTO);
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

        limparCampos();
        desabilitaCampo();
        lblAuxiliar.setText("");
        btnAdicionar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnSalvar.setEnabled(false);
        txtEspecialidadesProcede.setEnabled(false);

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

    private void gerarCodigoGUI() {

        /**
         * Setar uma Data pega no sistema e setar num JDateChooser
         * https://www.youtube.com/watch?v=GlBMniOwCnI
         */
        int numeroMax = gera.gerarCodigoEspecialidade();
        int resultado = 0;
        //  JOptionPane.showMessageDialog(null, "Numero"+ numeroMax);
        resultado = numeroMax + 1;
        lblIDProcedimento.setText(String.valueOf(resultado));

    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        String rsBruto = moeda.format(0.00);
        String rsClinica = moeda.format(0.00);
        String rsTerceiro = moeda.format(0.00);

        txtFormatedBruto.setText(rsBruto.replace("R$", "").trim());
        txtFormatedClinica.setText(rsClinica.replace("R$", "").trim());
        txtFormatedMedico.setText(rsTerceiro.replace("R$", "").trim());

        this.txtProcedimento.setEnabled(true);
        this.txtFormatedBruto.setEnabled(true);
        this.txtFormatedClinica.setEnabled(true);
        this.btnCalcula.setEnabled(true);
        this.btnAdicionar.setEnabled(false);
        this.btnSalvar.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        this.txtProcedimento.requestFocus();
    }

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained


    }//GEN-LAST:event_btnSalvarFocusGained

    private void btnSalvarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusLost
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_btnSalvarFocusLost

    private void limparCampos() {

        txtProcedimento.setText("");

        String rsBruto = moeda.format(0.00);
        String rsClinica = moeda.format(0.00);
        String rsTerceiro = moeda.format(0.00);

        txtFormatedBruto.setText(rsBruto.replace("R$", "").trim());
        txtFormatedClinica.setText(rsClinica.replace("R$", "").trim());
        txtFormatedMedico.setText(rsTerceiro.replace("R$", "").trim());
    }

    private void desabilitaCampo() {
        txtProcedimento.setEnabled(false);
        txtFormatedClinica.setEnabled(false);
        txtFormatedMedico.setEnabled(false);
        txtFormatedBruto.setEnabled(false);
    }

    private void salvar() {

        try {

            procedimentoDTO.setIdDto(Integer.parseInt(lblIDProcedimento.getText()));
            procedimentoDTO.setNomeDto(txtProcedimento.getText());

            //*****************************************************************
            //FÓRUM que deu a dica final para salvar no banco de dados MySQL 
            //no formato americado numeros do tipo Float, Doule, em um campo 
            //Decimal(10,2) no mysql
            //*****************************************************************
            //https://pt.stackoverflow.com/questions/37947/valor-decimal-mysql
            //*******************************************************************
            double rsBruto = MetodoStaticosUtil.converteDoubleUS(txtFormatedBruto.getText().replace(".", "").replace(",", ".").replace("R$", "").trim());
            double rsClinica = MetodoStaticosUtil.converteDoubleUS(txtFormatedClinica.getText().replace(".", "").replace(",", ".").replace("R$", "").trim());
            double rsTerceiro = MetodoStaticosUtil.converteDoubleUS(txtFormatedMedico.getText().replace(".", "").replace(",", ".").replace("R$", "").trim());

            procedimentoDTO.setRsBrutoDto(rsBruto);
            procedimentoDTO.setRsClinicaDto(rsClinica);
            procedimentoDTO.setRsMedicoDto(rsTerceiro);

            procedimentoBO = new ProcedimentoBO();

            if ((procedimentoBO.validaNome(procedimentoDTO)) == false) {

            } else {

                if ((flag == 1)) {

                    //label auxiliar usa para edição de registro 
                    lblAuxiliar.setText("");

                    procedimentoBO.cadastrar(procedimentoDTO);

                    btnSalvar.setEnabled(false);
                    btnAdicionar.setEnabled(true);

                    lblLinhaInformativa.setText("Resgistro Cadastrado com Sucesso");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));
                    btnCancelar.setEnabled(false);
                    txtEspecialidadesProcede.setEnabled(false);
                    btnAdicionar.requestFocus();
                    limparCampos();
                    desabilitaCampo();

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

                    // JOptionPane.showMessageDialog(null, "codigo:"+lblAuxiliar.getText());
                    procedimentoDTO.setIdAutoDto(Integer.parseInt(lblAuxiliar.getText()));

                    procedimentoBO.atualizarBO(procedimentoDTO);
                    txtEspecialidadesProcede.setEnabled(false);
                    btnAdicionar.setEnabled(true);
                    btnAdicionar.requestFocus();
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);

                    limparCampos();

                    desabilitaCampo();
                    lblAuxiliar.setText("");

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

            if (e.getMessage().equals("Campo ESPECIALIDADE Obrigatorio")) {
                txtEspecialidadesProcede.setBackground((Color.YELLOW));
                txtEspecialidadesProcede.setForeground(Color.BLACK);
                txtEspecialidadesProcede.requestFocus();
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

        txtEspecialidadesProcede.setEnabled(false);
        limparCampos();

        String rsBruto = moeda.format(0.00);
        String rsClinica = moeda.format(0.00);
        String rsTerceiro = moeda.format(0.00);

        txtFormatedBruto.setText(rsBruto);
        txtFormatedClinica.setText(rsClinica);
        txtFormatedMedico.setText(rsTerceiro);

        desabilitaCampo();
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);

//        //BOTOES ADICIONAID 
//        JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
//                + "Dados Cancelados com Sucesso."
//                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBtnCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instance = null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing

    }//GEN-LAST:event_formInternalFrameClosing

    private void txtPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusGained
        txtPesquisa.setBackground(new Color(0, 102, 102));
        txtPesquisa.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtPesquisaFocusGained

    private void txtPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusLost
        txtPesquisa.setBackground(Color.WHITE);
        txtPesquisa.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtPesquisaFocusLost

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
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        btnPesquisar.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

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

    private void txtProcedimentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProcedimentoFocusGained

        txtProcedimento.setBackground(new Color(0, 102, 102));
        txtProcedimento.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe o Procedimento");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtProcedimentoFocusGained

    private void txtProcedimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProcedimentoFocusLost
        txtProcedimento.setBackground(Color.WHITE);
        txtProcedimento.setForeground(Color.BLACK);

    }//GEN-LAST:event_txtProcedimentoFocusLost

    private void txtFormatedBrutoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedBrutoFocusGained

        if (txtFormatedBruto.getText().equals("0,00")) {
            txtFormatedBruto.setText("");
            txtFormatedBruto.setBackground(new Color(0, 102, 102));
            txtFormatedBruto.setForeground(Color.WHITE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("R$ BRUTO");
            lblLinhaInformativa.setFont(f);
        }


    }//GEN-LAST:event_txtFormatedBrutoFocusGained

    private void txtFormatedBrutoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedBrutoFocusLost

        txtFormatedBruto.setBackground(Color.WHITE);
        txtFormatedBruto.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");

    }//GEN-LAST:event_txtFormatedBrutoFocusLost

    private void txtFormatedClinicaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedClinicaFocusGained

        if (txtFormatedClinica.getText().equals("0,00")) {

            txtFormatedClinica.setText("");
            txtFormatedClinica.setBackground(new Color(0, 102, 102));
            txtFormatedClinica.setForeground(Color.WHITE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("R$ Clínica");
            lblLinhaInformativa.setFont(f);
        }


    }//GEN-LAST:event_txtFormatedClinicaFocusGained

    private void txtFormatedClinicaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedClinicaFocusLost
        txtFormatedClinica.setBackground(Color.WHITE);
        txtFormatedClinica.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_txtFormatedClinicaFocusLost

    private void txtFormatedMedicoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedMedicoFocusGained

        txtFormatedMedico.setBackground(new Color(0, 102, 102));
        txtFormatedMedico.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("R$ Médico");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtFormatedMedicoFocusGained

    private void txtFormatedMedicoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedMedicoFocusLost

        txtFormatedMedico.setBackground(Color.WHITE);
        txtFormatedMedico.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");

    }//GEN-LAST:event_txtFormatedMedicoFocusLost

    private void btnCalculaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCalculaFocusGained
        efeitoAoReceberFoco(btnCalcula);
        calculator();
    }//GEN-LAST:event_btnCalculaFocusGained

    private void btnCalculaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCalculaFocusLost

        btnCalcula.setBackground(Color.WHITE);
        btnCalcula.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_btnCalculaFocusLost

    private void txtProcedimentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcedimentoKeyPressed

        if (!txtProcedimento.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                txtProcedimento.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtProcedimento.getText().trim()));
                txtFormatedBruto.requestFocus();
            }
        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo é Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtProcedimento.requestFocus();

            }

        }


    }//GEN-LAST:event_txtProcedimentoKeyPressed

    private void txtFormatedBrutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedBrutoKeyPressed

        if (!txtFormatedBruto.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                txtFormatedClinica.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtProcedimento.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo é Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtFormatedBruto.requestFocus();

            }

        }


    }//GEN-LAST:event_txtFormatedBrutoKeyPressed

    private void txtFormatedClinicaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedClinicaKeyPressed

        if (!txtFormatedClinica.getText().isEmpty() && !txtFormatedClinica.getText().equals("0,00")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                btnCalcula.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtFormatedBruto.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo é Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtFormatedClinica.requestFocus();

            }

        }


    }//GEN-LAST:event_txtFormatedClinicaKeyPressed

    private void calculator() {
        try {
            if ((!txtFormatedBruto.getText().isEmpty())
                    && (!txtFormatedClinica.getText().isEmpty())) {

                btnCalcula.setBackground(new Color(0, 102, 102));
                btnCalcula.setForeground(Color.WHITE);
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("Efetua Calculo");
                lblLinhaInformativa.setFont(f);

                float rsBruto, rsClinica, rsMedico, resultadoCalculo;

                rsBruto = MetodoStaticosUtil.converteFloat(txtFormatedBruto.getText());
                //   JOptionPane.showMessageDialog(null, "rsBruto:" + rsBruto);
                rsClinica = MetodoStaticosUtil.converteFloat(txtFormatedClinica.getText());
                //   JOptionPane.showMessageDialog(null, "rsClinica:" + rsClinica);

                if (rsBruto < rsClinica) {
                    txtFormatedBruto.requestFocus();
                    JOptionPane.showMessageDialog(this, "" + "\n Info:\n"
                            + "Os valores dos campos:\n"
                            + " * R$Bruto e R$Clinica\n"
                            + "não podem conter valor nulo."
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                } else {
                    try {

                        resultadoCalculo = rsBruto - rsClinica;

                        String rsMedicoString = moeda.format(Float.parseFloat(String.valueOf(resultadoCalculo)));
                        //JOptionPane.showMessageDialog(null, rsMedicoString);
                        txtFormatedMedico.setText(rsMedicoString.replace("R$", "").trim());
                        btnSalvar.setEnabled(true);
                        btnSalvar.requestFocus();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {

                txtProcedimento.requestFocus();
                String rsBruto = moeda.format(0.00);
                String rsDesconto = moeda.format(0.00);
                JOptionPane.showMessageDialog(this, "" + "\n Info:\n"
                        + "Os Valores dos Campos R$Bruto e R$Clínica\n"
                        + "devem ser diferente de nulo e maiores que zero.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

        } catch (ParseException ex) {
            ex.printStackTrace();

            //informação 
            JOptionPane.showMessageDialog(this, "" + "\n Informação Exception Camada GUI:\n"
                    + "Componente btnCalcula \n"
                    + ex.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }

    }


    private void btnCalculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculaActionPerformed


    }//GEN-LAST:event_btnCalculaActionPerformed

    private void btnAdicionarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusGained

        if (flagDeControAddRow == 0) {
            addRowJTable();
            flagDeControAddRow = 1;
            txtPesquisa.requestFocus();
        }


    }//GEN-LAST:event_btnAdicionarFocusGained
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

    private void efeitoAoReceberFoco(JButton nomeBotao) {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(10);

                            if (segundos % 2 == 0) {
                                nomeBotao.setBackground(Color.YELLOW);

                            }

                            if (segundos % 2 != 0) {
                                nomeBotao.setBackground(new Color(0, 102, 102));
                            }

                            if (milissegundos > 1000) {
                                milissegundos = 0;
                                segundos++;

                            }

                            if (segundos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos++;
                            }

                            if (minutos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            milissegundos++;

                        } catch (Exception e) {

                        }

                    } else {
                        break;
                    }
                }

            }

        };
        t.start();

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCalcula;
    public static javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAuxiliar;
    private javax.swing.JLabel lblBruto;
    private javax.swing.JLabel lblEspecialidades;
    public static javax.swing.JLabel lblIDProcedimento;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JLabel lblPesNome;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    public static javax.swing.JLabel txtEspecialidadesProcede;
    private javax.swing.JFormattedTextField txtFormatedBruto;
    private javax.swing.JFormattedTextField txtFormatedClinica;
    private javax.swing.JFormattedTextField txtFormatedMedico;
    private javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextField txtProcedimento;
    // End of variables declaration//GEN-END:variables

    //primeiro iremos fazer a tela procedimento ficar visivel na tela de cadastrod e especialidaes
    //passando a mesma por um construtor 
    private TelaProcedimento telaProcediemento;

    public void MostrarTela(TelaProcedimento tela) {

        if (lblIDProcedimento.getText().equals("ID")) {
            this.telaProcediemento = tela;
            if (estaFechado(tela)) {
                tela = new TelaProcedimento();
                DeskTop.add(tela).setLocation(410, 3);
                tela.setVisible(true);

            }

        }

    }
}
