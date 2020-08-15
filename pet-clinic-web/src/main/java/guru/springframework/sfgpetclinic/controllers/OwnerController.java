package guru.springframework.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners") // prefixing all following RequestMappings
@Controller
public class OwnerController {

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listOwners(){
        return "owners/index"; // make Thymeleaf look for a template in 'templates/owners' called '/index'
    }
}
