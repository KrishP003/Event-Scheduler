package scheduler;

/**
 * Enum Class: Represents all Departments that a Contact can be a part of.
 * The order of declaration (in lexicographical order) determines the
 * functionality of the Department.compareTo method.
 * @author Dharmik Patel and Krish Patel
 */
public enum Department {
    BAIT("Business Analytics and Information Technology"),
    CS("Computer Science"),
    EE("Electrical Engineering"),
    ITI("Information Technology and Informatics"),
    MATH("Mathematics");
    private final String fullName;

    /**
     * This constructor is used by JVM, makes all the Location enums.
     * @param fullName the full name of the building
     */
    Department(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Overrides toString method to return a department's full name.
     * @return the timeslot in the specified format
     */
    @Override
    public String toString() {
        return fullName;
    }
}
