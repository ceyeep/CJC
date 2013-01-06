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
/*
 * This file contains annotation examples of supported CleanJava features.
 * 
 * Notes:
 *  This program was tested with zero error compilation using a CleanJava Checker.
 *  Some of the methods don't have a complete implementation; this
 *  program is focused on the documentation of CleanJava annotations.
 */
package test;

/** This file contains annotation examples of supported CleanJava features. */
public class TestFile{
	@SuppressWarnings("unused")
	private static int zero;
	@SuppressWarnings("unused")
	private static boolean flag;
	@SuppressWarnings("unused")
	private int x = 0, y = 0, z = 0, sum = 0, max = 0;
	
	//Class body declarations (entry CJ annotation point)
	
	//Static initializer
	//@ [ zero := 0 ]
	static{
		zero = 0;
	}
	
	//Instance initializer
	//@ [ x := 3 ]
	{
		x = 5;
	}
	
	//Constructor declaration
	//@ [ x := 3]
	public TestFile (){
		x = 3;
	}

	//Method declaration	 
	//@ [ flag := true ]
	public static void main(String[] args) {
		flag = true;
	}
	
	
	//CleanJava annotations
	
	//Single-line annotation
	//@ [ x := 3]
	public void singleLineAnnotation(){}
	
	//Multi-line annotation
	/*@ 
	   [ x == 0 -> y := 0
	    \else x > 0 -> y := 1 
	    \else x < 0 -> y := -1 ]
	 @*/
	public void multiLineAnnotation(){}
	
	//Comment inside of an annotation
	/*@ 
	   // This is a traditional single-line Java comment
	   [ x == 0 -> y := 0
	    \else x > 0 -> y := 1 
	    \else x < 0 -> y := -1 ]
	 @*/
	public void commentInsideAnnotation(){}
	
		
	//Java expressions
	
	//@ [ sum := x + y ]
	public void sum(){
		sum = x + y;
	}
	
	//@ [ max := Math.max(x, y) ]
	public void max(){
		max = Math.max(x, y);
	}
	
	
	//CleanJava operators and expressions
	
	//anything literal
	//@ [x := \anything ]
	public void anything(){
		x = (int) (Math.random()*10);
	}
	
	//informal notation
	//@ [x := (* maximum of x and y *) ]
	public void informalNotation01(){
		max = Math.max(x, y);
	}
	
	//@ [x := (* maximum of x and y *) + 10 ]
	public void informalNotation02(){
		max = Math.max(x, y) + 10;
	}
	
	//(MISSING TEST) \result - type checking
	
		
	//Iterate operators
		//(MISSING TEST) =>
	
	//Intended functions
	
	//Simple concurrent assignment (single element)
	//@ [ x := 3 ]
	public void concurrentAssignment01(){
		x = 3;
	}
	
	//Simple concurrent assignment (multiple elements)
	//@ [ x, y, z := 1, 2, 3 ]
	public void concurrentAssignment02(){
		x = 1;
		y = 2;
		z = 3;
	}
	
	
	//(MISSING TEST)Object equality := vs &=
	
	//Conditional concurrent assignment
	/*@ 
	   [ x > 0 -> y := 1
	    \else x < 0 -> y := -1 
	    \else \I ] //Do nothing. Original program state.
	 @*/
	public void conditionalConcurrentAssignment(){}
	
	
	//(MISSING TEST)Splitting definitions
	//(MISSING TEST)Combining definitions
		
}
