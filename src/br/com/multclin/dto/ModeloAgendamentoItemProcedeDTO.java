
package br.com.multclin.dto;

import java.util.ArrayList;


public class ModeloAgendamentoItemProcedeDTO {

    
    private AgendamentoDTO modAgendamentoDto;
    private ItemAgendamentoDTO modItemDto;
    private ProcedimentoDTO modProcedeDto;
    private ArrayList<ModeloAgendamentoItemProcedeDTO>listaModeloAgendaItemAgendaProcede;

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
     * @return the modProcedeDto
     */
    public ProcedimentoDTO getModProcedeDto() {
        return modProcedeDto;
    }

    /**
     * @param modProcedeDto the modProcedeDto to set
     */
    public void setModProcedeDto(ProcedimentoDTO modProcedeDto) {
        this.modProcedeDto = modProcedeDto;
    }

    /**
     * @return the listaModeloAgendaItemAgendaProcede
     */
    public ArrayList<ModeloAgendamentoItemProcedeDTO> getListaModeloAgendaItemAgendaProcede() {
        return listaModeloAgendaItemAgendaProcede;
    }

    /**
     * @param listaModeloAgendaItemAgendaProcede the listaModeloAgendaItemAgendaProcede to set
     */
    public void setListaModeloAgendaItemAgendaProcede(ArrayList<ModeloAgendamentoItemProcedeDTO> listaModeloAgendaItemAgendaProcede) {
        this.listaModeloAgendaItemAgendaProcede = listaModeloAgendaItemAgendaProcede;
    }

  
    
    
}
