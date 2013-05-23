class Function {
    String name;
    FunBase fb;
    public Function(String name, FunBase fb) {
	this.name = name;
	this.fb = fb;
    }
    public boolean eval(String name) {
	if (this.name.equals(name)) {
	    fb.execute();
	    return true;
	} else {
	    return false;
	}
    }
}