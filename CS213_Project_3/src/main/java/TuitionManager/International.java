package TuitionManager;

/**
 * The International class is a blueprint for an International
 * Student. It extends the NonResident class and stores an additional
 * variable isStudyAbroad.
 * @author Dylan Turner, Noor Hasan
 */
public class International extends NonResident {
    private boolean isStudyAbroad;

    /**
     * Constructor for an International Student.
     * @param profile profile of the student.
     * @param major major of the student.
     * @param creditCompleted amount of credits completed by the student.
     * @param isStudyAbroad whether they are studying abroad.
     */
    public International(Profile profile, String major,int creditCompleted, boolean isStudyAbroad){
        super(profile,  major, creditCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Calculates the tuition owed by the student based
     * on the amount of creditsEnrolled.
     * @param creditsEnrolled amount of credits student is enrolled in.
     * @return the tuition owed by the student.
     */
    public double tuitionDue(int creditsEnrolled){
        double tuition;
        if(!isStudyAbroad) {
             tuition = super.tuitionDue(creditsEnrolled); // uses nonresident tuition as a base
                tuition += TuitionValues.HEALTH_INSURANCE_FEE.getValue();
        }else{
             tuition = TuitionValues.FULL_UNIVERSITY_FEE.getValue() + TuitionValues.HEALTH_INSURANCE_FEE.getValue();
        }
        return tuition;
    }

    /**
     * Checks if the amount of credits input is a
     * valid amount for an international student.
     * @param creditEnrolled credits to check.
     * @return true if valid amount, false if not.
     */
    @Override
    public boolean isValid(int creditEnrolled) {
        if(!super.isValid(creditEnrolled)){
            return false;
        }else if(creditEnrolled >= TuitionValues.FULL_TIME_MIN.getValue() && isStudyAbroad){
            return false;
        }else if(creditEnrolled < TuitionValues.FULL_TIME_MIN.getValue() && !isStudyAbroad){
            return false;
        }
        return true;
    }

    /**
     * Checks if the student is a resident.
     * @return false in the case of an international student.
     */
    @Override
    public boolean isResident() {
        return super.isResident();
    }

    /**
     * Helper method for outputting string representation of a student.
     * @return that they are an international student.
     */
    @Override
    public String getType() {
        return "(International Student" + isAbroad();
    }

    /**
     * Helper method for outputting string representation of a student.
     * @return that they are an international student.
     */
    @Override
    public String getArea() {
        return "(International" + isAbroad();
    }

    /**
     * Helper method for string formating that checks
     * whether student is studying abroad or not
     * @return studying abroad if they are, closing parenthesis if not.
     */
    private String isAbroad() {
        if(this.isStudyAbroad){
            return ": Study Abroad)";
        } else {
            return ")";
        }
    }
}
