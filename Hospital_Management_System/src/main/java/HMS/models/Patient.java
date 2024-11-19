package HMS.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import HMS.MainApp;
import static HMS.MainApp.patients;
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
    public Patient() {

    }

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

    public String showDoctorAvailability() {
        StringBuilder availability = new StringBuilder();

        for (Doctor doctor : MainApp.doctors) {
            Map<Integer, List<Integer>> doctorAvailability = doctor.getAvailability();

            // Check if the doctor has any available dates
            boolean hasAvailableSlots = doctorAvailability.values().stream().anyMatch(slots -> !slots.isEmpty());

            // If no slots are available, mark the doctor as unavailable
            if (!hasAvailableSlots) {
                availability.append("Doctor: ").append(doctor.getName()).append(" - Unavailable\n");
                continue;
            }

            // Display doctor's name
            availability.append("Doctor: ").append(doctor.getName()).append("\n");

            // Sort dates (keys) in ascending order
            doctorAvailability.keySet().stream()
                    .sorted()
                    .forEach(dateKey -> {
                        List<Integer> slots = doctorAvailability.get(dateKey);

                        // Convert DDMMYYYY to yyyy-MM-dd format
                        String formattedDate = convertDateFormat(String.valueOf(dateKey));

                        // Display the date
                        availability.append("  Date: ").append(formattedDate).append("\n");

                        // Display each available slot as a time (e.g., 0900, 1000) in ascending order
                        if (slots.isEmpty()) {
                            availability.append("    No available slots\n");
                        } else {
                            availability.append("    Slots: ");
                            slots.stream()
                                    .sorted()
                                    .forEach(slot -> {
                                        String timeSlot = convertSlotToTime(slot);
                                        availability.append(timeSlot).append(" ");
                                    });
                            availability.append("\n");
                        }
                    });
            availability.append("\n");
        }

        System.out.println(availability.toString());
        return availability.toString();
    }

    public String showDoctorAvailability2() {
        StringBuilder availability = new StringBuilder();

        for (Doctor doctor : MainApp.doctors) {
            Map<Integer, List<Integer>> doctorAvailability = doctor.getAvailability();

            // Check if the doctor has any available dates
            boolean hasAvailableSlots = false;
            for (List<Integer> slots : doctorAvailability.values()) {
                if (!slots.isEmpty()) {
                    hasAvailableSlots = true;
                    break;
                }
            }

            // If no slots are available, mark the doctor as unavailable
            if (!hasAvailableSlots) {
                availability.append("Doctor: ").append(doctor.getName()).append(" - Unavailable\n");
                continue;
            }

            // Display doctor's name
            availability.append("Doctor: ").append(doctor.getName()).append("\n");

            // Display each date and its available slots
            for (Map.Entry<Integer, List<Integer>> entry : doctorAvailability.entrySet()) {
                int dateKey = entry.getKey();
                List<Integer> slots = entry.getValue();

                // Convert DDMMYYYY to yyyy-MM-dd format
                String formattedDate = convertDateFormat(String.valueOf(dateKey));

                // Display the date
                availability.append("  Date: ").append(formattedDate).append("\n");

                // Display each available slot as a time (e.g., 0900, 1000) in ascending order
                if (slots.isEmpty()) {
                    availability.append("    No available slots\n");
                } else {
                    availability.append("    Slots: ");
                    slots.stream().sorted().forEach(slot -> {
                        String timeSlot = convertSlotToTime(slot);
                        availability.append(timeSlot).append(" ");
                    });
                    availability.append("\n");
                }
            }
            availability.append("\n");
        }

        System.out.println(availability.toString());
        return availability.toString();
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

            // Format the appointment date and time for display
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = newAppointment.getAppointmentDateTime().format(formatter);

            System.out.println("Appointment scheduled with Dr. " + selectedDoctor.getName() + " on " + formattedDateTime);
            System.out.println("");
        } else {
            System.out.println("Failed to schedule the appointment.");
        }
    }

    public Map.Entry<Doctor, Appointment> checkAvaiDoctor(Scanner sc) {
        Doctor selectedDoctor = selectDoctor(sc);
        if (selectedDoctor == null) {
            return null; // User chose to exit
        }

        while (true) {
            // Display the availability of the selected doctor (dates sorted)
            Integer selectedDateKey = displayAvailableDatesSorted(selectedDoctor, sc);
            if (selectedDateKey == -1) {
                System.out.println("Scheduling canceled.");
                return null;
            }

            // Fetch available slots using the sorted date key
            List<Integer> availableSlots = selectedDoctor.getAvailability().get(selectedDateKey);
            if (availableSlots == null || availableSlots.isEmpty()) {
                System.out.println("No available slots for the selected date. Please choose another date.");
                continue;
            }

            // Display available slots in sorted order
            int selectedSlot = displayAvailableSlotsSorted(availableSlots, sc);
            if (selectedSlot == -1) {
                System.out.println("Scheduling canceled.");
                return null; // Exit if user chooses -1
            }

            LocalDate selectedDate = convertToLocalDate(selectedDateKey);
            if (selectedDate == null) {
                System.out.println("Error converting date. Please try again.");
                continue;
            }

            LocalDateTime appointmentDateTime = LocalDateTime.of(selectedDate, LocalTime.of(9 + (selectedSlot - 1), 0));

            // Create new appointment
            Appointment newAppointment = new Appointment(
                    this.getUserId(),
                    selectedDoctor.getUserId(),
                    appointmentDateTime,
                    AppointmentStatus.PENDING,
                    null
            );

            // Remove the selected slot from doctor availability
            selectedDoctor.setAvailability(selectedDateKey, selectedSlot);

            System.out.println("Appointment booked successfully!");
            System.out.println("");

            return Map.entry(selectedDoctor, newAppointment);
        }
    }

    private Integer displayAvailableDatesSorted(Doctor doctor, Scanner sc) {
        Map<Integer, List<Integer>> availability = doctor.getAvailability();

        // Sort the keys (dates) in ascending order
        List<Integer> sortedDates = new ArrayList<>(availability.keySet());
        sortedDates.sort(Integer::compareTo);

        // Display available dates
        System.out.println("Available Dates for " + doctor.getName() + ":");
        for (int i = 0; i < sortedDates.size(); i++) {
            Integer dateKey = sortedDates.get(i);
            System.out.println("[" + (i + 1) + "] " + convertToLocalDate(dateKey));
        }
        System.out.print("Select a date by entering the corresponding number (-1 to cancel): ");

        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine(); // Clear buffer
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.nextLine();
            return -1;
        }

        if (choice == -1) {
            return -1;
        }

        if (choice < 1 || choice > sortedDates.size()) {
            System.out.println("Invalid choice. Please try again.");
            return -1;
        }

        return sortedDates.get(choice - 1);
    }

    private int displayAvailableSlotsSorted(List<Integer> availableSlots, Scanner sc) {
        // Sort available slots in ascending order
        List<Integer> sortedSlots = new ArrayList<>(availableSlots);
        sortedSlots.sort(Integer::compareTo);

        // Display available slots
        System.out.println("Available Slots:");
        for (int i = 0; i < sortedSlots.size(); i++) {
            int slot = sortedSlots.get(i);
            System.out.println("[" + (i + 1) + "] " + convertSlotToTime(slot));
        }
        System.out.print("Select a slot by entering the corresponding number (-1 to cancel): ");

        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine(); // Clear buffer
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number.");
            sc.nextLine();
            return -1;
        }

        if (choice == -1) {
            return -1;
        }

        if (choice < 1 || choice > sortedSlots.size()) {
            System.out.println("Invalid choice. Please try again.");
            return -1;
        }

        return sortedSlots.get(choice - 1);
    }

    public Map.Entry<Doctor, Appointment> checkAvaiDoctor2(Scanner sc) {
        Doctor selectedDoctor = selectDoctor(sc);
        if (selectedDoctor == null) {
            return null; // User chose to exit
        }

        while (true) {
            // Display the availability of the selected doctor
            Integer selectedDateKey = displayAvailabeDates(selectedDoctor, sc);
            if (selectedDateKey == -1) {
                System.out.println("Scheduling canceled.");
                return null;
            }

            // Fetch available slots using the DDMMYYYY key
            List<Integer> availableSlots = selectedDoctor.getAvailability().get(selectedDateKey);

            int selectedSlot = displayAvailableSlots(availableSlots, sc);
            if (selectedSlot == -1) {
                System.out.println("Scheduling canceled.");
                return null; // Exit if user chooses -1
            }

            LocalDate selectedDate = convertToLocalDate(selectedDateKey);
            if (selectedDate == null) {
                System.out.println("Error converting date. Please try again.");
                continue;
            }

            LocalDateTime appointmentDateTime = LocalDateTime.of(selectedDate, LocalTime.of(9 + (selectedSlot - 1), 0));

            Appointment newAppointment = new Appointment(
                    this.getUserId(),
                    selectedDoctor.getUserId(),
                    appointmentDateTime,
                    AppointmentStatus.PENDING,
                    null
            );

            //Logic to remove doctor availability
            selectedDoctor.setAvailability(selectedDateKey, selectedSlot);

            System.out.println("Appointment booked successfully!");
            System.out.println("");

            return Map.entry(selectedDoctor, newAppointment);
        }
    }

    public Doctor selectDoctor(Scanner sc) {
        while (true) {
            System.out.println("Available Doctors:");

            // List to store doctors for selection by index
            List<Doctor> availableDoctors = new ArrayList<>();

            // Display all doctors with their availability status
            // Display all doctors with their availability status
            for (Doctor doctor : MainApp.doctors) {
                boolean isAvailable = false;

                // Check if the doctor has dates in the availability map
                Map<Integer, List<Integer>> availability = doctor.getAvailability();

                // Check if any date has at least one available slot
                for (List<Integer> slots : availability.values()) {
                    if (!slots.isEmpty()) {
                        isAvailable = true;
                        break;
                    }
                }

                // Display the doctor with their availability status
                if (isAvailable) {
                    System.out.println("[" + (availableDoctors.size() + 1) + "] " + doctor.getName());
                } else {
                    System.out.println("[" + (availableDoctors.size() + 1) + "] " + doctor.getName() + " - Unavailable");
                }

                availableDoctors.add(doctor);
            }

            System.out.println("[-1] Exit");
            System.out.print("Select a doctor or -1 to exit: \n");
            String input = sc.nextLine();

            // Exit if the user enters -1
            if (input.equals("-1")) {
                System.out.println("Exiting doctor selection.");
                return null;
            }

            // Validate the user input
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= availableDoctors.size()) {
                    // Return the selected doctor
                    return availableDoctors.get(choice - 1);
                } else {
                    System.out.println("Invalid choice. Please enter a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public int displayAvailabeDates(Doctor doctor, Scanner sc) {
        Map<Integer, List<Integer>> availability = doctor.getAvailability();

        if (availability.isEmpty()) {
            System.out.println("No available slots for Dr. " + doctor.getName() + ".");
            return -1; // Indicate no available dates
        }

        List<Integer> dateKeys = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                dateKeys.add(entry.getKey());
            }
        }

        // If no dates have available slots, return -1
        if (dateKeys.isEmpty()) {
            System.out.println("No available slots for Dr. " + doctor.getName() + ".");
            return -1;
        }

        System.out.println("Available Appointment Dates for Dr. " + doctor.getName() + ":");

        // Display the dates with available slots
        for (int i = 0; i < dateKeys.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + convertDateFormat(dateKeys.get(i).toString()));
        }
        System.out.println("[-1] Exit");

        while (true) {
            System.out.print("Select a date or -1 to exit: ");
            String input = sc.nextLine();
            try {
                int choice = Integer.parseInt(input);

                if (choice == -1) {
                    return -1; // User chose to exit
                } else if (choice >= 1 && choice <= dateKeys.size()) {
                    // Return the selected date in DDMMYYYY format
                    return dateKeys.get(choice - 1);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public int displayAvailableSlots(List<Integer> slots, Scanner sc) {
        if (slots.isEmpty()) {
            System.out.println("No available time slots.");
            return -1;
        }

        System.out.println("Available Time Slots:");
        for (int i = 0; i < slots.size(); i++) {
            int slot = slots.get(i);
            int hour = 9 + (slot - 1); // Convert slot number to hour
            System.out.printf("[%d] %02d00\n", (i + 1), hour);
        }
        System.out.println("[-1] Exit");

        // Prompt user to select a time slot
        while (true) {
            System.out.print("Select a time slot or -1 to exit: ");
            String input = sc.nextLine();
            try {
                int choice = Integer.parseInt(input);

                if (choice == -1) {
                    return -1; // User chose to exit
                } else if (choice >= 1 && choice <= slots.size()) {
                    return slots.get(choice - 1); // Return the selected slot
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
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

    public int viewScheduledAppointments() {
        System.out.println("Scheduled Appointments for " + getName() + ":");
        System.out.println("-------------------------------");

        int counter = 0;
        boolean hasAppointments = false;

        for (Appointment appointment : appointments) {
            // Check only for PENDING or CONFIRMED appointments
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING
                    || appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {

                System.out.println(appointment); // Assumes Appointment has a proper toString() implementation
                System.out.println("");
                hasAppointments = true;
                counter++;
            }
        }

        if (!hasAppointments) {
            System.out.println("No scheduled appointments.");
            return 0;
        }

        System.out.println("-------------------------------");
        System.out.println("");
        return counter;
    }

    public void rescheduleAppointment(Scanner sc) {
        // This would contain logic to reschedule an appointment
        System.out.println("Rescheduling appointment for patient: " + getName());
        System.out.println("");
        if (viewScheduledAppointments() == 0) {
            System.out.println("No appointments to reschedule.");
            System.out.println("");
            return;
        }
        System.out.print("Enter the appointment ID to reschedule or (-1) to cancel: ");
        int appointmentID = sc.nextInt();
        if (appointmentID == -1) {
            System.out.println("Rescheduling canceled.");
            System.out.println("");
            return;
        }
        sc.nextLine(); // Consume newline character

        Map.Entry<Doctor, Appointment> entry = checkAvaiDoctor(sc);
        if (null == entry) {
            System.out.println("Rescheduling canceled.");
            System.out.println("");
            return;
        }
        Doctor selectedDoctor = entry.getKey();
        Appointment newAppointment = entry.getValue();

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                // Add back the old time slot to the doctor's availability
                Doctor oldDoctor = new Doctor().getDoctorById(appointment.getDoctorID());
                if (oldDoctor != null) {
                    LocalDate oldDate = appointment.getAppointmentDateTime().toLocalDate();
                    int oldDateKey = Integer.parseInt(oldDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
                    int oldHour = appointment.getAppointmentDateTime().getHour();
                    int oldTimeSlot = (oldHour - 9) + 1; // Convert hour to slot number (1-9)
                    oldDoctor.setAvailability(oldDateKey, oldTimeSlot);
                }

                appointment.setDoctorID(selectedDoctor.getUserId());
                appointment.setDateTime(newAppointment.getAppointmentDateTime());
                appointment.setAppointmentStatus(AppointmentStatus.PENDING);
                appointment.setAppointmentOutcomeRecord(null);
            }
        }

        System.out.println("Appointment rescheduled successfully.");
    }

    public int cancelAppointment(Scanner sc) {

        System.out.println("Cancelling appointment for patient: " + getName());
        if (viewScheduledAppointments() == 0) {
            System.out.println("\nNo appointments to cancel.");
            return -1;
        }

        System.out.print("Enter the appointment ID to cancel or -1 to exit: ");
        int appointmentID;

        try {
            appointmentID = sc.nextInt();
            sc.nextLine(); // Consume the newline character
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid appointment ID.");
            sc.nextLine(); // Clear the buffer
            return -1;
        }

        if (appointmentID == -1) {
            System.out.println("Cancellation process aborted.");
            return -1;
        }

        for (Appointment appointment : this.appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                // Update the status to "Canceled"
                appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);

                // Update the appointment in the main list
                for (Appointment mainAppAppointment : getAppointments()) {
                    if (mainAppAppointment.getAppointmentID() == appointmentID) {
                        mainAppAppointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
                        break;
                    }
                }

                Doctor doctor = new Doctor();
                doctor = doctor.getDoctorById(appointment.getDoctorID());

                if (doctor != null) {
                    LocalDate appointmentDate = appointment.getAppointmentDateTime().toLocalDate();
                    int dateKey = Integer.parseInt(appointmentDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
                    int hour = appointment.getAppointmentDateTime().getHour();
                    int timeSlot = (hour - 9) + 1; // Convert hour to slot number (1-9)

                    doctor.setAvailability(dateKey, timeSlot);
                }

                System.out.println("Appointment canceled successfully.");
                System.out.println("");
                return appointmentID;
            }
        }

        // If appointment is not found
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

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public Patient getPatientById(String patientId) {
        for (Patient patient : patients) {
            if (patient.getUserId().equals(patientId)) {
                return patient;
            }
        }
        return null;
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

    private String convertDateFormat(String dateStr) {
        try {
            // Define the input format: DDMMYYYY
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

            // Parse the input string to LocalDate
            LocalDate date = LocalDate.parse(dateStr, inputFormatter);

            // Define the output format: yyyy-MM-dd
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Convert the LocalDate to the desired format
            return date.format(outputFormatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + dateStr);
            return null;
        }
    }

    private LocalDate convertToLocalDate(int dateKey) {
        try {
            String dateStr = String.valueOf(dateKey);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + dateKey);
            return null;
        }
    }

    private String convertSlotToTime(int slot) {
        int hour = 9 + (slot - 1); // Convert slot number to hour
        return String.format("%02d00", hour); // Format as HHmm
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
        boolean notFound = true;
        Scanner sc = new Scanner(System.in);
        // Check and display appointments that are confirmed or rejected
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED
                    || appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                if (notFound) {
                    System.out.println("\n---- Notifications ----");
                    notFound = false;
                }
                System.out.println("Upcoming Appointment"
                        + " with Dr. " + new Doctor().getDoctorById(appointment.getDoctorID()).getName()
                        + " on " + appointment.getAppointmentDateTime().format(formatter)
                        + " is " + appointment.getAppointmentStatus() + "!");
            }
        }
        while (choice != 9) {
            System.out.println();
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
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
            }
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
                    showDoctorAvailability();
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
                    System.out.println("Logging out... Returning to main login page...");
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
