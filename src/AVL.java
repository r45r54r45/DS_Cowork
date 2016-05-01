import java.util.Iterator;
import java.util.LinkedList;

public class AVL <Key extends Comparable<Key>, Value> implements CommonMethod<Key, Value> {
	private Node<Key, Value> root;
	
	public Node<Key, Value> getRoot(){return root;}
	public int size(){return size(root);}
	public int size(Node<Key, Value> n){
		if(n==null)return 0;
		else return n.getSubTreeSize();
	}
	public AVL(){
        root = null;
    }
	public AVL(Key k,Value v){
		root=new Node<Key, Value>(k,v,1);
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
		if(n==null)return new Node(k,v,1);
		
		int t=n.getKey().compareTo(k);
		
		if(t>0){
			n.setLeft(put(n.getLeft(),k,v));
			recursiveBalance(n);
		}
		else if(t<0){
			n.setRight(put(n.getRight(),k,v));
			recursiveBalance(n);
		}
		else {
			n.setName(v);
		}
		
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		
		return n;
	}
	public void recursiveBalance(Node cur) {
		  
		  // we do not use the balance in this class, but the store it anyway
		  setBalance(cur);
		  int balance = cur.getBalance();
		  
		  // check the balance
		  if(balance==-2) {
		   
		   if(height(cur.getLeft().getLeft())>=height(cur.getLeft().getRight())) {
		    cur = rotateRight(cur);
		   } else {
		    cur = doubleRotateLeftRight(cur);
		   }
		  } else if(balance==2) {
		   if(height(cur.getRight().getRight())>=height(cur.getRight().getLeft())) {
		    cur = rotateLeft(cur);
		   } else {
		    cur = doubleRotateRightLeft(cur);
		   }
		  }
		  
		  // we did not reach the root yet
		  if(cur.getParent()!=null) {
		   recursiveBalance(cur.getParent());
		  } else {
		   this.root = cur;
		  }
	}
	 public Node rotateLeft(Node n) {
		  
		  Node v = n.getRight();
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
		  
		  setBalance(n);
		  setBalance(v);
		  
		  return v;
	}
	public Node rotateRight(Node n) {
		  
		  Node v = n.getLeft();
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
		  
		  setBalance(n);
		  setBalance(v);
		  
		  return v;
	}
	public Node doubleRotateLeftRight(Node u) {
		  u.setLeft(rotateLeft(u.getLeft()));
		  return rotateRight(u);
	}
	 public Node doubleRotateRightLeft(Node u) {
		  u.setRight(rotateRight(u.getRight()));
		  return rotateLeft(u);
		 }
	private void setBalance(Node cur) {
		  cur.balance = height(cur.getRight())-height(cur.getLeft());
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
	public Key min() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteMin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Key k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String printTree() {
		// TODO Auto-generated method stub
		String result="";
		LinkedList<Node> queue=new LinkedList<>();
		LinkedList<String> resultList=new LinkedList<>();
		levelOrder(root, queue, resultList);
		Iterator<String> it=resultList.iterator();
		while(it.hasNext()){
			result+=(it.next()+" ");
		}
		return result;
	}
	
	private void levelOrder(Node root, LinkedList<Node> queue, LinkedList resultList )
	 {
	  if(root == null)return;

	  if(queue.isEmpty())
	  {
	   resultList.add(root.getKey().toString());
	  }
	  else
	  {
	   resultList.add(queue.getFirst().getKey().toString());
	  }

	  if(root.getLeft() != null)
	  {
	   queue.add(root.getLeft());
	  }
	  if(root.getRight() != null)
	  {
	   queue.add(root.getRight());
	  }
	  levelOrder(root.getLeft(),queue, resultList);
	  levelOrder(root.getRight(),queue,resultList);
	 }
}
