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

import java.io.*;
import cleanJavaTools.CleanJavaChecker;

/** This class contains test cases for the CleanJavaChecker frontend. 
 *	@author ceyeep
 */
public class CleanJavaCheckerTest {
	
	private CleanJavaChecker checker;
	
	
	/** Test a file with no CJ errors. */
	@Test
	public void testGetErrors01(){
		String[] args = {"tests"+File.separator+"javaTestFiles"+File.separator+"JavaTestFile01.java"};
		checker = new CleanJavaChecker(args);
		assertTrue(checker.getErrors().size()<1);
	}
	
	/** Test a file wih CJ errors. */
	@Test
	public void testGetErrors02(){
		String[] args = {"tests"+File.separator+"javaTestFiles"+File.separator+"JavaTestFile02.java"};
		checker = new CleanJavaChecker(args);
		assertTrue(checker.getErrors().size()>0);
	}
	
	/** Test get string key from resource. */
	@Test
	public void testGetString01(){
		String[] args = {};
		checker = new CleanJavaChecker(args);
		assertEquals("CleanJavaChecker",checker.name());
	}	
}
