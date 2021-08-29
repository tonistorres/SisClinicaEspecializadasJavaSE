package br.com.multclin.telas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dao.PacienteDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.PacienteDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaAgendamento.jDateTxtDtAgenda;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
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
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositIdPaciente;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositDtNascimento;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositSexo;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositEstadoCivil;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositConjuge;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositMae;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositPai;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositCelular;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositUF;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositCidade;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositBairro;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositRua;
import static br.com.multclin.telas.TelaInfoPaciente.lblRepositCPFPaciente;
import static br.com.multclin.telas.TelaPrincipal.barraProgresso;
import static br.com.multclin.telas.TelaPrincipal.lblNomeCompletoUsuario;
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import static br.com.multclin.telas.TelaPrincipal.lblStatusData;
import static br.com.multclin.telas.TelaPrincipal.lblStatusHora;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;

import static br.com.multclin.telas.TelaReagendamento.lblIDReagendamento;
import static java.lang.Thread.sleep;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JTextField;

//
/**
 *
 * @author Analista de Sistemas Tonis Alberto Torres Ferreira
 */
public class MovimentoDaAgenda extends javax.swing.JInternalFrame {

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());
    Font f = new Font("Tahoma", Font.BOLD, 12);

    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    AgendamentoBO agendamentoBO = new AgendamentoBO();

    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();

    br.com.multclin.telas.TelaInfoPaciente formInfoPaciente;
    br.com.multclin.telas.TelaReagendamento formReagendamento;
    br.com.multclin.telas.FrmMuralHannaProfSaude formMural;

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

    private void progressBar(int flagC) {
        //***************
        //PROGRESS BAR //
        //***********************************************************************
        //Contribuição Canal Yoube:https://www.youtube.com/watch?v=co2cITXfXqY//
        //**********************************************************************

        new Thread() {

            public void run() {

                lblVerificacaoMovimento.setVisible(true);
                barraProgressoMovimento.setVisible(true);

                for (int i = 0; i < 101; i++) {

                    try {
                        //sleep padrão é 100    
                        sleep(25);

                        //recebe o parametro {i) do laço de repetição for
                        barraProgressoMovimento.setValue(i);

                        if (barraProgressoMovimento.getValue() == 10) {

                            lblVerificacao.setText("");
                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 10% a barra de
                             * progresso inicia as configurações inicais
                             * futuramentes podemos fazer testes de redes...
                             *
                             */

                            lblVerificacaoMovimento.setText("10% Inicializando verificações...");

                            //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA 
                            lblLinhaInformativa.setBackground(Color.WHITE);
                            lblLinhaInformativa.setForeground(Color.BLACK);
                            lblLinhaInformativa.setText("10% Inicializando verificações...");

                        } else if (barraProgressoMovimento.getValue() == 20) {

                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 20% a barra de
                             * progresso faz um teste de conexão com a rede de
                             * internet e com o banco de dados hospedado na
                             * nuvem, caso exista conexão com a internet e
                             * ligação entre a aplicaçãoa e a nuvem onde se
                             * encontra hopedado o banco de dados então retorna
                             * TRUE caso contrário retorna FALSE e é fechado a
                             * tela de Movimento da Agenda e em seguida sai do
                             * sistema pois é invocado o método
                             * aoSairDoSistema();
                             */
                            lblVerificacaoMovimento.setText("20% Verificando conexão com a núvem... ");

                            //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA 
//                            lblLinhaInformativa.setBackground(new Color(135, 206, 235));
//                            lblLinhaInformativa.setForeground(Color.BLACK);
                            lblLinhaInformativa.setText("20% Iniciando teste de conexão (Internet/Banco Dados)...");
                            fazendoTesteConexao();

//                            lblLinhaInformativa.setBackground(new Color(135, 206, 235));
//                            lblLinhaInformativa.setForeground(Color.BLACK);
                            lblLinhaInformativa.setText("20% Conexão estabilizada.");
                        } else if (barraProgressoMovimento.getValue() == 30) {

                            /**
                             * CONTROLE DE FLAGS Em 30% temos uma estrutura
                             * switch casa para fazer tamadas de decisões que
                             * serão alimentadas por meio de FLAGS passadas pelo
                             * método progressBar(flagC), mediante a falg
                             * passada serão tomada decisões específicas. CASE
                             * 1: EXECUTAREMOS blocos de códigos ligados ao
                             * botão btnCancelar CASE 2: EXECUTAREMOS bloco de
                             * códigos ligados ao botão Info
                             *
                             */
                            switch (flagC) {
                                case 1: {
                                    lblVerificacaoMovimento.setText("Case 1: 30% Envio de email CANCELAMENTO...");

                                    //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA 
//                                    lblLinhaInformativa.setBackground(new Color(173, 216, 230));
//                                    lblLinhaInformativa.setForeground(Color.BLACK);
                                    lblLinhaInformativa.setText("Case 1: 30% Envio de email CANCELAMENTO...");
                                    enviarEmailCancelamento();
                                    break;
                                }

                                case 2: {//flag ==2

                                    lblVerificacaoMovimento.setText("Case 2: 30% capturando info...");

                                    //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA 
//                                    lblLinhaInformativa.setBackground(new Color(173, 216, 230));
//                                    lblLinhaInformativa.setForeground(Color.BLACK);
                                    lblLinhaInformativa.setText("Case 2: 30% capturando Informação do Paciente na núvem...");
                                    abrirInfoPaciente();
                                    break;
                                }

                                case 3: {//se flag ==3
                                    lblVerificacaoMovimento.setText("Case 3: 30% criando panfleto aviso Zap...");

                                    //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA 
//                                    lblLinhaInformativa.setBackground(new Color(173, 216, 230));
//                                    lblLinhaInformativa.setForeground(Color.BLACK);
                                    lblLinhaInformativa.setText("Case 3: 30% criando panfleto aviso Zap...");
                                    infoViaZapComParameter();
                                    break;
                                }

                                case 4: {
                                    lblVerificacaoMovimento.setText("Case 4: 30% Paciente enviado consultório...");

//                                    //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA 
//                                    lblLinhaInformativa.setBackground(new Color(173, 216, 230));
//                                    lblLinhaInformativa.setForeground(Color.BLACK);
                               //     lblLinhaInformativa.setText("Case 4: 30% Envio de email CAIXA...");
                                //    enviarEmailRegistroCaixa();
                                    break;
                                }

                                case 5: {
                                    lblVerificacaoMovimento.setText("Case 5: 30% Abrir Área de Trabalho Reagendamento...");
                                    abrirReagendamento();
                                    break;
                                }

                            }

                        } else if (barraProgressoMovimento.getValue() == 40) {
                            /**
                             * CONTROLE DE FLAGS Em 40% temos uma estrutura
                             * switch casa para fazer tamadas de decisões que
                             * onde serão salvos requisições inerentes a Banco
                             * de dados CASE 1: Executaremos o método
                             * salvaCancelamento() que irá fazer um update na
                             * tbAgendamento mudando o campo statusAgendamento
                             * de Agendado para Cancelado
                             *
                             */
                            switch (flagC) {

                                case 1: {//se flag ==1
                                    lblVerificacaoMovimento.setText("Case 1: 40% Cancelando Agendamento no BD...");
                                    salvaCancelamento();
                                    break;
                                }

                                case 2: {

                                    lblVerificacaoMovimento.setText("Case 2: Sem processo a serem salvos no BD.");
                                    //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA 
//                                    lblLinhaInformativa.setBackground(new Color(102, 205, 170));
//                                    lblLinhaInformativa.setForeground(Color.BLACK);
                                    lblLinhaInformativa.setText("Case 2: 40% Processos de conectividade instáveis... ");
                                    break;
                                }

                                case 3: {//se flag ==3
                                    lblVerificacaoMovimento.setText("Case 3: 40% Capturando Filtrando ZAP...");
                                    capturandoFiltrandoZAP();
                                    break;
                                }

                                case 4: {//se flag==4                                   
                                    lblVerificacaoMovimento.setText("Case 4: 40% Registrando Caixa no Banco...");
                                    registrandoCaixa();
                                    break;
                                }

                                case 5: {
                                    lblVerificacaoMovimento.setText("Case 5: 40% Sem procedimentos a serem executados...");

                                    break;

                                }

                            }

                        } else if (barraProgressoMovimento.getValue() == 50) {

                            /**
                             * CONTROLE DE FLAGS Em 50% temos uma estrutura
                             * switch case para fazer tamadas de decisões. CASE
                             * 1: Executaremos o método acaoBuscarPorNome() que
                             * em nossa thread hora executada tem a funçao de
                             * atualizar a lista tirando da mesma o registro que
                             * foi efetuado o cancelamento na lista de
                             * agendamento do formulario movimento do
                             * agendamento
                             *
                             */
                            switch (flagC) {

                                //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA
                                //personalizar caso 1 e 4  e observar a rota 3 tambem 
                                case 1: {

                                    lblVerificacaoMovimento.setText("Case 1:50% Atualizar Lista...");
                                    acaoBuscaPorNome();

                                    break;
                                }

                                case 2: {

                                    lblVerificacaoMovimento.setText("Case 2: InterFace Gráfica instável.");
                                    //DEIXAONDO A EXPERIENCIA COM USUÁRIO MAIS INTERATIVA 
//                                    lblLinhaInformativa.setBackground(new Color(102, 205, 170));
//                                    lblLinhaInformativa.setForeground(Color.WHITE);
                                    lblLinhaInformativa.setText("Case 2: 50%  Sem atualização de InterFace Gráfica.");
                                    break;
                                }

                                case 4: {

                                    lblVerificacaoMovimento.setText("Case 1:50% Atualizar Lista...");
                                    acaoBuscaPorNome();
                                    break;
                                }

                                case 5: {
                                    lblLinhaInformativa.setText("Case 2: 50%  Sem atualização de InterFace Gráfica.");
                                    break;
                                }
                            }
                        } else if (barraProgressoMovimento.getValue() == 60) {

                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 60% a barra de
                             * progresso
                             */
                            lblVerificacaoMovimento.setText("60% Sistema estável...");
                            lblLinhaInformativa.setText("60% Sistema estável...");

                        } else if (barraProgressoMovimento.getValue() == 70) {
                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 70% a barra de
                             * progresso
                             */
                            lblVerificacaoMovimento.setText("70% Drivers estáveis...");
                            lblLinhaInformativa.setText("70% Drivers estáveis...");

                        } else if (barraProgressoMovimento.getValue() == 80) {

                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 80% a barra de
                             * progresso
                             */
                            lblVerificacaoMovimento.setText("80% Gerenciador de banco estável...");
                            lblLinhaInformativa.setText("80% Gerenciador de banco estável...");

                        } else if (barraProgressoMovimento.getValue() == 90) {
                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 90% a barra de
                             * progresso
                             */
                            lblVerificacaoMovimento.setText("90% Inicializando procedimentos finais...");
                            lblLinhaInformativa.setText("90% Inicializando procedimentos finais...");
                        } else if (barraProgressoMovimento.getValue() == 100) {
                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 100% a barra
                             * de progresso
                             */
                            lblVerificacaoMovimento.setText("100% fim da Pesquisa.");
                            lblLinhaInformativa.setText("100% fim da Pesquisa.");
                            lblVerificacaoMovimento.setVisible(false);
                            barraProgressoMovimento.setVisible(false);
                            lblVerificacao.setText("");
                            lblVerificacaoMovimento.setText("");
                            lblLinhaInformativa.setText("");
                            lblLinhaInformativa.setBackground(new Color(0, 102, 102));

                            //se quiser desligar o relogio 
                            //  estado = false;
                        }

                    } catch (Exception e) {
                        // e.printStackTrace();
                        System.out.println("try:" + e.getMessage());

                        //JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());
                    }

                }
            }
        }
                .start();// iniciando a Thread

    }

    private void capturandoFiltrandoZAP() {

        lblVerificacaoMovimento.setText("Case 1: 40% Capturando celular e entrando no Zap...");
        String celular = tabela.getValueAt(tabela.getSelectedRow(), 3).toString();
        String linkZap = "https://api.whatsapp.com/send?phone=".concat("55" + celular);
        String linkTratado = linkZap.replace("(", "").replace(")", "").replace("-", "").trim();
        disparaZap(linkTratado);
    }

    private void fazendoTesteConexao() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            lblVerificacaoMovimento.setText("Verificando resultado: " + recebeConexao);
        } else {

            lblLinhaInformativa.setForeground(Color.red);
            lblLinhaInformativa.setText("SEM CONEXÃO COM A INTERNET OU ACESSO AO BANCO DE DADOS...");
            lblLinhaInformativa.setFont(f);
            dispose();
            acaoSairDoSistema();
        }

    }

    private void abrirInfoPaciente() {

        if (estaFechado(formInfoPaciente)) {
            formInfoPaciente = new TelaInfoPaciente();
            DeskTop.add(formInfoPaciente).setLocation(5, 65);
            formInfoPaciente.setTitle("Info. Paciente");
            formInfoPaciente.setVisible(true);

            int linhaRegistro = tabela.getSelectedRow();
            String capturaDaTabela = (tabela.getValueAt(linhaRegistro, 1).toString());
            lblRepositIdPaciente.setText(capturaDaTabela);

        } else {
            formInfoPaciente.toFront();
            formInfoPaciente.setVisible(true);

        }
    }

    private void abrirReagendamento() {
        if (estaFechado(formReagendamento)) {
            formReagendamento = new TelaReagendamento();
            DeskTop.add(formReagendamento).setLocation(5, 5);
            formReagendamento.setTitle("Tele de Reagendamento");
            formReagendamento.setVisible(true);

            int linha = tabela.getSelectedRow();
            String capturaDaTabela = (tabela.getValueAt(linha, 0).toString());

            //   JOptionPane.showMessageDialog(null, capturaDaTabela);
            lblIDReagendamento.setText(capturaDaTabela);

        } else {
            formReagendamento.toFront();
            formReagendamento.setVisible(true);

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
                            lblVerificacaoMovimento.setText("Inicializando barra de progresso");

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

    private void salvaCancelamento() {

        try {

            AgendamentoBO agendamentoBO = new AgendamentoBO();

            String IdAgenda = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();

            agendamentoDTO.setIdDto(Integer.parseInt(IdAgenda));
            agendamentoDTO.setStatusAgendamentoDto("CANCELADO");
            agendamentoDTO.setDtRegistroDto(lblStatusData.getText());
            agendamentoBO.atualizarBO(agendamentoDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void registrandoCaixa() {

        try {

            AgendamentoBO agendamentoBO = new AgendamentoBO();

            String IdAgenda = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();

            agendamentoDTO.setIdDto(Integer.parseInt(IdAgenda));
            agendamentoDTO.setStatusAgendamentoDto("CAIXA");
            agendamentoDTO.setDtRegistroDto(lblStatusData.getText());
            agendamentoBO.atualizarCaixaBO(agendamentoDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MovimentoDaAgenda() {
        initComponents();
        aoCarregar();
        frontEnd();
        addRowJTable();
    }

    private void aoCarregar() {
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setEnabled(true);
        lblLinhaInformativa.setEditable(false);

        //sobre a barra de progresso inicialização
        barraProgressoMovimento.setVisible(false);
        lblVerificacaoMovimento.setVisible(false);

        String rsBruto = moeda.format(0.00);
        String rsDesconto = moeda.format(0.00);
        String rsValorTotal = moeda.format(0.00);

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
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 8));
        tabela.getTableHeader().setOpaque(false);
        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
        tabela.setRowHeight(34);

    }

    public void addRowJTable() {
        //Inicio da Pesquisa no banco 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        txtCPFPaciente.setText("");
        txtCPFPaciente.setValue(null);

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<AgendamentoDTO> list;

        try {

            JButton btnZap = new JButton();
            btnZap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/watsap.png")));

            JButton btnInfo = new JButton();
            btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/info.png")));

            JButton btnReAgendar = new JButton();
            btnReAgendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/reagendar.png")));

            JButton btnCaixa = new JButton();
            btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/doctorConsultorio.png")));

            JButton btnCancelar = new JButton();
            btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancel.png")));

            btnZap.setName("btnZap");
            btnZap.setToolTipText("INFORMATIVO infoZaP.");
            btnInfo.setName("Info");
            btnInfo.setToolTipText("Informação PACIENTE");
            btnReAgendar.setName("reagendar");
            btnReAgendar.setToolTipText("REAGENDAR Consulta");
            btnCaixa.setName("btnCaixa");
            btnCaixa.setToolTipText("Lançar CAIXA");
            btnCancelar.setName("btnCancelar");
            btnCancelar.setToolTipText("CANCELAR Agendamento.");

            String pesquisAgendaEreAgendaLike = "AGENDADO";

            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosAgendados(pesquisAgendaEreAgendaLike);
            Object rowData[] = new Object[12];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getFk_pacienteDto();
                rowData[2] = list.get(i).getNomeDto();
                rowData[3] = list.get(i).getCelularPrefDto();

                rowData[4] = formatador.format(list.get(i).getDataAgendamentoDto());
                rowData[5] = moeda.format(list.get(i).getRsTotalAgendamentoDto());
                rowData[6] = btnInfo;
                rowData[7] = btnReAgendar;
                rowData[8] = btnZap;
                rowData[9] = btnCaixa;
                rowData[10] = btnCancelar;
                rowData[11] = list.get(i).getNomeMedicoDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(160);//nome
            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//celular
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(5).setPreferredWidth(80);//valor total
            tabela.getColumnModel().getColumn(6).setPreferredWidth(28);//info
            tabela.getColumnModel().getColumn(7).setPreferredWidth(28);//reagendamento
            tabela.getColumnModel().getColumn(8).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(10).setPreferredWidth(34);//cancelar
            tabela.getColumnModel().getColumn(11).setPreferredWidth(100);//cancelar

            //fim da pesquisa 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            contarClienteTableModel();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void acaoInicioDePesquisaBanco() {

        //coloca o cursor em modo de espera 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        this.lblLinhaInformativa.setFont(f);
        this.lblLinhaInformativa.setForeground(Color.BLACK);
        this.lblLinhaInformativa.setBackground(Color.ORANGE);
        this.lblLinhaInformativa.setText("");
        this.lblLinhaInformativa.setText("Iniciando a Pesquisa no Gerenciador de Banco de Dados MYSQ");

    }

    private void acaoFimDePesquisNoBanco() {
        //finalizar o modo de espera do cursor 
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.lblLinhaInformativa.setText("");
        this.lblLinhaInformativa.setFont(f);
        this.lblLinhaInformativa.setText("Pesquisa foi realizada com sucesso!");
        this.lblLinhaInformativa.setForeground(Color.BLACK);
        this.lblLinhaInformativa.setBackground(Color.white);
        this.txtNome.setText("");

    }

    private void frontEnd() {
        this.btnPesquisarNome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        this.btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/refreshNuvem.png")));
        this.btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        this.btnPesquisaData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/pesquisar.png")));
        this.btnMural.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/mural.png")));

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelPesquisaCPF = new javax.swing.JPanel();
        txtCPFPaciente = new javax.swing.JFormattedTextField();
        btnValidaCPF = new javax.swing.JButton();
        lblLinhaInformativa = new javax.swing.JTextField();
        painelJDataChooser = new javax.swing.JPanel();
        btnPesquisaData = new javax.swing.JButton();
        jDateTxtDtAgenda = new com.toedter.calendar.JDateChooser();
        painelAtualizar = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        painelPesquisaNome = new javax.swing.JPanel();
        txtNome = new javax.swing.JTextField();
        btnPesquisarNome = new javax.swing.JButton();
        barraProgressoMovimento = new javax.swing.JProgressBar();
        lblVerificacaoMovimento = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnMural = new javax.swing.JButton();

        setClosable(true);
        setTitle("Movimento da Agenda");

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDAgen", "IDPac", "Paciente", "Celular(Zap)", "DtAgenda", "R$ Total", "", "", "", "", "", "Prof.Saúde"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setRowHeight(20);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setResizable(false);
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setResizable(false);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(190);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(60);
            tabela.getColumnModel().getColumn(5).setResizable(false);
            tabela.getColumnModel().getColumn(5).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(6).setResizable(false);
            tabela.getColumnModel().getColumn(6).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(8).setResizable(false);
            tabela.getColumnModel().getColumn(8).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(9).setResizable(false);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(11).setResizable(false);
            tabela.getColumnModel().getColumn(11).setPreferredWidth(100);
        }

        painelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 810, 370));

        painelPesquisaCPF.setBackground(java.awt.Color.white);
        painelPesquisaCPF.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisar CPF:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPesquisaCPF.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        painelPesquisaCPF.add(txtCPFPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 140, 30));

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
        painelPesquisaCPF.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        painelPrincipal.add(painelPesquisaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 200, 70));
        painelPrincipal.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 470, 40));

        painelJDataChooser.setBackground(java.awt.Color.white);
        painelJDataChooser.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisar Data:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelJDataChooser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPesquisaData.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisaDataFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesquisaDataFocusLost(evt);
            }
        });
        btnPesquisaData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaDataActionPerformed(evt);
            }
        });
        painelJDataChooser.add(btnPesquisaData, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 22, 30, 30));

        jDateTxtDtAgenda.setForeground(java.awt.Color.white);
        jDateTxtDtAgenda.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jDateTxtDtAgenda.setPreferredSize(new java.awt.Dimension(87, 25));
        jDateTxtDtAgenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jDateTxtDtAgendaFocusGained(evt);
            }
        });
        painelJDataChooser.add(jDateTxtDtAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 140, 30));

        painelPrincipal.add(painelJDataChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 200, 70));

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
        painelAtualizar.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 15, 45, 45));

        painelPrincipal.add(painelAtualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 80, 70, 70));

        painelPesquisaNome.setBackground(new java.awt.Color(0, 102, 102));
        painelPesquisaNome.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Digite o  Nome do Profissional de Saúde:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), java.awt.Color.white)); // NOI18N
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

        btnPesquisarNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisarNomeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btnPesquisarNomeFocusLost(evt);
            }
        });
        btnPesquisarNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarNomeActionPerformed(evt);
            }
        });
        painelPesquisaNome.add(btnPesquisarNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 30, 30));

        painelPrincipal.add(painelPesquisaNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 260, 68));
        painelPrincipal.add(barraProgressoMovimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 250, 20));

        lblVerificacaoMovimento.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblVerificacaoMovimento.setForeground(new java.awt.Color(0, 102, 102));
        painelPrincipal.add(lblVerificacaoMovimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 250, 20));

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mural:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMuralActionPerformed(evt);
            }
        });
        jPanel1.add(btnMural, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 15, 45, 45));

        painelPrincipal.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 0, 70, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );

        setBounds(0, 0, 829, 560);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCPFPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFPacienteFocusGained
        txtCPFPaciente.setBackground(new Color(0, 102, 102));
        txtCPFPaciente.setForeground(Color.WHITE);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o CPF pressione ENTER");

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
                //iniciando pesquisa no banco 
                acaoInicioDePesquisaBanco();

                this.btnValidaCPF.requestFocus();

            }
        }
        if (txtCPFPaciente.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                acaoInicioDePesquisaBanco();
                addRowJTable();
                acaoBotaoPesquisarPorCPF();

                int numeroLinhas = tabela.getRowCount();
                txtCPFPaciente.setValue(null);
                if (numeroLinhas == 1) {

                    while (tabela.getModel().getRowCount() > 0) {
                        ((DefaultTableModel) tabela.getModel()).removeRow(0);

                    }

                }

                btnRefresh.requestFocus();
            }


    }//GEN-LAST:event_txtCPFPacienteKeyPressed
    }

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained
        btnValidaCPF.setBackground(Color.YELLOW);

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoBotaoPesquisarPorCPF();
        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }


    }//GEN-LAST:event_btnValidaCPFFocusGained

    private void btnValidaCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusLost
        this.btnValidaCPF.setBackground(Color.WHITE);

    }//GEN-LAST:event_btnValidaCPFFocusLost

    private void btnValidaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidaCPFActionPerformed


    }//GEN-LAST:event_btnValidaCPFActionPerformed

    private void btnValidaCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnValidaCPFKeyPressed

    }//GEN-LAST:event_btnValidaCPFKeyPressed

    private void btnPesquisaDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaDataActionPerformed

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {

            acaoBuscarPorJDateChooser();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }


    }//GEN-LAST:event_btnPesquisaDataActionPerformed

    private void pesquisarComJDateChooser() throws ParseException {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<AgendamentoDTO> list;

        try {

            JButton btnZap = new JButton();
            btnZap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/watsap.png")));

            JButton btnInfo = new JButton();
            btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/info.png")));

            JButton btnReAgendar = new JButton();
            btnReAgendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/reagendar.png")));

            JButton btnCaixa = new JButton();
            btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/doctorConsultorio.png")));

            JButton btnCancelar = new JButton();
            btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancel.png")));

            btnZap.setName("btnZap");
            btnZap.setToolTipText("INFORMATIVO infoZaP.");
            btnInfo.setName("Info");
            btnInfo.setToolTipText("Informação PACIENTE");
            btnReAgendar.setName("reagendar");
            btnReAgendar.setToolTipText("REAGENDAR Consulta");
            btnCaixa.setName("btnCaixa");
            btnCaixa.setToolTipText("Lançar CAIXA");
            btnCancelar.setName("btnCancelar");
            btnCancelar.setToolTipText("CANCELAR Agendamento.");

            String parametroParaPesquisa = "AGENDADO";

            //DADOS DO AGENDAMENTO 
            //Capturamos a Data no componente jDateChooser e convertemos para String 
            //https://www.guj.com.br/t/erro-java-util-date-cannot-be-cast-to-java-sql-date-resolvido/82451/6
            String DataAgenda = ((JTextField) jDateTxtDtAgenda.getDateEditor().getUiComponent()).getText();
            //Em seguida formatamos de String para Date 
            Date dtAgenda = formatador.parse(DataAgenda);

            //String novaData = formatador.format(jDateTxtDtAgenda.getDate());
            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosAgendadosJDateChooser(parametroParaPesquisa, new java.sql.Date(dtAgenda.getTime()));
            Object rowData[] = new Object[12];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getFk_pacienteDto();
                rowData[2] = list.get(i).getNomeDto();
                rowData[3] = list.get(i).getCelularPrefDto();

                rowData[4] = formatador.format(list.get(i).getDataAgendamentoDto());
                rowData[5] = moeda.format(list.get(i).getRsTotalAgendamentoDto());
                rowData[6] = btnInfo;
                rowData[7] = btnReAgendar;
                rowData[8] = btnZap;
                rowData[9] = btnCaixa;
                rowData[10] = btnCancelar;
                rowData[11] = list.get(i).getNomeMedicoDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(160);//nome
            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//celular
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(5).setPreferredWidth(80);//valor total
            tabela.getColumnModel().getColumn(6).setPreferredWidth(28);//info
            tabela.getColumnModel().getColumn(7).setPreferredWidth(28);//reagendamento
            tabela.getColumnModel().getColumn(8).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(10).setPreferredWidth(34);//cancelar
            tabela.getColumnModel().getColumn(11).setPreferredWidth(100);//cancelar
            //fim da pesquisa 
            acaoFimDePesquisNoBanco();

            contarClienteTableModel();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void acaoBuscarPorJDateChooser() {

        //iniciando pesquisa no banco 
        acaoInicioDePesquisaBanco();

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }
            try {
                pesquisarComJDateChooser();
            } catch (ParseException ex) {
                ex.getMessage();
            }

        } else {
            addRowJTable();
        }

    }


    private void btnPesquisaDataFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisaDataFocusGained
        btnPesquisaData.setBackground(Color.YELLOW);

    }//GEN-LAST:event_btnPesquisaDataFocusGained

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed

    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed

        if (!txtNome.getText().equals("")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                //iniciando pesquisa no banco 
                acaoInicioDePesquisaBanco();

                this.btnPesquisarNome.requestFocus();

            } else {
                if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                    JOptionPane.showMessageDialog(this, "" + "\n O nome é obrigatório.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtNome.requestFocus();
                }
            }
        }


    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtNomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusGained

        txtNome.setBackground(new Color(0, 102, 102));
        txtNome.setForeground(Color.WHITE);

    }//GEN-LAST:event_txtNomeFocusGained

    private void txtNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusLost

        txtNome.setBackground(Color.white);
        txtNome.setForeground(Color.BLACK);


    }//GEN-LAST:event_txtNomeFocusLost

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
        int numeroDeCaracter = 20;

        if (txtNome.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 20 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtNome.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 20 caracteres");

        }

    }//GEN-LAST:event_txtNomeKeyTyped

    private void bataoAtualizar() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<AgendamentoDTO> list;

        try {

            JButton btnZap = new JButton();
            btnZap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/watsap.png")));

            JButton btnInfo = new JButton();
            btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/info.png")));

            JButton btnReAgendar = new JButton();
            btnReAgendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/reagendar.png")));

            JButton btnCaixa = new JButton();
            btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/doctorConsultorio.png")));

            JButton btnCancelar = new JButton();
            btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancel.png")));

            btnZap.setName("btnZap");
            btnZap.setToolTipText("INFORMATIVO infoZaP.");
            btnInfo.setName("Info");
            btnInfo.setToolTipText("Informação PACIENTE");
            btnReAgendar.setName("reagendar");
            btnReAgendar.setToolTipText("REAGENDAR Consulta");
            btnCaixa.setName("btnCaixa");
            btnCaixa.setToolTipText("Lançar CAIXA");
            btnCancelar.setName("btnCancelar");
            btnCancelar.setToolTipText("CANCELAR Agendamento.");

            String nome = MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText());
            String parametroParaPesquisa = "AGENDADO";

            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosPorMedico(parametroParaPesquisa, nome);

            Object rowData[] = new Object[12];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getFk_pacienteDto();
                rowData[2] = list.get(i).getNomeDto();
                rowData[3] = list.get(i).getCelularPrefDto();

                rowData[4] = formatador.format(list.get(i).getDataAgendamentoDto());
                rowData[5] = moeda.format(list.get(i).getRsTotalAgendamentoDto());
                rowData[6] = btnInfo;
                rowData[7] = btnReAgendar;
                rowData[8] = btnZap;
                rowData[9] = btnCaixa;
                rowData[10] = btnCancelar;
                rowData[11] = list.get(i).getNomeMedicoDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(160);//nome
            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//celular
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(5).setPreferredWidth(80);//valor total
            tabela.getColumnModel().getColumn(6).setPreferredWidth(28);//info
            tabela.getColumnModel().getColumn(7).setPreferredWidth(28);//reagendamento
            tabela.getColumnModel().getColumn(8).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(10).setPreferredWidth(34);//cancelar
            tabela.getColumnModel().getColumn(11).setPreferredWidth(100);//cancelar

            //fim da pesquisa 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            //acaoFimDePesquisNoBanco();
            contarClienteTableModel();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void pesquisarPorNome() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<AgendamentoDTO> list;

        try {

            JButton btnZap = new JButton();
            btnZap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/watsap.png")));

            JButton btnInfo = new JButton();
            btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/info.png")));

            JButton btnReAgendar = new JButton();
            btnReAgendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/reagendar.png")));

            JButton btnCaixa = new JButton();
            btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/doctorConsultorio.png")));

            JButton btnCancelar = new JButton();
            btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancel.png")));

            btnZap.setName("btnZap");
            btnZap.setToolTipText("INFORMATIVO infoZaP.");
            btnInfo.setName("Info");
            btnInfo.setToolTipText("Informação PACIENTE");
            btnReAgendar.setName("reagendar");
            btnReAgendar.setToolTipText("REAGENDAR Consulta");
            btnCaixa.setName("btnCaixa");
            btnCaixa.setToolTipText("Lançar CAIXA");
            btnCancelar.setName("btnCancelar");
            btnCancelar.setToolTipText("CANCELAR Agendamento.");

            String nome = MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText());
            String parametroParaPesquisa = "AGENDADO";

            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosPorMedico(parametroParaPesquisa, nome);

            Object rowData[] = new Object[12];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getFk_pacienteDto();
                rowData[2] = list.get(i).getNomeDto();
                rowData[3] = list.get(i).getCelularPrefDto();

                rowData[4] = formatador.format(list.get(i).getDataAgendamentoDto());
                rowData[5] = moeda.format(list.get(i).getRsTotalAgendamentoDto());
                rowData[6] = btnInfo;
                rowData[7] = btnReAgendar;
                rowData[8] = btnZap;
                rowData[9] = btnCaixa;
                rowData[10] = btnCancelar;
                rowData[11] = list.get(i).getNomeMedicoDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(160);//nome
            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//celular
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(5).setPreferredWidth(80);//valor total
            tabela.getColumnModel().getColumn(6).setPreferredWidth(28);//info
            tabela.getColumnModel().getColumn(7).setPreferredWidth(28);//reagendamento
            tabela.getColumnModel().getColumn(8).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(10).setPreferredWidth(34);//cancelar
            tabela.getColumnModel().getColumn(11).setPreferredWidth(100);//cancelar

            //fim da pesquisa 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            contarClienteTableModelMedico(nome);

    //acaoFimDePesquisNoBanco();
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void contarClienteTableModelMedico(String str) {

        int cont = 0;

        try {

            for (int i = 0; i < tabela.getRowCount(); i++) {
                tabela.getValueAt(i, 0);
                cont++;

            }
            Font fContagem = new Font("Tahoma", Font.BOLD, 18);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText(str + " " + String.valueOf(cont) + " Pacientes Agendados");
            lblLinhaInformativa.setFont(fContagem);
            lblLinhaInformativa.setBackground(Color.WHITE);
            lblLinhaInformativa.setForeground(new Color(0, 102, 102));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void contarClienteTableModel() {

        int cont = 0;

        try {

            for (int i = 0; i < tabela.getRowCount(); i++) {
                tabela.getValueAt(i, 0);
                cont++;

            }
            Font fContagem = new Font("Tahoma", Font.BOLD, 18);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText(String.valueOf(cont) + " Pacientes Agendados");
            lblLinhaInformativa.setFont(fContagem);
            lblLinhaInformativa.setBackground(Color.WHITE);
            lblLinhaInformativa.setForeground(new Color(0, 102, 102));

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void acaoBuscaPorNome() {

        //iniciando pesquisa no banco 
        acaoInicioDePesquisaBanco();

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            pesquisarPorNome();

        } else {
            addRowJTable();
        }

    }

    private void acaoBotaoAtualizar() {

        //iniciando pesquisa no banco 
        acaoInicioDePesquisaBanco();

        int numeroLinhas = tabela.getRowCount();

        if (numeroLinhas > 0) {

            while (tabela.getModel().getRowCount() > 0) {
                ((DefaultTableModel) tabela.getModel()).removeRow(0);

            }

            bataoAtualizar();

        } else {
            addRowJTable();
        }

    }


    private void btnPesquisarNomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarNomeFocusGained
        btnPesquisarNome.setBackground(Color.YELLOW);
        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
        if (recebeConexao == true) {
            acaoBuscaPorNome();

        } else {
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                    + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        }

    }//GEN-LAST:event_btnPesquisarNomeFocusGained

    private void btnPesquisarNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisarNomeFocusLost
        btnPesquisarNome.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisarNomeFocusLost

    private void btnPesquisaDataFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnPesquisaDataFocusLost
        btnPesquisarNome.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnPesquisaDataFocusLost

    //https://www.guj.com.br/t/aplicacao-swing-link-htlm/42167/10
    private void disparaZap(String url) {
        Desktop desktop = null;

        //Primeiro verificamos se é possível a integração com o desktop
        if (!Desktop.isDesktopSupported()) {
            throw new IllegalStateException("Desktop resources not supported!");
        }
        desktop = Desktop.getDesktop();

        //Agora vemos se é possível disparar o browser default.   
        if (!desktop.isSupported(Desktop.Action.BROWSE)) {
            throw new IllegalStateException("No default browser set!");
        }

        //Pega a URI de um componente de texto.   
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

        //Dispara o browser default, que pode ser o Explorer, Firefox ou outro.   
        try {
            desktop.browse(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private void dataFormato() {
//        Date data = jDateTxtDtAgenda.getDate();
//        System.err.println("Data" + new java.sql.Date(data.getTime()));
//        new java.util.Date(data.getTime());
//
//    }
    private void infoViaZapComParameter() {

        //     acaoInicioDePesquisaBanco();
        //**********************************************************************************
        //CURSO:Como Desenvolver Relatórios em Java com o JasperReports e o JasperStudio
        //apartir daqui começa de fato a parte pratica em relatório
        //https://www.udemy.com/course/relatorios-em-java-para-iniciantes-como-desenvolver/learn/lecture/18733998#overview
        //https://www.udemy.com/course/relatorios-em-java-para-iniciantes-como-desenvolver/learn/lecture/18734818#overview
        //***********************************************************************************************
        ConexaoUtil conecta = new ConexaoUtil();
        try {

            String capturado = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
            int codigo = Integer.parseInt(capturado);
            lblLinhaInformativa.setText("Apontando Caminho onde encontra-se o xml do panfleto...");
            InputStream jrxmlStream = MovimentoDaAgenda.class.getResourceAsStream("/ireport/infoZap.xml");
            //compilamos o arquivo 
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            Map<String, Object> parametros = new HashMap<>();
            //passamos o parâmetro pro relatório ja compilado para fazer o filtro dos dados 
            parametros.put("CONDICAO_ID", codigo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conecta.getConnection());
            lblLinhaInformativa.setText("Criando arquivo jasperPrint...");
            File file = new File("C:/agendaZap/infoZap.jrprint");

            //agora iremos fazer uma verificação para ve se o arquivo existe ou não na pasta
            if (!file.exists()) {

                //iremos entao criá-lo
                file.createNewFile();
                lblLinhaInformativa.setText("Salvando arquivo jasperPrint...");
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
                lblLinhaInformativa.setText("Exportando pdf infoZap.pdf...");
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/agendaZap/infoZap.pdf")));

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
                lblLinhaInformativa.setText("Exportando pdf infoZap.pdf...");
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/agendaZap/infoZap.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();
            }
            // JasperViewer view = new JasperViewer(jasperPrint, false);
            // view.setVisible(true);
            acaoFimDePesquisNoBanco();
        } catch (Exception ex) {
            erroViaEmail(ex.getMessage(), "VizualizandoInfoZap()");
            ex.printStackTrace();
            //   JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
        }

    }


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
                if (boton.getName().equals("Info")) {

                    /**
                     * Na thread de Execução será executado todos os blocos de
                     * codigos CASE 2
                     */
                    int flagBtn = 2;
                    progressBar(flagBtn);
                }

                if (boton.getName().equalsIgnoreCase("btnZap")) {

                    /**
                     * Na thread de Execução será executado todos os blocos de
                     * codigos CASE 3
                     */
                    int flagBtn = 3;
                    progressBar(flagBtn);

                }

                if (boton.getName().equalsIgnoreCase("reagendar")) {
                    /**
                     * Na thread de Execução será executado todos os blocos de
                     * codigos CASE 4
                     */
                    int flagBtn = 5;
                    progressBar(flagBtn);

                }

                if (boton.getName().equalsIgnoreCase("btnCaixa")) {

                    int linhaPergunta = tabela.getSelectedRow();
                    String Valor_Total_Atendimento = (tabela.getValueAt(linha, 5).toString());

                    /*Evento ao ser clicado executa código*/
                    int excluir = JOptionPane.showConfirmDialog(null, "O Paciente efetuou o pagamento\n"
                            + "no valor de " + Valor_Total_Atendimento + " ? \n"
                            + "Se SIM enviando dados do atendimento \n"
                            + "para Consultório (Profissional de Saúde).\n "
                            + "Tenha um bom Trabalho ^_^", "Informação", JOptionPane.YES_NO_OPTION);

                    if (excluir == JOptionPane.YES_OPTION) {
                        /**
                         * Na thread de Execução será executado todos os blocos
                         * de codigos CASE 4
                         */
                        int flagBtn = 4;
                        progressBar(flagBtn);

                    }
                }

                if (boton.getName().equalsIgnoreCase("btnCancelar")) {

                    /*Evento ao ser clicado executa código*/
                    int excluir = JOptionPane.showConfirmDialog(null, "Deseja Cancelar o Agendamento?", "Informação", JOptionPane.YES_NO_OPTION);

                    if (excluir == JOptionPane.YES_OPTION) {
                        /**
                         * Na thread de Execução será executado todos os blocos
                         * de codigos CASE 1
                         */
                        int flagBtn = 1;
                        progressBar(flagBtn);

                    }
                }

            }

        }

    }//GEN-LAST:event_tabelaMouseClicked

    private void enviarEmailCancelamento() {

        //email que será responsavél para envios 
        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        //email receptores 
        String emailClinicaParceira = "clinicahanna2009@gmail.com";
        String emailAnalistaControleQualidade = "sisjavaclinicarobo@gmail.com";

        String IdPaciente = tabela.getValueAt(tabela.getSelectedRow(), 1).toString();
        String nomePaciente = tabela.getValueAt(tabela.getSelectedRow(), 2).toString();
        String celularPaciente = tabela.getValueAt(tabela.getSelectedRow(), 3).toString();
        String dataAgenda = tabela.getValueAt(tabela.getSelectedRow(), 4).toString();
        String rsTotal = tabela.getValueAt(tabela.getSelectedRow(), 5).toString();

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
            email.setSubject("CANCELADO: [" + lblStatusData.getText() + "]");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("-------------DADOS DO PACIENTE---------------------------\n"
                    + "Nº:" + IdPaciente + " Paciente:" + nomePaciente + "Celular:" + celularPaciente + "\n"
                    + "----------------DADOS DO AGENDAMENTO---------------\n"
                    + "Data Agendada:" + dataAgenda + " R$ Valor Total: " + rsTotal + "\n"
                    + "--------------DADOS DO CANCELAMENTO--------------\n"
                    + "Data Registro:" + lblStatusData.getText() + "Hora:" + lblStatusHora.getText() + "\n"
                    + "Usuário do Sistema:" + lblNomeCompletoUsuario.getText() + "Perfil:" + lblPerfil.getText() + "\n"
                    + "-----------------------------------------------------------------------\n"
                    + "O temor do Senhor é o princípio da sabedoria, e o conhecimento do Santo a prudência. \n "
                    + "Provérbios 9:10  \n"
                    + "-----------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(emailClinicaParceira);
            email.addTo(emailAnalistaControleQualidade);

            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equalsIgnoreCase("The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.")) {
                System.out.println("sem conexão com a internet: \n "
                        + "houve uma instabilidade ao fazer acesso ao servidor da google\n");
            }
            //melhorar o códgio agui caso não consigoa enviar colocar uma imagem informando que houve erro ao tentar enviar o email '
            e.getMessage();
        }

    }

    private void enviarEmailRegistroCaixa() {

        //email que será responsavél para envios 
        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        //email receptores 
        String emailClinicaParceira = "clinicahanna2009@gmail.com";
        String emailAnalistaControleQualidade = "sisjavaclinicarobo@gmail.com";

        String IdPaciente = tabela.getValueAt(tabela.getSelectedRow(), 1).toString();
        String nomePaciente = tabela.getValueAt(tabela.getSelectedRow(), 2).toString();
        String celularPaciente = tabela.getValueAt(tabela.getSelectedRow(), 3).toString();
        String dataAgenda = tabela.getValueAt(tabela.getSelectedRow(), 4).toString();
        String rsTotal = tabela.getValueAt(tabela.getSelectedRow(), 5).toString();

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
            email.setSubject("CAIXA : [" + lblStatusData.getText() + "]");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("-------------DADOS DO PACIENTE---------------------------\n"
                    + "Nº:" + IdPaciente + " Paciente:" + nomePaciente + "Celular:" + celularPaciente + "\n"
                    + "----------------DADOS DO AGENDAMENTO---------------\n"
                    + "Data Agendada:" + dataAgenda + " R$ Valor Total: " + rsTotal + "\n"
                    + "--------------DADOS DO CANCELAMENTO--------------\n"
                    + "Data Registro:" + lblStatusData.getText() + "Hora:" + lblStatusHora.getText() + "\n"
                    + "Usuário do Sistema:" + lblNomeCompletoUsuario.getText() + "Perfil:" + lblPerfil.getText() + "\n"
                    + "-----------------------------------------------------------------------\n"
                    + "O temor do Senhor é o princípio da sabedoria, e o conhecimento do Santo a prudência. \n "
                    + "Provérbios 9:10  \n"
                    + "-----------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(emailClinicaParceira);
            email.addTo(emailAnalistaControleQualidade);

            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equalsIgnoreCase("The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.")) {
                System.out.println("sem conexão com a internet: \n "
                        + "houve uma instabilidade ao fazer acesso ao servidor da google\n");
            }
            //melhorar o códgio agui caso não consigoa enviar colocar uma imagem informando que houve erro ao tentar enviar o email '
            e.getMessage();
        }

    }


    private void btnRefreshFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnRefreshFocusGained
        txtNome.setText("");
        acaoBotaoAtualizar();
        txtNome.requestFocus();
        txtNome.setBackground(new Color(0, 102, 102));
        txtNome.setForeground(Color.WHITE);
    }//GEN-LAST:event_btnRefreshFocusGained

    private void jDateTxtDtAgendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateTxtDtAgendaFocusGained
        //        jDateTxtDtAgenda.setBackground(Color.YELLOW);
        //        jDateTxtDtAgenda.setForeground(Color.BLACK);
    }//GEN-LAST:event_jDateTxtDtAgendaFocusGained

    private void btnPesquisarNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesquisarNomeActionPerformed

    private void btnMuralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMuralActionPerformed

        try {

            if (estaFechado(formMural)) {
                formMural = new FrmMuralHannaProfSaude();
                DeskTop.add(formMural).setLocation(2, 3);
                formMural.setTitle("Mural - Agenda dos Médicos(Procedimentos)");
                formMural.setVisible(true);
            } else {
                formMural.toFront();
                formMural.setVisible(true);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }//GEN-LAST:event_btnMuralActionPerformed

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

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            e.printStackTrace();
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgressoMovimento;
    private javax.swing.JButton btnMural;
    private javax.swing.JButton btnPesquisaData;
    private javax.swing.JButton btnPesquisarNome;
    public static javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnValidaCPF;
    public static com.toedter.calendar.JDateChooser jDateTxtDtAgenda;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lblLinhaInformativa;
    private javax.swing.JLabel lblVerificacaoMovimento;
    private javax.swing.JPanel painelAtualizar;
    private javax.swing.JPanel painelJDataChooser;
    private javax.swing.JPanel painelPesquisaCPF;
    private javax.swing.JPanel painelPesquisaNome;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JTable tabela;
    public static javax.swing.JFormattedTextField txtCPFPaciente;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables

    private void pesquisar() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<AgendamentoDTO> list;

        try {

            JButton btnZap = new JButton();
            btnZap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/watsap.png")));

            JButton btnInfo = new JButton();
            btnInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/info.png")));

            JButton btnReAgendar = new JButton();
            btnReAgendar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/reagendar.png")));

            JButton btnCaixa = new JButton();
            btnCaixa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/doctorConsultorio.png")));

            JButton btnCancelar = new JButton();
            btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancel.png")));

            btnZap.setName("btnZap");
            btnZap.setToolTipText("INFORMATIVO infoZaP.");
            btnInfo.setName("Info");
            btnInfo.setToolTipText("Informação PACIENTE");
            btnReAgendar.setName("reagendar");
            btnReAgendar.setToolTipText("REAGENDAR Consulta");
            btnCaixa.setName("btnCaixa");
            btnCaixa.setToolTipText("Lançar CAIXA");
            btnCancelar.setName("btnCancelar");
            btnCancelar.setToolTipText("CANCELAR Agendamento.");

            String parametroParaPesquisa = "AGENDADO";
            String cpf = txtCPFPaciente.getText();
            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.listarTodosAgendadosCPF(parametroParaPesquisa, cpf);

            Object rowData[] = new Object[12];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getFk_pacienteDto();
                rowData[2] = list.get(i).getNomeDto();
                rowData[3] = list.get(i).getCelularPrefDto();

                rowData[4] = formatador.format(list.get(i).getDataAgendamentoDto());
                rowData[5] = moeda.format(list.get(i).getRsTotalAgendamentoDto());
                rowData[6] = btnInfo;
                rowData[7] = btnReAgendar;
                rowData[8] = btnZap;
                rowData[9] = btnCaixa;
                rowData[10] = btnCancelar;
                rowData[11] = list.get(i).getNomeMedicoDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            tabela.getColumnModel().getColumn(0).setPreferredWidth(38);//id
            tabela.getColumnModel().getColumn(1).setPreferredWidth(42);//id
            tabela.getColumnModel().getColumn(2).setPreferredWidth(160);//nome
            tabela.getColumnModel().getColumn(3).setPreferredWidth(95);//celular
            tabela.getColumnModel().getColumn(4).setPreferredWidth(80);//dtAgendamento
            tabela.getColumnModel().getColumn(5).setPreferredWidth(80);//valor total
            tabela.getColumnModel().getColumn(6).setPreferredWidth(28);//info
            tabela.getColumnModel().getColumn(7).setPreferredWidth(28);//reagendamento
            tabela.getColumnModel().getColumn(8).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(9).setPreferredWidth(34);
            tabela.getColumnModel().getColumn(10).setPreferredWidth(34);//cancelar
            tabela.getColumnModel().getColumn(11).setPreferredWidth(100);//cancelar

            //fim da pesquisa 
            acaoFimDePesquisNoBanco();
            contarClienteTableModel();

        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
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

    private void pesquisarUsuarioPorCPF() {
        String pesquisarUsuarioCPF = MetodoStaticosUtil.removerAcentosCaixAlta(txtCPFPaciente.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<PacienteDTO> list;

        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.filtrarPesqRapidaPorCPF(pesquisarUsuarioCPF);

            Object rowData[] = new Object[3];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = list.get(i).getCpfDto();

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(120);

            //coloca o cursor em modo de espera 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }
    }

}
