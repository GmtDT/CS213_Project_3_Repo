package TuitionManager;

/**
 * The TriState class is a blueprint for a Tri-State
 * student. It extends the NonResident class and stores an additional
 * variable state
 * @author Dylan Turner, Noor Hasan
 */
public class TriState extends NonResident{
    private String state;

    /**
     * Constructor for a Tri-State student.
     * @param profile profile of the student.
     * @param major major of the student.
     * @param creditCompleted credits completed by the student.
     * @param state what state the student is from.
     */
    public TriState(Profile profile, String major,int creditCompleted,String state){
        super( profile,  major, creditCompleted);
        this.state = state;
    }

    /**
     * Calculates the tuition owed by the student based
     * on the amount of creditsEnrolled.
     * @param creditsEnrolled amount of credits student is enrolled in.
     * @return the tuition owed by the student.
     */
    public double tuitionDue(int creditsEnrolled){
        double tuition;
        tuition = super.tuitionDue(creditsEnrolled); // uses nonresident tuition as a base
        if(creditsEnrolled >= TuitionValues.FULL_TIME_MIN.getValue()) {
            if (state.equalsIgnoreCase("NY")) {
                tuition -= TuitionValues.NEW_YORK_DISCOUNT.getValue();
            } else if (state.equalsIgnoreCase("CT")) {
                tuition -= TuitionValues.CONNECTICUT.getValue();
            }
        }
        return tuition;
    }

    /**
     * Helper method for outputting string representation of a student.
     * @return that they are a Tri-State student.
     */
    @Override
    public String getType() {
        return "(Tri-State " + this.state+ ")";
    }

    /**
     * Checks if the student is a resident.
     * @return false in the case of a tri-state student.
     */
    @Override
    public boolean isResident() {
        return super.isResident();
    }

    /**
     * Checks if the amount of credits input is a
     * valid amount for a tri-state student.
     * @param creditEnrolled credits to check.
     * @return true if valid amount, false if not.
     */
    @Override
    public boolean isValid(int creditEnrolled) {
        return super.isValid(creditEnrolled);
    }

    /**
     * Helper method for outputting string representation of a student.
     * @return that they are a tri-state student.
     */
    @Override
    public String getArea() {
        return "(Tri-State:" + this.state + ") ";
    }
}
