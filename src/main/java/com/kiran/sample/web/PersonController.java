package com.kiran.sample.web;

import com.kiran.sample.domain.Person;
import com.kiran.sample.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kiranmai on 10/19/16.
 */
@Controller
public class PersonController {

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public String show(Model model, @RequestParam(value = "id", required = false) Long id) {
        model.addAttribute("person", personService.getById(id));

        return "data/person";

    }
    @ResponseBody
    @GetMapping(path = "/person/{id}")
    public ResponseEntity<Person> json(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(personService.getById(id), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(path = "/person/save")
    public ResponseEntity<List<Person>> save(@ModelAttribute("person") Person person, BindingResult result) {
        personService.save(person);
        return new ResponseEntity<>(personService.getAll(), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(path = "/person/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withBody(@RequestBody Person person, BindingResult result) {

        personValidator.validate(person, result);
        if (!result.hasErrors()) {
            personService.save(person);
            return new ResponseEntity<>(personService.getAll(), HttpStatus.OK);
        } else {
            Map<String, String> errorMap = new HashMap<>();
            for (ObjectError error : result.getAllErrors()) {
                String key = error.getCodes()[1];
                if (key != null) {
                    String message = key +":" + error.getDefaultMessage();
                    errorMap.put(key, message);
                }
            }
            return new ResponseEntity<>(errorMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
