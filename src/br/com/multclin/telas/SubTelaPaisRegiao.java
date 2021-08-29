package br.com.multclin.telas;

import br.com.multclin.bo.PaisBO;
import br.com.multclin.dao.PaisDAO;
import br.com.multclin.dto.PaisDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import static br.com.multclin.telas.TelaUsuario.txtIDUsuario;
import br.com.multclin.util.JtextFieldSomenteLetras;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author DaTorres
 */
public class SubTelaPaisRegiao extends javax.swing.JInternalFrame {
    
    PaisDTO paisDTO = new PaisDTO();
    PaisDAO paisDAO = new PaisDAO();
    PaisBO paisBO = new PaisBO();
    
    int flag;
    
    Font f = new Font("Tahoma", Font.BOLD, 9);
    /**
     * Código Mestre Chimura
     */
    private static SubTelaPaisRegiao instance = null;
    
    public static SubTelaPaisRegiao getInstance() {
        
        if (instance == null) {
            
            instance = new SubTelaPaisRegiao();
            
        }
        
        return instance;
    }
    
    public static boolean isOpen() {
        
        return instance != null;
    }
    
    public SubTelaPaisRegiao() {
        initComponents();
        buscarEstados();
        aoCarregarForm();
        personalizacaoFrontEnd();
        addRowJTable();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.getTableHeader().setDefaultRenderer(new br.com.multclin.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

        tabela.getTableHeader().setDefaultRenderer(new br.com.multclin.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela 

    }
    
    private void aoCarregarForm() {
        
        btnSalvar.setEnabled(false);
        lblRepositPerfil.setText(lblPerfil.getText());

        //campos
        txtCodigoSMS.setEnabled(false);
        txtPais.setEnabled(false);
        btnAdicionar.requestFocus();
        btnAdicionar.setBackground(Color.WHITE);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Click sobre botão em foco (Adicionar)");
        progressBarraPesquisa.setIndeterminate(true);
        
    }
    
    public void addRowJTable() {
        
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        
        ArrayList<PaisDTO> list;
        
        try {
            
            list = (ArrayList<PaisDTO>) paisDAO.listarTodos();

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[3];
            
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdpaisregiaoDto();
                rowData[1] = list.get(i).getCodigoSMSDto();
                rowData[2] = list.get(i).getPaisDto();
                
                model.addRow(rowData);
            }
            
            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
        
    }
    
    private void personalizacaoFrontEnd() {
        lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png")));
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        
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
        lblNuvemForms = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtCodigoSMS = new javax.swing.JTextField();
        txtPais = new JtextFieldSomenteLetras(20);
        lblCodigo = new javax.swing.JLabel();
        lblPaisRegiao = new javax.swing.JLabel();
        lblRepositCodigo = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        lblPerfilSubTelaPais = new javax.swing.JLabel();
        lblRepositPerfil = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

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

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa.setText("Linha Informativa");
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 310, 20));
        painelPrincipal.add(lblNuvemForms, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, 30));

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lançar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCodigoSMS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodigoSMSFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoSMSFocusLost(evt);
            }
        });
        txtCodigoSMS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoSMSKeyPressed(evt);
            }
        });
        jPanel1.add(txtCodigoSMS, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 50, 30));

        txtPais.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPaisFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaisFocusLost(evt);
            }
        });
        txtPais.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaisKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaisKeyTyped(evt);
            }
        });
        jPanel1.add(txtPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 130, 30));

        lblCodigo.setForeground(new java.awt.Color(0, 102, 102));
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigo.setText("Codigo:");
        jPanel1.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 50, -1));

        lblPaisRegiao.setForeground(new java.awt.Color(0, 102, 102));
        lblPaisRegiao.setText("País/Região:");
        jPanel1.add(lblPaisRegiao, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, -1, -1));

        lblRepositCodigo.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositCodigo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblRepositCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 30, 30));

        painelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 120, 280, 90));
        painelPrincipal.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 50, -1));

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
                "ID", "SMS", "PAÍS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
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
            tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(1).setMinWidth(80);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setMaxWidth(80);
            tabela.getColumnModel().getColumn(2).setMinWidth(120);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(2).setMaxWidth(120);
        }

        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        lblPais.setForeground(new java.awt.Color(0, 102, 102));
        lblPais.setText("Digite o País:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPais)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 24, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPais)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelPrincipal.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 220, 280, 200));

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

        lblPerfilSubTelaPais.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblPerfilSubTelaPais.setForeground(new java.awt.Color(0, 102, 102));
        lblPerfilSubTelaPais.setText("Perfil:");
        painelPrincipal.add(lblPerfilSubTelaPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 40, -1));

        lblRepositPerfil.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPerfil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        painelPrincipal.add(lblRepositPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 170, 30));

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

        btnEditar.setToolTipText("Editar Registro");
        btnEditar.setEnabled(false);
        btnEditar.setPreferredSize(new java.awt.Dimension(45, 45));
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
        painelPrincipal.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, -1));

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
        painelPrincipal.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, -1, -1));

        btnExcluir.setToolTipText("Excluir Registro");
        btnExcluir.setEnabled(false);
        btnExcluir.setPreferredSize(new java.awt.Dimension(45, 45));
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
        painelPrincipal.add(btnExcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void acaoMouseClicked() {

        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigo = Integer.parseInt("" + tabela.getValueAt(tabela.getSelectedRow(), 0));
        /**
         * Esse código está comentado só para ficar o exemplo de como pegaria o
         * valor de nome da tabela ou seja coluna 1 sendo que falando neses caso
         * trabalhamos como vetor que inicial do zero(0)
         */
        /*   cidadeDTO.setNomeCidadeDto("" + tblCidadesList.getValueAt(tblCidadesList.getSelectedRow(), 1));*/
        
        try {
            
            PaisDTO retorno = paisDAO.buscarPorId(codigo);
            
            if (retorno.getIdpaisregiaoDto() != null || !retorno.getIdpaisregiaoDto().equals("")) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */
                lblRepositCodigo.setText(String.valueOf(retorno.getIdpaisregiaoDto()));
                txtCodigoSMS.setText(retorno.getCodigoSMSDto());
                txtPais.setText(retorno.getPaisDto());

                /**
                 * Liberar os botões abaixo
                 */
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnCancelar.setEnabled(true);

                /**
                 * Habilitar Campos
                 */
                txtBuscar.setEnabled(true);

                /**
                 * Desabilitar campos
                 */
                /**
                 * Também irá habilitar nossos campos para que possamos digitar
                 * os dados no formulario medicos
                 */
                this.txtCodigoSMS.setEnabled(false);
                this.txtPais.setEnabled(false);
                
            } else {
                
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Registro não foi encontrado."
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: Método filtrarAoClicar()\n" + ex.getMessage());
        }
        
    }
    

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

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
        

    }//GEN-LAST:event_tabelaMouseClicked
    private void aoReceberFocoBtnPesquisar() {
        //    Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 

        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("Pressione ->[ENTER] Procurar ->[MYSQL]<--");
        
    }
    
    private void aoReceberFocoTxtBuscar() {
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("Digite o Registro Procurado -->[ENTER]<--");
    }
    
    private void personalizandoBarraInfoPesquisaTermino() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setForeground(Color.ORANGE);
        lblLinhaInformativa.setText("Pesquisa Terminada.");
        txtBuscar.requestFocus();
        txtBuscar.setBackground(Color.CYAN);
    }
    
    private void buscarEstados() {
        txtBuscar.requestFocus();
        
    }

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        btnPesquisar.requestFocus();
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
                    
                    campoPesquisar();
                } else {
                    addRowJTable();
                    
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            
        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed

    }//GEN-LAST:event_btnPesquisarKeyPressed

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        aoReceberFocoBtnPesquisar();
    }//GEN-LAST:event_btnPesquisarFocusGained
    
    private void adicionar() {
        
        if (!txtCodigoSMS.getText().isEmpty() && !txtPais.getText().isEmpty()) {

            //--------------------------------------------------------------------       
            //https://www.youtube.com/watch?v=jPfKFm2Yfow
            tabela.setDefaultRenderer(Object.class, new Render());
            
            JButton btnExcluir = new JButton("Excluir");
            
            btnExcluir.setName(
                    "Ex");
            
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            
            try {
                
                Object rowData[] = new Object[3];//são 04 colunas 

                rowData[0] = txtCodigoSMS.getText();
                rowData[1] = txtPais.getText();
                rowData[2] = btnExcluir;
                model.addRow(rowData);
                
                tabela.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
                
                txtCodigoSMS.setText("");
                txtPais.setText("");
                txtCodigoSMS.requestFocus();
                btnSalvar.setEnabled(true);
                
            } catch (Exception ex) {
                ex.printStackTrace();
                //Doctor MultClin
                erroViaEmail(ex.getMessage() + "\nAnalise Linhas469-528\nSubTelaPaisRegiao", "btnAdd SubTelaPaisRegiao");
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "contactar:sisvenda2011@gmail.com || ZAP:(99)984666132"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                
            }
            
        } else {
            //não ficar dando loop infinito colocar o foco em cima 
            //do campo codigo 
            txtCodigoSMS.requestFocus();
            //depois enviar a mensagen informativa 
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Preencha Todos os Campos Para que o \nSistema possa Inserir as Informações"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            
        }
    }
    
    private void SalvarAdicoesEdicoes() {
        //setando valores digitados nos campos 
        paisDTO.setCodigoSMSDto(txtCodigoSMS.getText());
        paisDTO.setPaisDto(txtPais.getText());
        
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

    private void txtCodigoSMSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoSMSFocusGained
        
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o Codigo do País(SMS)");
        
        txtCodigoSMS.setBackground(new Color(0, 102, 102));
        txtCodigoSMS.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtCodigoSMSFocusGained

    private void txtCodigoSMSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoSMSFocusLost
        
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        txtCodigoSMS.setBackground(Color.WHITE);
        txtCodigoSMS.setForeground(new Color(0, 102, 102));

    }//GEN-LAST:event_txtCodigoSMSFocusLost

    private void txtPaisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaisFocusGained
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o País");
        
        txtPais.setBackground(new Color(0, 102, 102));
        txtPais.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtPaisFocusGained

    private void txtPaisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaisFocusLost
        
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("");
        
        txtPais.setBackground(Color.WHITE);
        txtPais.setForeground(new Color(0, 102, 102));
    }//GEN-LAST:event_txtPaisFocusLost

    private void txtCodigoSMSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoSMSKeyPressed
        if (!txtCodigoSMS.getText().equals("")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                txtPais.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoSMSKeyPressed

    private void txtPaisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaisKeyPressed
        if (!txtPais.getText().equals("")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                btnSalvar.requestFocus();
                
            }
            
        }
        
        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtCodigoSMS.requestFocus();
        }
    }//GEN-LAST:event_txtPaisKeyPressed

    private void txtPaisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaisKeyTyped

    }//GEN-LAST:event_txtPaisKeyTyped

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
        txtCodigoSMS.setText("");
        txtPais.setText("");
        lblRepositCodigo.setText("");
        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        txtCodigoSMS.setEnabled(false);
        txtPais.setEnabled(false);
        
        txtBuscar.setEnabled(true);

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(true);
        
        btnEditar.setEnabled(false);
        btnExcluir.setEnabled(false);
        JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                + "Dados Cancelados com Sucesso."
                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
    }

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBtnCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnEditarMouseExited
    
    private void acaoBotaoEditar() {

        /**
         * Quando clicar em Editar essa flag recebe o valor de 2
         */
        flag = 2;

        /**
         * Também irá habilitar nossos campos para que possamos digitar os dados
         * no formulario medicos
         */
        this.txtCodigoSMS.setEnabled(true);
        this.txtPais.setEnabled(true);

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        this.btnSalvar.setEnabled(true);
        this.btnCancelar.setEnabled(true);
        this.btnEditar.setEnabled(false);
        this.btnAdicionar.setEnabled(false);
        this.btnExcluir.setEnabled(false);
        
        this.btnCancelar.requestFocus();
        this.btnCancelar.setBackground(Color.WHITE);
        
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        acaoBotaoEditar();
    }//GEN-LAST:event_btnEditarActionPerformed

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
        this.txtCodigoSMS.setEnabled(true);
        this.txtPais.setEnabled(true);

        /**
         * Limpar os campos para cadastrar
         */
        //txtIDUsuario.setVisible(false);
        this.txtCodigoSMS.setText("");
        this.txtPais.setText("");

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
        this.btnEditar.setEnabled(false);
        
        this.txtCodigoSMS.requestFocus();//setar o campo nome Bairro após adicionar

    }
    

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnExcluirMouseExited
    
    private void comportamentoAposExclusao() {
        /**
         * Ações de Botões
         */
        
        btnExcluir.setEnabled(false);
        btnAdicionar.setEnabled(true);
        /**
         * Após salvar limpar os campos
         */
        lblRepositCodigo.setText("");
        txtCodigoSMS.setText("");
        txtPais.setText("");
        
        txtCodigoSMS.setEnabled(false);
        txtPais.setEnabled(iconable);
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
    
    private void acaoBotaoExcluir() {
        
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        
        if (recebeConexao == true) {
            
            try {

                /*Evento ao ser clicado executa código*/
                int excluir = JOptionPane.showConfirmDialog(null, "Deseja Excluir Registro?", "Informação", JOptionPane.YES_NO_OPTION);
                
                if (excluir == JOptionPane.YES_OPTION) {
                    
                    paisDTO.setIdpaisregiaoDto(Integer.parseInt(lblRepositCodigo.getText()));

                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    paisDAO.deletar(paisDTO);

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
    
    private void salvar() {

        /**
         * capturando os campos do Form na Camada Gui e em vez de adicionar ha
         * uma variável encapsulamos e setamos como o método set
         */
        paisDTO.setCodigoSMSDto(txtCodigoSMS.getText());
        paisDTO.setPaisDto(txtPais.getText());
        
        try {
            /**
             * Depois de capturados e atribuídos seus respectivos valores
             * capturados nas variáveis acimas descrita. Iremos criar um objeto
             * do tipo UsuarioBO
             */
            paisBO = new PaisBO();

            /**
             * Trabalhando com os retornos das validações
             */
            if ((paisBO.validaNome(paisDTO)) == false) {
                txtCodigoSMS.setText("");
                
            } else {
                
                if ((flag == 1)) {
                    
                    paisBO.cadastrar(paisDTO);
                    /**
                     * Após salvar limpar os campos
                     */
                    txtCodigoSMS.setText("");
                    txtPais.setText("");

                    /**
                     * Bloquear campos e Botões
                     */
                    txtCodigoSMS.setEnabled(false);
                    txtPais.setEnabled(false);
                    txtBuscar.setEnabled(true);
                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    btnSalvar.setEnabled(false);
                    btnAdicionar.setEnabled(true);
                    
                    lblLinhaInformativa.setText("Resgistro Cadastrado com Sucesso");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));
                    
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
                    paisDTO.setIdpaisregiaoDto(Integer.parseInt(lblRepositCodigo.getText()));
                    /**
                     * capturando os campos do Form na Camada Gui e em vez de
                     * adicionar ha uma variável encapsulamos e setamos como o
                     * método set
                     */
                    paisDTO.setCodigoSMSDto(txtCodigoSMS.getText());
                    paisDTO.setPaisDto(txtPais.getText());
                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    paisBO.atualizarBO(paisDTO);

                    /**
                     * Após salvar limpar os campos
                     */
                    txtCodigoSMS.setText("");
                    txtPais.setText("");

                    /**
                     * Bloquear campos e Botões
                     */
                    txtCodigoSMS.setEnabled(false);
                    txtPais.setEnabled(false);

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
            JOptionPane.showMessageDialog(null, "Camada GUI: " + e.getMessage());
            
            if (e.getMessage().equals("Campo Codigo Obrigatorio")) {
                txtCodigoSMS.requestFocus();
                txtCodigoSMS.setBackground(new Color(0, 102, 102));
            }
            
            if (e.getMessage().equals("Campo nome aceita no MAX 5 chars")) {
                txtCodigoSMS.requestFocus();
                txtCodigoSMS.setBackground(new Color(0, 102, 102));
                lblLinhaInformativa.setText("Campo aceita no Máximo a digitação de 5 caracteres");
                
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
    
    private void campoPesquisar() {

        //método trim() tira espaços antes e depois 
        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText().trim());
        
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        
        ArrayList<PaisDTO> list;
        
        try {
            
            list = (ArrayList<PaisDTO>) paisDAO.filtrarUsuarioPesqRapida(pesquisar);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[3];
            
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdpaisregiaoDto();
                rowData[1] = list.get(i).getCodigoSMSDto();
                rowData[2] = list.get(i).getPaisDto();
                
                model.addRow(rowData);
            }
            
            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            
        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }
        
    }
    

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        
        if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
            if (txtBuscar.getText().isEmpty()) {
                txtBuscar.requestFocus();
                txtBuscar.setBackground(Color.YELLOW);
                txtBuscar.setForeground(Color.BLACK);
                
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Digite o nome de Um País pressione [ENTER] \n"
                        + "e o Sistema irá Efetuar á Pesquisa no Banco de Dados"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                
            }
            if (!txtBuscar.getText().isEmpty()) {
                
                btnPesquisar.requestFocus();
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
                            
                            campoPesquisar();
                        } else {
                            addRowJTable();
                            
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                            + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    
                }
            }
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setText("Digite o País que deseja Pesquisar");
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
    }//GEN-LAST:event_txtBuscarFocusGained

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
    public static javax.swing.JButton btnEditar;
    public static javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblPaisRegiao;
    private javax.swing.JLabel lblPerfilSubTelaPais;
    private javax.swing.JLabel lblRepositCodigo;
    private javax.swing.JLabel lblRepositPerfil;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigoSMS;
    private javax.swing.JTextField txtPais;
    // End of variables declaration//GEN-END:variables
}
