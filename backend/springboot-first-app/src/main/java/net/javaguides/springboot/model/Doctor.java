package net.javaguides.springboot.model;

import jakarta.persistence.*;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private int avgConsultTime; // in minutes

    public Doctor() {}

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getAvgConsultTime() { return avgConsultTime; }
    public void setAvgConsultTime(int avgConsultTime) { this.avgConsultTime = avgConsultTime; }
}