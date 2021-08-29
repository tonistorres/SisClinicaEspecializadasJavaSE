package br.com.multclin.telas;

import br.com.multclin.bo.PacienteBO;
import br.com.multclin.bo.PacienteBO;

import br.com.multclin.dao.GeraCodigoAutomaticoDAO;
import br.com.multclin.dao.PacienteDAO;
//import br.com.multclin.componentes.ComponentesFormTelaUsuario;
//import br.com.multclin.dao.SetorTramiteInternoDAO;
import br.com.multclin.dao.PacienteDAO;
import br.com.multclin.dto.PacienteDTO;
//import br.com.multclin.dto.ItensDoProtocoloTFDDTO;
//import br.com.multclin.dto.SetorTramiteInternoDTO;

import static br.com.multclin.telas.SubTelaFone.lblRepositCPF;
import static br.com.multclin.telas.SubTelaFone.lblRepositNome;
import static br.com.multclin.telas.SubTelaFone.lblRepositPerfil;

import br.com.multclin.dto.PacienteDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.barraProgresso;
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import static br.com.multclin.telas.TelaPrincipal.lblStatusData;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;
import br.com.multclin.util.JtextFieldSomenteLetras;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author DaTorres
 */
public class TelaPaciente extends javax.swing.JInternalFrame {

    br.com.multclin.telas.SubTelaFonePaciente formFone;

    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();
    PacienteBO pacienteBO = new PacienteBO();

    GeraCodigoAutomaticoDAO geraCodigoDAO = new GeraCodigoAutomaticoDAO();

    Font f = new Font("Tahoma", Font.ITALIC, 12);

    int flag = 0;
    int flagC = 0;

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

    /**
     * Código Mestre Chimura
     */
    private static TelaPaciente instance = null;

