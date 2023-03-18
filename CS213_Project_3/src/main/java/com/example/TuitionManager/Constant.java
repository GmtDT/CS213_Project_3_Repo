package com.example.TuitionManager;

/**
 * The Constant Enum class stores all the variables
 * with constant values in order to preserve clean
 * coding standards.
 * @author Dylan Turner, Noor Hasan
 */
public enum Constant {
    QUADRENNIAL(4),
    CENTENNIAL (100),
    QUATERCENTENNIAL (400),
    MAX_MONTH(12),
    MIN_MONTH(0),
    MAX_DAY(31),
    MIN_DAY(0),
    FEB_MAX(28),
    FEBRUARY (2),
    APRIL (4),
    JUNE (6),
    SEPTEMBER (9),
    NOVEMBER (11),
    INITIAL_SIZE (4),
    ENROLLMENT_INITIAL (1),
    NOT_FOUND (-1),
    MINIMUM_AGE (16),
    UNDEFINED_CREDITS (0),
    ONE_REQUIRED (1),
    TWO_REQUIRED (2),
    FOUR_REQUIRED (4),
    FIVE_REQUIRED (5),
    SIX_REQUIRED (6),
    SEVEN_REQUIRED (7),
    DATE_TESTS(5);

    private final int value;

    /**
     * Creates the constant
     * @param value the corresponding value.
     */
    Constant(int value){
        this.value = value;
    }
    /**
     * Getter method for the constants.
     * @return the value of the specified constant.
     */
    public int getValue(){
        return this.value;
    }
}
