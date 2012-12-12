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
package tests;

import java.io.*;
import junit.framework.TestCase;
import cleanJavaTools.JavaChecker;
import AST.BytecodeParser;
import AST.CompilationUnit;
import AST.JavaParser;
import AST.Problem;
import beaver.Parser;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ArrayList;
import java.lang.StringBuffer;

/** This class contains a set of test cases for the CleanJava parser.
 *  @author ceyeep
 */
public class ParserTest extends TestCase {
 
	// Test using this keyword
	public void testParser01() {
		assertParseOk("//@[this.x := 3]");
	}
	
	// Test simple assignment
	public void testParser02() {
		assertParseOk("//@[ x := 5 ]");
	}
	
	// Test object field assignment
	public void testParser03() {
		assertParseOk("//@[object.x := 3]");
	}
	
	// Test using simple addition expression
	public void testParser04() {
		assertParseOk("//@[ x :=  + 4 ]");
	}
	
	// Test using simple addition expression
	public void testParser05() {
		assertParseOk("//@[ x := x + 4 ]");
	}
	
	// Test multiple assignments
	public void testParser06() {
		assertParseOk("//@[ zero, z  := zero, zero +1 ]");
	}
	
	// Test constructor declaration
	public void testParser07() {
		assertParseOk("//@[ x := 3 ]","public Test(){ x = 3; }");
	}
	
	// Test instance initializer
	public void testParser08() {
		assertParseOk("//@[ x := 3 ]","{ x = 3; }");
	}
	
	// Test static initializer
	public void testParser09() {
		assertParseOk("//@[ x := 3 ]","static { x = 3; }");
	}
	
	// Test instance initializer
	public void testParser10() {
		assertParseOk("//@[ x := 3 ]","{ x = 3; }");
	}
	
	// Test intended function with multiple lines
	public void testParser11() {
		assertParseOk("/*@ [ x, y := \n 3, 4 ] #*/","{ x = 3; }");
	}
		
	// Test simple assignment (no blank spaces)
	public void testParser12() {
		assertParseOk("//@[x:=5]");
	}
	
	
	// Test missing value in the right side of the assignment expression
	public void testParserFail01() {
		assertParseError("//@[x := ]");
	}
	
	// Test incorrect assingment symbol
	public void testParserFail02() {
		assertParseError("//@[x : 3]");
	}
	
	// Test unclosed square bracket
	public void testParserFail03() {
		assertParseError("//@[x := 3");
	}
	
	// Test right-side side-effects
	public void testParserFail04() {
		assertParseError("//@[x := x++]");
	}
	
	
	/** Check if there are no parsing errors for a given testcase. */
	protected static void assertParseOk(String intendedFunction) {
		try {
			parse(createStub(intendedFunction));
		}
		catch(Parser.Exception e) {
			System.out.println("Parser error");
			fail(e.getMessage());
		}
		catch (Throwable e) {
			System.out.println("Other error");
			fail(e.getMessage());
		}
	}
	
	/** Check if there are no parsing errors for a given testcase
	    (specify chunk of code under intended function). */
	protected static void assertParseOk(String intendedFunction, String code) {
		try {
			parse(createStub(intendedFunction, code));
		}
		catch(Parser.Exception e) {
			System.out.println("Parser error");
			fail(e.getMessage());
		}
		catch (Throwable e) {
			System.out.println("Other error");
			fail(e.getMessage());
		}
	}
		
	/** Checks that the parser returns an error for the given testcase. */
	protected static void assertParseError(String intendedFunction) {
		try {
			parse(createStub(intendedFunction));
		} 
		catch(Parser.Exception e) {
			return;
		}
		catch (Throwable e) {
			System.out.println("Other error");
			fail(e.getMessage());	
		}
		fail("Expected to find parse error in " + intendedFunction);
	}
	
	/** Checks that the parser returns an error for the given testcase
	    (specify chunk of code under intended function). */
	protected static void assertParseError(String intendedFunction, String code) {
		try {
			parse(createStub(intendedFunction, code));
		} 
		catch(Parser.Exception e) {
			return;
		}
		catch (Throwable e) {
			System.out.println("Other error");
			fail(e.getMessage());	
		}
		fail("Expected to find parse error in " + intendedFunction+"\n"+code);
	}
	
	
	/** Creates a method stub for the given testcase. */
	protected static String createStub(String testCase)
	{
		String stub = "public class Test{\n";
		stub+=testCase;
		stub+="\npublic void foo(){}\n}";
		//System.out.println(stub);
		return stub;
	}
	
	/** Creates a class stub for the given testcase (specific annotated coded). */
	protected static String createStub(String intendedFunction, String code)
	{
		String stub = "public class Test{\n";
		stub+=intendedFunction+"\n";
		stub+=code;
		stub+="\n\n}";
		//System.out.println(stub);
		return stub;
	}
	
	/** Creates a new Scanner and Parser and parses the given testcase. */
	protected static void parse(String s) throws Throwable, beaver.Parser.Exception{
		parser.JavaParser parser = new parser.JavaParser();
		Reader reader = new StringReader(s);

		scanner.JavaScanner scanner = new scanner.JavaScanner(new BufferedReader(reader));
		parser.parse(scanner);
		reader.close();
	}
	
}
