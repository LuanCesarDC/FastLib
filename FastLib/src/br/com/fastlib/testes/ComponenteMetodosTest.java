package br.com.fastlib.testes;

import static org.junit.Assert.assertEquals;

//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.Test;
import org.junit.*;

import br.com.fastlib.models.Titulo;

public class ComponenteMetodosTest {
	
	@Test
	public void testaRemoveCharInicioFim() {
		Titulo titulo = new Titulo("Um titulo de exemplo");
		assertEquals(titulo.removeCharInicioFim("    texto aqui apenas exemplo           ", ' '),
												"texto aqui apenas exemplo");
		
		assertEquals(titulo.removeCharInicioFim("texto aqui apenas exemplo           ", ' '),
				"texto aqui apenas exemplo");
		
		assertEquals(titulo.removeCharInicioFim("    texto aqui apenas exemplo", ' '),
				"texto aqui apenas exemplo");
		
		assertEquals(titulo.removeCharInicioFim(" _ texto aqui apenas exemplo _ ", ' '),
				"_ texto aqui apenas exemplo _");
		
		assertEquals(titulo.removeCharInicioFim(" _ texto aqui apenas exemplo", ' '),
				"_ texto aqui apenas exemplo");
		
		assertEquals(titulo.removeCharInicioFim("texto aqui apenas exemplo _ ", ' '),
				"texto aqui apenas exemplo _");
		
		assertEquals(titulo.removeCharInicioFim("texto aqui apenas exemplo", ' '),
				"texto aqui apenas exemplo");
		
		assertEquals(titulo.removeCharInicioFim("    texto aqui apenas exemplo           ", 'z'),
				"    texto aqui apenas exemplo           ");
		
		assertEquals(titulo.removeCharInicioFim("zzzzztexto aqui apenas exemplozzzzzzzz", 'z'),
				"texto aqui apenas exemplo");
		
		assertEquals(titulo.removeCharInicioFim("ztexto aqui apenas exemplo", 'z'),
				"texto aqui apenas exemplo");
		
		assertEquals(titulo.removeCharInicioFim("", 'z'),
				"");
		
		assertEquals(titulo.removeCharInicioFim("z", 'z'),
				"");
		
		assertEquals(titulo.removeCharInicioFim(" ", ' '),
				"");
	}
	
	@Test
	public void testaProcessaString() {
		Titulo titulo = new Titulo("Um titulo de exemplo");
		
		assertEquals(titulo.processaString("      \n\n    \n texto completo aqui\n tem ate enter       \n\n\n\n\n"),
					"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString("\n\n\n\ntexto completo aqui\n tem ate enter       "),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString("\n\n\n\n\n\ntexto completo aqui\n tem ate enter\n\n\n\n\n\n"),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString("            texto completo aqui\n tem ate enter            "),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString("\n \n \n \n texto completo aqui\n tem ate enter \n \n \n \n"),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString("\n \n \n \n texto completo aqui\n tem ate enter\n \n \n \n "),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString(" texto completo aqui\n tem ate enter"),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString("texto completo aqui\n tem ate enter "),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString("\ntexto completo aqui\n tem ate enter"),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString("texto completo aqui\n tem ate enter\n"),
				"texto completo aqui\n tem ate enter");
		
		assertEquals(titulo.processaString(""),
				"");
		
		assertEquals(titulo.processaString("\n"),
				"");
		
		assertEquals(titulo.processaString("\n\n\n\n"),
				"");
		
		assertEquals(titulo.processaString(" "),
				"");
		
		assertEquals(titulo.processaString("            "),
				"");
		
		assertEquals(titulo.processaString("texto completo aqui\n tem ate enter"),
				"texto completo aqui\n tem ate enter");
	}
	
	@Test
	public void testaConverteStringLatex() {
		Titulo titulo = new Titulo("Um titulo de exemplo");
		
		assertEquals(titulo.converteStringLatex("a_b==c"), "a\\_b==c");
		assertEquals(titulo.converteStringLatex("$a_b==c$"), "\\$a\\_b==c\\$");
		assertEquals(titulo.converteStringLatex("$a_b^d==c$"), "\\$a\\_b\\^{}d==c\\$");
		assertEquals(titulo.converteStringLatex("{apenas isso}"), "\\{apenas isso\\}");
		assertEquals(titulo.converteStringLatex("texto normal"), "texto normal");
		assertEquals(titulo.converteStringLatex(""), "");
		
	}
	
}