/*******************************************************************************
 * Copyright (c) 2012 Cesar Yeep.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the BSD 3-Clause License
 * ("New BSD" or "BSD Simplified") which accompanies this distribution,
 * and is available at
 * http://opensource.org/licenses/BSD-3-Clause
 * 
 * Contributors:
 *     Cesar Yeep - initial API and implementation
 ******************************************************************************/
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
	
	
	protected ResourceBundle resources = null;
	protected String resourcename = "JastAddJ";
	protected String getString(String key) {
		if (resources == null) {
			try {
				resources = ResourceBundle.getBundle(resourcename);
			} catch (MissingResourceException e) {
				throw new Error("Could not open the resource " +
						resourcename);
			}
		}
		return resources.getString(key);
	}

	protected String name() { return getString("cjc.CleanJavaChecker"); }
	protected String version() { return getString("cjc.Version"); }
	protected String url() { return getString("cjc.URL"); }
}
