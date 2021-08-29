package br.com.multclin.telas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dao.ModeloAgendamentoItemDAO;
import br.com.multclin.dao.ProcedimentoDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.ModeloAgendamentoItemDTO;
import br.com.multclin.dto.ProcedimentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;
import br.com.multclin.util.PINTAR_TABELA_CONSULTA_PROCEDIMENTOS_POR_MEDICO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TelaCosultarProcedimentoPorMedico extends javax.swing.JInternalFrame {

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());
    Font f = new Font("Tahoma", Font.BOLD, 12);

    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    AgendamentoBO agendamentoBO = new AgendamentoBO();
    ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();
    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();

    ModeloAgendamentoItemDAO modeloAgendamentoItemDAO = new ModeloAgendamentoItemDAO();

    int flagC = 0;

    //label barra de ferramenta
    JLabel jLabelSystem = new JLabel();
    JLabel jLteste = new JLabel();
    JLabel jLTesteConexaoPositivo = new JLabel();
    JLabel jLRegistroCapturados = new JLabel();
    JLabel jLNuvemEstavel = new JLabel();
    JLabel jLDrivers = new JLabel();
    JLabel jLGerenciaBancoMysql = new JLabel();
    JLabel jL40 = new JLabel();
    JLabel jL50 = new JLabel();
    JLabel jL60 = new JLabel();
    JLabel jL70 = new JLabel();
    JLabel jL80 = new JLabel();
    JLabel jL90 = new JLabel();
    JLabel jLEnd = new JLabel();

    public TelaCosultarProcedimentoPorMedico() throws PersistenciaException {
        initComponents();
        frontEnd();

        progressBar();

    }

    private void aoCarregar() throws PersistenciaException {

        txtNome.setEditable(false);
        txtProcedimento.setEditable(false);
        lblLinhaInformativaConsulta.setEnabled(false);
//        btnBuscarPorProcede.setEnabled(false);
//        btnPesquisarNome.setEnabled(false);

        lblLinhaInformativaConsulta.setEnabled(true);
        lblLinhaInformativaConsulta.setEditable(false);
        txtNome.requestFocus();
        // listarCombo();

        pesquisarPorNomeProce();

    }

    private void progressBar() {
        //***************
        //PROGRESS BAR //
        //***********************************************************************
        //Contribuição Canal Yoube:https://www.youtube.com/watch?v=co2cITXfXqY//
        //**********************************************************************

        new Thread() {

            public void run() {

                //       iniciandoRelogio();
                for (int i = 0; i < 101; i++) {

                    try {
//29
                        sleep(50);

                        // System.out.println("vaor de i " + i);
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() == 10) {
                            lblLinhaInformativaConsulta.setText("10% Inicializando barra de progresso");
                            lblLinhaInformativaConsulta.setVisible(true);

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLabelSystem = new JLabel();
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/systems.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 20) {
                            lblLinhaInformativaConsulta.setText("20% Verificando se há conexão com a núvem ");

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLteste = new JLabel();
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/hostgator.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(55, 15, 50, 50);
                            fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            lblLinhaInformativaConsulta.setText("30% Conexão OK estável...");
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLTesteConexaoPositivo = new JLabel();
                            jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/conexao.png")));
                            painelSimulador.add(jLTesteConexaoPositivo);
                            jLTesteConexaoPositivo.setBounds(105, 15, 50, 50);
                            aoCarregar();

                        } else if (barraProgresso.getValue() == 40) {
                            lblLinhaInformativaConsulta.setText("40% Registros Capturados...");
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLRegistroCapturados = new JLabel();
                            jLRegistroCapturados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/registrosCap.png")));
                            painelSimulador.add(jLRegistroCapturados);
                            jLRegistroCapturados.setBounds(165, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 50) {
                            lblLinhaInformativaConsulta.setText("50% Núvem estável...");
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLNuvemEstavel = new JLabel();
                            jLNuvemEstavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemEstavel.png")));
                            painelSimulador.add(jLNuvemEstavel);
                            jLNuvemEstavel.setBounds(215, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 60) {

                            lblLinhaInformativaConsulta.setText("60% Sistema estável...");
                        } else if (barraProgresso.getValue() == 70) {

                            lblLinhaInformativaConsulta.setText("70% Drivers estáveis...");
                        } else if (barraProgresso.getValue() == 80) {

                            lblLinhaInformativaConsulta.setText("80% Gerenciador de banco estável...");
                        } else if (barraProgresso.getValue() == 90) {

                            lblLinhaInformativaConsulta.setText("90% Inicializando procedimentos finais...");
                        } else if (barraProgresso.getValue() == 100) {

                            lblLinhaInformativaConsulta.setText("100% fim da Pesquisa.");
                            //se quiser desligar o relogio 
//                            estado = false;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                    }

                }
            }
        }.start();// iniciando a Thread
    }


    private void fazendoTesteConexao() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            System.out.println("Verificando resultado: " + recebeConexao);
        } else {

            lblLinhaInformativaConsulta.setForeground(Color.red);
            lblLinhaInformativaConsulta.setText("SEM CONEXÃO COM A INTERNET OU ACESSO AO BANCO DE DADOS...");
            lblLinhaInformativaConsulta.setFont(f);
            acaoSairDoSistemaFormLocal();
        }

    }

    private void acaoSairDoSistemaFormLocal() {

        new Thread() {

            public void run() {

                for (int i = 0; i < 101; i++) {

                    try {

                        sleep(70);
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() <= 5) {

                            barraProgresso.setVisible(true);
                            lblLinhaInformativaConsulta.setVisible(true);
                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 5%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect1.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 15) {

                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 15%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect2.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(45, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 25) {

                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 25%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));

                            jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect3.png")));
                            painelSimulador.add(jLTesteConexaoPositivo);
                            jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 35) {
                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 35%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jL40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect4.png")));
                            painelSimulador.add(jL40);
                            jL40.setBounds(135, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 45) {
                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 45%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jL50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect5.png")));
                            painelSimulador.add(jL50);
                            jL50.setBounds(185, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 55) {
                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 55%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jL60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconnect6.png")));
                            painelSimulador.add(jL60);
                            jL60.setBounds(235, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 65) {
                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 65%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jL70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect7.jpg")));
                            painelSimulador.add(jL70);
                            jL70.setBounds(290, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 75) {
                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 75%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jL80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect8.png")));
                            painelSimulador.add(jL80);
                            jL80.setBounds(345, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 85) {
                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 85%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jL90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect9.png")));
                            painelSimulador.add(jL90);
                            jL90.setBounds(400, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 95) {
                            lblLinhaInformativaConsulta.setText("Sem Conexão saindo sistema em 95%");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect10.png")));
                            painelSimulador.add(jLEnd);
                            jLEnd.setBounds(455, 15, 100, 50);

                        } else {
                            lblLinhaInformativaConsulta.setText("Encerrado com sucesso!");
                            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));
                            jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect11.png")));
                            painelSimulador.add(jLEnd);
                            jLEnd.setBounds(455, 15, 100, 50);

                            System.exit(0);//sair do sistema
                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                }
            }
        }.start();// iniciando a Thread

    }

    private void limpaTodosIconesLinhaTemporal() {
        //label barra de ferramenta
        jLabelSystem.setIcon(null);//ok 10%
        jLteste.setIcon(null);//ok 20%
        jLTesteConexaoPositivo.setIcon(null);//ok 30%
        jLRegistroCapturados.setIcon(null);
        jLNuvemEstavel.setIcon(null);
        jLDrivers.setIcon(null);
        jLGerenciaBancoMysql.setIcon(null);

        jL40.setIcon(null);
        jL50.setIcon(null);
        jL60.setIcon(null);
        jL70.setIcon(null);
        jL80.setIcon(null);
        jL90.setIcon(null);
        jLEnd.setIcon(null);

    }

    public void listarCombo() throws PersistenciaException {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            ArrayList<ProcedimentoDTO> list;

            list = (ArrayList<ProcedimentoDTO>) procedimentoDAO.listarTodos();
//            cbProcede.removeAllItems();
            for (int i = 0; i < list.size(); i++) {

                //              cbProcede.addItem(list.get(i).getNomeDto());
            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }

    }

    private void frontEnd() {
//        this.btnPesquisarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        //      this.btnBuscarPorProcede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
//        //  this.btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
//        lblLogoMultClin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/fundo/logoPequena.jpeg")));

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelPesquisaNome = new javax.swing.JPanel();
        txtNome = new javax.swing.JTextField();
        painelValorTotal = new javax.swing.JPanel();
        lblValorTotalAgenda = new javax.swing.JLabel();
        lblLinhaInformativaConsulta = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtProcedimento = new javax.swing.JTextField();
        painelSimulador = new javax.swing.JPanel();
        lblLinhaInformativa1 = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdAg", "IdPc", "Paciente", "Data", "R$ ValorTotal", "Profissional Saúde", "Procedimentos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(42);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(250);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(90);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(6).setResizable(false);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(150);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 267, 780, 250));

        painelPesquisaNome.setBackground(java.awt.Color.white);
        painelPesquisaNome.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profissional Saúde:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPesquisaNome.setForeground(java.awt.Color.white);
        painelPesquisaNome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNome.setPreferredSize(new java.awt.Dimension(6, 30));
        txtNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomeFocusLost(evt);
            }
        });
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeKeyTyped(evt);
            }
        });
        painelPesquisaNome.add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 20, 200, -1));

        jPanel1.add(painelPesquisaNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 220, 70));

        painelValorTotal.setBackground(java.awt.Color.white);
        painelValorTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "R$ Total:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        painelValorTotal.setLayout(null);

        lblValorTotalAgenda.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblValorTotalAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblValorTotalAgenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelValorTotal.add(lblValorTotalAgenda);
        lblValorTotalAgenda.setBounds(10, 20, 240, 50);

        jPanel1.add(painelValorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 260, 80));

        lblLinhaInformativaConsulta.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Linha Informativa:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.add(lblLinhaInformativaConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 480, 80));

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Procedimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel2.setForeground(java.awt.Color.white);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtProcedimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProcedimentoKeyPressed(evt);
            }
        });
        jPanel2.add(txtProcedimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 20, 230, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, 250, 70));

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSimulador.setLayout(null);

        lblLinhaInformativa1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLinhaInformativa1.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        painelSimulador.add(lblLinhaInformativa1);
        lblLinhaInformativa1.setBounds(580, 15, 210, 20);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(580, 35, 210, 24);

        jPanel1.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusGained

        txtNome.setBackground(new Color(0, 102, 102));
        txtNome.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtNomeFocusGained

    private void txtNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusLost

        txtNome.setBackground(Color.white);
        txtNome.setForeground(Color.BLACK);

    }//GEN-LAST:event_txtNomeFocusLost

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed

        if (!txtNome.getText().equals("")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                //iniciando pesquisa no banco
                acaoInicioDePesquisaBanco();

//                this.btnPesquisarNome.requestFocus();
            } else {
                if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                    JOptionPane.showMessageDialog(this, "" + "\n O nome é obrigatório.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtNome.requestFocus();
                }
            }
        }

    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
        int numeroDeCaracter = 20;

        if (txtNome.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 20 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtNome.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro

        }
    }//GEN-LAST:event_txtNomeKeyTyped

    private void contarClienteTableModel() {

        int cont = 0;

        try {

            for (int i = 0; i < tabela.getRowCount(); i++) {
                tabela.getValueAt(i, 0);
                cont++;

            }
            Font fContagem = new Font("Tahoma", Font.BOLD, 18);
            lblLinhaInformativaConsulta.setText("");
            lblLinhaInformativaConsulta.setText(String.valueOf(cont) + " Pacientes Agendados");
            lblLinhaInformativaConsulta.setFont(fContagem);
            lblLinhaInformativaConsulta.setBackground(Color.WHITE);
            lblLinhaInformativaConsulta.setForeground(new Color(0, 102, 102));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void acaoInicioDePesquisaBanco() {

        //coloca o cursor em modo de espera 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.lblLinhaInformativaConsulta.setFont(f);
        this.lblLinhaInformativaConsulta.setForeground(Color.BLACK);
        this.lblLinhaInformativaConsulta.setBackground(Color.ORANGE);
        this.lblLinhaInformativaConsulta.setText("");
        this.lblLinhaInformativaConsulta.setText("Iniciando a Pesquisa no Gerenciador de Banco de Dados MYSQ");

    }

    private void acaoBuscaPorNome() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisarPorNome();

        } else {
            pesquisarPorNome();
        }

    }

    private void pesquisarPorNome() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ModeloAgendamentoItemDTO> list;

        try {

            String nome = MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText());
            String status = "AGENDADO";

            list = (ArrayList<ModeloAgendamentoItemDTO>) modeloAgendamentoItemDAO.filtrarProcedimentosPorMedico(status, nome);

            Object rowData[] = new Object[7];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getModAgendamentoDto().getIdDto();
                rowData[1] = list.get(i).getModAgendamentoDto().getFk_pacienteDto();
                rowData[2] = list.get(i).getModAgendamentoDto().getNomeDto();

                rowData[3] = formatador.format(list.get(i).getModAgendamentoDto().getDataAgendamentoDto());
                rowData[4] = moeda.format(list.get(i).getModItemDto().getRsBrutoDto());
                rowData[5] = list.get(i).getModAgendamentoDto().getNomeMedicoDto();
                rowData[6] = list.get(i).getModItemDto().getNomeProcedeDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(250);//nome

            tabela.getColumnModel().getColumn(3).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//valor total
            tabela.getColumnModel().getColumn(5).setPreferredWidth(120);//info
            tabela.getColumnModel().getColumn(6).setPreferredWidth(150);//info
            //fim da pesquisa 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            contarClienteTableModel();
            somaTotalProcede();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void somaTotalProcede() {
        try {

            float somaTotal = 0, valorTotal;

            int contador = tabela.getRowCount();

            for (int i = 0; i < contador; i++) {

                String rsValorTratado = tabela.getValueAt(i, 4).toString().replace("R$", "").trim();
                //            String rsDescontoTratado = tabela.getValueAt(i, 3).toString().replace("R$", "").trim();

                valorTotal = (MetodoStaticosUtil.converteFloat(rsValorTratado));

                somaTotal = somaTotal + valorTotal;
            }

            lblValorTotalAgenda.setText(moeda.format(somaTotal));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    private void txtProcedimentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProcedimentoKeyPressed

        if (!txtNome.getText().equals("") && !txtProcedimento.equals("")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                //iniciando pesquisa no banco
                acaoInicioDePesquisaBanco();

             //   this.btnBuscarPorProcede.requestFocus();
            } else {
                if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                    JOptionPane.showMessageDialog(this, "" + "\n Obrigatório o preencheimento\n"
                            + "dos campos Nome Profissional Saúde\n"
                            + "Tipo de Procedimento.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtProcedimento.requestFocus();
                }
            }
        }

    }//GEN-LAST:event_txtProcedimentoKeyPressed

    private void acaoBuscarPorNomeProcede() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisarPorNomeProce();

        } else {
            pesquisarPorNomeProce();
        }

    }

    private void pesquisarPorNomeProce() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ModeloAgendamentoItemDTO> list;

        try {
            //  tabela.setDefaultRenderer(Object.class, new PINTAR_TABELA_CONSULTA_PROCEDIMENTOS_POR_MEDICO());
            String nomeProfissionalSaude = MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText());
            String procede = MetodoStaticosUtil.removerAcentosCaixAlta(txtProcedimento.getText());
            String statusAgendamento = "AGENDADO";

            list = (ArrayList<ModeloAgendamentoItemDTO>) modeloAgendamentoItemDAO.filtrarPorMedidoProcede(statusAgendamento, nomeProfissionalSaude, procede);

            Object rowData[] = new Object[7];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getModAgendamentoDto().getIdDto();
                rowData[1] = list.get(i).getModAgendamentoDto().getFk_pacienteDto();
                rowData[2] = list.get(i).getModAgendamentoDto().getNomeDto();

                rowData[3] = formatador.format(list.get(i).getModAgendamentoDto().getDataAgendamentoDto());
                rowData[4] = moeda.format(list.get(i).getModItemDto().getRsBrutoDto());
                rowData[5] = list.get(i).getModAgendamentoDto().getNomeMedicoDto();
                rowData[6] = list.get(i).getModItemDto().getNomeProcedeDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(250);//nome

            tabela.getColumnModel().getColumn(3).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//valor total
            tabela.getColumnModel().getColumn(5).setPreferredWidth(120);//info
            tabela.getColumnModel().getColumn(6).setPreferredWidth(150);//info
            //fim da pesquisa 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
       //     contarClienteTableModel();
            //    somaTotalProcede();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLinhaInformativa1;
    private javax.swing.JTextField lblLinhaInformativaConsulta;
    private javax.swing.JLabel lblValorTotalAgenda;
    private javax.swing.JPanel painelPesquisaNome;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JPanel painelValorTotal;
    private javax.swing.JTable tabela;
    public static javax.swing.JTextField txtNome;
    public static javax.swing.JTextField txtProcedimento;
    // End of variables declaration//GEN-END:variables
}
