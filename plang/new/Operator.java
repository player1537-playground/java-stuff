abstract class Operator extends TreeNode {
    char type = 'x';
 
    public Operator() {
	setType('x');
    }

    public Operator(char t) {
	setType(t);
    }
    public void setType(char t) {
	this.type = t;
    }
    public char getType() {
	return this.type;
    }

    public abstract String[] getCode();

    public String toString() {
	return "{" + super.toString() + ": " + type + "}";
    }
}