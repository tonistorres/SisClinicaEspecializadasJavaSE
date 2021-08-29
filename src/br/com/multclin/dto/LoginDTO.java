package br.com.multclin.dto;

/**
 * AULA 12 - CAMADA DTO(Data Transfer Object)Padrao de Projeto foi criado para
 * AUXILIAR na transferencia de DADOS de um lado para outro dentro de um
 * projeto.sem logica de negocios em seus objetos e comumente associado a
 * transferencia de dados entre uma camada de visao (view layer) e outra de
 * persistencia dos dados (model layer) Muito frequentemente voce vera esse
 * padrao sendo usado em conjunto com um DAO. *
 * http://www.devmedia.com.br/diferenca-entre-os-patterns-po-pojo-bo-dto-e-vo/28162
 */
public class LoginDTO {

    /**
     * @return the iduserDto
     */
    public Integer getIduserDto() {
        return iduserDto;
    }

    /**
     * @param iduserDto the iduserDto to set
     */
    public void setIduserDto(Integer iduserDto) {
        this.iduserDto = iduserDto;
    }

    /**
     * @return the loginDto
     */
    public String getLoginDto() {
        return loginDto;
    }

    /**
     * @param loginDto the loginDto to set
     */
    public void setLoginDto(String loginDto) {
        this.loginDto = loginDto;
    }

    /**
     * @return the senhaDto
     */
    public String getSenhaDto() {
        return senhaDto;
    }

    /**
     * @param senhaDto the senhaDto to set
     */
    public void setSenhaDto(String senhaDto) {
        this.senhaDto = senhaDto;
    }

    private Integer iduserDto;
    private String loginDto;
    private String senhaDto;

}
