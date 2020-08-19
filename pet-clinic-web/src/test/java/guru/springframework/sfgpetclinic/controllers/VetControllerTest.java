package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VetControllerTest {

    // Set up Mock objects
    @Mock
    VetService vetService;

    @Mock
    Model model;

    // Controller to be tested
    VetController controller;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize Mock objects
        MockitoAnnotations.initMocks(this);
        // (Re-)Initialize controller under test
        controller = new VetController(vetService);
    }

    @Test // The actual test
    void listOwners() {

        // ###### TEST RETURN VALUE ######

        // Retrieve returned view name and assert that it is the expected one
        String viewName = controller.listVets(model);
        assertEquals("vets/index", viewName);

        // ###### TEST INTERACTIONS ######

        // Ensure that the findAll() method of the Mock vetService is called exactly once
        verify(vetService, times(1)).findAll();

        // Ensure that the addAttributes() method of the model is called exactly once and that its arguments contain a
        // Set (-> anySet()) with the attribute name vets.
        verify(model, times(1)).addAttribute(eq("vets"), anySet());
        // It should see a set, because in the original implementation, the attribute is added via
        // vetService.findAll() which returns a Set of Vets.
    }
}