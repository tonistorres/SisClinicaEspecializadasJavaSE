package br.com.multclin.telas;

/**
 * trazer o módulo de conexão, que irá fazer a conexão entre o código java e o
 * Banco de Dados para Isso importar Aula:18
 */
import br.com.multclin.bo.LoginBO;
import br.com.multclin.bo.UsuarioBO;
import br.com.multclin.dao.LoginDAO;
import br.com.multclin.dao.UsuarioDAO;
import br.com.multclin.dto.LoginDTO;
import br.com.multclin.dto.ReconhecimentoDTO;
import br.com.multclin.dto.UsuarioDTO;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.SerialUtils;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.SplashScreenPadrao.lblInfoCarregamento;
import static br.com.multclin.telas.SplashScreenPadrao.txtAreaIPs;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.barraProgresso;
import static br.com.multclin.telas.TelaPrincipal.lblImagemUser;
import static br.com.multclin.telas.TelaPrincipal.lblStatusData;
import static br.com.multclin.telas.TelaPrincipal.lblStatusHora;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.io.File;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*Para evitar erros de conexão é sempre bom importar a Classe abaixo*/
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

//Esse método quando ele gera o formulário 
public class Login extends javax.swing.JFrame {

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

    /**
     * Dentro o public irei inicializar as chamadas
     */
    ConexaoUtil conecta = new ConexaoUtil();

    LoginBO loginBO = new LoginBO();
    LoginDTO loginDTO = new LoginDTO();
    LoginDAO loginDAO = new LoginDAO();

    UsuarioBO usuarioBO = new UsuarioBO();
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    Font f = new Font("Tahoma", Font.BOLD, 9);//label informativo 

    //----------------------------------------------------------------------------------------------------- //
    // 1º PASSO DENTRO DA CLASSE LOGIN CRIAR UM OBJETO DO TIPO SplashScreenPadrao                          //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 04                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555368?start=0 //
    //------------------------------------------------------------------------------------------------ //
    SplashScreenPadrao splash;

