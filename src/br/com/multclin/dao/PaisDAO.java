package br.com.multclin.dao;

import br.com.multclin.dto.PaisDTO;
import br.com.multclin.dto.ReconhecimentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class PaisDAO implements GenericDAO<PaisDTO> {

    @Override
    public void inserir(PaisDTO paisDTO) throws PersistenciaException {
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
            String sql = "INSERT INTO tbpaisregiao(codigosms,pais,cadastro) VALUES(?,?,?)";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, paisDTO.getCodigoSMSDto());
            statement.setString(2, paisDTO.getPaisDto());
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
    public void atualizar(PaisDTO paisDTO) throws PersistenciaException {

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*
             * Esse recurso das interrogações na estrutura do código SQL é utilizada para
             * facilitar o NAO uso do SQL Injection esse é um recurso muito interessante do
             * JDBC que facilitar dessa forma a flexibilidade na montagem do nosso código
             * SQL
             */
            String sql = "UPDATE tbpaisregiao SET codigosms=?,pais=? WHERE idpaisregiao=?";

            /**
             * Preparando o nosso objeto Statement que irá executar instruções
             * SQL no nosso Banco de Dados passado por meio de argumento e de
             * uma String com o codigo SQL Desejado. PreparedStatement essé é o
             * objeto que utilizamos para manter o estado entre a comunicação da
             * Aplicação Java com o Banco
             */
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, paisDTO.getCodigoSMSDto());//usuario
            statement.setString(2, paisDTO.getPaisDto());//telefone
            statement.setInt(3, paisDTO.getIdpaisregiaoDto());
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
    public void deletar(PaisDTO paisDTO) throws PersistenciaException {
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "DELETE FROM tbpaisregiao WHERE idpaisregiao=?";

            //PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, paisDTO.getIdpaisregiaoDto());
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
    public List<PaisDTO> listarTodos() throws PersistenciaException {
        /**
         * Manipulando um objeto Array List da lista de Pessoas
         */
        List<PaisDTO> lista = new ArrayList<PaisDTO>();

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
            String sql = "SELECT *FROM tbpaisregiao";

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
                PaisDTO paisDTO = new PaisDTO();
                /*
                 * No caso das Listas cada itera��o que terei por meio o La�o de Repeti��o While
                 * ser� adicionado uma nova pessoa na lista, vale apenas ressaltar que estamos
                 * apenas recuperando valores contidos no resultSet
                 */

                paisDTO.setIdpaisregiaoDto(resultSet.getInt("idpaisregiao"));
                paisDTO.setCodigoSMSDto(resultSet.getString("codigosms"));
                paisDTO.setPaisDto(resultSet.getString("pais"));
                /**
                 * Agora para terminar vamos chamar nossa listaDePessoas e usar
                 * nela o M�todo add e passar como par�metro para esse m�todo
                 * pessoaDTO para adicionar a cada intera��o uma pessoa a lista
                 */
                lista.add(paisDTO);

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

        return lista;
    }

    @Override
    public PaisDTO buscarPorId(Integer id) throws PersistenciaException {
        /**
         * agente retorna null caso n�o encontre a pessoa buscada no banco ele
         * retorna nulo, ou seja, n�o encontrei a pessoa que voc� foi buscar
         */
        PaisDTO paisDTO = null;

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
            String sql = "SELECT *FROM tbpaisregiao WHERE idpaisregiao=?";

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
                paisDTO = new PaisDTO();

                paisDTO.setIdpaisregiaoDto(resultSet.getInt("idpaisregiao"));
                paisDTO.setCodigoSMSDto(resultSet.getString("codigosms"));
                paisDTO.setPaisDto(resultSet.getString("pais"));
                return paisDTO;
            }

            // Importante se abriu fecha seu objeto de conex�o
            connection.close();

        } catch (Exception e) {

            e.printStackTrace();
            throw new PersistenciaException(e.getMessage(), e);
        }
        return null;
    }

    public List<PaisDTO> filtrarUsuarioPesqRapida(String pesquisar) throws PersistenciaException {

        List<PaisDTO> lista = new ArrayList<PaisDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbpaisregiao WHERE pais LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                PaisDTO paisDTO = new PaisDTO();

                paisDTO.setIdpaisregiaoDto(resultSet.getInt("idpaisregiao"));
                paisDTO.setCodigoSMSDto(resultSet.getString("codigosms"));
                paisDTO.setPaisDto(resultSet.getString("pais"));

                lista.add(paisDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PaisDTO buscarPor(PaisDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(PaisDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PaisDTO filtrarAoClicar(PaisDTO modelo) throws PersistenciaException {
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
    public boolean inserirControlEmail(PaisDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(PaisDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(PaisDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(PaisDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PaisDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
