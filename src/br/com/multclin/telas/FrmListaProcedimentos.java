package br.com.multclin.telas;

import br.com.multclin.bo.ProcedimentoBO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.ProcedimentoDAO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.ProcedimentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaAgendamento.btnPesqMedico;
import static br.com.multclin.telas.TelaAgendamento.btnPesqPaciente;
import static br.com.multclin.telas.TelaAgendamento.cbEspecialidade;
import static br.com.multclin.telas.TelaAgendamento.lblAutoProcedimentos;
import static br.com.multclin.telas.TelaAgendamento.lblIdMedico;
import static br.com.multclin.telas.TelaAgendamento.lblRepositIDEspecialidade;
import static br.com.multclin.telas.TelaAgendamento.lblRepositNomeMedico;
import static br.com.multclin.telas.TelaAgendamento.lblRepositProcede;
import static br.com.multclin.telas.TelaAgendamento.lblRsBruto;
import static br.com.multclin.telas.TelaAgendamento.txtRsDesconto;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class FrmListaProcedimentos extends javax.swing.JInternalFrame {

//        Locale br=new Locale("pt","Brazil");
//        NumberFormat nf=NumberFormat.getInstance(br);
    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());

    ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();
    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();
    ProcedimentoBO medicoBO = new ProcedimentoBO();

    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
    /**
     * Código Mestre Chimura
     */
    private static FrmListaProcedimentos instance = null;

    public static FrmListaProcedimentos getInstance() {

        if (instance == null) {

            instance = new FrmListaProcedimentos();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public FrmListaProcedimentos() {

        initComponents();
        addRowJTable();
        frontEnd();
             //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela         

        tabela.getTableHeader().setDefaultRenderer(new br.com.multclin.util.EstiloTabelaHeader());//classe EstiloTableHeader Cabeçalho da Tabela

        //*************************************************************************
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

    private void frontEnd() {
        this.lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
    }

    private void desabilitarCampos() {
        txtBuscar.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {

        btnPesquisar.setEnabled(false);
    }

    public void addRowJTable() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ArrayList<ProcedimentoDTO> list;

            int codigo = Integer.parseInt(lblRepositIDEspecialidade.getText());
            try {

                list = (ArrayList<ProcedimentoDTO>) procedimentoDAO.listarTodosParametroID(codigo);

                Object rowData[] = new Object[4];
                for (int i = 0; i < list.size(); i++) {

                    String rsBruto = moeda.format(list.get(i).getRsBrutoDto());

                    rowData[0] = list.get(i).getIdAutoDto();
                    rowData[1] = list.get(i).getIdDto();
                    rowData[2] = list.get(i).getNomeDto().toString();
                    rowData[3] = rsBruto.replace("R$", "").trim();
                    model.addRow(rowData);
                }

                tabela.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(60);
                tabela.getColumnModel().getColumn(2).setPreferredWidth(280);
                tabela.getColumnModel().getColumn(3).setPreferredWidth(150);

                progressBarraPesquisa.setIndeterminate(false);
                progressBarraPesquisa.setIndeterminate(closable);

            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FrmListaEstados \n"
                        + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                        + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());

                progressBarraPesquisa.setIndeterminate(false);
                progressBarraPesquisa.setIndeterminate(closable);
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            desabilitarCampos();
            desabilitarTodosBotoes();
        }

    }

    private void CampoPesquisar() {

           int codigo = Integer.parseInt(lblRepositIDEspecialidade.getText());
        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ProcedimentoDTO> list;

        try {

            list = (ArrayList<ProcedimentoDTO>) procedimentoDAO.filtrarPesqRapida(pesquisar,codigo);

            Object rowData[] = new Object[4];
            for (int i = 0; i < list.size(); i++) {

                String rsBruto = moeda.format(list.get(i).getRsBrutoDto());

                rowData[0] = list.get(i).getIdAutoDto();
                rowData[1] = list.get(i).getIdDto();
                rowData[2] = list.get(i).getNomeDto().toString();
                rowData[3] = rsBruto.replace("R$", "").trim();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(150);
            
            progressBarraPesquisa.setIndeterminate(false);
            progressBarraPesquisa.setIndeterminate(closable);

            personalizandoBarraInfoPesquisaTermino();
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormListaEstados \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
            progressBarraPesquisa.setIndeterminate(false);
            progressBarraPesquisa.setIndeterminate(closable);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        lblLinhaInformativa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        lblNuvemForms = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Lista de Procedimentos");

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
        painelCabecalho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar.setBackground(new java.awt.Color(255, 255, 204));
        txtBuscar.setOpaque(false);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        painelCabecalho.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 220, 30));

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
        btnPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPesquisarKeyPressed(evt);
            }
        });
        painelCabecalho.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 32, 30));
        painelCabecalho.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 50, -1));

        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa.setText("Linha Informativa");
        painelCabecalho.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 5, 380, 29));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdAuto", "ID", "PROCEDIMENTOS", "R$ BRUTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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
            tabela.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(2).setMinWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setMaxWidth(280);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(170);
        }

        painelCabecalho.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 430, 320));
        painelCabecalho.add(lblNuvemForms, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 40, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased


    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        int linha = tabela.getSelectedRow();

        lblAutoProcedimentos.setText(tabela.getValueAt(linha, 0).toString());
        lblRepositProcede.setText(tabela.getValueAt(linha, 2).toString());
        lblRsBruto.setText(tabela.getValueAt(linha, 3).toString());

        cbEspecialidade.setEnabled(true);
        cbEspecialidade.setBackground(Color.YELLOW);
        cbEspecialidade.setForeground(Color.BLACK);
        btnPesqPaciente.setEnabled(true);
        btnPesqPaciente.requestFocus();
        this.dispose();

    }//GEN-LAST:event_tabelaMouseClicked
    private void aoReceberFocoBtnPesquisar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("Pressione -->[ENTER]<-- Procurar -->[MYSQL]<--");

    }

    private void aoReceberFocoTxtBuscar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 14);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("Digite o Registro -->[ENTER]<--");
    }

    private void personalizandoBarraInfoPesquisaTermino() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 14);//label informativo 
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("Pesquisa Terminada.");
        txtBuscar.requestFocus();

    }

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusGained
        txtBuscar.setBackground(new Color(0, 102, 102));
        txtBuscar.setForeground(Color.WHITE);
        txtBuscar.setToolTipText("Digite o Registro Procurado");
        aoReceberFocoTxtBuscar();
    }//GEN-LAST:event_txtBuscarFocusGained

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBuscarFocusLost
        txtBuscar.setBackground(Color.WHITE);
        txtBuscar.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtBuscarFocusLost

    private void acaoBotaoPesquisar() {

        progressBarraPesquisa.setIndeterminate(true);

        try {

            int numeroLinhas = tabela.getRowCount();

            if (numeroLinhas > 0) {

                while (tabela.getModel().getRowCount() > 0) {
                    ((DefaultTableModel) tabela.getModel()).removeRow(0);

                }

                CampoPesquisar();

            } else {
                addRowJTable();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void buscarProcedimentos() {
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
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }

    }

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

        buscarProcedimentos();

    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed

        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtBuscar.setBackground(Color.YELLOW);

            btnPesquisar.requestFocus();
            btnPesquisar.setBackground(Color.YELLOW);
            txtBuscar.setBackground(Color.WHITE);
        }


    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnPesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPesquisarKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            buscarProcedimentos();
        }
    }//GEN-LAST:event_btnPesquisarKeyPressed

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        aoReceberFocoBtnPesquisar();
    }//GEN-LAST:event_btnPesquisarFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
