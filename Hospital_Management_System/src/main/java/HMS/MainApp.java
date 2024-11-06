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

    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Pharmacist> pharmacists = new ArrayList<>();  
    private List<Administrator> administrators = new ArrayList<>();
    private List<Medication> medications = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello World");

        // Create an instance of MainApp
        MainApp app = new MainApp();
        app.initialiseData();
        
        // Print out patients
        for (Patient patient : app.patients) {
            System.out.println(patient);
        }
    }

    public void initialiseData() {
        List<Patient> patients = FileHelper.getPatientsData("src/main/java/HMS/data/Patient_Data.csv");
        List<Object> staff = FileHelper.getStaffData("src/main/java/HMS/data/Staff_Data.csv");
        List<Medication> medications = FileHelper.getMedicationsData("src/main/java/HMS/data/Medications_Data.csv");

        for (Patient patient : patients) {
            System.out.println(patient.toString());
        }

        for (Object staffMember : staff) {
            if (staffMember instanceof Doctor) {
                doctors.add((Doctor) staffMember);
            } else if (staffMember instanceof Pharmacist) {  // Corrected spelling
                pharmacists.add((Pharmacist) staffMember);
            } else if (staffMember instanceof Administrator) {
                administrators.add((Administrator) staffMember);
            }
        }

        for (Medication medication : medications) {
            System.out.println(medication);
        }
    }

}
