# SC2002-Hospital-Management-System
## Overview
- Our Hospital Management System is an application that automates the management of a hospital operation. It provides efficiency to all staff such as doctor, pharmacist and adminstrator. This system also helps to streamline process adminstrative work.

## Team
| Name             | Course | Lab Group |
|------------------|--------|-----------|
| Chen RuiLin      | SC2002 | SCSC      |
| Dylan Quek Zhi En| SC2002 | SCSC      |
| Kuek Hui Li      | SC2002 | SCSC      |
| Nga Wei En       | SC2002 | SCSC      |


## Objective
The goal of the assignment is to :
- Apply the Object-Oriented (OO) concepts you have learnt in the course. 
- To model, design and develop a hospital management system application. 
- Gain familiarity with using Java as an object oriented programming language. 
- Work collaboratively as a group to achieve a common goal.


## User role and capabilties 
### Administrator
View, add, update, and remove staff.
Manage appointments and medications.
Approve replenishment requests.

### Doctor
View and update patient medical records.
Record appointment outcomes.
Manage personal availability for appointments.

### Pharmacist
View appointment outcomes and dispense medications
Submit and manage replenishment requests

### Patient
Schedule, view, and manage appointments.
View personal medical history and records.


## Additional Features
### Password encryption 
Using MessageDigest.getInstance("SHA-256") with added salt at the system level.
This enhances security by securely hashing passwords, ensuring sensitive user information remains protected even if data files are compromised.

### Notification System
Introduced a notification system to alert users of pending tasks or updates relevant to their roles.
- Pharmacists are notified to dispense medication prescribed by doctors.
- Pharmacists are reminded to submit replenishment requests for low stock.
- Administrators are notified when pharmacists submit replenishment requests.
- Doctors are notified of new appointments scheduled by patients.
- Patients are notified when doctors accept or reject their appointments.

### Data persistence
Implemented a function to automatically save data to CSV files on user logout.




