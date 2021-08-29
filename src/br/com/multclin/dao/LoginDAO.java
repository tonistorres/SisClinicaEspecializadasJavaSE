package br.com.multclin.dao;

import br.com.multclin.dto.LoginDTO;
import br.com.multclin.dto.ReconhecimentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class LoginDAO implements GenericDAO<LoginDTO> {

    ReconhecimentoDTO reconheceDTO = new ReconhecimentoDTO();
    /*
     * criando um m�todo para se conectar com o banco de dadaos e mediantes os dados
     * passados pelo usu�rio verificar no Banco de Dados se Usu�rio e Senha
     * Cadastrados.Ent�o iremos passar como par�metro para esse M�todo o Objeto
     * LoginDTO que se encontra na segunda camada.
     */

    public boolean logar(LoginDTO loginDTO) throws PersistenciaException {
        /*
         * criamos uma flag e adicionamos a ela um resultado false por o dado do tipo
         * boolean
         */
        boolean resultado = false;

        try {
            /*
             * Dentro do bloco try catch temos um objeto do tipo Connection e criamos um
             * objeto connection que recebe da nossa Classe ConexaoUtil um metodo
             * getInstance do Padrao Singleton e getConection que e a nossa conexao de Fato
             */

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbusuarios WHERE login=? AND senha=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            /* Setamos os dois par�metros abaixo */
            statement.setString(1, loginDTO.getLoginDto());
            statement.setString(2, loginDTO.getSenhaDto());

            ResultSet resultSet = statement.executeQuery();
            /*
             * resultado recebe resultSet.next(), porque se vier alguma coisa do Banco de
             * Dados ent�o n�s teremos a certeza que USU�RIO E SENHA DO SQL foram atendidos
             * de que existe algum login com aqueles dados, se n�o vai resultar false fecha
             * a conex�o logo abaixo e retorna o valor false
             */

            resultado = resultSet.next();

            // Importante se abriu fecha seu objeto de conex�o
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);
        }
        return resultado;
    }

    @Override
    public void inserir(LoginDTO obj) throws PersistenciaException {

    }

    @Override
    public void atualizar(LoginDTO obj) throws PersistenciaException {

    }

    @Override
    public void deletar(LoginDTO obj) throws PersistenciaException {

    }

    @Override
    public List<LoginDTO> listarTodos() throws PersistenciaException {
        /**
         * Manipulando um objeto Array List da lista de Pessoas
         */
        List<LoginDTO> listaDeUsuarios = new ArrayList<LoginDTO>();

        try {

            /*
             * Dentro do bloco try catch temos um objeto do tipo Connection e criamos um
             * objeto connection que recebe da nossa Classe ConexaoUtil um m�todo
             * getInstance do Padr�o Singleton e getConection que � nossa conex�o de Fato
             */
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**
             * Preparando o nosso objeto Statement que ir� executar instru��es
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement ess� � o
             * objeto que utilizamos para manter o estado entre a comunica��o da
             * Aplica��o Java com o Banco
             */
            String sql = "SELECT *FROM tbusuarios";

            PreparedStatement statement = connection.prepareStatement(sql);
            /**
             * statement executa o codigo por meio do m�todo executeQuery e
             * retorna a tabela consultada no Banco de Dados, logo para
             * recebermos essa tabea utilizamos outro objeto Chamado ResultSet
             * que ir� receber esse retorno do statement
             */
            ResultSet resultSet = statement.executeQuery();

            /**
             * O M�TODO NEXT() funciona da seguinte forma: como se abrisse essa
             * Tabela anexada ao objeto resultSet e retornando um valor boleano
             * tipo se houver dados nessa tabela vai paginando linha por linha
             * inteirando dessa forma cada linha da tabela contida no resultSet
             */
            /*
             * Trocando por miudos: Enquanto tiver dados na tabela contida no resultSet ir
             * para o proximo, quando n�o mais houver sai do la�o de repeti��o
             */
            while (resultSet.next()) {
                /* Criando um Objeto pessoaDTO do Tipo DTO */
                LoginDTO loginDTO = new LoginDTO();
                /*
                 * No caso das Listas cada itera��o que terei por meio o La�o de Repeti��o While
                 * ser� adicionado uma nova pessoa na lista, vale apenas ressaltar que estamos
                 * apenas recuperando valores contidos no resultSet
                 */

                loginDTO.setIduserDto(resultSet.getInt("iduser"));
                loginDTO.setLoginDto(resultSet.getString("login"));
                loginDTO.setSenhaDto(resultSet.getString("senha"));

                /**
                 * Agora para terminar vamos chamar nossa listaDePessoas e usar
                 * nela o M�todo add e passar como par�metro para esse m�todo
                 * pessoaDTO para adicionar a cada intera��o uma pessoa a lista
                 */
                listaDeUsuarios.add(loginDTO);

            }

            /*
             * Importantissimo depois de ter aberto uma via de comunica��o com o Banco de
             * Dados por meio do Objeto Connection, um requesito de extrema importancia e
             * fechar essa via por meio do M�todo close() do Connection
             */
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);
        }

        return listaDeUsuarios;
    }

    @Override
    public LoginDTO buscarPorId(Integer id) throws PersistenciaException {
        /**
         * agente retorna null caso n�o encontre a pessoa buscada no banco ele
         * retorna nulo, ou seja, n�o encontrei a pessoa que voc� foi buscar
         */
        LoginDTO loginDTO = null;

        try {
            /*
             * Logo abaixo teremos um objeto do Tipo Connection que � o connection, e que
             * ir� receber da Classe ConexaoUtil o retorno da conex�o da aplica��o com o
             * Banco de Dados MYSQL
             */

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
             * Abaixo temos um Objeto do Tipo String chamado sql que recebe uma estrutura de
             * comando que seleciona uma pessoa no banco pelo sue ID_PESSOA
             */
            String sql = "SELECT *FROM tbusuarios WHERE iduser=?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco ir� trazer no m�ximo
             * um dado em vez de fazer um la�o de repeti��o faremos um if
             */

            if (resultSet.next()) {

                /*
                 * Dentro do loop n�o se esquecer de inicializar o objeto como abaixo, caso
                 * contr�rio erro de exception
                 */
                loginDTO = new LoginDTO();

                loginDTO.setIduserDto(resultSet.getInt("iduser"));
                loginDTO.setLoginDto(resultSet.getString("login"));
                loginDTO.setSenhaDto(resultSet.getString("senha"));
                return loginDTO;
            }

            // Importante se abriu fecha seu objeto de conex�o
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);
        }
        return null;
    }

    public ReconhecimentoDTO comparaSereiMotherboard(String serieMotherboard) {

        try {
            //A Autenticação é feita em outro banco de dado hospedado em núvm 
            Connection connection = ConexaoUtil.getInstance().getConnectionSisSegHospeda();

            String sql = "SELECT *FROM reconhecimentomaquina WHERE serial_placa_mae LIKE '%" + serieMotherboard + "%'";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                reconheceDTO = new ReconhecimentoDTO();
                reconheceDTO.setId_reconhecimentoDto(resultSet.getInt("id_reconhecimento"));
                reconheceDTO.setDt_hora_conectouDto(resultSet.getString("dt_hora_conectou"));
                reconheceDTO.setSerialHdDto(resultSet.getString("serial_hd"));
                reconheceDTO.setSerialCPUDto(resultSet.getString("serial_cpu"));
                reconheceDTO.setSerial_placa_maeDto(resultSet.getString("serial_placa_mae"));
                reconheceDTO.setInformacoes_diversasDto(resultSet.getString("informacoes_diversas"));
                reconheceDTO.setLiberado_bloqueadoDto(resultSet.getString("liberado_bloqueado"));
                reconheceDTO.setUsuario_responsavel_cadastro(resultSet.getString("usuario_responsavel"));
                return reconheceDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        return null;
    }

    @Override
    public LoginDTO buscarPor(LoginDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(LoginDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LoginDTO filtrarAoClicar(LoginDTO modelo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarPorCodigoTabela(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletarTudo() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean inserirControlEmail(LoginDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(LoginDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(LoginDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(LoginDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public List<LoginDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
