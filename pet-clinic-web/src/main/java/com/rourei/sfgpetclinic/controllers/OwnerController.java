package com.rourei.sfgpetclinic.controllers;

import com.rourei.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/owners") // prefixing all following RequestMappings
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"}) // when these URLs are requested, the method is called (prefix!)
    public String listOwners(Model model){

        // Add all available owners to the 'owners' property of the model
        model.addAttribute("owners", ownerService.findAll());

        return "owners/index"; // make Thymeleaf look for a template in 'templates/owners' called '/index'
    }

    @RequestMapping("/find") // Placeholder to fix broken links until functionality is added
    public String findOwners(){
        return "notimplemented"; // make Thymeleaf look for a template called 'notimplemented'
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
