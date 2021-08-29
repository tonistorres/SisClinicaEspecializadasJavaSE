/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.multclin.dao;

import br.com.multclin.exceptions.PersistenciaException;
import br.com.multclin.jbdc.ConexaoUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 * Link
 * Documentação:https://dev.mysql.com/doc/refman/8.0/en/show-processlist.html A
 * saída SHOW PROCESSLIST possui estas colunas:
 */
// * Id - O identificador de conexão.
//user - user - O usuário do MySQL que emitiu  a instrução.
//host-O nome do host do cliente que emite a instrução (exceto o usuário do sistema, para o qual não há host). 
//db-O banco de dados padrão, se um estiver selecionado; caso contrário, NULL.
//Command - O tipo de comando que o encadeamento está executando
//Time-O tempo em segundos em que o encadeamento está em seu estado atual
//State-Uma ação, evento ou estado que indica o que o encadeamento está fazendo.
//info -A instrução que o thread está executando, ou NULL, se não estiver executando nenhuma instrução. 
public class InfoControleConexaoDAO {

    public static int contarMaxConexaoServidor() throws PersistenciaException {

        int valorRetornar = 0;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**apontar o LIMITE MAXIMO de conexões com o servidor ‘max_connections’ é de*/
            String sql = "show variables like 'max_connections'";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                valorRetornar = resultSet.getInt("Value");

                return valorRetornar;
            }

            connection.close();

        } catch (Exception e) {
          //  e.getMessage();
            e.printStackTrace();
        }

        return valorRetornar;
    }

    
     public static int contarMaxConexaoUsuario() throws PersistenciaException {

        int valorRetornar = 0;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**enquanto o LIMITE MAXIMO DE CONEXAO POR USUARIO de um único usuário ‘max_user_connections’ é de*/
            String sql = "show variables like 'max_user_connections'";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                valorRetornar = resultSet.getInt("Value");

                return valorRetornar;
            }

            connection.close();

        } catch (Exception e) {
           e.printStackTrace();
           e.getMessage();
        }

        return valorRetornar;
    }
    
       public static int contarNumeroConexaoAbertasNoMomento() throws PersistenciaException {

        int valorRetornar = 0;

        try {

            Connection connection = ConexaoUtil.getInstance().getConnection();

            /**O número de conexões abertas no momento.*/
            String sql = "SHOW STATUS WHERE Variable_name = 'Threads_connected'";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                valorRetornar = resultSet.getInt("Value");

                return valorRetornar;
            }

            connection.close();

        } catch (Exception e) {
         e.printStackTrace();
         e.getMessage();
        }

        return valorRetornar;
    }
     
     
     
    public static void main(String[] args) throws PersistenciaException {

        int recber = contarMaxConexaoServidor();
        System.out.println("Conexao:" + recber);
    }
}
