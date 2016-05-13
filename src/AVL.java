import java.util.LinkedList;
import java.util.Queue;

public class AVL <Key extends Comparable<Key>, Value> implements CommonMethod<Key, Value> {
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
		Node newNode = old.getLeft();
		old.setLeft(newNode.getRight());
		newNode.setRight(old);
		balance(old);
		balance(newNode);
		return newNode;
	}
	public Node rotateRR(Node old){
		Node newNode = old.getRight();
	
		old.setRight(newNode.getLeft());
		newNode.setLeft(old);
		balance(old);
		balance(newNode);
		return newNode;
	}
	public Node rotateLR(Node old){
		old.setLeft(this.rotateRR(old.getLeft()));
		return rotateLL(old);
	}
	
	public Node rotateRL(Node old){
		old.setRight(rotateLL(old.getRight()));
		return rotateRR(old);
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
	@Override
	public String printTree() {
		String arr[]=new String[64];
		for(int i=0; i<64; i++){
			arr[i]="";
		}
		arr[1]=root.getKey().toString();
		int num=2;
		Queue<Node> q=new LinkedList<Node>();
		q.offer(root);
		while(!q.isEmpty()){
			if(num==64)break;
			Node temp=q.poll();
			if((int)temp.getKey()==-1){
				arr[num++]="";
				arr[num++]="";
				q.offer(new Node(-1,"fake"));
				q.offer(new Node(-1,"fake"));
			}else{
				if(temp.getLeft()!=null){
					arr[num++]=temp.getLeft().getKey().toString();
					q.offer(temp.getLeft());
				}else{
					arr[num++]="";
					q.offer(new Node(-1,"fake"));
				}
				if(temp.getRight()!=null){
					arr[num++]=temp.getRight().getKey().toString();
					q.offer(temp.getRight());
				}else{
					arr[num++]="";
					q.offer(new Node(-1,"fake"));
				}
			}
		}
		
		StringBuilder sb=new StringBuilder();
		for(int i=1; i<64; i++){
			sb.append(arr[i]+" ");
		}
		return sb.toString().trim();
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		root=null;
	}
}