    public static TelaPaciente getInstance() {

        if (instance == null) {

            instance = new TelaPaciente();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    private void progressBar(int flagC) {
        //***************
        //PROGRESS BAR //
        //***********************************************************************
        //Contribuição Canal Yoube:https://www.youtube.com/watch?v=co2cITXfXqY//
        //**********************************************************************

        new Thread() {

            public void run() {

                lblLinhaInformativaPaciente.setVisible(true);
                barraProgressoPaciente.setVisible(true);
                barraProgressoPaciente.setBackground(new Color(0, 102, 102));
                barraProgressoPaciente.setForeground(Color.WHITE);
                labelsInvisiveis();

                for (int i = 0; i < 101; i++) {

                    try {
                        //sleep padrão é 100    
                        sleep(50);

                        //recebe o parametro {i) do laço de repetição for
                        barraProgressoPaciente.setValue(i);

                        if (barraProgressoPaciente.getValue() == 1) {
                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 10% a barra de
                             * progresso inicia as configurações inicais
                             * futuramentes podemos fazer testes de redes...
                             *
                             */

                            lblLinhaInformativaPaciente.setText("1% Iniciando Systems...");

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/forms/linha/tempo/systems.png")));
                            jLabelSystem.setVisible(true);

                        } else if (barraProgressoPaciente.getValue() == 20) {

                            lblLinhaInformativaPaciente.setText("20% Testando Conexão... ");
                            lblLinhaInformativaPaciente.setBackground(new Color(0, 0, 128));
                            lblLinhaInformativaPaciente.setForeground(Color.WHITE);
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/forms/linha/tempo/hostgator.png")));
                            jLteste.setVisible(true);

                            fazendoTesteConexao();
                        } else if (barraProgressoPaciente.getValue() == 30) {

                            switch (flagC) {

                                case 1: {

                                    lblLinhaInformativaPaciente.setText("30% linha temporal...");
//                                    lblLinhaInformativaPaciente.setBackground(new Color(60, 179, 113));
//                                    lblLinhaInformativaPaciente.setForeground(Color.BLACK);
//                                    jLabel90Generic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/forms/linha/tempo/BD.png")));
//                                    jLabel90Generic.setVisible(true);
//                                    acaoMouseClicked();

                                    break;
                                }

                                case 2: {

                                    lblLinhaInformativaPaciente.setText("30% Salvando BD...");
                                    lblLinhaInformativaPaciente.setBackground(new Color(60, 179, 113));
                                    lblLinhaInformativaPaciente.setForeground(Color.BLACK);
                                    jLabel90Generic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/forms/linha/tempo/disco.png")));
                                    jLabel90Generic.setVisible(true);
                                    acaoBotaoSalvar();

                                    break;
                                }

                                case 3: {
                                    lblLinhaInformativaPaciente.setText("30% Buscando dados núvem...");

                                    break;
                                }

                            }

                        } else if (barraProgressoPaciente.getValue() == 40) {

                            switch (flagC) {

                                case 1: {

                                    lblLinhaInformativaPaciente.setText("40% Atualizando Idade...");
                                    lblLinhaInformativaPaciente.setBackground(new Color(173, 255, 47));
                                    lblLinhaInformativaPaciente.setForeground(Color.BLACK);

                                    jLabel90Generic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/forms/linha/tempo/idade.png")));
                                    jLabel90Generic.setVisible(true);
                                    calculaIdade();

                                    break;
                                }

                                case 2: {

                                    lblLinhaInformativaPaciente.setText("40% linha temporal...");

                                    break;
                                }

                                case 3: {
                                    lblLinhaInformativaPaciente.setText("40% linha temporal......");

                                    break;
                                }

                            }

                        } else if (barraProgressoPaciente.getValue() == 50) {

                            switch (flagC) {

                                case 1: {
                                    lblLinhaInformativaPaciente.setText("50% Update Registro...");
                                    lblLinhaInformativaPaciente.setBackground(new Color(245, 222, 179));
                                    lblLinhaInformativaPaciente.setForeground(Color.BLACK);

                                    jLabel90Generic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/forms/linha/tempo/disco.png")));
                                    jLabel90Generic.setVisible(true);
                                    flag = 2;
                                    salvarEdicaoProgressBar();
                                    break;
                                }

                                case 2: {

                                    lblLinhaInformativaPaciente.setText("50% linha temporal...");

                                    break;
                                }

                                case 3: {
                                    lblLinhaInformativaPaciente.setText("50% linha temporal...");

                                    break;
                                }

                            }

                        } else if (barraProgressoPaciente.getValue() == 60) {
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            lblLinhaInformativaPaciente.setText("60% linha temporal...");
                            lblLinhaInformativaPaciente.setBackground(Color.WHITE);
                            lblLinhaInformativaPaciente.setForeground(Color.BLACK);
                        } else if (barraProgressoPaciente.getValue() == 70) {
                            lblLinhaInformativaPaciente.setText("70% linha temporal...");
                            lblLinhaInformativaPaciente.setBackground(Color.WHITE);
                            lblLinhaInformativaPaciente.setForeground(Color.BLACK);
                        } else if (barraProgressoPaciente.getValue() == 80) {
                            lblLinhaInformativaPaciente.setText("80% linha temporal...");
                            lblLinhaInformativaPaciente.setBackground(Color.WHITE);
                            lblLinhaInformativaPaciente.setForeground(Color.BLACK);
                        } else if (barraProgressoPaciente.getValue() == 90) {

                            lblLinhaInformativaPaciente.setText("90% linha temporal...");

                            switch (flagC) {

                                case 1: {

                                    lblLinhaInformativaPaciente.setText("Gerando Relatório...");
                                    lblLinhaInformativaPaciente.setBackground(Color.WHITE);
                                    lblLinhaInformativaPaciente.setForeground(Color.RED);
                                    jLabel90Generic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/forms/linha/tempo/relatorio.png")));
                                    jLabel90Generic.setVisible(true);
                                    relatorioFicha();
                                    break;
                                }

                                case 2: {

                                    lblLinhaInformativaPaciente.setText("90% linha temporal...");

                                    break;
                                }

                                case 3: {
                                    lblLinhaInformativaPaciente.setText("90% linha temporal...");

                                    break;
                                }

                            }

                        } else if (barraProgressoPaciente.getValue() == 100) {
                            //  limpaTodosIconesLinhaTemporal();
                            lblLinhaInformativaPaciente.setText("100% Fim da execução...");
                            jLabelFim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/forms/linha/tempo/FimPesquisa.png")));
                            lblLinhaInformativaPaciente.setBackground(Color.WHITE);
                            lblLinhaInformativaPaciente.setForeground(Color.BLACK);
                            jLabelFim.setVisible(true);
                        }

                    } catch (Exception e) {
                        System.out.println("try:" + e.getMessage());
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

            lblLinhaInformativaPaciente.setForeground(Color.red);
            lblLinhaInformativaPaciente.setText("SEM CONEXÃO COM A INTERNET OU ACESSO AO BANCO DE DADOS...");
            lblLinhaInformativaPaciente.setFont(f);
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

    private void labelsInvisiveis() {
        jLabelSystem.setVisible(false);
        jLteste.setVisible(false);
        jLabel90Generic.setVisible(false);
        jLabelFim.setVisible(false);

    }

    public TelaPaciente() {
        initComponents();
        aoCarregarForm();
        personalizarFrontEnd();
        addRowJTable();

        lblLinhaInformativaPaciente.setEnabled(true);
        lblLinhaInformativaPaciente.setEditable(false);

        labelsInvisiveis();

        //Iremos criar dois métodos que colocará estilo em nossas tabelas 
        //https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555744?start=0
        tabela.setDefaultRenderer(Object.class, new br.com.multclin.util.EstiloTabelaRenderer());// classe EstiloTableRenderer Linhas da Tabela         
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
        tabela.setRowHeight(34);

    }

    private String dataNascimentoTratada(String str) {
        String strRecebe = str;
        String strRetorna = strRecebe.replaceAll("/", "");

        System.out.println("Palavra era:   [" + str + "]");
        System.out.println("Palavra ficou: [" + strRetorna + "]");

        return strRetorna;
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
            String DataDeNascimento = txtDataNascimento.getText();
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

                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Data Nascimento deve ser menor que Data Atual.\n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtDataNascimento.requestFocus();

            } else {

                if (calculo <= 150) {

                    txtIdade.setText(Long.toString(calculo));

                    this.cbSexo.setEnabled(true);
                    this.txtConjuge.setEnabled(true);
                    this.txtMae.setEnabled(true);
                    this.txtPai.setEnabled(true);
                    this.cbEstaCivil.setEnabled(true);
                    this.txtGmail.setEnabled(true);
                    this.cbUF.setEnabled(true);
                    this.txtCidade.setEnabled(true);
                    this.txtBairro.setEnabled(true);
                    this.txtRua.setEnabled(true);
                    this.txtComplemento.setEnabled(true);
                    this.txtNCasa.setEnabled(true);

                    this.txtFormatedCEP.setEnabled(true);
                    this.btnCadastroFone.setEnabled(true);
                    btnSalvar.setEnabled(true);

                    cbSexo.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                            + "O Sistema não Aceita uma passoa com mais de 150 anos.\n"
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtNome.requestFocus();

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
        }

    }

    private void gerarCodigo() {

        /**
         * Setar uma Data pega no sistema e setar num JDateChooser
         * https://www.youtube.com/watch?v=GlBMniOwCnI
         */
        int numeroMax = geraCodigoDAO.gerarCodigo();
        int resultado = 0;
        // JOptionPane.showMessageDialog(null, "Numero"+ numeroDaVenda);
        resultado = numeroMax + 1;
        txtID.setText(String.valueOf(resultado));
        txtID.setEditable(false);
        txtID.setEnabled(true);
        txtID.setForeground(Color.BLACK);

        //gerando o codigo customizado para ser utilizado pelo telefone
//        String codigoCustomizado = String.valueOf(resultado).concat("-PAC");
//        lblIDCustomizado.setText(String.valueOf(codigoCustomizado));
    }

    private void personalizarFrontEnd() {

        //Formulario TelaUsuariobtnEmailPreferencial
        this.btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        this.btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        this.btnPesquisaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        this.btnCadastroFone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/linkForms.png")));
        this.btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        this.btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        this.btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png")));
        this.btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        this.btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png")));
//        this.lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
        this.btnCalulaData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/calculator.png")));
    }

    private void desbilitarBotoesCarregamento() {

        //botoes padroes     
        this.btnExcluir.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        //outros
        this.btnValidaCPF.setEnabled(false);

        this.btnCadastroFone.setEnabled(false);

    }

    public void aoCarregarForm() {
        desabilitarCampos();
        desbilitarBotoesCarregamento();
        lblEmail.setEnabled(true);
        cbUF.setSelectedItem("MA");
        txtCidade.setText("MIRANDA DO NORTE");
        txtFormatedCEP.setText("65495000");
//        progressBarraPesquisa.setIndeterminate(true);
    }

    private void verificarCombos() {

        if (cbEstaCivil.getSelectedItem().equals("Selecione...")) {
            cbEstaCivil.setBackground(Color.YELLOW);
        }
        if (cbSexo.getSelectedItem().equals("Selecione...")) {
            cbSexo.setBackground(Color.YELLOW);
        }

    }

    private void acaoInicioDePesquisaBanco() {

        //coloca o cursor em modo de espera 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.lblLinhaInformativaPaciente.setFont(f);
        this.lblLinhaInformativaPaciente.setForeground(Color.BLACK);
        this.lblLinhaInformativaPaciente.setBackground(Color.WHITE);

    }

    private void acaoFimDePesquisNoBanco() {
        //finalizar o modo de espera do cursor 
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    public void addRowJTable() {
        //iniciando Pesquisa em Banco 
        acaoInicioDePesquisaBanco();

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            JButton btnFicha = new JButton();
            btnFicha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/fichaMedica.png")));
            btnFicha.setName("btnFicha");

            list = (ArrayList<PacienteDTO>) pacienteDAO.listarTodos();

            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCpfDto();
                rowData[3] = btnFicha;
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(210);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(42);
            contarClienteTableModel();
            acaoFimDePesquisNoBanco();

        } catch (PersistenciaException ex) {

            //coloca o cursor em modo de espera 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void contarClienteTableModel() {

        int cont = 0;

        try {

            for (int i = 0; i < tabela.getRowCount(); i++) {
                tabela.getValueAt(i, 0);
                cont++;

            }

            lblRepositNumeroClientes.setText(String.valueOf(cont));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void pesquisarUsuarioPorCPF() {

        String pesquisarUsuarioCPF = MetodoStaticosUtil.removerAcentosCaixAlta(txtFormatedPesquisaCPF.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            JButton btnFicha = new JButton();
            btnFicha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/fichaMedica.png")));
            btnFicha.setName("btnFicha");

            list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarPesqRapidaPorCPF(pesquisarUsuarioCPF);

            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCpfDto();
                rowData[3] = btnFicha;
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(210);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(42);

            acaoFimDePesquisNoBanco();

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void pesquisarUsuario() {

        acaoInicioDePesquisaBanco();

        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            JButton btnFicha = new JButton();
            btnFicha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/fichaMedica.png")));
            btnFicha.setName("btnFicha");

            list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarPesqRapida(pesquisarUsuario);
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCpfDto();
                rowData[3] = btnFicha;
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(210);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(42);

            acaoFimDePesquisNoBanco();

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void limparCampos() {

        this.txtID.setText("");
        this.txtCPFPaciente.setText("");
        this.txtNome.setText("");
        this.txtDataNascimento.setText("");
        this.txtIdade.setText("");
        this.cbSexo.setSelectedItem("Selecione...");
        this.txtConjuge.setText("");
        this.txtMae.setText("");
        this.txtPai.setText("");
        this.cbEstaCivil.setSelectedItem("Selecione...");
        this.txtGmail.setText("");
        this.cbUF.setSelectedItem("Selecione");
        this.txtCidade.setText("");
        this.txtBairro.setText("");
        this.txtRua.setText("");
        this.txtComplemento.setText("");
        this.txtNCasa.setText("");
        this.txtFormatedCEP.setText("");
        this.lblFonePreferencial.setText("");
    }

    private void desabilitarCampos() {

        this.txtID.setEnabled(false);
        this.txtCPFPaciente.setEnabled(false);
        this.txtNome.setEnabled(false);
        this.txtDataNascimento.setEnabled(false);
        this.txtIdade.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.txtConjuge.setEnabled(false);
        this.txtMae.setEnabled(false);
        this.txtPai.setEnabled(false);
        this.lblCelular.setEnabled(false);
        this.cbEstaCivil.setEnabled(false);
        this.txtGmail.setEnabled(false);
        this.cbUF.setEnabled(false);
        this.txtCidade.setEnabled(false);
        this.txtBairro.setEnabled(false);
        this.txtRua.setEnabled(false);
        this.txtComplemento.setEnabled(false);
        this.txtNCasa.setEnabled(false);
        this.txtFormatedCEP.setEnabled(false);

        //Campo que ficarão Habilitados por padrão 
        this.txtPesquisa.setEnabled(true);

    }

    private void salvar() {

        pacienteDTO.setIdDto(Integer.parseInt(txtID.getText()));//id
        pacienteDTO.setNomeDto(txtNome.getText());//nome
        pacienteDTO.setCpfDto(txtCPFPaciente.getText());//cpf 

        pacienteDTO.setDataNascDto(txtDataNascimento.getText());
        pacienteDTO.setIdadeDto(txtIdade.getText());//idade

        if (!cbSexo.getSelectedItem().equals("Selecione...")) {
            pacienteDTO.setSexoDto((String) cbSexo.getSelectedItem());//sexo 
        }

        pacienteDTO.setConjugeDto(txtConjuge.getText());
        pacienteDTO.setMaeDto(txtMae.getText());
        pacienteDTO.setPaiDto(txtPai.getText());
        pacienteDTO.setCelularPrefDto(lblFonePreferencial.getText());

        if (!cbEstaCivil.getSelectedItem().equals("Selecione...")) {
            pacienteDTO.setEstadoCivilDto((String) cbEstaCivil.getSelectedItem());//estado

        }

        pacienteDTO.setEmailPrefDto(txtGmail.getText());

        if (!cbUF.getSelectedItem().equals("Selecione...")) {
            pacienteDTO.setUfDto((String) cbUF.getSelectedItem());//UF Estado

        }

        pacienteDTO.setCidadeDto(txtCidade.getText());
        pacienteDTO.setBairroDto(txtBairro.getText());
        pacienteDTO.setRuaDto(txtRua.getText());
        pacienteDTO.setComplementoDto(txtComplemento.getText());
        pacienteDTO.setnCasaDto(txtNCasa.getText());
        pacienteDTO.setCEPDto(txtFormatedCEP.getText());

        try {

            pacienteBO = new PacienteBO();

            if ((pacienteBO.validaCamposFormPaciente(pacienteDTO)) == false) {
                txtNome.setText("");

            } else {

                if ((flag == 1)) {

                    boolean retornoVerifcaDuplicidade = pacienteDAO.verificaDuplicidade(pacienteDTO);

                    if (retornoVerifcaDuplicidade == false) {

                        lblLinhaInformativaPaciente.setBackground(Color.red);
                        lblLinhaInformativaPaciente.setForeground(Color.WHITE);
                        lblLinhaInformativaPaciente.setText("Cadastrando no Banco...");
                        pacienteBO.cadastrar(pacienteDTO);

                        limparCampos();
                        desabilitarCampos();
                        acoesDosBotoesAposSalvarExcluir();

                        lblLinhaInformativaPaciente.setBackground(Color.BLACK);
                        lblLinhaInformativaPaciente.setForeground(Color.WHITE);
                        lblLinhaInformativaPaciente.setText("Cadastrado Sucesso!!");
                        //*****************************************************************************
                        //retornar todos os componente do form que representa campos obrigatório 
                        //para o estado normal
                        //******************************************************************************
                        pinelCivilObrigatorio.setBackground(Color.white);
                        lblEstadoUsuario.setForeground(new Color(0, 102, 102));

                        painelDtNascObrigatorio.setBackground(Color.WHITE);
                        txtDataNascimento.setForeground(Color.BLACK);
                        txtDataNascimento.setBackground(Color.WHITE);

                        painelNomeObrigatorio.setBackground(Color.WHITE);
                        txtNome.setBackground(Color.WHITE);
                        txtNome.setForeground(Color.BLACK);

                        cbSexo.setBackground(Color.WHITE);
                        cbSexo.setForeground(Color.BLACK);
                        cbSexo.setFont(f);
                        paielSexoObrigatorio.setBackground(Color.WHITE);
                        lblSexo.setFont(f);
                        lblSexo.setForeground(new Color(0, 102, 102));

                        painelCidadeObrigatorio.setBackground(Color.WHITE);
                        txtCidade.setBackground(Color.WHITE);
                        txtCidade.setForeground(Color.BLACK);

                        labelsInvisiveis();

                        /**
                         * Zera a Linha informativa criada para esse Sistema
                         */
                        int numeroLinhas = tabela.getRowCount();

                        if (numeroLinhas > 0) {

                            while (tabela.getModel().getRowCount() > 0) {
                                ((DefaultTableModel) tabela.getModel()).removeRow(0);

                            }

                            addRowJTable();

                        } else {
                            addRowJTable();

                        }

                    } else {

                        lblLinhaInformativaPaciente.setText("Verificação efetuada, Login já cadastrado no Sistema");
                        lblLinhaInformativaPaciente.setForeground(Color.RED);

                    }

                } else {

                    /**
                     * Caso não seja um novo registro equivale dizer que é uma
                     * edição então executará esse código flag será !=1
                     */
                    pacienteDTO.setIdDto(Integer.parseInt(txtID.getText()));

                    lblLinhaInformativaPaciente.setBackground(Color.red);
                    lblLinhaInformativaPaciente.setForeground(Color.WHITE);
                    lblLinhaInformativaPaciente.setText("Atualizando no Banco...");
                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    pacienteBO.atualizarBO(pacienteDTO);

                    /**
                     * Após salvar limpar os campos
                     */
                    limparCampos();
                    /**
                     * Bloquear campos e Botões
                     */
                    desabilitarCampos();
                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    acoesDosBotoesAposSalvarExcluir();
                    //tira o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    lblLinhaInformativaPaciente.setBackground(Color.BLACK);
                    lblLinhaInformativaPaciente.setForeground(Color.WHITE);
                    lblLinhaInformativaPaciente.setText("Atualizado com Sucesso!!");

                    labelsInvisiveis();

                    int numeroLinhas = tabela.getRowCount();

                    if (numeroLinhas > 0) {

                        while (tabela.getModel().getRowCount() > 0) {
                            ((DefaultTableModel) tabela.getModel()).removeRow(0);

                        }

                        addRowJTable();

                    } else {
                        addRowJTable();

                    }
                    //tira o cursor do modo load
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //    JOptionPane.showMessageDialog(null, "Camada GUI: " + e.getMessage());

            if (e.getMessage().equalsIgnoreCase("Campo NOME Obrigatório")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.YELLOW);
                painelNomeObrigatorio.setBackground(Color.red);
                txtNome.setBackground(Color.WHITE);
                txtNome.setForeground(Color.red);

            }

            if (e.getMessage().equalsIgnoreCase("Campo CPF Obrigatório")) {
                txtCPFPaciente.requestFocus();
                txtCPFPaciente.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo DATA NASCIMENTO Obrigatório")) {
                txtDataNascimento.requestFocus();
                txtDataNascimento.setForeground(Color.red);
                txtDataNascimento.setBackground(Color.WHITE);
                painelDtNascObrigatorio.setBackground(Color.red);
            }

            if (e.getMessage().equalsIgnoreCase("Campo IDADE Obrigatório")) {
                txtIdade.requestFocus();
                txtIdade.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo SEXO Obrigatorio")) {
                cbSexo.requestFocus();
                cbSexo.setBackground(Color.WHITE);
                cbSexo.setForeground(Color.WHITE);
                cbSexo.setFont(f);
                paielSexoObrigatorio.setBackground(Color.red);
                lblSexo.setFont(f);
                lblSexo.setForeground(Color.WHITE);

            }

            if (e.getMessage().equalsIgnoreCase("Campo ESTADO CIVIL Obrigatório")) {
                cbEstaCivil.requestFocus();
                cbEstaCivil.setBackground(Color.WHITE);
                cbEstaCivil.setForeground(Color.red);
                pinelCivilObrigatorio.setBackground(Color.red);
                lblEstadoUsuario.setForeground(Color.WHITE);

            }

            if (e.getMessage().equalsIgnoreCase("Campo UF Obrigatório")) {
                cbUF.requestFocus();
                cbUF.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo CIDADE Obrigatório")) {
                txtCidade.requestFocus();
                painelCidadeObrigatorio.setBackground(Color.red);
                txtCidade.setBackground(Color.WHITE);
                txtCidade.setForeground(Color.red);

            }

        }

    }

    private void salvarEdicaoProgressBar() {

        //coloca o cursor em modo de espera 
        /**
         * capturando os campos do Form na Camada Gui e em vez de adicionar ha
         * uma variável encapsulamos e setamos como o método set
         */
        pacienteDTO.setIdDto(Integer.parseInt(txtID.getText()));//id
        pacienteDTO.setNomeDto(txtNome.getText());//nome
        pacienteDTO.setCpfDto(txtCPFPaciente.getText());//cpf 

        pacienteDTO.setDataNascDto(txtDataNascimento.getText());
        pacienteDTO.setIdadeDto(txtIdade.getText());//idade

        if (!cbSexo.getSelectedItem().equals("Selecione...")) {
            pacienteDTO.setSexoDto((String) cbSexo.getSelectedItem());//sexo 
        }

        pacienteDTO.setConjugeDto(txtConjuge.getText());
        pacienteDTO.setMaeDto(txtMae.getText());
        pacienteDTO.setPaiDto(txtPai.getText());
        pacienteDTO.setCelularPrefDto(lblFonePreferencial.getText());

        if (!cbEstaCivil.getSelectedItem().equals("Selecione...")) {
            pacienteDTO.setEstadoCivilDto((String) cbEstaCivil.getSelectedItem());//estado

        }

        pacienteDTO.setEmailPrefDto(txtGmail.getText());

        if (!cbUF.getSelectedItem().equals("Selecione...")) {
            pacienteDTO.setUfDto((String) cbUF.getSelectedItem());//UF Estado

        }

        pacienteDTO.setCidadeDto(txtCidade.getText());
        pacienteDTO.setBairroDto(txtBairro.getText());
        pacienteDTO.setRuaDto(txtRua.getText());
        pacienteDTO.setComplementoDto(txtComplemento.getText());
        pacienteDTO.setnCasaDto(txtNCasa.getText());
        pacienteDTO.setCEPDto(txtFormatedCEP.getText());

        try {

            pacienteBO = new PacienteBO();

            if ((pacienteBO.validaCamposFormPaciente(pacienteDTO)) == false) {
                txtNome.setText("");

            } else {

                if ((flag == 1)) {

                    boolean retornoVerifcaDuplicidade = pacienteDAO.verificaDuplicidade(pacienteDTO);

                    if (retornoVerifcaDuplicidade == false) {

                        pacienteBO.cadastrar(pacienteDTO);
                        /**
                         * Após salvar limpar os campos
                         */
                        limparCampos();
                        /**
                         * Desabilitar campos
                         */
                        desabilitarCampos();
                        /**
                         * Acao dos botões após salvar Como ficarão os botões
                         * apos ocorrer o evento de salvar
                         */
                        acoesDosBotoesAposSalvarExcluir();

                        lblLinhaInformativaPaciente.setText("Resgistro Cadastrado com Sucesso");
                        Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
                        lblLinhaInformativaPaciente.setFont(f);
                        lblLinhaInformativaPaciente.setForeground(Color.getHSBColor(51, 153, 255));

                        //tira o cursor em modo de espera 
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    } else {

                        lblLinhaInformativaPaciente.setText("Verificação efetuada, Login já cadastrado no Sistema");
                        lblLinhaInformativaPaciente.setForeground(Color.RED);

                    }

                } else {

                    /**
                     * Caso não seja um novo registro equivale dizer que é uma
                     * edição então executará esse código flag será !=1
                     */
                    pacienteDTO.setIdDto(Integer.parseInt(txtID.getText()));

                    /**
                     * Chamando o método que irá executar a Edição dos Dados em
                     * nosso Banco de Dados
                     */
                    pacienteBO.atualizarBO(pacienteDTO);

                    /**
                     * Bloquear campos e Botões
                     */
                    desabilitarCampos();
                    /**
                     * Liberar campos necessário para operações após salvamento
                     */
                    acoesDosBotoesAposSalvarExcluir();
                    //tira o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada GUI: " + e.getMessage());

            if (e.getMessage().equalsIgnoreCase("Campo NOME Obrigatório")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo CPF Obrigatório")) {
                txtCPFPaciente.requestFocus();
                txtCPFPaciente.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo DATA NASCIMENTO Obrigatório")) {
                txtDataNascimento.requestFocus();
                txtDataNascimento.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo IDADE Obrigatório")) {
                txtIdade.requestFocus();
                txtIdade.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo SEXO Obrigatorio")) {
                cbSexo.requestFocus();
                cbSexo.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo ESTADO CIVIL Obrigatório")) {
                cbEstaCivil.requestFocus();
                cbEstaCivil.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo UF Obrigatório")) {
                cbUF.requestFocus();
                cbUF.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo CIDADE Obrigatório")) {
                txtCidade.requestFocus();
                txtCidade.setBackground(Color.WHITE);
                txtCidade.setForeground(Color.BLACK);
            }

            //
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelDadosUsuario = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        lblDtNascimento = new javax.swing.JLabel();
        lblIdade = new javax.swing.JLabel();
        painelObrigatorio = new javax.swing.JPanel();
        btnValidaCPF = new javax.swing.JButton();
        txtCPFPaciente = new javax.swing.JFormattedTextField();
        lblCPFUsuario = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        lblIDCustomizado = new javax.swing.JLabel();
        txtConjuge = new javax.swing.JTextField();
        lblConjuge = new javax.swing.JLabel();
        txtMae = new javax.swing.JTextField();
        lblMae = new javax.swing.JLabel();
        txtPai = new javax.swing.JTextField();
        lblPai = new javax.swing.JLabel();
        txtIdade = new javax.swing.JTextField();
        btnCalulaData = new javax.swing.JButton();
        painelLinhaTempo = new javax.swing.JPanel();
        barraProgressoPaciente = new javax.swing.JProgressBar();
        lblLinhaInformativaPaciente = new javax.swing.JTextField();
        jLabelSystem = new javax.swing.JLabel();
        jLteste = new javax.swing.JLabel();
        jLabel90Generic = new javax.swing.JLabel();
        jLabelFim = new javax.swing.JLabel();
        painelDtNascObrigatorio = new javax.swing.JPanel();
        txtDataNascimento = new javax.swing.JFormattedTextField();
        painelNomeObrigatorio = new javax.swing.JPanel();
        txtNome =  new JtextFieldSomenteLetras(45);
        paielSexoObrigatorio = new javax.swing.JPanel();
        cbSexo = new javax.swing.JComboBox();
        lblSexo = new javax.swing.JLabel();
        txtGmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        lblFonePreferencial = new javax.swing.JLabel();
        btnCadastroFone = new javax.swing.JButton();
        pinelCivilObrigatorio = new javax.swing.JPanel();
        cbEstaCivil = new javax.swing.JComboBox();
        lblEstadoUsuario = new javax.swing.JLabel();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        painelPrincipalSecundario = new javax.swing.JPanel();
        painelEndereco = new javax.swing.JPanel();
        cbUF = new javax.swing.JComboBox();
        lblUF = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        lblBairro = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lblRua = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        lblComplemento = new javax.swing.JLabel();
        lblNCasa = new javax.swing.JLabel();
        lblCEP = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        txtNCasa = new javax.swing.JTextField();
        txtFormatedCEP = new javax.swing.JFormattedTextField();
        painelCidadeObrigatorio = new javax.swing.JPanel();
        txtCidade = new javax.swing.JTextField();
        painelPesquisaRapida = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        lblPesNome = new javax.swing.JLabel();
        txtFormatedPesquisaCPF = new javax.swing.JFormattedTextField();
        btnPesquisaCPF = new javax.swing.JButton();
        lblCPF = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblClientesCadastrados = new javax.swing.JLabel();
        lblRepositNumeroClientes = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Paciente");
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelDadosUsuario.setBackground(java.awt.Color.white);
        PanelDadosUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsuario.setForeground(new java.awt.Color(0, 102, 102));
        lblUsuario.setText("Paciente:");
        PanelDadosUsuario.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, -1, -1));

        lblDtNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lblDtNascimento.setText("Dt. Nasc.:");
        PanelDadosUsuario.add(lblDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 215, -1, -1));

        lblIdade.setForeground(new java.awt.Color(0, 102, 102));
        lblIdade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIdade.setText("Idade:");
        PanelDadosUsuario.add(lblIdade, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, 40, -1));

        painelObrigatorio.setBackground(new java.awt.Color(0, 102, 102));
        painelObrigatorio.setForeground(new java.awt.Color(0, 102, 102));
        painelObrigatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnValidaCPF.setPreferredSize(new java.awt.Dimension(32, 32));
        btnValidaCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnValidaCPFFocusLost(evt);
            }
        });
        btnValidaCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidaCPFActionPerformed(evt);
            }
        });
        btnValidaCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnValidaCPFKeyPressed(evt);
            }
        });
        painelObrigatorio.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, -1));

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
        painelObrigatorio.add(txtCPFPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 140, 30));

        lblCPFUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCPFUsuario.setForeground(java.awt.Color.white);
        lblCPFUsuario.setText("CPF:");
        painelObrigatorio.add(lblCPFUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        txtID.setEnabled(false);
        painelObrigatorio.add(txtID, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 30, 30));

        lblID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblID.setForeground(java.awt.Color.white);
        lblID.setText("ID:");
        painelObrigatorio.add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        lblIDCustomizado.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        lblIDCustomizado.setForeground(java.awt.Color.orange);
        lblIDCustomizado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelObrigatorio.add(lblIDCustomizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 60, 15));

        PanelDadosUsuario.add(painelObrigatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 340, 52));

