import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class RB <Key extends Comparable<Key>, Value> implements CommonMethod<Key, Value> {
	 private static final boolean RED   = true; 
	 private static final boolean BLACK = false;
	
	private Node<Key, Value> root;
	public Node<Key, Value> getRoot(){return root;}
	public int size(){return size(root);}
	public int size(Node<Key, Value> n){
		if(n==null)return 0;
		else return n.getSubTreeSize();
	}
	public boolean isRed(Node n){ 
		if (n==null) return false;
		return (n.getColor());
		}
	
	public RB(){
		root = null;
	}
	
	public RB(Key k,Value v){
		root=new Node<Key, Value>(k,v,1);
	}
	
	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
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
		root = put((Node)root, (Key)k, (Value)v);
		root.setColor(BLACK);
	} 
	
	public Node put(Node h, Key k, Value v) {
		// TODO Auto-generated method stub
		   if (h == null) return (new Node<Key, Value>(k, v,1));
	       int cmp = k.compareTo((Key)h.getKey());
	       if(cmp<0)  h.setLeft(put(h.getLeft(), k, v));
	       else if (cmp>0) h.setRight(put(h.getRight(), k, v));
	       else h.setName(v); 
	      //fix up any links with sequential red or child is red
	       if (isRed(h.getRight()) && isRed(h.getRight().getRight())) h = rotateLeft(h);
	       if (isRed(h.getLeft()) && isRed(h.getLeft().getLeft())) h = rotateRight(h);
	       if (isRed(h.getLeft()) && isRed(h.getRight())) colorFlip(h);
	       
	       h.setSubTreeSize(1+size(h.getLeft())+size(h.getRight()));
	       return h;   
	       } 

	public Node rotateLeft(Node h){
		Node x = h.getRight();
		h.setRight(x.getLeft());
		x.setLeft(h);
		x.setColor(h.getColor());
		h.setColor(RED);
		return x;
		}
		
		public Node rotateRight(Node h) {
			Node x = h.getLeft();
			h.setLeft(x.getRight());
			x.setRight(h);
			x.setColor(h.getColor());
			h.setColor(RED);
			return x;
			}

		public boolean isEmpty(){return (size()==0);}

		void colorFlip(Node h){
			h.setColor(!h.getColor());
			h.getLeft().setColor(h.getLeft().getColor());
			h.getRight().setColor(h.getRight().getColor());
			}

		@Override
		public Key min() {
			// TODO Auto-generated method stub
			if (isEmpty()) return null;
			return (Key)min(root).getKey();
		}
		
		private Node min(Node n){
			if (n.getLeft()==null) return n;
			return min(n.getLeft());
		}
		
		private Node fixUp(Node n){
		    if (isRed(n.getRight()) && isRed(n.getRight().getRight())) n = rotateLeft(n);
			if(isRed(n.getLeft()) && isRed(n.getLeft().getLeft())) n = rotateRight(n);
			if(isRed(n.getLeft()) && isRed(n.getRight())) colorFlip(n);
			
			return n;
		}


		@Override
		public void deleteMin() {
			// TODO Auto-generated method stub
			 root = deleteMin(root);
			 root.setColor(BLACK); }
		
		private Node deleteMin(Node h) {
			if (h.getLeft() == null) return null;
			if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
				h = moveRedLeft(h);
		    h.setLeft(deleteMin(h.getLeft()));
		   return fixUp(h); 
		}

		private Node moveRedLeft(Node h){
			colorFlip(h);
			if (isRed(h.getRight().getLeft()))
				{h.setRight(rotateRight(h.getRight()));
				h = rotateLeft(h);
				colorFlip(h);
				}
			return h;   }
		
		private Node moveRedRight(Node h){
			colorFlip(h);
			if (isRed(h.getLeft().getLeft())){
				h = rotateRight(h);
				colorFlip(h);
				}      
			return h;
			}
		
		public void delete(Key k){
			root = delete(root, k);
			root.setColor(BLACK);
			}
		
		private Node delete(Node h, Key k){
			if (k.compareTo((Key)h.getKey()) < 0) delete(h.getLeft(), k);
			else if (k.compareTo((Key)h.getKey()) > 0) delete(h.getRight(), k);
			else delete_one_child(h);

			
		/*	if (k.compareTo((Key)h.getKey()) < 0){              
				if (!isRed(h.getLeft())){
					if(!isRed(h.getLeft().getRight()) &&  !isRed(h.getLeft().getLeft())) h = moveRedLeft(h); 
					if(!isRed(h.getLeft().getRight()) &&  isRed(h.getLeft().getLeft())) h.setLeft(rotateRight(h.getLeft())); 
					if(isRed(h.getLeft().getRight()) &&  !isRed(h.getLeft().getLeft())) h.setLeft(rotateLeft(h.getLeft())); 
					if(isRed(h.getLeft().getRight()) &&  isRed(h.getLeft().getLeft())) colorFlip(h.getLeft());
					}
				h.setLeft(delete(h.getLeft(), k));
				}
			
			else{
				if (isRed(h.getLeft()))   h = rotateRight(h);               
				if (k.compareTo((Key)h.getKey()) == 0 && (h.getRight() == null))  return null;               
				if (!isRed(h.getRight()) && !isRed(h.getRight().getLeft())) h = moveRedRight(h);               
				if (k.compareTo((Key)h.getKey()) == 0) {
					h.setName(get(h.getRight(), (Key)min(h.getRight()).getKey()));
					h.setId(min(h.getRight()).getKey());
					h.setRight(deleteMin(h.getRight())); 
					} 
				else h.setRight(delete(h.getRight(), k));
				}*/
		       return h;
		       }


	@Override
	public String printTree() {
	LinkedList<Node<Key,Value>> myQueue = new LinkedList<Node<Key,Value>>();
		
		myQueue.offer(root);
		
		String returnString = "";
		Node<Key,Value> temp;
		int count = 0;
		int level = 0;
		
		while (level <= root.getSubTreeSize()) {
			temp = myQueue.poll();
			if (temp == null) {
				returnString += " ";
				myQueue.offer(null);
				myQueue.offer(null);
			}
			else {
				returnString +=" "+temp.getKey().toString();
				myQueue.offer(temp.getLeft());
				myQueue.offer(temp.getRight());
			}
			count++;
			if (count >= Math.pow(2, level)) {
				level++;
				count = 0;
			}
		}
		return returnString.substring(1);
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub
		root = null;
	}


	
	public Node sibling (Node n){
		if((n==null) || (n.getParent() == null)) return null; //no parents means no sibling
		if(n==n.getParent().getLeft()) return n.getParent().getRight();
		else return n.getParent().getLeft();
	}
	
	public void delete_one_child(Node n)
	{
	 /*
	  * Precondition: n has at most one non-null child. if it's leaf
	  */
	
	Node child = null;
	if (n.getRight() != null){
		child = new Node(n.getLeft().getKey(), n.getLeft().getValue(), n.getLeft().getSubTreeSize());
	}
	else {child = new Node(n.getRight().getKey(), n.getRight().getValue(), n.getRight().getSubTreeSize());}

	Node tmp = n;
	n = child;
	child = tmp;
	 
	 if (n.getColor() == BLACK) {
	  if (child.getColor() == RED)
	   child.setColor(BLACK);
	  else
	   delete_case1(child);
	 }
	 n = null;
	}
	
	private  void delete_case1(Node n)
	{
	 if (n.getParent() != null)
	  delete_case2(n);
	}
	
	private void delete_case2(Node n)
	{
		Node s = sibling(n);

	 if (s.getColor() == RED) {
	  n.getParent().setColor(RED);
	  s.setColor(BLACK);
	  if (n == n.getParent().getLeft())
	   rotateLeft(n.getParent());
	  else
	   rotateRight(n.getParent());
	 }
	 delete_case3(n);
	}
	
	private void delete_case3(Node n)
	{
	 Node s = sibling(n);

	 if ((n.getParent().getColor() == BLACK) &&
	     (s.getColor() == BLACK) &&
	     (s.getLeft().getColor() == BLACK) &&
	     (s.getRight().getColor() == BLACK)) {
	  s.setColor(RED);
	  delete_case1(n.getParent());
	 } else
	  delete_case4(n);
	}
	
	private void delete_case4(Node n)
	{
		 Node s = sibling(n);

	 if ((n.getParent().getColor() == RED) &&
	     (s.getColor() == BLACK) &&
	     (s.getLeft().getColor() == BLACK) &&
	     (s.getRight().getColor() == BLACK)) {
	  s.setColor(RED);
	  n.getParent().setColor(BLACK);
	 } else
	  delete_case5(n);
	}
	
	void delete_case5(Node n)
	{
		 Node s = sibling(n);

	 if  (s.getColor() == BLACK) { /* this if statement is trivial,
	due to case 2 (even though case 2 changed the sibling to a sibling's child,
	the sibling's child can't be red, since no red parent can have a red child). */
	/* the following statements just force the red to be on the left of the left of the parent,
	   or right of the right, so case six will rotate correctly. */
	  if ((n == n.getParent().getLeft()) &&
	      (s.getRight().getColor() == BLACK) &&
	      (s.getLeft().getColor() == RED)) { /* this last test is trivial too due to cases 2-4. */
	   s.setColor(RED);
	   s.getLeft().setColor(BLACK);
	   rotateRight(s);
	  } else if ((n == n.getParent().getRight()) &&
	             (s.getLeft().getColor() == BLACK) &&
	             (s.getRight().getColor() == RED)) {/* this last test is trivial too due to cases 2-4. */
	   s.setColor(RED);
	   s.getRight().setColor(BLACK);
	   rotateLeft(s);
	  }
	 }
	 delete_case6(n);
	}
	
	void delete_case6(Node n)
	{
		 Node s = sibling(n);

	 s.setColor(n.getParent().getColor());
	 n.getParent().setColor(BLACK);

	 if (n == n.getParent().getLeft()) {
	  s.getRight().setColor(BLACK);
	  rotateLeft(n.getParent());
	 } 
	 else {
	  s.getLeft().setColor(BLACK);
	  rotateRight(n.getParent());
	 }
	}
	
	
	
}



