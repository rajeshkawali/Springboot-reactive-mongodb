package com.rajeshkawali.service;

import com.rajeshkawali.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Rajesh_Kawali
 *
 */
public interface PersonService {

	public Flux<Person> getAllPersons();
	
	public Mono<Person> getPersonById(Integer index);

	public Flux<Person> getPersonsByGenderAndEyeColor(String gender, String eyeColor);

	public Mono<Person> savePerson(Mono<Person> personMono);

	public Mono<Person> updatePerson(Mono<Person> personMono, String id);
	
	public Mono<String> deletePerson(String id);

}
