package HMS.models;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import HMS.MainApp;
import HMS.enums.AppointmentStatus;
import HMS.enums.BloodType;
import HMS.enums.Gender;
import HMS.enums.Role;

public class Patient extends User {

    private LocalDate DOB;
    private BloodType bloodType;
    private String contactInfo;
    private ArrayList<Appointment> appointments;

    // Constructor
    public Patient(String userId, String password, Gender gender, String name, Role role, LocalDate DOB, BloodType bloodType, String contactInfo) {
        super(userId, password, gender, name, role);
        this.DOB = DOB;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.appointments = new ArrayList<>();
    }

    public void updateParticular(Scanner sc) {
        System.out.println("Updating patient details for: " + getName());

        LocalDate newDOB = null;
        String newContactInfo = null;

        // Step 1: Get a valid Date of Birth or exit
        while (true) {
            System.out.print("Enter new Date of Birth (yyyy-MM-dd) or (-1) to cancel: ");
            String dobInput = sc.nextLine();

            // Check if the user wants to exit
            if (dobInput.equals("-1")) {
                System.out.println("Update canceled. No changes were made.");
                return;
            }

            try {
                newDOB = LocalDate.parse(dobInput);
                break; // Exit the loop if a valid date is entered
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }

        // Step 2: Get new contact info or exit
        System.out.print("Enter new Contact Info or (-1) to cancel: ");
        newContactInfo = sc.nextLine();
        if (newContactInfo.equals("-1")) {
            System.out.println("Update canceled. No changes were made.");
            System.out.println("");
            return;
        }

        // Step 3: If all inputs are collected successfully, update the details
        this.setDOB(newDOB);
        this.setContactInfo(newContactInfo);

        System.out.println("Your details have been updated successfully.");
        viewParticular();
    }

    // Method to view patient details
    public void viewParticular() {
        System.out.println("--------------------");
        System.out.println("Patient ID: " + getUserId());  // assuming User has getUserId()
        System.out.println("Name: " + getName());
        System.out.println("DOB: " + DOB);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Contact Info: " + contactInfo);
        System.out.println("--------------------");
        System.out.println("");
    }

    public void scheduleAppointment(Scanner sc) {
        System.out.println("Scheduling appointment for patient: " + getName());

        // Call the method to select a doctor and schedule an appointment
        Map.Entry<Doctor, Appointment> entry = checkAvaiDoctor(sc);

        // Check if the entry is null (i.e., the user chose to exit)
        if (entry == null) {
            System.out.println("Appointment scheduling was canceled.");
            return;
        }

        Doctor selectedDoctor = entry.getKey();
        Appointment newAppointment = entry.getValue();

        // Add the new appointment to the patient's and main appointment lists
        if (newAppointment != null) {
            this.appointments.add(newAppointment);
            MainApp.appointments.add(newAppointment);

            // Format the appointment date and time for display
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = newAppointment.getAppointmentDateTime().format(formatter);

            System.out.println("Appointment scheduled with Dr. " + selectedDoctor.getName() + " on " + formattedDateTime);
            System.out.println("");
        } else {
            System.out.println("Failed to schedule the appointment.");
        }
    }


    /*
    public Map.Entry<Doctor, Appointment> checkAvaiDoctor(Scanner sc) {
        System.out.println("Available Doctors: ");
        int counter = 1;
        boolean validDoctorID = false;
        String doctorID = "";
        Doctor selectedDoctor = null;

        while (!validDoctorID) {
            for (Doctor doctor : MainApp.doctors) {
                System.out.println(counter + ": " + doctor.getUserId() + " " + doctor.getName());
                counter++;
            }
            System.out.println("Enter the ID of the doctor you want to schedule an appointment with: ");
            doctorID = sc.nextLine();
            for (Doctor doctor : MainApp.doctors) {
                if (doctor.getUserId().equals(doctorID)) {
                    validDoctorID = true;
                    selectedDoctor = doctor;
                    break;
                }
            }
            if (!validDoctorID) {
                System.out.println("Invalid Doctor ID. Please try again.");
            }
        }

        while (true) {
            System.out.println("Selected Doctor: " + selectedDoctor.getName());
            System.out.println("Available Appointment Slots: ");
            Map<Integer, List<Integer>> availability = selectedDoctor.getAvailability();
            if (availability.isEmpty()) {
                System.out.println("No available slots for Dr. " + selectedDoctor.getName() + ". Please choose another doctor.");
                validDoctorID = false;
                counter = 1;
                while (!validDoctorID) {
                    for (Doctor doctor : MainApp.doctors) {
                        System.out.println(counter + ": " + doctor.getUserId() + " " + doctor.getName());
                        counter++;
                    }
                    System.out.println("Enter the ID of the doctor you want to schedule an appointment with: ");
                    doctorID = sc.nextLine();
                    for (Doctor doctor : MainApp.doctors) {
                        if (doctor.getUserId().equals(doctorID)) {
                            validDoctorID = true;
                            selectedDoctor = doctor;
                            break;
                        }
                    }
                    if (!validDoctorID) {
                        System.out.println("Invalid Doctor ID. Please try again.");
                    }
                }
            } else {
                for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
                    System.out.println("Date: " + entry.getKey() + " Slots: " + entry.getValue());
                }
                System.out.print("Enter the Date (yyyy-MM-dd): ");
                String dateInput = sc.nextLine();
                LocalDate date;
                try {
                    date = LocalDate.parse(dateInput);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                    continue;
                }
                System.out.print("Enter the Slot: ");
                int slot = sc.nextInt();
                sc.nextLine(); // Consume newline character

                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Invalid date. Please enter a future date.");
                    continue;
                }

                List<Integer> availableSlots = availability.get(date.getDayOfYear());
                if (availableSlots == null || !availableSlots.contains(slot)) {
                    System.out.println("Invalid slot. Please enter a valid slot.");
                    continue;
                }

                LocalDateTime appointmentDateTime = LocalDateTime.of(date, LocalTime.of(slot, 0));
                Appointment newAppointment = new Appointment(this.getUserId(), selectedDoctor.getUserId(), appointmentDateTime, AppointmentStatus.PENDING, null);
                return Map.entry(selectedDoctor, newAppointment);
            }
        }
    }

     */
    public Map.Entry<Doctor, Appointment> checkAvaiDoctor(Scanner sc) {
        // Step 1: Select a doctor
        Doctor selectedDoctor = selectDoctor(sc);
        if (selectedDoctor == null) {
            return null; // User chose to exit
        }

        // Step 2: Loop until a valid appointment slot is chosen or user exits
        while (true) {
            // Display the availability of the selected doctor 
            displayDoctorAvailability(selectedDoctor);

            /*
        if (selectedDoctor.getAvailability().isEmpty()) {
            System.out.println("No available slots for Dr. " + selectedDoctor.getName() + ". Please choose another doctor.");
            selectedDoctor = selectDoctor(sc);
            if (selectedDoctor == null) {
                return null; // User chose to exit
            }
            continue;
        }
             */
            // Step 3: Prompt the user to select a date
            System.out.print("Enter the Date (yyyy-MM-dd): ");
            String dateInput = sc.nextLine();
            LocalDate date;

            // Validate the date format
            try {
                date = LocalDate.parse(dateInput);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                continue;
            }

            // Ensure the date is in the future
            if (date.isBefore(LocalDate.now())) {
                System.out.println("Invalid date. Please enter a future date.");
                continue;
            }

            // Step 4: Loop to get a valid time slot
            while (true) {
                System.out.print("Enter the Slot (hour in 24-hour format, e.g., 1400 for 2 PM, or -1 to exit): ");
                String slotInput = sc.nextLine();

                // Exit if the user enters -1
                if (slotInput.equals("-1")) {
                    System.out.println("Scheduling canceled.");
                    return null;
                }

                try {
                    // Ensure the input is exactly 4 digits long
                    if (slotInput.length() != 4) {
                        throw new IllegalArgumentException("Time must be in 4-digit format (e.g., 1400 for 2 PM).");
                    }

                    // Parse the hour and minute from the 4-digit input
                    int hour = Integer.parseInt(slotInput.substring(0, 2));  // First two digits are the hour
                    int minute = Integer.parseInt(slotInput.substring(2, 4)); // Last two digits are the minute

                    // Validate that hour and minute are within valid ranges
                    if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                        throw new IllegalArgumentException("Invalid time entered. Please use 24-hour format (HHmm).");
                    }

                    /*
                
                Map<Integer, List<Integer>> availability = selectedDoctor.getAvailability();
                List<Integer> availableSlots = availability.get(date.getDayOfYear());
                if (availableSlots == null || !availableSlots.contains(slotInput)) {
                    System.out.println("Invalid slot. Please enter a valid slot.");
                    continue;
                }
                     */
                    // Step 5: Create the LocalDateTime object
                    LocalDateTime appointmentDateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
                    Appointment newAppointment = new Appointment(
                            this.getUserId(),
                            selectedDoctor.getUserId(),
                            appointmentDateTime,
                            AppointmentStatus.PENDING,
                            null
                    );

                    // Return the new appointment
                    return Map.entry(selectedDoctor, newAppointment);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numeric values only (e.g., 1400 for 2 PM).");
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Invalid input length. Please enter a 4-digit time (e.g., 1400 for 2 PM).");
                } catch (DateTimeException e) {
                    System.out.println("Invalid time value. Ensure the hour is between 0-23 and the minute is between 0-59.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public Doctor selectDoctor(Scanner sc) {
        while (true) {
            int counter = 1;
            System.out.println("Available Doctors: ");
            for (Doctor doctor : MainApp.doctors) {
                System.out.println(counter + ": " + doctor.getUserId() + " " + doctor.getName());
                counter++;
            }
            System.out.println("-1: Exit");
            System.out.println("Enter the ID of the doctor you want to schedule an appointment with:");
            String doctorID = sc.nextLine();

            // Exit if the user enters -1
            if (doctorID.equals("-1")) {
                System.out.println("Exiting doctor selection.");
                System.out.println("");
                return null;
            }

            // Find the doctor by ID
            for (Doctor doctor : MainApp.doctors) {
                if (doctor.getUserId().equals(doctorID)) {
                    return doctor;
                }
            }
            System.out.println("Invalid Doctor ID. Please try again.");
            System.out.println("");
        }
    }

    public void displayDoctorAvailability(Doctor doctor) {
        Map<Integer, List<Integer>> availability = doctor.getAvailability();
        if (availability.isEmpty()) {
            System.out.println("No available slots for Dr. " + doctor.getName() + ".");
            System.out.println("");
        } else {
            System.out.println("Available Appointment Slots for Dr. " + doctor.getName() + ":");
            for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
                System.out.println("Date: " + entry.getKey() + " Slots: " + entry.getValue());
            }
        }
    }

    public void viewAllAppointments() {
        if (appointments == null) {
            System.out.println("No appointments scheduled.");
        } else {
            System.out.println("Appointments scheduled for " + getName() + ":");
            System.out.println("-------------------------------");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
            System.out.println("-------------------------------");
            System.out.println("");
        }
    }

    public void viewCompletedAppointments() {
        System.out.println("Completed Appointments for " + getName() + ":");
        System.out.println("-------------------------------");

        // Check if the appointments list is empty
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No completed appointments.");
        } else {
            boolean hasCompleted = false;

            // Loop through the list to find COMPLETED appointments
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                    System.out.println(appointment); // Assuming Appointment has a proper toString()
                    hasCompleted = true;
                }
            }

            // If no completed appointments found
            if (!hasCompleted) {
                System.out.println("No completed appointments.");
            }
        }
        System.out.println("-------------------------------");
        System.out.println("");
    }

