import java.util.ArrayList;

// To run:
// ./gradlew clean      ----> clean the folder
// ./gradlew build      ----> compile
// ./gradlew run --args="masterbankaccountfile.txt mergedtransactionsfile.txt"       ----> run the code
// ./gradlew run --args="oldmasterbankaccountfile.txt mergedtransactionsfile.txt"       ----> run the code

// Input:
// masterbankaccountfile.txt
// mergedtransactionsfile.txt

// Output (Examples):
// masterbankaccountfile2021-03-21.txt
// currentbankaccountfile2021-03-21.txt

// Overall steps:
// 1. Read the input files
// 2. Apply transactions onto bank acc + daily fees
// 3. Create new output files with the changed array

// Status code definition for Master Bank Accounts;
// A - Active Student
// B - Active Non-student
// C - Disable Student
// D - Disable Non-Student
// E - Deleted

public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println(
                    "ERROR - Please pass master and merged. [./gradlew run --args=\"masterbankaccountfile.txt mergedtransactionsfile.txt\"]");
        } else {
            // Read Previous Files
            ReadPrevious readPrevious = new ReadPrevious();
            // Old Master bank acc
            ArrayList<String> oldMasterBankAccs = readPrevious.ReadMasterBankAccs(args[0]);
            // Merged Transaction File
            ArrayList<String> mergedTransactions = readPrevious.ReadMergedTransactions(args[1]);

            // Apply transaction on to old master bank accs
            ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
            // New master bank account
            ArrayList<String> newMasterBankAccs = apply.Apply();

            // Create new Master Bank Account file and Current Bank Account File
            ExportMaster export = new ExportMaster();
            export.Export(newMasterBankAccs);
        }

    }
}