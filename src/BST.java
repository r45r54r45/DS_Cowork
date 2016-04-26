
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
	public BST(Key k,Value v){
		root=new Node<Key, Value>(k,v,1);
	}
	public boolean isEmpty(){return (size()==0);}
	public boolean contains(Key k){return get(k)!=null;}
	@Override
	public Value get(Key k) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void put(Key k, Value v) {
		// TODO Auto-generated method stub
		if(root==null)root=new Node<Key, Value>(k,v,1);
		
	}
	@Override
	public Key min() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void deleteMin() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Key k) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String printTree() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int compare(Key x, Key y) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
