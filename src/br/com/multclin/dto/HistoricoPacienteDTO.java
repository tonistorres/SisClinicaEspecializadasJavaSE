package br.com.multclin.dto;

import java.util.Date;

public class HistoricoPacienteDTO {
    private int idDto;
    private int idGeradoDto;
    private int fk_pacienteDto;
    private int fk_medicoDto;
    private String nomeMedicoDto;
    private Date dtHistoricoDto;
    private String cabecalhoDto;
    private String OrientacaoDto;
    private String DiagnosticoDto;
    private String nomeProfissionalDto;
    private String perfilProfissionalDto;
    private String sereialHDDto;

    
    //Construtor vazio 
    public HistoricoPacienteDTO(){
    
    
    }
    
        
    
    
    
    /**
     * @return the idGeradoDto
     */
    
     /**
     * @return the idDto
     */
    public int getIdDto() {
        return idDto;
    }

    /**
     * @param idDto the idDto to set
     */
    public void setIdDto(int idDto) {
        this.idDto = idDto;
    }
    public int getIdGeradoDto() {
        return idGeradoDto;
    }

    /**
     * @param idGeradoDto the idGeradoDto to set
     */
    public void setIdGeradoDto(int idGeradoDto) {
        this.idGeradoDto = idGeradoDto;
    }

    /**
     * @return the fk_pacienteDto
     */
    public int getFk_pacienteDto() {
        return fk_pacienteDto;
    }

    /**
     * @param fk_pacienteDto the fk_pacienteDto to set
     */
    public void setFk_pacienteDto(int fk_pacienteDto) {
        this.fk_pacienteDto = fk_pacienteDto;
    }

    /**
     * @return the fk_medicoDto
     */
    public int getFk_medicoDto() {
        return fk_medicoDto;
    }

    /**
     * @param fk_medicoDto the fk_medicoDto to set
     */
    public void setFk_medicoDto(int fk_medicoDto) {
        this.fk_medicoDto = fk_medicoDto;
    }

    /**
     * @return the nomeMedicoDto
     */
    public String getNomeMedicoDto() {
        return nomeMedicoDto;
    }

    /**
     * @param nomeMedicoDto the nomeMedicoDto to set
     */
    public void setNomeMedicoDto(String nomeMedicoDto) {
        this.nomeMedicoDto = nomeMedicoDto;
    }

    /**
     * @return the dtHistoricoDto
     */
    public Date getDtHistoricoDto() {
        return dtHistoricoDto;
    }

    /**
     * @param dtHistoricoDto the dtHistoricoDto to set
     */
    public void setDtHistoricoDto(Date dtHistoricoDto) {
        this.dtHistoricoDto = dtHistoricoDto;
    }

    /**
     * @return the OrientacaoDto
     */
    public String getOrientacaoDto() {
        return OrientacaoDto;
    }

    /**
     * @param OrientacaoDto the OrientacaoDto to set
     */
    public void setOrientacaoDto(String OrientacaoDto) {
        this.OrientacaoDto = OrientacaoDto;
    }

    /**
     * @return the nomeProfissionalDto
     */
    public String getNomeProfissionalDto() {
        return nomeProfissionalDto;
    }

    /**
     * @param nomeProfissionalDto the nomeProfissionalDto to set
     */
    public void setNomeProfissionalDto(String nomeProfissionalDto) {
        this.nomeProfissionalDto = nomeProfissionalDto;
    }

    /**
     * @return the perfilProfissionalDto
     */
    public String getPerfilProfissionalDto() {
        return perfilProfissionalDto;
    }

    /**
     * @param perfilProfissionalDto the perfilProfissionalDto to set
     */
    public void setPerfilProfissionalDto(String perfilProfissionalDto) {
        this.perfilProfissionalDto = perfilProfissionalDto;
    }

    /**
     * @return the sereialHDDto
     */
    public String getSereialHDDto() {
        return sereialHDDto;
    }

    /**
     * @param sereialHDDto the sereialHDDto to set
     */
    public void setSereialHDDto(String sereialHDDto) {
        this.sereialHDDto = sereialHDDto;
    }

    /**
     * @return the DiagnosticoDto
     */
    public String getDiagnosticoDto() {
        return DiagnosticoDto;
    }

    /**
     * @param DiagnosticoDto the DiagnosticoDto to set
     */
    public void setDiagnosticoDto(String DiagnosticoDto) {
        this.DiagnosticoDto = DiagnosticoDto;
    }

    /**
     * @return the cabecalhoDto
     */
    public String getCabecalhoDto() {
        return cabecalhoDto;
    }

    /**
     * @param cabecalhoDto the cabecalhoDto to set
     */
    public void setCabecalhoDto(String cabecalhoDto) {
        this.cabecalhoDto = cabecalhoDto;
    }

   
    
    

}
