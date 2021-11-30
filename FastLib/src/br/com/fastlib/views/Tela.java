package br.com.fastlib.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * A classe Tela funciona como um modelo geral de janela. Qualquer
 * outra janela que precise ser criada herda as propriedades da Tela.
 * 
 * @author Dayllon Vinicius Xavier Lemos
 * @author Luan César Dutra Carvalho
 */

@SuppressWarnings("serial")
public abstract class Tela extends JFrame implements ActionListener, MouseListener{
    private String nome;
	private JPanel painel;
	private Font fontLogo;
	private int largura;
	private int altura;
	private GraphicsEnvironment registradorGrafico;
	
	/**
	 * Constroi uma tela e atribui um titulo, uma largura e uma altura a ela.
	 * @param nome O nome do titulo que ficara presente no topo da tela
	 * @param largura A largura da tela
	 * @param altura A altura da tela
	 */
	public Tela(String nome, int largura, int altura){
        this.nome = nome;
        
        //Carrega Fonte   
        this.registradorGrafico = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.fontLogo = null;
		try {
			this.fontLogo = Font.createFont(Font.TRUETYPE_FONT, new File("./_fonts/Shelter_Font.ttf"));
			this.registradorGrafico.registerFont(fontLogo);
		} catch (FontFormatException e) {
			this.fontLogo = new Font("Arial", Font.BOLD, 16);
		} catch (IOException e) {
			this.fontLogo = new Font("Arial", Font.BOLD, 16);
		}
        
		this.painel = new JPanel();
		this.painel.setLayout(new GroupLayout(this.painel));
		this.painel.setBackground(new Color(237, 238, 222)); //Plano de fundo 212, 219, 229 - 237, 238, 222 - 197, 173, 135
		this.painel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(this.painel);
		
		//Tela
		this.setSize(largura, altura);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setTitle("FastLib - " + this.nome);
        this.setResizable(false);
	}
	
	//Size
	/**
	 * Atribui à tela uma largura e uma altura
	 * @param largura A largura a ser atribuida
	 * @param altura A altura a ser atribuida
	 */
	public void setSize(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
		super.setSize(largura, altura);
	}
	/**
	 * Retorna a largura da tela
	 * @return A largura da tela
	 */
	public int getLargura() {
		return this.largura;
	}
	/**
	 * Retorna a altura da tela.
	 * @return A altura da tela. 
	 */
	public int getAltura() {
		return this.altura;
	}
	/**
	 * Adiciona ao painel associado à tela, um JComponent
	 * @param jComponent o JComponent a ser adicionado ao {@link #painel}
	 */
	public void addJComponente(JComponent jComponente) {
		this.painel.add(jComponente);
	}
	/**
	 * Adiciona ao painel associado à tela, um JComponent em uma determinada posição
	 * @param jComponent O JComponente a ser adicionado ao {@link #painel}
	 * @param index O índice da posição onde ele será adicionada.
	 */
	public void addJComponente(JComponent jComponente, int index) {
		this.painel.add(jComponente, index);
	}
	/**
	 * Remove um determinado JComponent do painel associado à tela.
	 * @param jComponent O JComponent a ser removido.
	 */
	public void removeJComponente(JComponent jComponente) {
		this.painel.remove(jComponente);
	}
	/**
	 * Remove um JComponent de um determinado índice da tela.
	 * @param O índice da posição da qual será removido o JComponent.
	 */
	public void removeJComponente(int index) {
		this.painel.remove(index);
	}

	//Listenings
	public void actionPerformed(ActionEvent evento) {}
	public void mouseClicked(MouseEvent evento) {}
	public void mouseEntered(MouseEvent evento) {}
	public void mouseExited(MouseEvent evento) {}
	public void mousePressed(MouseEvent evento) {}
	public void mouseReleased(MouseEvent evento) {}
	
	//Utilidades
	/**
	 * Retorna um objeto Color que corresponde à cor do fundo da tela.
	 * @return Um objeto Color que corresponde à cor do fundo da tela.
	 */
	public Color getBackgroundColor() {
		return this.painel.getBackground();
	}
	/**
	 * Retorna um objeto Font que corresponde à fonte usada pela tela.
	 * @return um objeto Font que corresponde à fonte usada pela tela.
	 */
	public Font getFontLogo() {
		return this.fontLogo;
	}
	/**
	 * Retorna o painel {@link #painel} associado à Tela.
	 * @return o painel {@link #painel} associado à Tela.
	 */
	public JPanel getPainel() {
		return this.painel;
	}
}
