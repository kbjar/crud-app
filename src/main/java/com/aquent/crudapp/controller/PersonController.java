package com.aquent.crudapp.controller;

import com.aquent.crudapp.domain.Person;
import com.aquent.crudapp.service.ClientService;
import com.aquent.crudapp.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for handling basic person management operations.
 */
@Controller
@RequestMapping("/person")
public class PersonController {
    // Added a slfj4 logger and some basic debug log examples on the controller methods.
    public static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService<Person> personService;

    @Autowired
    private ClientService clientService;

    /**
     * Renders the listing page.
     *
     * @return list view populated with the current list of people
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView list() {
        LOGGER.debug("Getting person list");
        ModelAndView mav = new ModelAndView("person/list");
        mav.addObject("persons", personService.listPeople());
        return mav;
    }

    /**
     * Renders an empty form used to create a new person record.
     *
     * @return create view populated with an empty person
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create() {
        return "redirect:/person/edit";
    }

    /**
     * Renders an edit form for an existing person record.
     * Or a form for creating a new client record.
     *
     * @param personId the ID of the person to edit
     * @return edit view populated from the person record
     */
    @RequestMapping(value = {"edit", "edit/{personId}"}, method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(name = "personId", required = false) Integer personId) {
        // todo: put in some sort of handler/error message if bogus person id
        LOGGER.debug("Editing person {}.", personId);
        ModelAndView mav = new ModelAndView("person/edit");
        // handle a edit or a new person creation
        Optional<Person> person = Optional.ofNullable(personService.readPerson(personId));
        mav.addObject("person", person.orElse(new Person()));
        mav.addObject("clients", clientService.listClients());
        mav.addObject("errors", new ArrayList<String>());
        return mav;
    }

    /**
     * Validates and saves an edited person.
     * On
     * ccess, the user is redirected to the listing page.
     * On failure, the form is redisplayed with the validation errors.
     *
     * @param person populated form bean for the person
     * @return redirect, or edit view with errors
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ModelAndView save(Person person) {
        List<String> errors = personService.validate(person);
        if (errors.isEmpty()) {
            if (person.getPersonId() != null) {
                LOGGER.debug("Trying to save edit on person {}.", person.getPersonId());
                personService.updatePerson(person);
            } else {
                LOGGER.debug("Trying to create a new person.");
                personService.createPerson(person);
            }

            return new ModelAndView("redirect:/person/list");
        } else {
            ModelAndView mav = new ModelAndView("person/edit");
            mav.addObject("person", person);
            mav.addObject("errors", errors);
            return mav;
        }
    }

    /**
     * Handles person deletion or cancellation, redirecting to the listing page in either case.
     *
     * @param personId the ID of the person to be deleted
     * @return redirect to the listing page
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String delete(@RequestParam Integer personId) {
        LOGGER.debug("Deleting person {}.", personId);
        personService.deletePerson(personId);
        return "redirect:/person/list";
    }
}
