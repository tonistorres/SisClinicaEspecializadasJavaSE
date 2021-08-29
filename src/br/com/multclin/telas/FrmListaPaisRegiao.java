package br.com.multclin.telas;

import br.com.multclin.bo.PaisBO;
import br.com.multclin.dao.PaisDAO;
import br.com.multclin.dto.PaisDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.SubTelaFone.lblReposiSMStPaisRegiao;
import static br.com.multclin.telas.SubTelaFone.lblRepositIDPaisRegiao;
import static br.com.multclin.telas.SubTelaFone.lblRepositPaisRegiao;
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
public class FrmListaPaisRegiao extends javax.swing.JInternalFrame {

    PaisDTO paisDTO = new PaisDTO();
    PaisDAO paisDAO = new PaisDAO();
    PaisBO paisBO = new PaisBO();

    int flag;

    Font f = new Font("Tahoma", Font.BOLD, 9);
    /**
     * Código Mestre Chimura
     */
    private static FrmListaPaisRegiao instance = null;

    public static FrmListaPaisRegiao getInstance() {

        if (instance == null) {

            instance = new FrmListaPaisRegiao();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public FrmListaPaisRegiao() {
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

        lblRepositPerfil.setText(lblPerfil.getText());

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

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));

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
        progressBarraPesquisa = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        lblPais = new javax.swing.JLabel();
        lblPerfilSubTelaPais = new javax.swing.JLabel();
        lblRepositPerfil = new javax.swing.JLabel();

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelPrincipal.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 280, 340));

        lblPerfilSubTelaPais.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblPerfilSubTelaPais.setForeground(new java.awt.Color(0, 102, 102));
        lblPerfilSubTelaPais.setText("Perfil:");
        painelPrincipal.add(lblPerfilSubTelaPais, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 40, -1));

        lblRepositPerfil.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPerfil.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        painelPrincipal.add(lblRepositPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 170, 30));

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

        int linha = tabela.getSelectedRow();
        lblRepositIDPaisRegiao.setText(tabela.getValueAt(linha, 0).toString());
        lblReposiSMStPaisRegiao.setText(tabela.getValueAt(linha, 1).toString());
        lblRepositPaisRegiao.setText(tabela.getValueAt(linha, 2).toString());
        this.dispose();
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
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblPerfilSubTelaPais;
    private javax.swing.JLabel lblRepositPerfil;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
