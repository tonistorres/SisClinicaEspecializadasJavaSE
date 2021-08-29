package br.com.multcin.forms.consultas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.telas.*;
import br.com.multclin.bo.FoneBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dao.FoneDAO;
import br.com.multclin.dao.PacienteDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.FoneDTO;
import br.com.multclin.dto.PacienteDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;

import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPaciente.txtID;
import static br.com.multclin.telas.TelaPaciente.txtCPFPaciente;
import static br.com.multclin.telas.TelaPaciente.txtNome;
import static br.com.multclin.telas.TelaPaciente.lblFonePreferencial;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;
import br.com.multclin.util.PINTAR_TABELA_CONSULTAR;

import com.placeholder.PlaceHolder;
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
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class ConsultarMovimento extends javax.swing.JInternalFrame {

    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());
    Font f = new Font("Tahoma", Font.BOLD, 12);

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    AgendamentoBO agendamentoBO = new AgendamentoBO();

    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();

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

    public ConsultarMovimento() {
        initComponents();
        aoCarregarForm();
        frontEnd();
        addRowJTable();

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
        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        tabela.setDefaultRenderer(Object.class, new PINTAR_TABELA_CONSULTAR());
    }

    private void aoCarregarForm() {

        lblLinhaInformativa.setVisible(false);
        barraProgresso.setVisible(false);

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
                            limpaTodosIconesLinhaTemporal();

                            lblLinhaInformativa.setText("1% Inicializando barra de progresso");

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/systems.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                            btnRefresh.setEnabled(false);
                            txtCPFPaciente.setEnabled(false);
                            btnValidaCPF.setEnabled(false);
                            jDataInicial.setEnabled(false);
                            jDataFinal.setEnabled(false);
                            cbStatus.setEnabled(false);
                            btnDataPesquisa.setEnabled(false);

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
                                    acaoBotaoPesquisarPorCPF();

                                    break;
                                }

                                case 2: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Atualizando lista...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/updateLista.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);
                                    metodoAtualizarLista();
                                    break;
                                }

                                case 3: {
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    pesquisarDatasPorPeriodo();
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

                            btnRefresh.setEnabled(true);
                            txtCPFPaciente.setEnabled(true);
                            btnValidaCPF.setEnabled(true);
                            jDataInicial.setEnabled(true);
                            jDataFinal.setEnabled(true);
                            cbStatus.setEnabled(true);
                            btnDataPesquisa.setEnabled(true);

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

    private void pesquisarDatasPorPeriodo() {

        String DataInicial = ((JTextField) jDataInicial.getDateEditor().getUiComponent()).getText();
        String DataFinal = ((JTextField) jDataFinal.getDateEditor().getUiComponent()).getText();

        if (!DataInicial.isEmpty() && !DataFinal.isEmpty()) {

            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
            if (recebeConexao == true) {

                JOptionPane.showMessageDialog(null, "Fiz o teste entrando em acaoBuscarPorJDateChooser();");
                acaoBuscarPorJDateChooser();

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "Info:\n"
                    + "Um dos campos de Data esta Nulo,\n"
                    + "no componente Calendário escolha\n"
                    + "uma Data Válida para DtInicial e\n"
                    + "uma Data Válida para DtFinal. \n"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            jDataInicial.setBackground(Color.YELLOW);
            jDataInicial.requestFocus();

        }

    }

    private void acaoBuscarPorJDateChooser() {

        //iniciando pesquisa no banco 
          acaoInicioDePesquisaBanco();
        JOptionPane.showMessageDialog(null, "acaoBuscarPorJDateChooser()");
        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }
            JOptionPane.showMessageDialog(null, "dentro try");
            pesquisarComJDateChooser();

        } else {
            JOptionPane.showMessageDialog(null, "dentro else");
            addRowJTable();
        }

    }

    
    private void pesquisarComJDateChooser(){
    
    }
    
    
