import java.util.LinkedList;
import java.util.Queue;
public class BST <Key extends Comparable<Key>, Value> extends Testing implements CommonMethod<Key, Value> {
	private Node<Key, Value> root;
	public Node<Key, Value> getRoot(){return root;}
	public int size(){return size(root);}
	public int size(Node<Key, Value> n){
		if(n==null)return 0;
		else return n.getSubTreeSize();
	}
	public BST(){
		root=null;
		testFlag=false;
	}
	
	public BST(Key k,Value v){
		root=new Node<Key, Value>(k,v,1);
	}
	public boolean isEmpty(){return (size()==0);}
	public boolean contains(Key k){return get(k)!=null;}
	@Override
	public Value get(Key k) {
		test("put your syso test message here");
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
		if(t>0)n.setLeft(put(n.getLeft(),k,v));
		else if(t<0)n.setRight(put(n.getRight(),k,v));
		else n.setName(v);
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		return n;
	}
	@Override
	public Key min() {
		if(isEmpty())return null;
		return (Key)min(root).getKey();
	}
	private Node min(Node n){
		if(n.getLeft()==null)return n;
		return min(n.getLeft());
	}
	@Override
	public void deleteMin() {
		if(!isEmpty())root=deleteMin(root);
	}
	private Node deleteMin(Node n){
		if(n.getLeft()==null)return n.getRight();
		n.setLeft(deleteMin(n.getLeft()));
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		return n;
	}
	@Override
	public void delete(Key k) {
		root=delete(root,k);
	}
	private Node delete(Node n, Key k){
		if(n==null)return null;
		int t=n.getKey().compareTo(k);
		if(t>0)n.setLeft(delete(n.getLeft(),k));
		else if(t<0)n.setRight(delete(n.getRight(),k));
		else{
			if(n.getRight()==null)return n.getLeft();
			if(n.getLeft()==null)return n.getRight();
			Node target=n;
			n=min(target.getRight());
			n.setRight(deleteMin(target.getRight()));
			n.setLeft(target.getLeft());
		}
		n.setSubTreeSize(1+size(n.getLeft())+size(n.getRight()));
		return n;
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
	public void reset(){
		root=null;
	}
}