    public void viewScheduledAppointments() {
        System.out.println("Scheduled Appointments for " + getName() + ":");
        System.out.println("-------------------------------");

        boolean hasAppointments = false;

        for (Appointment appointment : appointments) {
            // Check only for PENDING or CONFIRMED appointments
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING
                    || appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {

                System.out.println(appointment); // Assumes Appointment has a proper toString() implementation
                hasAppointments = true;
            }
        }

        if (!hasAppointments) {
            System.out.println("No scheduled appointments.");
        }

        System.out.println("-------------------------------");
        System.out.println("");
    }

    public void rescheduleAppointment(Scanner sc) {
        // This would contain logic to reschedule an appointment
        System.out.println("Rescheduling appointment for patient: " + getName());
        System.out.println("");
        viewScheduledAppointments();
        System.out.print("Enter the appointment ID to reschedule: ");
        int appointmentID = sc.nextInt();
        sc.nextLine(); // Consume newline character

        Map.Entry<Doctor, Appointment> entry = checkAvaiDoctor(sc);
        Doctor selectedDoctor = entry.getKey();
        Appointment newAppointment = entry.getValue();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                appointment.setDoctorID(selectedDoctor.getUserId());
                appointment.setDateTime(newAppointment.getAppointmentDateTime());
                appointment.setAppointmentStatus(AppointmentStatus.PENDING);
                appointment.setAppointmentOutcomeRecord(null);
            }
        }

        System.out.println("Appointment rescheduled successfully.");
    }

    public int cancelAppointment(Scanner sc) {
        // This would contain logic to cancel an appointment
        System.out.println("Cancelling appointment for patient: " + getName());
        System.out.println("Select an appointment to cancel: ");
        viewScheduledAppointments();
        System.out.print("Enter the appointment ID: ");
        int appointmentID = sc.nextInt();

        for (Appointment appointment : this.appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                appointments.remove(appointment);
                MainApp.appointments.remove(appointment);
                System.out.println("Appointment cancelled successfully.");
                return appointmentID;
            }
        }

        System.out.println("Appointment not found.");
        return -1;
    }

    // Getters & Setters
    public String getPatientID() {
        return getUserId();
    }

    public String getName() {
        return super.getName();
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public Role getRole() {
        return super.getRole();
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    @Override
    public String toString() {
        return "Patient{"
                + "userId='" + getUserId() + '\''
                + ", name='" + getName() + '\''
                + ", gender=" + getGender()
                + ", role=" + getRole()
                + ", DOB=" + DOB
                + ", bloodType=" + bloodType
                + ", contactInfo='" + contactInfo + '\''
                + '}';
    }

    @Override
    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        while (choice != 9) {
            System.out.println("======================");
            System.out.println("-----Patient Menu-----");
            System.out.println("======================");
            System.out.println("1.View Medical Record");
            System.out.println("2.Update Personal Information");
            System.out.println("3.View Available Appointment Slots");
            System.out.println("4.Schedule an Appointment");
            System.out.println("5.Reschedule an Appointment");
            System.out.println("6.Cancel an Appointment");
            System.out.println("7.View Scheduled Appointments");
            System.out.println("8.View Past Appointment Outcome Records");
            System.out.println("9.Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    // View Medical Record
                    viewParticular();
                    viewCompletedAppointments();
                    break;

                case 2:
                    // Update Personal Information
                    updateParticular(sc);
                    break;

                case 3:
                    // View Available Appointment Slots
                    while (true) {
                        Doctor selectedDoctor = selectDoctor(sc);
                        if (selectedDoctor == null) {
                            break;
                        }
                        displayDoctorAvailability(selectedDoctor);
                    }
                    break;

                case 4:
                    // Schedule an Appointment
                    scheduleAppointment(sc);
                    break;

                case 5:
                    // Reschedule an Appointment
                    rescheduleAppointment(sc);
                    break;

                case 6:
                    // Cancel an Appointment
                    cancelAppointment(sc);
                    break;

                case 7:
                    // View Scheduled Appointments
                    viewScheduledAppointments();
                    break;

                case 8:
                    // View Past Appointment Outcome Records
                    viewCompletedAppointments();
                    break;

                case 9:
                    // Logout
                    System.out.println("Logging out");
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
