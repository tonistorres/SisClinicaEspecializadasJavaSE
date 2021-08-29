package br.com.multclin.dao;

import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.ItemAgendamentoDTO;
import br.com.multclin.dto.ModeloAgendamentoItemDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ModeloAgendamentoItemDAO {

    public List<ModeloAgendamentoItemDTO> filtrarProcedimentosPorMedico(String status, String nome) throws PersistenciaException {

        List<ModeloAgendamentoItemDTO> listar = new ArrayList<>();

        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();
        ModeloAgendamentoItemDTO modeloAgendamentoItemDTO = new ModeloAgendamentoItemDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */

            String sql = "SELECT "
                    + "tbAgendamentos.id,"//id
                    + "tbAgendamentos.fk_paciente,"//forenkey pacietne
                    + "tbAgendamentos.nomePaciente,"//nome paciente
                    + "tbAgendamentos.celularPaciente,"//celular
                    + "tbAgendamentos.dataAgendamento,"//data consulta
                    + "tbAgendamentos.nomeMedico,"//data consulta
                    + "tbItemAgendamento.nomeProcede,"//data consulta
                    + "tbItemAgendamento.RSBruto "
                    + "FROM tbAgendamentos join tbItemAgendamento "
                    + "ON tbAgendamentos.id=tbItemAgendamento.id "
                    + "WHERE statusAgendamento LIKE '%" + status + "%' AND nomeMedico LIKE'%" + nome + "%' order by dataAgendamento asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                agendamentoDTO = new AgendamentoDTO();
                itemAgendamentoDTO = new ItemAgendamentoDTO();
                modeloAgendamentoItemDTO = new ModeloAgendamentoItemDTO();

                //Agendamento
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));
                //agendamentoDTO.setFk_medicoDto(resultSet.getInt("fk_Medico"));
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));

                //Item do Agendamento
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));

                modeloAgendamentoItemDTO.setModAgendamentoDto(agendamentoDTO);
                modeloAgendamentoItemDTO.setModItemDto(itemAgendamentoDTO);
                listar.add(modeloAgendamentoItemDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listar;

    }

    public List<ModeloAgendamentoItemDTO> filtrarPorMedidoProcede(String statusAgenda, String nomeProfissionalSaude, String procede) throws PersistenciaException {

        List<ModeloAgendamentoItemDTO> listar = new ArrayList<>();

        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();
        ModeloAgendamentoItemDTO modeloAgendamentoItemDTO = new ModeloAgendamentoItemDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */

            String sql = "SELECT "
                    + "tbAgendamentos.id,"//id
                    + "tbAgendamentos.fk_paciente,"//forenkey pacietne
                    + "tbAgendamentos.nomePaciente,"//nome paciente
                    + "tbAgendamentos.celularPaciente,"//celular
                    + "tbAgendamentos.dataAgendamento,"//data consulta
                    + "tbAgendamentos.nomeMedico,"//data consulta
                    + "tbItemAgendamento.nomeProcede,"//data consulta
                    + "tbItemAgendamento.RSBruto "
                    + "FROM tbAgendamentos join tbItemAgendamento "
                    + "ON tbAgendamentos.id=tbItemAgendamento.id "
                    + "WHERE statusAgendamento LIKE '%" + statusAgenda + "%' AND nomeMedico LIKE'%" + nomeProfissionalSaude + "%' AND nomeProcede LIKE'%" + procede + "%'order by dataAgendamento asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                agendamentoDTO = new AgendamentoDTO();
                itemAgendamentoDTO = new ItemAgendamentoDTO();
                modeloAgendamentoItemDTO = new ModeloAgendamentoItemDTO();

                //Agendamento
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));
                //agendamentoDTO.setFk_medicoDto(resultSet.getInt("fk_Medico"));
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));

                //Item do Agendamento
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));

                modeloAgendamentoItemDTO.setModAgendamentoDto(agendamentoDTO);
                modeloAgendamentoItemDTO.setModItemDto(itemAgendamentoDTO);
                listar.add(modeloAgendamentoItemDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listar;

    }

    public List<ModeloAgendamentoItemDTO> filtrarQuadroDemonstrativo() throws PersistenciaException {

        List<ModeloAgendamentoItemDTO> listar = new ArrayList<>();

        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();
        ModeloAgendamentoItemDTO modeloAgendamentoItemDTO = new ModeloAgendamentoItemDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */

            String sql = "SELECT "
                    + "tbAgendamentos.nomeMedico," //nome do medico
                    + " tbItemAgendamento.nomeProcede, "//nome do procedimento
                    + "COUNT(*) AS NProcede, "
                    + "SUM(tbItemAgendamento.RSBruto) AS Total "
                    + "FROM "
                    + "tbAgendamentos JOIN  tbItemAgendamento"
                    + " ON  tbAgendamentos.id=tbItemAgendamento.id"
                    + " WHERE tbAgendamentos.statusAgendamento='AGENDADO' OR tbAgendamentos.statusAgendamento='REAGENDADO' "
                    + "group by  tbItemAgendamento.nomeProcede order by tbAgendamentos.nomeMedico asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                agendamentoDTO = new AgendamentoDTO();
                itemAgendamentoDTO = new ItemAgendamentoDTO();
                modeloAgendamentoItemDTO = new ModeloAgendamentoItemDTO();

                //tbAgendamentos
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
              

                //tbItemAgendamento
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));
                itemAgendamentoDTO.setnProcedeDto(resultSet.getInt("NProcede"));
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("Total"));
                
                modeloAgendamentoItemDTO.setModAgendamentoDto(agendamentoDTO);
                modeloAgendamentoItemDTO.setModItemDto(itemAgendamentoDTO);
                listar.add(modeloAgendamentoItemDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listar;

    }

}
