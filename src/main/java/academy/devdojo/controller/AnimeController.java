package academy.devdojo.controller;


import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMaper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Log4j2
@RequestMapping(path = {"v1/animes", "v1/animes/"})
public class AnimeController {

    private static AnimeMaper MAPPERS = AnimeMaper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.info("Request receveid to list all animes, param name '{}'", name);
        var animes = Anime.getAnimes();
        var animeGetResponses = MAPPERS.toAnimeGetResponseList(animes);
        if (name == null) return ResponseEntity.ok(animeGetResponses);
        animeGetResponses = animeGetResponses
                .stream()
                .filter(anime -> anime.getName().equalsIgnoreCase(name))
                .toList();

        return ResponseEntity.ok(animeGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findbyId(@PathVariable Long id) {
        log.info("Request received find anine by id '{}'", id);
        var animeFound = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElse(null);

        var response = MAPPERS.animeGetResponse(animeFound);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        Anime anime = MAPPERS.toAnime(request);
        AnimePostResponse animePostResponse = MAPPERS.animeResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(animePostResponse);
    }
}
