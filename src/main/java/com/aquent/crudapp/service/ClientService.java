package com.aquent.crudapp.service;

import com.aquent.crudapp.domain.Client;
import com.aquent.crudapp.domain.Person;

import java.util.List;

/**
 * Client operations
 */
public interface ClientService<T> extends GenericService<T> {

    /**
     * Retrieves all of the client records.
     *
     * @return List of clients
     */
    List<Client> listClients();

    /**
     * Creates a new client record
     *
     * @param client the client to save
     * @return the new client id
     */
    Integer createClient(Client client);

    /**
     * Get a client record by id
     *
     * @param id the person id
     * @return the client record
     */
    Client readClient(Integer id);

    /**
     * Read the client for a person by person id
     *
     * @param id the person id
     * @return the person's client
     */
    Client readClientForPerson(Integer id);

    /**
     * Update a client's values
     *
     * @param client the client to update
     */
    void updateClient(Client client);

    /**
     * Delete a client
     *
     * @param id the client id to delete
     */
    void deleteClient(Integer id);

    /**
     * Read a list of client contacts (Person) by client id
     *
     * @param id the client id
     * @return
     */
    List<Person> readClientContacts(Integer id);
}
