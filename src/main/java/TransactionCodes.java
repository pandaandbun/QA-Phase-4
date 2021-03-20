import java.util.HashMap;

// hold a list of transaction codes and their description
public class TransactionCodes {

    // Hash map to hold the codes and their description
    private HashMap<String, String> transactions = new HashMap<String, String>();

    // Init the transaction descriptions
    public TransactionCodes() {
        transactions.put("01", "Withdrawal");
        transactions.put("02", "Transfer");
        transactions.put("03", "Paybill");
        transactions.put("04", "Deposit");
        transactions.put("05", "Create");
        transactions.put("06", "Delete");
        transactions.put("07", "Disable");
        transactions.put("08", "ChangePlan");
        transactions.put("00", "End of Session");
    }

    // Get a description that corresponds to the error code
    public String getCodeDescString(String code) {
        return transactions.get(code);
    }
}
