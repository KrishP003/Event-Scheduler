package scheduler;

/**
 * Array-based implementation of the whole calendar filled with all
 * scheduled events.
 * @author Dharmik Patel and Krish Patel
 */
public class EventCalender {
    private final static int NOT_FOUND = -1;
    private final static int GROWTH_AMOUNT = 4;
    private final static int NO_EVENTS = 0;

    private Event[] events; //the array holding the list of events
    private int numEvents;
    //the number of events in the list. does not have to equal events.length

    /**
     * Instantiates a EventCalender object with an Events[] array
     * with initial capacity of GROWTH_AMOUNT(4) and numEvents to 0;
     */
    public EventCalender() {
        events = new Event[GROWTH_AMOUNT];
        numEvents = 0;
    }

    /**
     * Loops through the events array to find the given event.
     * @param event Event to fnd
     * @return The index of the event in events array, or NOT_FOUND(-1)
     */
    private int find(Event event) {
        for (int i = 0; i < numEvents; i++) {
            if (events[i] != null && events[i].equals(event)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Grows the events array by GROWTH_AMOUNT(4). This is a manual copy.
     */
    private void grow() {
        Event[] arrTemp = new Event[events.length + GROWTH_AMOUNT];
        for (int i = 0; i < events.length; i++) {
            arrTemp[i] = events[i];
        }
        events = arrTemp;
    }

    /**
     * Adds an event to the event calendar, if it does not already exist
     * in the event calendar. Will also grow the array if needed.
     * @param event Event to add.
     * @return True if event was added, false if it was not added.
     */
    public boolean add(Event event) {
        if (!(contains(event))) {
            try {
                events[numEvents] = event;
                numEvents++;
            } catch (ArrayIndexOutOfBoundsException e) {
                grow();
                events[numEvents] = event;
                numEvents++;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes the specified event if it is in the calendar. Also left shifts
     * the events, to fill empty space left by removed event.
     * @param event Event to remove
     * @return True if the event is removed, false if it is not found in
     *         the calendar.
     */
    public boolean remove(Event event) {
        int positionToRemove = find(event);
        if (positionToRemove == NOT_FOUND) {
            return false;
        } else {
            leftShiftArray(positionToRemove);
            return true;
        }
    }

    /**
     * Checks if the specified event is in the calendar.
     * @param event Event to check
     * @return True if event is in calendar, False if it is not.
     */
    public boolean contains(Event event) {
        return find(event) != NOT_FOUND;
    }

    /**
     * Utility method to shift all array elements after {@code position}.
     * @param position Index to start shifting from.
     */
    private void leftShiftArray(int position) {
        for (int i = position; i < numEvents - 1; i++) {
            events[i] = events[i + 1];
        }
        events[numEvents] = null;
        numEvents--;
    }

    /**
     * Utility method to reuse code to print the current calendar in the
     * current array order.
     */
    private void printCurrentArray() {
        for (int i = 0; i < numEvents; i++) {
            System.out.println(events[i]);
        }
    }

    /**
     * Prints the calendar in current array order
     */
    public void print() {
        if(numEvents == NO_EVENTS){
            System.out.println("Event calendar is empty!");
            return;
        }
        System.out.println("* Event calendar *");
        printCurrentArray();
        System.out.println("* end of event calendar *");
    }

    /**
     * Prints the calendar sorted by event date and start time.
     */
    public void printByDate() {
        if(numEvents == NO_EVENTS){
            System.out.println("Event calendar is empty!");
            return;
        }
        for (int i = 0; i < numEvents - 1; i++) {
            int indexOfEarliestEvent = i;
            for (int j = i + 1; j < numEvents; j++) {
                if (events[j].compareTo(events[indexOfEarliestEvent]) < 0) {
                    indexOfEarliestEvent = j;
                }
            }
            swap(i, indexOfEarliestEvent);
        }
        System.out.println("* Event calendar by event date and start time *");
        printCurrentArray();
        System.out.println("* end of event calendar *");
    }

    /**
     * Prints the calendar sorted by campus and building.
     */
    public void printByCampus() {
        if(numEvents == NO_EVENTS){
            System.out.println("Event calendar is empty!");
            return;
        }
        for (int i = 0; i < numEvents - 1; i++) {
            int indexOfEarliestEvent = i;
            for (int j = i + 1; j < numEvents; j++) {
                if (events[j].compareToByCampus(events[indexOfEarliestEvent])
                        < 0) {
                    indexOfEarliestEvent = j;
                }
            }
            swap(i, indexOfEarliestEvent);
        }
        System.out.println("* Event calendar by campus and building *");
        printCurrentArray();
        System.out.println("* end of event calendar *");
    }

    /**
     * Prints the calendar sorted by department.
     */
    public void printByDepartment() {
        if(numEvents == NO_EVENTS){
            System.out.println("Event calendar is empty!");
            return;
        }
        for (int i = 0; i < numEvents - 1; i++) {
            int indexOfEarliestEvent = i;
            for (int j = i + 1; j < numEvents; j++) {
                if (events[j].compareToByDepartment(
                        events[indexOfEarliestEvent]) < 0) {
                    indexOfEarliestEvent = j;
                }
            }
            swap(i, indexOfEarliestEvent);
        }
        System.out.println("* Event calendar by department *");
        printCurrentArray();
        System.out.println("* end of event calendar *");
    }

    /**
     * Utility method used while sorting to swap two events in the calendar
     * array.
     * @param i Index of 1st Event to swap
     * @param j Index of 2nd Event to swap
     */
    private void swap(int i, int j) {
        Event temp = events[j];
        events[j] = events[i];
        events[i] = temp;
    }
}
