import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
Master File Account Files (42 chars + newline)
NNNNN AAAAAAAAAAAAAAAAAAAA S PPPPPPPP TTTT

NNNNN: bank account number (0, 4)
AAAAAAAAAAAAAAAAAAAA: account holder’s name (5,26)
S: bank account status – active (A) or disabled (D) (28,28)
PPPPPPPP: current balance of the account (in Canadian dollars) (30,37)
TTTT: total number of transactions (39,42)

Current Account File
NNNNN AAAAAAAAAAAAAAAAAAAA S PPPPPPPP
Same as master but without TTTT

*/

public class ExportMaster {
    private BufferedWriter out;
    private ArrayList<String> merged_transactions;
    private String dateTime;

    public ExportMaster() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        this.dateTime = localDateTime.format(dtf);
        CreateMasterAndCurrentBankAccountFile();
    }

    private Boolean CreateMasterAndCurrentBankAccountFile() {

        try {
            File f = new File("masterbankaccountfile" + dateTime + ".txt");
            f.createNewFile();

            File file = new File("currentbankaccountfile" + dateTime + ".txt");
            file.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
     * Writes the new transactions to the master bank account. Without overwriting
     * the previous file. The old master bank account file will be retained (the
     * name will have ".old" appended to the end), and the new one will have the
     * date it was created appended to the end of the filename. (In other words, it
     * creates the current bank account file)
     */
    public Boolean Export(ArrayList<String> merged) {
        this.merged_transactions = merged;

        // Master File
        try {
            out = new BufferedWriter(new FileWriter("masterbankaccountfile" + this.dateTime + ".txt"));
            for (String s : merged_transactions) {
                out.write(s + "\n");
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Bank Account File
        try {
            out = new BufferedWriter(new FileWriter("currentbankaccountfile" + dateTime + ".txt"));
            for (String s : merged_transactions) {

                // Print only account not deleted
                String bankStatus = s.substring(27, 28);
                if (!bankStatus.equals("E")) {
                    out.write(s.substring(0, s.length() - 4) + "\n");
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}