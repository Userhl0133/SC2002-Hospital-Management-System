package HMS.service;

import java.util.List;
import HMS.models.Patient;
import HMS.models.Medication;

// Interface for File Service
public interface IFileService {
    // methods to read patient data
    List<Patient> getPatientsData(String filePath);
    // methods to read staff data
    List<Object> getStaffData(String filePath);
    // methods to read medication data
    List<Medication> getMedicationsData(String filePath);
    // methods to write patient data
    void writePatientsData(String filePath);
    // methods to write staff data
    void writeStaffData(String filePath);
    // methods to write medication data
    void writeMedicationData(String filePath);
    // methods to read doctor availability
    void getDoctorAvailability(String filePath);
    // methods to write doctor availability
    void writeDoctorAvailability(String filePath);
}
