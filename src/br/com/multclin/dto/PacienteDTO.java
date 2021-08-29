package br.com.multclin.dto;

import br.com.multclin.metodosstatics.MetodoStaticosUtil;
import com.toedter.calendar.JDateChooser;

public class PacienteDTO {

    //dados pessoais 

    private Integer idDto;//codigo do cliente
    private String nomeDto;//nome completo do cliente 
    private String dataNascDto;//data de nascimento do cliente
    private JDateChooser dataNascimentodtoJDateChooser;
    private String IdadeDto;//calcular a idade e guarda a idade do cliente 
    private String cpfDto;//cpf do cliente 
    private String sexoDto;//sexo do cliente 
    private String estadoCivilDto;//estado civil do cliente
    private String conjugeDto;//esposa ou esposo do cliente
    private String maeDto;//mae do cliente 
    private String paiDto;//pai do cliente 

    //contado
    private String celularPrefDto;//celular preferencial do cliente [vale apenas ressaltar que posso cadastra vários]
    private String emailPrefDto;//email preferencial do cliente 
 
    
     //dados residenciais
    private String ufDto; //estado do cliente 
    private String cidadeDto;//cidade do cliente 
    private String bairroDto;//bairro do cliente 
    private String ruaDto;//rua do cliente 
    private String complementoDto;//complemento
    private String nCasaDto;//numero da casa do cliente 
    private String CEPDto;//cep do cliente 

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
     * @return the dataNascDto
     */
    public String getDataNascDto() {
        return dataNascDto;
    }

    /**
     * @param dataNascDto the dataNascDto to set
     */
    public void setDataNascDto(String dataNascDto) {
        this.dataNascDto = dataNascDto;
    }

    /**
     * @return the IdadeDto
     */
    public String getIdadeDto() {
        return IdadeDto;
    }

    /**
     * @param IdadeDto the IdadeDto to set
     */
    public void setIdadeDto(String IdadeDto) {
        this.IdadeDto = IdadeDto;
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
     * @return the estadoCivilDto
     */
    public String getEstadoCivilDto() {
        return estadoCivilDto;
    }

    /**
     * @param estadoCivilDto the estadoCivilDto to set
     */
    public void setEstadoCivilDto(String estadoCivilDto) {
        this.estadoCivilDto = estadoCivilDto;
    }

    /**
     * @return the conjugeDto
     */
    public String getConjugeDto() {
        return conjugeDto;
    }

    /**
     * @param conjugeDto the conjugeDto to set
     */
    public void setConjugeDto(String conjugeDto) {
        this.conjugeDto = MetodoStaticosUtil.removerAcentosCaixAlta(conjugeDto);
    }

    /**
     * @return the maeDto
     */
    public String getMaeDto() {
        return maeDto;
    }

    /**
     * @param maeDto the maeDto to set
     */
    public void setMaeDto(String maeDto) {
        this.maeDto = MetodoStaticosUtil.removerAcentosCaixAlta(maeDto);
    }

    /**
     * @return the paiDto
     */
    public String getPaiDto() {
        return paiDto;
    }

    /**
     * @param paiDto the paiDto to set
     */
    public void setPaiDto(String paiDto) {
        this.paiDto = MetodoStaticosUtil.removerAcentosCaixAlta(paiDto);
    }

    /**
     * @return the celularPrefDto
     */
    public String getCelularPrefDto() {
        return celularPrefDto;
    }

    /**
     * @param celularPrefDto the celularPrefDto to set
     */
    public void setCelularPrefDto(String celularPrefDto) {
        this.celularPrefDto = celularPrefDto;
    }

    /**
     * @return the emailPrefDto
     */
    public String getEmailPrefDto() {
        return emailPrefDto;
    }

    /**
     * @param emailPrefDto the emailPrefDto to set
     */
    public void setEmailPrefDto(String emailPrefDto) {
        this.emailPrefDto = emailPrefDto;
    }

    /**
     * @return the ufDto
     */
    public String getUfDto() {
        return ufDto;
    }

    /**
     * @param ufDto the ufDto to set
     */
    public void setUfDto(String ufDto) {
        this.ufDto = ufDto;
    }

    /**
     * @return the cidadeDto
     */
    public String getCidadeDto() {
        return cidadeDto;
    }

    /**
     * @param cidadeDto the cidadeDto to set
     */
    public void setCidadeDto(String cidadeDto) {
        this.cidadeDto =MetodoStaticosUtil.removerAcentosCaixAlta(cidadeDto) ;
    }

    /**
     * @return the bairroDto
     */
    public String getBairroDto() {
        return bairroDto;
    }

    /**
     * @param bairroDto the bairroDto to set
     */
    public void setBairroDto(String bairroDto) {
        this.bairroDto = MetodoStaticosUtil.removerAcentosCaixAlta(bairroDto);
    }

    /**
     * @return the ruaDto
     */
    public String getRuaDto() {
        return ruaDto;
    }

    /**
     * @param ruaDto the ruaDto to set
     */
    public void setRuaDto(String ruaDto) {
        this.ruaDto = MetodoStaticosUtil.removerAcentosCaixAlta(ruaDto);
    }

    /**
     * @return the complementoDto
     */
    public String getComplementoDto() {
        return complementoDto;
    }

    /**
     * @param complementoDto the complementoDto to set
     */
    public void setComplementoDto(String complementoDto) {
        this.complementoDto =MetodoStaticosUtil.removerAcentosCaixAlta(complementoDto) ;
    }

    /**
     * @return the nCasaDto
     */
    public String getnCasaDto() {
        return nCasaDto;
    }

    /**
     * @param nCasaDto the nCasaDto to set
     */
    public void setnCasaDto(String nCasaDto) {
        this.nCasaDto = nCasaDto;
    }

    /**
     * @return the CEPDto
     */
    public String getCEPDto() {
        return CEPDto;
    }

    /**
     * @param CEPDto the CEPDto to set
     */
    public void setCEPDto(String CEPDto) {
        this.CEPDto = CEPDto;
    }

    /**
     * @return the dataNascimentodtoJDateChooser
     */
    public JDateChooser getDataNascimentodtoJDateChooser() {
        return dataNascimentodtoJDateChooser;
    }

    /**
     * @param dataNascimentodtoJDateChooser the dataNascimentodtoJDateChooser to set
     */
    public void setDataNascimentodtoJDateChooser(JDateChooser dataNascimentodtoJDateChooser) {
        this.dataNascimentodtoJDateChooser = dataNascimentodtoJDateChooser;
    }
    
}
