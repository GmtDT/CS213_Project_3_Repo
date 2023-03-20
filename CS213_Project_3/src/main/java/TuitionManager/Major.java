package TuitionManager;

/**
 The Major Enum class stores the possible majors a student
 can have, and the course code/school associated with it.
 @author Dylan Turner, Noor Hasan
 */
public enum Major {

    BAIT("33:136","RBS"),
    CS ("01:198","SAS"),
    MATH("01:640","SAS"),
    ITI("04:547","SC&I"),
    EE("14:332","SOE"),
    UNDEFINED("","");

    private final String majorCode;
    private final String school;

    /**
     * Creates the Major
     * @param majorCode the major code for the value
     * @param school the school for the value.
     */
    Major(String majorCode,String school) {
        this.majorCode = majorCode;
        this.school = school;
    }

    /**
     *Getter method for the major code.
     * @return the major code.
     */
    public String getMajorCode(){
        return this.majorCode;
    }

    /**
     *Getter method for the school.
     * @return the school.
     */
    public String getSchool(){
        return this.school;
    }
}