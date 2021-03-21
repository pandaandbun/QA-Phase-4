import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ReadPrevious {
    public static  ArrayList<String> oldMasterBankAccs = new ArrayList<String>(); 
	public ReadPrevious(ArrayList<String> oldMasterBankAccs) {
		 this.oldMasterBankAccs = oldMasterBankAccs;
	  }
	
	public static void ReadFile() {
	     BufferedReader bufferReader = null;
		  try {
		   String strCurrentLine;
		   int i = 0;
		   String array[] = null;
		   bufferReader = new BufferedReader(new FileReader("mastertbankaccountfile.txt"));

		   while ((strCurrentLine = bufferReader.readLine()) != null) {
			    ParseDoc(strCurrentLine,array);
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
	
	static void ParseDoc(String strCurrentLine, String[] array) {
	    array = strCurrentLine.split("\\s+");
	    System.out.println(Arrays.toString(array));	
	  }
}

