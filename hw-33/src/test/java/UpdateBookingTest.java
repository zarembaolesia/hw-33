import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.main.automation.base.BaseTestNG;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class PartialUpdateBookingTest extends BaseTestNG {
    private String token;

    @BeforeTest
    public void auth (){
        Response response = RestAssured.given()
                .header(new Header("Content-Type", "application/json"))
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200)
                .body("token", is(notNullValue()))
                .extract().response();

        token = response.jsonPath().getString("token");
    }

    @Test
    public void partUpdateBookingTest () {
        RestAssured.given()
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Accept",    "application/json"))
                .header(new Header("Cookie", "token="+token))
                .baseUri("https://restful-booker.herokuapp.com/booking")
                .body("{\"totalprice\": 12670}")
                .pathParam("id", 12)
                .when()
                .patch("/{id}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Sally"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(12670))
                .body("depositpaid", equalTo(true))
                .body("additionalneeds", equalTo("Breakfast"));
    }
}
