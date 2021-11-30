package br.com.fastlib.models;

import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Titulo extends Componente {
	private String conteudo;
	
	/**
	 * Constrói um Componente do tipo Titulo e atribui uma String ao seu atributo conteudo.
	 * @param conteudo O conteudo a ser atribuido.
	 */
	public Titulo(String conteudo) {
		this.setConteudo(conteudo);
	}
	
	//Atributo conteudo
	/**
	 * Retorna o atributo conteudo do Titulo.
	 * @return O atributo conteudo do Titulo.
	 */
	public String getConteudo() {
		return conteudo;
	}
	/**
	 * Atribui uma String ao atributo conteudo do Titulo.
	 * @param conteudo O conteúdo a ser atribuido.
	 */
	public void setConteudo(String conteudo) {
		this.conteudo = this.processaString(conteudo).replace("\n", " ");
	}

	//Renderização
	/**
	 * ({@inheritDoc})
	 * Um Titulo será renderizado na tela como um JLabel.
	 * O atributo conteudo da classe Titulo é uma String que representa o próprio Titulo.
	 * A função renderizarTela() formada o conteúdo em uma tag HTML para que este seja
	 * apresentado no Preview do PDF da melhor forma.
	 * @return O JLabel já formatado.
	 */
	public JComponent renderizarTela() {
		JLabel tituloLabel = new JLabel("<html>" + this.conteudo + "</html>");
		tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
		tituloLabel.setBorder(new EmptyBorder(5, 0, 5, 0));
		return tituloLabel;
	}
	/**
	 * ({@inheritDoc})
	 * Em LaTeX o Titulo representa um simpleTitle, que é um comando presente no cabeçalho do LaTeX que indica
	 * que o Titulo será um texto com uma determinada formatação definida no cabeçalho.
	 * @return uma String contendo o equivalente à representação do componente no PDF porém descrito em LaTeX.
	 */
	public String renderizarLatex() {
		String latexStr = "\\simpleTitle{" + this.converteStringLatex(this.conteudo);
		latexStr += "}\\vspace{5pt}\n";
		return latexStr;
	}
}