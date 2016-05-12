import java.util.LinkedList;

public class Splay <Key extends Comparable<Key>, Value> extends Testing implements CommonMethod<Key, Value> {
	private Node<Key, Value> root;
	
	public Splay() { }
	
	public int size(){return size(root);}
	public int size(Node<Key, Value> n){
		if(n==null)return 0;
		else return n.getSubTreeSize();
	}
	public boolean isEmpty(){return (size()==0);}
    private void splay(Node node) {
        while (node.getParent()!=null) {
            Node parent = node.getParent();
            if (parent.getParent()==null) {
                if (parent.getLeft() == node) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else {
                Node gparent = parent.getParent();
                if (parent.getLeft() == node && gparent.getLeft() == parent) {
                    rotateRight(gparent);
                    rotateRight(node.getParent());
                } else if (parent.getRight() == node &&
                        gparent.getRight() == parent) {
                    rotateLeft(gparent);
                    rotateLeft(node.getParent());
                } else if (parent.getLeft() == node &&
                        gparent.getRight() == parent) {
                    rotateRight(parent);
                    rotateLeft(node.getParent());
                } else {
                    rotateLeft(parent);
                    rotateRight(node.getParent());
                }
            }
        }
    }
    
    private void rotateLeft(Node x) {
        Node y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else {
            if (x == x.getParent().getLeft()) {
                x.getParent().setLeft(y);
            } else {
                x.getParent().setRight(y);
            }
        }
        y.setLeft(x);
        x.setParent(y);
    }

    private void rotateRight(Node x) {
        Node y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != null) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else {
            if (x == x.getParent().getLeft()) {
                x.getParent().setLeft(y);
            } else {
                x.getParent().setRight(y);
            }
        }
        y.setRight(x);
        x.setParent(y);
    }
    
	
	
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
		search(k);
	}
	private Node put(Node n,Key k,Value v) {
		if(n==null)return new Node<Key,Value>(k,v,1);
		int t=n.getKey().compareTo(k);
		if(t>0){
				n.setLeft(put(n.getLeft(),k,v));
		}else if(t<0){
				n.setRight(put(n.getRight(),k,v));
		}else n.setName(v);
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		return n;
    }
	
	public boolean search(Key key) {
        if (root == null) {
            return false;
        }

        Node node = search(key, root);
        splay(node);
        return node != null;
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
        if (root == null) {
            return;
        }

        search(key);
        delete(key, root);
    }

    private void delete(Key k, Node n) {
    	int t=n.getKey().compareTo(k);
        if (t> 0) {
            if (n.getLeft()!=null) {
                delete(k, n.getLeft());
            }
            if (n.getLeft().isDeleted()) {
                n.setLeft(null);
            }
            return;
        }
        if (t< 0) {
            if (n.getRight()!=null) {
                delete(k, n.getRight());
            }
            if (n.getRight().isDeleted()) {
                n.setRight(null);
            }
            return;
        }

        delete(n);
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
