package br.com.multclin.dao;

import br.com.multclin.dto.EspecialidadeDTO;
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
public class EspecialidadeDAO implements GenericDAO<EspecialidadeDTO> {

    @Override
    public List<EspecialidadeDTO> listarTodos() throws PersistenciaException {

        List<EspecialidadeDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbEspecialidades order by nome";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

                especialidadeDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                especialidadeDTO.setIdDto(resultSet.getInt("id"));
                especialidadeDTO.setNomeDto(resultSet.getString("nome"));
                especialidadeDTO.setCpfDto(resultSet.getString("cpf"));
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));

                /**
                 * Adiciona na lista todos os dados capturado pelo laço e
                 * adicionado no objeto especialidadeDTO
                 */
                lista.add(especialidadeDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        /*retorna a lista */
        return lista;

    }

    public List<EspecialidadeDTO> filtrarEspecialidadeID(String especialidade, int id) throws PersistenciaException {

        List<EspecialidadeDTO> lista = new ArrayList<EspecialidadeDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbEspecialidades WHERE especialidade LIKE '%" + especialidade + "%'AND id=" + id;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

                especialidadeDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));
                lista.add(especialidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<EspecialidadeDTO> filtrarPesqRapidaPorCPF(String pesquisar) throws PersistenciaException {

        List<EspecialidadeDTO> lista = new ArrayList<EspecialidadeDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbEspecialidades WHERE cpf LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

                especialidadeDTO.setIdDto(resultSet.getInt("id"));
                especialidadeDTO.setNomeDto(resultSet.getString("nome"));
                especialidadeDTO.setCpfDto(resultSet.getString("cpf"));
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));
                lista.add(especialidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<EspecialidadeDTO> filtrarPesqRapida(String pesquisar) throws PersistenciaException {

        List<EspecialidadeDTO> lista = new ArrayList<EspecialidadeDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbEspecialidades WHERE nome LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

                especialidadeDTO.setIdDto(resultSet.getInt("id"));
                especialidadeDTO.setNomeDto(resultSet.getString("nome"));
                especialidadeDTO.setCpfDto(resultSet.getString("cpf"));
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));

                lista.add(especialidadeDTO);

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    public List<EspecialidadeDTO> buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        List<EspecialidadeDTO> lista = new ArrayList<EspecialidadeDTO>();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbEspecialidades where id=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

                especialidadeDTO.setIdDto(resultSet.getInt("id"));
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));
                System.out.println("id: " + resultSet.getInt("id") + " Especialidade: " + resultSet.getString("especialidade"));

                lista.add(especialidadeDTO);
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            
        }

        return lista;

    }

    public EspecialidadeDTO buscarPorIdRetornaObjeto(int codigo) throws PersistenciaException {

        EspecialidadeDTO especialidadeDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbEspecialidades where id=" + codigo;
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
                especialidadeDTO = new EspecialidadeDTO();

                especialidadeDTO.setIdDto(resultSet.getInt("id"));
                especialidadeDTO.setNomeDto(resultSet.getString("nome"));
                especialidadeDTO.setCpfDto(resultSet.getString("cpf"));
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));

                return especialidadeDTO;
            }

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
            
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
    public void inserir(EspecialidadeDTO especialidadeDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "INSERT INTO tbEspecialidades(id,fk_cadEspecialidade,nome,cpf,especialidade,conselho,perfil,cadastro) VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, especialidadeDTO.getIdDto());//id
            statement.setInt(2, especialidadeDTO.getFk_cadEspecialidadeDto());//fk_especialidade
            statement.setString(3, especialidadeDTO.getNomeDto());//nome
            statement.setString(4, especialidadeDTO.getCpfDto());//cpf
            statement.setString(5, especialidadeDTO.getEspecialidadeDto());//especialidade
            statement.setString(6, especialidadeDTO.getConselhoDto());//conselho 
            statement.setString(7, especialidadeDTO.getPerfilDto());//perfil

            statement.setDate(8, null);

            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(EspecialidadeDTO especialidadeDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "UPDATE tbEspecialidades SET fk_cadEspecialidade=?,nome=?,cpf=?,especialidade=?,conselho=?,perfil=? WHERE idAuto=?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, especialidadeDTO.getFk_cadEspecialidadeDto());//nome

            statement.setString(2, especialidadeDTO.getNomeDto());//nome
            statement.setString(3, especialidadeDTO.getCpfDto());//cpf
            statement.setString(4, especialidadeDTO.getEspecialidadeDto());//especialidade
            statement.setString(5, especialidadeDTO.getConselhoDto());
            statement.setString(6, especialidadeDTO.getPerfilDto());

            statement.setInt(7, especialidadeDTO.getIdAutoDto());

            statement.execute();

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }
    }

    @Override
    public void deletar(EspecialidadeDTO obj) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "DELETE FROM tbEspecialidades WHERE idAuto=?";

            PreparedStatement statement;
            statement = connection.prepareStatement(sql);
            statement.setInt(1, obj.getIdAutoDto());
            statement.execute();

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (ex.getMessage().equals("Cannot delete or update a parent row: a foreign key constraint fails (`inovec87_sishanna`.`tbMedicos`, CONSTRAINT `tbMedicos_ibfk_1` FOREIGN KEY (`id`) REFERENCES `tbEspecialidades` (`id`))")) {
                JOptionPane.showMessageDialog(null, "" + "\n Info Camada DAO:\n"
                        + "Registro de [especialidades] relacionado\n"
                        + "com Registro de [Médico] impossibilitando\n"
                        + "a deleção."
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
    public EspecialidadeDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EspecialidadeDTO buscarPor(EspecialidadeDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(EspecialidadeDTO especialidadeDTO) throws PersistenciaException {
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
            String sql = "SELECT *FROM tbEspecialidades where cpf='" + especialidadeDTO.getCpfDto() + "'";

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
           
        }

        return nomeDuplicado;

    }

    public boolean verificaDuplicidadeCPF(EspecialidadeDTO especialidadeDTO) throws PersistenciaException {
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
            String sql = "SELECT *FROM tbEspecialidades where CPF='" + especialidadeDTO.getCpfDto() + "'";

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
            
        }

        return nomeDuplicado;

    }

    @Override
    public EspecialidadeDTO filtrarAoClicar(EspecialidadeDTO modelo) throws PersistenciaException {

        EspecialidadeDTO especialidadeDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbEspecialidades WHERE nome LIKE'%" + modelo.getNomeDto() + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                /*
                 * Dentro do loop não se esquecer de inicializar o objeto como abaixo, caso
                 * contrário erro de exception
                 */
                especialidadeDTO = new EspecialidadeDTO();

                especialidadeDTO.setIdDto(resultSet.getInt("id"));
                especialidadeDTO.setNomeDto(resultSet.getString("nome"));
                especialidadeDTO.setCpfDto(resultSet.getString("cpf"));
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));

                return especialidadeDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
         
        }

        return null;

    }

    @Override
    public boolean inserirControlEmail(EspecialidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(EspecialidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(EspecialidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(EspecialidadeDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<EspecialidadeDTO> listarTodosParametro(String str) throws PersistenciaException {
        /**
         * Manipulando um objeto Array List da lista de Pessoas
         */
        List<EspecialidadeDTO> lista = new ArrayList<EspecialidadeDTO>();

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbEspecialidades WHERE cpf LIKE '%" + str + "%'";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                EspecialidadeDTO especialidadeDTO = new EspecialidadeDTO();

                especialidadeDTO.setIdAutoDto(resultSet.getInt("idAuto"));
                especialidadeDTO.setIdDto(resultSet.getInt("id"));
                especialidadeDTO.setNomeDto(resultSet.getString("nome"));
                especialidadeDTO.setCpfDto(resultSet.getString("cpf"));
                especialidadeDTO.setEspecialidadeDto(resultSet.getString("especialidade"));

                lista.add(especialidadeDTO);

            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);
        }

        return lista;
    }

}
