package br.com.fastlib.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import br.com.fastlib.exceptions.CaixaTextoVaziaException;
import br.com.fastlib.gerenciadores.GerenciadorArquivos;
import br.com.fastlib.gerenciadores.GerenciadorExportacao;
import br.com.fastlib.models.Capitulo;
import br.com.fastlib.models.Codigo;
import br.com.fastlib.models.Componente;
import br.com.fastlib.models.Texto;
import br.com.fastlib.models.Titulo;
import br.com.fastlib.models.Topico;

/**
 * Classe da Janela Principal, onde o usuário realiza todas as ações relacionadas ao programa.
 * 
 * 
 * @author Dayllon Vinicius Xavier Lemos
 * @author Luan César Dutra Carvalho
 */

@SuppressWarnings("serial")
public final class TelaPrincipal extends Tela implements TreeSelectionListener{
	private String pathDadosArquivo = "./CapitulosArquivoDados.dat";
	private GerenciadorExportacao gerenciadorExportacao = null;
	private GerenciadorArquivos gerenciadorArquvios = null;
	private ArrayList<Capitulo> capitulos = new ArrayList<Capitulo>();					
	private ArrayList<DefaultMutableTreeNode> nodesComponentesJTree = new ArrayList<DefaultMutableTreeNode>();
	private ArrayList<DefaultMutableTreeNode> nodesFolhaComponentesJTree = new ArrayList<DefaultMutableTreeNode>();
	private ArrayList<Componente> componentesJTree = new ArrayList<Componente>();
	private ArrayList<Componente> componentesFolhaJTree = new ArrayList<Componente>();
	private ArrayList<Componente> expandedComponentes = new ArrayList<Componente>();
	private Componente componenteSelecionado = null, itemComponenteSelecionado = null;
	private DefaultMutableTreeNode raizTopicosNode = null;
	private int operacao = -1; // 0 -> adicionar, 1 -> editar, 2 -> excluir
	
	private int previewPdfIdentation = 20;
	private JLabel logoLabel = null, logoLabelProfundidade = null, mensagemPopupJLabel = null;
	private JPanel pesquisaJPanel = null, previewPdfJPanel = null, navegarTopicosJPanel = null, 
			inputJPanel = null, optionsComponentesJPanel = null, popupJPanel = null, centralizarMensagemPopupJPanel;
	private JScrollPane scrollPesquisa = null, scrollPreviewPdf = null, inputTextoPopupJScrollPane = null;
	private JTree navegarTopicos = null;
	private JTextField inputPesquisa = null;
	private JLabel labelPesquisa = null, labelButtonGerarLatex = null, buttonExcluir = null, 
			buttonEditar = null, buttonAdicionar = null, confirmarPopupButton= null, cancelarPopupButton = null;
	private JPopupMenu opcoesAdicionarJPopupMenu = null;
	private JMenuItem topicoMenuItem = null, capituloMenuItem = null, textoMenuItem = null, tituloMenuItem = null, codigoMenuItem = null;
	private JDialog popupJDialog = null;
	private JTextField inputPopupJTextField = null;
	private JTextArea inputTextoPopupJTextArea = null;
	private String textoInputPesquisa = null, adicionarComponenteTipo = null;
	
