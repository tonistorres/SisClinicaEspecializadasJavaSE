package br.com.multclin.dto;

import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import java.util.Date;

public class CadEspecialidadeDTO {

    
    private Integer idDto;
    private String nomeDto;
    private Date cadastroDto;

    

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
        this.nomeDto = MetodoStaticosUtil.removerAcentosCaixAlta(nomeDto);
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

}
