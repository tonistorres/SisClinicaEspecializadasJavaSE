package br.com.multclin.telas;

import br.com.multclin.bo.PacienteBO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.PacienteDAO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.PacienteDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaAgendamento.btnLancar;
import static br.com.multclin.telas.TelaAgendamento.btnPesqMedico;
import static br.com.multclin.telas.TelaAgendamento.btnPesqProcede;
import static br.com.multclin.telas.TelaAgendamento.cbEspecialidade;
import static br.com.multclin.telas.TelaAgendamento.jDateTxtDtAgenda;
import static br.com.multclin.telas.TelaAgendamento.lblIdMedico;
import static br.com.multclin.telas.TelaAgendamento.lblRepositBairro;
import static br.com.multclin.telas.TelaAgendamento.lblRepositCPFPaciente;
import static br.com.multclin.telas.TelaAgendamento.lblRepositCelular;
import static br.com.multclin.telas.TelaAgendamento.lblRepositCidade;
import static br.com.multclin.telas.TelaAgendamento.lblRepositConjuge;
import static br.com.multclin.telas.TelaAgendamento.lblRepositDtNascimento;
import static br.com.multclin.telas.TelaAgendamento.lblRepositEstadoCivil;
import static br.com.multclin.telas.TelaAgendamento.lblRepositIdPaciente;
import static br.com.multclin.telas.TelaAgendamento.lblRepositMae;
import static br.com.multclin.telas.TelaAgendamento.lblRepositNomeMedico;
import static br.com.multclin.telas.TelaAgendamento.lblRepositPaciente;
import static br.com.multclin.telas.TelaAgendamento.lblRepositPai;
import static br.com.multclin.telas.TelaAgendamento.lblRepositRua;
import static br.com.multclin.telas.TelaAgendamento.lblRepositSexo;
import static br.com.multclin.telas.TelaAgendamento.lblRepositUF;
//import static br.com.multclin.telas.TelaAgendamento.txtAreaObserva;
import static br.com.multclin.telas.TelaAgendamento.txtRsDesconto;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class FrmListaPaciente extends javax.swing.JInternalFrame {

    Font f = new Font("Tahoma", Font.BOLD, 12);
    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();
    PacienteBO medicoBO = new PacienteBO();
    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

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
    private static FrmListaPaciente instance = null;

    public static FrmListaPaciente getInstance() {

        if (instance == null) {

            instance = new FrmListaPaciente();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public FrmListaPaciente() {

        initComponents();
        aoCarregar();
        frontEnd();

        int flagBtn = 3;
        progressBar(flagBtn);
    }

    private void buscarDadosNuvem() {

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
                         //Não precisa fazer teste de conexão pois no momento que é chamado esse formulario já é feita uma    
                            //    fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            switch (flagC) {

                                case 1: {

                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);
                                    acaoBotaoPesquisar();

                                    break;
                                }

                                case 2: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

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

                                    break;
                                }

                                case 3: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    buscarDadosNuvem();
                                    break;
                                }

                                case 4: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);
                                    acaoBotaoPesquisarPorCPF();
                                    break;
                                }

                                case 5: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    txtFormatedPesquisaCPF.requestFocus();
                                    txtFormatedPesquisaCPF.setBackground(Color.YELLOW);
                                    txtFormatedPesquisaCPF.setForeground(Color.BLACK);

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

                                    break;
                                }

                                case 6: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    btnPesquisaCPF.requestFocus();
                                    lblLinhaInformativa.setText("");
                                    lblLinhaInformativa.setText("fazendo pesquisa no MYSQL");
                                    lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                                    lblLinhaInformativa.setFont(f);

                                    int linha = tabela.getSelectedRow();
                                    String pesquisarCPF = MetodoStaticosUtil.removerAcentosCaixAlta(tabela.getValueAt(linha, 2).toString());
                                    ArrayList<PacienteDTO> list;
                                    try {

                                        lblRepositIdPaciente.setText(tabela.getValueAt(linha, 0).toString());
                                        lblRepositPaciente.setText(tabela.getValueAt(linha, 1).toString());

                                        list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarPesqRapidaPorCPF(pesquisarCPF);
                                        if (list.size() > 0) {

                                            jDateTxtDtAgenda.setEnabled(true);
                                            // txtAreaObserva.setEnabled(true);
                                            btnPesqProcede.setEnabled(true);
                                            btnLancar.setEnabled(true);
                                            txtRsDesconto.setEnabled(true);

                                            for (PacienteDTO list1 : list) {
                                                lblRepositDtNascimento.setText(list1.getDataNascDto());
                                                lblRepositSexo.setText(list1.getSexoDto());
                                                lblRepositEstadoCivil.setText(list1.getEstadoCivilDto());
                                                lblRepositConjuge.setText(list1.getConjugeDto());
                                                lblRepositMae.setText(list1.getMaeDto());
                                                lblRepositPai.setText(list1.getPaiDto());
                                                lblRepositCelular.setText(list1.getCelularPrefDto());
                                                lblRepositUF.setText(list1.getUfDto());
                                                lblRepositCidade.setText(list1.getCidadeDto());
                                                lblRepositBairro.setText(list1.getBairroDto());
                                                lblRepositRua.setText(list1.getRuaDto());
                                                lblRepositCPFPaciente.setText(list1.getCpfDto());
                                            }

                                        } else {
                                            System.out.println("Não exite itens na lista de especialidades");

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        e.getMessage();
                                    }

                                    jDateTxtDtAgenda.requestFocus();
                                    dispose();

                                    break;
                                }

                                case 7: {
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

        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao) {
            System.out.println("Verificando resultado: " + recebeConexao);
        } else {

            acaoSairDoSistemaFormLocal();
            //System.exit(0);

        }

    }

    private void aoCarregar() {

        txtPesquisa.setBackground(Color.YELLOW);
        barraProgresso.setVisible(false);
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
        txtPesquisa.requestFocus();

    }

    private void desabilitarCampos() {
        txtPesquisa.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {

        btnPesquisar.setEnabled(false);
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

    public void addRowJTable() {

        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            ArrayList<PacienteDTO> list;

            try {

                list = (ArrayList<PacienteDTO>) pacienteDAO.listarTodos();

                Object rowData[] = new Object[5];//são 04 colunas 
                for (int i = 0; i < list.size(); i++) {
                    rowData[0] = list.get(i).getIdDto();
                    rowData[1] = list.get(i).getNomeDto();
                    rowData[2] = list.get(i).getCpfDto();
                    rowData[3] = list.get(i).getCidadeDto();
                    rowData[4] = list.get(i).getBairroDto();
                    model.addRow(rowData);
                }

                tabela.setModel(model);
                tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
                tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
                tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
                tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
                tabela.getColumnModel().getColumn(4).setPreferredWidth(180);

            } catch (PersistenciaException ex) {

                System.out.println("Erro:Método addRowTable()" + ex.getMessage());

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

        String pesquisar = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarPesqRapida(pesquisar);
            Object rowData[] = new Object[5];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCpfDto();
                rowData[3] = list.get(i).getCidadeDto();
                rowData[4] = list.get(i).getBairroDto();
                model.addRow(rowData);
            }

            tabela.setModel(model);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(180);

            personalizandoBarraInfoPesquisaTermino();
        } catch (PersistenciaException ex) {
            System.out.println("Método CampoPesquisar()" + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        lblPesNome = new javax.swing.JLabel();
        txtFormatedPesquisaCPF = new javax.swing.JFormattedTextField();
        btnPesquisaCPF = new javax.swing.JButton();
        lblCPF = new javax.swing.JLabel();
        painelSimulador = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();

        setClosable(true);
        setTitle("Paciente ");

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
        painelCabecalho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "PACIENTE", "CPF", "CIDADE", "BAIRRO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(180);
        }

        painelCabecalho.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 680, 340));

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
        jPanel1.add(txtPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 260, 35));

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
        jPanel1.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 32, 32));

        lblPesNome.setBackground(new java.awt.Color(0, 102, 102));
        lblPesNome.setForeground(new java.awt.Color(0, 102, 102));
        lblPesNome.setText("Nome:");
        jPanel1.add(lblPesNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        try {
            txtFormatedPesquisaCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFormatedPesquisaCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFormatedPesquisaCPFFocusGained(evt);
            }
        });
        txtFormatedPesquisaCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFormatedPesquisaCPFKeyPressed(evt);
            }
        });
        jPanel1.add(txtFormatedPesquisaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 100, 30));

        btnPesquisaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesquisaCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisaCPFFocusGained(evt);
            }
        });
        btnPesquisaCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaCPFActionPerformed(evt);
            }
        });
        jPanel1.add(btnPesquisaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 30, 30));

        lblCPF.setForeground(new java.awt.Color(0, 102, 102));
        lblCPF.setText("CPF:");
        jPanel1.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        painelCabecalho.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 690, 90));

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
            .addComponent(painelCabecalho, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void criarListaComboEspecialidade() {

        int codigo = Integer.parseInt(lblIdMedico.getText());
        ArrayList<EspecialidadeDTO> list;
        try {

            list = (ArrayList<EspecialidadeDTO>) especialidadeDAO.buscarPorIdTblConsultaList(codigo);

            if (list.size() > 0) {
                cbEspecialidade.removeAllItems();

                for (int i = 0; i < list.size(); i++) {

                    cbEspecialidade.addItem(list.get(i).getEspecialidadeDto());
                    btnPesqMedico.requestFocus();
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
        int flagBtn = 6;
        progressBar(flagBtn);


    }//GEN-LAST:event_tabelaMouseClicked

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

                int flagBtn = 2;
                progressBar(flagBtn);

            }

        }

        if (!txtPesquisa.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                btnPesquisar.requestFocus();

//                int flagBtn = 3;
//                progressBar(flagBtn);
            }
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void pesquisarUsuario() {
        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarPesqRapida(pesquisarUsuario);

               Object rowData[] = new Object[5];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCpfDto();
                rowData[3] = list.get(i).getCidadeDto();
                rowData[4] = list.get(i).getBairroDto();
                model.addRow(rowData);
            }

            tabela.setModel(model);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(180);

         

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }


    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        int flagBtn = 1;
        progressBar(flagBtn);

    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarFocusLost

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed


    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtFormatedPesquisaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedPesquisaCPFFocusGained
        txtFormatedPesquisaCPF.setBackground(new Color(0, 102, 102));
        txtFormatedPesquisaCPF.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtFormatedPesquisaCPFFocusGained

    private void txtFormatedPesquisaCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedPesquisaCPFKeyPressed

        if (txtFormatedPesquisaCPF.getText().equalsIgnoreCase("   .   .   -  ")) {

            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                int flagBtn = 5;
                progressBar(flagBtn);

            }
        }

        if (!txtFormatedPesquisaCPF.getText().equalsIgnoreCase("   .   .   -  ")) {

            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                btnPesquisaCPF.requestFocus();

            }
        }

    }//GEN-LAST:event_txtFormatedPesquisaCPFKeyPressed

    private void btnPesquisaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisaCPFFocusGained
        btnPesquisaCPF.setBackground(new Color(0, 102, 102));

        int flagBtn = 4;
        progressBar(flagBtn);


    }//GEN-LAST:event_btnPesquisaCPFFocusGained

    private void btnPesquisaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesquisaCPFActionPerformed

    private void acaoBotaoPesquisarPorCPF() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisarUsuarioPorCPF();

        } else {
            addRowJTable();
        }

