import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ErrorLog {
    enum ErrorType {
        CONSTRAINT, FATAL
    };

    private TransactionCodes transactionCodes = new TransactionCodes();
    private PrintWriter outErr;

    public ErrorLog() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formatter for the date
        String outFile = String.format("%s%s%s", "err-log-", formatter.format(LocalDate.now()), ".log");

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
        outErr.flush();
    }

    // Log exception to console and file (WIP)
    public void LogException(Exception ex) {
        System.err.println(ErrorType.FATAL.name());
        // LocalDate date = LocalDate.now(); // Gets current date
        ex.printStackTrace();
        outErr.println(ErrorType.FATAL.name());
        // Also save the exception to a file
        outErr.print(ex.toString());
    }
}
