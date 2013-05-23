class Number extends TreeNode {
    double number;

    public Number(String s) {
	this(Double.parseDouble(s));
    }
    
    public Number(double n) {
	setNumber(n);
    }

    public void setNumber(double n) {
	this.number = n;
    }
    public double getNumber() {
	return this.number;
    }

    public String toString() {
	return "{" + super.toString() + ": " + getNumber() + "}";
    }
}