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

	      //fix up any right leaning links or links with sequential red
	       rbInsertFixup(h);

	       h.setSubTreeSize(1+size(h.getLeft())+size(h.getRight()));
	       return h;   
	       } 

	
	 protected void rbInsertFixup(Node z) {
	    	while (isRed(z.getParent())) {
	        if (z.getParent() == z.getParent().getParent().getLeft()) {
	          // z's parent is a left child.
	          Node y = z.getParent().getParent().getRight();
	          if (isRed(y)) {
	            // Case 1: z's uncle y is red.
//	            System.out.println("rbInsertFixup: Case 1 left");
	            (z.getParent()).setColor(BLACK);
	            y.setColor(BLACK);
	            z.getParent().getParent().setColor(RED);
	            z = z.getParent().getParent();
	          }
	          else {
	            if (z == z.getParent().getRight()) {
	              // Case 2: z's uncle y is black and z is a right child.
//	              System.out.println("rbInsertFixup: Case 2 left");
	              z = z.getParent();
	              rotateLeft(z);
	            }
	            // Case 3: z's uncle y is black and z is a left child.
//	            System.out.println("rbInsertFixup: Case 3 left");
	             z.getParent().setColor(BLACK);
	             z.getParent().getParent().setColor(RED);
	             rotateRight(z.getParent().getParent());
	          }
	        }
	        else {
	          // z's parent is a right child.  Do the same as when z's
	          // parent is a left child, but exchange "left" and "right."
	          Node y =z.getParent().getParent().getLeft();
	          if (isRed(y)) {
	            // Case 1: z's uncle y is red.
//	            System.out.println("rbInsertFixup: Case 1 right");
	            z.getParent().setColor(BLACK);
	            y.setColor(BLACK);
	            z.getParent().getParent().setColor(RED);
	            z = z.getParent().getParent();
	          }
	          else {
	            if (z == z.getParent().getLeft()) {
	              // Case 2: z's uncle y is black and z is a left child.
//	              System.out.println("rbInsertFixup: Case 2 right");
	              z =z.getParent();
	             rotateRight(z);
	            }
	            // Case 3: z's uncle y is black and z is a right child.
//	            System.out.println("rbInsertFixup: Case 3 right");
	            z.getParent().setColor(BLACK);
	            z.getParent().getParent().setColor(RED);
	            rotateLeft(z.getParent().getParent());
	          }
	        }
	      }

	      // The root is always black.
	      root.setColor(BLACK);
	    }
	



	public Node rotateLeft(Node h){
		Node x = h.getRight();
		h.setRight(x.getLeft());
		x.setLeft(h);
		x.setColor(h.getColor());
		h.setColor(RED);
		return x;
		}

	

	public void colorFlip(Node h){
		test("colorFlip");
		h.setColor(!h.getColor());
		//if (h.getLeft() != null)
			h.getLeft().setColor(!h.getLeft().getColor());
		//if (h.getRight() != null)
			h.getRight().setColor(!h.getRight().getColor());
		}


	public boolean isEmpty(){return (size()==0);}

		
		public Node rotateRight(Node h) {
			Node x = h.getLeft();
			h.setLeft(x.getRight());
			x.setRight(h);
			x.setColor(h.getColor());
			h.setColor(RED);
			return x;
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
			//general rb tree의 leaf node 는 다 black 이어야 함
			if ((n.getLeft() == null) && (n.getRight() == null)) n.setColor(BLACK);
		    if (isRed(n.getRight()) && isRed(n.getRight().getRight())) n = rotateLeft(n);
			if(isRed(n.getLeft()) && isRed(n.getLeft().getLeft())) n = rotateRight(n);
			if(isRed(n.getLeft()) && isRed(n.getRight())) colorFlip(n);
			
			return n;
		}


		@Override
		public void deleteMin() {
			test("deleteMin1");
			// TODO Auto-generated method stub
			 root = deleteMin(root);
			 if(root!=null)
			 root.setColor(BLACK); 
			 }
		
		private Node deleteMin(Node h) {
			test("deleteMin2");
			if (h== null) {return null;	}
			if(h.getLeft() == null) {
				h=null; 
				return null;
			}
			if (!isRed(h.getLeft()) && !isRed(h.getLeft().getLeft()))
				h = moveRedLeft(h);
		    h.setLeft(deleteMin(h.getLeft()));
		    //test("fixing up");
		    rbRemoveFixup(h);
		   return h;
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
			test ("딜릿1");
			if(root == null) System.out.println("딜릿 할게 없음");
			else root = delete(root, k); 
			if(root == null) System.out.println("바꿀 색이 없음");
			else root.setColor(BLACK);
			}
		
		private Node delete(Node h, Key k){
			test ("딜릿2");
			if (h == null) return null;
			if (k.compareTo((Key)h.getKey()) < 0)
				if (h.getLeft() != null) delete(h.getLeft(), k);
			else if (k.compareTo((Key)h.getKey()) > 0) 
				if (h.getRight() != null) delete(h.getRight(), k);
			else {delete_fin(h);}

			root.setColor(BLACK);
		       return h;
		       }

		
		public void delete_fin(Node z) { //찾으면 그때 딜리트
		      Node y = z;   // y is the node either removed or moved within the tree
		      boolean yOrigWasBlack = !isRed(y);  // need to know whether y was black
		      Node p;
		      Node x;       // x is the node that will move into y's original position
		      if (z.getLeft() ==null) {       // no left child?
		       p = z.getParent();
		    	x =  z.getRight();
		    	if (p.getLeft() == z) p.setLeft(x);
		    	else p.setRight(x);
		    	// replace z by its right child
		      }
		      else if (z.getRight() == null) { // no right child?
			       p = z.getParent();
		    	  x =z.getLeft();
		    	  if (p.getLeft() == z) p.setLeft(x);
			    else p.setRight(x);
	          // replace z by its left child
		      }
		      else {
		        // Node z has two children.  Its successor y is in its right subtree
		        // and has no left child.
		    	 Node min = min(z.getRight());
		        y = min(z.getRight());
		        yOrigWasBlack = !isRed(y);
		        x = y.getRight();

		        // Splice y out of its current location, and have it replace z.
		        if (y.getParent() == z)
		          x.setParent(y);
		        else {
		          // If y is not z's right child, replace y as a child of its parent by
		          // y's right child and turn z's right child into y's right child.
		        	 p = y.getParent();
		 	    	if (p.getLeft() == y) p.setLeft(x);
		 	    	else p.setRight(x);
		          y.setRight(z.getRight());
		          y.getRight().setParent(y);
		        }

		        // Regardless of whether we found that y was z's right child, replace z as
		        // a child of its parent by y and replace y's left child by z's left child.
		        p = z.getParent();
		    	if (p.getLeft() == z) p.setLeft(y);
		    	else p.setRight(y);
		        y.setLeft(z.getLeft());
		        y.getLeft().setParent(y);

		        // Give y the same color as z.
		        if (!isRed(z))
		         y.setColor(BLACK);
		        else
		          y.setColor(RED);
		      }

		      // If we removed a black node, then must fix up the tree because
		      // black-heights are now incorrect.
		      if (yOrigWasBlack)
		        rbRemoveFixup(x);
		    }
		
		protected void rbRemoveFixup(Node x) {
		      Node w = null;

		      while ((x != root) && !(isRed(x))) {
		    	  if(x.getParent().getLeft() != null)
		          if (x == x.getParent().getLeft()) {
		            w =x.getParent().getRight();
		            if (isRed(w)) {
		              // Case 1: x's sibling w is red.
//		              System.out.println("rbRemoveFixup: Case 1 left");
		              w.setColor(BLACK);
		             x.getParent().setColor(RED);
		             rotateLeft(x.getParent());
		              w =x.getParent().getRight();
		            }
		            if (!isRed(w.getLeft()) && !isRed(w.getRight())) {
		              // Case 2: x's sibling w is black, and both of w's children are black.
//		              System.out.println("rbRemoveFixup: Case 2 left");
		              w.setColor(RED);
		              x =x.getParent();
		            }
		            else {
		              if (!isRed(w.getRight())) {
		                // Case 3: x's sibling w is black, w's left child is red,
		                // and w's right child is black.
//		                System.out.println("rbRemoveFixup: Case 3 left");
		                w.getLeft().setColor(BLACK);
		                w.setColor(RED);
		                rotateRight(w);
		                w = x.getParent().getRight();
		              }
		              // Case 4: x's sibling w is black, and w's right child is red.
//		              System.out.println("rbRemoveFixup: Case 4 left");
		              if (!isRed(x.getParent()))
		                w.setColor(BLACK);
		              else
		                w.setColor(RED);
		              	x.getParent().setColor(BLACK);
		              	w.getRight().setColor(BLACK);
		                rotateLeft(x.getParent());
		                x =root;
		            }
		          }
		          else {
		            w = x.getParent().getLeft();
		            if (isRed(w)) {
		              // Case 1: x's sibling w is red.
//		              System.out.println("rbRemoveFixup: Case 1 right");
		              w.setColor(BLACK);
		              x.getParent().setColor(RED);
		              rotateRight(x.getParent());
		              w = x.getParent().getLeft();
		            }
		            if (!isRed(w.getRight()) && !isRed(w.getLeft())) {
		              // Case 2: x's sibling w is black, and both of w's children are black.
//		              System.out.println("rbRemoveFixup: Case 2 right");
		              w.setColor(RED);
		              x = x.getParent();
		            }
		            else {
		              if (!isRed(w.getLeft())) {
		                // Case 3: x's sibling w is black, w's right child is red,
		                // and w's left child is black.
//		                System.out.println("rbRemoveFixup: Case 3 right");
		                w.getRight().setColor(BLACK);
		                w.setColor(RED);
		                rotateLeft(w);
		                w =x.getParent().getLeft();
		              }
		              // Case 4: x's sibling w is black, and w's left child is red.
//		              System.out.println("rbRemoveFixup: Case 4 right");
		              if ((!isRed(x.getParent())))
		                w.setColor(BLACK);
		              else
		                w.setColor(RED);
		                x.getParent().setColor(BLACK);
		                w.getLeft().setColor(BLACK);
		              	rotateRight(x.getParent());
		              x =root;
		            }
		          }
		        }

		      x.setColor(BLACK);
		    }

		

	public String printTree(){
		return super.printTree(root);
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
	}private int maximum(int a, int b) {
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



	
	}


	
	


