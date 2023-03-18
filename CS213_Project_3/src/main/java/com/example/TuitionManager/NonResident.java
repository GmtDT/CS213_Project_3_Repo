package com.example.TuitionManager;

/**
 * The NonResident class is a blueprint for a Non-Resident
 * Student. It extends the student class and does not store
 * any additional variables. It had two subclasses related
 * to it, International and TriState.
 * @author Dylan Turner, Noor Hasan
 */
public class NonResident extends Student {

    /**
     * Constructor for a NonResident student.
     * @param profile profile of the student.
     * @param major major of the student.
     * @param creditCompleted credits completed by the student.
     */
    public NonResident(Profile profile, String major,int creditCompleted){
        super( profile,  major, creditCompleted);
    }

    /**
     * Calculates the tuition owed by the student based
     * on the amount of creditsEnrolled.
     * @param creditsEnrolled amount of credits student is enrolled in.
     * @return the tuition owed by the student.
     */
    public double tuitionDue(int creditsEnrolled){
        double tuition;
        int exCred = (int)(creditsEnrolled - TuitionValues.ADDITIONAL_MAX.getValue());
        if(exCred < 0)
            exCred = 0;
        if(creditsEnrolled >= TuitionValues.FULL_TIME_MIN.getValue() ){ // full time student
            tuition = TuitionValues.FULL_NONRESIDENT_BASE.getValue() + TuitionValues.FULL_UNIVERSITY_FEE.getValue()
                    + TuitionValues.ADDITIONAL_NONRESIDENT_RATE.getValue() * exCred ;
        }else{
            tuition = TuitionValues.ADDITIONAL_NONRESIDENT_RATE.getValue() * creditsEnrolled
                    + TuitionValues.PART_UNIVERSITY_FEE.getValue();
        }
        return tuition;
    }

    /**
     * Checks if the amount of credits input is a
     * valid amount for a NonResident student.
     * @param creditEnrolled credits to check.
     * @return true if valid amount, false if not.
     */
    @Override
    public boolean isValid(int creditEnrolled) {
        return super.isValid(creditEnrolled);
    }

    /**
     * Checks if the student is a resident.
     * @return false in the case of a NonResident student.
     */
    @Override
    public boolean isResident() {
        return false;
    }

    /**
     * Helper method for outputting string representation of a student.
     * @return an empty string.
     */
    @Override
    public String getArea() {
        return "";
    }

    /**
     * Helper method for outputting string representation of a student.
     * @return that they are a Non-Resident student.
     */
    @Override
    public String getType() {
        return "(Non-Resident)";
    }
}
