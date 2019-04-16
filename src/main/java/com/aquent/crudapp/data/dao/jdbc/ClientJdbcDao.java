package com.aquent.crudapp.data.dao.jdbc;

import com.aquent.crudapp.data.dao.ClientDao;
import com.aquent.crudapp.domain.Client;
import com.aquent.crudapp.domain.Person;
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
import java.util.stream.Collectors;

/**
 * Spring JDBC implementation of {@link com.aquent.crudapp.data.dao.ClientDao}.
 */
@Repository
@Transactional
public class ClientJdbcDao implements ClientDao {
    public static final Logger LOGGER = LoggerFactory.getLogger(ClientJdbcDao.class);

    private static final String SQL_LIST_CLIENTS = "SELECT * FROM client ORDER BY name asc";
    private static final String SQL_UPDATE_CLIENT = "UPDATE client SET (name, website_uri, phone_number, street_address, city, state, zip_code)\n"
            + "= (:name, :websiteUri, :phoneNumber, :streetAddress, :city, :state, :zipCode)\n"
            + "WHERE client_id = :clientId";
    private static final String SQL_DELETE_CLIENT_CONTACTS =
            "DELETE FROM contact\n"
                    + "where client_id =  :clientId";
    private static final String SQL_INSERT_CLIENT_CONTACTS =
            "INSERT INTO contact (client_id, person_id)\n"
                    + "VALUES (:clientId, :personId)";
    private static final String SQL_DELETE_CLIENT = "DELETE FROM client WHERE client_id = :clientId";
    private static final String SQL_READ_CLIENT = "SELECT * FROM client WHERE client_id = :clientId ORDER BY name";
    private static final String SQL_READ_CLIENT_CONTACTS =
            "SELECT p.*\n" +
                    "FROM client c\n" +
                    "INNER JOIN contact ct\n" +
                    "ON c.client_id = ct.client_id\n" +
                    "INNER JOIN person p\n" +
                    "ON ct.person_id = p.person_id\n" +
                    "WHERE c.client_id = :clientId\n" +
                    "ORDER BY p.last_name, p.first_name";
    private static final String SQL_READ_CLIENT_FOR_PERSON = "" +
            "SELECT c.*\n" +
            "FROM client c\n" +
            "LEFT JOIN CONTACT ct\n" +
            "ON c.client_id = ct.client_id\n" +
            "WHERE ct.person_id = :personId";
    private static final String SQL_CREATE_CLIENT = "INSERT INTO client (name, website_uri, phone_number, street_address, city, state, zip_code)"
            + " VALUES (:name, :websiteUri, :phoneNumber, :streetAddress, :city, :state, :zipCode)";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Client> listClients() {
        return namedParameterJdbcTemplate.query(
                SQL_LIST_CLIENTS,
                new BeanPropertyRowMapper<>(Client.class));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Client readClient(Integer clientId) {
        Client client = null;
        try {
            client = namedParameterJdbcTemplate.queryForObject(
                    SQL_READ_CLIENT,
                    Collections.singletonMap("clientId", clientId),
                    new BeanPropertyRowMapper<>(Client.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Could not find a client {}", clientId);

        }
        return client;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Client readClientForPerson(Integer personId) {
        Client client = null;
        try {
            client = namedParameterJdbcTemplate.queryForObject(
                    SQL_READ_CLIENT_FOR_PERSON,
                    Collections.singletonMap("personId", personId),
                    new BeanPropertyRowMapper<>(Client.class));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Could not find a client for person {}", personId);
        }

        return client;
    }

    @Override
    public List<Person> readClientContacts(Integer clientId) {
        return namedParameterJdbcTemplate.query(
                SQL_READ_CLIENT_CONTACTS,
                Collections.singletonMap("clientId", clientId),
                new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public void deleteClient(Integer clientId) {
        namedParameterJdbcTemplate.update(SQL_DELETE_CLIENT, Collections.singletonMap("clientId", clientId));
    }

    @Override
    public void updateClient(Client client) {
        // update the client
        namedParameterJdbcTemplate.update(SQL_UPDATE_CLIENT, new BeanPropertySqlParameterSource(client));

        // delete client contacts
        namedParameterJdbcTemplate.update(SQL_DELETE_CLIENT_CONTACTS, Collections.singletonMap("clientId", client.getClientId()));

        // batch insert contact for the client
        List<MapSqlParameterSource> collect = client.getContacts().stream().map(p ->
                new MapSqlParameterSource()
                        .addValue("clientId", client.getClientId())
                        .addValue("personId", p.getPersonId())).collect(Collectors.toList());

        SqlParameterSource[] sqlParameterSources = collect.toArray(new SqlParameterSource[client.getContacts().size()]);

        namedParameterJdbcTemplate.batchUpdate(SQL_INSERT_CLIENT_CONTACTS, sqlParameterSources);

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
    public Integer createClient(Client client) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_CLIENT, new BeanPropertySqlParameterSource(client), keyHolder);
        return keyHolder.getKey().intValue();
    }

}
