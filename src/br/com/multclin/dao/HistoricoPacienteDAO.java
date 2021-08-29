package br.com.multclin.dao;

import br.com.multclin.dto.HistoricoPacienteDTO;
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

public class HistoricoPacienteDAO implements GenericDAO<HistoricoPacienteDTO> {

    /**
     * Método Listar todos registro de uma tabela
     */
    @Override
    public List<HistoricoPacienteDTO> listarTodos() throws PersistenciaException {

        List<HistoricoPacienteDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbHistoricoPaciente order by dtHistorico asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                HistoricoPacienteDTO historicoPacienteDTO = new HistoricoPacienteDTO();

                historicoPacienteDTO.setIdGeradoDto(resultSet.getInt("idGerado"));
                historicoPacienteDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));
                historicoPacienteDTO.setFk_medicoDto(resultSet.getInt("fk_medico"));
                historicoPacienteDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                historicoPacienteDTO.setDtHistoricoDto(resultSet.getDate("dtHistorico"));
                historicoPacienteDTO.setDiagnosticoDto(resultSet.getString("diagnostico"));
                historicoPacienteDTO.setOrientacaoDto(resultSet.getString("orientacao"));
                historicoPacienteDTO.setNomeProfissionalDto(resultSet.getString("nomeProfissional"));
                historicoPacienteDTO.setPerfilProfissionalDto(resultSet.getString("perfilProfissional"));
                historicoPacienteDTO.setSereialHDDto(resultSet.getString("HD"));

                lista.add(historicoPacienteDTO);

            }

            connection.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<HistoricoPacienteDTO> buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        List<HistoricoPacienteDTO> lista = new ArrayList<HistoricoPacienteDTO>();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbHistoricoPaciente  where fk_paciente=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                HistoricoPacienteDTO historicoPacienteDTO = new HistoricoPacienteDTO();

                historicoPacienteDTO.setIdGeradoDto(resultSet.getInt("idAgendamento"));
                historicoPacienteDTO.setDiagnosticoDto(resultSet.getString("diagnostico"));
                historicoPacienteDTO.setOrientacaoDto(resultSet.getString("orientacao"));
                historicoPacienteDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));

                lista.add(historicoPacienteDTO);

            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return lista;

    }

    @Override
    public void inserir(HistoricoPacienteDTO historicoPacienteDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "INSERT INTO tbHistoricoPaciente("
                    + "id,"//idGerado
                    + "idAgendamento,"//idAgendamento
                    + "fk_paciente,"//chave estrangeira paciente
                    + "fk_medico," //chave estrangeira medico
                    + "nomeMedico,"//nome do médico 
                    + "dtHistorico,"//data do historico
                    + "cabecalho,"//cabecalho
                    + "diagnostico,"//data do historico
                    + "orientacao,"//orientacao
                    + "nomeProfissional,"// nome Profissional
                    + "perfilProfissional,"//perfil profissional  
                    + "HD"// hd
                    + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, historicoPacienteDTO.getIdDto());//id
            statement.setInt(2, historicoPacienteDTO.getIdGeradoDto());//idAgendamento
            statement.setInt(3, historicoPacienteDTO.getFk_pacienteDto());//fk_paciente
            statement.setInt(4, historicoPacienteDTO.getFk_medicoDto());//fk_meidico
            statement.setString(5, historicoPacienteDTO.getNomeMedicoDto());//nomeMedico
            statement.setDate(6, null);//dtHistorico
            statement.setString(7, historicoPacienteDTO.getCabecalhoDto());
            statement.setString(8, historicoPacienteDTO.getDiagnosticoDto());//diagnostico
            statement.setString(9, historicoPacienteDTO.getOrientacaoDto());//orientação
            statement.setString(10, historicoPacienteDTO.getNomeProfissionalDto());//nomeProfissional
            statement.setString(11, historicoPacienteDTO.getPerfilProfissionalDto());//perfilProfissional
            statement.setString(12, historicoPacienteDTO.getSereialHDDto());//hd

            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void deletar(HistoricoPacienteDTO obj) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**
             * O código abaixo é uma pesquisa montada na linguagem estruturada
             * de consulta que tem como funcionalidade buscar no campo
             * nome_medico o nome digitado pelo usuário no FormMedico campo
             * txtPesquisa que foi setado pelo metodo de encapsulamento
             * setPesquisa() contido na camada MedicoDTO e agora recuperado pelo
             * parametro medico que recupera o valor digitado pelo
             * getPesquisa(). OBSERVAÇÃO IMPORTANTE:O operador ILIKE é
             * específico do PostgreSQL e seu comportamento é semelhante ao
             * LIKE. A única diferença é que ele é case-insensitive, ou seja,
             * não diferencia maiúsculas de minúsculas.
             * Fontes:https://pt.stackoverflow.com/questions/96926/como-fazer-consulta-sql-que-ignora-mai%C3%BAsculas-min%C3%BAsculas-e-acentos
             */
            /**
             * 12min
             */
            String sql = "DELETE FROM tbHistoricoPaciente WHERE id=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, obj.getIdDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();

            /**
             * Fecha Conexão
             */
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();

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
    public HistoricoPacienteDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HistoricoPacienteDTO buscarPor(HistoricoPacienteDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(HistoricoPacienteDTO historicoPacienteDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna nomeDuplicado=true(Verdadeiro). Caso contrário
         * nomeDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean registroeDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbHistoricoPaciente where idGerado='" + historicoPacienteDTO.getIdGeradoDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                registroeDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return registroeDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return registroeDuplicado;

    }

    @Override
    public boolean inserirControlEmail(HistoricoPacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(HistoricoPacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(HistoricoPacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(HistoricoPacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HistoricoPacienteDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(HistoricoPacienteDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HistoricoPacienteDTO filtrarAoClicar(HistoricoPacienteDTO modelo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
