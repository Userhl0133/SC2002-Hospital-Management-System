package HMS.models;

import HMS.enums.*;
import java.util.*;
import java.time.LocalDateTime;

import static HMS.MainApp.administrators;
import static HMS.MainApp.*;

public class Doctor extends User{

    // Attributes
    // availability hashmap contains time slots where the doctor is unavailable, in 1 hour blocks
    // Key: Date in (DDMMYYYY) format, Value: Timeslot from int 1-9 (0900-1700)
    private Map<Integer, List<Integer>> availability;
    private int age;

    public Doctor() {
    }

    // Constructor
    public Doctor(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);  // Passing all required parameters to User
        this.availability = new HashMap<>();
        this.age = age;
    }

    public Doctor getDoctorById(String doctorId) {
        for (Doctor doctor : doctors) {
            if (doctor.getUserId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    public String getName() {
        return super.getName();
    }

    public Map<Integer, List<Integer>> getAvailability() {
         Map<Integer, List<Integer>> copy = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }


    // Methods
    public void viewPersonalSchedule() {
        // Viewing upcoming appointments
        System.out.println("Personal Schedule:");
        for (Patient patient : patients){
            for (Appointment appointment : patient.getAppointments()) {
                if(appointment.getDoctorID() == super.getUserId()){
                    System.out.println("Appointment " + appointment.getAppointmentID());
                    System.out.println("Patient: " + patient.getName());
                    System.out.println("Date and Time: " + appointment.getDateTime().toString());
                    System.out.println("Status: " + appointment.getAppointmentStatus());
                    System.out.println();
                }
            }
        }

        //TODO: personal availability
    }

    public void updatePatientParticular() {
        // Implementation for updating patient particulars

    }

    public void viewUpcomingAppointment() {
        // Implementation for viewing upcoming appointments
        System.out.println("\nUpcoming appointments:");
        for (Patient patient : patients){
            for (Appointment appointment : patient.getAppointments()) {
                if(appointment.getDoctorID() == super.getUserId()){
                    System.out.println(appointment);
                    System.out.println();
                }
            }
        }
    }

    public void updateAppointmentOutcomeRecord() {
        // Implementation for updating appointment outcome records
    }

    public void viewAppointmentOutcomeRecord() {
        // Implementation for viewing appointment outcome records
    }

    public void createAppointmentOutcomeRecord() {
        // Implementation for creating an appointment outcome record
    }

    public void cancelAppointment() {
        // Implementation for canceling an appointment
    }

    // Returns false if time slot removed, true if added
    public boolean setAvailability(int date, int time) {
        // If date exists
        if (this.availability.containsKey(date)) {
            if (this.availability.get(date).contains(time)) {
                this.availability.get(date).remove(Integer.valueOf(time));
                return false;
            }
            else{
                this.availability.get(date).add(time);
                return true;
            }
        }
        // If date does not exist
        else{
            List<Integer> timeSlots = new ArrayList<>();
            timeSlots.add(time);
            this.availability.put(date, timeSlots);
            return true;
        }
    }

    public void updateAppointment() {
        // Implementation for updating an appointment
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole());
    }

    public void showMenu() {
        int choice = 0;
        boolean notFound = true;
        Scanner sc = new Scanner(System.in);
        while (choice != 8) {
            System.out.println("=====================");
            System.out.println("-----Doctor Menu-----");
            System.out.println("=====================");
            System.out.println("1.View Patient Medical Records");
            System.out.println("2.Update Patient Medical Records");
            System.out.println("3.View Personal Schedule");
            System.out.println("4.Set Availability for Appointments");
            System.out.println("5.Accept or Decline Appointment Requests");
            System.out.println("6.View Upcoming Appointments");
            System.out.println("7.Record Appointment Outcome");
            System.out.println("8.Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            switch(choice) {
                case 1 :
                    // View Patient Medical Records
                    for (Patient patient : patients) {
                        for (Appointment appointment : patient.getAppointments()) {
                            if (appointment.getDoctorID() == super.getUserId()) {
                                System.out.println(patient);
                                for (Appointment appointment1 : patient.getAppointments()) {
                                    System.out.println(appointment1);
                                }
                                System.out.println();
                                break;
                            }
                        }
                    }
                    break;

                case 2 :
                    // Update Patient Medical Records

                    break;

                case 3 :
                    // View Personal Schedule
                    viewPersonalSchedule();
                    break;

                case 4 :
                    System.out.println("Set unavailable timeslot");
                    System.out.println("Enter date (DDMMYYYY): ");
                    int date = sc.nextInt();

                    // Print timeslots
                    int counter = 1;
                    for (int timeslot = 900; timeslot <= 1700; timeslot += 100) {
                        System.out.printf("[%d] %04d", counter, timeslot);
                        if (this.availability.containsKey(date) && this.availability.get(date).contains(counter)) {
                            System.out.print(" (unavailable)");
                        }
                        System.out.println();
                        counter++;
                    }

                    System.out.println("Enter a timeslot number: ");
                    int time = sc.nextInt();

                    if (time < 1 || time > 9) {
                        System.out.println("Invalid timeslot. Please try again.");
                        break;
                    }

                    if (setAvailability(date, time)) {
                        System.out.println(date + " " + (time+8)*100 + " set as unavailable");
                    }
                    else {
                        System.out.println(date + " " + (time+8)*100 + " set as available");
                    }
                    break;

                case 5 :
                    // Accept or Decline Appointment Requests

                    // Display pending appointments
                    System.out.println("\nPending appointments: ");
                    for (Patient patient : patients) {
                        for (Appointment appointment : patient.getAppointments()) {
                            if(appointment.getDoctorID() == super.getUserId()){
                                if(appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                                    System.out.println(appointment);
                                    System.out.println();
                                }
                            }
                        }
                    }
                    // Accept or decline
                    notFound = true;
                    while (notFound) {
                        System.out.println("Enter appointment ID to accept or decline: ");
                        int id = sc.nextInt();
                        for (Patient patient : patients) {
                            for (Appointment appointment : patient.getAppointments()) {
                                if (appointment.getAppointmentID() == id) {
                                    System.out.println("Accept appointment " + id + "? (Y/N): ");
                                    String answer = sc.next();
                                    if (answer.equalsIgnoreCase("Y")) {
                                        appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
                                        System.out.println("Appointment " + id + " is confirmed\n");
                                        notFound = false;
                                    }
                                    else if (answer.equalsIgnoreCase("N")) {
                                        appointment.setAppointmentStatus(AppointmentStatus.DECLINED);
                                        System.out.println("Appointment " + id + " is declined.\n");
                                        notFound = false;
                                    }
                                }
                            }
                        }
                        if (notFound) {
                            System.out.println("Invalid input. Please try again");
                        }
                    }

                    break;

                case 6 :
                    // View Upcoming Appointments
                    viewUpcomingAppointment();
                    break;

                case 7 :
                    // Record Appointment Outcome

                    System.out.println("\n Appointments:");
                    for (Patient patient : patients){
                        for (Appointment appointment : patient.getAppointments()) {
                            if(appointment.getDoctorID() == super.getUserId()){
                                System.out.println(appointment);
                                System.out.println();
                            }
                        }
                    }

                    notFound = true;
                    while (notFound) {
                        System.out.println("\nEnter appointment ID:  ");
                        int id = sc.nextInt();
                        for (Patient patient : patients) {
                            for (Appointment appointment : patient.getAppointments()) {
                                if (appointment.getAppointmentID() == id) {

                                    // Service type
                                    int i = 1;
                                    System.out.println("Enter service type [1/2/3]:  ");
                                    for (ServiceType serviceType : ServiceType.values()) {
                                        System.out.println("[" + i + "] " + serviceType);
                                    }
                                    int serviceTypeIndex = sc.nextInt();
                                    ServiceType serviceType = ServiceType.values()[serviceTypeIndex];


                                    // Prescribed medications
                                    i = 1;
                                    for (Medication medication : medications) {
                                        System.out.println("[" + i + "] " + medication.getMedicationName());
                                    }
                                    System.out.println("Enter prescribed medication(s), separated by comma [eg. 1,2,3]:  ");
                                    String inputMedications = sc.nextLine();
                                    String[] medicationsIndex = inputMedications.split(",");
                                    List<Medication> medicationsList = new ArrayList<>();
                                    for (String index : medicationsIndex) {
                                        medicationsList.add(medications.get(Integer.parseInt(index)));
                                    }

                                    // Consultation Notes
                                    System.out.println("Enter consultation notes: ");
                                    String inputConsultationNotes = sc.nextLine();

                                    AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(1, serviceType, medicationsList, inputConsultationNotes);
                                    appointment.setAppointmentOutcomeRecord(appointmentOutcomeRecord);
                                    notFound = false;

                                }
                            }
                        }
                        if (notFound) {
                            System.out.println("Invalid input. Please try again");
                        }
                    }
                    break;

                case 8 :
                    // Logout
                    System.out.println("Logging out");
                    break;

                default :
                    System.out.println("Invalid Option");
            }
        }
    }
}
