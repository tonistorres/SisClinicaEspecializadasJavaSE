package br.com.multclin.dao;

import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.dto.ItemAgendamentoDTO;
import br.com.multclin.dto.MedicoDTO;
import br.com.multclin.dto.ModeloAgendamentoItemDTO;
import br.com.multclin.dto.ModeloMedicoEspecialidadeDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ModeloMedicoEspecialidadeDAO {

    public List<ModeloMedicoEspecialidadeDTO> filtrarTodosMedicosEspecialidades() throws PersistenciaException {

        List<ModeloMedicoEspecialidadeDTO> listar = new ArrayList<>();

        MedicoDTO medicoDTO = new MedicoDTO();
        EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
        ModeloMedicoEspecialidadeDTO modeloMedicoEspecialidadeDTO = new ModeloMedicoEspecialidadeDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */

            String sql = "select tbMedicos.id,tbMedicos.nome,tbMedicos.Conselho,tbMedicos.cidade,tbEspecialidades.especialidade from tbMedicos join tbEspecialidades on tbMedicos.id=tbEspecialidades.id ";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                medicoDTO = new MedicoDTO();
                especialidadeDTO = new EspecialidadeDTO();
                modeloMedicoEspecialidadeDTO = new ModeloMedicoEspecialidadeDTO();

                //MedicoDTO
                medicoDTO.setIdDto(resultSet.getInt("id"));
                medicoDTO.setNomeDto(resultSet.getString("nome"));
                medicoDTO.setConselhoDto(resultSet.getString("Conselho"));
                medicoDTO.setCidadeDto(resultSet.getString("cidade"));
                //EspecialidadeDTO
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));

                modeloMedicoEspecialidadeDTO.setModMedicoDto(medicoDTO);
                modeloMedicoEspecialidadeDTO.setModEspecialidadeDto(especialidadeDTO);
                listar.add(modeloMedicoEspecialidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listar;

    }

    public List<ModeloMedicoEspecialidadeDTO> filtrarMedicoPornome(String pesquisar) throws PersistenciaException {

        List<ModeloMedicoEspecialidadeDTO> listar = new ArrayList<>();

        MedicoDTO medicoDTO = new MedicoDTO();
        EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();
        ModeloMedicoEspecialidadeDTO modeloMedicoEspecialidadeDTO = new ModeloMedicoEspecialidadeDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */

            String sql = "select tbMedicos.id,tbMedicos.nome,tbMedicos.Conselho,tbMedicos.cidade,tbEspecialidades.especialidade from tbMedicos join tbEspecialidades on tbMedicos.id=tbEspecialidades.id where tbMedicos.nome LIKE '%" + pesquisar + "%'";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                medicoDTO = new MedicoDTO();
                especialidadeDTO = new EspecialidadeDTO();
                modeloMedicoEspecialidadeDTO = new ModeloMedicoEspecialidadeDTO();

                //MedicoDTO
                medicoDTO.setIdDto(resultSet.getInt("id"));
                medicoDTO.setNomeDto(resultSet.getString("nome"));
                medicoDTO.setConselhoDto(resultSet.getString("Conselho"));
                medicoDTO.setCidadeDto(resultSet.getString("cidade"));
                //EspecialidadeDTO
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));

                modeloMedicoEspecialidadeDTO.setModMedicoDto(medicoDTO);
                modeloMedicoEspecialidadeDTO.setModEspecialidadeDto(especialidadeDTO);
                listar.add(modeloMedicoEspecialidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listar;

    }

}
