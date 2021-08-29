package br.com.multclin.dao;

import br.com.multclin.dto.ItemAgendamentoDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.dto.UsuarioDTO;
import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ItemAgendamentoDAO implements GenericDAO<ItemAgendamentoDTO> {

    public boolean salvarItensAgendamentoLista(ArrayList<ItemAgendamentoDTO> listaItensDTO) throws PersistenciaException {

        boolean listaAdicionada = false;

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            int cont = listaItensDTO.size();

            for (int i = 0; i < cont; i++) {

                String sql = "INSERT INTO tbItemAgendamento(id,idProcede,idEsp,nomeProcede,RSBruto,RSDesconto,cadastro) VALUES(?,?,?,?,?,?,?)";

                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setInt(1, listaItensDTO.get(i).getIdDto());//id
                statement.setInt(2, listaItensDTO.get(i).getIdProceDto());//idProcede
                statement.setInt(3, listaItensDTO.get(i).getIdEspDto());//idEsp
                statement.setString(4, listaItensDTO.get(i).getNomeProcedeDto());
//                statement.setString(5, listaItensDTO.get(i).getRsBrutoDto());//rsBruto
//                statement.setString(6, listaItensDTO.get(i).getRsDescontoDto());//rsDesconto

                statement.setDouble(5, listaItensDTO.get(i).getRsBrutoDto());//rsBruto
                statement.setDouble(6, listaItensDTO.get(i).getRsDescontoDto());//rsDesconto

                statement.setDate(7, null);

                statement.execute();

            }

            listaAdicionada = true;
            connection.close();

        } catch (Exception ex) {

            listaAdicionada = false;
            ex.printStackTrace();
            throw new PersistenciaException(ex.getMessage(), ex);

        }
        return listaAdicionada;
    }

    //se um dia precisar atualizar esse metodo para itens deve-se para pra racicinar este métod não 
    //esta sendo utilizado ada forma adequada 
    @Override
    public void inserir(ItemAgendamentoDTO itemAgendamentoDTO) throws PersistenciaException {
//        try {
//
//            Connection connection = ConexaoUtil.getInstance().getConnection();
//            String sql = "INSERT INTO tbItemAgendamento(id,nomeProcede,RSBruto,RSClinica,RSTerceiro,RSDesconto,cadastro) VALUES(?,?,?,?,?,?,?)";
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//
//            statement.setInt(1, itemAgendamentoDTO.getIdDto());
//            statement.setString(2, itemAgendamentoDTO.getNomeProcedeDto());
//            statement.setString(3, itemAgendamentoDTO.getRsBrutoDto());//rsBruto
//            statement.setString(4, itemAgendamentoDTO.getRsClinicaDto());//rsClininca
//            statement.setString(5, itemAgendamentoDTO.getRsTerceiroDto());//rsTerceiro
//            statement.setString(6, itemAgendamentoDTO.getRsDescontoDto());//rsDesconto
//            statement.setDate(7, null);
//            statement.execute();
//            // importantíssimo fechar sempre a conexão
//            connection.close();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new PersistenciaException(ex.getMessage(), ex);
//        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //se um dia precisar atualizar esse metodo para itens deve-se para pra racicinar este métod não 
    //esta sendo utilizado ada forma adequada 
    @Override
    public void atualizar(ItemAgendamentoDTO itemAgendamentoDTO) throws PersistenciaException {
//        try {
//
//            Connection connection = ConexaoUtil.getInstance().getConnection();
//            String sql = "UPDATE tbItemAgendamento SET nomeProcede=?,RSBruto=?,RSClinica=?,RSTerceiro=?,RSDesconto=? WHERE idAuto=?";
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//
//            statement.setString(1, itemAgendamentoDTO.getNomeProcedeDto());
//            statement.setString(2, itemAgendamentoDTO.getRsBrutoDto());//nome
//            statement.setString(3, itemAgendamentoDTO.getRsClinicaDto());//nome
//            statement.setString(4, itemAgendamentoDTO.getRsTerceiroDto());//nome
//            statement.setString(5, itemAgendamentoDTO.getRsDescontoDto());//nome
//            statement.setInt(6, itemAgendamentoDTO.getIdAutoDto());
//            /**
//             * executar o statement
//             */
//            statement.execute();
//             importantíssimo fechar sempre a conexão
//            connection.close();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new PersistenciaException(ex.getMessage(), ex);
//        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletar(ItemAgendamentoDTO itemAgendamentoDTO) throws PersistenciaException {

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "DELETE FROM tbItemAgendamento WHERE idAuto=?";

            PreparedStatement statement;

            statement = connection.prepareStatement(sql);

            statement.setInt(1, itemAgendamentoDTO.getIdAutoDto());
            /**
             * A estamos disparando por meio do método execute a minhha strig
             * sql devidamente setada
             */
            statement.execute();
            JOptionPane.showMessageDialog(null, "Dado Deletado com Sucesso!!");
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
    public List<ItemAgendamentoDTO> listarTodos() throws PersistenciaException {
        List<ItemAgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbItemAgendamento order by nomeProcede";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();

                itemAgendamentoDTO.setIdDto(resultSet.getInt("id"));
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));
//                itemAgendamentoDTO.setRsBrutoDto(resultSet.getString("RSBruto"));
//                itemAgendamentoDTO.setRsDescontoDto(resultSet.getString("RSDesconto"));
//                
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                itemAgendamentoDTO.setRsDescontoDto(resultSet.getDouble("RSDesconto"));
                lista.add(itemAgendamentoDTO);
            }

            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista;
    }

    public ItemAgendamentoDTO buscarPorIdTblConsultaList(int codigo) throws PersistenciaException {

        ItemAgendamentoDTO itemAgendamentoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT * FROM tbItemAgendamento WHERE idAuto=" + codigo;

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                itemAgendamentoDTO = new ItemAgendamentoDTO();

                itemAgendamentoDTO.setIdDto(resultSet.getInt("id"));
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));
//                itemAgendamentoDTO.setRsBrutoDto(resultSet.getString("RSBruto"));
//                itemAgendamentoDTO.setRsDescontoDto(resultSet.getString("RSDesconto"));
//                
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                itemAgendamentoDTO.setRsDescontoDto(resultSet.getDouble("RSDesconto"));

                return itemAgendamentoDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro:\n Camada DAO" + e.getMessage());
        }

        return null;

    }

    public List<ItemAgendamentoDTO> listaCodigo(int codigo) throws PersistenciaException {
        List<ItemAgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbItemAgendamento WHERE idAuto LIKE'%" + codigo + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            /**
             * Quando entrar no while e começar os loops ele vai adicionando os
             * valores na minha lista de produtoDTO
             */
            while (resultSet.next()) {

                ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();

                itemAgendamentoDTO.setIdDto(resultSet.getInt("id"));
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));
//                itemAgendamentoDTO.setRsBrutoDto(resultSet.getString("RSBruto"));
//                itemAgendamentoDTO.setRsDescontoDto(resultSet.getString("RSDesconto"));
//                
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                itemAgendamentoDTO.setRsDescontoDto(resultSet.getDouble("RSDesconto"));
                lista.add(itemAgendamentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;//depois de tudo adicionado retorna minha lista com todos produtos adicionados pela consulta sql

    }
    
    
    public List<ItemAgendamentoDTO> pesquisarPorID(int codigo) throws PersistenciaException {
        List<ItemAgendamentoDTO> lista = new ArrayList<>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT *FROM tbItemAgendamento WHERE id=" + codigo ;
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            /**
             * Quando entrar no while e começar os loops ele vai adicionando os
             * valores na minha lista de produtoDTO
             */
            while (resultSet.next()) {

                ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();

                itemAgendamentoDTO.setIdDto(resultSet.getInt("id"));
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                itemAgendamentoDTO.setRsDescontoDto(resultSet.getDouble("RSDesconto"));
                lista.add(itemAgendamentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;//depois de tudo adicionado retorna minha lista com todos produtos adicionados pela consulta sql

    }


    @Override
    public ItemAgendamentoDTO buscarPorId(Integer id) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemAgendamentoDTO buscarPor(ItemAgendamentoDTO obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaDuplicidade(ItemAgendamentoDTO itemAgendamentoDTO) throws PersistenciaException {
        /**
         * Criando uma flag e atribuindo a ela o valor boolean de false a qual
         * fará a verificação se o registro foi encontrado ou não.Valor
         * encontrado retorna usuarioDuplicado=true(Verdadeiro). Caso contrário
         * usuarioDuplicado=false(continua com o valor atribuido no inicio do
         * código por meio da flag)
         */

        boolean pacienteDuplicado = false;

        try {
            //criando conexão utilizando padrão de projeto  singleton 
            Connection connection = ConexaoUtil.getInstance().getConnection();

            /*Nossa consulta sql que irá fazer a busca dentro de nosso Banco de Dados*/
            String sql = "SELECT *FROM tbItemAgendamento where nomeProcede='" + itemAgendamentoDTO.getNomeProcedeDto() + "'";

            PreparedStatement statement = connection.prepareStatement(sql);

            //statement.setString(1, medico);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                pacienteDuplicado = true;
                // JOptionPane.showMessageDialog(null, "Estado já se encontra cadastrado");
                return pacienteDuplicado;
            }

            connection.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro: Metodo verificaDuplicidade Camada DAO\n" + ex.getMessage());
        }

        return pacienteDuplicado;

    }

    @Override
    public ItemAgendamentoDTO filtrarAoClicar(ItemAgendamentoDTO modelo) throws PersistenciaException {
        /**
         * FLAG agente retorna null caso não encontre o médico buscado no banco
         * ele retorna nulo, ou seja, não foi encontrado no banco o médico
         * buscado
         */
        ItemAgendamentoDTO itemAgendamentoDTO = null;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbItemAgendamento WHERE nomeProcede LIKE'%" + modelo.getNomeProcedeDto() + "%'";

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
                itemAgendamentoDTO = new ItemAgendamentoDTO();

                itemAgendamentoDTO.setIdDto(resultSet.getInt("id"));
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));
//                itemAgendamentoDTO.setRsBrutoDto(resultSet.getString("RSBruto"));
//                itemAgendamentoDTO.setRsDescontoDto(resultSet.getString("RSDesconto"));
//                
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                itemAgendamentoDTO.setRsDescontoDto(resultSet.getDouble("RSDesconto"));
                return itemAgendamentoDTO;
            }

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro Método filtrarAoClicar()\n" + e.getMessage());
        }

        return null;

    }

    
    
    
    
    
    public List<ItemAgendamentoDTO> filtrarUsuarioPesqRapida(String pesquisar) throws PersistenciaException {

        List<ItemAgendamentoDTO> lista = new ArrayList<ItemAgendamentoDTO>();

        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();

            String sql = "SELECT *FROM tbItemAgendamento WHERE nomeProcede LIKE '%" + pesquisar + "%'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ItemAgendamentoDTO itemAgendamentoDTO = new ItemAgendamentoDTO();

                itemAgendamentoDTO.setIdDto(resultSet.getInt("id"));
                itemAgendamentoDTO.setNomeProcedeDto(resultSet.getString("nomeProcede"));
                itemAgendamentoDTO.setRsBrutoDto(resultSet.getDouble("RSBruto"));
                itemAgendamentoDTO.setRsDescontoDto(resultSet.getDouble("RSDesconto"));
                lista.add(itemAgendamentoDTO);

            }
            connection.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lista;

    }

    @Override
    public boolean inserirControlEmail(ItemAgendamentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean atualizarControlEmail(ItemAgendamentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(ItemAgendamentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarTudoControlEmail(ItemAgendamentoDTO obj) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletarControlEmail(int codigo) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ItemAgendamentoDTO> listarTodosParametro(String str) throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
