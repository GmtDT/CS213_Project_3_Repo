package com.example.TuitionManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This is user interface that reads terminal commands and displays the results.
 * It can process single commands or sequences of commands. It stores the roster
 * and the enrollment.
 * @author Dylan Turner, Noor Hasan
 */
public class TuitionManager {
    private final Roster roster;
    private final Enrollment enrollment;

    /**
     * Constructor for the Tuition Manager. Creates a
     * new roster and enrollment and stores them.
     */
    public TuitionManager() {
        this.roster = new Roster();
        this.enrollment = new Enrollment();
    }

    /**
     * Continuously runs and processes user commands until it is
     * terminated by quit command "Q".
     */
    public void run() {
        Scanner lineScanner = new Scanner(System.in);
        System.out.println("Tuition Manager Running...");
        boolean stopProgram = false;
        while (!stopProgram) {                          //checks if the terminate command has been entered.
            String commandLine = lineScanner.nextLine();
            stopProgram = processCommand(commandLine);
        }
        lineScanner.close();
        System.out.println("Tuition Manager Terminated.");
    }

    /**
     * Helper method for run(), that stores and runs all valid commands.
     * @param commandLine next line to read inputs from.
     * @return true if terminate command given, false otherwise.
     */
    private boolean processCommand(String commandLine) {
        Scanner scanner = new Scanner(commandLine);
        StringTokenizer tokenizer = new StringTokenizer(commandLine);
        int tokenAmount = tokenizer.countTokens();
        if(tokenAmount == 0) {return false;}
        String operationCode = scanner.next();
        switch (operationCode) {
            case "A":                           //add a student to the roster.
                if(isRightAmount(Constant.SIX_REQUIRED,tokenAmount))
                    addResident(scanner.next(), scanner.next(), scanner.next(), scanner.next(), scanner.next());
                return false;
            case "R":                           //remove a student from the roster.
                if(isRightAmount(Constant.FOUR_REQUIRED,tokenAmount))
                    remove(scanner.next(), scanner.next(), scanner.next());
                return false;
            case "P":                           //prints the roster sorted by profile.
                if(isRightAmount(Constant.ONE_REQUIRED,tokenAmount))
                    if(!this.roster.isEmpty()){roster.print(); return false;}
                rosterEmpty();
                return false;
            case "PS":                          //prints the roster sorted by standing.
                if(isRightAmount(Constant.ONE_REQUIRED,tokenAmount)){
                    if(!this.roster.isEmpty()){roster.printByStanding(); return false;}
                    rosterEmpty();
                }
                return false;
            case "PC":                          //prints the roster sorted by major.
                if(isRightAmount(Constant.ONE_REQUIRED,tokenAmount)){
                    if(!this.roster.isEmpty()){roster.printBySchoolMajor(); return false;}
                    rosterEmpty();
                }
                return false;
            case "L":                           //lists all students under a specified school.
                if(isRightAmount(Constant.TWO_REQUIRED,tokenAmount))
                    printList(scanner.next().toUpperCase());
                return false;
            default:                            //invalid command.
                return processAdditionalCommands(scanner,operationCode,tokenAmount); // continues to helper method to process additional commands
        }
    }

