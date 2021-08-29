package br.com.multclin.telas;

import br.com.multclin.bo.AgendamentoBO;
import br.com.multclin.bo.PacienteBO;
import br.com.multclin.dao.AgendamentoDAO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dao.GeraCodigoAutomaticoDAO;
import br.com.multclin.dao.HistoricoPacienteDAO;
import br.com.multclin.dao.PacienteDAO;
import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.HistoricoPacienteDTO;
import br.com.multclin.dto.PacienteDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;

import static br.com.multclin.telas.Modulo2AtendimentoConsultorio.btnRefresh;
import static br.com.multclin.telas.TelaPaciente.txtNome;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.lblNomeCompletoUsuario;
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import static br.com.multclin.telas.TelaPrincipal.lblRepositorioHD;
import static br.com.multclin.telas.TelaPrincipal.lblStatusData;
import static br.com.multclin.telas.TelaPrincipal.lblStatusHora;
import static br.com.multclin.telas.TelaPrincipal.lblUsuarioLogado;
//import static com.itextpdf.kernel.pdf.PdfName.Font;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
//import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.awt.Font;

import java.io.OutputStream;
//import java.awt.Font;
//import java.awt.Font;
import static java.lang.Thread.sleep;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import texteditor.TelaEditor;
import texteditor.TelaEditorConsulta;
import static texteditor.TelaEditor.tp_texto;
import static texteditor.TelaEditorConsulta.tp_textoConsulta;
import texteditor.TelaEditorDiagnostico;
import static texteditor.TelaEditorDiagnostico.tp_textoDiagnostico;

/**
 *
 * @author Pessoal
 */
public class Modulo2HistoricoPaciente extends javax.swing.JInternalFrame {

    //sobre pdf gerar receituario 
    public static final String DEST = "C:/agendaZap/receituario.pdf";

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat moeda = NumberFormat.getCurrencyInstance(Locale.getDefault());

    //Font f=new Font(Font.TIMES_ROMAN,14,Font.BOLD);
    Font f = new Font("Tahoma", Font.BOLD, 11);
    texteditor.TelaEditor telaEditor;
    texteditor.TelaEditorConsulta telaEditorConsulta;
    texteditor.TelaEditorDiagnostico telaEditorDiagnostico;
    br.com.multclin.telas.Modulo2HistoricoPacienteConsulta formConsultarHistorico;

    GeraCodigoAutomaticoDAO geraCodigoDAO = new GeraCodigoAutomaticoDAO();

    HistoricoPacienteDTO historicoDTO = new HistoricoPacienteDTO();
    HistoricoPacienteDAO historicoDAO = new HistoricoPacienteDAO();

    AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
    AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
    AgendamentoBO agendamentoBO = new AgendamentoBO();

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

    PacienteDTO pacienteDTO = new PacienteDTO();
    PacienteDAO pacienteDAO = new PacienteDAO();
    PacienteBO medicoBO = new PacienteBO();
    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

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

    public Modulo2HistoricoPaciente() {
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

                btnDiagnostico.setEnabled(false);
                btnEditorPrescricao.setEnabled(false);
                btnReceituario.setEnabled(false);
                btnConsultarLista.setEnabled(false);
                btnRefresh.setEnabled(false);
                save.setEnabled(false);

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

                            lblLinhaInformativa.setForeground(Color.ORANGE);
                            lblLinhaInformativa.setBackground(new Color(75, 0, 130));
                            lblLinhaInformativa.setText("");
                            lblLinhaInformativa.setText("^_^ Linha Informativa ^_^");

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

                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Dr(a) " + lblNomeCompletoUsuario.getText() + ". Tenha Bom Trabalho ^_^");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);
                                    //carregar dados do paciente 
                                    gerarCodigo();
                                    dadosPaciente();
                                    lblRepositHD.setText(lblRepositorioHD.getText());
                                    preenchendoPrescricao();
                                    preenchendoDiagnostico();
                                    //  abrirEditorDiagnostico();
                                    //  buscandoHistoricoPaciente();
                                    lblRepositNomeMedicoAgenda.setText(lblNomeCompletoUsuario.getText());
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

                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + ". Tenha Bom Trabalho ^_^");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);
                                    abrirEditorBarraFerramenta();

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

                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + ". Editor Aberto Consulta");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);
