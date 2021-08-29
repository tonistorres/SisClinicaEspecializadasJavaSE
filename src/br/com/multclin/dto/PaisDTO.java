package br.com.multclin.dto;

import br.com.multclin.metodosstatics.MetodoStaticosUtil;

/**
 * AULA 12 - CAMADA DTO(Data Transfer Object)Padrao de Projeto foi criado para
 * AUXILIAR na transferencia de DADOS de um lado para outro dentro de um
 * projeto.sem logica de negocios em seus objetos e comumente associado a
 * transferencia de dados entre uma camada de visao (view layer) e outra de
 * persistencia dos dados (model layer) Muito frequentemente voce vera esse
 * padrao sendo usado em conjunto com um DAO. *
 * http://www.devmedia.com.br/diferenca-entre-os-patterns-po-pojo-bo-dto-e-vo/28162
 */
public class PaisDTO {

    
    private Integer idpaisregiaoDto;
    private String paisDto;
    private String codigoSMSDto;
    

    /**
     * @return the idpaisregiaoDto
     */
    public Integer getIdpaisregiaoDto() {
        return idpaisregiaoDto;
    }

    /**
     * @param idpaisregiaoDto the idpaisregiaoDto to set
     */
    public void setIdpaisregiaoDto(Integer idpaisregiaoDto) {
        this.idpaisregiaoDto = idpaisregiaoDto;
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
        //método trim() retira todos os espaços antes e depois da string 
        this.paisDto = MetodoStaticosUtil.removerAcentosCaixAlta(paisDto.trim());
        
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
        this.codigoSMSDto = MetodoStaticosUtil.removerAcentosCaixAlta(codigoSMSDto.trim());
    }

 

}
