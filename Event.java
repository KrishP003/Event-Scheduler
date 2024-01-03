package scheduler;

/**
 * Represents a specific event booked by the user.
 * @author Dharmik Patel and Krish Patel
 */
public class Event implements Comparable<Event> {

    public static final int MAX_DURATION = 120;
    public static final int MIN_DURATION = 30;

    private static final int MAX_MIN_IN_A_HOUR = 60;
    private final Date date;
    private final Timeslot startTime;
    private final Location location;
    private final Contact contact; //include the department name and email
    private final int duration; //in minutes

    /**
     * This constructor makes a INCOMPLETE EVENT (without Contact and
     * Duration).
     * <p>
     * Only use to make an event to compare an event to see if it already
     * exists. The contact and department do not matter in this case
     * because an Event-to-Event comparisons are only rely on date,
     * timeslot, and location.
     * @param date - specific date of the event
     * @param startTime - Timeslot of the event
     * @param location - Location of the event
     */
    public Event(Date date, Timeslot startTime, Location location){
        this.date = date;
        this.startTime = startTime;
        this.location = location;
        this.contact = null;
        this.duration = 0;
    }
    /**
     * This constructor makes a complete event.
     * @param date - specific date of the event
     * @param startTime - Timeslot of the event
     * @param location - Location of the event
     * @param contact - Department and Email of the person leading the event
     * @param duration - the length of the event
     */
    public Event(Date date, Timeslot startTime, Location location,
                 Contact contact, int duration) {
        this.date = date;
        this.startTime = startTime;
        this.location = location;
        this.contact = contact;
        this.duration = duration;
    }

    /**
     * Compares {@code this Event} and {@code anotherEvent} by date and
     * timeslots.
     * @param anotherEvent the event to be compared.
     * @return  0 if {@code this Event} occurs at same time as
     *          {@code anotherEvent};
     *          -1 if {@code this Event} occurs before
     *          {@code anotherEvent};
     *          +1 if {@code this Event} occurs after
     *          {@code anotherEvent};
     */
    @Override
    public int compareTo(Event anotherEvent) {
        if (date.equals(anotherEvent.date)) {
            return startTime.compareTo(anotherEvent.startTime);
        } else {
            return date.compareTo(anotherEvent.date);
        }
    }

    /**
     * Compares {@code this Event} and {@code anotherEvent} by campus and
     * building names lexicographically.
     * Depends fully on the oder of declaration of the constants in the
     * {@code Location Enum Class}
     * @param anotherEvent the event to be compared.
     * @return  0 if {@code this Event} occurs on same campus and building
     *          {@code anotherEvent};
     *          -1 if {@code this Event} location is lexicographically less
     *          than {@code anotherEvent} location;
     *          +1 if {@code this Event} location is lexicographically greater
     *          than {@code anotherEvent} location;
     */
    public int compareToByCampus(Event anotherEvent) {
        return location.compareTo(anotherEvent.location);
    }

    /**
     * Compares {@code this Event} and {@code anotherEvent} by department
     * names lexicographically.
     * Depends fully on the oder of declaration of the constants in the
     * {@code Department Enum Class}
     * @param anotherEvent the event to be compared.
     * @return  0 if {@code this Event} occurs for the same department
     *          {@code anotherEvent};
     *          -1 if {@code this Event} department is lexicographically
     *          less than {@code anotherEvent} department;
     *          +1 if {@code this Event} department is lexicographically
     *          greater than {@code anotherEvent} department;
     */
    public int compareToByDepartment(Event anotherEvent) {
        return contact.getDepartment().compareTo(
                anotherEvent.contact.getDepartment());
    }

    /**
     * Compares {@code this Event} and {@code objAnother}. Result is true if
     * and only if {@code objAnother}
     * is an Event object and has the same date, timeslot and location as
     * {@code this Event}.
     * @param  objAnother the Object to compare to
     * @return True if the objects are equal. False if the objects are not
     *          equal or the object is not instance of Event Class
     */
    @Override
    public boolean equals(Object objAnother) {
        if (objAnother instanceof Event) {
            Event eventToCompare = (Event) objAnother;
            return this.date.equals(eventToCompare.date) &&
                    this.startTime.equals(eventToCompare.startTime) &&
                    this.location.equals(eventToCompare.location);
        }
        return false;
    }

    /**
     * Returns a String representation of {@code this Event}.
     * Format: [Date] [Start Time] [End Time] Building Code
     *         (Building Name, Campus) [Department, email]
     * @return The properly formatted event.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s %s %s", date, startTime,
                getEndTime(), location, contact);
    }

    /**
     * Adds duration to start time, and deals with overflow.
     * Format: [End: Hour:Min Locale]
     * @return Returns string representation of the end time in the proper
     *         format.
     */
    private String getEndTime() {
        int endMin = startTime.getMinute() + duration % MAX_MIN_IN_A_HOUR;
        int endHour = startTime.getHour() + duration / MAX_MIN_IN_A_HOUR;
        while (!(endMin < MAX_MIN_IN_A_HOUR)) {
            endMin -= 60;
            endHour++;
        }
        //endHour%=MAX_HOUR;
        String locale = Timeslot.LOCALE_PM;
        final int hourToSwitchLocale = 12;
        if (startTime.equals(Timeslot.MORNING) &&
                endHour < hourToSwitchLocale) {
            locale = Timeslot.LOCALE_AM;
        }
        return String.format("[End: %d:%02d %s]", endHour, endMin, locale);
    }

