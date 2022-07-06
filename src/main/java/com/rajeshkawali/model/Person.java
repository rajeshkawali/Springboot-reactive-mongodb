package com.rajeshkawali.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rajesh_Kawali
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(value = "Persons")
public class Person {

	@Id
    private String id;
	
	@JsonProperty("index")
	public Integer index;
	
	@JsonProperty("name")
	public String name;
	
	@JsonProperty("isActive")
	public Boolean isActive;
	
	@JsonProperty("registered")
	public String registered;
	
	@JsonProperty("age")
	public Integer age;
	
	@JsonProperty("gender")
	public String gender;
	
	@JsonProperty("eyeColor")
	public String eyeColor;
	
	@JsonProperty("favoriteFruit")
	public String favoriteFruit;
	
	@JsonProperty("company")
	public Company company;
	
	@JsonProperty("tags")
	public List<String> tags;

}