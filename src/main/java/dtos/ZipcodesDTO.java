package dtos;

import java.util.ArrayList;
import java.util.List;

public class ZipcodesDTO {
    private List<String> all = new ArrayList<>();

    public ZipcodesDTO(List<String> zipcodes) {
        all.addAll(zipcodes);
    }

    public List<String> getAll() {
        return all;
    }
}
