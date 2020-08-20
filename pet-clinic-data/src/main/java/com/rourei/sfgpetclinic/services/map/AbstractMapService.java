package com.rourei.sfgpetclinic.services.map;

import com.rourei.sfgpetclinic.model.BaseEntity;

import java.util.*;

// Using generics, but ID needs to be anything that extends the Long type, T needs to extend BaseEntity
public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {

    // Long type can be used because of extension above -> allows casting
    protected Map<Long, T> map = new HashMap<>();

    // Returns all stored entries
    Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    T findById(ID id){
        return map.get(id);
    }

    T save(T object){

        if(object != null) {
            // Method getId() available, because T extends BaseEntity
            if (object.getId() == null) {
                object.setId(getNextId()); // Assign new ID if not specified
            }
            map.put(object.getId(), object); // Save the object to the map
        }
        else{
            throw new RuntimeException("Object cannot be Null");
        }

        return object;
    }

    void deleteById(ID id){
        map.remove(id);
    }

    // Requires corresponding equals method in the implementations
    void delete(T object){
        map.entrySet().removeIf(entry -> entry.getValue().equals(object)); // Lambda
    }

    // Generates the next ID value available
    private Long getNextId(){

        // Initialize ID value
        Long nextId = null;

        try {
            nextId = Collections.max(map.keySet()) + 1; // Try grabbing the maximum of existing keys and add one
        } catch (NoSuchElementException e){
            nextId = 1L; // Assign on first run (e.g. no elements in the map yet)
        }

        return nextId;
    }
}
