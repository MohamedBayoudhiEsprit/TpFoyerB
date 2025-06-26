package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasicTest {
    
    @Test
    void testBasicAssertion() {
        // Simple test that always passes
        String expected = "Hello";
        String actual = "Hello";
        assertEquals(expected, actual);
    }
    
    @Test
    void testNotNull() {
        String value = "test";
        assertNotNull(value);
        assertTrue(value.length() > 0);
    }
}
