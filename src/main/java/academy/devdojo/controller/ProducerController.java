package academy.devdojo.controller;


import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.response.ProducerGetResponse;
import academy.devdojo.response.ProducerPostResponse;
import academy.devdojo.service.ProducerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping(path = {"v1/producers", "v1/producers/"})
public class ProducerController {

    private ProducerMapper mapper;

    private ProducerService producerService;

    public ProducerController(ProducerMapper mapper, ProducerService producerService) {
        this.mapper = mapper;
        this.producerService = producerService;
    }

    @GetMapping
    public ResponseEntity<List<ProducerGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.info("Request receveid to list all producer, param name '{}'", name);
        var producers = producerService.findAll(name);
        var producerGetResponses = mapper.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producerGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducerGetResponse> findbyId(@PathVariable Long id) {
        log.info("Request received find producer by id '{}'", id);
        var producerFound = producerService.findById(id).get();

        var response = mapper.toProducerGetResponse(producerFound);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ProducerPostResponse> save(@RequestBody ProducerPostRequest request) {
        var producer = mapper.toProducer(request);
        producer = producerService.save(producer);

        var response = mapper.toProducerPostResponse(producer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        producerService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {

        Producer producerToUpdate = mapper.toProducer(request);
        producerService.update(producerToUpdate);

        return ResponseEntity.noContent().build();

    }
}
