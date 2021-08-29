package br.com.multclin.bo;

import br.com.multclin.dao.LoginDAO;
import br.com.multclin.dao.FoneDAO;
import br.com.multclin.dto.FoneDTO;
import br.com.multclin.exceptions.NegocioException;
import br.com.multclin.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

/**
 * Seguindo o padr�o de projeto Business Object - serve para termos uma camada
 * que possamos tratar valida��es entre outros assuntos inerentes a regras de
 * negocios de forma mais organizada AULA 19. Em sintese uma classe que se
 * responsabiliza apenas por regras de neg�cios
 */
public class FoneBO {

    FoneDAO foneDAO = new FoneDAO();
    
    public boolean validaCustomizado(FoneDTO foneDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (foneDTO.getFoneDto().equals("(  )     -    ")) {

            ehValido = false;
            throw new ValidacaoException("Campo Fone Obrigatorio");

        }
       
        
        return ehValido;
    }

    
    
    
    
    
    
    
    public void cadastrar(FoneDTO foneDTO) throws NegocioException {

        try {

            FoneDAO foneDAO = new FoneDAO();

            foneDAO.inserir(foneDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }
    }

    public void atualizarBO(FoneDTO foneDTO) {

        try {
         
            foneDAO.atualizar(foneDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }
    
    
      /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(FoneDTO foneDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        FoneDAO foneDAO;
        try {
            foneDAO = new FoneDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            foneDAO.deletar(foneDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }
}
