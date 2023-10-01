import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.main.automation.base.BaseTestNG;
import org.main.automation.utils.BookingRequest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

public class BookingTest extends BaseTestNG {

    @DataProvider
    public Object[][] createBookingData() {
        return new Object[][]{
                {new BookingRequest("Tabby", "Dunphy", 121, true, new BookingRequest.BookingDates(LocalDate.of(2018, 1, 1), LocalDate.of(2019, 1, 1)), "Supper")},
                {new BookingRequest("", "", 121, false, new BookingRequest.BookingDates(LocalDate.of(1, 1, 1), LocalDate.of(1, 1, 1)), "")},
        };
    }

    @Test(dataProvider = "createBookingData")
    public void createBookingTest (BookingRequest bookingRequest) {
        RestAssured.given()
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Accept",    "application/json"))
                .body(bookingRequest)
                .when()
                .post("https://restful-booker.herokuapp.com/booking")
                .then()
                .statusCode(200)
                .body("bookingid", is(notNullValue()))
                .body("booking.firstname", equalTo(bookingRequest.getFirstname()))
                .body("booking.lastname", equalTo(bookingRequest.getLastname()))
                .body("booking.totalprice", equalTo(bookingRequest.getTotalprice()))
                .body("booking.depositpaid", equalTo(bookingRequest.isDepositpaid()))
                .body("booking.bookingdates.checkin", equalTo(bookingRequest.getBookingdates().getCheckin()))
                .body("booking.bookingdates.checkout", equalTo(bookingRequest.getBookingdates().getCheckout()))
                .body("booking.additionalneeds", equalTo(bookingRequest.getAdditionalneeds()));
    }
}
