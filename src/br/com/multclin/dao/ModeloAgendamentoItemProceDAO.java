package br.com.multclin.dao;

import br.com.multclin.dto.AgendamentoDTO;
import br.com.multclin.dto.ItemAgendamentoDTO;
import br.com.multclin.dto.ModeloAgendamentoItemProcedeDTO;
import br.com.multclin.dto.ProcedimentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ModeloAgendamentoItemProceDAO {

    public List<ModeloAgendamentoItemProcedeDTO> filtrarCaixa(Date dataInicial, Date dataFinal, String status) throws PersistenciaException {

        List<ModeloAgendamentoItemProcedeDTO> listar = new ArrayList<>();

        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();
        ProcedimentoDTO procedimentoDTO =new ProcedimentoDTO();
        
        
        ModeloAgendamentoItemProcedeDTO modeloAgendamentoItemProcedimentoDTO = new ModeloAgendamentoItemProcedeDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */
  
            String sql="SELECT a.id,a.fk_paciente,a.nomePaciente,a.celularPaciente,a.dataAgendamento,a.nomeMedico,a.statusAgendamento,i.nomeProcede,p.RSBruto,p.RSClinica,p.RSMedico FROM tbAgendamentos a INNER JOIN tbItemAgendamento i ON a.id=i.id INNER JOIN tbProcedimentos p ON p.idAuto=i.idProcede WHERE dataAgendamento BETWEEN '" + dataInicial + "' AND '" + dataFinal + "' AND statusAgendamento LIKE '%" + status + "%' ORDER BY dataAgendamento ASC";
          
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                agendamentoDTO = new AgendamentoDTO();
                itemAgendamentoDTO = new ItemAgendamentoDTO();
                procedimentoDTO=new ProcedimentoDTO();
                
                modeloAgendamentoItemProcedimentoDTO = new ModeloAgendamentoItemProcedeDTO();

                //Agendamento
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));

                //Item do Agendamento
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));
                
                //Procedimento Agendamento
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSMedico"));
                

                modeloAgendamentoItemProcedimentoDTO.setModAgendamentoDto(agendamentoDTO);
                modeloAgendamentoItemProcedimentoDTO.setModItemDto(itemAgendamentoDTO);
                modeloAgendamentoItemProcedimentoDTO.setModProcedeDto(procedimentoDTO);
                
                
                listar.add(modeloAgendamentoItemProcedimentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listar;

    }

    
    public List<ModeloAgendamentoItemProcedeDTO> filtrarCaixaExtratoMedico(Date dataInicial, Date dataFinal, String status,String medico) throws PersistenciaException {

        List<ModeloAgendamentoItemProcedeDTO> listar = new ArrayList<>();

        AgendamentoDTO agendamentoDTO = new AgendamentoDTO();
        ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();
        ProcedimentoDTO procedimentoDTO =new ProcedimentoDTO();
        
        
        ModeloAgendamentoItemProcedeDTO modeloAgendamentoItemProcedimentoDTO = new ModeloAgendamentoItemProcedeDTO();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            /**
             * INNER JOIN retorna linhas quando houver pelo menos uma
             * correspondencia entre ambas as tabelas
             */
  
            String sql="SELECT a.id,"
                    + "a.fk_paciente,"
                    + "a.nomePaciente,"
                    + "a.celularPaciente,"
                    + "a.dataAgendamento,"
                    + "a.nomeMedico,"
                    + "a.statusAgendamento,"
                    + "p.nome,"
                    + "p.RSBruto,"
                    + "p.RSClinica,"
                    + "p.RSMedico "
                    + "FROM "
                    + "tbAgendamentos a INNER JOIN tbItemAgendamento i"
                    + " ON "
                    + "a.id=i.id "
                    + "INNER JOIN tbProcedimentos p "
                    + "ON i.idProcede=p.idAuto "
                    + "WHERE "
                    + "dataAgendamento "
                    + "BETWEEN '" + dataInicial + "' AND '" + dataFinal + "' AND statusAgendamento LIKE '%" + status + "%' AND nomeMedico LIKE '%" + medico + "%' ORDER BY dataAgendamento ASC";
          
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                agendamentoDTO = new AgendamentoDTO();
                itemAgendamentoDTO = new ItemAgendamentoDTO();
                procedimentoDTO=new ProcedimentoDTO();
                
                modeloAgendamentoItemProcedimentoDTO = new ModeloAgendamentoItemProcedeDTO();

                //Agendamento
                agendamentoDTO.setIdDto(resultSet.getInt("id"));
                agendamentoDTO.setFk_pacienteDto(resultSet.getInt("fk_paciente"));
                agendamentoDTO.setNomeDto(resultSet.getString("nomePaciente"));
                agendamentoDTO.setCelularPrefDto(resultSet.getString("celularPaciente"));
                agendamentoDTO.setDataAgendamentoDto(resultSet.getDate("dataAgendamento"));
                agendamentoDTO.setNomeMedicoDto(resultSet.getString("nomeMedico"));
                agendamentoDTO.setStatusAgendamentoDto(resultSet.getString("statusAgendamento"));

                
                
                
                //Procedimento Agendamento
                procedimentoDTO.setNomeDto(resultSet.getString("nome"));
                procedimentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                procedimentoDTO.setRsClinicaDto(resultSet.getDouble("RSClinica"));
                procedimentoDTO.setRsMedicoDto(resultSet.getDouble("RSMedico"));
                

                modeloAgendamentoItemProcedimentoDTO.setModAgendamentoDto(agendamentoDTO);
                modeloAgendamentoItemProcedimentoDTO.setModItemDto(itemAgendamentoDTO);
                modeloAgendamentoItemProcedimentoDTO.setModProcedeDto(procedimentoDTO);
                
                
                listar.add(modeloAgendamentoItemProcedimentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listar;

    }

    

}
