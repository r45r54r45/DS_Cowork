
public class LLRB <Key extends Comparable<Key>, Value> implements CommonMethod<Key, Value> {
	 private static final boolean RED   = true; 
	 private static final boolean BLACK = false;
	
	private Node<Key, Value> root;
	public Node<Key, Value> getRoot(){return root;}
	public int size(){return size(root);}
	public int size(Node<Key, Value> n){
		if(n==null)return 0;
		else return n.getSubTreeSize();
	}
	
	public LLRB(){
		root=null;
	}
	public LLRB(Key k,Value v){
		root=new Node<Key, Value>(k,v,1);
	}
	 
	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
		 Node x = root;
		 while (x != null){
			 int cmp = k.compareTo((Key)x.getKey());
			 if (cmp == 0) return (Value)x.getValue();
			 else if (cmp < 0) x = x.getLeft(); 
			 else if (cmp > 0) x = x.getRight();
		 }
		return null;
	}

	@Override
	public void put(Key k, Value v) {
		// TODO Auto-generated method stub
		root = put((Node)root, (Key)k, (Value)v);
		root.color = BLACK;
	} 
	
	public void put(Node h, Key k, Value v) {
		// TODO Auto-generated method stub
		   if (h == null) return new Node(key, value);
	       if (isRed(h.getLeft()) && isRed(h.right)) colorFlip(h);
	       int cmp = key.compareTo(h.key);       if (cmp == 0)     h.val = value;       else if (cmp < 0) h.left =  insert(h.left, key, value);       else              h.right = insert(h.right, key, value);
	       if (isRed(h.right) && !isRed(h.left))    h = rotateLeft(h);       if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
	       return h;   } 
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
		return null;
	}

}
