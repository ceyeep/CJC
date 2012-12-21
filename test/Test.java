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
package test;
public class Test {

	static int x = 3, y = 2, zero, z;
	static boolean bit = true;
	static int[] array = new int[]{1,2};
	static Test myTest = new Test();
	
	// [ x > 0 -> this.x:= 3 \else x > 10 -> this.x := 2 \else \I]
	public Test ()
	{
		x = 3;
	}

	//@[x := 3]
	{
		x = 5;
	}
	
	/*[zero := \anything + 3]
	*/
	static
	{
		zero = 0;
	}
	 
	/* 
		[ bit :=  (*informal notation*) ]
	*/
	public static void main(String[] args) {
		// [bit := false ]
		{
			bit = true;
			bit = false;
		}
	}
	
	// [ zero :=  array=>clone(\cj int x, int y; true; true)]
	public static void assignZero()
	{
		zero = returnZero();
	}
	
	//method decl -> iterate operator declaration must inherit methodDecl
	//create a library (just a java class with iterate operator declarations)
	//maybe create iterate operator declartion outside of the CJ scope (extend Java compiler)

	
	// [ x := myTest=>clone(\cj int x, int y; true; true) ]
	public static int returnZero()
	{
		return 0;
	}
}
