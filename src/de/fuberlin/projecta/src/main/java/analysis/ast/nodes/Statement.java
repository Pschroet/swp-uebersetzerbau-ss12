package analysis.ast.nodes;

import analysis.SymbolTableStack;


public abstract class Statement extends AbstractSyntaxTree {

	public void buildSymbolTable(SymbolTableStack tables) {

	}
	
	@Override
	public boolean checkSemantics() {
		return true;
	}
}