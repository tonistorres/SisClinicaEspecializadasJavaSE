package br.com.multclin.telas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.bo.PacienteBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dao.EspecialidadeDAO;

import br.com.multclin.dao.HistoricoPacienteDAO;
import br.com.multclin.dao.PacienteDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.HistoricoPacienteDTO;
import br.com.multclin.dto.PacienteDTO;

import br.com.multclin.jbdc.ConexaoUtil;
import static br.com.multclin.telas.Modulo2AtendimentoConsultorio.btnRefresh;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.lblRepositNomeMedicoAgenda;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.save;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.txtAreaDiagnostico;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.txtAreaPrescricao;

import static br.com.multclin.telas.TelaPaciente.txtNome;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.lblStatusData;

import java.awt.Color;
import java.awt.Font;

import static java.lang.Thread.sleep;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import texteditor.TelaEditor;
import texteditor.TelaEditorConsulta;
import static texteditor.TelaEditor.tp_texto;
import static texteditor.TelaEditorConsulta.tp_textoConsulta;

/**
 *
 * @author Pessoal
 */
public class Modulo2HistoricoPacienteConsulta extends javax.swing.JInternalFrame {

    Font f = new Font("Tahoma", Font.BOLD, 11);

    texteditor.TelaEditor telaEditor;
    texteditor.TelaEditorConsulta telaEditorConsulta;

    HistoricoPacienteDTO historicoDTO = new HistoricoPacienteDTO();
    HistoricoPacienteDAO historicoDAO = new HistoricoPacienteDAO();

    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    AgendamentoBO agendamentoBO = new AgendamentoBO();

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

    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();
    PacienteBO medicoBO = new PacienteBO();
    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

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

    public Modulo2HistoricoPacienteConsulta() {
        initComponents();
        frontEnd();
        aoCarregar();
    }

    private void aoCarregar() {
        barraProgresso.setVisible(false);

        int flagBtn = 1;
        progressBar(flagBtn);

    }

