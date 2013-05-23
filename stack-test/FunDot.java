import java.util.*;

class FunDot extends FunBase {
    void execute() {
	Double a = (Double)this.dataStack.pop();
	System.out.println(a);
    }
}
	