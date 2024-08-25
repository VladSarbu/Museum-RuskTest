package ro.rusk.test.Museum_RuskTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ro.rusk.test.Museum_RuskTest.model.CollectionModel;

@SpringBootTest
public class RuskTesting {

    RequestSpecification httpRequest = RestAssured.given();

    @BeforeAll
    public static void before (){
        RestAssured.baseURI = "https://www.rijksmuseum.nl/api/nl/collection";

    }

    @Test
    public void collectionHttpCode200(){
        Response res = httpRequest.queryParam("key","0fiuZFh4")
                .queryParam("involvedMaker","Rembrandt+van+Rijn").get();
        res.getBody().print();
        Assertions.assertEquals(res.getStatusCode(), 200);
    }

    @Test
    public void resultPageLimit() throws JsonProcessingException {
        Response rse = httpRequest.queryParam("key","0fiuZFh4").queryParam("p", "100")
                .queryParam("ps","100").get();
    ObjectMapper objectMapper = new ObjectMapper();
        CollectionModel responsePojo = objectMapper.readValue(rse.asString(),CollectionModel.class);
        Assertions.assertEquals(100,responsePojo.getArtObjects().size());
    }

    @Test
    public void resultPageNegativeValue() throws JsonProcessingException {
        Response rse = httpRequest.queryParam("key","0fiuZFh4").queryParam("p", "100")
                .queryParam("ps","-1").get();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionModel responsePojo = objectMapper.readValue(rse.asString(),CollectionModel.class);
        Assertions.assertEquals(10,responsePojo.getArtObjects().size());
        int actualSize = responsePojo.getArtObjects().size();
        System.out.println("Actual size of the response = " + actualSize);
    }

    @Test
    public void resultPageOverLimit() throws JsonProcessingException {
        Response rse = httpRequest.queryParam("key","0fiuZFh4").queryParam("p", "10")
                .queryParam("ps","101").get();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionModel responsePojo = objectMapper.readValue(rse.asString(),CollectionModel.class);
        Assertions.assertEquals(100,responsePojo.getArtObjects().size());
        int actualSize = responsePojo.getArtObjects().size();
        System.out.println("Actual size of the response = " + actualSize);
    }

    @Test
    public void resultPageOver10000() throws JsonProcessingException {
        Response rse = httpRequest.queryParam("key","0fiuZFh4").queryParam("p", "101")
                .queryParam("ps","100").get();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionModel responsePojo = objectMapper.readValue(rse.asString(),CollectionModel.class);
        Assertions.assertEquals(0,responsePojo.getArtObjects().size());
        int actualSize = responsePojo.getArtObjects().size();
        System.out.println("Actual size of the response = " + actualSize);
    }


    @Test
    public void resultPageLimitError() throws JsonProcessingException {
        int statusCode = httpRequest.queryParam("key","0fiuZFh4").queryParam("p", "1000")
                .queryParam("ps","1000").get().getStatusCode();
        Assertions.assertEquals(statusCode, 400);
        Assertions.assertThrows(RuntimeException.class,()->httpRequest.queryParam("key","0fiuZFh4").queryParam("p", "1000")
                .queryParam("ps","1000").get());
    }

    @Test
    public void collectionWithoutKey(){
        Response res = httpRequest.queryParam("key","0fiuZFh")
                .queryParam("involvedMaker","Rembrandt+van+Rijn").get();
        res.getBody().print();
        Assertions.assertEquals(res.getStatusCode(), 401);
        int statusValue = res.getStatusCode();
        System.out.println("Status Code when invalid Key = " + statusValue);
    }

    @Test
    public void imageOnlyValidation() throws JsonProcessingException {
        Response res = httpRequest.queryParam("key","0fiuZFh4").queryParam("imgonly","false").get();
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionModel responsePojo = objectMapper.readValue(res.asString(),CollectionModel.class);
        for(var obj:responsePojo.getArtObjects()){
            Assertions.assertFalse(obj.isHasImage());

        }
    }


}
