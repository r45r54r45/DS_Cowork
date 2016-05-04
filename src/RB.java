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
			if (k.compareTo((Key)h.getKey()) < 0){              
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
				}
		       return fixUp(h);
		       }


	@Override
	public String printTree() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		root = null;
	}

	
	
}



