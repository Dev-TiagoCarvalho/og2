package dev.online.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeyValuePairTests {

    @Test
    public void testKeyValuePair$Null_Null() {
        KeyValuePair<String, String> valuePair1 = new KeyValuePair<>(null, null);
        KeyValuePair<String, String> valuePair2 = new KeyValuePair<>(null, null);
        assertTrue(valuePair1.equals(valuePair2));
    }

    @Test
    public void testKeyValuePair$Value_Null() {
        KeyValuePair<String, String> valuePair1 = new KeyValuePair<>("Key", null);
        KeyValuePair<String, String> valuePair2 = new KeyValuePair<>(null, null);
        KeyValuePair<String, String> valuePair3 = new KeyValuePair<>("Key", null);
        assertFalse(valuePair1.equals(valuePair2));
        assertFalse(valuePair2.equals(valuePair3));
        assertTrue(valuePair1.equals(valuePair3));
    }

    @Test
    public void testKeyValuePair$Value_Value() {
        KeyValuePair<String, String> valuePair1 = new KeyValuePair<>("Key", "Value");
        KeyValuePair<String, String> valuePair2 = new KeyValuePair<>(null, null);
        KeyValuePair<String, String> valuePair3 = new KeyValuePair<>("Key", "Value");
        KeyValuePair<String, String> valuePair4 = new KeyValuePair<>(null, "Value");
        assertFalse(valuePair1.equals(valuePair2));
        assertFalse(valuePair1.equals(valuePair4));
        assertFalse(valuePair2.equals(valuePair3));
        assertFalse(valuePair2.equals(valuePair4));
        assertFalse(valuePair3.equals(valuePair4));
        assertTrue(valuePair1.equals(valuePair3));
    }

    @Test
    public void testKeyValuePair$Value_Val() {
        KeyValuePair<String, String> valuePair1 = new KeyValuePair<>("Key", "Val");
        KeyValuePair<String, String> valuePair2 = new KeyValuePair<>("Key", "Value");
        assertFalse(valuePair1.equals(valuePair2));
    }
}
