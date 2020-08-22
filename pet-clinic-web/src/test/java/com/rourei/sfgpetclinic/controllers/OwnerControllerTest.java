package com.rourei.sfgpetclinic.controllers;

import com.rourei.sfgpetclinic.model.Owner;
import com.rourei.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class) // Set up the JUnit5 environment for Mockito -> init Mocks automatically
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    // Centralized definition of properties
    Set<Owner> owners;
    MockMvc mockMvc; // Initializes a test environment for the controller without the need of a running web server

    @BeforeEach
    void setUp() {
        // Initialize Owners and add some objects via the Builder Pattern that is provided via Lombok
        owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());

        // Create a light weight Mock MVC for controller testing via Builder Pattern
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller) // very lightweight, does not bring up a server, no Context necessary
                .build();
    }

    @ParameterizedTest // Enable parameter usage for this test -> multiple configurations for the same test
    @ValueSource(strings = {"/owners", "/owners/index", "/owners/index.html"}) // Define parameters
    void listOwners(String urlPath) throws Exception {

        // Set up the Mock interaction
        when(ownerService.findAll()).thenReturn(owners);

        mockMvc.perform(get(urlPath))
                .andExpect(status().is2xxSuccessful()) // or .is.Ok() or .is(200)
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));

        /*
         The above code performs an HTTP GET request and defines certain expectations for the received response.
         Matchers are used to define the individual expectations. If one of the expectations is not met, the test fails.

         The expectations include: a successful request (Code 200), a returned view name (owners/index) and an attribute
         added to the model with the name 'owners' and the size of 2 (since 2 Owners are added in setUp())
        */
    }

    // This test will be updated as soon as the findOwners() method is actually implemented.
    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("notimplemented"));

        // Ensure that the controller does not call any unwanted methods
        verifyNoInteractions(ownerService);
    }

    @Test
    void displayOwner() throws Exception{

        // Set up the Mock interaction
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));

        /*
         The above code performs an HTTP GET request and defines certain expectations for the received response.
         Matchers are used to define the individual expectations. If one of the expectations is not met, the test fails.

         The expectations include: a successful request, a returned view name (owners/ownerDetails) and an attribute
         added to the model with the name 'owners' which is expected to have an ID value of 1, since this has been
         defined by the mock behaviour.
        */
    }
}