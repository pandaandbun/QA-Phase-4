import java.util.ArrayList;

// Combine merged transaction with old master bank account balance
public class ApplyTransaction {

    // Old master bank accs and Merge transactions
    private ArrayList<String> oldMasterBankAccs;
    private ArrayList<String> mergedTransactions;

    // Validation and transaction codes class
    private ValidateConstraints validateConstraints = new ValidateConstraints();
    private TransactionCodes transactionCodes = new TransactionCodes();

    // Constructor
    public ApplyTransaction(ArrayList<String> oldMasterBankAccs, ArrayList<String> mergedTransactions) {
        this.oldMasterBankAccs = oldMasterBankAccs;
        this.mergedTransactions = mergedTransactions;
    }

    // Apply Transaction on to bank acc
    // Return an updated bank acc array
    public ArrayList<String> Apply() {

        // Transaction
        for (int i = 0; i < mergedTransactions.size(); i++) {

            // Get each transaction info
            String transCode = mergedTransactions.get(i).substring(0, 2);
            String transName = mergedTransactions.get(i).substring(3, 23);
            String transAccNum = mergedTransactions.get(i).substring(24, 29);
            float transBalance = Float.parseFloat(mergedTransactions.get(i).substring(30, 38));
            String transMisc = mergedTransactions.get(i).substring(39, 41);

            // Skip end of line
            if (transAccNum.equals("00000")) {
                continue;
            }

            // to deal with transaction CREATE
            if (transCode.equals("05")) {

                // add new acc to list
                String bankStatus = "A";
                String bankBalanceString = mergedTransactions.get(i).substring(30, 38);
                String bankNumOfTransString = "000";
                String newBankAcc = transAccNum + " " + transName + " " + bankStatus + " " + bankBalanceString + " "
                        + bankNumOfTransString;

                // Validate the new account follow constraints
                boolean bankAccPassed = validateConstraints.validateCreate(newBankAcc, oldMasterBankAccs);
                if (bankAccPassed) {
                    oldMasterBankAccs.add(newBankAcc);
                }

                continue;
            }

            // Old master bank account
            for (int j = 0; j < oldMasterBankAccs.size(); j++) {

                String bankAccNum = oldMasterBankAccs.get(j).substring(0, 5);

                // If account is found
                if (transAccNum.equals(bankAccNum)) {

                    // Get bamk accs info
                    String bankName = oldMasterBankAccs.get(j).substring(6, 26);
                    String bankStatus = oldMasterBankAccs.get(j).substring(27, 28);
                    float bankBalance = Float.parseFloat(oldMasterBankAccs.get(j).substring(29, 37));
                    int bankNumOfTrans = Integer.parseInt(oldMasterBankAccs.get(j).substring(38, 41));

                    // Get the type of transaction
                    String transCodeString = transactionCodes.getCodeDesString(transCode);

                    // The actual application
                    if (transCodeString.equals("Paybill") || transCodeString.equals("Withdrawal")) {
                        bankBalance -= transBalance;
                    } else if (transCodeString.equals("Deposit")) {
                        bankBalance += transBalance;
                    } else if (transCodeString.equals("Transfer")) {
                        if (transMisc.equals("CR")) {
                            bankBalance -= transBalance;
                        } else if (transMisc.equals("DR")) {
                            bankBalance += transBalance;
                        } else {
                            System.err.println("ERROR - TRANSFER - SOMETHING WRONG HERE");
                        }
                    } else if (transCodeString.equals("Disable")) {
                        bankStatus = "D";
                    } else if (transCodeString.equals("Delete")) {
                        // mergedTransactions.remove(i);
                        // break;
                        bankStatus = "E";
                    } else {
                        System.out.println("ERROR - SOMETHING WRONG HERE");
                        break;
                    }

                    // Update number of transaction and daily fee
                    bankNumOfTrans += 1;
                    // Student Plan
                    if (bankStatus.equals("A")) {
                        bankBalance -= 0.05;
                    }
                    // Non student plan
                    else if (bankStatus.equals("B")) {
                        bankBalance -= 0.10;
                    }

                    // update bank acc
                    String bankBalanceString = String.format("%8.2f", bankBalance).replace(' ', '0');
                    String bankNumOfTransString = String.format("%3s", bankNumOfTrans).replace(' ', '0');
                    String newBankAcc = bankAccNum + " " + bankName + " " + bankStatus + " " + bankBalanceString + " "
                            + bankNumOfTransString;

                    // Validate that the update follow constraints
                    boolean bankAccPassed = validateConstraints.validateBalance(newBankAcc);
                    if (bankAccPassed) {
                        oldMasterBankAccs.set(j, newBankAcc);
                    }

                    break;
                }
            }
        }

        // return updated bank acc list
        return oldMasterBankAccs;
    }
}
