package com.sap.icn.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by I321761 on 2017/5/22.
 */
@RestController
public class GreetingController {
    private static final String template = "Hello, %s !";
    private static final AtomicLong counter = new AtomicLong();
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "world")String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
