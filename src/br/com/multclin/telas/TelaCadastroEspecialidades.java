package br.com.multclin.telas;

import br.com.multclin.bo.CadEspecialidadeBO;
import br.com.multclin.bo.EspecialidadeBO;
import br.com.multclin.dao.CadEspecialidadeDAO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.GeraCodigoAutomaticoDAO;
import br.com.multclin.dto.CadEspecialidadeDTO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;

import static br.com.multclin.telas.TelaPrincipal.DeskTop;

//objetos vindo de outros formulários 
//*******************
//TELA PRINCIPAL 
//******************
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import static br.com.multclin.telas.TelaProcedimento.lblIDProcedimento;
import static br.com.multclin.telas.TelaProcedimento.txtEspecialidadesProcede;
import static br.com.multclin.telas.TelaProcedimento.txtProcedimento;

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
public class TelaCadastroEspecialidades extends javax.swing.JInternalFrame {

    CadEspecialidadeDTO cadEspecialidadeDTO = new CadEspecialidadeDTO();
    CadEspecialidadeDAO cadEspecialidadeDAO = new CadEspecialidadeDAO();
    CadEspecialidadeBO cadEspecialidadeBO = new CadEspecialidadeBO();
  
    GeraCodigoAutomaticoDAO gera = new GeraCodigoAutomaticoDAO();
    br.com.multclin.telas.TelaProcedimento formProcede;
    Font f = new Font("Tahoma", Font.BOLD, 9);

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
    private static TelaCadastroEspecialidades instance = null;

    public static TelaCadastroEspecialidades getInstance() {

        if (instance == null) {

            instance = new TelaCadastroEspecialidades();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public TelaCadastroEspecialidades() {
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
            tabela.setRowHeight(34);

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<CadEspecialidadeDTO> list;

        try {

            JButton btnLancaEdicao = new JButton("Editar");
            JButton btnLancaExclusao = new JButton("Delete");
            JButton btnProcede = new JButton("Procede");

            btnLancaEdicao.setName("Ed");
            btnLancaExclusao.setName("Ex");
            btnProcede.setName("LP");

            list = (ArrayList<CadEspecialidadeDTO>) cadEspecialidadeDAO.listarTodos();

            Object rowData[] = new Object[5];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = btnLancaEdicao;
                rowData[3] = btnLancaExclusao;
                rowData[4] = btnProcede;

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
            tabela.getColumnModel().getColumn(4).setPreferredWidth(130);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    public void pesquisar() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<CadEspecialidadeDTO> list;

        try {

            JButton btnLancaEdicao = new JButton("Editar");
            JButton btnLancaExclusao = new JButton("Delete");
            JButton btnProcede = new JButton("Procede");

            btnLancaEdicao.setName("Ed");
            btnLancaExclusao.setName("Ex");
            btnProcede.setName("LP");

            String nome = txtPesquisa.getText();
            list = (ArrayList<CadEspecialidadeDTO>) cadEspecialidadeDAO.filtrarPesqRapida(nome);

            Object rowData[] = new Object[5];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = btnLancaEdicao;
                rowData[3] = btnLancaExclusao;
                rowData[4] = btnProcede;

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
            tabela.getColumnModel().getColumn(4).setPreferredWidth(130);

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void aoCarregarForm() {

        txtEspecialidades.setEnabled(false);
      //  lblLancaProcedimentos.setEnabled(false);

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
        lblID = new javax.swing.JLabel();
        lblEspecialidades = new javax.swing.JLabel();
        txtEspecialidades = new javax.swing.JTextField();
        lblNuvemForms = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        btnAdicionar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        lblPesNome = new javax.swing.JLabel();

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
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 420, 20));

        painelDados.setBackground(java.awt.Color.white);
        painelDados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelDados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(0, 102, 102));
        lblID.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblID.setText("ID");
        painelDados.add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 40, 20));

        lblEspecialidades.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEspecialidades.setForeground(new java.awt.Color(0, 102, 102));
        lblEspecialidades.setText("Especialidade:");
        painelDados.add(lblEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 15, -1, 20));

