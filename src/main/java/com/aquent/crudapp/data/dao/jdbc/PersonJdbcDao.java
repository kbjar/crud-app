package com.aquent.crudapp.data.dao.jdbc;

import com.aquent.crudapp.data.dao.PersonDao;
import com.aquent.crudapp.domain.Client;
import com.aquent.crudapp.domain.Person;
import com.aquent.crudapp.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

/**
 * Spring JDBC implementation of {@link PersonDao}.
 */
@Repository
@Transactional
public class PersonJdbcDao implements PersonDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(PersonJdbcDao.class);

    @Autowired
    ClientService clientService;

    private static final String SQL_LIST_PEOPLE = "SELECT * FROM person ORDER BY first_name, last_name, person_id";
    private static final String SQL_READ_PERSON =
            "SELECT p.*, c.client_id \"client.client_id\"\n" +
                    "FROM person p\n" +
                    "LEFT JOIN CONTACT ct\n" +
                    "ON p.person_id = ct.person_id\n" +
                    "LEFT JOIN CLIENT c\n" +
                    "ON ct.client_id = c.client_id\n" +
                    "WHERE p.person_id = :personId";
    private static final String SQL_DELETE_PERSON = "DELETE FROM person WHERE person_id = :personId";
    private static final String SQL_UPDATE_PERSON = "UPDATE person SET (first_name, last_name, email_address, street_address, city, state, zip_code)"
            + " = (:firstName, :lastName, :emailAddress, :streetAddress, :city, :state, :zipCode)"
            + " WHERE person_id = :personId";
    private static final String SQL_DELETE_PERSON_CLIENT =
            "DELETE FROM contact\n" +
                    "WHERE person_id = :personId";
    private static final String SQL_INSERT_PERSON_CONTACTS =
            "INSERT INTO contact (client_id, person_id)\n"
                    + "VALUES (:clientId, :personId)";
    private static final String SQL_CREATE_PERSON = "INSERT INTO person (first_name, last_name, email_address, street_address, city, state, zip_code)"
            + " VALUES (:firstName, :lastName, :emailAddress, :streetAddress, :city, :state, :zipCode)";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Person> listPeople() {
        return namedParameterJdbcTemplate.getJdbcOperations().query(
                SQL_LIST_PEOPLE,
                new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Person readPerson(Integer personId) {
        Person person = null;
        try {
            person = namedParameterJdbcTemplate.queryForObject(
                    SQL_READ_PERSON,
                    Collections.singletonMap("personId", personId),
                    new BeanPropertyRowMapper<>(Person.class));

            // todo: clean this up. some other way is probably better
            Client client = clientService.readClientForPerson(person.getPersonId());
            person.setClient(client);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Could not person {}", personId);
        }

        return person;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void deletePerson(Integer personId) {
        namedParameterJdbcTemplate.update(SQL_DELETE_PERSON, Collections.singletonMap("personId", personId));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public void updatePerson(Person person) {
        namedParameterJdbcTemplate.update(SQL_UPDATE_PERSON, new BeanPropertySqlParameterSource(person));

        if (person.getClient().getClientId() != null) {
            namedParameterJdbcTemplate.update(SQL_DELETE_PERSON_CLIENT, Collections.singletonMap("personId", person.getPersonId()));

            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("clientId", person.getClient().getClientId())
                    .addValue("personId", person.getPersonId());
            namedParameterJdbcTemplate.update(SQL_INSERT_PERSON_CONTACTS, namedParameters);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Integer createPerson(Person person) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_PERSON, new BeanPropertySqlParameterSource(person), keyHolder);

        // insert contact
        if (person.getClient().getClientId() != null) {
            SqlParameterSource namedParameters = new MapSqlParameterSource()
                    .addValue("clientId", person.getClient().getClientId())
                    .addValue("personId", keyHolder.getKey().intValue());
            namedParameterJdbcTemplate.update(SQL_INSERT_PERSON_CONTACTS, namedParameters);
        }
        return keyHolder.getKey().intValue();

    }
}
