
public class Node <Key extends Comparable<Key>, Value>{
	private Key id;
	private Value name;
	private int balance;
	private int subTreeSize;
	private Node<Key, Value> left, right, parent;
	public Node(Key k) {
		  left = right = parent = null;
		  balance = 0;
		  this.id= k;
	}
	public Node(Key newId, Value newName, int newSubTreeSize){
		this.id=newId;
		this.name=newName;
		this.subTreeSize=newSubTreeSize;
		this.left=null;
		this.right=null;
	} 
	public Key getKey(){return id;}
	public Value getValue(){return name;}
	public int getBalance(){return balance;}
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
	public void setBalance(int bal) {
		// TODO Auto-generated method stub
		this.balance=bal;
	}
}
