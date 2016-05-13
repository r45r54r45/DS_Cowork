import java.util.LinkedList;
import java.util.Queue;


public class Splay <Key extends Comparable<Key>, Value> extends Testing implements CommonMethod<Key, Value> {
	private Node<Key, Value> root;
	
	public Splay() { 
		testFlag=true;
		root=null;
	}
	
	public int size(){return size(root);}
	private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.getLeft()) + size(x.getRight());
    }
	public boolean isEmpty(){return (size()==0);}
	@Override
	public Value get(Key key) {//g
        root = splay(root, key);
        int t = key.compareTo(root.getKey());
        if (t == 0) return root.getValue();
        else{
        	return null;
        }
    }    
	@Override
	public void put(Key key, Value value) {//p
        if (root == null) {
            root = new Node(key, value);
            test(size()+"");
            return;
        }
		
        root = splay(root, key);
        int t = key.compareTo(root.getKey());
        if (t < 0) {
            Node n = new Node(key, value);
            n.setLeft(root.getLeft());
            n.setRight(root);
            root.setLeft(null);
            root = n;
        }
        else if (t > 0) {
            Node n = new Node(key, value);
            n.setRight(root.getRight());
            n.setLeft(root);
            root.setRight(null);
            root = n;
        }
        else if (t == 0) {
            root.setName(value);
        }
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
		delete(min());
	}
	@Override
	public void delete(Key key) {//d
        if (root == null) return; 
        root = splay(root, key);
        int t = key.compareTo(root.getKey());
        if (t == 0) {
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
        else{
        	test("지우려는게 여기에 없다 ");
        }
    }
	private Node splay(Node h, Key key) {
        if (h == null) return null;
        int t1 = key.compareTo((Key)h.getKey());
        if (t1 < 0) {
            if (h.getLeft() == null){
            	return h;
            	}
            int t2 = key.compareTo((Key)h.getLeft().getKey());
            if (t2 < 0) {
                h.getLeft().setLeft(splay(h.getLeft().getLeft(), key));
                h = rotateRight(h);
            }
            else if (t2 > 0) {
                h.getLeft().setRight(splay(h.getLeft().getRight(), key));
                if (h.getLeft().getRight() != null){
                    h.setLeft(rotateLeft(h.getLeft()));
                }
            }
            if (h.getLeft() == null){return h;}
            else return rotateRight(h);
        }
        else if (t1 > 0) { 
            if (h.getRight() == null){return h;}
            int t2 = key.compareTo((Key)h.getRight().getKey());
            if (t2 < 0) {
                h.getRight().setLeft(splay(h.getRight().getLeft(), key));
                if (h.getRight().getLeft() != null){
                    h.setRight(rotateRight(h.getRight()));
                }
            }
            else if (t2 > 0) {
                h.getRight().setRight(splay(h.getRight().getRight(), key));
                h = rotateLeft(h);
            }
            if (h.getRight() == null){return h;}
            else return rotateLeft(h);
        }
        else{
        	return h;
        }
    }
    private Node rotateRight(Node h) {
        Node x = h.getLeft();
        h.setLeft(x.getRight());
        x.setRight(h);
        return x;
    }
    private Node rotateLeft(Node h) {
        Node x = h.getRight();
        h.setRight(x.getLeft());
        x.setLeft(h);
        return x;
    }

    private int height(Node cur) {
		  if(cur==null) {
		   return -1;
		  }
		  if(cur.getLeft()==null && cur.getRight()==null) {
		   return 0;
		  }
		  return 1 + Math.max(height(cur.getLeft()), height(cur.getRight()));
	}

	@Override
	public String printTree() {//P
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
