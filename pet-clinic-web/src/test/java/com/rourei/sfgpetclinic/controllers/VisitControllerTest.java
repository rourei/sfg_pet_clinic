package com.rourei.sfgpetclinic.controllers;

import com.rourei.sfgpetclinic.model.Owner;
import com.rourei.sfgpetclinic.model.Pet;
import com.rourei.sfgpetclinic.model.PetType;
import com.rourei.sfgpetclinic.model.Visit;
import com.rourei.sfgpetclinic.services.OwnerService;
import com.rourei.sfgpetclinic.services.PetService;
import com.rourei.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    PetService petService;

    @Mock
    OwnerService ownerService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    VisitController visitController;

    MockMvc mockMvc;

    // Centralized definition of properties
    Owner owner;
    Pet pet;
    Set<Visit> visits;

    @BeforeEach
    void setUp() {

        // Initialize an Owner and some PetTypes
        owner = Owner.builder().id(1L).build();
        pet = Pet.builder().id(2L).build();

        owner.getPets().add(pet);
        pet.setOwner(owner);

        visits.add(Visit.builder().pet(pet).date(LocalDate.now()).build());

        // Lightweight test environment (no web server, no Spring Context)
        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();
    }
}