
package br.com.multclin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.com.multclin.jbdc.ConexaoUtil;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class GeraCodigoAutomaticoDAO {
   
    
    public int gerarCodigoEspecialidade() {
        int codigoMax = 0;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT MAX(id) FROM tbCadastroEspecialidades";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                codigoMax = resultSet.getInt(1);

                return codigoMax;
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigoMax;
    }
    
    
    public int gerarCodigoAgendamento() {
        int codigoMax = 0;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT MAX(id) FROM tbAgendamentos";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                codigoMax = resultSet.getInt(1);

                return codigoMax;
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigoMax;
    }
    
    
    
    public int gerarCodigoUsuario() {
        int codigoMax = 0;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT MAX(iduser) FROM tbusuarios";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                codigoMax = resultSet.getInt(1);

                return codigoMax;
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigoMax;
    }
    
    
    public int gerarCodigoMedico() {
        int codigoMax = 0;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT MAX(id) FROM tbMedicos";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                codigoMax = resultSet.getInt(1);

                return codigoMax;
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigoMax;
    }
    
    
    
    
   
    
    
    
    
    
    public int gerarCodigo() {
        int codigoMax = 0;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT MAX(id) FROM tbpacientes";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                codigoMax = resultSet.getInt(1);

                return codigoMax;
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigoMax;
    }
    
    
    
    public int gerarCodigoTbHistoricoPaciente() {
        int codigoMax = 0;
        try {
            Connection connection = ConexaoUtil.getInstance().getConnection();
            String sql = "SELECT MAX(id) FROM tbHistoricoPaciente";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            /**
             * como sabemos que pelo sql disparado no banco irá trazer no máximo
             * um dado em vez de fazer um laço de repetição faremos um if
             */

            if (resultSet.next()) {

                codigoMax = resultSet.getInt(1);

                return codigoMax;
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigoMax;
    }
    
    
    
    
  
}
