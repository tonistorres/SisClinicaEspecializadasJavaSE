package br.com.multclin.telas;

import br.com.multclin.bo.MedicoBO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.MedicoDAO;
import br.com.multclin.dao.ModeloAgendamentoItemDAO;
import br.com.multclin.dao.ModeloMedicoEspecialidadeDAO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.MedicoDTO;
import br.com.multclin.dto.ModeloAgendamentoItemDTO;
import br.com.multclin.dto.ModeloMedicoEspecialidadeDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaAgendamento.btnPesqMedico;
import static br.com.multclin.telas.TelaAgendamento.btnPesqPaciente;
import static br.com.multclin.telas.TelaAgendamento.cbEspecialidade;
import static br.com.multclin.telas.TelaAgendamento.jDateTxtDtAgenda;
import static br.com.multclin.telas.TelaAgendamento.lblIdMedico;
import static br.com.multclin.telas.TelaAgendamento.lblRepositIDEspecialidade;
import static br.com.multclin.telas.TelaAgendamento.lblRepositNomeMedico;
import static br.com.multclin.telas.TelaCosultarProcedimentoPorMedico.txtNome;
import static br.com.multclin.telas.TelaCosultarProcedimentoPorMedico.txtProcedimento;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import com.placeholder.PlaceHolder;
import java.awt.Color;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DaTorres
 */
public class FrmMuralHannaProfSaude extends javax.swing.JInternalFrame {

