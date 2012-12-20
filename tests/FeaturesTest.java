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

import java.io.File;

import cleanJavaTools.CleanJavaChecker;

/**
 * @author Yeep
 *
 */
public class FeaturesTest {

	private CleanJavaChecker checker;
	
	
	/** Test frontend test file that includes main CJC features*/
	@Test
	public void testFeaturesFile(){
		String[] args = {"test"+File.separator+"TestFile.java"};
		checker = new CleanJavaChecker(args);
		assertEquals("",checker.getErrors());
	}


}
