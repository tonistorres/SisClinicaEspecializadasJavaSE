/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multclin.dto;

/**
 *
 * @author Dã Torres
 */
public class ReconhecimentoDTO {
    private int id_reconhecimentoDto;
    private String dt_hora_conectouDto;
    private String serialHdDto;
    private String serialCPUDto;
    private String serial_placa_maeDto;
    private String informacoes_diversasDto;
    private String liberado_bloqueadoDto;
    private String usuario_responsavel_cadastro;

    /**
     * @return the id_reconhecimentoDto
     */
    public int getId_reconhecimentoDto() {
        return id_reconhecimentoDto;
    }

  
    /**
     * @return the dt_hora_conectouDto
     */
    public String getDt_hora_conectouDto() {
        return dt_hora_conectouDto;
    }

    /**
     * @param dt_hora_conectouDto the dt_hora_conectouDto to set
     */
    public void setDt_hora_conectouDto(String dt_hora_conectouDto) {
        this.dt_hora_conectouDto = dt_hora_conectouDto;
    }

    /**
     * @return the serialHdDto
     */
    public String getSerialHdDto() {
        return serialHdDto;
    }

    /**
     * @param serialHdDto the serialHdDto to set
     */
    public void setSerialHdDto(String serialHdDto) {
        this.serialHdDto = serialHdDto;
    }

    /**
     * @return the serialCPUDto
     */
    public String getSerialCPUDto() {
        return serialCPUDto;
    }

    /**
     * @param serialCPUDto the serialCPUDto to set
     */
    public void setSerialCPUDto(String serialCPUDto) {
        this.serialCPUDto = serialCPUDto;
    }

    /**
     * @return the serial_placa_maeDto
     */
    public String getSerial_placa_maeDto() {
        return serial_placa_maeDto;
    }

    /**
     * @param serial_placa_maeDto the serial_placa_maeDto to set
     */
    public void setSerial_placa_maeDto(String serial_placa_maeDto) {
        this.serial_placa_maeDto = serial_placa_maeDto;
    }

    /**
     * @return the informacoes_diversasDto
     */
    public String getInformacoes_diversasDto() {
        return informacoes_diversasDto;
    }

    /**
     * @param informacoes_diversasDto the informacoes_diversasDto to set
     */
    public void setInformacoes_diversasDto(String informacoes_diversasDto) {
        this.informacoes_diversasDto = informacoes_diversasDto;
    }

    /**
     * @return the liberado_bloqueadoDto
     */
    public String getLiberado_bloqueadoDto() {
        return liberado_bloqueadoDto;
    }

    /**
     * @param liberado_bloqueadoDto the liberado_bloqueadoDto to set
     */
    public void setLiberado_bloqueadoDto(String liberado_bloqueadoDto) {
        this.liberado_bloqueadoDto = liberado_bloqueadoDto;
    }

    /**
     * @param id_reconhecimentoDto the id_reconhecimentoDto to set
     */
    public void setId_reconhecimentoDto(int id_reconhecimentoDto) {
        this.id_reconhecimentoDto = id_reconhecimentoDto;
    }

    /**
     * @return the usuario_responsavel_cadastro
     */
    public String getUsuario_responsavel_cadastro() {
        return usuario_responsavel_cadastro;
    }

    /**
     * @param usuario_responsavel_cadastro the usuario_responsavel_cadastro to set
     */
    public void setUsuario_responsavel_cadastro(String usuario_responsavel_cadastro) {
        this.usuario_responsavel_cadastro = usuario_responsavel_cadastro;
    }
}
