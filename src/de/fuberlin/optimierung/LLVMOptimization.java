package de.fuberlin.optimierung;

import java.io.*;
import java.util.LinkedList;

class LLVMOptimization implements ILLVMOptimization {
	
	private String code = "";
	
	private LinkedList<LLVM_Function> functions;
	
	
	public LLVMOptimization(){
		functions = new LinkedList<LLVM_Function>();
	}
	
	private void parseCode() {
		
		// Splitte in Funktionen
		String[] functions = this.code.split("define");
		
		for (int i = 1; i < functions.length; i++) {
			this.functions.add(new LLVM_Function(functions[i]));
		}
	}

	private String optimizeCode() {
		// Code steht als String in this.code
		// Starte Optimierung
		this.parseCode();
		
		// TODO Erstelle Flussbaum
		
		//this.createRegisterMaps();
		//this.eliminateDeadRegisters();
		
		String outputLLVM = "";
		LLVM_Function tmp;
		
		for (int i = 0; i < functions.size(); i++) {
			// aktuelle Funktion fuer Optimierung
			tmp = functions.get(i);
			
			// Optimierungsfunktionen
			tmp.createRegisterMaps();
			while(tmp.eliminateDeadRegisters()){
				tmp.createRegisterMaps();
			}
			
			// Optimierte Ausgabe
			outputLLVM += tmp.toString();
		}
		
		return outputLLVM;
	}

	private void readCodeFromFile(String fileName){
		
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
			String line = "";
			while((line = fileReader.readLine()) != null) {
				this.code = this.code + line;
				this.code = this.code + "\n";
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String optimizeCodeFromString(String code) {

		this.code = code;
		return this.optimizeCode();

	}

	public String optimizeCodeFromFile(String fileName) {

		this.readCodeFromFile(fileName);
		return this.optimizeCode();

	}
	
	public static void main(String args[]) {

		ILLVMOptimization optimization = new LLVMOptimization();        
		String optimizedCode = optimization.optimizeCodeFromFile("input/llvm_dead_registers2");
		System.out.println(optimizedCode);
	}

}