package br.com.multclin.telas;

import br.com.multclin.dao.InfoControleConexaoDAO;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class SplashScreenPadrao extends javax.swing.JFrame {

    //----------------------------------------------------------------------------------------------------- //
    // 6º PASSO CRIAR UM OBJETO DO TIPO SplashScreenPadrao TAMBÉM NA TELA DE SplashScreenPadrão              //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 08                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555380?start=0 //
    //------------------------------------------------------------------------------------------------ //
    SplashScreenPadrao splash = this;

    //InfoControleConexaoDAO controle = new InfoControleConexaoDAO();
    public SplashScreenPadrao() {
        initComponents();
        personalizacaoFrontEnd();
        aoCarregar();
        startThread();

        //Tive que desabilitar esse método pois o uso do mesmo desta forma acarreta em sobre carga
        //no numero de conexões lançadas no banco causando incosistencia no sistema:
        //Lentidão , e derrubando o mesmo quando excede esse limite 
        //    metodoStartTesteConexao();
    }

    
    private void aoCarregar(){
    txtAreaIPs.setEnabled(true);
    txtAreaIPs.setEditable(false);
    txtAreaIPs.setForeground(new Color(230,230,250));
    
    }
    
    //personalização do incones da Tela Via Código Java 
    private void personalizacaoFrontEnd() {
        lblLogoMultClin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/ireport/imagens/logoHannaMenor_sem_Slogam.jpeg")));
        lblHannaBorder1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/ireport/imagens/border1.jpeg")));
    }

    //----------------------------------------------------------------------------------------------------- //
    // 7º PASSO MÉTODO PARA INICIAR O PROGRESSO COM SPLASH                                                 //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 08                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555380?start=0 //
    //------------------------------------------------------------------------------------------------ //
    void startThread() {
        /**
         * Função de Inicialização da Barra de Progresso
         */
        Thread hi = new Thread(new Runnable() {
            @Override
            //----------------------------------------------------------------------------------------------------- //
            // 8º PASSO CHAMAR O FORMULARIO DE LOGIN                                                               //
            //--------------------------------------------------------------------------------------------------- //
            //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 09                        //
            // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555384?start=0 //
            //------------------------------------------------------------------------------------------------ //
            public void run() {

                //criado um construturo na tela de Login que recebe a tela de splash()
                Login login;
                try {
                    login = new Login(splash);
                    login.setLocationRelativeTo(null);
                    login.setVisible(true);
                    dispose();

                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                            + ex.getMessage()
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                }

            }
        });

        hi.start();//inicie minha barra de progresso 
    }

    //----------------------------------------------------------------------------------------------------- //
    // 4º PASSO CRIAR O MÉTODO  getJProgressBar() E getJLabel()                                            //
    //--------------------------------------------------------------------------------------------------- //
    //Contribuição Hugo Vasconcelos Curso Ponto de Venda com Java e MySQL AULA 06                        //
    // Udemy:https://www.udemy.com/ponto-de-vendas-com-java-e-mysql/learn/v4/t/lecture/10555374?start=0 //
    //------------------------------------------------------------------------------------------------ //
    public JProgressBar getJProgressBar() {
        return Progresso;
    }

    /**
     * segue a mesma lógica do getProgressBar só que criamos o getJLabel e
     * retornamos nosso label que está associado a nossa brra de progresso
     */
    public JLabel getJLabel() {
        return lblInfoCarregamento;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelProgress = new javax.swing.JPanel();
        lblInfoCarregamento = new javax.swing.JLabel();
        Progresso = new javax.swing.JProgressBar();
        lblSlogan = new javax.swing.JLabel();
        painelInfoLogoEmpresa = new javax.swing.JPanel();
        lblLogoMultClin = new javax.swing.JLabel();
        lblHannaBorder1 = new javax.swing.JLabel();
        painelDeveloper = new javax.swing.JPanel();
        repositDeveloper = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        repositEmail = new javax.swing.JLabel();
        lblInstagram = new javax.swing.JLabel();
        repositInstagram = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaIPs = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("splash_padrao");
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelProgress.setBackground(new java.awt.Color(255, 255, 255));
        painelProgress.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblInfoCarregamento.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblInfoCarregamento.setForeground(new java.awt.Color(0, 102, 102));
        lblInfoCarregamento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfoCarregamento.setText("Carregando Sistema  Version 1.0 ");
        lblInfoCarregamento.setName("lblInfoCarregamento"); // NOI18N
        lblInfoCarregamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInfoCarregamentoMouseClicked(evt);
            }
        });
        painelProgress.add(lblInfoCarregamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 16, 320, -1));

        Progresso.setBackground(new java.awt.Color(255, 255, 255));
        Progresso.setName("Progresso"); // NOI18N
        Progresso.setOpaque(true);
        painelProgress.add(Progresso, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 33, 290, 27));

        lblSlogan.setFont(new java.awt.Font("Monotype Corsiva", 1, 20)); // NOI18N
        lblSlogan.setForeground(new java.awt.Color(204, 0, 51));
        lblSlogan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlogan.setText("Quem se ama se cuida!");
        painelProgress.add(lblSlogan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 300, 30));

        getContentPane().add(painelProgress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 320, 90));

        painelInfoLogoEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        painelInfoLogoEmpresa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoMultClin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblLogoMultClin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        painelInfoLogoEmpresa.add(lblLogoMultClin, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, -10, 270, 90));

        lblHannaBorder1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        painelInfoLogoEmpresa.add(lblHannaBorder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 70, 60));

        getContentPane().add(painelInfoLogoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, -1));

        painelDeveloper.setBackground(java.awt.Color.white);
        painelDeveloper.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "I9Conhecimento:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelDeveloper.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        repositDeveloper.setForeground(new java.awt.Color(0, 102, 102));
        repositDeveloper.setText("Tonis A. Torres Ferreira");
        painelDeveloper.add(repositDeveloper, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 130, -1));

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNome.setForeground(new java.awt.Color(0, 102, 102));
        lblNome.setText("Developer:");
        painelDeveloper.add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 80, -1));

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(0, 102, 102));
        lblEmail.setText("Email:");
        painelDeveloper.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 60, -1));

        repositEmail.setForeground(new java.awt.Color(0, 102, 102));
        repositEmail.setText("sisvenda2011@gmail.com");
        painelDeveloper.add(repositEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 150, -1));

        lblInstagram.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblInstagram.setForeground(new java.awt.Color(0, 102, 102));
        lblInstagram.setText("Instagram:");
        painelDeveloper.add(lblInstagram, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        repositInstagram.setForeground(new java.awt.Color(0, 102, 102));
        repositInstagram.setText("i9conhecimento");
        painelDeveloper.add(repositInstagram, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 120, -1));

        getContentPane().add(painelDeveloper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 320, 110));

        txtAreaIPs.setColumns(20);
        txtAreaIPs.setRows(5);
        txtAreaIPs.setBorder(null);
        jScrollPane1.setViewportView(txtAreaIPs);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 320, -1));

        setSize(new java.awt.Dimension(325, 397));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    String metodo = "Método";

    private void lblInfoCarregamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInfoCarregamentoMouseClicked
        Login form;
        try {
            form = new Login();
            form.setLocationRelativeTo(form);
            form.toFront();
            form.setVisible(true);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }//GEN-LAST:event_lblInfoCarregamentoMouseClicked

    public static void main(String args[]) {

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
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreenPadrao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /**
         * Contribuição:Hugo
         * Vasconcelos:https://www.udemy.com/curso-de-java-design/learn/v4/t/lecture/10503330?start=0
         * Dessa forma abrirá centralizada em qualquer monitor com qualquer
         * configuração
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SplashScreenPadrao tela = new SplashScreenPadrao();//Aqui utilizamos orientação objeto: criamo um objetio do tipo tela
                tela.setLocationRelativeTo(null);//setamos uma atributo a esse objeto do tipo setLocationRelativeTo 
                tela.setVisible(true);//Em seguida outro abritubo para torná-la visivel 

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar Progresso;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblHannaBorder1;
    public static javax.swing.JLabel lblInfoCarregamento;
    private javax.swing.JLabel lblInstagram;
    private javax.swing.JLabel lblLogoMultClin;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblSlogan;
    private javax.swing.JPanel painelDeveloper;
    private javax.swing.JPanel painelInfoLogoEmpresa;
    private javax.swing.JPanel painelProgress;
    private javax.swing.JLabel repositDeveloper;
    private javax.swing.JLabel repositEmail;
    private javax.swing.JLabel repositInstagram;
    public static javax.swing.JTextArea txtAreaIPs;
    // End of variables declaration//GEN-END:variables
}
