
public interface CommonMethod <Key extends Comparable<Key>, Value> {
	public Value get(Key k);
	public void put(Key k,Value v);
	public Key min();
	public void deleteMin();
	public void delete(Key k);
	public String printTree();
}
