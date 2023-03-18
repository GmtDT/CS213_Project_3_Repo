package com.example.TuitionManager;

import java.text.DecimalFormat;

/**
 The Enrollment class acts as a database for enrolled students, storing
 an array of enrolled students and the size of the array.
 @author Dylan Turner, Noor Hasan
 */
public class Enrollment {
    private EnrollStudent[] enrollStudents;
    private int size;

    /**
     * Constructor for the Enrollment Class, initializes array of length
     * one and sets size instance variable to zero.
     */
    public Enrollment() {
        this.enrollStudents = new EnrollStudent[Constant.ENROLLMENT_INITIAL.getValue()];
        this.size = 0;
    }

    /**
     * Adds a given EnrollStudent into enrollment array
     * after increasing the size of the array by one.
     * @param enrollStudent EnrollStudent to be enrolled.
     */
    public void add(EnrollStudent enrollStudent) {
        if(this.size == enrollStudents.length){
            int newLength = this.size + 1;
            EnrollStudent[] tempEnroll = new EnrollStudent[newLength];
            for(int i = 0; i < this.size; i++) {
                tempEnroll[i] = this.enrollStudents[i];
            }
            tempEnroll[size] = enrollStudent;
            this.enrollStudents = tempEnroll;
            this.size++;
            return;
        }
        enrollStudents[size] = enrollStudent;
        this.size++;
    }

    /**
     * Removes an EnrollStudent from the enrollment array
     * by overriding them with the student at the end of the array.
     * Leaves a null reference at the end of the array.
     * @param enrollStudent EnrollStudent to be removed.
     */
    public void remove(EnrollStudent enrollStudent) {
        int removePosition = find(enrollStudent);
        if(removePosition != Constant.NOT_FOUND.getValue()){
            enrollStudents[removePosition] = enrollStudents[size-1];
            enrollStudents[this.size-1] = null;
            this.size--;
        }
    }

    /**
     * Checks whether an enrolled student is already taking
     * the amount of credits input.
     * @param student EnrollStudent to be checked.
     * @param credits amount of credits to be checked.
     * @return true if already taking that many credits, false otherwise.
     */
    public boolean isAlreadyTaking(EnrollStudent student, int credits) {
        int position = find(student);
        return (this.enrollStudents[position].getCreditsEnrolled() == credits);
    }

    /**
     * Checks whether a specified EnrollStudent is
     * currently in the enrollment array.
     * @param enrollStudent EnrollStudent to look for.
     * @return true if they are in the array, false if not.
     */
    public boolean contains(EnrollStudent enrollStudent) {
        for(int i = 0; i < this.size; i++) {
            if(enrollStudents[i].equals(enrollStudent)){
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the index of a specified student
     * in the enrollment array.
     * @param enrollStudent EnrollStudent to look for.
     * @return the index of enrollStudent in the array.
     */
    public int find(EnrollStudent enrollStudent) {
        for(int i = 0; i < this.size; i++) {
            if(enrollStudents[i].equals(enrollStudent)){
                return i;
            }
        }
        return Constant.NOT_FOUND.getValue();
    }

    /**
     * Searches the enrollment array for a student that has the given profile
     * and returns a reference to said student.
     * @param profile profile to search for
     * @return a reference to the student containing that profile.
     */
    public EnrollStudent getEnrollStudent(Profile profile) {
        for(int i = 0; i < this.size; i++) {
            if(enrollStudents[i].getProfile().equals(profile)){
                return enrollStudents[i];
            }
        }
        return null;
    }

    /**
     * Sets the amount of credits a specified EnrollStudent is enrolled for.
     * @param enrollStudent EnrollStudent to change credits of.
     * @param enrollCredits amount of credits to change to.
     */
    public void setEnrollCredits(EnrollStudent enrollStudent, int enrollCredits) {
        int position = find(enrollStudent);
        this.enrollStudents[position].setCreditsEnrolled(enrollCredits);
    }

    /**
     * tells whether the enrollment is empty.
     * @return true if empty, false if not.
     */
    public boolean isEmpty() {
        return (this.size == 0);
    }

    /**
     * Prints out the enrollment array as a list in
     * string representation, in the same order of the array.
     */
    public void print() {
        if(!isEmpty()){
            System.out.println("** Enrollment **");
            for(int i = 0; i < this.size; i++){
                System.out.println(enrollStudents[i].toString() + ": credits enrolled: " + enrollStudents[i].getCreditsEnrolled());
            }
            System.out.println("** end of enrollment **");
            return;
        }
        System.out.println("Enrollment is empty!");
    }

    /**
     * Prints out the tuition due for each enrolled student in the enrollment array.
     * @param roster reference to the Roster from the TuitionManager.
     */
    public void printTuition(Roster roster) {
        double tuition;
        int creditsEnrolled;
        if(isEmpty()){
            System.out.println("Student roster is empty!");
            return;
        }
        System.out.println("** Tuition due **");
        for(EnrollStudent enrolled : this.enrollStudents) {
            if(!(enrolled == null)){
                Student currentStudent = roster.getStudent(enrolled.getProfile());
                tuition = currentStudent.tuitionDue(enrolled.getCreditsEnrolled());
                creditsEnrolled = enrolled.getCreditsEnrolled();
                System.out.println(currentStudent.getProfile().toString() + " "
                        + currentStudent.getType() + " enrolled " + creditsEnrolled
                        + " credits: tuition due: $" + formatDouble(tuition));
            }
        }
        System.out.println("** end of tuition due **");
    }
    public String formatDouble(double t){
        String pattern = ".00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        return decimalFormat.format(t);
    }

    /**
     * Adds the credits a student was enrolled in to their completed credits
     * and prints out a list of all students who are now eligible for graduation.
     * @param roster reference to the Roster from the TuitionManager.
     */
    public void semesterEnd(Roster roster) {
        int creditsEnrolled;
        System.out.println("Credits completed has been updated.");
        System.out.println("** list of students eligible for graduation **");
        for(EnrollStudent enrolled : this.enrollStudents) {
            if(!(enrolled == null)){
                Student currentStudent = roster.getStudent(enrolled.getProfile());
                creditsEnrolled = enrolled.getCreditsEnrolled();
                currentStudent.addCredits(creditsEnrolled);
                if(currentStudent.getCredits() >= 120) {
                    System.out.println(currentStudent);
                }
            }
        }
        System.out.println("** end of graduation list");
    }
}
