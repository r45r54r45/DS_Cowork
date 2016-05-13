

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
		recursivebalance(n);
		return n;
	}
	private void Balance(Node cur) {
		  int bal = height(cur.getLeft())-height(cur.getRight());
		  cur.setBalance(bal);
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
		recursivebalance(n); 
		return n;
	}	
	public int balance(Node x){
		int left , right;
		if (x.getLeft() == null && x.getRight() == null){
			return 0;
		}
		
		if (x.getLeft() == null) left = 0;
		else left = x.getLeft().getHeight();

		if (x.getRight() == null) right = 0;
		else right = x.getRight().getHeight();	
		return (left > right ? left : right);
	}
	public Node recursivebalance(Node x){
		if(balance(x) > 1 ){
				if (x.getLeft().getLeft() == null) return this.rotateLR(x);
				if (x.getLeft().getRight() == null) return this.rotateRR(x);
				if (height(x.getLeft().getLeft()) > height(x.getLeft().getRight()) ) return this.rotateRR(x);
				else return this.rotateRL(x);
		}
		if(balance(x) < -1){
			if (x.getRight().getRight() == null) return this.rotateRL(x);
			if (x.getRight().getLeft() == null)	return this.rotateLL(x);
			if (height(x.getRight().getLeft()) >height(x.getRight().getRight())) return this.rotateLR(x);
			else return this.rotateLL(x);
		}
		return x;
	}
	public Node rotateLL(Node old){
		Node newNode = old.getRight();

		old.setRight(newNode.getLeft());
		newNode.setLeft(old);
		balance(old);
		balance(newNode);
		
		return newNode;
	}
	public Node rotateRR(Node old){
		Node newNode = old.getLeft();
		old.setLeft(newNode.getRight());
		newNode.setRight(old);
		old.setBalance( max(old) + 1);
		newNode.setBalance( max(newNode) + 1);
		return newNode;
	}
	public Node rotateLR(Node old){
		old.setLeft(this.rotateLL(old.getLeft()));
		return rotateRR(old);
	}
	
	public Node rotateRL(Node old){
		old.setRight(rotateRR(old.getRight()));
		return rotateLL(old);
	}

	@Override
	public void delete(Key k) {
		root=delete(root,k);
	}
	private Node delete(Node n, Key k){
		if(n==null)return null;
		int t=n.getKey().compareTo(k);
		if(t>0){
			n.setLeft(delete(n.getLeft(),k));
		}
		else if(t<0){
			n.setRight(delete(n.getRight(),k));
		}
		else{
			if(n.getLeft()==null)return n.getRight();
			if(n.getRight()==null)return n.getLeft();
			Node target=n;
			n=min(target.getRight());
			n.setRight(deleteMin(target.getRight()));
			n.setLeft(target.getLeft());
		}
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		recursivebalance(n);
		return n;
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
	public int max(Node x){
		int left , right;
		if (x.getLeft() == null && x.getRight() == null){
			return 0;
		}
		
		if (x.getLeft() == null) left = 0;
		else left = x.getLeft().getHeight();

		if (x.getRight() == null) right = 0;
		else right = x.getRight().getHeight();	
		return (left > right ? left : right);
	}
	
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		root=null;
	}


	@Override
	public String printTree(){
		return super.printTree(root);
	}
}
