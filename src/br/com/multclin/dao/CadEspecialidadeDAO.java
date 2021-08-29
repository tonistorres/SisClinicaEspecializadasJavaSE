package br.com.multclin.dao;

import br.com.multclin.dto.CadEspecialidadeDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author DaTorres
 */
public class CadEspecialidadeDAO implements GenericDAO<CadEspecialidadeDTO> {

    @Override
    public List<CadEspecialidadeDTO> listarTodos() throws PersistenciaException {

        List<CadEspecialidadeDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbCadastroEspecialidades";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CadEspecialidadeDTO cadEspecialidadeDTO = new CadEspecialidadeDTO();

                cadEspecialidadeDTO.setIdDto(resultSet.getInt("id"));
                cadEspecialidadeDTO.setNomeDto(resultSet.getString("nome"));

                lista.add(cadEspecialidadeDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return lista;

    }

    public List<CadEspecialidadeDTO> filtrarPesqRapida(String pesquisar) throws PersistenciaException {

        List<CadEspecialidadeDTO> lista = new ArrayList<CadEspecialidadeDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbCadastroEspecialidades WHERE nome LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                CadEspecialidadeDTO cadEspecialidadeDTO = new CadEspecialidadeDTO();

                cadEspecialidadeDTO.setIdDto(resultSet.getInt("id"));
                cadEspecialidadeDTO.setNomeDto(resultSet.getString("nome"));

                lista.add(cadEspecialidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }

    public CadEspecialidadeDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        CadEspecialidadeDTO cadEspecialidadeDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbCadastroEspecialidades where id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                cadEspecialidadeDTO = new CadEspecialidadeDTO();

                cadEspecialidadeDTO.setIdDto(resultSet.getInt("id"));
                cadEspecialidadeDTO.setNomeDto(resultSet.getString("nome"));

                return cadEspecialidadeDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    private void erroMsg(String mensagemErro) {

        String meuEmail = "sisprotocoloj@gmail.com";
        String minhaSenha = "gerlande2111791020";

        //agora iremos utilizar os objetos das Bibliotecas referente Email
        //commons-email-1.5.jar e mail-1.4.7.jar
        SimpleEmail email = new SimpleEmail();
        //Agora podemos utilizar o objeto email do Tipo SimplesEmail
        //a primeira cois a fazer é acessar o host abaixo estarei usando
        //o host do google para enviar as informações
        email.setHostName("smtp.gmail.com");
        //O proximo método abaixo é para configurar a porta desse host
        email.setSmtpPort(465);
        //esse método vai autenticar a conexão o envio desse email
        email.setAuthenticator(new DefaultAuthenticator(meuEmail, minhaSenha));
        //esse método vai ativar a conexao de forma segura 
        email.setSSLOnConnect(true);

        //agora um try porque os outro métodos podem gerar erros daí devem está dentro de um bloco de exceções
        try {
            //configura de quem é esse email de onde ele está vindo 
            email.setFrom(meuEmail);
            //configurar o assunto 
            email.setSubject("Erro Camada GUI - Metodo:SalvarAdicoesEdicoes()");
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Erro:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

            //enviando mensagem de confirmação Usuário
            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Todos Procedimentos concluídos com Sucesso."
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro ao Enviar Email." + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            e.printStackTrace();
        }

    }

    @Override
    public void inserir(CadEspecialidadeDTO cadEspecialidadeDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "INSERT INTO tbCadastroEspecialidades(id,nome,cadastro) VALUES(?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, cadEspecialidadeDTO.getIdDto());//id
            statement.setString(2, cadEspecialidadeDTO.getNomeDto());//nome
            statement.setDate(3, null);

            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(CadEspecialidadeDTO cadEspecialidadeDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "UPDATE tbCadastroEspecialidades SET nome=? WHERE id=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, cadEspecialidadeDTO.getNomeDto());//nome
            statement.setInt(2, cadEspecialidadeDTO.getIdDto());

            /**
             * executar o statement
             */
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deletar(CadEspecialidadeDTO obj) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "DELETE FROM tbCadastroEspecialidades WHERE id=?";

            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setInt(1, obj.getIdDto());
            statement.execute();

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
                                      
            if (ex.getMessage().equals("Cannot delete or update a parent row: a foreign key constraint fails (`inovec87_sishanna`.`tbProcedimentos`, CONSTRAINT `fk_tbProcedimentos_tbCadastroEspecialidades1` FOREIGN KEY (`id`) REFERENCES `tbCadastroEspecialidades` (`id`) ON DELETE NO ACTION ON UPDATE N)")) {

                JOptionPane.showMessageDialog(null, "" + "\n Info:\n"
                        + "O Registro não pode ser excluído devido um forte \n"
                        + "relacionamento entre as Entidades Cadastro de \n"
                        + "Especialidade  e Cadastro de Procedimentos Médicos.\n"
                        + "Demo.:[Especialidade]<-Relacionado---> [Procedimento].\n"
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

                                      //
            if (ex.getMessage().equals("Cannot delete or update a parent row: a foreign key constraint fails (`inovec87_sishanna`.`tbEspecialidades`, CONSTRAINT `fk_tbEspecialidades_tbCadastroEspecialidades1` FOREIGN KEY (`fk_cadEspecialidade`) REFERENCES `tbCadastroEspecialidades` (`id`) ON DELETE NO)")) {

                JOptionPane.showMessageDialog(null, "" + "\n Info:\n"
                        + "O    Procedimento    que   está   tentando    executar,\n"
                        + "não  poderá  ocorrer  devido  a    um    relacionamento\n"
                        + "entre  as   Entidades   Cadastro de    Especialidade  e \n"
                        + "Cadastro de Especialidade Médica.\n"
                        + "Demo.:[CadEspecialidade]<-Relacionado---> [tbEspecialidade].\n"
                        + "O  Banco  de  Dados   está  fortemente  relacionado.\n"
                        + "De forma que um registro não  pode ser excluído quando\n"
                        + "outro depende do seu vínculo."
                        + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));

            }

        }
    }

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CadEspecialidadeDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CadEspecialidadeDTO buscarPor(CadEspecialidadeDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(CadEspecialidadeDTO cadEspecialidadeDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna nomeDuplicado=true(Verdadeiro). Caso contrário
         * nomeDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean nomeDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbCadastroEspecialidades where nome='" + cadEspecialidadeDTO.getNomeDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                nomeDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return nomeDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return nomeDuplicado;

    }

    public boolean verificaDuplicidadeCPF(CadEspecialidadeDTO cadEspecialidadeDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna nomeDuplicado=true(Verdadeiro). Caso contrário
         * nomeDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean nomeDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbCadastroEspecialidades where nome='" + cadEspecialidadeDTO.getNomeDto() + "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                nomeDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return nomeDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return nomeDuplicado;

    }

    @Override
    public CadEspecialidadeDTO filtrarAoClicar(CadEspecialidadeDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        CadEspecialidadeDTO cadEspecialidadeDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbCadastroEspecialidades WHERE nome LIKE'%" + modelo.getNomeDto() + "%'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
                cadEspecialidadeDTO = new CadEspecialidadeDTO();

                cadEspecialidadeDTO.setIdDto(resultSet.getInt("id"));
                cadEspecialidadeDTO.setNomeDto(resultSet.getString("nome"));

                return cadEspecialidadeDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    @Override
    public boolean inserirControlEmail(CadEspecialidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(CadEspecialidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(CadEspecialidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(CadEspecialidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CadEspecialidadeDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
