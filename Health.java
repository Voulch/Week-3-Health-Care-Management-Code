package Healthcare;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

// Main class to run the program and process user input
public class Health {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Validation validation = new Validation(scanner);

        LinkedList<Doctor> doctors = Doctor.createDoctors();
        LinkedList<Nurse> nurses = Nurse.createNurses();

        Patient patient = createPatient(validation);
        Doctor doctor = chooseDoctor(scanner, doctors, validation);
        Nurse nurse = chooseNurse(scanner, nurses, validation);
        MedicalLog log = createMedicalLog(patient, doctor, nurse, validation);
        patient.addLog(log);

        Appointment.AppointmentManager appointmentManager = new Appointment.AppointmentManager();
        manageAppointments(scanner, patient, doctor, appointmentManager, validation);

        patient.viewBilling();
    }

    private static Patient createPatient(Validation validation) {
        System.out.println("\n======== Enter Patient details ========");

        String name = validation.valString("\nName: ");
        String patientId = validation.valString("Patient ID: ");
        String dateOfBirth = validation.valString("Date of Birth: ");
        String phoneNumber = validation.valString("Phone Number: ");

        System.out.println("\n=======================================");

        return new Patient(patientId, name, dateOfBirth, phoneNumber);
    }

    private static Doctor chooseDoctor(Scanner scanner, LinkedList<Doctor> doctors, Validation validation) {
        System.out.println("\n========== Available Doctors ==========");
        System.out.println();
        for (int i = 0; i < doctors.size(); i++) {
            System.out.println((i + 1) + ". " + doctors.get(i).getName() + " - " + doctors.get(i).getSpecialty());
        }
        System.out.print("\nWhich Doctor do you need?: ");
        int doctorChoice = validation.valInt(scanner, 1, doctors.size());
        System.out.println("\n=======================================");
        return doctors.get(doctorChoice - 1);
    }

    private static Nurse chooseNurse(Scanner scanner, LinkedList<Nurse> nurses, Validation validation) {
        System.out.println("\n========== Available Nurses ===========");
        System.out.println();
        for (int i = 0; i < nurses.size(); i++) {
            System.out.println((i + 1) + ". " + nurses.get(i).getName() + " - " + nurses.get(i).getDepartment());
        }
        System.out.print("\nWhich Nurse do you need?: ");
        int nurseChoice = validation.valInt(scanner, 1, nurses.size());
        System.out.println("\n=======================================");
        return nurses.get(nurseChoice - 1);
    }

    private static MedicalLog createMedicalLog(Patient patient, Doctor doctor, Nurse nurse, Validation validation) {
        System.out.println("\n====== Enter Medical log details ======");
        String logId = validation.valString("\nLog ID: ");
        System.out.println("\n=======================================");
        MedicalLog log = new MedicalLog(logId, patient, doctor);
        log.addStaff(nurse);

        System.out.println("\n=======================================");
        String response = validation.valString("\nAdd prescription [Yes/No]: ");
        System.out.println("\n=======================================");

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("\n=======================================");
            String prescription = validation.valString("\nPrescription: ");
            log.addPrescription(prescription);
            System.out.println("\n=======================================");
        }

        System.out.println("\n=======================================");
        response = validation.valString("\nAdd test result [Yes/No]: ");
        System.out.println("\n=======================================");

        if (response.equalsIgnoreCase("yes")) {
            System.out.println("\n=======================================");
            String testResult = validation.valString("\nTest Result: ");
            log.addTestResult(testResult);
            System.out.println("\n=======================================");
        }

        return log;
    }

    private static void manageAppointments(Scanner scanner, Patient patient, Doctor doctor, Appointment.AppointmentManager appointmentManager, Validation validation) {
        while (true) {
            System.out.println("\n======= Schedule an Appointment =======");
            System.out.println("\n1. Schedule an Appointment");
            System.out.println("2. Update an Appointment");
            System.out.println("3. Cancel an Appointment");
            System.out.println("4. Search an Appointment");
            System.out.println("5. Filter an Appointment");
            System.out.println("6. Exit");
            System.out.print("\nChoose an option: ");
            int choice = validation.valInt(scanner, 1 , 6);
            System.out.println("\n=======================================");

                    switch (choice) {
                case 1 -> {
                    List<String> availableTimes = appointmentManager.getAvailableTimes();
                    System.out.println("\n======= Schedule an Appointment ======");
                    System.out.println();
                    System.out.println("Available Schedule:");
                    for (int i = 0; i < availableTimes.size(); i++) {
                        System.out.println((i + 1) + ". " + availableTimes.get(i));
                    }
                    String appointmentID = validation.valString("\nEnter Appointment ID: ");
                    System.out.print("\nChoose time from available options: ");
                    int timeChoice = validation.valInt(scanner, 1, availableTimes.size());
                    String selectedTime = availableTimes.get(timeChoice - 1);
                    System.out.println("\n=======================================");
                    Appointment newAppointment = new Appointment(appointmentID, patient, doctor, selectedTime);
                    String result = appointmentManager.addAppointment(newAppointment);
                    System.out.println(result);
                    if (result.contains("Added")) {
                        availableTimes.remove(selectedTime);
                    }
                }
                case 2 -> {
                    System.out.println("\n======== Change an Appointment ======");
                    String updateID = validation.valString("\nEnter Appointment ID to update: ");
                    List<String> availableTimesForUpdate = appointmentManager.getAvailableTimes();
                    System.out.println("\nAvailable Schedule:");
                    for (int i = 0; i < availableTimesForUpdate.size(); i++) {
                        System.out.println((i + 1) + ". " + availableTimesForUpdate.get(i));
                    }
                    System.out.print("\nEnter new time: ");
                    int updateTimeChoice = validation.valInt(scanner, 1, availableTimesForUpdate.size());
                    String newTime = availableTimesForUpdate.get(updateTimeChoice - 1);
                    System.out.println("\n=======================================");
                    System.out.println(appointmentManager.updateAppointment(updateID, newTime));
                }
                case 3 -> {
                    System.out.println("\n======== Cancel an Appointment ======");
                    String cancelID = validation.valString("\nAppointment ID to cancel: ");
                    System.out.println("\n=======================================");
                    String result = appointmentManager.cancelAppointment(cancelID);
                    System.out.println(result);
                    if (result.contains("Cancelled")) {
                        List<String> availableTimesAfterCancel = appointmentManager.getAvailableTimes();
                        availableTimesAfterCancel.add(cancelID);
                    }
                }
                case 4 -> {
                    System.out.println("\n======= Search Appointments =======");
                    String searchCriteria = validation.valString("\nEnter Appointment ID: ");
                    List<Appointment> searchResults = appointmentManager.searchAppointments(searchCriteria);
                    if (searchResults.isEmpty()) {
                        System.out.println("\nNo appointments found.");
                    } else {
                        for (Appointment appointment : searchResults) {
                            System.out.println(appointment.viewAppointment());
                        }
                    }
                    break;
                }
                case 5 -> {
                    System.out.println("\n======= Filter Appointments =======");

                    String filterCriteria = validation.valString("\nWhich Appointments? [Scheduled or Cancelled]: ");
                    List<Appointment> filterResults = appointmentManager.filterAppointments(filterCriteria);
                    if (filterResults.isEmpty()) {
                        System.out.println("\nNo appointments found.");
                    } else {
                        for (Appointment appointment : filterResults) {
                            System.out.println(appointment.viewAppointment());
                        }
                    }
                    break;
                }
                case 6 -> {
                    return;
                }
                default -> System.out.println("\n ERROR! Invalid option. Please try again.");
            }
        }
    }
}
