import java.util.Iterator;
import java.util.LinkedList;

public class AVL <Key extends Comparable<Key>, Value> extends Testing implements CommonMethod<Key, Value> {
	private Node<Key, Value> root;
	
	public AVL(){
        root = null;
    }
	public AVL(Key k,Value v){
		root=new Node<Key, Value>(k,v,1);
	}
	
	public Node<Key, Value> getRoot(){return root;}
	public int size(){return size(root);}
	public int size(Node<Key, Value> n){
		if(n==null)return 0;
		else return n.getSubTreeSize();
	}
	public boolean isEmpty(){return (size()==0);}
	public boolean contains(Key k){return get(k)!=null;}
	
	
	@Override
	public Value get(Key k) {
		return get(root,k);
	}
	private Value get(Node n, Key k){
		if(n==null)return null;
		int t=n.getKey().compareTo(k);
		if(t>0)return get(n.getLeft(),k);
		else if(t<0)return get(n.getRight(),k);
		else return (Value)n.getValue();
	}
  
	@Override
	public void put(Key k, Value v) {
		// TODO Auto-generated method stub
		root=put(root,k,v);	
		//System.out.print(get(k)+" ");
	}
	private Node put(Node n, Key k, Value v){
		if(n==null)return new Node<Key,Value>(k,v,1);
		int t=n.getKey().compareTo(k);
		if(t>0){
			n.setLeft(put(n.getLeft(),k,v));
		}
		else if(t<0){
			n.setRight(put(n.getRight(),k,v));
		}
		else n.setName(v);
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		recursiveBalance(n);
		return n;
	}
	public void recursiveBalance(Node cur) {
		  Balance(cur);
		  int balance = cur.getBalance();
		  if(balance==2){//LL or LR
			   if(height(cur.getLeft().getLeft())>=height(cur.getLeft().getRight())) {//LL
			    cur = rotateLeft(cur);
			   } else {//LR
			    cur = doubleRotateLeftRight(cur);
			   }
		  }else if(balance==-2){//RR or RL
			   if(height(cur.getRight().getRight())>=height(cur.getRight().getLeft())) {//RR
				    cur = rotateRight(cur);
			   } else {//RL
				    cur = doubleRotateRightLeft(cur);
			   }
		  }
		  if(cur.getParent()!=null){
			   recursiveBalance(cur.getParent());
		  }else {
		   this.root = cur;
		  }
	}
	 public Node rotateRight(Node n) {
		  Node v = n.getRight();
		  if(n.getParent()!=null)
			  v.setParent(n.getParent());
		  n.setRight(v.getLeft());
		  if(n.getRight()!=null) {
		   n.getRight().setParent(n);
		  }
		  v.setLeft(n);
		  n.setParent(v);
		  if(v.getParent()!=null) {
		   if(v.getParent().getRight()==n) {
		    v.getParent().setRight(v);
		   } else if(v.getParent().getLeft()==n) {
		    v.getParent().setLeft(v);
		   }
		  }
		  Balance(n);
		  Balance(v);
		  return v;
	}
	public Node rotateLeft(Node n) {
		  Node v = n.getLeft();
		  if(n.getParent()!=null)
			  v.setParent(n.getParent());
		  n.setLeft(v.getRight());
		  if(n.getLeft()!=null) {
		   n.getLeft().setParent(n);
		  }
		  v.setRight(n);
		  n.setParent(v);
		  if(v.getParent()!=null) {
		   if(v.getParent().getRight()==n) {
		    v.getParent().setRight(v);
		   } else if(v.getParent().getLeft()==n) {
		    v.getParent().setLeft(v);
		   }
		  }
		  Balance(n);
		  Balance(v);
		  return v;
	}
	public Node doubleRotateLeftRight(Node u) {
		  u.setLeft(rotateRight(u.getLeft()));
		  return rotateLeft(u);
	}
	public Node doubleRotateRightLeft(Node u) {
		  u.setRight(rotateLeft(u.getRight()));
		  return rotateRight(u);
	}
	private void Balance(Node cur) {
		  int bal = height(cur.getLeft())-height(cur.getRight());
		  cur.setBalance(bal);
	}
	private int height(Node cur) {
		  if(cur==null) {
		   return -1;
		  }
		  if(cur.getLeft()==null && cur.getRight()==null) {
		   return 0;
		  } else if(cur.getLeft()==null) {
		   return 1+height(cur.getRight());
		  } else if(cur.getRight()==null) {
		   return 1+height(cur.getLeft());
		  } else {
		   return 1+maximum(height(cur.getLeft()),height(cur.getRight()));
		  }
	}
	private int maximum(int a, int b) {
		  if(a>=b) {
		   return a;
		  } else {
		   return b;
		  }
	}
	@Override
	public Key min(){//m
		if(isEmpty())return null;
		return (Key)min(root).getKey();
	}
	private Node min(Node n){
		if(n.getLeft()==null)return n;
		return min(n.getLeft());
	}
	@Override
	public void deleteMin() {//D
		if(!isEmpty())root=deleteMin(root);	
	}
	private Node deleteMin(Node n){
		if(n.getLeft()==null)return n.getRight();
		n.setLeft(deleteMin(n.getLeft()));
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		recursiveBalance(n);//delete 후 left right balance맞추기 
		return n;
	}

	@Override
	public void delete(Key k) {
		root=delete(root,k);
	}
//	private Node delete(Node n, Key k){
//		if(n==null)return null;
//		int t=n.getKey().compareTo(k);
//		if(t>0){
//			n.setLeft(delete(n.getLeft(),k));
//			recursiveBalance(n);
//		}
//		else if(t<0){
//			n.setRight(delete(n.getRight(),k));
//			recursiveBalance(n);
//		}
//		else{
//			if(n.getRight()==null)return n.getLeft();
//			if(n.getLeft()==null)return n.getRight();
//			Node target=n;
//			n=min(target.getRight());
//			n.setRight(delete(n.getRight(),(Key)n.getKey()));
////			n.setRight(deleteMin(target.getRight()));
////			n.setLeft(target.getLeft());
//		}
//		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
//		recursiveBalance(n);
//		return n;
//	}
	private Node delete(Node n, Key k){
		if(n==null)return null;
		int t=n.getKey().compareTo(k);
		if(t>0){
			n.setLeft(delete(n.getLeft(),k));
			recursiveBalance(n);
		}
		else if(t<0){
			n.setRight(delete(n.getRight(),k));
			recursiveBalance(n);
		}
		else{
			if (n.getLeft() == null && n.getRight() == null)
				return null;
			if(n.getLeft()==null)return n.getRight();
			if(n.getRight()==null)return n.getLeft();
			Node target=n;
			n=min(target.getRight());
			n.setRight(deleteMin(target.getRight()));
			n.setLeft(target.getLeft());
			
		}
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		recursiveBalance(n);
		return n;
	}
	@Override
	public String printTree() {
		LinkedList<Node<Key,Value>> myQueue = new LinkedList<Node<Key,Value>>();
		
		myQueue.offer(root);
		
		String returnString = "";
		Node<Key,Value> temp;
		int count = 0;
		int level = 0;
		
		while (level <= height(root)) {
			temp = myQueue.poll();
			if (temp == null) {
				returnString += " ";
				myQueue.offer(null);
				myQueue.offer(null);
			}
			else {
				returnString += " " + temp.getKey().toString();
				myQueue.offer(temp.getLeft());
				myQueue.offer(temp.getRight());
			}
			count++;
			if (count >= Math.pow(2, level)) {
				level++;
				count = 0;
			}
		}
		return returnString;
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		root=null;
	}
}
