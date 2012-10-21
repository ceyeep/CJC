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
// 3.4 Line Terminators
LineTerminator = \n|\r|\r\n
InputCharacter = [^\r\n]

// 3.6 White Space
WhiteSpace = [ ] | \t | \f | {LineTerminator}

// 3.7 Comments
Comment = {TraditionalComment}
        | {EndOfLineComment}
        | {ShortEndOfLineComment}

TraditionalComment = "/*" [^*@+] ~"*/" | "/*+" [^@] ~"*/" | "/*" "*"+ "/" | "/*" "*"+ [^/*] ~"*/"
EndOfLineComment = "//" [^+@\n\r] {InputCharacter}* {LineTerminator}? | "//+" [^@\n\r] {InputCharacter}* {LineTerminator}?
ShortEndOfLineComment = "//" [+]? {LineTerminator}?


// 3.8 Identifiers
Identifier = [:jletter:][:jletterdigit:]*

// 3.10.1 Integer Literals
DecimalNumeral = 0 | {NonZeroDigit} {Digits}? 
HexNumeral = 0 [xX] [0-9a-fA-F]+
OctalNumeral = 0 [0-7]+

Digits = {Digit}+
Digit = 0 | {NonZeroDigit}
NonZeroDigit = [1-9]

// 3.10.2 Floating-Point Literals
FloatingPointLiteral = {Digits} \. {Digits}? {ExponentPart}?
                     | \. {Digits} {ExponentPart}?
                     | {Digits} {ExponentPart}
ExponentPart = [eE] [+-]? [0-9]+

// 3.10.4 Character Literals
SingleCharacter = [^\r\n\'\\]

// 3.10.5 String Literals
StringCharacter = [^\r\n\"\\]

// 3.10.6 Escape Sequences for Character and String Literals
OctalEscape = \\ {OctalDigit} 
            | \\ {OctalDigit} {OctalDigit}
            | \\  {ZeroToThree} {OctalDigit} {OctalDigit}
OctalDigit = [0-7]
ZeroToThree = [0-3]

