package dtos;

import java.util.ArrayList;
import java.util.List;

public class PersonsDTO {
    private List<PersonDTO> all = new ArrayList<>();
    public PersonsDTO(List<PersonDTO> personDTOList) {
        all.addAll(personDTOList);
    }

    public List<PersonDTO> getAll() {
        return all;
    }
}
