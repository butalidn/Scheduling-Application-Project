# **Scheduling Application: Software II**
Project Overview
----------------
This project was for Software II. The application allows the user to manage appointment and customer information. The user is conneted to a MySQL database and can add, modify, and delete customer or appointment data that is also reflected in the database. They can also generate various reports.

#### Project specifications:
* IDE: IntelliJ Community Edition 2021.1.1
* JDK: Java SE 11.0.4
* JavaFX: JavaFX-SDK-11.0.2
* MySQL: mysql-connector-java:8.0.222

## ***WARNING***
The MySQL database the project was previously connected to is no longer active. Additional code must be written to connect to another MySQL database in order for the application to fully function. 


***The following text was written as part of the project guidelines:***

### Instructions

Launch application through main method in the "Main" class. The user will be taken to the login screen. The login screen will be in English or French, depending on the user's location.
The user can either enter "test" for both the username and password, or "admin" for both username and password. Login activity will be recorded in the login_activity.txt file located
in the root folder of the project (\Butalid_C195_PA). 

The user will be taken to the management screen where a message will pop up. The message will inform the user if they have an appointment coming up in the next 15 minutes or not. The user can
then exit the pop up message and choose whether to access customer or appointment information with the corresponding button.

### Customer Information
---------------------
The user will be taken to the customer screen. The table will contain all existing customers in the database with their corresponding information. The user can add, update, or delete customers
from this table that will also add, update, or delete the customer from the database. When the user selects an entry, that customer's information will be loaded in the respective fields.
*In order to select a first-level division, a country must be selected first. The first-level division combo box will only display divisions that are located in the selected country.
Customers can only be deleted once the customers has no existing appointments associated with them, i.e., all associated must be deleted first.

### Appointment Information
------------------------
The user will be taken to the appointment screen. The table will initially display all appointments and their information. The user has the option to also filter the displayed appointment based on
appointments that are in the same current week or current month. The user can add, modify a selected appointment, or delete an appointment. The add or modify options will take the user to another screen.
If add is selected, the user can enter information into the given fields to create a new appointment. If modify is selected, the selected appointment's information will be loaded into the entry fields andthe user
can make changes to the information and save them.

From the initial appointment screen the user can select the reports button to go to a reports screen.

### Reports Information
--------------------
This screen will give the user the option to generate three different reports.
1) The user can generate how many appointments there are with a given month and appointment type. The result will appear on the GUI.
2) The user can select a contact and view all appointments that are associated with the selected contact.
3) The user can select a date and view all appointments that are on the specified date. The table will initially display them in chronological order.


### Exit Information
-----------------
The user can exit the application through the 'X' button in the top right of any of the screens.
