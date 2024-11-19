package HMS.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import static HMS.MainApp.doctors;
import static HMS.MainApp.medications;
import static HMS.MainApp.patients;
import HMS.enums.AppointmentStatus;
import HMS.enums.Gender;
import HMS.enums.PrescriptionStatus;
import HMS.enums.Role;
import HMS.enums.ServiceType;
import static HMS.models.Appointment.getAppointments;

public class Doctor extends User{

    // Attributes
    // availability hashmap contains time slots where the doctor is available, in 1 hour blocks. Key: Date in (DDMMYYYY) format, Value: List of timeslots from int 1-9 (0900-1700)
    private Map<Integer, List<Integer>> availability;
    private int age;

    // Empty constructor
    public Doctor() {

    }

    // Constructor
    public Doctor(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);  // Passing all required parameters to User
        this.availability = new HashMap<>();
        this.age = age;
    }

    // Returns doctor with input ID
    public Doctor getDoctorById(String doctorId) {
        for (Doctor doctor : doctors) {
            if (doctor.getUserId().equals(doctorId)) {
                return doctor;
            }
        }
        return null;
    }

    // Getter for Age
    public int getAge() {
        return age;
    }

    // Setter for Age
    public void setAge(int age) {
        this.age = age;
    }

    // Getter for availability
    public Map<Integer, List<Integer>> getAvailability() {
        Map<Integer, List<Integer>> copy = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }


    // Methods
    // View patient medical records
    public void viewPatientMedicalRecords() {
        boolean notFound = true;
        for (Patient patient : patients) {
            for (Appointment appointment : patient.getAppointments()) {
                if (Objects.equals(appointment.getDoctorID(), super.getUserId())) {
                    patient.viewParticular();
                    patient.viewCompletedAppointments();
                    System.out.println();
                    notFound = false;
                    break;
                }
            }
        }
        if(notFound){
            System.out.println("There are no patients under " + super.getUserId() + " " + super.getName());
        }
    }

    // Update patient medical records
    public void updatePatientMedicalRecords() {
        boolean notFound = true;
        Scanner sc = new Scanner(System.in);
        try {
            // View patients
            notFound = true;
            for (Patient patient : patients) {
                for (Appointment appointment : patient.getAppointments()) {
                    if (Objects.equals(appointment.getDoctorID(), super.getUserId())) {
                        System.out.println(patient.getPatientID() + "    " + patient.getName());
                        System.out.println();
                        notFound = false;
                        break;
                    }
                }
            }
            if (notFound) {
                System.out.println("There are no patients under " + super.getUserId() + " " + super.getName());
                return;
            }

            Patient patient;
            while(true) {
                System.out.println("Enter Patient ID: ");
                String inputPatientId = sc.nextLine();
                patient = new Patient().getPatientById(inputPatientId);
                if (patient != null) {
                    break;
                }
                System.out.println("Invalid Patient ID, please try again");
            }

            System.out.println();
            System.out.println(patient.getPatientID() + " " + patient.getName());
            notFound = true;
            for(Appointment appointment : patient.getAppointments()) {
                System.out.println("\nAppoointment ID: " + appointment.getAppointmentID());
                System.out.println("Diagnosis: " + appointment.getAppointmentOutcomeRecord().getDiagnosis());
                System.out.println("Treatment plan: " + appointment.getAppointmentOutcomeRecord().getTreatmentPlan());
                notFound = false;
            }
            if(notFound){
                System.out.println("There are no appointments under " + patient.getPatientID() + " " + patient.getName());
            }

            Appointment appointment = null;
            notFound = true;
            while(true) {
                System.out.println("Enter Appointment ID to update: ");
                int inputAppointmentId = sc.nextInt();
                for (Appointment x : patient.getAppointments()) {
                    if (Objects.equals(x.getAppointmentID(), inputAppointmentId)) {
                        appointment = x;
                        notFound = false;
                        break;
                    }
                }
                if(notFound){
                    System.out.println("Invalid Appointment ID, please try again");
                    continue;
                }
                break;
            }

            System.out.println("[1] Update diagnosis");
            System.out.println("[2] Update treatment plan");
            System.out.println("Select an option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1:
                    System.out.println("Enter updated diagnosis: ");
                    String diagnosis = sc.nextLine();
                    appointment.getAppointmentOutcomeRecord().setDiagnosis(diagnosis);
                    break;

                case 2:
                    System.out.println("Enter updated treatment plan: ");
                    String treatmentPlan = sc.nextLine();
                    appointment.getAppointmentOutcomeRecord().setDiagnosis(treatmentPlan);
                    break;

                default:
                    System.out.println("Invalid option");
            }

            System.out.println("Updated medical record successfully");
            for(Appointment appointment1 : patient.getAppointments()) {
                System.out.println("\nAppoointment ID: " + appointment1.getAppointmentID());
                System.out.println("Diagnosis: " + appointment1.getAppointmentOutcomeRecord().getDiagnosis());
                System.out.println("Treatment plan: " + appointment1.getAppointmentOutcomeRecord().getTreatmentPlan());
            }
        }

        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // View personal schedule
    public void viewPersonalSchedule() {
        // Viewing upcoming appointments
        boolean notFound = true;
        System.out.println("Upcoming Appointments:");
        for (Patient patient : patients){
            for (Appointment appointment : patient.getAppointments()) {
                if(Objects.equals(appointment.getDoctorID(), super.getUserId())
                        && appointment.getAppointmentStatus() != AppointmentStatus.COMPLETED
                        && appointment.getAppointmentStatus() != AppointmentStatus.CANCELLED){
                    System.out.println("Appointment " + appointment.getAppointmentID());
                    System.out.println("Patient: " + patient.getName());
                    System.out.println("Date and Time: " + appointment.getDateTime().toString());
                    System.out.println("Status: " + appointment.getAppointmentStatus());
                    notFound = false;
                }
            }
        }
        if(notFound){
            System.out.println("No appointments found");
        }

        // View available timeslots
        System.out.println("\nAvailable timeslots:");
        List<Integer> timeslots = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            timeslots.add(i);
        }
        notFound = true;
        for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
            int date = entry.getKey();
            for (Integer time : timeslots) {
                if(!entry.getValue().contains(time)){
                    String formattedDate = convertDateFormat(String.valueOf(date));
                    System.out.printf("%s %04d - %04d\n", formattedDate, (time+8)*100, (time+9)*100);
                    notFound = false;
                }
            }
        }
        if(notFound) {
            System.out.println("No timeslots available");
        }
        System.out.println();
    }

    // View upcoming appointments
    public void viewUpcomingAppointment() {
        // Implementation for viewing upcoming appointments
        boolean notFound = true;
        System.out.println("Upcoming appointments:");
        for (Patient patient : patients){
            for (Appointment appointment : patient.getAppointments()) {
                if(Objects.equals(appointment.getDoctorID(), super.getUserId())){
                    System.out.println(appointment);
                    System.out.println();
                    notFound = false;
                }
            }
        }
        if(notFound){
            System.out.println("No upcoming appointments");
        }
    }

    // Set availability : Returns false if time slot removed, true if added
    public boolean setAvailability(int date, int time) {
        // Initialize the list if the date does not exist in the map
        if (!this.availability.containsKey(date)) {
            this.availability.put(date, new ArrayList<>());
        }

        // Now, the list is guaranteed to be non-null
        List<Integer> slots = this.availability.get(date);
        if (slots.contains(time)) {
            slots.remove(Integer.valueOf(time));
            return false; // Time slot removed
        } else {
            slots.add(time);
            return true; // Time slot added
        }
    }

    // Record appointment outcome
    public void setAppointmentOutcome() {
        boolean notFound = true;
        List<Integer> validIds = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Appointments:");
        for (Patient patient : patients){
            for (Appointment appointment : patient.getAppointments()) {
                if(Objects.equals(appointment.getDoctorID(), super.getUserId())){
                    System.out.println(appointment);
                    System.out.println();
                    validIds.add(appointment.getAppointmentID());
                    notFound = false;
                }
            }
        }
        if (notFound) {
            System.out.println("No Appointments found");
            return;
        }

        notFound = true;
        while (notFound) {
            System.out.println("\nEnter appointment ID: ");
            int id = sc.nextInt();
            if (!validIds.contains(id)) {
                System.out.println("Invalid appointment ID");
                continue;
            }
            for (Patient patient : patients) {
                for (Appointment appointment : patient.getAppointments()) {
                    if (appointment.getAppointmentID() == id) {
                        // Service type
                        ServiceType serviceType;
                        while(true){
                            int i = 1;
                            for (ServiceType service : ServiceType.values()) {
                                System.out.println("[" + i++ + "] " + service);
                            }
                            System.out.println("\nEnter one service type [1/2/3]:  ");
                            int serviceTypeIndex = sc.nextInt();
                            try {
                                if(serviceTypeIndex == 1 || serviceTypeIndex == 2 || serviceTypeIndex == 3){
                                    serviceType = ServiceType.values()[serviceTypeIndex-1];
                                    break;
                                }
                            }
                            catch(Exception e){
                                System.out.println("Invalid input. Please try again");
                            }
                        }

                        // Prescribed medications
                        int i = 1;
                        for (Medication medication : medications) {
                            System.out.println("[" + i++ + "] " + medication.getMedicationName());
                        }

                        System.out.println("\nEnter prescribed medication(s), separated by comma [eg. 1,2,3]:  ");
                        sc.nextLine();
                        String inputMedications = sc.nextLine();
                        String[] medicationsIndex = inputMedications.split(",");
                        List<Prescription> prescriptionList = new ArrayList<>();
                        for (String index : medicationsIndex) {
                            System.out.println("\nEnter quantity for " + medications.get(Integer.parseInt(index)-1).getMedicationName() + ": ");
                            int inputQuantity = sc.nextInt();
                            System.out.println("Frequency: To be taken <frequency> times every <period>");
                            System.out.println("Enter frequency: ");
                            sc.nextLine();
                            String inputFrequency = sc.nextLine();
                            System.out.println("Frequency: To be taken " + inputFrequency + " times every <period>");
                            System.out.println("Enter period: ");
                            String inputPeriod = sc.nextLine();
                            String frequency = "To be taken " + inputFrequency + " times every " + inputPeriod;
                            Prescription prescription = new Prescription(medications.get(Integer.parseInt(index)-1), PrescriptionStatus.PENDING, inputQuantity, frequency);
                            prescriptionList.add(prescription);
                        }

                        // Consultation Notes
                        System.out.println("\n\nEnter Diagnosis: ");
                        String inputDiagnosis = sc.nextLine();
                        System.out.println("\nEnter Treatment plan: ");
                        String inputTreatmentPlan = sc.nextLine();
                        System.out.println("\nEnter consultation notes: ");
                        String inputConsultationNotes = sc.nextLine();
                        AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(1, serviceType, inputDiagnosis, inputTreatmentPlan, prescriptionList,inputConsultationNotes);
                        appointment.setAppointmentOutcomeRecord(appointmentOutcomeRecord);
                        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
                        notFound = false;
                        System.out.println("\nOutcome record for appointment " + id + " added successfully\n");
                        System.out.println(appointmentOutcomeRecord);
                    }
                }
            }
            if (notFound) {
                System.out.println("Invalid input. Please try again");
            }
        }
    }

    // Convert DDMMYYY to YYYY-MM-DD
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

    @Override
    // Print doctor object
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s, Age: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole(), age);
    }

    // Show doctor menu
    public void showMenu() {
        int choice = 0;
        boolean notFound = true;
        ArrayList<Integer> validIds = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        // Check and display pending appointments
        for (Appointment appointment : getAppointments()) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.PENDING && Objects.equals(appointment.getDoctorID(), super.getUserId())) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                if(notFound){
                    System.out.println("\n---- Notifications ----");
                    notFound = false;
                }
                System.out.println("Pending Appointment"
                        + " with Patient " + new Patient().getPatientById(appointment.getPatientID()).getName()
                        + " on " + appointment.getAppointmentDateTime().format(formatter));
            }
        }

        while (choice != 8) {
            System.out.println();
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
            try {
                choice = sc.nextInt();
            }
            catch(Exception e) {}
            sc.nextLine();
            System.out.print("\n");
            switch(choice) {
                case 1 :
                    // View Patient Medical Records
                    viewPatientMedicalRecords();
                    break;

                case 2 :
                    // Update Patient Medical Records
                    updatePatientMedicalRecords();
                    break;


                case 3 :
                    // View Personal Schedule
                    viewPersonalSchedule();
                    break;

                case 4 :
                    int date, time;
                    LocalDate dateTime;
                    System.out.println("Set timeslot availability");
                    while (true){
                        System.out.println("Enter date (DDMMYYYY): ");
                        try {
                            date = sc.nextInt();
                            // Check if valid date
                            String dateStr = String.format("%08d", date);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                            dateTime = LocalDate.parse(dateStr, formatter);
                            break;
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid Date. Please try again");
                        }
                    }

                    // Print timeslots
                    int counter = 1;

                    // Check for appointments
                    List<Integer> appointmentTimes = new ArrayList<>();
                    for (Appointment appointment : getAppointments()) {
                        if (Objects.equals(appointment.getDoctorID(), super.getUserId())) {
                            if(appointment.getDateTime().toLocalDate().equals(dateTime)){
                                appointmentTimes.add(appointment.getDateTime().getHour()-8);
                            }
                        }
                    }
                    for (int timeslot = 900; timeslot <= 1700; timeslot += 100) {
                        System.out.printf("[%d] %04d", counter, timeslot);
                        if (appointmentTimes.contains(counter)) {
                            System.out.print(" (appointment)");
                        } else if (this.availability.containsKey(date) && this.availability.get(date).contains(counter)) {
                            System.out.print(" (available)");
                        } else {
                            System.out.print(" (unavailable)");
                        }
                        System.out.println();
                        counter++;
                    }

                    while (true){
                        System.out.println("Enter a timeslot number: ");
                        time = sc.nextInt();
                        if(appointmentTimes.contains(time)) {
                            System.out.println("Unable to set appointment timeslot as available.");
                            continue;
                        }
                        if ((time >= 0 && time <= 9)) {
                            break;
                        }
                        System.out.println("Invalid timeslot. Please try again.");
                    }
                    String formattedDate = convertDateFormat(String.valueOf(date));

                    if (setAvailability(date, time)) {
                        System.out.printf("%s %04d set as available\n", formattedDate, (time+8)*100);
                    }
                    else {
                        System.out.printf("%s %04d set as unavailable\n", formattedDate, (time+8)*100);
                    }
                    break;

                case 5 :
                    // Accept or Decline Appointment Requests

                    // Display pending appointments
                    notFound = true;
                    validIds = new ArrayList<>();
                    System.out.println("Pending appointments: ");
                    for (Patient patient : patients) {
                        for (Appointment appointment : patient.getAppointments()) {
                            if(Objects.equals(appointment.getDoctorID(), super.getUserId())){
                                if(appointment.getAppointmentStatus() == AppointmentStatus.PENDING) {
                                    validIds.add(appointment.getAppointmentID());
                                    System.out.println(appointment);
                                    System.out.println();
                                    notFound = false;
                                }
                            }
                        }
                    }
                    if (notFound) {
                        System.out.println("No Appointments found");
                        break;
                    }
                    // Accept or decline
                    notFound = true;
                    while (notFound) {
                        System.out.println("Enter appointment ID: ");
                        int id = 0;
                        try{
                            id = sc.nextInt();
                            if(!validIds.contains(id)){
                                System.out.println("Invalid appointment ID");
                                continue;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid appointment ID");
                            continue;
                        }
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
                                        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);

                                        // Free up availability
                                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
                                        String dateString = appointment.getDateTime().format(dateFormatter);
                                        int dateInt = Integer.parseInt(dateString);
                                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
                                        String timeString = appointment.getDateTime().format(timeFormatter);
                                        int timeInt = Integer.parseInt(timeString);
                                        setAvailability(dateInt, timeInt);

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
                    setAppointmentOutcome();
                    break;

                case 8 :
                    // Logout
                    System.out.println("Logging out... Returning to main login page...");
                    break;

                default :
                    System.out.println("Invalid Option");
            }
        }
    }
}