package scheduler;
/**
 * Enum Class: Represents all possible timeslots for an event.
 * The order of declaration (in time order) determines the functionality of
 * the Timeslot.compareTo method.
 * @author Dharmik Patel and Krish Patel
 */
public enum Timeslot {


    MORNING(10, 30),
    AFTERNOON(2, 0),
    EVENING(6, 30);
    public final static String LOCALE_AM = "AM";
    public final static String LOCALE_PM = "PM";
    private final int hour;
    private final int minute;


    /**
     * This constructor is used by JVM, makes all the Timeslot enums.
     * @param hour start hour of timeslot
     * @param minute start minute of timeslot
     */
    Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Getter method to get the field: hour.
     * @return start hour of the timeslot
     */
    public int getHour() {
        return hour;
    }

    /**
     * Getter method to get the field: minute.
     * @return start minute of the timeslot
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Overrides toString method to return a timeslot in the
     * format: [Start: HOUR:MIN LOCALE_AM|LOCALE_PM]
     * @return the timeslot in the specified format
     */
    @Override
    public String toString() {
        String locale = this.equals(Timeslot.MORNING) ? LOCALE_AM : LOCALE_PM;
        return String.format("[Start: %d:%02d %s]", hour, minute, locale);
    }

}
