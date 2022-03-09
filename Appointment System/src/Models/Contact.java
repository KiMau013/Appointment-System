package Models;

/**
 * Model for Contacts
 */
public class Contact {

    private Integer contactId;
    private String contactName, email;

    public Contact(){}

    public Contact(Integer contactId, String contactName, String email){
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }
    public Contact(Integer contactId, String contactName){
        this.contactId = contactId;
        this.contactName = contactName;
    }

    //For Dropdowns
    public Contact(String contactName){
        this.contactName = contactName;
    }

    //getters
    public Integer getContactId(){
        return contactId;
    }
    public String getContactName(){
        return contactName;
    }
    public String getEmail(){
        return email;
    }

    //setters
    public void setContactId(Integer contactId){
        this.contactId = contactId;
    }
    public void setContactName(String contactName){
        this.contactName = contactName;
    }
    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public String toString() {return contactName;}
}
