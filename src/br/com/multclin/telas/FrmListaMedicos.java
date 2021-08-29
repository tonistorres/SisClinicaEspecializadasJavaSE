package br.com.multclin.telas;

import br.com.multclin.bo.MedicoBO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.MedicoDAO;
import br.com.multclin.dao.ModeloMedicoEspecialidadeDAO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.MedicoDTO;
import br.com.multclin.dto.ModeloMedicoEspecialidadeDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaAgendamento.btnPesqMedico;
import static br.com.multclin.telas.TelaAgendamento.btnPesqPaciente;
import static br.com.multclin.telas.TelaAgendamento.cbEspecialidade;
import static br.com.multclin.telas.TelaAgendamento.jDateTxtDtAgenda;
import static br.com.multclin.telas.TelaAgendamento.lblIdMedico;
import static br.com.multclin.telas.TelaAgendamento.lblRepositIDEspecialidade;
import static br.com.multclin.telas.TelaAgendamento.lblRepositNomeMedico;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class FrmListaMedicos extends javax.swing.JInternalFrame {

    Font f = new Font("Tahoma", Font.BOLD, 12);
    MedicoDTO medicoDTO = new MedicoDTO();
    MedicoDAO medicoDAO = new MedicoDAO();
    MedicoBO medicoBO = new MedicoBO();
    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
    ModeloMedicoEspecialidadeDTO modeloMedicoEspecialidadeDTO = new ModeloMedicoEspecialidadeDTO();
    ModeloMedicoEspecialidadeDAO modeloMedicoEspecialidadeDAO = new ModeloMedicoEspecialidadeDAO();
    ConexaoUtil conecta = new ConexaoUtil();

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

    int flagC = 0;
    /**
     * Código Mestre Chimura
     */
    private static FrmListaMedicos instance = null;

    public static FrmListaMedicos getInstance() {

        if (instance == null) {

            try {
                instance = new FrmListaMedicos();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public FrmListaMedicos() throws ClassNotFoundException, SQLException {

        initComponents();
        frontEnd();

        //carregando o formulario buscando Dados em Nuvem 
        int flagBtn = 2;
        progressBar(flagBtn);

    }

    private void buscandoDadosNuvem() {

        try {
            conecta.getInstance().getConnection();

            if (conecta != null) {

                addRowJTable();

            }

        } catch (Exception e) {

            e.getMessage();
            acaoSairDoSistemaFormLocal();
        }

    }

    //***************
    //PROGRESS BAR //
    //***********************************************************************
    //Contribuição Canal Yoube:https://www.youtube.com/watch?v=co2cITXfXqY//
    //**********************************************************************
    private void progressBar(int flagC) {

        new Thread() {

            public void run() {

                lblLinhaInformativa.setVisible(true);
                barraProgresso.setVisible(true);
                jLEnd.setIcon(null);
                limpaTodosIconesLinhaTemporal();
                for (int i = 0; i < 101; i++) {

                    try {

                        sleep(5);
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() == 1) {
                            lblLinhaInformativa.setText("1% Inicializando barra de progresso");

                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/systems.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 20) {

                            lblLinhaInformativa.setText("20% Executando teste de Conexão núvem... ");
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/hostgator.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(45, 15, 50, 50);
                            //não precisa acabou de ser feito em tela anterior
                            //  fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            switch (flagC) {

                                case 1: {

                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    aoReceberFocoBtnPesquisar();

                                    break;
                                }

                                case 2: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);
                                    buscandoDadosNuvem();
                                    break;
                                }

                                case 3: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    break;
                                }

                                case 4: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    int linha = tabela.getSelectedRow();

                                    lblIdMedico.setText(tabela.getValueAt(linha, 0).toString());
                                    lblRepositNomeMedico.setText(tabela.getValueAt(linha, 1).toString());
                                    criarListaComboEspecialidade();
                                    cbEspecialidade.setEnabled(true);
                                    cbEspecialidade.setBackground(Color.YELLOW);
                                    cbEspecialidade.setForeground(Color.BLACK);
                                    btnPesqPaciente.setEnabled(true);
                                    btnPesqPaciente.requestFocus();

                                    dispose();

                                    break;
                                }

                                case 5: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    break;
                                }

                            }

                        } else if (barraProgresso.getValue() == 40) {
                            lblLinhaInformativa.setText("40% linha temporal...");
                            jL40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar40.png")));
                            painelSimulador.add(jL40);
                            jL40.setBounds(135, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 50) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            lblLinhaInformativa.setText("50% linha temporal...");
                            jL50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar50.png")));
                            painelSimulador.add(jL50);
                            jL50.setBounds(185, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 60) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            lblLinhaInformativa.setText("60% linha temporal...");
                            jL60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar60.png")));
                            painelSimulador.add(jL60);
                            jL60.setBounds(235, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 70) {
                            lblLinhaInformativa.setText("70% linha temporal...");
                            jL70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar70.png")));
                            painelSimulador.add(jL70);
                            jL70.setBounds(290, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 80) {
                            lblLinhaInformativa.setText("80% linha temporal...");
                            jL80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar80.png")));
                            painelSimulador.add(jL80);
                            jL80.setBounds(345, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 90) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            lblLinhaInformativa.setText("90% teste linha temporal...");
                            jL90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar90.png")));
                            painelSimulador.add(jL90);
                            jL90.setBounds(400, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 100) {

                            //limpaTodosIconesLinhaTemporal();
                            lblLinhaInformativa.setText("100% Fim da execução...");
                            jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                            painelSimulador.add(jLEnd);
                            jLEnd.setBounds(455, 15, 100, 50);

                        }

                    } catch (Exception e) {
                        System.out.println("try:" + e.getMessage());
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

    private void fazendoTesteConexao() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            System.out.println("Verificando resultado: " + recebeConexao);
        } else {

            lblLinhaInformativa.setForeground(Color.red);
            lblLinhaInformativa.setText("SEM CONEXÃO COM A INTERNET OU ACESSO AO BANCO DE DADOS...");
            lblLinhaInformativa.setFont(f);
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
                            lblLinhaInformativa.setVisible(true);
                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 5%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect1.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 15) {

                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 15%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect2.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(45, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 25) {

                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 25%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));

                            jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect3.png")));
                            painelSimulador.add(jLTesteConexaoPositivo);
                            jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 35) {
                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 35%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jL40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect4.png")));
                            painelSimulador.add(jL40);
                            jL40.setBounds(135, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 45) {
                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 45%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jL50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect5.png")));
                            painelSimulador.add(jL50);
                            jL50.setBounds(185, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 55) {
                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 55%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jL60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconnect6.png")));
                            painelSimulador.add(jL60);
                            jL60.setBounds(235, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 65) {
                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 65%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jL70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect7.jpg")));
                            painelSimulador.add(jL70);
                            jL70.setBounds(290, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 75) {
                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 75%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jL80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect8.png")));
                            painelSimulador.add(jL80);
                            jL80.setBounds(345, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 85) {
                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 85%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jL90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect9.png")));
                            painelSimulador.add(jL90);
                            jL90.setBounds(400, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 95) {
                            lblLinhaInformativa.setText("Sem Conexão saindo sistema em 95%");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                            jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect10.png")));
                            painelSimulador.add(jLEnd);
                            jLEnd.setBounds(455, 15, 100, 50);

                        } else {
                            lblLinhaInformativa.setText("Encerrado com sucesso!");
                            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
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

    private void frontEnd() {
//        this.lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));

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

            ArrayList<ModeloMedicoEspecialidadeDTO> list;

            try {

                list = (ArrayList<ModeloMedicoEspecialidadeDTO>) modeloMedicoEspecialidadeDAO.filtrarTodosMedicosEspecialidades();

                Object rowData[] = new Object[5];//são 04 colunas 
                for (int i = 0; i < list.size(); i++) {

                    rowData[0] = list.get(i).getModMedicoDto().getIdDto();
                    rowData[1] = list.get(i).getModMedicoDto().getNomeDto().toString();
                    rowData[2] = list.get(i).getModMedicoDto().getConselhoDto().toString();
                    rowData[3] = list.get(i).getModEspecialidadeDto().getEspecialidadeDto().toString();
                    rowData[4] = list.get(i).getModMedicoDto().getCidadeDto().toString();
                    model.addRow(rowData);
                }

                tabela.setModel(model);

                /**
                 * Coluna ID posição[0] vetor
                 */
                tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
                tabela.getColumnModel().getColumn(2).setPreferredWidth(130);
                tabela.getColumnModel().getColumn(3).setPreferredWidth(130);
                tabela.getColumnModel().getColumn(4).setPreferredWidth(160);
            } catch (PersistenciaException ex) {
                JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FrmListaEstados \n"
                        + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                        + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());

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

        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtBuscar.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ModeloMedicoEspecialidadeDTO> list;

        try {

            list = (ArrayList<ModeloMedicoEspecialidadeDTO>) modeloMedicoEspecialidadeDAO.filtrarMedicoPornome(pesquisar);

            Object rowData[] = new Object[5];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getModMedicoDto().getIdDto();
                rowData[1] = list.get(i).getModMedicoDto().getNomeDto().toString();
                rowData[2] = list.get(i).getModMedicoDto().getConselhoDto().toString();
                rowData[3] = list.get(i).getModEspecialidadeDto().getEspecialidadeDto().toString();
                rowData[4] = list.get(i).getModMedicoDto().getCidadeDto().toString();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(130);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(130);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(160);
            personalizandoBarraInfoPesquisaTermino();
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormListaEstados \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelSimulador = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();

        setClosable(true);

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
        painelCabecalho.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 280, 30));

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
        painelCabecalho.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 32, 30));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "MÉDICO", "CONSELHO", "ESPECIALIDADE", "Cidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
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
            tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(250);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(130);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(130);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(160);
        }

        painelCabecalho.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 620, 400));

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSimulador.setLayout(null);

        lblLinhaInformativa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        painelSimulador.add(lblLinhaInformativa);
        lblLinhaInformativa.setBounds(520, 15, 170, 20);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(520, 35, 170, 24);

        painelCabecalho.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void criarListaComboEspecialidade() {

        int codigo = Integer.parseInt(lblIdMedico.getText());
        ArrayList<EspecialidadeDTO> list;
        try {

            list = (ArrayList<EspecialidadeDTO>) especialidadeDAO.buscarPorIdTblConsultaList(codigo);

            if (list.size() > 0) {
                cbEspecialidade.removeAllItems();

                for (int i = 0; i < list.size(); i++) {
                    lblRepositIDEspecialidade.setText(list.get(i).getIdDto().toString());
                    cbEspecialidade.addItem(list.get(i).getEspecialidadeDto());

                }

            } else {
                System.out.println("Não exite itens na lista de especialidades");

            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();

        }

    }


    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        int flagBtn = 4;
        progressBar(flagBtn);


    }//GEN-LAST:event_tabelaMouseClicked
    private void aoReceberFocoBtnPesquisar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(Color.BLUE);
        lblLinhaInformativa.setText("Pressione -->[ENTER]<-- Procurar -->[MYSQL]<--");
        buscarPorMedicos();
    }

    private void aoReceberFocoTxtBuscar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 9);//label informativo 
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

    private void buscarPorMedicos() {
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
            buscarPorMedicos();
        }
    }//GEN-LAST:event_btnPesquisarKeyPressed

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        int flagBtn = 1;
        progressBar(flagBtn);

    }//GEN-LAST:event_btnPesquisarFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
