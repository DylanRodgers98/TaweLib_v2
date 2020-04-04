package com.crowvalley.tawelib.model.resource;

public class ResourceDTO {

    private Long id;

    private ResourceType resourceType;

    private String title;

    private String year;

    public ResourceDTO(Long id, ResourceType resourceType, String title, String year) {
        this.id = id;
        this.resourceType = resourceType;
        this.title = title;
        this.year = year;
    }

    public ResourceDTO() {
        // no-op constructor as Hibernate uses Reflection to populate beans using AliasToBeanTransformer
    }

    public Long getId() {
        return id;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    @Override
    public String toString() {
        return title + " (" + year + ")";
    }

}
