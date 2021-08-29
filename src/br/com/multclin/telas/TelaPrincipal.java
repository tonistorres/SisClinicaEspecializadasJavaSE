package br.com.multclin.telas;

import br.com.multcin.forms.consultas.ConsultarMovimento;
import br.com.multclin.dao.InfoControleConexaoDAO;
import br.com.multclin.dao.ModeloAgendamentoItemDAO;
import br.com.multclin.dto.ModeloAgendamentoItemDTO;

import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;

import br.com.multclin.thread.MinhaThred;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;

import static java.lang.Thread.sleep;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
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
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author DaTorres
 */
public class TelaPrincipal extends javax.swing.JFrame {

    br.com.multclin.telas.SubTelaPaisRegiao formPais;
    br.com.multclin.telas.TelaPaciente formPaciente;
    br.com.multclin.telas.SubTelaFone formFone;
    br.com.multclin.telas.TelaUsuario formUsuario;
    br.com.multclin.telas.TelaMedico formMedico;
    br.com.multclin.telas.TelaCadastroEspecialidades formCadEspec;
    br.com.multclin.telas.TelaAgendamento formAgenda;
    br.com.multclin.telas.MovimentoDaAgenda formMovimentoAgenda;
    br.com.multclin.telas.TelaCosultarMovimento formConsultarMove;
    br.com.multclin.telas.Modulo2AtendimentoConsultorio modulo2ConsultorioAtendimento;
    br.com.multclin.telas.TelaCosultarProcedimentoPorMedico formConsultarProceMedico;

//TESTE DE CONECTIVIDADE COM BANCO DE DADOS [[[[[PRIMERIO CRONOMETRO]]]]
    //Documentação:https://www.youtube.com/watch?v=LoKQvAQpL3w
    //************************************************************
    // inicializando variáveis do tipo int e estáticas
    static int milissegundos = 0;
    static int segundos = 0;
    static int minutos = 0;
    static int horas = 0;
    static boolean estado = true;
    Font f = new Font("Tahoma", Font.BOLD, 9);

    JLabel jLabelAcoroJesus = new JLabel();

    InfoControleConexaoDAO controle = new InfoControleConexaoDAO();
    ModeloAgendamentoItemDAO modeloAgendamentoItemDAO = new ModeloAgendamentoItemDAO();

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

    private void metodoStartTesteConexao() {

        estado = true;

        Thread t;
        t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (milissegundos > 1000) {
                                milissegundos = 0;
                                segundos++;

                            }

                            if (segundos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos++;

                                if (minutos == 1 || minutos == 10 || minutos == 30 || minutos == 50 || minutos == 59) {

                                    buscarInforConexao();

                                    int maxConexaoNoServidor = Integer.parseInt(lblMaxServidor.getText());
                                    int maxDeConexaoPorUsuario = Integer.parseInt(lblMaxUsuario.getText());
                                    int conexoesAbertasInstate = Integer.parseInt(lblConexoesAtivas.getText());

                                    if (conexoesAbertasInstate <= ((int) maxDeConexaoPorUsuario / 2)) {
                                        painelInfoComunic.setBackground(Color.WHITE);
                                        PanelInformacoesAdicionais.setBackground(Color.WHITE);
                                        //     MinhaThred tFecharOuReconectar = new MinhaThred("tarefa1", 1000);

                                    }
                                    if ((conexoesAbertasInstate > (int) maxDeConexaoPorUsuario / 2) && (conexoesAbertasInstate <= maxDeConexaoPorUsuario - 5)) {
                                        painelInfoComunic.setBackground(new Color(32, 178, 170));
                                        PanelInformacoesAdicionais.setBackground(new Color(32, 178, 170));
                                        painelSairSistema.setBackground(new Color(32, 178, 170));

                                        //    MinhaThred tFecharOuReconectar = new MinhaThred("tarefa1", 1000);
                                    }
                                    if (maxConexaoNoServidor == 0) {
                                        painelInfoComunic.setBackground(new Color(240, 255, 255));
                                        PanelInformacoesAdicionais.setBackground(new Color(240, 255, 255));
                                        painelSairSistema.setBackground(new Color(240, 255, 255));
                                        TelaPrincipal.lblSaindoSistemaPorFaltaConexao.setVisible(true);
                                        mnPrincipal.setBackground(Color.red);
                                        //criei uma instancia da minha Classe MinhaThread                                        
                                        MinhaThred tFecharOuReconectar = new MinhaThred("tarefa1", 1000);

                                    }

                                }

                            }

                            if (minutos > 60) {
                                milissegundos = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }

                            lblMilissegundos.setText(" : " + milissegundos);
                            milissegundos++;
                            lblSegundos.setText(" : " + segundos);
                            lblMinuto.setText(" : " + minutos);
                            lblHora.setText(" : " + horas);

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

    public TelaPrincipal() {
        initComponents();

        personalizarFrontEnd();
        //inicializa o robo de controle de conexão ativas (trafégo)
        metodoStartTesteConexao();
        aoCarregar();

        //alert estado critico sem conexao 
        lblSaindoSistemaPorFaltaConexao.setVisible(false);
        //  this.setExtendedState(MAXIMIZED_BOTH);// propriedade para maximizar tela tanto na vertical quanto na horizontal Canal:https://www.youtube.com/watch?v=-Y2rpyIWj9c
        // Desabilitar o botão fechar de um JFrame
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);//desabilita o botão fechar da TelaPrincipal 
    }

    private void aoCarregar() {


    }

