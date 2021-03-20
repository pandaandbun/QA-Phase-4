import java.util.ArrayList;

// A - Active Student
// B - Active Non-student
// C - Disable Student
// D - Disable Non-Student
// E - Deleted

public class Main {

    public static void main(String[] args) {

        // Old Master bank acc
        ArrayList<String> oldMasterBankAccs = new ArrayList<String>();
        oldMasterBankAccs.add("12345 John Smith           A 26648.26 001");
        oldMasterBankAccs.add("56789 John                 B 09714.00 000");
        oldMasterBankAccs.add("54321 Major Cream          A 48566.00 020");
        oldMasterBankAccs.add("54320 Test Constraint      B 00001.00 000");

        // Merge Transaction file
        ArrayList<String> mergedTransactions = new ArrayList<String>();
        mergedTransactions.add("06 Major Cream          54321 00010.50 CR");
        mergedTransactions.add("02 John                 56789 00001.00 CR");
        mergedTransactions.add("02                      54321 00001.00 DR");
        mergedTransactions.add("02 Test Constraint      54320 00002.00 CR");
        mergedTransactions.add("05 Test Constraint      54320 00000.00 00");
        mergedTransactions.add("00                      00000 00000.00 00");

        // apply transaction on to old master bank accs
        ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
        // New master bank account
        ArrayList<String> newMasterBankAccs = apply.Apply();

        System.out.println(newMasterBankAccs);

        ExportMaster export = new ExportMaster();
        export.Export(newMasterBankAccs);

    }

}