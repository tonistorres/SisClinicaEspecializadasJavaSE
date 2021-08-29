package br.com.multclin.dto;

import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import java.util.Date;

/**
 * AULA 12 - CAMADA DTO(Data Transfer Object)Padrao de Projeto foi criado para
 * AUXILIAR na transferencia de DADOS de um lado para outro dentro de um
 * projeto.sem logica de negocios em seus objetos e comumente associado a
 * transferencia de dados entre uma camada de visao (view layer) e outra de
 * persistencia dos dados (model layer) Muito frequentemente voce vera esse
 * padrao sendo usado em conjunto com um DAO. *
 * http://www.devmedia.com.br/diferenca-entre-os-patterns-po-pojo-bo-dto-e-vo/28162
 */
public class FoneDTO {

    private Integer idFoneAutoIncrementoDto;
    private Integer idFoneDto;
    private Integer fkIDPaisRegiaoDto;
    private String nomeDto;
    private String cpfDto;
    private String codigoSMSDto;
    private String paisDto;
    private String foneDto;
    private String perfilDto;
    private Date cadastroDto;

    /**
     * @return the idFoneDto
     */
    public Integer getIdFoneDto() {
        return idFoneDto;
    }

    /**
     * @param idFoneDto the idFoneDto to set
     */
    public void setIdFoneDto(Integer idFoneDto) {
        this.idFoneDto = idFoneDto;
    }

    /**
     * @return the fkIDPaisRegiaoDto
     */
    public Integer getFkIDPaisRegiaoDto() {
        return fkIDPaisRegiaoDto;
    }

    /**
     * @param fkIDPaisRegiaoDto the fkIDPaisRegiaoDto to set
     */
    public void setFkIDPaisRegiaoDto(Integer fkIDPaisRegiaoDto) {
        this.fkIDPaisRegiaoDto = fkIDPaisRegiaoDto;
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
     * @return the codigoSMSDto
     */
    public String getCodigoSMSDto() {
        return codigoSMSDto;
    }

    /**
     * @param codigoSMSDto the codigoSMSDto to set
     */
    public void setCodigoSMSDto(String codigoSMSDto) {
        this.codigoSMSDto = codigoSMSDto;
    }

    /**
     * @return the paisDto
     */
    public String getPaisDto() {
        return paisDto;
    }

    /**
     * @param paisDto the paisDto to set
     */
    public void setPaisDto(String paisDto) {
        this.paisDto = paisDto;
    }

    /**
     * @return the foneDto
     */
    public String getFoneDto() {
        return foneDto;
    }

    /**
     * @param foneDto the foneDto to set
     */
    public void setFoneDto(String foneDto) {
        this.foneDto = foneDto;
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
     * @return the idFoneAutoIncrementoDto
     */
    public Integer getIdFoneAutoIncrementoDto() {
        return idFoneAutoIncrementoDto;
    }

    /**
     * @param idFoneAutoIncrementoDto the idFoneAutoIncrementoDto to set
     */
    public void setIdFoneAutoIncrementoDto(Integer idFoneAutoIncrementoDto) {
        this.idFoneAutoIncrementoDto = idFoneAutoIncrementoDto;
    }
    
    
    
 

}
