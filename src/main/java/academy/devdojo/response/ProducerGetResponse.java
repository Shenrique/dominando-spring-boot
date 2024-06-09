package academy.devdojo.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProducerGetResponse {

    private Long id;
    private String name;

}
