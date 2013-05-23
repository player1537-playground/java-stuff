import java.util.*;

class FunMinus extends FunBase {
    void execute() {
	Double a = (Double)this.dataStack.pop();
	Double b = (Double)this.dataStack.pop();
	this.dataStack.push(b - a);
    }
}
	