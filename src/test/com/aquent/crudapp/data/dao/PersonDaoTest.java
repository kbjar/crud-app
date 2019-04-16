package com.aquent.crudapp.data.dao;

import com.aquent.crudapp.domain.Client;
import com.aquent.crudapp.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonDaoTest {
    /**
     * Basic examples just to throw in some tests
     * TODO: more tests!
     */
    @Autowired
    PersonDao personDao;

    @Test
    public void testPersonDao() {
        Person person = new Person();
        person.setFirstName("first");
        person.setLastName("last");
        person.setEmailAddress("email");
        person.setCity("asheville");
        person.setState("nc");
        person.setZipCode("28803");
        person.setStreetAddress("111 fake st");
        person.setClient(new Client());
        Integer person1Id = personDao.createPerson(person);
        assertNotNull(person1Id);

        Person readPerson = personDao.readPerson(person1Id);
        assertEquals(person, readPerson);
    }

    @Test
    public void testDeletingPersonDeletesContact() {
        // todo: not a good test
        Person readPerson = personDao.readPerson(0);
        assertNotNull(readPerson);

        personDao.deletePerson(0);



    }
}