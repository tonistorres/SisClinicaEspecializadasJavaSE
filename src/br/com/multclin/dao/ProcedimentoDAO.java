package br.com.multclin.dao;

import br.com.multclin.dto.ProcedimentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author DaTorres
 */
public class ProcedimentoDAO implements GenericDAO<ProcedimentoDTO> {

    @Override
    public List<ProcedimentoDTO> listarTodos() throws PersistenciaException {

        List<ProcedimentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbProcedimentos";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();

                procedimentoDTO.setIdDto(resultSet.getInt("id"));
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsMedicoDto(resultSet.getDouble("RSMedico"));

                lista.add(procedimentoDTO);

            }

            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

     public List<ProcedimentoDTO> filtrarPassandoUmParameter(String pesquisar) throws PersistenciaException {

        List<ProcedimentoDTO> lista = new ArrayList<ProcedimentoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbProcedimentos WHERE nome LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();
                procedimentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                procedimentoDTO.setIdDto(resultSet.getInt("id"));
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsMedicoDto(resultSet.getDouble("RSMedico"));
                lista.add(procedimentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }
    
    
    
    public List<ProcedimentoDTO> filtrarPesqRapida(String pesquisar,int codigoEspe) throws PersistenciaException {

        List<ProcedimentoDTO> lista = new ArrayList<ProcedimentoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbProcedimentos WHERE nome LIKE '%" + pesquisar + "%' AND id="+codigoEspe;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();
                procedimentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                procedimentoDTO.setIdDto(resultSet.getInt("id"));
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsMedicoDto(resultSet.getDouble("RSMedico"));
                lista.add(procedimentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }

    public List<ProcedimentoDTO> filtrarComDoisParametrosIdAutoID(int codigoAuto, int codigoEsp) throws PersistenciaException {

        List<ProcedimentoDTO> lista = new ArrayList<ProcedimentoDTO>();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbProcedimentos WHERE idAuto=" + codigoAuto + " AND id=" + codigoEsp;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();

                procedimentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                procedimentoDTO.setIdDto(resultSet.getInt("id"));
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsMedicoDto(resultSet.getDouble("RSMedico"));
                lista.add(procedimentoDTO);

            }

            connection.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Camada DAO filtarPorID:\n" + e.getMessage());
            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);

        }

