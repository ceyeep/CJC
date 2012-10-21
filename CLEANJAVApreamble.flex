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
// replace preamble found in Frontend1.4
package scanner;

import beaver.Symbol;
import beaver.Scanner;
import parser.JavaParser.Terminals;
import java.io.*;
// added to track states
import java.util.Stack;


%%

%public 
%final 
%class JavaScanner
%extends Scanner

%type Symbol 
%function nextToken 
%yylexthrow Scanner.Exception

%unicode
%line %column

%{
  StringBuffer strbuf = new StringBuffer(128);
  int sub_line;
  int sub_column;
  int strlit_start_line, strlit_start_column;
  
  /** Track states for CLEANJAVA, especially in predicates */
  Stack statestack = new Stack();
  
  /** Track ( and ) in expressions, so we know what to do if we see ; */
  int parenstack = 0;

  private Symbol sym(short id) {
    return new Symbol(id, yyline + 1, yycolumn + 1, len(), str());
  }

  private Symbol sym(short id, String value) {
    return new Symbol(id, yyline + 1, yycolumn + 1, len(), value);
  }

  private Symbol sym(short id, String value, int start_line, int start_column, int len) {
    return new Symbol(id, start_line, start_column, len, value);
  }

  private String str() { return yytext(); }
  private int len() { return yylength(); }

  private void error(String msg) throws Scanner.Exception {
    throw new Scanner.Exception(yyline + 1, yycolumn + 1, msg);
  }
  
  
  
  /** enter the appropriate state for a CLEANJAVA predicate expression.*/
  private void lParenInExpr() {
    if (yystate() == CLEANSINGLEEXPRESSION || yystate() == CLEANMULTILINEEXPRESSION)
	  parenstack++;
  }

  private void rParenInExpr() {
    if (yystate() == CLEANSINGLEEXPRESSION || yystate() == CLEANMULTILINEEXPRESSION)
	  parenstack--;
  }
  
  /** counts semicolons for quantifiers in expressions. */
  private void processSemiInExpr() {
	if (parenstack > 0)
		return;
    Integer i = yystate();
   //System.out.println("Start processSemiInExpr");
    if(i==CLEANSINGLEEXPRESSION || i==CLEANMULTILINEEXPRESSION) {
      yybegin((Integer)statestack.pop());
      //System.out.println("pop once");
    }
  }
  
  /** enter the appropriate state for a CLEAN predicate expression.*/
  private void enterPredicate() {
    switch(yystate()) {
      case CLEANSINGLE:
        statestack.push(CLEANSINGLE);
        yybegin(CLEANSINGLEEXPRESSION);
        //System.out.println("Enter Single Line Expression State");
        break;
      case CLEANMULTILINE:
        statestack.push(CLEANMULTILINE);
        yybegin(CLEANMULTILINEEXPRESSION);
        //System.out.println("Enter Multiline Expression State");
        break;
    }
  } 
%}


// CLEANJAVA state
%state CLEANMULTILINE
%state CLEANSINGLE
%state CLEANSINGLEEXPRESSION
%state CLEANMULTILINEEXPRESSION
%state CLEANEXPRESSIONSTRING
%state CLEANINFORMALDESCRIPTION
