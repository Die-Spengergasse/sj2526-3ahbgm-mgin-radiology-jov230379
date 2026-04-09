package at.spengergasse.spring_thymeleaf.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Long insuranceNumber;
    private String firstName;
    private String surname;
    private String gender;
    private LocalDate birth;

    public Patient() {}

    public int getId() { return id; }

    public Long getInsuranceNumber() { return insuranceNumber; }
    public void setInsuranceNumber(Long insuranceNumber) { this.insuranceNumber = insuranceNumber; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getBirth() { return birth; }
    public void setBirth(LocalDate birth) { this.birth = birth; }
}