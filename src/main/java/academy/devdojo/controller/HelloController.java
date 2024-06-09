package academy.devdojo.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/")
@Log4j2
public class HelloController {

    @GetMapping
    public String hi() {
        return "OMAE WA MO SHINDEIRU";
    }

    @PostMapping
    public Long save(@RequestBody String nome) {
        log.info("Saving name '{}'", nome);
        return ThreadLocalRandom.current().nextLong();
    }

}
