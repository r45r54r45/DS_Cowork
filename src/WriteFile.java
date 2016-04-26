import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
	private String path;
	private FileWriter fw;
	public WriteFile(String path){
		this.path=path;
	}
	public void write(String str){
		try{
			fw=new FileWriter(path,true);
			if(!str.equals("\n"))fw.write(str+" ");
			else fw.write(str);
				fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}		 
	}
}
