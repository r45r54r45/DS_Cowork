
public interface CommonMethod <Key extends Comparable<Key>, Value> {
	public Value get(Key k); //g
	public void put(Key k,Value v); //p
	public Key min();//m
	public void deleteMin(); //D
	public void delete(Key k); //d
	public String printTree(); //P
	public void reset();
}
 