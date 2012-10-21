/*
 * The JastAdd Extensible Java Compiler (http://jastadd.org) is covered
 * by the modified BSD License. You should have received a copy of the
 * modified BSD license with this compiler.
 * 
 * Copyright (c) 2005-2008, Torbjorn Ekman
 * 
 * modified by Cesar Yeep, 2012
 * All rights reserved.
 */
package cleanJavaTools;

import AST.*;
import java.util.*;

public class CleanJavaChecker extends Frontend {

	//TODO: consider changing string to a collection of errors
	private String errorString = "";
	
	public static void main(String args[]) {
		CleanJavaChecker checker = new CleanJavaChecker(args);
		checker.getErrors();
	}
	
	public CleanJavaChecker(String[] args)
	{
		checker(args);
	}
	
	public String getErrors()
	{
		return errorString;
	}
	
	protected void processErrors(Collection errors, CompilationUnit unit) {
		//System.out.println("Errors:");
		for(Iterator<Problem> iter2 = errors.iterator(); iter2.hasNext(); ) {
			String error = iter2.next().toSimpleString();
			errorString += error;
			if(iter2.hasNext())
				errorString += "\n";
			//System.out.println(error);
		}
		System.out.println(errorString);
    }

	public void checker(String[] args) {
		process(
			args,
			new BytecodeParser(),
			new JavaParser() {
				parser.JavaParser parser = new parser.JavaParser();

				public CompilationUnit parse(java.io.InputStream is, String fileName) throws java.io.IOException, beaver.Parser.Exception {
					return parser.parse(is, fileName);
				}
			}
		);
  }
}
