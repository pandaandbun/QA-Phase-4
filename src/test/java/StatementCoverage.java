import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Statement Coverage for ValidateConstraints - validateBalance
// validateBalance has 1 Test Case: 
//      T1: Negative Balance - 00361 George Ty            A -39154.9 001
public class StatementCoverage {

    // T1: Test if the method works with negative balance
    @Test
    public void testValidate() {

        String newBankAcc = "00361 George Ty            A -39154.9 001";

        ValidateConstraints validateConstraints = new ValidateConstraints();
        boolean bankAccPassed = validateConstraints.validateBalance("01", newBankAcc);

        assertEquals(bankAccPassed, false);
    }

}
