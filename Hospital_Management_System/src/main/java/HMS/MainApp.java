package HMS;

import java.util.ArrayList;
import java.util.List;
import HMS.models.Administrator;
import HMS.models.Patient;
import HMS.models.Pharmacist;
import HMS.models.Doctor;
import HMS.models.Medication;

import HMS.utils.FileHelper;

public class MainApp {

    private static List<Patient> patients = new ArrayList<>();
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<Pharmacist> pharmacists = new ArrayList<>();  
    private static List<Administrator> administrators = new ArrayList<>();
    private static List<Medication> medications = new ArrayList<>();

    public static void main(String[] args) {
        initialiseData();

        // Print out patients
        for (Patient patient : patients) {
            System.out.println(patient);
        }
        for (Medication medication : medications) {
            System.out.println(medication.getMedicationName());
        }

        // Menu
        while(true) {
            System.out.println("-----Hospital Management System-----");
            System.out.println("Please log in");
            // TODO: login system
            // Login system shld return Administrator/Doctor/Patient/Pharmacist object
            // user.showMenu();

            // Test
            /*
            Patient user = patients.get(1);
            user.showMenu();
            break;
             */
        }

    }

    public static void initialiseData() {
        patients = FileHelper.getPatientsData("Hospital_Management_System/src/main/java/HMS/data/Patient_Data.csv");
        List<Object> staff = FileHelper.getStaffData("Hospital_Management_System/src/main/java/HMS/data/Staff_Data.csv");
        medications = FileHelper.getMedicationsData("Hospital_Management_System/src/main/java/HMS/data/Medicine_Data.csv");

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
