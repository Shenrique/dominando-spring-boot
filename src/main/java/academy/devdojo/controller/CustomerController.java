package academy.devdojo.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = {"v1/customers","v1/customers"})
public class CustomerController {

    public static final List<String> NAMES = List.of("Henrique", "Sergio", "Mota", "Brandão");

    @GetMapping
    public List<String> list(){
        return NAMES;
    }

    @GetMapping("filter")
    public List<String> filter(@RequestParam String nameParam){
        return NAMES.stream().filter(s -> s.equalsIgnoreCase(nameParam)).toList();
    }

}
