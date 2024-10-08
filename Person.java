package Healthcare;

// Parent class of Doctor, Patient, and Nurse classes take the data to indentify the people.

public class Person {
    private final String id;
    final String name;
    private final String dateOfBirth;
    private final String contact;

    public Person(String id, String name, String dateOfBirth, String contact) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getContact() {
        return contact;
    }
}