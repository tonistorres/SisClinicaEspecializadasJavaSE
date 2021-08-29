package br.com.multclin.bo;

import br.com.multclin.dao.LoginDAO;
import br.com.multclin.dao.PaisDAO;
import br.com.multclin.dto.PaisDTO;
import br.com.multclin.exceptions.NegocioException;
import br.com.multclin.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

/**
 * Seguindo o padr�o de projeto Business Object - serve para termos uma camada
 * que possamos tratar valida��es entre outros assuntos inerentes a regras de
 * negocios de forma mais organizada AULA 19. Em sintese uma classe que se
 * responsabiliza apenas por regras de neg�cios
 */
public class PaisBO {

    PaisDAO paisDAO = new PaisDAO();
    
    public boolean validaNome(PaisDTO paisDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (paisDTO.getCodigoSMSDto() == null || paisDTO.getCodigoSMSDto().equals("")) {

            ehValido = false;
            throw new ValidacaoException("Campo Codigo Obrigatorio");

        }
        if (paisDTO.getCodigoSMSDto().length() > 5) {
            ehValido = false;
            throw new ValidacaoException("Campo nome aceita no MAX 5 chars");

        }

        return ehValido;
    }

    public void cadastrar(PaisDTO paisDTO) throws NegocioException {

        try {

            PaisDAO paisDAO = new PaisDAO();

            paisDAO.inserir(paisDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }
    }

    public void atualizarBO(PaisDTO paisDTO) {

        try {
         
            paisDAO.atualizar(paisDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }
    
    
      /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(PaisDTO paisDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        PaisDAO paisDAO;
        try {
            paisDAO = new PaisDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            paisDAO.deletar(paisDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }
}
