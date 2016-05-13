import java.util.LinkedList;
import java.util.Queue;

public class Testing {
	protected boolean testFlag;
	public void test(String str){
		if(testFlag)System.out.println(str);
	}
	public String printTree(Node root) {
		int height=root.getHeight()+1;
		int limit;
		if(height>6){
			limit=64;
		}else{
			limit=(int) Math.pow(2, height);
		}
		
		String arr[]=new String[limit];
		for(int i=0; i<limit; i++){
			arr[i]="";
		}
		arr[1]=root.getKey().toString();
		int num=2;
		Queue<Node> q=new LinkedList<Node>();
		q.offer(root);
		while(!q.isEmpty()){
			if(num==limit)break;
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
		for(int i=1; i<limit-1; i++){
			sb.append(arr[i]+" ");
		}
		sb.append(arr[limit-1]);
		return sb.toString();
	}
	
}
