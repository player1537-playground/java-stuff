class Tree {
    TreeNode head = new TreeNode();
    TreeNode oldLeftMost = head;
    
    public TreeNode getHead() {
	//System.err.println("Deprecated - getHead");
	return this.head;
    }

    public TreeNode getChild() {
	return head.getLeft();
    }
    
    public void insertTop(TreeNode node) {
	if (head.getLeft() == null) {
	    head.setLeft(node);
	} else {
	    head.getLeft().setHead(node);
	    node.setLeft(head.getLeft());
	    node.setHead(head);
	    head.setLeft(node);
	}
    }

    public void insertRecent(TreeNode node) {
	if (head.getLeft() == null) {
	    System.err.println("Err, what?  insertRecent");
	    head.setLeft(node);
	} else {
	    //System.err.println("oldLeftMost: " + ((Number)oldLeftMost).getNumber());
	    //System.err.println("oldLeftMost: " + (head == oldLeftMost.getHead()));
	    if (head != oldLeftMost.getHead()) {
		node.setLeft(oldLeftMost);
		oldLeftMost.getHead().setRight(node);
		node.setHead(oldLeftMost.getHead());
		oldLeftMost.setHead(node);
	    } else {
		node.setLeft(head.getLeft());
		head.setLeft(node);
		node.getLeft().setHead(node);
		node.setHead(head);
	    }
	}
    }

    public TreeNode getLeftMost() {
	if (head.getLeft() instanceof Number || head.getLeft() == null) {
	    return head;
	} else {
	    return getLeftMost(head.getLeft());
	}
    }

    public TreeNode getLeftMost(TreeNode node) {
	if (node instanceof Operator || node instanceof Setter) {
	    if (node.getLeft() == null) {
		return node;
	    } else if (node.getRight() == null) {
		return node;
	    } else {
		TreeNode tmp = getLeftMost(node.getLeft());
		if (tmp != null) {
		    return tmp;
		}
		tmp = getLeftMost(node.getRight());
		if (tmp != null) {
		    return tmp;
		}
		System.err.println("wtf");
		return null;
	    }
	} else {
	    System.err.println("Parser found dead-end: " + node);
	    return null;
	}    
    }
    
    public void addLeftMost(TreeNode node) {
	try {
	    TreeNode tmp = getLeftMost();
	    if (tmp == null) {
		System.err.println("getLeftMost == null");
	    } else {
		if (tmp.getLeft() == null) {
		    tmp.setLeft(node);
		} else {
		    tmp.setRight(node);
		}
		node.setHead(tmp);
		oldLeftMost = node;
	    }
	} catch (Exception e) {
	    System.err.println("addLeftMost - probably null: " + e);
	}
    }

    public String print(TreeNode node) {
	if (node == null) {
	    return "";
	} else if (node instanceof Number) {
	    return "" + ((Number)node).getNumber();
	} else if (node instanceof Operator) {
	    return ((Operator)node).getType() + "[" + print(node.getLeft()) + "][" + print(node.getRight()) + "]";
	} else if (node instanceof Getter) {
	    return "$" + ((Getter)node).getVariable();
	} else if (node instanceof Setter) {
	    return ((Setter)node).getVariable() + "=[" + print(node.getLeft()) + "]";
	} else {
	    System.err.println("Wait, wtf?");
	    System.err.println(node);
	}
	return "";
    }

    public String toString() {
	return print(head.getLeft());
    }
}