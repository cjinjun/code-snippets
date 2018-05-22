package com.code.codesnippets.flux.controller;

import com.code.codesnippets.flux.pojo.User;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;


@RestController
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@PostMapping("persion/getPersion/{id}.json")
    @ResponseBody
	public User getPersion(@PathVariable("id") String id) {
		log.info("ID:" + id);
		return new User("1", "leftso", 1, "重庆.大竹林");
	}

	@GetMapping("/randomNumbers")

	public Flux<ServerSentEvent<Integer>> randomNumbers() {

		return Flux.interval(Duration.ofSeconds(1))

				.map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))

				.map(data -> ServerSentEvent.<Integer>builder()

						.event("random")

						.id(Long.toString(data.getT1()))

						.data(data.getT2())

						.build());

	}

}