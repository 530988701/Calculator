import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    private static String[] op = { "+", "-", "*", "/" };// Operation set
    public static void main(String[] args) {
        String question = MakeFormula();
        System.out.println(question);
        String ret = Solve(question);
        System.out.println(ret);
    }

    public static void save()  {
        Scanner scanner=new Scanner(System.in);
        BufferedWriter titledWriter=null;
        BufferedWriter answerWriter=null;
        System.out.println("请输入题目数量：");
        int n = Integer.parseInt(scanner.nextLine());
        System.out.println("请输入题目保存地址：");
        String path=scanner.nextLine();
        try {
            titledWriter = new BufferedWriter(new FileWriter(path+"/title.txt"));
            answerWriter = new BufferedWriter(new FileWriter(path+"/answer.txt"));
            titledWriter.write("");
        } catch (IOException e) {
            System.out.println("地址不存在！");
        }
       for (int i = 0; i < n; i++) {
            String question = MakeFormula();
            String ret = Solve(question);
           try {
               titledWriter.write(question+"\r\n");
               answerWriter.write(ret+"\r\n");
           } catch (IOException e) {
               e.printStackTrace();
           }

           if(i % 10 == 0) {
               try {
                   titledWriter.flush();
                   answerWriter.flush();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

       }
            try {
                titledWriter.close();
                answerWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println("创建完成！");
        }

    public static String MakeFormula(){
        StringBuilder build = new StringBuilder();
        int count = (int) (Math.random() * 2) + 1; // generate random count
        int start = 0;
        int number1 = (int) (Math.random() * 99) + 1;
        build.append(number1);
        while (start <= count){
            int operation = (int) (Math.random() * 3); // generate operator
            int number2 = (int) (Math.random() * 99) + 1;
            build.append(op[operation]).append(number2);
            start ++;
        }
        return build.toString();
    }

    public static String Solve(String formula){
        Stack<String> tempStack = new Stack<>();//Store number or operator
        Stack<Character> operatorStack = new Stack<>();//Store operator
        int len = formula.length();
        int k = 0;
        for(int j = -1; j < len - 1; j++){
            char formulaChar = formula.charAt(j + 1);
            if(j == len - 2 || formulaChar == '+' || formulaChar == '-' || formulaChar == '/' || formulaChar == '*') {
                if (j == len - 2) {
                    tempStack.push(formula.substring(k));
                }
                else {
                    if(k < j){
                        tempStack.push(formula.substring(k, j + 1));
                    }
                    if(operatorStack.empty()){
                        operatorStack.push(formulaChar); //if operatorStack is empty, store it
                    }else{
                        char stackChar = operatorStack.peek();
                        if ((stackChar == '+' || stackChar == '-')
                                && (formulaChar == '*' || formulaChar == '/')){
                            operatorStack.push(formulaChar);
                        }else {
                            tempStack.push(operatorStack.pop().toString());
                            operatorStack.push(formulaChar);
                        }
                    }
                }
                k = j + 2;
            }
        }
        while (!operatorStack.empty()){ // Append remaining operators
            tempStack.push(operatorStack.pop().toString());
        }
        Stack<String> calcStack = new Stack<>();
        for(String peekChar : tempStack){ // Reverse traversing of stack
            if(!peekChar.equals("+") && !peekChar.equals("-") && !peekChar.equals("/") && !peekChar.equals("*")) {
                calcStack.push(peekChar); // Push number to stack
            }else{
                int a1 = 0;
                int b1 = 0;
                if(!calcStack.empty()){
                    b1 = Integer.parseInt(calcStack.pop());
                }
                if(!calcStack.empty()){
                    a1 = Integer.parseInt(calcStack.pop());
                }
                switch (peekChar) {
                    case "+":
                        calcStack.push(String.valueOf(a1 + b1));
                        break;
                    case "-":
                        calcStack.push(String.valueOf(a1 - b1));
                        break;
                    case "*":
                        calcStack.push(String.valueOf(a1 * b1));
                        break;
                    default:
                        calcStack.push(String.valueOf(a1 / b1));
                        break;
                }
            }
        }
        return formula + "=" + calcStack.pop();
    }
}
