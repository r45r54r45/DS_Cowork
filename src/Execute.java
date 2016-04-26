
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Execute {
	private static boolean TIME_CHECK = true;

	private static WriteFile writer;

	public static void main(String[] args) {
		ReadFile reader = new ReadFile("input.txt");
		writer = new WriteFile("output.txt");
		Execute.executeInstructions(new BST<Integer, String>(), reader.getCases());
		Execute.executeInstructions(new AVL<Integer, String>(), reader.getCases());
		Execute.executeInstructions(new RB<Integer, String>(), reader.getCases());
		Execute.executeInstructions(new LLRB<Integer, String>(), reader.getCases());
		Execute.executeInstructions(new Splay<Integer, String>(), reader.getCases());
	}

	private static void executeInstructions(CommonMethod<Integer, String> object, ArrayList<LinkedList<ReadFile.InstructionSet>> arrayList) {
		long time_start = 0;
		for (int i = 0; i < 20; i++) {
			LinkedList<ReadFile.InstructionSet> instructionList=arrayList.get(i);
			if (TIME_CHECK) {
				time_start = System.currentTimeMillis();
			}
			Iterator<ReadFile.InstructionSet> it = instructionList.iterator();
			while (it.hasNext()) {
				ReadFile.InstructionSet set = it.next();
				if (set.instruction == "g") {
					writer.write((String) object.get(Integer.parseInt(set.data1)));
				} else if (set.instruction == "m") {
					writer.write((String) object.get(object.min()));
				} else if (set.instruction == "P") {
					writer.write((String) object.printTree());
				}
			}
			writer.write("\n");
			if (TIME_CHECK) {
				System.out.println(object.toString() + "의 "+i+"번 째 케이스에서 소요된 시간: " + (System.currentTimeMillis() - time_start));
			}
		}
	}
}
