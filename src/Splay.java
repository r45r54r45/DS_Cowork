import java.util.LinkedList;


public class Splay <Key extends Comparable<Key>, Value> extends Testing implements CommonMethod<Key, Value> {
	private Node<Key, Value> root;
	
	public Splay() { }
	
	public int size(){return size(root);}
	private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.getLeft()) + size(x.getRight());
    }
	public boolean isEmpty(){return (size()==0);}
	public boolean contains(Key key) {
        return get(key) != null;
    }
	@Override
	public Value get(Key key) {
        root = splay(root, key);
        int cmp = key.compareTo(root.getKey());
        if (cmp == 0) return root.getValue();
        else          return null;
    }    
	@Override
	public void put(Key key, Value value) {
        // splay key to root
        if (root == null) {
            root = new Node(key, value);
            return;
        }
        
        root = splay(root, key);

        int cmp = key.compareTo(root.getKey());
        
        // Insert new node at root
        if (cmp < 0) {
            Node n = new Node(key, value);
            n.setLeft(root.getLeft());
            n.setRight(root);
            root.setLeft(null);
            root = n;
        }

        // Insert new node at root
        else if (cmp > 0) {
            Node n = new Node(key, value);
            n.setRight(root.getRight());
            n.setLeft(root);
            root.setRight(null);
            root = n;
        }

        // It was a duplicate key. Simply replace the value
        else if (cmp == 0) {
            root.setName(value);
        }

    }

    private Node search(Key key, Node node) {
        if (key == node.getKey()) {
            return node;
        }
        int t=node.getKey().compareTo(key);
        if (t > 0) {
            if (node.getLeft()==null) {
                return null;
            }
            return search(key, node.getLeft());
        }

        if (t <0 ) {
            if (node.getRight()==null) {
                return null;
            }
            return search(key, node.getRight());
        }

        return null;
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
	private Key findMin(Node n) {
        if (n.getLeft()==null) {
            n.setDeleted(true);
            return (Key)n.getKey();
        }

        Key min = findMin(n.getLeft());
        if (n.getLeft().isDeleted()) {
            n.setLeft(null);
        }
        return min;
    }

	@Override
	public void deleteMin() {
		delete(findMin(root));
	}

	@Override
	public void delete(Key key) {
        if (root == null) return; // empty tree
        
        root = splay(root, key);

        int cmp = key.compareTo(root.getKey());
        
        if (cmp == 0) {
            if (root.getLeft() == null) {
                root = root.getRight();
            } 
            else {
                Node x = root.getRight();
                root = root.getLeft();
                splay(root, key);
                root.setRight(x);
            }
        }

        // else: it wasn't in the tree to remove
    }
	private Node splay(Node h, Key key) {
        if (h == null) return null;

        int cmp1 = key.compareTo((Key)h.getKey());

        if (cmp1 < 0) {
            // key not in tree, so we're done
            if (h.getLeft() == null) {
                return h;
            }
            int cmp2 = key.compareTo((Key)h.getLeft().getKey());
            if (cmp2 < 0) {
                h.getLeft().setLeft(splay(h.getLeft().getLeft(), key));
                h = rotateRight(h);
            }
            else if (cmp2 > 0) {
                h.getLeft().setRight(splay(h.getLeft().getRight(), key));
                if (h.getLeft().getRight() != null)
                    h.setLeft(rotateLeft(h.getLeft()));
            }
            
            if (h.getLeft() == null) return h;
            else                return rotateRight(h);
        }

        else if (cmp1 > 0) { 
            // key not in tree, so we're done
            if (h.getRight() == null) {
                return h;
            }

            int cmp2 = key.compareTo((Key)h.getRight().getKey());
            if (cmp2 < 0) {
                h.getRight().setLeft(splay(h.getRight().getLeft(), key));
                if (h.getRight().getLeft() != null)
                    h.setRight(rotateRight(h.getRight()));
            }
            else if (cmp2 > 0) {
                h.getRight().setRight(splay(h.getRight().getRight(), key));
                h = rotateLeft(h);
            }
            
            if (h.getRight() == null) return h;
            else                 return rotateLeft(h);
        }

        else return h;
    }
	public int height() { return height(root); }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.getLeft()), height(x.getRight()));
    }

    
    
    // right rotate
    private Node rotateRight(Node h) {
        Node x = h.getLeft();
        h.setLeft(x.getRight());
        x.setRight(h);
        return x;
    }

    // left rotate
    private Node rotateLeft(Node h) {
        Node x = h.getRight();
        h.setRight(x.getLeft());
        x.setLeft(h);
        return x;
    }

    private void delete(Node n) {
        if (!(n.getLeft()!=null || n.getRight()!=null)) {
            n.setDeleted(true);
            return;
        }

        if (n.getLeft()!=null && n.getRight()==null) {
            n.setId(n.getLeft().getKey());
            if (n.getLeft().getRight()!=null) {
                n.setRight(n.getLeft().getRight());
            }
            if (n.getLeft().getLeft()!=null) {
                n.setLeft(n.getLeft().getLeft());
            } else {
                n.setLeft(null);
            }
            return;
        }

        if (n.getRight()!=null && n.getLeft()==null) {
            n.setId(n.getRight().getKey());
            if (n.getRight().getLeft()!=null) {
                n.setLeft(n.getLeft().getLeft());
            }
            if (n.getRight().getRight()!=null) {
                n.setRight(n.getLeft().getRight());
            } else {
                n.setRight(null);
            }
            return;
        }
        Key min = findMin(n.getRight());
        n.setId(min);
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
		root=null;
	}

}
