package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRespository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AnimeService {

    private AnimeHardCodedRespository repository;

    public AnimeService(AnimeHardCodedRespository repository) {
        this.repository = repository;
    }

    public List<Anime> findAll(String name) {
        return repository.findByName(name);
    }

    public Anime save(Anime anime) {
        return repository.save(anime);
    }

    public Anime findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public void delete(Long id) {
        var anime = findById(id);
        repository.delete(anime);
    }

    public void update(Anime animeToUpdate) {
        findById(animeToUpdate.getId());
        repository.update(animeToUpdate);
    }
}
