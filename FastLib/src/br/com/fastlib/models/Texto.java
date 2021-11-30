package br.com.fastlib.models;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * A classe Texto consiste em uma abstração dos componentes do tipo Texto.
 * Um texto não pode conter nenhum outro componente, este será usado para
 * representar alguma informação a qual será gravada no atributo conteudo,
 * do tipo String.
 * 
 *  @author Dayllon Vinícius Xavier Lemos
 *  @author Luan César Dutra Carvalho
 */

@SuppressWarnings("serial")
public class Texto extends Componente {
	private String conteudo;
	
	/**
	 * Constroi um Componente do tipo Texto e atribui a esse Componente
	 * um conteúdo.
	 * @param conteudo Conteudo a ser atribuido.
	 */
	public Texto(String conteudo) {
		this.setConteudo(conteudo);
	}
	
	//Atributo conteudo
	/**
	 * Retorna o atributo conteudo do Texto.
	 * @return O atributo conteudo do Texto.
	 */
	public String getConteudo() {
		return conteudo;
	}
	/**
	 * Atribui um Conteúdo ao atributo conteudo do Texto.
	 * @param conteudo O conteúdo a ser atribuido.
	 */
	public void setConteudo(String conteudo) {
		this.conteudo = this.processaString(conteudo);
	}

	//Renderização
	/**
	 * ({@inheritDoc})
	 * Um Texto será renderizado na tela como um JLabel.
	 * A String presente no JLabel será a String do atributo conteudo do texto.
	 * A fonte pré-definida é Arial.
	 * @return o próprio JLabel (que representa o texto) já formatado.
	 */
	public JComponent renderizarTela() {
		JLabel textoLabel = new JLabel("<html><p width='450px' style='text-align: justify;'>&#9" + this.conteudo.replace("\n", "<br>&#9").replace("\t", "&#9") + "</p></html>");
		textoLabel.setFont(new Font("Arial", 0, 14));
		return textoLabel;
	}
	/**
	 * ({@inheritDoc})
	 * Em LaTeX o Texto é representado pelo atributo 'conteudo' do próprio Texto.
	 * @see #processaString(String)
	 * @return uma String contendo o equivalente à representação do componente no PDF porém descrito em LaTeX.
	 */
	public String renderizarLatex() {
		String latexStr = this.converteStringLatex(this.conteudo+"\n");
		return latexStr;
	}
}