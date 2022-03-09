package Models;

/**
 * Model for Appointments
 */
public class Appointment {
    private Integer appointmentId, customerId, userId, contactId, count;
    private String contact;
    private String title;
    private String description;
    private String location;
    private String type;
    private String date;
    private String start;
    private String end;

    public Appointment() {}

    //For Appointment Table
    public Appointment(Integer appointmentId, String title, String description, String location, String contact, String type, String start, String end, Integer customerId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    //For Appointments
    public Appointment(Integer appointmentId, Integer userId, Integer customerId, String contact, String title, String description, String location, String type, String date, String start, String end) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.customerId = customerId;
        this.contact = contact;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    //For Contact Table
    public Appointment(Integer appointmentId, String title, String type, String description, String start, String end, Integer customerId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    //For Warning
    public Appointment(Integer appointmentId, String type, String date, String start, String end){
        this.appointmentId = appointmentId;
        this.type = type;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    //For byType and byLocation Report
    public Appointment(String start, String type, Integer count){
        this.start = start;
        this.type = type;
        this.count = count;
    }

    //For byCustomer Report
    public Appointment(String start, Integer customerId, Integer count){
        this.start = start;
        this.customerId = customerId;
        this.count = count;
    }

    //getters
    public Integer getAppointmentId(){
        return appointmentId;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getLocation(){
        return location;
    }

    public String getType(){
        return type;
    }

    public String getContact() { return contact; }

    public String getDate(){ return date; }

    public String getStart(){
        return start;
    }

    public String getEnd(){
        return end;
    }

    public Integer getCustomerId(){
        return customerId;
    }

    public Integer getUserId(){
        return userId;
    }

    public Integer getContactId(){
        return contactId;
    }

    public Integer getCount(){ return count; }

    //setters
    public void setAppointmentId(Integer appointmentId){
        this.appointmentId = appointmentId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setContact(String contact) { this.contact = contact; }

    public void setDate(String date) { this.date = date; }

    public void setStart(String start){
        this.start = start;
    }

    public void setEnd(String end){
        this.end = end;
    }

    public void setCustomerId(Integer customerId){
        this.customerId = customerId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public void setContactId(Integer contactId){
        this.contactId = contactId;
    }

    public void setCount(Integer count) { this.count = count; }
}
