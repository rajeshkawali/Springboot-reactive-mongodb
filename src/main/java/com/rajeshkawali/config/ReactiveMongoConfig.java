package com.rajeshkawali.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.mongodb.reactivestreams.client.MongoClient;
/**
 * @author Rajesh_Kawali
 *
 */
@Configuration
public class ReactiveMongoConfig {

	@Autowired
	MongoClient mongoClient;
	
	//MongoClient mongoClient2 = MongoClients.create("mongodb://localhost:27017");

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(mongoClient, "india");
	}
}