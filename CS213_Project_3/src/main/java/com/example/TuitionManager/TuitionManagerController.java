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
        if(!isEnoughSelections()){
            //output error msg
            return;
        }
        if(btResident.isSelected()){
            //create a resident student
        }  else if(btTriState.isSelected()){
            if(btNY.isSelected()){
                String state = "NY";
            } else{
                String state = "CT";
            }
            //create tristate student
        } else if(btInternational.isSelected()){
            boolean studyAbroad = boxStudyAbroad.isSelected();
            //create international study abroad or not study abroad.
        } else{
            //create non res
        }
        //add student
    }

    private boolean isEnoughSelections(){
        if(btGroup_isResident == null){
            return false;
        }
        if(btTriState.isSelected() && btGroup_State == null){
            return false;
        }
        return true;
    }

    @FXML
    protected void onRemoveButtonClick() {
    }

    @FXML
    protected void onChangeButtonClick() {
    }

    @FXML
    protected void onLoadButtonClick() {
    }

    @FXML
    protected void onEnrollButtonClick() {
    }

    @FXML
    protected void onDropButtonClick() {
    }

    @FXML
    protected void onUpdateButtonClick() {
    }

    @FXML
    protected void onPrintButtonClick() {
    }

    @FXML
    protected void onPrintMajorButtonClick() {
    }

    @FXML
    protected void onPrintStandingButtonClick() {
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
}