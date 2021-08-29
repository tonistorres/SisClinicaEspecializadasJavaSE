package br.com.multclin.telas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.bo.PacienteBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.ItemAgendamentoDAO;
import br.com.multclin.dao.PacienteDAO;
import br.com.multclin.dao.ProcedimentoDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.ItemAgendamentoDTO;
import br.com.multclin.dto.PacienteDTO;
import br.com.multclin.dto.ProcedimentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import static br.com.multclin.telas.Login.estado;
import static br.com.multclin.telas.MovimentoDaAgenda.btnRefresh;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.barraProgresso;
import static br.com.multclin.telas.TelaPrincipal.lblNomeCompletoUsuario;
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import static br.com.multclin.telas.TelaPrincipal.lblStatusData;
import static br.com.multclin.telas.TelaPrincipal.lblStatusHora;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author DaTorres
 */
public class TelaReagendamento extends javax.swing.JInternalFrame {

    int flagC = 0;
    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());
    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

    //label barra de ferramenta
    JLabel jLabelSystem = new JLabel();
    JLabel jLteste = new JLabel();
    JLabel jLTesteConexaoPositivo = new JLabel();
    JLabel jLRegistroCapturados = new JLabel();
    JLabel jLNuvemEstavel = new JLabel();
    JLabel jLDrivers = new JLabel();
    JLabel jLGerenciaBancoMysql = new JLabel();
    

    //DE PISCAR O CAMPO QUANDO RECEBER FOCO
    static int milissegundos = 0;
    static int segundos = 0;
    static int minutos = 0;
    static int horas = 0;
    //estado para controle de movimentos paineis txtLogin e txtSenha
    //bem essas flags já entram setada em true porque ja iniciam suas
    //tarefas no momento que o formulario é aberto 
    static boolean estado = true;

    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();
    PacienteBO medicoBO = new PacienteBO();
    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    AgendamentoBO agendamentoBO = new AgendamentoBO();

    ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();
    ItemAgendamentoDAO itemAgendamentoDAO = new ItemAgendamentoDAO();

    Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 

    /**
     * Código Mestre Chimura
     */
    private static TelaReagendamento instance = null;

    public static TelaReagendamento getInstance() {

        if (instance == null) {

            instance = new TelaReagendamento();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    //
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

    public TelaReagendamento() {

        initComponents();

        //Rota inicial no carregamento 
        flagC = 1;
        progressBar(flagC);

        frontEnd();

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
    }

    public void addRowJTable() {

        String idReagendamento = lblIDReagendamento.getText();
        int codigo = Integer.parseInt(idReagendamento);

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<ItemAgendamentoDTO> list;

        try {

            list = (ArrayList<ItemAgendamentoDTO>) itemAgendamentoDAO.pesquisarPorID(codigo);

            /**
             * IMPORTANTE: No momento de montar a estrutura para colocar os
             * dados na Tabela preencher de forma correta a quantidade de
             * colunas terá essa JTabel na Matriz Object[] conforme a linha de
             * código abaixo
             */
            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {
                rowData[0] = String.valueOf(list.get(i).getIdDto());
                rowData[1] = list.get(i).getNomeProcedeDto();
                rowData[2] = moeda.format(list.get(i).getRsBrutoDto());
                rowData[3] = moeda.format(list.get(i).getRsDescontoDto());
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);

            //coloca o cursor em modo de espera 
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        } catch (PersistenciaException ex) {

            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void frontEnd() {

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/adicionar.png")));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
    }


    private void progressBar(int flagC) {
        //***************
        //PROGRESS BAR //
        //***********************************************************************
        //Contribuição Canal Yoube:https://www.youtube.com/watch?v=co2cITXfXqY//
        //**********************************************************************

        new Thread() {

            public void run() {

                jLabelSystem.setIcon(null);
                jLteste.setIcon(null);
                jLTesteConexaoPositivo.setIcon(null);
                jLRegistroCapturados.setIcon(null);
                jLNuvemEstavel.setIcon(null);
                jLDrivers.setIcon(null);

                for (int i = 0; i < 101; i++) {

                    try {
                        sleep(50);

                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() == 10) {
                            /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 10% a barra de
                             * progresso inicia as configurações inicais
                             * futuramentes podemos fazer testes de redes...
                             *
                             */

                            lblVerificacao.setText("10% Inicializando barra de progresso");
                            lblVerificacao.setVisible(true);
                            btnSalvar.setEnabled(false);
                            jDateTxtDtReAgenda.setEnabled(false);

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/systems.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 20) {

                           /**
                             * GENERICO (PARA TODOS OS BOTOES) Em 10% a barra de
                             * progresso inicia as configurações inicais
                             * futuramentes podemos fazer testes de redes...
                             *
                             */
                            lblVerificacao.setText("20% Verificando se há conexão com a núvem ");

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/hostgator.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(55, 15, 50, 50);
                            fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            switch (flagC) {
                                case 1: {

                                    lblVerificacao.setText("30% Buscando dados núvem...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(105, 15, 50, 50);
                                    buscarDadosAgendamento();
                                    break;
                                }

                                case 2: {

                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(105, 15, 50, 50);

                                    lblVerificacao.setText("40% Registros Capturados...");
                                    executandoReagendamento();
                                    break;
                                }

                            }

                        } else if (barraProgresso.getValue() == 40) {

                            switch (flagC) {

                                case 1: {
                                    lblVerificacao.setText("40% Registros Capturados com sucesso...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLRegistroCapturados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/registrosCap.png")));
                                    painelSimulador.add(jLRegistroCapturados);
                                    jLRegistroCapturados.setBounds(165, 15, 50, 50);
                                    break;

                                }

                                case 2: {

                                    lblVerificacao.setText("40% SQL executada com sucesso...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/executeSQL.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(165, 15, 50, 50);

                                    break;
                                }

                            }

                        } else if (barraProgresso.getValue() == 50) {

                            switch (flagC) {
                                case 1: {

                                    lblVerificacao.setText("50% Preenchendo a tabela procedimentos...");

                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLNuvemEstavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/table.png")));
                                    painelSimulador.add(jLNuvemEstavel);
                                    jLNuvemEstavel.setBounds(215, 15, 50, 50);
                                    addRowJTable();
                                    break;
                                }

                                case 2: {

                                    lblVerificacao.setText("50% Enviando Email...");
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    jLNuvemEstavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/gmail.png")));
                                    painelSimulador.add(jLNuvemEstavel);
                                    jLNuvemEstavel.setBounds(215, 15, 50, 50);
                                    enviarEmailReagendamento();
                                    break;

                                }

                            }

                        } else if (barraProgresso.getValue() == 60) {

                            lblVerificacao.setText("60% Sistema estável...");
                        } else if (barraProgresso.getValue() == 70) {

                            lblVerificacao.setText("70% Drivers estáveis...");
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            jLDrivers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/ssd.png")));
                            painelSimulador.add(jLDrivers);
                            jLDrivers.setBounds(285, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 80) {

                            lblVerificacao.setText("80% Gerenciador de banco estável...");
                        } else if (barraProgresso.getValue() == 90) {

                            lblVerificacao.setText("90% Inicializando procedimentos finais...");
                        } else if (barraProgresso.getValue() == 100) {

                            lblVerificacao.setText("100% fim da Pesquisa.");
                            //se quiser desligar o relogio 
                            estado = false;

                            switch (flagC) {
                                case 2: {
                                    lblVerificacao.setText("100% fim da Pesquisa.");
                                    //se quiser desligar o relogio 
                                    estado = false;
                                    btnRefresh.requestFocus();
                                    dispose();
                                }
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                    }

                }
            }
        }.start();// iniciando a Thread
    }

    private void enviarEmailReagendamento() {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";
        String emailDestinatarioMultClin = "gl_salomao@hotmail.com";
        // String emailClinicaHanna = "clinicahanna2009@gmail.com";
        String emailPersonalizacao = "sisprotocoloj@gmail.com";
        //String emailProcuradorGeral = "affonsobbatista@hotmail.com";

        String IdPaciente = lblRepositIdPacienteAgenda.getText();
        String nomePaciente = lblRepositPacienteAgenda.getText();
        String celularPaciente = lblRepositCelularAgenda.getText();
        String reAgendamento = formatador.format(jDateTxtDtReAgenda.getDate());
        String rsTotal = lblValorTotalAgenda.getText();

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
            email.setSubject("REAGENDADO: [" + lblStatusData.getText() + "]");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("-------------DADOS DO PACIENTE---------------------------\n"
                    + "Nº:" + IdPaciente + " Paciente:" + nomePaciente + "Celular:" + celularPaciente + "\n"
                    + "----------------DADOS DO REAGENDAMENTO-----------\n"
                    + "Data Agendada:" + lblDataAgendamento.getText() + "Data Re-Agendada:" + reAgendamento + " R$ Valor Total: " + rsTotal + "\n"
                    + "--------------USUÁRIO RESPONSÁVEL--------------\n"
                    + "Data Envio Email" + lblStatusData.getText() + "Hora:" + lblStatusHora.getText() + "\n"
                    + "Usuário do Sistema:" + lblNomeCompletoUsuario.getText() + "Perfil:" + lblPerfil.getText() + "\n"
                    + "-----------------------------------------------------------------------\n"
                    + "O temor do Senhor é o princípio da sabedoria, e o conhecimento do Santo a prudência. \n "
                    + "Provérbios 9:10  \n"
                    + "-----------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            email.addTo(emailPersonalizacao);
            //email.addTo(emailClinicaHanna);
            email.addTo(emailDestinatarioMultClin);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equalsIgnoreCase("The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.")) {
                System.out.println("sem conexão com a internet: \n "
                        + "houve uma instabilidade ao fazer acesso ao servidor da google\n");
            }

            e.getMessage();
        }

    }

    private void executandoReagendamento() {

        try {

            AgendamentoBO agendamentoBO = new AgendamentoBO();

            String IdAgenda = lblIDReagendamento.getText();

            agendamentoDTO.setIdDto(Integer.parseInt(IdAgenda));
//            String novaData = formatador.format(jDateTxtDtReAgenda.getDate());
            agendamentoDTO.setDataAgendamentoDto(jDateTxtDtReAgenda.getDate());

            agendamentoDTO.setStatusAgendamentoDto("REAGENDADO");
            agendamentoDTO.setDtRegistroDto(lblStatusData.getText());
            agendamentoBO.atualizarReagendamentoBO(agendamentoDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void fazendoTesteConexao() {

        ConexaoUtil conecta = new ConexaoUtil();
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        if (recebeConexao == true) {
            lblVerificacao.setText("Verificando resultado: " + recebeConexao);
        } else {

            lblVerificacao.setForeground(Color.red);
            lblVerificacao.setText("SEM CONEXÃO COM A INTERNET OU ACESSO AO BANCO DE DADOS...");
            lblVerificacao.setFont(f);
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

    private void buscarDadosAgendamento() {

        String idAgendamento = lblIDReagendamento.getText();
        int codigo = Integer.parseInt(idAgendamento);

        cbEspecialidadeAgenda.setEnabled(false);

        ArrayList<AgendamentoDTO> list;
        try {

            //criar um método id para agendamentoDAO que retorne um lista passando um codigo interio 
            //  JOptionPane.showMessageDialog(null, "Codigo dentro de busca:"+codigo);
            list = (ArrayList<AgendamentoDTO>) agendamentoDAO.filtrarPorIDRetornaLista(codigo);
            System.out.println("Tamanho Lista:" + list.size());
            if (list.size() > 0) {

                for (int i = 0; i < list.size(); i++) {
                    //médico
                    cbEspecialidadeAgenda.setText(list.get(i).getEspecialidadeDto());
                    lblIdMedicoAgenda.setText(String.valueOf(list.get(i).getFk_medicoDto()));
                    lblRepositNomeMedicoAgenda.setText(list.get(i).getNomeMedicoDto());
                    lblValorTotalAgenda.setText(moeda.format(list.get(i).getRsTotalAgendamentoDto()));
                    //Convertendo as Datas de String para Date |String|--->>|Date|
                    //Date dataAgendamento = formatador.parse();
                    jDateTxtDtReAgenda.setDate(list.get(i).getDataAgendamentoDto());
                    lblDataAgendamento.setText(list.get(i).getDtRegistroDto());

                    //Paciente
                    lblRepositIdPacienteAgenda.setText(String.valueOf(list.get(i).getFk_pacienteDto()));
                    lblRepositPacienteAgenda.setText(list.get(i).getNomeDto());
                    lblRepositDtNascimentoAgenda.setText(list.get(i).getDataNascDto());
                    lblRepositSexoAgenda.setText(list.get(i).getSexoDto());
                    lblRepositEstadoCivilAgenda.setText(list.get(i).getEstadoCivilDto());
                    lblRepositConjugeAgenda.setText(list.get(i).getConjugeDto());
                    lblRepositMaeAgenda.setText(list.get(i).getMaeDto());
                    lblRepositPaiAgenda.setText(list.get(i).getPaiDto());
                    lblRepositCelularAgenda.setText(list.get(i).getCelularPrefDto());
                    lblRepositUFAgenda.setText(list.get(i).getUfDto());
                    lblRepositCidadeAgenda.setText(list.get(i).getCidadeDto());
                    lblRepositBairro.setText(list.get(i).getBairroDto());
                    lblRepositRuaAgenda.setText(list.get(i).getRuaDto());
                    lblRepositCPFPacienteAgenda.setText(list.get(i).getCpfDto());

                }

                //DEPOIS ABRE O FORMULARIO 
            } else {
                System.out.println("Não exite itens na lista ");

            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            dispose();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        painelPaciente = new javax.swing.JPanel();
        painelSimulador = new javax.swing.JPanel();
        lblVerificacao = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();
        painelSobreMedico = new javax.swing.JPanel();
        lblRepositNomeMedicoAgenda = new javax.swing.JLabel();
        lblMedico = new javax.swing.JLabel();
        lblEspecialidades = new javax.swing.JLabel();
        lblIdMedicoAgenda = new javax.swing.JLabel();
        cbEspecialidadeAgenda = new javax.swing.JLabel();
        painelPaciente1 = new javax.swing.JPanel();
        lblRepositIdPacienteAgenda = new javax.swing.JLabel();
        lblRepositPacienteAgenda = new javax.swing.JLabel();
        lblNomePaciente = new javax.swing.JLabel();
        lblDtNascimento = new javax.swing.JLabel();
        lblRepositDtNascimentoAgenda = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblRepositSexoAgenda = new javax.swing.JLabel();
        lblEstadoCivil = new javax.swing.JLabel();
        lblRepositEstadoCivilAgenda = new javax.swing.JLabel();
        lblConjuge = new javax.swing.JLabel();
        lblRepositConjugeAgenda = new javax.swing.JLabel();
        lblMae = new javax.swing.JLabel();
        lblRepositMaeAgenda = new javax.swing.JLabel();
        lblPai = new javax.swing.JLabel();
        lblRepositPaiAgenda = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        lblRepositCelularAgenda = new javax.swing.JLabel();
        lblUF = new javax.swing.JLabel();
        lblRepositUFAgenda = new javax.swing.JLabel();
        lblCidade = new javax.swing.JLabel();
        lblRepositCidadeAgenda = new javax.swing.JLabel();
        lblBairro = new javax.swing.JLabel();
        lblRepositBairro = new javax.swing.JLabel();
        lblRua = new javax.swing.JLabel();
        lblRepositRuaAgenda = new javax.swing.JLabel();
        lblRepositCPFPacienteAgenda = new javax.swing.JLabel();
        painelReagendar = new javax.swing.JPanel();
        lblIDReagendamento = new javax.swing.JLabel();
        jDateTxtDtReAgenda = new com.toedter.calendar.JDateChooser();
        btnAdicionar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelValorTotal = new javax.swing.JPanel();
        lblValorTotalAgenda = new javax.swing.JLabel();
        lblDataAgendamento = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Reagendamento");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
        painelCabecalho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPaciente.setBackground(java.awt.Color.white);
        painelPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSimulador.setLayout(null);

        lblVerificacao.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblVerificacao.setForeground(new java.awt.Color(0, 102, 102));
        painelSimulador.add(lblVerificacao);
        lblVerificacao.setBounds(20, 90, 290, 17);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(10, 110, 300, 24);

        painelPaciente.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 320, 160));

        painelSobreMedico.setBackground(java.awt.Color.white);
        painelSobreMedico.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Médico:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSobreMedico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositNomeMedicoAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositNomeMedicoAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelSobreMedico.add(lblRepositNomeMedicoAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 240, 20));

        lblMedico.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMedico.setForeground(new java.awt.Color(0, 102, 102));
        lblMedico.setText("Médico:");
        painelSobreMedico.add(lblMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 24, -1, -1));

        lblEspecialidades.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEspecialidades.setForeground(new java.awt.Color(0, 102, 102));
        lblEspecialidades.setText("Especialidade");
        painelSobreMedico.add(lblEspecialidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        lblIdMedicoAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblIdMedicoAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelSobreMedico.add(lblIdMedicoAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 30, 20));

        cbEspecialidadeAgenda.setToolTipText("");
        cbEspecialidadeAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelSobreMedico.add(cbEspecialidadeAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 44, 240, 20));

        painelPaciente.add(painelSobreMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 340, 80));

        painelPaciente1.setBackground(java.awt.Color.white);
        painelPaciente1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Paciente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPaciente1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositIdPacienteAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositIdPacienteAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositIdPacienteAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 20));

        lblRepositPacienteAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPacienteAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositPacienteAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 220, 20));

        lblNomePaciente.setForeground(new java.awt.Color(0, 102, 102));
        lblNomePaciente.setText(" ID     Paciente:  CPF.:");
        painelPaciente1.add(lblNomePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 120, -1));

        lblDtNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lblDtNascimento.setText("Data Nascimento:");
        painelPaciente1.add(lblDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, -1));

        lblRepositDtNascimentoAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositDtNascimentoAgenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositDtNascimentoAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositDtNascimentoAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 90, 20));

        lblSexo.setForeground(new java.awt.Color(0, 102, 102));
        lblSexo.setText("Sexo:");
        painelPaciente1.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

        lblRepositSexoAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositSexoAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositSexoAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 120, 20));

        lblEstadoCivil.setForeground(new java.awt.Color(0, 102, 102));
        lblEstadoCivil.setText("Estado Civil");
        painelPaciente1.add(lblEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, -1, -1));

        lblRepositEstadoCivilAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositEstadoCivilAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositEstadoCivilAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, 140, 20));

        lblConjuge.setForeground(new java.awt.Color(0, 102, 102));
        lblConjuge.setText("Conjuge:");
        painelPaciente1.add(lblConjuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 65, -1, -1));

        lblRepositConjugeAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositConjugeAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositConjugeAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 210, 20));

        lblMae.setForeground(new java.awt.Color(0, 102, 102));
        lblMae.setText("Mãe:");
        painelPaciente1.add(lblMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 65, -1, -1));

        lblRepositMaeAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositMaeAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositMaeAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, 220, 20));

        lblPai.setForeground(new java.awt.Color(0, 102, 102));
        lblPai.setText("Pai:");
        painelPaciente1.add(lblPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 65, -1, -1));

        lblRepositPaiAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPaiAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositPaiAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 200, 20));

        lblCelular.setForeground(new java.awt.Color(0, 102, 102));
        lblCelular.setText("Celular:");
        painelPaciente1.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, -1, -1));

        lblRepositCelularAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositCelularAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositCelularAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 110, 20));

        lblUF.setForeground(new java.awt.Color(0, 102, 102));
        lblUF.setText("UF:");
        painelPaciente1.add(lblUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 105, -1, -1));

        lblRepositUFAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositUFAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositUFAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 30, 20));

        lblCidade.setForeground(new java.awt.Color(0, 102, 102));
        lblCidade.setText("Cidade:");
        painelPaciente1.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 105, -1, -1));

        lblRepositCidadeAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositCidadeAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositCidadeAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 130, 20));

        lblBairro.setForeground(new java.awt.Color(0, 102, 102));
        lblBairro.setText("Bairro:");
        painelPaciente1.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 105, -1, -1));

        lblRepositBairro.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositBairro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 100, 20));

        lblRua.setForeground(new java.awt.Color(0, 102, 102));
        lblRua.setText("Rua:");
        painelPaciente1.add(lblRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 105, -1, -1));

        lblRepositRuaAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositRuaAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente1.add(lblRepositRuaAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 230, 20));

        lblRepositCPFPacienteAgenda.setForeground(java.awt.Color.orange);
        painelPaciente1.add(lblRepositCPFPacienteAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 130, 15));

        painelPaciente.add(painelPaciente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 670, 160));

        painelReagendar.setBackground(java.awt.Color.white);
        painelReagendar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelReagendar.setLayout(null);

        lblIDReagendamento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIDReagendamento.setForeground(new java.awt.Color(0, 102, 102));
        lblIDReagendamento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIDReagendamento.setText("ID");
        painelReagendar.add(lblIDReagendamento);
        lblIDReagendamento.setBounds(10, 20, 50, 30);

        jDateTxtDtReAgenda.setForeground(java.awt.Color.white);
        jDateTxtDtReAgenda.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jDateTxtDtReAgenda.setPreferredSize(new java.awt.Dimension(87, 25));
        jDateTxtDtReAgenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jDateTxtDtReAgendaFocusGained(evt);
            }
        });
        painelReagendar.add(jDateTxtDtReAgenda);
        jDateTxtDtReAgenda.setBounds(60, 20, 140, 30);

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
        painelReagendar.add(btnAdicionar);
        btnAdicionar.setBounds(220, 10, 45, 45);

        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelReagendar.add(btnSalvar);
        btnSalvar.setBounds(280, 10, 45, 45);

        painelPaciente.add(painelReagendar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 5, 340, 70));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdProce", "PROCEDIMENTO", "R$ VALOR", "R$ DESCONTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(0).setPreferredWidth(55);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        painelPaciente.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 460, 110));

        painelValorTotal.setBackground(java.awt.Color.white);
        painelValorTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "R$ Total:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N
        painelValorTotal.setLayout(null);

        lblValorTotalAgenda.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblValorTotalAgenda.setForeground(new java.awt.Color(0, 102, 102));
        lblValorTotalAgenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelValorTotal.add(lblValorTotalAgenda);
        lblValorTotalAgenda.setBounds(9, 20, 190, 50);

        painelPaciente.add(painelValorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 330, 210, 80));

        lblDataAgendamento.setForeground(new java.awt.Color(0, 102, 102));
        lblDataAgendamento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelPaciente.add(lblDataAgendamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 410, 210, 30));

        painelCabecalho.add(painelPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 450));

        getContentPane().add(painelCabecalho, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jDateTxtDtReAgendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDateTxtDtReAgendaFocusGained
        //        jDateTxtDtAgenda.setBackground(Color.YELLOW);
        //        jDateTxtDtAgenda.setForeground(Color.BLACK);
    }//GEN-LAST:event_jDateTxtDtReAgendaFocusGained

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        /**
         * Na thread de Execução será executado todos os blocos de codigos CASE
         * 2
         */
        int flagBtn = 2;
        progressBar(flagBtn);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnAdicionarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusGained
        //   efeitoAoReceberFoco(btnAdicionar);

    }//GEN-LAST:event_btnAdicionarFocusGained

    private void btnAdicionarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnAdicionarFocusLost
        btnAdicionar.setBackground(Color.WHITE);
    }//GEN-LAST:event_btnAdicionarFocusLost

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        jDateTxtDtReAgenda.setEnabled(true);
        btnAdicionar.setEnabled(false);
        btnSalvar.setEnabled(true);
        // acaoBotaoAdicionar();

    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

    }//GEN-LAST:event_tabelaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel cbEspecialidadeAgenda;
    public static com.toedter.calendar.JDateChooser jDateTxtDtReAgenda;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblConjuge;
    private javax.swing.JLabel lblDataAgendamento;
    private javax.swing.JLabel lblDtNascimento;
    private javax.swing.JLabel lblEspecialidades;
    private javax.swing.JLabel lblEstadoCivil;
    public static javax.swing.JLabel lblIDReagendamento;
    public static javax.swing.JLabel lblIdMedicoAgenda;
    private javax.swing.JLabel lblMae;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblNomePaciente;
    private javax.swing.JLabel lblPai;
    public static javax.swing.JLabel lblRepositBairro;
    public static javax.swing.JLabel lblRepositCPFPacienteAgenda;
    public static javax.swing.JLabel lblRepositCelularAgenda;
    public static javax.swing.JLabel lblRepositCidadeAgenda;
    public static javax.swing.JLabel lblRepositConjugeAgenda;
    public static javax.swing.JLabel lblRepositDtNascimentoAgenda;
    public static javax.swing.JLabel lblRepositEstadoCivilAgenda;
    public static javax.swing.JLabel lblRepositIdPacienteAgenda;
    public static javax.swing.JLabel lblRepositMaeAgenda;
    public static javax.swing.JLabel lblRepositNomeMedicoAgenda;
    public static javax.swing.JLabel lblRepositPacienteAgenda;
    public static javax.swing.JLabel lblRepositPaiAgenda;
    public static javax.swing.JLabel lblRepositRuaAgenda;
    public static javax.swing.JLabel lblRepositSexoAgenda;
    public static javax.swing.JLabel lblRepositUFAgenda;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblUF;
    private javax.swing.JLabel lblValorTotalAgenda;
    private javax.swing.JLabel lblVerificacao;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JPanel painelPaciente;
    private javax.swing.JPanel painelPaciente1;
    private javax.swing.JPanel painelReagendar;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JPanel painelSobreMedico;
    private javax.swing.JPanel painelValorTotal;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
