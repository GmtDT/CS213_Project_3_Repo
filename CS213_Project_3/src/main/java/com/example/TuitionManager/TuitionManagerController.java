package com.example.TuitionManager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class TuitionManagerController {

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
    private RadioButton btCN;
    @FXML
    private CheckBox boxStudyAbroad;


    //Enrollment Tab
    @FXML
    private TextField fnameEnrollment;
    @FXML
    private TextField lnameEnrollment;
    @FXML
    private DatePicker dobEnrollment;
    @FXML
    private TextField creditsEnrollment;

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
    protected void onAddButtonClick() {
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
}