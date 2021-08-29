package br.com.multclin.dao;

import br.com.multclin.dto.UsuarioDTO;
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
public class UsuarioDAO implements GenericDAO<UsuarioDTO> {

    /**
     * Método Para listar todos os Usuários de uma tabela
     */
    @Override
    public List<UsuarioDTO> listarTodos() throws PersistenciaException {

        List<UsuarioDTO> listaDeUsuarios = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios order by usuario and perfil asc";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                UsuarioDTO usuarioDTO = new UsuarioDTO();

                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setCelularPrincipalDto(resultSet.getString("celular_principal"));
                usuarioDTO.setLoginDto(resultSet.getString("login"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                usuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                usuarioDTO.setSexoDto(resultSet.getString("sexo"));
                usuarioDTO.setEmailPrincipalDto(resultSet.getString("email_principal"));
                usuarioDTO.setEstadoDto(resultSet.getString("estado"));
                /**
                 * Adiciona na listaDeUsuarios todos os dados capturado pelo
                 * laço e adicionado no objeto usuarioDTO
                 */
                listaDeUsuarios.add(usuarioDTO);

            }

            /**
             * fecha a conexão
             */
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        /*retorna a lista */
        return listaDeUsuarios;

    }

    public List<UsuarioDTO> filtrarUsuarioPesqRapida(String pesquisarUsuarios) throws PersistenciaException {

        List<UsuarioDTO> listaDeUsuario = new ArrayList<UsuarioDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios WHERE login LIKE '%" + pesquisarUsuarios + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                UsuarioDTO usuarioDTO = new UsuarioDTO();

               
                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setCelularPrincipalDto(resultSet.getString("celular_principal"));
                usuarioDTO.setLoginDto(resultSet.getString("login"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                usuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                usuarioDTO.setSexoDto(resultSet.getString("sexo"));
                usuarioDTO.setEmailPrincipalDto(resultSet.getString("email_principal"));
                usuarioDTO.setEstadoDto(resultSet.getString("estado"));

                listaDeUsuario.add(usuarioDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeUsuario;
        //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Esse método que recebe como parâmetro um numero do tipo inteiro irá fazer
     * uma pesquisa no Banco de Dados Infoq na tabela de usuaios e em seguida
     * retorna o usuario encontrado caso exista, pois, trata-se de um método com
     * retorno do tipo UsuarioDTO e que recebe como parâmetro um número inteiro
     * vindo de uma JTable
     */
    public UsuarioDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        UsuarioDTO usuarioDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios where iduser=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                usuarioDTO = new UsuarioDTO();

               
                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setCpfDto(resultSet.getString("CPF"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setCelularPrincipalDto(resultSet.getString("celular_principal"));
                usuarioDTO.setLoginDto(resultSet.getString("login"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                usuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                usuarioDTO.setSexoDto(resultSet.getString("sexo"));
                usuarioDTO.setEmailPrincipalDto(resultSet.getString("email_principal"));
                usuarioDTO.setEstadoDto(resultSet.getString("estado"));
                
                return usuarioDTO;
            }

            connection.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    public List<UsuarioDTO> buscarPerfilRetornarList(String pesquisarUsuarios) throws PersistenciaException {

        List<UsuarioDTO> listaDeUsuario = new ArrayList<UsuarioDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios WHERE perfil LIKE '%" + pesquisarUsuarios + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                UsuarioDTO usuarioDTO = new UsuarioDTO();

                
                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                usuarioDTO.setCelularPrincipalDto(resultSet.getString("celular_principal"));
                usuarioDTO.setLoginDto(resultSet.getString("login"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                usuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                usuarioDTO.setSexoDto(resultSet.getString("sexo"));
                usuarioDTO.setEmailPrincipalDto(resultSet.getString("email_principal"));
                usuarioDTO.setEstadoDto(resultSet.getString("estado"));
                listaDeUsuario.add(usuarioDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return listaDeUsuario;
        //To change body of generated methods, choose Tools | Templates.
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
    public void inserir(UsuarioDTO usuarioDTO) throws PersistenciaException {
        try {

            /**
             * Dentro do bloco try catch temos um objeto do tipo Connection e
             * criamos um objeto connection que recebe da nossa Classe
             * ConexaoUtil um método getInstance do Padrão Singleton e
             * getConection que é nossa conexão de Fato
             */
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**
             * Esse recurso das interrogações na estrutura do código SQL é
             * utilizada para facilitar o NAO uso do SQL Injection esse é um
             * recurso muito interessante do JDBC que facilitar dessa forma a
             * flexibilidade na montagem do nosso código SQL
             */
            String sql = "INSERT INTO tbusuarios(iduser,CPF,usuario,celular_principal,login,senha,perfil,sexo,email_principal,estado,cadastro) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, usuarioDTO.getIdUserDto());
            statement.setString(2, usuarioDTO.getCpfDto());
            statement.setString(3, usuarioDTO.getUsuarioDto());
            statement.setString(4, usuarioDTO.getCelularPrincipalDto());
            statement.setString(5, usuarioDTO.getLoginDto());
            statement.setString(6, usuarioDTO.getSenhaDto());
            statement.setString(7, usuarioDTO.getPerfilDto());
            statement.setString(8, usuarioDTO.getSexoDto());
            statement.setString(9, usuarioDTO.getEmailPrincipalDto());
            statement.setString(10,usuarioDTO.getEstadoDto());
            statement.setDate(11, null);
            
            statement.execute();
            // importantíssimo fechar sempre a conexão
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);
        }

    }

    @Override
    public void atualizar(UsuarioDTO usuarioDTO) throws PersistenciaException {
        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
             * Esse recurso das interrogações na estrutura do código SQL é utilizada para
             * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
             * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
             * SQL
             */
            String sql = "UPDATE tbusuarios SET CPF=?,usuario=?,celular_principal=?,login=?,senha=?,perfil=?,sexo=?,email_principal=?,estado=? WHERE iduser=?";

            PreparedStatement statement = connection.prepareStatement(sql);

             statement.setString(1, usuarioDTO.getCpfDto());
            statement.setString(2, usuarioDTO.getUsuarioDto());
            statement.setString(3, usuarioDTO.getCelularPrincipalDto());
            statement.setString(4, usuarioDTO.getLoginDto());
            statement.setString(5, usuarioDTO.getSenhaDto());
            statement.setString(6, usuarioDTO.getPerfilDto());
            statement.setString(7, usuarioDTO.getSexoDto());
            statement.setString(8, usuarioDTO.getEmailPrincipalDto());
            statement.setString(9, usuarioDTO.getEstadoDto());
            statement.setInt(10, usuarioDTO.getIdUserDto());
           
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
    public void deletar(UsuarioDTO obj) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**
             * O código abaixo é uma pesquisa montada na linguagem estruturada
             * de consulta que tem como funcionalidade buscar no campo
             * nome_medico o nome digitado pelo usuário no FormMedico campo
             * txtPesquisa que foi setado pelo metodo de encapsulamento
             * setPesquisa() contido na camada MedicoDTO e agora recuperado pelo
             * parametro medico que recupera o valor digitado pelo
             * getPesquisa(). OBSERVAÇÃO IMPORTANTE:O operador ILIKE é
             * específico do PostgreSQL e seu comportamento é semelhante ao
             * LIKE. A única diferença é que ele é case-insensitive, ou seja,
             * não diferencia maiúsculas de minúsculas.
             * Fontes:https://pt.stackoverflow.com/questions/96926/como-fazer-consulta-sql-que-ignora-mai%C3%BAsculas-min%C3%BAsculas-e-acentos
             */
            /**
             * 12min
             */
            String sql = "DELETE FROM tbusuarios WHERE iduser=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, obj.getIdUserDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();
             JOptionPane.showMessageDialog(null, "" + "\n Info:\n"
                    + "Dados deletados com sucesso. "
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/multclin/imagens/info.png")));
            /**
             * Fecha Conexão
             */
            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro na Deleção do Dado\nErro:" + ex.getMessage());

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
    public UsuarioDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioDTO buscarPor(UsuarioDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(UsuarioDTO usuarioDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean usuarioDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbusuarios where cpf='" + usuarioDTO.getCpfDto()+ "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                usuarioDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return usuarioDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return usuarioDuplicado;

    }

    
      public boolean verificaDuplicidadeCPF(UsuarioDTO usuarioDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean usuarioDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbusuarios where CPF='" + usuarioDTO.getCpfDto()+ "'";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                usuarioDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return usuarioDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return usuarioDuplicado;

    }

    
    
    
    
    @Override
    public UsuarioDTO filtrarAoClicar(UsuarioDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        UsuarioDTO usuarioDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios WHERE usuario LIKE'%" + modelo.getUsuarioDto() + "%'";

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
                usuarioDTO = new UsuarioDTO();

                usuarioDTO.setIdUserDto(resultSet.getInt("iduser"));
                usuarioDTO.setUsuarioDto(resultSet.getString("usuario"));
                
                usuarioDTO.setLoginDto(resultSet.getString("login"));
                usuarioDTO.setSenhaDto(resultSet.getString("senha"));
                usuarioDTO.setPerfilDto(resultSet.getString("perfil"));
                usuarioDTO.setSexoDto(resultSet.getString("sexo"));


                return usuarioDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    public UsuarioDTO logar(UsuarioDTO usuarioDTO) throws PersistenciaException {
        /*
         * criamos uma flag e adicionamos a ela um resultado false por o dado � do tipo
         * boolean
         */
        UsuarioDTO userDTO = null;

        try {
            /*
             * Dentro do bloco try catch temos um objeto do tipo Connection e criamos um
             * objeto connection que recebe da nossa Classe ConexaoUtil um m�todo
             * getInstance do Padr�o Singleton e getConection que � nossa conex�o de Fato
             */

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios WHERE login=? AND senha=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            /* Setamos os dois par�metros abaixo */
            statement.setString(1, usuarioDTO.getLoginDto());
            statement.setString(2, usuarioDTO.getSenhaDto());

            ResultSet resultSet = statement.executeQuery();
            /*
             * resultado recebe resultSet.next(), porque se vier alguma coisa do Banco de
             * Dados ent�o n�s teremos a certeza que USU�RIO E SENHA DO SQL foram atendidos
             * de que existe algum login com aqueles dados, se n�o vai resultar false fecha
             * a conex�o logo abaixo e retorna o valor false
             */

            if (resultSet.next()) {

                userDTO = new UsuarioDTO();

                userDTO.setLoginDto(resultSet.getString("login"));
                userDTO.setSenhaDto(resultSet.getString("senha"));
                userDTO.setPerfilDto(resultSet.getString("perfil"));
                userDTO.setUsuarioDto(resultSet.getString("usuario"));
                userDTO.setSexoDto(resultSet.getString("sexo"));
                userDTO.setEstadoDto(resultSet.getString("estado"));

                return userDTO;

            }

            // Importante se abriu fecha seu objeto de conex�o
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean inserirControlEmail(UsuarioDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(UsuarioDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(UsuarioDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(UsuarioDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuarioDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
