import java.util.ArrayList;
import java.util.Collections;

// Combine merged transaction with old master bank account balance
public class ApplyTransaction {

    // Old master bank accs and Merge transactions
    private ArrayList<String> oldMasterBankAccs;
    private ArrayList<String> mergedTransactions;

    // Validation and transaction codes class
    private ValidateConstraints validateConstraints = new ValidateConstraints();
    private TransactionCodes transactionCodes = new TransactionCodes();
    private ErrorLog errorLog = new ErrorLog();

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
                createBankAcc(transCode, transName, transAccNum, transMisc, mergedTransactions.get(i));
                continue;
            }

            // Find Bank Accounts
            findBankAcc(transCode, transName, transAccNum, transBalance, transMisc);
        }

        // sort bank accounts
        sortBankAcc();

        // return updated bank acc list
        return oldMasterBankAccs;
    }

    // Create New Account
    private void createBankAcc(String transCode, String transName, String transAccNum, String transMisc,
            String transaction) {
        
        boolean bankAccPassed = false;
        int bankAccNumInt = Integer.parseInt(transAccNum);
        String newBankAcc = "";

        // Loop until the new accout have a valid account number
        while (!bankAccPassed) {
            String bankAccNumString = String.format("%5s", bankAccNumInt).replace(' ', '0');
            String bankStatus = "A";
            String bankBalanceString = transaction.substring(30, 38);
            String bankNumOfTransString = "000";
            newBankAcc = bankAccNumString + " " + transName + " " + bankStatus + " " + bankBalanceString + " "
                    + bankNumOfTransString;

            // Validate the new account follow constraints
            bankAccPassed = validateConstraints.validateCreate(transCode, newBankAcc, oldMasterBankAccs);
            bankAccNumInt += 1;
        }

        // add new acc to list
        oldMasterBankAccs.add(0, newBankAcc);
    }

    // Find Bank Account that match with the transaction
    private void findBankAcc(String transCode, String transName, String transAccNum, float transBalance,
            String transMisc) {
        // Loop until bank account is found
        bankAccFound: {
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
                    String transCodeString = transactionCodes.getCodeDescString(transCode);

                    // The actual application
                    // Paybill, Withdrawal
                    if (transCodeString.equals("Paybill") || transCodeString.equals("Withdrawal")) {
                        bankBalance -= transBalance;
                    } 
                    
                    // Deposit
                    else if (transCodeString.equals("Deposit")) {
                        bankBalance += transBalance;
                    } 
                    
                    // Tramsfer
                    else if (transCodeString.equals("Transfer")) {
                        if (transMisc.equals("CR")) {
                            bankBalance -= transBalance;
                        } else if (transMisc.equals("DR")) {
                            bankBalance += transBalance;
                        } else {
                            errorLog.LogError(transCode, "Account " + transAccNum + ": Something wrong with CR/DR");
                        }
                    } 
                    
                    // Disable
                    else if (transCodeString.equals("Disable")) {
                        if (bankStatus.equals("A")) {
                            bankStatus = "C";
                        } else if (bankStatus.equals("B")) {
                            bankStatus = "D";
                        } else {
                            errorLog.LogError(transCode, "Account " + transAccNum + ": Status code is not A or B");
                        }
                    } 
                    
                    // Change Plan
                    else if (transCodeString.equals("ChangePlan")) {
                        if (bankStatus.equals("A")) {
                            bankStatus = "B";
                        } else if (bankStatus.equals("B")) {
                            bankStatus = "A";
                        } else {
                            errorLog.LogError(transCode, "Account " + transAccNum + ": Status code is not A or B");
                        }
                    } 
                    
                    // Delete
                    else if (transCodeString.equals("Delete")) {
                        // mergedTransactions.remove(i);
                        // break;
                        bankStatus = "E";
                    } 
                    
                    // Error
                    else {
                        // System.out.println("ERROR - SOMETHING WRONG HERE");
                        errorLog.LogError(transCode, "Transaction type cant be read");
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
                    boolean bankAccPassed = validateConstraints.validateBalance(transCode, newBankAcc);
                    if (bankAccPassed) {
                        oldMasterBankAccs.set(j, newBankAcc);

                        // Transfer - Find the Account receiving the money
                        // we put the code here to verify that the account transferring is valid before transferring the money
                        if (transCodeString.equals("Transfer") && transMisc.equals("CR")) {
                            findBankAcc(transCode, "", transName.trim(), transBalance, "DR");
                        }
                    }

                    break bankAccFound;
                }
            }

            // If code reach this point, the account was not found
            errorLog.LogError(transCode, "Account " + transAccNum + " Was Not Found");
        }
    }

    // Sort bank accs
    // Add end of file line to master list
    private void sortBankAcc() {
        Collections.sort(oldMasterBankAccs);
        String endOfFile = "00000 END OF FILE          D 00000.00 000";
        oldMasterBankAccs.add(endOfFile);
    }
}