    //----------------------------------------------------------------------------------------------------- //
    // 2º PASSO CRIAR UM CONSTRUTOR PASSANDO COMO PARÂMETRO UM OBJETO SplashScreenPadrao spl               //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 04                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555368?start=0 //
    //------------------------------------------------------------------------------------------------ //
    Login(SplashScreenPadrao splash) throws ClassNotFoundException {

        /**
         * O nosso objeto splash recebe ele mesmo o this significa neste
         * formulário
         */
        this.splash = splash;

        /**
         * O código abaixo faz inicialização dos componentes do form
         */
        initComponents();
        aoCarregarForm();
        personalizacaoFrontEnd();

        //SEGURANÇA NUVEM 
        sisSegHospeda();
        //SEGURANÇA INTRINSECO       
        // sisSegHospedaSegIntrincica();
        /**
         * Setando valores ao carregar o formulário
         */
        txtLogin.setEditable(true);
        txtLogin.requestFocus();
        ConexaoUtil conecta = new ConexaoUtil();
        try {
            conecta.getInstance().getConnection();
            /**
             * Site Baixar icone: Contribuição do
             * Site:https://www.iconfinder.com
             */
            if (conecta != null) {
                //  lblStatusDaConexao.setText("Conectado");
                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/logoHannaMenor_sem_Slogam.jpeg")));
                lblStatusEspecificacao.setText("Banco Dado Conectado");
                lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/chaveok.png")));
                /**
                 * pedimos que o método setProgress faça verificações na minha
                 * barra de progresso
                 */
                setProgress(0, "Carregando Componentes do Sistema");//barra em 0% mostrar esse texto na label

                setProgress(20, "Verificando conexao com MySQL");//barra em 20% 
                setProgress(40, "Carregando os Módulos");//barra em 40%
                setProgress(60, "Carregando interfaces");//barra em 60%
                setProgress(100, "Caso não exista criando caminho para relatório Unidade C:");//barra em 100%

                //robo conecatado servidor google
                criandoCaminhoRelatoriosEmC();

                //criando pasta para colocar os agendamento em pdf
                criandoCaminhoRelatoriosCaminhoAgendaZap();

            } else {
                lblStatusEspecificacao.setText("Banco Dado Desconectado");
                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/LogoMultClinMaior.jpg")));
                lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/chaveDefeito.png")));
                txtSenha.setEnabled(false);
                txtLogin.setEnabled(false);
                btnLogin.setEnabled(false);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            erroViaEmail("Erro no construtor da Classe que \nFaz Teste de Conexão Com Banco\n Form:Login analisar Linhas:79-141 ", ex.getMessage());
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
            System.exit(0);
        }

    }

    private void criandoCaminhoRelatoriosCaminhoAgendaZap() {

        //Dica:https://respostas.guj.com.br/5728-criar-pasta-com-mkdir
        String nomePasta = "C:" + File.separator + "agendaZap";
        File diretorio = new File(nomePasta);

        if (diretorio.exists()) {
            System.out.println("diretorio ja existe:" + diretorio.getPath());
            // erroViaEmail("Diretório ireport Criado com sucesso em C:", "criandoCaminhoRelatorioEMC()");

        } else {
            System.out.println("diretorio inexistente criando pasta");
            diretorio.mkdir();
            System.out.println("Pasta Criada:" + diretorio.getPath());

            //linha robo aviso
            erroViaEmail("Criando uma Pasta na Unidade C:\n"
                    + " que conterá os relatórios do Agendamento\n"
                    + "exportado transformados em pdf", "criandoCaminhoRelatoriosCaminhoAgendaZap()\n"
                    + "" + diretorio.getPath());

        }

    }

    private void criandoCaminhoRelatoriosEmC() {
//Dica:https://respostas.guj.com.br/5728-criar-pasta-com-mkdir
        String nomePasta = "C:" + File.separator + "ireport";
        File diretorio = new File(nomePasta);

        if (diretorio.exists()) {
            System.out.println("diretorio ja existe:" + diretorio.getPath());
            // erroViaEmail("Diretório ireport Criado com sucesso em C:", "criandoCaminhoRelatorioEMC()");

        } else {
            System.out.println("diretorio inexistente criando pasta");
            diretorio.mkdir();
            System.out.println("Pasta Criada:" + diretorio.getPath());

            //linha robo aviso
            erroViaEmail("Criando uma Pasta na Unidade C:\n"
                    + " que conterá os relatórios a serem \n"
                    + "exportadose transformados em pdf", "criandoCaminhoRelatorioEMC()\n"
                    + "" + diretorio.getPath());

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

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            e.printStackTrace();
        }

    }

    private void aoCarregarForm() {
        txtLogin.requestFocus();
        lblLogin.setVisible(false);
        progressBarraPesquisa.setIndeterminate(true);

    }

    //----------------------------------------------------------------------------------------------------- //
    // 3º PASSO CRIAR O MÉTODO setProgress QUE IRÁ REALIZARA A INICIALIZAÇÃO DA BARRA DE PROGRESSO         //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 05                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555372?start=0 //
    //------------------------------------------------------------------------------------------------ //
    void setProgress(int percent, String informacao
    ) {

        /**
         * Captura os textos do setProgress e seta no label
         * (lblInfoCarregamento) na tela de SplashScreenPadrao
         */
        splash.getJLabel().setText(informacao);

        /**
         * Captura o valor em porcentagem do setProgress e seta o mesmo a Barra
         * de Progresso contida na Tela SplashScreenPadrao
         */
        splash.getJProgressBar().setValue(percent);

        //----------------------------------------------------------------------------------------------------- //
        // 5º PASSO CRIAR PROGRAMAR O Thread.sleep()                                            //
        //--------------------------------------------------------------------------------------------------- //
        //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 06                        //
        // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555374?start=0 //
        //------------------------------------------------------------------------------------------------ //
        try {

            Thread.sleep(1000);//o tempo que o meu ProgressBar vai demorar para percorrer toda barra
        } catch (InterruptedException e) {
            //no catch em caso de erro avisa para o usuário a causa do erro 
            JOptionPane.showMessageDialog(this, "Não foi possível carregar a inicialização\n" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        painelInformativo = new javax.swing.JPanel();
        painelColetadoInformaceos = new javax.swing.JPanel();
        txtRecuperaData = new javax.swing.JTextField();
        txtHDSerial = new javax.swing.JTextField();
        lblSerialHD = new javax.swing.JLabel();
        txtSerialCPU = new javax.swing.JTextField();
        lblSerialCPU = new javax.swing.JLabel();
        lblPlacaMae = new javax.swing.JLabel();
        txtPlacaMae = new javax.swing.JTextField();
        lblDataHora = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAInformacoesLocais = new javax.swing.JTextArea();
        jPanelStatus = new javax.swing.JPanel();
        lblStatusDaConexao = new javax.swing.JLabel();
        painelEfeitoTxtLogin = new javax.swing.JPanel();
        txtLogin = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        lblStatusEspecificacao = new javax.swing.JLabel();
        lblChaveEntrada = new javax.swing.JLabel();
        lblInfoUsuario = new javax.swing.JLabel();
        painelEfeitoTxtSenha = new javax.swing.JPanel();
        txtSenha = new javax.swing.JPasswordField();
        lblUsuario = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();
        painelNuvem = new javax.swing.JPanel();
        lblImgNuvemPos6 = new javax.swing.JLabel();
        lblImgNuvemPos1 = new javax.swing.JLabel();
        lblImgNuvemPos2 = new javax.swing.JLabel();
        lblImgNuvemPos3 = new javax.swing.JLabel();
        lblImgNuvemPos4 = new javax.swing.JLabel();
        lblImgNuvemPos5 = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        barraProgresso = new javax.swing.JProgressBar();
        lblVerificacao = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBackground(java.awt.Color.white);
        setForeground(java.awt.Color.white);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelInformativo.setBackground(java.awt.Color.white);

        painelColetadoInformaceos.setBackground(new java.awt.Color(255, 255, 255));
        painelColetadoInformaceos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Coletando Informações:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), java.awt.Color.white)); // NOI18N
        painelColetadoInformaceos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtRecuperaData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtRecuperaData.setForeground(java.awt.Color.white);
        txtRecuperaData.setEnabled(false);
        txtRecuperaData.setOpaque(false);
        txtRecuperaData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRecuperaDataActionPerformed(evt);
            }
        });
        painelColetadoInformaceos.add(txtRecuperaData, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 120, 22));

        txtHDSerial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtHDSerial.setForeground(java.awt.Color.white);
        txtHDSerial.setOpaque(false);
        painelColetadoInformaceos.add(txtHDSerial, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 120, 22));

        lblSerialHD.setForeground(new java.awt.Color(0, 102, 102));
        lblSerialHD.setText("Serial HD:");
        painelColetadoInformaceos.add(lblSerialHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        txtSerialCPU.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtSerialCPU.setForeground(java.awt.Color.white);
        txtSerialCPU.setOpaque(false);
        painelColetadoInformaceos.add(txtSerialCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 120, 22));

        lblSerialCPU.setForeground(new java.awt.Color(0, 102, 102));
        lblSerialCPU.setText("Serial CPU:");
        painelColetadoInformaceos.add(lblSerialCPU, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        lblPlacaMae.setForeground(new java.awt.Color(0, 102, 102));
        lblPlacaMae.setText("Placa Mãe:");
        painelColetadoInformaceos.add(lblPlacaMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        txtPlacaMae.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPlacaMae.setForeground(java.awt.Color.white);
        txtPlacaMae.setOpaque(false);
        painelColetadoInformaceos.add(txtPlacaMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 120, 22));

        lblDataHora.setForeground(new java.awt.Color(0, 102, 102));
        lblDataHora.setText("DtReg.:");
        painelColetadoInformaceos.add(lblDataHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 50, -1));

        jScrollPane1.setBackground(java.awt.Color.white);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informações Diversas:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 102, 102))); // NOI18N
        jScrollPane1.setForeground(new java.awt.Color(0, 102, 102));

        txtAInformacoesLocais.setColumns(20);
        txtAInformacoesLocais.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtAInformacoesLocais.setRows(5);
        txtAInformacoesLocais.setOpaque(false);
        jScrollPane1.setViewportView(txtAInformacoesLocais);

        javax.swing.GroupLayout painelInformativoLayout = new javax.swing.GroupLayout(painelInformativo);
        painelInformativo.setLayout(painelInformativoLayout);
        painelInformativoLayout.setHorizontalGroup(
            painelInformativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelColetadoInformaceos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        painelInformativoLayout.setVerticalGroup(
            painelInformativoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelInformativoLayout.createSequentialGroup()
                .addComponent(painelColetadoInformaceos, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(painelInformativo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 220, 350));

        jPanelStatus.setBackground(java.awt.Color.white);
        jPanelStatus.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblStatusDaConexao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelStatus.add(lblStatusDaConexao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 36, 230, 70));

        painelEfeitoTxtLogin.setBackground(new java.awt.Color(204, 0, 51));
        painelEfeitoTxtLogin.setForeground(java.awt.Color.white);

        txtLogin.setEditable(false);
        txtLogin.setBackground(java.awt.Color.white);
        txtLogin.setName("txtLogin"); // NOI18N
        txtLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLoginFocusLost(evt);
            }
        });
        txtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginActionPerformed(evt);
            }
        });
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoginKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout painelEfeitoTxtLoginLayout = new javax.swing.GroupLayout(painelEfeitoTxtLogin);
        painelEfeitoTxtLogin.setLayout(painelEfeitoTxtLoginLayout);
        painelEfeitoTxtLoginLayout.setHorizontalGroup(
            painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
            .addGroup(painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelEfeitoTxtLoginLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        painelEfeitoTxtLoginLayout.setVerticalGroup(
            painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
            .addGroup(painelEfeitoTxtLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelEfeitoTxtLoginLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanelStatus.add(painelEfeitoTxtLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 165, 110, 40));

        btnLogin.setBackground(new java.awt.Color(0, 102, 102));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLogin.setForeground(java.awt.Color.white);
        btnLogin.setText("Logar");
        btnLogin.setToolTipText("");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btnLoginStateChanged(evt);
            }
        });
        btnLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnLoginFocusGained(evt);
            }
        });
        btnLogin.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                btnLoginComponentHidden(evt);
            }
        });
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        btnLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLoginKeyPressed(evt);
            }
        });
        jPanelStatus.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 100, 40));

        lblStatusEspecificacao.setBackground(java.awt.Color.white);
        lblStatusEspecificacao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatusEspecificacao.setForeground(new java.awt.Color(0, 102, 102));
        lblStatusEspecificacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatusEspecificacao.setText("mensagemProgramada");
        jPanelStatus.add(lblStatusEspecificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 112, 230, 30));

        lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/chaveDefeito.png"))); // NOI18N
        jPanelStatus.add(lblChaveEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 39, 40));

        lblInfoUsuario.setBackground(new java.awt.Color(255, 204, 204));
        lblInfoUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanelStatus.add(lblInfoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 40));

        painelEfeitoTxtSenha.setBackground(new java.awt.Color(204, 0, 51));
        painelEfeitoTxtSenha.setPreferredSize(new java.awt.Dimension(210, 70));
        painelEfeitoTxtSenha.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSenhaFocusLost(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });
        painelEfeitoTxtSenha.add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 5, 100, 30));

        jPanelStatus.add(painelEfeitoTxtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 215, 110, 40));

        lblUsuario.setBackground(new java.awt.Color(51, 255, 0));
        lblUsuario.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanelStatus.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 50, 50));

        lblSenha.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanelStatus.add(lblSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 50, 50));

        painelNuvem.setBackground(java.awt.Color.white);
        painelNuvem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nuvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelNuvem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblImgNuvemPos6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, 50));

        lblImgNuvemPos1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 70, 50));

        lblImgNuvemPos2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 70, 50));

        lblImgNuvemPos3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 70, 50));

        lblImgNuvemPos4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 70, 50));

        lblImgNuvemPos5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        painelNuvem.add(lblImgNuvemPos5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 70, 50));

        jPanelStatus.add(painelNuvem, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 90, 350));

        lblLogin.setBackground(new java.awt.Color(204, 0, 51));
        lblLogin.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(0, 102, 102));
        lblLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogin.setText("0%");
        jPanelStatus.add(lblLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 190, 20));
        jPanelStatus.add(progressBarraPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 170, -1));

        getContentPane().add(jPanelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, 350));

        barraProgresso.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(barraProgresso, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 540, 20));
        getContentPane().add(lblVerificacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 270, 20));

        setSize(new java.awt.Dimension(556, 417));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void desabilitarCampos() {
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
    }

    private void desabilitarTodosBotoes() {
        btnLogin.setEnabled(false);
    }

    private void personalizacaoFrontEnd() {

        jPanelStatus.setBackground(Color.WHITE);

        // painelColetadoInformaceos.setBackground(new Color(204,0,51));
        painelColetadoInformaceos.setBackground(new Color(0, 102, 102));

        lblDataHora.setForeground(Color.WHITE);
        lblSerialCPU.setForeground(Color.WHITE);
        lblSerialHD.setForeground(Color.WHITE);
        lblPlacaMae.setForeground(Color.WHITE);
        //botão entrar 
        btnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/login.png")));
        lblUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/userLogin.png")));
        lblSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/password.png")));

        personalizarTxtCapturaInfoMaquina();
    }

    private void personalizarTxtCapturaInfoMaquina() {
        Font fonteTxtInformacaoMaquina = new Font("Tahoma", Font.ITALIC, 10);

        txtRecuperaData.setFont(fonteTxtInformacaoMaquina);
        txtHDSerial.setFont(fonteTxtInformacaoMaquina);
        txtSerialCPU.setFont(fonteTxtInformacaoMaquina);
        txtPlacaMae.setFont(fonteTxtInformacaoMaquina);
        Font fonteTxtInformacaoMaquinaTexArea = new Font("Tahoma", Font.ITALIC, 9);
        txtAInformacoesLocais.setFont(fonteTxtInformacaoMaquinaTexArea);

    }

    private void personalizarMaquinaReconhecida() {
        Font f = new Font("Tahoma", Font.BOLD, 15);
        lblInfoUsuario.setText("Maquina RECONHECIDA");
        lblInfoUsuario.setForeground(new Color(0, 102, 102));
        lblInfoUsuario.setFont(f);
        txtLogin.setEnabled(true);
        txtSenha.setEnabled(true);
    }

    private void personalizarMaquinaBloqueada() {
        Font f = new Font("Tahoma", Font.BOLD, 15);
        lblInfoUsuario.setText("Maquina BLOQUEADA");
        //lblInfoUsuario.setForeground(Color.WHITE);
        lblInfoUsuario.setForeground(new Color(9, 81, 107));
        lblInfoUsuario.setFont(f);
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);

    }


    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