    private void personalizarFrontEnd() {

        //MENU CADASTROS
        itemUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/menuFuncionario.png")));
        itemPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/paciente.png")));
        itemMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/doctor.png")));
        itemCadEspecialidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/especialidades.png")));
//        itemRelatorioAgendamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/agendar.png")));

        //MENU CONSULTA 
        itemConultarMovimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/consultarMovimento.png")));
        itemConsultasAtendimentoConsultorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/atendimento.png")));
//        itemConultarProcedimentosPorMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/consultarProcede.png")));

        //BARRA DE FERRAMENTAS 
        btnBarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/menuFuncionario.png")));
        btnBarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/paciente.png")));
        btnBarMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/doctor.png")));
        btnBarAgendamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/agendar.png")));
        btnMovimentoAgendamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/agendaMov.png")));
        btnModulo2ConsultorioAtendimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/atendimento.png")));

        lblVerificacao.setForeground(Color.WHITE);

        //painel inferior 
        lblVerificacao.setVisible(false);
        barraProgresso.setVisible(false);

        //barra status
        lblImagemStatusData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/status/data.png")));
        lblStatusImagemHora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/status/hora.png")));
        //capturandoData();
        criarDataHoraBarraStatus();

        //setando incone de usuario e data
        lblImagemData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/calendar.png")));
        progressBarraPesquisa.setIndeterminate(true);

        //personalizando informações de Maquina 
        lblRepositorioCPU.setForeground(new Color(0, 102, 102));
        lblRepositorioHD.setForeground(new Color(0, 102, 102));
        lblCPU.setForeground(Color.BLACK);
        lblHD.setForeground(Color.BLACK);
        //painelMiniMaquia.setBackground(new Color(9, 81, 107));
    }

    private void botoesBarraFerramenta() {
//
//        //CRIAR UMA CLASSE QUE SERÁ RESPONSAVEL POR EXCUTAR MEUS BOTOES
//        //EXECUTAR EVENTOS 
//        //https://www.youtube.com/watch?v=juVANshV2Eo
        Thehandler handler = new Thehandler();
//
//        //ADICIONANDO AOS BOTOES AÇOES QUE SERAO TRATADAS PELO ACTIONLISLISTENER
//        btnCadEmpresa.addActionListener(handler);
//        btnCadFuncionario.addActionListener(handler);
//        btnFuncionario.addActionListener(handler);
//        btnPessoasOutros.addActionListener(handler);
//        btnProtocolarEmpresa.addActionListener(handler);
//        btnProtocolarTFD.addActionListener(handler);
//        btnProtocolarDepartamentos.addActionListener(handler);
//        btnProtocolarPessoasOutros.addActionListener(handler);
//        btnHelp.addActionListener(handler);
//        btnExit.addActionListener(handler);

    }

    private void criarDataHoraBarraStatus() {
        //https://www.youtube.com/watch?v=55CgbuWnmNc
        Date dataDoSistema = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        lblStatusData.setText(formato.format(dataDoSistema));

        Font f = new Font("Tahoma", Font.BOLD, 14);
        lblStatusData.setForeground(Color.RED);
        lblStatusData.setFont(f);

        Timer timer = new Timer(1000, new hora());
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelBarraStatus = new javax.swing.JPanel();
        BarraFerramentas = new javax.swing.JToolBar();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        btnBarUsuario = new javax.swing.JButton();
        lblEspaco1 = new javax.swing.JLabel();
        btnBarPaciente = new javax.swing.JButton();
        btnBarMedico = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        btnBarAgendamento = new javax.swing.JButton();
        btnMovimentoAgendamento = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        lblEspaco2 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JToolBar.Separator();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        btnModulo2ConsultorioAtendimento = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jSeparator11 = new javax.swing.JToolBar.Separator();
        lblEspaco3 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JToolBar.Separator();
        btnHelp = new javax.swing.JButton();
        jSeparator14 = new javax.swing.JToolBar.Separator();
        btnExit = new javax.swing.JButton();
        jSeparator15 = new javax.swing.JToolBar.Separator();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        DeskTop = new javax.swing.JDesktopPane();
        lblSaindoSistemaPorFaltaConexao = new javax.swing.JLabel();
        lblFundo = new javax.swing.JLabel();
        PanelInformacoesAdicionais = new javax.swing.JPanel();
        lblDataSistema = new javax.swing.JLabel();
        lblUsuarioLogado = new javax.swing.JLabel();
        lblImagemUser = new javax.swing.JLabel();
        lblImagemData = new javax.swing.JLabel();
        lblPerfil = new javax.swing.JLabel();
        lblNomeCompletoUsuario = new javax.swing.JLabel();
        painelInfoComunic = new javax.swing.JPanel();
        rtlMaximoConexaoServidor = new javax.swing.JLabel();
        lblMaxServidor = new javax.swing.JLabel();
        rtlUsuarioMaximoConexao = new javax.swing.JLabel();
        lblMaxUsuario = new javax.swing.JLabel();
        rtlNumeroConexoesAtivas = new javax.swing.JLabel();
        lblConexoesAtivas = new javax.swing.JLabel();
        painelCronometro = new javax.swing.JPanel();
        lblHora = new javax.swing.JLabel();
        lblMinuto = new javax.swing.JLabel();
        lblSegundos = new javax.swing.JLabel();
        lblMilissegundos = new javax.swing.JLabel();
        lblRepositorioCPU = new javax.swing.JLabel();
        lblCPU = new javax.swing.JLabel();
        lblRepositorioHD = new javax.swing.JLabel();
        lblHD = new javax.swing.JLabel();
        lblStatusData = new javax.swing.JLabel();
        lblImagemStatusData = new javax.swing.JLabel();
        lblStatusImagemHora = new javax.swing.JLabel();
        lblStatusHora = new javax.swing.JLabel();
        painelSairSistema = new javax.swing.JPanel();
        barraProgresso = new javax.swing.JProgressBar();
        lblVerificacao = new javax.swing.JLabel();
        mnPrincipal = new javax.swing.JMenuBar();
        Paciente = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        itemUsuario = new javax.swing.JMenuItem();
        itemCadEspecialidade = new javax.swing.JMenuItem();
        itemMedico = new javax.swing.JMenuItem();
        itemPaciente = new javax.swing.JMenuItem();
        menuFone = new javax.swing.JMenu();
        itemMenuPaisRegiao = new javax.swing.JMenuItem();
        mnConsultas = new javax.swing.JMenu();
        itemConultarMovimento = new javax.swing.JMenuItem();
        itemConsultasAtendimentoConsultorio = new javax.swing.JMenuItem();
        mnRelarorios = new javax.swing.JMenu();
        mnAjuda = new javax.swing.JMenu();
        itmnSobre = new javax.swing.JMenuItem();
        mnSair = new javax.swing.JMenu();
        itmnSairSistema = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clinica Hanna -    Pv. 9:10 - O temor do Senhor é o princípio da sabedoria, o conhecimento do Santo a prudência. ");
        setBackground(java.awt.Color.white);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPrincipal.setPreferredSize(new java.awt.Dimension(789, 700));
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelBarraStatus.setBackground(new java.awt.Color(51, 102, 255));
        painelBarraStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        painelBarraStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelPrincipal.add(painelBarraStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 539, 770, -1));

