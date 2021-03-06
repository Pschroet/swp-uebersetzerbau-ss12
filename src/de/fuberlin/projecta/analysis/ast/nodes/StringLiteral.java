package de.fuberlin.projecta.analysis.ast.nodes;


public class StringLiteral extends Statement {

	private String value;

	public StringLiteral(String value) {
		this.value = value;
	}

	@Override
	public boolean checkSemantics() {
		return true;
	}
	
	@Override
	public String genCode() {
		return "c\"" + this.value  + "\\00" + "\"";
	}

	public int getLength(){
		return value.length();
	}
	
	public String getValue() {
		return this.value;
	}

	@Override
	public boolean checkTypes() {
		// TODO Auto-generated method stub
		return false;
	}
}