//         
    }

    // Esse método quando inicializa o formulário     
    /**
     * Classe de inicialização Formulario Login Criado nos Estudos de Hugo Hugo
     * Vasconcelos
     * Contribuição:https://www.dropbox.com/sh/gbp9emvqjb4q88n/AADofh1m8UgXA9RWufgRgMwta?dl=0
     */
    public Login() throws ClassNotFoundException {

        initComponents();// inicialização dos componentes 

        ConexaoUtil conecta = new ConexaoUtil();

        try {
            conecta.getInstance().getConnection();
            /**
             * Site Baixar icone: Contribuição do
             * Site:https://www.iconfinder.com
             */
            if (conecta != null) {
                // lblStatusDaConexao.setText("Conectado");
                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/Banco_MySQL.png")));
                lblStatusEspecificacao.setText("Banco Dado Conectado");
                lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/chaveok.png")));
                /**
                 * pedimos que o método setProgress faça verificações na minha
                 * barra de progresso
                 */
                setProgress(0, "Carregando Componentes do Sistema");//barra em 0% mostrar esse texto na label
                setProgress(20, "Verificando conexao com MySQL");//barra em 20% 
                setProgress(40, "Carregando os Módulos");//barra em 40%
                setProgress(60, "Carregando interfaces");//barra em 60%
                setProgress(100, "Bem vindo ao sistema");//barra em 100%
            } else {
                lblStatusEspecificacao.setText("Banco Dado Desconectado");
                lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/BancoDesconectado.png")));
                lblChaveEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/chaveDefeito.png")));
                txtSenha.setEnabled(false);
                txtLogin.setEnabled(false);
                btnLogin.setEnabled(false);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Tratamento Erro Camada GUI: " + ex.getMessage());
        }

    }//GEN-LAST:event_btnLoginActionPerformed

//METODO PARA LOGAR 
    public void login() {

        /**
         * Setando os valores digitados pelo usuario
         */
        //
        usuarioDTO.setLoginDto(txtLogin.getText());
        usuarioDTO.setSenhaDto(new String(txtSenha.getPassword()));

        try {

            UsuarioDTO resultado = usuarioBO.logarBO(usuarioDTO);

            //          String resultadoPerfil = resultado.getPerfilDto();
//            String resultadoPolido = MetodoStaticosUtil.removerAcentosCaixAlta(resultadoPerfil);
            if (resultado.getPerfilDto().equalsIgnoreCase("ANALISTA") && resultado.getEstadoDto().equalsIgnoreCase("ATIVO")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblPerfil.setFont(f);
                TelaPrincipal.lblPerfil.setForeground(new Color(0, 102, 102));
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());
                TelaPrincipal.lblRepositorioCPU.setForeground(new Color(0, 102, 102));

                //*********************
                //MENU CONSULTAS    //
                //********************
                TelaPrincipal.mnConsultas.setVisible(true);
                TelaPrincipal.mnConsultas.setEnabled(true);

                TelaPrincipal.itemConultarMovimento.setVisible(true);
                TelaPrincipal.itemConultarMovimento.setEnabled(true);

                TelaPrincipal.itemConsultasAtendimentoConsultorio.setVisible(true);
                TelaPrincipal.itemConsultasAtendimentoConsultorio.setEnabled(true);

//                TelaPrincipal.itemConultarProcedimentosPorMedico.setVisible(true);
//                TelaPrincipal.itemConultarProcedimentosPorMedico.setEnabled(true);

                //********************************************* 
                //BARRA DE FERRAMENTAS 
                //**********************************************
                TelaPrincipal.btnBarUsuario.setVisible(true);
                TelaPrincipal.btnBarUsuario.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnBarPaciente.setVisible(true);
                TelaPrincipal.btnBarPaciente.setEnabled(true);
                //**********************************************
                TelaPrincipal.btnBarMedico.setVisible(true);
                TelaPrincipal.btnBarMedico.setEnabled(true);
                //**********************************************
                TelaPrincipal.btnBarAgendamento.setVisible(true);
                TelaPrincipal.btnBarAgendamento.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnMovimentoAgendamento.setVisible(true);
                TelaPrincipal.btnMovimentoAgendamento.setEnabled(true);
                //***************************************************
                TelaPrincipal.btnModulo2ConsultorioAtendimento.setVisible(true);
                TelaPrincipal.btnModulo2ConsultorioAtendimento.setEnabled(true);
                //*************************************************************
                TelaPrincipal.btnHelp.setVisible(true);
                TelaPrincipal.btnHelp.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnExit.setVisible(true);
                TelaPrincipal.btnExit.setEnabled(true);

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/user_2_1.png")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));
                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/user_1.png")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                }

                this.dispose();

            }

            //ADMINISTRADOR (A)
            if (resultado.getPerfilDto().equalsIgnoreCase("ADMIN") && resultado.getEstadoDto().equalsIgnoreCase("ATIVO")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblPerfil.setFont(f);
                TelaPrincipal.lblPerfil.setForeground(new Color(0, 102, 102));
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());
                TelaPrincipal.lblRepositorioCPU.setForeground(new Color(0, 102, 102));

                //MENU CADASTRO 
                TelaPrincipal.Paciente.setEnabled(true);
                TelaPrincipal.Paciente.setVisible(true);

                //SUB MENU DE CADASTRO / USUARIOS
                TelaPrincipal.itemUsuario.setEnabled(true);
                TelaPrincipal.itemUsuario.setVisible(true);

                //SUB MENU DE CADASTRO / FONES 
                TelaPrincipal.menuFone.setEnabled(false);
                TelaPrincipal.menuFone.setVisible(false);

                //*********************
                //MENU CONSULTAS    //
                //********************
                TelaPrincipal.mnConsultas.setVisible(true);
                TelaPrincipal.mnConsultas.setEnabled(true);

                TelaPrincipal.itemConultarMovimento.setVisible(true);
                TelaPrincipal.itemConultarMovimento.setEnabled(true);

                TelaPrincipal.itemConsultasAtendimentoConsultorio.setVisible(true);
                TelaPrincipal.itemConsultasAtendimentoConsultorio.setEnabled(true);

//                TelaPrincipal.itemConultarProcedimentosPorMedico.setVisible(true);
//                TelaPrincipal.itemConultarProcedimentosPorMedico.setEnabled(true);

                //********************************************* 
                //BARRA DE FERRAMENTAS 
                //**********************************************
                TelaPrincipal.btnBarUsuario.setVisible(true);
                TelaPrincipal.btnBarUsuario.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnBarPaciente.setVisible(true);
                TelaPrincipal.btnBarPaciente.setEnabled(true);
                //**********************************************
                TelaPrincipal.btnBarMedico.setVisible(true);
                TelaPrincipal.btnBarMedico.setEnabled(true);
                //**********************************************
                TelaPrincipal.btnBarAgendamento.setVisible(true);
                TelaPrincipal.btnBarAgendamento.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnMovimentoAgendamento.setVisible(true);
                TelaPrincipal.btnMovimentoAgendamento.setEnabled(true);
                //***************************************************
                TelaPrincipal.btnModulo2ConsultorioAtendimento.setVisible(true);
                TelaPrincipal.btnModulo2ConsultorioAtendimento.setEnabled(true);
                //*************************************************************
                TelaPrincipal.btnHelp.setVisible(true);
                TelaPrincipal.btnHelp.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnExit.setVisible(true);
                TelaPrincipal.btnExit.setEnabled(true);

                if (resultado.getLoginDto().equals("KELLY")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/kelly.jpg")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                } else { //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                    if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                        lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/user_2_1.png")));
                        TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));
                    }

                    if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                        lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/user_1.png")));
                        TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                    }
                }
                this.dispose();

            }

            //MEDICO 
            if (resultado.getPerfilDto().equalsIgnoreCase("MEDICO") && resultado.getEstadoDto().equalsIgnoreCase("ATIVO")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblPerfil.setForeground(new Color(0, 102, 102));
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());
                TelaPrincipal.lblRepositorioCPU.setForeground(new Color(0, 102, 102));

                //MENU CADASTRO 
                TelaPrincipal.Paciente.setEnabled(false);
                TelaPrincipal.Paciente.setVisible(false);

                //SUB MENU DE CADASTRO / USUARIOS
                TelaPrincipal.itemUsuario.setEnabled(false);
                TelaPrincipal.itemUsuario.setVisible(false);

                //SUB MENU DE CADASTRO / FONES 
                TelaPrincipal.menuFone.setEnabled(false);
                TelaPrincipal.menuFone.setVisible(false);

                //sub item paciente 
                TelaPrincipal.itemPaciente.setVisible(false);
                TelaPrincipal.itemPaciente.setEnabled(false);

                //sub item medico 
                TelaPrincipal.itemMedico.setVisible(false);
                TelaPrincipal.itemMedico.setEnabled(false);

                //sub cadastro de especialidades 
                TelaPrincipal.itemCadEspecialidade.setVisible(false);
                TelaPrincipal.itemCadEspecialidade.setEnabled(false);

                //********************************************* 
                //BARRA DE FERRAMENTAS 
                //**********************************************
                TelaPrincipal.btnBarUsuario.setVisible(true);
                TelaPrincipal.btnBarUsuario.setEnabled(false);
                //***********************************************
                TelaPrincipal.btnBarPaciente.setVisible(true);
                TelaPrincipal.btnBarPaciente.setEnabled(false);
                //**********************************************
                TelaPrincipal.btnBarMedico.setVisible(true);
                TelaPrincipal.btnBarMedico.setEnabled(false);
                //**********************************************
                TelaPrincipal.btnBarAgendamento.setVisible(true);
                TelaPrincipal.btnBarAgendamento.setEnabled(false);
                //***********************************************
                TelaPrincipal.btnMovimentoAgendamento.setVisible(true);
                TelaPrincipal.btnMovimentoAgendamento.setEnabled(false);
                //***************************************************
                TelaPrincipal.btnModulo2ConsultorioAtendimento.setVisible(true);
                TelaPrincipal.btnModulo2ConsultorioAtendimento.setEnabled(true);
                //*************************************************************
                TelaPrincipal.btnHelp.setVisible(true);
                TelaPrincipal.btnHelp.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnExit.setVisible(true);
                TelaPrincipal.btnExit.setEnabled(true);

                //*********************************
                //MENU CONSULTAS E RELATORIOS    //
                //********************************
                TelaPrincipal.mnConsultas.setVisible(true);
                TelaPrincipal.mnConsultas.setEnabled(true);
                TelaPrincipal.itemConsultasAtendimentoConsultorio.setVisible(true);
                TelaPrincipal.itemConsultasAtendimentoConsultorio.setEnabled(true);
                TelaPrincipal.itemConultarMovimento.setVisible(false);
                TelaPrincipal.itemConultarMovimento.setEnabled(false);

