import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

// Decision Coverage for ApplyTransaction - Apply Method
// Test Cases:
// T1: EOF True
// T2: EOF False
// T3: Create True
// T4: Create False
public class DecisionCoverage {

    // Old master bank accs and Merge transactions
    ArrayList<String> oldMasterBankAccs = new ArrayList<String>();
    ArrayList<String> mergedTransactions = new ArrayList<String>();
    ArrayList<String> correctMasterBankAccs = new ArrayList<String>();

    // T1
    @Test
    public void testEOFTrue() {

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

    // T2
    @Test
    public void testEOFFalse() {
        // Correct master bank accs
        correctMasterBankAccs.add("00000 END OF FILE          D 00000.00 000");
        // merged transaction
        mergedTransactions.add("00                      00001 00000.00 00");

        // Apply transaction on to old master bank accs
        ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
        // New master bank account
        ArrayList<String> newMasterBankAccs = apply.Apply();

        // Assert
        assertEquals(correctMasterBankAccs, newMasterBankAccs);
    }

    // T3
    @Test
    public void testCreateTrue() {
        // Correct master bank accs
        correctMasterBankAccs.add("00001 Test Create          A 00000.00 000");
        correctMasterBankAccs.add("00000 END OF FILE          D 00000.00 000");
        // merged transaction
        mergedTransactions.add("05 Test Create          00001 00000.00 00");

        // Apply transaction on to old master bank accs
        ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
        // New master bank account
        ArrayList<String> newMasterBankAccs = apply.Apply();

        // Assert
        assertEquals(correctMasterBankAccs, newMasterBankAccs);
    }

    // T4
    @Test
    public void testCreateFalse() {
        // Correct master bank accs
        correctMasterBankAccs.add("00000 END OF FILE          D 00000.00 000");
        // merged transaction
        mergedTransactions.add("04 Test Create          00001 00000.00 00");

        // Apply transaction on to old master bank accs
        ApplyTransaction apply = new ApplyTransaction(oldMasterBankAccs, mergedTransactions);
        // New master bank account
        ArrayList<String> newMasterBankAccs = apply.Apply();

        // Assert
        assertEquals(correctMasterBankAccs, newMasterBankAccs);
    }

}
