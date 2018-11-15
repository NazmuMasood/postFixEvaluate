postfixEvaluate - Java Simple Math Expression Evaluator
=================================================

## Introduction
postfixEvaluate is a simple, handy expression evaluator for java that calculates the result of a given mathematical expression. Demonstrates an object-oriented approach to parsing and handling expressions. The program creates postfix notation of the given infix expression and performs postfix expression evaluation. 

## General view
```
 Input:
 a=2
 b=6
 c=3
 a^2+b/c-cxc+(a+b)
 
 Output:
 5
```
## Details about the program
The program will ask the user to input a value(say n). 
 Then user will input n lines of input each of which contains an identifier and its corresponding value. 
 Then program will ask the user again to input a value(say m). Then user will input m lines of expressions. 
 Then program will calculate the final value for each of the given expression using first n lines of input. 
 If you can't evaluate any expression from given numbers of identifiers then output 'Compilation Error'.  
``` 
 Input:
 3
 a = 1
 b = 2
 c = 2
 2
 a x b + a x c + b x c
 a x c - b / c + (c x c)
 
 Output:
 Postfix expression : a b x a c x + b c x + 
 Answer is 8
 
 Postfix expression : a c x b c / - c c x +  
 Answer is 5
 
 Input:
 4
 g = 2
 p = 3
 t = 1
 w = 2
 3
 g + p x t - w x p
 t - g + t - w
 e + t x t - m
 
 Output:
 Postfix expression : g p t x + w p x - 
 Answer is -1
 
 Postfix expression : t g - t + w -  
 Answer is -2
 
 Compilation Error
``` 
### Supported Operators
<table>
  <tr><th>Mathematical Operators</th></tr>
  <tr><th>Operator</th><th>Description</th></tr>
  <tr><td>+</td><td>Additive operator</td></tr>
  <tr><td>-</td><td>Subtraction</td></tr>
  <tr><td>x</td><td>Multiplication operator</td></tr>
  <tr><td>/</td><td>Division operator</td></tr>
  <tr><td>^</td><td>Power operator</td></tr>
</table>

## About used algorithm
 *The algorithm used for getting postfix notation from infix notation :*
 <br>When a infix expression is given -->
 <br>1. Examine the next element in the input.
 <br>2. If it is operand, output it.
 <br>3.  If it is opening parenthesis, push it on stack.
 <br>4.  If it is an operator, then
  <br>  &nbsp;i. If stack is empty, push operator on stack.
  <br>  &nbsp;ii. If the top of stack is opening parenthesis, push operator on stack
  <br>  &nbsp;iii. If it has higher priority than the top of stack, push operator on stack.
  <br>  &nbsp;iv. Else pop the operator from the stack and output it, repeat step 4 
 <br>5.  If it is a closing parenthesis, pop operators from stack and output them until 
  an opening parenthesis is encountered. pop and discard the opening parenthesis.
 <br>6.  If there is more input go to step 1
 <br>7.  If there is no more input, pop the remaining operators to output.
 
  *The algorithm used for calculating postfix expressions :*
   <br>When a postfix expression is given -->
 - get the next element in the input
 - if it is an operand push it onto the stack
 - else if it is an operator
 - pop the stack for the right hand operand
 - pop the stack for the left hand operand
 - apply the operator to the two operands
 - push the result onto the stack
 - when the expression has been exhausted the result is the top (and only element) of the stack

### Notes
```
!!!The program assumes only integer values are given in input
!!!Also assumes 'x'(lower case) as an operator; For simplicity, variables shouldn't contain the letter 'x' 
```

### Project Layout
The program was created and tested using Java 10.0.2.

## About author
 NAZMUDDIN AL MASOOD 
 <br> Contact: nazmumasood96@gmail.com

