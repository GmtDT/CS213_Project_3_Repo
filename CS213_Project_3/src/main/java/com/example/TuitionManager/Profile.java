package com.example.TuitionManager;

/**
 * Blueprint for profile objects: stores the last name,
 * first name, and date of birth.
 * @author Dylan Turner, Noor Hasan
 */
public class Profile implements Comparable<Profile> {
    private String lname;
    private String fname;
    private Date dob;

    /**
     * Constructor method for the Profile object.
     * @param lname the student's last name.
     * @param fname the student's first name.
     * @param dob the student's date of birth.
     */
    public Profile(String lname, String fname, String dob) {
        this.lname = lname;
        this.fname = fname;
        this.dob = new Date(dob);
    }

    /**
     * Converts the profile to string representation.
     * @return the string representation of the profile.
     */
    @Override
    public String toString() {
        return (this.fname + " " + this.lname + " " + this.dob.toString());
    }

    /**
     * Checks if one profile is equal to another.
     * @param otherProfile the object to be compared to.
     * @return true if the profiles are equal, false otherwise.
     */
    @Override
    public boolean equals(Object otherProfile) {
        if (otherProfile instanceof Profile profile) {
            return (this.lname.equalsIgnoreCase(profile.getLname()))
                    && (this.fname.equalsIgnoreCase(profile.getFname()))
                    && (this.dob.equals(profile.getDob()));
        }
        return false;
    }

    /**
     * Checks whether another profile should be before or after this profile.
     * @param otherProfile the profile to be compared to.
     * @return 1 if the other profile should be before this one.
     *        -1 if the other profile should be after this one.
     *         0 if the two profiles are equal.
     */
    @Override
    public int compareTo(Profile otherProfile) {
        if (this.equals(otherProfile)) {
            return 0;
        }
        if (this.lname.compareTo(otherProfile.getLname()) < 0) {
            return -1;
        }
        if (this.lname.compareTo(otherProfile.getLname()) > 0) {
            return 1;
        }
        if (this.fname.compareTo(otherProfile.getFname()) < 0) {
            return -1;
        }
        if (this.fname.compareTo(otherProfile.getFname()) > 0) {
            return 1;
        }
        if (this.dob.compareTo(otherProfile.getDob()) < 0) {
            return -1;
        }
        return 1;
    }

    /**
     * Getter method for last name of the profile
     * @return the profile's last name.
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * Getter method for first name of the profile
     * @return the profile's first name.
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * Getter method for date of birth of the profile
     * @return the profile's date of birth.
     */
    public Date getDob() {
        return dob;
    }
}