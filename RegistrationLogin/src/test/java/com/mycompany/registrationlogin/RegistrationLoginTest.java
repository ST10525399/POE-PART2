package com.mycompany.registrationlogin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationLoginTest {

    private LoginClass auth;
    private Message msgTool;

    @BeforeEach
    public void setUp() {
        auth = new LoginClass();
        msgTool = new Message();
    }

    // ==========================================
    // 1. AUTHENTICATION & LOGIN UNIT TESTS
    // ==========================================
    @Test
    public void testCheckUserName_Null() {
        assertFalse(auth.checkUserName(null));
    }

    @Test
    public void testCheckUserName_Valid() {
        assertTrue(auth.checkUserName("k_12"));
    }

    @Test
    public void testCheckUserName_Invalid() {
        assertFalse(auth.checkUserName("abc_de")); // Too long
        assertFalse(auth.checkUserName("abcde"));  // No underscore
    }

    @Test
    public void testCheckPasswordComplexity() {
        assertFalse(auth.checkPasswordComplexity(null));
        assertTrue(auth.checkPasswordComplexity("Ch@llenge2026"));
        assertFalse(auth.checkPasswordComplexity("short"));
    }

    @Test
    public void testLoginValidationBranches() {
        assertFalse(auth.loginUser("k_12", "Ch@llenge2026")); // No reg state
        auth.registerUser("k_12", "Ch@llenge2026", "+27123456789");
        assertTrue(auth.loginUser("k_12", "Ch@llenge2026"));
        assertFalse(auth.loginUser("k_12", "wrongpass"));
    }

    @Test
    public void testReturnLoginStatus() {
        assertEquals("Welcome John Doe, it is great to see you again.", auth.returnLoginStatus(true, "John", "Doe"));
        assertEquals("Username or password incorrect, please try again.", auth.returnLoginStatus(false, "John", "Doe"));
    }

    // ==========================================
    // 2. ASSIGNMENT TASK 2 SPECIFIC UNIT TESTS
    // ==========================================
    
    @Test
    public void testCheckMessageLength_Success() {
        String testMsg = "Hi Mike, can you join us for dinner tonight?";
        String result = msgTool.checkMessageLength(testMsg);
        assertEquals("Message ready to send", result);
    }

    @Test
    public void testCheckMessageLength_Failure() {
        // Build a string that exceeds 250 characters
        StringBuilder longMsg = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            longMsg.append("This is a long message segment. ");
        }
        String result = msgTool.checkMessageLength(longMsg.toString());
        assertTrue(result.contains("Message exceeds 250 characters by"));
    }

    @Test
    public void testCheckRecipientCell_Success() {
        // Test alignment with brief data (+27718693002 matches formatting)
        String cell = "+27718693002";
        String result = msgTool.checkRecipientCell(cell);
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        // Test null input
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msgTool.checkRecipientCell(null));
        
        // Test missing international code prefix
        String badCell = "0718693002";
        String result = msgTool.checkRecipientCell(badCell);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testCreateMessageHash() {
        // Checks that the hash is properly combined, generated in upper case, and formats properly
        String msgId = "0012345678"; 
        int msgNum = 0;
        String message = "Hi Mike, can you join us for dinner tonight?";
        
        String hash = msgTool.createMessageHash(msgId, msgNum, message);
        assertEquals("00:0:HITONIGHT", hash);
        
        // Test edge boundaries
        assertEquals("00:0:ERROR", msgTool.createMessageHash(null, 0, "Hello"));
        assertEquals("00:0:ERROR", msgTool.createMessageHash("0", 0, "Hello"));
    }

    @Test
    public void testGenerateMessageID() {
        String id1 = msgTool.generateMessageID();
        String id2 = msgTool.generateMessageID();
        
        assertEquals(10, id1.length());
        assertEquals(10, id2.length());
        assertNotEquals(id1, id2); // Random tokens must change across instances
    }

    @Test
    public void testStoreMessageActions() {
        assertEquals("Message successfully sent.", msgTool.storeMessage(1));
        assertEquals("Press 0 to delete the message.", msgTool.storeMessage(2));
        assertEquals("Message successfully stored.", msgTool.storeMessage(3));
        assertEquals("Invalid choice.", msgTool.storeMessage(99));
    }

    @Test
    public void testStoreMessageInJSON() {
        String jsonOutput = msgTool.storeMessageInJSON("0012345678", "00:0:HITONIGHT", "+27718693002", "Hi Mike", 0);
        assertTrue(jsonOutput.contains("\"messageID\": \"0012345678\""));
        assertTrue(jsonOutput.contains("\"messageHash\": \"00:0:HITONIGHT\""));
    }
}