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
// Start of intended function
<YYINITIAL> "//" [+]? [@]+                   { yybegin(CLEANSINGLE);}
<YYINITIAL> "/*" [+]? [@]+                   { yybegin(CLEANMULTILINE);}


// End of intended function
<CLEANSINGLE> {
  \n|\r|\r\n|{EndOfLineComment}               { yybegin(YYINITIAL); }
}

<CLEANMULTILINE> {
  [@]* [+]? "*/"                  { yybegin(YYINITIAL); }
}

// ignoring #'s at the beginning of lines of multi-line annotation comments
<CLEANMULTILINE,CLEANMULTILINEEXPRESSION> {
  ^{WhiteSpace}* [@]*                          { }
} 

// ignore comments inside of intended function
<CLEANSINGLE,CLEANMULTILINE> {
  "//" {InputCharacter}* {LineTerminator}?     { }
}


<CLEANSINGLE,CLEANMULTILINE> {
  "{"                            { return sym(Terminals.LBRACE); }
  "}"                            { return sym(Terminals.RBRACE); }
}

// recognizing Cleanjava keywords in annotation function
<CLEANSINGLE,CLEANMULTILINE> {
  "["   { enterPredicate(); return sym(Terminals.START); }
}

//JAVA GRAMMAR + CLEANJAVA GRAMMAR

// The following reserved words are copied from the Java lexer,
<CLEANSINGLE,CLEANMULTILINE,CLEANSINGLEEXPRESSION,CLEANMULTILINEEXPRESSION> {
  {WhiteSpace} { }

  "assert"                       { return sym(Terminals.ASSERT); }
  "abstract"                     { return sym(Terminals.ABSTRACT); }
  "boolean"                      { return sym(Terminals.BOOLEAN); }
  "break"                        { return sym(Terminals.BREAK); }
  "byte"                         { return sym(Terminals.BYTE); }
  "case"                         { return sym(Terminals.CASE); }
  "catch"                        { return sym(Terminals.CATCH); }
  "char"                         { return sym(Terminals.CHAR); }
  "class"                        { return sym(Terminals.CLASS); }
  "const"                        { return sym(Terminals.EOF); }
  "continue"                     { return sym(Terminals.CONTINUE); }
  "default"                      { return sym(Terminals.DEFAULT); }
  "do"                           { return sym(Terminals.DO); }
  "double"                       { return sym(Terminals.DOUBLE); }
  "else"                         { return sym(Terminals.ELSE); }
  "extends"                      { return sym(Terminals.EXTENDS); }
  "final"                        { return sym(Terminals.FINAL); }
  "finally"                      { return sym(Terminals.FINALLY); }
  "float"                        { return sym(Terminals.FLOAT); }
  "for"                          { return sym(Terminals.FOR); }
  "goto"                         { return sym(Terminals.EOF); }
  "if"                           { return sym(Terminals.IF); }
  "implements"                   { return sym(Terminals.IMPLEMENTS); }
  "import"                       { return sym(Terminals.IMPORT); }
  "instanceof"                   { return sym(Terminals.INSTANCEOF); }
  "int"                          { return sym(Terminals.INT); }
  "interface"                    { return sym(Terminals.INTERFACE); }
  "long"                         { return sym(Terminals.LONG); }
  "native"                       { return sym(Terminals.NATIVE); }
  "new"                          { return sym(Terminals.NEW); }
  "package"                      { return sym(Terminals.PACKAGE); }
  "private"                      { return sym(Terminals.PRIVATE); }
  "protected"                    { return sym(Terminals.PROTECTED); }
  "public"                       { return sym(Terminals.PUBLIC); }
  "return"                       { return sym(Terminals.RETURN); }
  "short"                        { return sym(Terminals.SHORT); }
  "static"                       { return sym(Terminals.STATIC); }
  "strictfp"                     { return sym(Terminals.STRICTFP); }
  "super"                        { return sym(Terminals.SUPER); }
  "switch"                       { return sym(Terminals.SWITCH); }
  "synchronized"                 { return sym(Terminals.SYNCHRONIZED); }
  "this"                         { return sym(Terminals.THIS); }
  "throw"                        { return sym(Terminals.THROW); }
  "throws"                       { return sym(Terminals.THROWS); }
  "transient"                    { return sym(Terminals.TRANSIENT); }
  "try"                          { return sym(Terminals.TRY); }
  "void"                         { return sym(Terminals.VOID); }
  "volatile"                     { return sym(Terminals.VOLATILE); }
  "while"                        { return sym(Terminals.WHILE); }
  
  "enum" { return sym(Terminals.ENUM); }
  
}

