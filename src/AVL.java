import java.util.LinkedList;
import java.util.Queue;

public class AVL <Key extends Comparable<Key>, Value> extends Testing implements CommonMethod<Key, Value> {
	private Node root;
	public Node getRoot() {return root;}
	
	
	//현재 노드의 height를 구하기 위한 메소드로써,
	//이는 현재 노드의 자식노드들의 height를 비교한 후,
	//둘 중 큰 height에 +1 함으로써 구해집니다.
	public int max(Node x){
		int left , right;
		if (x.getLeft() == null && x.getRight() == null){
			return 0;
		}
		
		if (x.getLeft() == null) left = 0;
		else left = x.getLeft().getHeight();

		if (x.getRight() == null) right = 0;
		else right = x.getRight().getHeight();	
		return (left > right ? left : right);
	}
	
	
	//왼쪽 노드의 height - 오른쪽노드의 height값을 리턴합니다
	//이를 이용해 현재 노드에서 조정을 해야하는지 아닌지를 알 수 있습니다.
	public int balance(Node root){
		int right, left;
		if (root.getLeft() == null){ left = 0;}
		else {left = root.getLeft().getHeight();}
		
		if (root.getRight() == null){right = 0;}
		else {right = root.getRight().getHeight();}
		
		return left - right;
	}
	
	
	//트리를 avl의 특성에 맞게 조정하는 과정입니다.
	public Node rebalance(Node x){
		
		//왼쪽이 클 때
		if (balance(x) > 1 ){
			if (x.getLeft().getLeft() == null) return this.rotateLeftRight(x);
			if (x.getLeft().getRight() == null) return this.rotateRight(x);
			if (x.getLeft().getLeft().getHeight() > x.getLeft().getRight().getHeight() ) return this.rotateRight(x);
			else return this.rotateRightLeft(x);
		}
		//오른쪽이 더 클 때
		if(balance(x) < -1){
			if (x.getRight().getRight() == null) return this.rotateRightLeft(x);
			if (x.getRight().getLeft() == null)	return this.rotateLeft(x);
			if (x.getRight().getLeft().getHeight() > x.getRight().getRight().getHeight()) return this.rotateLeftRight(x);
			else return this.rotateLeft(x);
		}
		//문제 없을 때 - 그냥 리턴합니다.
		return x;
}
	
	//생성자 
	
	public AVL(Key k, Value v){ 
		root = new Node(k, v, 1);
	}
	public AVL(){ 
		root =null;
	}
		
	public Value get(Key k) {return get(root,k);} // refer to the lecture note.
	
	public Value get(Node x, Key k) {
		if (x == null) return null; // k is not found.
		int t = x.getKey().compareTo(k);
		if (t > 0) return get(x.getLeft(), k); // if k is smaller, go to left
		else if (t < 0) return get(x.getRight(), k);
		else return (Value) x.getValue();
	}
	
	//오른쪽으로 돌려버리기
	public Node rotateRight(Node old){
			Node newNode = old.getLeft();
			old.setLeft(newNode.getRight());
			newNode.setRight(old);
			old.setHeight( max(old) + 1);
			newNode.setHeight( max(newNode) + 1);
			return newNode;
	}
	
	//왼쪽 - 오른쪽으로 돌려버리기
	public Node rotateLeftRight(Node old){
		old.setLeft(this.rotateLeft(old.getLeft()));
		return this.rotateRight(old);
	}
	
	//왼쪽으로 돌려버리기
	public Node rotateLeft(Node old){
		Node newNode = old.getRight();

		old.setRight(newNode.getLeft());
		newNode.setLeft(old);
		
		old.setHeight(max(old)+1);
		newNode.setHeight( max( newNode) + 1);
		
		return newNode;
	}
	
	//오른쪽 - 왼쪽으로 돌려버리기
	public Node rotateRightLeft(Node old){
		old.setRight(this.rotateRight(old.getRight()));
		return this.rotateLeft(old);
	}
	
	public void put(Key k, Value v) {
			root = put(root,k,v);
		} // refer to the lecture note
	
	public Node put(Node x, Key k, Value v){
		//System.out.print("putting ");
		//System.out.println(k);
		if (x == null) {
			return new Node(k, v);
			}
		
		int t = x.getKey().compareTo(k);
		if (t > 0) {
			//System.out.println("left");
			x.setLeft(put(x.getLeft(), k, v));
			}
		else if (t < 0) {
			//System.out.println("Right");
			x.setRight(put(x.getRight(), k, v));
		}
		
		else x.setName(v); //update with v

		return this.rebalance(x);
	}
	
	
	public Key min() { 
		if (root == null) return null;
		return (Key) min(root).getKey();
	} 	
	private Node min(Node x){
		if (x.getLeft() == null) return x;
		return min(x.getLeft());
	}
	
	public void deleteMin() {
		root = deleteMin(root);
	}
	
	public Node deleteMin(Node x){
		
		if(x.getLeft() == null) { return x.getRight();}
		x.setLeft(deleteMin(x.getLeft()));
		return this.rebalance(x);
	
	}
	
	public void delete(Key k) {root = delete(root, k) ;}
	public Node delete(Node x , Key k){
		if (x == null) return null;
		int t = x.getKey().compareTo(k);
		if (t > 0) x.setLeft(delete(x.getLeft(), k));
		else if (t < 0) x.setRight(delete(x.getRight(), k));
		
		else { // found target node to be deleted
			if (x.getRight() == null) return x.getLeft(); // case 0, 1
			if (x.getLeft() == null) return x.getRight(); // \case 1
			Node target = x; // case 2
			x = min(target.getRight());
			x.setRight( deleteMin(target.getRight()));
			x.setLeft(target.getLeft());
		}
		
		
		
		return this.rebalance(x);
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		root=null;
	}


	@Override
	public String printTree(){
		return super.printTree(root);
	}
}
