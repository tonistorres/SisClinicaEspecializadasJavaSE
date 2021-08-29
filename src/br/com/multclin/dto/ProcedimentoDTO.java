package br.com.multclin.dto;

import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import java.util.Date;

public class ProcedimentoDTO {

    private Integer idAutoDto;
    private Integer idDto;
    private String nomeDto;
    private double rsBrutoDto;
    private double rsClinicaDto;
    private double rsMedicoDto;
    private Date cadastroDto;

    /**
     * @return the idAutoDto
     */
    public Integer getIdAutoDto() {
        return idAutoDto;
    }

    /**
     * @param idAutoDto the idAutoDto to set
     */
    public void setIdAutoDto(Integer idAutoDto) {
        this.idAutoDto = idAutoDto;
    }

    /**
     * @return the idDto
     */
    public Integer getIdDto() {
        return idDto;
    }

    /**
     * @param idDto the idDto to set
     */
    public void setIdDto(Integer idDto) {
        this.idDto = idDto;
    }

    /**
     * @return the nomeDto
     */
    public String getNomeDto() {
        return nomeDto;
    }

    /**
     * @param nomeDto the nomeDto to set
     */
    public void setNomeDto(String nomeDto) {
        this.nomeDto = nomeDto;
    }

   
    /**
     * @return the cadastroDto
     */
    public Date getCadastroDto() {
        return cadastroDto;
    }

    /**
     * @param cadastroDto the cadastroDto to set
     */
    public void setCadastroDto(Date cadastroDto) {
        this.cadastroDto = cadastroDto;
    }

    /**
     * @return the rsBrutoDto
     */
    public double getRsBrutoDto() {
        return rsBrutoDto;
    }

    /**
     * @param rsBrutoDto the rsBrutoDto to set
     */
    public void setRsBrutoDto(double rsBrutoDto) {
        this.rsBrutoDto = rsBrutoDto;
    }

    /**
     * @return the rsClinicaDto
     */
    public double getRsClinicaDto() {
        return rsClinicaDto;
    }

    /**
     * @param rsClinicaDto the rsClinicaDto to set
     */
    public void setRsClinicaDto(double rsClinicaDto) {
        this.rsClinicaDto = rsClinicaDto;
    }

    /**
     * @return the rsMedicoDto
     */
    public double getRsMedicoDto() {
        return rsMedicoDto;
    }

    /**
     * @param rsMedicoDto the rsMedicoDto to set
     */
    public void setRsMedicoDto(double rsMedicoDto) {
        this.rsMedicoDto = rsMedicoDto;
    }

}