        txtConjuge.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtConjugeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtConjugeFocusLost(evt);
            }
        });
        txtConjuge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtConjugeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtConjugeKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtConjuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 290, 30));

        lblConjuge.setForeground(new java.awt.Color(0, 102, 102));
        lblConjuge.setText("Conjuge:");
        PanelDadosUsuario.add(lblConjuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        txtMae.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaeFocusLost(evt);
            }
        });
        txtMae.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMaeKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 310, 30));

        lblMae.setForeground(new java.awt.Color(0, 102, 102));
        lblMae.setText("Mãe:");
        PanelDadosUsuario.add(lblMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, -1));

        txtPai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPaiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPaiFocusLost(evt);
            }
        });
        txtPai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPaiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPaiKeyTyped(evt);
            }
        });
        PanelDadosUsuario.add(txtPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 310, 30));

        lblPai.setForeground(new java.awt.Color(0, 102, 102));
        lblPai.setText("Pai:");
        PanelDadosUsuario.add(lblPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        txtIdade.setPreferredSize(new java.awt.Dimension(6, 30));
        PanelDadosUsuario.add(txtIdade, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 235, 40, -1));

        btnCalulaData.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnCalulaDataFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnCalulaDataFocusLost(evt);
            }
        });
        btnCalulaData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalulaDataActionPerformed(evt);
            }
        });
        PanelDadosUsuario.add(btnCalulaData, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 235, 30, 30));

        painelLinhaTempo.setBackground(java.awt.Color.white);
        painelLinhaTempo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        barraProgressoPaciente.setBackground(new java.awt.Color(0, 102, 102));
        barraProgressoPaciente.setForeground(java.awt.Color.white);
        painelLinhaTempo.add(barraProgressoPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 37, 110, 22));

        lblLinhaInformativaPaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblLinhaInformativaPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelLinhaTempo.add(lblLinhaInformativaPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 0, 348, 24));
        painelLinhaTempo.add(jLabelSystem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 32, 32));
        painelLinhaTempo.add(jLteste, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 25, 33, 33));
        painelLinhaTempo.add(jLabel90Generic, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 25, 33, 33));
        painelLinhaTempo.add(jLabelFim, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 25, 60, 33));

        PanelDadosUsuario.add(painelLinhaTempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 350, 67));

        painelDtNascObrigatorio.setBackground(java.awt.Color.white);
        painelDtNascObrigatorio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelDtNascObrigatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        try {
            txtDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataNascimento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDataNascimento.setPreferredSize(new java.awt.Dimension(6, 30));
        txtDataNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataNascimentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDataNascimentoFocusLost(evt);
            }
        });
        txtDataNascimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDataNascimentoKeyPressed(evt);
            }
        });
        painelDtNascObrigatorio.add(txtDataNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 3, 90, -1));

        PanelDadosUsuario.add(painelDtNascObrigatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 100, 36));

        painelNomeObrigatorio.setBackground(java.awt.Color.white);
        painelNomeObrigatorio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelNomeObrigatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        painelNomeObrigatorio.add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 3, 270, 30));

        PanelDadosUsuario.add(painelNomeObrigatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 280, 36));

        paielSexoObrigatorio.setBackground(java.awt.Color.white);
        paielSexoObrigatorio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        paielSexoObrigatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbSexo.setBackground(new java.awt.Color(152, 251, 152));
        cbSexo.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbSexo.setForeground(new java.awt.Color(0, 102, 102));
        cbSexo.setMaximumRowCount(9);
        cbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "MASCULINO", "FEMININO", "OUTROS" }));
        cbSexo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(152, 251, 152)));
        cbSexo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbSexoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbSexoFocusLost(evt);
            }
        });
        cbSexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSexoActionPerformed(evt);
            }
        });
        cbSexo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbSexoKeyPressed(evt);
            }
        });
        paielSexoObrigatorio.add(cbSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 5, 100, 30));

        lblSexo.setForeground(new java.awt.Color(0, 102, 102));
        lblSexo.setText("Sexo:");
        paielSexoObrigatorio.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 40, -1));

        PanelDadosUsuario.add(paielSexoObrigatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 225, 150, 40));

        txtGmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGmail.setForeground(new java.awt.Color(0, 102, 102));
        PanelDadosUsuario.add(txtGmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, 300, 30));

        lblEmail.setForeground(new java.awt.Color(0, 102, 102));
        lblEmail.setText("Email:");
        PanelDadosUsuario.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, -1, -1));

        lblCelular.setForeground(new java.awt.Color(0, 102, 102));
        lblCelular.setText("Celular:");
        PanelDadosUsuario.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 400, 50, -1));

        lblFonePreferencial.setForeground(new java.awt.Color(0, 102, 102));
        lblFonePreferencial.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        PanelDadosUsuario.add(lblFonePreferencial, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 100, 30));

        btnCadastroFone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroFoneActionPerformed(evt);
            }
        });
        PanelDadosUsuario.add(btnCadastroFone, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 31, 31));

        pinelCivilObrigatorio.setBackground(java.awt.Color.white);
        pinelCivilObrigatorio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        pinelCivilObrigatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbEstaCivil.setBackground(new java.awt.Color(152, 251, 152));
        cbEstaCivil.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbEstaCivil.setForeground(new java.awt.Color(0, 102, 102));
        cbEstaCivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "SOLTEIRO(A)", "CASADO(A)", "VIUVO(A)", "OUTROS" }));
        cbEstaCivil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(152, 251, 152)));
        pinelCivilObrigatorio.add(cbEstaCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 2, 118, 30));

        lblEstadoUsuario.setForeground(new java.awt.Color(0, 102, 102));
        lblEstadoUsuario.setText("Civil:");
        pinelCivilObrigatorio.add(lblEstadoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        PanelDadosUsuario.add(pinelCivilObrigatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 160, 34));

        getContentPane().add(PanelDadosUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 49, 360, 500));

        PanelBotoesManipulacaoBancoDados.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.disabledShadow"));

        btnCancelar.setToolTipText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
        });
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setToolTipText("Gravar Registro");
        btnSalvar.setEnabled(false);
        btnSalvar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnSalvar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnSalvarFocusGained(evt);
            }
        });
        btnSalvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalvarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalvarMouseExited(evt);
            }
        });
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        btnSalvar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSalvarKeyPressed(evt);
            }
        });

        btnEditar.setToolTipText("Editar Registro");
        btnEditar.setEnabled(false);
        btnEditar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEditarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnEditarFocusLost(evt);
            }
        });
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditarMouseExited(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnAdicionar.setToolTipText("Adicionar Registro");
        btnAdicionar.setPreferredSize(new java.awt.Dimension(45, 45));
        btnAdicionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdicionarMouseExited(evt);
            }
        });
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnExcluir.setToolTipText("Excluir Registro");
        btnExcluir.setEnabled(false);
        btnExcluir.setPreferredSize(new java.awt.Dimension(45, 45));
        btnExcluir.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnExcluirFocusGained(evt);
            }
        });
        btnExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcluirMouseExited(evt);
            }
        });
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelBotoesManipulacaoBancoDadosLayout = new javax.swing.GroupLayout(PanelBotoesManipulacaoBancoDados);
        PanelBotoesManipulacaoBancoDados.setLayout(PanelBotoesManipulacaoBancoDadosLayout);
        PanelBotoesManipulacaoBancoDadosLayout.setHorizontalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        PanelBotoesManipulacaoBancoDadosLayout.setVerticalGroup(
            PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(PanelBotoesManipulacaoBancoDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, -1));

        painelPrincipalSecundario.setBackground(java.awt.Color.white);
        painelPrincipalSecundario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Endereço:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelEndereco.setForeground(new java.awt.Color(0, 102, 102));
        painelEndereco.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbUF.setBackground(new java.awt.Color(152, 251, 152));
        cbUF.setForeground(new java.awt.Color(0, 102, 102));
        cbUF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", " " }));
        cbUF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(152, 251, 152)));
        cbUF.setPreferredSize(new java.awt.Dimension(56, 30));
        cbUF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbUFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbUFFocusLost(evt);
            }
        });
        cbUF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbUFKeyPressed(evt);
            }
        });
        painelEndereco.add(cbUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 35, 97, -1));

        lblUF.setForeground(new java.awt.Color(0, 102, 102));
        lblUF.setText("UF:");
        painelEndereco.add(lblUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 15, -1, -1));

        lblCidade.setForeground(new java.awt.Color(0, 102, 102));
        lblCidade.setText("Cidade:");
        painelEndereco.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 15, -1, -1));

        lblBairro.setForeground(new java.awt.Color(0, 102, 102));
        lblBairro.setText("Bairro:");
        painelEndereco.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        txtBairro.setForeground(new java.awt.Color(0, 102, 102));
        txtBairro.setPreferredSize(new java.awt.Dimension(59, 30));
        txtBairro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBairroFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBairroFocusLost(evt);
            }
        });
        txtBairro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBairroKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBairroKeyTyped(evt);
            }
        });
        painelEndereco.add(txtBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 85, 140, -1));

        lblRua.setForeground(new java.awt.Color(0, 102, 102));
        lblRua.setText("Rua:");
        painelEndereco.add(lblRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, -1));

        txtRua.setForeground(new java.awt.Color(0, 102, 102));
        txtRua.setPreferredSize(new java.awt.Dimension(6, 30));
        txtRua.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRuaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRuaFocusLost(evt);
            }
        });
        txtRua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRuaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRuaKeyTyped(evt);
            }
        });
        painelEndereco.add(txtRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 85, 250, -1));

        lblComplemento.setForeground(new java.awt.Color(0, 102, 102));
        lblComplemento.setText("Complemento:");
        painelEndereco.add(lblComplemento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        lblNCasa.setForeground(new java.awt.Color(0, 102, 102));
        lblNCasa.setText("NºCasa:");
        painelEndereco.add(lblNCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, -1));

        lblCEP.setForeground(new java.awt.Color(0, 102, 102));
        lblCEP.setText("CEP:");
        painelEndereco.add(lblCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, -1, -1));

        txtComplemento.setForeground(new java.awt.Color(0, 102, 102));
        txtComplemento.setPreferredSize(new java.awt.Dimension(59, 30));
        txtComplemento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComplementoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComplementoFocusLost(evt);
            }
        });
        txtComplemento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtComplementoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtComplementoKeyTyped(evt);
            }
        });
        painelEndereco.add(txtComplemento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, 220, -1));

        txtNCasa.setForeground(new java.awt.Color(0, 102, 102));
        txtNCasa.setPreferredSize(new java.awt.Dimension(59, 30));
        txtNCasa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNCasaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNCasaFocusLost(evt);
            }
        });
        txtNCasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNCasaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNCasaKeyTyped(evt);
            }
        });
        painelEndereco.add(txtNCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 135, 55, -1));

        txtFormatedCEP.setForeground(new java.awt.Color(0, 102, 102));
        try {
            txtFormatedCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFormatedCEP.setPreferredSize(new java.awt.Dimension(6, 30));
        txtFormatedCEP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFormatedCEPFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFormatedCEPFocusLost(evt);
            }
        });
        txtFormatedCEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFormatedCEPKeyPressed(evt);
            }
        });
        painelEndereco.add(txtFormatedCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 135, 110, -1));

        painelCidadeObrigatorio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelCidadeObrigatorio.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCidade.setForeground(new java.awt.Color(152, 251, 152));
        txtCidade.setPreferredSize(new java.awt.Dimension(6, 30));
        txtCidade.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCidadeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCidadeFocusLost(evt);
            }
        });
        txtCidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCidadeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCidadeKeyTyped(evt);
            }
        });
        painelCidadeObrigatorio.add(txtCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 3, 280, -1));

        painelEndereco.add(painelCidadeObrigatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 290, 36));

        painelPrincipalSecundario.add(painelEndereco, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 420, 180));

        painelPesquisaRapida.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa Rápida:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPesquisaRapida.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        painelPesquisaRapida.add(txtPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 160, 35));

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
        painelPesquisaRapida.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 32, 32));

        lblPesNome.setBackground(new java.awt.Color(0, 102, 102));
        lblPesNome.setForeground(new java.awt.Color(0, 102, 102));
        lblPesNome.setText("Nome:");
        painelPesquisaRapida.add(lblPesNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

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
        painelPesquisaRapida.add(txtFormatedPesquisaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 120, 30));

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
        painelPesquisaRapida.add(btnPesquisaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 40, 30, 30));

        lblCPF.setForeground(new java.awt.Color(0, 102, 102));
        lblCPF.setText("CPF:");
        painelPesquisaRapida.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        painelPrincipalSecundario.add(painelPesquisaRapida, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 420, 90));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME", "CPF", "Ficha"
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
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(220);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(42);
        }

        painelPrincipalSecundario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 440, 220));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelPrincipalSecundario.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 295, 360, 25));

        lblClientesCadastrados.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblClientesCadastrados.setForeground(new java.awt.Color(0, 102, 102));
        lblClientesCadastrados.setText("Nº Pacientes-->>");
        painelPrincipalSecundario.add(lblClientesCadastrados, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 522, 110, 20));

        lblRepositNumeroClientes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblRepositNumeroClientes.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositNumeroClientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositNumeroClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPrincipalSecundario.add(lblRepositNumeroClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 522, 50, 20));

        getContentPane().add(painelPrincipalSecundario, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 460, 550));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed

        if (!txtNome.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                txtNome.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText().trim()));
                btnCadastroFone.setEnabled(true);

                txtDataNascimento.requestFocus();
                txtDataNascimento.setBackground(Color.YELLOW);
                txtDataNascimento.setForeground(Color.BLACK);
            }
        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo é Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
        int numeroDeCaracter = 44;

        if (txtNome.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 44 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtNome.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 44 caracteres");

        }
    }//GEN-LAST:event_txtNomeKeyTyped

    private void acaoBtnCancelar() {

        /**
         * Limpar os Campos
         */
        limparCampos();
        /**
         * DESABILITAR CAMPOS
         */

        desabilitarCampos();

        /**
         * ao clicar em btnAdicionar fazemos com que btnSalvar fique habilitado
         * para um eventual salvamento
         */
        this.btnSalvar.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        this.btnAdicionar.setEnabled(true);
        this.btnCadastroFone.setEnabled(false);

        this.btnValidaCPF.setEnabled(false);
        this.btnEditar.setEnabled(false);
        this.btnExcluir.setEnabled(false);
        this.btnAdicionar.requestFocus();

        labelsInvisiveis();
        JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                + "Registro Cancelado."
                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
    }


    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        acaoBtnCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void acoesDosBotoesAposSalvarExcluir() {
        btnAdicionar.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnEditar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnPesquisar.setEnabled(true);
//    outros 
        btnValidaCPF.setEnabled(false);
        btnCadastroFone.setEnabled(false);

    }


    private void btnSalvarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnSalvarFocusGained

    }//GEN-LAST:event_btnSalvarFocusGained

    private void acaoBotaoSalvar() {

        //coloca o cursor em modo de espera 
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            salvar();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }

    }

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        flagC = 2;
        progressBar(flagC);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void acaoBotaoEditar() {
        if (txtNome.equals("")) {
            labelsInvisiveis();

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Para que se possa EDITAR é necessário \n"
                    + "que haja um registro selecionado"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } else {
            /**
             * Quando clicar em Editar essa flag recebe o valor de 2
             */

            flag = 2;

            labelsInvisiveis();
            //habilitar campos
            this.txtNome.setEnabled(true);
            this.txtDataNascimento.setEnabled(true);
            this.cbSexo.setEnabled(true);
            this.txtConjuge.setEnabled(true);
            this.txtMae.setEnabled(true);
            this.txtPai.setEnabled(true);
            this.cbEstaCivil.setEnabled(true);
            this.txtGmail.setEnabled(true);
            this.cbUF.setEnabled(true);
            this.txtCidade.setEnabled(true);
            this.txtBairro.setEnabled(true);
            this.txtRua.setEnabled(true);
            this.txtComplemento.setEnabled(true);
            this.txtNCasa.setEnabled(true);
            this.txtFormatedCEP.setEnabled(true);
            this.txtCPFPaciente.setEnabled(true);
            btnValidaCPF.setEnabled(true);

            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);
            //outros botoes
            btnCadastroFone.setEnabled(true);

        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (lblPerfil.getText().equals("ANALISTA") || lblPerfil.getText().equals("ADMIN")) {
            acaoBotaoEditar();
        } else {
            lblLinhaInformativaPaciente.setText("");
            lblLinhaInformativaPaciente.setText("Procure um Administrador para efetuar EDIÇÃO");
            lblLinhaInformativaPaciente.setFont(f);
            lblLinhaInformativaPaciente.setForeground(Color.red);
        }


    }//GEN-LAST:event_btnEditarActionPerformed

    private void habilitarCampos() {
        this.txtNome.setEnabled(true);
        this.txtIdade.setEnabled(true);
        this.cbSexo.setEnabled(true);
        this.cbEstaCivil.setEnabled(true);

        /**
         * Limpar os campos para cadastrar
         */
        //txtIDUsuario.setVisible(false);
        this.txtNome.setText("");
        this.txtIdade.setText("");

        this.lblFonePreferencial.setText("");

        /**
         * Botões que deverão ficar habilitados nesse evento para esse tipo de
         * Formulario
         */
        this.btnAdicionar.setEnabled(false);
        this.btnSalvar.setEnabled(true);
        this.btnCancelar.setEnabled(true);

        /**
         * Botões que deverão ficar desabilitados nesse evento para esse tipo de
         * Formulario
         */
        this.txtPesquisa.setEnabled(false);
        this.btnEditar.setEnabled(false);

        this.txtNome.requestFocus();//setar o campo nome Bairro após adicionar
        this.txtNome.setBackground(Color.WHITE);  // altera a cor do fundo
        this.txtNome.setForeground(new Color(0, 102, 102));

        this.cbSexo.setSelectedItem("Selecione...");

        this.cbEstaCivil.setSelectedItem("Selecione...");

        verificarCombos();
    }

    private void habilitarCamposForm() {

        this.txtCPFPaciente.setEnabled(true);
        this.txtNome.setEnabled(true);
        this.txtDataNascimento.setEnabled(true);
        this.cbSexo.setEnabled(true);
        this.txtConjuge.setEnabled(true);
        this.txtMae.setEnabled(true);
        this.txtPai.setEnabled(true);
        this.cbEstaCivil.setEnabled(true);
        this.txtGmail.setEnabled(true);
        this.cbUF.setEnabled(true);
        this.txtCidade.setEnabled(true);
        this.txtBairro.setEnabled(true);
        this.txtRua.setEnabled(true);
        this.txtComplemento.setEnabled(true);
        this.txtNCasa.setEnabled(true);
        this.txtFormatedCEP.setEnabled(true);

    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        //limpar campos
        this.txtID.setText("");
        this.txtCPFPaciente.setText("");
        this.txtNome.setText("");
        this.txtDataNascimento.setText("");
        this.txtIdade.setText("");
        this.cbSexo.setSelectedItem("Selecione...");
        this.txtConjuge.setText("");
        this.txtMae.setText("");
        this.txtPai.setText("");
        this.cbEstaCivil.setSelectedItem("Selecione...");
        this.txtGmail.setText("");
        this.txtBairro.setText("");
        this.txtRua.setText("");
        this.txtComplemento.setText("");
        this.txtNCasa.setText("");
        gerarCodigo();
        txtCPFPaciente.setEnabled(true);
        btnValidaCPF.setEnabled(true);
        btnCancelar.setEnabled(true);
        txtCPFPaciente.requestFocus();
        labelsInvisiveis();

    }
    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void acaoExcluirRegistro() {

        /*Evento ao ser clicado executa código*/
        int excluir = JOptionPane.showConfirmDialog(null, "Deseja Excluir Registro?", "Informação", JOptionPane.YES_NO_OPTION);

        if (excluir == JOptionPane.YES_OPTION) {
            pacienteDTO.setIdDto(Integer.parseInt(txtID.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            pacienteBO.ExcluirBO(pacienteDTO);
            /**
             * Após salvar limpar os campos
             */

            limparCampos();
            desabilitarCampos();
            acoesDosBotoesAposSalvarExcluir();
            labelsInvisiveis();

            try {
                /**
                 * Conta o Número de linhas na minha tabela e armazena na
                 * variável numeroLinas
                 * https://www.youtube.com/watch?v=1fKwn-Vd0uc
                 */
                int numeroLinhas = tabela.getRowCount();
                if (numeroLinhas > 0) {

                    //http://andersonneto.blogspot.com.br/2015/05/tutorial-remover-todas-as-linhas-de-um.html
                    while (tabela.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tabela.getModel()).removeRow(0);
                    }
                    addRowJTable();
                } else {
                    addRowJTable();
                }

            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
        }

    }

    private void acaoBotaoExcluir() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {

            acaoExcluirRegistro();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            desabilitarCampos();

        }

    }

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed

        if (lblPerfil.getText().equals("ANALISTA") || lblPerfil.getText().equals("ADMIN")) {
            acaoBotaoExcluir();
        } else {
            lblLinhaInformativaPaciente.setText("");
            lblLinhaInformativaPaciente.setText("Somente Usuário do Tipo Administrador pode EXCLUIR");
            lblLinhaInformativaPaciente.setFont(f);
            lblLinhaInformativaPaciente.setForeground(Color.red);
        }


    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed

        if (txtPesquisa.getText().equalsIgnoreCase("")) {

            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtPesquisa.requestFocus();
                txtPesquisa.setBackground(Color.YELLOW);
                txtPesquisa.setForeground(Color.BLACK);

                lblLinhaInformativaPaciente.setText("");
                lblLinhaInformativaPaciente.setText("Digite o Registro a ser pesquisado [ENTER]");
                lblLinhaInformativaPaciente.setForeground(Color.red);
                lblLinhaInformativaPaciente.setFont(f);
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
            }

        }

        if (!txtPesquisa.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                btnPesquisar.requestFocus();

                //coloca o cursor em modo de espera 
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

                            pesquisarUsuario();
                        } else {
                            addRowJTable();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    //coloca o cursor em modo de espera 
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                    JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                            + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                }
            }
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed

    private void relatorioFicha() {

        ConexaoUtil conecta = new ConexaoUtil();

        acaoInicioDePesquisaBanco();

        try {

            String capturado = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
            int codigo = Integer.parseInt(capturado);
            InputStream jrxmlStream = TelaPaciente.class.getResourceAsStream("/ireport/ficha_consulta.xml");

            //compilamos o arquivo 
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            Map<String, Object> parametros = new HashMap<>();

            lblLinhaInformativaPaciente.setText("");
            lblLinhaInformativaPaciente.setText("Compilando relatório...");
            lblLinhaInformativaPaciente.setBackground(Color.RED);
            lblLinhaInformativaPaciente.setForeground(Color.WHITE);

            parametros.put("CONDICAO_ID", codigo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conecta.getConnection());

            File file = new File("C:/ireport/ficha_consulta.jrprint");

            if (!file.exists()) {
                //iremos entao criá-lo
                file.createNewFile();
                JRSaver.saveObject(jasperPrint, file);

                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação

                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 

                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/ficha_consulta.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();

            } else {//caso contrário, ou seja, se não  existir 
                //vamos exportar o arquivo no formato pdf
                JRPdfExporter exporterPdf = new JRPdfExporter();
                //aqui é o input que irá alimentar o arquivo pdf que no caso arqui jasperPrint
                //esse método abaixo é uma interface que encapsula o objeto jasperprint
                //preparando para exportação

                ExporterInput input = new SimpleExporterInput(jasperPrint);
                exporterPdf.setExporterInput(input);
                //agora iremos definir o caminho onde ficará o arquivo gerado
                //esse caminho é encapsulado pela classe SimpleOutputStreamExporterOutput 

                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/ficha_consulta.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();
            }

            lblLinhaInformativaPaciente.setText("");
            lblLinhaInformativaPaciente.setText("Abrindo Relatório...");
            lblLinhaInformativaPaciente.setBackground(new Color(0, 102, 102));
            lblLinhaInformativaPaciente.setForeground(Color.WHITE);

            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);
            acaoFimDePesquisNoBanco();
        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "relatorioFicha()");
            ex.printStackTrace();
            //   JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
        }

    }

    private void acaoMouseClicked() {

        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigo = Integer.parseInt("" + tabela.getValueAt(tabela.getSelectedRow(), 0));

        try {

            PacienteDTO retorno = pacienteDAO.buscarPorIdTblConsultaList(codigo);

            if (retorno.getIdDto() != null || !retorno.getIdDto().equals("")) {
                /**
                 * String.valueOf() pega um Valor Inteiro e transforma em String
                 * e seta em txtIDMedico que é um campo do tipo texto
                 */

                pacienteDTO.setIdDto(codigo);
                txtID.setText(String.valueOf(retorno.getIdDto())); //id
                txtCPFPaciente.setText(retorno.getCpfDto());//cpf
                txtNome.setText(retorno.getNomeDto());//nome
                txtDataNascimento.setText(retorno.getDataNascDto());
                txtIdade.setText(retorno.getIdadeDto());
                cbSexo.setSelectedItem(retorno.getSexoDto());
                txtConjuge.setText(retorno.getConjugeDto());
                txtMae.setText(retorno.getMaeDto());
                txtPai.setText(retorno.getPaiDto());
                lblFonePreferencial.setText(retorno.getCelularPrefDto());
                cbEstaCivil.setSelectedItem(retorno.getEstadoCivilDto());
                txtGmail.setText(retorno.getEmailPrefDto());
                cbUF.setSelectedItem(retorno.getUfDto());
                txtCidade.setText(retorno.getCidadeDto());
                txtBairro.setText(retorno.getBairroDto());
                txtRua.setText(retorno.getRuaDto());
                txtComplemento.setText(retorno.getComplementoDto());
                txtNCasa.setText(retorno.getnCasaDto());
                txtFormatedCEP.setText(retorno.getCEPDto());

                /**
                 * Liberar os botões abaixo
                 */
                btnEditar.setEnabled(true);
                btnExcluir.setEnabled(true);
                btnAdicionar.setEnabled(false);
                btnCancelar.setEnabled(true);

            } else {

                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Registro não foi encontrado."
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + ex.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }
    }

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoMouseClicked();
            //https://www.youtube.com/watch?v=jPfKFm2Yfow
            int coluna = tabela.getColumnModel().getColumnIndexAtX(evt.getX());
            int linha = evt.getY() / tabela.getRowHeight();

            if (linha < tabela.getRowCount() && linha >= 0 && coluna < tabela.getColumnCount() && coluna >= 0) {
                Object value = tabela.getValueAt(linha, coluna);
                if (value instanceof JButton) {
                    ((JButton) value).doClick();
                    JButton boton = (JButton) value;

                    /**
                     * btnFicha.setName("btnFicha"); Botão Exclusão Evento
                     */
                    if (boton.getName().equals("btnFicha")) {

                        flagC = 1;
                        progressBar(flagC);

                    }

                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }


    }//GEN-LAST:event_tabelaMouseClicked

    private void btnAdicionarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseEntered
        btnAdicionar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnAdicionarMouseEntered

    private void btnAdicionarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAdicionarMouseExited
        btnAdicionar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnAdicionarMouseExited

    private void btnExcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseEntered
        btnExcluir.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnExcluirMouseEntered

    private void btnExcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcluirMouseExited
        btnExcluir.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnExcluirMouseExited

    private void btnEditarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseEntered
        btnEditar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnEditarMouseEntered

    private void btnEditarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseExited
        btnEditar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnEditarMouseExited

    private void btnSalvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseEntered
        btnSalvar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnSalvarMouseEntered

    private void btnSalvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalvarMouseExited
        btnSalvar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnSalvarMouseExited

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_btnCancelarMouseExited

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

        lblLinhaInformativaPaciente.setText("Fim da Pesquisa.");
        lblLinhaInformativaPaciente.setFont(f);
        lblLinhaInformativaPaciente.setForeground(Color.ORANGE);

    }

    private void acaoBotaoPesquisar() {

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisarUsuario();

        } else {
            addRowJTable();
        }

    }


    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed

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
    }//GEN-LAST:event_btnPesquisarActionPerformed


    private void txtNomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusGained
