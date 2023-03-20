package TuitionManager;

import static org.junit.Assert.*;

/**
 * JUnit Test Class for the isValid() method in the Date class
 * Tests for valid and invalid dates given different months and days
 * @author Dylan Turner, Noor Hasan
 */
public class DateTest {

    /**
     * Tests if an invalid amount of days in February outside a leap year returns as false
     */
    @org.junit.Test
    public void isValid_daysInFeb_NonLeap() {
        Date date = new Date("2/29/2003");   // required case 2/29/2003.
        assertFalse(date.isValid());
    }
    /**
     * Tests if a valid amount of days in February during a leap year returns as true
     */
    @org.junit.Test
    public void isValid_daysInFeb_Leap() {
        Date date = new Date("2/29/2016");
        assertTrue(date.isValid());
    }
    /**
     * Tests if an invalid amount of days in February during a leap year returns as false
     */
    @org.junit.Test
    public void isValid_InValid_daysInFeb_Leap() {
        Date date = new Date("2/0/2016");
        assertFalse(date.isValid());
    }
    /**
     * Tests if a valid amount of days in a thirty-day month returns as true
     */
    @org.junit.Test
    public void isValid_within30Days() {
        Date date = new Date("4/1/2003");
        assertTrue(date.isValid());
    }
    /**
     * Tests if an invalid amount of days in a thirty-day month returns as false
     */
    @org.junit.Test
    public void isValid_inValid_Day30() {
        Date date = new Date("4/31/2003"); //required test case

        assertFalse(date.isValid());
    }
    /**
     * Tests if an invalid amount of days in a thirty-one-day month returns as false
     */
    @org.junit.Test
    public void isValid_inValid_Day31() {
        Date date = new Date("3/32/2003"); //required test case

        assertFalse(date.isValid());
    }
    /**
     * Tests if an invalid month returns as false
     */
    @org.junit.Test
    public void isValid_inValid_Month() {
        Date date = new Date("13/31/2003");  // required case 13/31/2003
        Date date2 = new Date("-1/31/2003"); //required test case

        assertFalse(date2.isValid());
        assertFalse(date.isValid());
    }
}