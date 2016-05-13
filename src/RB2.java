import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class RB2 <Key extends Comparable<Key>, Value> extends Testing implements CommonMethod<Key, Value> {
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
	
	public RB2(){
		testFlag = true;
		root=null;
	}
	public RB2(Key k,Value v){
		testFlag = true;
		root=new Node<Key, Value>(k,v,1);
	}
	
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
		test("여기");
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
	      //fix up links with sequential red or leaf nodes being red
	       if (isRed(h.getRight()) && isRed(h.getRight().getLeft())) h = rotateLeft(h);
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
		 h.getLeft().setColor(!h.getLeft().getColor());
		h.getRight().setColor(!h.getRight().getColor());
		}


	public boolean isEmpty(){return (size()==0);}

	@Override
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
		//test("fixUp");
		if(isRed(n.getRight())) n = rotateLeft(n);
		if(isRed(n.getLeft()) && isRed(n.getLeft().getLeft())) n = rotateRight(n);
		if(isRed(n.getLeft()) && isRed(n.getRight())) colorFlip(n);
		
		return n;
	}

	@Override
	public void deleteMin() {
		//test("deleteMin1");
		// TODO Auto-generated method stub
		if(root!=null) root = deleteMin(root);
		if(root!=null) root.setColor(BLACK); 
	}
	
	private Node deleteMin(Node h) {
		//test("deleteMin2");
		if (h== null) {return null;	}
		if(h.getLeft() == null) {
			h=null; 
			return null;
		}
		if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
			h = moveRedLeft(h);
	    h.setLeft(deleteMin(h.getLeft()));
	   return fixUp(h); 
	}
	
	private Node moveRedLeft(Node h){
		//test("moveRedLeft");
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
		//test("delete1");
		if(root == null) System.out.println("딜릿 할게 없음");
		else root = delete(root, k);
		if(root == null) System.out.println("바꿀 색이 없음");
		else root.setColor(BLACK);
		}
	
	private Node delete(Node h, Key k){
		//test("delete2");
		Node tmp;
	if (h == null) return null;
		if (k.compareTo((Key)h.getKey()) < 0){              
			if (h.getLeft() != null){
				if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
					h = moveRedLeft(h);
				h.setLeft(delete(h.getLeft(), k));
			}
			}
		else{
			if (isRed(h.getLeft()))   h = rotateRight(h);               
			if (k.compareTo((Key)h.getKey()) == 0 && (h.getRight() == null)) {
				h =null;
				return null;               
			}
			if (h.getRight() != null){
				if (!isRed(h.getRight()) && !isRed(h.getRight().getLeft())) 
					h = moveRedRight(h);}   
			// 현재 레드노드가 정답이 거나 작은데, 오른쪽에 노드는 무조건 있음. 
				if (k.compareTo((Key)h.getKey()) == 0) {
					tmp = min(h.getRight());
					h.setName(tmp.getValue());
					h.setId(tmp.getKey());
					h.setRight(deleteMin(h.getRight()));
					/*h.setName(get(h.getRight(), (Key)min(h.getRight()).getKey()));
					h.setId(min(h.getRight()).getKey());
					h.setRight(deleteMin(h.getRight()));*/ 
					} 
			else h.setRight(delete(h.getRight(), k));
			}
	       return fixUp(h);
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



	public void reset() {
		// TODO Auto-generated method stub
		root=null;
	}

}