// The following expression tokens are copied from the Java lexer,
<CLEANSINGLEEXPRESSION,CLEANMULTILINEEXPRESSION> {
  // 3.10.1 Integer Literals
  {NumericLiteral}               { return sym(Terminals.NUMERIC_LITERAL); }
  
  // 3.10.3 Boolean Literals
  "true"                         { return sym(Terminals.BOOLEAN_LITERAL); }
  "false"                        { return sym(Terminals.BOOLEAN_LITERAL); }
  
  // 3.10.4 Character Literals
  \'{SingleCharacter}\'          { return sym(Terminals.CHARACTER_LITERAL, str().substring(1, len()-1)); }
  // 3.10.6 Escape Sequences for Character Literals
  \'"\\b"\'                      { return sym(Terminals.CHARACTER_LITERAL, "\b"); }
  \'"\\t"\'                      { return sym(Terminals.CHARACTER_LITERAL, "\t"); }
  \'"\\n"\'                      { return sym(Terminals.CHARACTER_LITERAL, "\n"); }
  \'"\\f"\'                      { return sym(Terminals.CHARACTER_LITERAL, "\f"); }
  \'"\\r"\'                      { return sym(Terminals.CHARACTER_LITERAL, "\r"); }
  \'"\\\""\'                     { return sym(Terminals.CHARACTER_LITERAL, "\""); }
  \'"\\'"\'                      { return sym(Terminals.CHARACTER_LITERAL, "\'"); }
  \'"\\\\"\'                     { return sym(Terminals.CHARACTER_LITERAL, "\\"); }
  \'{OctalEscape}\'              { int val = Integer.parseInt(str().substring(2,len()-1),8);
			                             return sym(Terminals.CHARACTER_LITERAL, new Character((char)val).toString()); }
  // Character Literal errors
  \'\\.                          { error("illegal escape sequence \""+str()+"\""); }
  \'{LineTerminator}             { error("unterminated character literal at end of line"); }

  // 3.10.5 String Literals
  \"                             { statestack.push(yystate()); yybegin(CLEANEXPRESSIONSTRING); strbuf.setLength(0); }

  // CLEAN Informal expression (* ... *)
  "(*"                           { statestack.push(yystate()); yybegin(CLEANINFORMALDESCRIPTION); strbuf.setLength(0); }

  // 3.10.7 The Null Literal
  "null"                         { return sym(Terminals.NULL_LITERAL); }
  
  // CLEANJAVA Literals
  
  "\\anything"					 { return sym(Terminals.ANYTHING_LITERAL); }
  "\\I"							 { return sym(Terminals.IDENTITY_LITERAL); }
  //"\\result"						 { return sym(Terminals.RESULT_LITERAL); }
  
  // CLEANJAVA iterators
  //"\\any" 						 { return sym(Terminals.ANY_ITERATOR); }


 
  // 3.11 Separators
  "("                            { lParenInExpr();return sym(Terminals.LPAREN); }
  ")"                            { rParenInExpr();return sym(Terminals.RPAREN); }
  "{"                            { return sym(Terminals.LBRACE); }
  "}"                            { return sym(Terminals.RBRACE); }
  "["                            { return sym(Terminals.LBRACK); }
  "]"                            { processSemiInExpr(); return sym(Terminals.RBRACK); }
  ";"                            { return sym(Terminals.SEMICOLON); }
  ","                            { return sym(Terminals.COMMA); }
  "."                            { return sym(Terminals.DOT); }
  
  // CLEANJAVA Separators

  
  // 3.12 Operators
  ">"                            { return sym(Terminals.GT); }
  "<"                            { return sym(Terminals.LT); }
  "!"                            { return sym(Terminals.NOT); }
  "~"                            { return sym(Terminals.COMP); }
  "?"                            { return sym(Terminals.QUESTION); }
  ":"                            { return sym(Terminals.COLON); }
  "=="                           { return sym(Terminals.EQEQ); }
  "<="                           { return sym(Terminals.LTEQ); }
  ">="                           { return sym(Terminals.GTEQ); }
  "!="                           { return sym(Terminals.NOTEQ); }
  "&&"                           { return sym(Terminals.ANDAND); }
  "||"                           { return sym(Terminals.OROR); }
  "+"                            { return sym(Terminals.PLUS); }
  "-"                            { return sym(Terminals.MINUS); }
  "*"                            { return sym(Terminals.MULT); }
  "/"                            { return sym(Terminals.DIV); }
  "&"                            { return sym(Terminals.AND); }
  "|"                            { return sym(Terminals.OR); }
  "^"                            { return sym(Terminals.XOR); }
  "%"                            { return sym(Terminals.MOD); }
  "<<"                           { return sym(Terminals.LSHIFT); }
  ">>"                           { return sym(Terminals.RSHIFT); }
  ">>>"                          { return sym(Terminals.URSHIFT); }


  "@"                            { return sym(Terminals.AT); }
  "..."                          { return sym(Terminals.ELLIPSIS); }

  // CLEANJAVA Operators
  ":="							{ return sym(Terminals.CLEANEQ); }
  "->"							{ return sym(Terminals.THEN); }
  "\\else"						{ return sym(Terminals.CLEANELSE); }
  "=>"							{ return sym(Terminals.ITERATOR); }
  "\\cj"							{ return sym(Terminals.CJVAR); }

  // 3.8 Identifiers located at end of current state due to rule priority disambiguation
  ([:jletter:]|[\ud800-\udfff])([:jletterdigit:]|[\ud800-\udfff])* { return sym(Terminals.IDENTIFIER); }

  }


// The following are copied from the Java lexer,
<CLEANEXPRESSIONSTRING> {
  \"                             { yybegin((Integer)statestack.pop()); return sym(Terminals.STRING_LITERAL, strbuf.toString()); }

  {StringCharacter}+             { strbuf.append(str()); }

  // 3.10.6 Escape sequences for String Literals
  "\\b"                          { strbuf.append( '\b' ); }
  "\\t"                          { strbuf.append( '\t' ); }
  "\\n"                          { strbuf.append( '\n' ); }
  "\\f"                          { strbuf.append( '\f' ); }
  "\\r"                          { strbuf.append( '\r' ); }
  "\\\""                         { strbuf.append( '\"' ); }
  "\\'"                          { strbuf.append( '\'' ); }
  "\\\\"                         { strbuf.append( '\\' ); }
  {OctalEscape}                  { strbuf.append((char)Integer.parseInt(str().substring(1),8)); }

  // String Literal errors
  \\.                            { error("illegal escape sequence \""+str()+"\""); }
  {LineTerminator}               { error("unterminated string at end of line"); }
}


<CLEANINFORMALDESCRIPTION>{
  "*)"                           { yybegin((Integer)statestack.pop()); return sym(Terminals.CLEAN_INFORMAL, strbuf.toString()); }
  [^*]                           { strbuf.append(str()); }
}
