package de.fuberlin.optimierung.commands;

import java.util.LinkedList;
import de.fuberlin.optimierung.ILLVMBlock;
import de.fuberlin.optimierung.ILLVMCommand;
import de.fuberlin.optimierung.LLVMOperation;
import de.fuberlin.optimierung.LLVMParameter;

/*
 * Syntax: sample "add" but also for "sub", "mul", "div"

  <result> = add <ty> <op1>, <op2>          ; yields {ty}:result
  <result> = add nuw <ty> <op1>, <op2>      ; yields {ty}:result
  <result> = add nsw <ty> <op1>, <op2>      ; yields {ty}:result
  <result> = add nuw nsw <ty> <op1>, <op2>  ; yields {ty}:result
 */

public class LLVM_ArithmeticCommand extends LLVM_GenericCommand{
	
	private boolean has_nuw = false;
	private boolean has_nsw = false;
	
	public LLVM_ArithmeticCommand(String[] cmd, LLVMOperation operation, ILLVMCommand predecessor, ILLVMBlock block, String comment){
		super(operation, predecessor, block, comment);
		// Init operands
		operands = new LinkedList<LLVMParameter>();
		
		int i = -1;
		for (int j = 0; j < cmd.length; j++){
			if (cmd[j].contains(",")){
				i = j;
			}		
		}
		
		switch(i){
			case 5 :
				if(cmd[3].equals("nuw"))
					has_nuw = true;
				else
					has_nsw = true;
				target = new LLVMParameter(cmd[0], cmd[4]);
				operands.add(new LLVMParameter(cmd[5], cmd[4]));
				operands.add(new LLVMParameter(cmd[6], cmd[4]));
				break;
			case 6 :
				has_nuw = true;
				has_nsw = true;
				target = new LLVMParameter(cmd[0], cmd[5]);
				operands.add(new LLVMParameter(cmd[6], cmd[5]));
				operands.add(new LLVMParameter(cmd[7], cmd[5]));
				break;
			default:
				target = new LLVMParameter(cmd[0], cmd[3]);
				operands.add(new LLVMParameter(cmd[4], cmd[3]));
				operands.add(new LLVMParameter(cmd[5], cmd[3]));
				break;
		}
		
		System.out.println("Operation generiert: ");
		System.out.println(this.toString());
	}
	
	public String toString() {
		String cmd_output = target.getName()+" = ";
		
		switch(operation){
			case ADD :
				cmd_output +="add ";
				break;
			case SUB :
				cmd_output +="sub ";
				break;
			case MUL :
				cmd_output +="mul ";
				break;
			case DIV :
				cmd_output +="div ";
				break;
			case UREM :
				cmd_output +="urem ";
				break;
			case SREM :
				cmd_output +="srem ";
				break;
			default:
				return "";
		}
		
		cmd_output += has_nuw==true?"unw ":"";
		cmd_output += has_nsw==true?"nsw ":"";
		cmd_output += operands.get(0).getTypeString()+" ";
		cmd_output += operands.get(0).getName()+", ";
		cmd_output += operands.get(1).getName();
		
		cmd_output += getComment();
		
		return cmd_output;
	}
}