package de.fuberlin.optimierung.commands;

import de.fuberlin.optimierung.*;

/*
 * Syntax:

  <result> = icmp <cond> <ty> <op1>, <op2>   ; yields {i1} or {<N x i1>}:result

 */

public class LLVM_IcmpCommand extends LLVM_GenericCommand{
	
	public LLVM_IcmpCommand(String cmdLine, LLVM_GenericCommand predecessor, LLVM_Block block){
		super(predecessor, block, cmdLine);
		
		StringBuilder cmd = new StringBuilder(cmdLine);
		parseEraseComment(cmd);
		String result = parseReadResult(cmd);
		parseReadValue(cmd); // Operation löschen
		target = new LLVM_Parameter(result, "i1");
		
		String cond = parseReadValue(cmd);
		
		// cond festlegen
		if (cond.startsWith("eq")){
			setOperation(LLVM_Operation.ICMP_EQ);
		}else if (cond.startsWith("ne")){
			setOperation(LLVM_Operation.ICMP_NE);
		}else if (cond.startsWith("ugt")){
			setOperation(LLVM_Operation.ICMP_UGT);
		}else if (cond.startsWith("uge")){
			setOperation(LLVM_Operation.ICMP_UGE);
		}else if (cond.startsWith("ult")){
			setOperation(LLVM_Operation.ICMP_ULT);
		}else if (cond.startsWith("ule")){
			setOperation(LLVM_Operation.ICMP_ULE);
		}else if (cond.startsWith("sgt")){
			setOperation(LLVM_Operation.ICMP_SGT);
		}else if (cond.startsWith("sge")){
			setOperation(LLVM_Operation.ICMP_SGE);
		}else if (cond.startsWith("slt")){
			setOperation(LLVM_Operation.ICMP_SLT);
		}else if (cond.startsWith("sle")){
			setOperation(LLVM_Operation.ICMP_SLE);
		}
		
		String ty = parseReadType(cmd);
		String op1 = parseReadValue(cmd);
		parseEraseString(cmd, ",");
		String op2 = parseReadValue(cmd);
		
		operands.add(new LLVM_Parameter(op1, ty));
		operands.add(new LLVM_Parameter(op2, ty));
		
		if (LLVM_Optimization.DEBUG) System.out.println("Operation generiert: " +  this.toString());
	}
	
	public String toString() {
		String cmd_output = target.getName()+" = ";
		cmd_output += "icmp ";
		
		switch(operation){
			case ICMP_EQ :
				cmd_output +="eq ";
				break;
			case ICMP_NE :
				cmd_output +="ne ";
				break;
			case ICMP_UGT :
				cmd_output +="ugt ";
				break;
			case ICMP_UGE :
				cmd_output +="uge ";
				break;
			case ICMP_ULT :
				cmd_output +="ult ";
				break;
			case ICMP_ULE :
				cmd_output +="ule ";
				break;
			case ICMP_SGT :
				cmd_output +="sgt ";
				break;
			case ICMP_SGE :
				cmd_output +="sge ";
				break;
			case ICMP_SLT :
				cmd_output +="slt ";
				break;
			case ICMP_SLE :
				cmd_output +="sle ";
				break;
			default:
				return "";
		}
		
		cmd_output += operands.get(0).getTypeString()+" ";
		cmd_output += operands.get(0).getName()+", ";
		cmd_output += operands.get(1).getName();
		
		cmd_output += " " + getComment();
		
		return cmd_output;
	}
}
