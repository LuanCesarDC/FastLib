package br.com.fastlib.models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * A classe Codigo consiste em uma abstração dos componentes do tipo Codigo.
 * Um Codigo não pode conter nenhum outro componente, este será usado para
 * representar um programa o qual será escrito em uma determinada linguagem,
 * definida no atributo linguagem do tipo String.
 * 
 *  @author Dayllon Vinícius Xavier Lemos
 *  @author Luan César Dutra Carvalho
 */

@SuppressWarnings("serial")
public class Codigo extends Componente {
	private String conteudo;
	private String linguagem;
	private ArrayList<String> RESERVADAS = new ArrayList<String>();
	private ArrayList<String> PRIMITIVOS = new ArrayList<String>();
	

	private class HighlightDocument extends DefaultStyledDocument {
			
		private final AttributeSet pReservada;
		private final AttributeSet pNormal;
		private final AttributeSet tipoPrimitivo;
		private final AttributeSet pString;
	
		public HighlightDocument() {
			
            final StyleContext context = StyleContext.getDefaultStyleContext();
            pReservada 		= context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
            tipoPrimitivo 	= context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
            pNormal 		= context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
            pString			= context.addAttribute(context.getEmptySet(), StyleConstants.Foreground, new Color(148, 0, 210));
        }
		
		private void highlight() throws BadLocationException {
            String text = getText(0, getLength());
            String[] palavras = text.split("(\\W)");
            
            int in = 0;
            int fim = 0;
            
            for (String palavra : palavras) { // Palavras reservadas
                in = text.indexOf(palavra, in);
                fim = in + palavra.length();
                
                AttributeSet estilo = pNormal;
                if(RESERVADAS.contains(palavra)) {
                	estilo = pReservada;
                }
                else if(PRIMITIVOS.contains(palavra)) {
                	estilo = tipoPrimitivo;
                }
                else {
                	estilo = pNormal;
                
                }
                setCharacterAttributes(in, palavra.length(), estilo, false);
                in = fim + 1;
            }
            
            text = text.replaceAll("\\\\\"", ">º");
            text = text.replaceAll("\\\\\'", "<º");
            for(int i=0;i<text.length();i++) {
            	if(text.charAt(i) == '\"') {
            		in = i;
            		fim = text.indexOf('\"', in+1);
            		if(fim == -1) break;
            		setCharacterAttributes(in, fim-in+1, pString, false);
            		i = fim+1;
            	}
            }
            for(int i=0;i<text.length();i++) {
            	if(text.charAt(i) == '\'') {
            		in = i;
            		fim = text.indexOf('\'', in+1);
            		if(fim == -1) break;
            		setCharacterAttributes(in, fim-in+1, pString, false);
            		i = fim+1;
            	}	
            }
            text = text.replaceAll("<º", "\\\\\'");
            text = text.replaceAll(">º", "\\\\\"");
        }
		
