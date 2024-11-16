package HMS.service;

import java.util.List;
import HMS.models.Patient;
import HMS.models.Doctor;
import HMS.models.Administrator;
import HMS.models.Pharmacist;
import HMS.models.Medication;

public interface IFileService {
    List<Patient> getPatientsData(String filePath);
    List<Object> getStaffData(String filePath);
    List<Medication> getMedicationsData(String filePath);
    void writePatientsData(String filePath, List<Patient> patients);
    void writeStaffData(String filePath, List<Object> staff);
    void writeMedicationData(String filePath, List<Medication> medications);
    void getDoctorAvailability(String filePath);
    void writeDoctorAvailability(String filePath, List<Doctor> doctors);
}