        BarraFerramentas.setRollover(true);
        BarraFerramentas.add(jSeparator3);

        btnBarUsuario.setToolTipText("Cadastro de Usuário");
        btnBarUsuario.setFocusable(false);
        btnBarUsuario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBarUsuario.setPreferredSize(new java.awt.Dimension(31, 31));
        btnBarUsuario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarUsuarioActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnBarUsuario);
        BarraFerramentas.add(lblEspaco1);

        btnBarPaciente.setToolTipText("CADASTRAR PACIENTE AQUI");
        btnBarPaciente.setFocusable(false);
        btnBarPaciente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBarPaciente.setPreferredSize(new java.awt.Dimension(31, 31));
        btnBarPaciente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarPacienteActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnBarPaciente);

        btnBarMedico.setFocusable(false);
        btnBarMedico.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBarMedico.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBarMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarMedicoActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnBarMedico);
        BarraFerramentas.add(jSeparator2);

        btnBarAgendamento.setToolTipText("AGENDAR AQUI");
        btnBarAgendamento.setFocusable(false);
        btnBarAgendamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBarAgendamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBarAgendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarAgendamentoActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnBarAgendamento);

        btnMovimentoAgendamento.setToolTipText("MOVIMENTAR AGENDA AQUI");
        btnMovimentoAgendamento.setFocusable(false);
        btnMovimentoAgendamento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMovimentoAgendamento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMovimentoAgendamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovimentoAgendamentoActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnMovimentoAgendamento);
        BarraFerramentas.add(jSeparator4);
        BarraFerramentas.add(jSeparator6);
        BarraFerramentas.add(jSeparator7);

        lblEspaco2.setText("                          ");
        BarraFerramentas.add(lblEspaco2);
        BarraFerramentas.add(jSeparator12);
        BarraFerramentas.add(jSeparator8);

        btnModulo2ConsultorioAtendimento.setFocusable(false);
        btnModulo2ConsultorioAtendimento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModulo2ConsultorioAtendimento.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModulo2ConsultorioAtendimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModulo2ConsultorioAtendimentoActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnModulo2ConsultorioAtendimento);
        BarraFerramentas.add(jSeparator9);
        BarraFerramentas.add(jSeparator10);
        BarraFerramentas.add(jSeparator11);
        BarraFerramentas.add(lblEspaco3);
        BarraFerramentas.add(jSeparator13);

        btnHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/menuHelp.png"))); // NOI18N
        btnHelp.setToolTipText("Ajuda Sobre o Sistema");
        btnHelp.setFocusable(false);
        btnHelp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHelp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnHelp);
        BarraFerramentas.add(jSeparator14);

        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/ferramentas/menuexit.png"))); // NOI18N
        btnExit.setToolTipText("Sair do Sistema");
        btnExit.setFocusable(false);
        btnExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        BarraFerramentas.add(btnExit);
        BarraFerramentas.add(jSeparator15);

        progressBarraPesquisa.setPreferredSize(new java.awt.Dimension(25, 14));
        BarraFerramentas.add(progressBarraPesquisa);

        painelPrincipal.add(BarraFerramentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 789, 30));

        lblSaindoSistemaPorFaltaConexao.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        lblSaindoSistemaPorFaltaConexao.setForeground(java.awt.Color.white);
        lblSaindoSistemaPorFaltaConexao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblFundo.setBackground(new java.awt.Color(255, 255, 255));
        lblFundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/fundo/fundoClinicaHana.jpg"))); // NOI18N

        javax.swing.GroupLayout DeskTopLayout = new javax.swing.GroupLayout(DeskTop);
        DeskTop.setLayout(DeskTopLayout);
        DeskTopLayout.setHorizontalGroup(
            DeskTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DeskTopLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSaindoSistemaPorFaltaConexao, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
                .addGap(84, 84, 84))
            .addGroup(DeskTopLayout.createSequentialGroup()
                .addComponent(lblFundo)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        DeskTopLayout.setVerticalGroup(
            DeskTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeskTopLayout.createSequentialGroup()
                .addComponent(lblFundo, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSaindoSistemaPorFaltaConexao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        DeskTop.setLayer(lblSaindoSistemaPorFaltaConexao, javax.swing.JLayeredPane.DEFAULT_LAYER);
        DeskTop.setLayer(lblFundo, javax.swing.JLayeredPane.DEFAULT_LAYER);

        painelPrincipal.add(DeskTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 33, 840, 580));

        getContentPane().add(painelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 613));

        PanelInformacoesAdicionais.setBackground(java.awt.Color.white);
        PanelInformacoesAdicionais.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações:", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        PanelInformacoesAdicionais.setPreferredSize(new java.awt.Dimension(180, 530));
        PanelInformacoesAdicionais.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblDataSistema.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblDataSistema.setForeground(new java.awt.Color(0, 102, 102));
        lblDataSistema.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDataSistema.setText("Data");
        PanelInformacoesAdicionais.add(lblDataSistema, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 150, 100, 20));

        lblUsuarioLogado.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblUsuarioLogado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuarioLogado.setText("Login");
        lblUsuarioLogado.setPreferredSize(new java.awt.Dimension(31, 15));
        PanelInformacoesAdicionais.add(lblUsuarioLogado, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 140, -1));

        lblImagemUser.setBackground(new java.awt.Color(255, 102, 102));
        lblImagemUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagemUser.setBorder(javax.swing.BorderFactory.createTitledBorder("Icon:"));
        PanelInformacoesAdicionais.add(lblImagemUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 25, 80, 85));

        lblImagemData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PanelInformacoesAdicionais.add(lblImagemData, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 129, 80, 50));

        lblPerfil.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblPerfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPerfil.setText("Perfil");
        PanelInformacoesAdicionais.add(lblPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 140, -1));

        lblNomeCompletoUsuario.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblNomeCompletoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNomeCompletoUsuario.setText("Usuario");
        PanelInformacoesAdicionais.add(lblNomeCompletoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 200, 23));

        painelInfoComunic.setBackground(java.awt.Color.white);
        painelInfoComunic.setLayout(null);

        rtlMaximoConexaoServidor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtlMaximoConexaoServidor.setText("Servidor Máx:");
        painelInfoComunic.add(rtlMaximoConexaoServidor);
        rtlMaximoConexaoServidor.setBounds(20, 10, 100, 14);

        lblMaxServidor.setForeground(new java.awt.Color(0, 102, 102));
        lblMaxServidor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaxServidor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelInfoComunic.add(lblMaxServidor);
        lblMaxServidor.setBounds(120, 10, 50, 22);

        rtlUsuarioMaximoConexao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtlUsuarioMaximoConexao.setText("Usuário Máx:");
        painelInfoComunic.add(rtlUsuarioMaximoConexao);
        rtlUsuarioMaximoConexao.setBounds(20, 30, 90, 14);

        lblMaxUsuario.setForeground(new java.awt.Color(0, 102, 102));
        lblMaxUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaxUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelInfoComunic.add(lblMaxUsuario);
        lblMaxUsuario.setBounds(120, 30, 50, 22);

        rtlNumeroConexoesAtivas.setText("Conexões Ativas:");
        painelInfoComunic.add(rtlNumeroConexoesAtivas);
        rtlNumeroConexoesAtivas.setBounds(20, 60, 97, 25);

        lblConexoesAtivas.setForeground(new java.awt.Color(0, 102, 102));
        lblConexoesAtivas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConexoesAtivas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelInfoComunic.add(lblConexoesAtivas);
        lblConexoesAtivas.setBounds(120, 60, 50, 25);

        painelCronometro.setBackground(java.awt.Color.white);

        lblHora.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHora.setText("00:");

        lblMinuto.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblMinuto.setForeground(new java.awt.Color(0, 102, 102));
        lblMinuto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinuto.setText("00:");

        lblSegundos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblSegundos.setForeground(new java.awt.Color(0, 102, 102));
        lblSegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSegundos.setText("00:");

        lblMilissegundos.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblMilissegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMilissegundos.setText("00");

        lblRepositorioCPU.setForeground(new java.awt.Color(0, 102, 102));

        lblCPU.setText("CPU:");

        lblRepositorioHD.setForeground(new java.awt.Color(0, 102, 102));

        lblHD.setText("HD:");

        javax.swing.GroupLayout painelCronometroLayout = new javax.swing.GroupLayout(painelCronometro);
        painelCronometro.setLayout(painelCronometroLayout);
        painelCronometroLayout.setHorizontalGroup(
            painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCronometroLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMilissegundos, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelCronometroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblHD, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRepositorioHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRepositorioCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelCronometroLayout.setVerticalGroup(
            painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCronometroLayout.createSequentialGroup()
                .addGroup(painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMilissegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblRepositorioCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblHD)
                    .addComponent(lblRepositorioHD, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelInfoComunic.add(painelCronometro);
        painelCronometro.setBounds(0, 92, 210, 102);

        lblStatusData.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblStatusData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblStatusData.setText("statusData");
        lblStatusData.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        painelInfoComunic.add(lblStatusData);
        lblStatusData.setBounds(60, 210, 100, 20);
        painelInfoComunic.add(lblImagemStatusData);
        lblImagemStatusData.setBounds(30, 200, 20, 30);
        painelInfoComunic.add(lblStatusImagemHora);
        lblStatusImagemHora.setBounds(30, 240, 20, 30);

        lblStatusHora.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblStatusHora.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblStatusHora.setText("statusHora");
        painelInfoComunic.add(lblStatusHora);
        lblStatusHora.setBounds(60, 250, 100, 20);

        painelSairSistema.setBackground(java.awt.Color.white);
        painelSairSistema.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        barraProgresso.setBackground(new java.awt.Color(255, 255, 255));
        painelSairSistema.add(barraProgresso, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 180, 20));

        lblVerificacao.setBackground(new java.awt.Color(204, 204, 204));
        lblVerificacao.setText("verificacao...");
        painelSairSistema.add(lblVerificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, 20));

        painelInfoComunic.add(painelSairSistema);
        painelSairSistema.setBounds(0, 270, 210, 60);

        PanelInformacoesAdicionais.add(painelInfoComunic, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 170, 210, 320));

        getContentPane().add(PanelInformacoesAdicionais, new org.netbeans.lib.awtextra.AbsoluteConstraints(852, 0, 240, 613));

        Paciente.setText("Cadastros");
        Paciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PacienteActionPerformed(evt);
            }
        });
        Paciente.add(jSeparator1);

        itemUsuario.setText("1º Usuários");
        itemUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemUsuarioActionPerformed(evt);
            }
        });
        Paciente.add(itemUsuario);

        itemCadEspecialidade.setText("2º Especialidades");
        itemCadEspecialidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCadEspecialidadeActionPerformed(evt);
            }
        });
        Paciente.add(itemCadEspecialidade);

        itemMedico.setText("3º Médicos");
        itemMedico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMedicoActionPerformed(evt);
            }
        });
        Paciente.add(itemMedico);

        itemPaciente.setText("4º Pacientes");
        itemPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPacienteActionPerformed(evt);
            }
        });
        Paciente.add(itemPaciente);

        menuFone.setText("Fones");

        itemMenuPaisRegiao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/smsMenu.png"))); // NOI18N
        itemMenuPaisRegiao.setText("País / Região");
        itemMenuPaisRegiao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuPaisRegiaoActionPerformed(evt);
            }
        });
        menuFone.add(itemMenuPaisRegiao);

        Paciente.add(menuFone);

        mnPrincipal.add(Paciente);

        mnConsultas.setText("Consultas");

        itemConultarMovimento.setText("Movimento da Agenda");
        itemConultarMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemConultarMovimentoActionPerformed(evt);
            }
        });
        mnConsultas.add(itemConultarMovimento);

        itemConsultasAtendimentoConsultorio.setText("Módulo 2: Atendimento Consultório");
        itemConsultasAtendimentoConsultorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemConsultasAtendimentoConsultorioActionPerformed(evt);
            }
        });
        mnConsultas.add(itemConsultasAtendimentoConsultorio);

        mnPrincipal.add(mnConsultas);

        mnRelarorios.setText("Relatórios");
        mnPrincipal.add(mnRelarorios);

        mnAjuda.setText("Ajuda");
        mnAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnAjudaActionPerformed(evt);
            }
        });

        itmnSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/menuHelp.png"))); // NOI18N
        itmnSobre.setText("Sobre");
        itmnSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnSobreActionPerformed(evt);
            }
        });
        mnAjuda.add(itmnSobre);

        mnPrincipal.add(mnAjuda);

        mnSair.setText("Sair");

        itmnSairSistema.setText("Sair do Sistema");
        itmnSairSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmnSairSistemaActionPerformed(evt);
            }
        });
        mnSair.add(itmnSairSistema);

        mnPrincipal.add(mnSair);

        setJMenuBar(mnPrincipal);

        setSize(new java.awt.Dimension(1111, 673));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void buscarInforConexao() {

        try {
            //se o metodo recebe for igual a zero não houve conexão com o banco 
            int recebeServidor = controle.contarMaxConexaoServidor();
            int recebeUsuario = controle.contarMaxConexaoUsuario();

            if (recebeUsuario == 0) {
                recebeUsuario = 50;
                lblMaxUsuario.setText(String.valueOf(recebeUsuario));
            }

            if (recebeUsuario != 0) {
                lblMaxUsuario.setText(String.valueOf(recebeUsuario));
            }

            int recebeAtivasMomento = controle.contarNumeroConexaoAbertasNoMomento();
            lblMaxServidor.setText(String.valueOf(recebeServidor));

            lblConexoesAtivas.setText(String.valueOf(recebeAtivasMomento));
        } catch (PersistenciaException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI: Sem Conectividade,\n"
                    + "Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO,\n"
                    + "BANCO DE DADOS HOSPEDADO Sobrecarregado de Conexões Ativas\n"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
        }
    }


    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        /**
         * Esse evento executa o codigo quando o formulário for aberto.
         *
         */
        Date data = new Date(); // criando um objeto do tipo Date para isso necessito instanciar a classe 
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);// capturar a data do sistema 
        lblDataSistema.setText(formatador.format(data));//adicionar a data capturada no meu label na área de trabalho 
        lblDataSistema.setForeground(new Color(0, 102, 102));
    }//GEN-LAST:event_formWindowActivated

    private void acaoSairSistema() {

        /*Evento ao ser clicado executa código*/
        int sair = JOptionPane.showConfirmDialog(null, "Deseja Sair do Sistema?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (sair == JOptionPane.YES_OPTION) {

            barraProgresso.setVisible(true);
            lblVerificacao.setVisible(true);

            new Thread() {

                public void run() {

                    for (int i = 0; i < 101; i++) {

                        try {

                            sleep(25);
                            barraProgresso.setValue(i);

                            if (barraProgresso.getValue() <= 5) {
                                lblVerificacao.setText("Inicializando barra de progresso");

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
                            System.out.println("aoSairDoSistema()" + e.getMessage());

                        }

                    }
                }
            }.start();// iniciando a Thread

        }

    }
    private void itmnSairSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnSairSistemaActionPerformed
        acaoSairSistema();
    }//GEN-LAST:event_itmnSairSistemaActionPerformed

    br.com.multclin.telas.TelaSobre formAjuda;

    public void acaoAbriTelaAjuda() {
        if (estaFechado(formAjuda)) {
            formAjuda = new TelaSobre();
            DeskTop.add(formAjuda).setLocation(8, 10);
            formAjuda.setTitle("Sobre o Sistema");
            formAjuda.setVisible(true);
        } else {
            formAjuda.toFront();
            formAjuda.setVisible(true);

        }

    }


    private void itmnSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmnSobreActionPerformed
        acaoAbriTelaAjuda();

    }//GEN-LAST:event_itmnSobreActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

        barraProgresso.setVisible(false);
        lblVerificacao.setVisible(false);
    }//GEN-LAST:event_formWindowOpened

    private void abrirBancoEmpresa() {

    }

    private void abrirEmpresa() {
    }

    private void abrirItensDoProtocolo() {
    }

    private void sairAplicacao() {

        /*Evento ao ser clicado executa código*/
        int sair = JOptionPane.showConfirmDialog(null, "Deseja Sair do Sistema?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (sair == JOptionPane.YES_OPTION) {

            barraProgresso.setVisible(true);
            lblVerificacao.setVisible(true);

            new Thread() {

                public void run() {

                    for (int i = 0; i < 101; i++) {

                        try {

                            sleep(25);
                            barraProgresso.setValue(i);

                            if (barraProgresso.getValue() <= 5) {
                                lblVerificacao.setText("Inicializando barra de progresso");

                                barraProgresso.setVisible(true);
                                lblVerificacao.setVisible(true);

                            } else if (barraProgresso.getValue() <= 15) {
                                lblVerificacao.setText("15% Descarregado");

                            } else if (barraProgresso.getValue() <= 25) {

                                lblVerificacao.setText("25% Descarregado");

                            } else if (barraProgresso.getValue() <= 35) {
                                lblVerificacao.setText("35% Descarregado");

                            } else if (barraProgresso.getValue() <= 45) {
                                lblVerificacao.setText("45% Descarregado");

                            } else if (barraProgresso.getValue() <= 55) {
                                lblVerificacao.setText("55% Descarregado");

                            } else if (barraProgresso.getValue() <= 65) {
                                lblVerificacao.setText("65% Descarregado");

                            } else if (barraProgresso.getValue() <= 75) {
                                lblVerificacao.setText("75% Descarregado");

                            } else if (barraProgresso.getValue() <= 85) {
                                lblVerificacao.setText("85% Descarregado");

                            } else if (barraProgresso.getValue() <= 95) {
                                lblVerificacao.setText("95% Descarregado");

                            } else {
                                lblVerificacao.setText("Encerrado com sucesso!");
                                System.exit(0);//sair do sistema
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("sairAplicacao()" + e.getMessage());

                        }

                    }
                }
            }.start();// iniciando a Thread

        }

    }

    private void mnAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnAjudaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnAjudaActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        acaoSairSistema();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        acaoAbriTelaAjuda();
    }//GEN-LAST:event_btnHelpActionPerformed

    private void PacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PacienteActionPerformed


    private void itemUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemUsuarioActionPerformed

        if (estaFechado(formUsuario)) {
            formUsuario = new TelaUsuario();
            DeskTop.add(formUsuario).setLocation(5, 3);
            formUsuario.setTitle("Usuario");
            formUsuario.setVisible(true);

        } else {
            formUsuario.toFront();
            formUsuario.setVisible(true);

        }

    }//GEN-LAST:event_itemUsuarioActionPerformed

    private void itemMenuPaisRegiaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuPaisRegiaoActionPerformed
        if (estaFechado(formPais)) {
            formPais = new SubTelaPaisRegiao();
            DeskTop.add(formPais).setLocation(320, 3);
            formPais.setTitle("Cadastro País/Região");
            formPais.setVisible(true);
        } else {
            formPais.toFront();
            formPais.setVisible(true);

        }

    }//GEN-LAST:event_itemMenuPaisRegiaoActionPerformed

    private void itemPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPacienteActionPerformed
        if (estaFechado(formPaciente)) {
            formPaciente = new TelaPaciente();
            DeskTop.add(formPaciente).setLocation(1, 1);
            formPaciente.setTitle("Cadastro de Pacientes");
            formPaciente.setVisible(true);
        } else {
            formPaciente.toFront();
            formPaciente.setVisible(true);

        }
    }//GEN-LAST:event_itemPacienteActionPerformed

    private void btnBarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarUsuarioActionPerformed

        if (estaFechado(formUsuario)) {
            formUsuario = new TelaUsuario();
            DeskTop.add(formUsuario).setLocation(5, 3);
            formUsuario.setTitle("Usuario");
            formUsuario.setVisible(true);

        } else {
            formUsuario.toFront();
            formUsuario.setVisible(true);

        }

    }//GEN-LAST:event_btnBarUsuarioActionPerformed


    private void btnBarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarPacienteActionPerformed
        if (estaFechado(formPaciente)) {
            formPaciente = new TelaPaciente();
            DeskTop.add(formPaciente).setLocation(1, 1);
            formPaciente.setTitle("Cadastro de Pacientes");
            formPaciente.setVisible(true);
        } else {
            formPaciente.toFront();
            formPaciente.setVisible(true);

        }
    }//GEN-LAST:event_btnBarPacienteActionPerformed

    private void itemMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMedicoActionPerformed

        if (estaFechado(formMedico)) {
            formMedico = new TelaMedico();
            DeskTop.add(formMedico).setLocation(1, 3);
            formMedico.setTitle("Cadastro de Médicos");
            formMedico.setVisible(true);
        } else {
            formMedico.toFront();
            formMedico.setVisible(true);

        }


    }//GEN-LAST:event_itemMedicoActionPerformed

    private void btnBarMedicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarMedicoActionPerformed

        if (estaFechado(formMedico)) {
            formMedico = new TelaMedico();
            DeskTop.add(formMedico).setLocation(1, 3);
            formMedico.setTitle("Cadastro de Médicos");
            formMedico.setVisible(true);
        } else {
            formMedico.toFront();
            formMedico.setVisible(true);

        }


    }//GEN-LAST:event_btnBarMedicoActionPerformed

    private void itemCadEspecialidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCadEspecialidadeActionPerformed

        if (estaFechado(formCadEspec)) {
            formCadEspec = new TelaCadastroEspecialidades();
            DeskTop.add(formCadEspec).setLocation(2, 3);
            formCadEspec.setTitle("Cadastro de Especialidades");
            formCadEspec.setVisible(true);
        } else {
            formCadEspec.toFront();
            formCadEspec.setVisible(true);

        }


    }//GEN-LAST:event_itemCadEspecialidadeActionPerformed

    private void btnBarAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarAgendamentoActionPerformed
        if (estaFechado(formAgenda)) {
            formAgenda = new TelaAgendamento();
            DeskTop.add(formAgenda).setLocation(2, 3);
            formAgenda.setTitle("Agendamento");
            formAgenda.setVisible(true);
        } else {
            formAgenda.toFront();
            formAgenda.setVisible(true);

        }

    }//GEN-LAST:event_btnBarAgendamentoActionPerformed

    private void btnMovimentoAgendamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovimentoAgendamentoActionPerformed
        if (estaFechado(formMovimentoAgenda)) {
            formMovimentoAgenda = new MovimentoDaAgenda();
            DeskTop.add(formMovimentoAgenda).setLocation(2, 3);
            formMovimentoAgenda.setTitle("Movimento da Agenda");
            formMovimentoAgenda.setVisible(true);
        } else {
            formMovimentoAgenda.toFront();
            formMovimentoAgenda.setVisible(true);

        }


    }//GEN-LAST:event_btnMovimentoAgendamentoActionPerformed

    private void relatorioTodosAgendadosSistema() {

        //**********************************************************************************
        //CURSO:Como Desenvolver Relatórios em Java com o JasperReports e o JasperStudio
        //apartir daqui começa de fato a parte pratica em relatório
        //https://www.udemy.com/course/relatorios-em-java-para-iniciantes-como-desenvolver/learn/lecture/18733998#overview
        //https://www.udemy.com/course/relatorios-em-java-para-iniciantes-como-desenvolver/learn/lecture/18734818#overview
        //***********************************************************************************************
        //criando um objeto de conexão do tipo ConexãoUtil
        ConexaoUtil conecta = new ConexaoUtil();

        try {

            //iremos setar o caminho de onde se encontra o arquivo xml por meio de um InputStream
            InputStream jrxmlStream = TelaPrincipal.class.getResourceAsStream("/ireport/agendamento_todos.xml");
            //compilamos o arquivo xml capiturado acima pelo objeto do Tipo InputStream
            //e fazemos sua conpilaçao por meio do JasperCompilerManger
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            //aqui preenchemos o relatório e passamos a conexao objeto conecta.getConnection()
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashedMap<>(), conecta.getConnection());
            //neste ponto já tenho meu relatório preenchido agora vamos salvar o arquivo JasperPrint 
            File file = new File("C:/ireport/agendamento_todos.jrprint");

            //agora iremos fazer uma verificação para ve se o arquivo existe ou não na pasta
            //caso não exista
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
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/agendamento_todos.pdf")));

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
                exporterPdf.setExporterOutput(new SimpleOutputStreamExporterOutput(new File("C:/ireport/agendamento_todos.pdf")));

                //agora iremos chamar o método que gera o relatório propriamente dito 
                exporterPdf.exportReport();
            }

            JasperViewer view = new JasperViewer(jasperPrint, false);
            view.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
        }

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
            e.printStackTrace();
        }

    }


    private void itemConultarMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConultarMovimentoActionPerformed

        if (estaFechado(formConsultarMove)) {
            formConsultarMove = new TelaCosultarMovimento();
            DeskTop.add(formConsultarMove).setLocation(2, 3);
            formConsultarMove.setTitle("Consultar Movimento");
            formConsultarMove.setVisible(true);
        } else {
            formConsultarMove.toFront();
            formConsultarMove.setVisible(true);
        }
    }//GEN-LAST:event_itemConultarMovimentoActionPerformed

    private void btnModulo2ConsultorioAtendimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModulo2ConsultorioAtendimentoActionPerformed

        try {

            if (estaFechado(modulo2ConsultorioAtendimento)) {
                modulo2ConsultorioAtendimento = new Modulo2AtendimentoConsultorio();
                DeskTop.add(modulo2ConsultorioAtendimento).setLocation(2, 3);
                modulo2ConsultorioAtendimento.setTitle("Atendimento no Consultório");
                modulo2ConsultorioAtendimento.setVisible(true);
            } else {
                modulo2ConsultorioAtendimento.toFront();
                modulo2ConsultorioAtendimento.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }//GEN-LAST:event_btnModulo2ConsultorioAtendimentoActionPerformed

    private void itemConsultasAtendimentoConsultorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemConsultasAtendimentoConsultorioActionPerformed

        try {

            if (estaFechado(modulo2ConsultorioAtendimento)) {
                modulo2ConsultorioAtendimento = new Modulo2AtendimentoConsultorio();
                DeskTop.add(modulo2ConsultorioAtendimento).setLocation(2, 3);
                modulo2ConsultorioAtendimento.setTitle("Atendimento no Consultório");
                modulo2ConsultorioAtendimento.setVisible(true);
            } else {
                modulo2ConsultorioAtendimento.toFront();
                modulo2ConsultorioAtendimento.setVisible(true);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }//GEN-LAST:event_itemConsultasAtendimentoConsultorioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    class hora implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Calendar now = Calendar.getInstance();
            lblStatusHora.setText(String.format("%1$tH:%1$tM:%1$tS", now));

            Font f = new Font("Tahoma", Font.BOLD, 14);

            lblStatusHora.setForeground(new Color(0, 102, 102));
            lblStatusData.setForeground(new Color(0, 102, 102));
            lblStatusHora.setFont(f);

        }

    }

