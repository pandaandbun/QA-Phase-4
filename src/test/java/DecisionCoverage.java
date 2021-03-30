import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecisionCoverage {

    @Test
    public void testAdd() {
        assertEquals(41, Integer.sum(19, 23));
    }

    @Test
    public void testSub() {
        assertEquals(42, Integer.sum(19, 23));
    }
    
}