//    private void pesquisarComJDateChooser() throws ParseException {
//
//        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
//
//        ArrayList<AgendamentoDTO> list;
//
//        try {
//
//            String dtInicial = ((JTextField) jDataInicial.getDateEditor().getUiComponent()).getText();
//            String dtFinal = ((JTextField) jDataFinal.getDateEditor().getUiComponent()).getText();
//
//            //Agora convertendo a data de String <--> para Date
//            Date dtInicialConvertida = formatador.parse(dtInicial);
//            Date dtFinalConvertida = formatador.parse(dtFinal);
//
//            String parametroParaPesquisa = (String) cbStatus.getSelectedItem();
//
//            JOptionPane.showMessageDialog(null, "Entrando no método listarTodosAgendadosStatus() ");
////            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosAgendadosStatus(new java.sql.Date(dtInicialConvertida.getTime()), new java.sql.Date(dtFinalConvertida.getTime()));
//
//            JOptionPane.showMessageDialog(null, "Tamanho Lista:" + list.size());
//            Object rowData[] = new Object[7];
//
//            for (int i = 0; i < list.size(); i++) {
//
//                //capturamos as datas das suas devidas fontes 
//                String DataInicial = ((JTextField) jDataInicial.getDateEditor().getUiComponent()).getText();
//                String DataFinal = ((JTextField) jDataFinal.getDateEditor().getUiComponent()).getText();
////                String dataAgendaBD = list.get(i).getDataAgendamentoDto();
//
//                //Convertendo as Datas de String para Date |String|--->>|Date|
//                Date dtInicialConvDate = formatador.parse(DataInicial);
//                Date dtFinalConvDate = formatador.parse(DataFinal);
//                //       Date dtBDConvDate = formatador.parse(dataAgendaBD);
//
////                if ((dtBDConvDate.getTime() >= dtInicialConvDate.getTime()) && (dtBDConvDate.getTime() <= dtFinalConvDate.getTime())) {
////
////                    rowData[0] = list.get(i).getIdDto();
////                    rowData[1] = list.get(i).getFk_pacienteDto();
////                    rowData[2] = list.get(i).getNomeDto();
////                    rowData[3] = list.get(i).getCelularPrefDto();
////               //     rowData[4] = dataAgendaBD;
////                    rowData[5] = moeda.format(list.get(i).getRsTotalAgendamentoDto());
////                    rowData[6] = list.get(i).getStatusAgendamentoDto();
////
////                    model.addRow(rowData);
////                }
//            }
//
//            tabela.setModel(model);
//
////            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
////            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//id
////            tabela.getColumnModel().getColumn(2).setPreferredWidth(160);//nome
////            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//celular
////            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//dtAgendamento
////            tabela.getColumnModel().getColumn(5).setPreferredWidth(80);//valor tota
////            tabela.getColumnModel().getColumn(6).setPreferredWidth(100);//valor tota
//            //soma valor total 
//            somaTotalProcede();
//            acaoFimDePesquisNoBanco();
//
//        } catch (PersistenciaException ex) {
//            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
//                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
//                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
//        }
//
//    }

    private void metodoAtualizarLista() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }
            addRowJTable();

        } else {
            addRowJTable();
        }

        txtCPFPaciente.requestFocus();
        txtCPFPaciente.setBackground(new Color(0, 102, 102));
        txtCPFPaciente.setForeground(Color.WHITE);
    }

    public void addRowJTable() {
        //Inicio da Pesquisa no banco 
     //   acaoInicioDePesquisaBanco();

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<AgendamentoDTO> list;

        try {

            //https://www.youtube.com/watch?v=jPfKFm2Yfow
           // tabela.setDefaultRenderer(Object.class, new PINTAR_TABELA_CONSULTAR());

            String paramentro = "AGENDADO";
            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosAgendados(paramentro);

            JOptionPane.showMessageDialog(null, "Tamanho:" + list.size());
            
            Object rowData[] = new Object[7];

            for (AgendamentoDTO lista : list) {
                rowData[0] =lista.getIdDto();
                rowData[1] = lista.getFk_pacienteDto();
                rowData[2] = lista.getNomeDto();
                rowData[3] = lista.getCelularPrefDto();
                rowData[4] = lista.getDataAgendamentoDto();
                rowData[5] = moeda.format(lista.getRsTotalAgendamentoDto());
                rowData[6] = lista.getStatusAgendamentoDto();
                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(280);//nome
            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//celular
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(5).setPreferredWidth(90);//valor total
            tabela.getColumnModel().getColumn(6).setPreferredWidth(100);//valor total

            //fazendo a soma total 
          //  somaTotalProcede();
            //fim da pesquisa 
           // acaoFimDePesquisNoBanco();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    /**
     * Soma todos os Valores Totais lançados na minha tabela venda
     * https://www.youtube.com/watch?v=ME_VgfjKvsQ&index=30&list=PLt2CbMyJxu8iQL67Am38O1j5wKLf0AIRZ
     */
    private void somaTotalProcede() {
        try {

            float somaTotal = 0, valorTotal;

            int contador = tabela.getRowCount();

            for (int i = 0; i < contador; i++) {

                String rsValorTratado = tabela.getValueAt(i, 5).toString().replace("R$", "").trim();
                //            String rsDescontoTratado = tabela.getValueAt(i, 3).toString().replace("R$", "").trim();

                valorTotal = (MetodoStaticosUtil.converteFloat(rsValorTratado));

                somaTotal = somaTotal + valorTotal;
            }

            lblValorTotalAgenda.setText(moeda.format(somaTotal));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void acaoBotaoPesquisarPorCPF() {

        //iniciando pesquisa no banco 
        acaoInicioDePesquisaBanco();

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisar();

        } else {
            addRowJTable();
        }

    }

    private void pesquisar() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<AgendamentoDTO> list;

        try {
            //https://www.youtube.com/watch?v=jPfKFm2Yfow
            tabela.setDefaultRenderer(Object.class, new PINTAR_TABELA_CONSULTAR());

            String parametroParaPesquisa = "AGENDADO";
            String cpf = txtCPFPaciente.getText();
            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosAgendadosCPF(parametroParaPesquisa, cpf);

            Object rowData[] = new Object[11];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getFk_pacienteDto();
                rowData[2] = list.get(i).getNomeDto();
                rowData[3] = list.get(i).getCelularPrefDto();
                rowData[4] = list.get(i).getDataAgendamentoDto();
                rowData[5] = moeda.format(list.get(i).getRsTotalAgendamentoDto());

                model.addRow(rowData);
            }

            tabela.setModel(model);

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

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(280);//nome
            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//celular
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(5).setPreferredWidth(90);//valor total

            //fazendo a soma total 
            somaTotalProcede();
            //fim da pesquisa 
            acaoFimDePesquisNoBanco();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void acaoInicioDePesquisaBanco() {

        //coloca o cursor em modo de espera 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

    }

    private void acaoFimDePesquisNoBanco() {
        //finalizar o modo de espera do cursor 
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    private void frontEnd() {
        this.btnDataPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        this.btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/refreshNuvem.png")));
        this.btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));

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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        painelPesquisaCPF = new javax.swing.JPanel();
        txtCPFPaciente = new javax.swing.JFormattedTextField();
        painelAtualizar = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        painelValorTotal = new javax.swing.JPanel();
        lblValorTotalAgenda = new javax.swing.JLabel();
        btnValidaCPF = new javax.swing.JButton();
        painelSimulador = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();

        setClosable(true);

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdAgen", "idParc", "Paciente", "Celular(ZAP)", "Data", "R$ ValorTotal", "status"
            }
        ));
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(6).setResizable(false);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(100);
        }

        painelPesquisaPorPeriodo.setBackground(java.awt.Color.white);
        painelPesquisaPorPeriodo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa por Período:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPesquisaPorPeriodo.setLayout(null);

        lblDataInicial.setForeground(new java.awt.Color(0, 102, 102));
        lblDataInicial.setText("Data Inicial:");
        painelPesquisaPorPeriodo.add(lblDataInicial);
        lblDataInicial.setBounds(10, 20, 100, 14);

        lblDataFinal.setForeground(new java.awt.Color(0, 102, 102));
        lblDataFinal.setText("Data Final:");
        painelPesquisaPorPeriodo.add(lblDataFinal);
        lblDataFinal.setBounds(140, 20, 100, 14);

        lblStatusDaPesquisa.setForeground(new java.awt.Color(0, 102, 102));
        lblStatusDaPesquisa.setText("Status da Pesquisa:");
        painelPesquisaPorPeriodo.add(lblStatusDaPesquisa);
        lblStatusDaPesquisa.setBounds(270, 20, 110, 14);

        jDataInicial.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        painelPesquisaPorPeriodo.add(jDataInicial);
        jDataInicial.setBounds(10, 40, 120, 30);

        jDataFinal.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        painelPesquisaPorPeriodo.add(jDataFinal);
        jDataFinal.setBounds(140, 40, 120, 30);

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "AGENDADO", "CANCELADO", "CAIXA", "REAGENDADO", " " }));
        cbStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        cbStatus.setOpaque(false);
        painelPesquisaPorPeriodo.add(cbStatus);
        cbStatus.setBounds(270, 40, 110, 30);

        btnDataPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataPesquisaActionPerformed(evt);
            }
        });
        painelPesquisaPorPeriodo.add(btnDataPesquisa);
        btnDataPesquisa.setBounds(390, 40, 30, 30);

        painelPesquisaCPF.setBackground(java.awt.Color.white);
        painelPesquisaCPF.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisar CPF:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPesquisaCPF.setLayout(null);

        try {
            txtCPFPaciente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPFPaciente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCPFPaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFPacienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCPFPacienteFocusLost(evt);
            }
        });
        txtCPFPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPFPacienteActionPerformed(evt);
            }
        });
        txtCPFPaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFPacienteKeyPressed(evt);
            }
        });
        painelPesquisaCPF.add(txtCPFPaciente);
        txtCPFPaciente.setBounds(10, 30, 140, 30);

        painelAtualizar.setBackground(java.awt.Color.white);
        painelAtualizar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atualizar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelAtualizar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnRefresh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnRefreshFocusGained(evt);
            }
        });
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        painelAtualizar.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 20, 45, 45));

        painelValorTotal.setBackground(java.awt.Color.white);
        painelValorTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "R$ Total:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        painelValorTotal.setLayout(null);

        lblValorTotalAgenda.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblValorTotalAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblValorTotalAgenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelValorTotal.add(lblValorTotalAgenda);
        lblValorTotalAgenda.setBounds(9, 20, 130, 50);

        btnValidaCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusGained(evt);
            }
        });

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSimulador.setLayout(null);

        lblLinhaInformativa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        painelSimulador.add(lblLinhaInformativa);
        lblLinhaInformativa.setBounds(10, 115, 290, 17);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(10, 135, 290, 24);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(btnValidaCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                .addComponent(painelSimulador, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(painelPesquisaPorPeriodo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(painelPesquisaCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(painelAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(painelValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnValidaCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelSimulador, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 270, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(painelPesquisaCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(painelAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(painelValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(painelPesquisaPorPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCPFPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFPacienteFocusGained
        txtCPFPaciente.setBackground(new Color(0, 102, 102));
        txtCPFPaciente.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtCPFPacienteFocusGained

    private void txtCPFPacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFPacienteFocusLost
        txtCPFPaciente.setBackground(Color.WHITE);
        txtCPFPaciente.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtCPFPacienteFocusLost

    private void txtCPFPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFPacienteActionPerformed

    private void txtCPFPacienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFPacienteKeyPressed

        if (!txtCPFPaciente.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                this.btnValidaCPF.requestFocus();

            }
        }
        if (txtCPFPaciente.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtCPFPaciente.requestFocus();
                txtCPFPaciente.setBackground(Color.YELLOW);
                txtCPFPaciente.setForeground(Color.BLACK);

                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "CPF Inválido"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }

        }


    }//GEN-LAST:event_txtCPFPacienteKeyPressed

    private void btnRefreshFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnRefreshFocusGained
        btnRefresh.setBackground(Color.YELLOW);

        flagC = 2;
        progressBar(flagC);
    }//GEN-LAST:event_btnRefreshFocusGained

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnDataPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataPesquisaActionPerformed

        pesquisarDatasPorPeriodo();

//
//        flagC = 3;
//        progressBar(flagC);
    }//GEN-LAST:event_btnDataPesquisaActionPerformed

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained

        flagC = 1;
        progressBar(flagC);

    }//GEN-LAST:event_btnValidaCPFFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnDataPesquisa;
    public static javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnValidaCPF;
    private javax.swing.JComboBox cbStatus;
    private com.toedter.calendar.JDateChooser jDataFinal;
    private com.toedter.calendar.JDateChooser jDataInicial;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDataFinal;
    private javax.swing.JLabel lblDataInicial;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblStatusDaPesquisa;
    private javax.swing.JLabel lblValorTotalAgenda;
    private javax.swing.JPanel painelAtualizar;
    private javax.swing.JPanel painelPesquisaCPF;
    private javax.swing.JPanel painelPesquisaPorPeriodo;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JPanel painelValorTotal;
    private javax.swing.JTable tabela;
    public static javax.swing.JFormattedTextField txtCPFPaciente;
    // End of variables declaration//GEN-END:variables
}
