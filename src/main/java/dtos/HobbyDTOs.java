package dtos;

import entities.Hobby;

import java.util.ArrayList;
import java.util.List;

public class HobbyDTOs {
    private List<HobbyDTO> all;

    public HobbyDTOs(List<HobbyDTO> all) {
        this.all = all;
    }

    public List<HobbyDTO> getAll() {
        return all;
    }

    public static List<HobbyDTO> makeDTOlist(List<Hobby> hobbies) {
        List<HobbyDTO> hobbyDTOS = new ArrayList<>();
        hobbies.forEach(hobby -> {
            hobbyDTOS.add(new HobbyDTO(hobby));
        });
        return hobbyDTOS;
    }
}
