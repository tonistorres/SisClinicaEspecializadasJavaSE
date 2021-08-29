package br.com.multclin.dao;

import br.com.multclin.dto.AgendamentoDTO;
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
public class AgendamentoDAO implements GenericDAO<AgendamentoDTO> {

    /**
     * Método Para listar todos os Usuários de uma tabela
     */
    @Override
    public List<AgendamentoDTO> listarTodos() throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos order by dataAgendamento asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<AgendamentoDTO> listarTodosAgendados(String pesquisar) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            //String sql = "SELECT *FROM tbAgendamentos WHERE statusAgendamento='" + pesquisarAgendamento + "' OR statusAgendamento='"+pesquisaReAgendamento+"'";
            String sql = "SELECT *FROM tbAgendamentos WHERE statusAgendamento LIKE '%" + pesquisar + "%' order by dataAgendamento asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<AgendamentoDTO> listarTodosAgendadosCPF(String pesquisar, String cpf) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos WHERE statusAgendamento LIKE '%" + pesquisar + "%' AND cpfPaciente LIKE'%" + cpf + "%' order by dataAgendamento asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    //método responsavel pela consulta de valores entre datas e mais status 
    public List<AgendamentoDTO> listarTodosAgendadosStatus(Date dataInicial, Date dataFinal, String pesquisa) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos WHERE dataAgendamento BETWEEN '" + dataInicial + "' AND '" + dataFinal + "' AND statusAgendamento='" + pesquisa + "' ORDER BY dataAgendamento ASC";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<AgendamentoDTO> listarTodosAgendadosJDateChooser(String pesquisar, Date data) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos WHERE statusAgendamento LIKE '%" + pesquisar + "%' AND dataAgendamento='" + data + "'";
            //String sql = "SELECT *FROM tbAgendamentos WHERE statusAgendamento LIKE '%" + pesquisar + "%' AND dataAgendamento='" + data + "' order by dataAgendamento asc";
            // String sql = "SELECT *FROM tbAgendamentos WHERE statusAgendamento='" + pesquisar + "'";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<AgendamentoDTO> listarTodosAgendadosPorNome(String pesquisar, String nome) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos WHERE statusAgendamento LIKE '%" + pesquisar + "%' AND nomePaciente LIKE'%" + nome + "%' order by dataAgendamento asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<AgendamentoDTO> listarTodosPorMedico(String status, String nome) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
          
