
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Execute {
	private static boolean TIME_CHECK = true;
	private static int TEST_CASES_NUM = 3;
	private static WriteFile writer;

	public static void main(String[] args) {
		ReadFile reader = new ReadFile("input.txt");
		writer = new WriteFile("output.txt");
		Execute.executeInstructions(new BST<Integer, String>(), reader.getCases());
		Execute.executeInstructions(new AVL<Integer, String>(), reader.getCases());
//		Execute.executeInstructions(new RB<Integer, String>(), reader.getCases());
		Execute.executeInstructions(new LLRB<Integer, String>(), reader.getCases());
//		Execute.executeInstructions(new Splay<Integer, String>(), reader.getCases());
	}
 
	private static void executeInstructions(CommonMethod<Integer, String> object, ArrayList<LinkedList<ReadFile.InstructionSet>> arrayList) {
		long time_start = 0;
		long inst_time_start = 0;
		long inst_count=0;
		long[] inst_total_time=new long[6];
		long[] inst_avr_time=new long[6];
		long[] inst_call=new long[6];
		for (int i = 0; i < TEST_CASES_NUM; i++) {
			LinkedList<ReadFile.InstructionSet> instructionList=arrayList.get(i);
			if (TIME_CHECK) {
				time_start = System.nanoTime();
			}
			Iterator<ReadFile.InstructionSet> it = instructionList.iterator();
			int cnt=0;
			while (it.hasNext()) {
				ReadFile.InstructionSet set = it.next();
				inst_count++;
				if (TIME_CHECK) {
					inst_time_start = System.nanoTime();
				}
				int inst=-1;
				if (set.instruction.equals("g")) {
					inst=0;
					String temp=object.get(Integer.parseInt(set.data1));
					if(temp==null)writer.write("fail");
					else writer.write(temp.trim());
				} else if (set.instruction.equals("m")) {
					inst=1;
					writer.write(object.get(object.min()).trim());
				} else if (set.instruction.equals("P")) {
					inst=2;
//					writer.write(object.printTree());
				} else if (set.instruction.equals("p")) {
					inst=3;
					object.put(Integer.parseInt(set.data1), set.data2);
				} else if (set.instruction.equals("d")) {
					inst=4;
					object.delete(Integer.parseInt(set.data1));
				} else if (set.instruction.equals("D")) {
					inst=5;
					object.deleteMin();
				}
				inst_call[inst]++;
				if (TIME_CHECK) {
					inst_total_time[inst]+=(System.nanoTime()-inst_time_start);
				}
			}
			writer.write("\n");
			if (TIME_CHECK) {
				System.out.println(object.toString().split("@")[0] + "'s "+i+" turn execute time: " + (System.nanoTime() - time_start)+" ns");
			}
			writer.writeFile();
			object.reset();
		}
		if (TIME_CHECK) {
			System.out.println("----------------------------------------------- ");
			System.out.println("| "+object.toString().split("@")[0]+"'s statistic");
			System.out.println("----------------------------------------------- ");
			for(int i=0; i<6; i++){
				String inst="";
				switch(i){
				case 0:
					inst="GET       ";
					break;
				case 1:
					inst="MIN       ";
					break;
				case 2:
					inst="PRINT     ";
					break;
				case 3:
					inst="PUT       ";
					break;
				case 4:
					inst="DELETE    ";
					break;
				case 5:
					inst="DELETEMIN ";
					break;
				}
				System.out.println("| "+inst+" | "+String.format("%6d", inst_total_time[i]/inst_call[i])+" ns");
			}	
			System.out.println("----------------------------------------------- \n");
			
			System.out.println("Total instruction count: "+inst_count);
		}
	}
}