        txtEspecialidades.setBackground(new java.awt.Color(0, 102, 102));
        txtEspecialidades.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtEspecialidades.setForeground(java.awt.Color.white);
        txtEspecialidades.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEspecialidadesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEspecialidadesFocusLost(evt);
            }
        });
        txtEspecialidades.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEspecialidadesKeyPressed(evt);
            }
        });
        painelDados.add(txtEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 240, 30));

        painelPrincipal.add(painelDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 400, 50));
        painelPrincipal.add(lblNuvemForms, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 30, 30));
        painelPrincipal.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 60, -1));

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

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "ESPECIALIDADE", "", "", ""
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
            tabela.getColumnModel().getColumn(1).setPreferredWidth(170);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(130);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelPrincipal.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 400, 290));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa Rápida:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(302, 65));
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
        jPanel1.add(txtPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 310, 35));

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
        jPanel1.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 32, 32));

        lblPesNome.setBackground(new java.awt.Color(0, 102, 102));
        lblPesNome.setForeground(new java.awt.Color(0, 102, 102));
        lblPesNome.setText("Nome:");
        jPanel1.add(lblPesNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        painelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 400, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 420, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
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
                    int codigo = Integer.parseInt("" + tabela.getValueAt(tabela.getSelectedRow(), 0));

                    try {

                        CadEspecialidadeDTO retorno = cadEspecialidadeDAO.buscarPorIdTblConsultaList(codigo);

                        lblID.setText(tabela.getValueAt(linha, 0).toString());
                        txtEspecialidades.setText(tabela.getValueAt(linha, 1).toString());
                        txtEspecialidades.setEnabled(true);
                        this.btnSalvar.setEnabled(true);
                        this.btnCancelar.setEnabled(true);
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

                            cadEspecialidadeDTO.setIdDto(Integer.parseInt(numeroCapturado));
                            cadEspecialidadeDAO.deletar(cadEspecialidadeDTO);
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
                if (boton.getName().equalsIgnoreCase("LP")) {
                    TelaProcedimento tProcede = new TelaProcedimento();
                    tProcede.MostrarTela(tProcede);
                    txtEspecialidadesProcede.setText(tabela.getValueAt(linha, 1).toString());
                    lblIDProcedimento.setText(tabela.getValueAt(linha, 0).toString());
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

        btnAdicionar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnSalvar.setEnabled(false);
        txtEspecialidades.setText("");
        lblID.setText("");
        txtEspecialidades.setEnabled(false);

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
        lblID.setText(String.valueOf(resultado));

    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        gerarCodigoGUI();
        txtEspecialidades.setEnabled(true);
        this.btnAdicionar.setEnabled(false);
        this.btnSalvar.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        this.txtEspecialidades.requestFocus();

    }

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

        if (txtEspecialidades.getText().isEmpty()) {
            txtEspecialidades.requestFocus();
            txtEspecialidades.setBackground(Color.YELLOW);
            txtEspecialidades.setForeground(Color.BLACK);
            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Campo Obrigatório."
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

    private void liberarCampo() {
        txtEspecialidades.setEnabled(true);

    }

    private void salvar() {

        cadEspecialidadeDTO.setIdDto(Integer.parseInt(lblID.getText()));
        cadEspecialidadeDTO.setNomeDto(txtEspecialidades.getText());

        try {
            cadEspecialidadeBO = new CadEspecialidadeBO();

            if ((cadEspecialidadeBO.validaNome(cadEspecialidadeDTO)) == false) {

            } else {

                if ((flag == 1)) {

                    cadEspecialidadeBO.cadastrar(cadEspecialidadeDTO);

                    btnSalvar.setEnabled(false);
                    btnAdicionar.setEnabled(true);

                    lblLinhaInformativa.setText("Resgistro Cadastrado com Sucesso");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));
                    btnCancelar.setEnabled(false);
                    txtEspecialidades.setText("");
                    txtEspecialidades.setEnabled(false);
                    lblID.setText("");
                    btnAdicionar.requestFocus();
                    // liberarCampo();

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

                    cadEspecialidadeDTO.setIdDto(Integer.parseInt(lblID.getText()));
                    cadEspecialidadeBO.atualizarBO(cadEspecialidadeDTO);
                    lblID.setText("");
                    txtEspecialidades.setText("");
                    txtEspecialidades.setEnabled(false);
                    btnAdicionar.setEnabled(true);
                    btnAdicionar.requestFocus();
                    btnCancelar.setEnabled(false);
                    btnSalvar.setEnabled(false);

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
                txtEspecialidades.setBackground((Color.YELLOW));
                txtEspecialidades.setForeground(Color.BLACK);
                txtEspecialidades.requestFocus();
            }

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

        txtEspecialidades.setText("");
        lblID.setText("");
        txtEspecialidades.setEnabled(false);

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

    private void txtEspecialidadesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEspecialidadesKeyPressed

        if (!txtEspecialidades.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                txtEspecialidades.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtEspecialidades.getText().trim()));

                btnSalvar.requestFocus();
                btnSalvar.setBackground(Color.YELLOW);

            }
        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo é Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                txtEspecialidades.requestFocus();
                txtEspecialidades.setBackground(Color.YELLOW);
                txtEspecialidades.setForeground(Color.BLACK);
            }

        }


    }//GEN-LAST:event_txtEspecialidadesKeyPressed

    private void txtEspecialidadesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEspecialidadesFocusGained

        txtEspecialidades.setBackground(new Color(0, 102, 102));
        txtEspecialidades.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe a Especialidade");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtEspecialidadesFocusGained

    private void txtEspecialidadesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEspecialidadesFocusLost
        txtEspecialidades.setBackground(Color.WHITE);
        txtEspecialidades.setForeground(Color.BLACK);


    }//GEN-LAST:event_txtEspecialidadesFocusLost
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEspecialidades;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JLabel lblPesNome;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtEspecialidades;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
