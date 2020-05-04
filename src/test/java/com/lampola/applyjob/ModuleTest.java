package com.lampola.applyjob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lampola.applyjob.domain.Appl;
import com.lampola.applyjob.domain.Appl.Gender;
import com.lampola.applyjob.repository.ApplicationRepository;

/**
 *
 * @author Markus Lampola
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuleTest {

    @Autowired
    ApplicationRepository applicationRepository;
            
    public ModuleTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testApplication() {
        String firstName = "Albert";
        String lastName = "Edelfeldt";
        Gender gender = Gender.Male;
        String description = "Description";
        
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateOfBirth;
        
        try {
            dateOfBirth = format.parse("6.12.1930");
        } catch (ParseException e) {
            dateOfBirth = new Date();
        }
        
        Appl application = new Appl();
        application.setFirstName(firstName);
        application.setLastName(lastName);
        application.setDateOfBirth(dateOfBirth);
        application.setGender(gender);
        application.setDescription(description);
        
        applicationRepository.save(application);
        
        Appl dbApplication = applicationRepository.findByLastNameAndFirstNameAndDateOfBirth(lastName, firstName, dateOfBirth);
        assertNotNull(dbApplication);
        assertEquals(firstName, dbApplication.getFirstName());
        assertEquals(lastName, dbApplication.getLastName());
        assertEquals(gender, dbApplication.getGender());
        assertEquals(description, dbApplication.getDescription());
        assertEquals(dateOfBirth, dbApplication.getDateOfBirth());
        
        applicationRepository.delete(dbApplication.getId());
    }
}
