package com.rajeshkawali.util;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * @author Rajesh_Kawali
 *
 */
public class FluxAndMonoExamples {

	public Flux<String> usersFlux() {
		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).log();
	}

	public Flux<String> usersFluxMap() {
		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).map(String::toUpperCase);
	}

	public Flux<String> usersFluxFilter(int number) {
		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).filter(s -> s.length() > number);
	}

	public Flux<String> usersFluxFilterMap(int number) {
		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).filter(s -> s.length() > number)
				.map(String::toUpperCase);
	}

	public Flux<String> usersFluxFlatMap() {
		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).flatMap(s -> Flux.just(s.split(""))).log();
	}

	public Flux<String> usersFluxFlatMapAsync() {
		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav"))
				.flatMap(s -> Flux.just(s.split("")).delayElements(Duration.ofMillis(new Random().nextInt(1000))))
				.log();
	}

	public Flux<String> usersFluxConcatMap() {
		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav"))
				.concatMap(s -> Flux.just(s.split("")).delayElements(Duration.ofMillis(new Random().nextInt(1000))))
				.log();
	}

	public Mono<List<String>> fruitMonoFlatMap() {
		return Mono.just("Mahesh").flatMap(s -> Mono.just(List.of(s.split("")))).log();
	}

	public Flux<String> fruitMonoFlatMapMany() {
		return Mono.just("Mahesh").flatMapMany(s -> Flux.just(s.split(""))).log();
	}

	public Flux<String> usersFluxTransform(int number) {

		Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).transform(filterData).log();
		// .filter(s -> s.length() > number);
	}

	public Flux<String> usersFluxTransformDefaultIfEmpty(int number) {

		Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).transform(filterData).defaultIfEmpty("Default")
				.log();

	}

	public Flux<String> usersFluxTransformSwitchIfEmpty(int number) {

		Function<Flux<String>, Flux<String>> filterData = data -> data.filter(s -> s.length() > number);

		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).transform(filterData)
				.switchIfEmpty(Flux.just("Vidya", "Laksh").transform(filterData)).log();

	}

	public Flux<String> usersFluxConcat() {
		var users = Flux.just("Mahesh", "Rajesh");
		var veggies = Flux.just("Hari", "Nayan");

		return Flux.concat(users, veggies);
	}

	public Flux<String> usersFluxConcatWith() {
		var users = Flux.just("Mahesh", "Rajesh");
		var veggies = Flux.just("Hari", "Nayan");

		return users.concatWith(veggies);
	}

	public Flux<String> usersMonoConcatWith() {
		var users = Mono.just("Mahesh");
		var veggies = Mono.just("Hari");

		return users.concatWith(veggies);
	}

	public Flux<String> usersFluxMerge() {
		var users = Flux.just("Mahesh", "Rajesh").delayElements(Duration.ofMillis(50));
		var veggies = Flux.just("Hari", "Nayan").delayElements(Duration.ofMillis(75));

		return Flux.merge(users, veggies);
	}

	public Flux<String> usersFluxMergeWith() {
		var users = Flux.just("Mahesh", "Rajesh").delayElements(Duration.ofMillis(50));
		var veggies = Flux.just("Hari", "Nayan").delayElements(Duration.ofMillis(75));

		return users.mergeWith(veggies);
	}

	public Flux<String> usersFluxMergeWithSequential() {
		var users = Flux.just("Mahesh", "Rajesh").delayElements(Duration.ofMillis(50));
		var veggies = Flux.just("Hari", "Nayan").delayElements(Duration.ofMillis(75));

		return Flux.mergeSequential(users, veggies);
	}

	public Flux<String> usersFluxZip() {
		var users = Flux.just("Mahesh", "Rajesh");
		var veggies = Flux.just("Hari", "Nayan");

		return Flux.zip(users, veggies, (first, second) -> first + second).log();
	}

	public Flux<String> usersFluxZipWith() {
		var users = Flux.just("Mahesh", "Rajesh");
		var veggies = Flux.just("Hari", "Nayan");

		return users.zipWith(veggies, (first, second) -> first + second).log();
	}

	public Flux<String> usersFluxZipTuple() {
		var users = Flux.just("Mahesh", "Rajesh");
		var veggies = Flux.just("Hari", "Nayan");
		var moreVeggies = Flux.just("Sandeep", "Kartik");

		return Flux.zip(users, veggies, moreVeggies)
				.map(objects -> objects.getT1() + objects.getT2() + objects.getT3());
	}

	public Mono<String> usersMonoZipWith() {
		var users = Mono.just("Mahesh");
		var veggies = Mono.just("Hari");

		return users.zipWith(veggies, (first, second) -> first + second).log();
	}

	public Mono<String> fruitMono() {
		return Mono.just("Mahesh").log();
	}

	public Flux<String> usersFluxFilterDoOn(int number) {
		return Flux.fromIterable(List.of("Mahesh", "Rajesh", "Keshav")).filter(s -> s.length() > number).doOnNext(s -> {
			System.out.println("s = " + s);
		}).doOnSubscribe(subscription -> {
			System.out.println("subscription.toString() = " + subscription.toString());
		}).doOnComplete(() -> System.out.println("Completed!!!"));
	}

	public Flux<String> usersFluxOnErrorReturn() {
		return Flux.just("Apple", "Mahesh").concatWith(Flux.error(new RuntimeException("Exception Occurred")))
				.onErrorReturn("Rajesh");
	}

	public Flux<String> usersFluxOnErrorContinue() {
		return Flux.just("Apple", "Mahesh", "Rajesh").map(s -> {
			if (s.equalsIgnoreCase("Mahesh"))
				throw new RuntimeException("Exception Occurred");
			return s.toUpperCase();
		}).onErrorContinue((e, f) -> {
			System.out.println("e = " + e);
			System.out.println("f = " + f);
		});
	}

	public Flux<String> usersFluxOnErrorMap() {
		return Flux.just("Kiran", "Mahesh", "Rajesh").checkpoint("Error Checkpoint1").map(s -> {
			if (s.equalsIgnoreCase("Mahesh"))
				throw new RuntimeException("Exception Occurred");
			return s.toUpperCase();
		}).checkpoint("Error Checkpoint2").onErrorMap(throwable -> {
			System.out.println("throwable = " + throwable);
			return new IllegalStateException("From onError Map");
		});
	}

	public Flux<String> usersFluxOnError() {
		return Flux.just("Kiran", "Mahesh", "Rajesh").map(s -> {
			if (s.equalsIgnoreCase("Mahesh"))
				throw new RuntimeException("Exception Occurred");
			return s.toUpperCase();
		}).doOnError(throwable -> {
			System.out.println("throwable = " + throwable);

		});
	}

	public static void main(String[] args) {

		FluxAndMonoExamples fluxAndMonoExamples = new FluxAndMonoExamples();

		fluxAndMonoExamples.usersFlux().subscribe(s -> {
			System.out.println("s = " + s);
		});
		System.out.println("-----------------------------------------------------------------");
		fluxAndMonoExamples.fruitMono().subscribe(s -> {
			System.out.println("Mono -> s = " + s);
		});
		System.out.println("-----------------------------------------------------------------");
	}
}
