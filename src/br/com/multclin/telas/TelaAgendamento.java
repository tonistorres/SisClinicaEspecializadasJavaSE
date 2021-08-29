package br.com.multclin.telas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dao.CadEspecialidadeDAO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.GeraCodigoAutomaticoDAO;
import br.com.multclin.dao.ItemAgendamentoDAO;
import br.com.multclin.dao.ProcedimentoDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.CadEspecialidadeDTO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.ItemAgendamentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.lblNomeCompletoUsuario;
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import static br.com.multclin.telas.TelaPrincipal.lblRepositorioCPU;
import static br.com.multclin.telas.TelaPrincipal.lblRepositorioHD;
import java.awt.Color;
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

/**
 *
 * @author Analista de Sistemas Tonis Alberto Torres Ferreira
 */
public class TelaAgendamento extends javax.swing.JInternalFrame {

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());
    Font f = new Font("Tahoma", Font.BOLD, 12);

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

    int flag = 0;

    //TRABALHANDO A THREAD QUE IRÁ DA O EFEITO 
    //DE PISCAR O CAMPO QUANDO RECEBER FOCO
    static int milissegundos = 0;
    static int segundos = 0;
    static int minutos = 0;
    static int horas = 0;
    //estado para controle de movimentos paineis txtLogin e txtSenha
    //bem essas flags já entram setada em true porque ja iniciam suas
    //tarefas no momento que o formulario é aberto 
    static boolean estado = true;

    br.com.multclin.telas.FrmListaPaciente frmListaPaciente;
    br.com.multclin.telas.FrmListaMedicos frmListaMedicos;
    br.com.multclin.telas.FrmListaProcedimentos frmListaProcedimentos;
    br.com.multclin.telas.TelaMedico formMedico;

    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

    CadEspecialidadeDTO cadEspecialidadeDTO = new CadEspecialidadeDTO();
    CadEspecialidadeDAO cadEspecialidadeDAO = new CadEspecialidadeDAO();

    AgendamentoBO agendamentoBO = new AgendamentoBO();
    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();

    ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();
    ItemAgendamentoDAO itemAgendamentoDAO = new ItemAgendamentoDAO();

    ArrayList<ItemAgendamentoDTO> listaItensProcedimentosDTO = new ArrayList<>();

    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();
    GeraCodigoAutomaticoDAO geraCodigoDAO = new GeraCodigoAutomaticoDAO();

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

    public TelaAgendamento() {
        initComponents();
        aoCarregar();
        frontEnd();
    }

    private void aoCarregar() {
        //    this.progressBarraPesquisa.setIndeterminate(true);
        this.cbEspecialidade.setEnabled(false);
        this.btnPesqMedico.setEnabled(false);
        this.btnPesqPaciente.setEnabled(false);
        this.btnPesqProcede.setEnabled(false);
        this.btnLancar.setEnabled(false);
//        this.txtAreaObserva.setEnabled(false);
        this.jDateTxtDtAgenda.setEnabled(false);
        this.txtRsDesconto.setEnabled(false);
        this.btnSalvar.setEnabled(false);
        this.lblRsBruto.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.barraProgresso.setVisible(false);

        String rsBruto = moeda.format(0.00);
        String rsDesconto = moeda.format(0.00);
        String rsValorTotal = moeda.format(0.00);

        lblRsBruto.setText(rsBruto.replace("R$", "").trim());
        txtRsDesconto.setText(rsDesconto.replace("R$", "").trim());
        lblValorTotal.setText(rsValorTotal.replace("R$", "").trim());

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
    }

    private void frontEnd() {
//        this.lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
        this.btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        this.btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        this.btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
    }

    private void efeitoAoReceberFoco(JButton nomeBotao) {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(10);

                            if (segundos % 2 == 0) {
                                nomeBotao.setBackground(Color.YELLOW);

                            }

                            if (segundos % 2 != 0) {
                                nomeBotao.setBackground(new Color(0, 102, 102));
                            }

                            if (milissegundos > 1000) {
                                milissegundos = 0;
                                segundos++;

                            }

                            if (segundos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos++;
                            }

                            if (minutos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            milissegundos++;

                        } catch (Exception e) {

                        }

                    } else {
                        break;
                    }
                }

            }

        };
        t.start();

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

                            fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            switch (flagC) {

                                case 1: {

                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);
                                    //Abrir lista
                                    acaoBotaoAdicionar();

                                    break;
                                }

                                case 2: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    int idMedico = Integer.parseInt(lblIdMedico.getText());
                                    if (idMedico > 0) {

                                        if (!lblRepositIDEspecialidade.getText().isEmpty()) {
                                            abrirTelaListaPaciente();
                                            jDateTxtDtAgenda.requestFocus();
                                        } else {
                                            JOptionPane.showMessageDialog(null, "" + "\n Informação:\n"
                                                    + "O Administrador do Sistema\n"
                                                    + "Deverá Abrir o Cadastro de \n"
                                                    + "Médico e Vincular uma Especialidade\n"
                                                    + "Para o Médico em questão.\n" + lblRepositNomeMedico.getText() + "\n"
                                                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                                            dispose();
                                        }

                                    }

                                    break;
                                }

                                case 3: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    if (jDateTxtDtAgenda.getDate() == null) {
                                        JOptionPane.showMessageDialog(null, "" + "\n Informação:\n"
                                                + "DATA DE AGENDAMENTO Obrigatória"
                                                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                                        jDateTxtDtAgenda.requestFocus();
                                        jDateTxtDtAgenda.setBackground(Color.red);

                                    } else {
                                        // capturarEspecialidadeId();
                                        abrirFrmListaProcede();
                                    }

                                    break;
                                }

                                case 4: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    try {
                                        Salvar();
                                    } catch (ParseException ex) {
                                        ex.printStackTrace();
                                        erroViaEmail("Erro TelaAgendamento", ex.getMessage());
                                    }

                                    break;
                                }

                                case 5: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    limparCamposSalvamento();
                                    desabilitarCampos();

                                    jDateTxtDtAgenda.setBackground(Color.WHITE);
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

        painelPrincipal = new javax.swing.JPanel();
        painelSobreMedico = new javax.swing.JPanel();
        lblRepositNomeMedico = new javax.swing.JLabel();
        lblMedico = new javax.swing.JLabel();
        lblEspecialidades = new javax.swing.JLabel();
        btnPesqMedico = new javax.swing.JButton();
        lblIdMedico = new javax.swing.JLabel();
        cbEspecialidade = new javax.swing.JComboBox();
        lblRepositIDEspecialidade = new javax.swing.JLabel();
        painelPaciente = new javax.swing.JPanel();
        lblRepositIdPaciente = new javax.swing.JLabel();
        lblRepositPaciente = new javax.swing.JLabel();
        lblNomePaciente = new javax.swing.JLabel();
        btnPesqPaciente = new javax.swing.JButton();
        lblDtNascimento = new javax.swing.JLabel();
        lblRepositDtNascimento = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblRepositSexo = new javax.swing.JLabel();
        lblEstadoCivil = new javax.swing.JLabel();
        lblRepositEstadoCivil = new javax.swing.JLabel();
        lblConjuge = new javax.swing.JLabel();
        lblRepositConjuge = new javax.swing.JLabel();
        lblMae = new javax.swing.JLabel();
        lblRepositMae = new javax.swing.JLabel();
        lblPai = new javax.swing.JLabel();
        lblRepositPai = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        lblRepositCelular = new javax.swing.JLabel();
        lblUF = new javax.swing.JLabel();
        lblRepositUF = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        lblRepositCidade = new javax.swing.JLabel();
        lblBairro = new javax.swing.JLabel();
        lblRepositBairro = new javax.swing.JLabel();
        lblRua = new javax.swing.JLabel();
        lblRepositRua = new javax.swing.JLabel();
        lblRepositCPFPaciente = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelNavegação = new javax.swing.JPanel();
        btnSalvar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        painelValorTotal = new javax.swing.JPanel();
        lblValorTotal = new javax.swing.JLabel();
        painelLancamento = new javax.swing.JPanel();
        lblIDAgendamento = new javax.swing.JLabel();
        jDateTxtDtAgenda = new com.toedter.calendar.JDateChooser();
        lblAutoProcedimentos = new javax.swing.JLabel();
        lblProcedimento = new javax.swing.JLabel();
        lblRepositProcede = new javax.swing.JLabel();
        btnPesqProcede = new javax.swing.JButton();
        lblRSBruto = new javax.swing.JLabel();
        lblRsBruto = new javax.swing.JTextField();
        txtRsDesconto = new javax.swing.JFormattedTextField();
        btnLancar = new javax.swing.JButton();
        lblDesconto = new javax.swing.JLabel();
        painelSimulador = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();

        setClosable(true);
        setTitle("Agendamento");

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelSobreMedico.setBackground(java.awt.Color.white);
        painelSobreMedico.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Médico:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSobreMedico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositNomeMedico.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositNomeMedico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelSobreMedico.add(lblRepositNomeMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 190, 20));

        lblMedico.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMedico.setForeground(new java.awt.Color(0, 102, 102));
        lblMedico.setText("Médico:");
        painelSobreMedico.add(lblMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 24, -1, -1));

        lblEspecialidades.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEspecialidades.setForeground(new java.awt.Color(0, 102, 102));
        lblEspecialidades.setText("Especialidade");
        painelSobreMedico.add(lblEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 50, -1, -1));

        btnPesqMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesqMedico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesqMedicoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesqMedicoFocusLost(evt);
            }
        });
        btnPesqMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqMedicoActionPerformed(evt);
            }
        });
        painelSobreMedico.add(btnPesqMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 35, 35));

        lblIdMedico.setForeground(new java.awt.Color(0, 102, 102));
        lblIdMedico.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelSobreMedico.add(lblIdMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 30, 20));

        cbEspecialidade.setForeground(new java.awt.Color(0, 102, 102));
        cbEspecialidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbEspecialidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbEspecialidadeFocusLost(evt);
            }
        });
        cbEspecialidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEspecialidadeActionPerformed(evt);
            }
        });
        painelSobreMedico.add(cbEspecialidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 45, 150, -1));

        lblRepositIDEspecialidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelSobreMedico.add(lblRepositIDEspecialidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, 30, 20));

        painelPrincipal.add(painelSobreMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 340, 80));

        painelPaciente.setBackground(java.awt.Color.white);
        painelPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Paciente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositIdPaciente.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositIdPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositIdPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 30, 20));

        lblRepositPaciente.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 180, 20));

        lblNomePaciente.setForeground(new java.awt.Color(0, 102, 102));
        lblNomePaciente.setText(" ID     Paciente:  CPF.:");
        painelPaciente.add(lblNomePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, 120, -1));

        btnPesqPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesqPaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesqPacienteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesqPacienteFocusLost(evt);
            }
        });
        btnPesqPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqPacienteActionPerformed(evt);
            }
        });
        painelPaciente.add(btnPesqPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 20, 35, 35));

        lblDtNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lblDtNascimento.setText("Data Nascimento:");
        painelPaciente.add(lblDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 15, -1, -1));

        lblRepositDtNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositDtNascimento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositDtNascimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 90, 20));

        lblSexo.setForeground(new java.awt.Color(0, 102, 102));
        lblSexo.setText("Sexo:");
        painelPaciente.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 15, -1, -1));

        lblRepositSexo.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositSexo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 100, 20));

        lblEstadoCivil.setForeground(new java.awt.Color(0, 102, 102));
        lblEstadoCivil.setText("Estado Civil");
        painelPaciente.add(lblEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 15, -1, -1));

        lblRepositEstadoCivil.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositEstadoCivil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 150, 20));

        lblConjuge.setForeground(new java.awt.Color(0, 102, 102));
        lblConjuge.setText("Conjuge:");
        painelPaciente.add(lblConjuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lblRepositConjuge.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositConjuge.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositConjuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, 210, 20));

        lblMae.setForeground(new java.awt.Color(0, 102, 102));
        lblMae.setText("Mãe:");
        painelPaciente.add(lblMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, -1, -1));

        lblRepositMae.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositMae.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 75, 190, 20));

        lblPai.setForeground(new java.awt.Color(0, 102, 102));
        lblPai.setText("Pai:");
        painelPaciente.add(lblPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, -1, -1));

        lblRepositPai.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 75, 220, 20));

        lblCelular.setForeground(new java.awt.Color(0, 102, 102));
        lblCelular.setText("Celular:");
        painelPaciente.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        lblRepositCelular.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositCelular.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 115, 110, 20));

        lblUF.setForeground(new java.awt.Color(0, 102, 102));
        lblUF.setText("UF:");
        painelPaciente.add(lblUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, -1, -1));

        lblRepositUF.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositUF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 115, 30, 20));

        lblCidade.setForeground(new java.awt.Color(0, 102, 102));
        lblCidade.setText("Cidade:");
        painelPaciente.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, -1, -1));

        lblRepositCidade.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositCidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 115, 130, 20));

        lblBairro.setForeground(new java.awt.Color(0, 102, 102));
        lblBairro.setText("Bairro:");
        painelPaciente.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, -1, -1));

        lblRepositBairro.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositBairro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 115, 100, 20));

        lblRua.setForeground(new java.awt.Color(0, 102, 102));
        lblRua.setText("Rua:");
        painelPaciente.add(lblRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 100, -1, -1));

        lblRepositRua.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositRua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 115, 220, 20));

        lblRepositCPFPaciente.setForeground(java.awt.Color.orange);
        painelPaciente.add(lblRepositCPFPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 15, 90, 15));

        painelPrincipal.add(painelPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 670, 150));

        tabela.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdProce", "PROCEDIMENTO", "R$ VALOR", "R$ DESCONTO", "Del"
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
            tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        painelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 670, 120));

        painelNavegação.setBackground(java.awt.Color.white);
        painelNavegação.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Navegação:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelNavegação.setForeground(java.awt.Color.white);
        painelNavegação.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelNavegação.add(btnSalvar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 45, 45));

        btnAdicionar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnAdicionarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnAdicionarFocusLost(evt);
            }
        });
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });
        painelNavegação.add(btnAdicionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 45, 45));

        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        painelNavegação.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 45, 45));

        painelPrincipal.add(painelNavegação, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 190, 80));

        painelValorTotal.setBackground(java.awt.Color.white);
        painelValorTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Total Agendado:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 102))); // NOI18N

        lblValorTotal.setBackground(new java.awt.Color(0, 102, 102));
        lblValorTotal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblValorTotal.setForeground(new java.awt.Color(0, 102, 102));
        lblValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout painelValorTotalLayout = new javax.swing.GroupLayout(painelValorTotal);
        painelValorTotal.setLayout(painelValorTotalLayout);
        painelValorTotalLayout.setHorizontalGroup(
            painelValorTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblValorTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
        );
        painelValorTotalLayout.setVerticalGroup(
            painelValorTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblValorTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
        );

        painelPrincipal.add(painelValorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 130, 80));

        painelLancamento.setBackground(java.awt.Color.white);
        painelLancamento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelLancamento.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblIDAgendamento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIDAgendamento.setForeground(new java.awt.Color(0, 102, 102));
        lblIDAgendamento.setText("ID");
        painelLancamento.add(lblIDAgendamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 50, 30));

        jDateTxtDtAgenda.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data da Consulta:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        jDateTxtDtAgenda.setForeground(new java.awt.Color(0, 102, 102));
        jDateTxtDtAgenda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jDateTxtDtAgenda.setPreferredSize(new java.awt.Dimension(87, 25));
        jDateTxtDtAgenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jDateTxtDtAgendaFocusGained(evt);
            }
        });
        painelLancamento.add(jDateTxtDtAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 150, 70));

        lblAutoProcedimentos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelLancamento.add(lblAutoProcedimentos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 28, 25));

        lblProcedimento.setForeground(new java.awt.Color(0, 102, 102));
        lblProcedimento.setText("Procedimento:");
        painelLancamento.add(lblProcedimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, 20));

        lblRepositProcede.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositProcede.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelLancamento.add(lblRepositProcede, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 170, 25));

        btnPesqProcede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png"))); // NOI18N
        btnPesqProcede.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesqProcedeFocusGained(evt);
            }
        });
        btnPesqProcede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesqProcedeActionPerformed(evt);
            }
        });
        painelLancamento.add(btnPesqProcede, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 35, 35));

        lblRSBruto.setForeground(new java.awt.Color(0, 102, 102));
        lblRSBruto.setText("Valor R$:");
        painelLancamento.add(lblRSBruto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 50, -1));

        lblRsBruto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRsBruto.setPreferredSize(new java.awt.Dimension(6, 25));
        painelLancamento.add(lblRsBruto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 80, -1));

        txtRsDesconto.setForeground(new java.awt.Color(0, 102, 102));
        txtRsDesconto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtRsDesconto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtRsDesconto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRsDescontoKeyPressed(evt);
            }
        });
        painelLancamento.add(txtRsDesconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 70, 25));

        btnLancar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/addForms.png"))); // NOI18N
        btnLancar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnLancarFocusGained(evt);
            }
        });
        btnLancar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLancarActionPerformed(evt);
            }
        });
        painelLancamento.add(btnLancar, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 35, 35));

        lblDesconto.setForeground(new java.awt.Color(0, 102, 102));
        lblDesconto.setText("Desconto R$:");
        painelLancamento.add(lblDesconto, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        painelPrincipal.add(painelLancamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 670, 100));

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSimulador.setLayout(null);

        lblLinhaInformativa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        painelSimulador.add(lblLinhaInformativa);
        lblLinhaInformativa.setBounds(500, 5, 190, 30);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(520, 35, 170, 24);

        painelPrincipal.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
        );

        setBounds(0, 0, 717, 579);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPesqMedicoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesqMedicoFocusGained
        efeitoAoReceberFoco(btnPesqMedico);

    }//GEN-LAST:event_btnPesqMedicoFocusGained

    private void abriTelaListaMedico() {

        try {
            if (estaFechado(frmListaMedicos)) {
                frmListaMedicos = new FrmListaMedicos();
                DeskTop.add(frmListaMedicos).setLocation(3, 5);
                frmListaMedicos.setTitle("Lista Especialidades do Médico");
                frmListaMedicos.setVisible(true);

            } else {
                frmListaMedicos.toFront();
                frmListaMedicos.setVisible(true);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void btnPesqMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqMedicoActionPerformed
        int flagBtn = 1;
        progressBar(flagBtn);
    }//GEN-LAST:event_btnPesqMedicoActionPerformed

    private void btnPesqMedicoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesqMedicoFocusLost

        btnPesqMedico.setBackground(Color.WHITE);
        btnPesqMedico.setForeground(Color.BLACK);


    }//GEN-LAST:event_btnPesqMedicoFocusLost

    private void cbEspecialidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEspecialidadeFocusGained


    }//GEN-LAST:event_cbEspecialidadeFocusGained

    public void abrirTelaListaPaciente() {

        try {

            if (estaFechado(frmListaPaciente)) {
                frmListaPaciente = new FrmListaPaciente();
                DeskTop.add(frmListaPaciente).setLocation(38, 10);
                frmListaPaciente.setTitle("Lista Pacientes");
                frmListaPaciente.setVisible(true);

            } else {
                frmListaPaciente.toFront();
                frmListaPaciente.setVisible(true);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void abrirTelaMedico() {

        if (estaFechado(formMedico)) {
            formMedico = new TelaMedico();
            DeskTop.add(formMedico).setLocation(1, 3);
            formMedico.setTitle("Cadastro de Médicos");
            formMedico.setVisible(true);
        } else {
            formMedico.toFront();
            formMedico.setVisible(true);

        }

    }


    private void btnPesqPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqPacienteActionPerformed

        int flagBtn = 2;
        progressBar(flagBtn);


    }//GEN-LAST:event_btnPesqPacienteActionPerformed

    private void btnPesqPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesqPacienteFocusGained
        efeitoAoReceberFoco(btnPesqPaciente);


    }//GEN-LAST:event_btnPesqPacienteFocusGained

    private void btnPesqPacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesqPacienteFocusLost
        btnPesqPaciente.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesqPacienteFocusLost

    private void cbEspecialidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEspecialidadeActionPerformed
        capturarEspecialidadeId();

    }//GEN-LAST:event_cbEspecialidadeActionPerformed

    private void capturarEspecialidadeId() {

        String itemSelecionado = (String) cbEspecialidade.getSelectedItem();//combo 

        ArrayList<CadEspecialidadeDTO> list;

        try {

            list = (ArrayList<CadEspecialidadeDTO>) cadEspecialidadeDAO.filtrarPesqRapida(itemSelecionado);

            if (list.size() > 0) {
                cbEspecialidade.removeAllItems();
                for (int i = 0; i < list.size(); i++) {
                    lblRepositIDEspecialidade.setText(String.valueOf(list.get(i).getIdDto()));
                    cbEspecialidade.addItem(list.get(i).getNomeDto());
                }

                btnPesqPaciente.setEnabled(true);
                btnPesqPaciente.requestFocus();

            } else {
                System.out.println("Não exite itens na lista de especialidades");

            }
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            System.out.println("Erro Camada GUI:\n" + ex.getMessage());

        }

    }


    private void cbEspecialidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEspecialidadeFocusLost

    }//GEN-LAST:event_cbEspecialidadeFocusLost

    private String trataDesconto(String str) {

        String strTratado = "";

        try {

            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == 'R') {

                    strTratado = str.replace(str.charAt(i), ' ');

                }

                if (str.charAt(i) == '$') {

                    strTratado = strTratado.replace(str.charAt(i), ' ');

                }

                if (str.charAt(i) == ',') {

                    strTratado = strTratado.replace(str.charAt(i), '.');

                }

                if (str.charAt(i) == '.') {

                    strTratado = strTratado.replace(str.charAt(i), ',');

                }

            }

            strTratado = strTratado.replace(" ", "");

            String rsDesconto;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return moeda.format(Float.parseFloat(strTratado));
    }

    private void adicionarTabelaItemAgendamento() throws ParseException {

        //Procedimentos que deve acontecer após o lançamento do primeiro item na tabela 
        double rsDesc = MetodoStaticosUtil.converteDoubleUS(txtRsDesconto.getText());
        double rsBruto = MetodoStaticosUtil.converteDoubleUS(lblRsBruto.getText());

        if (rsDesc > rsBruto) {
            //informação 
            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Desconto deve ser \n"
                    + "menor ou igual ao \n"
                    + "valor do procedimento.\n"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            txtRsDesconto.requestFocus();
            txtRsDesconto.setBackground(Color.YELLOW);
            txtRsDesconto.setForeground(Color.BLACK);

        } else {

            if (!txtRsDesconto.getText().isEmpty()) {

                if (lblRepositProcede.getText().equals("") || (lblRepositProcede.getText() == null)) {

                    //informação 
                    JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                            + "O Campo Procedimento \n"
                            + "não pode conter valor nulo. \n"
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                    //se quiser deixar um código mais inteligente criar um método para abrir 
                    //de forma automatica o frmListaProcedimentos
                    btnPesqProcede.setBackground(Color.yellow);
                    btnPesqProcede.requestFocus();
                    abrirFrmListaProcede();

                } else {

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
                    tabela.setRowHeight(40);

                    tabela.setDefaultRenderer(Object.class, new Render());
                    JButton btnExcluir = new JButton();
                    btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/lixeira.png")));

                    btnExcluir.setName(
                            "Ex");

                    DefaultTableModel model = (DefaultTableModel) tabela.getModel();

                    try {
                        ///AQUI A CODIFICAÇÃO DE LANÇAMENTO 
                        String rsDesconto = "";

                        Object rowData[] = new Object[5];

                        rowData[0] = lblAutoProcedimentos.getText();
                        rowData[1] = lblRepositProcede.getText();

                        rowData[2] = lblRsBruto.getText();
                        rowData[3] = txtRsDesconto.getText();
                        rowData[4] = btnExcluir;
                        model.addRow(rowData);

                        tabela.setModel(model);

                        tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
                        tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
                        tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
                        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
                        tabela.getColumnModel().getColumn(4).setPreferredWidth(30);

                        lblRepositProcede.setText("");
                        lblAutoProcedimentos.setText("");
                        lblRsBruto.setText("0,00");
                        txtRsDesconto.setText("0,00");
                        btnSalvar.setEnabled(true);

                        somaTotalProcede();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("Erro:Método addRowTable()" + ex.getMessage());

                    }

                }//fim 

            } else {

                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo [Desconto] não\n"
                        + "não pode conteer valor\n"
                        + "nulo"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                String rsDesconto = moeda.format(0.00);
                txtRsDesconto.setText(rsDesconto.replace("R$", "").trim());
                txtRsDesconto.requestFocus();
                txtRsDesconto.setBackground(Color.YELLOW);
                txtRsDesconto.setForeground(Color.BLACK);

            }
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

                String rsValorTratado = tabela.getValueAt(i, 2).toString().replace("R$", "").trim();
                String rsDescontoTratado = tabela.getValueAt(i, 3).toString().replace("R$", "").trim();

                valorTotal = (MetodoStaticosUtil.converteFloat(rsValorTratado)) - (MetodoStaticosUtil.converteFloat(rsDescontoTratado));

                somaTotal = somaTotal + valorTotal;
            }

            lblValorTotal.setText(moeda.format(somaTotal));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    private void btnLancarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLancarActionPerformed

        if (jDateTxtDtAgenda.getDate() == null) {
            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "DATA DE AGENDAMENTO Obrigatória"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            jDateTxtDtAgenda.requestFocus();
            jDateTxtDtAgenda.setBackground(Color.red);

        } else {
            if (txtRsDesconto.getText().isEmpty()) {

                String rsDesconto = moeda.format(0.00);
                txtRsDesconto.setText(rsDesconto.replace("R$", "").trim());
                try {
                    adicionarTabelaItemAgendamento();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

            } else {
                try {

                    adicionarTabelaItemAgendamento();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }//GEN-LAST:event_btnLancarActionPerformed

    private void abrirFrmListaProcede() {

        if (estaFechado(frmListaProcedimentos)) {
            frmListaProcedimentos = new FrmListaProcedimentos();
            DeskTop.add(frmListaProcedimentos).setLocation(38, 50);
            frmListaProcedimentos.setTitle("Lista de Procedimentos");
            frmListaProcedimentos.setVisible(true);

        } else {
            frmListaProcedimentos.toFront();
            frmListaProcedimentos.setVisible(true);

        }

    }

    private void btnPesqProcedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesqProcedeActionPerformed

        int flagBtn = 3;
        progressBar(flagBtn);


    }//GEN-LAST:event_btnPesqProcedeActionPerformed

    private void gerarCodigoGUIeDataSistema() {

        /**
         * Setar uma Data pega no sistema e setar num JDateChooser
         * https://www.youtube.com/watch?v=GlBMniOwCnI
         */
        int numeroMax = geraCodigoDAO.gerarCodigoAgendamento();
        int resultado = 0;
        //  JOptionPane.showMessageDialog(null, "Numero"+ numeroMax);
        resultado = numeroMax + 1;
        lblIDAgendamento.setText(String.valueOf(resultado));

    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        gerarCodigoGUIeDataSistema();

        btnPesqMedico.setEnabled(true);
        btnPesqMedico.requestFocus();
        abriTelaListaMedico();
        btnAdicionar.setEnabled(false);
        btnCancelar.setEnabled(true);

    }


    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        /**
         * Na thread de Execução será executado todos os blocos de codigos CASE
         * 2
         */
        int flagBtn = 1;
        progressBar(flagBtn);


    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnAdicionarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusGained
        efeitoAoReceberFoco(btnAdicionar);


    }//GEN-LAST:event_btnAdicionarFocusGained

    private void btnAdicionarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusLost
        btnAdicionar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnAdicionarFocusLost

    private void btnPesqProcedeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesqProcedeFocusGained
        efeitoAoReceberFoco(btnPesqProcede);
    }//GEN-LAST:event_btnPesqProcedeFocusGained

    private void btnLancarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnLancarFocusGained

        lblRsBruto.getText().replaceAll(",", ".");
        efeitoAoReceberFoco(btnLancar);
    }//GEN-LAST:event_btnLancarFocusGained

    private void jDateTxtDtAgendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateTxtDtAgendaFocusGained
