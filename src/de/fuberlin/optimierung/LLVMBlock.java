package de.fuberlin.optimierung;

import java.util.LinkedList;

import de.fuberlin.optimierung.commands.*;

class LLVMBlock implements ILLVMBlock {
	
	// Erster und letzter Befehl des Blockes
	private ILLVMCommand firstCommand = null;
	private ILLVMCommand lastCommand = null;

	// Ursprüngliches Label des Blockes
	private String label = "";

	// Vorgaenger- und Nachfolgerbloecke
	// Hieraus entsteht der Flussgraph zwischen den Bloecken
	private LinkedList<ILLVMBlock> children;
	private LinkedList<ILLVMBlock> parents;
	
	// Kompletter Code des Blocks als String
	private String blockCode;

	public LLVMBlock(String blockCode) {
	
		children = new LinkedList<ILLVMBlock>();
		parents = new LinkedList<ILLVMBlock>();
		
		this.blockCode = blockCode;
		System.out.println(blockCode + "\n*****************\n");
		
		this.createCommands();

	}
	
	public boolean isEmpty() {
		return (this.firstCommand==null);
	}

	public void optimizeBlock() {

	}

	public void deleteBlock() {

	}

	private void createDAG() {

	}

	private void createCommands() {
		String commandsArray[] = this.blockCode.split("\n");
		this.firstCommand = mapCommands(commandsArray[1], null);
		
		ILLVMCommand predecessor = firstCommand;
		for(int i=2; i<commandsArray.length; i++) {
			ILLVMCommand c = mapCommands(commandsArray[i], predecessor);
			if(firstCommand == null){
				firstCommand = c;
				predecessor = c;
			}else{
				predecessor = c;
			}
		}
		this.lastCommand = predecessor;
	}
	
	// Ermittelt Operation und erzeugt Command mit passender Klasse
	//TODO elegante Methode finden, switch funktioniert auf Strings nicht!
	private LLVM_GenericCommand mapCommands(String cmdLine, ILLVMCommand predecessor){
		
		// Kommentar Handling
		String[] com = cmdLine.trim().split(";");
		String comment = "";
		
		if (com.length > 1){
			for (int i = 1; i < com.length; i++){
				comment += com[i]; 
			}
		}
		
		if (com.length == 0) return null;
		
		// Kommando Handling
		String[] cmd = com[0].trim().split("[ \t]");
		
		if (cmd.length > 0){
			if (cmd[0].compareTo("br") == 0){
				if (cmd[1].compareTo("label") == 0){
					return new LLVM_BranchCommand(cmd, LLVMOperation.BR, predecessor, this, comment);
				}else{
					return new LLVM_BranchCommand(cmd, LLVMOperation.BR_CON, predecessor, this, comment);
				}
			} else if (cmd[0].compareTo("ret") == 0){
				if (cmd[1].compareTo("void") == 0){
					return new LLVM_ReturnCommand(cmd, LLVMOperation.RET, predecessor, this, comment);
				}else{
					return new LLVM_ReturnCommand(cmd, LLVMOperation.RET_CODE, predecessor, this, comment);
				}
			}
			if (cmd.length > 3 && cmd[1].equals("=")){
				
				if (cmd[2].compareTo("add") == 0){
					return new LLVM_ArithmeticCommand(cmd, LLVMOperation.ADD, predecessor, this, comment);
				}else if(cmd[2].compareTo("sub") == 0){
					return new LLVM_ArithmeticCommand(cmd, LLVMOperation.SUB, predecessor, this, comment);
				}else if(cmd[2].compareTo("mul") == 0){
					return new LLVM_ArithmeticCommand(cmd, LLVMOperation.MUL, predecessor, this, comment);
				}else if(cmd[2].compareTo("div") == 0){
					return new LLVM_ArithmeticCommand(cmd, LLVMOperation.DIV, predecessor, this, comment);
				}else if(cmd[2].compareTo("urem") == 0){
					return new LLVM_ArithmeticCommand(cmd, LLVMOperation.UREM, predecessor, this, comment);
				}else if(cmd[2].compareTo("srem") == 0){
					return new LLVM_ArithmeticCommand(cmd, LLVMOperation.SREM, predecessor, this, comment);
				}else if (cmd[2].compareTo("alloca") == 0){
					return new LLVM_Alloca(cmd, LLVMOperation.ALLOCA, predecessor, this, comment);
				}else if (cmd[2].compareTo("and") == 0){
					return new LLVM_Alloca(cmd, LLVMOperation.AND, predecessor, this, comment);
				}else if (cmd[2].compareTo("or") == 0){
					return new LLVM_Alloca(cmd, LLVMOperation.OR, predecessor, this, comment);
				}else if (cmd[2].compareTo("xor") == 0){
					return new LLVM_Alloca(cmd, LLVMOperation.XOR, predecessor, this, comment);
				}else if (cmd[2].compareTo("icmp") == 0){
					if (cmd[3].compareTo("eq") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_EQ, predecessor, this, comment);
					}else if (cmd[3].compareTo("ne") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_NE, predecessor, this, comment);
					}else if (cmd[3].compareTo("ugt") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_UGT, predecessor, this, comment);
					}else if (cmd[3].compareTo("uge") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_UGE, predecessor, this, comment);
					}else if (cmd[3].compareTo("ult") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_ULT, predecessor, this, comment);
					}else if (cmd[3].compareTo("ule") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_ULE, predecessor, this, comment);
					}else if (cmd[3].compareTo("sgt") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_SGT, predecessor, this, comment);
					}else if (cmd[3].compareTo("sge") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_SGE, predecessor, this, comment);
					}else if (cmd[3].compareTo("slt") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_SLT, predecessor, this, comment);
					}else if (cmd[3].compareTo("sle") == 0){
						return new LLVM_IcmpCommand(cmd, LLVMOperation.ICMP_SLE, predecessor, this, comment);
					}
				}
			}
		}
		return null;
	}

	public void setFirstCommand(ILLVMCommand first) {
		this.firstCommand = first;
	}

	public void setLastCommand(ILLVMCommand last) {
		this.lastCommand = last;
	}
	
	public ILLVMCommand getFirstCommand() {
		return firstCommand;
	}

	public ILLVMCommand getLastCommand() {
		return lastCommand;
	}
	
	public String toString() {
		
		String code = "";
		
		if(!label.equals("")){
			code = label+":\n";
		}
		
		ILLVMCommand tmp = firstCommand;
		while(tmp != null){
			code += "\t"+tmp.toString();
			tmp = tmp.getSuccessor();
		}
		//code += "\n";
		
		return code;
	}
}