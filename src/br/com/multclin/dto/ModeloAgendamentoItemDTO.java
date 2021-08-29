
package br.com.multclin.dto;

import java.util.ArrayList;


public class ModeloAgendamentoItemDTO {

    
    private AgendamentoDTO modAgendamentoDto;
    private ItemAgendamentoDTO modItemDto;
    private ArrayList<ModeloAgendamentoItemDTO>listaModeloAgendaItemAgenda;

    /**
     * @return the modAgendamentoDto
     */
    public AgendamentoDTO getModAgendamentoDto() {
        return modAgendamentoDto;
    }

    /**
     * @param modAgendamentoDto the modAgendamentoDto to set
     */
    public void setModAgendamentoDto(AgendamentoDTO modAgendamentoDto) {
        this.modAgendamentoDto = modAgendamentoDto;
    }

    /**
     * @return the modItemDto
     */
    public ItemAgendamentoDTO getModItemDto() {
        return modItemDto;
    }

    /**
     * @param modItemDto the modItemDto to set
     */
    public void setModItemDto(ItemAgendamentoDTO modItemDto) {
        this.modItemDto = modItemDto;
    }

    /**
     * @return the listaModeloAgendaItemAgenda
     */
    public ArrayList<ModeloAgendamentoItemDTO> getListaModeloAgendaItemAgenda() {
        return listaModeloAgendaItemAgenda;
    }

    /**
     * @param listaModeloAgendaItemAgenda the listaModeloAgendaItemAgenda to set
     */
    public void setListaModeloAgendaItemAgenda(ArrayList<ModeloAgendamentoItemDTO> listaModeloAgendaItemAgenda) {
        this.listaModeloAgendaItemAgenda = listaModeloAgendaItemAgenda;
    }

    
    
}
