package HMS.service;

import java.util.List;
import HMS.models.Patient;
import HMS.models.Medication;

public interface IFileService {
    List<Patient> getPatientsData(String filePath);
    List<Object> getStaffData(String filePath);
    List<Medication> getMedicationsData(String filePath);
    void writePatientsData(String filePath);
    void writeStaffData(String filePath);
    void writeMedicationData(String filePath);
    void getDoctorAvailability(String filePath);
    void writeDoctorAvailability(String filePath);
}
