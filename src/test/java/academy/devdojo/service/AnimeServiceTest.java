package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRespository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;
    @Mock
    private AnimeHardCodedRespository repository;

    private List<Anime> animes;

    @BeforeEach
    void setUp() {
        var baki = Anime.builder().id(1L).name("Baki Hamna").build();
        var tibia = Anime.builder().id(2L).name("Tibia").build();
        var lol = Anime.builder().id(3L).name("League of Legends").build();
        animes = new ArrayList<>(List.of(baki, tibia, lol));
    }

    @Test
    @Order(1)
    @DisplayName("findAll() returns a lista with all animes")
    void findAll_ReturnsAllAnimes_WhenSuccessfull() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.animes);

        var anime = service.findAll(null);
        Assertions.assertThat(anime).hasSameElementsAs(this.animes);
    }

    @Test
    @Order(2)
    @DisplayName("findAll() returns a lista with found animes when name is not null")
    void findAll_ReturnsAllAnimes_WhenNameIsPassedAndFoundSuccessfull() {
        var name = "Baki Hamna";
        List<Anime> animeFound = this.animes.stream().filter(anime -> anime.getName().equals(name)).toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(animeFound);

        var animes = service.findAll(name);
        Assertions.assertThat(animes).hasSize(1).contains(animeFound.get(0));
    }

    @Test
    @Order(3)
    @DisplayName("findAll() returns an empty list when no anime is found by name ")
    void findAll_ReturnsEmptyList_WhenNoNameIsFound() {
        var name = "x";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var anime = service.findAll(name);
        Assertions.assertThat(anime).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById() returns a Optional anime when id exists")
    void findById_ReturnsEmptyList_WhenIdExists() {
        var id = 1L;
        var animeExpected = this.animes.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeExpected));

        var animeOptional = service.findById(id);
        Assertions.assertThat(animeOptional).isEqualTo(animeExpected);
    }

    @Test
    @Order(5)
    @DisplayName("findById() returns throw ResponseStatusException when no anime is found")
    void findById_ReturnsThrowResponseException_WhenNoAnimeIsFound() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findById(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(6)
    @DisplayName("save() create a Anime")
    void save_CreateProducer_WhenSuccessful() {
        var animeToSave = Anime.builder()
                .id(99L)
                .name("He-man")
                .build();

        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);
        var anime = service.save(animeToSave);

        Assertions.assertThat(anime)
                .isEqualTo(animeToSave)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("delete() removes a anime")
    void delete_RemoveAnime_WhenSuccessful() {
        var id = 1L;
        var AnimeToBeDelete = this.animes.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(AnimeToBeDelete));
        BDDMockito.doNothing().when(repository).delete(AnimeToBeDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));
    }

    @Test
    @Order(8)
    @DisplayName("delete() throw ResponseStatusException when no anime is found")
    void delete_ThrowsResponseStatusException_WhenNoAnimeIsFound() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update() updates a anime")
    void update_UpdateAnime_WhenSuccessful() {
        var id = 1L;
        var animeToUpdate = this.animes.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToUpdate));
        animeToUpdate.setName("Aniplex");

        service.update(animeToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToUpdate));
    }

    @Test
    @Order(10)
    @DisplayName("update() throw ResponseStatusException when no anime is found")
    void update_UpdateAnime_WhenNoProducerIsFound() {
        var id = 1L;
        var animeToUpdate = this.animes.get(0);
        animeToUpdate.setName("Aniplex");

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }

}