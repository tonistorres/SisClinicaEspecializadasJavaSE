/*
TUTORIAL SITE : http://www.yanaga.com.br/2012/06/validacao-do-cns-cartao-nacional-de.html
PROJETO GUIHUB:https://github.com/insula/opes
 */
package br.com.multclin.util;

/**
 *
 * @author Dã Torres
 */
public class ValidacaoCNS {
    // verificação cartão susu
    
public  boolean isValid(String s) {
	if (s.matches("[1-2]\\d{10}00[0-1]\\d") || s.matches("[7-9]\\d{14}")) {
		return somaPonderada(s) % 11 == 0;
	}
	return false;
}

private int somaPonderada(String s) {
	char[] cs = s.toCharArray();
	int soma = 0;
	for (int i = 0; i < cs.length; i++) {
		soma += Character.digit(cs[i], 10) * (15 - i);
	}
	return soma;
}
}