    Font f = new Font("Tahoma", Font.BOLD, 12);
    MedicoDTO medicoDTO = new MedicoDTO();
    MedicoDAO medicoDAO = new MedicoDAO();
    MedicoBO medicoBO = new MedicoBO();
    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
    ModeloMedicoEspecialidadeDTO modeloMedicoEspecialidadeDTO = new ModeloMedicoEspecialidadeDTO();
    ModeloMedicoEspecialidadeDAO modeloMedicoEspecialidadeDAO = new ModeloMedicoEspecialidadeDAO();
    ModeloAgendamentoItemDAO modeloAgendamentoItemDAO = new ModeloAgendamentoItemDAO();
    br.com.multclin.telas.TelaCosultarProcedimentoPorMedico formConsultarProceMedico;
    ConexaoUtil conecta = new ConexaoUtil();

    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());

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

    int flagC = 0;
    /**
     * Código Mestre Chimura
     */
    private static FrmMuralHannaProfSaude instance = null;

    public static FrmMuralHannaProfSaude getInstance() {

        if (instance == null) {

            try {
                instance = new FrmMuralHannaProfSaude();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    
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

    
    public FrmMuralHannaProfSaude() throws ClassNotFoundException, SQLException {

        initComponents();
        frontEnd();

        try {
            conecta.getInstance().getConnection();

            if (conecta != null) {

                int flagBtn = 1;
                progressBar(flagBtn);

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
                tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 9));
                tabela.getTableHeader().setOpaque(false);
                tabela.getTableHeader().setBackground(new Color(32, 136, 203));
                tabela.getTableHeader().setForeground(new Color(255, 255, 255));
                tabela.setRowHeight(34);

            }

        } catch (Exception e) {

            e.getMessage();
            acaoSairDoSistemaFormLocal();
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
                jLEnd.setIcon(null);
                limpaTodosIconesLinhaTemporal();
                for (int i = 0; i < 101; i++) {

                    try {

                        sleep(45);
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
                                    //acaoBotaoPesquisar();
                                    acaoCarregaMural();

                                    break;
                                }

                                case 2: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    if (estaFechado(formConsultarProceMedico)) {
                                        formConsultarProceMedico = new TelaCosultarProcedimentoPorMedico();
                                        DeskTop.add(formConsultarProceMedico).setLocation(2, 3);
                                        formConsultarProceMedico.setTitle("Procedimento(s) por Médico");
                                        
                                        String ProfSaude = tabela.getValueAt(tabela.getSelectedRow(), 0).toString();
                                        String Procedimento = tabela.getValueAt(tabela.getSelectedRow(), 1).toString();
                                        txtNome.setText(ProfSaude);
                                        txtProcedimento.setText(Procedimento);
                                        formConsultarProceMedico.setVisible(true);
                                        
                                    } else {
                                        formConsultarProceMedico.toFront();
                                        formConsultarProceMedico.setVisible(true);
                                    }

                                    break;
                                }

                                case 3: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    break;
                                }

                                case 4: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

                                    break;
                                }

                                case 5: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setText("30% Buscando dados núvem...");
                                    jLTesteConexaoPositivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/database.png")));
                                    painelSimulador.add(jLTesteConexaoPositivo);
                                    jLTesteConexaoPositivo.setBounds(85, 15, 50, 50);

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

    /**
     * Soma todos os Valores Totais lançados na minha tabela venda
     * https://www.youtube.com/watch?v=ME_VgfjKvsQ&index=30&list=PLt2CbMyJxu8iQL67Am38O1j5wKLf0AIRZ
     */
    private void somaTotal() {
        try {

            float somaTotal = 0, valorTotal;

            int contador = tabela.getRowCount();

            for (int i = 0; i < contador; i++) {

                String rsValorTratado = tabela.getValueAt(i, 3).toString().replace("R$", "").trim();
                //String rsDescontoTratado = tabela.getValueAt(i, 3).toString().replace("R$", "").trim();

                valorTotal = MetodoStaticosUtil.converteFloat(rsValorTratado);

                somaTotal = somaTotal + valorTotal;
            }

            lblValorTotal.setText(moeda.format(somaTotal));

        } catch (Exception e) {

            e.printStackTrace();

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

                        sleep(70);
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

    private void frontEnd() {
//        this.lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
    }

    public void addRowJTable() {

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
        tabela.setRowHeight(34);

        //*************************************************************************
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        ArrayList<ModeloAgendamentoItemDTO> list;

        try {

            JButton btnProceMedico = new JButton();
            btnProceMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/multclin/imagens/menu/consultarProcede.png")));

            btnProceMedico.setName("btnProceMedico");
            btnProceMedico.setToolTipText("Buscar Detalhado.");

            list = (ArrayList<ModeloAgendamentoItemDTO>) modeloAgendamentoItemDAO.filtrarQuadroDemonstrativo();

            Object rowData[] = new Object[5];//são 04 colunas 
            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getModAgendamentoDto().getNomeMedicoDto();
                rowData[1] = list.get(i).getModItemDto().getNomeProcedeDto();
                rowData[2] = list.get(i).getModItemDto().getnProcedeDto();
                rowData[3] = moeda.format(list.get(i).getModItemDto().getRsBrutoDto());
                rowData[4] = btnProceMedico;
                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(70);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(160);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(44);//reagendamento

            somaTotal();

        } catch (Exception e) {
            System.out.println("Método aoCarregar()" + e.getMessage());
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelCabecalho = new javax.swing.JPanel();
        painelSimulador = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        barraProgresso = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelValorTotal = new javax.swing.JPanel();
        lblValorTotal = new javax.swing.JLabel();

        setClosable(true);

        painelCabecalho.setBackground(new java.awt.Color(255, 255, 255));
        painelCabecalho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelSimulador.setLayout(null);

        lblLinhaInformativa.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        painelSimulador.add(lblLinhaInformativa);
        lblLinhaInformativa.setBounds(520, 15, 170, 20);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(520, 35, 170, 24);

        painelCabecalho.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 70));

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mural Agenda Profissional Saúde:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18), javax.swing.UIManager.getDefaults().getColor("InternalFrame.activeTitleGradient"))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROF.SAUDE", "PROCEDIMENTO", "QTDE.", "TOTAL", ""
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
        jScrollPane2.setViewportView(tabela);
        if (tabela.getColumnModel().getColumnCount() > 0) {
            tabela.getColumnModel().getColumn(4).setResizable(false);
            tabela.getColumnModel().getColumn(4).setPreferredWidth(44);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 40, 600, 330));

        painelValorTotal.setBackground(java.awt.Color.white);
        painelValorTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Somatório dos Totais:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 102, 102))); // NOI18N

        lblValorTotal.setBackground(new java.awt.Color(0, 102, 102));
        lblValorTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblValorTotal.setForeground(new java.awt.Color(0, 102, 102));
        lblValorTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout painelValorTotalLayout = new javax.swing.GroupLayout(painelValorTotal);
        painelValorTotal.setLayout(painelValorTotalLayout);
        painelValorTotalLayout.setHorizontalGroup(
            painelValorTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblValorTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
        );
        painelValorTotalLayout.setVerticalGroup(
            painelValorTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblValorTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        jPanel1.add(painelValorTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 170, 70));

        painelCabecalho.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 650, 450));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelCabecalho, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
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

                if (boton.getName().equals("btnProceMedico")) {

                   
                    
                    int flagBtn = 2;
                    progressBar(flagBtn);
                }

            }

        }
    }//GEN-LAST:event_tabelaMouseClicked

    private void aoReceberFocoTxtBuscar() {
        Font fonteLlbInfoInicio = new Font("Tahoma", Font.BOLD, 9);//label informativo 
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("Digite o Registro -->[ENTER]<--");
    }

    private void acaoCarregaMural() {

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JPanel painelCabecalho;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JPanel painelValorTotal;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
