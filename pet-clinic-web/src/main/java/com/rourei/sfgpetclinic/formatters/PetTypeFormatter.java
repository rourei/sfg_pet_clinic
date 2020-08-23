package com.rourei.sfgpetclinic.formatters;

import com.rourei.sfgpetclinic.model.PetType;
import com.rourei.sfgpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

/*
  Automatic parsing of Spring MVC probably tried to create a new PetType and assign the name to the ID value
  which is why it failed. Providing this class resolves this problem since the parsing is handled explicitly.
*/

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {

        // Retrieve all available PetTypes and iterate over them
        Collection<PetType> findPetTypes = petTypeService.findAll();

        for (PetType type : findPetTypes) {
            if (type.getName().equals(text)) {
                return type;
            }
        }

        throw new ParseException("type not found: " + text, 0);
    }

}
