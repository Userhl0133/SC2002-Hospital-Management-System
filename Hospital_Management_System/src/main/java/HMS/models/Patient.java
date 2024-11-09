package HMS.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        System.out.print("Enter new Date of Birth (yyyy-MM-dd): ");
        String dobInput = sc.nextLine();
        try {
            LocalDate newDOB = LocalDate.parse(dobInput);
            this.setDOB(newDOB);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            return;
        }
        System.out.print("Enter new Contact Info: ");
        this.setContactInfo(sc.nextLine());
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
    }

    // Placeholder method for viewing appointment details
    public void viewUpcomingAppointment() {
        // This would contain logic to display appointment details
        System.out.println("Viewing appointment details for patient: " + getName());
    }

    public void scheduleAppointment(Scanner sc) {
        sc = new Scanner(System.in);
        System.out.println("Scheduling appointment for patient: " + getName());

        Map.Entry<Doctor, Appointment> entry = checkAvaiDoctor(sc);
        Doctor selectedDoctor = entry.getKey();
        Appointment newAppointment = entry.getValue();

        this.appointments.add(newAppointment);
        MainApp.appointments.add(newAppointment);

        System.out.println("Appointment scheduled with Dr. " + selectedDoctor.getName() + " on " + newAppointment.getAppointmentDateTime());
    }

    public Map.Entry<Doctor, Appointment> checkAvaiDoctor(Scanner sc) {
        System.out.println("Available Doctors: ");
        int counter = 1;
        boolean validDoctorID = false;
        String doctorID = "";
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
                    break;
                }
            }
            if (!validDoctorID) {
                System.out.println("Invalid Doctor ID. Please try again.");
            }
        }
        Doctor doctorInstance = new Doctor();
        Doctor selectedDoctor = doctorInstance.getDoctorById(doctorID);
        System.out.println("Selected Doctor: " + selectedDoctor.getName());

        System.out.println("Available Appointment Slots: ");
        System.out.println("");
        System.out.print("Enter Appointment Number: ");
        Map<Integer, List<Integer>> availability = selectedDoctor.getAvailability();
        for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
            System.out.println("Day: " + entry.getKey() + " Slots: " + entry.getValue());
        }
        System.out.print("Enter the day number: ");
        int day = sc.nextInt();
        System.out.print("Enter the slot number: ");
        int slot = sc.nextInt();
        LocalDateTime appointmentDateTime = LocalDateTime.of(LocalDate.now().with(DayOfWeek.of(day)), LocalTime.of(slot, 0));

        Appointment newAppointment = new Appointment(this.getUserId(), "", appointmentDateTime, AppointmentStatus.PENDING, null);
        return Map.entry(selectedDoctor, newAppointment);
    }

    public void viewAllAppointment() {
        if (appointments == null) {
            System.out.println("No appointments scheduled");
        } else {
            System.out.println("Appointments scheduled for " + getName());
            System.out.println("-------------------------------");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
            System.out.println("-------------------------------");
        }
    }

    public void rescheduleAppointment(Scanner sc) {
        // This would contain logic to reschedule an appointment
        System.out.println("Rescheduling appointment for patient: " + getName());
        System.out.println("Select an appointment to reschedule: ");
        viewAllAppointment();
        System.out.print("Enter the appointment ID: ");
        int appointmentID = sc.nextInt();

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
        viewAllAppointment();
        System.out.print("Enter the appointment ID: ");
        int appointmentID = sc.nextInt();

        for (Appointment appointment : appointments) {
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
                    break;

                case 2:
                    updateParticular(sc);
                    break;

                case 3:
                    // View Available Appointment Slots
                    checkAvaiDoctor(sc);
                    break;

                case 4:
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
                    viewAllAppointment();
                    break;

                case 8:
                    // View Past Appointment Outcome Records

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
