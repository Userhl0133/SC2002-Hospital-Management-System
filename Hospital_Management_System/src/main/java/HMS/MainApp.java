package HMS;

import java.util.ArrayList;
import java.util.List;

import HMS.models.Administrator;
import HMS.models.Appointment;
import HMS.models.AppointmentOutcomeRecord;
import HMS.models.Doctor;
import HMS.models.Medication;
import HMS.models.Patient;
import HMS.models.Pharmacist;
import HMS.models.ReplenishmentRequest;
import HMS.models.User;
import HMS.utils.FileHelper;

public class MainApp {

    public static List<Patient> patients = new ArrayList<>();
    public static List<Doctor> doctors = new ArrayList<>();
    public static List<Pharmacist> pharmacists = new ArrayList<>();
    public static List<Administrator> administrators = new ArrayList<>();
    public static List<Medication> medications = new ArrayList<>();
    public static List<Appointment> appointments = new ArrayList<>();
    public static List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>();
    public static List<AppointmentOutcomeRecord> appointmentOutcomeRecords = new ArrayList<>();


    public static void main(String[] args) {
        initialiseData();

        // Menu
        while (true) {
            System.out.println("====================================");
            System.out.println("-----Hospital Management System-----");
            System.out.println("====================================");
            System.out.println("Please log in");

            User validatedUser = User.login(patients, doctors, pharmacists, administrators);

            if (validatedUser instanceof Patient) {
                Patient patient = (Patient) validatedUser;
                patient.showMenu();
            } else if (validatedUser instanceof Doctor) {
                Doctor doctor = (Doctor) validatedUser;
                doctor.showMenu();
            } else if (validatedUser instanceof Pharmacist) {
                Pharmacist pharmacist = (Pharmacist) validatedUser;
                pharmacist.showMenu();
            } else if (validatedUser instanceof Administrator) {
                Administrator admin = (Administrator) validatedUser;
                admin.showMenu();
            } else {
                System.out.println("User not found.");
            }
        }

    }

    // To load data from CSV files
    public static void initialiseData() {
        patients = FileHelper.getPatientsData(System.getProperty("user.dir") + "/Hospital_Management_System/src/main/java/HMS/data/Patient_Data.csv");
        List<Object> staff = FileHelper.getStaffData(System.getProperty("user.dir") + "/Hospital_Management_System/src/main/java/HMS/data/Staff_Data.csv");
        medications = FileHelper.getMedicationsData(System.getProperty("user.dir") + "/Hospital_Management_System/src/main/java/HMS/data/Medicine_Data.csv");
    
        for (Object staffMember : staff) {
            if (staffMember instanceof Doctor doctor) {
                doctors.add(doctor);
            } else if (staffMember instanceof Pharmacist pharmacist) {
                pharmacists.add(pharmacist);
            } else if (staffMember instanceof Administrator administrator) {
                administrators.add(administrator);
            }
        }
    }
}