//                                    abrirEditorBarraFerramentaConsulta();

                                    break;
                                }

                                case 4: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setFont(f);
                                    lblLinhaInformativa.setForeground(Color.ORANGE);
                                    lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + ". Irei Salvar os Dados na Nuvem...");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);
                                    salvar();

                                    break;
                                }

                                case 5: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setFont(f);
                                    lblLinhaInformativa.setForeground(Color.ORANGE);
                                    lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                                    lblLinhaInformativa.setText("Abrindo Editor Diagnóstico...");
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + ". Tenha Bom Trabalho ^_^");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    abrirEditorDiagnostico();

                                    break;
                                }

                                case 6: {
                                    //MELHORANDO EXPERIENCIA VISUAL DO USUÁRIO 
                                    lblLinhaInformativa.setFont(f);
                                    lblLinhaInformativa.setForeground(Color.ORANGE);
                                    lblLinhaInformativa.setBackground(new Color(75, 0, 130)); //preto quase branco 
                                    lblLinhaInformativa.setText("Abrindo Form Histórico...");
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + ". Tenha Bom Trabalho ^_^");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                    abrirFormConsultaHistorico();

                                    break;
                                }

                                default: {
                                    lblLinhaInformativa.setForeground(new Color(75, 0, 130));
                                    lblLinhaInformativa.setText("Olá Dr(a) " + lblNomeCompletoUsuario.getText() + " obrigado pela autenticação ^_^. Tenha um Bom Trabalho!!");
                                    lblLinhaInformativa.setBackground(Color.ORANGE);
                                    btnRefresh.setEnabled(true);
                                    jLEnd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/barra/nuvem/FimPesquisa.png")));
                                    painelSimulador.add(jLEnd);
                                    jLEnd.setBounds(455, 15, 100, 50);

                                }

                            }

                            save.setEnabled(false);

                            btnDiagnostico.setEnabled(true);
                            btnEditorPrescricao.setEnabled(true);
                            btnReceituario.setEnabled(true);
                            btnConsultarLista.setEnabled(true);

                        }

                    } catch (Exception e) {
                        System.out.println("try:" + e.getMessage());
                    }

                }
            }

        }.start();// iniciando a Thread

    }

