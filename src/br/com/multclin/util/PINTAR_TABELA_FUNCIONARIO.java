
package br.com.multclin.util;

import java.awt.Color;
import java.awt.Component;
import javax.print.event.PrintJobEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author X552E
 */
public class PINTAR_TABELA_FUNCIONARIO extends DefaultTableCellRenderer {
//*************************************************************************
//Java - Desktop - INSERINDO COR NA LINHA DO JTABLE ( Color jTable ) # 1
//https://www.youtube.com/watch?v=ibR1-Sp4S1k
//***************************************************************************


//criamos o construtor 
    
    public PINTAR_TABELA_FUNCIONARIO(){}
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        //declarando variaveis para utilização posterior 
        
        //cor de fundo 
        Color background=Color.WHITE;
       
        //cor da linha(registros)
        Color foregroud=Color.BLACK;
        
        //agora preciso pegar as informações que estarão dentro de uma tabela 
        // que se referencie a uma determinada coluna 
        //bem vou criar um objeto que pegue qualquer valor que esteja embutido 
        //há uma determinada linha e coluna da tabela especifica 
        //OBSERVACAO: a coluna estou utilizando o valor zero só para deixar como
        //modelo mais pode ser qualquer coluna 
        Object objeto=table.getValueAt(row, 2);
        
        //agora vamos criar a condição para pintar a tabela 
        try {
            
            String setor =objeto.toString();
            //PINTANDO SETORES INTERNOS DO PROTOCOLO 
            if(setor.equalsIgnoreCase("PROTOCOLO")){
                background=Color.YELLOW;
                foregroud=Color.BLACK;
            }
            
            if(setor.equalsIgnoreCase("CONTABILIDADE")){
                background=(new Color(0, 128, 255));
                foregroud=Color.BLACK;
            }
            
            
            if(setor.equalsIgnoreCase("TESOURARIA")){
                background=(new Color(215, 215, 0));
                foregroud=Color.BLACK;
            }
            
            
             if(setor.equalsIgnoreCase("ARQUIVAMENTO DE PROCESSO")){
                background=(new Color(175, 47, 68));
                foregroud=Color.white;
            }
            
             if(setor.equalsIgnoreCase("CPL (COMISSAO PERMANENTE DE LICITACAO)")){
                background=(new Color(42, 37, 115));
                foregroud=Color.white;
            }
             
             if(setor.equalsIgnoreCase("GABINETE")){
                background=(new Color(28, 84, 148));
                foregroud=Color.white;
            }
             
             if(setor.equalsIgnoreCase("SETOR DE COMPRAS")){
                background=(new Color(22, 104, 129));
                foregroud=Color.white;
            }
             
             
             if(setor.equalsIgnoreCase("SEC ADMINISTRACAO")){
                background=(new Color(22, 129, 115));
                foregroud=Color.white;
            }
//            
//             if(setor.equalsIgnoreCase("SECRETARIA DE EDUCACAO")){
//                background=(new Color(102, 5, 105));
//                foregroud=Color.white;
//            }
//             
//             
//             if(setor.equalsIgnoreCase("SECRETARIA DE SAUDE")){
//                background=(new Color(5, 5, 105));
//                foregroud=Color.white;
//            }
//             
//             
//             if(setor.equalsIgnoreCase("SECRETARIA DE SAUDE")){
//                background=(new Color(5, 5, 105));
//                foregroud=Color.white;
//            }
//            
        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "Classe: PINTAR_TABELA");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Classe: PINTAR_TABELA\n"+e.getMessage());
        }
        

        //agora vamos pinta o fundo da tabela 
        label.setBackground(background);
        label.setForeground(foregroud);
        
        return label;
    }

    private void erroViaEmail(String mensagemErro, String metodo) {

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
            email.setSubject("Metodo:" + metodo);
            //configurando o corpo deo email (Mensagem)
            email.setMsg("Mensagem:" + mensagemErro + "\n"
            );

            //para quem vou enviar(Destinatário)
            email.addTo(meuEmail);
            //tendo tudo configurado agora é enviar o email 
            email.send();

        } catch (Exception e) {

            erroViaEmail(mensagemErro, metodo);

            JOptionPane.showMessageDialog(null, "" + "\n Camada GUI:\n"
                    + "Erro:" + e.getMessage()
                    + "", "Mensagem Informativa", 0, new ImageIcon(getClass().getResource("/br/com/subgerentepro/imagens/info.png")));
            e.printStackTrace();
        }

    }

    
}
