class FunDup extends FunBase {
    void execute() {
	Object x = this.dataStack.pop();
	this.dataStack.push(x);
	this.dataStack.push(x);
    }
}
	