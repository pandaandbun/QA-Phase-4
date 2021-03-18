import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ReadPrevious {
    private ArrayList<String> oldMasterBankAccs;
    public BufferedReader bufferReader = null;
    
	public ReadPrevious(ArrayList<String> oldMasterBankAccs) {
		 this.oldMasterBankAccs = oldMasterBankAccs;
	  }
	
	public void ReadFile() {
		  try {
		   String strCurrentLine;
		   bufferReader = new BufferedReader(new FileReader("currentbankaccountfile.txt"));

		   while ((strCurrentLine = bufferReader.readLine()) != null) {
		    System.out.println(strCurrentLine);
		   }

		  } catch (IOException e) {

		   e.printStackTrace(); //add error when completed

		  } finally {

		   try {
		    if (bufferReader != null)
		    	bufferReader.close();
		   } catch (IOException ex) {
		    ex.printStackTrace(); //Add Error Function when completed
		   }
		  }
	  }
	
	static void ParseDoc() {
	  }
	
	
	public void main(String[] args) {
		ReadFile();
	}

}
