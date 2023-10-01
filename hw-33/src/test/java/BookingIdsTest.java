import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.main.automation.base.BaseTestNG;
import org.main.automation.utils.BookingIds;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;

public class BookingIds extends BaseTestNG {

    @DataProvider
    public Object[][] getBookingIdsData() {
        return new Object[][]{
                {new BookingIds("Tabby", "Dunphy", new BookingIds.BookingDates(LocalDate.of(2018, 1, 1), LocalDate.of(2019, 1, 1)))},
                {new BookingIds("", "", new BookingIds.BookingDates(LocalDate.of(1, 1, 1), LocalDate.of(1, 1, 1)))},
        };
    }

    @Test(dataProvider = "getBookingIdsData")
    public void getBookingIdTest (BookingRequest bookingRequest) {
        RestAssured.given()
                .header(new Header("Content-Type", "application/json"))
                .header(new Header("Accept",    "application/json"))
                .body(bookingRequest)
                .when()
                .get("https://restful-booker.herokuapp.com/booking")
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
