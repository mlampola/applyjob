/*
 * (C) Markus Lampola 2016
 */
package com.lampola.applyjob.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

import com.lampola.applyjob.validation.RequiredAge;

/**
 *
 * @author Markus Lampola
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"lastName", "firstName", "dateOfBirth"})})
public class Appl extends AbstractPersistable<Long> {

    @NotBlank
    @Length(min = 1, max = 50)
    private String firstName;
    
    @NotBlank
    @Length(min = 1, max = 50)
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @RequiredAge // Custom anotation for age check
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    @Length(min = 1, max = 1000)
    private String description;

    public enum Gender {
        Male, Female
    }

    public Appl() {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
