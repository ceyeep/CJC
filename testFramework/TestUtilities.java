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
package testFramework;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;

import cleanJavaTools.CleanJavaChecker;

/** Provides utilities to run a test case in CJC frontend. */
public class TestUtilities {

	private String fileName = "TempTestFile";
	private Path workspacePath;
	private Path filePath;
	
	
	/** Runs the checker and returns any error generated by it. 
	 *
	 *	@param setUp Provides any neccesary setup for the testcase.
	 *  @param testCase Contains current testcase.
	*/
	public String runChecker(String setUp, String testCase) {
		String test = setUp + "\n\n" + testCase;
		return runChecker(test);
	}

	
	/** Runs the checker and returns any error generated by it. 
	 *
	 *  @param testCase Contains current testcase.
	*/
	public String runChecker(String testCase) {
		try{
			createFile(createStub(testCase));
		} 
		catch (IOException e){
			System.err.println("Error creating file: "+e.getMessage());
		}
		String checkerOutput = new CleanJavaChecker(new String[]{filePath.toString()}).getErrorString();
		deleteFile();
		return	checkerOutput;
	}
	
	/** Create a temporal folder to run tests. */
	public void createTempDirectory() {
		//create a temp directory
		try {
			workspacePath = Files.createTempDirectory("tempTest");
			//System.out.println(workspacePath.toString()+" created");
		} catch (IOException e) {
			e.printStackTrace();
		}
		filePath = Paths.get(workspacePath.toString()+File.separator+fileName+".java");
	}
	
	/** Delete temporal folder. */
	public void deleteTempDirectory() {
		//delete temp directory
		try{
			Files.deleteIfExists(workspacePath);
			//System.out.println(workspacePath.toString()+ " deleted");
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/** Creates a stub for the testcase. */
	private String createStub(String testCase) {
		String stub = "public class " + fileName +"{\n";
		stub+=testCase;
		stub+="\n\tpublic void foo(){}\n}";
		return stub;
	}
	
	/** Creates a new java file from the stub. */
	private void createFile(String code) throws IOException {
		Charset charset = Charset.forName("US-ASCII");
		
		try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) {
			writer.write(code, 0, code.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
	
	/** Delete temp file. */
	private void deleteFile() {
		try{
			Files.deleteIfExists(filePath);
			//System.out.println(filePath.toString()+ " deleted");
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}