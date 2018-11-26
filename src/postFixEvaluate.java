/**Author: NAZMUDDIN AL MASOOD - Contact:nazmumasood96@gmail.com 
  Date started:17/10/2018; Date finished:19/10/2018; Last edited:14/11/2018
  **/

/*                  ----- About the program ----
 The program will ask the user to input a value(say n). 
 Then user will input n lines of input each of which contains an identifier and its corresponding value. 
 Then program will ask the user again to input a value(say m). Then user will input m lines of expressions. 
 Then program will calculate the final value for each of the given expression using first n lines of input. 
 If you can't evaluate any expression from given numbers of identifiers then output 'Compilation Error'. 
 Allowed mathematical operators are +(add), -(subtract), x(multiply), /(divide), ^(power).
 !!!The program assumes only integer values are given in input; 
 !!!Also assumes 'x'(lower case) as an operator. For simplicity, variables shouldn't contain the letter 'x'   
 
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
 */

/*            ----- About used algorithms -----
 ***The algorithm used for getting postfix notation from infix notation
 --> when a infix expression is given
 1)Examine the next element in the input.
 2)  If it is operand, output it.
 3)  If it is opening parenthesis, push it on stack.
 4)  If it is an operator, then
 i) If stack is empty, push operator on stack.
 ii) If the top of stack is opening parenthesis, push operator on stack
 iii) If it has higher priority than the top of stack, push operator on stack.
 iv) Else pop the operator from the stack and output it, repeat step 4 
 5)  If it is a closing parenthesis, pop operators from stack and output them until 
 an opening parenthesis is encountered. pop and discard the opening parenthesis.
 6)  If there is more input go to step 1
 7)  If there is no more input, pop the remaining operators to output.
 
 ***The algorithm used for calculating postfix expressions
 --> When a postfix expression is given
 -get the next element in the input
 -if it is an operand push it onto the stack
 -else if it is an operator
 -pop the stack for the right hand operand
 -pop the stack for the left hand operand
 -apply the operator to the two operands
 -push the result onto the stack
 -when the expression has been exhausted the result is the top (and only element) of the stack
 */

/* Methods used : main(), analyzer(), makePostV(), calPV(), precedenceLevel()  */
import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;
import java.util.StringTokenizer;
public class postFixEvaluate{
  
  static ArrayList<String> var = new ArrayList<String>();//Contains the identifiers   
  static ArrayList<String> num = new ArrayList<String>();  
  static ArrayList<String> expr = new ArrayList<String>();  
  static ArrayList<Integer> intA=new ArrayList<Integer>();//Contains the identifiers' corresponding numerical values  
  
