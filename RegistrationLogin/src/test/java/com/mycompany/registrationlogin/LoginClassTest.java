package com.mycompany.registrationlogin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginClassTest {

    private LoginClass auth;

    @BeforeEach
    public void setUp() {
        auth = new LoginClass();
    }

    // ==========================================
    // 1. USERNAME VALIDATION TESTS
    // ==========================================
    
    @Test
    public void testCheckUserName_Null() {
        assertFalse(auth.checkUserName(null));
    }

    @Test
    public void testCheckUserName_Valid() {
        assertTrue(auth.checkUserName("k_12"));
        assertTrue(auth.checkUserName("ab_cd")); 
    }

    @Test
    public void testCheckUserName_Invalid_NoUnderscore() {
        assertFalse(auth.checkUserName("abcde"));
    }

    @Test
    public void testCheckUserName_Invalid_TooLong() {
        assertFalse(auth.checkUserName("abc_de"));
    }

    // ==================================================
    // 2. PASSWORD COMPLEXITY TESTS
    // ==================================================

    @Test
    public void testCheckPasswordComplexity_Null() {
        assertFalse(auth.checkPasswordComplexity(null));
    }

    @Test
    public void testCheckPasswordComplexity_Valid() {
        assertTrue(auth.checkPasswordComplexity("Ch@llenge2026"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid_TooShort() {
        assertFalse(auth.checkPasswordComplexity("P@ss1"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid_NoCapital() {
        assertFalse(auth.checkPasswordComplexity("p@ssword123"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid_NoNumber() {
        assertFalse(auth.checkPasswordComplexity("Password@"));
    }

    @Test
    public void testCheckPasswordComplexity_Invalid_NoSymbol() {
        assertFalse(auth.checkPasswordComplexity("Password123"));
    }

    // ==============================================
    // 3. PHONE NUMBER VALIDATION TESTS
    // ==============================================

    @Test
    public void testCheckCellPhoneNumber_Null() {
        assertFalse(auth.checkCellPhoneNumber(null));
    }

    @Test
    public void testCheckCellPhoneNumber_Valid() {
        assertTrue(auth.checkCellPhoneNumber("+27123456789"));
        assertTrue(auth.checkCellPhoneNumber("+1"));
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid_NoPlus() {
        assertFalse(auth.checkCellPhoneNumber("271234567890"));
    }

    @Test
    public void testCheckCellPhoneNumber_Invalid_TooLong() {
        assertFalse(auth.checkCellPhoneNumber("+1234567890123")); 
    }

    // ========================================
    // 4. REGISTRATION TESTS
    // ========================================

    @Test
    public void testRegisterUser_Success() {
        String status = auth.registerUser("k_12", "Ch@llenge2026", "+27123456789");
        assertEquals("The two above conditions have been met, and the user has been registered successfully.", status);
    }

    @Test
    public void testRegisterUser_Fail_Username() {
        String status = auth.registerUser("invalid_user", "Ch@llenge2026", "+27123456789");
        assertEquals("The username is incorrectly formatted.", status);
    }

    @Test
    public void testRegisterUser_Fail_Password() {
        String status = auth.registerUser("k_12", "weak", "+27123456789");
        assertEquals("The password does not meet the complexity requirements.", status);
    }

    @Test
    public void testRegisterUser_Fail_Cell() {
        String status = auth.registerUser("k_12", "Ch@llenge2026", "0123456789");
        assertEquals("The phone number format is not correct.", status);
    }

    // ==================================
    // 5. LOGIN LOGIC TESTS
    // ==================================

    @Test
    public void testLoginUser_WithoutRegistration() {
        assertFalse(auth.loginUser("k_12", "Ch@llenge2026"));
    }

    @Test
    public void testLoginUser_Success() {
        auth.registerUser("k_12", "Ch@llenge2026", "+27123456789");
        assertTrue(auth.loginUser("k_12", "Ch@llenge2026"));
    }

    @Test
    public void testLoginUser_Fail_WrongUsername() {
        auth.registerUser("k_12", "Ch@llenge2026", "+27123456789");
        assertFalse(auth.loginUser("wrong_u", "Ch@llenge2026"));
    }

    @Test
    public void testLoginUser_Fail_WrongPassword() {
        auth.registerUser("k_12", "Ch@llenge2026", "+27123456789");
        assertFalse(auth.loginUser("k_12", "WrongPass1!"));
    }

    // ============================================
    // 6. LOGIN STATUS MESSAGE TESTS
    // ============================================

    @Test
    public void testReturnLoginStatus_Success() {
        String message = auth.returnLoginStatus(true, "John", "Doe");
        assertEquals("Welcome John Doe, it is great to see you again.", message);
    }

    @Test
    public void testReturnLoginStatus_Failure() {
        String message = auth.returnLoginStatus(false, "John", "Doe");
        assertEquals("Username or password incorrect, please try again.", message);
    }
}