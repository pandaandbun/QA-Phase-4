import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

// Decision Coverage for ApplyTransaction - Apply Method
// Test Cases:
// T1: None
// T2: Once
// T3: Twice
// T4: Many
public class LoopCoverage {

    // Old master bank accs and Merge transactions
    ArrayList<String> oldMasterBankAccs = new ArrayList<String>();
    ArrayList<String> mergedTransactions = new ArrayList<String>();
    ArrayList<String> correctMasterBankAccs = new ArrayList<String>();

    // T1
    @Test
    public void testLoopNone() {

        // Correct master bank accs
        correctMasterBankAccs.add("00000 END OF FILE          D 00000.00 000");

        // Apply transaction on to old master bank accs
        ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
        // New master bank account
        ArrayList<String> newMasterBankAccs = apply.Apply();

        // Assert
        assertEquals(correctMasterBankAccs, newMasterBankAccs);
    }

    // T2
    @Test
    public void testLoopOnce() {
        // Correct master bank accs
        correctMasterBankAccs.add("00000 END OF FILE          D 00000.00 000");
        // merged transaction
        mergedTransactions.add("00                      00000 00000.00 00");

        // Apply transaction on to old master bank accs
        ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
        // New master bank account
        ArrayList<String> newMasterBankAccs = apply.Apply();

        // Assert
        assertEquals(correctMasterBankAccs, newMasterBankAccs);
    }

    // T3
    @Test
    public void testLoopTwice() {
        // Correct master bank accs
        correctMasterBankAccs.add("00001 Test Create          A 00000.00 000");
        correctMasterBankAccs.add("00000 END OF FILE          D 00000.00 000");
        // merged transaction
        mergedTransactions.add("05 Test Create          00001 00000.00 00");
        mergedTransactions.add("00                      00000 00000.00 00");

        // Apply transaction on to old master bank accs
        ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
        // New master bank account
        ArrayList<String> newMasterBankAccs = apply.Apply();

        // Assert
        assertEquals(correctMasterBankAccs, newMasterBankAccs);
    }

    // T4
    @Test
    public void testLoopMany() {
        // Correct master bank accs
        correctMasterBankAccs.add("00001 Test Create          A 00079.60 008");
        correctMasterBankAccs.add("00000 END OF FILE          D 00000.00 000");
        // merged transaction
        mergedTransactions.add("05 Test Create          00001 00000.00 00");
        mergedTransactions.add("04 Test Create          00001 00010.00 00");
        mergedTransactions.add("04 Test Create          00001 00010.00 00");
        mergedTransactions.add("04 Test Create          00001 00010.00 00");
        mergedTransactions.add("04 Test Create          00001 00010.00 00");
        mergedTransactions.add("04 Test Create          00001 00010.00 00");
        mergedTransactions.add("04 Test Create          00001 00010.00 00");
        mergedTransactions.add("04 Test Create          00001 00010.00 00");
        mergedTransactions.add("04 Test Create          00001 00010.00 00");
        mergedTransactions.add("00                      00000 00000.00 00");

        // Apply transaction on to old master bank accs
        ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
        // New master bank account
        ArrayList<String> newMasterBankAccs = apply.Apply();

        // Assert
        assertEquals(correctMasterBankAccs, newMasterBankAccs);
    }

}
