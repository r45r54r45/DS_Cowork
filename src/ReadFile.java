import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ReadFile {
	private FileReader fr;
	private BufferedReader br;
	private int pointer;
	private LinkedList<InstructionSet> dataInCase;
	private ArrayList<LinkedList<InstructionSet>> caseList;
	public ReadFile(String path){
		pointer=0;
		caseList=new ArrayList<>(20);
		read(path);
	}
	public ArrayList<LinkedList<InstructionSet>> getCases(){
		return caseList;
	}
	private void read(String path){
		try{
		fr=new FileReader(path);
		br=new BufferedReader(fr);
		String line;
		while((line=br.readLine())!=null){
//			StringTokenizer st=new StringTokenizer(line, " ");
			//HACK 
			//has problem of not detecting consequent white spaces
			String st[]=line.split(" ");
			dataInCase=new LinkedList<>();
			int i=-1;
			while(i<st.length-1){
				i++;
				String temp=st[i].trim();
				if(temp.equals("g")){
					dataInCase.add(new InstructionSet(temp, st[++i]));
				}else if(temp.equals("p")){
					dataInCase.add(new InstructionSet(temp, st[++i] , st[++i]));
				}else if(temp.equals("d")){
					dataInCase.add(new InstructionSet(temp, st[++i]));
				}else if(temp.equals("m")){
					dataInCase.add(new InstructionSet(temp, null));
				}else if(temp.equals("D")){
					dataInCase.add(new InstructionSet(temp, null));
				}else if(temp.equals("P")){
					dataInCase.add(new InstructionSet(temp, null));
				}else{
					System.out.println("WTF? :  "+temp);
				}
			} 
			caseList.add(dataInCase);
		}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	class InstructionSet{
		public String instruction;
		public String data1;
		public String data2;
		public InstructionSet(String instruction,String data1){
			this.instruction=instruction;
			this.data1=data1;
			this.data2=null;
		}
		public InstructionSet(String instruction,String data1,String data2){
			this.instruction=instruction;
			this.data1=data1;
			this.data2=data2;
		}
	}
}
