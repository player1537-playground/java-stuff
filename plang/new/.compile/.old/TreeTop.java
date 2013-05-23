class TreeTop extends TreeNode {
    TreeNode head = new TreeNode();
    
    public void set(TreeNode v) {
	head.setLeft(v);
    }

    public TreeNode getHead() {
	return this.getLeft().getHead();
    }
    public TreeNode getLeft() {
	return this.getLeft().getLeft();
    }
    public TreeNode getRight() {
	return this.getLeft().getRight();
    }
    
    public void setHead(TreeNode v) {
	this.getLeft().setHead(v);
    }
    public void setLeft(TreeNode v) {
	this.getLeft().setLeft(v);
    }
    public void setRight(TreeNode v) {
	this.getLeft().setRight(v);
    }   
}    
