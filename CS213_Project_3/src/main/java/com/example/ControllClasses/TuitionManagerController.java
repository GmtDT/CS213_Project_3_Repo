package com.example.ControllClasses;

import TuitionManager.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The controller class for the scene builder class
 * It converts all the functionality of the previous
 * TuitionManager class into GUI based output instead
 * of terminal based. Stores the roster and enrollment.
 * @author Dylan Turner, Noor Hasan
 */
public class TuitionManagerController {
    private final Roster roster = new Roster();
    private final Enrollment enrollment = new Enrollment();

    //Button Groups
    @FXML
    private ToggleGroup btGroup_State;
    @FXML
    private ToggleGroup btGroup_isResident;
    @FXML
    private ToggleGroup buttonGroupMajor;

    //Roster Tab
    @FXML
    private TextField fnameRoster;
    @FXML
    private TextField lnameRoster;
    @FXML
    private DatePicker dobRoster;
    @FXML
    private TextField creditsRoster;
    @FXML
    private RadioButton btBAIT;
    @FXML
    private RadioButton btCS;
    @FXML
    private RadioButton btECE;
    @FXML
    private RadioButton btITI;
    @FXML
    private RadioButton btResident;
    @FXML
    private RadioButton btTriState;
    @FXML
    private RadioButton btInternational;
    @FXML
    private RadioButton btNY;
    @FXML
    private RadioButton btCT;
    @FXML
    private CheckBox boxStudyAbroad;
    @FXML
    private TextArea rosterOutput;

    //Enrollment Tab
    @FXML
    private TextField fnameEnrollment;
    @FXML
    private TextField lnameEnrollment;
    @FXML
    private DatePicker dobEnrollment;
    @FXML
    private TextField creditsEnrollment;
    @FXML
    private TextArea enrollmentOutput;

    //Scholarship Tab
    @FXML
    private TextField fnameScholar;
    @FXML
    private TextField lnameScholar;
    @FXML
    private DatePicker dobScholar;
    @FXML
    private TextField amountScholar;
    @FXML
    private TextArea scholarOutput;

    //Display Tab
    @FXML
    private TextArea displayOutput;

