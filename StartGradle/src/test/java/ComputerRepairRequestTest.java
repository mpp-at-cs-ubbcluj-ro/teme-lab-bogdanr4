import ro.mpp.model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComputerRepairRequestTest {
    @Test
    @DisplayName("Test ComputerRepairRequest default constructor")
    public void testComputerRepairRequest() {
        ComputerRepairRequest request = new ComputerRepairRequest();
        assertEquals("", request.getOwnerName());
        assertEquals("", request.getOwnerAddress());
        assertEquals("", request.getPhoneNumber());
    }

    @Test
    @DisplayName("Test ComputerRepairRequest setters")
    public void testComputerRepairRequestSetters() {
        ComputerRepairRequest request = new ComputerRepairRequest();
        request.setID(10);
        assertEquals(10, request.getID());
        request.setOwnerName("ABC");
        assertEquals("ABC", request.getOwnerName());
    }
}
