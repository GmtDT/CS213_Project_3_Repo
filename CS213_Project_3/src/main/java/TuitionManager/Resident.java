package TuitionManager;

/**
 * The Resident class is a blueprint for a Resident
 * Student. It extends the student class and stores an
 * additional variable scholarship.
 * @author Dylan Turner, Noor Hasan
 */
public class Resident extends Student{
    private int scholarship;

    /**
     * Constructor for a Resident student.
     * @param profile profile of the student.
     * @param major major of the student.
     * @param creditCompleted credits completed by the student.
     */
    public Resident(Profile profile, String major,int creditCompleted){
        super(profile, major, creditCompleted);
        int scholarship = 0;
    }

    /**
     * Calculates the tuition owed by the student based
     * on the amount of creditsEnrolled.
     * @param creditsEnrolled amount of credits student is enrolled in.
     * @return the tuition owed by the student.
     */
    @Override
    public double tuitionDue(int creditsEnrolled){
        double tuition;
        int exCred = (int)(creditsEnrolled - TuitionValues.ADDITIONAL_MAX.getValue());
        if(exCred < 0)
            exCred = 0;
        if(creditsEnrolled >= TuitionValues.FULL_TIME_MIN.getValue() ){ // full time student
            tuition = TuitionValues.FULL_RESIDENT_BASE.getValue() + TuitionValues.FULL_UNIVERSITY_FEE.getValue()
                    + TuitionValues.ADDITIONAL_RESIDENT_RATE.getValue() * exCred - scholarship;
        }else{
            tuition = TuitionValues.PART_RESIDENT_RATE.getValue() * creditsEnrolled
                    + TuitionValues.PART_UNIVERSITY_FEE.getValue()-scholarship;
        }
        return tuition;
    }

    /**
     * Checks if the amount of credits input is a
     * valid amount for a Resident student.
     * @param creditEnrolled credits to check.
     * @return true if valid amount, false if not.
     */
    @Override
    public boolean isValid(int creditEnrolled) {
        return super.isValid(creditEnrolled);
    }

    /**
     * Checks if the student is a resident.
     * @return true in the case of a Resident student.
     */
    @Override
    public boolean isResident() {
        return true;
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
     * @return that they are a Resident student.
     */
    @Override
    public String getType() {
        return "(Resident)";
    }

    /**
     * Setter method for the Resident students scholarship amount.
     * @param scholarship the amount to set the scholarship to.
     */
    public void setScholarship(int scholarship) {
        this.scholarship = scholarship;
    }

    /**
     * Getter method for the Resident students scholarship amount.
     * @return
     */
    public int getScholarship() {
        return this.scholarship;
    }

    /**
     * Checks whether the Resident student
     * is eligible for a scholarship.
     * @return true if they are eligible, false if not.
     */
    public boolean isEligible() {
        return this.getCredits() >= TuitionValues.FULL_TIME_MIN.getValue();
    }
}