        return lista;
    }

    public List<ProcedimentoDTO> filtrarParametroID(int codigo) throws PersistenciaException {

        List<ProcedimentoDTO> lista = new ArrayList<ProcedimentoDTO>();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbProcedimentos where id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();

                procedimentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                procedimentoDTO.setIdDto(resultSet.getInt("id"));
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsMedicoDto(resultSet.getDouble("RSMedico"));
                lista.add(procedimentoDTO);

            }

            connection.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Camada DAO filtarPorID:\n" + e.getMessage());
            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);

        }

        return lista;
    }

    public ProcedimentoDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        ProcedimentoDTO procedimentoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbProcedimentos where idAuto=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                procedimentoDTO = new ProcedimentoDTO();
                procedimentoDTO.setIdDto(resultSet.getInt("id"));
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsMedicoDto(resultSet.getDouble("RSMedico"));
                return procedimentoDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    private void erroMsg(String mensagemErro) {

        String meuEmail = "sisprotocoloj@gmail.com";
        String minhaSenha = "gerlande2111791020";

        //agora iremos utilizar os objetos das Bibliotecas referente Email
        //commons-email-1.5.jar e mail-1.4.7.jar
        SimpleEmail email = new SimpleEmail();
        //Agora podemos utilizar o objeto email do Tipo SimplesEmail
        //a primeira cois a fazer ?? acessar o host abaixo estarei usando
        //o host do google para enviar as informa????es
        email.setHostName("smtp.gmail.com");
        //O proximo m??todo abaixo ?? para configurar a porta desse host
        email.setSmtpPort(465);
        //esse m??todo vai autenticar a conex??o o envio desse email
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        //esse m??todo vai ativar a conexao de forma segura 
        email.setSSLOnConnect(true);

        //agora um try porque os outro m??todos podem gerar erros da?? devem est?? dentro de um bloco de exce????es
        try {
            //configura de quem ?? esse email de onde ele est?? vindo 
            email.setFrom(meuEmail);
            //configurar o assunto 
            email.setSubject("Erro Camada GUI - Metodo:SalvarAdicoesEdicoes()");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinat??rio)
            email.addTo(meuEmail);
            //tendo tudo configurado agora ?? enviar o email 
            email.send();

            //enviando mensagem de confirma????o Usu??rio
            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Todos Procedimentos conclu??dos com Sucesso."
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro ao Enviar Email." + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            e.printStackTrace();
        }

    }

    @Override
    public void inserir(ProcedimentoDTO procedimentoDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "INSERT INTO tbProcedimentos(id,nome,RSBruto,RSClinica,RSMedico,cadastro) VALUES(?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, procedimentoDTO.getIdDto());//id
            statement.setString(2, procedimentoDTO.getNomeDto());//nome
            statement.setDouble(3, procedimentoDTO.getRsBrutoDto());//rsBruto
            statement.setDouble(4, procedimentoDTO.getRsClinicaDto());//rsClininca
            statement.setDouble(5, procedimentoDTO.getRsMedicoDto());//rsMedico
            statement.setDate(6, null);

            statement.execute();
            // important??ssimo fechar sempre a conex??o
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(ProcedimentoDTO procedimentoDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            //   JOptionPane.showMessageDialog(null, "DAO id:" + procedimentoDTO.getIdAutoDto());
            String sql = "UPDATE tbProcedimentos SET nome=?, RSBruto=?,RSClinica=?,RSMedico=? WHERE idAuto=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, procedimentoDTO.getNomeDto());//nome
            statement.setDouble(2, procedimentoDTO.getRsBrutoDto());//nome
            statement.setDouble(3, procedimentoDTO.getRsClinicaDto());//nome
            statement.setDouble(4, procedimentoDTO.getRsMedicoDto());//nome
            statement.setInt(5, procedimentoDTO.getIdAutoDto());

            statement.execute();
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deletar(ProcedimentoDTO obj) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "DELETE FROM tbProcedimentos WHERE idAuto=?";

            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setInt(1, obj.getIdDto());
            statement.execute();

            connection.close();

        } catch (Exception ex) {

            //   ex.printStackTrace();
            if (ex.getMessage().equals("Cannot delete or update a parent row: a foreign key constraint fails (`inovec87_sishanna`.`tbItemAgendamento`, CONSTRAINT `fk_tbItemAgendamento_tbProcedimentos1` FOREIGN KEY (`idProcede`) REFERENCES `tbProcedimentos` (`idAuto`) ON DELETE NO ACTION ON UPDATE N)")) {

                JOptionPane.showMessageDialog(null, "" + "\n Info:\n"
                        + "O   Procedimento   que   est??  tentando   executar,\n"
                        + "n??o poder?? ocorrer  devido  a   um   relacionamento\n"
                        + "entre as Entidades Procedimento e Item do Agendamento,\n"
                        + "[Procedimento]<---Relacionamento---> [Item Agendamento].\n"
                        + "O  Banco  de  Dados   est??  fortemente  relacionado.\n"
                        + "De forma que um registro n??o  pode ser exclu??do quando\n"
                        + "outro depende do seu v??nculo."
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

        }
    }

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProcedimentoDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProcedimentoDTO buscarPor(ProcedimentoDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(ProcedimentoDTO procedimentoDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * far?? a verifica????o se o registro foi encontrado ou n??o.Valor
         * encontrado retorna nomeDuplicado=true(Verdadeiro). Caso contr??rio
         * nomeDuplicado=false(continua com o valor atribuido no inicio do
         * c??digo por meio da flag)
         */

        boolean nomeDuplicado = false;

        try {
            //criando conex??o utilizando padr??o de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que ir?? fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbProcedimentos where nome='" + procedimentoDTO.getNomeDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                nomeDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado j?? se encontra cadastrado");
                return nomeDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return nomeDuplicado;

    }

    @Override
    public ProcedimentoDTO filtrarAoClicar(ProcedimentoDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso n??o encontre o m??dico buscado no banco
         * ele retorna nulo, ou seja, n??o foi encontrado no banco o m??dico
         * buscado
         */
        ProcedimentoDTO procedimentoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbProcedimentos WHERE nome LIKE'%" + modelo.getNomeDto() + "%'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco ir?? trazer no m??ximo
             * um dado em vez de fazer um la??o de repeti????o faremos um if
             */

            if (resultSet.next()) {

                /*
                 * Dentro do loop n??o se esquecer de inicializar o objeto como abaixo, caso
                 * contr??rio erro de exception
                 */
                procedimentoDTO = new ProcedimentoDTO();

                procedimentoDTO.setIdDto(resultSet.getInt("id"));
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));

                return procedimentoDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro M??todo filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    public List<ProcedimentoDTO> listarTodosParametroID(int codigo) throws PersistenciaException {

        List<ProcedimentoDTO> lista = new ArrayList<ProcedimentoDTO>();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbProcedimentos where id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                ProcedimentoDTO procedimentoDTO = new ProcedimentoDTO();

                procedimentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                procedimentoDTO.setIdDto(resultSet.getInt("id"));
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsMedicoDto(resultSet.getDouble("RSMedico"));

                lista.add(procedimentoDTO);

            }

            connection.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Camada DAO filtarPorID:\n" + e.getMessage());
            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);

        }

        return lista;
    }

    @Override
    public boolean inserirControlEmail(ProcedimentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(ProcedimentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(ProcedimentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(ProcedimentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProcedimentoDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.  
    }

}
