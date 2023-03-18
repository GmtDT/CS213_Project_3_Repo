package com.example.TuitionManager;

/**
 * The Standing Enum class stores each standing and
 * the value that represents it.
 * @author Dylan Turner, Noor Hasan
 */

public enum Standing {
    FRESHMAN (30),
    SOPHOMORE (60),
    JUNIOR (90);

    private final int value;

    /**
     * Creates the standing constant
     * @param value the corresponding value.
     */
    Standing(int value){
        this.value = value;
    }

    /**
     * Getter method for the standings.
     * @return the value of the specified standing.
     */
    public int getValue(){
        return this.value;
    }
}

