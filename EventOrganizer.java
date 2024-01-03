package scheduler;

import java.util.Scanner;

/**
 * This class interfaces with the user via the command line.
 * An instance of this class can process a single command line
 * or multiple command lines at a time.
 * @author Dharmik Patel and Krish Patel
 */
public class EventOrganizer {

    private static final String CMD_ADD = "A";
    private static final String CMD_CANCEL = "R";
    private static final String CMD_PRINT = "P";
    private static final String CMD_PRINT_BY_DATES = "PE";
    private static final String CMD_PRINT_BY_CAMPUS = "PC";
    private static final String CMD_PRINT_BY_DEPARTMENT = "PD";
    private static final String CMD_QUIT = "Q";
    private static final String CMD_EMPTY = "";
    private static final int INDEX_OF_CMD_IN_INPUT = 0;
    private static final int INDEX_OF_DATE_IN_INPUT = 1;
    private static final int INDEX_OF_TIME_IN_INPUT = 2;
    private static final int INDEX_OF_LOCATION_IN_INPUT = 3;
    private static final int INDEX_OF_DEPARTMENT_IN_INPUT = 4;
    private static final int INDEX_OF_EMAIL_IN_INPUT = 5;
    private static final int INDEX_OF_DURATION_LENGTH_IN_INPUT = 6;




    private EventCalender eventCalender;

    /**
     * This is the run method to make the User UI work.
     * Will run until CMD_QUIT("Q") is inputted
     * The last line of input must end in a "\n" new line character.
     */
    public void run() {
        eventCalender = new EventCalender();
        System.out.println("Event Organizer running...");
        Scanner scanner = new Scanner(System.in);
        String currentFullLine;
        do {
            currentFullLine = scanner.nextLine();
            String[] commands = currentFullLine.split("\\s+");
            switch (commands[INDEX_OF_CMD_IN_INPUT]){
                case CMD_ADD -> addEvent(commands);
                case CMD_CANCEL -> cancelEvent(commands);
                case CMD_PRINT -> eventCalender.print();
                case CMD_PRINT_BY_CAMPUS -> eventCalender.printByCampus();
                case CMD_PRINT_BY_DATES -> eventCalender.printByDate();
                case CMD_PRINT_BY_DEPARTMENT -> {
                    eventCalender.printByDepartment();
                }
                case CMD_QUIT -> System.out.println(
                        "Event Organizer terminated.");
                case CMD_EMPTY -> {}
                default -> System.out.printf("%s is an invalid command!\n",
                        commands[0]);
            }
        } while (!(currentFullLine.equals(CMD_QUIT)));
        scanner.close();
    }
    /**
     * This method adds an event from the Event Calendar if the event
     * does not already exist in the calendar. It also takes care of
     * invalid inputs by calling helper methods: getEventDate, getStartTime,
     * getLocation, getContact, and getDuration.
     * @param commands - The line input split by words.
     */
    private void addEvent(String[] commands){
        Date eventDate = getAndCheckEventDate(
                commands[INDEX_OF_DATE_IN_INPUT]);
        if (eventDate == null) return;

        Timeslot startTime = getAndCheckStartTime(
                commands[INDEX_OF_TIME_IN_INPUT]);
        if (startTime == null) return;

        Location location = getAndCheckLocation(
                commands[INDEX_OF_LOCATION_IN_INPUT]);
        if (location == null) return;

        Contact contact = getAndCheckContact(
                commands[INDEX_OF_DEPARTMENT_IN_INPUT],
                commands[INDEX_OF_EMAIL_IN_INPUT]);
        if (contact == null) return;

        int duration = getAndCheckDuration(
                commands[INDEX_OF_DURATION_LENGTH_IN_INPUT]);
        if(duration == 0) return;

        Event eventToAdd = new Event(eventDate, startTime,
                location, contact, duration);
        boolean isEventAdded = eventCalender.add(eventToAdd);
        if(!isEventAdded){
            System.out.println("The event is already on the calendar.");
            return;
        }
        System.out.println("Event added to the calendar.");
    }

