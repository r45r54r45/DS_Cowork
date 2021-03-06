public class Node <Key extends Comparable<Key>, Value>{
	private static final boolean RED   = true; 
	private static final boolean BLACK = false;
	 
	private Key id;
	private Value name;
	private int balance;
	private int subTreeSize;
	private Node<Key, Value> left, right, parent;
	private boolean color;
	private boolean isDeleted = false;
	public Node(Key k,Node<Key,Value> parent){
		this.id=k;
		this.parent=parent;
	}
	public Node(Key k) {
		  left = right = parent = null;
		  balance = 0;
		  this.color = RED;
		  this.id= k;
	}
	public Node(Key newId, Value newName){
		this.id=newId;
		this.name=newName;
		this.color = RED;
		this.left=null;
		this.right=null;
	} 
	public Node(Key newId, Value newName, int newSubTreeSize){
		this.id=newId;
		this.name=newName;
		this.color = RED;
		this.subTreeSize=newSubTreeSize;
		this.left=null;
		this.right=null;
	} 
	public boolean isDeleted(){return isDeleted;}
    public void setDeleted(boolean isDeleted){this.isDeleted = isDeleted;}
	public boolean getColor(){return color;}
	public void setColor(boolean c){this.color = c;}
	public Key getKey(){return id;}
	public Value getValue(){return name;}
	public void setBalance(int b){
		balance=b;
	}
	public int getBalance(){
		return balance;
	}
	public int getHeight(){return findHeight(this);}
	public int getSubTreeSize(){return subTreeSize;}
	public Node<Key, Value> getLeft(){return left;}
	public Node<Key, Value> getRight() {return right;}
	public Node<Key, Value> getParent() {return parent;}
	public void setLeft(Node<Key, Value> newLeft) {
		this.left = newLeft;
	}
	public void setRight(Node<Key, Value> newRight) {
		this.right = newRight;
	}
	public void setParent(Node<Key, Value> newParent) {
		this.parent = newParent;
	}
	public void setId(Key newId) {
		this.id = newId;
	}
	public void setName(Value newName) {
		this.name = newName;
	}
	public void setSubTreeSize(int newSubTreeSize) {
		this.subTreeSize = newSubTreeSize;
	}
	public void setHeight(int bal) {
		this.balance=bal;
	}
	public void print() {
        print(this, 0);
    }
    
    private void print(Node root, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("   ");
        }
        if (root == null) {
            System.out.println("null");
            return;
        }
        System.out.println(root.getValue());
        if (root.isLeaf()) return;
        print(root.getLeft(), indent + 1);
        print(root.getRight(), indent + 1);
    }
    public boolean isLeaf() {
        return this.getLeft() == null && this.getRight() == null;
    }
   public int findHeight(Node aNode)
    {
        if (aNode == null)
        {
            return -1;
        }

        int lefth = findHeight(aNode.left);
        int righth = findHeight(aNode.right);

        if(lefth > righth)
        {
            return lefth + 1;
        }
        else
        {
            return righth + 1;
        }
    }
}