    /**
     * This method is run when the Add button is clicked
     * in the Roster tab. It will create and add a student
     * to the Roster based on the input from the user in
     * the text fields.
     */
    @FXML
    protected void onAddButtonClick() {
        rosterOutput.setText("");
        if(!isValidRosterSelection()){
            return;
        }
        Student newStudent;
        String dob = dobReformater(dobRoster.getValue().toString());
        Profile newProfile = new Profile(lnameRoster.getText(),fnameRoster.getText(), dob);
        String state;
        int creditsCompleted = Integer.parseInt(creditsRoster.getText());
        if(btResident.isSelected()){
            newStudent = new Resident(newProfile, getSelectedMajor(), creditsCompleted);
        } else if(btTriState.isSelected()){
            if(btNY.isSelected()){
                state = "NY";
            } else{
                state = "CT";
            }
            newStudent = new TriState(newProfile, getSelectedMajor(), creditsCompleted, state);
            } else if(btInternational.isSelected()){
                boolean studyAbroad = boxStudyAbroad.isSelected();
                newStudent = new International(newProfile, getSelectedMajor(), creditsCompleted, studyAbroad);
                } else{
                    newStudent = new NonResident(newProfile, getSelectedMajor(), creditsCompleted);
                }
        if(!isAllowedDate(newStudent)){
            return;
        }
        String reformattedDOB = dobReformater(dobRoster.getValue().toString());
        if(this.roster.add(newStudent)){
            rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + reformattedDOB + " was added to the roster.");
            return;
        }
        rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + reformattedDOB + " is already in the roster.");
    }

    /**
     * Reformats a date of birth returned from a Date Picker node
     * to make it correctly ordered for the Date constructor.
     * @param wrongDOB the date of birth from the Date Picker.
     * @return the date as a string in the format "mm/dd/yyyy".
     */
    private String dobReformater(String wrongDOB) {
        StringTokenizer splitDate = new StringTokenizer(wrongDOB, "-");
        int year = Integer.parseInt(splitDate.nextToken());
        int month = Integer.parseInt(splitDate.nextToken());
        int day = Integer.parseInt(splitDate.nextToken());
        return(month + "/" + day + "/" + year);
    }

    /**
     * Determines which major button
     * the user has selected.
     * @return the major that is currently selected as a string.
     */
    private String getSelectedMajor() {
        String selectedMajor;
        if(btBAIT.isSelected()){
            selectedMajor = "BAIT";
        } else if(btCS.isSelected()){
            selectedMajor = "CS";
        } else if(btECE.isSelected()){
            selectedMajor = "EE";
        } else if(btITI.isSelected()){
            selectedMajor = "ITI";
        } else {
            selectedMajor = "MATH";
        }
        return selectedMajor;
    }

    /**
     * Helper method to check if the user has selected a valid
     * amount of inputs in the Roster tab for the desired action.
     * @return true is valid amount of inputs, false if not.
     */
    private boolean isValidRosterSelection() {
        if(fnameRoster == null || fnameRoster.getText().trim().isEmpty() || isNotText(fnameRoster.getText())){
            rosterOutput.setText("Please enter a valid first name.");
            return false;
        }
        if(lnameRoster == null || lnameRoster.getText().trim().isEmpty() || isNotText(lnameRoster.getText())){
            rosterOutput.setText("Please enter a valid last name.");
            return false;
        }
        if(dobRoster.getValue() == null || dobRoster.getValue().toString().trim().isEmpty()){
            rosterOutput.setText("Please enter a valid date of birth.");
            return false;
        }
        if(buttonGroupMajor.getSelectedToggle() == null){
            rosterOutput.setText("Please select a major.");
            return false;
        }
        if(creditsRoster == null || creditsRoster.getText().trim().isEmpty() || notAllowedCredits(creditsRoster.getText())){
            rosterOutput.setText("Please enter a valid credit amount.");
            return false;
        }
        if(btGroup_isResident.getSelectedToggle() == null){
            rosterOutput.setText("Please select a valid student type.");
            return false;
        }
        if(btTriState.isSelected() && btGroup_State.getSelectedToggle() == null){
            rosterOutput.setText("Please select a state.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to check if the user has selected a valid
     * amount of inputs in the Roster tab for the desired action.
     * @return true is valid amount of inputs, false if not.
     */
    private boolean isValidRosterSelectionAddition() {
        if (fnameRoster == null || fnameRoster.getText().trim().isEmpty() || isNotText(fnameRoster.getText())) {
            rosterOutput.setText("Please enter a valid first name.");
            return false;
        }
        if (lnameRoster == null || lnameRoster.getText().trim().isEmpty() || isNotText(lnameRoster.getText())) {
            rosterOutput.setText("Please enter a valid last name.");
            return false;
        }
        if (dobRoster.getValue() == null || dobRoster.getValue().toString().trim().isEmpty()) {
            rosterOutput.setText("Please enter a valid date of birth.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to check if the user has selected a valid
     * amount of inputs in the Enrollment tab for the desired action.
     * @return true is valid amount of inputs, false if not.
     */
    private boolean isValidEnrollmentSelection() {
        if(fnameEnrollment == null || fnameEnrollment.getText().trim().isEmpty() || isNotText(fnameEnrollment.getText())){
            enrollmentOutput.setText("Please enter a valid first name.");
            return false;
        }
        if(lnameEnrollment == null || lnameEnrollment.getText().trim().isEmpty() || isNotText(lnameEnrollment.getText())){
            enrollmentOutput.setText("Please enter a valid last name.");
            return false;
        }
        if(dobEnrollment.getValue() == null || dobEnrollment.getValue().toString().trim().isEmpty()){
            enrollmentOutput.setText("Please enter a valid date of birth.");
            return false;
        }
        if(creditsEnrollment == null || creditsEnrollment.getText().trim().isEmpty() || notAllowedCredits(creditsEnrollment.getText())){
            enrollmentOutput.setText("Please enter a valid credit amount.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to check if the user has selected a valid
     * amount of inputs in the Enrollment tab for the desired action.
     * @return true is valid amount of inputs, false if not.
     */
    private boolean isValidEnrollmentSelectionAddition() {
        if (fnameEnrollment == null || fnameEnrollment.getText().trim().isEmpty() || isNotText(fnameEnrollment.getText())) {
            enrollmentOutput.setText("Please enter a valid first name.");
            return false;
        }
        if (lnameEnrollment == null || lnameEnrollment.getText().trim().isEmpty() || isNotText(lnameEnrollment.getText())) {
            enrollmentOutput.setText("Please enter a valid last name.");
            return false;
        }
        if (dobEnrollment.getValue() == null || dobEnrollment.getValue().toString().trim().isEmpty()) {
            enrollmentOutput.setText("Please enter a valid date of birth.");
            return false;
        }
        return true;
    }

    /**
     * Helper method to check if the user has selected a valid
     * amount of inputs in the Scholarship tab for the desired action.
     * @return true is valid amount of inputs, false if not.
     */
    private boolean isValidScholarSelection() {
        if(fnameScholar == null || fnameScholar.getText().trim().isEmpty() || isNotText(fnameScholar.getText())){ // need to make it so u cant type random symbols.
            scholarOutput.setText("Please enter a valid first name.");
            return false;
        }
        if(lnameScholar == null || lnameScholar.getText().trim().isEmpty() || isNotText(lnameScholar.getText())){
            scholarOutput.setText("Please enter a valid last name.");
            return false;
        }
        if(dobScholar.getValue() == null || dobScholar.getValue().toString().trim().isEmpty()){
            scholarOutput.setText("Please enter a valid date of birth.");
            return false;
        }
        if(amountScholar == null || amountScholar.getText().trim().isEmpty() || isNotNumeric(amountScholar.getText())){
            scholarOutput.setText("Please enter a valid scholarship amount.");
            return false;
        }
        return true;
    }

    /**
     * Run when the remove button is clicked in the Roster tab.
     * Removes the specified student from the roster if they
     * are in it.
     */
    @FXML
    protected void onRemoveButtonClick() {
        if(!isValidRosterSelectionAddition()){
            return;
        }
        String dob = dobReformater(dobRoster.getValue().toString());
        Profile newProfile = new Profile(lnameRoster.getText(),fnameRoster.getText(),dob);
        Student newStudent = this.roster.getStudent(newProfile);
        String reformattedDOB = dobReformater(dobRoster.getValue().toString());
        if(newStudent != null) {
            if (this.roster.remove(newStudent)) {
                rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + reformattedDOB + " removed from the Roster.");
                return;
            }
        }
        rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + reformattedDOB + " is not in the Roster.");
    }

    /**
     * Run when the Change Major button is clicked in the Roster tab.
     * Changes the major of the specified student if they are valid.
     */
    @FXML
    protected void onChangeButtonClick() {
        if(!isValidRosterSelectionAddition()){
            return;
        }
        String dob = dobReformater(dobRoster.getValue().toString());
        Profile profile = new Profile(lnameRoster.getText(), fnameRoster.getText(), dob);
        Resident tempResident = new Resident(profile,Major.UNDEFINED.toString(), Constant.UNDEFINED_CREDITS.getValue());
        String reformattedDOB = dobReformater(dobRoster.getValue().toString());
        if (this.roster.contains(tempResident)) {                        //checks if the student is actually in the roster.
            if(this.roster.replaceMajor(tempResident,getSelectedMajor())){            //checks if the major can/should be replaced.
                rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + reformattedDOB + " major changed to " + getSelectedMajor());
            } else {
                rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + reformattedDOB + " already has this major");
            }
        } else {
            rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + reformattedDOB + " is not in the Roster.");
        }
    }

    /**
     * Run when the Load from File button is clicked in the Roster tab.
     * Reads a from a text file a list of students to be added to the roster.
     */
    @FXML
    protected void onLoadButtonClick() {
        readFile();
    }

    /**
     * Runs when the Enroll button is clicked in the Enrollment tab.
     * Enrolls a specified student to the enrollment if they are valid.
     */
    @FXML
    protected void onEnrollButtonClick() {
        enrollmentOutput.setText("");
        if(!isValidEnrollmentSelection()){
            return;
        }
        String reformattedDOB = dobReformater(dobEnrollment.getValue().toString());
        int enrollCreditsInt = Integer.parseInt(creditsEnrollment.getText());
        Profile enrollProfile = new Profile(lnameEnrollment.getText(),fnameEnrollment.getText(),reformattedDOB);
        EnrollStudent enrollStudent = new EnrollStudent(enrollProfile, enrollCreditsInt);
        if(this.enrollment.contains(enrollStudent)){
            if(this.enrollment.isAlreadyTaking(enrollStudent,enrollCreditsInt)) {
                enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + reformattedDOB + " already is enrolled in " + enrollCreditsInt + " credits.");
            } else{
                Student student = this.roster.getStudent(enrollProfile);
                if(student.isValid(enrollCreditsInt)){
                    this.enrollment.setEnrollCredits(enrollStudent, enrollCreditsInt);
                    enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + reformattedDOB + " enrolled credits changed to " + enrollCreditsInt);
                }else{
                    enrollmentOutput.setText(student.getType() + " " + enrollCreditsInt + ": invalid credit hours.");
                }
            }
        } else if(this.roster.contains(enrollProfile)){
            Student student = this.roster.getStudent(enrollProfile);
            if(student.isValid(enrollCreditsInt)){
                this.enrollment.add(enrollStudent);
                enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + reformattedDOB + " enrolled " + enrollCreditsInt + " credits.");
            }else{
                enrollmentOutput.setText(student.getType() + " " + enrollCreditsInt + ": invalid credit hours.");
            }
        } else {
            enrollmentOutput.setText("Cannot enroll: " + fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + reformattedDOB + " is not in the roster.");
        }
    }

    /**
     * Runs when the Drop button is clicked in the Enrollment tab.
     * Drops a specified student from the enrollment if they are valid.
     */
    @FXML
    protected void onDropButtonClick() {
        if(!isValidEnrollmentSelectionAddition()){
            return;
        }
        String reformattedDOB = dobReformater(dobEnrollment.getValue().toString());
        Profile dropProfile = new Profile(lnameEnrollment.getText(),fnameEnrollment.getText(),reformattedDOB);
        EnrollStudent dropStudent = this.enrollment.getEnrollStudent(dropProfile);
        if (!(dropStudent == null)) {
            if (this.enrollment.contains(dropStudent)) {
                this.enrollment.remove(dropStudent);
                enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + reformattedDOB  + " dropped.");
                return;
            }
        }
        enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + reformattedDOB  + " is not enrolled.");
    }

    /**
     * Runs when the Update Scholarship button is clicked in the Scholarship tab.
     * Updates the scholarship of the specified student.
     */
    @FXML
    protected void onUpdateButtonClick() {
        scholarOutput.setText("");
        if(!isValidScholarSelection()){
            return;
        }
        String reformattedDOB = dobReformater(dobScholar.getValue().toString());
        Profile profile = new Profile(lnameScholar.getText(),fnameScholar.getText(),reformattedDOB);
        Student student = this.roster.getStudent(profile);
        EnrollStudent enrollStudent = this.enrollment.getEnrollStudent(profile);
        if(!isValidUpdate(student,enrollStudent,reformattedDOB,profile)){
            return;
        }
        Resident resident = (Resident) student;
        if (this.roster.contains(resident)) {
            if(resident.getScholarship() == Integer.parseInt(amountScholar.getText())){
                scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + reformattedDOB + " already has this scholarship");
                return;
            }
            if(this.roster.replaceScholar(resident,amountScholar.getText())){
                if(resident.getScholarship() == 0) {
                    resident.setScholarship(Integer.parseInt(amountScholar.getText()));
                    scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + reformattedDOB + " awarded a scholarship of $" + amountScholar.getText());
                }
                else {
                    resident.setScholarship(Integer.parseInt(amountScholar.getText()));
                    scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + reformattedDOB + ": scholarship amount updated.");
                }
            } else {
                scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + reformattedDOB + " " + student.getType() + " is not eligible for the scholarship.");
            }
        } else {
            scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + reformattedDOB + " is not in the Roster.");
        }
    }

    /**
     * Helper method for the onUpdateButtonClick method that
     * checks if the student is valid before continuing.
     * @param student student to check
     * @param enrollStudent enrollStudent to check
     * @param reformattedDOB reformattedDOB to check
     * @param profile profile to check
     * @return true if valid, false if not.
     */
    private boolean isValidUpdate(Student student, EnrollStudent enrollStudent, String reformattedDOB, Profile profile) {
        if(student == null){
            scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + reformattedDOB + " is not in the Roster.");
            return false;
        }
        if(enrollStudent == null){
            scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + reformattedDOB + " is not in the Enrollment.");
            return false;
        }
        if(isNotNumeric(amountScholar.getText())){
            scholarOutput.setText("Amount is not an integer.");
            return false;
        }
        if(!student.isResident() || !(enrollment.getEnrollStudent(profile).getCreditsEnrolled() >= TuitionValues.FULL_TIME_MIN.getValue())){
            scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + reformattedDOB + " is not eligible for the scholarship.");
            return false;
        }
        if(Integer.parseInt(amountScholar.getText()) > TuitionValues.MAX_SCHOLARSHIP.getValue() || Integer.parseInt(amountScholar.getText()) <= 0){
            scholarOutput.setText(amountScholar.getText() + ": invalid amount.");
            return false;
        }
        return true;
    }

    /**
     * Runs when the Print by Profile button is clicked in the
     * Display tab. Prints the roster sorted by profile.
     */
    @FXML
    protected void onPrintButtonClick() {
        if(!this.roster.isEmpty()){
            this.roster.sort(SortType.PROFILE);
            displayOutput.setText("Roster sorted by Profile:\n" + "\n" + this.roster.printRoster());
            return;
        }
        displayOutput.setText("The Roster is empty!");
    }

    /**
     * Runs when the Print by Major button is clicked in the
     * Display tab. Prints the roster sorted by major.
     */
    @FXML
    protected void onPrintMajorButtonClick() {
        if(!this.roster.isEmpty()){
            this.roster.sort(SortType.MAJOR);
            displayOutput.setText("Roster sorted by Major:\n" + "\n" + this.roster.printRoster());
            return;
        }
        displayOutput.setText("The Roster is empty!");
    }

    /**
     * Runs when the Print by Standing button is clicked in the
     * Display tab. Prints the roster sorted by standing.
     */
    @FXML
    protected void onPrintStandingButtonClick() {
        if(!this.roster.isEmpty()){
            this.roster.sort(SortType.STANDING);
            displayOutput.setText("Roster sorted by Standing:\n" + "\n" + this.roster.printRoster());
            return;
        }
        displayOutput.setText("The Roster is empty!");
    }

    /**
     * Runs when the RBS button is clicked in the Display tab.
     * Prints a list of the students in RBS.
     */
    @FXML
    protected void onRBSButtonClick() {
        printList("RBS");
    }

    /**
     * Runs when the SAS button is clicked in the Display tab.
     * Prints a list of the students in SAS.
     */
    @FXML
    protected void onSASButtonClick() {
        printList("SAS");
    }

    /**
     * Runs when the SC&I button is clicked in the Display tab.
     * Prints a list of the students in SC&I.
     */
    @FXML
    protected void onSCIButtonClick() {
        printList("SC&I");
    }

    /**
     * Runs when the SOE button is clicked in the Display tab.
     * Prints a list of the students in SOE.
     */
    @FXML
    protected void onSOEButtonClick() {
        printList("SOE");
    }

    /**
     * Runs when the Print Enrolled Students button is clicked in the Display tab.
     * Prints a list of the students enrolled in the current semester.
     */
    @FXML
    protected void onPrintEnrolledButtonClick() {
        displayOutput.setText(this.enrollment.print());
    }

    /**
     * Runs when the Print Tuition Due button is clicked in the Display tab.
     * Prints a list of tuition's due for the students enrolled in the current semester.
     */
    @FXML
    protected void onPrintTuitionButtonClick() {
        displayOutput.setText(this.enrollment.printTuition(this.roster));
    }

    /**
     * Runs when the Semester End button is clicked in the Display tab.
     * Updates the credits completed by each student enrolled, and displays list
     * of students eligible for graduation.
     */
    @FXML
    protected void onSemesterEndButtonClick() {
        displayOutput.setText(this.enrollment.semesterEnd(this.roster));
    }

    /**
     * Prints a list of the students from a specified school.
     * @param school school that you want to list from.
     */
    private void printList(String school){
            displayOutput.setText("Students in " + school + " :\n" + this.roster.list(school));
    }

    /**
     * Runs when the Resident radio button is selected.
     * Enables and disables appropriate dependent radio buttons.
     */
    @FXML
    protected void onResSelect() {
        btTriState.setDisable(true);
        btInternational.setDisable(true);
        boxStudyAbroad.setDisable(true);
        btNY.setDisable(true);
        btCT.setDisable(true);
        btNY.setSelected(false);
        btCT.setSelected(false);
        boxStudyAbroad.setSelected(false);
        btTriState.setSelected(false);
        btInternational.setSelected(false);
    }

    /**
     * Runs when the NonResident radio button is selected.
     * Enables and disables appropriate dependent radio buttons.
     */
    @FXML
    protected void onNonResSelect() {
        btTriState.setDisable(false);
        btInternational.setDisable(false);
    }

    /**
     * Runs when the TriState radio button is selected.
     * Enables and disables appropriate dependent radio buttons.
     */
    @FXML
    protected void onTristateSelect() {
        btNY.setDisable(false);
        btCT.setDisable(false);
        boxStudyAbroad.setDisable(true);
        boxStudyAbroad.setSelected(false);
    }

    /**
     * Runs when the International radio button is selected.
     * Enables and disables appropriate dependent radio buttons.
     */
    @FXML
    protected void onInternationalSelect() {
        btNY.setDisable(true);
        btCT.setDisable(true);
        btNY.setSelected(false);
        btCT.setSelected(false);
        boxStudyAbroad.setDisable(false);
    }

    /**
     * Helper method for add and enroll command to check if the amount of credits are valid.
     * @param inputCredits credits to check.
     * @return true if they are not valid, false if not.
     */
    private boolean notAllowedCredits(String inputCredits) {
        if(isNotNumeric(inputCredits)){               //checks if the credits are a number.
            System.out.println("Credits completed invalid: not an integer!");
            return true;
        }
        if(!(Double.parseDouble(inputCredits) % 1 == 0)){   //checks if the credits are an integer.
            System.out.println("Credits completed invalid: not an integer!");
            return true;
        }
        if (Double.parseDouble(inputCredits) < 0) {   //checks if the credits given are a valid amount.
            System.out.println("Credits completed invalid: cannot be negative!");
            return true;
        }
        return false;
    }

    /**
     * Checks if a given string is not numeric.
     * @param string string to check.
     * @return true if not numeric, false if is.
     */
    private static boolean isNotNumeric(String string) {
        try {
            Double.parseDouble(string);
            return false;
        } catch(NumberFormatException e){
            return true;
        }
    }

    /**
     * Checks if a given string contains any digits.
     * @param string string to be checked.
     * @return true if contains any digits, false if not.
     */
    private static boolean isNotText(String string) {
        char[] chars = string.toCharArray();
        for(char c : chars){
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method for the add() method, checks if a student is allowed to
     * be entered into the roster.
     * @param student student's date of birth.
     * @return false if the student fails any checks, true otherwise.
     */
    private boolean isAllowedDate(Student student){
        Date birthday = student.getProfile().getDob();
        Date today = new Date();
        String dob = birthday.toString();
        if (!birthday.isValid()) {                      //checks if birthday is a valid date.
            rosterOutput.setText("DOB invalid " + dob +  " not a valid calendar date!");
            return false;
        }
        if (birthday.compareTo(today) >= 0) {           //checks if birthday is beyond current date.
            rosterOutput.setText("DOB invalid " + dob + " not a valid calendar date!");
            return false;
        }
        if (!isAllowedAge(today, birthday)) {           //checks if birthday does not meet age requirement.
            rosterOutput.setText("DOB invalid: " + dob + " younger than 16 years old.");
            return false;
        }
        return true;
    }

    /**
     * Helper method for isAllowedDate() method, checks if student is allowed age.
     * @param today date object containing current day.
     * @param dob the student's date of birth.
     * @return false if the student is too young, true otherwise.
     */
    private boolean isAllowedAge (Date today, Date dob){
        int yearDifference = today.getYear() - dob.getYear();
        int monthDifference = today.getMonth() - dob.getMonth();
        int dayDifference = today.getDay() - dob.getDay();
        if (yearDifference > Constant.MINIMUM_AGE.getValue()) {         //checks if student is of age.
            return true;
        }
        if (yearDifference < Constant.MINIMUM_AGE.getValue()) {
            return false;
        }
        if (monthDifference > 0) {
            return true;
        }
        if (monthDifference < 0) {
            return false;
        }
        return dayDifference >= 0;
    }

    /**
     * Helper method for the readFile() method.
     * Process commands input from the file.
     * @param linescanner scanner for each line of the file.
     * @param operationCode operation code for given command.
     * @return string representation of student that was added.
     */
    private String processCommand(Scanner linescanner, String operationCode) {
        return switch (operationCode) {
            case "AR" ->                                                     // add a Resident student, for example, AR John Doe 4/3/2003 CS 29
                    (addResident(linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next()));
            case "AN" ->                                                     // add a NonResident student, for example, AN Leo Jones 4/21/2006 ITI 20
                    (addNonResident(linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next()));
            case "AT" ->                                                     // add a Tri state student, for example, AT Emma Miller 2/28/2003 CS 15 NY
                    (addTriState(linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next()));
            case "AI" ->                                                     // add an International student, for example, AI Oliver Chang 11/30/2000 BAIT 78 false
                    (addInternational(linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next(), linescanner.next()));
            default -> ("shouldn't get here");
        };
    }

    /**
     * Reads from an external file and recursively calls read
     * commands. In this case of project 2, it adds a
     * list of students to the roster.
     */
    private void readFile() {
        StringBuilder toReturn = new StringBuilder();
        try {
            File studentList = new File("studentList.txt");
            Scanner fileScanner = new Scanner(studentList);
            String lineCommand;
            toReturn.append("Students loaded to the Roster:\n" + "\n");
            while (fileScanner.hasNextLine()){
                lineCommand = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(lineCommand);
                lineScanner.useDelimiter(",|\n");
                String operationCode = "A" + lineScanner.next();
                toReturn.append(processCommand(lineScanner, operationCode));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        rosterOutput.setText(toReturn.toString());
    }

    /**
     * Adds a student to the Roster.
     * @param student student to be added.
     */
    private String add(Student student) {
        StringBuilder toReturn = new StringBuilder();
        String fname = student.getProfile().getFname();
        String lname = student.getProfile().getLname();
        String dob = student.getProfile().getDob().toString();
        if (this.roster.add(student))
            toReturn.append(fname).append(" ").append(lname).append(" ").append(dob).append(" added to the Roster." + "\n");
        return(toReturn.toString());
    }

    /**
     * Adds a Resident to the roster if it passes all validity checks.
     * @param fname first name of the student.
     * @param lname last name of the student.
     * @param dob date of birth of the student.
     * @param major major of the student.
     * @param creditsCompleted amount of credits completed by the student.
     */
    private String addResident(String fname, String lname, String dob, String major, String creditsCompleted) {
        Profile newProfile = new Profile(lname,fname,dob);
        Resident newResident = new Resident(newProfile, major, Integer.parseInt(creditsCompleted));
        return(add(newResident));
    }

    /**
     * Adds a Non-Resident to the roster if it passes all validity checks.
     * @param fname first name of the student.
     * @param lname last name of the student.
     * @param dob date of birth of the student.
     * @param major major of the student.
     * @param creditsCompleted amount of credits completed by the student.
     */
    private String addNonResident(String fname, String lname, String dob, String major, String creditsCompleted) {
        Profile newProfile = new Profile(lname,fname,dob);
        NonResident newNonResident = new NonResident(newProfile, major, Integer.parseInt(creditsCompleted));
        return(add(newNonResident));
    }

    /**
     * Adds a Tri-State student to the roster if it passes all validity checks.
     * @param fname first name of the student.
     * @param lname last name of the student.
     * @param dob date of birth of the student.
     * @param major major of the student.
     * @param creditsCompleted amount of credits completed by the student.
     * @param state state where the student lives.
     */
    private String addTriState(String fname, String lname, String dob, String major, String creditsCompleted,String state) {
        Profile newProfile = new Profile(lname,fname,dob);
        TriState newTriState = new TriState(newProfile, major, Integer.parseInt(creditsCompleted), state);
        return(add(newTriState));
    }

    /**
     * Adds an International student to the roster if it passes all validity checks.
     * @param fname first name of the student.
     * @param lname last name of the student.
     * @param dob date of birth of the student.
     * @param major major of the student.
     * @param creditsCompleted amount of credits completed by the student.
     * @param isAbroad whether the student is studying abroad or not.
     */
    private String addInternational(String fname, String lname, String dob, String major, String creditsCompleted, String isAbroad) {
        Profile newProfile = new Profile(lname,fname,dob);
        International newInternational = new International(newProfile, major, Integer.parseInt(creditsCompleted), (isAbroad.equals("true")));
        return(add(newInternational));
    }
}