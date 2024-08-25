package ro.rusk.test.Museum_RuskTest.model;

import lombok.Data;

@Data
public class HeaderImage {
    private String guid;
    private Double offsetPercentageX;
    private Double offsetPercentageY;
    private Integer width;
    private Integer height;
    private String url;
}
