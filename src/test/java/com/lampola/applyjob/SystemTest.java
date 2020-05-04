/*
 * (C) Markus Lampola 2016
 */
package com.lampola.applyjob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.lampola.applyjob.domain.Appl;
import com.lampola.applyjob.repository.ApplicationRepository;

/**
 *
 * @author Markus Lampola
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemTest {
    
    @Autowired
    private WebApplicationContext webAppContext;

    @Autowired
    ApplicationRepository applicationRepository;

    private MockMvc mockMvc;
    
    private String firstName;
    private String lastName;
    private Appl.Gender gender;
    private String description;

    private SimpleDateFormat formatFrom;
    private SimpleDateFormat formatTo;
    private Date dateOfBirth;        
    String datestring;

    public SystemTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

        this.firstName = "Albert";
        this.lastName = "Edelfeldt";
        this.gender = Appl.Gender.Male;
        this.description = "Description";

        this.formatFrom = new SimpleDateFormat("dd.MM.yyyy");

        try {
            this.dateOfBirth = formatFrom.parse("6.12.1930");
        } catch (ParseException e) {
            this.dateOfBirth = new Date();
        }
        
        this.formatTo = new SimpleDateFormat("yyyy-MM-dd");
        datestring = formatTo.format(this.dateOfBirth);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void applicationIsAddedToDb() throws Exception {

        mockMvc.perform(post("/")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("gender", gender.toString())
                .param("description", description)
                .param("dateOfBirth", datestring))
                .andExpect(status().is3xxRedirection());
        
        Appl dbApplication = applicationRepository.findByLastNameAndFirstNameAndDateOfBirth(lastName, firstName, dateOfBirth);
        assertNotNull(dbApplication);
        assertEquals(firstName, dbApplication.getFirstName());
        assertEquals(lastName, dbApplication.getLastName());
        assertEquals(gender, dbApplication.getGender());
        assertEquals(description, dbApplication.getDescription());
        assertEquals(dateOfBirth, dbApplication.getDateOfBirth());
        
        applicationRepository.delete(dbApplication.getId());
    }
    
    @Test
    public void emptyFirstNameNotAccepted() throws Exception {

        mockMvc.perform(post("/")
                .param("firstName", "")
                .param("lastName", lastName)
                .param("gender", gender.toString())
                .param("description", description)
                .param("dateOfBirth", datestring))
                .andExpect(status().isOk()); // Not ok because status is not is3xxRedirection to confirmation page
    }
    
    @Test
    public void emptyLastNameNotAccepted() throws Exception {

        mockMvc.perform(post("/")
                .param("firstName", firstName)
                .param("lastName", "")
                .param("gender", gender.toString())
                .param("description", description)
                .param("dateOfBirth", datestring))
                .andExpect(status().isOk()); // Not ok because status is not is3xxRedirection to confirmation page
    }
    
    @Test
    public void emptyDescriptionNotAccepted() throws Exception {

        mockMvc.perform(post("/")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("gender", gender.toString())
                .param("description", "")
                .param("dateOfBirth", datestring))
                .andExpect(status().isOk()); // Not ok because status is not is3xxRedirection to confirmation page
    }
    
    @Test
    public void invalidGenderNotAccepted() throws Exception {

        mockMvc.perform(post("/")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("gender", "Other")
                .param("description", description)
                .param("dateOfBirth", datestring))
                .andExpect(status().isOk()); // Not ok because status is not is3xxRedirection to confirmation page
    }
    
    @Test
    public void tooYoungNotAccepted() throws Exception {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String tooYoung = fmt.format(new Date());  // 0 years old
        
        mockMvc.perform(post("/")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("gender", gender.toString())
                .param("description", description)
                .param("dateOfBirth", tooYoung))
                .andExpect(status().isOk()); // Not ok because status is not is3xxRedirection to confirmation page
    }
    
    @Test
    public void tooOldNotAccepted() throws Exception {

        Calendar c101 = Calendar.getInstance(); 
        c101.setTime(new Date());
        c101.add(Calendar.YEAR, -101);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String tooOld = fmt.format(c101.getTime()); // 101 years old
        
        mockMvc.perform(post("/")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("gender", gender.toString())
                .param("description", description)
                .param("dateOfBirth", tooOld))
                .andExpect(status().isOk()); // Not ok because status is not is3xxRedirection to confirmation page
    }
}
