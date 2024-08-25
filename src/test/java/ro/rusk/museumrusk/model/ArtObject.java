package ro.rusk.museumrusk.model;

import lombok.Data;

import java.util.List;

@Data
public class ArtObject {
    private Link links;
    private String id;
    private String objectNumber;
    private String title;
    private boolean hasImage;
    private String principalOrFirstMaker;
    private String longTitle;
    private boolean showImage;
    private boolean permitDownload;
    private WebImage webImage;
    private HeaderImage headerImage;
    private List<String> productionPlaces;

}
