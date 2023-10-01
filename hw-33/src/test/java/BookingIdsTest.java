import io.restassured.RestAssured;
import org.main.automation.base.BaseTestNG;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class BookingIdsTest extends BaseTestNG {

    @Test
    public void getBookingIdTest () {
        RestAssured.given()
                .when()
                .get("https://restful-booker.herokuapp.com/booking")
                .then()
                .statusCode(200)
                .body("bookingid", is(notNullValue()));
    }
}
