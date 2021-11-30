package br.com.fastlib.gerenciadores;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import br.com.fastlib.models.Capitulo;

/**
 *	A classe GerenciadorExportação, a qual contém apenas métodos estáticos, controla a exportação para LaTeX
 *	das instâncias de classes usadas. 
 * 
 *  @author Dayllon Vinícius Xavier Lemos
 *  @author Luan César Dutra Carvalho
 */

public class GerenciadorExportacao {
	private static String cabecalhoLatex = "\\documentclass{article}\n"
			+ "\\usepackage{titlesec}\n"
			+ "\\usepackage[legalpaper, margin=0.8in]{geometry}\n"
			+ "\\usepackage[utf8]{inputenc}\n"
			+ "\\usepackage[T1]{fontenc}\n"
			+ "\\usepackage[brazil]{babel}\n"
			+ "\\usepackage{listings}\n"
			+ "\\usepackage{color}\n"
			+ "\\usepackage{indentfirst}"
			+ "\\titleformat{\\chapter}[display]{\\normalfont\\bfseries}{}{0pt}{\\huge}\n"
			+ "\\newcommand{\\simpleTitle}[1]{\\large \\textbf{#1}\n"
			+ "\n"
			+ "}\n"
			+ "\\definecolor{mygreen}{rgb}{0.1,0.1,1.0}\n"
			+ "\\definecolor{mygray}{rgb}{0.5,0.5,0.5}\n"
			+ "\\definecolor{mymauve}{rgb}{0.58,0,0.82}\n"
			+ "\\lstset{ \n"
			+ "  backgroundcolor=\\color{white},\n"
			+ "  basicstyle=\\large\\ttfamily,\n"
			+ "  breakatwhitespace=false,\n"
			+ "  breaklines=true,\n"
			+ "  captionpos=b,\n"
			+ "  commentstyle=\\color{mygreen},\n"
			+ "  deletekeywords={...},\n"
			+ "  escapeinside={\\%*}{*)},\n"
			+ "  extendedchars=true,\n"
			+ "  firstnumber=1,\n"
			+ "  frame=single,\n"
			+ "  keepspaces=true,\n"
			+ "  keywordstyle=\\color{blue},\n"
			+ "  language=C++,\n"
			+ "  morekeywords={*,...},\n"
			+ "  numbers=left,\n"
			+ "  numbersep=7pt,\n"
			+ "  numberstyle=\\color{mygray},\n"
			+ "  rulecolor=\\color{black},\n"
			+ "  showspaces=false,\n"
			+ "  showstringspaces=false,\n"
			+ "  showtabs=false,\n"
			+ "  stepnumber=1,\n"
			+ "  stringstyle=\\color{mymauve},\n"
			+ "  tabsize=2,\n"
			+ "  inputencoding = utf8,\n"
			+ "  extendedchars = true,\n"
			+ "  literate      =\n"
			+ "      {á}{{\\'a}}1  {é}{{\\'e}}1  {í}{{\\'i}}1 {ó}{{\\'o}}1  {ú}{{\\'u}}1\n"
			+ "      {Á}{{\\'A}}1  {É}{{\\'E}}1  {Í}{{\\'I}}1 {Ó}{{\\'O}}1  {Ú}{{\\'U}}1\n"
			+ "      {à}{{\\`a}}1  {è}{{\\`e}}1  {ì}{{\\`i}}1 {ò}{{\\`o}}1  {ù}{{\\`u}}1\n"
			+ "      {À}{{\\`A}}1  {È}{{\\'E}}1  {Ì}{{\\`I}}1 {Ò}{{\\`O}}1  {Ù}{{\\`U}}1\n"
			+ "      {ä}{{\\\"a}}1  {ë}{{\\\"e}}1  {ï}{{\\\"i}}1 {ö}{{\\\"o}}1  {ü}{{\\\"u}}1\n"
			+ "      {Ä}{{\\\"A}}1  {Ë}{{\\\"E}}1  {Ï}{{\\\"I}}1 {Ö}{{\\\"O}}1  {Ü}{{\\\"U}}1\n"
			+ "      {â}{{\\^a}}1  {ê}{{\\^e}}1  {î}{{\\^i}}1 {ô}{{\\^o}}1  {û}{{\\^u}}1\n"
			+ "      {Â}{{\\^A}}1  {Ê}{{\\^E}}1  {Î}{{\\^I}}1 {Ô}{{\\^O}}1  {Û}{{\\^U}}1\n"
			+ "      {œ}{{\\oe}}1  {Œ}{{\\OE}}1  {æ}{{\\ae}}1 {Æ}{{\\AE}}1  {ß}{{\\ss}}1\n"
			+ "      {ç}{{\\c c}}1 {Ç}{{\\c C}}1 {ø}{{\\o}}1  {å}{{\\r a}}1 {Å}{{\\r A}}1\n"
			+ "      {ã}{{\\~a}}1  {õ}{{\\~o}}1  {Ã}{{\\~A}}1 {Õ}{{\\~O}}1\n"
			+ "      {ñ}{{\\~n}}1  {Ñ}{{\\~N}}1  {¿}{{?`}}1  {¡}{{!`}}1\n"
			+ "      {°}{{\\textdegree}}1 {º}{{\\textordmasculine}}1 {ª}{{\\textordfeminine}}1"
			+ "}\n"
			+ "\\title{\\Huge Competitive Programming Lib}\n"
			+ "\\author{\\Huge FastLib}\n"
			+ "\\date{}\n";
	private static String inicioDocumentoLatex = "\\begin{document}\n"
			+ "\\large\n"
			+ "\\maketitle\\newpage\n"
			+ "\\tableofcontents{}\\newpage\n";
	private static String finalDocumentoLatex = "\\end{document}";
	
	/**
	 * Concatena as partes necessárias para a construção do código LaTeX.
	 * 
	 * @param ArrayList contendo os capítulos a serem representados em LaTeX.
	 * @return O código LaTeX da representação dos objetos.
	 */
	public String geraCodigoLatex(ArrayList<Capitulo> capitulos) {
		String codigoLatex = cabecalhoLatex + inicioDocumentoLatex;
		for (Capitulo capitulo : capitulos)
			codigoLatex += capitulo.renderizarLatex();
		codigoLatex += finalDocumentoLatex;
		return codigoLatex;
	}
	 /**
	  * Mostra o código LaTeX em uma popup durante a execução do programa.
	  * 
	  * @param O código LaTeX da representação dos objetos gerado pela função {@link #geraCodigoLatex(ArrayList)}
	  * @see #geraCodigoLatex(ArrayList)
	  */
	public void exibirLatexEmPopup(String codigoLatex) {
		JFrame f = new JFrame();
		JTextArea t = new JTextArea();
		JScrollPane s = new JScrollPane(t);
		f.getContentPane();
		t.setText(codigoLatex);
		t.setTabSize(1);
		t.copy();
		t.setEditable(false);
		t.setLineWrap(true);
		f.getContentPane().add(s);
		f.setSize(500, 500);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setTitle("Código LaTeX");
        f.setVisible(true);
	}
}