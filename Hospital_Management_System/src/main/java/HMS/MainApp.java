package HMS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import HMS.enums.Gender;
import HMS.enums.Role;
import HMS.models.Administrator;
import HMS.models.Patient;
import HMS.models.Pharmacist;
import HMS.models.Doctor;
import HMS.models.Medication;

import HMS.utils.FileHelper;

public class MainApp {

    public static List<Patient> patients = new ArrayList<>();
    public static List<Doctor> doctors = new ArrayList<>();
    public static List<Pharmacist> pharmacists = new ArrayList<>();
    public static List<Administrator> administrators = new ArrayList<>();
    public static List<Medication> medications = new ArrayList<>();

    public static void main(String[] args) {
        initialiseData();

        // Print out patients
        for (Patient patient : patients) {
            System.out.println(patient);
        }
        for (Medication medication : medications) {
            System.out.println(medication.getMedicationName());
        }
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
        for (Pharmacist pharmacist : pharmacists) {
            System.out.println(pharmacist);
        }
        for (Administrator administrator : administrators) {
            System.out.println(administrator);
        }

        // Menu
        while(true) {
            System.out.println("-----Hospital Management System-----");
            System.out.println("Please log in");
            // TODO: login system
            // Login system shld return Administrator/Doctor/Patient/Pharmacist object
            // user.showMenu();

            // Test
            // Patient user = patients.get(1);
            // user.showMenu();
            break;

        }

    }

    public static void initialiseData() {
        patients = FileHelper.getPatientsData("src/main/java/HMS/data/Patient_Data.csv");
        List<Object> staff = FileHelper.getStaffData("src/main/java/HMS/data/Staff_Data.csv");
        medications = FileHelper.getMedicationsData("src/main/java/HMS/data/Medicine_Data.csv");

        // The following code is commented out because the classes 
        // constructor for Doctor, Pharmacist, and Administrator are not defined

        for (Object staffMember : staff) {
            if (staffMember instanceof Doctor) {
                doctors.add((Doctor) staffMember);
            } else if (staffMember instanceof Pharmacist) {
                pharmacists.add((Pharmacist) staffMember);
            } else if (staffMember instanceof Administrator) {
                administrators.add((Administrator) staffMember);
            }
        }
    }
}
