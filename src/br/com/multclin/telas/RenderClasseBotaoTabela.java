package br.com.multclin.telas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

//***********************************************************************
//Canal:David Pacheco Jimenez -Boton modificar y eliminar en JTable Java
//https://www.youtube.com/watch?v=jPfKFm2Yfow
//***********************************************************************
//OBSERVAÇÃO 01- Neste canal peguei a ideia para colocar um botão na tabela
//que fosse capaz de escutar um evento e aciona-lo a partir de um método 
//embutido nele
//*************************************************************************
//Java - Desktop - INSERINDO COR NA LINHA DO JTABLE ( Color jTable ) # 1
//https://www.youtube.com/watch?v=ibR1-Sp4S1k
//***************************************************************************
//OBSERVAÇÃO 02 - neste canal tem uma excelente explicaçao para aprende a 
//pintar a tabela com cores apartir de condicionais pré-definidas 
//para uma classe de renderização não prejudicar a outra tive que coloar 
//as duas de PINTAR_TABELA junto com a classe de Criar o JButton, pois,
//uma sobrepunha a outra causando conflitos quando separadas 
//*****************************************************************************
public class RenderClasseBotaoTabela extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        //nessa parte do codigo retorna um botao do tipo JButton 
        if (value instanceof JButton) {
            JButton btn = (JButton) value;

            if (isSelected) {
                btn.setForeground(table.getSelectionForeground());
                btn.setBackground(table.getSelectionBackground());
            } else {
                btn.setForeground(table.getForeground());
                btn.setBackground(UIManager.getColor("Button.background"));
            }
            return btn;
        }
        //aqui pintamos a tabela segindo condição 
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.

        //declarando variaveis para utilização posterior 
        //cor de fundo 
        Color background = Color.WHITE;

        //cor da linha(registros)
        Color foregroud = Color.BLACK;

        //agora preciso pegar as informações que estarão dentro de uma tabela 
        // que se referencie a uma determinada coluna 
        //bem vou criar um objeto que pegue qualquer valor que esteja embutido 
        //há uma determinada linha e coluna da tabela especifica 
        //OBSERVACAO: a coluna estou utilizando o valor zero só para deixar como
        //modelo mais pode ser qualquer coluna 
        Object objeto = table.getValueAt(row, 3);
        Object objetoLancamento=table.getValueAt(row, 6);

        //agora vamos criar a condição para pintar a tabela 
        try {

            String setor = objeto.toString();
            String lacamento=objetoLancamento.toString();
         
          //************************************************
         //  TABELA DE CORES ONLINE
        //   https://www.colorspire.com/rgb-color-wheel/
       //*************************************************     

            if (setor.equalsIgnoreCase("PROTOCOLO")) {
                background = (new Color(239, 255, 0));
                foregroud = Color.black;
            }

            if (setor.equalsIgnoreCase("CONTABILIDADE")) {
                background = (new Color(0, 128, 255));
                foregroud = Color.BLACK;
            }
            
            if (setor.equalsIgnoreCase("TESOURARIA")) {
                background = (new Color(204, 172, 0));//COR DE OUTRO
                foregroud = Color.WHITE;
            }
            
            if(setor.equalsIgnoreCase("PROTOCOLO") && lacamento.equalsIgnoreCase("CANCELADO")){
                background=Color.RED;
                foregroud=Color.WHITE;
            }
            
            
            if(setor.equalsIgnoreCase("CONTABILIDADE") && lacamento.equalsIgnoreCase("CANCELADO")){
                background=Color.RED;
                foregroud=Color.WHITE;
            }
            
            if(setor.equalsIgnoreCase("TESOURARIA") && lacamento.equalsIgnoreCase("CANCELADO")){
                background=Color.RED;
                foregroud=Color.WHITE;
            }

        } catch (Exception e) {
            erroViaEmail(e.getMessage(), "Classe: PINTAR_TABELA");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Classe: PINTAR_TABELA\n" + e.getMessage());
        }

        //agora vamos pinta o fundo da tabela 
        label.setBackground(background);
        label.setForeground(foregroud);

        return label;

//  return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    //https://www.youtube.com/watch?v=ibR1-Sp4S1k
//criamos o construtor 
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
