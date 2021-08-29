package br.com.multclin.telas;

import br.com.multclin.bo.PacienteBO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.PacienteDAO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.PacienteDTO;
import br.com.multclin.jbdc.ConexaoUtil;
import static br.com.multclin.telas.Login.estado;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.barraProgresso;
import static br.com.multclin.telas.TelaPrincipal.lblVerificacao;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author DaTorres
 */
public class TelaInfoPaciente extends javax.swing.JInternalFrame {

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

    Font f = new Font("Tahoma", Font.BOLD, 14);//label informativo 

    /**
     * Código Mestre Chimura
     */
    private static TelaInfoPaciente instance = null;

    public static TelaInfoPaciente getInstance() {

        if (instance == null) {

            instance = new TelaInfoPaciente();

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

    public TelaInfoPaciente() {

        initComponents();
        progressBar();
        //  aoCarregar();
    }

    private void iniciandoRelogio() {

        estado = true;

        Thread t = new Thread() {
            public void run() {

                for (;;) {
                    if (estado == true) {

                        try {
                            sleep(1);

                            System.out.println("Segundos:" + segundos);

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

                            lblMilissegundos.setText(" : " + milissegundos);
                            lblSegundos.setText(" : " + segundos);
                            lblMinuto.setText(" : " + minutos);
                            lblHora.setText(" : " + horas);

                            milissegundos++;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        break;
                    }
                }

            }

        };
        t.start();

    }

    private void progressBar() {
        //***************
        //PROGRESS BAR //
        //***********************************************************************
        //Contribuição Canal Yoube:https://www.youtube.com/watch?v=co2cITXfXqY//
        //**********************************************************************

        new Thread() {

            public void run() {

                iniciandoRelogio();
                for (int i = 0; i < 101; i++) {

                    try {
//29
                        sleep(50);

                        // System.out.println("vaor de i " + i);
                        barraProgresso.setValue(i);

                        if (barraProgresso.getValue() == 10) {
                            lblVerificacao.setText("10% Inicializando barra de progresso");
                            lblVerificacao.setVisible(true);

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLabelSystem = new JLabel();
                            jLabelSystem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/systems.png")));
                            painelSimulador.add(jLabelSystem);
                            jLabelSystem.setBounds(5, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 20) {
                            lblVerificacao.setText("20% Verificando se há conexão com a núvem ");

                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLteste = new JLabel();
                            jLteste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/hostgator.png")));
                            painelSimulador.add(jLteste);
                            jLteste.setBounds(55, 15, 50, 50);
                            fazendoTesteConexao();

                        } else if (barraProgresso.getValue() == 30) {

                            lblVerificacao.setText("30% Conexão OK estável...");
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLTesteConexaoPositivo = new JLabel();
                            jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/conexao.png")));
                            painelSimulador.add(jLTesteConexaoPositivo);
                            jLTesteConexaoPositivo.setBounds(105, 15, 50, 50);
                            aoCarregar();

                        } else if (barraProgresso.getValue() == 40) {
                            lblVerificacao.setText("40% Registros Capturados...");
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLRegistroCapturados = new JLabel();
                            jLRegistroCapturados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/registrosCap.png")));
                            painelSimulador.add(jLRegistroCapturados);
                            jLRegistroCapturados.setBounds(165, 15, 50, 50);

                        } else if (barraProgresso.getValue() == 50) {
                            lblVerificacao.setText("50% Núvem estável...");
                            //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                            JLabel jLNuvemEstavel = new JLabel();
                            jLNuvemEstavel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/nuvemEstavel.png")));
                            painelSimulador.add(jLNuvemEstavel);
                            jLNuvemEstavel.setBounds(215, 15, 50, 50);
                            
                        } else if (barraProgresso.getValue() == 60) {

                            lblVerificacao.setText("60% Sistema estável...");
                        } else if (barraProgresso.getValue() == 70) {

                            lblVerificacao.setText("70% Drivers estáveis...");
                        } else if (barraProgresso.getValue() == 80) {

                            lblVerificacao.setText("80% Gerenciador de banco estável...");
                        } else if (barraProgresso.getValue() == 90) {

                            lblVerificacao.setText("90% Inicializando procedimentos finais...");
                        } else if (barraProgresso.getValue() == 100) {

                            lblVerificacao.setText("100% fim da Pesquisa.");
                            //se quiser desligar o relogio 
                            estado = false;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Desculpe! Erro no moduto de Descarregamento\n" + e.getMessage());

                    }

                }
            }
        }.start();// iniciando a Thread
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

    private void aoCarregar() {

        int codigo = Integer.parseInt(lblRepositIdPaciente.getText());

        ArrayList<PacienteDTO> list;
        try {

            list = (ArrayList<PacienteDTO>) pacienteDAO.buscarPorID(codigo);
            if (list.size() > 0) {

                for (int i = 0; i < list.size(); i++) {

                    lblRepositIdPaciente.setText(list.get(i).getIdDto().toString());
                    lblRepositPaciente.setText(list.get(i).getNomeDto());
                    lblRepositDtNascimento.setText(list.get(i).getDataNascDto());
                    lblRepositSexo.setText(list.get(i).getSexoDto());
                    lblRepositEstadoCivil.setText(list.get(i).getEstadoCivilDto());
                    lblRepositConjuge.setText(list.get(i).getConjugeDto());
                    lblRepositMae.setText(list.get(i).getMaeDto());
                    lblRepositPai.setText(list.get(i).getPaiDto());
                    lblRepositCelular.setText(list.get(i).getCelularPrefDto());
                    lblRepositUF.setText(list.get(i).getUfDto());
                    lblRepositCidade.setText(list.get(i).getCidadeDto());
                    lblRepositBairro.setText(list.get(i).getBairroDto());
                    lblRepositRua.setText(list.get(i).getRuaDto());
                    lblRepositCPFPaciente.setText(list.get(i).getCpfDto());

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
        lblRepositIdPaciente = new javax.swing.JLabel();
        lblRepositPaciente = new javax.swing.JLabel();
        lblNomePaciente = new javax.swing.JLabel();
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
        painelSimulador = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        painelCronometro = new javax.swing.JPanel();
        lblHora = new javax.swing.JLabel();
        lblMinuto = new javax.swing.JLabel();
        lblSegundos = new javax.swing.JLabel();
        lblMilissegundos = new javax.swing.JLabel();
        lblVerificacao = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();

        setClosable(true);
        setTitle("Paciente ");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
        painelCabecalho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelPaciente.setBackground(java.awt.Color.white);
        painelPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Paciente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositIdPaciente.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositIdPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositIdPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 20));

        lblRepositPaciente.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 220, 20));

        lblNomePaciente.setForeground(new java.awt.Color(0, 102, 102));
        lblNomePaciente.setText(" ID     Paciente:  CPF.:");
        painelPaciente.add(lblNomePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 120, -1));

        lblDtNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lblDtNascimento.setText("Data Nascimento:");
        painelPaciente.add(lblDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, -1));

        lblRepositDtNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositDtNascimento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositDtNascimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 90, 20));

        lblSexo.setForeground(new java.awt.Color(0, 102, 102));
        lblSexo.setText("Sexo:");
        painelPaciente.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));