		@Override
        public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
            super.insertString(offset, str, a);
            highlight();
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
        	//highlight();
            super.remove(offs, len);
        }
	}
	/**
	 * <p>Constrói um componente do tipo Codigo e atribui seu conteúdo.
	 * <p>A linguagem do Codigo é definida como C++ por padrão. Além disso
	 * carrega as palavras-chave da linguagem.
	 * @param conteudo Conteúdo do código.
	 * @see #carregaPalavrasChave()
	 */
	public Codigo(String conteudo) {
		this.setConteudo(conteudo);
		this.setLinguagem("C++");
		this.carregaPalavrasChave();
	}
	/**
	 * Constroi um componente do tipo Codigo e atribui seu conteúdo e sua linguagem.
	 * Além disso carrega as palavras-chave da linguagem.
	 * @param conteudo Conteúdo do código.
	 * @param linguagem A linguagem de programação do código.
	 * @see #carregaPalavrasChave() 
	 */
	public Codigo(String conteudo, String linguagem) {
		this.setConteudo(conteudo);
		this.setLinguagem(linguagem);
		this.carregaPalavrasChave();
	}

	//Atributo conteudo
	/**
	 * Retorna o atributo conteúdo do Codigo.
	 * @return O atributo conteúdo do Código.
	 */
	public String getConteudo() {
		return conteudo;
	}
	/**
	 * Atribui um conteúdo ao atributo conteudo do Codigo.
	 * @param conteudo O conteudo a ser atribuído.
	 */
	public void setConteudo(String conteudo) {
		this.conteudo = this.processaString(conteudo.replace("\t", "    "));
	}

	//Atributo linguagem
	/**
	 * Retorna o atributo linguagem do Codigo.
	 * @return O atributo linguagem do Codigo.
	 */
	public String getLinguagem() {
		return linguagem;
	}
	/**
	 * Atribui uma linguagem de programação ao atributo linguagem do Codigo.
	 * @param linguagem A linguagem a ser atribuida. 
	 */
	public void setLinguagem(String linguagem) {
		this.linguagem = linguagem;
	}
	/**
	 * Após definido o atributo linguagem do tipo String, faz a atribuição do conjunto de palavras-chave
	 * associado à linguagem de programação definida.
	 * O ArrayList denominado RESERVADAS armazenará as palavras reservadas da linguagem, enquanto o ArrayList
	 * PRIMITIVOS armazenará os nomes dos tipos primitivos.
	 * @see #RESERVADAS
	 * @see #PRIMITIVOS
	 */
	public void carregaPalavrasChave() {
		if(linguagem.toUpperCase().equals("C++")) {
			String str_reservadas[] = {	"alignas", "alignof", "and", "and_eq", "asm", "atomic_cancel", "atomic_commit", "atomic_noexcept",
										"auto", "bitand", "bitor", "break", "case", "catch", "class", "compl", "concept", "consteval", "constexpr",
										"constinit", "const_cast", "continue", "co_await", "co_return", "co_yield", "decltype", "default", "delete",
										"do", "dynamic_cast", "else", "enum", "explicit", "export", "extern", "false", "friend", "goto", "if", 
										"inline", "mutable", "namespace", "new", "noexcept", "not", "not_eq", "nullptr", "operator",
										"or", "or_eq", "private", "protected", "public", "reflexpr", "register", "reinterpret_cast", "requires",
										"return", "signed", "sizeof", "static", "static_assert", "static_cast", "struct", "switch",
										"synchronized", "template", "this", "thread_local", "throw", "true", "try", "typedef", "typeid",
										"typename", "using", "virtual", "volatile", "while", "xor", "xor_eq"};
			String str_tipos[] = {  "bool", "char", "char8_t", "char16_t", "char32_t", "const", "double", "float", "int", "long", "short",
					 				"signed", "union", "unsigned", "void"};
			RESERVADAS.addAll(Arrays.asList(str_reservadas));
			PRIMITIVOS.addAll(Arrays.asList(str_tipos));
		
		}else {
			String str_reservadas[] = {};
			String str_tipos[] = {};
			RESERVADAS.addAll(Arrays.asList(str_reservadas));
			PRIMITIVOS.addAll(Arrays.asList(str_tipos));
		}
	}
	/**
	 * Dado uma String str, faz a quebra de linha da String em blocos de tamanho n.
	 * @param str String a ser processada pela função.
	 * @param n Tamanho de cada bloco. 
	 */
	public String quebraLinhas(String str, int tamanhoLinha) {
		String novaStr = "";
		for (int i = 0; i < str.length(); i++) {
			if ((i+1)%tamanhoLinha == 0)
				novaStr += '\n';
			novaStr += str.charAt(i);
		}
		return novaStr;
	}
	//Renderização
	/**
	 * ({@inheritDoc})
	 * Um Codigo será renderizado na tela como um JTextPane.
	 * O atributo conteudo da classe Codigo é uma String que representa o próprio código.
	 * A função atual faz a formatação para que haja a quebra de cada linha corretamente, 
	 * além de utilizar a Nested Class HilightDocument para fazer o "Syntax Highlight" do
	 * código.
	 * @see #quebraLinhas(String, int)
	 * @return o próprio JTextPane (que representa o código) já formatado.
	 */
	public JComponent renderizarTela() {
		String str = this.conteudo;
		String[] palavras = str.split("\n");
		str = "";
		for(String s:palavras) {
			if(s.length() <= 75) { //75 é o tamanho maximo da string
				str += (str.length() == 0 ? "" : "\n") + s;
			}else {
				str += "\n" + quebraLinhas(s, 75);
			}
		}
		HighlightDocument document = new HighlightDocument();
        JTextPane jtextpane = new JTextPane(document);
        
        jtextpane.setAlignmentX(0);
        jtextpane.setFont(new Font("Monospaced", 0, 14));
        jtextpane.setEditable(false);
        jtextpane.setOpaque(false);
        jtextpane.setText(str);
        jtextpane.setMaximumSize(new Dimension(jtextpane.getMaximumSize().width, jtextpane.getPreferredSize().height));
        jtextpane.setMinimumSize(new Dimension(jtextpane.getMaximumSize().width, jtextpane.getPreferredSize().height));
        
		return jtextpane;
	}
	/**
	 * ({@inheritDoc})
	 * Em LaTeX o Código é representado dentro de um ambiente denominado "lstlisting", que é próprio para Códigos.
	 * O atributo conteudo do tipo String contém o código que será renderizado.
	 * 
	 * @return uma String contendo o equivalente à representação do componente no PDF porém descrito em LaTeX.
	 */
	public String renderizarLatex() {
		String latexStr = "\\vspace{5pt}\\begin{lstlisting}\n" + this.conteudo + "\n\\end{lstlisting}\n\n";
		return latexStr;
	}  

}