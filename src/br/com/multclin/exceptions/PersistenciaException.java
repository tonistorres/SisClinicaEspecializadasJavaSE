package br.com.multclin.exceptions;

/**
 * AULA 14 : 4� CAMADA que trata das exce��es que poder�o ser lan�adas pela
 * nossa aplica��o. Nessa camada poderemos encapsular nossas excess�es
 * personalizando em nosso estilo dessa forma estamos criando essa classe que
 * manipula erros e trata da forma que o programador achar melhor.
 */
public class PersistenciaException extends Exception {

	
	//construtor padr�o 
	public PersistenciaException(String msg) {
		super(msg);
	}
	
	
	//construtor sobrecarregado 
	public PersistenciaException(String msg, Exception exception) {
		super(msg,exception);
	}
	
	
}