            String sql = "SELECT *FROM tbAgendamentos WHERE statusAgendamento LIKE '%" + status + "%' AND nomeMedico LIKE'%" + nome + "%' order by dataAgendamento asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));
                agendamentoDTO.setFk_medicoDto(resultSet.getInt("fk_Medico"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<AgendamentoDTO> filtrarPesqRapidaPorCPF(String pesquisar) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<AgendamentoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos WHERE cpfPaciente LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<AgendamentoDTO> filtrarPesqRapida(String pesquisar) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<AgendamentoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos WHERE nomePaciente LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                lista.add(agendamentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<AgendamentoDTO> filtrarPorIDRetornaLista(int codigo) throws PersistenciaException {

        List<AgendamentoDTO> lista = new ArrayList<AgendamentoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos where id=" + codigo;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                AgendamentoDTO agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_medicoDto(resultSet.getInt("fk_medico"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));
                agendamentoDTO.setDtRegistroDto(resultSet.getString("cadastro"));

                lista.add(agendamentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
    }

    public AgendamentoDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        AgendamentoDTO agendamentoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos where idAuto=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                return agendamentoDTO;
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
            email.setSubject("Erro Camada GUI - Metodo:SalvarAdicoesEdicoes()");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

            //enviando mensagem de confirmação Usuário
            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Todos Procedimentos concluídos com Sucesso."
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro ao Enviar Email." + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            e.printStackTrace();
        }

    }

    @Override
    public void inserir(AgendamentoDTO agendamentoDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "INSERT INTO tbAgendamentos("
                    + "id,"//id
                    + "fk_paciente,"//paciente
                    + "fk_Medico," //medico
                    + "nomeMedico,"//nome do médico 
                    + "especialidadesMedico,"//especialidade do médico 
                    + "cpfPaciente,"//cpf do paciente 
                    + "nomePaciente,"//no do paciente
                    + "dataNascPaciente,"//data de nascimento do paciente 
                    + "sexoPaciente,"//sexo do paciente 
                    + "estadoCivilPaciente,"//estado civil do paciente 
                    + "conjugePaciente,"//conjuge do paciente 
                    + "maePaciente,"// mae do paciente 
                    + "paiPaciente,"//pai do paciente 
                    + "celularPaciente,"//celular do paciente 
                    + "emailPaciente,"//email do paciente
                    + "ufPaciente,"//uf do paciente
                    + "cidadePaciente,"//cidade do paciente 
                    + "bairroPaciente,"//bairro paciente 
                    + "ruaPaciente,"//rua paciente
                    + "complementoPaciente,"//complemento paciente
                    + "ncasaPaciente,"//numero da casa do paciente 
                    + "cepPaciente,"//cep do paciente 
                    + "dataAgendamento,"//data do agenda mento do procediemento que o paciente irá realizar 
                    + "observacaoAgendamento,"
                    + "statusAgendamento,"//status do agendamento
                    + "rsTotalAgendamento,"// valor total do agendamento 
                    + "cpu,"
                    + "hd,"
                    + "usuarioSistema,"
                    + "perfil,"
                    + "cadastro"
                    + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, agendamentoDTO.getIdDto());
            statement.setInt(2, agendamentoDTO.getFk_pacienteDto());
            statement.setInt(3, agendamentoDTO.getFk_medicoDto());

            //DADOS MEDICO 
            statement.setString(4, agendamentoDTO.getNomeMedicoDto());
            statement.setString(5, agendamentoDTO.getEspecialidadeDto());
            statement.setString(6, agendamentoDTO.getCpfDto());
            statement.setString(7, agendamentoDTO.getNomeDto());
            statement.setString(8, agendamentoDTO.getDataNascDto());
            statement.setString(9, agendamentoDTO.getSexoDto());
            statement.setString(10, agendamentoDTO.getEstadoCivilDto());
            statement.setString(11, agendamentoDTO.getConjugeDto());
            statement.setString(12, agendamentoDTO.getMaeDto());
            statement.setString(13, agendamentoDTO.getPaiDto());
            statement.setString(14, agendamentoDTO.getCelularPrefDto());
            statement.setString(15, agendamentoDTO.getEmailPrefDto());
            statement.setString(16, agendamentoDTO.getUfDto());
            statement.setString(17, agendamentoDTO.getCidadeDto());
            statement.setString(18, agendamentoDTO.getBairroDto());
            statement.setString(19, agendamentoDTO.getRuaDto());
            statement.setString(20, agendamentoDTO.getComplementoDto());
            statement.setString(21, agendamentoDTO.getnCasaDto());
            statement.setString(22, agendamentoDTO.getCEPDto());
            statement.setDate(23, (Date) agendamentoDTO.getDataAgendamentoDto());
            statement.setString(24, agendamentoDTO.getObservacaoAgendaDto());
            statement.setString(25, agendamentoDTO.getStatusAgendamentoDto());
            statement.setDouble(26, agendamentoDTO.getRsTotalAgendamentoDto());

            statement.setString(27, agendamentoDTO.getCpuDto());
            statement.setString(28, agendamentoDTO.getHdDto());
            statement.setString(29, agendamentoDTO.getUsuarioDto());
            statement.setString(30, agendamentoDTO.getPerfilDto());

            statement.setDate(31, null);

            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    public void atualizarReagendar(AgendamentoDTO agendamentoDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "UPDATE tbAgendamentos SET dataAgendamento=?,dtRegistro=?,statusAgendamento=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            //DADOS DO MÉDICO 
            statement.setDate(1, (java.sql.Date) agendamentoDTO.getDataAgendamentoDto());
            statement.setString(2, agendamentoDTO.getDtRegistroDto());
            statement.setString(3, agendamentoDTO.getStatusAgendamentoDto());
            statement.setInt(4, agendamentoDTO.getIdDto());

            /**
             * executar o statement
             */
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void atualizar(AgendamentoDTO agendamentoDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "UPDATE tbAgendamentos SET dtRegistro=?,statusAgendamento=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            //DADOS DO MÉDICO 
            statement.setString(1, agendamentoDTO.getDtRegistroDto());
            statement.setString(2, agendamentoDTO.getStatusAgendamentoDto());
            statement.setInt(3, agendamentoDTO.getIdDto());

            /**
             * executar o statement
             */
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    public void atualizarCaixa(AgendamentoDTO agendamentoDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "UPDATE tbAgendamentos SET dtRegistro=?,statusAgendamento=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            //DADOS DO MÉDICO 
            statement.setString(1, agendamentoDTO.getDtRegistroDto());
            statement.setString(2, agendamentoDTO.getStatusAgendamentoDto());
            statement.setInt(3, agendamentoDTO.getIdDto());

            /**
             * executar o statement
             */
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deletar(AgendamentoDTO obj) throws PersistenciaException {
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
            String sql = "DELETE FROM tbAgendamentos WHERE id=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, obj.getIdDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();
            JOptionPane.showMessageDialog(null, "" + "\n Info:\n"
                    + "Dados deletados com sucesso. "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            /**
             * Fecha Conexão
             */
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro na Deleção do Dado\nErro:" + ex.getMessage());

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
    public AgendamentoDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AgendamentoDTO buscarPor(AgendamentoDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(AgendamentoDTO agendamentoDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna nomeDuplicado=true(Verdadeiro). Caso contrário
         * nomeDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean nomeDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbAgendamentos where cpf='" + agendamentoDTO.getCpfDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                nomeDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return nomeDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return nomeDuplicado;

    }

    public boolean verificaDuplicidadeCPF(AgendamentoDTO agendamentoDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna nomeDuplicado=true(Verdadeiro). Caso contrário
         * nomeDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean nomeDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbAgendamentos where CPF='" + agendamentoDTO.getCpfDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                nomeDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
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
    public AgendamentoDTO filtrarAoClicar(AgendamentoDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        AgendamentoDTO agendamentoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbAgendamentos WHERE nomePaciente LIKE'%" + modelo.getNomeDto() + "%'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
                agendamentoDTO = new AgendamentoDTO();

                agendamentoDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                agendamentoDTO.setIdDto(resultSet.getInt("id"));

                //DADOS MEDICO 
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setEspecialidadeDto(resultSet.getString("especialidadesMedico"));

                //DADOS PACIENTE 
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setDataNascDto(resultSet.getString("dataNascPaciente"));
                agendamentoDTO.setCpfDto(resultSet.getString("cpfPaciente"));
                agendamentoDTO.setSexoDto(resultSet.getString("sexoPaciente"));
                agendamentoDTO.setEstadoCivilDto(resultSet.getString("estadoCivilPaciente"));
                agendamentoDTO.setConjugeDto(resultSet.getString("conjugePaciente"));
                agendamentoDTO.setMaeDto(resultSet.getString("maePaciente"));
                agendamentoDTO.setPaiDto(resultSet.getString("paiPaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setEmailPrefDto(resultSet.getString("emailPaciente"));
                agendamentoDTO.setUfDto(resultSet.getString("ufPaciente"));
                agendamentoDTO.setCidadeDto(resultSet.getString("cidadePaciente"));
                agendamentoDTO.setBairroDto(resultSet.getString("bairroPaciente"));
                agendamentoDTO.setRuaDto(resultSet.getString("ruaPaciente"));
                agendamentoDTO.setComplementoDto(resultSet.getString("complementoPaciente"));
                agendamentoDTO.setnCasaDto(resultSet.getString("ncasaPaciente"));
                agendamentoDTO.setCEPDto(resultSet.getString("cepPaciente"));

                //DADOS DO AGENDAMENTO
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setObservacaoAgendaDto(resultSet.getString("observacaoAgendamento"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));
                agendamentoDTO.setRsTotalAgendamentoDto(resultSet.getDouble("rsTotalAgendamento"));

                return agendamentoDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    @Override
    public boolean inserirControlEmail(AgendamentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(AgendamentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(AgendamentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(AgendamentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AgendamentoDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
