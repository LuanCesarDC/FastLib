package br.com.fastlib.gerenciadores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import br.com.fastlib.models.Capitulo;

/**
 *	A classe GerenciadorArquivos contém apenas métodos estáticos e controla a gravação e o carregamento
 *	das instâncias de classes usadas no decorrer do programa. 
 * 
 *  @author Dayllon Vinícius Xavier Lemos
 *  @author Luan César Dutra Carvalho
 */

public class GerenciadorArquivos {
	private String enderecoArquivo;
	
	public GerenciadorArquivos(String enderecoArquivo) {
		this.enderecoArquivo = enderecoArquivo;
	}
	
	/**
	 * Grava o próprio ArrayList onde se encontram os capítulos.
	 * 
	 * @param É o ArrayList a ser gravado.
	 * @param Nome do arquivo onde o ArrayList de Capítulos será gravado.
	 * @return True caso haja sucesso na gravação, e False caso não haja sucesso.
	 */
	public boolean gravarCapitulosEmArrayList(ArrayList<Capitulo> capitulos) {
		try {
			File arquivo = new File(this.enderecoArquivo);
			FileOutputStream fluxoGravacao = new FileOutputStream(arquivo);
			ObjectOutputStream filtroGravacao = new ObjectOutputStream(fluxoGravacao);
			filtroGravacao.writeObject(capitulos);
			filtroGravacao.flush();
			filtroGravacao.close();
			fluxoGravacao.flush();
			fluxoGravacao.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Carrega um ArrayList de capítulos que foi gravado em um determinado arquivo.
	 * 
	 * @param Nome do arquivo do qual será carregado o ArrayList
	 * @return O próprio ArrayList de capítulos
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Capitulo> carregaCapitulosEmArrayList(){
		ArrayList<Capitulo> capitulos = new ArrayList<Capitulo>();
		try {
			File arquivo = new File(this.enderecoArquivo);
			if (arquivo.exists()) {
				FileInputStream fluxoLeitura = new FileInputStream(arquivo);
				ObjectInputStream filtroLeitura = new ObjectInputStream(fluxoLeitura);
				capitulos = (ArrayList<Capitulo>) filtroLeitura.readObject();
				filtroLeitura.close();
				fluxoLeitura.close();
			}
		}
		catch (IOException e) {
			capitulos.clear();
		}
		catch (ClassNotFoundException e) {
			capitulos.clear();
		}
		return capitulos;
	}

}