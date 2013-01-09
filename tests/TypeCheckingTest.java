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
import org.junit.AfterClass;
import org.junit.BeforeClass;

import testFramework.TestUtilities;

/** This class contains test cases for type checking. 
 *	@author ceyeep
 */
public class TypeCheckingTest {

	private static TestUtilities testUtilities;
	private static String fileName  = "TempTestFile";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		testUtilities = new TestUtilities(fileName);
		testUtilities.createTempDirectory();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownClass() throws Exception {
		testUtilities.deleteTempDirectory();
	}
	
	//Tests
    
	/** Test simple integer assignment. */
	@Test
	public void testTypeChecking01() {
		String setUp = " int x; ";
		String testCase = " //@ [ x := 3] ";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test using keyword this. */
	@Test
	public void testTypeChecking02() {
		String setUp = "int x;";
		String testCase = " //@ [ this.x := 3]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test using an undeclared variable. */
	@Test
	public void testTypeChecking03() {
		String setUp = "int x;";
		String testCase = " //@ [ y := 3]";
		assertEquals("Semantic Error: no field named y is accessible",testUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test using different types. */
	@Test
	public void testTypeChecking04() {
		String setUp = "int x;";
		String testCase = " //@ [ x := \"hello\"]";
		assertEquals("Semantic Error: cannot assign x of type int "
			+"a value of type java.lang.String",testUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test multple assignment. */
	@Test
	public void testTypeChecking05() {
		String setUp = "int x, y;";
		String testCase = " //@ [ x, y := 1,2]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
		
	}
	
	/** Test unbalanced concurrent assignment. */
	@Test
	public void testTypeChecking06() {
		String setUp = "int x, y;";
		String testCase = "//@ [ x, y := 3]";
		assertEquals("Semantic Error: concurrent assignment is unbalanced",testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test informal description 1. */
	@Test
	public void testTypeChecking07() {
		String setUp = "boolean flag;";
		String testCase = "//@ [ flag := (* informal description *) ]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test informal description 2 (different type). */
	@Test
	public void testTypeChecking08() {
		String setUp = "int x;";
		String testCase = "//@ [ x := (* informal description *) ]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
	}
	
	
	/** Test variable duplicate. */
	public void testTypeChecking09() {
		String setUp = "int x;";
		String testCase = "//@ [ x, x := 2, 3 ]";
		assertEquals("Semantic Error: there are duplicates in the left-hand side of the equation: x",
			testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test multiple duplicates. */
	public void testTypeChecking10() {
		String setUp = "String s1, s2;";
		String testCase = "//# [ this.s1, s1, this.s2, s2 := \"s\", \"s\", \"s\", \"s\" ]";
		assertEquals("Semantic Error: there are duplicates in the left-hand side of the equation: s1, s2",
			testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test "anything" literal. */
	@Test
	public void testTypeChecking11() {
		String setUp = "int x;";
		String testCase = "//@ [ x := \\anything ]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test "anything" literal with an object. */
	@Test
	public void testTypeChecking12() {
		String setUp = "String s = \"\";";
		String testCase = "//@ [ s := \\anything ]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test conditional concurrent assignments. */
	@Test
	public void testTypeChecking13() {
		String setUp = "int x = 2;";
		String testCase = "//@ [ x > 0 -> x := 3 ]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test conditional concurrent assignments (else statement). */
	@Test
	public void testTypeChecking14() {
		String setUp = "int x = 2;";
		String testCase = "//@ [ x > 0 -> x := 3 \\else x > 10 -> x := 20 ]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test conditional concurrent assignments 
	(multiple else statements and identity function). */
	@Test
	public void testTypeChecking15() {
		String setUp = "int x = 2;";
		String testCase = "//@ [ x > 0 -> x := 3 \\else x > 10 -> x := 20 \\else \\I]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test a method with a return statement. */
	@Test
	public void testTypeChecking16() {
		String testCase = "//@ [ \\result := 5 ]";
		String code = "public int foo1(){return 5;}";
		assertEquals("",testUtilities.runChecker(testCase,code));
	}
	
	/** Test a method with a double \\result variable . */
	@Test
	public void testTypeChecking17() {
		String testCase = "//@ [ \\result, \\result := 5, 4 ]";
		String code = "public int foo1(){return 5;}";
		assertEquals("Semantic Error: there are duplicates in the left-hand side of the equation: \\result",
			testUtilities.runChecker(testCase,code));
	}
	
	/** Test \\result variable with an invalid type. */
	@Test
	public void testTypeChecking18() {
		String testCase = "//@ [ \\result := 5 ]";
		String code = "public String foo1(){return \"hello\";}";
		assertEquals("Semantic Error: cannot assign \\result of type java.lang.String a value of type int",testUtilities.runChecker(testCase,code));
	}
	
	/** Test \\result variable in a constructor. */
	@Test
	public void testTypeChecking19() {
		String testCase = "//@ [ \\result := 5 ]";
		String code = "public " +fileName+ " (){}";
		assertEquals("Semantic Error: incorrect use of \\result, \\result must be used within a method declaration",testUtilities.runChecker(testCase,code));
	}
	
	/** Test concurrent assignment with referential semantics. */
	@Test
	public void testTypeChecking22() {
		String setUp = "Object o1 = new Object(), o2 = new Object();";
		String testCase = "//@ [ o1 @= 02 ]";
		assertEquals("",testUtilities.runChecker(setUp,testCase));
	}
	
	/** Test incorrect use of simple concurrent assignment with referential semantics. */
	@Test
	public void testTypeChecking21() {
		String setUp = "int x = 2;";
		String testCase = "//@ [ x @= 3 ]";
		assertEquals("Semantic Error: cannot use x of primitive int type using referential semantics",testUtilities.runChecker(setUp,testCase));
	}
}
