package de.fuberlin.optimierung.commands;

import java.util.LinkedList;
import de.fuberlin.optimierung.ILLVM_Block;
import de.fuberlin.optimierung.ILLVM_Command;
import de.fuberlin.optimierung.LLVM_Operation;
import de.fuberlin.optimierung.LLVM_Parameter;

/*
* Syntax:
  
  store [volatile] <ty> <value>, <ty>* <pointer>[, align <alignment>][, !nontemporal !<index>]        ; yields {void}
  store atomic [volatile] <ty> <value>, <ty>* <pointer> [singlethread] <ordering>, align <alignment>  ; yields {void}
*/

public class LLVM_StoreCommand extends LLVM_GenericCommand{
	
	public LLVM_StoreCommand(String[] cmd, LLVM_Operation operation, ILLVM_Command predecessor, ILLVM_Block block, String comment){
		super(operation, predecessor, block, comment);
		// init operands
		operands = new LinkedList<LLVM_Parameter>();
		
		// speichert den Typ und den Wert den store speichert in ersten Operanden
		// speichert den Typ und den Namen der Zieladresse in zweiten Operanden
		operands.add(new LLVM_Parameter(cmd[2], cmd[1]));
		operands.add(new LLVM_Parameter(cmd[4], cmd[3]));
		
		
		System.out.println("Operands generiert: ");
		System.out.println(this.toString());
	}
	
	public String toString() {
		String cmd_out = "store ";
		
		cmd_out += operands.get(0).getTypeString()+" ";
		cmd_out += operands.get(0).getName()+" ";
		
		cmd_out += operands.get(1).getTypeString()+" ";
		cmd_out += operands.get(1).getName();
		
		cmd_out += getComment();
		
		return cmd_out;
	}
}