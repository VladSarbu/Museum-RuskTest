package ro.rusk.museumrusk.model;

import lombok.Data;

@Data
public class WebImage {
    private String guid;
    private Double offsetPercentageX;
    private Double offsetPercentageY;
    private Integer width;
    private Integer height;
    private String url;
}
