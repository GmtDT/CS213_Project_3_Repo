package com.example.TuitionManager;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TuitionManagerController {
    private final Roster roster = new Roster();
    private final Enrollment enrollment = new Enrollment();

    //Button Groups
    @FXML
    private ToggleGroup btGroup_Location;
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
    private RadioButton btMATH;
    @FXML
    private RadioButton btResident;
    @FXML
    private RadioButton btNonRes;
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

    @FXML
    protected void onAddButtonClick() {
        rosterOutput.setText("");
        if(!isValidRosterSelection()){
            return;
        }
        Student newStudent;
        Profile newProfile = new Profile(lnameRoster.getText(),fnameRoster.getText(),dobRoster.getValue().toString());
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
        if(this.roster.add(newStudent)){
            rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobRoster.getValue() + " was added to the roster.");
            return;
        }
        rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobRoster.getValue() + " is already in the roster.");
    }

    private String getSelectedMajor() {
        String selectedMajor;
        if(btBAIT.isSelected()){
            selectedMajor = "BAIT";
        } else if(btCS.isSelected()){
            selectedMajor = "CS";
        } else if(btECE.isSelected()){
            selectedMajor = "ECE";
        } else if(btITI.isSelected()){
            selectedMajor = "ITI";
        } else {
            selectedMajor = "MATH";
        }
        return selectedMajor;
    }

    //checks if the text field has valid entry, and if any buttons are not selected.
    private boolean isValidRosterSelection() {
        if(fnameRoster == null || fnameRoster.getText().trim().isEmpty() || isNotText(fnameRoster.getText())){ // need to make it so u cant type random symbols.
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
        if(creditsRoster == null || creditsRoster.getText().trim().isEmpty() || !allowedCredits(creditsRoster.getText())){
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

    private boolean isValidEnrollmentSelection() {
        if(fnameEnrollment == null || fnameEnrollment.getText().trim().isEmpty() || isNotText(fnameEnrollment.getText())){ // need to make it so u cant type random symbols.
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
        if(creditsEnrollment == null || creditsEnrollment.getText().trim().isEmpty() || !allowedCredits(creditsEnrollment.getText())){
            enrollmentOutput.setText("Please enter a valid credit amount.");
            return false;
        }
        return true;
    }

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
        if(amountScholar == null || amountScholar.getText().trim().isEmpty() || !isNumeric(amountScholar.getText())){
            scholarOutput.setText("Please enter a valid scholarship amount.");
            return false;
        }
        return true;
    }

    @FXML
    protected void onRemoveButtonClick() {
        Profile newProfile = new Profile(lnameRoster.getText(),fnameRoster.getText(),dobRoster.getValue().toString());
        Student newStudent = this.roster.getStudent(newProfile);
        if(newStudent != null) {
            if (this.roster.remove(newStudent)) {
                rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobRoster.getValue() + " removed from the Roster.");
                return;
            }
        }
        rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobRoster.getValue() + " is not in the Roster.");

    }

    @FXML
    protected void onChangeButtonClick() {
        if (!isValidMajor(getSelectedMajor())) {                     //checks if the major is valid first.
            return;
        }
        Profile profile = new Profile(lnameRoster.getText(), fnameRoster.getText(), dobRoster.getValue().toString());
        Resident tempResident = new Resident(profile,Major.UNDEFINED.toString(),Constant.UNDEFINED_CREDITS.getValue());
        if (this.roster.contains(tempResident)) {                        //checks if the student is actually in the roster.
            if(this.roster.replaceMajor(tempResident,getSelectedMajor())){            //checks if the major can/should be replaced.
                rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobRoster.getValue() + " major changed to " + getSelectedMajor());
            } else {
                rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobRoster.getValue() + " already has this major");
            }
        } else {
            rosterOutput.setText(fnameRoster.getText() + " " + lnameRoster.getText() + " " + dobRoster.getValue() + " is not in the Roster.");
        }
    }

    @FXML
    protected void onLoadButtonClick() {

    }

    @FXML
    protected void onEnrollButtonClick() {
        enrollmentOutput.setText("");
        isValidEnrollmentSelection();
        int enrollCreditsInt = Integer.parseInt(creditsEnrollment.getText());
        Profile enrollProfile = new Profile(lnameEnrollment.getText(),fnameEnrollment.getText(),dobEnrollment.getValue().toString());
        EnrollStudent enrollStudent = new EnrollStudent(enrollProfile, enrollCreditsInt);
        if(this.enrollment.contains(enrollStudent)){
            if(this.enrollment.isAlreadyTaking(enrollStudent,enrollCreditsInt)) {
                enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + dobEnrollment.getValue() + " already is enrolled in " + enrollCreditsInt + " credits.");
            } else{
                Student student = this.roster.getStudent(enrollProfile);
                if(student.isValid(enrollCreditsInt)){
                    this.enrollment.setEnrollCredits(enrollStudent, enrollCreditsInt);
                    enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + dobEnrollment.getValue() + " enrolled " + enrollCreditsInt + " credits.");
                }else{
                    enrollmentOutput.setText(student.getType() + " " + enrollCreditsInt + ": invalid credit hours.");
                }
            }
        } else if(this.roster.contains(enrollProfile)){
            Student student = this.roster.getStudent(enrollProfile);
            if(student.isValid(enrollCreditsInt)){
                this.enrollment.add(enrollStudent);
                enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + dobEnrollment.getValue() + " enrolled " + enrollCreditsInt + " credits.");
            }else{
                enrollmentOutput.setText(student.getType() + " " + enrollCreditsInt + ": invalid credit hours.");
            }
        } else {
            enrollmentOutput.setText("Cannot enroll: " + fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + dobEnrollment.getValue() + " is not in the roster.");
        }
    }

    @FXML
    protected void onDropButtonClick() {
        Profile dropProfile = new Profile(lnameEnrollment.getText(),fnameEnrollment.getText(),dobEnrollment.getValue().toString());
        EnrollStudent dropStudent = this.enrollment.getEnrollStudent(dropProfile);
        if (!(dropStudent == null)) {
            if (this.enrollment.contains(dropStudent)) {
                this.enrollment.remove(dropStudent);
                enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + dobEnrollment.getValue() + " dropped.");
                return;
            }
        }
        enrollmentOutput.setText(fnameEnrollment.getText() + " " + lnameEnrollment.getText() + " " + dobEnrollment.getValue() + " is not enrolled.");
    }

    @FXML
    protected void onUpdateButtonClick() {
        scholarOutput.setText("");
        isValidScholarSelection();
        Profile profile = new Profile(lnameScholar.getText(),fnameScholar.getText(),dobScholar.getValue().toString());
        Student student = this.roster.getStudent(profile);
        if(!isNumeric(amountScholar.getText())){
            scholarOutput.setText("Amount is not an integer.");
            return;
        }
        if(!student.isResident() || !(enrollment.getEnrollStudent(profile).getCreditsEnrolled() >= TuitionValues.FULL_TIME_MIN.getValue())){
            scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + dobScholar.getValue() + " part time student is not eligible for the scholarship.");
            return;
        }
        if(Integer.parseInt(amountScholar.getText()) > TuitionValues.MAX_SCHOLARSHIP.getValue() || Integer.parseInt(amountScholar.getText()) <= 0){
            scholarOutput.setText(amountScholar.getText() + ": invalid amount.");
            return;
        }
        Resident resident = (Resident) student;
        if (this.roster.contains(resident)) {
            if(resident.getScholarship() == Integer.parseInt(amountScholar.getText())){
                scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + dobScholar.getValue() + " already has this scholarship");
                return;
            }
            if(this.roster.replaceScholar(resident,amountScholar.getText())){
                if(resident.getScholarship() == 0) {
                    resident.setScholarship(Integer.parseInt(amountScholar.getText()));
                    scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + dobScholar.getValue() + " awarded a scholarship of $" + amountScholar.getText());
                }
                else {
                    resident.setScholarship(Integer.parseInt(amountScholar.getText()));
                    scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + dobScholar.getValue() + ": scholarship amount updated.");
                }
            } else {
                scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + dobScholar.getValue() + " " + student.getType() + " is not eligible for the scholarship.");
            }
        } else {
            scholarOutput.setText(fnameScholar.getText() + " " + lnameScholar.getText() + " " + dobScholar.getValue() + " is not in the Roster.");
        }
    }

    @FXML
    protected void onPrintButtonClick() {
        if(!this.roster.isEmpty()){
            this.roster.sort(SortType.PROFILE);
            displayOutput.setText("Roster sorted by profile:" + "\n" + this.roster.printRoster());
            return;
        }
        displayOutput.setText("The roster is empty.");
    }

    @FXML
    protected void onPrintMajorButtonClick() {
        if(!this.roster.isEmpty()){
            this.roster.sort(SortType.MAJOR);
            displayOutput.setText("Roster sorted by major:" + "\n" + this.roster.printRoster());
            return;
        }
        displayOutput.setText("The roster is empty.");
    }

    @FXML
    protected void onPrintStandingButtonClick() {
        if(!this.roster.isEmpty()){
            this.roster.sort(SortType.STANDING);
            displayOutput.setText("Roster sorted by standing:" + "\n" + this.roster.printRoster());
            return;
        }
        displayOutput.setText("The roster is empty.");
    }

    @FXML
    protected void onRBSButtonClick() {
    }

    @FXML
    protected void onSASButtonClick() {
    }

    @FXML
    protected void onSCIButtonClick() {
    }

    @FXML
    protected void onSOEButtonClick() {
    }

    @FXML
    protected void onPrintEnrolledButtonClick() {
    }

    @FXML
    protected void onPrintTuitionButtonClick() {
    }

    @FXML
    protected void onSemesterEndButtonClick() {
    }

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

    @FXML
    protected void onNonResSelect() {
        btTriState.setDisable(false);
        btInternational.setDisable(false);
    }

    @FXML
    protected void onTristateSelect() {
        btNY.setDisable(false);
        btCT.setDisable(false);
        boxStudyAbroad.setDisable(true);
        boxStudyAbroad.setSelected(false);
    }

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
     * @return true if they are valid, false if not.
     */
    private boolean allowedCredits(String inputCredits) {
        if(!isNumeric(inputCredits)){               //checks if the credits are a number.
            System.out.println("Credits completed invalid: not an integer!");
            return false;
        }
        if(!(Double.parseDouble(inputCredits) % 1 == 0)){   //checks if the credits are an integer.
            System.out.println("Credits completed invalid: not an integer!");
            return false;
        }
        if (Double.parseDouble(inputCredits) < 0) {   //checks if the credits given are a valid amount.
            System.out.println("Credits completed invalid: cannot be negative!");
            return false;
        }
        return true;
    }

    /**
     * Checks if a given string is numeric.
     * @param string string to check.
     * @return true if numeric, false if not.
     */
    private static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

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
     * Helper method for isAllowed() method, checks if a major is valid.
     * @param major the major in question.
     * @return false if major is not in the Major Enum class, true if it is.
     */
    private boolean isValidMajor (String major){
        for (Major validMajor : Major.values()) {
            if (major.toUpperCase().equals(validMajor.toString())) {
                return true;
            }
        }
        System.out.println("Major code invalid: " + major);
        return false;
    }
}