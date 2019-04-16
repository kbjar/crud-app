package com.aquent.crudapp.controller;

import com.aquent.crudapp.domain.Client;
import com.aquent.crudapp.domain.Person;
import com.aquent.crudapp.service.ClientService;
import com.aquent.crudapp.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling basic client management operations.
 */
@Controller
@RequestMapping("/client")
public class ClientController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private PersonService personService;

    @Autowired
    private ClientService<Client> clientService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        // map the person ID value from the select box to Person.personId
        binder.registerCustomEditor(Person.class, new PersonPropertyEditor());
    }

    /**
     * Renders the listing page.
     *
     * @return list view populated with the current list of people
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        LOGGER.debug("Getting client list");
        ModelAndView mav = new ModelAndView("client/list");
        mav.addObject("clients", clientService.listClients());
        return mav;
    }

    /**
     * Renders an empty form used to create a new client record.
     *
     * @return create view populated with an empty client
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create() {
        return "redirect:/client/edit";
    }

    /**
     * Renders an edit form for an existing client record.
     * Or a form for creating a new client record.
     *
     * @param clientId the ID of the client to edit
     * @return edit view populated from the client record
     */
    @RequestMapping(value = {"edit", "edit/{clientId}"}, method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(required = false) Integer clientId) {
        // todo: put in some sort of handler/error message if bogus client id
        LOGGER.debug("Editing client {}.", clientId);
        ModelAndView mav = new ModelAndView("client/edit");
        // handle a edit or a new client creation
        Optional<Client> client = Optional.ofNullable(clientService.readClient(clientId));
        mav.addObject("client", client.orElse(new Client()));
        mav.addObject("contacts", personService.listPeople());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    /**
     * Validates and saves an edited client.
     * On success, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param client populated form bean for the client
     * @return redirect, or edit view with errors
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public ModelAndView edit(Client client) {
        List<String> errors = clientService.validate(client);
        if (errors.isEmpty()) {
            if (client.getClientId() != null) {
                LOGGER.debug("Trying to save edit on client {}.", client.getClientId());
                clientService.updateClient(client);
            } else {
                LOGGER.debug("Trying to create a new client.");
                clientService.createClient(client);
            }

            return new ModelAndView("redirect:/client/list");
        } else {
            ModelAndView mav = new ModelAndView("client/edit");
            mav.addObject("client", client);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    /**
     * Handles client deletion or cancellation, redirecting to the listing page in either case.
     *
     * @param clientId the ID of the client to be deleted
     * @return redirect to the listing page
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam Integer clientId) {
        LOGGER.debug("Deleting client {}.", clientId);
        clientService.deleteClient(clientId);
        return "redirect:/client/list";
    }
}
