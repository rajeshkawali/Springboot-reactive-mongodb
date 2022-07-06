package com.rajeshkawali.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rajeshkawali.model.Person;
import com.rajeshkawali.service.PersonService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Rajesh_Kawali
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1")
public class PersonController {

	public static final String CLASS_NAME = PersonController.class.getName();

	@Autowired
	private PersonService personService;

	@GetMapping(value = "/getAllPersons")
	public Flux<Person> getAllPersons() {
		String _function = ".getAllPersons";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personService.getAllPersons();
	}

	@GetMapping(value = "/getPersonById/{index}")
	public Mono<Person> getPersonById(@PathVariable Integer index) {
		String _function = ".getPersonById";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personService.getPersonById(index);
	}

	@GetMapping(value = "/getPersonsByGenderAndEyeColor")
	public Flux<Person> getPersonsByGenderAndEyeColor(@RequestParam("gender") String gender, @RequestParam String eyeColor) {
		String _function = ".getPersonsByGenderAndEyeColor";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personService.getPersonsByGenderAndEyeColor(gender, eyeColor);
	}

	@PostMapping(value="/save")
	public Mono<Person> savePerson(@RequestBody Mono<Person> personMono) {
		String _function = ".savePerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personService.savePerson(personMono);
	}
	
	@PutMapping("/update/{id}")
	public Mono<Person> updatePerson(@RequestBody Mono<Person> personMono, @PathVariable String id) {
		String _function = ".updatePerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personService.updatePerson(personMono, id);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public Mono<String> deletePerson(@PathVariable String id) {
		String _function = ".deletePerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personService.deletePerson(id);
	}

}
