import java.util.Iterator;
import java.util.LinkedList;

public class BST <Key extends Comparable<Key>, Value> implements CommonMethod<Key, Value>{
	private Node<Key, Value> root;
	public Node<Key, Value> getRoot(){return root;}
	public int size(){return size(root);}
	public int size(Node<Key, Value> n){
		if(n==null)return 0;
		else return n.getSubTreeSize();
	}
	public BST(){
		root=null;
	}
	public void reset(){
		root=null;
	}
	public BST(Key k,Value v){
		root=new Node<Key, Value>(k,v,1);
	}
	public boolean isEmpty(){return (size()==0);}
	public boolean contains(Key k){return get(k)!=null;}
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
	@Override
	public String printTree() {
		StringBuilder result=new StringBuilder();
		LinkedList<Node> queue=new LinkedList<>();
		LinkedList<String> resultList=new LinkedList<>();
		levelOrder(root, queue, resultList);
		Iterator<String> it=resultList.iterator();
		while(it.hasNext()){
			result.append(it.next()+" ");
		}
		return result.toString();
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
