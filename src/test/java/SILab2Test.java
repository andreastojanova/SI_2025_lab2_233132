import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class SILab2Test {

    // Test for Every Statement criterion
    @Test
    public void everyStatementTest() {
        // Test 1: null list (should throw exception)
        RuntimeException ex1 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(null, "1234567890123456");
        });
        assertEquals("allItems list can't be null!", ex1.getMessage());

        // Test 2 & 4: for loop with valid item with discount and price > 300
        List<Item> items2 = new ArrayList<>();
        items2.add(new Item("validItem", 5, 400, 0.1)); // price > 300, discount > 0
        double result2 = SILab2.checkCart(items2, "1234567890123456");
        // Expected: 400 × (1-0.1) × 5 - 30 = 1770
        assertEquals(1770.0, result2);

        // Test 3: item with invalid name (should throw exception)
        List<Item> items3 = new ArrayList<>();
        items3.add(new Item("", 5, 200, 0)); // empty name
        RuntimeException ex3 = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items3, "1234567890123456");
        });
        assertEquals("Invalid item!", ex3.getMessage());

        // Test 5: invalid card number (should throw exception)
        List<Item> items5 = new ArrayList<>();
        items5.add(new Item("validItem", 5, 200, 0));
        
        // Test with invalid card number length
        RuntimeException ex5a = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items5, "12345"); // too short
        });
        assertEquals("Invalid card number!", ex5a.getMessage());
        
        // Test with invalid card number characters
        RuntimeException ex5b = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items5, "123456789012345A"); // contains letter A
        });
        assertEquals("Invalid character in card number!", ex5b.getMessage());
        
        // Test with null card number
        RuntimeException ex5c = assertThrows(RuntimeException.class, () -> {
            SILab2.checkCart(items5, null);
        });
        assertEquals("Invalid card number!", ex5c.getMessage());
    }

    // Test for Multiple Condition criterion
    @Test
    public void multipleConditionTest() {
        List<Item> items = new ArrayList<>();
        String validCardNumber = "1234567890123456";
        
        // Case 1: A=T (price > 300), B and C are irrelevant
        items.clear();
        items.add(new Item("item1", 5, 400, 0)); // price > 300, discount = 0, quantity <= 10
        double result1 = SILab2.checkCart(items, validCardNumber);
        // Expected: 400 × 5 - 30 = 1970
        assertEquals(1970.0, result1);
        
        // Case 2: A=F, B=T (discount > 0), C is irrelevant
        items.clear();
        items.add(new Item("item2", 5, 200, 0.1)); // price <= 300, discount > 0, quantity <= 10
        double result2 = SILab2.checkCart(items, validCardNumber);
        // Expected: 200 × (1-0.1) × 5 - 30 = 870
        assertEquals(870.0, result2);
        
        // Case 3: A=F, B=F, C=T (quantity > 10)
        items.clear();
        items.add(new Item("item3", 15, 200, 0)); // price <= 300, discount = 0, quantity > 10
        double result3 = SILab2.checkCart(items, validCardNumber);
        // Expected: 200 × 15 - 30 = 2970
        assertEquals(2970.0, result3);
        
        // Case 4: A=F, B=F, C=F (all conditions false)
        items.clear();
        items.add(new Item("item4", 5, 200, 0)); // price <= 300, discount = 0, quantity <= 10
        double result4 = SILab2.checkCart(items, validCardNumber);
        // Expected: 200 × 5 = 1000 (no 30 discount)
        assertEquals(1000.0, result4);
    }
}
