package com.example.TuitionManager;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit Test Class for the tuitionDue() method.
 * Tests done on an international student studying abroad
 * and an international student not studying abroad.
 */
public class InternationalTest {
    /**
     * Tests to see if International Student studying abroad and under the credit limit owes $5918.00.
     */
    @Test
    public void tuitionDue_UnderCreditLimit_isStudyAbroad() {
        Profile profile = new Profile("Diaz","Mia","06/30/2002");
        International student = new International(profile, "MATH", 90, true);
        int enrolledCredits = 9;

        assertEquals(5918.00, student.tuitionDue(enrolledCredits), 0.001);
    }

    /**
     * Tests to see if International Student not studying abroad and under the credit limit owes $40485.00.
     */
    @Test
    public void tuitionDue_UnderCreditLimit_isNotStudyAbroad() {
        Profile profile = new Profile("Scanlan","Bill","05/01/1999");
        International student = new International(profile, "MATH", 90, false);
        int enrolledCredits = 21;

        assertEquals(40485.00,student.tuitionDue(enrolledCredits), 0.001);
    }

    /**
     * Tests to see if International student not studying abroad and above the credit limit does not owe $40485.00.
     */
    @Test
    public void tuitionDue_AboveCreditLimit_isNotStudyAbroad() {
        Profile profile = new Profile("Scanlan","Bill","05/01/1999");
        International student = new International(profile, "MATH", 90, false);
        int enrolledCredits = 26;

        assertNotEquals(40485.00,student.tuitionDue(enrolledCredits), 0.001);
    }
}