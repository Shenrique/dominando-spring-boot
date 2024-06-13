package academy.devdojo.controller;


import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMaper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import academy.devdojo.service.AnimeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Log4j2
@RequestMapping(path = {"v1/animes", "v1/animes/"})
public class AnimeController {

    private AnimeMaper mappers;

    private AnimeService animeService;

    public AnimeController(AnimeMaper mappers, AnimeService animeService) {
        this.mappers = mappers;
        this.animeService = animeService;
    }

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.info("Request receveid to list all anime, param name '{}'", name);
        var all = animeService.findAll(name);
        var animeGetResponses = mappers.toAnimeGetResponseList(all);

        return ResponseEntity.ok(animeGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findbyId(@PathVariable Long id) {
        log.info("Request received find anime by id '{}'", id);
        var animeFound = animeService.findById(id);

        var response = mappers.toAnimeGetResponse(animeFound);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        var anime = mappers.toAnime(request);
        anime = animeService.save(anime);

        var response = mappers.toAnimePostResponse(anime);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        animeService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {

        Anime animeToUpdate = mappers.toAnime(request);
        animeService.update(animeToUpdate);

        return ResponseEntity.noContent().build();

    }
}
