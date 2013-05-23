import java.util.*;

class FunPlus extends FunBase {
    void execute() {
	Double a = (Double)this.dataStack.pop();
	Double b = (Double)this.dataStack.pop();
	this.dataStack.push(a + b);
    }
}
	