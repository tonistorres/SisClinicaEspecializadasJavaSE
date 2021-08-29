package br.com.multclin.dto;

import br.com.multclin.metodosstatics.MetodoStaticosUtil;

/**
 *
 * @author DaTorres
 */
public class UsuarioDTO {

    private Integer idUserDto;
    private String cpfDto;
    private String UsuarioDto;
    private String celularPrincipalDto;
    private String loginDto;
    private String senhaDto;
    private String perfilDto;
    private String sexoDto;
    private String emailPrincipalDto;
    private String estadoDto;

    /**
     * @return the idUserDto
     */
    public Integer getIdUserDto() {
        return idUserDto;
    }

    /**
     * @param idUserDto the idUserDto to set
     */
    public void setIdUserDto(Integer idUserDto) {
        this.idUserDto = idUserDto;
    }

    /**
     * @return the UsuarioDto
     */
    public String getUsuarioDto() {
        return UsuarioDto;
    }

    /**
     * @param UsuarioDto the UsuarioDto to set
     */
    public void setUsuarioDto(String UsuarioDto) {
        this.UsuarioDto = MetodoStaticosUtil.removerAcentosCaixAlta(UsuarioDto);
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
        this.loginDto = MetodoStaticosUtil.removerAcentosCaixAlta(MetodoStaticosUtil.removerTodosEspacosEmBranco(loginDto));
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
     * @return the sexoDto
     */
    public String getSexoDto() {
        return sexoDto;
    }

    /**
     * @param sexoDto the sexoDto to set
     */
    public void setSexoDto(String sexoDto) {
        this.sexoDto = sexoDto;
    }

    /**
     * @return the celularPrincipalDto
     */
    public String getCelularPrincipalDto() {
        return celularPrincipalDto;
    }

    /**
     * @param celularPrincipalDto the celularPrincipalDto to set
     */
    public void setCelularPrincipalDto(String celularPrincipalDto) {
        this.celularPrincipalDto = celularPrincipalDto;
    }

    /**
     * @return the emailPrincipalDto
     */
    public String getEmailPrincipalDto() {
        return emailPrincipalDto;
    }

    /**
     * @param emailPrincipalDto the emailPrincipalDto to set
     */
    public void setEmailPrincipalDto(String emailPrincipalDto) {
        this.emailPrincipalDto = emailPrincipalDto;
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
     * @return the estadoDto
     */
    public String getEstadoDto() {
        return estadoDto;
    }

    /**
     * @param estadoDto the estadoDto to set
     */
    public void setEstadoDto(String estadoDto) {
        this.estadoDto = estadoDto;
    }
  
    
}
