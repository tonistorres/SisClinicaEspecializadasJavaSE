package br.com.multclin.telas;

import br.com.multclin.bo.MedicoBO;
import br.com.multclin.bo.MedicoBO;

import br.com.multclin.dao.GeraCodigoAutomaticoDAO;
import br.com.multclin.dao.MedicoDAO;
//import br.com.multclin.componentes.ComponentesFormTelaUsuario;
//import br.com.multclin.dao.SetorTramiteInternoDAO;
import br.com.multclin.dao.MedicoDAO;
import br.com.multclin.dto.MedicoDTO;
//import br.com.multclin.dto.ItensDoProtocoloTFDDTO;
//import br.com.multclin.dto.SetorTramiteInternoDTO;
import br.com.multclin.dto.MedicoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import static br.com.multclin.telas.TelaPrincipal.DeskTop;
import static br.com.multclin.telas.TelaPrincipal.lblPerfil;
import static br.com.multclin.telas.TelaPrincipal.lblStatusData;
import br.com.multclin.util.JtextFieldSomenteLetras;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author DaTorres
 */
public class TelaMedico extends javax.swing.JInternalFrame {

    SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
    MedicoDTO medicoDTO = new MedicoDTO();
    MedicoDAO medicoDAO = new MedicoDAO();
    MedicoBO medicoBO = new MedicoBO();
    br.com.multclin.telas.SubTelaEspecialidades formEspecialidade;
    GeraCodigoAutomaticoDAO geraCodigoDAO = new GeraCodigoAutomaticoDAO();

    Font f = new Font("Tahoma", Font.BOLD, 9);

    int flag = 0;

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
    private static TelaMedico instance = null;

    public static TelaMedico getInstance() {

        if (instance == null) {

            instance = new TelaMedico();

        }

        return instance;
    }

    public static boolean isOpen() {

        return instance != null;
    }

    public TelaMedico() {
        initComponents();
        aoCarregarForm();
        personalizarFrontEnd();
        addRowJTable();

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

    private String dataNascimentoTratada(String str) {
        String strRecebe = str;
        String strRetorna = strRecebe.replaceAll("/", "");

        System.out.println("Palavra era:   [" + str + "]");
        System.out.println("Palavra ficou: [" + strRetorna + "]");

        return strRetorna;
    }

    //houve alteração no campo DATA para jDateChooser
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
            String DataDeNascimento = formatador.format(txtDataNascimento.getDate());

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

//                    this.cbSexo.setEnabled(true);
//                    this.cbEstaCivil.setEnabled(true);
//                    this.txtGmail.setEnabled(true);
//                    this.cbUF.setEnabled(true);
//                    this.txtCidade.setEnabled(true);
//                    this.txtBairro.setEnabled(true);
//                    this.txtRua.setEnabled(true);
//                    this.txtComplemento.setEnabled(true);
//                    this.txtNCasa.setEnabled(true);
//                    this.txtFormatedCEP.setEnabled(true);
//                    this.txtFormatedFoneFixo.setEnabled(true);
//                    this.txtFormatedCelular.setEnabled(true);
//                    btnSalvar.setEnabled(true);
//                    
                    this.txtFormatedConselho.setEnabled(true);

                    txtFormatedConselho.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                            + "O Sistema não Aceita uma passoa com mais de 150 anos.\n"
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtDataNascimento.requestFocus();

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
        int numeroMax = geraCodigoDAO.gerarCodigoMedico();
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
        this.btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/cancelar.png")));
        this.btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/salvar.png")));
        this.btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/editar.png")));
        this.btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/excluir.png")));
        this.btnValidaCPF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/validation.png")));
        this.lblNuvemForms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/nuvemForms.png")));
        this.btnCalulaData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/botoes/forms/calculator.png")));
    }

    private void desbilitarBotoesCarregamento() {

        //botoes padroes     
        this.btnExcluir.setEnabled(false);
        this.btnCancelar.setEnabled(false);
        //outros
        this.btnValidaCPF.setEnabled(false);

    }

    public void aoCarregarForm() {
        desabilitarCampos();
        desbilitarBotoesCarregamento();
        lblEmail.setEnabled(true);

        btnCalulaData.setEnabled(false);
        cbUF.setSelectedItem("MA");
        txtFormatedConselho.setEnabled(false);
        progressBarraPesquisa.setIndeterminate(true);
    }

    private void verificarCombos() {

        if (cbEstaCivil.getSelectedItem().equals("Selecione...")) {
            cbEstaCivil.setBackground(Color.YELLOW);
        }
        if (cbSexo.getSelectedItem().equals("Selecione...")) {
            cbSexo.setBackground(Color.YELLOW);
        }

    }

    public void addRowJTable() {

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<MedicoDTO> list;

        try {

            JButton btnEspecialidades = new JButton("Especialidade");
            JButton btnEditar = new JButton("Editar");
            btnEspecialidades.setName("ES");
            btnEditar.setName("ED");
            list = (ArrayList<MedicoDTO>) medicoDAO.listarTodos();

            Object rowData[] = new Object[4];

            for (int i = 0; i < list.size(); i++) {

                rowData[0] = list.get(i).getIdDto();
                rowData[1] = list.get(i).getNomeDto();
                rowData[2] = btnEditar;
                rowData[3] = btnEspecialidades;

                model.addRow(rowData);
            }

            tabela.setModel(model);

            /**
             * Coluna ID posição[0] vetor
             */
            tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
            tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(145);
            //coloca o cursor em modo de espera 

        } catch (PersistenciaException ex) {

            //coloca o cursor em modo de espera 
            JOptionPane.showMessageDialog(null, "Erro:Método addRowTable() FormUsuario \n"
                    + "Contactar Analista de Sistemas pelo Email: sisvenda2011@gmail.com\n"
                    + "Responsável pelo Projeto Tonis Alberto Torres Ferreira\n" + ex.getMessage());
        }

    }

