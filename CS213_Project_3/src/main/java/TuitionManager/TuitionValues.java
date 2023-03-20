package TuitionManager;

/**
 * The Tuition Values Enum class stores all the costs
 * for each type of student.
 * @author Dylan Turner, Noor Hasan
 */
public enum TuitionValues {
    //Table Values
    FULL_RESIDENT_BASE (12536),
    FULL_NONRESIDENT_BASE (29737),
    FULL_UNIVERSITY_FEE (3268),
    HEALTH_INSURANCE_FEE (2650),
    PART_RESIDENT_RATE (404),
    PART_UNIVERSITY_FEE (2614.4), //80% Full University Fee
    ADDITIONAL_RESIDENT_RATE (404),
    ADDITIONAL_NONRESIDENT_RATE (966),

    //Max and Min Credits Values
    MAX_CREDITS (24),
    MIN_CREDITS (3),
    ADDITIONAL_MAX (16),
    FULL_TIME_MIN (12),

    MAX_SCHOLARSHIP (10000),
    NEW_YORK_DISCOUNT (4000),
    CONNECTICUT (5000);

    private final double value;

    /**
     * Creates the Tuition Value
     * @param value the corresponding value.
     */
    TuitionValues(double value){
        this.value = value;
    }
    /**
     * Getter method for the Tuition Values
     * @return the value of the specified value.
     */
    public double getValue(){
        return this.value;
    }
}
