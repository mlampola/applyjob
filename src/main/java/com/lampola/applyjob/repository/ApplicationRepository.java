/*
 * (C) Markus Lampola 2016
 */
package com.lampola.applyjob.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lampola.applyjob.domain.Appl;

/**
 *
 * @author Markus Lampola
 */
public interface ApplicationRepository extends JpaRepository<Appl, Long> {
    
    Appl findByLastNameAndFirstNameAndDateOfBirth(String lastName, String firstName, Date dateOfBirth);
}