//                TelaPrincipal.itemConultarProcedimentosPorMedico.setVisible(false);
//                TelaPrincipal.itemConultarProcedimentosPorMedico.setEnabled(false);

                TelaPrincipal.mnRelarorios.setVisible(false);
                TelaPrincipal.mnRelarorios.setEnabled(false);

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/medica.png")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));
                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/medico.png")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                }

                this.dispose();

            }

            //USER1
            if (resultado.getPerfilDto().equalsIgnoreCase("USER1") && resultado.getEstadoDto().equalsIgnoreCase("ATIVO")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblPerfil.setFont(f);
                TelaPrincipal.lblPerfil.setForeground(new Color(0, 102, 102));
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());
                TelaPrincipal.lblRepositorioCPU.setForeground(new Color(0, 102, 102));

                //personalizando paineis de usuarios 
                //MENU CADASTRO 
                TelaPrincipal.Paciente.setEnabled(true);
                TelaPrincipal.Paciente.setVisible(true);

                //SUB MENU DE CADASTRO / USUARIOS
                TelaPrincipal.itemUsuario.setEnabled(false);
                TelaPrincipal.itemUsuario.setVisible(false);

                //SUB MENU DE CADASTRO / FONES 
                TelaPrincipal.menuFone.setEnabled(false);
                TelaPrincipal.menuFone.setVisible(false);

                //sub item paciente 
                TelaPrincipal.itemPaciente.setVisible(true);
                TelaPrincipal.itemPaciente.setEnabled(true);

                //sub item medico 
                TelaPrincipal.itemMedico.setVisible(false);
                TelaPrincipal.itemMedico.setEnabled(false);

                //sub cadastro de especialidades 
                TelaPrincipal.itemCadEspecialidade.setVisible(false);
                TelaPrincipal.itemCadEspecialidade.setEnabled(false);

                //********************************************* 
                //BARRA DE FERRAMENTAS 
                //**********************************************
                TelaPrincipal.btnBarUsuario.setVisible(true);
                TelaPrincipal.btnBarUsuario.setEnabled(false);
                //***********************************************
                TelaPrincipal.btnBarPaciente.setVisible(true);
                TelaPrincipal.btnBarPaciente.setEnabled(true);
                //**********************************************
                TelaPrincipal.btnBarMedico.setVisible(true);
                TelaPrincipal.btnBarMedico.setEnabled(false);
                //**********************************************
                TelaPrincipal.btnBarAgendamento.setVisible(true);
                TelaPrincipal.btnBarAgendamento.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnMovimentoAgendamento.setVisible(true);
                TelaPrincipal.btnMovimentoAgendamento.setEnabled(true);
                //***************************************************
                TelaPrincipal.btnModulo2ConsultorioAtendimento.setVisible(true);
                TelaPrincipal.btnModulo2ConsultorioAtendimento.setEnabled(false);
                //*************************************************************
                TelaPrincipal.btnHelp.setVisible(true);
                TelaPrincipal.btnHelp.setEnabled(true);
                //***********************************************
                TelaPrincipal.btnExit.setVisible(true);
                TelaPrincipal.btnExit.setEnabled(true);

                //*********************************
                //MENU CONSULTAS E RELATORIOS    //
                //********************************
                TelaPrincipal.mnConsultas.setVisible(true);
                TelaPrincipal.mnConsultas.setEnabled(true);
                TelaPrincipal.itemConsultasAtendimentoConsultorio.setVisible(true);
                TelaPrincipal.itemConsultasAtendimentoConsultorio.setEnabled(true);
                TelaPrincipal.itemConultarMovimento.setVisible(false);
                TelaPrincipal.itemConultarMovimento.setEnabled(false);