//        lblLinhaInformativa.setText("Fim da Pesquisa.");
//        lblLinhaInformativa.setFont(f);
//        lblLinhaInformativa.setForeground(Color.ORANGE);
    }

    private void pesquisarUsuarioPorCPF() {
        String pesquisarUsuarioCPF = MetodoStaticosUtil.removerAcentosCaixAlta(txtFormatedPesquisaCPF.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarPesqRapidaPorCPF(pesquisarUsuarioCPF);
   Object rowData[] = new Object[5];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCpfDto();
                rowData[3] = list.get(i).getCidadeDto();
                rowData[4] = list.get(i).getBairroDto();
                model.addRow(rowData);
            }

            tabela.setModel(model);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(45);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(180);

         

            //coloca o cursor em modo de espera 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void aoReceberFocoBtnPesquisar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 22);//label informativo 
//        lblLinhaInformativa.setText("");
//        lblLinhaInformativa.setForeground(Color.BLUE);
//        lblLinhaInformativa.setText("Pressione -->[ENTER]<-- Procurar -->[MYSQL]<--");

    }

    private void aoReceberFocoTxtBuscar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 14);//label informativo 
//        lblLinhaInformativa.setText("");
//        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
//        lblLinhaInformativa.setText("Digite o Registro -->[ENTER]<--");
    }

    private void personalizandoBarraInfoPesquisaTermino() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 14);//label informativo 
//        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
//        lblLinhaInformativa.setText("Pesquisa Terminada.");
        txtPesquisa.requestFocus();

    }

    private void acaoBotaoPesquisar() {

//        progressBarraPesquisa.setIndeterminate(true);
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

    private void buscarEstados() {
        txtPesquisa.requestFocus();
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);

        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoBotaoPesquisar();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnPesquisaCPF;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblPesNome;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JTable tabela;
    private javax.swing.JFormattedTextField txtFormatedPesquisaCPF;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
