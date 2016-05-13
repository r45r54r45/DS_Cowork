import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class RB <Key extends Comparable<Key>, Value> extends Testing implements CommonMethod<Key, Value> {
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
		testFlag = true;
		root = null;
	}
	
	public RB(Key k,Value v){
		testFlag = true;
		root=new Node<Key, Value>(k,v,1);
	}
	
	@Override
	public Value get(Key k) {
		//test("get1");
		if (root == null) {test("줄게 없어여!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1트리비어있음"); }
		else {
			test("줌");
		}
		return get(root,k);
	}
	private Value get(Node n, Key k){
		//test("get2");
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>" + n.getValue());
		if(n==null) {test("줄게 없어여!!!!!!!!!!!!!!!!!!!!!!!!!!!");return null;} 
		int t=n.getKey().compareTo(k);
		
		if(t>0)return get(n.getLeft(),k);
		else if(t<0)return get(n.getRight(),k);
		else{
		//test("여기");
		return (Value)n.getValue();}
	}

	@Override
	public void put(Key k, Value v) {
		//test("put1");
		// TODO Auto-generated method stub
		root = put((Node)root, (Key)k, (Value)v);
		root.setColor(BLACK);
	} 
	
	public Node put(Node h, Key k, Value v) {
		//test("put2");
		// TODO Auto-generated method stub
		   if (h == null) return (new Node<Key, Value>(k, v,1)); //없다고 null 주면 잣됨
	       int cmp = k.compareTo((Key)h.getKey());
	       if(cmp<0)  h.setLeft(put(h.getLeft(), k, v));
	       else if (cmp>0) h.setRight(put(h.getRight(), k, v));
	       else h.setName(v); 
	      //fix up any right leaning links or links with sequential red
	       if (isRed(h.getRight()) && !isRed(h.getLeft())) h = rotateLeft(h);
	       if (isRed(h.getLeft()) && isRed(h.getLeft().getLeft())) h = rotateRight(h);
	       if (isRed(h.getLeft()) && isRed(h.getRight())) colorFlip(h);
	       
	       h.setSubTreeSize(1+size(h.getLeft())+size(h.getRight()));
			//if (h!= null)System.out.println(">>>>>>>>>putputput>>>>>>>>>>>>" + h.getValue());
	       return h;   
	       } 
	
	public Node rotateLeft(Node h){
	//test("rotateLeft");
	Node x = h.getRight();
	if(h==null) return null; //////////////////맞나
	h.setRight(x.getLeft());
	x.setLeft(h);
	x.setColor(h.getColor());
	h.setColor(RED);
	  return x;
	}
	
	public Node rotateRight(Node h) {
		//test("rotateRight");
		Node x = h.getLeft();
		if(h==null) return null; //////////////////맞나
		h.setLeft(x.getRight());
		x.setRight(h);
		x.setColor(h.getColor());
		h.setColor(RED);
		return x;
		}
	

	public void colorFlip(Node h){
		//test("colorFlip");
		h.setColor(!h.getColor());
		//if (h.getLeft() != null)
			h.getLeft().setColor(!h.getLeft().getColor());
		//if (h.getRight() != null)
			h.getRight().setColor(!h.getRight().getColor());
		}


	public boolean isEmpty(){return (size()==0);}



	public Key min() {
		//test("first min1");
		// TODO Auto-generated method stub
		if (isEmpty()) return null;
		else 
		return (Key)min(root).getKey();
	}
	
	private Node min(Node n){
		//test("min2");
		if (n==null) return null;
		while (n.getLeft()!=null)
			n = n.getLeft();
		return n;
	}
		private Node fixUp(Node n){
			if (isRed(n.getRight()) && isRed(n.getRight().getRight())) n = rotateLeft(n);
			if(isRed(n.getLeft()) && isRed(n.getLeft().getLeft())) n = rotateRight(n);
			if(isRed(n.getLeft()) && isRed(n.getRight())) colorFlip(n);
			
			return n;
		}


		@Override
		public void deleteMin() {
			//test("딜민1");
			// TODO Auto-generated method stub
			if(root!=null) root = deleteMin(root);
			if(root!=null) root.setColor(BLACK); 
		}
		
		private Node deleteMin(Node h) {
			//test("딜민2");
			if (h== null) {return null;	}
			if(h.getLeft() == null) {
				h=null; 
				return null;
			}
			if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
				h = moveRedLeft(h);
		    h.setLeft(deleteMin(h.getLeft()));
		    test("fixing up");
		   return fixUp(h); 
		}

		private Node moveRedLeft(Node h){
			test("moveRedLeft");
			colorFlip(h);
			if (h != null) {
				if (h.getRight() != null) {
					if (isRed(h.getRight().getLeft()))
						{h.setRight(rotateRight(h.getRight()));
						h = rotateLeft(h);
						colorFlip(h);
						}
					}
			}
			return h;   }
		
		private Node moveRedRight(Node h){
			//test("moveRedRight");
		
			colorFlip(h);
			if (h !=null){
				if (h.getLeft() != null) {
					if (isRed(h.getLeft().getLeft())){
						h = rotateRight(h);
						colorFlip(h);
						} 
				}
			}
			return h;
			}

		
		public void delete(Key k){
			//test ("딜릿1");
			if(root == null) System.out.println("딜릿 할게 없음");
			else root = delete(root, k);
			if(root == null) System.out.println("바꿀 색이 없음");
			else root.setColor(BLACK);
			}
		
		private Node delete(Node h, Key k){
			//test ("딜릿2");
			if (h == null) return null;
			if (k.compareTo((Key)h.getKey()) < 0)
				if (h.getLeft() != null) delete(h.getLeft(), k);
			else if (k.compareTo((Key)h.getKey()) > 0) 
				if (h.getRight() != null) delete(h.getRight(), k);
			else {delete_one_child(h);}
		       return h;
		       }


	@Override
	public String printTree() {
LinkedList<Node<Key,Value>> myQueue = new LinkedList<Node<Key,Value>>();
		
		myQueue.offer(root);
		StringBuilder sb=new StringBuilder();
		Node<Key,Value> temp;
		int count = 0;
		int level = 0;
		
		while (level <= height(root)) {
			temp = myQueue.poll();
			if (temp == null) {
				sb.append(" ");
				myQueue.offer(null);
				myQueue.offer(null);
			}
			else {
				sb.append(" "+temp.getKey().toString());
				myQueue.offer(temp.getLeft());
				myQueue.offer(temp.getRight());
			}
			count++;
			if (count >= Math.pow(2, level)) {
				level++;
				count = 0;
			}
		}
		return sb.toString().substring(1);
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
	test (" 한 노드를 삭제한다!!!!!!!!!!!!!!!!!!!!!");
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
	
}



