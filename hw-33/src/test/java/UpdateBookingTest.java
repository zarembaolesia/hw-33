import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.main.automation.base.BaseTestNG;
import org.main.automation.utils.BookingRequest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

public class UpdateBookingTest extends BaseTestNG {
    private String token;

    @DataProvider
    public Object[][] updateBookingData() {
        return new Object[][]{
                {new BookingRequest("Helen", "Karr", 121, true, new BookingRequest.BookingDates(LocalDate.of(2018, 1, 1), LocalDate.of(2019, 1, 1)), "Supper")},
        };
    }

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
    public void partialUpdateBookingTest () {
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
                .body("firstname", equalTo("John"))
                .body("lastname", equalTo("Smith"))
                .body("totalprice", equalTo(12670))
                .body("depositpaid", equalTo(true))
                .body("additionalneeds", equalTo("Breakfast"));
    }

    @Test(dataProvider = "updateBookingData")
    public void UpdateBookingTest (BookingRequest bookingRequest) {
        RestAssured.given()
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Accept",    "application/json"))
                .header(new Header("Cookie", "token="+token))
                .baseUri("https://restful-booker.herokuapp.com/booking")
                .body(bookingRequest)
                .pathParam("id", 3)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200)
                .body("firstname", equalTo("Helen"))
                .body("lastname", equalTo("Karr"))
                .body("totalprice", equalTo(121))
                .body("depositpaid", equalTo(true))
                .body("additionalneeds", equalTo("Supper"));
    }

    @Test
    public void deleteBookingTest () {
        RestAssured.given()
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Accept",    "application/json"))
                .header(new Header("Cookie", "token="+token))
                .baseUri("https://restful-booker.herokuapp.com/booking")
                .pathParam("id", 5)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(201);

            RestAssured.given()
                    .when()
                    .get("https://restful-booker.herokuapp.com/booking/5")
                    .then()
                    .statusCode(404);
        }
    }
