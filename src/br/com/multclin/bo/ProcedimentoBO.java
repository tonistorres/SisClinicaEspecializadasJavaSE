package br.com.multclin.bo;

import br.com.multclin.dao.LoginDAO;
import br.com.multclin.dao.ProcedimentoDAO;
import br.com.multclin.dto.ProcedimentoDTO;
import br.com.multclin.exceptions.NegocioException;
import br.com.multclin.exceptions.ValidacaoException;
import javax.swing.JOptionPane;

/**
 * Seguindo o padr�o de projeto Business Object - serve para termos uma camada
 * que possamos tratar valida��es entre outros assuntos inerentes a regras de
 * negocios de forma mais organizada AULA 19. Em sintese uma classe que se
 * responsabiliza apenas por regras de neg�cios
 */
public class ProcedimentoBO {

    ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();
    
    public boolean validaNome(ProcedimentoDTO procedimentoDTO) throws ValidacaoException {

        boolean ehValido = true;

        if (procedimentoDTO.getNomeDto()==null||procedimentoDTO.getNomeDto().isEmpty()) {

            ehValido = false;
            throw new ValidacaoException("Campo ESPECIALIDADE Obrigatorio");

        }
    
        
        return ehValido;
    }

    public void cadastrar(ProcedimentoDTO procedimentoDTO) throws NegocioException {

        try {

            ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO();

            procedimentoDAO.inserir(procedimentoDTO);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            //throw new NegocioException(e.getMessage());
        }
    }

    public void atualizarBO(ProcedimentoDTO procedimentoDTO) {

        try {
         
            procedimentoDAO.atualizar(procedimentoDTO);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Camada BO:\n" + e.getMessage());

        }
    }
    
    
      /**
     * Se vamos cadastrar alguma coisa não precisamos retornar nada entao
     * utilizamos o método void() e passamos como parâmetro o DTO em questão
     */
    public void ExcluirBO(ProcedimentoDTO procedimentoDTO) {
        /**
         * 1º Vamos criar um objeto de PessoaDAO
         */
        ProcedimentoDAO procedimentoDAO;
        try {
            procedimentoDAO = new ProcedimentoDAO();
            /*
			 * Por meio do objeto pessoaDAO iremos chamar o método inserir() passando como
			 * argumento PessoaDTO, o método inserir encontra-se dentro da classe pessoaDAO
             */

            procedimentoDAO.deletar(procedimentoDTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "MENSAGEM DE ERRO:Método ExcluirBO()\n" + e.getMessage());
        }
    }
}
