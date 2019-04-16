package com.aquent.crudapp.service.impl;

import com.aquent.crudapp.data.dao.ClientDao;
import com.aquent.crudapp.domain.Client;
import com.aquent.crudapp.domain.Person;
import com.aquent.crudapp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Default implementation of {@link com.aquent.crudapp.service.ClientService}.
 */
@Service
public class DefaultClientService extends DefaultGenericService<Client> implements ClientService<Client> {
    @Autowired
    ClientDao clientDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Client> listClients() {
        return clientDao.listClients();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Client readClientForPerson(Integer id) {
        return clientDao.readClientForPerson(id);
    }

    @Override
    public Integer createClient(Client client) {
        return clientDao.createClient(client);
    }

    @Override
    public Client readClient(Integer id) {
        if (id == null) return null;

        Client client = clientDao.readClient(id);
        List<Person> contacts = clientDao.readClientContacts(id);
        client.setContacts(contacts);
        return client;
    }

    @Override
    public void updateClient(Client client) {
        clientDao.updateClient(client);
    }

    @Override
    public void deleteClient(Integer id) {
        clientDao.deleteClient(id);
    }

    @Override
    public List<Person> readClientContacts(Integer id) {
        return clientDao.readClientContacts(id);
    }

}