    //***************
    //PROGRESS BAR //
    //***********************************************************************
    //Contribuição Canal Yoube:https://www.youtube.com/watch?v=co2cITXfXqY//
    //**********************************************************************
    private void progressBar(int flagC) {

        new Thread() {

            public void run() {

                barraProgresso.setVisible(true);

                jLEnd.setIcon(null);
                limpaTodosIconesLinhaTemporal();
                btnRefresh.setEnabled(false);

                for (int i = 0; i < 101; i++) {

                    try {

                        sleep(25);
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() == 10) {

                            //preto quase branco 
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/systems.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 20) {

                            //preto quase branco 
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/hostgator.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(45, 15, 50, 50);

                            fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/updateLista.png")));
                            painelSimulador.add(jLTesteConexaoPositivo);
                            jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 40) {

                            //preto quase branco 
                            jL40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar40.png")));
                            painelSimulador.add(jL40);
                            jL40.setBounds(135, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 50) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 

                            //preto quase branco 
                            jL50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar50.png")));
                            painelSimulador.add(jL50);
                            jL50.setBounds(185, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 60) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 

                            //preto quase branco 
                            jL60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar60.png")));
                            painelSimulador.add(jL60);
                            jL60.setBounds(235, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 70) {

                            //preto quase branco 
                            jL70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar70.png")));
                            painelSimulador.add(jL70);
                            jL70.setBounds(290, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 80) {

                            jL80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar80.png")));
                            painelSimulador.add(jL80);
                            jL80.setBounds(345, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 90) {

                            jL90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar90.png")));
                            painelSimulador.add(jL90);
                            jL90.setBounds(400, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 100) {

                            switch (flagC) {

                                case 1: {

                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);
                                    //carregar dados do paciente 
                                    dadosPaciente();
                                    buscandoHistoricoPaciente();
                                    break;
                                }

                                case 2: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 

                                    //preto quase branco 
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    break;
                                }

                                case 3: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 

                                    //preto quase branco 
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);
                                    abrirEditorBarraFerramentaConsulta();

                                    break;
                                }

                                case 4: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 

                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);
                                    lancarModeloPrescrito();
                                    break;
                                }

                                default: {
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                }

                                
                                
                            }

                        }

                    } catch (Exception e) {
                        System.out.println("try:" + e.getMessage());
                    }

                }
            }

        }.start();// iniciando a Thread

    }

    private void buscandoHistoricoPaciente() {

        int idPaciente = Integer.parseInt(lblRepositIdPacienteConsulta.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        //***********************************************
        //https://www.youtube.com/watch?v=jPfKFm2Yfow //
        //***********************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
        tabela.setRowHeight(100);

        tabela.setDefaultRenderer(Object.class, new Render());
        tabela.getColumnModel().getColumn(1).setCellRenderer(new TextAreaCellRenderer());
        tabela.getColumnModel().getColumn(2).setCellRenderer(new TextAreaCellRenderer());

        JButton btnEditor = new JButton();
        btnEditor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editor.png")));

        JButton btnLanca = new JButton();
        btnLanca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/texteditor/lancar.png")));

        btnLanca.setName(
                "lancar");

        btnEditor.setName(
                "Editor");
        ArrayList<HistoricoPacienteDTO> list;

        try {

            list = (ArrayList<HistoricoPacienteDTO>) historicoDAO.buscarPorIdTblConsultaList(idPaciente);

            Object rowData[] = new Object[6];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdGeradoDto();
                rowData[1] = list.get(i).getDiagnosticoDto();
                rowData[2] = list.get(i).getOrientacaoDto();
                rowData[3] = list.get(i).getNomeMedicoDto();
                rowData[4] = btnEditor;
                rowData[5] = btnLanca;
                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(200);//nome paciente 
            tabela.getColumnModel().getColumn(2).setPreferredWidth(300);//data agendamento
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(50);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("buscando Historico do paciente -buscandoHistoricoPaciente() ");
        }

    }

    private void frontEnd() {
        tabela.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela         
        tabela.getTableHeader().setDefaultRenderer(new br.com.multclin.util.EstiloTabelaHeaderMedico());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new Render());
    }

    private void fazendoTesteConexao() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            System.out.println("Verificando resultado: " + recebeConexao);
        } else {

            acaoSairDoSistemaFormLocal();
        }

    }

    private void adicionarTabela() {

        //***********************************************
        //https://www.youtube.com/watch?v=jPfKFm2Yfow //
        //***********************************************
        //CANAL:MamaNs - Java Swing UI - Design jTable       
        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
        //https://www.youtube.com/watch?v=RXhMdUPk12k
        //*******************************************************************************************
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
        tabela.setRowHeight(10);

        tabela.setDefaultRenderer(Object.class, new Render());
        tabela.getColumnModel().getColumn(1).setCellRenderer(new TextAreaCellRenderer());

        JButton btnExcluir = new JButton();
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/lixeira.png")));

        JButton btnEditor = new JButton();
        btnEditor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editor.png")));

        btnExcluir.setName(
                "Ex");

        btnEditor.setName(
                "Editor");

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        try {

            Object rowData[] = new Object[5];

            rowData[0] = lblIdOrientacaoConsulta.getText();
            rowData[1] = txtAreaPrescricao.getText();
            rowData[2] = lblRepositNomeMedicoAgenda.getText();
            rowData[3] = btnEditor;
            rowData[4] = btnExcluir;

            model.addRow(rowData);

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(580);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(140);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(50);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Erro:Método adicionarTabela()" + ex.getMessage());

        }

    }

    private void acaoSairDoSistemaFormLocal() {

        new Thread() {

            public void run() {

                for (int i = 0; i < 101; i++) {

                    try {

                        sleep(100);
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() <= 5) {

                            barraProgresso.setVisible(true);

                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect1.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 15) {

                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect2.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(45, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 25) {

                            jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect3.png")));
                            painelSimulador.add(jLTesteConexaoPositivo);
                            jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 35) {
                            jL40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect4.png")));
                            painelSimulador.add(jL40);
                            jL40.setBounds(135, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 45) {
                            jL50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect5.png")));
                            painelSimulador.add(jL50);
                            jL50.setBounds(185, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 55) {
                            jL60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconnect6.png")));
                            painelSimulador.add(jL60);
                            jL60.setBounds(235, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 65) {
                            jL70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect7.jpg")));
                            painelSimulador.add(jL70);
                            jL70.setBounds(290, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 75) {
                            jL80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect8.png")));
                            painelSimulador.add(jL80);
                            jL80.setBounds(345, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 85) {
                            jL90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect9.png")));
                            painelSimulador.add(jL90);
                            jL90.setBounds(400, 15, 50, 50);

                        } else if (barraProgresso.getValue() <= 95) {
                            jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/semconexao/noconect10.png")));
                            painelSimulador.add(jLEnd);
                            jLEnd.setBounds(455, 15, 100, 50);

                        } else {
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

    private void dadosPaciente() {

        int codigo = Integer.parseInt(lblRepositIdPacienteConsulta.getText());

        ArrayList<PacienteDTO> list;
        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.buscarPorID(codigo);
            if (list.size() > 0) {

                for (int i = 0; i < list.size(); i++) {

                    lblRepositIdPacienteConsulta.setText(list.get(i).getIdDto().toString());
                    lblRepositPaciente.setText(list.get(i).getNomeDto());
                    lblRepositDtNascimento.setText(list.get(i).getDataNascDto());
                    lblRepositSexo.setText(list.get(i).getSexoDto());
                }

                calculaIdade();
            } else {
                System.out.println("Não exite itens na lista ");

            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            dispose();
        }

    }

    private void calculaIdade() {

        try {

            //formatados 
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

            /**
             * Primeiro criamos uma variável do tipo String e capturamos o valor
             * digitado no campo por meio do método getText() onde ficará
             * armazenado na variável CNPJ criado para receber o valor capturado
             * pelou usuário.
             */
            //String str = dataNascimentoTratada(txtDataNascimento.getText());
            String DataDeNascimento = lblRepositDtNascimento.getText();
            //String DataDeNascimento = txtDataNascimento.getText();
            //segunda data atual 
            String DataAtual = lblStatusData.getText();

            Date primeiraDt = formatador.parse(DataAtual);
            Date segundaDt = formatador.parse(DataDeNascimento);

            //aqui teremos a diferença em milissegundo entre as duas datas 
            //o metodo Math.abs() pega o valor absoluto
            long diffEmMil = Math.abs(primeiraDt.getTime() - segundaDt.getTime());
            //pegando a diferença entre dias utilizado a classe Time.Unit
            long diff = TimeUnit.DAYS.convert(diffEmMil, TimeUnit.MILLISECONDS);
            //setando um valor long em uma String 

            long calculo = diff / 365;

            if (segundaDt.getTime() > primeiraDt.getTime()) {

                lblRepositDtNascimento.requestFocus();

            } else {

                if (calculo <= 150) {

                    lblRepositIdade.setText(Long.toString(calculo) + "ano(s)");

//                    btnSalvar.setEnabled(true);
                } else {

                    txtNome.requestFocus();

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
        }

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

    private void abrirEditorBarraFerramenta() {

        if (estaFechado(telaEditor)) {
            telaEditor = new TelaEditor();
            DeskTop.add(telaEditor).setLocation(1, 1);
            telaEditor.setTitle("Prescrever Orientações");
            telaEditor.setVisible(true);
            tp_texto.setText(txtAreaPrescricao.getText());

        } else {
            telaEditor.toFront();
            telaEditor.setVisible(true);

        }
    }

    
    private void lancarModeloPrescrito() {
    
        
        int linhaRegistro = tabela.getSelectedRow();
    
         String diagnostico=(String) tabela.getValueAt(linhaRegistro, 1);
        
        
          String quebrandoLinha =  (String) tabela.getValueAt(linhaRegistro, 1);

        //Esse é o numero maximo de caracteres por linha
        int numeroDeCaracteresPorLinha = 75;
        //Aqui eu conto o numero de caractres do texto que estou quebrando
        int tamanhoDoTextoQueVouQuebrar = quebrandoLinha.length();
        //Aqui é onde vou guardar os caracteres quando forem inseridas as quebras de linha
        String textoNovo = "";

        //Esse loop começará no numero 1 e vai até o numero de caracteres que meu texto tem
        //ou seja, vai percorrer letra por letra do meu texto
        for (int i = 1; i <= tamanhoDoTextoQueVouQuebrar; i++) {
        //Adiciono o caracter atual ao texto novo, e como no java a contagem começa de zero
            //eu tenho que pegar o numero atual -1
            textoNovo = textoNovo + quebrandoLinha.charAt(i - 1);
        //Se o numero atual dividido pelo limite de  caracteres tem resto zero, e se não for o ultimo
            //Caracter do meu texto
            if (i % numeroDeCaracteresPorLinha == 0 && i < tamanhoDoTextoQueVouQuebrar) {
                //Adiciona uma quebra de linha no meu texto
                textoNovo = textoNovo + System.getProperty("line.separator");
            }
        }
        
        String orientacao=(String)tabela.getValueAt(linhaRegistro, 2);
        txtAreaDiagnostico.setText(textoNovo);
        txtAreaPrescricao.setText(orientacao);
        save.setEnabled(true);
        dispose();
        
    }

    private void abrirEditorBarraFerramentaConsulta() {

        if (estaFechado(telaEditorConsulta)) {
            telaEditorConsulta = new TelaEditorConsulta();
            DeskTop.add(telaEditorConsulta).setLocation(1, 1);
            telaEditorConsulta.setTitle("Consultar Prescição");
            telaEditorConsulta.setVisible(true);

            int linhaRegistro = tabela.getSelectedRow();

            String diagnostico = (tabela.getValueAt(linhaRegistro, 1).toString());
            String prescricao = (tabela.getValueAt(linhaRegistro, 2).toString());
            tp_textoConsulta.setText(diagnostico + "\n" + prescricao);
            tp_textoConsulta.setEditable(false);
            // aqui proxima tentativa

        } else {
            telaEditorConsulta.toFront();
            telaEditorConsulta.setVisible(true);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelSimulador = new javax.swing.JPanel();
        barraProgresso = new javax.swing.JProgressBar();
        lblIdOrientacaoConsulta = new javax.swing.JLabel();
        painelPaciente = new javax.swing.JPanel();
        lblRepositIdPacienteConsulta = new javax.swing.JLabel();
        lblRepositPaciente = new javax.swing.JLabel();
        lblNomePaciente = new javax.swing.JLabel();
        lblDtNascimento = new javax.swing.JLabel();
        lblRepositDtNascimento = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblRepositSexo = new javax.swing.JLabel();
        lblRepositIdade = new javax.swing.JLabel();
        scrollPrescricao = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();

        setClosable(true);

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelSimulador.setLayout(null);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(510, 25, 230, 24);

        lblIdOrientacaoConsulta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIdOrientacaoConsulta.setForeground(new java.awt.Color(75, 0, 130));
        lblIdOrientacaoConsulta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIdOrientacaoConsulta.setText("ID");
        painelSimulador.add(lblIdOrientacaoConsulta);
        lblIdOrientacaoConsulta.setBounds(750, 10, 50, 50);

        painelPrincipal.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 810, 70));

        painelPaciente.setBackground(java.awt.Color.white);
        painelPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Paciente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelPaciente.setForeground(new java.awt.Color(75, 0, 130));
        painelPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositIdPacienteConsulta.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositIdPacienteConsulta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelPaciente.add(lblRepositIdPacienteConsulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 20));

        lblRepositPaciente.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelPaciente.add(lblRepositPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 370, 20));

        lblNomePaciente.setForeground(new java.awt.Color(75, 0, 130));
        lblNomePaciente.setText(" ID       Paciente: ");
        painelPaciente.add(lblNomePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, -1));

        lblDtNascimento.setForeground(new java.awt.Color(75, 0, 130));
        lblDtNascimento.setText("Data Nascimento:");
        painelPaciente.add(lblDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        lblRepositDtNascimento.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositDtNascimento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositDtNascimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelPaciente.add(lblRepositDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 90, 20));

        lblSexo.setForeground(new java.awt.Color(75, 0, 130));
        lblSexo.setText("Sexo:");
        painelPaciente.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, -1, -1));

        lblRepositSexo.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositSexo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositSexo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelPaciente.add(lblRepositSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 90, 20));

        lblRepositIdade.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblRepositIdade.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositIdade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositIdade.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Idade:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelPaciente.add(lblRepositIdade, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, 100, 50));

        painelPrincipal.add(painelPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 810, 80));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DIAGNÓSTICO", "ORIENTAÇÃO", "DR(A)", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setSelectionBackground(java.awt.Color.orange);
        tabela.setSelectionForeground(new java.awt.Color(75, 0, 130));
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        scrollPrescricao.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setPreferredWidth(36);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(300);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(400);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        painelPrincipal.add(scrollPrescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 810, 380));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
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

                /**
                 * Botão de Autenticação
                 */
                if (boton.getName().equals("Ex")) {

                    /*Evento ao ser clicado executa código*/
                    int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

                    if (excluir == JOptionPane.YES_OPTION) {

                        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

                        if (tabela.getSelectedRow() != -1) {

                            model.removeRow(tabela.getSelectedRow());

                        } else {

                        }
                    }
                }

                if (boton.getName().equals("Editor")) {

                    int flagC = 3;
                    progressBar(flagC);

                }

                if (boton.getName().equals("lancar")) {

                    int flagC = 4;
                    progressBar(flagC);

                }

            }

        }


    }//GEN-LAST:event_tabelaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JLabel lblDtNascimento;
    public static javax.swing.JLabel lblIdOrientacaoConsulta;
    private javax.swing.JLabel lblNomePaciente;
    public static javax.swing.JLabel lblRepositDtNascimento;
    public static javax.swing.JLabel lblRepositIdPacienteConsulta;
    private javax.swing.JLabel lblRepositIdade;
    public static javax.swing.JLabel lblRepositPaciente;
    public static javax.swing.JLabel lblRepositSexo;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JPanel painelPaciente;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JScrollPane scrollPrescricao;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables

}
