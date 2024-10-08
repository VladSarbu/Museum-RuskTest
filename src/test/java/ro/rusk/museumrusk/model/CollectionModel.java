package ro.rusk.museumrusk.model;

import lombok.Data;

import java.util.List;
@Data
public class CollectionModel {

    private Integer elapsedMilliseconds;
    private Long count;
    private CountFacets countFacets;
    private List<ArtObject> artObjects;
    private List <Facet> facets;

}
