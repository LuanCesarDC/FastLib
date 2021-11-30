package br.com.fastlib.models;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
 * A classe Capítulo consiste em uma abstração dos componentes do tipo Capítulo.
 * Um Capítulo pode conter um conjunto de componentes do tipo Tópico, além disso
 * contém também um atributo nome do tipo String.
 * 
 *  @author Dayllon Vinícius Xavier Lemos
 *  @author Luan César Dutra Carvalho
 */

@SuppressWarnings("serial")
public class Capitulo extends Componente{
	private String nome;
	private ArrayList<Topico> topicos = new ArrayList<Topico>();
	
	/**
	 * Constroi um Componente do tipo capítulo e atribui um nome a este.
	 * @param nome O nome a ser atribuido.
	 */
	public Capitulo(String nome) {
		this.setNome(nome);
	}
	
	//Atributo nome
	/**
	 * Retorna o atributo nome do tipo String.
	 * @return O atributo nome.
	 */
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = this.processaString(nome).replace("\n", " ");
	}
	
	//Atributo topicos
	/**
	 * Retorna um tópico do ArrayList {@link #topicos} que tenha um determinado indice.
	 * @param index O indice do tópico a ser retornado.
	 * @return O tópico de indice index
	 */
	public Topico getTopico(int index) {
		return this.topicos.get(index);
	}
	/**
	 * Retorna o índice de um determinado tópico presente no ArrayList {@link #topicos}. 
	 * @param topico O tópico do qual se quer descobrir o índice.
	 * @return O índice do Topico.
	 */
	public int getIndexTopico(Topico topico) {
		return this.topicos.indexOf(topico);
	}
	/**
	 * Adiciona um Topico ao ArrayList {@link #topicos}
	 * @param topico O tópico a ser adicionado.
	 * */
	public void addTopico(Topico topico) {
		this.topicos.add(topico);
	}
	/**
	 * Adiciona um Topico a uma determinada posição  do ArrayList {@link #topicos} 
	 * @param topico O tópico a ser adicionado.
	 * @param index O índice da posição do ArrayList {@link #topicos} onde será adicionado o topico.
	 * */
	public void addTopico(Topico topico, int index) {
		this.topicos.add(index, topico);
	}
	/**
	 * Remove o Topico de um determinado indice do ArrayList {@link #topicos}
	 * @param index O índice do Topico a ser removido.
	 */
	public void removeTopico(int index) {
		this.topicos.remove(index);
	}
	/**
	 * Remove um determinado Topico do ArrayList {@link #topicos}.
	 * @param topico O tópico a ser removido.
	 */
	public void removeTopico(Topico topico) {
		this.topicos.remove(topico);
	}
	/**
	 * Troca um Topico de uma determinada posição do ArrayList {@link #topicos} por outro Topico dado.
	 * @param index O indice do tópico a ser trocado.
	 * @param topico O Topico que substituirá o anterior.
	 */
	public void replaceTopico(int index, Topico topico) {
		this.topicos.set(index, topico);
	}
	/**
	 * Intercambia a posição de dois Topicos que estão em determinadas posições.
	 * @param indexTopico1 O índice do primeiro Topico
	 * @param indexTopico2 O índice do segundo Topico.
	 * @see #replaceTopico(int, Topico)
	 */
	public void swapTopicos(int indexTopico1, int indexTopico2) {
		Topico aux = this.getTopico(indexTopico1);
		this.replaceTopico(indexTopico1, this.getTopico(indexTopico2));
		this.replaceTopico(indexTopico2, aux);
	}
	/**
	 * Intercambia a posição de dois Topicos dados.
	 * @param topico1 O primeiro Topico
	 * @param topico2 O segundo Topico
	 * @see #swapTopicos(int, int) 
	 */
	public void swapTopicos(Topico topico1, Topico topico2) {
		this.swapTopicos(this.getIndexTopico(topico1), this.getIndexTopico(topico2));
	}
	/**
	 * Retorna tamanho do ArrayList {@link #topicos}.
	 * @return O tamanho do ArrayList {@link #topicos}
	 */
	public int getTopicosSize() {
		return this.topicos.size();
	}
	
	//Renderizar
	/**
	 * ({@inheritDoc})
	 * Um Capítulo será renderizado na tela como um JLabel.
	 * O texto presente no JLabel será o conteúdo do atributo nome do capítulo.
	 * A fonte pré-definida é Arial.
	 * @return o próprio JLabel (que representa o capítulo) já formatado.
	 */
	public JComponent renderizarTela() {
		JLabel capituloLabel = new JLabel(this.nome);
		capituloLabel.setFont(new Font("Arial", Font.BOLD, 25));
		capituloLabel.setBorder(new EmptyBorder(0, 0, 6, 0));
		return capituloLabel;
	}
	/**
	 * ({@inheritDoc})
	 * Em LaTeX o Capítulo representa uma Section
	 * @return uma String contendo o equivalente à representação do componente no PDF porém descrito em LaTeX.
	 */
	public String renderizarLatex() {
		String latexStr = "\\section{" + this.converteStringLatex(this.nome) + "}\n\n";
		for (int i = 0; i < this.topicos.size(); i++) {
			latexStr += this.getTopico(i).renderizarLatex();
		}
		return latexStr;
	}
}