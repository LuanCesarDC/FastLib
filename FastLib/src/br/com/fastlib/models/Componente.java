package br.com.fastlib.models;

import java.io.Serializable;

import javax.swing.JComponent;

/**
 * A classe abstrata Componente é um modelo geral para os elementos presentes
 * na abstração de uma Lib. Dessa forma, qualquer elemento presente no PDF que
 * representa a Lib será um filho direto, ou indireto de Componente.
 * 
 *  @author Dayllon Vinícius Xavier Lemos
 *  @author Luan César Dutra Carvalho
 */

@SuppressWarnings("serial")
public abstract class Componente implements Serializable{
	/**
	 * Renderiza o componente em questão na tela de Preview.
	 */
	public abstract JComponent renderizarTela();
	/**
	 * Gera o código LaTeX para o componente em questão.
	 */
	public abstract String renderizarLatex();
	
	//utilidades
	/**
	 *  Remove um determinado caractere do início e do fim de uma dada String.
	 *  @param str String da qual serão removidos os caracteres especificados.
	 *  @param ch Caractere a ser removido do início e do final da String
	 * */
	public String removeCharInicioFim(String str, char ch) {
		int countInicio = 0, countFim = 0;
		while(countInicio < str.length() && str.charAt(countInicio) == ch)
			countInicio++;
		while(countFim < str.length() && str.charAt(str.length()-countFim-1) == ch)
			countFim++;
		if (countInicio == str.length())
			return "";
		str = str.substring(countInicio, str.length()-countFim);
		return str;
	}
	/**
	 * Remove os caracteres de quebra de linha e de espaço tanto do início quanto do final da String.
	 * @param str String a ser processada, ou seja, da qual serão retirados os caracteres iniciais e finais de quebra de linha e espaço. 
	 * */
	public String processaString(String str) {
		while(str.length() > 0 && (str.charAt(0) == '\n' || str.charAt(0) == ' '))
			str = removeCharInicioFim(str, str.charAt(0));
		while(str.length() > 0 && (str.charAt(str.length()-1) == '\n' || str.charAt(str.length()-1) == ' '))
			str = removeCharInicioFim(str, str.charAt(str.length()-1));
		return str;
	}
	/**
	 * Os caracteres "{", "}", "_", "^", "$" são especiais no LaTeX, portanto não podem
	 * ser usados como texto diretamente sem antes haver o uso de uma "\". Dessa forma a
	 * função faz a troca de todos os caracteres invalidos por caracteres validos.
	 * @param str A String a ser refatorada.
	 * @return A String str já refatorada.
	 */
	public String converteStringLatex(String str) {
		String str_invalidos[] = {"{", "}", "_", "^", "$", "\"", "'", "\n"};
		String str_validos[] = {"\\{", "\\}", "\\_", "\\^{}", "\\$", "\\\"", "\\'", "\n\n"};
		for (int i = 0; i < str_invalidos.length; i++) {
			str = str.replace(str_invalidos[i], str_validos[i]);
		}
		return str;
	}
}