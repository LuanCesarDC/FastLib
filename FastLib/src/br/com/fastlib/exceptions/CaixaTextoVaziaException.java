package br.com.fastlib.exceptions;

@SuppressWarnings("serial")
public class CaixaTextoVaziaException extends Exception {
	public CaixaTextoVaziaException(String str) {
		super(str);
	}
}