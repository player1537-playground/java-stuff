import java.util.*;

class PlangCompiler {
    public static void compile(String s) {
	//header();
	compileFull(makeTree(s).getHead().getLeft());
	//footer();
    }

    private static void header() {
	System.out.println(".class Test");
	System.out.println(".method static run ()D");
    }
    private static void footer() {
	System.out.println(" dreturn");
	System.out.println(".end method");
    }

    public static void compileFull(TreeNode node) {
	System.out.println(".class Test");
	System.out.print(".method static run (");
	for (int i=0; i<Variable.getVariableCount(); i++) {
	    System.out.print("D");
	}
	System.out.println(")D");
	compile(node);
	footer();
    }

    public static void compile() {
	//compile(head.getLeft());
	System.err.println("Don't use compile()");
    }

    public static void compile(TreeNode node) {
	if (node instanceof Operator) {
	    compile(node.getLeft());
	    compile(node.getRight());
	    //PlangCompiler.code(PlangCompiler.operator(((Operator)node).getType()));
	    code(((Operator)node).getCode());
	    //return compile(node.getLeft()) + compile(node.getRight()) + ((Operator)node).getType() + "\n";
	} else if (node instanceof Setter) {
	    compile(node.getLeft());
	    PlangCompiler.code(((Variable)node).getCode());
	} else if (node instanceof Getter) {
	    PlangCompiler.code(((Variable)node).getCode());
	} else if (node instanceof Number) {
	    PlangCompiler.code(PlangCompiler.constant(((Number)node).getNumber()));
	    //return ((Number)node).getNumber() + "\n";
	} else {
	    System.err.println("Not a Number nor Operator: " + node);
	}
    }

    public static Tree makeTree(String s) {
	Stack<Tree> trees = new Stack<Tree>();
	trees.push(new Tree());
	for (int i=0; i<s.length(); i++) {
	    System.err.println("Stack: " + trees.peek());
	    char c = s.charAt(i);
	    if (Character.isDigit(c)) {
		System.err.println("Adding number: " + c + " || " + trees.peek());
		trees.peek().addLeftMost(new Number("" + c));
		System.err.println("Added number: " + c + " || " + trees.peek());
	    } else if (Character.isLetter(c)) {
		try {
		    if (s.charAt(i+1) == '=') {
			trees.peek().insertRecent(new Setter(c));
			trees.push(new Tree());
			s += ")";
			i++;
			System.err.println("Creating var= || " + c);
		    } else {
			trees.peek().addLeftMost(new Getter(c));
		    }
		} catch (Exception e) {
		    System.err.println("I caught yo error, fool: " + e);
		    trees.peek().addLeftMost(new Getter(c));
		}
	    } else if (c == '^') {
		trees.peek().insertTop(new PowerOperator());
	    } else if (c == '+') {
		trees.peek().insertTop(new AddOperator());
	    } else if (c == '-') {
		System.err.println("Yo, this value is: " + trees.peek().getLeftMost());
		trees.peek().insertTop(new SubtractOperator());
	    } else if (c == '*') {
		trees.peek().insertRecent(new MultiplyOperator());
	    } else if (c == '/') {
		trees.peek().insertRecent(new DivideOperator());
	    } else if (c == '(') {
		trees.push(new Tree());
	    } else if (c == ')') {
		Tree t = trees.pop();
		trees.peek().addLeftMost(t.getChild());
	    } else if (c == ';') {
		trees.peek().insertRecent(new PrintOperator());
	    }
	}
	System.err.println("Trees: " + trees);
	System.err.println(trees.peek().toString());
	return trees.peek();
    }

    public static String constant(Double num) {
	/*Map<double, String> map = new Map<double, String>() {{
		put(0, "iconst_0");
		put(1, "iconst_1");
		put(2, "iconst_2");
		put(3, "iconst_3");
		put(4, "iconst_4");
		put(5, "iconst_5");
		put(-1, "iconst_m1");
	    }};
	*/
	if (num == 0.0) {
	    return "dconst_0";
	} else if (num == 1.0) {
	    return "dconst_1";
	} else {
	    return "ldc2_w " + num + "D";
	}
    }

    public static String operator(char oper) {
	switch (oper) {
	case '+':
	    return "dadd";
	case '-':
	    return "dsub";
	case '/':
	    return "ddiv";
	case '*':
	    return "dmul";
	case '%':
	    return "drem";
	case ';':
	    return "getstatic java/lang/System/out Ljava/io/PrintStream;\n dup_x2\n pop\n invokevirtual java/io/PrintStream/println (D)V";
	default:
	    System.out.println("Operator '" + oper + "' unknown");
	    return "";
	}
    }

    public static void code(String code) {
	System.out.println(" " + code);
    }
    public static void code(String[] codes) {
	for (String code : codes) {
	    code(code);
	}
    }

    public static void compileTree(Tree tree) {
	//System.out.println(tree.compile());
    }

    public static String load(int pos) {
	switch (pos) {
	case 0:
	    return "dload_0";
	case 1:
	    return "dload_1";
	case 2:
	    return "dload_2";
	case 3:
	    return "dload_3";
	default:
	    return "dload " + String.valueOf(pos);
	}
    }

    public static String store(int pos) {
	switch (pos) {
	case 0:
	    return "dstore_0";
	case 1:
	    return "dstore_1";
	case 2:
	    return "dstore_2";
	case 3:
	    return "dstore_3";
	default:
	    return "dstore " + String.valueOf(pos);
	}
    }
}
