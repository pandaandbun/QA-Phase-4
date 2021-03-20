import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ErrorLog {
    enum ErrorType {CONSTRAINT, FATAL};
    private TransactionCodes transactionCodes;
    private PrintWriter outErr;
    public ErrorLog() {
        LocalDate date = LocalDate.now(); // Gets current date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Formatter for the date
        String outFile = String.format("%s-%s", "err-log-", formatter.format(LocalDate.now()));

        try {
            outErr = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
        } catch (IOException e) {
            LogException(e);
        }
    }

    // Logs an error to the console and to a file (WIP)
    public void LogError(String errCode, String errString) {
        System.err.println(errCode + " -" + transactionCodes.getCodeDescString(errCode) + " - " + errString);
        outErr.println(errCode + " -" + transactionCodes.getCodeDescString(errCode) + " - " + errString);
    }

    // Log exception to console and file (WIP)
    public void LogException(Exception ex)
    {
        System.err.println(ErrorType.FATAL.name());
        outErr.println(ErrorType.FATAL.name());
        ex.printStackTrace();
        // Also save the exception to a file
        outErr.print(ex.toString());
    }
}
