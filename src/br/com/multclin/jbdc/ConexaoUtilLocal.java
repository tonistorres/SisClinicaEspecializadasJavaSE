package br.com.multclin.jbdc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoUtilLocal {

    private static ConexaoUtilLocal connexaoUtil;

    public static ConexaoUtilLocal getInstance() {

        if (connexaoUtil == null) {

            connexaoUtil = new ConexaoUtilLocal();
        }
        return connexaoUtil;
    }

    //local
    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/infoq", "root", "1020");

    }

//    public Connection getConnectionSisSegHospeda() throws ClassNotFoundException, SQLException {
//
//        Class.forName("com.mysql.jdbc.Driver");
//        System.out.println("conectado sisHospeda");
//        return DriverManager.getConnection("jdbc:mysql://localhost:3306/sisseg", "root", "1020");
//
//    }
    /**
     * Construtor da Classe
     */
    public boolean ConexaoVerificaEstado() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/infoq", "root", "1020");

            conexao.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            //JOptionPane.showMesssageDialog(null,"Erro ao se Conectar"+ e.getMessage());
        }

        return false;

    }

    //NUVEM  - neste caso irei fazer a verificação de segurança no Banco de Dados na nuvem 
    public Connection getConnectionSisSegHospeda() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("conectado sisHospeda");
        return DriverManager.getConnection("jdbc:mysql://segsis.mysql.uhserver.com:3306/segsis", "datorres", "Pmaa2111791@2@");

    }

//    
//    public Connection getConnection() throws ClassNotFoundException, SQLException {
//
//        Class.forName("com.mysql.jdbc.Driver");
//
//        return DriverManager.getConnection("jdbc:mysql://infoq.mysql.uhserver.com:3306/infoq", "tferreira", "**Pmaa2111791@2@");
//
//    }
//
//
//    /**
//     * Construtor da Classe
//     */
//    public boolean ConexaoVerificaEstado() {
//
//        try {
//
//            Class.forName("com.mysql.jdbc.Driver");
//
//            Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/infoq", "tferreira", "**Pmaa2111791@2@");
//
//            conexao.close();
//            
//            return true;
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            //JOptionPane.showMesssageDialog(null,"Erro ao se Conectar"+ e.getMessage());
//        }
//        
//        return false;
//
//    }
//
//    public static void main(String[] args) throws SQLException {
//        Connection conn = null;
//        try {
//            try {
//                Class.forName("com.mysql.jdbc.Driver");
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//            conn = (Connection) DriverManager.getConnection("jdbc:mysql://segsis.mysql.uhserver.com:3306/segsis", "datorres", "Pmaa2111791@2@");
//            System.out.println("Connection is created succcessfully:");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        ResultSet rs = null;
//        DatabaseMetaData meta = (DatabaseMetaData) conn.getMetaData();
//        rs = meta.getTables(null, null, null, new String[]{
//            "TABLE"
//        });
//        int count = 0;
//        System.out.println("All table names are in test database:");
//        while (rs.next()) {
//            String tblName = rs.getString("TABLE_NAME");
//            System.out.println(tblName);
//            count++;
//        }
//        System.out.println(count + " Rows in set ");
//    }
}