//        txtNome.setBackground(new Color(0, 102, 102));
//        txtNome.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Informe o Nome");
        lblLinhaInformativaPaciente.setFont(f);
    }//GEN-LAST:event_txtNomeFocusGained

    private void txtNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusLost
        txtNome.setBackground(Color.WHITE);
        txtNome.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");

    }//GEN-LAST:event_txtNomeFocusLost

    private void txtCPFPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFPacienteFocusGained
        txtCPFPaciente.setBackground(Color.YELLOW);
        lblLinhaInformativaPaciente.setFont(f);
        lblLinhaInformativaPaciente.setForeground(new Color(0, 102, 102));
        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite o CPF");


    }//GEN-LAST:event_txtCPFPacienteFocusGained

    private void txtCPFPacienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFPacienteFocusLost
        txtCPFPaciente.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCPFPacienteFocusLost

    private void txtCPFPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFPacienteActionPerformed

    private void txtCPFPacienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFPacienteKeyPressed

        if (!txtCPFPaciente.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                //this.btnValidaCPF.requestFocus();
                acaoValidaCPFPolindoDados();
            }
        }
        if (txtCPFPaciente.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                txtCPFPaciente.requestFocus();
                JOptionPane.showMessageDialog(this, "" + "\n Obrigatório CPF.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }
        }

    }//GEN-LAST:event_txtCPFPacienteKeyPressed

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained
        btnValidaCPF.setBackground(Color.YELLOW);
        this.lblLinhaInformativaPaciente.setFont(f);
        this.lblLinhaInformativaPaciente.setForeground(new Color(0, 102, 102));
        this.lblLinhaInformativaPaciente.setText("");
        this.lblLinhaInformativaPaciente.setText("Click sobre o [BOTÃO] ou Pressione [ENTER]");
    }//GEN-LAST:event_btnValidaCPFFocusGained

    private void btnValidaCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusLost
        this.btnValidaCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnValidaCPFFocusLost

    private void acaoValidaCPFPolindoDados() {

        /**
         * Primeiro criamos uma String com o nome de [CNPJ] e capturamos o valor
         * digitado no campo txtCNPJ por meio do método getText() onde ficará
         * armazenado na variável CNPJ criado para receber o valor capturado
         * pelou usuário.
         */
        pacienteDTO.setCpfDto(this.txtCPFPaciente.getText());

        try {
            //robo conectado ao servidor google 
            boolean retornoVerifcaDuplicidade = pacienteDAO.verificaDuplicidadeCPF(pacienteDTO);//verificar se já existe CNPJ

            if (retornoVerifcaDuplicidade == false) {

                /**
                 * Criamos um contador que será incrementado a medida que
                 * estiver sendo executado na string passando por cada caracter
                 * da mesma e nos dando a posição exata onde se encontra para
                 * que possamos fazer uma intervenção exata.
                 */
                int cont = 0;

                /**
                 * Inicia-se o for que irá percorrer o tamanho total da variável
                 * CNPJ que guarda o valor capturado do campo txtCNPJ
                 */
                for (int i = 0; i < pacienteDTO.getCpfDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (pacienteDTO.getCpfDto().charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             *
                             */
                            pacienteDTO.setCpfDto(pacienteDTO.getCpfDto().replace(pacienteDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 12) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (pacienteDTO.getCpfDto().charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            pacienteDTO.setCpfDto(pacienteDTO.getCpfDto().replace(pacienteDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cpfTratado = pacienteDTO.getCpfDto().replace(" ", "");

                /**
                 * A baixo fazemos a aplicação da função que irá validar se o
                 * cnpj é válido ou não isCNPJ
                 */
                boolean recebeCPF = MetodoStaticosUtil.isCPF(cpfTratado);
                /**
                 * se o retorno for verdadeiro CNPJ válido caso contrário CNPJ
                 * Inválido
                 */
                if (recebeCPF == true) {
                    //   JOptionPane.showMessageDialog(this, "" + "\n Validado com Sucesso.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    acaoCNPJTrue();
                    txtCPFPaciente.setEditable(true);
                    txtCPFPaciente.setBackground(Color.WHITE);

                    lblLinhaInformativaPaciente.setText("");
                    lblLinhaInformativaPaciente.setFont(f);
                    lblLinhaInformativaPaciente.setText("Validado com Sucesso.");
                    lblLinhaInformativaPaciente.setForeground(new Color(0, 102, 102));
                    txtNome.requestFocus();
                    txtCPFPaciente.setEnabled(false);
                    btnValidaCPF.setEnabled(false);

                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n CPF Inválido.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtCPFPaciente.setBackground(Color.YELLOW);
                    txtCPFPaciente.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Registro Duplicado.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtCPFPaciente.requestFocus();
                txtCPFPaciente.setBackground(Color.RED);

            }
        } catch (PersistenciaException ex) {
            //robo conectado servidor google 
            erroViaEmail(ex.getMessage(), "acaoValidaCPFPolindoDados()\n"
                    + "Camada GUI - validando cpf e polindo dados ");
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    private void acaoCNPJTrue() {
        // habilitarCamposForm();

        txtNome.setEnabled(true);
        txtDataNascimento.setEnabled(true);
        btnCalulaData.setEnabled(true);

        cbSexo.requestFocus();

    }

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
            email.setSubject("Erro Camada DAO - " + metodo);
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                    + "Erro Método de Envio Email:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            e.printStackTrace();
        }
    }

    private void btnValidaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCPFActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            acaoValidaCPFPolindoDados();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
        }
    }//GEN-LAST:event_btnValidaCPFActionPerformed

    private void btnValidaCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnValidaCPFKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            //se ele executar usando a ação keyPress desabilita o botão 
            btnValidaCPF.setEnabled(false);
            ConexaoUtil conecta = new ConexaoUtil();
            boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

            if (recebeConexao == true) {
                acaoValidaCPFPolindoDados();
            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                        + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }

        }

        if (evt.getKeyCode() == evt.VK_RIGHT) {
            cbSexo.requestFocus();
        }

        if (evt.getKeyCode() == evt.VK_LEFT) {
            txtCPFPaciente.requestFocus();
        }
    }//GEN-LAST:event_btnValidaCPFKeyPressed

    private void cbSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSexoActionPerformed


    private void cbSexoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusGained
        cbSexo.setBackground(new Color(0, 102, 102));
        cbSexo.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Escolha o Sexo");
        lblLinhaInformativaPaciente.setFont(f);
    }//GEN-LAST:event_cbSexoFocusGained

    private void cbSexoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusLost
        cbSexo.setBackground(Color.WHITE);
        cbSexo.setForeground(Color.BLACK);

    }//GEN-LAST:event_cbSexoFocusLost

    private void cbSexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSexoKeyPressed

        if (!cbSexo.getSelectedItem().equals("Selecione...")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtConjuge.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                txtDataNascimento.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_UP) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo Obrigatório.  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                cbSexo.requestFocus();

            }

            if (evt.getKeyCode() == evt.VK_LEFT) {
                txtDataNascimento.requestFocus();
            }

        }
    }//GEN-LAST:event_cbSexoKeyPressed

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        btnEditar.setBackground(new Color(0, 102, 102));

        this.lblLinhaInformativaPaciente.setText("");
        this.lblLinhaInformativaPaciente.setText("Edita o Registro Selecionado");

        this.lblLinhaInformativaPaciente.setFont(f);
        this.lblLinhaInformativaPaciente.setForeground(new Color(0, 102, 102));


    }//GEN-LAST:event_btnEditarFocusGained

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        if ((lblPerfil.getText().equalsIgnoreCase("ANALISTA"))) {
            btnExcluir.setEnabled(true);
            lblLinhaInformativaPaciente.setFont(f);
            lblLinhaInformativaPaciente.setForeground(new Color(0, 102, 102));
            lblLinhaInformativaPaciente.setText("");
            lblLinhaInformativaPaciente.setText("Liberado para Exclusão");
        } else {
            btnExcluir.setEnabled(false);
            lblLinhaInformativaPaciente.setFont(f);
            lblLinhaInformativaPaciente.setForeground(Color.red);
            lblLinhaInformativaPaciente.setText("");
            lblLinhaInformativaPaciente.setText("Perfil não possui autorização de Exclusão");

        }
    }//GEN-LAST:event_btnExcluirFocusGained

    private void txtPesquisaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusGained
        txtPesquisa.setBackground(new Color(0, 102, 102));
        txtPesquisa.setForeground(Color.WHITE);
    }//GEN-LAST:event_txtPesquisaFocusGained

    private void txtPesquisaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPesquisaFocusLost
        txtPesquisa.setBackground(Color.WHITE);
        txtPesquisa.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtPesquisaFocusLost

    private void btnPesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusGained
        btnPesquisar.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btnPesquisarFocusGained

    private void btnPesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarFocusLost
        btnPesquisar.setBackground(Color.WHITE);

    }//GEN-LAST:event_btnPesquisarFocusLost

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        instance = null;
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

    }//GEN-LAST:event_formInternalFrameOpened


    private void btnCalulaDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalulaDataActionPerformed

    }//GEN-LAST:event_btnCalulaDataActionPerformed

    private void txtDataNascimentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataNascimentoFocusGained
        txtDataNascimento.setBackground(new Color(0, 102, 102));
        txtDataNascimento.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Informe a Data Nascimento");
        lblLinhaInformativaPaciente.setFont(f);
    }//GEN-LAST:event_txtDataNascimentoFocusGained

    private void txtDataNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataNascimentoFocusLost
        txtDataNascimento.setBackground(Color.WHITE);
        txtDataNascimento.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");
    }//GEN-LAST:event_txtDataNascimentoFocusLost

    private void txtDataNascimentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDataNascimentoKeyPressed

        if (!txtDataNascimento.getText().equals("  /  /    ")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                btnCalulaData.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtNome.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campoé Obrigatório  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtDataNascimento.setBackground(Color.YELLOW);
                txtDataNascimento.setForeground(Color.BLACK);
                txtDataNascimento.requestFocus();

            }
        }
    }//GEN-LAST:event_txtDataNascimentoKeyPressed

    private void btnCalulaDataFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCalulaDataFocusGained
        btnCalulaData.setBackground(new Color(0, 102, 102));
        btnCalulaData.setForeground(Color.WHITE);
        calculaIdade();
        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Calcula Idade");
        lblLinhaInformativaPaciente.setFont(f);

    }//GEN-LAST:event_btnCalulaDataFocusGained

    private void btnCalulaDataFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCalulaDataFocusLost
        btnCalulaData.setBackground(Color.WHITE);
        btnCalulaData.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");

    }//GEN-LAST:event_btnCalulaDataFocusLost

    private void txtConjugeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConjugeFocusGained
        txtConjuge.setBackground(new Color(0, 102, 102));
        txtConjuge.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite o nome esposo(a)");
        lblLinhaInformativaPaciente.setFont(f);
    }//GEN-LAST:event_txtConjugeFocusGained

    private void txtConjugeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConjugeFocusLost
        txtConjuge.setBackground(Color.WHITE);
        txtConjuge.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");
    }//GEN-LAST:event_txtConjugeFocusLost

    private void txtConjugeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConjugeKeyTyped
        int numeroDeCaracter = 49;

        if (txtConjuge.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 49 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtConjuge.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 49 caracteres");

        }
    }//GEN-LAST:event_txtConjugeKeyTyped

    private void txtConjugeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtConjugeKeyPressed

        if (!txtConjuge.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {
                txtConjuge.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtConjuge.getText().trim()));
                txtMae.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                cbSexo.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtMae.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                cbSexo.requestFocus();
            }
        }
    }//GEN-LAST:event_txtConjugeKeyPressed

    private void txtMaeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaeFocusGained
        txtMae.setBackground(new Color(0, 102, 102));
        txtMae.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite o nome Mãe");
        lblLinhaInformativaPaciente.setFont(f);
    }//GEN-LAST:event_txtMaeFocusGained

    private void txtMaeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaeFocusLost
        txtMae.setBackground(Color.WHITE);
        txtMae.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");
    }//GEN-LAST:event_txtMaeFocusLost

    private void txtMaeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaeKeyTyped
        int numeroDeCaracter = 49;

        if (txtMae.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 49 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtMae.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 49 caracteres");

        }
    }//GEN-LAST:event_txtMaeKeyTyped

    private void txtMaeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaeKeyPressed

        if (!txtMae.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtMae.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtMae.getText().trim()));
                txtPai.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtConjuge.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtPai.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtConjuge.requestFocus();
            }
        }


    }//GEN-LAST:event_txtMaeKeyPressed

    private void txtPaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaiFocusGained

        txtPai.setBackground(new Color(0, 102, 102));
        txtPai.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite o nome Pai");
        lblLinhaInformativaPaciente.setFont(f);


    }//GEN-LAST:event_txtPaiFocusGained

    private void txtPaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPaiFocusLost
        txtPai.setBackground(Color.WHITE);
        txtPai.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");
    }//GEN-LAST:event_txtPaiFocusLost

    private void txtPaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaiKeyTyped
        int numeroDeCaracter = 49;

        if (txtPai.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 49 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtPai.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 49 caracteres");

        }
    }//GEN-LAST:event_txtPaiKeyTyped

    private void txtPaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPaiKeyPressed

        if (!txtPai.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtPai.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtPai.getText().trim()));
                btnCadastroFone.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtMae.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                btnCadastroFone.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtMae.requestFocus();
            }
        }


    }//GEN-LAST:event_txtPaiKeyPressed

    private void cbUFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbUFFocusGained

        cbUF.setBackground(new Color(0, 102, 102));
        cbUF.setForeground(Color.WHITE);
        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Escolha o Estado");
        lblLinhaInformativaPaciente.setFont(f);


    }//GEN-LAST:event_cbUFFocusGained

    private void cbUFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbUFFocusLost
        cbUF.setBackground(Color.WHITE);
        cbUF.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");


    }//GEN-LAST:event_cbUFFocusLost

    private void cbUFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbUFKeyPressed

        if (!cbUF.getSelectedItem().equals("Selecione...")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtCidade.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                txtGmail.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_UP) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo Obrigatório.  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                cbUF.requestFocus();

            }

            if (evt.getKeyCode() == evt.VK_LEFT) {
                txtGmail.requestFocus();
            }

        }


    }//GEN-LAST:event_cbUFKeyPressed

    private void txtCidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCidadeFocusGained
