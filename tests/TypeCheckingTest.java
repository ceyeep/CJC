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

import static org.junit.Assert.*;
import org.junit.Test;

import testFramework.TestUtilities;

/** This class contains test cases for type checking. 
 *	@author ceyeep
 */
public class TypeCheckingTest {

    
	/** Test simple integer assignment */
	@Test
	public void testTypeChecking01() {
		String setUp = " int x; ";
		String testCase = " //@ [ x := 3] ";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test using keyword this */
	@Test
	public void testTypeChecking02() {
		String setUp = "int x;";
		String testCase = " //@ [ this.x := 3]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test using an undeclared variable */
	@Test
	public void testTypeChecking03() {
		String setUp = "int x;";
		String testCase = " //@ [ y := 3]";
		assertEquals("Semantic Error: no field named y is accessible",TestUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test using different types */
	@Test
	public void testTypeChecking04() {
		String setUp = "int x;";
		String testCase = " //@ [ x := \"hello\"]";
		assertEquals("Semantic Error: can not assign x of type int "
			+"a value of type java.lang.String",TestUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test multple assignment */
	@Test
	public void testTypeChecking05() {
		String setUp = "int x, y;";
		String testCase = " //@ [ x, y := 1,2]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test unbalanced concurrent assignment */
	@Test
	public void testTypeChecking06() {
		String setUp = "int x, y;";
		String testCase = "//@ [ x, y := 3]";
		assertEquals("Semantic Error: concurrent assignment is unbalanced",TestUtilities.runChecker(setUp,testCase));
	}
	
	/** Test informal description 1 */
	@Test
	public void testTypeChecking07() {
		String setUp = "boolean flag;";
		String testCase = "//@ [ flag := (* informal description *) ]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
	}
	
	/** Test informal description 2 (different type) */
	@Test
	public void testTypeChecking08() {
		String setUp = "int x;";
		String testCase = "//@ [ x := (* informal description *) ]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
	}
	
	/*
	// Test concurrent assignment variable duplicate
	public void testTypeChecking09() {
		String setUp = "int x;";
		String testCase = "//# [ x, x := 2, 3 ]";
		assertEquals("Semantic Error: there are duplicates in the left-hand side of the equation: x"
			,runChecker(setUp,testCase));
	}
	
	// Test triple concurrent assignment with duplicate
	public void testTypeChecking10() {
		String setUp = "int x;";
		String testCase = "//# [ x, x, x := 2, 3, 5 ]";
		assertEquals("Semantic Error: there are duplicates in the left-hand side of the equation: x"
			,runChecker(setUp,testCase));
	}
	*/
	
	/** Test "anything" literal */
	@Test
	public void testTypeChecking11() {
		String setUp = "int x;";
		String testCase = "//@ [ x := \\anything ]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
	}
	
	/** Test "anything" literal with an object */
	@Test
	public void testTypeChecking12() {
		String setUp = "String s = \"\";";
		String testCase = "//@ [ s := \\anything ]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
	}
	
	/** Test conditional concurrent assignments */
	@Test
	public void testTypeChecking13() {
		String setUp = "int x = 2;";
		String testCase = "//@ [ x > 0 -> x := 3 ]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
	}
	
	/** Test conditional concurrent assignments (else statement) */
	@Test
	public void testTypeChecking14() {
		String setUp = "int x = 2;";
		String testCase = "//@ [ x > 0 -> x := 3 \\else x > 10 -> x := 20 ]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
	}
	
	/** Test conditional concurrent assignments 
	(multiple else statements and identity function) */
	@Test
	public void testTypeChecking15() {
		String setUp = "int x = 2;";
		String testCase = "//@ [ x > 0 -> x := 3 \\else x > 10 -> x := 20 \\else \\I]";
		assertEquals("",TestUtilities.runChecker(setUp,testCase));
	}


}
