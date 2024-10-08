package Healthcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class Appointment to manage Appointments
public class Appointment {
    private final String appointmentID;
    private final Patient patient;
    private final Person provider;
    private String dateTime;
    private String status;

    public Appointment(String appointmentID, Patient patient, Person provider, String dateTime) {
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.provider = provider;
        this.dateTime = dateTime;
        this.status = "Scheduled";
    }

    public String scheduleAppointment() {
        return "\nScheduled: " + viewAppointment();
    }

    public String updateAppointment(String newDateTime) {
        this.dateTime = newDateTime;
        this.status = "Updated";
        return "\nUpdated: " + viewAppointment();
    }

    public String cancelAppointment() {
        this.status = "Cancelled";
        return "\nCancelled: " + viewAppointment();
    }

    public String getStatus() {
        return status;
    }

    public String viewAppointment() {
        return "\nAppointment ID: " + appointmentID + "\nPatient: " + patient.getName() + "\nHealth Provider: " + provider.getName() + "\nSchedule: " + dateTime;
    }

    public String getAppointmentID() {
        return appointmentID;
    }
    
    public static class AppointmentManager {
        private final Map<String, Appointment> appointments = new HashMap<>();
        private final List<String> availableTimes;

        public AppointmentManager() {
            availableTimes = new ArrayList<>();
            availableTimes.add("October 8, 2024 | 10:00 AM - 12:00 PM");
            availableTimes.add("October 8, 2024 | 1:00 PM - 3:00 PM");
            availableTimes.add("October 8, 2024 | 5:00 PM - 7:00 PM");
            availableTimes.add("October 9, 2024 | 10:00 AM - 12:00 PM");
            availableTimes.add("October 9, 2024 | 1:00 PM - 3:00 PM");
        }

        public List<String> getAvailableTimes() {
            return availableTimes;
        }

        public String addAppointment(Appointment appointment) {
            if (availableTimes.contains(appointment.dateTime)) {
                appointments.put(appointment.getAppointmentID(), appointment);
                availableTimes.remove(appointment.dateTime);
                return appointment.scheduleAppointment();
            } else {
                return "\nThe Schedule you selected is Unavailable.";
            }
        }

        public String updateAppointment(String appointmentID, String newDateTime) {
            Appointment appointment = appointments.get(appointmentID);
            if (appointment != null && availableTimes.contains(newDateTime)) {
                availableTimes.remove(newDateTime);
                availableTimes.add(appointment.dateTime);
                appointment.updateAppointment(newDateTime);
                return "\nUpdated: " + appointment.viewAppointment();
            } else {
                return "\nERROR! ID not found or the recently choose Schedule is Unvailable.";
            }
        }
        
        public String cancelAppointment(String appointmentID) {
            Appointment appointment = appointments.remove(appointmentID);
            if (appointment != null) {
                availableTimes.add(appointment.dateTime);
                return appointment.cancelAppointment();
            } else {
                return "\nERROR! ID not found.";
            }
        }

        public List<Appointment> searchAppointments(String searchValue) {
            List<Appointment> searchResults = new ArrayList<>();
            for (Appointment appointment : appointments.values()) {
                if (appointment.getAppointmentID().equals(searchValue)) {
                    searchResults.add(appointment);
                }
            }
            return searchResults;
        }

        public List<Appointment> filterAppointments(String filterCriteria) {
            List<Appointment> filterResults = new ArrayList<>();
            for (Appointment appointment : appointments.values()) {
                if (filterCriteria.equals("Scheduled") && appointment.getStatus().equals("Scheduled")) {
                    filterResults.add(appointment);
                } else if (filterCriteria.equals("Cancelled") && appointment.getStatus().equals("Cancelled")) {
                    filterResults.add(appointment);
                }
            }
            return filterResults;
        }
    }
}
