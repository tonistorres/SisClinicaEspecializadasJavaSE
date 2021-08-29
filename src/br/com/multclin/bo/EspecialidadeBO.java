package br.com.multclin.bo;

import br.com.multclin.dao.LoginDAO;
import br.com.multclin.dao.EspecialidadeDAO;
import br.com.multclin.dto.EspecialidadeDTO;
import br.com.multclin.exceptions.NegocioException;
import br.com.multclin.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

/**
 * Seguindo o padr�o de projeto Business Object - serve para termos uma camada
 * que possamos tratar valida��es entre outros assuntos inerentes a regras de
 * negocios de forma mais organizada AULA 19. Em sintese uma classe que se
 * responsabiliza apenas por regras de neg�cios
 */
public class EspecialidadeBO {

    EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
    
    public boolean validaNome(EspecialidadeDTO especialidadeDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (especialidadeDTO.getEspecialidadeDto().equalsIgnoreCase("Selecione...")) {

            ehValido = false;
            throw new ValidacaoException("Campo ESPECIALIDADE Obrigatorio");

        }
      
     
        
        return ehValido;
    }

    public void cadastrar(EspecialidadeDTO especialidadeDTO) throws NegocioException {

        try {

            EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

            especialidadeDAO.inserir(especialidadeDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }
    }

    public void atualizarBO(EspecialidadeDTO especialidadeDTO) {

        try {
         
            especialidadeDAO.atualizar(especialidadeDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }
    
    
      /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(EspecialidadeDTO especialidadeDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        EspecialidadeDAO especialidadeDAO;
        try {
            especialidadeDAO = new EspecialidadeDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            especialidadeDAO.deletar(especialidadeDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }
}
