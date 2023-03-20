package TuitionManager;

/**
 * The Roster class acts as a database for the students, storing
 * an array of students and the amount of students in the roster.
 * @author Dylan Turner, Noor Hasan
 */
public class Roster {
    private Student[] roster;
    private int size;

    /**
     * Constructor for the Roster Object.
     * Initial size is 4.
     */
    public Roster(){
        this.roster = new Student[Constant.INITIAL_SIZE.getValue()];
        this.size = 0;
    }

    /**
     * Determine the index of a student in the roster if they are in it.
     * @param student student to look for.
     * @return the index of the desired student's location in the roster.
     */
    private int find(Student student) {
        for(int i = 0; i < this.roster.length;i++){
            if(student.equals(this.roster[i])){
                return i;
            }
        }
        return (Constant.NOT_FOUND.getValue());
    }

    /**
     * Searches the roster array for a student that has the given profile
     * and returns a reference to said student.
     * @param profile profile to search for
     * @return a reference to the student containing that profile.
     */
    public Student getStudent(Profile profile) {
        for(int i = 0; i < this.size; i++) {
            if(roster[i].getProfile().equals(profile)){
                return roster[i];
            }
        }
//        System.out.println("poopstudent null");
        return null;
    }

    /**
     * Checks if the roster is currently empty.
     * @return true if the roster is empty, false if not.
     */
    public boolean isEmpty() {
        return(this.size == 0);
    }

    /**
     * Increases the size of the roster by 4 when necessary.
     */
    private void grow() {
        Student[] newRoster = new Student[this.size + Constant.INITIAL_SIZE.getValue()];
        for(int i = 0; i < this.roster.length;i++){
            newRoster[i] = this.roster[i];
        }
        this.roster = newRoster;
    }

    /**
     * Adds a student to the roster if it is allowed. If it is full then calls grow().
     * @param student the student to add to the roster.
     * @return true if the student could be added, false otherwise.
     */
    public boolean add(Student student) {
        if (find(student) == Constant.NOT_FOUND.getValue()) {
            this.roster[this.size] = student;
            this.size++;
            if (this.size == this.roster.length) {
                grow();
            }
            return true;
        }
        return false;
    }

    /**
     * Removes a student from the roster if they can be found.
     * Maintains the order of the array after removal
     * @param student student to be removed.
     * @return true if the student was removed, false otherwise.
     */
    public boolean remove(Student student) {
        int removePosition = find(student);
        if(removePosition != Constant.NOT_FOUND.getValue()){
            for(int i = removePosition; i < this.size; i++ ){
                this.roster[i] = this.roster[i + 1];
            }
            this.size--;
            return true;

        }
        return false;
    }

    /**
     * Checks if the student is currently in the roster.
     * @param student student to be looked for.
     * @return true if the student is in the roster, false otherwise.
     */
    public boolean contains(Student student) {
        for (Student value : this.roster) {
            if (student.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the specified profile can be found in the roster.
     * @param profile profile to be looked for.
     * @return true if the profile is in the roster, false otherwise.
     */
    public boolean contains(Profile profile) {
        for (Student value : this.roster) {
            if(value == null) {return false;}
            if (value.getProfile().equals(profile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method for the print() methods below.
     * Prints out the roster sorted as it currently is.
     */
    public String printRoster() {
        StringBuilder toReturn = new StringBuilder();
        for(int i = 0; i < this.size; i++) {
            if (this.roster[i] == null) {
                return "null print";
            } else {
                toReturn.append(this.roster[i].toString()).append("\n");
            }
        }
        return toReturn.toString();
    }

    /**
     * In place sorting algorithm used to sort the roster by a desired sort type.
     * This is a modified version of the selection sort algorithm.
     * @param type the sort type that should be used.
     */
    public void sort(SortType type)
    {
        int n = this.size;
        for (int i = 0; i < n-1; i++)
        {
            int min = i;
            for (int j = i+1; j < n; j++)
                if (isLess(roster[j],roster[min],type))
                    min = j;
            Student temp = this.roster[min];
            this.roster[min] = this.roster[i];
            this.roster[i] = temp;
        }
    }

    /**
     * Helper method for the selection sort algorithm. Determines whether one student
     * should be before another student based on the sort type.
     * @param s1 the first student.
     * @param s2 the second student.
     * @param sortType the desired sort type.
     * @return true if s1 should be before s2, false otherwise.
     */
    private boolean isLess(Student s1, Student s2, SortType sortType){
        switch(sortType){
            case PROFILE:
                if (s1.getProfile().compareTo(s2.getProfile()) < 0) {
                    return true;}
                if (s1.getProfile().compareTo(s2.getProfile()) > 0) {
                    return false;}
                if (s1.getProfile().compareTo(s2.getProfile()) <= 0) {
                    return true;}
            case MAJOR:
                return s1.getMajor().compareTo(s2.getMajor()) <= 0;
            case STANDING:
                return s1.getCredits() <= s2.getCredits();
            default:
                return true;
        }
    }

    /**
     * Helper method for the printList() method. Determines how many students
     * are in the specified school and displays them.
     @param school school that students should belong to.
     @return string list of all students in the school.
     */
    public String list(String school) {
        int amount = 0;
        StringBuilder toReturn = new StringBuilder();
        for(int i = 0; i < this.size; i++) {
            if (this.roster[i] != null && this.roster[i].getMajor().getSchool().equals(school)) {
                toReturn.append("\n").append(this.roster[i].toString());
                amount++;
            }
        }
        if(amount == 0){
            return("\n" + "There are no students in the Roster with that school.");
        }
        return toReturn.toString();
    }

    /**
     * Replaces a students major if it passes the validity checks.
     * @param student student whose major should be replaced
     * @param major the major that it should be changed to.
     */
    public boolean replaceMajor(Student student, String major){
        int position = find(student);
        if(position != -1){
            if(this.roster[position].getMajor().toString().equals(major)){ return false;}
            for (Major allowedMajor: Major.values()) {
                if (major.toUpperCase().equals(allowedMajor.toString())) {
                    this.roster[position].setMajor(allowedMajor);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a scholarship can be replaced for a given Resident student.
     * @param resident resident student to check.
     * @param scholarShip scholarship to check
     * @return true if it can be replaced, false if not
     */
    public boolean replaceScholar(Resident resident, String scholarShip) {
        if(resident.isEligible()){
            resident.setScholarship(Integer.parseInt(scholarShip));
            return true;
        } else {
            return false;
        }

    }
}