    private void pesquisarUsuarioPorCPF() {
        String pesquisarUsuarioCPF = MetodoStaticosUtil.removerAcentosCaixAlta(txtFormatedPesquisaCPF.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<MedicoDTO> list;

        try {

            list = (ArrayList<MedicoDTO>) medicoDAO.filtrarPesqRapidaPorCPF(pesquisarUsuarioCPF);

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
        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void pesquisarUsuario() {
        String pesquisarUsuario = MetodoStaticosUtil.removerAcentosCaixAlta(txtPesquisa.getText());

        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        ArrayList<MedicoDTO> list;

        try {

            list = (ArrayList<MedicoDTO>) medicoDAO.filtrarPesqRapida(pesquisarUsuario);

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
        } catch (Exception e) {
            e.printStackTrace();
            // MensagensUtil.add(TelaUsuarios.this, e.getMessage());
        }

    }

    private void limparCampos() {

        this.txtID.setText("");
        this.txtCPF.setText("");
        this.txtNome.setText("");

            
       this.txtDataNascimento.setCalendar(null);
        
        this.txtIdade.setText("");
        this.cbSexo.setSelectedItem("Selecione...");
        this.cbEstaCivil.setSelectedItem("Selecione...");
        this.txtGmail.setText("");
        this.cbUF.setSelectedItem("Selecione...");
        this.txtCidade.setText("");
        this.txtBairro.setText("");
        this.txtRua.setText("");
        this.txtComplemento.setText("");
        this.txtNCasa.setText("");
        this.txtFormatedCEP.setText("");
        this.txtFormatedFoneFixo.setText("");
        this.txtFormatedCelular.setText("");
        this.txtFormatedConselho.setText("");

    }

    private void desabilitarCampos() {

        this.txtID.setEnabled(false);
        this.txtCPF.setEnabled(false);
        this.txtNome.setEnabled(false);
        this.txtDataNascimento.setEnabled(false);
        this.txtIdade.setEnabled(false);
        this.cbSexo.setEnabled(false);
        this.cbEstaCivil.setEnabled(false);
        this.txtGmail.setEnabled(false);
        this.cbUF.setEnabled(false);
        this.txtCidade.setEnabled(false);
        this.txtBairro.setEnabled(false);
        this.txtRua.setEnabled(false);
        this.txtComplemento.setEnabled(false);
        this.txtNCasa.setEnabled(false);
        this.txtFormatedCEP.setEnabled(false);
        this.txtFormatedFoneFixo.setEnabled(false);
        this.txtFormatedCelular.setEnabled(false);
        this.txtFormatedConselho.setEnabled(false);

        //Campo que ficarão Habilitados por padrão 
        this.txtPesquisa.setEnabled(true);

    }

    private void salvar() {

        //coloca o cursor em modo de espera 
        /**
         * capturando os campos do Form na Camada Gui e em vez de adicionar ha
         * uma variável encapsulamos e setamos como o método set
         */
        medicoDTO.setIdDto(Integer.parseInt(txtID.getText()));//id
        medicoDTO.setNomeDto(txtNome.getText());//nome
        medicoDTO.setCpfDto(txtCPF.getText());//cpf 

        String dataNascimento = formatador.format(txtDataNascimento.getDate());
        medicoDTO.setDataNascDto(dataNascimento);
        medicoDTO.setIdadeDto(txtIdade.getText());//idade

        if (!cbSexo.getSelectedItem().equals("Selecione...")) {
            medicoDTO.setSexoDto((String) cbSexo.getSelectedItem());//sexo 
        }

        if (!cbEstaCivil.getSelectedItem().equals("Selecione...")) {
            medicoDTO.setEstadoCivilDto((String) cbEstaCivil.getSelectedItem());//estado

        }

        medicoDTO.setEmailPrefDto(txtGmail.getText());

        if (!cbUF.getSelectedItem().equals("Selecione...")) {
            medicoDTO.setUfDto((String) cbUF.getSelectedItem());//UF Estado

        }

        medicoDTO.setCidadeDto(txtCidade.getText());
        medicoDTO.setBairroDto(txtBairro.getText());
        medicoDTO.setRuaDto(txtRua.getText());
        medicoDTO.setComplementoDto(txtComplemento.getText());
        medicoDTO.setnCasaDto(txtNCasa.getText());
        medicoDTO.setCEPDto(txtFormatedCEP.getText());
        medicoDTO.setFoneFixoDto(txtFormatedFoneFixo.getText());
        medicoDTO.setCelularPrefDto(txtFormatedCelular.getText());
        medicoDTO.setConselhoDto(txtFormatedConselho.getText());

        try {

            medicoBO = new MedicoBO();

            if ((medicoBO.validaCamposForm(medicoDTO)) == false) {
                txtNome.setText("");

            } else {

                if ((flag == 1)) {

                    boolean retornoVerifcaDuplicidade = medicoDAO.verificaDuplicidade(medicoDTO);

                    if (retornoVerifcaDuplicidade == false) {

                        medicoBO.cadastrar(medicoDTO);
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

                        lblLinhaInformativa.setText("Resgistro Cadastrado com Sucesso");
                        Font f = new Font("Tahoma", Font.BOLD, 12);//label informativo 
                        lblLinhaInformativa.setFont(f);
                        lblLinhaInformativa.setForeground(Color.getHSBColor(51, 153, 255));

                        //tira o cursor em modo de espera 
//                        JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
//                                + "Resgistro Salvo Com Sucesso.\n"
//                                + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
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

                        lblLinhaInformativa.setText("Verificação efetuada, Login já cadastrado no Sistema");
                        lblLinhaInformativa.setForeground(Color.RED);

                    }

                } else {
                    medicoDTO.setIdDto(Integer.parseInt(txtID.getText()));
                    medicoBO.atualizarBO(medicoDTO);
                    limparCampos();
                    desabilitarCampos();
                    acoesDosBotoesAposSalvarExcluir();
                    //tira o cursor em modo de espera 

//
//                    JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
//                            + "Resgistro Editado Com Sucesso.\n"
//                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
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

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Info: " + e.getMessage());

            if (e.getMessage().equalsIgnoreCase("Campo NOME Obrigatório")) {
                txtNome.requestFocus();
                txtNome.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo CPF Obrigatório")) {
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.YELLOW);
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
                txtCidade.setBackground(Color.YELLOW);
            }

            if (e.getMessage().equalsIgnoreCase("Campo Conselho Obrigatório")) {
                txtFormatedConselho.requestFocus();
                txtFormatedConselho.setBackground(Color.YELLOW);
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
        lblNome = new javax.swing.JLabel();
        lblDtNascimento = new javax.swing.JLabel();
        lblIdade = new javax.swing.JLabel();
        txtNome =  new JtextFieldSomenteLetras(45);
        PanelJTable = new javax.swing.JPanel();
        lblLinhaInformativa = new javax.swing.JLabel();
        cbSexo = new javax.swing.JComboBox();
        lblSexo = new javax.swing.JLabel();
        painelObrigatorio = new javax.swing.JPanel();
        btnValidaCPF = new javax.swing.JButton();
        txtCPF = new javax.swing.JFormattedTextField();
        lblCPFUsuario = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        lblIDCustomizado = new javax.swing.JLabel();
        txtIdade = new javax.swing.JTextField();
        btnCalulaData = new javax.swing.JButton();
        lblEstadoUsuario = new javax.swing.JLabel();
        cbEstaCivil = new javax.swing.JComboBox();
        lblUF = new javax.swing.JLabel();
        cbUF = new javax.swing.JComboBox();
        lblCidade = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lblRua = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        lblComplemento = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        lblNCasa = new javax.swing.JLabel();
        lblCEP = new javax.swing.JLabel();
        txtFormatedCEP = new javax.swing.JFormattedTextField();
        txtNCasa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtFormatedConselho = new javax.swing.JFormattedTextField();
        txtDataNascimento = new com.toedter.calendar.JDateChooser();
        PanelBotoesManipulacaoBancoDados = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnAdicionar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        lblNuvemForms = new javax.swing.JLabel();
        progressBarraPesquisa = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        lblPesNome = new javax.swing.JLabel();
        txtFormatedPesquisaCPF = new javax.swing.JFormattedTextField();
        btnPesquisaCPF = new javax.swing.JButton();
        lblCPF = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelContato = new javax.swing.JPanel();
        txtFormatedFoneFixo = new javax.swing.JFormattedTextField();
        txtFormatedCelular = new javax.swing.JFormattedTextField();
        lblFoneFixo = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtGmail = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Médico");
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

        lblNome.setForeground(new java.awt.Color(0, 102, 102));
        lblNome.setText("Médico:");
        PanelDadosUsuario.add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        lblDtNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lblDtNascimento.setText("Dt. Nasc.:");
        PanelDadosUsuario.add(lblDtNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 145, -1, -1));

        lblIdade.setForeground(new java.awt.Color(0, 102, 102));
        lblIdade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIdade.setText("Idade:");
        PanelDadosUsuario.add(lblIdade, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 145, 50, -1));

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
        PanelDadosUsuario.add(txtNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 115, 330, 30));

        PanelJTable.setLayout(new java.awt.GridLayout(1, 0));
        PanelDadosUsuario.add(PanelJTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 164, 481, -1));

        lblLinhaInformativa.setForeground(new java.awt.Color(0, 102, 102));
        lblLinhaInformativa.setText("Linha Informativa");
        PanelDadosUsuario.add(lblLinhaInformativa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 20));

        cbSexo.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        cbSexo.setForeground(new java.awt.Color(0, 102, 102));
        cbSexo.setMaximumRowCount(9);
        cbSexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "MASCULINO", "FEMININO", "OUTROS" }));
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
        PanelDadosUsuario.add(cbSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 205, 100, 30));

        lblSexo.setForeground(new java.awt.Color(0, 102, 102));
        lblSexo.setText("Sexo:");
        PanelDadosUsuario.add(lblSexo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 50, -1));

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
        painelObrigatorio.add(btnValidaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        try {
            txtCPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCPF.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCPFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCPFFocusLost(evt);
            }
        });
        txtCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPFActionPerformed(evt);
            }
        });
        txtCPF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCPFKeyPressed(evt);
            }
        });
        painelObrigatorio.add(txtCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 140, 30));

        lblCPFUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCPFUsuario.setForeground(java.awt.Color.white);
        lblCPFUsuario.setText("CPF:");
        painelObrigatorio.add(lblCPFUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

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

        PanelDadosUsuario.add(painelObrigatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 330, 60));

        txtIdade.setPreferredSize(new java.awt.Dimension(6, 30));
        PanelDadosUsuario.add(txtIdade, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 40, -1));

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
        PanelDadosUsuario.add(btnCalulaData, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 160, 30, 30));

        lblEstadoUsuario.setForeground(new java.awt.Color(0, 102, 102));
        lblEstadoUsuario.setText("Civil:");
        PanelDadosUsuario.add(lblEstadoUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));

        cbEstaCivil.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbEstaCivil.setForeground(new java.awt.Color(0, 102, 102));
        cbEstaCivil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "SOLTEIRO(A)", "CASADO(A)", "VIUVO(A)", "OUTROS" }));
        cbEstaCivil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cbEstaCivilFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbEstaCivilFocusLost(evt);
            }
        });
        cbEstaCivil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbEstaCivilKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(cbEstaCivil, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 205, 110, 30));

        lblUF.setForeground(new java.awt.Color(0, 102, 102));
        lblUF.setText("UF:");
        PanelDadosUsuario.add(lblUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, -1, -1));

        cbUF.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbUF.setForeground(new java.awt.Color(0, 102, 102));
        cbUF.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", " " }));
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
        PanelDadosUsuario.add(cbUF, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 205, 97, -1));

        lblCidade.setForeground(new java.awt.Color(0, 102, 102));
        lblCidade.setText("Cidade:");
        PanelDadosUsuario.add(lblCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        txtCidade.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtCidade.setForeground(new java.awt.Color(0, 102, 102));
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
        PanelDadosUsuario.add(txtCidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 255, 160, -1));

        lblBairro.setForeground(new java.awt.Color(0, 102, 102));
        lblBairro.setText("Bairro:");
        PanelDadosUsuario.add(lblBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 40, -1));

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
        PanelDadosUsuario.add(txtBairro, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 255, 160, -1));

        lblRua.setForeground(new java.awt.Color(0, 102, 102));
        lblRua.setText("Rua:");
        PanelDadosUsuario.add(lblRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

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
        PanelDadosUsuario.add(txtRua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 305, 330, -1));

        lblComplemento.setForeground(new java.awt.Color(0, 102, 102));
        lblComplemento.setText("Complemento:");
        PanelDadosUsuario.add(lblComplemento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 335, -1, -1));

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
        PanelDadosUsuario.add(txtComplemento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 160, 30));

        lblNCasa.setForeground(new java.awt.Color(0, 102, 102));
        lblNCasa.setText("NºCasa:");
        PanelDadosUsuario.add(lblNCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 335, -1, -1));

        lblCEP.setForeground(new java.awt.Color(0, 102, 102));
        lblCEP.setText("CEP:");
        PanelDadosUsuario.add(lblCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 335, -1, -1));

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
        PanelDadosUsuario.add(txtFormatedCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 350, 100, -1));

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
        PanelDadosUsuario.add(txtNCasa, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 50, -1));

        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Conselho:");
        PanelDadosUsuario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 145, -1, -1));

        txtFormatedConselho.setMinimumSize(new java.awt.Dimension(6, 30));
        txtFormatedConselho.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFormatedConselhoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFormatedConselhoFocusLost(evt);
            }
        });
        txtFormatedConselho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFormatedConselhoKeyPressed(evt);
            }
        });
        PanelDadosUsuario.add(txtFormatedConselho, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 120, 30));

        txtDataNascimento.setFont(new java.awt.Font("Tahoma", 0, 9)); // NOI18N
        PanelDadosUsuario.add(txtDataNascimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 125, 30));

        getContentPane().add(PanelDadosUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 52, 360, 390));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNuvemForms, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuvemForms, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelBotoesManipulacaoBancoDadosLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(progressBarraPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        getContentPane().add(PanelBotoesManipulacaoBancoDados, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisa Rápida:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(txtPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 140, 35));

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
        jPanel1.add(btnPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 32, 32));

        lblPesNome.setBackground(new java.awt.Color(0, 102, 102));
        lblPesNome.setForeground(new java.awt.Color(0, 102, 102));
        lblPesNome.setText("Nome:");
        jPanel1.add(lblPesNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

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
        jPanel1.add(txtFormatedPesquisaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 100, 30));

        btnPesquisaCPF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnPesquisaCPFFocusGained(evt);
            }
        });
        jPanel1.add(btnPesquisaCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 30, 30));

        lblCPF.setForeground(new java.awt.Color(0, 102, 102));
        lblCPF.setText("CPF:");
        jPanel1.add(lblCPF, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 360, 90));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME", "", ""
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
            tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
            tabela.getColumnModel().getColumn(2).setResizable(false);
            tabela.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabela.getColumnModel().getColumn(3).setResizable(false);
            tabela.getColumnModel().getColumn(3).setPreferredWidth(145);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 230, 370, 210));

        painelContato.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contato:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 102))); // NOI18N
        painelContato.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtFormatedFoneFixo.setForeground(new java.awt.Color(0, 102, 102));
        try {
            txtFormatedFoneFixo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFormatedFoneFixo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFormatedFoneFixoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFormatedFoneFixoFocusLost(evt);
            }
        });
        txtFormatedFoneFixo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFormatedFoneFixoKeyPressed(evt);
            }
        });
        painelContato.add(txtFormatedFoneFixo, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 30, 156, 30));

        txtFormatedCelular.setForeground(new java.awt.Color(0, 102, 102));
        try {
            txtFormatedCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFormatedCelular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFormatedCelularFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFormatedCelularFocusLost(evt);
            }
        });
        txtFormatedCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFormatedCelularKeyPressed(evt);
            }
        });
        painelContato.add(txtFormatedCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 150, 30));

        lblFoneFixo.setForeground(new java.awt.Color(0, 102, 102));
        lblFoneFixo.setText("Fone Fixo:");
        painelContato.add(lblFoneFixo, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 15, -1, -1));

        lblCelular.setForeground(new java.awt.Color(0, 102, 102));
        lblCelular.setText("Celular:");
        painelContato.add(lblCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 15, -1, -1));

        lblEmail.setForeground(new java.awt.Color(0, 102, 102));
        lblEmail.setText("Email:");
        painelContato.add(lblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtGmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtGmail.setForeground(new java.awt.Color(0, 102, 102));
        txtGmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtGmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGmailFocusLost(evt);
            }
        });
        txtGmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtGmailKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGmailKeyTyped(evt);
            }
        });
        painelContato.add(txtGmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 320, 30));

        getContentPane().add(painelContato, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 10, 360, 120));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed

        if (!txtNome.getText().isEmpty()) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                //polindo dados Campo Usuário
                txtNome.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtNome.getText().trim()));

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
            lblLinhaInformativa.setText("Campo Nome: Máximo 44 caracteres");

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

        this.btnValidaCPF.setEnabled(false);
        this.btnEditar.setEnabled(false);
        this.btnExcluir.setEnabled(false);
        this.btnAdicionar.requestFocus();

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
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnSalvarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSalvarKeyPressed
        acaoBotaoSalvar();
    }//GEN-LAST:event_btnSalvarKeyPressed

    private void acaoBotaoEditar() {
        if (txtNome.equals("")) {

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Para que se possa EDITAR é necessário \n"
                    + "que haja um registro selecionado"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } else {
            /**
             * Quando clicar em Editar essa flag recebe o valor de 2
             */

            flag = 2;

            //habilitar campos
            this.txtNome.setEnabled(true);
            this.txtDataNascimento.setEnabled(true);
            this.cbSexo.setEnabled(true);
            this.cbEstaCivil.setEnabled(true);
            this.txtGmail.setEnabled(true);
            this.cbUF.setEnabled(true);
            this.txtCidade.setEnabled(true);
            this.txtBairro.setEnabled(true);
            this.txtRua.setEnabled(true);
            this.txtComplemento.setEnabled(true);
            this.txtNCasa.setEnabled(true);
            this.txtFormatedCEP.setEnabled(true);
            this.txtFormatedConselho.setEnabled(true);
            this.txtFormatedFoneFixo.setEnabled(true);
            this.txtFormatedCelular.setEnabled(true);

            /**
             * ao clicar em btnAdicionar fazemos com que btnSalvar fique
             * habilitado para um eventual salvamento
             */
            btnSalvar.setEnabled(true);
            btnCancelar.setEnabled(true);
            btnEditar.setEnabled(false);
            btnAdicionar.setEnabled(false);
            btnExcluir.setEnabled(false);
            btnCalulaData.setEnabled(true);

        }
    }

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

        if (lblPerfil.getText().equals("ANALISTA") || lblPerfil.getText().equals("ADMIN")) {
            acaoBotaoEditar();
        } else {
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Procure um Administrador para efetuar EDIÇÃO");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
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

        this.txtCPF.setEnabled(true);
        this.txtNome.setEnabled(true);
        this.txtDataNascimento.setEnabled(true);
        this.cbSexo.setEnabled(true);
        this.cbEstaCivil.setEnabled(true);
        this.txtGmail.setEnabled(true);
        this.cbUF.setEnabled(true);
        this.txtCidade.setEnabled(true);
        this.txtBairro.setEnabled(true);
        this.txtRua.setEnabled(true);
        this.txtComplemento.setEnabled(true);
        this.txtNCasa.setEnabled(true);
        this.txtFormatedCEP.setEnabled(true);
        this.txtFormatedCelular.setEnabled(true);
        this.txtFormatedFoneFixo.setEnabled(true);

    }

    private void acaoBotaoAdicionar() {

        flag = 1;

        //limpar campos
        this.txtID.setText("");//id
        this.txtCPF.setText("");//cpf
        this.txtNome.setText("");
        this.txtDataNascimento.setDate(null);//data de nascimento
        this.txtIdade.setText("");//idade
        this.cbSexo.setSelectedItem("Selecione...");//sexo
        this.cbEstaCivil.setSelectedItem("Selecione...");// estado civil
        this.cbUF.setSelectedItem("Selecione...");//uf
        this.txtCidade.setText("");//cidade
        this.txtBairro.setText("");//bairro
        this.txtRua.setText("");//rua
        this.txtComplemento.setText("");//complemento
        this.txtNCasa.setText("");//ncasa
        this.txtFormatedFoneFixo.setText("");
        this.txtFormatedCelular.setText("");
        this.txtGmail.setText("");//

        gerarCodigo();
        txtCPF.setEnabled(true);
        btnValidaCPF.setEnabled(true);
        btnCancelar.setEnabled(true);
        txtCPF.requestFocus();

        btnSalvar.setEnabled(true);
        btnAdicionar.setEnabled(false);

    }
    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        acaoBotaoAdicionar();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void acaoExcluirRegistro() {

        /*Evento ao ser clicado executa código*/
        int excluir = JOptionPane.showConfirmDialog(null, "Deseja Excluir Registro?", "Informação", JOptionPane.YES_NO_OPTION);

        if (excluir == JOptionPane.YES_OPTION) {
            medicoDTO.setIdDto(Integer.parseInt(txtID.getText()));
            /**
             * Chamando o método que irá executar a Edição dos Dados em nosso
             * Banco de Dados
             */
            medicoBO.ExcluirBO(medicoDTO);
            /**
             * Após salvar limpar os campos
             */

            limparCampos();
            desabilitarCampos();
            acoesDosBotoesAposSalvarExcluir();

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
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Somente Usuário do Tipo Administrador pode EXCLUIR");
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
        }


    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtPesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyPressed

        if (txtPesquisa.getText().equalsIgnoreCase("")) {

            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtPesquisa.requestFocus();
                txtPesquisa.setBackground(Color.YELLOW);
                txtPesquisa.setForeground(Color.BLACK);

                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("Digite o Registro a ser pesquisado [ENTER]");
                lblLinhaInformativa.setForeground(Color.red);
                lblLinhaInformativa.setFont(f);
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
                    JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
                            + "Sem Conectividade: Verifica \n a Conexao entre a aplicação e o Banco Hospedado "
                            + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                }
            }
        }
    }//GEN-LAST:event_txtPesquisaKeyPressed
