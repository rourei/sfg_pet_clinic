package com.rourei.sfgpetclinic.controllers;

import com.rourei.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets", "/vets/index", "/vets/index.html", "/vets.html"}) // when these URLs are requested, the method is called
    public String listVets(Model model){

        // Add all available vets to the 'vets' property of the model
        model.addAttribute("vets", vetService.findAll());

        return "vets/index"; // make Thymeleaf look for a template in 'templates/vets' called '/index'
    }
}
