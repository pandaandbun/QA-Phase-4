import java.util.ArrayList;

// Validate the accounts follow constraints
// Return true if follow, false if it does not 
public class ValidateConstraints {

    // check if balance is negative
    public boolean validateBalance(String acc) {

        // get account info
        String bankAccNum = acc.substring(0, 5);
        String bankBalance = acc.substring(29, 37);

        // check if balance is negative
        if (bankBalance.contains("-")) {
            System.err.printf("ERROR - CONSTRAINT - %s BALANCE BELOW 0\n", bankAccNum);
            return false;
        }

        return true;
    }

    // Check if acc is unique
    public boolean validateCreate(String acc, ArrayList<String> currentMasterBankAccs) {

        // get account info
        String bankAccNum = acc.substring(0, 5);

        // check if account is unique
        for (int i = 0; i < currentMasterBankAccs.size(); i++) {

            // Get other bank account info
            String otherBankAccNum = currentMasterBankAccs.get(i).substring(0, 5);

            if (bankAccNum.equals(otherBankAccNum)) {
                System.err.printf("ERROR - CONSTRAINT - %s ACCOUNT IS NOT UNIQUE\n", bankAccNum);
                return false;
            }

        }

        return true;
    }
}
