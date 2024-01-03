package scheduler;

/**
 * Enum Class: Represents all possible locations for an event.
 * The order of declaration (in lexicographical order) determines the
 * functionality of the Location.compareTo method.
 * @author Dharmik Patel and Krish Patel
 */
public enum Location {
    ARC103("Allison Road Classroom", "Busch"),
    HLL114("Hill Center", "Busch"),
    AB2225("Academic Building", "College Avenue"),
    MU302("Murray Hall", "College Avenue"),
    BE_AUD("Beck Hall", "Livingston"),
    TIL232("Tillet Hall", "Livingston");
    private final String building;
    private final String campus;

    /**
     * This constructor is used by JVM, makes all the Location enums.
     * @param building the full name of the building
     * @param campus the campus the building is on
     */
    Location(String building, String campus) {
        this.building = building;
        this.campus = campus;
    }

    /**
     * Overrides toString method to return a location in the
     * format: BUILDING_CODE (BUILDING_NAME, CAMPUS)
     * @return the location in the specified format
     */
    @Override
    public String toString() {
        return String.format("@%s (%s, %s)", this.name(), building, campus);
    }
}
