package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCodedRespository {

    private static List<Anime> ANIMES = new ArrayList<>();


    static {
        var dbz = Anime.builder().id(1L).name("DBZ").build();
        var pokemon = Anime.builder().id(2L).name("POKEMON").build();
        var garfield = Anime.builder().id(3L).name("GARFIELD").build();
        ANIMES.addAll(List.of(dbz, pokemon, garfield));
    }

    public List<Anime> findAll() {
        return ANIMES;
    }

    public Optional<Anime> findById(Long id) {
        return ANIMES.stream().filter(anime -> anime.getId().equals(id)).findFirst();

    }

    public List<Anime> findByName(String name) {
        return name == null ? ANIMES :
                ANIMES.stream().filter(anime -> anime.getName().equals(name)).toList();
    }

    public Anime save(Anime anime) {
        ANIMES.add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        ANIMES.remove(anime);
    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);
    }

}