//                TelaPrincipal.itemConultarProcedimentosPorMedico.setVisible(false);
//                TelaPrincipal.itemConultarProcedimentosPorMedico.setEnabled(false);

                TelaPrincipal.mnRelarorios.setVisible(false);
                TelaPrincipal.mnRelarorios.setEnabled(false);

                //*********************************
                //MENU CONSULTAS E RELATORIOS    //
                //********************************
                TelaPrincipal.mnConsultas.setVisible(false);
                TelaPrincipal.mnConsultas.setEnabled(false);
                TelaPrincipal.mnRelarorios.setVisible(false);
                TelaPrincipal.mnRelarorios.setEnabled(false);

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getLoginDto().equals("SUELMA")) {

                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/suelma.jpg")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                } else if (resultado.getLoginDto().equals("JARDENE")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/jardene.png")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                } else {

                    if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                        lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/user_2_1.png")));
                        TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));
                    }

                    if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                        lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/user_1.png")));
                        TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                    }

                }

                this.dispose();

            }

            //USER2
            if (resultado.getPerfilDto().equalsIgnoreCase("USER2") && resultado.getEstadoDto().equalsIgnoreCase("ATIVO")) {

                TelaPrincipal tela = new TelaPrincipal();
                tela.setVisible(true);

                this.dispose();

                TelaPrincipal.lblPerfil.setText(resultado.getPerfilDto());
                TelaPrincipal.lblPerfil.setFont(f);
                TelaPrincipal.lblPerfil.setForeground(new Color(0, 102, 102));
                TelaPrincipal.lblUsuarioLogado.setText(resultado.getLoginDto().toUpperCase());
                TelaPrincipal.lblNomeCompletoUsuario.setText(resultado.getUsuarioDto());
                TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                //INFORMAÇÕES DE SEGURANÇA A NIVEL DE MAQUINA 
                TelaPrincipal.lblRepositorioHD.setText(txtHDSerial.getText());
                TelaPrincipal.lblRepositorioCPU.setText(txtSerialCPU.getText());
                TelaPrincipal.lblRepositorioCPU.setForeground(new Color(0, 102, 102));

                //MENU CADASTRO 
                TelaPrincipal.Paciente.setEnabled(false);
                TelaPrincipal.Paciente.setVisible(false);

                //SUB MENU DE CADASTRO / USUARIOS
                TelaPrincipal.itemUsuario.setEnabled(false);
                TelaPrincipal.itemUsuario.setVisible(false);

                //SUB MENU DE CADASTRO / FONES 
                TelaPrincipal.menuFone.setEnabled(false);
                TelaPrincipal.menuFone.setVisible(false);

                //sub item paciente 
                TelaPrincipal.itemPaciente.setVisible(false);
                TelaPrincipal.itemPaciente.setEnabled(false);

                //sub item medico 
                TelaPrincipal.itemMedico.setVisible(false);
                TelaPrincipal.itemMedico.setEnabled(false);

                //sub cadastro de especialidades 
                TelaPrincipal.itemCadEspecialidade.setVisible(false);
                TelaPrincipal.itemCadEspecialidade.setEnabled(false);

                //********************************************* 
                //BARRA DE FERRAMENTAS 
                //**********************************************
                TelaPrincipal.btnBarUsuario.setVisible(false);
                TelaPrincipal.btnBarUsuario.setEnabled(false);
                //***********************************************
                TelaPrincipal.btnBarPaciente.setVisible(false);
                TelaPrincipal.btnBarPaciente.setEnabled(false);
                //**********************************************
                TelaPrincipal.btnBarMedico.setVisible(false);
                TelaPrincipal.btnBarMedico.setEnabled(false);

                //*********************************
                //MENU CONSULTAS E RELATORIOS    //
                //********************************
                TelaPrincipal.mnConsultas.setVisible(false);
                TelaPrincipal.mnConsultas.setEnabled(false);
                TelaPrincipal.mnRelarorios.setVisible(false);
                TelaPrincipal.mnRelarorios.setEnabled(false);

//                TelaPrincipal.itemConultarProcedimentosPorMedico.setVisible(false);
//                TelaPrincipal.itemConultarProcedimentosPorMedico.setEnabled(false);

                //SETAR IMAGEM DE USUARIO SE FEMININO OU MASCULINO 
                if (resultado.getSexoDto().equalsIgnoreCase("FEMININO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/user_2_1.png")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));
                }

                if (resultado.getSexoDto().equalsIgnoreCase("MASCULINO")) {
                    lblImagemUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/user_1.png")));
                    TelaPrincipal.lblNomeCompletoUsuario.setForeground(new Color(0, 102, 102));

                }

                this.dispose();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());

            if (ex.getMessage().equals("Login Obrigatorio!")) {

                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                txtLogin.requestFocus();
                txtLogin.setBackground(Color.RED);

            }

            if (ex.getMessage().equals("Senha Obrigatorio!")) {

                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);

                txtSenha.requestFocus();
                txtSenha.setBackground(Color.RED);
            }

            if (ex.getMessage().equals("Usuario ou Senha Incorretos")) {
                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);

                txtLogin.requestFocus();
                txtLogin.setText("");
                txtSenha.setText("");
                txtLogin.setBackground(Color.red);
                txtSenha.setBackground(Color.red);
            }
        }

    }

    private void desabilitarCamposInfo() {

        txtPlacaMae.setEnabled(false);
        txtRecuperaData.setEnabled(false);
        txtHDSerial.setEnabled(false);
        txtSerialCPU.setEnabled(false);
        txtAInformacoesLocais.setEnabled(false);
    }

    //********************   
    //SEGURANÇA SISTEMA //
    //********************  
    private void sisSegHospeda() {

        //captura o estado da conexão 
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        //Filtro 1: Verifica se Há conexão com a Internet 
        if (recebeConexao) {

            try {

                //*****************************************************************************
                //BUSCANDO INFORMAÇÕES NO SISTEMA OPERACIONAL QUE EXECUTOU A APLICAÇÃO       //
                //OBSERVAÇÃO.:A aplicação esta buscando Informações do Sistema Operacional  //
                //que executou a mesma e não Na Base de Dados do SisHospeda                //
                //***************************************************************************
                String recuperaPlaMae = SerialUtils.getMotherboardSerialLinux();
                String recuperaCPU = SerialUtils.getCPUSerialLinux();
                
                //********************************************************************************
                //DEPOIS DE COLETADOS OS DADOS DO SISTEMA OPERACIONAL QUE EXECUTROU A APLICAÇÃO//
                //O sistema irá fazer a comparação dos dados capturados com os sistemas que   //
                //possui permissão na base de dados                                          //
                //****************************************************************************
                ReconhecimentoDTO recebeComparacaoDto = loginDAO.comparaSereiMotherboard(recuperaPlaMae);

                if (recebeComparacaoDto.getSerial_placa_maeDto().trim().equals(recuperaPlaMae.trim()) && recebeComparacaoDto.getLiberado_bloqueadoDto().trim().equalsIgnoreCase("LIBERADO")) {
                    //**************************************************************************************//
                    //SE A MAQUINA FOR RECONHECIDA ELE IRÁ SETAR AS INFORMAÇÕES NO PAINEL DE RECONHECIMENTO//
                    //***************************************************************************************
                    txtPlacaMae.setText(recebeComparacaoDto.getSerial_placa_maeDto());
                    txtRecuperaData.setText(recebeComparacaoDto.getDt_hora_conectouDto());
                    //txtHDSerial.setText(recebeComparacaoDto.getSerialHdDto());
                    txtSerialCPU.setText(recebeComparacaoDto.getSerialCPUDto());
                    txtAInformacoesLocais.setText(recebeComparacaoDto.getInformacoes_diversasDto());
                    //*******************************************//
                    //O MÉTDOO ABAIXO IRÁ DESABILITAR OS CAMPOS //
                    //*****************************************//
                    desabilitarCamposInfo();
                    //*******************************************************************//
                    //O MÉTODO ABAIXO IRÁ HABILITAR OS CAMPOS OS CAMPOS DE LOGIN E SENHA //
                    //******************************************************************//
                    personalizarMaquinaReconhecida();

                    //*********************************************************************************************************
                    //                          COMO CAPTURAR VARIOS IPS DE UMA MAQUINA                                     //
                    //https://forum.scriptbrasil.com.br/topic/105098-como-descobrir-o-ip-da-esta%C3%A7%C3%A3o-do-cliente/
                    //******************************************************************************************************
//                    String localhost = InetAddress.getLocalHost().getHostName();
//                    InetAddress[] addr = InetAddress.getAllByName(localhost);
//                    for (int i = 0; i < addr.length; i++) {
//                        System.out.println(addr[i]);
//                        txtAreaIPs.append(addr[i].toString() + "\n");
//
//                      //  emailCapturandoListaIPSRECONHECIDA(" " + addr[i]);
//                    }

                    //**********************************************************************************************************
                    //O MÉTODO ABAIXO É ROBOTIZADO PARA ENVIAR UM EMAIL ME DANDO INFORMAÇÕES DO SISTEMA QUE EXECUTOU APLICAÇAO//
                    //*********************************************************************************************************
//                    emailMaquinaReconhecida(recebeComparacaoDto.getId_reconhecimentoDto(), recebeComparacaoDto.getUsuario_responsavel_cadastro(), "Clinica Hanna kelly", recuperaPlaMae, recuperaCPU, recuperaHD, recebeComparacaoDto.getDt_hora_conectouDto());

                } else {

                    //MAQUINA NÃO AUTORIZADA
                    txtPlacaMae.setText(recebeComparacaoDto.getSerial_placa_maeDto());
                    txtRecuperaData.setText(recebeComparacaoDto.getDt_hora_conectouDto());
                    //txtHDSerial.setText(recebeComparacaoDto.getSerialHdDto());
                    txtSerialCPU.setText(recebeComparacaoDto.getSerialCPUDto());
                    txtAInformacoesLocais.setText(recebeComparacaoDto.getInformacoes_diversasDto());

                    desabilitarCamposInfo();
                    personalizarMaquinaBloqueada();
                    btnLogin.setEnabled(false);
                    txtLogin.setEnabled(false);
                    txtSenha.setEnabled(false);

                }
            } catch (Exception e) {

                //*********************************************************************************************************
                //CASO A MAQUINA NÃO TENHA AUTORIZAÇÃO -  COMO CAPTURAR VARIOS IPS DE UMA MAQUINA                    //
                //https://forum.scriptbrasil.com.br/topic/105098-como-descobrir-o-ip-da-esta%C3%A7%C3%A3o-do-cliente/
                //******************************************************************************************************
                String localhost;
                try {
                    localhost = InetAddress.getLocalHost().getHostName();
                    InetAddress[] addr = InetAddress.getAllByName(localhost);
                    for (int i = 0; i < addr.length; i++) {
                        System.out.println(addr[i]);
//                        emailCapturandoListaIpsNAOReconhecido(" " + addr[i]);
                    }

                } catch (UnknownHostException ex) {
                    ex.printStackTrace();
                }
                //criar um código mais educado para sair da aplicação neste caso 
                // barra de tempo 
                System.exit(0);
            }

        } else {

            //CASO NÃO TENHA CONEXÃO COM A INTERNET
            JOptionPane.showMessageDialog(null, "" + "\n Sem Conexão com a INTERNET:\n"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            System.exit(0);
        }

    }

    private void emailCapturandoListaIPSRECONHECIDA(String ips) {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        String enviarParaSupervisor = "sisjavarobomaquinareconhecida@gmail.com";

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
            email.setSubject("LISTA IPS" + MetodoStaticosUtil.capturarDataEConverterString());
            //configurando o corpo deo email (Mensagem)
            email.setMsg(
                    "--------------------------------------------------------------------------\n"
                    + "LISTA DE IPS DA MAQUNIDA RECONHECIDA\n"
                    + "------------------------------------------------------------------------\n"
                    + ips
                    + "-------------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(enviarParaSupervisor);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equals("Sending the email to the following server failed : smtp.gmail.com:465")) {

                JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                        + "Erro Método de Envio Email:" + e.getMessage()
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                System.exit(0);
            }

            e.printStackTrace();

        }

    }

    private void emailMaquinaReconhecida(int id_reconecimentoSISHOSPEDA, String UsuarioSISHOSPEDA, String nomeDeclarativoDono, String placaMae, String CPU, String HD, String dtHoraAcesso) {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        String enviarParaSupervisor = "sisjavarobomaquinareconhecida@gmail.com";

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
            email.setSubject("RECONHECIDA" + MetodoStaticosUtil.capturarDataEConverterString());
            //configurando o corpo deo email (Mensagem)
            email.setMsg(
                    "--------------------------------------------------------------------------\n"
                    + "DADOS BUSCADOS NA BASE DE DOS SISHOSPEDA\n"
                    + "------------------------------------------------------------------------\n"
                    + "ID MAQUINA CADASTRADA SISHOSPEDA: " + id_reconecimentoSISHOSPEDA + " " + "Sistema Desenvolvido para:" + nomeDeclarativoDono + "\n"
                    + "Está rodando na maquina: " + UsuarioSISHOSPEDA + "\n"
                    + "-------------------------------------------------------------------------\n"
                    + "    DADOS COLETADOS DA MÁQUINA EM TEMPO DE EXECUÇÃO\n"
                    + "-------------------------------------------------------------------------\n"
                    + "PLACA MAE:" + placaMae + " " + " CPU: " + " " + CPU + " " + " HD: " + HD + "\n"
                    + "-------------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(enviarParaSupervisor);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equals("Sending the email to the following server failed : smtp.gmail.com:465")) {

                JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                        + "Erro Método de Envio Email:" + e.getMessage()
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                System.exit(0);
            }

            e.printStackTrace();

        }

    }

    private void emailCapturandoListaIpsNAOReconhecido(String ips) {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        String enviarParaSupervisor = "sisjavarobomaquinadesconhecida@gmail.com";

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
            email.setSubject("LISTA IPS" + MetodoStaticosUtil.capturarDataEConverterString());
            //configurando o corpo deo email (Mensagem)
            email.setMsg(
                    "--------------------------------------------------------------------------\n"
                    + "LISTA DE IPS DESCONHECIDO\n"
                    + "------------------------------------------------------------------------\n"
                    + ips + "\n"
                    + "-------------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(enviarParaSupervisor);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equals("Sending the email to the following server failed : smtp.gmail.com:465")) {

                JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                        + "Erro Método de Envio Email:" + e.getMessage()
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                System.exit(0);
            }

            e.printStackTrace();

        }

    }

    private void emailMaquinaNAOReconhecida(int id_reconecimentoSISHOSPEDA, String UsuarioSISHOSPEDA, String nomeDeclarativoDono, String placaMae, String CPU, String HD, String dtHoraAcesso) {

        String meuEmail = "sisjavaclinicarobo@gmail.com";
        String minhaSenha = "aa2111791020";

        String enviarParaSupervisor = "sisjavarobomaquinadesconhecida@gmail.com";

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
            email.setSubject("DESCONHECIDA" + MetodoStaticosUtil.capturarDataEConverterString());
            //configurando o corpo deo email (Mensagem)
            email.setMsg(
                    "--------------------------------------------------------------------------\n"
                    + "DADOS BUSCADOS NA BASE DE DOS SISHOSPEDA\n"
                    + "------------------------------------------------------------------------\n"
                    + "ID MAQUINA CADASTRADA SISHOSPEDA: " + id_reconecimentoSISHOSPEDA + " " + "Sistema Desenvolvido para:" + nomeDeclarativoDono + "\n"
                    + "Está rodando na maquina: " + UsuarioSISHOSPEDA + "\n"
                    + "-------------------------------------------------------------------------\n"
                    + "    DADOS COLETADOS DA MÁQUINA EM TEMPO DE EXECUÇÃO\n"
                    + "-------------------------------------------------------------------------\n"
                    + "PLACA MAE:" + placaMae + " " + " CPU: " + " " + CPU + " " + " HD: " + HD + "\n"
                    + "-------------------------------------------------------------------------\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(enviarParaSupervisor);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            if (e.getMessage().equals("Sending the email to the following server failed : smtp.gmail.com:465")) {

                JOptionPane.showMessageDialog(null, "" + "\n Camada DAO:\n"
                        + "Erro Método de Envio Email:" + e.getMessage()
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                System.exit(0);
            }

            e.printStackTrace();

        }

    }


    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {

            txtSenha.requestFocus();

        }

    }//GEN-LAST:event_txtLoginKeyPressed

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            btnLogin.requestFocus();

        }

    }//GEN-LAST:event_txtSenhaKeyPressed

    private void btnLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLoginKeyPressed

    }//GEN-LAST:event_btnLoginKeyPressed

    private void txtRecuperaDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRecuperaDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRecuperaDataActionPerformed


    private void btnLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnLoginFocusGained

        //coloca o cursor em modo de espera 
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        //captura o estado da conexão 
        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();

        //seta os dois campos Login e Senha inicialmente como nulo +o Botão Logar
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
        btnLogin.setEnabled(false);

        //caso o retorno da conexão seja verdadeiro ou true 
        if (recebeConexao == true) {

            // NESTE PONTO IREMOS FAZER A VERIFICAÇÃO DOS CAMPOS DE LOGIN
            //TRATAMENTO A NIVEL DE FRONT-END (CAMADA SWING) CASCA
            //Se Login e Senha diferentes de nulo então entrar nessa condição 
            // e executar o método login();
            if (!txtLogin.getText().equals("") && !txtSenha.getText().equals("")) {

                //método login()
                login();
            }

            //se Login nulo então irá executar os procedimentos 
            //inerentes a condicional logo abaixo 
            if (txtLogin.getText().equals("")) {

                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Digite o Usuario");
                txtLogin.requestFocus();
                txtLogin.setText("Usuario?");

                //coloca o cursor em modo normal
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            }

            //Se senha nulo então irá executar os procedimentos inerentes 
            //a condicional abaixo relacionada 
            if (txtSenha.getText().equals("")) {

                txtLogin.setEnabled(true);
                txtSenha.setEnabled(true);
                btnLogin.setEnabled(true);
                JOptionPane.showMessageDialog(null, "Digite a SENHA");
                txtSenha.requestFocus();
                txtSenha.setText("");

                //coloca o cursor em modo normal
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            }

        } else {

            Font f = new Font("Tahoma", Font.ITALIC, 9);
            lblLogin.setVisible(true);
            lblLogin.setText("Sem Conexao Internet");
            lblLogin.setForeground(Color.RED);
            lblStatusDaConexao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/BancoDesconectado.png")));
            lblStatusEspecificacao.setText("Falha na Comunicação");
            txtLogin.requestFocus();

            //coloca o cursor em modo normal
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }

    }//GEN-LAST:event_btnLoginFocusGained

    private void txtLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusLost

    }//GEN-LAST:event_txtLoginFocusLost

    private void txtSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusGained

        efeitoCampoTxtSenhaReceberFoco();
        txtLogin.setToolTipText("Digite a SENHA do Usuário");


    }//GEN-LAST:event_txtSenhaFocusGained

    private void txtSenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusLost
        estado = false;
        txtSenha.setBackground(Color.WHITE);

    }//GEN-LAST:event_txtSenhaFocusLost

    private void efeitoCampoTxtSenhaReceberFoco() {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (segundos % 2 == 0) {
                                painelEfeitoTxtSenha.setVisible(true);
                                painelEfeitoTxtSenha.setBackground(new Color(0, 102, 102));
                                txtSenha.setBackground(Color.WHITE);
                                txtSenha.setForeground(Color.BLACK);

                            }

                            if (segundos % 2 != 0) {
                                painelEfeitoTxtSenha.setVisible(true);
                                painelEfeitoTxtSenha.setBackground(new Color(204, 0, 51));
                                txtSenha.setBackground(Color.WHITE);
                                txtSenha.setForeground(Color.BLACK);

                            }

                            if (segundos > 54 && segundos <= 60) {
                                new Thread() {

                                    public void run() {

                                        for (int i = segundos; i < 61; i++) {

                                            try {

                                                sleep(4000);
                                                barraProgresso.setValue(i);

                                                if (barraProgresso.getValue() == 55) {
                                                    lblVerificacao.setText("1% Inicializando barra de progresso");

                                                    // painelInferior.setVisible(true);
                                                    // barraProgresso.setVisible(true);
                                                    lblVerificacao.setVisible(true);

                                                } else if (barraProgresso.getValue() == 56) {
                                                    lblVerificacao.setText("45% Descarregado");

                                                } else if (barraProgresso.getValue() == 57) {

                                                    lblVerificacao.setText("65% Descarregado");

                                                } else if (barraProgresso.getValue() == 59) {

                                                    lblVerificacao.setText("85% Descarregado");
                                                } else {
                                                    lblVerificacao.setText("Encerrado com sucesso!");
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

    //METODO CAMPO TXTLOGIN 
    private void efeitoCampoTxtLoinReceberFoco() {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            if (segundos % 2 == 0) {

                                painelEfeitoTxtLogin.setVisible(true);
                                painelEfeitoTxtLogin.setBackground(new Color(0, 102, 102));
//                               txtLogin.setBackground(Color.WHITE);
//                               txtLogin.setForeground(Color.BLACK);

                            }

                            if (segundos % 2 != 0) {
                                painelEfeitoTxtLogin.setVisible(true);
                                painelEfeitoTxtLogin.setBackground(new Color(204, 0, 51));
//                                txtLogin.setBackground(Color.WHITE);
//                                txtLogin.setForeground(Color.BLACK);

                                lblImgNuvemPos1.setVisible(false);
                            }

                            //se segundos dividido por 2 igual a zero e menor que 10
                            //ou seja, os numeros pares menor igual a 10
                            //se par 
                            if (segundos % 2 == 0 && segundos <= 10) {
                                //                            System.out.println("PAR" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(false);
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            //se impar e menor que 10
                            if (segundos % 2 != 0 && segundos <= 10) {
                                //                          System.out.println("IMPAR" + segundos);
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 10 && segundos <= 20)) {
                                //                        System.out.println("PAR:" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 10 && segundos <= 20)) {
                                //                      System.out.println("IMPAR:" + segundos);
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(false);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);

                            }

                            if (segundos % 2 == 0 && (segundos > 20 && segundos <= 30)) {
            //                    System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 20 && segundos <= 30)) {
              //                  System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 30 && segundos <= 40)) {
                //                System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 30 && segundos <= 40)) {
                  //              System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 40 && segundos <= 50)) {
                    //            System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 40 && segundos <= 50)) {
                      //          System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(false);

                            }

                            if (segundos % 2 == 0 && (segundos > 50 && segundos <= 54)) {
                        //        System.out.println("PAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);
                                //boneco posicao 3 fica visivel estatico PAR 
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);
                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos6.setVisible(true);

                            }

                            if (segundos % 2 != 0 && (segundos > 50 && segundos <= 54)) {
                          //      System.out.println("IMPAR:" + segundos);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos1.setVisible(true);
                                //boneco posicao 2 fica visivel estatico PAR
                                lblImgNuvemPos2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos2.setVisible(true);

                                //boneco posicao 3 fica visivel estatico PAR
                                lblImgNuvemPos3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos3.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos4.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos5.setVisible(true);

                                //boneco posicao 1 fica visivel estatico PAR
                                lblImgNuvemPos6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemHostGator.png")));
                                lblImgNuvemPos6.setVisible(false);

                            }

                            if (segundos > 54 && segundos <= 60) {
                                new Thread() {

                                    public void run() {

                                        for (int i = segundos; i < 61; i++) {

                                            try {

                                                sleep(4000);
                                                barraProgresso.setValue(i);

                                                if (barraProgresso.getValue() == 55) {
                                                    lblVerificacao.setText("1% Inicializando barra de progresso");

                                                    // painelInferior.setVisible(true);
                                                    // barraProgresso.setVisible(true);
                                                    lblVerificacao.setVisible(true);

                                                } else if (barraProgresso.getValue() == 56) {
                                                    lblVerificacao.setText("45% Descarregado");

                                                } else if (barraProgresso.getValue() == 57) {

                                                    lblVerificacao.setText("65% Descarregado");

                                                } else if (barraProgresso.getValue() == 59) {

                                                    lblVerificacao.setText("85% Descarregado");
                                                } else {
                                                    lblVerificacao.setText("Encerrado com sucesso!");
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


    private void txtLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusGained
        txtLogin.setToolTipText("Digite NOME do Usuário");
        efeitoCampoTxtLoinReceberFoco();

    }//GEN-LAST:event_txtLoginFocusGained

    private void txtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoginActionPerformed

    private void btnLoginStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btnLoginStateChanged


    }//GEN-LAST:event_btnLoginStateChanged

    private void btnLoginComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_btnLoginComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoginComponentHidden

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
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                try {
                    new Login().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    ex.getMessage();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JProgressBar barraProgresso;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChaveEntrada;
    private javax.swing.JLabel lblDataHora;
    private javax.swing.JLabel lblImgNuvemPos1;
    private javax.swing.JLabel lblImgNuvemPos2;
    private javax.swing.JLabel lblImgNuvemPos3;
    private javax.swing.JLabel lblImgNuvemPos4;
    private javax.swing.JLabel lblImgNuvemPos5;
    private javax.swing.JLabel lblImgNuvemPos6;
    private javax.swing.JLabel lblInfoUsuario;
    public static javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblPlacaMae;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblSerialCPU;
    private javax.swing.JLabel lblSerialHD;
    private javax.swing.JLabel lblStatusDaConexao;
    private javax.swing.JLabel lblStatusEspecificacao;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVerificacao;
    private javax.swing.JPanel painelColetadoInformaceos;
    private javax.swing.JPanel painelEfeitoTxtLogin;
    private javax.swing.JPanel painelEfeitoTxtSenha;
    private javax.swing.JPanel painelInformativo;
    private javax.swing.JPanel painelNuvem;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTextArea txtAInformacoesLocais;
    private javax.swing.JTextField txtHDSerial;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtPlacaMae;
    private javax.swing.JTextField txtRecuperaData;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtSerialCPU;
    // End of variables declaration//GEN-END:variables
}