  public static void main(String[]args){
    Scanner sc = new Scanner(System.in);
    
//Taking the identifiers and their values as string input    
    System.out.println("How many identifiers ?");
    int n1 = sc.nextInt();    
    for(int i=0;i<=n1;i++){
      String s1 = sc.nextLine();
      analyzer(s1);
      if(var.size()!=0&&num.size()!=0){
        if(var.get(var.size()-1).equals("x")){
          System.out.println("Wrong input : 'x' can not be a variable ");
          var.remove(var.size()-1);num.remove(num.size()-1); i--;
        }
      }
    }     
    int[]a=new int[num.size()];
    for(int i=0;i<a.length;i++){a[i]=Integer.parseInt(num.get(i));}
    for(int i=0;i<a.length;i++){intA.add(a[i]);}//Numerical values are stored in arrayList "intA" 
    
//Taking the expressions as string input      
    System.out.println("How many expressions ?");
    int n2 = sc.nextInt();    
    String [] ar = new String[n2];//Contains the given expressions 
    for(int i=0;i<=n2;i++){
      String s1 = sc.nextLine();
      s1 = s1.replaceAll("\\s","");
      expr.add(s1);//Ghaplar jnne nite hoise
    }    
    for(int i=0,j=1;i<ar.length;i++,j++){ar[i]=expr.get(j);}//Each expression is stored as a string in array "ar"
    
//Executing the expressions one by one
    for(int i=0;i<ar.length;i++){     
      String tempExpr = ar[i];//"s" contains current expression
      
      //Checks for unknown identifiers in the given expression
      String sD = tempExpr+"$";
      int matched = 0; int p = 0; 
      while(p<tempExpr.length()){
        int count = 1; 
        String sTemp=Character.toString(tempExpr.charAt(p));
        
        //this portion enables the program succesfully identify variables with length more than 'one' i.e.
        //'abad','temp' instead of 'a','b','t'
        String sNext = Character.toString(sD.charAt(p+1));
        if(Character.toString(sTemp.charAt(sTemp.length()-1)).matches("[a-z&&[^x]].*")||Character.toString(sTemp.charAt(sTemp.length()-1)).matches("[A-Z].*")||Character.toString(sTemp.charAt(sTemp.length()-1)).matches("-?\\d+(\\.\\d+)?")){
          while(!sNext.matches(".*[-+x/^%$()].*")){
            p++;
            sTemp=sTemp+sNext;
            sNext=Character.toString(sD.charAt(p+1));
          }
        }
        
        if(sTemp.matches("[a-z&&[^x]].*")||sTemp.matches("[A-Z].*")){
          count=0;          
          for(int k=0;k<var.size();k++){
            if(sTemp.equals(var.get(k))){count=1;}
          }
          if(count==0){matched=0;break;}
          if(count==1){matched=1;}
        }
        if(sTemp.matches("-?\\d+(\\.\\d+)?")){matched=1;}
        if(sTemp.matches(".*[-+x/^$%()].*")){
          if(sTemp.equals(")")){matched=1;}
          else{matched=0;}
        }
        p++;
      }
      //If unknown identifiers exist, compilation error
      if(matched==0){System.out.println("Compilation error");}
      
      //If all known identifiers, starts calculation
      if(matched==1){
        //System.out.println("Good to go"); 
        
        //Producing a postfix expression
        ArrayList<String> postV = new ArrayList<String>();        
        postV=makePostV(tempExpr); 
        //Printing the postfix expression
        System.out.print("Postfix expression : ");
        for(int j=0;j<postV.size();j++){System.out.print(postV.get(j)+" ");}
        System.out.println();
        
        //Calculating postfix expression
        System.out.println("Answer is "+calPV(postV));
        System.out.println();
      }      
    }      
  }
  
  
//"makePostV" method produces postfix expression   
  public static ArrayList<String> makePostV(String s){
    String sD = s+"$";
    Stack<String> st1 = new Stack<String>();//Operators and parenthesis are stored temporarily on string stack "st1"
    ArrayList<String> postV = new ArrayList<String>();
    int p=0;//Pointer for each character in the expression; Gets incremented 
    
    while(p<s.length()){
      int count = 0;//Count for making sure only one of the following "if"s are executed in one iteration     
      String s1=Character.toString(s.charAt(p));
      
      String sNext = Character.toString(sD.charAt(p+1));
      if(Character.toString(s1.charAt(s1.length()-1)).matches("[a-z&&[^x]].*")||Character.toString(s1.charAt(s1.length()-1)).matches("[A-Z].*")||Character.toString(s1.charAt(s1.length()-1)).matches("-?\\d+(\\.\\d+)?")){
        while(!sNext.matches(".*[-+x/^%$()].*")){
          p++;
          s1=s1+sNext;
          sNext=Character.toString(sD.charAt(p+1));
        }
      }
      
      //For identifiers in the expression      
      if((s1.matches("[a-z&&[^x]].*")||s1.matches("[A-Z].*"))&&count==0){postV.add(s1);count++;}
      
      //For numerical values given directly in the expression
      if(s1.matches("-?\\d+(\\.\\d+)?")&&count==0){postV.add(s1);count++;}
      
      //For parenthesis in the expression
      if((s1.equals("(")||s1.equals(")"))&&count==0){ 
        if(s1.equals("(")){st1.push("(");}
        if(s1.equals(")")){
          while(!st1.peek().equals("(")&&!st1.empty()){String currOp = st1.pop();postV.add(currOp);}
          st1.pop();
        }count++;
      }  
      
      //For operators in the expression
      if(s1.equals("+")||s1.equals("-")||s1.equals("%")||s1.equals("/")||s1.equals("x")||s1.equals("^")){     
        if(st1.empty()&&count==0){st1.push(s1);count++;}
        
        if(!st1.empty()&&count==0){
          if(st1.peek().equals("(")){st1.push(s1);count++;}          
          
          String s2=st1.peek();
          char op1 = s2.charAt(0);
          char op2=s1.charAt(0);       
          
          if(count==0){
            while((precedenceLevel(op1)>=precedenceLevel(op2))&&!st1.empty()){
              String currOp=st1.pop();            
              postV.add(currOp);
              if(!st1.empty()&&st1.peek().equals("(")){break;}
              if(!st1.empty()){s2=st1.peek();op1 = s2.charAt(0);}
            } st1.push(s1);count++;      
          }
          
          if((precedenceLevel(op1)<precedenceLevel(op2))&&count==0){
            st1.push(s1);
            count++;
          }          
        }     
      }    
      p++;  
    }
    
    //At the end pops operators if remained in the stack
    while(!st1.empty()){
      String currOp=st1.pop();
      postV.add(currOp);
    }       
    return postV;//Returns the postfix expression
  }
  
  
//"calPV" method calculates postfix expression of a "int"  
  public static int calPV(ArrayList<String> arL){
    ArrayList<String> postV = new ArrayList<String>();
    postV = arL;
    Stack<String> st2 = new Stack<String>();//Operands are stored temporarily on string stack st2
    int summ=0; //Stores the answer to the expression 
    
    for(int i=0;i<postV.size();i++){
      String s = postV.get(i);
      
      //Deals with the operands (identifiers and numerical values) in the expressions
      //If operand, push them in the stack
      if((s.matches("[a-z&&[^x]].*")||s.matches("[A-Z].*"))||s.matches("-?\\d+(\\.\\d+)?")){st2.push(s);}
      
      //Deals with the operators in the expression
      //If operator, pop two operands from the stack
      else{
        int n1=0,n2=0;int index1=-1,index2=-1;
        String ss2 = st2.pop();
        String ss1 = st2.pop();
        
        //If the operands are identifiers
        if(!ss1.matches("-?\\d+(\\.\\d+)?")){
          for(int j=0;j<var.size();j++){
            if(ss1.equals(var.get(j))){
              index1=j;
              break;
            }
          }
          n1= intA.get(index1);
        }        
        if(!ss2.matches("-?\\d+(\\.\\d+)?")){
          for(int j=0;j<var.size();j++){
            if(ss2.equals(var.get(j))){
              index2=j;
              break;
            }
          }
          n2= intA.get(index2);
        }
        
        //If the operands are numerical values
        if(ss1.matches("-?\\d+(\\.\\d+)?")){ n1=Integer.parseInt(ss1);}
        if(ss2.matches("-?\\d+(\\.\\d+)?")){ n2=Integer.parseInt(ss2);}
        
        //Calculates the operands by the operator
        if(s.equals("+")){summ=n1+n2;st2.push(Integer.toString(summ));}
        if(s.equals("-")){summ=n1-n2;st2.push(Integer.toString(summ));}
        if(s.equals("/")){summ=n1/n2;st2.push(Integer.toString(summ));}
        if(s.equals("x")){summ=n1*n2;st2.push(Integer.toString(summ));}
        if(s.equals("%")){summ=n1%n2;st2.push(Integer.toString(summ));} 
        if(s.equals("^")){summ=(int) Math.pow(n1, n2);st2.push(Integer.toString(summ));}             
      }            
    }   
    return summ; //Returns the answer to the expression
  }
  
  
//"precendenceLevel" method to compare operator's precendence
  static int precedenceLevel(char op) {
    switch (op) {
      case '+':
      case '-':
        return 0;
      case 'x':
      case '/':
      case '%': 
        return 1;
      case '^':
        return 2;
      default:
        throw new IllegalArgumentException("Operator unknown: " + op);
    }
  }
  
  
//"analyzer" method for identifying the identifiers and retrieving their values  
  public static int analyzer(String s){ 
//Checking for WhiteSpaces
    if(s.contains(" ")){
      StringTokenizer st = new StringTokenizer(s," ");    
      int t = st.countTokens();
      for (int i=0;i<t;i++){
        String s1 = st.nextToken();
        analyzer(s1);
      }return 0;
    }    
    
//Checking for "="
    if(s.contains("=")){
      StringTokenizer st = new StringTokenizer(s,"=");
      int t = st.countTokens();
      for (int i=0;i<t;i++){
        String s1 = st.nextToken();
        analyzer(s1);
      } return 0;
    }     
    
//Checking for Variables
    if(s.matches("[a-zA-Z].*")){
      int count=0;
      for(int i=0;i<var.size();i++){
        if(s.equals(var.get(i))){count++;}
      }
      if(count==0){var.add(s);}
      return 0;
    }
    
//Checking for Integers
    if(s.matches("-?\\d+(\\.\\d+)?")){
      num.add(s); return 0;
    }
    return 0;    
  }
}
