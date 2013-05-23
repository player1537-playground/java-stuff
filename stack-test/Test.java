import java.util.*;
import java.io.*;

public class Test {
    static Stack dataStack = new Stack();
    Function[] funs = {
	new Function("dup", new FunDup(dataStack)),
	new Function("+", new FunPlus(dataStack)),
	new Function("-", new FunMinus(dataStack)),
	new Function(".", new FunDot(dataStack))
    };
    public static void main(String[] args) throws IOException {
	InputStreamReader isr = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(isr);
	String str;
	do {
	    str = in.readLine();
	    eval(str);
	} while (!(str.equals(".")));
	System.out.println("The stack is: " + dataStack);
    }
    static void eval(String str) {
	String[] words = str.split(" ");
	for (String word : words) {
	    try {
		dataStack.push(Double.parseDouble(word));
	    } catch (Exception e) {
		switch (word) {
		case "dup": 
		    Double x = (Double)dataStack.pop();
		    dataStack.push(x);
		    dataStack.push(x);
		    break;
		case "+":
		    dataStack.push((Double)dataStack.pop() + (Double)dataStack.pop());
		    break;
		case ".":
		    System.out.println(dataStack.pop());
		    break;
		}
	    }
	}
    }
}

	    