        lblRepositSexo.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositSexo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 100, 20));

        lblEstadoCivil.setForeground(new java.awt.Color(0, 102, 102));
        lblEstadoCivil.setText("Estado Civil");
        painelPaciente.add(lblEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 20));

        lblRepositEstadoCivil.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositEstadoCivil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositEstadoCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 120, 20));

        lblConjuge.setForeground(new java.awt.Color(0, 102, 102));
        lblConjuge.setText("Conjuge:");
        painelPaciente.add(lblConjuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        lblRepositConjuge.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositConjuge.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositConjuge, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 340, 20));

        lblMae.setForeground(new java.awt.Color(0, 102, 102));
        lblMae.setText("Mãe:");
        painelPaciente.add(lblMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, 20));

        lblRepositMae.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositMae.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositMae, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 230, 20));

        lblPai.setForeground(new java.awt.Color(0, 102, 102));
        lblPai.setText("Pai:");
        painelPaciente.add(lblPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, 20));

        lblRepositPai.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositPai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositPai, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 230, 20));

        lblCelular.setForeground(new java.awt.Color(0, 102, 102));
        lblCelular.setText("Celular:");
        painelPaciente.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, 20));

        lblRepositCelular.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositCelular.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 110, 20));

        lblUF.setForeground(new java.awt.Color(0, 102, 102));
        lblUF.setText("UF:");
        painelPaciente.add(lblUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, 20));

        lblRepositUF.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositUF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 50, 20));

        lblCidade.setForeground(new java.awt.Color(0, 102, 102));
        lblCidade.setText("Cidade:");
        painelPaciente.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, -1, 20));

        lblRepositCidade.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositCidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 140, 20));

        lblBairro.setForeground(new java.awt.Color(0, 102, 102));
        lblBairro.setText("Bairro:");
        painelPaciente.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, -1, 20));

        lblRepositBairro.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositBairro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 140, 20));

        lblRua.setForeground(new java.awt.Color(0, 102, 102));
        lblRua.setText("Rua:");
        painelPaciente.add(lblRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 20));

        lblRepositRua.setForeground(new java.awt.Color(0, 102, 102));
        lblRepositRua.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        painelPaciente.add(lblRepositRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 190, 20));

        lblRepositCPFPaciente.setForeground(java.awt.Color.orange);
        painelPaciente.add(lblRepositCPFPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 17, 90, 20));

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N

        javax.swing.GroupLayout painelSimuladorLayout = new javax.swing.GroupLayout(painelSimulador);
        painelSimulador.setLayout(painelSimuladorLayout);
        painelSimuladorLayout.setHorizontalGroup(
            painelSimuladorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 268, Short.MAX_VALUE)
        );
        painelSimuladorLayout.setVerticalGroup(
            painelSimuladorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 57, Short.MAX_VALUE)
        );

        painelPaciente.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 280, 80));

        painelCabecalho.add(painelPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 270));

        getContentPane().add(painelCabecalho, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.setBackground(java.awt.Color.white);

        painelCronometro.setBackground(java.awt.Color.white);
        painelCronometro.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Medindo Tráfego:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N

        lblHora.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHora.setText("00:");

        lblMinuto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMinuto.setForeground(new java.awt.Color(0, 102, 102));
        lblMinuto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinuto.setText("00:");

        lblSegundos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblSegundos.setForeground(new java.awt.Color(0, 102, 102));
        lblSegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSegundos.setText("00:");

        lblMilissegundos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMilissegundos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMilissegundos.setText("00");

        javax.swing.GroupLayout painelCronometroLayout = new javax.swing.GroupLayout(painelCronometro);
        painelCronometro.setLayout(painelCronometroLayout);
        painelCronometroLayout.setHorizontalGroup(
            painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCronometroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMilissegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelCronometroLayout.setVerticalGroup(
            painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelCronometroLayout.createSequentialGroup()
                .addGroup(painelCronometroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMilissegundos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMinuto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        lblVerificacao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblVerificacao.setForeground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(painelCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(barraProgresso, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(lblVerificacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblVerificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(barraProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelCronometro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 273, 500, 90));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblConjuge;
    private javax.swing.JLabel lblDtNascimento;
    private javax.swing.JLabel lblEstadoCivil;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblMae;
    private javax.swing.JLabel lblMilissegundos;
    private javax.swing.JLabel lblMinuto;
    private javax.swing.JLabel lblNomePaciente;
    private javax.swing.JLabel lblPai;
    public static javax.swing.JLabel lblRepositBairro;
    public static javax.swing.JLabel lblRepositCPFPaciente;
    public static javax.swing.JLabel lblRepositCelular;
    public static javax.swing.JLabel lblRepositCidade;
    public static javax.swing.JLabel lblRepositConjuge;
    public static javax.swing.JLabel lblRepositDtNascimento;
    public static javax.swing.JLabel lblRepositEstadoCivil;
    public static javax.swing.JLabel lblRepositIdPaciente;
    public static javax.swing.JLabel lblRepositMae;
    public static javax.swing.JLabel lblRepositPaciente;
    public static javax.swing.JLabel lblRepositPai;
    public static javax.swing.JLabel lblRepositRua;
    public static javax.swing.JLabel lblRepositSexo;
    public static javax.swing.JLabel lblRepositUF;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblSegundos;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblUF;
    private javax.swing.JLabel lblVerificacao;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JPanel painelCronometro;
    private javax.swing.JPanel painelPaciente;
    private javax.swing.JPanel painelSimulador;
    // End of variables declaration//GEN-END:variables
}
