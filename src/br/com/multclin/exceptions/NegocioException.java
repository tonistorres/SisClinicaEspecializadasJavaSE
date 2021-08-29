package br.com.multclin.exceptions;

public class NegocioException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8916895283914218760L;

	
	//construtor padr�o 
	public NegocioException(String msg) {
		super(msg);
	}
	
	
	//construtor sobrecarregado 
	public NegocioException(String msg, Exception exception) {
		super(msg,exception);
	}
	
}
