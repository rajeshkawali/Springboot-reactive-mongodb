package com.rajeshkawali.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajeshkawali.model.Person;
import com.rajeshkawali.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Rajesh_Kawali
 *
 */
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {
	
	public static final String CLASS_NAME = PersonServiceImpl.class.getName();
	
	@Autowired
	private PersonRepository personRepository;

	@Override
	public Flux<Person> getAllPersons() {
		String _function = ".getAllPersons";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.getAllPersons();
	}

	@Override
	public Mono<Person> getPersonById(Integer index) {
		String _function = ".getPersonById";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.getPersonById(index);
	}

	@Override
	public Flux<Person> getPersonsByGenderAndEyeColor(String gender, String eyeColor) {
		String _function = ".getPersonsByGenderAndEyeColor";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.getPersonsByGenderAndEyeColor(gender,eyeColor);
	}

	@Override
	public Mono<Person> savePerson(Mono<Person> personMono) {
		String _function = ".savePerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.savePerson(personMono);
	}

	@Override
	public Mono<Person> updatePerson(Person person, String id) {
		String _function = ".updatePerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.updatePerson(person, id);
	}

	@Override
	public Mono<String> deletePerson(String id) {
		String _function = ".deletePerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.deletePerson(id);
	}
	
	@Override
	public Flux<Person> getAllMalePersons() {
		String _function = ".getAllMalePersons";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.getAllMalePersons();
	}

	@Override
	public Flux<String> getNameOfAllPerson() {
		String _function = ".getNameOfAllPerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.getNameOfAllPerson();
	}

	@Override
	public Flux<Person> getAllPersonwithGreenEye() {
		String _function = ".getAllPersonwithGreenEye";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.getAllPersonwithGreenEye();
	}

	@Override
	public Mono<Person> getPersonWithAgeAndEyeColor(Integer index) {
		String _function = ".getPersonWithAgeAndEyeColor";
		log.info(CLASS_NAME + _function + "::ENTER");
		return personRepository.getPersonWithAgeAndEyeColor(index);
	}

}
