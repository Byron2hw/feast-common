package com.feast.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author Byron
 * @date 2022/12/9 2:19 下午
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("test");
    }

    @GetMapping("/test-null")
    public Mono<String> testNull() {
        String s = null;
        return Mono.justOrEmpty(s);
    }

    @GetMapping("/test-list")
    public Flux<List<String>> testNull1() {
        return Flux.empty();
    }

    @GetMapping("/test-map")
    public Flux<Map<String, String>> testNull2() {
        log.info("test-map....");
        return Flux.empty();
    }
}
