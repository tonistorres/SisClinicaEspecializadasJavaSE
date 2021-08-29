
package br.com.multclin.dto;

import java.util.ArrayList;


public class ModeloMedicoEspecialidadeDTO {

    
    private MedicoDTO modMedicoDto;
    private EspecialidadeDTO modEspecialidadeDto;
    private ArrayList<ModeloMedicoEspecialidadeDTO>listaMedicoEspecialidade;

    /**
     * @return the modMedicoDto
     */
    public MedicoDTO getModMedicoDto() {
        return modMedicoDto;
    }

    /**
     * @param modMedicoDto the modMedicoDto to set
     */
    public void setModMedicoDto(MedicoDTO modMedicoDto) {
        this.modMedicoDto = modMedicoDto;
    }

    /**
     * @return the modEspecialidadeDto
     */
    public EspecialidadeDTO getModEspecialidadeDto() {
        return modEspecialidadeDto;
    }

    /**
     * @param modEspecialidadeDto the modEspecialidadeDto to set
     */
    public void setModEspecialidadeDto(EspecialidadeDTO modEspecialidadeDto) {
        this.modEspecialidadeDto = modEspecialidadeDto;
    }

    /**
     * @return the listaMedicoEspecialidade
     */
    public ArrayList<ModeloMedicoEspecialidadeDTO> getListaMedicoEspecialidade() {
        return listaMedicoEspecialidade;
    }

    /**
     * @param listaMedicoEspecialidade the listaMedicoEspecialidade to set
     */
    public void setListaMedicoEspecialidade(ArrayList<ModeloMedicoEspecialidadeDTO> listaMedicoEspecialidade) {
        this.listaMedicoEspecialidade = listaMedicoEspecialidade;
    }

    
}
