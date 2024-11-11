package HMS.models;

import HMS.enums.*;
import java.util.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import static HMS.MainApp.*;

public class Doctor extends User{

    // Attributes
    // availability hashmap contains time slots where the doctor is available, in 1 hour blocks
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

        // Populate availability for next 10 days
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        // Populate timeslots from 1 to 9
        List<Integer> timeslots = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            timeslots.add(i);
        }
        // Populate the next 10 dates
        for (int i = 0; i < 10; i++) {
            int dateKey = Integer.parseInt(sdf.format(calendar.getTime()));
            this.availability.put(dateKey, new ArrayList<>(timeslots));
            calendar.add(Calendar.DATE, 1); // Move to the next day
        }
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
                if(Objects.equals(appointment.getDoctorID(), super.getUserId())){
                    System.out.println("Appointment " + appointment.getAppointmentID());
                    System.out.println("Patient: " + patient.getName());
                    System.out.println("Date and Time: " + appointment.getDateTime().toString());
                    System.out.println("Status: " + appointment.getAppointmentStatus());
                    System.out.println();
                }
            }
        }

        System.out.println("Unavailable timeslots:");
        for (Map.Entry<Integer, List<Integer>> entry : availability.entrySet()) {
            int date = entry.getKey();
            for (Integer time : entry.getValue()) {
                System.out.printf("%d %04d - %04d", date, (time+8)*100, (time+9)*100);
            }
        }
        System.out.println();
    }

    public void updatePatientParticular() {
        // Implementation for updating patient particulars

    }

    public void viewUpcomingAppointment() {
        // Implementation for viewing upcoming appointments
        System.out.println("\nUpcoming appointments:");
        for (Patient patient : patients){
            for (Appointment appointment : patient.getAppointments()) {
                if(Objects.equals(appointment.getDoctorID(), super.getUserId())){
                    System.out.println(appointment);
                    System.out.println();
                }
            }
        }
    }

    // Returns false if time slot removed, true if added
    public boolean setAvailability(int date, int time) {
        // If date exists
        if (this.availability.get(date).contains(time)) {
            this.availability.get(date).remove(Integer.valueOf(time));
            return false;
        }
        else{
            this.availability.get(date).add(time);
            return true;
        }
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s, Age: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole(), age);
    }

    public void showMenu() {
        int choice = 0;
        boolean notFound = true;
        Scanner sc = new Scanner(System.in);
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
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice) {
                case 1 :
                    // View Patient Medical Records
                    for (Patient patient : patients) {
                        for (Appointment appointment : patient.getAppointments()) {
                            if (Objects.equals(appointment.getDoctorID(), super.getUserId())) {
                                patient.viewParticular();
                                patient.viewCompletedAppointments();
                                System.out.println();
                                break;
                            }
                        }
                    }
                    break;

                case 2 :
                    // Update Patient Medical Records

                    try {
                        // View patients
                        boolean noPatients = true;
                        for (Patient patient : patients) {
                            for (Appointment appointment : patient.getAppointments()) {
                                if (Objects.equals(appointment.getDoctorID(), super.getUserId())) {
                                    System.out.println(patient.getPatientID() + "    " + patient.getName());
                                    System.out.println();
                                    noPatients = false;
                                    break;
                                }
                            }
                        }
                        if (noPatients) {
                            System.out.println("There are no patients under " + super.getUserId() + " " + super.getName());
                            break;
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
                        System.out.println("Diagnoses: ");
                        if(patient.getDiagnoses() == null || patient.getDiagnoses().isEmpty()){
                            System.out.println("None");
                        }
                        else{
                            for(String diagnosis : patient.getDiagnoses()){
                                System.out.println(diagnosis);
                            }
                        }
                        System.out.println("Treatment plan: ");
                        if(patient.getDiagnoses().isEmpty()){
                            System.out.println("None");
                        }
                        else{
                            for(String treatmentPlan : patient.getTreatmentPlan()){
                                System.out.println(treatmentPlan);
                            }
                        }
                        System.out.println("[1] Add diagnosis");
                        System.out.println("[2] Add treatment plan");
                        System.out.println("Select an option: ");
                        choice = sc.nextInt();
                        sc.nextLine();
                        switch(choice) {
                            case 1:
                                System.out.println("Enter diagnosis: ");
                                String diagnosis = sc.nextLine();
                                patient.addDiagnoses(diagnosis);
                                break;

                            case 2:
                                System.out.println("Enter treatment plan: ");
                                String treatmentPlan = sc.nextLine();
                                patient.addTreatmentPlan(treatmentPlan);
                                break;

                            default:
                                System.out.println("Invalid option");


                        }

                        System.out.println("Updated medical record for " +patient.getPatientID() + " " + patient.getName());
                        System.out.println("Diagnoses: ");
                        if(patient.getDiagnoses().isEmpty()){
                            System.out.println("None");
                        }
                        else{
                            for(String diagnosis : patient.getDiagnoses()){
                                System.out.println(diagnosis);
                            }
                        }
                        System.out.println("Treatment plan: ");
                        if(patient.getTreatmentPlan().isEmpty()){
                            System.out.println("None");
                        }
                        else{
                            for(String treatmentPlan : patient.getTreatmentPlan()){
                                System.out.println(treatmentPlan);
                            }
                        }
                    }

                    catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;


                case 3 :
                    // View Personal Schedule
                    viewPersonalSchedule();
                    break;

                case 4 :
                    int date, time;
                    System.out.println("Set timeslot availability");
                    while (true){
                        System.out.println("Enter date (DDMMYYYY): ");
                        date = sc.nextInt();
                        if (this.availability.containsKey(date)) {
                            break;
                        }
                        System.out.println("Invalid Date. Please try again");
                    }

                    // Print timeslots
                    int counter = 1;
                    for (int timeslot = 900; timeslot <= 1700; timeslot += 100) {
                        System.out.printf("[%d] %04d", counter, timeslot);
                        if (this.availability.containsKey(date) && this.availability.get(date).contains(counter)) {
                            System.out.print(" (available)");
                        }
                        else {
                            System.out.print(" (unavailable)");
                        }
                        System.out.println();
                        counter++;
                    }

                    while (true){
                        System.out.println("Enter a timeslot number: ");
                        time = sc.nextInt();
                        if (time >= 0 && time <= 9) {
                            break;
                        }
                        System.out.println("Invalid timeslot. Please try again.");
                    }

                    if (setAvailability(date, time)) {
                        System.out.printf("%d %04d set as available\n", date, (time+8)*100);
                    }
                    else {
                        System.out.printf("%d %04d set as unavailable\n", date, (time+8)*100);
                    }
                    break;

                case 5 :
                    // Accept or Decline Appointment Requests

                    // Display pending appointments
                    System.out.println("\nPending appointments: ");
                    for (Patient patient : patients) {
                        for (Appointment appointment : patient.getAppointments()) {
                            if(Objects.equals(appointment.getDoctorID(), super.getUserId())){
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
                        System.out.println("Enter appointment ID: ");
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
                                        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);
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
                            if(Objects.equals(appointment.getDoctorID(), super.getUserId())){
                                System.out.println(appointment);
                                System.out.println();
                            }
                        }
                    }

                    notFound = true;
                    while (notFound) {
                        System.out.println("\nEnter appointment ID: ");
                        int id = sc.nextInt();
                        for (Patient patient : patients) {
                            for (Appointment appointment : patient.getAppointments()) {
                                if (appointment.getAppointmentID() == id) {

                                    // Service type
                                    int i = 1;
                                    System.out.println("Enter service type [1/2/3]:  ");
                                    for (ServiceType serviceType : ServiceType.values()) {
                                        System.out.println("[" + i++ + "] " + serviceType);
                                    }
                                    System.out.println();
                                    int serviceTypeIndex = sc.nextInt();
                                    ServiceType serviceType = ServiceType.values()[serviceTypeIndex-1];


                                    // Prescribed medications
                                    i = 1;
                                    for (Medication medication : medications) {
                                        System.out.println("[" + i++ + "] " + medication.getMedicationName());
                                    }

                                    System.out.println("Enter prescribed medication(s), separated by comma [eg. 1,2,3]:  ");
                                    sc.nextLine();
                                    String inputMedications = sc.nextLine();
                                    String[] medicationsIndex = inputMedications.split(",");
                                    List<Prescription> prescriptionList = new ArrayList<>();
                                    for (String index : medicationsIndex) {
                                        Prescription prescription = new Prescription(medications.get(Integer.parseInt(index)-1), PrescriptionStatus.PENDING);
                                        prescriptionList.add(prescription);
                                    }

                                    // Consultation Notes
                                    System.out.println("\n\nEnter Diagnosis: ");
                                    String inputDiagnosis = sc.nextLine();
                                    System.out.println("\nEnter Treatment plan: ");
                                    String inputTreatmentPlan = sc.nextLine();
                                    System.out.println("\nEnter consultation notes: ");
                                    String inputConsultationNotes = sc.nextLine();
                                    AppointmentOutcomeRecord appointmentOutcomeRecord = new AppointmentOutcomeRecord(1, serviceType, prescriptionList,inputDiagnosis,inputTreatmentPlan,inputConsultationNotes);
                                    appointment.setAppointmentOutcomeRecord(appointmentOutcomeRecord);
                                    appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);
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
