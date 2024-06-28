package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;

    @Mock
    private ProducerHardCodedRepository repository;

    private List<Producer> producers;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var withStudio = Producer.builder().id(2L).name("With studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producers = new ArrayList<>(List.of(ufotable, withStudio, studioGhibli));
    }

    @Test
    @Order(1)
    @DisplayName("findAll() returns a lista with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessfull() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.producers);

        var producers = service.findAll(null);
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @Order(2)
    @DisplayName("findAll() returns a lista with found producers when name is not null")
    void findAll_ReturnsAllProducers_WhenNameIsPassedAndFoundSuccessfull() {
        var name = "Ufotable";
        List<Producer> producerFound = this.producers.stream().filter(producer -> producer.getName().equals(name)).toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(producerFound);

        var producers = service.findAll(name);
        Assertions.assertThat(producers).hasSize(1).contains(producerFound.get(0));
    }

    @Test
    @Order(3)
    @DisplayName("findAll() returns an empty list when no producer is found by name ")
    void findAll_ReturnsEmptyList_WhenNoNameIsFound() {
        var name = "x";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var producers = service.findAll(name);
        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById() returns a Optional producer when id exists")
    void findAll_ReturnsEmptyList_WhenIdExists() {
        var id = 1L;
        var expectedProducer = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(expectedProducer));

        var producerOptional = service.findById(id);
        Assertions.assertThat(producerOptional).isPresent().contains(expectedProducer);
    }

    @Test
    @Order(5)
    @DisplayName("findById() returns a empty Optional when id does not exists")
    void findAll_ReturnsEmptyOptional_WhenIdDoesNotExists() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        var producerOptional = service.findById(id);
        Assertions.assertThat(producerOptional).isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("save() create a Producer")
    void save_CreateProducer_WhenSuccessful() {
        var producerToBeSaved = Producer.builder()
                .id(99L)
                .name("MAPPA")
                .createdAt(LocalDateTime.now())
                .build();

        BDDMockito.when(repository.save(producerToBeSaved)).thenReturn(producerToBeSaved);
        var producer = service.save(producerToBeSaved);

        Assertions.assertThat(producer).isEqualTo(producerToBeSaved).hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("delete() removes a producer")
    void delete_RemoveProducer_WhenSuccessful() {
        var id = 1L;
        Producer producerToBeDelete = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerToBeDelete));
        BDDMockito.doNothing().when(repository).delete(producerToBeDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(id));
    }

    @Test
    @Order(8)
    @DisplayName("delete() removes throw ResponseStatusException when no producer is found")
    void delete_ThrowsResponseStatusException_WhenNoProducerIsFound() {
        var id = 1L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.delete(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update() updates a producer")
    void update_UpdateProducer_WhenSuccessful() {
        var id = 1L;
        Producer producerToBeUpdate = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerToBeUpdate));
        producerToBeUpdate.setName("Aniplex");

        service.update(producerToBeUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToBeUpdate));
    }

    @Test
    @Order(10)
    @DisplayName("update() updates throw ResponseStatusException when no producer is found")
    void update_UpdateProducer_WhenNoProducerIsFound() {
        var id = 1L;
        Producer producerToBeUpdate = this.producers.get(0);
        producerToBeUpdate.setName("Aniplex");

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.update(producerToBeUpdate))
                .isInstanceOf(ResponseStatusException.class);
    }
}