//
//        txtCidade.setBackground(new Color(0, 102, 102));
        txtCidade.setForeground(Color.black);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite Cidade");
        lblLinhaInformativaPaciente.setFont(f);


    }//GEN-LAST:event_txtCidadeFocusGained

    private void txtCidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCidadeFocusLost

        txtCidade.setBackground(Color.WHITE);
        txtCidade.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");

    }//GEN-LAST:event_txtCidadeFocusLost

    private void txtCidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCidadeKeyTyped

        int numeroDeCaracter = 29;

        if (txtCidade.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 29 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtCidade.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 29 caracteres");

        }


    }//GEN-LAST:event_txtCidadeKeyTyped

    private void txtCidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCidadeKeyPressed

        if (!txtCidade.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {
                txtCidade.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtCidade.getText().trim()));
                txtBairro.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                cbUF.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtBairro.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                cbUF.requestFocus();
            }
        }


    }//GEN-LAST:event_txtCidadeKeyPressed

    private void txtBairroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBairroFocusGained

        txtBairro.setBackground(new Color(0, 102, 102));
        txtBairro.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite Bairro");
        lblLinhaInformativaPaciente.setFont(f);


    }//GEN-LAST:event_txtBairroFocusGained

    private void txtBairroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBairroFocusLost

        txtBairro.setBackground(Color.WHITE);
        txtBairro.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");

    }//GEN-LAST:event_txtBairroFocusLost

    private void txtBairroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyTyped

        int numeroDeCaracter = 29;

        if (txtBairro.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 29 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtBairro.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 29 caracteres");

        }


    }//GEN-LAST:event_txtBairroKeyTyped

    private void txtBairroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyPressed

        if (!txtBairro.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {
                txtBairro.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtBairro.getText().trim()));
                txtRua.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtCidade.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtRua.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtCidade.requestFocus();
            }
        }


    }//GEN-LAST:event_txtBairroKeyPressed

    private void txtRuaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRuaFocusGained

        txtRua.setBackground(new Color(0, 102, 102));
        txtRua.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite Rua");
        lblLinhaInformativaPaciente.setFont(f);


    }//GEN-LAST:event_txtRuaFocusGained

    private void txtRuaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRuaFocusLost

        txtRua.setBackground(Color.WHITE);
        txtRua.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");


    }//GEN-LAST:event_txtRuaFocusLost

    private void txtRuaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRuaKeyTyped

        int numeroDeCaracter = 29;

        if (txtRua.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 29 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtRua.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 29 caracteres");

        }


    }//GEN-LAST:event_txtRuaKeyTyped

    private void txtRuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRuaKeyPressed

        if (!txtRua.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {
                txtRua.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtRua.getText().trim()));
                txtComplemento.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtBairro.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtComplemento.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtBairro.requestFocus();
            }
        }


    }//GEN-LAST:event_txtRuaKeyPressed

    private void txtComplementoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComplementoFocusGained

        txtComplemento.setBackground(new Color(0, 102, 102));
        txtComplemento.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite Complemento");
        lblLinhaInformativaPaciente.setFont(f);


    }//GEN-LAST:event_txtComplementoFocusGained

    private void txtComplementoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComplementoFocusLost

        txtComplemento.setBackground(Color.WHITE);
        txtComplemento.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");


    }//GEN-LAST:event_txtComplementoFocusLost

    private void txtComplementoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComplementoKeyTyped

        int numeroDeCaracter = 29;

        if (txtComplemento.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 29 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtComplemento.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 29 caracteres");

        }


    }//GEN-LAST:event_txtComplementoKeyTyped

    private void txtComplementoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComplementoKeyPressed

        if (!txtComplemento.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {
                txtComplemento.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtComplemento.getText().trim()));
                txtNCasa.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtRua.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtNCasa.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtRua.requestFocus();
            }
        }


    }//GEN-LAST:event_txtComplementoKeyPressed

    private void txtNCasaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNCasaFocusGained

        txtNCasa.setBackground(new Color(0, 102, 102));
        txtNCasa.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite Nº Casa");
        lblLinhaInformativaPaciente.setFont(f);


    }//GEN-LAST:event_txtNCasaFocusGained

    private void txtNCasaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNCasaFocusLost

        txtNCasa.setBackground(Color.WHITE);
        txtNCasa.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");


    }//GEN-LAST:event_txtNCasaFocusLost

    private void txtNCasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNCasaKeyPressed

        if (!txtNCasa.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {
                txtNCasa.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtNCasa.getText().trim()));
                txtFormatedCEP.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtComplemento.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtFormatedCEP.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtComplemento.requestFocus();
            }
        }


    }//GEN-LAST:event_txtNCasaKeyPressed

    private void txtNCasaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNCasaKeyTyped

        int numeroDeCaracter = 7;

        if (txtNCasa.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 7 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtNCasa.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativaPaciente.setText("Campo Nome: Máximo 7 caracteres");

        }


    }//GEN-LAST:event_txtNCasaKeyTyped

    private void txtFormatedCEPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedCEPFocusGained

        txtFormatedCEP.setBackground(new Color(0, 102, 102));
        txtFormatedCEP.setForeground(Color.WHITE);

        lblLinhaInformativaPaciente.setText("");
        lblLinhaInformativaPaciente.setText("Digite CEP");
        lblLinhaInformativaPaciente.setFont(f);


    }//GEN-LAST:event_txtFormatedCEPFocusGained

    private void txtFormatedCEPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedCEPFocusLost

        txtFormatedCEP.setBackground(Color.WHITE);
        txtFormatedCEP.setForeground(Color.BLACK);
        lblLinhaInformativaPaciente.setText("");


    }//GEN-LAST:event_txtFormatedCEPFocusLost

    private void txtFormatedCEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedCEPKeyPressed

        if (!txtFormatedCEP.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                btnSalvar.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtNCasa.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                btnSalvar.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtNCasa.requestFocus();
            }
        }


    }//GEN-LAST:event_txtFormatedCEPKeyPressed

    private void btnEditarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusLost
        lblLinhaInformativaPaciente.setText("");

    }//GEN-LAST:event_btnEditarFocusLost

    private void txtFormatedPesquisaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedPesquisaCPFFocusGained
        txtFormatedPesquisaCPF.setBackground(new Color(0, 102, 102));
        txtFormatedPesquisaCPF.setForeground(Color.WHITE);

    }//GEN-LAST:event_txtFormatedPesquisaCPFFocusGained

    private void txtFormatedPesquisaCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedPesquisaCPFKeyPressed

        if (txtFormatedPesquisaCPF.getText().equalsIgnoreCase("   .   .   -  ")) {

            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtFormatedPesquisaCPF.requestFocus();
                txtFormatedPesquisaCPF.setBackground(Color.YELLOW);
                txtFormatedPesquisaCPF.setForeground(Color.BLACK);

                lblLinhaInformativaPaciente.setText("");
                lblLinhaInformativaPaciente.setText("Digite o Registro a ser pesquisado [ENTER]");
                lblLinhaInformativaPaciente.setForeground(Color.red);
                lblLinhaInformativaPaciente.setFont(f);
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
            }
        }

        if (!txtFormatedPesquisaCPF.getText().equalsIgnoreCase("   .   .   -  ")) {

            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                btnPesquisaCPF.requestFocus();
                lblLinhaInformativaPaciente.setText("");
                lblLinhaInformativaPaciente.setText("fazendo pesquisa no MYSQL");
                lblLinhaInformativaPaciente.setForeground(new Color(0, 102, 102));
                lblLinhaInformativaPaciente.setFont(f);

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
            }
        }


    }//GEN-LAST:event_txtFormatedPesquisaCPFKeyPressed

    private void btnPesquisaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisaCPFFocusGained
        btnPesquisaCPF.setBackground(new Color(0, 102, 102));
        // 1º IREMOS SETAR TODOS OS VALORES DOS CAMPOS DO FORMULÁRIO
        //  JOptionPane.showMessageDialog(this, "flag: " + flag);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoBotaoPesquisarPorCPF();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }


    }//GEN-LAST:event_btnPesquisaCPFFocusGained

    private void btnCadastroFoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroFoneActionPerformed
        if (estaFechado(formFone)) {
            formFone = new SubTelaFonePaciente();
            DeskTop.add(formFone).setLocation(5, 3);
            formFone.setTitle("Cadasto de Celulares/Paciente");
            formFone.setVisible(true);

        } else {
            formFone.toFront();
            formFone.setVisible(true);

        }

    }//GEN-LAST:event_btnCadastroFoneActionPerformed

    private void btnPesquisaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesquisaCPFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JPanel PanelDadosUsuario;
    private javax.swing.JProgressBar barraProgressoPaciente;
    public static javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCadastroFone;
    private javax.swing.JButton btnCalulaData;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnEditar;
    public static javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisaCPF;
    private javax.swing.JButton btnPesquisar;
    public static javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnValidaCPF;
    private javax.swing.JComboBox cbEstaCivil;
    private javax.swing.JComboBox cbSexo;
    private javax.swing.JComboBox cbUF;
    private javax.swing.JLabel jLabel90Generic;
    private javax.swing.JLabel jLabelFim;
    private javax.swing.JLabel jLabelSystem;
    private javax.swing.JLabel jLteste;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCEP;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCPFUsuario;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblClientesCadastrados;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblConjuge;
    private javax.swing.JLabel lblDtNascimento;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstadoUsuario;
    public static javax.swing.JLabel lblFonePreferencial;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIDCustomizado;
    private javax.swing.JLabel lblIdade;
    private javax.swing.JTextField lblLinhaInformativaPaciente;
    private javax.swing.JLabel lblMae;
    private javax.swing.JLabel lblNCasa;
    private javax.swing.JLabel lblPai;
    private javax.swing.JLabel lblPesNome;
    private javax.swing.JLabel lblRepositNumeroClientes;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblUF;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel paielSexoObrigatorio;
    private javax.swing.JPanel painelCidadeObrigatorio;
    private javax.swing.JPanel painelDtNascObrigatorio;
    private javax.swing.JPanel painelEndereco;
    private javax.swing.JPanel painelLinhaTempo;
    private javax.swing.JPanel painelNomeObrigatorio;
    private javax.swing.JPanel painelObrigatorio;
    private javax.swing.JPanel painelPesquisaRapida;
    private javax.swing.JPanel painelPrincipalSecundario;
    private javax.swing.JPanel pinelCivilObrigatorio;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtBairro;
    public static javax.swing.JFormattedTextField txtCPFPaciente;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtConjuge;
    private javax.swing.JFormattedTextField txtDataNascimento;
    private javax.swing.JFormattedTextField txtFormatedCEP;
    private javax.swing.JFormattedTextField txtFormatedPesquisaCPF;
    private javax.swing.JTextField txtGmail;
    public static javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIdade;
    private javax.swing.JTextField txtMae;
    private javax.swing.JTextField txtNCasa;
    public static javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPai;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtRua;
    // End of variables declaration//GEN-END:variables
}
