import java.util.*;

class CompileTest {
    public static void main(String[] args) {
	String s;
	if (args.length >= 1) {
	    s = args[0];
	} else {
	    s = "3+2-1/3";
	}
	System.out.println("; " + s);
	//PlangCompiler pc = new PlangCompiler();
	PlangCompiler.compile(s);
    }
}