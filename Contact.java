package scheduler;
/**
 * Represents a contact by a department and email
 * @author Dharmik Patel and Krish Patel
 */
public class Contact {
    private final Department department;
    private final String email;

    /**
     * This constructor makes a Contact(email and department) for the event.
     * @param department - The department the person is a part of
     * @param email - The email of the person
     */
    public Contact(Department department, String email){
        this.department = department;
        this.email = email;
    }
    /**
     * A getter method for the private variable department
     * @return The department of the contact.
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Utility method to test if the email is valid. Email must end in
     * "@rutgers.edu"
     * @return True if the email is valid. False if it is not
     */
    public boolean isValid(){
        return email.matches("[^@]+@rutgers\\.edu");
    }

    /**
     * Overrides the toString method to return the contact in the
     * format: [Contact: department, email]
     * @return Returns a Contact in the specified format.
     */
    @Override
    public String toString() {
        return String.format("[Contact: %s, %s]", department, email);
    }
}
