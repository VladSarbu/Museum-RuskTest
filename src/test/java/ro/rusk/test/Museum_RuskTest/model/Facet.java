package ro.rusk.test.Museum_RuskTest.model;

import lombok.Data;

import java.util.List;

@Data
public class Facet {
    private List<FacetDetails> facets;
    private String name;
    private Long otherTerms;
    private Integer prettyName;
}