    /**
     * Helper method for processCommand(), that continues switch case
     * to process additiional commands.
     * @param scanner scanner from the previous method.
     * @param operationCode operation code from previous method.
     * @param tokenAmount token amount from previous method.
     * @return true if terminate command given, false otherwise.
     */
    private boolean processAdditionalCommands(Scanner scanner,String operationCode, int tokenAmount) {
        switch (operationCode) {
            case "E":                                                     // enroll a student with the number of credits. For example, E John Doe 4/3/2003 24
                if(isRightAmount(Constant.FIVE_REQUIRED,tokenAmount))
                    enroll(scanner.next(),scanner.next(),scanner.next(),scanner.next());
                return false;
            case "D":                                                     // drop a student from the enrollment list, for example, D John Doe 4/3/2003
                if(isRightAmount(Constant.FOUR_REQUIRED,tokenAmount))
                    drop(scanner.next(),scanner.next(),scanner.next());
                return false;
            case "S":                                                     // award the scholarship to a resident student, for example, S Roy Brooks 9/9/1999 10000
                if(isRightAmount(Constant.FIVE_REQUIRED,tokenAmount))
                    awardScholar(scanner.next(),scanner.next(),scanner.next(),scanner.next());
                return false;
            case "PE":                                                    // display the current enrollment list, based on their order in the array.
                if(isRightAmount(Constant.ONE_REQUIRED,tokenAmount))
                    this.enrollment.print();
                return false;
            case "PT":                                                     // display the tuition due based on the credits enrolled, with the order in the enrollment array.
                if(isRightAmount(Constant.ONE_REQUIRED,tokenAmount))
                    this.enrollment.printTuition(this.roster);
                return false;
            case "SE":                                                     // semester end to add the enrolled credits to the credit completed in the roster
                if(isRightAmount(Constant.ONE_REQUIRED,tokenAmount))
                    this.enrollment.semesterEnd(this.roster);              // and print out the students who have already completed 120 credits or more.
                return false;
            case "C":                           //changes the major of a specified student.
                if(isRightAmount(Constant.FIVE_REQUIRED,tokenAmount))
                    changeMajor(scanner.next(), scanner.next(), scanner.next(), scanner.next());
                return false;
            case "Q":                           //terminate command.
                if(isRightAmount(Constant.ONE_REQUIRED,tokenAmount))
                    return true;
            default:
                return processAddCommands(scanner,operationCode,tokenAmount);
        }
    }

    /**
     * Helper method for processAdditionalCommands(), that continues
     * switch case to process the add student commands.
     * @param scanner scanner from the previous method.
     * @param operationCode operation code from previous method.
     * @param tokenAmount token amount from previous method.
     * @return true if terminate command given, false otherwise.
     */
    private boolean processAddCommands(Scanner scanner,String operationCode, int tokenAmount){
        switch(operationCode){
            case "LS":                                                     // load the student roster from an external file
                if(isRightAmount(Constant.TWO_REQUIRED,tokenAmount))
                    readFile(scanner.next());
                return false;
            case "AR":                                                     // add a Resident student, for example, AR John Doe 4/3/2003 CS 29
                if(isRightAmount(Constant.SIX_REQUIRED,tokenAmount))
                    addResident(scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next());
                return false;
            case "AN":                                                     // add a NonResident student, for example, AN Leo Jones 4/21/2006 ITI 20
                if(isRightAmount(Constant.SIX_REQUIRED,tokenAmount))
                    addNonResident(scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next());
                return false;
            case "AT":                                                     // add a Tri state student, for example, AT Emma Miller 2/28/2003 CS 15 NY
                if(hasState(tokenAmount))
                    addTriState(scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next());
                return false;
            case "AI":                                                     // add an International student, for example, AI Oliver Chang 11/30/2000 BAIT 78 false
                if(hasAbroad(scanner,tokenAmount))
                    addInternational(scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next(), scanner.next());
                return false;
            default:
                System.out.println(operationCode + " is an invalid command!");
                return false;
        }
    }

    /**
     * Checks whether the input command has enough tokens
     * to add an international student, and adds them if it does.
     * @param scanner scanner from processAddCommands()
     * @param tokenAmount tokenAmount from processAddCommands()
     * @return true if could add the international student, false if not.
     */
    private boolean hasAbroad(Scanner scanner, int tokenAmount) {
        if(Constant.SEVEN_REQUIRED.getValue() == tokenAmount){
            return true;
        } else if (Constant.SIX_REQUIRED.getValue() == tokenAmount){
            addInternational(scanner.next(),scanner.next(),scanner.next(),scanner.next(),scanner.next());
            return false;
        } else {
            System.out.println("Missing data in line command.");
            return false;
        }
    }

    /**
     * Healer method for the process command methods that checks if the
     * amount of input tokens is correct for the specific command.
     * @param requiredAmount amount of tokens that there should be.
     * @param tokenAmount amount of tokens that there are.
     * @return true if there is enough tokens, false if not.
     */
    private boolean isRightAmount(Constant requiredAmount, int tokenAmount){
        if(requiredAmount.getValue() == tokenAmount){
            return true;
        } else {
            System.out.println("Missing data in line command.");
            return false;
        }
    }

