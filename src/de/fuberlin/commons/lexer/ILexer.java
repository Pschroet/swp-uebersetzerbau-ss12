package de.fuberlin.commons.lexer;

public interface ILexer {

	/**
	 * Interface for accessing the token stream
	 * 
	 * @return The next Token
	 * @throws RuntimeException
	 */
	IToken getNextToken();

}
