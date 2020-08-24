package com.rourei.sfgpetclinic.controllers;

import com.rourei.sfgpetclinic.model.Pet;
import com.rourei.sfgpetclinic.model.Visit;
import com.rourei.sfgpetclinic.services.PetService;
import com.rourei.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

// Visits can only be added and not edited / updated!

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
public class VisitController {

    private final PetService petService;
    private final VisitService visitService;

    public VisitController(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @GetMapping("/new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Model model) {

        // Create empty visit and add to model
        model.addAttribute("visit", new Visit());
        // Retrieve requested pet and add to model in order for information to be displayed
        model.addAttribute("pet", petService.findById(petId));

        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/new")
    public String processNewVisitForm(@Valid Visit visit, @PathVariable("petId") Long petId, BindingResult result) {

        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            // Retrieve requested pet and add the visit
            Pet pet = petService.findById(petId);
            pet.getVisits().add(visit);

            // Assign pet to visit and save
            visit.setPet(pet);
            visitService.save(visit);

            return "redirect:/owners/{ownerId}";
        }
    }
}
