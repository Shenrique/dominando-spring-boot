package academy.devdojo.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Anime {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;

}
