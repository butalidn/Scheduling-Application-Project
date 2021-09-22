package Model;

import java.time.LocalDateTime;

/**
 * This class is for an appointment object.
 * Contains its appointment id, customer id, user id, title, description, title, location, contact id, type, start and end time
 */
public class Appointment {
    private int appointmentID;
    private int customerID;
    private int userID;
    private String title;
    private String description;
    private String location;
    private int contactID;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**
     * This is the constructor method for an Appointment object
     * @param appointmentID The appointment ID
     * @param customerID The customer ID
     * @param userID The user ID
     * @param title The title of the appointment
     * @param description The description of the appointment
     * @param location The location of the appointment
     * @param contactID The contact ID
     * @param type The type of the appointment
     * @param startTime The start time and date of the appointment
     * @param endTime The end time and date of the appointment
     */
    public Appointment(int appointmentID, int customerID, int userID, String title, String description, String location, int contactID, String type, LocalDateTime startTime, LocalDateTime endTime) {
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * The getter for appointment ID
     * @return Returns an int of the id
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * The setter for appointment ID
     * @param appointmentID The given id to be set
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * The getter for customer ID
     * @return Returns an int of the id
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * The setter for customer ID
     * @param customerID The given id to be set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * The getter for user ID
     * @return Returns an int of the id
     */
    public int getUserID() {
        return userID;
    }

    /**
     * The setter for appointment ID
     * @param userID The given id to be set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * The getter for title
     * @return Returns a string of the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The setter for title
     * @param title The title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The getter for description
     * @return Returns the string of the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * The setter for description
     * @param description The given description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The getter for location
     * @return Returns the string of the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * The setter for description
     * @param location The location to be set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The getter for contact id
     * @return Returns the int of the id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * The setter for contact id
     * @param contactID The id to be set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * The getter for type
     * @return Returns the string of the type
     */
    public String getType() {
        return type;
    }

    /**
     * The setter for type
     * @param type The string to be set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The getter for the start time
     * @return Returns a local date time of the start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * The setter for the start time
     * @param startTime The local date time to be set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * The getter for the end time
     * @return Returns the local date time of the end time
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * The setter for the end time
     * @param endTime The local date time to be set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