    /**
     * Checks whether the input command has enough tokens
     * to add a tri-state student, and adds them if it does.
     * @param tokenAmount amount of tokens that are there.
     * @return true if it has a state parameter, false if not.
     */
    private boolean hasState(int tokenAmount){
        if(Constant.SEVEN_REQUIRED.getValue() == tokenAmount){
            return true;
        } else if (Constant.SIX_REQUIRED.getValue() == tokenAmount){
            System.out.println("Missing the state code.");
            return false;
        } else {
            System.out.println("Missing data in line command.");
        }
        return false;
    }

    /**
     * Reads from an external file and recursively calls read
     * commands. In this case of project 2, it adds a
     * list of students to the roster.
     * @param fileName the name of the desired file.
     */
    private void readFile(String fileName) {
        try {
            File classSchedule = new File(fileName);
            Scanner fileScanner = new Scanner(classSchedule);
            String lineCommand;
            System.out.println("Students loaded to the roster.");
            while (fileScanner.hasNextLine()){
                lineCommand = fileScanner.nextLine();
                Scanner lineScanner = new Scanner(lineCommand);
                lineScanner.useDelimiter(",|\n");
                StringTokenizer tokenizer = new StringTokenizer(lineCommand,",");
                int tokenAmount = tokenizer.countTokens();
                String operationCode = "A" + lineScanner.next();
                processAddCommands(lineScanner, operationCode,tokenAmount);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a student to the Roster if it passes all the validity checks.
     * @param student student to be added.
     */
    private void add(Student student) {
        if (isAllowedDate(student)) {              //checks if the student is allowed to be added
            String fname = student.getProfile().getFname();
            String lname = student.getProfile().getLname();
            String dob = student.getProfile().getDob().toString();
            if (this.roster.add(student)) {
                System.out.println(fname + " " + lname + " " + dob + " added to the Roster.");
            } else {
                System.out.println(fname + " " + lname + " " + dob + " is already in the Roster.");
            }
        }
    }

    /**
     * Adds a Resident to the roster if it passes all validity checks.
     * @param fname first name of the student.
     * @param lname last name of the student.
     * @param dob date of birth of the student.
     * @param major major of the student.
     * @param creditsCompleted amount of credits completed by the student.
     */
    private void addResident(String fname, String lname, String dob, String major, String creditsCompleted) {
        Profile newProfile = new Profile(lname,fname,dob);
        if (!allowedCredits(creditsCompleted) || !isValidMajor(major)) { return;}
        Resident newResident = new Resident(newProfile, major, Integer.parseInt(creditsCompleted));
        add(newResident);
    }

    /**
     * Adds a Non-Resident to the roster if it passes all validity checks.
     * @param fname first name of the student.
     * @param lname last name of the student.
     * @param dob date of birth of the student.
     * @param major major of the student.
     * @param creditsCompleted amount of credits completed by the student.
     */
    private void addNonResident(String fname, String lname, String dob, String major, String creditsCompleted) {
        Profile newProfile = new Profile(lname,fname,dob);
        if (!allowedCredits(creditsCompleted)) { return;}
        NonResident newNonResident = new NonResident(newProfile, major, Integer.parseInt(creditsCompleted));
        add(newNonResident);
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
    private void addTriState(String fname, String lname, String dob, String major, String creditsCompleted,String state) {
        if(!state.equalsIgnoreCase("NY")  && !state.equalsIgnoreCase("NJ") && !state.equalsIgnoreCase("CT")){
            System.out.println(state + ": Invalid state code.");
            return;
        }
        Profile newProfile = new Profile(lname,fname,dob);
        if (!allowedCredits(creditsCompleted)) { return;}
        TriState newTriState = new TriState(newProfile, major, Integer.parseInt(creditsCompleted), state);
        add(newTriState);
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
    private void addInternational(String fname, String lname, String dob, String major, String creditsCompleted, String isAbroad) {
        Profile newProfile = new Profile(lname,fname,dob);
        if (!allowedCredits(creditsCompleted)) { return;}
        International newInternational = new International(newProfile, major, Integer.parseInt(creditsCompleted), (isAbroad.equals("true")));
        add(newInternational);
    }

    /**
     * Adds an International student to the roster if it passes all validity checks.
     * @param fname first name of the student.
     * @param lname last name of the student.
     * @param dob date of birth of the student.
     * @param major major of the student.
     * @param creditsCompleted amount of credits completed by the student.
     */
    private void addInternational(String fname, String lname, String dob, String major, String creditsCompleted) {
        Profile newProfile = new Profile(lname,fname,dob);
        if (!allowedCredits(creditsCompleted)) { return;}
        International newInternational = new International(newProfile, major, Integer.parseInt(creditsCompleted), false);
        add(newInternational);
    }

    /**
     * Enrolls a student to the enrollment if it passes all validity checks.
     * @param fname first name of the student.
     * @param lname last name of the student.
     * @param dob date of birth of the student.
     * @param enrollCredits amount of credits for the student to enroll in.
     */
    private void enroll(String fname, String lname, String dob, String enrollCredits) {
        if(!isNumeric(enrollCredits)){
            System.out.println("Credits enrolled is not an integer.");
            return;
        }
        int enrollCreditsInt = Integer.parseInt(enrollCredits);
        Profile enrollProfile = new Profile(lname, fname, dob);
        EnrollStudent enrollStudent = new EnrollStudent(enrollProfile, enrollCreditsInt);
        if(this.enrollment.contains(enrollStudent)){
            if(this.enrollment.isAlreadyTaking(enrollStudent,enrollCreditsInt)) {
                System.out.println(fname + " " + lname + " " + dob + " already is enrolled in " + enrollCreditsInt + " credits.");
            } else{
                Student student = this.roster.getStudent(enrollProfile);
                if(student.isValid(enrollCreditsInt)){
                    this.enrollment.setEnrollCredits(enrollStudent, enrollCreditsInt);
                    System.out.println(fname + " " + lname + " " + dob + " enrolled " + enrollCreditsInt + " credits.");
                }else{
                    System.out.println(student.getType() + " " + enrollCreditsInt + ": invalid credit hours.");
                }
            }
        } else if(this.roster.contains(enrollProfile)){
            Student student = this.roster.getStudent(enrollProfile);
            if(student.isValid(enrollCreditsInt)){
                this.enrollment.add(enrollStudent);
                System.out.println(fname + " " + lname + " " + dob + " enrolled " + enrollCreditsInt + " credits.");
            }else{
                System.out.println(student.getType() + " " + enrollCreditsInt + ": invalid credit hours.");
            }
        } else {
            System.out.println("Cannot enroll: " + fname + " " + lname + " " + dob + " is not in the roster.");
        }
    }

    /**
     * Removes a student from the roster given their profile if it passes validity checks.
     * @param lname the student's last name.
     * @param fname the student's first name.
     * @param dob the student's date of birth.
     */
    private void remove(String fname, String lname, String dob) {
        Profile profile = new Profile(lname, fname, dob);
        Resident tempResident = new Resident(profile,Major.UNDEFINED.toString(),Constant.UNDEFINED_CREDITS.getValue());
        if (this.roster.remove(tempResident)) {
            System.out.println(fname + " " + lname + " " + dob +" removed from the Roster.");
            return;
        }
        System.out.println(fname + " " + lname + " " + dob + " is not in the Roster.");
    }

    /**
     * Drops a student from the enrollment if it passes all validity checks.
     * @param fname first name of student.
     * @param lname last name of student.
     * @param dob date of birth of student.
     */
    private void drop(String fname, String lname, String dob) {
        Profile dropProfile = new Profile(lname, fname, dob);
        EnrollStudent dropStudent = this.enrollment.getEnrollStudent(dropProfile);
        if (!(dropStudent == null)) {
            if (this.enrollment.contains(dropStudent)) {
                this.enrollment.remove(dropStudent);
                System.out.println(fname + " " + lname + " " + dob + " dropped.");
                return;
            }
        }
        System.out.println(fname + " " + lname + " " + dob + " is not enrolled.");
    }

    /**
     * Awards a scholarship to a student if it is allowed.
     * @param fname first name of the student.
     * @param lname last name of student.
     * @param dob date of birth of student.
     * @param scholarShip amount to award to student.
     */
    private void awardScholar(String fname, String lname, String dob, String scholarShip) {
        Profile profile = new Profile(lname, fname, dob);
        Student student = this.roster.getStudent(profile);
        if(!isNumeric(scholarShip)){
            System.out.println("Amount is not an integer.");
            return;
        }
        if(!student.isResident() || !(enrollment.getEnrollStudent(profile).getCreditsEnrolled() >= TuitionValues.FULL_TIME_MIN.getValue())){
            System.out.println(fname + " " + lname + " " + dob + " part time student is not eligible for the scholarship.");
            return;
        }
        if(Integer.parseInt(scholarShip) > TuitionValues.MAX_SCHOLARSHIP.getValue() || Integer.parseInt(scholarShip) <= 0){
            System.out.println(scholarShip + ": invalid amount.");
            return;
        }
        Resident resident = (Resident) student;
        if (this.roster.contains(resident)) {
            if(resident.getScholarship() == Integer.parseInt(scholarShip)){
                System.out.println(fname + " " + lname + " " + dob + " already has this scholarship");
                return;
            }
            if(this.roster.replaceScholar(resident,scholarShip)){
                if(resident.getScholarship() == 0) {
                    resident.setScholarship(Integer.parseInt(scholarShip));
                    System.out.println(fname + " " + lname + " " + dob + " awarded a scholarship of $" + scholarShip);
                }
                else {
                    resident.setScholarship(Integer.parseInt(scholarShip));
                    System.out.println(fname + " " + lname + " " + dob + ": scholarship amount updated.");
                }
            } else {
                System.out.println(fname + " " + lname + " " + dob + " " + student.getType() + " is not eligible for the scholarship.");
            }
        } else {
            System.out.println(fname + " " + lname + " " + dob + " is not in the Roster.");
        }
    }

    /**
     * Changes the major of a student in the roster given it passes validity checks.
     * @param lname the student's last name.
     * @param fname the student's first name.
     * @param dob the student's date of birth.
     * @param major the student's major.
     */
    private void changeMajor(String fname, String lname, String dob, String major) {
        if (!isValidMajor(major)) {                     //checks if the major is valid first.
            return;
        }
        Profile profile = new Profile(lname, fname, dob);
        Resident tempResident = new Resident(profile,Major.UNDEFINED.toString(),Constant.UNDEFINED_CREDITS.getValue());
        if (this.roster.contains(tempResident)) {                        //checks if the student is actually in the roster.
            if(this.roster.replaceMajor(tempResident,major)){            //checks if the major can/should be replaced.
                System.out.println(fname + " " + lname + " " + dob + " major changed to " + major);
            } else {
                System.out.println(fname + " " + lname + " " + dob + " already has this major");
            }
        } else {
            System.out.println(fname + " " + lname + " " + dob + " is not in the Roster.");
        }
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
            System.out.println("DOB invalid " + dob + " not a valid calendar date!");
            return false;
        }
        if (birthday.compareTo(today) >= 0) {           //checks if birthday is beyond current date.
            System.out.println("DOB invalid " + dob + " not a valid calendar date!");
            return false;
        }
        if (!isAllowedAge(today, birthday)) {           //checks if birthday does not meet age requirement.
            System.out.println("DOB invalid: " + dob + " younger than 16 years old.");
            return false;
        }
        return true;
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
        if (Integer.parseInt(inputCredits) < 0) {   //checks if the credits given are a valid amount.
            System.out.println("Credits completed invalid: cannot be negative!");
            return false;
        }
        if(!(Double.parseDouble(inputCredits) % 1 == 0)){   //checks if the credits are an integer.
            System.out.println("Credits completed invalid: not an integer!");
            return false;
        }
        return true;
    }

    /**
     * Helper method for isAllowed() method, checks if student is allowed age.
     * param today date object containing current day.
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

    /**
     * Prints a list of the students from a specified school.
     * @param school school that you want to list from.
     */
    private void printList(String school){
        boolean allowedSchool = false;
        for (Major allowedMajor: Major.values()) {                          //first checks if the school is valid.
            if (school.toUpperCase().equals(allowedMajor.getSchool())) {
                allowedSchool = true;
                break;
            }
        }
        if(allowedSchool){
            System.out.println("* Students in " + school + " *");
            this.roster.list(school);
            System.out.println("* end of list **");
        } else {
            System.out.println("School doesn't exist: " + school);
        }
    }

    /**
     * Prints error code saying that the roster is empty.
     */
    private void rosterEmpty() {
        System.out.println("Student roster is empty!");
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
}