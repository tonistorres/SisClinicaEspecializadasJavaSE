package br.com.multclin.bo;

import br.com.multclin.dao.LoginDAO;
import br.com.multclin.dto.LoginDTO;
import br.com.multclin.exceptions.NegocioException;
import javax.swing.JOptionPane;

/**
 * Seguindo o padr�o de projeto Business Object - serve para termos uma camada
 * que possamos tratar valida��es entre outros assuntos inerentes a regras de
 * negocios de forma mais organizada AULA 19. Em sintese uma classe que se
 * responsabiliza apenas por regras de neg�cios
 */
public class LoginBO {

    /**
     * AULA 19 DEVMEDIA INTRODUÇÃO AO JBDC - comenta sobre o esse METODO
     */
    public boolean logar(LoginDTO loginDTO) throws NegocioException {
        /**
         * criamos uma flag resultado e atribuiamos a ELA o valor boolean false
         * onde entrara nos ifs fazendo teste e conforme os testes feitos
         * poderemos ter um resultdo true ou permanecer false
         */
        boolean resultado = false;

        try {
            /**
             * Se o login for igual a null OU Login igual a vazio (se usu�rio
             * nao estiver digitado nada )
             */
            if (loginDTO.getLoginDto()== null || "".equals(loginDTO.getLoginDto())) {
                /**
                 * Iremos lan�ar uma exce��o dizendo que os dados preenchidos
                 * n�o est�o corretos
                 */

                //https://brunoagt.wordpress.com/2011/03/28/javax-swing-joptionpane-conhecendo-e-utilizando-a-classe-joptionpane/
                //JOptionPane.showMessageDialog(null, "Usuário (Login)é obrigatório", "Alerta", JOptionPane.INFORMATION_MESSAGE);

                throw new NegocioException("Login Obrigatorio!");
            } else if (loginDTO.getSenhaDto()== null || "".equals(loginDTO.getSenhaDto())) {
                /**
                 * Iremos lancar uma excecao dizendo que os dados preenchidos
                 * nao estao corretos
                 */

                //https://brunoagt.wordpress.com/2011/03/28/javax-swing-joptionpane-conhecendo-e-utilizando-a-classe-joptionpane/
               // JOptionPane.showMessageDialog(null, "Senha é obrigatória", "Alerta", JOptionPane.INFORMATION_MESSAGE);
                  throw new NegocioException("Senha Obrigatorio!");
            } else {
                /**
                 * Vamos criar um objeto de LoginDAO
                 */
                LoginDAO loginDAO = new LoginDAO();

                resultado = loginDAO.logar(loginDTO);
                
               
                if(resultado==false){
                    //https://brunoagt.wordpress.com/2011/03/28/javax-swing-joptionpane-conhecendo-e-utilizando-a-classe-joptionpane/
                JOptionPane.showMessageDialog(null, "Usuário não Cadastrado", "Alerta", JOptionPane.INFORMATION_MESSAGE);
                    
                }
            }

        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }

        return resultado;
    }
}
