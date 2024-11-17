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

        while (true) {
            System.out.print("Enter new Date of Birth (yyyy-MM-dd) or (-1) to cancel: ");
            String dobInput = sc.nextLine();
            if (dobInput.equals("-1")) {
                System.out.println("Update canceled. No changes were made.");
                return;
            }

            try {
                newDOB = LocalDate.parse(dobInput);
                break;
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
        System.out.print("Enter new Contact Info or (-1) to cancel: ");
        newContactInfo = sc.nextLine();
        if (newContactInfo.equals("-1")) {
            System.out.println("Update canceled. No changes were made.");
            System.out.println("");
            return;
        }
        this.setDOB(newDOB);
        this.setContactInfo(newContactInfo);

        System.out.println("Your details have been updated successfully.");
        viewParticular();
    }

    public void viewParticular() {
        System.out.println("--------------------");
        System.out.println("Patient ID: " + getUserId());
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

            boolean hasAvailableSlots = false;
            for (List<Integer> slots : doctorAvailability.values()) {
                if (!slots.isEmpty()) {
                    hasAvailableSlots = true;
                    break;
                }
            }

            if (!hasAvailableSlots) {
                availability.append("Doctor: ").append(doctor.getName()).append(" - Unavailable\n");
                continue;
            }
            availability.append("Doctor: ").append(doctor.getName()).append("\n");

            for (Map.Entry<Integer, List<Integer>> entry : doctorAvailability.entrySet()) {
                int dateKey = entry.getKey();
                List<Integer> slots = entry.getValue();

                String formattedDate = convertDateFormat(String.valueOf(dateKey));

                availability.append("  Date: ").append(formattedDate).append("\n");

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

        Map.Entry<Doctor, Appointment> entry = checkAvaiDoctor(sc);

        if (entry == null) {
            System.out.println("Appointment scheduling was canceled.");
            return;
        }

        Doctor selectedDoctor = entry.getKey();
        Appointment newAppointment = entry.getValue();
        if (newAppointment != null) {
            this.appointments.add(newAppointment);

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
            return null;
        }

        while (true) {
            Integer selectedDateKey = displayAvailabeDates(selectedDoctor, sc);
            if (selectedDateKey == -1) {
                System.out.println("Scheduling canceled.");
                return null;
            }

            List<Integer> availableSlots = selectedDoctor.getAvailability().get(selectedDateKey);

            int selectedSlot = displayAvailableSlots(availableSlots, sc);
            if (selectedSlot == -1) {
                System.out.println("Scheduling canceled.");
                return null;
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

            selectedDoctor.setAvailability(selectedDateKey, selectedSlot);

            System.out.println("Appointment booked successfully!");
            System.out.println("");

            return Map.entry(selectedDoctor, newAppointment);
        }
    }

    public Doctor selectDoctor(Scanner sc) {
        while (true) {
            System.out.println("Available Doctors:");

            List<Doctor> availableDoctors = new ArrayList<>();

            for (Doctor doctor : MainApp.doctors) {
                boolean isAvailable = false;

                Map<Integer, List<Integer>> availability = doctor.getAvailability();

                for (List<Integer> slots : availability.values()) {
                    if (!slots.isEmpty()) {
                        isAvailable = true;
                        break;
                    }
                }

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

            if (input.equals("-1")) {
                System.out.println("Exiting doctor selection.");
                return null;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= availableDoctors.size()) {
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
            return -1;
        }

        List<Integer> dateKeys = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                dateKeys.add(entry.getKey());
            }
        }

        if (dateKeys.isEmpty()) {
            System.out.println("No available slots for Dr. " + doctor.getName() + ".");
            return -1;
        }

        System.out.println("Available Appointment Dates for Dr. " + doctor.getName() + ":");

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
                    return -1;
                } else if (choice >= 1 && choice <= dateKeys.size()) {
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
            int hour = 9 + (slot - 1);
            System.out.printf("[%d] %02d00\n", (i + 1), hour);
        }
        System.out.println("[-1] Exit");

        while (true) {
            System.out.print("Select a time slot or -1 to exit: ");
            String input = sc.nextLine();
            try {
                int choice = Integer.parseInt(input);

                if (choice == -1) {
                    return -1;
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

        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No completed appointments.");
        } else {
            boolean hasCompleted = false;
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                    System.out.println(appointment);
                    hasCompleted = true;
                }
            }

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
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING
                    || appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {

                System.out.println(appointment);
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
        sc.nextLine();

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
                Doctor oldDoctor = new Doctor().getDoctorById(appointment.getDoctorID());
                if (oldDoctor != null) {
                    LocalDate oldDate = appointment.getAppointmentDateTime().toLocalDate();
                    int oldDateKey = Integer.parseInt(oldDate.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
                    int oldHour = appointment.getAppointmentDateTime().getHour();
                    int oldTimeSlot = (oldHour - 9) + 1;
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

        viewScheduledAppointments();

        System.out.print("Enter the appointment ID to cancel or -1 to exit: ");
        int appointmentID;

        try {
            appointmentID = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid appointment ID.");
            sc.nextLine();
            return -1;
        }

        if (appointmentID == -1) {
            System.out.println("Cancellation process aborted.");
            return -1;
        }

        for (Appointment appointment : this.appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);

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
                    int timeSlot = (hour - 9) + 1;

                    doctor.setAvailability(dateKey, timeSlot);
                }

                System.out.println("Appointment canceled successfully.");
                System.out.println("");
                return appointmentID;
            }
        }

        System.out.println("Appointment not found.");
        return -1;
    }

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
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

            LocalDate date = LocalDate.parse(dateStr, inputFormatter);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
        int hour = 9 + (slot - 1);
        return String.format("%02d00", hour);
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
        // Check and display appointments that are confirmed or rejected
        boolean notFound = true;
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED
                    || appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
                if(notFound) {
                    System.out.println("\n---- Notifications ----");
                    notFound = false;
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewParticular();
                    viewCompletedAppointments();
                    break;

                case 2:
                    updateParticular(sc);
                    break;

                case 3:
                    showDoctorAvailability();
                    break;

                case 4:
                    scheduleAppointment(sc);
                    break;

                case 5:
                    rescheduleAppointment(sc);
                    break;

                case 6:
                    cancelAppointment(sc);
                    break;

                case 7:
                    viewScheduledAppointments();
                    break;

                case 8:
                    viewCompletedAppointments();
                    break;

                case 9:
                    System.out.println("Logging out... Returning to main login page...");
                    break;

                case 10:
                    System.err.println("Hashed Password = "+ this.getHashedPassword());

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
