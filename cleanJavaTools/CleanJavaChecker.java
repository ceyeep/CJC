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
	
	/** Main checker constructor. */
	public CleanJavaChecker(String[] args)
	{
		checker(args);
	}
	
	/** Get error string used for testing purposes. */
	public String getErrors()
	{
		return errorString;
	}
	
	/** Process compiler errors. */
	protected void processErrors(Collection errors, CompilationUnit unit) {
		System.err.println("Errors:");
		for(Iterator<Problem> iter2 = errors.iterator(); iter2.hasNext(); ) {
			Problem problem = iter2.next();
			System.err.println(problem.toString());
			errorString += problem.toSimpleString();
			if(iter2.hasNext())
				errorString += "\n";
		}
		
    }
	/** Process input file. Creates a new compilation unit. */
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
	
	//Properties configuration from JastAddJ7
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
	
	//Version printing extension from Frontend
	
	public void printVersion() {
      System.out.println(name() + " Version " + version() + " Build " + build());
    }
	
	public void printLongVersion() {
      System.out.println(longName() + " " + url() + " Version " + version() + " Build " + build());
    }
	
	public void printUsage() {
      printLongVersion();
      System.out.println(
          "\nUsage: java " + name() + " <options> <source files>\n" +
          "  -verbose                  Output messages about what the compiler is doing\n" +
          "  -classpath <path>         Specify where to find user class files\n" +
          "  -sourcepath <path>        Specify where to find input source files\n" + 
          "  -bootclasspath <path>     Override location of bootstrap class files\n" + 
          "  -extdirs <dirs>           Override location of installed extensions\n" +
          "  -d <directory>            Specify where to place generated class files\n" +
          "  -help                     Print a synopsis of standard options\n" +
          "  -version                  Print version information\n"
          );
    }

	public String name() { return getString("cjc.Name"); }
	public String longName() { return getString("cjc.LongName"); }
	public String version() { return getString("cjc.Version"); }
	public String build() { return getString("cjc.Build"); }
	public String url() { return getString("cjc.URL"); }
}
