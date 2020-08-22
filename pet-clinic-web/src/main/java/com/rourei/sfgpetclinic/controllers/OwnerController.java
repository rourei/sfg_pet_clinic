package com.rourei.sfgpetclinic.controllers;

import com.rourei.sfgpetclinic.model.Owner;
import com.rourei.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/owners") // prefixing all following RequestMappings
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    // Handles the binding of form posts to Java objects
    @InitBinder // allows injection of a WebDataBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        /*
         This is basically a security feature: by disallowing the ID field, the ID property cannot be changed from the
         web interface. This is important since it is the primary property for the underlying database.
        */
        dataBinder.setDisallowedFields("id");
    }

    // ###### DEPRECATED ######
//    @RequestMapping({"", "/", "/index", "/index.html"}) // when these URLs are requested, the method is called (prefix!)
//    public String listOwners(Model model){
//
//        // Add all available owners to the 'owners' property of the model
//        model.addAttribute("owners", ownerService.findAll());
//
//        return "owners/index"; // make Thymeleaf look for a template in 'templates/owners' called '/index'
//    }

    @RequestMapping("/find")
    public String findOwners(Model model){

        model.addAttribute("owner", Owner.builder().build());

        return "owners/findOwners"; // make Thymeleaf look for a template called 'owners/findOwners'
    }

    @GetMapping // Called for path '/owners' (see prefix at the top)
    public String processFindForm(Owner owner, BindingResult result, Model model){

        // Allow parameterless GET request for owners to return all records
        if (owner.getLastName() == null){
            owner.setLastName(""); //  empty String means broadest possible search
        }

        // Find owners by last name and treat result according to number of results (none, 1, many)
        List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());

        if (results.isEmpty()) {
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners"; // return default view
        } else if (results.size() == 1){
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId(); // redirect to search result
        } else {
            model.addAttribute("selections", results);
            return "owners/ownersList"; // show list of possible matches
        }
    }

    @GetMapping("/{ownerId}") // uses a path variable (relative to /owners !)
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId){ // piping the path variable into the method

        // Using a combined model and view type
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        // Look for the requested owner (path variable!) and add it to the model
        mav.addObject(ownerService.findById(ownerId));

        return mav;
    }
}
