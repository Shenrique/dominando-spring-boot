package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Anime {

    private Long id;
    private String name;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var dbz = new Anime(1L, "DBZ");
        var pokemon = new Anime(2L, "POKEMON");
        var garfield = new Anime(3L, "GARFIELD");
        animes.addAll(List.of(dbz, pokemon, garfield));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}
