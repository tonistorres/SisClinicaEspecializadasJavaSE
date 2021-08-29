package br.com.multclin.telas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.lblIdAgendamentoReposit;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.lblIdMedicoAgenda;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.lblRepositIdPaciente;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.lblNomeCompletoUsuario;
import static br.com.multclin.telas.TelaPrincipal.lblStatusData;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
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

public class Modulo2AtendimentoConsultorio extends javax.swing.JInternalFrame {

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());
    Font f = new Font("Tahoma", Font.BOLD, 14);

    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    AgendamentoBO agendamentoBO = new AgendamentoBO();
    ConexaoUtil conecta = new ConexaoUtil();

    br.com.multclin.telas.Modulo2HistoricoPaciente formDiagnostico;
    JButton btnAutentica = new JButton();

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

    public Modulo2AtendimentoConsultorio() throws ClassNotFoundException {

        //fora do bloco do try os métodos que não buscam nada na nuver 
        initComponents();
        frontEnd();
        aoCarregar();

    }

    private void aoCarregar() {
        barraProgresso.setVisible(false);
        lblLinhaInformativa.setVisible(false);

        int flagBtn = 1;
        progressBar(flagBtn);

    }

    private void abrirDiagnostico() {

        if (estaFechado(formDiagnostico)) {
            formDiagnostico = new Modulo2HistoricoPaciente();
            DeskTop.add(formDiagnostico).setLocation(1, 1);
            formDiagnostico.setTitle("Ficha de Diagnóstico");
            formDiagnostico.setVisible(true);

            try {
                int linhaRegistro = tabela.getSelectedRow();

                String capturaIdPacienteDaTabela = (tabela.getValueAt(linhaRegistro, 1).toString());
                lblRepositIdPaciente.setText(capturaIdPacienteDaTabela);

                String capturaIdMedicoTabela = (tabela.getValueAt(linhaRegistro, 4).toString());
                lblIdMedicoAgenda.setText(capturaIdMedicoTabela);

                String capturaIdAgendamentoTabela = (tabela.getValueAt(linhaRegistro, 0).toString());
                lblIdAgendamentoReposit.setText(capturaIdAgendamentoTabela);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            formDiagnostico.toFront();
            formDiagnostico.setVisible(true);

        }

    }

    private void frontEnd() {
        this.btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/refreshNuvem.png")));

        tabela.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela         
        tabela.getTableHeader().setDefaultRenderer(new br.com.multclin.util.EstiloTabelaHeaderMedico());//classe EstiloTableHeader Cabeçalho da Tabela
        tabela.setDefaultRenderer(Object.class, new Render());
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 10));
        tabela.getTableHeader().setOpaque(false);
        tabela.setRowHeight(36);

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<AgendamentoDTO> list;

        try {

            btnAutentica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/digital.jpg")));;
            btnAutentica.setName("btnAutentica");
            btnAutentica.setToolTipText("Autentica Atendimento");

            String nomeUsuarioLogado = MetodoStaticosUtil.removerAcentosCaixAlta(lblNomeCompletoUsuario.getText());
            String status = "CONSULTORIO";

            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosPorMedico(status, nomeUsuarioLogado);

            Object rowData[] = new Object[8];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();//id do agendamento 
                rowData[1] = list.get(i).getFk_pacienteDto();//fk(foren Key) do paciente 
                rowData[2] = list.get(i).getNomeDto();
                rowData[3] = formatador.format(list.get(i).getDataAgendamentoDto());
                rowData[4] = list.get(i).getFk_medicoDto();//fk (foren Key) do médico 
                rowData[5] = list.get(i).getNomeMedicoDto();
                rowData[6] = btnAutentica;
                rowData[7] = list.get(i).getStatusAgendamentoDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(42);//id agendamento 
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//fk do paciente 
            tabela.getColumnModel().getColumn(2).setPreferredWidth(280);//nome paciente 
            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//data do agendamento
            tabela.getColumnModel().getColumn(4).setPreferredWidth(42);//fk (foren key) do medico 
            tabela.getColumnModel().getColumn(5).setPreferredWidth(150);//nome do medico
            tabela.getColumnModel().getColumn(6).setPreferredWidth(36);//btnAutentica
            tabela.getColumnModel().getColumn(7).setPreferredWidth(140);//btnAutentica
            //fim da pesquisa 
            //    acaoFimDePesquisNoBanco();
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
        }

    }

    private void acaoPreparandoListaInicial() {

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
                lblLinhaInformativa.setEditable(false);
                jLEnd.setIcon(null);
                limpaTodosIconesLinhaTemporal();
                btnRefresh.setEnabled(false);

                for (int i = 0; i < 101; i++) {

                    try {

                        sleep(25);
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() == 10) {

                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("Viajando nuvem 10%...");
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/systems.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 20) {

                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("20% Dr(a)" + lblNomeCompletoUsuario.getText() + " Testando Conexão. ");
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/hostgator.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(45, 15, 50, 50);

                            fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("30% Linha Temporal...");
                            jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/updateLista.png")));
                            painelSimulador.add(jLTesteConexaoPositivo);
                            jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 40) {
                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("40% linha temporal...");
                            jL40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar40.png")));
                            painelSimulador.add(jL40);
                            jL40.setBounds(135, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 50) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("50% linha temporal...");
                            jL50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar50.png")));
                            painelSimulador.add(jL50);
                            jL50.setBounds(185, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 60) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("60% linha temporal...");
                            jL60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar60.png")));
                            painelSimulador.add(jL60);
                            jL60.setBounds(235, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 70) {
                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("70% linha temporal...");
                            jL70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar70.png")));
                            painelSimulador.add(jL70);
                            jL70.setBounds(290, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 80) {
                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("80% linha temporal...");
                            jL80.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar80.png")));
                            painelSimulador.add(jL80);
                            jL80.setBounds(345, 15, 50, 50);
                        } else if (barraProgresso.getValue() == 90) {

                            lblLinhaInformativa.setFont(f);
                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                            lblLinhaInformativa.setText("90% teste linha temporal...");
                            jL90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/progressBar90.png")));
                            painelSimulador.add(jL90);
                            jL90.setBounds(400, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 100) {

                            switch (flagC) {

                                case 1: {

                                    lblLinhaInformativa.setFont(f);
                                    lblLinhaInformativa.setForeground(Color.ORANGE);
                                    lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                                    lblLinhaInformativa.setText("Buscando dados núvem...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    
                                    btnRefresh.setEnabled(true);
                                    btnRefresh.requestFocus();
                                    tabela.requestFocus();
                                    
                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + " segue abaixo lista de paciente(s). Tenha Bom Trabalho ^_^");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);

                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    break;
                                }

                                case 2: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setFont(f);
                                    lblLinhaInformativa.setForeground(Color.ORANGE);
                                    lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                                    lblLinhaInformativa.setText("Atualizando lista...");
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    abrirDiagnostico();
                                    //atualizando lista 
                                    acaoPreparandoListaInicial();

                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + " segue abaixo lista de paciente(s). Tenha Bom Trabalho ^_^");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    break;
                                }

                                case 3: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setFont(f);
                                    lblLinhaInformativa.setForeground(Color.ORANGE);
                                    lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                                    lblLinhaInformativa.setText("Atualizando lista...");
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    //atualizando lista 
                                    acaoPreparandoListaInicial();

                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + " segue abaixo lista de paciente(s). Tenha Bom Trabalho ^_^");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    break;
                                }

                                default: {
                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + " segue abaixo lista de paciente(s). Tenha Bom Trabalho ^_^");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
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

                        sleep(100);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelSimulador = new javax.swing.JPanel();
        barraProgresso = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JTextField();
        painelAtualizar = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();

        setClosable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdAg", "IdPc", "Paciente", "Data", "ID", "Porf.Saude", "", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(42);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(42);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(6).setResizable(false);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(38);
            tabela.getColumnModel().getColumn(7).setResizable(false);
            tabela.getColumnModel().getColumn(7).setPreferredWidth(140);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 167, 780, 340));

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelSimulador.setLayout(null);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(510, 25, 240, 24);

        jPanel1.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 770, 70));

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Linha Informativa:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 21, 670, 50));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 690, 80));

        painelAtualizar.setBackground(java.awt.Color.white);
        painelAtualizar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atualizar:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
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
        painelAtualizar.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 15, 55, 55));

        jPanel1.add(painelAtualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, 80, 80));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 520));

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
                if (boton.getName().equals("btnAutentica")) {

                    /*Evento ao ser clicado executa código*/
                    // int excluir = JOptionPane.showConfirmDialog(null, "Autenticar Atendimento?", "Informação", JOptionPane.YES_NO_OPTION);
                    //if (excluir == JOptionPane.YES_OPTION) {
                    /**
                     * Na thread de Execução será executado todos os blocos de
                     * codigos CASE 1
                     */
                    int flagC = 2;
                    progressBar(flagC);

                    //  }
                }

            }

        }

    }//GEN-LAST:event_tabelaMouseClicked

    private void btnRefreshFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnRefreshFocusGained
        int flagC = 3;
        progressBar(flagC);
    }//GEN-LAST:event_btnRefreshFocusGained

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }
            int flagBtn = 1;
            progressBar(flagBtn);
        } else {

            int flagBtn = 1;
            progressBar(flagBtn);
        }


    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    public static javax.swing.JButton btnRefresh;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lblLinhaInformativa;
    private javax.swing.JPanel painelAtualizar;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
