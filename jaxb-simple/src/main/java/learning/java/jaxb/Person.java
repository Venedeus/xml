package learning.java.jaxb;

import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(propOrder = {"lastName", "firstName", "birthDate"}, name = "person")
public class Person {
    private Date birthDate;
    private String firstName;
    private String lastName;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