//    private void acaoMouseClicked() {
//
//        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
//         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
//        int codigo = Integer.parseInt("" + tabela.getValueAt(tabela.getSelectedRow(), 0));
//        /**
//         * Esse código está comentado só para ficar o exemplo de como pegaria o
//         * valor de nome da tabela ou seja coluna 1 sendo que falando neses caso
//         * trabalhamos como vetor que inicial do zero(0)
//         */
//        /*   cidadeDTO.setNomeCidadeDto("" + tblCidadesList.getValueAt(tblCidadesList.getSelectedRow(), 1));*/
//
//        try {
//
//            MedicoDTO retorno = medicoDAO.buscarPorIdTblConsultaList(codigo);
//
//            if (retorno.getIdDto() != null || !retorno.getIdDto().equals("")) {
//                /**
//                 * String.valueOf() pega um Valor Inteiro e transforma em String
//                 * e seta em txtIDMedico que é um campo do tipo texto
//                 */
//
//                medicoDTO.setIdDto(codigo);
//                txtID.setText(String.valueOf(retorno.getIdDto())); //id
//                txtCPF.setText(retorno.getCpfDto());//cpf
//                txtNome.setText(retorno.getNomeDto());//nome
//                txtDataNascimento.setText(retorno.getDataNascDto());
//                txtIdade.setText(retorno.getIdadeDto());
//                cbSexo.setSelectedItem(retorno.getSexoDto());
//                cbEstaCivil.setSelectedItem(retorno.getEstadoCivilDto());
//                txtGmail.setText(retorno.getEmailPrefDto());
//                cbUF.setSelectedItem(retorno.getUfDto());
//                txtCidade.setText(retorno.getCidadeDto());
//                txtBairro.setText(retorno.getBairroDto());
//                txtRua.setText(retorno.getRuaDto());
//                txtComplemento.setText(retorno.getComplementoDto());
//                txtNCasa.setText(retorno.getnCasaDto());
//                txtFormatedCEP.setText(retorno.getCEPDto());
//                txtFormatedConselho.setText(retorno.getConselhoDto());
//                txtFormatedFoneFixo.setText(retorno.getFoneFixoDto());
//                txtFormatedCelular.setText(retorno.getCelularPrefDto());
//
//                /**
//                 * Liberar os botões abaixo
//                 */
//                btnEditar.setEnabled(true);
//                btnExcluir.setEnabled(true);
//                btnAdicionar.setEnabled(false);
//                btnCancelar.setEnabled(true);
//
//                /**
//                 * Habilitar Campos
//                 */
//                // habilitarCampos();
//                /**
//                 * Desabilitar campos
//                 */
//                //desabilitarCampos();
//            } else {
//
//                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
//                        + "Registro não foi encontrado."
//                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
//
//            }
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
//                    + ex.getMessage()
//                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
//
//        }
//    }

    private void acaoBotaoEditarTabela() {
        /*Utilizamos aqui o método encapsulado set para pegar o que o usuário digitou no campo txtPesquisa
         Youtube:https://www.youtube.com/watch?v=v1ERhLdmf98*/
        int codigo = Integer.parseInt("" + tabela.getValueAt(tabela.getSelectedRow(), 0));

        try {

            MedicoDTO retorno = medicoDAO.buscarPorIdTblConsultaList(codigo);

            if (retorno.getIdDto() != null || !retorno.getIdDto().equals("")) {

                medicoDTO.setIdDto(codigo);
                txtID.setText(String.valueOf(retorno.getIdDto())); //id
                txtCPF.setText(retorno.getCpfDto());//cpf
                txtNome.setText(retorno.getNomeDto());//nome
                //Convertendo as Datas de String para Date |String|--->>|Date|
                Date dataNascimento = formatador.parse(retorno.getDataNascDto());
                txtDataNascimento.setDate(dataNascimento);
                txtIdade.setText(retorno.getIdadeDto());
                cbSexo.setSelectedItem(retorno.getSexoDto());
                cbEstaCivil.setSelectedItem(retorno.getEstadoCivilDto());
                txtGmail.setText(retorno.getEmailPrefDto());
                cbUF.setSelectedItem(retorno.getUfDto());
                txtCidade.setText(retorno.getCidadeDto());
                txtBairro.setText(retorno.getBairroDto());
                txtRua.setText(retorno.getRuaDto());
                txtComplemento.setText(retorno.getComplementoDto());
                txtNCasa.setText(retorno.getnCasaDto());
                txtFormatedCEP.setText(retorno.getCEPDto());
                txtFormatedConselho.setText(retorno.getConselhoDto());
                txtFormatedFoneFixo.setText(retorno.getFoneFixoDto());
                txtFormatedCelular.setText(retorno.getCelularPrefDto());

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

        //https://www.youtube.com/watch?v=jPfKFm2Yfow
        int coluna = tabela.getColumnModel().getColumnIndexAtX(evt.getX());
        int linha = evt.getY() / tabela.getRowHeight();

        if (linha < tabela.getRowCount() && linha >= 0 && coluna < tabela.getColumnCount() && coluna >= 0) {
            Object value = tabela.getValueAt(linha, coluna);
            if (value instanceof JButton) {
                ((JButton) value).doClick();
                JButton boton = (JButton) value;

                if (boton.getName().equals("ES")) {
                    acaoBotaoEditarTabela();
                    if (estaFechado(formEspecialidade)) {
                        formEspecialidade = new SubTelaEspecialidades();
                        DeskTop.add(formEspecialidade).setLocation(350, 3);
                        formEspecialidade.setTitle("Especialidade Profissional");
                        formEspecialidade.setVisible(true);

                    } else {
                        formEspecialidade.toFront();
                        formEspecialidade.setVisible(true);

                    }

                }

                if (boton.getName().equals("ED")) {
                    acaoBotaoEditarTabela();
                }

            }

        }

//        ConexaoUtil conecta = new ConexaoUtil();
//        boolean recebeConexao = conecta.getInstance().ConexaoVerificaEstado();
//
//        if (recebeConexao == true) {
//            acaoMouseClicked();
//
//        } else {
//            JOptionPane.showMessageDialog(this, "" + "\n Camada GUI:\n"
//                    + "Sem Conectividade: \n Entre à APLICAÇÃO e o BANCO DE DADOS HOSPEDADO"
//                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
//
//        }

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

        lblLinhaInformativa.setText("Fim da Pesquisa.");
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(Color.ORANGE);

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
        txtNome.setBackground(new Color(0, 102, 102));
        txtNome.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe o Nome");
        lblLinhaInformativa.setFont(f);
    }//GEN-LAST:event_txtNomeFocusGained

    private void txtNomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusLost
        txtNome.setBackground(Color.WHITE);
        txtNome.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");

    }//GEN-LAST:event_txtNomeFocusLost

    private void txtCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusGained
        txtCPF.setBackground(Color.YELLOW);
        lblLinhaInformativa.setFont(f);
        lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o CPF");


    }//GEN-LAST:event_txtCPFFocusGained

    private void txtCPFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCPFFocusLost
        txtCPF.setBackground(Color.WHITE);
    }//GEN-LAST:event_txtCPFFocusLost

    private void txtCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPFActionPerformed

    private void txtCPFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPFKeyPressed

        if (!txtCPF.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                //this.btnValidaCPF.requestFocus();
                acaoValidaCPFPolindoDados();
            }
        }
        if (txtCPF.getText().equals("   .   .   -  ")) {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {
                txtCPF.requestFocus();
                JOptionPane.showMessageDialog(this, "" + "\n Obrigatório CPF.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            }
        }

    }//GEN-LAST:event_txtCPFKeyPressed

    private void btnValidaCPFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnValidaCPFFocusGained
        btnValidaCPF.setBackground(Color.YELLOW);
        this.lblLinhaInformativa.setFont(f);
        this.lblLinhaInformativa.setForeground(new Color(0, 102, 102));
        this.lblLinhaInformativa.setText("");
        this.lblLinhaInformativa.setText("Click sobre o [BOTÃO] ou Pressione [ENTER]");
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
        medicoDTO.setCpfDto(this.txtCPF.getText());

        try {
            //robo conectado ao servidor google 
            boolean retornoVerifcaDuplicidade = medicoDAO.verificaDuplicidadeCPF(medicoDTO);//verificar se já existe CNPJ

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
                for (int i = 0; i < medicoDTO.getCpfDto().length(); i++) {

                    cont += 1;//Aqui o contador começa a ser incrementado em mais um a cada passada 

                    //Quando o contador estiver na posicao 3 execute o codigo abaixo 
                    if (cont == 4) {

                        /**
                         * Se na posição 3 do campo txtCNPJ estiver um ponto
                         * substitua em todos os lugares da String que estiver
                         * com ponto por um estaço em branco
                         */
                        if (medicoDTO.getCpfDto().charAt(i) == '.') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             *
                             */
                            medicoDTO.setCpfDto(medicoDTO.getCpfDto().replace(medicoDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                    //Quando o contador estiver na posicao 5 execute o codigo abaixo 
                    if (cont == 12) {

                        /**
                         * Se na posição 16 do campo txtCNPJ estiver um traço
                         * [-] substitua em todos os lugares da String que
                         * estiver com ponto por um estaço em branco
                         */
                        if (medicoDTO.getCpfDto().charAt(i) == '-') {

                            /**
                             * o método replace efetua essa mudança de
                             * comportamento nesta String
                             */
                            medicoDTO.setCpfDto(medicoDTO.getCpfDto().replace(medicoDTO.getCpfDto().charAt(i), ' '));

                        }

                    }

                }
                /**
                 * Neste ponto criamos uma String nova chamada cnpjTratado e
                 * capturamos a string ja tratada e fazemos o último tratamento
                 * que é tirar todos os espaços embrancos da string tratada
                 */
                String cpfTratado = medicoDTO.getCpfDto().replace(" ", "");

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
                    txtCPF.setEditable(true);
                    txtCPF.setBackground(Color.WHITE);

                    lblLinhaInformativa.setText("");
                    lblLinhaInformativa.setFont(f);
                    lblLinhaInformativa.setText("Validado com Sucesso.");
                    lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                    txtNome.requestFocus();
                    txtCPF.setEnabled(false);
                    btnValidaCPF.setEnabled(false);

                } else {
                    JOptionPane.showMessageDialog(this, "" + "\n CPF Inválido.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                    txtCPF.setBackground(Color.YELLOW);
                    txtCPF.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(this, "" + "\n Registro Duplicado.", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                txtCPF.requestFocus();
                txtCPF.setBackground(Color.RED);

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
        txtNome.requestFocus();
        txtDataNascimento.setEnabled(true);
        btnCalulaData.setEnabled(true);

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
            txtCPF.requestFocus();
        }
    }//GEN-LAST:event_btnValidaCPFKeyPressed

    private void cbSexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSexoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSexoActionPerformed


    private void cbSexoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusGained
        cbSexo.setBackground(new Color(0, 102, 102));
        cbSexo.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Escolha o Sexo");
        lblLinhaInformativa.setFont(f);
    }//GEN-LAST:event_cbSexoFocusGained

    private void cbSexoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbSexoFocusLost
        cbSexo.setBackground(Color.WHITE);
        cbSexo.setForeground(Color.BLACK);

    }//GEN-LAST:event_cbSexoFocusLost

    private void txtGmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGmailFocusGained

        txtGmail.setBackground(new Color(0, 102, 102));
        txtGmail.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite o Email");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtGmailFocusGained

    private void txtGmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGmailFocusLost
        txtGmail.setBackground(Color.WHITE);
        txtGmail.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");
    }//GEN-LAST:event_txtGmailFocusLost

    private void cbSexoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbSexoKeyPressed

        if (!cbSexo.getSelectedItem().equals("Selecione...")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                cbEstaCivil.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                txtFormatedConselho.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_UP) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo Obrigatório.  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                cbEstaCivil.requestFocus();

            }

            if (evt.getKeyCode() == evt.VK_LEFT) {
                txtFormatedConselho.requestFocus();
            }

        }
    }//GEN-LAST:event_cbSexoKeyPressed

    private void cbEstaCivilFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEstaCivilFocusGained
        cbEstaCivil.setBackground(new Color(0, 102, 102));
        cbEstaCivil.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Escolha o Estado Civil");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_cbEstaCivilFocusGained

    private void cbEstaCivilFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbEstaCivilFocusLost
        cbEstaCivil.setBackground(Color.WHITE);
        cbEstaCivil.setForeground(Color.BLACK);

    }//GEN-LAST:event_cbEstaCivilFocusLost

    private void cbEstaCivilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbEstaCivilKeyPressed

        if (!cbEstaCivil.getSelectedItem().equals("Selecione...")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                cbUF.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                cbSexo.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_UP) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo Obrigatório\n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                cbEstaCivil.requestFocus();

            }

            if (evt.getKeyCode() == evt.VK_LEFT) {
                cbUF.requestFocus();
            }

        }
    }//GEN-LAST:event_cbEstaCivilKeyPressed

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        btnEditar.setBackground(new Color(0, 102, 102));

        this.lblLinhaInformativa.setText("");
        this.lblLinhaInformativa.setText("Edita o Registro Selecionado");

        this.lblLinhaInformativa.setFont(f);
        this.lblLinhaInformativa.setForeground(new Color(0, 102, 102));


    }//GEN-LAST:event_btnEditarFocusGained

    private void btnExcluirFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnExcluirFocusGained
        if ((lblPerfil.getText().equalsIgnoreCase("ANALISTA"))) {
            btnExcluir.setEnabled(true);
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(new Color(0, 102, 102));
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Liberado para Exclusão");
        } else {
            btnExcluir.setEnabled(false);
            lblLinhaInformativa.setFont(f);
            lblLinhaInformativa.setForeground(Color.red);
            lblLinhaInformativa.setText("");
            lblLinhaInformativa.setText("Perfil não possui autorização de Exclusão");

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

    private void txtDtNascAoReceberFoco() {
        txtDataNascimento.setBackground(new Color(0, 102, 102));
        txtDataNascimento.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe a Data Nascimento");
        lblLinhaInformativa.setFont(f);

        if (txtNome.getText().isEmpty()) {
            txtNome.requestFocus();
        }
    }


    private void btnCalulaDataFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCalulaDataFocusGained
        btnCalulaData.setBackground(new Color(0, 102, 102));
        btnCalulaData.setForeground(Color.WHITE);
        calculaIdade();
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Calcula Idade");
        lblLinhaInformativa.setFont(f);

    }//GEN-LAST:event_btnCalulaDataFocusGained

    private void btnCalulaDataFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnCalulaDataFocusLost
        btnCalulaData.setBackground(Color.WHITE);
        btnCalulaData.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");

    }//GEN-LAST:event_btnCalulaDataFocusLost

    private void txtGmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGmailKeyTyped

        int numeroDeCaracter = 49;

        if (txtGmail.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 49 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtGmail.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 49 caracteres");

        }


    }//GEN-LAST:event_txtGmailKeyTyped

    private void txtGmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGmailKeyPressed

        if (!txtGmail.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtGmail.setText(MetodoStaticosUtil.removerAcentosCaixAlta(txtGmail.getText().trim()));
                btnSalvar.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtFormatedCelular.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                btnSalvar.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtFormatedCelular.requestFocus();
            }
        }


    }//GEN-LAST:event_txtGmailKeyPressed

    private void cbUFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbUFFocusGained

        cbUF.setBackground(new Color(0, 102, 102));
        cbUF.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Escolha o Estado");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_cbUFFocusGained

    private void cbUFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbUFFocusLost
        cbUF.setBackground(Color.WHITE);
        cbUF.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_cbUFFocusLost

    private void cbUFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbUFKeyPressed

        if (!cbUF.getSelectedItem().equals("Selecione...")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtCidade.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {

                cbEstaCivil.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_UP) {
                JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                        + "Campo Obrigatório.  \n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
                cbUF.requestFocus();

            }

            if (evt.getKeyCode() == evt.VK_LEFT) {
                txtCidade.requestFocus();
            }

        }


    }//GEN-LAST:event_cbUFKeyPressed

    private void txtCidadeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCidadeFocusGained

        txtCidade.setBackground(new Color(0, 102, 102));
        txtCidade.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite Cidade");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtCidadeFocusGained

    private void txtCidadeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCidadeFocusLost

        txtCidade.setBackground(Color.WHITE);
        txtCidade.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");

    }//GEN-LAST:event_txtCidadeFocusLost

    private void txtCidadeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCidadeKeyTyped

        int numeroDeCaracter = 29;

        if (txtCidade.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 29 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtCidade.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 29 caracteres");

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

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite Bairro");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtBairroFocusGained

    private void txtBairroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBairroFocusLost

        txtBairro.setBackground(Color.WHITE);
        txtBairro.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");

    }//GEN-LAST:event_txtBairroFocusLost

    private void txtBairroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBairroKeyTyped

        int numeroDeCaracter = 29;

        if (txtBairro.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 29 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtBairro.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 29 caracteres");

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

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite Rua");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtRuaFocusGained

    private void txtRuaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRuaFocusLost

        txtRua.setBackground(Color.WHITE);
        txtRua.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_txtRuaFocusLost

    private void txtRuaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRuaKeyTyped

        int numeroDeCaracter = 29;

        if (txtRua.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 29 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtRua.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 29 caracteres");

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

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite Complemento");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtComplementoFocusGained

    private void txtComplementoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComplementoFocusLost

        txtComplemento.setBackground(Color.WHITE);
        txtComplemento.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_txtComplementoFocusLost

    private void txtComplementoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComplementoKeyTyped

        int numeroDeCaracter = 29;

        if (txtComplemento.getText().length() > numeroDeCaracter) {
            evt.consume();//dizemos para evento consuma, ou seja , execute

            JOptionPane.showMessageDialog(this, "" + "\n Informação:\n"
                    + "Máximo 29 caracteres"
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            txtComplemento.setBackground(new Color(0, 102, 102));//modificamos a cor do campo para visualmente indicar ao usuário o erro
            lblLinhaInformativa.setText("Campo Nome: Máximo 29 caracteres");

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

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite Nº Casa");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtNCasaFocusGained

    private void txtNCasaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNCasaFocusLost

        txtNCasa.setBackground(Color.WHITE);
        txtNCasa.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


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
            lblLinhaInformativa.setText("Campo Nome: Máximo 7 caracteres");

        }


    }//GEN-LAST:event_txtNCasaKeyTyped

    private void txtFormatedCEPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedCEPFocusGained

        txtFormatedCEP.setBackground(new Color(0, 102, 102));
        txtFormatedCEP.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite CEP");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtFormatedCEPFocusGained

    private void txtFormatedCEPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedCEPFocusLost

        txtFormatedCEP.setBackground(Color.WHITE);
        txtFormatedCEP.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_txtFormatedCEPFocusLost

    private void txtFormatedCEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedCEPKeyPressed

        if (!txtFormatedCEP.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtFormatedFoneFixo.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtNCasa.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtFormatedFoneFixo.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtNCasa.requestFocus();
            }
        }


    }//GEN-LAST:event_txtFormatedCEPKeyPressed

    private void btnEditarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusLost
        lblLinhaInformativa.setText("");

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

                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("Digite o Registro a ser pesquisado [ENTER]");
                lblLinhaInformativa.setForeground(Color.red);
                lblLinhaInformativa.setFont(f);
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
                lblLinhaInformativa.setText("");
                lblLinhaInformativa.setText("fazendo pesquisa no MYSQL");
                lblLinhaInformativa.setForeground(new Color(0, 102, 102));
                lblLinhaInformativa.setFont(f);

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


    private void txtFormatedConselhoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedConselhoFocusGained

        txtFormatedConselho.setBackground(new Color(0, 102, 102));
        txtFormatedConselho.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Informe a Sigla e o Número do Conselho do Profissional de Saúde");
        lblLinhaInformativa.setFont(f);


    }//GEN-LAST:event_txtFormatedConselhoFocusGained

    private void txtFormatedConselhoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedConselhoFocusLost

        txtFormatedConselho.setBackground(Color.WHITE);
        txtFormatedConselho.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");

    }//GEN-LAST:event_txtFormatedConselhoFocusLost

    private void txtFormatedConselhoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedConselhoKeyPressed

        if (!txtFormatedConselho.getText().isEmpty()) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {
                cbSexo.requestFocus();

                this.cbSexo.setEnabled(true);
                this.cbEstaCivil.setEnabled(true);
                this.txtGmail.setEnabled(true);
                this.cbUF.setEnabled(true);
                this.txtCidade.setEnabled(true);
                this.txtBairro.setEnabled(true);
                this.txtRua.setEnabled(true);
                this.txtComplemento.setEnabled(true);
                this.txtNCasa.setEnabled(true);
                this.txtFormatedCEP.setEnabled(true);
                this.txtFormatedCelular.setEnabled(true);
                this.txtFormatedFoneFixo.setEnabled(true);

            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtDataNascimento.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {
                JOptionPane.showMessageDialog(this, "Info:"
                        + "O campo [CONSELHO] é Obrigatório\n "
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

                txtFormatedConselho.requestFocus();
                txtFormatedConselho.setBackground(Color.YELLOW);
                txtFormatedConselho.setForeground(Color.BLACK);
                txtFormatedConselho.setFont(f);
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtDataNascimento.requestFocus();
            }
        }


    }//GEN-LAST:event_txtFormatedConselhoKeyPressed

    private void txtFormatedFoneFixoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedFoneFixoKeyPressed

        if (!txtFormatedFoneFixo.getText().equalsIgnoreCase("(  )    -    ")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtFormatedCelular.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtFormatedCEP.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtFormatedCelular.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtFormatedCEP.requestFocus();
            }
        }


    }//GEN-LAST:event_txtFormatedFoneFixoKeyPressed

    private void txtFormatedCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFormatedCelularKeyPressed

        if (!txtFormatedCelular.getText().equalsIgnoreCase("(  )    -    ")) {

            //Segundo campo campo se ENTES OU SETA PRA FRENTE OU  SETA PRA BAIXO 
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT || evt.getKeyCode() == evt.VK_DOWN) {

                txtGmail.requestFocus();
            }

            //Se seta pra cima ou seta a esqueda 
            if (evt.getKeyCode() == evt.VK_UP || evt.getKeyCode() == evt.VK_LEFT) {
                txtFormatedFoneFixo.requestFocus();
            }

        } else {
            if (evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_RIGHT) {

                txtGmail.requestFocus();
            }

            if (evt.getKeyCode() == evt.VK_LEFT || evt.getKeyCode() == evt.VK_UP) {
                txtFormatedFoneFixo.requestFocus();
            }
        }


    }//GEN-LAST:event_txtFormatedCelularKeyPressed

    private void txtFormatedFoneFixoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedFoneFixoFocusGained

        txtFormatedFoneFixo.setBackground(new Color(0, 102, 102));
        txtFormatedFoneFixo.setForeground(Color.WHITE);

        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite Fone Fixo");
        lblLinhaInformativa.setFont(f);
    }//GEN-LAST:event_txtFormatedFoneFixoFocusGained

    private void txtFormatedFoneFixoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedFoneFixoFocusLost

        txtFormatedFoneFixo.setBackground(Color.WHITE);
        txtFormatedFoneFixo.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");


    }//GEN-LAST:event_txtFormatedFoneFixoFocusLost

    private void txtFormatedCelularFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedCelularFocusGained

        txtFormatedCelular.setBackground(new Color(0, 102, 102));
        txtFormatedCelular.setForeground(Color.WHITE);
        lblLinhaInformativa.setText("");
        lblLinhaInformativa.setText("Digite Celular");
        lblLinhaInformativa.setFont(f);

    }//GEN-LAST:event_txtFormatedCelularFocusGained

    private void txtFormatedCelularFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFormatedCelularFocusLost

        txtFormatedCelular.setBackground(Color.WHITE);
        txtFormatedCelular.setForeground(Color.BLACK);
        lblLinhaInformativa.setText("");

    }//GEN-LAST:event_txtFormatedCelularFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotoesManipulacaoBancoDados;
    private javax.swing.JPanel PanelDadosUsuario;
    private javax.swing.JPanel PanelJTable;
    public static javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCalulaData;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnEditar;
    public static javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnPesquisaCPF;
    private javax.swing.JButton btnPesquisar;
    public static javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnValidaCPF;
    public static javax.swing.JComboBox cbEstaCivil;
    public static javax.swing.JComboBox cbSexo;
    public static javax.swing.JComboBox cbUF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCEP;
    private javax.swing.JLabel lblCPF;
    private javax.swing.JLabel lblCPFUsuario;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblDtNascimento;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEstadoUsuario;
    private javax.swing.JLabel lblFoneFixo;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIDCustomizado;
    private javax.swing.JLabel lblIdade;
    private javax.swing.JLabel lblLinhaInformativa;
    private javax.swing.JLabel lblNCasa;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNuvemForms;
    private javax.swing.JLabel lblPesNome;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblSexo;
    private javax.swing.JLabel lblUF;
    private javax.swing.JPanel painelContato;
    private javax.swing.JPanel painelObrigatorio;
    private javax.swing.JProgressBar progressBarraPesquisa;
    private javax.swing.JTable tabela;
    public static javax.swing.JTextField txtBairro;
    public static javax.swing.JFormattedTextField txtCPF;
    public static javax.swing.JTextField txtCidade;
    public static javax.swing.JTextField txtComplemento;
    private com.toedter.calendar.JDateChooser txtDataNascimento;
    public static javax.swing.JFormattedTextField txtFormatedCEP;
    public static javax.swing.JFormattedTextField txtFormatedCelular;
    public static javax.swing.JFormattedTextField txtFormatedConselho;
    public static javax.swing.JFormattedTextField txtFormatedFoneFixo;
    private javax.swing.JFormattedTextField txtFormatedPesquisaCPF;
    public static javax.swing.JTextField txtGmail;
    public static javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIdade;
    public static javax.swing.JTextField txtNCasa;
    public static javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisa;
    public static javax.swing.JTextField txtRua;
    // End of variables declaration//GEN-END:variables
}
