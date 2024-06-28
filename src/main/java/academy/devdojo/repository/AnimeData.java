package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimeData {

    private List<Anime> animes = new ArrayList<>();

    {
        var dbz = Anime.builder().id(1L).name("DBZ").build();
        var pokemon = Anime.builder().id(2L).name("POKEMON").build();
        var garfield = Anime.builder().id(3L).name("GARFIELD").build();
        animes.addAll(List.of(dbz, pokemon, garfield));
    }

    public List<Anime> getAnimes() {
        return animes;
    }
}
