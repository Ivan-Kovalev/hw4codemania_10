package org.example.sql_tutorial.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "highest_category")
    private boolean highestCategory;

    @Column(name = "graduation_date")
    private Date graduationDate;

    @Column(name = "employment_date")
    private Date employmentDate;

    public Doctor() {

    }

    public Doctor(
            Long departmentId,
            String name,
            Integer yearsOfExperience,
            String speciality,
            boolean highestCategory,
            Date graduationDate,
            Date employmentDate
    ) {
        this.departmentId = departmentId;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.speciality = speciality;
        this.highestCategory = highestCategory;
        this.graduationDate = graduationDate;
        this.employmentDate = employmentDate;
    }

    public Long getId() {
        return id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getName() {
        return name;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getSpeciality() {
        return speciality;
    }

    public boolean isHighestCategory() {
        return highestCategory;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setHighestCategory(boolean highestCategory) {
        this.highestCategory = highestCategory;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", speciality='" + speciality + '\'' +
                ", highestCategory=" + highestCategory +
                ", graduationDate=" + graduationDate +
                ", employmentDate=" + employmentDate +
                '}';
    }
}
