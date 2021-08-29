package br.com.multclin.dto;

import java.util.Date;

public class AgendamentoDTO {

    private int idAutoDto;
    private int idDto;
    private int fk_pacienteDto;
    private int fk_medicoDto;
    //MEDICO
    private String nomeMedicoDto;
    private String especialidadeDto;
    //PACIENTE
    private String cpfDto;//cpf do cliente 
    private String nomeDto;//nome completo do cliente 
    private String dataNascDto;//data de nascimento do cliente
    private String sexoDto;//sexo do cliente 
    private String estadoCivilDto;//estado civil do cliente
    private String conjugeDto;//esposa ou esposo do cliente
    private String maeDto;//mae do cliente 
    private String paiDto;//pai do cliente 

    //CONTATO PACIENTE
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

    //AGENDAMENTO 
    private Date dataAgendamentoDto;
    private String observacaoAgendaDto;
    private String statusAgendamentoDto;
    private double rsTotalAgendamentoDto;
    private String cpuDto;
    private String hdDto;
    private String usuarioDto;
    private String perfilDto;
    private String dtRegistroDto;

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
        this.conjugeDto = conjugeDto;
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
        this.maeDto = maeDto;
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
        this.paiDto = paiDto;
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
        this.cidadeDto = cidadeDto;
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
        this.bairroDto = bairroDto;
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
        this.ruaDto = ruaDto;
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
        this.complementoDto = complementoDto;
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
     * @return the observacaoAgendaDto
     */
    public String getObservacaoAgendaDto() {
        return observacaoAgendaDto;
    }

    /**
     * @param observacaoAgendaDto the observacaoAgendaDto to set
     */
    public void setObservacaoAgendaDto(String observacaoAgendaDto) {
        this.observacaoAgendaDto = observacaoAgendaDto;
    }

    /**
     * @return the statusAgendamentoDto
     */
    public String getStatusAgendamentoDto() {
        return statusAgendamentoDto;
    }

    /**
     * @param statusAgendamentoDto the statusAgendamentoDto to set
     */
    public void setStatusAgendamentoDto(String statusAgendamentoDto) {
        this.statusAgendamentoDto = statusAgendamentoDto;
    }

    /**
     * @return the rsTotalAgendamentoDto
     */
//    public String getRsTotalAgendamentoDto() {
//        return rsTotalAgendamentoDto;
//    }
    /**
     * @param rsTotalAgendamentoDto the rsTotalAgendamentoDto to set
     */
//    public void setRsTotalAgendamentoDto(String rsTotalAgendamentoDto) {
//        this.rsTotalAgendamentoDto = rsTotalAgendamentoDto;
//    }
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
     * @return the cpuDto
     */
    public String getCpuDto() {
        return cpuDto;
    }

    /**
     * @param cpuDto the cpuDto to set
     */
    public void setCpuDto(String cpuDto) {
        this.cpuDto = cpuDto;
    }

    /**
     * @return the hdDto
     */
    public String getHdDto() {
        return hdDto;
    }

    /**
     * @param hdDto the hdDto to set
     */
    public void setHdDto(String hdDto) {
        this.hdDto = hdDto;
    }

    /**
     * @return the usuarioDto
     */
    public String getUsuarioDto() {
        return usuarioDto;
    }

    /**
     * @param usuarioDto the usuarioDto to set
     */
    public void setUsuarioDto(String usuarioDto) {
        this.usuarioDto = usuarioDto;
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
     * @return the rsTotalAgendamentoDto
     */
    public double getRsTotalAgendamentoDto() {
        return rsTotalAgendamentoDto;
    }

    /**
     * @param rsTotalAgendamentoDto the rsTotalAgendamentoDto to set
     */
    public void setRsTotalAgendamentoDto(double rsTotalAgendamentoDto) {
        this.rsTotalAgendamentoDto = rsTotalAgendamentoDto;
    }

    /**
     * @return the dtRegistroDto
     */
    public String getDtRegistroDto() {
        return dtRegistroDto;
    }

    /**
     * @param dtRegistroDto the dtRegistroDto to set
     */
    public void setDtRegistroDto(String dtRegistroDto) {
        this.dtRegistroDto = dtRegistroDto;
    }

    /**
     * @return the dataAgendamentoDto
     */
    public Date getDataAgendamentoDto() {
        return dataAgendamentoDto;
    }

    /**
     * @param dataAgendamentoDto the dataAgendamentoDto to set
     */
    public void setDataAgendamentoDto(Date dataAgendamentoDto) {
        this.dataAgendamentoDto =new java.sql.Date( dataAgendamentoDto.getTime());
    }


}