	public TelaPrincipal(String nome, int largura, int altura){
		super(nome, largura, altura);
		this.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent evt) {
			   gravaDados();
		   }
		});
		this.gerenciadorExportacao = new GerenciadorExportacao();
		this.gerenciadorArquvios = new GerenciadorArquivos(this.pathDadosArquivo);
		this.carregaDados();
		//Logo
		this.logoLabel = new JLabel("]FastLib[");
		this.logoLabel.setFont(this.getFontLogo().deriveFont(0, 70f));
		this.logoLabel.setForeground(new Color(79, 60, 62));
		this.logoLabel.setBounds(30, 20, 300, 90);
		
		this.logoLabelProfundidade = new JLabel("]FastLib[");
		this.logoLabelProfundidade.setFont(this.getFontLogo().deriveFont(0, 70f));
		this.logoLabelProfundidade.setForeground(new Color(180, 156, 124));
		this.logoLabelProfundidade.setBounds(33, 23, 300, 90);
		
		//Paineis
		this.pesquisaJPanel = new JPanel();
		this.pesquisaJPanel.setBounds(30, 140, 300, 460);
		this.pesquisaJPanel.setBackground(new Color(0,0,0,0));
		this.pesquisaJPanel.setLayout(new BoxLayout(this.pesquisaJPanel,BoxLayout.Y_AXIS));
		
		this.optionsComponentesJPanel = new JPanel();
		this.optionsComponentesJPanel.setBounds(360, 90, 709, 40);
		this.optionsComponentesJPanel.setLayout(new BoxLayout(this.optionsComponentesJPanel, BoxLayout.X_AXIS));
		this.optionsComponentesJPanel.setOpaque(false);
		UIManager.put("MenuItem.selectionBackground", new Color(180, 156, 124));//Mudando o padrao do MenuItem
		this.opcoesAdicionarJPopupMenu = new JPopupMenu();
		this.opcoesAdicionarJPopupMenu.setBackground(new Color(209, 200, 190));
		this.opcoesAdicionarJPopupMenu.setBorder(new CompoundBorder(new LineBorder(new Color(88, 68, 50)), new MatteBorder(2, 2, 2, 2, new Color(209, 200, 190))));
		
		//Botoes
		this.inicializarBotoes();
		
		//Pdf preview
		this.previewPdfJPanel = new JPanel();
		this.previewPdfJPanel.setLayout(new BoxLayout(this.previewPdfJPanel,BoxLayout.Y_AXIS));
		this.previewPdfJPanel.setBorder(new EmptyBorder(5, 10, 0, 0));
		this.scrollPreviewPdf = new JScrollPane(this.previewPdfJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPreviewPdf.setBounds(360, 140, 710, 460);
		this.previewPdfJPanel.setBackground(new Color(251, 252, 247)); // 251, 252, 247
		
		//Input
		this.textoInputPesquisa = "";
		this.inputJPanel = new JPanel();
		this.inputJPanel.setLayout(new BoxLayout(this.inputJPanel,BoxLayout.X_AXIS));
		this.inputJPanel.setMaximumSize(new Dimension(300, 35));
		this.inputJPanel.setBackground(new Color(0, 0, 0, 0));
		this.inputPesquisa = new JTextField();
		this.inputPesquisa.setFont(new Font("Arial", 0, 15));
		this.labelPesquisa = new JLabel();
		this.labelPesquisa.setIcon(new ImageIcon("./_imgs/lupa.png"));
		this.labelPesquisa.setBorder(new EmptyBorder(0, 5, 0, 0));
		this.labelPesquisa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.inputJPanel.add(this.inputPesquisa);
		this.inputJPanel.add(this.labelPesquisa);
		this.pesquisaJPanel.add(this.inputJPanel);

		//JTree
		this.navegarTopicosJPanel = new JPanel(new CardLayout());
		this.scrollPesquisa = new JScrollPane(this.navegarTopicosJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.scrollPesquisa.setBackground(new Color(0,0,0,0));
		this.scrollPesquisa.setBorder(new CompoundBorder(new EmptyBorder(10, 0, 0, 0), new MatteBorder(1, 1, 1, 0, Color.gray)));
		this.pesquisaJPanel.add(this.scrollPesquisa);
		
		//JDialog (popup editar/adicionar)
		this.popupJDialog = new JDialog(this, true);
		this.popupJDialog.setSize(new Dimension(700, 400));
		this.popupJDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.popupJDialog.setLocationRelativeTo(null);
		this.popupJPanel = new JPanel();
		this.popupJPanel.setLayout(new GroupLayout(this.popupJPanel));
		this.popupJPanel.setBackground(new Color(180, 156, 124));
		this.centralizarMensagemPopupJPanel = new JPanel();
		this.centralizarMensagemPopupJPanel.setOpaque(false);
		this.mensagemPopupJLabel = new JLabel();
		this.mensagemPopupJLabel.setFont(new Font("Arial", 0, 20));
		this.inputPopupJTextField = new JTextField();
		this.inputPopupJTextField.setFont(new Font("Arial", 0, 16));
		this.inputPopupJTextField.setBorder(new CompoundBorder(new LineBorder(new Color(88, 68, 50)), new EmptyBorder(1, 3, 1, 3)));
		this.inputTextoPopupJTextArea = new JTextArea();
		this.inputTextoPopupJTextArea.setFont(new Font("Arial", 0, 14));
		this.inputTextoPopupJTextArea.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 1, new Color(88, 68, 50)), new EmptyBorder(1, 3, 1, 3)));
		this.inputTextoPopupJTextArea.setLineWrap(true);
		this.inputTextoPopupJTextArea.setWrapStyleWord(true);
		this.inputTextoPopupJTextArea.setTabSize(1);
		this.inputTextoPopupJScrollPane = new JScrollPane(this.inputTextoPopupJTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.inputTextoPopupJScrollPane.setBorder(new LineBorder(new Color(88, 68, 50)));
		this.popupJDialog.add(this.popupJPanel);
		
		this.addJComponente(this.logoLabel);
		this.addJComponente(this.logoLabelProfundidade);
		this.addJComponente(this.optionsComponentesJPanel);
		this.addJComponente(this.opcoesAdicionarJPopupMenu);
		this.addJComponente(this.labelButtonGerarLatex);
		this.addJComponente(this.scrollPreviewPdf);
		this.addJComponente(this.pesquisaJPanel);
		
		this.carregaTela();
	}
	/**
	 * Carrega as principais visualizações associadas à tela, são essas
	 * a JTree dos componentes, e o Preview do PDF. Além disso faz a primeira
	 * construção do menu de opções associado ao botão Adicionar, e adiciona
	 * um MouseListener ao botão de pesquisa. 
	 * @see #constroiNavegarTopicosJTree()
	 * @see #constroiPreviewPdf()
	 * @see #constroiOpcoesAdicionarJPopupMenu()
	 */
	private void carregaTela() {
		//Construindo o Preview PDF
		this.constroiNavegarTopicosJTree();
		this.constroiPreviewPdf();
		this.constroiOpcoesAdicionarJPopupMenu();
		
		this.labelPesquisa.addMouseListener(this);
	}
	/**
	 * Atualiza o componente selecionado na JTree e reconstrói o menu de opções
	 * associado ao botão adicinar e o Preview do PDF.
	 * @see #atualizaItemComponenteSelecionado(Componente)
	 * @see #constroiPreviewPdf()
	 * @see #constroiOpcoesAdicionarJPopupMenu() 
	 */
	private void atualizaComponenteSelecionado(Componente componente) { 
		this.componenteSelecionado = componente;
		this.constroiPreviewPdf();
		this.constroiOpcoesAdicionarJPopupMenu();
	}
	/**
	 * Atualiza o {@link #itemComponenteSelecionado} e se este não for uma instância de 
	 * Capítulo ou de Tópico, reconstroi o preview do PDF de modo que a função de reconstrução
	 * do PDF destaque o componente selecionado no preview.
	 */
	private void atualizaItemComponenteSelecionado(Componente componente) {
		this.itemComponenteSelecionado = componente;
	}
	/**
	 * Cria, posiciona e estiliza todos os botões que estarão da TelaPrincipal. Além
	 * disso, adiciona os respectivos ActionListeners que serão usados para controlar
	 * as ações associadas aos botões.
	 * @see #criaLabelButton(String, String)
	 * @see #criaMenuItem(String)
	 */
	private void inicializarBotoes() {
		//PopupButtons
		this.confirmarPopupButton = criaLabelButton("Confirmar", "./_imgs/confirmar.png");
		this.cancelarPopupButton = criaLabelButton("Cancelar", "./_imgs/cancelar.png");
		this.confirmarPopupButton.addMouseListener(this);
		this.cancelarPopupButton.addMouseListener(this);
		
		//LaTeX Button
		this.labelButtonGerarLatex = new JLabel();
		this.labelButtonGerarLatex.setIcon(new ImageIcon("./_imgs/gerarLatex.png"));
		this.labelButtonGerarLatex.setBounds(1020, 20, 50, 50);
		this.labelButtonGerarLatex.setToolTipText("Gerar LaTeX");
		this.labelButtonGerarLatex.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.labelButtonGerarLatex.addMouseListener(this);
		
		//Adicionar, Editar e Excluir
		this.buttonAdicionar = criaLabelButton("Adicionar", "./_imgs/adicionar.png");
		this.optionsComponentesJPanel.add(this.buttonAdicionar);
		this.buttonAdicionar.addMouseListener(this);
		
		JLabel separacao1 = new JLabel();
		separacao1.setBorder(new EmptyBorder(0, 0, 0, 364));
		this.optionsComponentesJPanel.add(separacao1);
		
		this.buttonEditar = criaLabelButton("Editar", "./_imgs/editar.png");
		this.optionsComponentesJPanel.add(this.buttonEditar);
		this.buttonEditar.addMouseListener(this);
		
		JLabel separacao2 = new JLabel();
		separacao2.setBorder(new EmptyBorder(0, 0, 0, 5));
		this.optionsComponentesJPanel.add(separacao2);
		
		this.buttonExcluir = criaLabelButton("Excluir", "./_imgs/excluir.png");
		this.optionsComponentesJPanel.add(this.buttonExcluir);
		this.buttonExcluir.addMouseListener(this);
		
		//MenuItem
		this.capituloMenuItem = this.criaMenuItem("Capítulo");
		this.topicoMenuItem = this.criaMenuItem("Tópico");
		this.tituloMenuItem = this.criaMenuItem("Título");
		this.textoMenuItem = this.criaMenuItem("Texto");
		this.codigoMenuItem = this.criaMenuItem("Código");
		
		this.capituloMenuItem.addActionListener(this);
		this.topicoMenuItem.addActionListener(this);
		this.tituloMenuItem.addActionListener(this);
		this.textoMenuItem.addActionListener(this);
		this.codigoMenuItem.addActionListener(this);
	}
	/**
	 * Cria um JLabel com a função de um botão. Além estiliza e define as
	 * principais propriedades do JLabel necessárias.
	 * @param mensagem Mensagem presente no botão
	 * @param pathIcone Caminho do ícone do botão
	 * @return O próprio JLabel criado
	 */
	private JLabel criaLabelButton(String mensagem, String pathIcone) {
		JLabel auxButton = new JLabel(mensagem);
		auxButton.setFont(new Font("Arial", Font.BOLD, 16));
		auxButton.setIcon(new ImageIcon(pathIcone));
		auxButton.setIconTextGap(0);
		auxButton.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, new Color(88, 68, 50)), new EmptyBorder(1, 2, 1, 9)));
		auxButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		auxButton.setBackground(new Color(209, 200, 190));
		auxButton.setOpaque(true);
		return auxButton;
	}
	/**
	 * Cria um JMenuItem estilizado, além de definir as propriedades
	 * necessárias.
	 * @param mensagem Mensagem presente no JMenuItem
	 * @return O próprio JMenuItem
	 */
	private JMenuItem criaMenuItem(String mensagem) {
		JMenuItem auxMenuItem = new JMenuItem(mensagem);
		auxMenuItem.setFont(new Font("Arial", Font.BOLD, 16));
		auxMenuItem.setBorder(new MatteBorder(0, 1, 0, 0, new Color(180, 156, 124)));
		auxMenuItem.setOpaque(false);
		auxMenuItem.setPreferredSize(new Dimension(125, 30));
		return auxMenuItem;
	}
	/**
	 * <p>Constrói o menu de opções referente à adição de novos componentes. As opções
	 * presentes no menu variam de acordo com o componente selecionado na JTree. O menu
	 * por padrão não é visível, sendo mostrado apenas quando o botão Adicionar é clicado.
	 * <p>Caso nenhum componente esteja selecionado, a opção que será construída é um capítulo
	 * por ser o componente mais geral. Caso um capítulo esteja selecionado, a opção que será
	 * construída é um tópico. Caso um tópico seja selecionado, as opções construídas serão
	 * Titulo, Texto, Código, e tambem Subtópico no caso do tópico selecionado ter nível igual
	 * a zero.
	 * <p>Em ultimo caso, se um componente Titulo, Texto, Codigo ou Subtopico estiver selecionado
	 * as opções que serão construídas assumindo o caso em que o componente pai desse componente
	 * selecionado estivesse selecionado.
	 * 
	 * @see #mouseClicked(MouseEvent)
	 * @see #componenteSelecionado
	 */
	private void constroiOpcoesAdicionarJPopupMenu() {
		this.opcoesAdicionarJPopupMenu.removeAll();
		if (this.componenteSelecionado == null) {
			this.opcoesAdicionarJPopupMenu.add(this.capituloMenuItem);
		}
		else if (this.componenteSelecionado instanceof Capitulo) {
			this.topicoMenuItem.setText("Tópico");
			this.opcoesAdicionarJPopupMenu.add(this.topicoMenuItem);
		}
		else {
			this.opcoesAdicionarJPopupMenu.add(this.tituloMenuItem);
			this.opcoesAdicionarJPopupMenu.add(this.textoMenuItem);
			this.opcoesAdicionarJPopupMenu.add(this.codigoMenuItem);
			if (((Topico)this.componenteSelecionado).getNivel() == 0) {
				this.topicoMenuItem.setText("Subtópico");
				this.opcoesAdicionarJPopupMenu.add(this.topicoMenuItem);
			}
		}
	}
	
	//Popup JDialog
	/**
	 * É usado para preparar a adição de  um componente ao clicar em um dos menus
	 * relacionados ao botão {@link #buttonAdicionar}. Além disso chama a função {@link #constroiPopup(String)}
	 * para construir a Popup de adição de componente. Observação, o componente só será adicionado após
	 * haver um clique no botão de confirmação.
	 * @see #constroiPopup(String)
	 */
	private void adicionarComponente(String tipo) {
		this.adicionarComponenteTipo = tipo;
		this.operacao = 0;
		this.constroiPopup(tipo);
		this.popupJDialog.setVisible(true);
	}
	/**
	 * Define qual o tipo do componente que está selecionado na JTree e através
	 * desse tipo, chama a função {@link #constroiPopup(String)} passando como
	 * parâmetro esse tipo. Além disso disponibiliza na caixa de texto da popup
	 * o conteudo do componente selecionado, para que haja a edição.
	 * @see #constroiPopup(String)
	 */
	private void editarComponenteSelecionado() {
		if (this.itemComponenteSelecionado == null)
			return;
		this.operacao = 1;
		if (this.itemComponenteSelecionado instanceof Capitulo) {
			this.constroiPopup("Capítulo");
			this.inputPopupJTextField.setText(((Capitulo)this.itemComponenteSelecionado).getNome());
		}
		else if (this.itemComponenteSelecionado instanceof Topico) {
			this.constroiPopup("Tópico");
			this.inputPopupJTextField.setText(((Topico)this.itemComponenteSelecionado).getNome());
		}
		else if (this.itemComponenteSelecionado instanceof Titulo) {
			this.constroiPopup("Título");
			this.inputPopupJTextField.setText(((Titulo)this.itemComponenteSelecionado).getConteudo());
		}
		else if (this.itemComponenteSelecionado instanceof Texto) {
			this.constroiPopup("Texto");
			this.inputTextoPopupJTextArea.setText(((Texto)this.itemComponenteSelecionado).getConteudo());
		}
		else if (this.itemComponenteSelecionado instanceof Codigo) {
			this.constroiPopup("Código");
			this.inputTextoPopupJTextArea.setText(((Codigo)this.itemComponenteSelecionado).getConteudo());
		}
		this.popupJDialog.setVisible(true);
	}
	/**
	 * Define qual o tipo do componente que está selecionado na JTree e através
	 * desse tipo, chama a função {@link #constroiPopup(String)} passando como
	 * parâmetro o texto a ser exibido na confirmação de remoção do componente.
	 * @see #constroiPopup(String)
	 * 
	 */
	private void excluirComponenteSelecionado() {
		if (this.itemComponenteSelecionado == null)
			return;
		this.operacao = 2;
		if (this.itemComponenteSelecionado instanceof Capitulo)
			this.constroiPopup("Remover Capítulo");
		else if (this.itemComponenteSelecionado instanceof Topico)
			this.constroiPopup("Remover Tópico");
		else if (this.itemComponenteSelecionado instanceof Titulo)
			this.constroiPopup("Remover Título");
		else if (this.itemComponenteSelecionado instanceof Texto)
			this.constroiPopup("Remover Texto");
		else if (this.itemComponenteSelecionado instanceof Codigo)
			this.constroiPopup("Remover Código");
		this.popupJDialog.setVisible(true);
	}
	/**
	 * Constrói a Popup que é aberta ao tentar Adicionar ou Editar um componente.
	 * @param tipo Tipo do componente a ser adicionado ou editado
	 */
	private void constroiPopup(String tipo) {
		this.popupJPanel.removeAll();
		this.centralizarMensagemPopupJPanel.add(this.mensagemPopupJLabel);
		this.popupJPanel.add(this.centralizarMensagemPopupJPanel);
		if (this.operacao == 0)
			this.mensagemPopupJLabel.setText("Cadastrar " + tipo);
		else if (this.operacao == 1)
			this.mensagemPopupJLabel.setText("Editar " + tipo);
		if (tipo.equals("Capítulo") || tipo.equals("Tópico") || tipo.equals("Título")) {
			this.centralizarMensagemPopupJPanel.setBounds(50, 100, 600, 40);
			this.inputPopupJTextField.setBounds(100, 160, 500, 40);
			this.confirmarPopupButton.setBounds(201, 220, 140, 40);
			this.cancelarPopupButton.setBounds(359, 220, 140, 40);
			this.popupJPanel.add(this.inputPopupJTextField);
		}
		else if (tipo.equals("Texto") || tipo.equals("Código")) {
			this.centralizarMensagemPopupJPanel.setBounds(50, 20, 600, 40);
			this.inputTextoPopupJScrollPane.setBounds(100, 70, 500, 210);
			this.confirmarPopupButton.setBounds(201, 300, 140, 40);
			this.cancelarPopupButton.setBounds(359, 300, 140, 40);
			this.popupJPanel.add(this.inputTextoPopupJScrollPane);
		}
		else {
			this.mensagemPopupJLabel.setText(tipo);
			this.centralizarMensagemPopupJPanel.setBounds(50, 130, 600, 40);
			this.confirmarPopupButton.setBounds(201, 180, 140, 40);
			this.cancelarPopupButton.setBounds(359, 180, 140, 40);
		}
		this.popupJPanel.add(this.confirmarPopupButton);
		this.popupJPanel.add(this.cancelarPopupButton);
	}
	
	//Funcoes de construcao do JTree
	/**
	 * Constroi a JTree de acordo com o {@link #componenteSelecionado} e o {@link #itemComponenteSelecionado}.
	 * @see #constroiJTreeNodes()
	 */
	private void constroiNavegarTopicosJTree() {
		if (this.raizTopicosNode == null)
			this.raizTopicosNode = new DefaultMutableTreeNode("PDF");
		else {
			//Salva os componentes abertos da JTree
			this.expandedComponentes.clear();
			for (int i = 0; i < this.componentesJTree.size(); i++) {
				if (this.navegarTopicos.isExpanded(new TreePath(this.nodesComponentesJTree.get(i).getPath())))
					this.expandedComponentes.add(this.componentesJTree.get(i));
			}
			this.raizTopicosNode.removeAllChildren();
		}
		this.componentesJTree.clear();
		this.nodesComponentesJTree.clear();
		this.constroiJTreeNodes();
		if (this.navegarTopicos != null) {
			this.navegarTopicos.removeAll();
			this.navegarTopicosJPanel.remove(this.navegarTopicos);
		}
		this.navegarTopicos = new JTree(this.raizTopicosNode);
		//Abre os componentes abertos salvos
		int indexAux;
		for (int i = 0; i < this.expandedComponentes.size(); i++) {
			indexAux = this.componentesJTree.indexOf(this.expandedComponentes.get(i));
			if (indexAux == -1) continue;
			this.navegarTopicos.expandPath(new TreePath(this.nodesComponentesJTree.get(indexAux).getPath()));
		}
		if (this.operacao == 0 && this.componenteSelecionado != null) {
			indexAux = this.componentesJTree.indexOf(this.componenteSelecionado);
			this.navegarTopicos.expandPath(new TreePath(this.nodesComponentesJTree.get(indexAux).getPath()));
		}
		this.navegarTopicos.addTreeSelectionListener(this);
		this.navegarTopicosJPanel.add(this.navegarTopicos);
		this.navegarTopicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		this.navegarTopicos.revalidate();
		this.navegarTopicos.repaint();
	}
	/**
	 * Constroi a JTree para cada um dos capitulos de {@link #capitulos}
	 * @see #constroiComponenteJTreeNodes(Componente, DefaultMutableTreeNode, boolean) 
	 */
	private void constroiJTreeNodes() {
		for (Capitulo capitulo : this.capitulos) {
			this.constroiComponenteJTreeNodes(capitulo, this.raizTopicosNode, false);
		}
	}
	/**
	 * Adiciona na raiz na da JTree, de forma hierárquica, um novo componente construido se for uma subString de {@link #textoInputPesquisa} 
	 * @param componente o componente a ser gerado 
	 * @param nodeParent a item pai do componente a ser gerado na arvore
	 * @param adicionaSubTree booleano para identificar se é para construir toda a subárvore 
	 * @return booleano que indica se um determinado componente da subárvore foi construído
	 */
	private boolean constroiComponenteJTreeNodes(Componente componente, DefaultMutableTreeNode nodeParent, boolean adicionaSubTree) {
		boolean adicionaElemento = adicionaSubTree | existeSubstring(componente, this.textoInputPesquisa);
		boolean filhoAdicionado = false;
		if (componente instanceof Capitulo) {
			Capitulo capituloAux = (Capitulo) componente;
			DefaultMutableTreeNode capituloNode = new DefaultMutableTreeNode(capituloAux.getNome());
			this.componentesJTree.add(componente);
			this.nodesComponentesJTree.add(capituloNode);
			for (int i = 0; i < capituloAux.getTopicosSize(); i++) {
				filhoAdicionado |= this.constroiComponenteJTreeNodes(capituloAux.getTopico(i), capituloNode, adicionaElemento);
			}
			adicionaElemento |= filhoAdicionado;
			if (adicionaElemento) nodeParent.add(capituloNode);
		}
		else if (componente instanceof Topico) {
			Topico topicoAux = (Topico) componente;
			DefaultMutableTreeNode topicoNode = new DefaultMutableTreeNode(topicoAux.getNome());
			this.componentesJTree.add(componente);
			this.nodesComponentesJTree.add(topicoNode);
			for (int i = 0; i < topicoAux.getComponentesSize(); i++) {
				filhoAdicionado |= this.constroiComponenteJTreeNodes(topicoAux.getComponente(i), topicoNode, adicionaElemento);
			}
			adicionaElemento |= filhoAdicionado;
			if (adicionaElemento) nodeParent.add(topicoNode);
		}
		else {
			DefaultMutableTreeNode componenteNode = new DefaultMutableTreeNode(componente.getClass().getSimpleName());
			this.nodesFolhaComponentesJTree.add(componenteNode);
			this.componentesFolhaJTree.add(componente);
			if (adicionaElemento) nodeParent.add(componenteNode);
		}
		return adicionaElemento;
	}
	/**
	 * Verifica se existe no conteúdo do componente uma determinada String.
	 * @param componente Componente cujo conteúdo será verificado.
	 * @param substring String que será procurada.
	 */
	private boolean existeSubstring(Componente componente, String substring) {
		if (substring.length() == 0) return true;
		String auxString = null;
		if (componente instanceof Capitulo) auxString = ((Capitulo)componente).getNome();
		else if (componente instanceof Topico) auxString = ((Topico)componente).getNome();
		else if (componente instanceof Titulo) auxString = ((Titulo)componente).getConteudo();
		else if (componente instanceof Texto) auxString = ((Texto)componente).getConteudo();
		else if (componente instanceof Codigo) auxString = ((Codigo)componente).getConteudo();
		return auxString.toUpperCase().contains(substring.toUpperCase());
	}
	
	//------------------------------------------
	//Funcoes de contrucao do PDF
	/**
	 * Se não houver nenhum componente selecionado na JTree, constrói o preview
	 * de todo o PDF. Caso haja algum componente selecionado, constrói o preview
	 * com base nesse componente, renderizando o próprio componente e todos os
	 * componentes abaixo.
	 * @see #constroiComponente(Componente, JPanel, int)
	 */
	private void constroiPreviewPdf() {
		this.previewPdfJPanel.removeAll();
		if (this.componenteSelecionado == null) {
			for (Capitulo capitulo : this.capitulos) {
				this.constroiComponente(capitulo, this.previewPdfJPanel, 0);
			}
			if (this.capitulos.size() == 0) {
				JLabel nenhumCadastroJLabel = new JLabel("<html><div style='text-align: center;'>Nenhum conteúdo cadastrado ainda.<br>Realize o cadastro!</div></html>");
				nenhumCadastroJLabel.setFont(this.getFontLogo().deriveFont(0, 55f));
				nenhumCadastroJLabel.setForeground(new Color(150, 126, 94));
				nenhumCadastroJLabel.setBorder(new EmptyBorder(100, 85, 0, 0));
				this.previewPdfJPanel.add(nenhumCadastroJLabel);
			}
		}
		else
			this.constroiComponente(this.componenteSelecionado, this.previewPdfJPanel, 0);
		this.previewPdfJPanel.revalidate();
		this.previewPdfJPanel.repaint();
	}
	/**
	 * Função recursiva que dado um componente, renderiza em um JPanel todos os componentes
	 * abaixo deste. Ou seja, se tivermos um Capítulo, serão renderizados nesse JPanel todos
	 * os tópicos filhos desse capítulo, e recursivamente, todos os componentes filhos de cada
	 * tópico e subtópico. 
	 * @param componente Componente que irá definir a construção do JPanel
	 * @param painel Painel onde os componentes serão renderizados
	 * @param nivelIdentacao Nivel de identação do componente dado à função. Como a função é recursiva, os próximos
	 * Níveis de identação serão decididos de acordo com este Nível dado na chamada da função.
	 * @see #encapsulaJPanel(JComponent, int)
	 */
	private void constroiComponente(Componente componente, JPanel painel, int nivelIdentacao) {
		JPanel auxPainel = this.encapsulaJPanel(componente.renderizarTela(), nivelIdentacao);
		painel.add(auxPainel);
		if (componente instanceof Capitulo) {
			for (int i = 0; i < ((Capitulo)componente).getTopicosSize(); i++) {
				this.constroiComponente(((Capitulo)componente).getTopico(i), painel, nivelIdentacao+1);
			}
		}
		else if (componente instanceof Topico) {
			for (int i = 0; i < ((Topico)componente).getComponentesSize(); i++) {
				this.constroiComponente(((Topico)componente).getComponente(i), painel, nivelIdentacao+1);
			}
		}
		else if (componente == this.itemComponenteSelecionado){
			auxPainel.setOpaque(true);
		}
	}
	/**
	 * Coloca um JComponent qualquer dentro de um JPanel, fazendo com que o posicionamento
	 * dele na tela de renderização de PDF seja padronizada.
	 * @param componente O componente a ser renderizado
	 * @param nivelIdentacao O nível de identação do componente. Cada componente terá uma identação que remete
	 * ao seu nível hierárquico na JTree, ou seja, um Capítulo ficará mais à esquerda que um tópico
	 * e assim sucessivamente.
	 * @return o próprio JPanel com o Componente já encapsulado.
	 */
	private JPanel encapsulaJPanel(JComponent componente, int nivelIdentacao) {
		JPanel capsulaJPanel = new JPanel();
		capsulaJPanel.setLayout(new BoxLayout(capsulaJPanel, BoxLayout.Y_AXIS));
		capsulaJPanel.setBorder(new EmptyBorder(0, nivelIdentacao * this.previewPdfIdentation, 0, 0));
		capsulaJPanel.setOpaque(false);
		capsulaJPanel.setBackground(new Color((float)0.3, (float)0.3, (float)0.7, (float)0.2));
		capsulaJPanel.add(componente);
		return capsulaJPanel;
	}
	//----------------------------
	/**
	 * Carrega os dados gravados no arquivo cujo caminho é {@link #pathDadosArquivo}.
	 * Os dados carregados são inseridos no ArrayList {@link #capitulos}.
	 */
	private void carregaDados() {
		this.capitulos = gerenciadorArquvios.carregaCapitulosEmArrayList();
	}
	/**
	 * Grava os os objetos do ArrayList {@link #capitulos} em um arquivo cujo caminho é
	 * {@link #pathDadosArquivo}.
	 */
	private void gravaDados() {
		gerenciadorArquvios.gravarCapitulosEmArrayList(capitulos);
	}
	
	private void gerarLatex() {
		gerenciadorExportacao.exibirLatexEmPopup(gerenciadorExportacao.geraCodigoLatex(this.capitulos));
	}
	
	private void checaInputVazio() throws CaixaTextoVaziaException {
		if (this.inputPopupJTextField.getText().length() == 0 && this.inputTextoPopupJTextArea.getText().length() == 0){
			throw new CaixaTextoVaziaException("A caixa de texto está vazia!");
		}
	}
	
	/**
	 * Gerencia os eventos de clique de mouse.
	 * @param evento Evento de clique. O evento disparado serve para que a função "decida" qual ação tomar.
	 */
	public void mouseClicked(MouseEvent evento) {
		if (evento.getSource() == this.labelPesquisa) {
			this.textoInputPesquisa = this.inputPesquisa.getText();
			this.constroiNavegarTopicosJTree();
		}
		else if (evento.getSource() == this.labelButtonGerarLatex) {
			this.gerarLatex();
		}
		else if (evento.getSource() == this.buttonAdicionar) {
			this.opcoesAdicionarJPopupMenu.show(this.getPainel(), 360, 130);
		}
		else if (evento.getSource() == this.buttonEditar) {
			this.editarComponenteSelecionado();
		}
		else if (evento.getSource() == this.buttonExcluir) {
			this.excluirComponenteSelecionado();
		}
		else if (evento.getSource() == this.cancelarPopupButton) {
			this.popupJDialog.dispose();
			this.inputPopupJTextField.setText("");
			this.inputTextoPopupJTextArea.setText("");
			this.adicionarComponenteTipo = null;
		}
		else if (evento.getSource() == this.confirmarPopupButton) {
			this.popupJDialog.dispose();
			
			if (this.operacao == 0) {
				try {
					this.checaInputVazio();
				}catch(CaixaTextoVaziaException e) {
					JOptionPane.showMessageDialog(inputPopupJTextField, e.getMessage(), "CaixaTextoVaziaException", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				if (this.adicionarComponenteTipo.equals("Capítulo"))
					this.capitulos.add(new Capitulo(this.inputPopupJTextField.getText()));
				else if (this.adicionarComponenteTipo.equals("Tópico")) {
					if (this.componenteSelecionado instanceof Capitulo)
						((Capitulo)this.componenteSelecionado).addTopico(new Topico(this.inputPopupJTextField.getText(), 0));
					else
						((Topico)this.componenteSelecionado).addComponente(new Topico(this.inputPopupJTextField.getText(), 1));
				}
				else if (this.adicionarComponenteTipo.equals("Título"))
					((Topico)this.componenteSelecionado).addComponente(new Titulo(this.inputPopupJTextField.getText()));
				else if (this.adicionarComponenteTipo.equals("Texto")) 
					((Topico)this.componenteSelecionado).addComponente(new Texto(this.inputTextoPopupJTextArea.getText()));
				else if (this.adicionarComponenteTipo.equals("Código")) 
					((Topico)this.componenteSelecionado).addComponente(new Codigo(this.inputTextoPopupJTextArea.getText()));
			}
			else if (this.operacao == 1) {
				if (this.itemComponenteSelecionado instanceof Capitulo)
					((Capitulo)this.itemComponenteSelecionado).setNome(this.inputPopupJTextField.getText());
				else if (this.itemComponenteSelecionado instanceof Topico)
					((Topico)this.itemComponenteSelecionado).setNome(this.inputPopupJTextField.getText());
				else if (this.itemComponenteSelecionado instanceof Titulo)
					((Titulo)this.itemComponenteSelecionado).setConteudo(this.inputPopupJTextField.getText());
				else if (this.itemComponenteSelecionado instanceof Texto)
					((Texto)this.itemComponenteSelecionado).setConteudo(this.inputTextoPopupJTextArea.getText());
				else if (this.itemComponenteSelecionado instanceof Codigo)
					((Codigo)this.itemComponenteSelecionado).setConteudo(this.inputTextoPopupJTextArea.getText());
			}
			else if (this.operacao == 2) {
				if (this.itemComponenteSelecionado instanceof Capitulo) {
					this.capitulos.remove(this.itemComponenteSelecionado);
					this.atualizaComponenteSelecionado(null);
				}
				else if (this.itemComponenteSelecionado instanceof Topico) {
					int idxAux = this.componentesJTree.indexOf(this.itemComponenteSelecionado);
					idxAux = this.nodesComponentesJTree.indexOf(this.nodesComponentesJTree.get(idxAux).getParent());
					if (this.componentesJTree.get(idxAux) instanceof Capitulo)
						((Capitulo)this.componentesJTree.get(idxAux)).removeTopico((Topico)this.itemComponenteSelecionado);
					else
						((Topico)this.componentesJTree.get(idxAux)).removeComponente(this.itemComponenteSelecionado);
					this.atualizaComponenteSelecionado(this.componentesJTree.get(idxAux));
				}
				else {
					((Topico)this.componenteSelecionado).removeComponente(this.itemComponenteSelecionado);
				}
				this.atualizaItemComponenteSelecionado(this.componenteSelecionado);
			}
			this.inputPopupJTextField.setText("");
			this.inputTextoPopupJTextArea.setText("");
			this.constroiNavegarTopicosJTree();
			this.constroiPreviewPdf();
			this.constroiOpcoesAdicionarJPopupMenu();
		}
	}
	/**
	 * Utiliza o evento disparado para chamar a função {@link #adicionarComponente(String)}
	 * de acordo com o tipo de componente a ser adicionado.
	 * @param evento O evento ocorrido.
	 * @see #adicionarComponente(String)
	 */
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource() == this.capituloMenuItem) 
			this.adicionarComponente("Capítulo");
		if (evento.getSource() == this.topicoMenuItem) 
			this.adicionarComponente("Tópico");
		if (evento.getSource() == this.tituloMenuItem)
			this.adicionarComponente("Título");
		if (evento.getSource() == this.textoMenuItem)
			this.adicionarComponente("Texto");
		if (evento.getSource() == this.codigoMenuItem)
			this.adicionarComponente("Código");
	}
	/**
	 * Método da interface TreeSelectionListener
	 * Dado um evento do tipo TreeSelectionEvent o presente método altera o valor do {@link #componenteSelecionado}
	 * e do {@link #itemComponenteSelecionado}, definindo ambos como null se a raiz da JTree for selecionada.
	 * @param evento evento recebido ao selecionar om objeto do JTree.
	 * @see #atualizaComponenteSelecionado(Componente)
	 * @see #atualizaItemComponenteSelecionado(Componente) 
	 */
	public void valueChanged(TreeSelectionEvent evento) {
		if (evento.getPath().getPathCount() == 1) {
			if (evento.getPath().getLastPathComponent() == this.raizTopicosNode) {
				this.atualizaItemComponenteSelecionado(null);
				this.atualizaComponenteSelecionado(null);
			}
		}
		else {
			for (int i = 0; i < this.nodesComponentesJTree.size(); i++) {
				if (evento.getPath().getLastPathComponent() == this.nodesComponentesJTree.get(i)) {
					this.atualizaItemComponenteSelecionado(this.componentesJTree.get(i));
					this.atualizaComponenteSelecionado(this.componentesJTree.get(i));
					return;
				}
			}
			for (int i = 0; i < this.nodesFolhaComponentesJTree.size(); i++) {
				if (evento.getPath().getLastPathComponent() == this.nodesFolhaComponentesJTree.get(i)) {
					int indexAux = this.nodesComponentesJTree.indexOf(this.nodesFolhaComponentesJTree.get(i).getParent());
					this.atualizaItemComponenteSelecionado(this.componentesFolhaJTree.get(i));
					this.atualizaComponenteSelecionado(this.componentesJTree.get(indexAux));
					return;
				}
			}
		}
	}
}
