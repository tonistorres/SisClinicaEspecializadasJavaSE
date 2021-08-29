package br.com.multclin.telas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dao.MedicoDAO;
import br.com.multclin.dao.ModeloAgendamentoItemProceDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.MedicoDTO;
import br.com.multclin.dto.ModeloAgendamentoItemProcedeDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TelaCosultarMovimento extends javax.swing.JInternalFrame {

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());
    Font f = new Font("Tahoma", Font.BOLD, 12);

    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    AgendamentoBO agendamentoBO = new AgendamentoBO();

    MedicoDTO medicoDTO = new MedicoDTO();
    MedicoDAO medicoDAO = new MedicoDAO();

    ModeloAgendamentoItemProcedeDTO modeloAgendamentoItemProcedeDTO = new ModeloAgendamentoItemProcedeDTO();
    ModeloAgendamentoItemProceDAO modeloAgendamentoItemProceDAO = new ModeloAgendamentoItemProceDAO();

    ConexaoUtil conecta = new ConexaoUtil();

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

    public TelaCosultarMovimento() {
        initComponents();
        frontEnd();
        aoCarregar();

    }

    public void listarCombo() {

        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            ArrayList<MedicoDTO> list;

            try {

                list = (ArrayList<MedicoDTO>) medicoDAO.listarTodos();

                cbProfissionalSaude.removeAllItems();
                for (int i = 0; i < list.size(); i++) {

                    cbProfissionalSaude.addItem(list.get(i).getNomeDto());

                }
                //cbProfissionalSaude.setSelectedItem("ALTO ALEGRE DO MARANHAO");
            } catch (PersistenciaException ex) {
                ex.printStackTrace();
            }

        } else {
            System.out.println("sem conexão internet método listarCombo()");

        }

    }

    private void aoCarregar() {
        barraProgresso.setVisible(false);
        lblLinhaInformativa.setVisible(false);

    }

    private void frontEnd() {
        this.btnDataPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        this.btnExtratoProfissionalSaude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));

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
        tabela.setRowHeight(25);

    }

    public void FiltroParaCaixaClinica() throws ParseException {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ModeloAgendamentoItemProcedeDTO> list;

        try {

            //capturando componentes do tipo JDateChooser (Date) e convertendo em String 
            String dtInicial = ((JTextField) jDataInicial.getDateEditor().getUiComponent()).getText();
            String dtFinal = ((JTextField) jDataFinal.getDateEditor().getUiComponent()).getText();
            //Agora convertendo a data de String <--> para Date
            Date dtInicialConvertida = formatador.parse(dtInicial);
            Date dtFinalConvertida = formatador.parse(dtFinal);
            //capturando o valor do status setado 
            String parametroPesquisa = (String) cbStatus.getSelectedItem();

            list = (ArrayList<ModeloAgendamentoItemProcedeDTO>) modeloAgendamentoItemProceDAO.filtrarCaixa(new java.sql.Date(dtInicialConvertida.getTime()), new java.sql.Date(dtFinalConvertida.getTime()), parametroPesquisa);

            Object rowData[] = new Object[9];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getModAgendamentoDto().getIdDto();
                rowData[1] = list.get(i).getModAgendamentoDto().getNomeDto();
                rowData[2] = formatador.format(list.get(i).getModAgendamentoDto().getDataAgendamentoDto());
                rowData[3] = list.get(i).getModAgendamentoDto().getNomeMedicoDto();
                rowData[4] = moeda.format(list.get(i).getModProcedeDto().getRsBrutoDto());
                rowData[5] = moeda.format(list.get(i).getModProcedeDto().getRsClinicaDto());
                rowData[6] = moeda.format((list.get(i).getModProcedeDto().getRsBrutoDto()) - (list.get(i).getModProcedeDto().getRsClinicaDto()));
                rowData[7] = list.get(i).getModAgendamentoDto().getStatusAgendamentoDto();
                rowData[8] = list.get(i).getModItemDto().getNomeProcedeDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);//nome paciente 
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);//data agendamento
            tabela.getColumnModel().getColumn(3).setPreferredWidth(180);//dtAgendamento
            tabela.getColumnModel().getColumn(4).setPreferredWidth(100);//valor total
            tabela.getColumnModel().getColumn(5).setPreferredWidth(100);//valor total
            tabela.getColumnModel().getColumn(6).setPreferredWidth(100);//valor total
            tabela.getColumnModel().getColumn(7).setPreferredWidth(100);//valor total
            tabela.getColumnModel().getColumn(8).setPreferredWidth(130);//valor total

            somaTotalProcede();//somatorio total dos procedimentos
            somaTotalClinica();//somatorio total da clinica 
            somaTotalTerceiro();//subtração de valor total bruto - valor total clinica 
            contarProcede();//somatorio total dos registros 

        } catch (PersistenciaException ex) {
            ex.printStackTrace();
        }

    }

    public void FiltroExtratoCaixaClinicaMedico() throws ParseException {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ModeloAgendamentoItemProcedeDTO> list;

        try {

            //capturando componentes do tipo JDateChooser (Date) e convertendo em String 
            String dtInicial = ((JTextField) jDataInicial.getDateEditor().getUiComponent()).getText();
            String dtFinal = ((JTextField) jDataFinal.getDateEditor().getUiComponent()).getText();
            //Agora convertendo a data de String <--> para Date
            Date dtInicialConvertida = formatador.parse(dtInicial);
            Date dtFinalConvertida = formatador.parse(dtFinal);
            //capturando o valor do status setado 
            String parametroPesquisa = (String) cbStatus.getSelectedItem();
            String nomeMedico = (String) cbProfissionalSaude.getSelectedItem();

            list = (ArrayList<ModeloAgendamentoItemProcedeDTO>) modeloAgendamentoItemProceDAO.filtrarCaixaExtratoMedico(new java.sql.Date(dtInicialConvertida.getTime()), new java.sql.Date(dtFinalConvertida.getTime()), parametroPesquisa, nomeMedico);

            Object rowData[] = new Object[9];

            for (int i = 0; i < list.size(); i++) {
              //  JOptionPane.showMessageDialog(null, "Procedimeto:"+list.get(i).getModItemDto().getNomeProcedeDto()+"Bruto :" + list.get(i).getModProcedeDto().getRsBrutoDto());
                rowData[0] = list.get(i).getModAgendamentoDto().getIdDto();
                rowData[1] = list.get(i).getModAgendamentoDto().getNomeDto();
                rowData[2] = formatador.format(list.get(i).getModAgendamentoDto().getDataAgendamentoDto());
                rowData[3] = list.get(i).getModAgendamentoDto().getNomeMedicoDto();
                rowData[4] = moeda.format(list.get(i).getModProcedeDto().getRsBrutoDto());
                rowData[5] = moeda.format(list.get(i).getModProcedeDto().getRsClinicaDto());
                rowData[6] = moeda.format(list.get(i).getModProcedeDto().getRsMedicoDto());
                rowData[7] = list.get(i).getModAgendamentoDto().getStatusAgendamentoDto();
                rowData[8] = list.get(i).getModProcedeDto().getNomeDto();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);//nome paciente 
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);//data agendamento
            tabela.getColumnModel().getColumn(3).setPreferredWidth(180);//dtAgendamento
            tabela.getColumnModel().getColumn(4).setPreferredWidth(100);//valor total
            tabela.getColumnModel().getColumn(5).setPreferredWidth(100);//valor total
            tabela.getColumnModel().getColumn(6).setPreferredWidth(100);//valor total
            tabela.getColumnModel().getColumn(7).setPreferredWidth(100);//valor total
            tabela.getColumnModel().getColumn(8).setPreferredWidth(130);//valor total

            somaTotalProcede();//somatorio total dos procedimentos
            somaTotalClinica();//somatorio total da clinica 
            somaTotalTerceiro();//subtração de valor total bruto - valor total clinica 
            contarProcede();//somatorio total dos registros 
            preenchendoExtrato();//preencher extrato Envio médico
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
        }

    }

    private void progressBar(int flagC) {
        //***************
        //PROGRESS BAR //
        //***********************************************************************
        //Contribuição Canal Yoube:https://www.youtube.com/watch?v=co2cITXfXqY//
        //**********************************************************************

        new Thread() {

            public void run() {

                lblLinhaInformativa.setVisible(true);
                barraProgresso.setVisible(true);
                jLEnd.setIcon(null);

                for (int i = 0; i < 101; i++) {

                    try {
                        //sleep padrão é 100    
                        sleep(100);

                        //recebe o parametro {i) do laço de repetição for
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() == 1) {
                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 10% a barra de
                             * progresso inicia as configurações inicais
                             * futuramentes podemos fazer testes de redes...
                             *
                             */

                            lblLinhaInformativa.setText("1% Inicializando barra de progresso");

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/systems.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);
                            jDataInicial.setEnabled(false);
                            jDataFinal.setEnabled(false);
                            cbStatus.setEnabled(false);
                            btnDataPesquisa.setEnabled(false);
                            cbProfissionalSaude.setEnabled(false);
                            btnExtratoProfissionalSaude.setEnabled(false);

                        } else if (barraProgresso.getValue() == 20) {

                            lblLinhaInformativa.setText("20% Executando teste de Conexão núvem... ");
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/hostgator.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(45, 15, 50, 50);

                            fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            switch (flagC) {

                                case 1: {

                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);
                                    //Buscar pelo CPF

                                    break;
                                }

                                case 2: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Atualizando lista...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/updateLista.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    break;
                                }

                                case 3: {
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    acaoBuscarPorJDateChooserFiltro();
                                    break;
                                }

                                case 4: {
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    acaoBuscarExtratoMedico();
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
                            jL70.setBounds(5, 55, 50, 50);
                        } else if (barraProgresso.getValue() == 80) {
                            lblLinhaInformativa.setText("80% linha temporal...");
                            jL80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar80.png")));
                            painelSimulador.add(jL80);
                            jL80.setBounds(45, 55, 50, 50);
                        } else if (barraProgresso.getValue() == 90) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            lblLinhaInformativa.setText("90% teste linha temporal...");
                            jL90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar90.png")));
                            painelSimulador.add(jL90);
                            jL90.setBounds(85, 55, 50, 50);

                        } else if (barraProgresso.getValue() == 100) {

                            jDataInicial.setEnabled(true);
                            jDataFinal.setEnabled(true);
                            cbStatus.setEnabled(true);
                            btnDataPesquisa.setEnabled(true);

                            cbProfissionalSaude.setEnabled(true);
                            btnExtratoProfissionalSaude.setEnabled(true);

                            limpaTodosIconesLinhaTemporal();

                            lblLinhaInformativa.setText("100% Fim da execução...");
                            jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                            painelSimulador.add(jLEnd);
                            jLEnd.setBounds(145, 65, 100, 50);

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
            dispose();
            acaoSairDoSistema();
        }

    }

    private void acaoSairDoSistema() {

        new Thread() {

            public void run() {

                for (int i = 0; i < 101; i++) {

                    try {

                        sleep(25);
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() <= 5) {

                            barraProgresso.setVisible(true);
                            lblVerificacao.setVisible(true);
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 15) {
                            lblVerificacao.setText("15% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 25) {

                            lblVerificacao.setText("25% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 35) {
                            lblVerificacao.setText("35% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 45) {
                            lblVerificacao.setText("45% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 55) {
                            lblVerificacao.setText("55% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 65) {
                            lblVerificacao.setText("65% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 75) {
                            lblVerificacao.setText("75% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 85) {
                            lblVerificacao.setText("85% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else if (barraProgresso.getValue() <= 95) {
                            lblVerificacao.setText("95% Descarregado");
                            lblVerificacao.setForeground(new Color(0, 102, 102));

                        } else {
                            lblVerificacao.setText("Encerrado com sucesso!");
                            lblVerificacao.setForeground(new Color(0, 102, 102));
                            System.exit(0);//sair do sistema
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                    }

                }
            }
        }.start();// iniciando a Thread

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelPesquisaPorPeriodo = new javax.swing.JPanel();
        lblDataInicial = new javax.swing.JLabel();
        lblDataFinal = new javax.swing.JLabel();
        lblStatusDaPesquisa = new javax.swing.JLabel();
        jDataInicial = new com.toedter.calendar.JDateChooser();
        jDataFinal = new com.toedter.calendar.JDateChooser();
        cbStatus = new javax.swing.JComboBox();
        btnDataPesquisa = new javax.swing.JButton();
        cbProfissionalSaude = new javax.swing.JComboBox();
        btnExtratoProfissionalSaude = new javax.swing.JButton();
        lblProfSaude = new javax.swing.JLabel();
        painelSimulador = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();
        painelValorTotal = new javax.swing.JPanel();
        lblValorTotalRSBruto = new javax.swing.JLabel();
        painelValorTotal1 = new javax.swing.JPanel();
        lblSomatorioClinica = new javax.swing.JLabel();
        painelValorTotal2 = new javax.swing.JPanel();
        lblPagTerceiros = new javax.swing.JLabel();
        painelValorTotal3 = new javax.swing.JPanel();
        lblNRegistros = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaExtratoSimplesConferencia = new javax.swing.JTextArea();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdAg", "Paciente", "Data", "Medico", "R$ BRUTO", "R$ CLINICA", "R$ TERCEIRO", "status", "Procede"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
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
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(6).setResizable(false);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(110);
            tabela.getColumnModel().getColumn(7).setResizable(false);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(8).setResizable(false);
            tabela.getColumnModel().getColumn(8).setPreferredWidth(130);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 800, 140));

        painelPesquisaPorPeriodo.setBackground(java.awt.Color.white);
        painelPesquisaPorPeriodo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa por Período:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPesquisaPorPeriodo.setLayout(null);

        lblDataInicial.setForeground(new java.awt.Color(0, 102, 102));
        lblDataInicial.setText("Data Inicial:");
        painelPesquisaPorPeriodo.add(lblDataInicial);
        lblDataInicial.setBounds(30, 30, 100, 14);

        lblDataFinal.setForeground(new java.awt.Color(0, 102, 102));
        lblDataFinal.setText("Data Final:");
        painelPesquisaPorPeriodo.add(lblDataFinal);
        lblDataFinal.setBounds(30, 90, 100, 14);

        lblStatusDaPesquisa.setForeground(new java.awt.Color(0, 102, 102));
        lblStatusDaPesquisa.setText("Status da Pesquisa:");
        painelPesquisaPorPeriodo.add(lblStatusDaPesquisa);
        lblStatusDaPesquisa.setBounds(170, 30, 120, 20);

        jDataInicial.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        painelPesquisaPorPeriodo.add(jDataInicial);
        jDataInicial.setBounds(30, 50, 120, 30);

        jDataFinal.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        painelPesquisaPorPeriodo.add(jDataFinal);
        jDataFinal.setBounds(30, 110, 120, 30);

        cbStatus.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "AGENDADO", "CANCELADO", "CAIXA", "REAGENDADO", " " }));
        cbStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        cbStatus.setOpaque(false);
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });
        painelPesquisaPorPeriodo.add(cbStatus);
        cbStatus.setBounds(170, 50, 200, 30);

        btnDataPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPesquisaActionPerformed(evt);
            }
        });
        painelPesquisaPorPeriodo.add(btnDataPesquisa);
        btnDataPesquisa.setBounds(370, 50, 30, 30);

        cbProfissionalSaude.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbProfissionalSaude.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPesquisaPorPeriodo.add(cbProfissionalSaude);
        cbProfissionalSaude.setBounds(170, 110, 200, 30);

        btnExtratoProfissionalSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExtratoProfissionalSaudeActionPerformed(evt);
            }
        });
        painelPesquisaPorPeriodo.add(btnExtratoProfissionalSaude);
        btnExtratoProfissionalSaude.setBounds(370, 110, 30, 30);

        lblProfSaude.setForeground(new java.awt.Color(0, 102, 102));
        lblProfSaude.setText("Profissional de Saúde:");
        painelPesquisaPorPeriodo.add(lblProfSaude);
        lblProfSaude.setBounds(170, 90, 130, 14);

        jPanel1.add(painelPesquisaPorPeriodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 420, 180));

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSimulador.setLayout(null);

        lblLinhaInformativa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        painelSimulador.add(lblLinhaInformativa);
        lblLinhaInformativa.setBounds(10, 112, 320, 20);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(10, 135, 360, 24);

        jPanel1.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 380, 180));

        painelValorTotal.setBackground(java.awt.Color.white);
        painelValorTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Somatório Bruto:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        painelValorTotal.setLayout(null);

        lblValorTotalRSBruto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblValorTotalRSBruto.setForeground(new java.awt.Color(0, 102, 102));
        lblValorTotalRSBruto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelValorTotal.add(lblValorTotalRSBruto);
        lblValorTotalRSBruto.setBounds(9, 20, 150, 50);

        jPanel1.add(painelValorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 190, 180, 90));

        painelValorTotal1.setBackground(java.awt.Color.white);
        painelValorTotal1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Somatório Clinica:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        painelValorTotal1.setLayout(null);

        lblSomatorioClinica.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSomatorioClinica.setForeground(new java.awt.Color(0, 102, 102));
        lblSomatorioClinica.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelValorTotal1.add(lblSomatorioClinica);
        lblSomatorioClinica.setBounds(9, 20, 150, 50);

        jPanel1.add(painelValorTotal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 180, 90));

        painelValorTotal2.setBackground(java.awt.Color.white);
        painelValorTotal2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pagamento Terceiros:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        painelValorTotal2.setLayout(null);

        lblPagTerceiros.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblPagTerceiros.setForeground(new java.awt.Color(0, 102, 102));
        lblPagTerceiros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelValorTotal2.add(lblPagTerceiros);
        lblPagTerceiros.setBounds(9, 20, 150, 50);

        jPanel1.add(painelValorTotal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 290, 180, 90));

        painelValorTotal3.setBackground(java.awt.Color.white);
        painelValorTotal3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nº Registros:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        painelValorTotal3.setLayout(null);

        lblNRegistros.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNRegistros.setForeground(new java.awt.Color(0, 102, 102));
        lblNRegistros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelValorTotal3.add(lblNRegistros);
        lblNRegistros.setBounds(9, 20, 150, 50);

        jPanel1.add(painelValorTotal3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 180, 90));

        jScrollPane2.setBackground(java.awt.Color.white);

        txtAreaExtratoSimplesConferencia.setColumns(20);
        txtAreaExtratoSimplesConferencia.setRows(5);
        txtAreaExtratoSimplesConferencia.setBorder(null);
        jScrollPane2.setViewportView(txtAreaExtratoSimplesConferencia);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 420, 200));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 10, 830, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void preenchendoExtrato() {

        //limpa texto inseridos
        txtAreaExtratoSimplesConferencia.setText("");
        try {

            txtAreaExtratoSimplesConferencia.append("-----------------------------------------------------------------------------------\n");
            txtAreaExtratoSimplesConferencia.append("EXTRATO PARA SIMPLES CONFERÊNCIA (PROFISSIONAL DE SAUDE)\n");
            txtAreaExtratoSimplesConferencia.append("-----------------------------------------------------------------------------------\n");

            for (int i = 0; i < tabela.getRowCount(); i++) {
                txtAreaExtratoSimplesConferencia.append("Consulta:" + tabela.getValueAt(i, 2) + " Paciente:" + tabela.getValueAt(i, 1) + "\n");
                txtAreaExtratoSimplesConferencia.append("Profissional: " + tabela.getValueAt(i, 3) + " Pgto: " + tabela.getValueAt(i, 6) + " Procedimento: " + tabela.getValueAt(i, 8) + "\n");
                txtAreaExtratoSimplesConferencia.append("-----------------------------------------------------------------------------------\n");
            }

            txtAreaExtratoSimplesConferencia.append("Nº Atendimento(s): " + lblNRegistros.getText() + " Valor Pago: " + lblPagTerceiros.getText() + "\n");
            txtAreaExtratoSimplesConferencia.append("-----------------------------------------------------------------------------------\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void contarProcede() {

        try {

            int contador = 0;

            for (int i = 0; i < tabela.getRowCount(); i++) {

                contador++;
            }
            lblNRegistros.setText(String.valueOf(contador));

        } catch (Exception e) {
            e.printStackTrace();
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

            lblValorTotalRSBruto.setText(moeda.format(somaTotal));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void somaTotalTerceiro() {
        try {

            float somaTotal = 0, valorTotal;

            int contador = tabela.getRowCount();

            for (int i = 0; i < contador; i++) {

                String rsValorTratadoRSBruto = tabela.getValueAt(i, 4).toString().replace("R$", "").trim();
                String rsValorTratadoRSClinica = tabela.getValueAt(i, 5).toString().replace("R$", "").trim();

                valorTotal = (MetodoStaticosUtil.converteFloat(rsValorTratadoRSBruto) - MetodoStaticosUtil.converteFloat(rsValorTratadoRSClinica));

                somaTotal = somaTotal + valorTotal;
            }

            lblPagTerceiros.setText(moeda.format(somaTotal));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void somaTotalClinica() {
        try {

            float somaTotal = 0, valorTotal;

            int contador = tabela.getRowCount();

            for (int i = 0; i < contador; i++) {

                String rsValorTratado = tabela.getValueAt(i, 5).toString().replace("R$", "").trim();
                //            String rsDescontoTratado = tabela.getValueAt(i, 3).toString().replace("R$", "").trim();

                valorTotal = (MetodoStaticosUtil.converteFloat(rsValorTratado));

                somaTotal = somaTotal + valorTotal;
            }

            lblSomatorioClinica.setText(moeda.format(somaTotal));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void acaoBuscarPorJDateChooserFiltro() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            try {
                FiltroParaCaixaClinica();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

        } else {

            try {
                FiltroParaCaixaClinica();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

    }

    private void acaoBuscarExtratoMedico() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            try {
                FiltroExtratoCaixaClinicaMedico();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

        } else {

            try {
                FiltroExtratoCaixaClinicaMedico();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

    }


    private void btnDataPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataPesquisaActionPerformed

        if (jDataInicial.getDate() == null || jDataFinal.getDate() == null || cbStatus.getSelectedItem().equals("Selecione...")) {

            JOptionPane.showMessageDialog(this, "" + "\n Info.:\n"
                    + "Verifique se preencheu as Datas de Incio e Fim\n"
                    + "corretamente. Verifique se escolheu uma opção \n"
                    + "válida no Status da Pesquisa. "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } else {

            flagC = 3;
            progressBar(flagC);

        }


    }//GEN-LAST:event_btnDataPesquisaActionPerformed

    private void btnExtratoProfissionalSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExtratoProfissionalSaudeActionPerformed

        if (jDataInicial.getDate() == null || jDataFinal.getDate() == null || cbStatus.getSelectedItem().equals("Selecione...")) {

            JOptionPane.showMessageDialog(this, "" + "\n Info.:\n"
                    + "Verifique se preencheu as Datas de Incio e Fim\n"
                    + "corretamente. Verifique se escolheu uma opção \n"
                    + "válida no Status da Pesquisa. "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } else {

            flagC = 4;
            progressBar(flagC);

        }


    }//GEN-LAST:event_btnExtratoProfissionalSaudeActionPerformed

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        //capturando itens selecionado caixa combinação fluxo
        String itemSelecionado = (String) cbStatus.getSelectedItem();

        if (itemSelecionado.equalsIgnoreCase("CAIXA")
                || itemSelecionado.equalsIgnoreCase("AGENDAMENTO")
                || itemSelecionado.equalsIgnoreCase("REAGENDAMENTO")
                || itemSelecionado.equalsIgnoreCase("CANCELADO")) {

            listarCombo();

        }

    }//GEN-LAST:event_cbStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnDataPesquisa;
    private javax.swing.JButton btnExtratoProfissionalSaude;
    private javax.swing.JComboBox cbProfissionalSaude;
    private javax.swing.JComboBox cbStatus;
    private com.toedter.calendar.JDateChooser jDataFinal;
    private com.toedter.calendar.JDateChooser jDataInicial;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDataFinal;
    private javax.swing.JLabel lblDataInicial;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNRegistros;
    private javax.swing.JLabel lblPagTerceiros;
    private javax.swing.JLabel lblProfSaude;
    private javax.swing.JLabel lblSomatorioClinica;
    private javax.swing.JLabel lblStatusDaPesquisa;
    private javax.swing.JLabel lblValorTotalRSBruto;
    private javax.swing.JPanel painelPesquisaPorPeriodo;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JPanel painelValorTotal;
    private javax.swing.JPanel painelValorTotal1;
    private javax.swing.JPanel painelValorTotal2;
    private javax.swing.JPanel painelValorTotal3;
    private javax.swing.JTable tabela;
    private javax.swing.JTextArea txtAreaExtratoSimplesConferencia;
    // End of variables declaration//GEN-END:variables
}