//    //https://www.youtube.com/watch?v=juVANshV2Eo   
//    //criando uma classe dentro de outra para implementar as 
//    //ações dos meus botões da Barra de ferramentasa 
//    //Como é uma classe dentro da outra ela herda tudo que a
//    //classe mae tem ...
    private class Thehandler implements ActionListener {
//
//        //dentro dessa classe iremo implementar um método nativo do java 
//        //na verdade é a unica assinatura dessa classe que devemos implementar

        public void actionPerformed(ActionEvent evt) {
//            //BOTAO CADASTRO EMPRESA 

        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JToolBar BarraFerramentas;
    public static javax.swing.JDesktopPane DeskTop;
    public static javax.swing.JMenu Paciente;
    public static javax.swing.JPanel PanelInformacoesAdicionais;
    public static javax.swing.JProgressBar barraProgresso;
    public static javax.swing.JButton btnBarAgendamento;
    public static javax.swing.JButton btnBarMedico;
    public static javax.swing.JButton btnBarPaciente;
    public static javax.swing.JButton btnBarUsuario;
    public static javax.swing.JButton btnExit;
    public static javax.swing.JButton btnHelp;
    public static javax.swing.JButton btnModulo2ConsultorioAtendimento;
    public static javax.swing.JButton btnMovimentoAgendamento;
    public static javax.swing.JMenuItem itemCadEspecialidade;
    public static javax.swing.JMenuItem itemConsultasAtendimentoConsultorio;
    public static javax.swing.JMenuItem itemConultarMovimento;
    public static javax.swing.JMenuItem itemMedico;
    public static javax.swing.JMenuItem itemMenuPaisRegiao;
    public static javax.swing.JMenuItem itemPaciente;
    public static javax.swing.JMenuItem itemUsuario;
    public static javax.swing.JMenuItem itmnSairSistema;
    public static javax.swing.JMenuItem itmnSobre;
    public static javax.swing.JPopupMenu.Separator jSeparator1;
    public static javax.swing.JToolBar.Separator jSeparator10;
    public static javax.swing.JToolBar.Separator jSeparator11;
    public static javax.swing.JToolBar.Separator jSeparator12;
    public static javax.swing.JToolBar.Separator jSeparator13;
    public static javax.swing.JToolBar.Separator jSeparator14;
    public static javax.swing.JToolBar.Separator jSeparator15;
    public static javax.swing.JToolBar.Separator jSeparator2;
    public static javax.swing.JToolBar.Separator jSeparator3;
    public static javax.swing.JToolBar.Separator jSeparator4;
    public static javax.swing.JToolBar.Separator jSeparator6;
    public static javax.swing.JToolBar.Separator jSeparator7;
    public static javax.swing.JToolBar.Separator jSeparator8;
    public static javax.swing.JToolBar.Separator jSeparator9;
    public static javax.swing.JLabel lblCPU;
    public static javax.swing.JLabel lblConexoesAtivas;
    public static javax.swing.JLabel lblDataSistema;
    public static javax.swing.JLabel lblEspaco1;
    public static javax.swing.JLabel lblEspaco2;
    public static javax.swing.JLabel lblEspaco3;
    public static javax.swing.JLabel lblFundo;
    public static javax.swing.JLabel lblHD;
    public static javax.swing.JLabel lblHora;
    public static javax.swing.JLabel lblImagemData;
    public static javax.swing.JLabel lblImagemStatusData;
    public static javax.swing.JLabel lblImagemUser;
    public static javax.swing.JLabel lblMaxServidor;
    public static javax.swing.JLabel lblMaxUsuario;
    public static javax.swing.JLabel lblMilissegundos;
    public static javax.swing.JLabel lblMinuto;
    public static javax.swing.JLabel lblNomeCompletoUsuario;
    public static javax.swing.JLabel lblPerfil;
    public static javax.swing.JLabel lblRepositorioCPU;
    public static javax.swing.JLabel lblRepositorioHD;
    public static javax.swing.JLabel lblSaindoSistemaPorFaltaConexao;
    public static javax.swing.JLabel lblSegundos;
    public static javax.swing.JLabel lblStatusData;
    public static javax.swing.JLabel lblStatusHora;
    public static javax.swing.JLabel lblStatusImagemHora;
    public static javax.swing.JLabel lblUsuarioLogado;
    public static javax.swing.JLabel lblVerificacao;
    public static javax.swing.JMenu menuFone;
    public static javax.swing.JMenu mnAjuda;
    public static javax.swing.JMenu mnConsultas;
    public static javax.swing.JMenuBar mnPrincipal;
    public static javax.swing.JMenu mnRelarorios;
    public static javax.swing.JMenu mnSair;
    public static javax.swing.JPanel painelBarraStatus;
    public static javax.swing.JPanel painelCronometro;
    public static javax.swing.JPanel painelInfoComunic;
    public static javax.swing.JPanel painelPrincipal;
    public static javax.swing.JPanel painelSairSistema;
    public static javax.swing.JProgressBar progressBarraPesquisa;
    public static javax.swing.JLabel rtlMaximoConexaoServidor;
    public static javax.swing.JLabel rtlNumeroConexoesAtivas;
    public static javax.swing.JLabel rtlUsuarioMaximoConexao;
    // End of variables declaration//GEN-END:variables
}
