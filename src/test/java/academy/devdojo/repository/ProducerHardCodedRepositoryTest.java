package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;

    @Mock
    private ProducerData producerData;

    private List<Producer> producers;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var withStudio = Producer.builder().id(2L).name("With studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producers = new ArrayList<>(List.of(ufotable, withStudio, studioGhibli));

        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }

    @Test
    @Order(1)
    @DisplayName("findAll() returns a lista with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessfull() {
        var producers = repository.findAll();

        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @Order(2)
    @DisplayName("findById() returns an object with given id")
    void findById_ReturnAProducer_WhenSuccessfull() {
        var producerOptional = repository.findById(3L);

        Assertions.assertThat(producerOptional).contains(producers.get(2));
    }

    @Test
    @Order(3)
    @DisplayName("findByName() returns all producers when name is null")
    void findByName_ReturnAllProducer_WhenNameIsNullSuccessfull() {
        var producerOptional = repository.findByName(null);

        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @Order(4)
    @DisplayName("findByName() returns List with filter producers when name is not null")
    void findByName_ReturnAllProducer_WhenNameNotNullSuccessfull() {
        var producer = repository.findByName("Ufotable");

        Assertions.assertThat(producer).hasSize(1).contains(this.producers.get(0));
    }

    @Test
    @Order(5)
    @DisplayName("findByName() returns empty List when no producer is found")
    void findByName_ReturnsEmptyListOfProducers_WhenNothingIsFound() {
        var producer = repository.findByName("xxxxx");

        Assertions.assertThat(producer).isNotNull().isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("save() creates a producer")
    void save_CreateProducer_WhenSuccessful() {
        var producerToBeSaved = Producer.builder()
                .id(99L)
                .name("MAPPA")
                .createdAt(LocalDateTime.now())
                .build();

        var producer = repository.save(producerToBeSaved);
        var producers = repository.findAll();

        Assertions.assertThat(producer).isEqualTo(producerToBeSaved).hasNoNullFieldsOrProperties();
        Assertions.assertThat(producers).contains(producerToBeSaved);
    }

    @Test
    @Order(7)
    @DisplayName("delete() removes a producer")
    void delete_RemoveProducer_WhenSuccessful() {
        Producer producerToBeDelete = this.producers.get(0);
        repository.delete(producerToBeDelete);

        Assertions.assertThat(this.producers).doesNotContain(producerToBeDelete);
    }

    @Test
    @Order(8)
    @DisplayName("update() updates a producer")
    void update_UpdateProducer_WhenSuccessful() {
        Producer producerToBeUpdate = this.producers.get(0);
        producerToBeUpdate.setName("Aniplex");

        repository.update(producerToBeUpdate);

        Assertions.assertThat(this.producers).contains(producerToBeUpdate);
        this.producers.stream()
                .filter(producer -> producer.getId().equals(producerToBeUpdate.getId()))
                .findFirst()
                .ifPresent(producer -> Assertions.assertThat(producer.getName()).isEqualTo(producerToBeUpdate.getName()));
    }

}