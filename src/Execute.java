
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Execute {
	private static boolean TIME_CHECK = true;
	private static int TEST_CASES_NUM = 2;
	private static WriteFile writer;

	public static void main(String[] args) {
		ReadFile reader = new ReadFile("input.txt");
		writer = new WriteFile("output.txt");
		Execute.executeInstructions(new BST<Integer, String>(), reader.getCases());
//		Execute.executeInstructions(new AVL<Integer, String>(), reader.getCases());
//		Execute.executeInstructions(new RB<Integer, String>(), reader.getCases());
//		Execute.executeInstructions(new LLRB<Integer, String>(), reader.getCases());
//		Execute.executeInstructions(new Splay<Integer, String>(), reader.getCases());
	}

	private static void executeInstructions(CommonMethod<Integer, String> object, ArrayList<LinkedList<ReadFile.InstructionSet>> arrayList) {
		long time_start = 0;
		for (int i = 0; i < TEST_CASES_NUM; i++) {
			LinkedList<ReadFile.InstructionSet> instructionList=arrayList.get(i);
			if (TIME_CHECK) {
				time_start = System.nanoTime();
			}
			Iterator<ReadFile.InstructionSet> it = instructionList.iterator();
			while (it.hasNext()) {
				ReadFile.InstructionSet set = it.next();
				if (set.instruction.equals("g")) {
					String temp=object.get(Integer.parseInt(set.data1));
					if(temp==null)writer.write("fail");
					else writer.write(temp.trim());
				} else if (set.instruction.equals("m")) {
					writer.write(object.get(object.min()).trim());
				} else if (set.instruction.equals("P")) {
					writer.write(object.printTree().trim());
				} else if (set.instruction.equals("p")) {
					object.put(Integer.parseInt(set.data1), set.data2);
				} else if (set.instruction.equals("d")) {
					object.delete(Integer.parseInt(set.data1));
				} else if (set.instruction.equals("D")) {
					object.deleteMin();
				}
			}
			writer.write("\n");
			if (TIME_CHECK) {
				System.out.println(object.toString().split("@")[0] + "의 "+i+"번 째 케이스에서 소요된 시간: " + (System.nanoTime() - time_start)+" ns");
			}
		}
	}
}
