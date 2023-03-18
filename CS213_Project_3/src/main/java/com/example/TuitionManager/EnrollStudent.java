package com.example.TuitionManager;

/**
 * The blueprint for an enrolled student object: stores the profile
 * of the student and the amount of credits they are enrolled for.
 * @author Dylan Turner, Noor Hasan
 */
public class EnrollStudent {
    private Profile profile;
    private int creditsEnrolled;

    /**
       Constructor for the EnrollStudent class.
       @param profile profile of the student to create.
       @param creditsEnrolled amount of credits to enroll for.
     */
    public EnrollStudent(Profile profile, int creditsEnrolled) {
        this.profile = profile;
        this.creditsEnrolled = creditsEnrolled;
    }

    /**
     * Checks whether two EnrollStudent's profiles are equal.
     * @param otherEnroll other EnrollStudent to compare to.
     * @return true if they are equal, false if not.
     */
    public boolean equals(EnrollStudent otherEnroll){
        return (this.profile.equals(otherEnroll.profile));
    }

    /**
     * Returns the string representation of the EnrollStudent.
     * @return string representation of the EnrollStudent.
     */
    public String toString(){
        return (this.profile.toString());
    }

    /**
     * Getter method for the amount of credits enrolled.
     * @return the amount of credits enrolled.
     */
    public int getCreditsEnrolled(){
        return this.creditsEnrolled;
    }

    /**
     * Setter method for the amount of credits enrolled.
     * @param creditsEnrolled amount of credits to set to.
     */
    public void setCreditsEnrolled(int creditsEnrolled) {
        this.creditsEnrolled = creditsEnrolled;
    }

    /**
     * Getter method for the EnrollStudent's profile
     * @return the EnrollStudent's profile.n
     */
    public Profile getProfile() {
        return profile;
    }
}