    /**
     * Test Case #1: Tests if the equals method works
     * when two events are completely different.
     * <p>
     * The contact and durations do not matter, because
     * they are not used for comparisons (they do not
     * indicate if two events happen at the same time).
     */
    private static void testTwoCompletelyDifferentEvents() {
        Event e1 = new Event(new Date("09/06/2023"), Timeslot.AFTERNOON,
                Location.HLL114, new Contact(Department.ITI,
                "cs@rutgers.edu"), 60);
        Event e2 = new Event(new Date("03/06/2021"), Timeslot.MORNING,
                Location.ARC103, new Contact(Department.CS,
                "hi@rutgers.edu"), 60);
        boolean expectedOutput = false; //define expected output
        boolean actualOutput = e1.equals(e2);
        //call equals method to get actual output
        System.out.println("**Test #1: 2 completely different events");
        testResults(expectedOutput, actualOutput);
    }

    /**
     * Test Case #2: Tests if the equals method works
     * when 2 dates are the same, but the timeslots are different.
     * In this test case, the value of the locations does not matter
     * because of the order of comparisons in the equals method.
     * <p>
     * The contact and durations do not matter, because
     * they are not used for comparisons (they do not
     * indicate if two events happen at the same time).
     */
    private static void testTwoDatesSame() {
        Event e1 = new Event(new Date("09/06/2023"), Timeslot.AFTERNOON,
                Location.HLL114, new Contact(Department.ITI,
                "cs@rutgers.edu"), 60);
        Event e2 = new Event(new Date("09/06/2023"), Timeslot.MORNING,
                Location.ARC103, new Contact(Department.CS,
                "hi@rutgers.edu"), 60);
        boolean expectedOutput = false; //define expected output
        boolean actualOutput = e1.equals(e2);
        //call equals method to get actual output
        System.out.println("**Test #2: same dates, different timeslots");
        testResults(expectedOutput, actualOutput);
    }

    /**
     * Test Case #3: Tests if the equals method works
     * when 2 dates are the same, 2 timeslots are the same, but the
     * locations are different.
     * <p>
     * The contact and durations do not matter, because
     * they are not used for comparisons (they do not
     * indicate if two events happen at the same time).
     */
    private static void testTwoDatesSameAndTwoTimeslotsSame() {
        Event e1 = new Event(new Date("09/06/2023"), Timeslot.MORNING,
                Location.HLL114, new Contact(Department.ITI,
                "cs@rutgers.edu"), 60);
        Event e2 = new Event(new Date("09/06/2023"), Timeslot.MORNING,
                Location.ARC103, new Contact(Department.CS,
                "hi@rutgers.edu"), 60);
        boolean expectedOutput = false; //define expected output
        boolean actualOutput = e1.equals(e2);
        //call equals method to get actual output
        System.out.println("**Test #3: same dates & timeslots, " +
                "different locations");
        testResults(expectedOutput, actualOutput);
    }

    /**
     * Test Case #4: Tests if the equals method works
     * when 2 dates are the same, 2 timeslots, and
     * 2 locations are the same
     * <p>
     * The contact and durations do not matter, because
     * they are not used for comparisons (they do not
     * indicate if two events happen at the same time).
     */
    private static void testTwoSameEvents() {
        Event e1 = new Event(new Date("09/06/2023"),
                Timeslot.MORNING, Location.BE_AUD, new Contact(Department.ITI,
                "cs@rutgers.edu"), 60);
        Event e2 = new Event(new Date("09/06/2023"), Timeslot.MORNING,
                Location.BE_AUD, new Contact(Department.CS,
                "hi@rutgers.edu"), 60);
        boolean expectedOutput = true; //define expected output
        boolean actualOutput = e1.equals(e2);
        //call equals method to get actual output
        System.out.println("**Test #4: same events");
        testResults(expectedOutput, actualOutput);
    }

    /**
     * Template code to format and print the test results to the command line.
     * @param expectedOutput The correct output of this test.
     * @param actualOutput The output generated by the equals() method
     */
    private static void testResults(boolean expectedOutput,
                                    boolean actualOutput) {
        if (expectedOutput != actualOutput) {
            System.out.printf("\t%s | Expected: %s, Got: %s\n",
                    "Test Failed!", expectedOutput, actualOutput);
        } else {
            System.out.printf("\t%s | Expected: %s, Got: %s\n",
                    "Test Passed!", expectedOutput, actualOutput);
        }
    }

    /**
     * Testbed main for the equals() method.
     * @param args command line args
     */
    public static void main(String[] args) {
        testTwoCompletelyDifferentEvents();
        testTwoDatesSame();
        testTwoDatesSameAndTwoTimeslotsSame();
        testTwoSameEvents();
    }

}