//    private void buscandoHistoricoPaciente() {
//
//        int idPaciente = Integer.parseInt(lblRepositIdPaciente.getText());
//
//        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
//
//        ***********************************************
//        https://www.youtube.com/watch?v=jPfKFm2Yfow //
//        ***********************************************
//        CANAL:MamaNs - Java Swing UI - Design jTable       
//        clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
//        https://www.youtube.com/watch?v=RXhMdUPk12k
//        *******************************************************************************************
//        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
//        tabela.getTableHeader().setOpaque(false);
//        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
//        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
//        tabela.setRowHeight(50);
//
//        tabela.setDefaultRenderer(Object.class, new Render());
//        tabela.getColumnModel().getColumn(1).setCellRenderer(new TextAreaCellRenderer());
//
//        JButton btnEditor = new JButton();
//        btnEditor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editor.png")));
//
//        btnEditor.setName(
//                "Editor");
//        ArrayList<HistoricoPacienteDTO> list;
//
//        try {
//
//            list = (ArrayList<HistoricoPacienteDTO>) historicoDAO.buscarPorIdTblConsultaList(idPaciente);
//
//            Object rowData[] = new Object[5];
//
//            for (int i = 0; i < list.size(); i++) {
//                  JOptionPane.showMessageDialog(null, "Procedimeto:"+list.get(i).getModItemDto().getNomeProcedeDto()+"Bruto :" + list.get(i).getModProcedeDto().getRsBrutoDto());
//                rowData[0] = list.get(i).getIdGeradoDto();
//                rowData[1] = list.get(i).getOrientacaoDto();
//                rowData[2] = list.get(i).getNomeMedicoDto();
//                rowData[3] = btnEditor;
//                model.addRow(rowData);
//            }
//
//            tabela.setModel(model);
//
//            tabela.getColumnModel().getColumn(0).setPreferredWidth(42);//id
//            tabela.getColumnModel().getColumn(1).setPreferredWidth(580);//nome paciente 
//            tabela.getColumnModel().getColumn(2).setPreferredWidth(180);//data agendamento
//            tabela.getColumnModel().getColumn(3).setPreferredWidth(50);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("buscando Historico do paciente -buscandoHistoricoPaciente() ");
//        }
//
//    }
    private void gerarCodigo() {

        /**
         * Setar uma Data pega no sistema e setar num JDateChooser
         * https://www.youtube.com/watch?v=GlBMniOwCnI
         */
        int numeroMax = geraCodigoDAO.gerarCodigoTbHistoricoPaciente();
        int resultado = 0;
        // JOptionPane.showMessageDialog(null, "Numero"+ numeroDaVenda);
        resultado = numeroMax + 1;
        lblIdOrientacao.setText(String.valueOf(resultado));
        lblIdOrientacao.setForeground(new Color(75, 0, 130));

    }

    private void preenchendoDiagnostico() {
        txtAreaDiagnostico.append("\n");
        txtAreaDiagnostico.append("Diagnóstico(Orientação):\n");
    }

    private void preenchendoPrescricao() {
        txtAreaPrescricao.append("\n");
        txtAreaPrescricao.append("Prescrição:\n");
        txtAreaPrescricao.append("\n");

        txtAreaPrescricao.append("1)    _______________  mg \n");
        txtAreaPrescricao.append("MODO DE USAR: \n");

        txtAreaPrescricao.append("2)    _______________  mg \n");
        txtAreaPrescricao.append("MODO DE USAR: \n");

        txtAreaPrescricao.append("3)    _______________  mg \n");
        txtAreaPrescricao.append("MODO DE USAR: \n");

        txtAreaPrescricao.append("4)    _______________  mg \n");
        txtAreaPrescricao.append("MODO DE USAR: \n");

        txtAreaPrescricao.append("5)    _______________  mg \n");
        txtAreaPrescricao.append("MODO DE USAR: \n");

        txtAreaPrescricao.append("6)    _______________  mg \n");
        txtAreaPrescricao.append("MODO DE USAR: \n");

        txtAreaPrescricao.append("7)    _______________  mg \n");
        txtAreaPrescricao.append("MODO DE USAR: \n");

        txtAreaPrescricao.append("8)    _______________  mg \n");
        txtAreaPrescricao.append("MODO DE USAR: \n");

        txtAreaPrescricao.append("\n");
        txtAreaPrescricao.append("Recomendações(Orientações Adicionais):\n");

    }

    private void frontEnd() {

        this.save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/save.png")));
        this.btnConsultarLista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/info.png")));
        this.btnEditorPrescricao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editor.png")));
        this.btnDiagnostico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editor.png")));
        this.btnReceituario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/receituario.png")));

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

//    private void adicionarTabela() {
//
//        //***********************************************
//        //https://www.youtube.com/watch?v=jPfKFm2Yfow //
//        //***********************************************
//        //CANAL:MamaNs - Java Swing UI - Design jTable       
//        //clicar e a tabela mudar a linha de cor indicando que a linha cliccada é aquela 
//        //https://www.youtube.com/watch?v=RXhMdUPk12k
//        //*******************************************************************************************
//        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
//        tabela.getTableHeader().setOpaque(false);
//        tabela.getTableHeader().setBackground(new Color(32, 136, 203));
//        tabela.getTableHeader().setForeground(new Color(255, 255, 255));
//        tabela.setRowHeight(50);
//
//        tabela.setDefaultRenderer(Object.class, new Render());
//        tabela.getColumnModel().getColumn(1).setCellRenderer(new TextAreaCellRenderer());
//
//        JButton btnExcluir = new JButton();
//        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/lixeira.png")));
//
//        JButton btnEditor = new JButton();
//        btnEditor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editor.png")));
//
//        btnExcluir.setName(
//                "Ex");
//
//        btnEditor.setName(
//                "Editor");
//
//        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
//
//        try {
//
//            Object rowData[] = new Object[5];
//
//            rowData[0] = lblIdOrientacao.getText();
//            rowData[1] = txtAreaPrescricao.getText();
//            rowData[2] = lblRepositNomeMedicoAgenda.getText();
//            rowData[3] = btnEditor;
//            rowData[4] = btnExcluir;
//
//            model.addRow(rowData);
//
//            tabela.setModel(model);
//
//            tabela.getColumnModel().getColumn(0).setPreferredWidth(60);
//            tabela.getColumnModel().getColumn(1).setPreferredWidth(580);
//            tabela.getColumnModel().getColumn(2).setPreferredWidth(140);
//            tabela.getColumnModel().getColumn(3).setPreferredWidth(50);
//            tabela.getColumnModel().getColumn(4).setPreferredWidth(50);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("Erro:Método adicionarTabela()" + ex.getMessage());
//
//        }
//
//    }
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

    private void dadosPaciente() {

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
                }

                calculaIdade();
            } else {
                System.out.println("Não exite itens na lista ");

            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            dispose();
        }

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
            String DataDeNascimento = lblRepositDtNascimento.getText();
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

                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setFont(f);
                lblLinhaInformativa.setText("Data Nascimento deve ser menor que Data Atual");
                lblRepositDtNascimento.requestFocus();

            } else {

                if (calculo <= 150) {

                    lblRepositIdade.setText(Long.toString(calculo) + "ano(s)");

//                    btnSalvar.setEnabled(true);
                } else {

                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setText("O Sistema não Aceita uma passoa com mais de 150 anos.");
                    txtNome.requestFocus();

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
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

    private void abrirEditorBarraFerramenta() {

        if (estaFechado(telaEditor)) {
            telaEditor = new TelaEditor();
            DeskTop.add(telaEditor).setLocation(1, 1);
            telaEditor.setTitle("Prescrever Orientações");
            telaEditor.setVisible(true);
            tp_texto.setText(txtAreaPrescricao.getText());

        } else {
            telaEditor.toFront();
            telaEditor.setVisible(true);

        }
    }

    private void abrirEditorDiagnostico() {

        if (estaFechado(telaEditorDiagnostico)) {
            telaEditorDiagnostico = new TelaEditorDiagnostico();
            DeskTop.add(telaEditorDiagnostico).setLocation(1, 1);
            telaEditorDiagnostico.setTitle("Editor de Texto Diagnóstico");
            telaEditorDiagnostico.setVisible(true);
            tp_textoDiagnostico.setText(txtAreaDiagnostico.getText());

        } else {
            telaEditorDiagnostico.toFront();
            telaEditorDiagnostico.setVisible(true);

        }
    }

    private void abrirFormConsultaHistorico() {

        if (estaFechado(formConsultarHistorico)) {
            formConsultarHistorico = new Modulo2HistoricoPacienteConsulta();
            DeskTop.add(formConsultarHistorico).setLocation(1, 1);
            formConsultarHistorico.setTitle("Consultar Historico");
            formConsultarHistorico.setVisible(true);
            Modulo2HistoricoPacienteConsulta.lblRepositIdPacienteConsulta.setText(lblRepositIdPaciente.getText());
            Modulo2HistoricoPacienteConsulta.lblIdOrientacaoConsulta.setText(lblIdOrientacao.getText());

        } else {
            formConsultarHistorico.toFront();
            formConsultarHistorico.setVisible(true);

        }
    }

//    private void abrirEditorBarraFerramentaConsulta() {
//
//        if (estaFechado(telaEditorConsulta)) {
//            telaEditorConsulta = new TelaEditorConsulta();
//            DeskTop.add(telaEditorConsulta).setLocation(1, 1);
//            telaEditorConsulta.setTitle("Consultar Prescição");
//            telaEditorConsulta.setVisible(true);
//
//            int linhaRegistro = tabela.getSelectedRow();
//            String corpoDiagnostico = (tabela.getValueAt(linhaRegistro, 1).toString());
//            tp_textoConsulta.setText(corpoDiagnostico);
//            tp_textoConsulta.setEditable(false);
//            // aqui proxima tentativa
//
//        } else {
//            telaEditorConsulta.toFront();
//            telaEditorConsulta.setVisible(true);
//
//        }
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelPrincipal = new javax.swing.JPanel();
        painelSimulador = new javax.swing.JPanel();
        barraProgresso = new javax.swing.JProgressBar();
        lblIdOrientacao = new javax.swing.JLabel();
        painelLinhaInformativa = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JTextField();
        painelPaciente = new javax.swing.JPanel();
        lblRepositIdPaciente = new javax.swing.JLabel();
        lblRepositPaciente = new javax.swing.JLabel();
        lblNomePaciente = new javax.swing.JLabel();
        lblDtNascimento = new javax.swing.JLabel();
        lblRepositDtNascimento = new javax.swing.JLabel();
        lblSexo = new javax.swing.JLabel();
        lblRepositSexo = new javax.swing.JLabel();
        lblRepositIdade = new javax.swing.JLabel();
        painelSobreMedico = new javax.swing.JPanel();
        lblRepositNomeMedicoAgenda = new javax.swing.JLabel();
        lblMedico = new javax.swing.JLabel();
        lblIdMedicoAgenda = new javax.swing.JLabel();
        painelAutenticacao = new javax.swing.JPanel();
        lblHD = new javax.swing.JLabel();
        lblRepositHD = new javax.swing.JLabel();
        lblIdAgendamentoReposit = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaPrescricao = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaDiagnostico = new javax.swing.JTextArea();
        painelBarraDiagnostico = new javax.swing.JPanel();
        btnDiagnostico = new javax.swing.JButton();
        lblDiagnostico = new javax.swing.JLabel();
        painelBarraPrescricao = new javax.swing.JPanel();
        save = new javax.swing.JButton();
        btnReceituario = new javax.swing.JButton();
        btnEditorPrescricao = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnConsultarLista = new javax.swing.JButton();

        setClosable(true);

        painelPrincipal.setBackground(java.awt.Color.white);
        painelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        painelSimulador.setBackground(new java.awt.Color(255, 255, 255));
        painelSimulador.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Viajando na Núvem:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelSimulador.setLayout(null);
        painelSimulador.add(barraProgresso);
        barraProgresso.setBounds(510, 25, 240, 24);

        lblIdOrientacao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblIdOrientacao.setForeground(new java.awt.Color(75, 0, 130));
        lblIdOrientacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIdOrientacao.setText("ID");
        painelSimulador.add(lblIdOrientacao);
        lblIdOrientacao.setBounds(750, 10, 50, 50);

        painelPrincipal.add(painelSimulador, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 810, 70));

        painelLinhaInformativa.setBackground(java.awt.Color.white);
        painelLinhaInformativa.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Linha Informativa:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelLinhaInformativa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        painelLinhaInformativa.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(9, 21, 540, 40));

        painelPrincipal.add(painelLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 560, 70));

        painelPaciente.setBackground(java.awt.Color.white);
        painelPaciente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Paciente:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelPaciente.setForeground(new java.awt.Color(75, 0, 130));
        painelPaciente.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositIdPaciente.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositIdPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelPaciente.add(lblRepositIdPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 20));

        lblRepositPaciente.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositPaciente.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelPaciente.add(lblRepositPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 200, 20));

        lblNomePaciente.setForeground(new java.awt.Color(75, 0, 130));
        lblNomePaciente.setText(" ID       Paciente: ");
        painelPaciente.add(lblNomePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 110, -1));

        lblDtNascimento.setForeground(new java.awt.Color(75, 0, 130));
        lblDtNascimento.setText("Data Nascimento:");
        painelPaciente.add(lblDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, -1));

        lblRepositDtNascimento.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositDtNascimento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositDtNascimento.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelPaciente.add(lblRepositDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, 90, 20));

        lblSexo.setForeground(new java.awt.Color(75, 0, 130));
        lblSexo.setText("Sexo:");
        painelPaciente.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, -1, -1));

        lblRepositSexo.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositSexo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositSexo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelPaciente.add(lblRepositSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 90, 20));

        lblRepositIdade.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblRepositIdade.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositIdade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRepositIdade.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Idade:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelPaciente.add(lblRepositIdade, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, 80, 50));

        painelPrincipal.add(painelPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 550, 80));

        painelSobreMedico.setBackground(java.awt.Color.white);
        painelSobreMedico.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sobre Médico:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelSobreMedico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRepositNomeMedicoAgenda.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositNomeMedicoAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelSobreMedico.add(lblRepositNomeMedicoAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 190, 20));

        lblMedico.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMedico.setForeground(new java.awt.Color(75, 0, 130));
        lblMedico.setText("Nome:");
        painelSobreMedico.add(lblMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        lblIdMedicoAgenda.setForeground(new java.awt.Color(75, 0, 130));
        lblIdMedicoAgenda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelSobreMedico.add(lblIdMedicoAgenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 30, 20));

        painelAutenticacao.setBackground(java.awt.Color.white);
        painelAutenticacao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Autenticação a Nível de Máquina", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(75, 0, 130))); // NOI18N
        painelAutenticacao.setForeground(new java.awt.Color(75, 0, 130));
        painelAutenticacao.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHD.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHD.setForeground(new java.awt.Color(75, 0, 130));
        lblHD.setText("HD:");
        painelAutenticacao.add(lblHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 31, 25));

        lblRepositHD.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblRepositHD.setForeground(new java.awt.Color(75, 0, 130));
        lblRepositHD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelAutenticacao.add(lblRepositHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 20, 120, 25));

        lblIdAgendamentoReposit.setForeground(new java.awt.Color(75, 0, 130));
        lblIdAgendamentoReposit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(75, 0, 130)));
        painelAutenticacao.add(lblIdAgendamentoReposit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 24, 30, 20));

        painelSobreMedico.add(painelAutenticacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 220, 60));

        painelPrincipal.add(painelSobreMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 240, 140));

        txtAreaPrescricao.setEditable(false);
        txtAreaPrescricao.setColumns(20);
        txtAreaPrescricao.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txtAreaPrescricao.setForeground(new java.awt.Color(75, 0, 130));
        txtAreaPrescricao.setRows(5);
        txtAreaPrescricao.setToolTipText("Área de Prescrição");
        txtAreaPrescricao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAreaPrescricaoFocusGained(evt);
            }
        });
        txtAreaPrescricao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAreaPrescricaoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(txtAreaPrescricao);

        painelPrincipal.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 410, 280));

        txtAreaDiagnostico.setEditable(false);
        txtAreaDiagnostico.setColumns(20);
        txtAreaDiagnostico.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        txtAreaDiagnostico.setForeground(new java.awt.Color(75, 0, 130));
        txtAreaDiagnostico.setRows(5);
        txtAreaDiagnostico.setToolTipText("Área de Diagnóstico");
        txtAreaDiagnostico.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAreaDiagnosticoFocusGained(evt);
            }
        });
        txtAreaDiagnostico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAreaDiagnosticoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtAreaDiagnostico);

        painelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 390, 280));

        painelBarraDiagnostico.setBackground(java.awt.Color.orange);
        painelBarraDiagnostico.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnDiagnostico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosticoActionPerformed(evt);
            }
        });
        painelBarraDiagnostico.add(btnDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 5, 32, 32));

        lblDiagnostico.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDiagnostico.setForeground(new java.awt.Color(75, 0, 130));
        lblDiagnostico.setText("Diagnóstico:");
        painelBarraDiagnostico.add(lblDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        painelPrincipal.add(painelBarraDiagnostico, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 390, 40));

        painelBarraPrescricao.setBackground(new java.awt.Color(75, 0, 130));
        painelBarraPrescricao.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });
        painelBarraPrescricao.add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 5, 32, 32));

        btnReceituario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReceituarioActionPerformed(evt);
            }
        });
        painelBarraPrescricao.add(btnReceituario, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 5, 32, 32));

        btnEditorPrescricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditorPrescricaoActionPerformed(evt);
            }
        });
        painelBarraPrescricao.add(btnEditorPrescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 5, 32, 32));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(java.awt.Color.orange);
        jLabel1.setText("Prescrição:");
        painelBarraPrescricao.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        btnConsultarLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarListaActionPerformed(evt);
            }
        });
        painelBarraPrescricao.add(btnConsultarLista, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 5, 32, 32));

        painelPrincipal.add(painelBarraPrescricao, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, 410, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void enviandoParaCaixaStatus() {
        //transformando status em caixa 
        try {

            AgendamentoBO agendamentoBO = new AgendamentoBO();

            String IdAgenda = lblIdAgendamentoReposit.getText();

            agendamentoDTO.setIdDto(Integer.parseInt(IdAgenda));
            agendamentoDTO.setStatusAgendamentoDto("CAIXA");
            agendamentoDTO.setDtRegistroDto(lblStatusData.getText());
            agendamentoBO.atualizarCaixaBO(agendamentoDTO);

            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Caixa lançado com sucesso");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setBackground(Color.red);
            lblLinhaInformativa.setForeground(Color.WHITE);

            //********************************************************
            //colocar o foco em cima do botão refresh do formulário 
            //Modulo2AtendimentoConsultorio que ele irá executar a 
            //atualização da lista automaticamente
            //*******************************************************
            btnRefresh.requestFocus();

            //retornando a barra informativa para com default
            lblLinhaInformativa.setBackground(Color.ORANGE);
            lblLinhaInformativa.setForeground(new Color(75, 0, 130));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void salvarDados() throws PersistenciaException {

        //Agora convertendo a data de String <--> para Date
        Date dtConvertida;
        try {

            dtConvertida = formatador.parse(lblStatusData.getText());

            historicoDTO.setIdGeradoDto(Integer.parseInt(lblIdOrientacao.getText()));//idGerado
            historicoDTO.setFk_pacienteDto(Integer.parseInt(lblRepositIdPaciente.getText()));//fk_paciente
            historicoDTO.setFk_medicoDto(Integer.parseInt(lblIdMedicoAgenda.getText()));//fk_medico
            historicoDTO.setNomeMedicoDto(lblRepositNomeMedicoAgenda.getText());//nomeMedico
            historicoDTO.setDtHistoricoDto(dtConvertida);//histórico médico 
            historicoDTO.setOrientacaoDto(txtAreaPrescricao.getText().trim());//orientação
            historicoDTO.setNomeProfissionalDto(lblUsuarioLogado.getText());//login
            historicoDTO.setPerfilProfissionalDto(lblPerfil.getText());//perfil
            historicoDTO.setSereialHDDto(lblRepositorioHD.getText());//HD

            historicoDAO.inserir(historicoDTO);
            lblLinhaInformativa.setBackground(Color.red);
            lblLinhaInformativa.setForeground(Color.WHITE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setText("Registro Salvo com Sucesso!!!");

            enviandoParaCaixaStatus();

//            int numeroLinhas = tabela.getRowCount();
//
//            if (numeroLinhas > 0) {
//
//                while (tabela.getModel().getRowCount() > 0) {
//                    ((DefaultTableModel) tabela.getModel()).removeRow(0);
//
//                }
//
//                buscandoHistoricoPaciente();
//
//            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            System.out.println("método Salvar Histórico e Diagnostico médico(Ficha paciente)");
        }

    }

    /**
     * Abrindo pdf
     */
    private void abrirArquivoPdf() throws IOException {
        Desktop.getDesktop().open(new File("C:/agendaZap/receituario.pdf"));
    }

    /**
     * Gerando PDF Nery
     */
    private void gerandoPdfNeri() {
        //https://www.youtube.com/watch?v=3_W-2ORczWY
        Document documento = new Document();//cria um documento tipo pdf
        FontFactory.register("c:/windows/fonts/Tahoma.ttf", "tahoma");//seta caminho font
        //Get the image as Buffere Image

        try {

            OutputStream outputStream = new FileOutputStream("C:/agendaZap/receituario.pdf");//seta caminho onde será criando o pdf
            PdfWriter.getInstance(documento, outputStream);//intacia o documento e caminho do documento

            documento.open();//abre o documento
            documento.setPageSize(PageSize.A4); // modificado para o tamanho A4;

            com.lowagie.text.Font tahomaFontMenor = FontFactory.getFont("tahoma", 10f);//instancia o font do tipo tahomaFont
            com.lowagie.text.Font tahomaFont = FontFactory.getFont("tahoma", 12f);//instancia o font do tipo tahomaFont
            com.lowagie.text.Font tahomaFontMaior = FontFactory.getFont("tahoma", 16f);//instancia o font do tipo tahomaFont

            Image image = Image.getInstance("logoHannaMenor.jpeg");
            image.setAlignment(Element.ALIGN_CENTER);
            documento.add(image);

            Paragraph cnpj = new Paragraph("CNPJ:11.164.775/0001-19", tahomaFont);
            cnpj.setAlignment(Element.ALIGN_CENTER);
            documento.add(cnpj);

            Paragraph fones = new Paragraph("Fone:(98) 98134-7398/(98)8428-3726", tahomaFont);
            fones.setAlignment(Element.ALIGN_CENTER);
            documento.add(fones);

            Paragraph cabecalhoReceituario = new Paragraph("RECEITUARIO", tahomaFontMaior);
            cabecalhoReceituario.setAlignment(Element.ALIGN_CENTER);
            documento.add(cabecalhoReceituario);
//
//            Paragraph cabecalhoClinica = new Paragraph("Clínica Hanna | Miranda do Norte Maranhão | Data: " + lblStatusData.getText() + " Hora: " + lblStatusHora.getText(), tahomaFontMaior);
//            cabecalhoClinica.setAlignment(Element.ALIGN_LEFT);
//            documento.add(cabecalhoClinica);
//
//            Paragraph cabecalhoPaciente = new Paragraph("Paciente: " + lblRepositPaciente.getText() + " Sexo: " + lblRepositSexo.getText() + " Idade: " + lblRepositIdade.getText(), tahomaFontMaior);
//            cabecalhoPaciente.setAlignment(Element.ALIGN_LEFT);
//            documento.add(cabecalhoPaciente);

            Paragraph corpoReceituario = new Paragraph(txtAreaPrescricao.getText(), tahomaFont);
            corpoReceituario.setAlignment(Element.ALIGN_JUSTIFIED);
            documento.add(corpoReceituario);

            //Criando Área para Carimbo Médico   
//            Paragraph linhaHorizontal1Carimbo = new Paragraph("------------------------------", tahomaFontMaior);
//            linhaHorizontal1Carimbo.setAlignment(Element.ALIGN_LEFT);
//            documento.add(linhaHorizontal1Carimbo);
//
//            Paragraph linhaVertical1Carimbo = new Paragraph("|                                |", tahomaFontMaior);
//            linhaVertical1Carimbo.setAlignment(Element.ALIGN_LEFT);
//            documento.add(linhaVertical1Carimbo);
//
//            Paragraph linhaVertical2Carimbo = new Paragraph("|                                |", tahomaFontMaior);
//            linhaVertical2Carimbo.setAlignment(Element.ALIGN_LEFT);
//            documento.add(linhaVertical2Carimbo);
//
//            Paragraph linhaVertical3Carimbo = new Paragraph("|                                |", tahomaFontMaior);
//            linhaVertical3Carimbo.setAlignment(Element.ALIGN_LEFT);
//            documento.add(linhaVertical3Carimbo);
//
//            Paragraph linhaVertical4Carimbo = new Paragraph("|                                |", tahomaFontMaior);
//            linhaVertical4Carimbo.setAlignment(Element.ALIGN_LEFT);
//            documento.add(linhaVertical4Carimbo);
//
//            Paragraph linhaVertical5Carimbo = new Paragraph("|                                |", tahomaFontMaior);
//            linhaVertical5Carimbo.setAlignment(Element.ALIGN_LEFT);
//            documento.add(linhaVertical5Carimbo);
//
//            Paragraph linhaHorisontal2Carimbo = new Paragraph("-----------------------------", tahomaFontMaior);
//            linhaHorisontal2Carimbo.setAlignment(Element.ALIGN_LEFT);
//            documento.add(linhaHorisontal2Carimbo);
            Paragraph linhaEndereço = new Paragraph("__________________________________________________________", tahomaFontMaior);
            linhaEndereço.setAlignment(Element.ALIGN_CENTER);
            documento.add(linhaEndereço);
//
//            HeaderFooter footer = new HeaderFooter(new Phrase("av. Comércio, s/n - Centro - CEP 65.495-000 - Miranda do Norte -MA ",tahomaFont ), false);
//            footer.setBorder(Rectangle.TOP);
//            documento.setFooter(footer);

            Paragraph Endereco = new Paragraph("av. Comércio, s/n - Centro - CEP 65.495-000 - Miranda do Norte -MA", tahomaFont);
            Endereco.setAlignment(Element.ALIGN_CENTER);
            documento.add(Endereco);

            Paragraph Email = new Paragraph("Email:Clinicahanna2009@gmail.com", tahomaFont);
            Email.setAlignment(Element.ALIGN_CENTER);
            documento.add(Email);

            documento.close();

            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setText("receituário Pdf Gerado.");

        } catch (Exception e) {
        }

    }


    private void btnEditorPrescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditorPrescricaoActionPerformed
        int flagBtn = 2;
        progressBar(flagBtn);

    }//GEN-LAST:event_btnEditorPrescricaoActionPerformed

    private void btnReceituarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceituarioActionPerformed
        try {
            gerandoPdfNeri();
            abrirArquivoPdf();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReceituarioActionPerformed

    private void btnDiagnosticoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosticoActionPerformed
        int flagBtn = 5;
        progressBar(flagBtn);
    }//GEN-LAST:event_btnDiagnosticoActionPerformed

    private void salvar() throws PersistenciaException {

        try {

            //**********************************************************
            //SETANDO E SALVANDO DADOS PARA O HISTÓRICO DE PACIENTES //
            //*********************************************************
            lblLinhaInformativa.setBackground(Color.red);
            lblLinhaInformativa.setForeground(Color.WHITE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Setando Valores a serem salvos...");
            lblLinhaInformativa.setFont(f);

            historicoDTO.setIdGeradoDto(Integer.parseInt(lblIdAgendamentoReposit.getText()));//idAgendamento
            historicoDTO.setFk_pacienteDto(Integer.parseInt(lblRepositIdPaciente.getText()));//fk_paciente
            historicoDTO.setFk_medicoDto(Integer.parseInt(lblIdMedicoAgenda.getText()));//fk_medico
            historicoDTO.setNomeMedicoDto(lblRepositNomeMedicoAgenda.getText());//nomeMedico

            //Agora convertendo a data de String <--> para Date
            Date dtHistorico = formatador.parse(lblStatusData.getText());

            historicoDTO.setDtHistoricoDto(dtHistorico);

            historicoDTO.setCabecalhoDto("Clinica Hanna Data: " + lblStatusData.getText()
                    + " Hora: " + lblStatusHora.getText()
                    + " Profissional: " + lblRepositNomeMedicoAgenda.getText()
                    + " Paciente: " + lblRepositPaciente.getText()
                    + " Sexo: " + lblSexo.getText()
                    + " Idade:" + lblRepositIdade.getText());

            historicoDTO.setDiagnosticoDto(
                    txtAreaDiagnostico.getText()
            );

            historicoDTO.setOrientacaoDto(
                    txtAreaPrescricao.getText()
            );
            historicoDTO.setNomeProfissionalDto(lblUsuarioLogado.getText());
            historicoDTO.setPerfilProfissionalDto(lblPerfil.getText());
            historicoDTO.setSereialHDDto(lblRepositorioHD.getText());

            lblLinhaInformativa.setBackground(Color.red);
            lblLinhaInformativa.setForeground(Color.WHITE);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Iniciando o processo de salvar registros na nuvem...");
            lblLinhaInformativa.setFont(f);

            //Salvando dados na tbHistórico
            historicoDAO.inserir(historicoDTO);

            lblLinhaInformativa.setBackground(Color.ORANGE);
            lblLinhaInformativa.setForeground(new Color(75, 0, 130));
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Salvo Histórico. Iniciando atualização CAIXA");

            //setando dados para atualizar CAIXA
            int codigoTabAgendamento = Integer.parseInt(lblIdAgendamentoReposit.getText());
            agendamentoDTO.setIdDto(codigoTabAgendamento);
            agendamentoDTO.setDtRegistroDto(lblStatusData.getText());
            String status = "CAIXA";
            agendamentoDTO.setStatusAgendamentoDto(status);
            agendamentoDAO.atualizarCaixa(agendamentoDTO);

            btnRefresh.requestFocus();
            dispose();

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }


    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        int flagBtn = 4;
        progressBar(flagBtn);
    }//GEN-LAST:event_saveActionPerformed

    private void txtAreaDiagnosticoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaDiagnosticoFocusGained


    }//GEN-LAST:event_txtAreaDiagnosticoFocusGained

    private void txtAreaPrescricaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaPrescricaoFocusGained

    }//GEN-LAST:event_txtAreaPrescricaoFocusGained

    private void btnConsultarListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarListaActionPerformed
        int flagBtn = 6;
        progressBar(flagBtn);

    }//GEN-LAST:event_btnConsultarListaActionPerformed

    private void txtAreaDiagnosticoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAreaDiagnosticoMouseClicked
        lblLinhaInformativa.setForeground(Color.WHITE);
        lblLinhaInformativa.setBackground(Color.red);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Área de Diagnóstico. Abrindo o Editor Automaticamente...");

        btnDiagnostico.requestFocus();
        btnDiagnostico.setBackground(Color.white);
        btnDiagnostico.setForeground(new Color(75, 0, 130));

        int flagBtn = 5;
        progressBar(flagBtn);
    }//GEN-LAST:event_txtAreaDiagnosticoMouseClicked

    private void txtAreaPrescricaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAreaPrescricaoMouseClicked

        lblLinhaInformativa.setForeground(Color.WHITE);
        lblLinhaInformativa.setBackground(Color.red);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Área de Prescrição. Abrindo o Editor Automaticamente...");

        btnEditorPrescricao.requestFocus();
        btnEditorPrescricao.setBackground(Color.white);
        btnEditorPrescricao.setForeground(new Color(75, 0, 130));

        int flagBtn = 2;
        progressBar(flagBtn);

    }//GEN-LAST:event_txtAreaPrescricaoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgresso;
    public static javax.swing.JButton btnConsultarLista;
    private javax.swing.JButton btnDiagnostico;
    public static javax.swing.JButton btnEditorPrescricao;
    public static javax.swing.JButton btnReceituario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDiagnostico;
    private javax.swing.JLabel lblDtNascimento;
    private javax.swing.JLabel lblHD;
    public static javax.swing.JLabel lblIdAgendamentoReposit;
    public static javax.swing.JLabel lblIdMedicoAgenda;
    private javax.swing.JLabel lblIdOrientacao;
    private javax.swing.JTextField lblLinhaInformativa;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblNomePaciente;
    public static javax.swing.JLabel lblRepositDtNascimento;
    private javax.swing.JLabel lblRepositHD;
    public static javax.swing.JLabel lblRepositIdPaciente;
    private javax.swing.JLabel lblRepositIdade;
    public static javax.swing.JLabel lblRepositNomeMedicoAgenda;
    public static javax.swing.JLabel lblRepositPaciente;
    public static javax.swing.JLabel lblRepositSexo;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JPanel painelAutenticacao;
    private javax.swing.JPanel painelBarraDiagnostico;
    private javax.swing.JPanel painelBarraPrescricao;
    private javax.swing.JPanel painelLinhaInformativa;
    private javax.swing.JPanel painelPaciente;
    private javax.swing.JPanel painelPrincipal;
    private javax.swing.JPanel painelSimulador;
    private javax.swing.JPanel painelSobreMedico;
    public static javax.swing.JButton save;
    public static javax.swing.JTextArea txtAreaDiagnostico;
    public static javax.swing.JTextArea txtAreaPrescricao;
    // End of variables declaration//GEN-END:variables

}
