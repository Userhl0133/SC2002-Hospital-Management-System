package HMS.service;

import java.util.List;
import HMS.models.Patient;
import HMS.models.Medication;

// Interface for FileService
public interface IFileService {
    // method to get patients data
    List<Patient> getPatientsData(String filePath);
    // method to get staff data
    List<Object> getStaffData(String filePath);
    // method to get medications data
    List<Medication> getMedicationsData(String filePath);
    // method to write patients data
    void writePatientsData(String filePath);
    // method to write staff data
    void writeStaffData(String filePath);
    // method to write medications data
    void writeMedicationData(String filePath);
    // method to get doctor availability
    void getDoctorAvailability(String filePath);
    // method to write doctor availability
    void writeDoctorAvailability(String filePath);
}
