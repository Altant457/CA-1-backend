package dtos;

import entities.Hobby;

public class HobbyDTO {
    private Long id;
    private String name, wikiLink, category, type;

    public HobbyDTO(Hobby hobby) {
        this.id = hobby.getId();
        this.name = hobby.getName();
        this.wikiLink = hobby.getWikiLink();
        this.category = hobby.getCategory();
        this.type = hobby.getType();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }
}
