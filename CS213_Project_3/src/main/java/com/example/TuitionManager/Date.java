package com.example.TuitionManager;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * The blueprint for date object: stores the year, month,
 * and day. Can test for valid dates, compare dates, etc.
 * @author Dylan Turner, Noor Hasan
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    /**
     * Default constructor for the Date object, sets date to current date.
     */
    public Date() {
        Calendar today = Calendar.getInstance();
        this.year = today.get(Calendar.YEAR);
        this.month = today.get(Calendar.MONTH) + 1;
        this.day = today.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Constructor for specific date object.
     * @param date desired date in "mm/dd/yyyy" format.
     */
    public Date(String date) {
        StringTokenizer splitDate = new StringTokenizer(date, "-");
        this.year = Integer.parseInt(splitDate.nextToken());
        this.month = Integer.parseInt(splitDate.nextToken());
        this.day = Integer.parseInt(splitDate.nextToken());
    }

    /**
     * Constructor for specific date object using integers.
     * @param month desired month.
     * @param day   desired day.
     * @param year  desired year
     */
    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Getter method for year of the date object
     * @return the date's year.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Getter method for month of the date object
     * @return the date's month.
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Getter method for day of the date object
     * @return the date's day.
     */
    public int getDay() {
        return this.day;
    }

    /**
     * Checks if the date is valid.
     * @return true if valid, false otherwise.
     */
    public boolean isValid() {
        boolean thirtyDay = (this.month == Constant.APRIL.getValue())
                || (this.month == Constant.JUNE.getValue())
                || (this.month == Constant.SEPTEMBER.getValue())
                || (this.month == Constant.NOVEMBER.getValue());
        if (this.day <= Constant.MIN_DAY.getValue() || this.day > Constant.MAX_DAY.getValue()) {          //check if days are between 0-31
            return false;
        }
        if (thirtyDay) {                                        //check if the day is allowed for what month it is
            if (this.day > Constant.MAX_DAY.getValue()-1)
                return false;
        }
        if ((this.month == Constant.FEBRUARY.getValue()          //check if it's a leap year or not
                && this.day > Constant.FEB_MAX.getValue()
                && !isLeapYear())){
            return false;
        }
        if(this.month == Constant.FEBRUARY.getValue()
                && this.day > Constant.FEB_MAX.getValue()+1){
            return false;
        }
        if (this.year < 0) {                                     //checks if the year is below 0
            return false;
        }
        return (this.month <= Constant.MAX_MONTH.getValue())   //check if months are below 0 or above 12
                && this.month > Constant.MIN_MONTH.getValue();
    }

    /**
     * Helper method for the isValid() method. Checks if the date
     * is a leap year or not.
     * @return true if leap year,false if not.
     */
    public boolean isLeapYear() {
        boolean leapCase1 = (this.year % Constant.QUATERCENTENNIAL.getValue() == 0);
        boolean leapCase2 = (this.year % Constant.QUADRENNIAL.getValue() == 0)
                && (this.year % Constant.CENTENNIAL.getValue() != 0);
        return leapCase1 || leapCase2;
    }

    /**
     * Returns string representation of the date.
     * @return string representation of date.
     */
    @Override
    public String toString() {
        return (this.month + "/" + this.day + "/" + this.year);
    }

    /**
     * Returns whether two dates are equal.
     * @param otherDate date to be compared to.
     * @return true if dates are equal, false if not.
     */
    public boolean equals(Date otherDate) {
        return ((this.year == otherDate.year) && (this.month == otherDate.month) && (this.day == otherDate.day));
    }

    /**
     * Returns whether a date should be before or after other date.
     * @param otherDate date to be compared to.
     * @return 1 if the other date is before this date.
     * -1 if the other date is after this date.
     * 0 if the two dates are equal.
     */
    @Override
    public int compareTo(Date otherDate) {
        if (this.equals(otherDate)) {       //check if they are equal first
            return 0;
        }
        if (this.year < otherDate.year) {   //check which one is greater
            return -1;
        }
        if (this.year > otherDate.year) {
            return 1;
        }
        if (this.month < otherDate.month) {
            return -1;
        }
        if (this.month > otherDate.month) {
            return 1;
        }
        if (this.day < otherDate.day) {
            return -1;
        }
        return 1;
    }


    /**
       Helper Method for Testbed Main.
       Creates individual dates from test arrays and checks
       whether isValid() method returns correct output.
       @param month array of test months.
       @param day array of test days.
       @param year array of test years.
       @param test the test number to perform from the design document.
       @param correctOutput what the test should return.
     */
    private static void callTests(int[] month, int[] day, int[] year, int test, boolean correctOutput) {
        for (int i = 0; i < Constant.DATE_TESTS.getValue(); i++) {
            Date date = new Date(month[i], day[i], year[i]);
            System.out.println("Test #" + test + ", " + date
                    + " isValid() returns: " + date.isValid()
                    + ". " + "Passed: " + (date.isValid() == correctOutput));
        }
    }

    /**
       Testbed main for the isValid() method.
       @param args arguments input from the terminal (unnecessary here)
     */
    public static void main(String[] args) {
        int[] monthTest1 = {0, 13, 50, -1, 50};                 //Months between 1-12.
        int[] dayTest1 = {1, 5, 10, 15, 20};                            //Includes required case 13/31/2003
        int[] yearTest1 = {2003, 2005, 2007, 2009, 2011};
        callTests(monthTest1, dayTest1, yearTest1, 1, false);

        int[] monthTest2 = {6, 4, 9, 11, 4};                    //Days between 1-30 for correct months.
        int[] dayTest2 = {0, 31, 33, 31, 34};                           //Includes required case 4/31/2003
        int[] yearTest2 = {2005, 2003, 2007, 2009, 2011};               //Includes required case -1/31/2003
        callTests(monthTest2, dayTest2, yearTest2, 2, false);

        int[] monthTest3 = {1, 3, 5, 8, 10};                    //Days between 1-31 for correct months.
        int[] dayTest3 = {0, 32, 38, 1005, 35};                            //Includes required case 3/32/2003
        int[] yearTest3 = {2005, 2003, 2007, 2009, 2011};
        callTests(monthTest3, dayTest3, yearTest3, 3, false);

        int[] monthTest4 = {2, 2, 2, 2, 2};                    //February's days between 1-28 on non-leap year.
        int[] dayTest4 = {0, 29, 29, 29, 30};                           //Includes required case 2/29/2003.
        int[] yearTest4 = {2001, 2003, 2009, 2014, 2019};
        callTests(monthTest4, dayTest4, yearTest4, 4, false);

        int[] monthTest5 = {2, 2, 2, 2, 2};                    //February's days between 1-29 on leap year.
        int[] dayTest5 = {0, 30, 39, 35, 30};
        int[] yearTest5 = {2000, 2004, 2008, 2012, 2016};
        callTests(monthTest5, dayTest5, yearTest5, 5, false);

        int[] monthTest6 = {2, 2, 2, 2, 2};                   //February has 29 days on leap year
        int[] dayTest6 = {1, 29, 29, 29, 28};
        int[] yearTest6 = {2000, 2004, 2008, 2012, 2016};
        callTests(monthTest6, dayTest6, yearTest6, 6, true);

        int[] monthTest7 = {4, 6, 9, 11, 4};                   //Days between 1-30 for correct months.
        int[] dayTest7 = {1, 5, 10, 15, 30};
        int[] yearTest7 = {2003, 2005, 2007, 2009, 2011};
        callTests(monthTest7, dayTest7, yearTest7, 7, true);

        int[] monthTest8 = {1, 3, 5, 8, 10};                   //Days between 1-31 for correct months.
        int[] dayTest8 = {1, 5, 10, 15, 31};
        int[] yearTest8 = {2003, 2005, 2007, 2009, 2011};
        callTests(monthTest8, dayTest8, yearTest8, 8, true);
    }
}


