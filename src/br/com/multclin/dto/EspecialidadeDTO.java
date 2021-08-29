package br.com.multclin.dto;

import br.com.multclin.metodosstatics.MetodoStaticosUtil;

public class EspecialidadeDTO {

    //dados pessoais 

    private Integer idAutoDto;
    private Integer idDto;//codigo do cliente
    private Integer fk_cadEspecialidadeDto;
    private String nomeDto;//nome completo do cliente 
    private String cpfDto;//cpf do cliente 
    private String especialidadeDto;
    private String perfilDto;
    private String conselhoDto;
    
    

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
        this.nomeDto =MetodoStaticosUtil.removerAcentosCaixAlta(nomeDto) ;
    }

    
    /**
     * @return the cpfDto
     */
    public String getCpfDto() {
        return cpfDto;
    }

    /**
     * @param cpfDto the cpfDto to set
     */
    public void setCpfDto(String cpfDto) {
        this.cpfDto = cpfDto;
    }

    /**
     * @return the especialidadeDto
     */
    public String getEspecialidadeDto() {
        return especialidadeDto;
    }

    /**
     * @param especialidadeDto the especialidadeDto to set
     */
    public void setEspecialidadeDto(String especialidadeDto) {
        this.especialidadeDto = especialidadeDto;
    }

    /**
     * @return the perfilDto
     */
    public String getPerfilDto() {
        return perfilDto;
    }

    /**
     * @param perfilDto the perfilDto to set
     */
    public void setPerfilDto(String perfilDto) {
        this.perfilDto = perfilDto;
    }

    /**
     * @return the conselhoDto
     */
    public String getConselhoDto() {
        return conselhoDto;
    }

    /**
     * @param conselhoDto the conselhoDto to set
     */
    public void setConselhoDto(String conselhoDto) {
        this.conselhoDto = conselhoDto;
    }

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
     * @return the fk_cadEspecialidadeDto
     */
    public Integer getFk_cadEspecialidadeDto() {
        return fk_cadEspecialidadeDto;
    }

    /**
     * @param fk_cadEspecialidadeDto the fk_cadEspecialidadeDto to set
     */
    public void setFk_cadEspecialidadeDto(Integer fk_cadEspecialidadeDto) {
        this.fk_cadEspecialidadeDto = fk_cadEspecialidadeDto;
    }

    
}
