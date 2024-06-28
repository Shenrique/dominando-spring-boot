package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeHardCodedRespositoryTest {

    @InjectMocks
    private AnimeHardCodedRespository repository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        var digimon = Anime.builder().id(1L).name("Digimon").build();
        var looney = Anime.builder().id(2L).name("Looney").build();
        var mma = Anime.builder().id(3L).name("MMA").build();
        animes = new ArrayList<>(List.of(digimon, looney, mma));

        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }

    @Test
    @Order(1)
    @DisplayName("findAll() returns a lista with all animes")
    void findAll_ReturnsAllAnimes_WhenSuccessfull() {
        var animes = repository.findAll();

        org.assertj.core.api.Assertions.assertThat(animes).hasSameElementsAs(this.animes);
    }

    @Test
    @Order(2)
    @DisplayName("findById() returns an object with given id")
    void findById_ReturnAAnime_WhenSuccessfull() {
        var animeOptional = repository.findById(3L);

        org.assertj.core.api.Assertions.assertThat(animeOptional).contains(animes.get(2));
    }

    @Test
    @Order(3)
    @DisplayName("findByName() returns all animes when name is null")
    void findByName_ReturnAllAnimes_WhenNameIsNullSuccessfull() {
        var animeOptional = repository.findByName(null);

        org.assertj.core.api.Assertions.assertThat(animeOptional).hasSameElementsAs(this.animes);
    }

    @Test
    @Order(4)
    @DisplayName("findByName() returns List with filter animes when name is not null")
    void findByName_ReturnAllAnimes_WhenNameNotNullSuccessfull() {
        var anime = repository.findByName("Digimon");

        org.assertj.core.api.Assertions.assertThat(anime).hasSize(1).contains(this.animes.get(0));
    }

    @Test
    @Order(5)
    @DisplayName("findByName() returns empty List when no anime is found")
    void findByName_ReturnsEmptyListOfAnime_WhenNothingIsFound() {
        var anime = repository.findByName("xxxxx");

        org.assertj.core.api.Assertions.assertThat(anime).isNotNull().isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("save() creates a anime")
    void save_CreateAnime_WhenSuccessful() {
        var animeToBeSaved = Anime.builder()
                .id(99L)
                .name("Mr Bean")
                .build();

        var anime = repository.save(animeToBeSaved);
        var animes = repository.findAll();

        org.assertj.core.api.Assertions.assertThat(anime).isEqualTo(animeToBeSaved).hasNoNullFieldsOrProperties();
        org.assertj.core.api.Assertions.assertThat(animes).contains(animeToBeSaved);
    }

    @Test
    @Order(7)
    @DisplayName("delete() removes a anime")
    void delete_RemoveAnime_WhenSuccessful() {
        Anime animeToBeDelete = this.animes.get(0);
        repository.delete(animeToBeDelete);

        org.assertj.core.api.Assertions.assertThat(this.animes).doesNotContain(animeToBeDelete);
    }

    @Test
    @Order(8)
    @DisplayName("update() updates a anime")
    void update_UpdateAnime_WhenSuccessful() {
        Anime animeToBeUpdate = this.animes.get(0);
        animeToBeUpdate.setName("AnimeUpdate");

        repository.update(animeToBeUpdate);

        org.assertj.core.api.Assertions.assertThat(this.animes).contains(animeToBeUpdate);
        this.animes.stream()
                .filter(anime -> anime.getId().equals(animeToBeUpdate.getId()))
                .findFirst()
                .ifPresent(anime -> org.assertj.core.api.Assertions.assertThat(anime.getName()).isEqualTo(animeToBeUpdate.getName()));
    }

}