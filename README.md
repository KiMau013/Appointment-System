# Appointment System

This application is a fully featured Appointment System that allows a company to manage Appointments, Customers, and their Contacts.  You can create, edit, and delete as needed, which are updated to your MySQL Database.  It features a login page for secure access, as well as language functions depending on the users default language (default is English; French is implemented).  It will also translate the timezone of the user to the appropriate UTC for the backend MySQL Database, and vice versa.  The limitations of hours for appointments have been implemented for the EST time zone based on the requested business requirements (8am - 10pm EST).  Additionally, it has 4 separate views/reports for quick visual filtering of information.

*Utilizes the Data-Access-Object (DAO) framework.*

**Does not automatically have any data as the MySQL server information used to create it has been redacted**

## Java 11
- JavaFx
- JavaDocs
- MySQL server
- IntelliJ IDEA CE IDE

### Example - Login
![Login Image](/Sample%20Images/Login.png)

### Example - Main
![Main Image](/Sample%20Images/Main.png)

### Example - Add and Modify Customers
![Add Customer Image](/Sample%20Images/Add%20Customer.png)
![Modify Customer Image](/Sample%20Images/Modify%20Customer.png)

### Example - Add and Modify Appointments
![Add Appointment Image](/Sample%20Images/Add%20Appointment.png)
![Modify Appointment Image](/Sample%20Images/Modify%20Appointment.png)

### Example - Customer Records
![Customer Records Image](/Sample%20Images/Customer%20Records.png)

### Example - Various Reports
![Report - Contact Scheduler Image](/Sample%20Images/Report%20-%20Contact%20Scheduler.png)
![Report - Customers Image](/Sample%20Images/Report%20-%20Customers.png)
![Report - Location Image](/Sample%20Images/Report%20-%20Location.png)
![Report - Type Image](/Sample%20Images/Report%20-%20Type.png)
