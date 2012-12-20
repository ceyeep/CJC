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
package tests.javaTestFiles;
public class JavaTestFile02 {
	private static int x;
	
	//@	[x := 3 
	public static void main(String[] main){
		x = 3;
		System.out.println("Number: "+x);
	}
}