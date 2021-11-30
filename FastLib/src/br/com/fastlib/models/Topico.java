package br.com.fastlib.models;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * <p>A classe Tópico consiste em uma abstração dos componentes do tipo Tópico.
 * Um Tópico pode conter um conjunto de componentes, estes componentes podem ser
 * do tipo Texto, Título, Código ou mesmo do tipo Tópico em alguns casos.
 * 
 * <p>Todo tópico tem um atributo denominado Nível. O Nível do tópico determina se ele
 * já está, ou não, contido em um tópico. No caso de o nível ser igual a zero, o tópico
 * está contido em um Capítulo, caso contrário ele está contido em um tópico, e por isso
 * será denominado subtópico. 
 * 
 *  @author Dayllon Vinícius Xavier Lemos
 *  @author Luan César Dutra Carvalho
 */

@SuppressWarnings("serial")
public class Topico extends Componente {
	private String nome;
	private int nivel;
	private ArrayList<Componente> componentes = new ArrayList<Componente>();
	
	/**
	 * Constroi um Componente do tipo Topico e atribui uma String ao atributo nome e um inteiro ao atributo nivel.
	 * @param nome O nome a ser atribuido.
	 * @param nivel O nível a ser atribuido.
	 */
	public Topico(String nome, int nivel) {
		this.setNivel(nivel);
		this.setNome(nome);
	}
	
	//Atributo nome
	/**
	 * Retorna o atributo nome do Topico.
	 * @return O atributo nome do Topico.
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * Atribui uma string ao atributo nome do Topico.
	 * @param nome A string a ser atribuida ao nome.
	 */
	public void setNome(String nome) {
		this.nome = this.processaString(nome).replace("\n", " ");
	}
	
	//Atributo nivel
	/**
	 * Retorna o atributo nivel do Topico.
	 * @return O atributo nivel do Topico.
	 */
	public int getNivel() {
		return nivel;
	}
	/**
	 * Atribui um inteiro ao atributo nivel do Topico.
	 * @param O inteiro a ser atribuído ao nivel.
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	//Atributo componentes
	/**
	 * Retorna o componente de um determinado índice do ArrayList {@link #componentes}.
	 * @param index O indice do componente a ser retornado.
	 * @return O componente de indice index do ArrayList {@link #componentes}
	 */
	public Componente getComponente(int index) {
		return this.componentes.get(index);
	}
	/**
	 * Retorna o indice de um determinado Componente do ArrayList {@link #componentes}
	 * @param componente O componente do qual se busca o índice.
	 * @return O indice do componente no ArrayList {@link #componentes}.
	 */
	public int getIndexComponente(Componente componente) {
		return this.componentes.indexOf(componente);
	}
	/**
	 * Adiciona um componente ao ArrayList {@link #componentes}
	 * @param componente O Componente a ser adicionado.
	 */
	public void addComponente(Componente componente) {
		this.componentes.add(componente);
	}
	/**
	 * Adiciona um componente a um determinado indice do ArrayList {@link #componentes}
	 * @param O componente a ser adicionado.
	 * @param O índice do ArrayList {@link #componentes} onde será adicionado o Componente. 
	 */
	public void addComponente(Componente componente, int index) {
		this.componentes.add(index, componente);
	}
	/**
	 * Remove um componente do ArrayList {@link #componentes} que ocupa uma determinada posição.
	 * @param index O indice da posição ocupada pelo Componente a ser removido.
	 */
	public void removeComponente(int index) {
		this.componentes.remove(index);
	}
	/**
	 * Remove um determinado componente do ArrayList {@link #componentes}.
	 * @param componente O componente a ser removido.
	 */
	public void removeComponente(Componente componente) {
		this.componentes.remove(componente);
	}
	/**
	 * Troca um componente do ArrayList {@link #componentes} de uma determinada posição, por outro componente
	 * @param index O indice da posição ocupada pelo Componente a ser trocado.
	 * @param componente O componente a substituir o Componente anterior.
	 */
	public void replaceComponente(int index, Componente componente) {
		this.componentes.set(index, componente);
	}
	/**
	 * Intercambia a posição de dois Componentes que estão em determinadas posições do ArrayList {@link #componentes}
	 * @param indexComponente1 O índice do primeiro Topico
	 * @param indexComponente2 O índice do segundo Topico.
	 * @see	  #replaceComponente(int, Componente)	
	 */
	public void swapComponentes(int indexComponente1, int indexComponente2) {
		Componente aux = this.getComponente(indexComponente1);
		this.replaceComponente(indexComponente1, this.getComponente(indexComponente2));
		this.replaceComponente(indexComponente2, aux);
	}
	/**
	 * Intercambia a posição de dois Componentes dados.
	 * @param Componente1 O primeiro Componente
	 * @param Componente2 O segundo Componente
	 * @see #swapComponentes(int, int)
	 */
	public void swapComponentes(Componente componente1, Componente componente2) {
		this.swapComponentes(this.getIndexComponente(componente1), this.getIndexComponente(componente2));
	}
	/**
	 * Retorna o tamanho do ArrayList {@link #componentes}
	 * @return O tamanho do ArrayList {@link #componentes}
	 */
	public int getComponentesSize() {
		return this.componentes.size();
	}
	
	//Renderização
	/**
	 * ({@inheritDoc})
	 * Um Tópico será renderizado na tela como um JLabel.
	 * O texto presente no JLabel será o conteúdo do atributo nome do tópico.
	 * A fonte pré-definida é Arial.
	 * @return o próprio JLabel (que representa o tópico) já formatado.
	 */
	public JComponent renderizarTela() {
		JLabel topicoLabel = new JLabel(this.nome);
		if (this.nivel == 0)
			topicoLabel.setFont(new Font("Arial", Font.BOLD, 20));
		else 
			topicoLabel.setFont(new Font("Arial", Font.BOLD, 18));
		topicoLabel.setBorder(new EmptyBorder(0, 0, 3, 0));
		return topicoLabel;
	}
	/**
	 * ({@inheritDoc})
	 * Em LaTeX o Tópico representa uma Subsection se o seu nível for igual a 0, caso o nível
	 * seja igual a 1, ele representará uma Subsubsection.
	 * @return uma String contendo o equivalente à representação do componente no PDF porém descrito em LaTeX.
	 */
	public String renderizarLatex() {
		String latexStr;
		if (this.nivel == 0)
			latexStr = "\\subsection";
		else
			latexStr = "\\subsubsection";
		latexStr += "{" + this.converteStringLatex(this.nome) + "}\n\n";
		for (int i = 0; i < this.componentes.size(); i++) {
			latexStr += this.getComponente(i).renderizarLatex();
		}
		return latexStr;
	}
}