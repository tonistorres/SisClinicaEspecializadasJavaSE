
package br.com.multclin.dto;

import java.util.Date;


public class ItemAgendamentoDTO {
    private int idAutoDto;//id automatico 
    private int idDto;//id agendamento 
    private int idProceDto;//id do procedimento
    private int idEspDto;// id da especialidade 
    private int nProcedeDto;
    
    
    private String nomeProcedeDto;//nome
    //private String rsBrutoDto;
   // private String rsDescontoDto;
    
    private double rsBrutoDto;
    private double rsDescontoDto;
    
    private Date cadastroTimeStampDto;//cadastro

    /**
     * @return the idAutoDto
     */
    public int getIdAutoDto() {
        return idAutoDto;
    }

    /**
     * @param idAutoDto the idAutoDto to set
     */
    public void setIdAutoDto(int idAutoDto) {
        this.idAutoDto = idAutoDto;
    }

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

    /**
     * @return the nomeProcedeDto
     */
    public String getNomeProcedeDto() {
        return nomeProcedeDto;
    }

    /**
     * @param nomeProcedeDto the nomeProcedeDto to set
     */
    public void setNomeProcedeDto(String nomeProcedeDto) {
        this.nomeProcedeDto = nomeProcedeDto;
    }

    /**
     * @return the cadastroTimeStampDto
     */
    public Date getCadastroTimeStampDto() {
        return cadastroTimeStampDto;
    }

    /**
     * @param cadastroTimeStampDto the cadastroTimeStampDto to set
     */
    public void setCadastroTimeStampDto(Date cadastroTimeStampDto) {
        this.cadastroTimeStampDto = cadastroTimeStampDto;
    }

    /**
     * @return the rsBrutoDto
     */
//    public String getRsBrutoDto() {
//        return rsBrutoDto;
//    }
//
//    /**
//     * @param rsBrutoDto the rsBrutoDto to set
//     */
//    public void setRsBrutoDto(String rsBrutoDto) {
//        this.rsBrutoDto = rsBrutoDto;
//    }

  

    /**
     * @return the rsDescontoDto
     */
//    public String getRsDescontoDto() {
//        return rsDescontoDto;
//    }
//
//    /**
//     * @param rsDescontoDto the rsDescontoDto to set
//     */
//    public void setRsDescontoDto(String rsDescontoDto) {
//        this.rsDescontoDto = rsDescontoDto;
//    }

    /**
     * @return the idProceDto
     */
    public int getIdProceDto() {
        return idProceDto;
    }

    /**
     * @param idProceDto the idProceDto to set
     */
    public void setIdProceDto(int idProceDto) {
        this.idProceDto = idProceDto;
    }

    /**
     * @return the idEspDto
     */
    public int getIdEspDto() {
        return idEspDto;
    }

    /**
     * @param idEspDto the idEspDto to set
     */
    public void setIdEspDto(int idEspDto) {
        this.idEspDto = idEspDto;
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
     * @return the rsDescontoDto
     */
    public double getRsDescontoDto() {
        return rsDescontoDto;
    }

    /**
     * @param rsDescontoDto the rsDescontoDto to set
     */
    public void setRsDescontoDto(double rsDescontoDto) {
        this.rsDescontoDto = rsDescontoDto;
    }

    /**
     * @return the nProcedeDto
     */
    public int getnProcedeDto() {
        return nProcedeDto;
    }

    /**
     * @param nProcedeDto the nProcedeDto to set
     */
    public void setnProcedeDto(int nProcedeDto) {
        this.nProcedeDto = nProcedeDto;
    }

    

    
    
    
}
