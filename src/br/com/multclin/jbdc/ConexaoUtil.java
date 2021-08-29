package br.com.multclin.jbdc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoUtil {

    private static ConexaoUtil connexaoUtil;

    public static ConexaoUtil getInstance() {

        if (connexaoUtil == null) {

            connexaoUtil = new ConexaoUtil();
        }
        return connexaoUtil;
    }

    //  TRABALHANDO COM CONEXAO NUVEM HOSTGATOR 
    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection("jdbc:mysql://162.241.61.90/inovec87_sishanna", "inovec87_tonis_sishanna", "pmaa1@2@");

    }

    
    /**
     * TRABALHANDO COM A CONEXAO NUVEM HOSTGATOR
     */
    public boolean ConexaoVerificaEstado() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection conexao = DriverManager.getConnection("jdbc:mysql://162.241.61.90/inovec87_sishanna", "inovec87_tonis_sishanna", "pmaa1@2@");

            conexao.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
           // JOptionPane.showMessageDialog(null, e.getMessage());

        }

        return false;

    }

////VERIFICANDO A SEGURANÇA COM CLASSE NAS NUVENS UTILIZO TANTO PARA VERIFICAÇÃO LOCAL HOST COMO NUVEM 
//
    public Connection getConnectionSisSegHospeda() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        System.out.println("conectado sisHospeda");
        return DriverManager.getConnection("jdbc:mysql://162.241.61.90/inovec87_sisseg", "inovec87_alberto", "pmaa1@2@");

    }

}




    
    
    //TRABALHANDO DE FORMA LOCAL HOST TESTE 
//    public Connection getConnection() throws ClassNotFoundException, SQLException {
//
//        Class.forName("com.mysql.jdbc.Driver");
//
//        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/inovec87_infoq", "root", "");
//
//    }
    
    
    //TRABALHANDO DE FORMA LOCAL HOST NUVEM HOSTGATOR 
//    public boolean ConexaoVerificaEstado() {
//
//        try {
//
//            Class.forName("com.mysql.jdbc.Driver");
//
//            Connection conexao = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/inovec87_infoq", "root", "");
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


