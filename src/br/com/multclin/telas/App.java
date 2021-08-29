package br.com.multclin.telas;

import br.com.multclin.dao.HistoricoPacienteDAO;
import br.com.multclin.dto.HistoricoPacienteDTO;
import br.com.multclin.exceptions.PersistenciaException;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.save;
import static br.com.multclin.telas.Modulo2HistoricoPaciente.txtAreaDiagnostico;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws PersistenciaException {
        lancarModeloPrescrito();
    }

    public static void lancarModeloPrescrito() throws PersistenciaException {

        HistoricoPacienteDTO historicoDTO = new HistoricoPacienteDTO();
        HistoricoPacienteDAO historicoDAO = new HistoricoPacienteDAO();

        String Diagnostico = "";
        int codigo = 89;

        ArrayList<HistoricoPacienteDTO> list;
        try {

            list = (ArrayList<HistoricoPacienteDTO>) historicoDAO.buscarPorIdTblConsultaList(codigo);

            for (int i = 0; i < list.size(); i++) {

                Diagnostico = list.get(i).getDiagnosticoDto();
            }

            
            
            System.out.println(Diagnostico.substring(0, 19)+"12/12/20"+Diagnostico.substring(30, 36));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
