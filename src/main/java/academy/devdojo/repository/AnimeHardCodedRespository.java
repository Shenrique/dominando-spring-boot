package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimeHardCodedRespository {

    @Autowired
    private AnimeData animeData;

    public List<Anime> findAll() {
        return animeData.getAnimes();
    }

    public Optional<Anime> findById(Long id) {
        return animeData.getAnimes().stream().filter(anime -> anime.getId().equals(id)).findFirst();

    }

    public List<Anime> findByName(String name) {
        return name == null ? animeData.getAnimes() :
                animeData.getAnimes().stream().filter(anime -> anime.getName().equals(name)).toList();
    }

    public Anime save(Anime anime) {
        animeData.getAnimes().add(anime);
        return anime;
    }

    public void delete(Anime anime) {
        animeData.getAnimes().remove(anime);
    }

    public void update(Anime anime) {
        delete(anime);
        save(anime);
    }

}
