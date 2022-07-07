package com.rajeshkawali.repository;

import java.time.Duration;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.rajeshkawali.exception.PersonException;
import com.rajeshkawali.model.Person;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Rajesh_Kawali
 *
 */
@Slf4j
@Repository
public class PersonRepositoryImpl implements PersonRepository {
	
	public static final String CLASS_NAME = PersonRepositoryImpl.class.getName();
	
	@Autowired
    ReactiveMongoTemplate mongoTemplate;
	
	@Override
	public Flux<Person> getAllPersons() {
		String _function = ".getAllPersons";
		log.info(CLASS_NAME + _function + "::ENTER");
		return mongoTemplate.findAll(Person.class).switchIfEmpty(Flux.empty());
	}
	
	@Override
	public Mono<Person> getPersonById(Integer index) {
		String _function = ".getPersonById";
		log.info(CLASS_NAME + _function + "::ENTER");
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("index").exists(true),
				Criteria.where("index").is(index)));
		log.info(CLASS_NAME + _function + "::query: {}", query);
		return mongoTemplate.findOne(query, Person.class).switchIfEmpty(Mono.empty());
	}

	@Override
	public Flux<Person> getPersonsByGenderAndEyeColor(String gender, String eyeColor) {
		String _function = ".getPersonsByGenderAndEyeColor";
		log.info(CLASS_NAME + _function + "::ENTER");
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(
				Criteria.where("gender").is(gender),
				Criteria.where("eyeColor").is(eyeColor)));
		log.info(CLASS_NAME + _function + "::query: {}", query);
		return mongoTemplate.find(query, Person.class).switchIfEmpty(Flux.empty());
	}

	@Override
	public Mono<Person> savePerson(Mono<Person> personMono) {
		String _function = ".savePerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		return mongoTemplate.save(personMono);
	}

	@SuppressWarnings("static-access")
	@Override
	public Mono<Person> updatePerson(Person person, String id) {
		Update update = new Update();
		update.set("name", person.getName());
		update.set("company.email", person.getCompany().getEmail());
		update.set("company.location.country", person.getCompany().getLocation().getCountry());
		
		Query query = new Query();
		query.addCriteria(new Criteria().where("_id").is(id));
		return mongoTemplate.findAndModify(query, update, Person.class);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public Mono<String> deletePerson(String id) {
		String _function = ".deletePerson";
		log.info(CLASS_NAME + _function + "::ENTER");
		Query query = new Query();
		query.addCriteria(new Criteria().where("_id").is(id));
		Mono<DeleteResult> result = mongoTemplate.remove(query, Person.class);
		return result.map(r -> "Deleted count is: " + r.getDeletedCount());
	}
	
	@Override
	public Flux<Person> getAllMalePersons() {
		String _function = ".getAllMalePersons";
		log.info(CLASS_NAME + _function + "::ENTER");
		return mongoTemplate.findAll(Person.class)
				.flatMap(s -> Flux.just(s).filter(p -> p.gender.equalsIgnoreCase("male")))
				.delayElements(Duration.ofMillis(100))
				.switchIfEmpty(Flux.empty())
				.log();
	}
	
	@Override
	public Flux<String> getNameOfAllPerson() {
		String _function = ".getAllMalePersons";
		log.info(CLASS_NAME + _function + "::ENTER");
		return mongoTemplate.findAll(Person.class)
				.flatMap(s -> Flux.just(s))
				.map(person ->person.getName()+"\n")
				.delayElements(Duration.ofMillis(10))
				.switchIfEmpty(Flux.just(""))
				//.defaultIfEmpty("Rajesh Kawali")
				.log();
	}
	
	@Override
	public Flux<Person> getAllPersonwithGreenEye() {
		String _function = ".getAllPersonwithGreenEye";
		log.info(CLASS_NAME + _function + "::ENTER");
		return mongoTemplate.findAll(Person.class)
				.flatMap(s -> Flux.just(s))
				.filter(p -> p.eyeColor.equalsIgnoreCase("green"))
				.onErrorMap(throwable -> {
					log.error(CLASS_NAME + _function + "::Exception occurred :" + throwable);
					return new PersonException("Exception occurred while fetching Person");
				}).log();
	}
	
	@Override
	public Mono<Person> getPersonWithAgeAndEyeColor(Integer index) {
		String _function = ".getPersonWithAgeAndEyeColor";
		log.info(CLASS_NAME + _function + "::ENTER");
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(
						Criteria.where("index").exists(true), 
						Criteria.where("index").is(index)));
		log.info(CLASS_NAME + _function + "::query: {}", query);
		Function<Mono<Person>, Mono<Person>> filterPerson = data -> data.filter(s -> s.age > 20);
		
		return mongoTemplate.findOne(query, Person.class)
				.switchIfEmpty(Mono.empty())
				.transform(filterPerson)
				.map(s -> {
					if (s.getEyeColor().equalsIgnoreCase("green")) {
						throw new RuntimeException("Exception Occurred ");
					} else {
						return s;
					}
				})
				.doOnError(throwable -> {
					log.error(CLASS_NAME + _function + "::Exception occurred :" + throwable);
				})
				.log();
	}

}