    /**
     * This method removes an event from the Event Calendar if it exists
     * in the calendar. It also takes care of invalid inputs by calling
     * helper methods: getEventDate, getStartTime, and getLocation.
     * @param commands - The line input split by words.
     */
    private void cancelEvent(String[] commands){
        Date eventDate = getAndCheckEventDate(
                commands[INDEX_OF_DATE_IN_INPUT]);
        if (eventDate == null) return;

        Timeslot startTime = getAndCheckStartTime(
                commands[INDEX_OF_TIME_IN_INPUT]);
        if (startTime == null) return;

        Location location = getAndCheckLocation(
                commands[INDEX_OF_LOCATION_IN_INPUT]);
        if (location == null) return;

        Event eventToRemove = new Event(eventDate, startTime, location);
        if(!(eventCalender.remove(eventToRemove))){
            System.out.println("Cannot remove; " +
                    "event is not in the calendar!");
            return;
        }
        System.out.println("Event has been removed from the calendar!");
    }

    /**
     * Utility method to make and validate the contact.
     * @param department - department of the contact person
     * @param email - email of contact person
     * @return - returns a valid contact object. Null if not valid
     */
    private static Contact getAndCheckContact(String department, String email)
    {
        Department departmentOfEventMaker;
        try{
            departmentOfEventMaker = Department.valueOf(
                    department.toUpperCase());
        }catch (Exception e){
            System.out.println("Invalid contact information!");
            return null;
        }
        Contact contactOfEventMaker = new Contact(
                departmentOfEventMaker, email);
        if(!(contactOfEventMaker.isValid())){
            System.out.println("Invalid contact information!");
            return null;
        }
        return contactOfEventMaker;
    }

    /**
     * Utility method to make and validate the location.
     * @param location string location, building code, of the event
     * @return returns a valid Location enum
     */
    private static Location getAndCheckLocation(String location) {
        Location locationForEvent;
        try{
            locationForEvent = Location.valueOf(location.toUpperCase());
        }catch (Exception e){
            System.out.println("Invalid location!");
            return null;
        }
        return locationForEvent;
    }

    /**
     * Utility method to make and validate the start time
     * @param startTime string start time
     * @return returns a valid Timeslot enum. Null if not valid
     */
    private static Timeslot getAndCheckStartTime(String startTime) {
        Timeslot startTimeToStartEventAt;
        try{
            startTimeToStartEventAt = Timeslot.valueOf(
                    startTime.toUpperCase());
        }catch (Exception e){
            System.out.println("Invalid time slot!");
            return null;
        }
        return startTimeToStartEventAt;
    }

    /**
     * Utility method to make and validate the date.
     * @param date date start time in format "MONTH/DAY/YEAR"
     * @return returns a valid Date. Null if not valid
     */
    private static Date getAndCheckEventDate(String date) {
        Date eventDateToAdd = new Date(date);
        if(!(eventDateToAdd.isValid())){
            System.out.printf("%s: Invalid calendar date!\n", date);
            return null;
        } else if (!(eventDateToAdd.isMoreThanPresentDate())) {
            System.out.printf("%s: Event date must be a future date!\n",
                    date);
            return null;
        } else if (!(eventDateToAdd.isLessThanDateSixMonthsInFuture())) {
            System.out.printf("%s: Event date must be within 6 months!\n",
                    date);
            return null;
        }
        return eventDateToAdd;
    }

    /**
     * Utility method to get and validate the duration
     * @param duration string duration length
     * @return returns a valid duration integer. 0 if not valid
     */
    private static int getAndCheckDuration(String duration){
        if (Integer.parseInt(duration) > Event.MAX_DURATION) {
            System.out.println("Event duration must be at least 30 minutes" +
                    " and at most 120 minutes");
            return 0;
        } else if (Integer.parseInt(duration) < Event.MIN_DURATION) {
            System.out.println("Event duration must be at least 30 minutes" +
                    " and at most 120 minutes");
            return 0;
        }
        return Integer.parseInt(duration);
    }
}
