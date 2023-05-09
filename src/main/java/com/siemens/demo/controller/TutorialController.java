package com.siemens.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.siemens.demo.model.Item;
import com.siemens.demo.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllTutorials(@RequestParam(required = false) String name) {
        try {
            List<Item> tutorials = new ArrayList<Item>();

            if (name == null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByName(name).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getTutorialById(@PathVariable("id") long id) {
        Optional<Item> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Item> createTutorial(@RequestBody Item tutorial) {
        try {
            Item _tutorial = tutorialRepository
                    .save(new Item(tutorial.getName(), tutorial.getDescription(), tutorial.getQuantity()));
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/item_update/{id}")
    public ResponseEntity<Item> updateTutorial(@PathVariable("id") long id, @RequestBody Item tutorial) {
        Optional<Item> tutorialData = tutorialRepository.findById(id);

        if(tutorialData.isPresent()) {
            Item _tutorial = tutorialData.get();
            _tutorial.setName(tutorial.getName());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setQuantity(tutorial.getQuantity());
            return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/item_delete/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try{
            tutorialRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/items_delete")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try{
            tutorialRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}