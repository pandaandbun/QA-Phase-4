import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Read the previous files
public class ReadPrevious {

	// Old Master and Merged Transaction
	private ArrayList<String> oldMasterBankAccs = new ArrayList<String>();
	private ArrayList<String> mergedTransactions = new ArrayList<String>();

	// Read Master Bank Accs file
	public ArrayList<String> ReadMasterBankAccs(String fileName) {
		BufferedReader bufferReader = null;
		try {
			String strCurrentLine;

			// Read the file "mastertbankaccountfile.txt"
			bufferReader = new BufferedReader(new FileReader(fileName));

			// Parse the file
			while ((strCurrentLine = bufferReader.readLine()) != null) {
				ParseDoc(strCurrentLine, true);
			}

		} catch (IOException e) {
			e.printStackTrace(); // add error when completed
		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
			} catch (IOException ex) {
				ex.printStackTrace(); // Add Error Function when completed
			}
		}

		// Return the file
		return oldMasterBankAccs;
	}

	// Read Merged Transaction File
	public ArrayList<String> ReadMergedTransactions(String fileName) {
		BufferedReader bufferReader = null;
		try {
			String strCurrentLine;

			// Read the file "mergedtransactionsfile.txt"
			bufferReader = new BufferedReader(new FileReader(fileName));

			// Parse the file
			while ((strCurrentLine = bufferReader.readLine()) != null) {
				ParseDoc(strCurrentLine, false);
			}

		} catch (IOException e) {
			e.printStackTrace(); // add error when completed
		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
			} catch (IOException ex) {
				ex.printStackTrace(); // Add Error Function when completed
			}
		}

		// Return the file
		return mergedTransactions;
	}

	// Pare the file
	private void ParseDoc(String strCurrentLine, boolean fromMasterBankAccs) {
		// Master bank acc
		if (fromMasterBankAccs) {
			// Add bank accs except for 00000 which is end of file
			String bankAccNum = strCurrentLine.substring(0, 5);
			if (!bankAccNum.equals("00000")) {
				oldMasterBankAccs.add(strCurrentLine);
			}
		}
		// Merged transactions file
		else {
			mergedTransactions.add(strCurrentLine);
		}
	}
}
