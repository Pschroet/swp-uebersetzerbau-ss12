package de.fuberlin.projecta;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import de.fuberlin.commons.lexer.TokenType;
import de.fuberlin.projecta.parser.NonTerminal;
import de.fuberlin.projecta.parser.ParseTable;

public class ParseTableTest {
	
	private ParseTable table = new ParseTable();

	@Test
	public void testSetEntry() {
		table.setEntry(NonTerminal.program, TokenType.DEF, "program ::= funcs");
		String entry = table.getEntry(NonTerminal.program, TokenType.DEF);
		assertEquals(entry, "program ::= funcs");
	}


	@Test(expected = IllegalStateException.class)
	public void testSetEntryTwice() {
		table.setEntry(NonTerminal.program, TokenType.DEF, "program ::= funcs");
		table.setEntry(NonTerminal.program, TokenType.DEF, "program ::= funcs");
	}

	@Test
	public void testGetInvalidEntry() {
		String entry = table.getEntry(NonTerminal.program, TokenType.DEF);
		assertEquals(entry, null);
	}

}
