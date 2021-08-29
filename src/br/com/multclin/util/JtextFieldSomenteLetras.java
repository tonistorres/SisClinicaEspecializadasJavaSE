package br.com.multclin.util;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

//*****************************
//Artigo que explica a classe
//Link:https://www.devmedia.com.br/jtextfield-aceitando-apenas-letras/26019
//*****************************
/**
*Após criar a classe, e colocar os imports necessários temos duas opções que vamos explicar abaixo.

*No NetBeans, basta inserir um JTextField normal, clicar com o botão direito sobre o componente e 
* ir em personalizar código.
*Vai existir no lado esquerdo da janela, um combobox com duas opções código padrão e código personalizado. 
Vamos trocar para código personalizado.
*Após a definição de new vamos mudar a classe que está sendo instanciada.
* //**************************************************
// Estava assim:
//  jTextFieldNome = new javax.swing.JTextField();
//****************************************************
* Vamos Deixar assim:
// jTextFieldNome = new JtextFieldSomenteLetras();
* 
* Neste caso apenas acionamos a opção que vai eliminar os números do teclado.
Ou podemos deixar assim:
jTextFieldNome = new JtextFieldSomenteLetras(50);
*/
public final class JtextFieldSomenteLetras extends JTextField {
    private int maximoCaracteres=-1;// definição de -1
// como  valor normal de um textfield sem limite de caracters
public JtextFieldSomenteLetras() {
        super();
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
    public void keyTyped(java.awt.event.KeyEvent evt) {
        jTextFieldKeyTyped(evt);}});
    }

public JtextFieldSomenteLetras(int maximo) {
    super();
    setMaximoCaracteres(maximo);// define o tamanho máximo
//que deve ser aceito no jtextfield que foi recebido no  construtor

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
    public void keyTyped(java.awt.event.KeyEvent evt) {
        jTextFieldKeyTyped(evt);}});
    }

        private void jTextFieldKeyTyped(KeyEvent evt) {

String caracteres="0987654321";// lista de caracters que não devem ser aceitos
if(caracteres.contains(evt.getKeyChar()+"")){// se o character que gerou oevento estiver na lista

evt.consume();//aciona esse propriedade para eliminar a ação do evento
}
if((getText().length()>=getMaximoCaracteres())&&(getMaximoCaracteres()!=-1)){
//if para saber se precisa verificar também o tamanho da string do campo
// maior ou igual ao tamanho máximo, cancela e nao deixa inserir mais
evt.consume();
setText(getText().substring(0,getMaximoCaracteres()));
// esta linha acima é para remover os caracters inválidos caso o usuário tenha copiado o
//conteúdo de algum lugar ou seja com tamanho maior que o definido
}//fim do if do tamanho sa string do campo

        }

    public int getMaximoCaracteres() {
        return maximoCaracteres;
    }
    public void setMaximoCaracteres(int maximoCaracteres) {
        this.maximoCaracteres = maximoCaracteres;
    }
}