//        jDateTxtDtAgenda.setBackground(Color.YELLOW);
//        jDateTxtDtAgenda.setForeground(Color.BLACK);
    }//GEN-LAST:event_jDateTxtDtAgendaFocusGained

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
                 * Botão Exclusão Evento
                 */
                if (boton.getName().equals("Ex")) {

                    /*Evento ao ser clicado executa código*/
                    int excluir = JOptionPane.showConfirmDialog(null, "Deseja excluir registro?", "Informação", JOptionPane.YES_NO_OPTION);

                    if (excluir == JOptionPane.YES_OPTION) {

                        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

                        if (tabela.getSelectedRow() != -1) {
                            // tabelaVendas.removeRowSelectionInterval(linha, linha);
                            model.removeRow(tabela.getSelectedRow());
                            somaTotalProcede();

                        } else {
                            JOptionPane.showMessageDialog(this, "" + "\n Selecione um Registro na Tabela de Vendas", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/systorres/imagens/info.png")));
                        }
                    }
                }

            }

        }

    }//GEN-LAST:event_tabelaMouseClicked

    private void txtRsDescontoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRsDescontoKeyPressed

        if (!txtRsDesconto.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                btnLancar.setEnabled(true);

            }
        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo [Desconto] não\n"
                        + "não pode conteer valor\n"
                        + "nulo"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                String rsDesconto = moeda.format(0.00);
                txtRsDesconto.setText(rsDesconto.replace("R$", "").trim());
                txtRsDesconto.requestFocus();
                txtRsDesconto.setBackground(Color.YELLOW);
                txtRsDesconto.setForeground(Color.BLACK);

            }

        }


    }//GEN-LAST:event_txtRsDescontoKeyPressed

    private void limparCamposSalvamento() {

        //DADOS MÉDICO
        this.lblIdMedico.setText("");
        this.lblRepositNomeMedico.setText("");
        this.lblRepositIDEspecialidade.setText("");
        this.cbEspecialidade.setSelectedItem(null);

        //DADOS PACIENTE 
        this.lblRepositIdPaciente.setText("");
        this.lblRepositPaciente.setText("");
        this.lblRepositDtNascimento.setText("");
        this.lblRepositSexo.setText("");
        this.lblRepositEstadoCivil.setText("");
        this.lblRepositConjuge.setText("");
        this.lblRepositCPFPaciente.setText("");
        this.lblRepositMae.setText("");
        this.lblRepositPai.setText("");
        this.lblRepositCelular.setText("");
        this.lblRepositUF.setText("");
        this.lblRepositCidade.setText("");
        this.lblRepositBairro.setText("");
        this.lblRepositRua.setText("");
        this.jDateTxtDtAgenda.setDate(null);
//        this.txtAreaObserva.setText("");
        this.lblAutoProcedimentos.setText("");
        this.lblRepositProcede.setText("");
        this.lblRSBruto.setText("");
        this.txtRsDesconto.setText("");
        this.lblValorTotal.setText("");
        lblIDAgendamento.setText("");
        this.lblRsBruto.setText("");

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

        }

    }

    private void Salvar() throws ParseException {

        //1º PASSO: SETAMOS TODOS OS VALORES DO CAMPO AGENDAMENTO 
        agendamentoDTO.setIdDto(Integer.parseInt(lblIDAgendamento.getText()));//id
        agendamentoDTO.setFk_pacienteDto(Integer.parseInt(lblRepositIdPaciente.getText()));
        agendamentoDTO.setFk_medicoDto(Integer.parseInt(lblIdMedico.getText()));
        agendamentoDTO.setNomeMedicoDto(lblRepositNomeMedico.getText());//nome do médico 

        // Observação: essa é a forma de capturar um campo do tipo Caixa de combinaçao 
        if (!cbEspecialidade.getSelectedItem().equals("")) {
            agendamentoDTO.setEspecialidadeDto((String) cbEspecialidade.getSelectedItem());//especialidade caixa de combinação

        }

        agendamentoDTO.setCpfDto(lblRepositCPFPaciente.getText());
        agendamentoDTO.setNomeDto(lblRepositPaciente.getText());
        agendamentoDTO.setDataNascDto(lblRepositDtNascimento.getText());
        agendamentoDTO.setSexoDto(lblRepositSexo.getText());
        agendamentoDTO.setEstadoCivilDto(lblRepositEstadoCivil.getText());
        agendamentoDTO.setConjugeDto(lblRepositConjuge.getText());
        agendamentoDTO.setMaeDto(lblRepositMae.getText());
        agendamentoDTO.setPaiDto(lblRepositPai.getText());
        agendamentoDTO.setCelularPrefDto(lblRepositCelular.getText());
        agendamentoDTO.setEmailPrefDto("");
        agendamentoDTO.setUfDto(lblRepositUF.getText());
        agendamentoDTO.setCidadeDto(lblRepositCidade.getText());
        agendamentoDTO.setBairroDto(lblRepositBairro.getText());
        agendamentoDTO.setRuaDto(lblRepositRua.getText());
        agendamentoDTO.setComplementoDto("");
        agendamentoDTO.setnCasaDto("");
        agendamentoDTO.setCEPDto("");

        //DADOS DO AGENDAMENTO 
        //Capturamos a Data no componente jDateChooser e convertemos para String 
        //https://www.guj.com.br/t/erro-java-util-date-cannot-be-cast-to-java-sql-date-resolvido/82451/6
        String DataAgenda = ((JTextField) jDateTxtDtAgenda.getDateEditor().getUiComponent()).getText();
        //Em seguida formatamos de String para Date 
        Date dataConverteBD = formatador.parse(DataAgenda);
        //em seguida setamos essa data que se encontra emcapsulada pelo método set()
        //na Classe AgendaDTO no Atributos [Date dataAgendamentoDto] no método set  
        //utilizamos o seguinte método antes de inserir no banco 
        // this.dataAgendamentoDto =new java.sql.Date( dataAgendamentoDto.getTime());
        //Objetos que tem campo Date no banco de dados 
        agendamentoDTO.setDataAgendamentoDto(dataConverteBD);

        agendamentoDTO.setObservacaoAgendaDto("");
        agendamentoDTO.setStatusAgendamentoDto("AGENDADO");

        //*****************************************************************
        //FÓRUM que deu a dica final para salvar no banco de dados MySQL 
        //no formato americado numeros do tipo Float, Doule, em um campo 
        //Decimal(10,2) no mysql
        //*****************************************************************
        //https://pt.stackoverflow.com/questions/37947/valor-decimal-mysql
        //*******************************************************************
        double valorTotalTratado = MetodoStaticosUtil.converteDoubleUS(lblValorTotal.getText().replace(".", "").replace(",", ".").replace("R$", "").trim());

        agendamentoDTO.setRsTotalAgendamentoDto(valorTotalTratado);

        //DADOS ADICIONAIS RECONHECIMENTO
        agendamentoDTO.setCpuDto(lblRepositorioCPU.getText());
        agendamentoDTO.setHdDto(lblRepositorioHD.getText());
        agendamentoDTO.setUsuarioDto(lblNomeCompletoUsuario.getText());
        agendamentoDTO.setPerfilDto(lblPerfil.getText());
        try {

            if ((flag == 1)) {

                int codProced = 0; //inicializando a variável que irá capturar o codigo o procedimento

                //2º PASSO: CADASTRAMOS OS VALORES SETADOS 
                agendamentoBO.cadastrar(agendamentoDTO);

                //intanciando uma lista para salvar para salvar os itens adicionados
                //na tabela (Camada GUI) 
                listaItensProcedimentosDTO = new ArrayList<>();

                for (int i = 0; i < tabela.getRowCount(); i++) {

                    itemAgendamentoDTO = new ItemAgendamentoDTO();

                    itemAgendamentoDTO.setIdDto(Integer.parseInt(lblIDAgendamento.getText()));// id agendamento    
                    itemAgendamentoDTO.setIdProceDto(Integer.parseInt((String) tabela.getValueAt(i, 0))); //id procedimento
                    itemAgendamentoDTO.setIdEspDto(Integer.parseInt(lblRepositIDEspecialidade.getText()));//id Medico
                    itemAgendamentoDTO.setNomeProcedeDto(String.valueOf(tabela.getValueAt(i, 1)));//Procedimento

                    String rsBrutoRecebe = (String) tabela.getValueAt(i, 2);
                    String rsDescontoRecebe = (String) tabela.getValueAt(i, 3);
                    //*****************************************************************
                    //FÓRUM que deu a dica final para salvar no banco de dados MySQL 
                    //no formato americado numeros do tipo Float, Doule, em um campo 
                    //Decimal(10,2) no mysql
                    //*****************************************************************
                    //https://pt.stackoverflow.com/questions/37947/valor-decimal-mysql
                    //*******************************************************************
                    double rsBruto = MetodoStaticosUtil.converteDoubleUS(rsBrutoRecebe.replace(".", "").replace(",", ".").replace("R$", "").trim());
                    double rsDesconto = MetodoStaticosUtil.converteDoubleUS(rsDescontoRecebe.replace(".", "").replace(",", ".").replace("R$", "").trim());

                    itemAgendamentoDTO.setRsBrutoDto(rsBruto);//R$Bruto
                    itemAgendamentoDTO.setRsDescontoDto(rsDesconto);
                    listaItensProcedimentosDTO.add(itemAgendamentoDTO);
                }

                //3ºPASSO: SALVAMOS OS VALORES DE ITENS DO AGENDAMENTO   E RETORNAMOS TRUE SE VERDADEIRO (CADASTRADO)
                itemAgendamentoDAO.salvarItensAgendamentoLista(listaItensProcedimentosDTO);

                limparCamposSalvamento();

                desabilitarCampos();

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Salvar()" + e.getMessage());

        }

    }

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        int flagBtn = 4;
        progressBar(flagBtn);


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        int flagBtn = 5;
        progressBar(flagBtn);


    }//GEN-LAST:event_btnCancelarActionPerformed

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
            System.out.println("erroViaEmail()" + e.getMessage());
            e.printStackTrace();
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnLancar;
    public static javax.swing.JButton btnPesqMedico;
    public static javax.swing.JButton btnPesqPaciente;
    public static javax.swing.JButton btnPesqProcede;
    private javax.swing.JButton btnSalvar;
    public static javax.swing.JComboBox cbEspecialidade;
    public static com.toedter.calendar.JDateChooser jDateTxtDtAgenda;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lblAutoProcedimentos;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblConjuge;
    private javax.swing.JLabel lblDesconto;
    private javax.swing.JLabel lblDtNascimento;
    private javax.swing.JLabel lblEspecialidades;
    private javax.swing.JLabel lblEstadoCivil;
    private javax.swing.JLabel lblIDAgendamento;
    public static javax.swing.JLabel lblIdMedico;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblMae;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblNomePaciente;
    private javax.swing.JLabel lblPai;
    private javax.swing.JLabel lblProcedimento;
    private javax.swing.JLabel lblRSBruto;
    public static javax.swing.JLabel lblRepositBairro;
    public static javax.swing.JLabel lblRepositCPFPaciente;
    public static javax.swing.JLabel lblRepositCelular;
    public static javax.swing.JLabel lblRepositCidade;
    public static javax.swing.JLabel lblRepositConjuge;
    public static javax.swing.JLabel lblRepositDtNascimento;
    public static javax.swing.JLabel lblRepositEstadoCivil;
    public static javax.swing.JLabel lblRepositIDEspecialidade;
    public static javax.swing.JLabel lblRepositIdPaciente;
    public static javax.swing.JLabel lblRepositMae;
    public static javax.swing.JLabel lblRepositNomeMedico;
    public static javax.swing.JLabel lblRepositPaciente;
    public static javax.swing.JLabel lblRepositPai;
    public static javax.swing.JLabel lblRepositProcede;
    public static javax.swing.JLabel lblRepositRua;
    public static javax.swing.JLabel lblRepositSexo;
    public static javax.swing.JLabel lblRepositUF;
    public static javax.swing.JTextField lblRsBruto;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblUF;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JPanel painelLancamento;
    private javax.swing.JPanel painelNavegação;
    private javax.swing.JPanel painelPaciente;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JPanel painelSobreMedico;
    private javax.swing.JPanel painelValorTotal;
    private javax.swing.JTable tabela;
    public static javax.swing.JFormattedTextField txtRsDesconto;
    // End of variables declaration//GEN-END:variables

    private void desabilitarCampos() {

        this.btnPesqMedico.setEnabled(false);
        this.cbEspecialidade.setEnabled(false);
        this.btnSalvar.setEnabled(false);
        this.jDateTxtDtAgenda.setEnabled(false);
//        this.txtAreaObserva.setEnabled(false);
        this.btnPesqProcede.setEnabled(false);
        this.txtRsDesconto.setEnabled(false);
        this.btnLancar.setEnabled(false);
        this.btnPesqPaciente.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnAdicionar.setEnabled(true);

    }
}
